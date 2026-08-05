#!/usr/bin/env python3
"""校验 APK 里的 native/Java 产物确实是本轮构建结果。"""

import os
import re
import sys
import zipfile

REPO = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
NATIVE_BUILD = os.path.join(REPO, "magia-native", "build")
PATCH_SRC = os.path.join(REPO, "patch", "src", "main", "java")
STUB_ONLY = {"RestClient"}


def rodata_sections(blob):
    import struct
    out = []
    if blob[:4] != b"\x7fELF":
        return out
    is64 = blob[4] == 2
    en = "<" if blob[5] == 1 else ">"
    if is64:
        e_shoff, = struct.unpack_from(en + "Q", blob, 0x28)
        e_shentsize, e_shnum, e_shstrndx = struct.unpack_from(en + "HHH", blob, 0x3A)
    else:
        e_shoff, = struct.unpack_from(en + "I", blob, 0x20)
        e_shentsize, e_shnum, e_shstrndx = struct.unpack_from(en + "HHH", blob, 0x2E)
    if not e_shoff or not e_shnum:
        return out

    def sh(i):
        o = e_shoff + i * e_shentsize
        name, = struct.unpack_from(en + "I", blob, o)
        if is64:
            off, = struct.unpack_from(en + "Q", blob, o + 0x18)
            size, = struct.unpack_from(en + "Q", blob, o + 0x20)
        else:
            off, = struct.unpack_from(en + "I", blob, o + 0x10)
            size, = struct.unpack_from(en + "I", blob, o + 0x14)
        return name, off, size

    _, stroff, _strsize = sh(e_shstrndx)
    for i in range(e_shnum):
        nameoff, off, size = sh(i)
        try:
            end = blob.index(b"\0", stroff + nameoff)
        except ValueError:
            continue
        name = blob[stroff + nameoff:end].decode("ascii", "replace")
        if name.startswith(".rodata"):
            out.append(blob[off:off + size])
    return out


def strings_of(blob, minlen=6):
    chunks = rodata_sections(blob) or [blob]
    result = set()
    for chunk in chunks:
        result |= set(re.findall(rb"[\x20-\x7e]{%d,}" % minlen, chunk))
    return result


def check_native(apk, problems):
    if not os.path.isdir(NATIVE_BUILD):
        return 0
    checked = 0
    with zipfile.ZipFile(apk) as zf:
        names = set(zf.namelist())
        for abi in sorted(os.listdir(NATIVE_BUILD)):
            built_path = os.path.join(NATIVE_BUILD, abi, "libMagiaLegacy.so")
            if not os.path.isfile(built_path):
                continue
            entry = "lib/%s/libMagiaLegacy.so" % abi
            if entry not in names:
                problems.append("%s 不在 APK 里" % entry)
                continue
            in_apk = zf.read(entry)
            built = open(built_path, "rb").read()
            if in_apk != built:
                missing = strings_of(built) - strings_of(in_apk)
                if missing:
                    sample = sorted(s.decode("ascii", "replace") for s in missing)[:5]
                    problems.append(
                        "%s 缺少构建产物里的 %d 条字符串（例：%s），可能仍是旧库"
                        % (entry, len(missing), "、".join(sample)))
            checked += 1
    return checked


def patch_classes():
    out = []
    for root, _dirs, files in os.walk(PATCH_SRC):
        for filename in files:
            if filename.endswith(".java"):
                name = filename[:-5]
                if name not in STUB_ONLY:
                    out.append(name)
    return sorted(out)


def dex_blobs(apk):
    blobs = []
    with zipfile.ZipFile(apk) as zf:
        for name in sorted(zf.namelist()):
            if name.startswith("classes") and name.endswith(".dex"):
                blobs.append((name, zf.read(name)))
    return blobs


def check_dex(apk, problems):
    blobs = dex_blobs(apk)
    if not blobs:
        problems.append("APK 里没有 dex")
        return 0
    for name in patch_classes():
        needle = name.encode()
        if not any(needle in blob for _dex, blob in blobs):
            problems.append("补丁类 %s 编译了却不在任何 dex 里" % name)
    return len(patch_classes())


def check_webview_overlay(apk, problems):
    """确保真正生效的是 Java 加固版，而非 classes.dex 里的旧类。"""
    blobs = dex_blobs(apk)
    combined = b"".join(blob for _name, blob in blobs)
    required = (
        b"MagiaHook-Reject",
        b"cross-origin overlay request",
        b"outside overlay root or not a file",
        b"shouldOverrideUrlLoading timed out",
    )
    for token in required:
        if token not in combined:
            problems.append(
                "加固 WebViewImpl 缺少产物标记 %r，Java 替换可能没有进入 APK" % token)
    legacy = b"/data/data/io.kamihama.totentanz/files/magica/"
    if legacy in combined:
        problems.append(
            "APK 仍含旧 WebViewImpl 的硬编码私有路径；可能出现 classes.dex/"
            "classes3.dex 重复类，实际运行的仍是未加固版本")
    return len(required)


def main():
    if len(sys.argv) < 2:
        print("用法: check-apk-freshness.py <apk>", file=sys.stderr)
        return 2
    apk = sys.argv[1]
    problems = []
    n_so = check_native(apk, problems)
    n_cls = check_dex(apk, problems)
    n_web = check_webview_overlay(apk, problems)
    if problems:
        print("✘ APK 内容与构建产物对不上：")
        for problem in problems:
            print("   " + problem)
        return 1
    print("✔ 产物新鲜度检查通过（%d 个 native、%d 个 Java 顶层类、"
          "%d 个 WebView 加固标记）" % (n_so, n_cls, n_web))
    return 0


if __name__ == "__main__":
    sys.exit(main())
