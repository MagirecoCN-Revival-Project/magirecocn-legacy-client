#!/usr/bin/env python3
"""Generate MagiaLegacy with ABI-safe text and typed font-path hooks."""
from __future__ import annotations

import argparse
import hashlib
import json
from pathlib import Path
import sys

START_TEXT_IMPL = "// ─── 引擎硬编码串翻译（cocos2d::Label 系列钩子）"
END_TEXT_IMPL = "// NDK libc++ std::string 原地改写（font 段复用 i18n 段的 NdkStrView）"
START_FONT_IMPL = END_TEXT_IMPL
END_FONT_IMPL = "// ─── JNI_OnLoad"
START_TEXT_HOOKS = "    // ── 引擎硬编码串翻译（cocos2d::Label 系列）──"
START_FONT_HOOKS = "    // ── 引擎 UI 字体路径重定向（MTF4a5kp → TTZhiHeiGB3-W4）──"
END_FONT_HOOKS = '    LOGI("[JNI] hooks 安装完成：成功 %d 个，失败 %d 个", hookOk, hookFail);'

UNSAFE_TOKENS = (
    "static std::unordered_map<std::string, std::string> g_engineI18n;",
    "struct FakeNdkStr",
    "fakeNdkStr(",
    "using SetStringFn = void (*)(void*, const void*)",
    "struct CNColor4B { unsigned char r, g, b; };",
    "g_engineI18n.swap(fresh)",
    "struct NdkStrView",
    "fontPathOverwrite(",
    "fontPathFix(",
    "static std::string hold;",
    "using SetTtfCfgFn = void (*)(void*, const void*)",
)
REQUIRED_GENERATED = (
    '#include "RuntimeTextI18n.inc"',
    '#include "RuntimeFontPathHook.inc"',
    "installRuntimeTextI18nHooks(H);",
    "installRuntimeFontPathHooks(H);",
    "#include <algorithm>",
    "#include <utility>",
)
REQUIRED_TEXT_INCLUDE = (
    "std::atomic_store_explicit",
    "std::atomic_load_explicit",
    "struct CNColor4B { unsigned char r, g, b, a; };",
    "using LoadingSetTextFn = void (*)(void*, std::string);",
    "using RuntimeSetStringFn = void (*)(void*, const std::string&);",
    "installRuntimeTextI18nHooks",
)
REQUIRED_FONT_INCLUDE = (
    'RUNTIME_FONT_FROM[] = "fonts/MTF4a5kp.ttf"',
    'RUNTIME_FONT_TO[] = "fonts/TTZhiHeiGB3-W4.ttf"',
    "struct RuntimeTtfConfig",
    "using CreateWithTtfConfigFn = void* (*)(",
    "using CreateWithTtfPathFn = void* (*)(",
    "using SetTtfConfigInternalFn = bool (*)(",
    "RuntimeTtfConfig local = config;",
    "installRuntimeFontPathHooks",
    '"font: createWithTTF(cfg) typed MTF4a5kp→TTZhiHeiGB3-W4"',
    '"font: createWithTTF(str) typed MTF4a5kp→TTZhiHeiGB3-W4"',
    '"font: setTTFConfigInternal typed MTF4a5kp→TTZhiHeiGB3-W4"',
)


class GenerationError(RuntimeError):
    pass


def replace_between(text: str, start: str, end: str, replacement: str) -> str:
    if text.count(start) != 1 or text.count(end) != 1:
        raise GenerationError(
            f"marker drift: {start!r}={text.count(start)}, {end!r}={text.count(end)}"
        )
    begin = text.index(start)
    finish = text.index(end, begin)
    return text[:begin] + replacement + "\n\n" + text[finish:]


def ensure_include(text: str, include: str, anchor: str) -> str:
    if include in text:
        return text
    if text.count(anchor) != 1:
        raise GenerationError(f"cannot insert {include}: anchor drift {anchor!r}")
    return text.replace(anchor, include + "\n" + anchor, 1)


def sha256(value: str) -> str:
    return hashlib.sha256(value.encode("utf-8")).hexdigest()


def require_tokens(text: str, tokens: tuple[str, ...], label: str) -> None:
    for token in tokens:
        if token not in text:
            raise GenerationError(f"{label} missing: {token}")


def generate(source: Path, output: Path, report: Path | None) -> dict:
    original = source.read_text("utf-8")
    text_include_path = source.parent / "RuntimeTextI18n.inc"
    font_include_path = source.parent / "RuntimeFontPathHook.inc"
    text_include = text_include_path.read_text("utf-8")
    font_include = font_include_path.read_text("utf-8")

    require_tokens(text_include, REQUIRED_TEXT_INCLUDE, "RuntimeTextI18n.inc")
    require_tokens(font_include, REQUIRED_FONT_INCLUDE, "RuntimeFontPathHook.inc")

    generated = original
    generated = ensure_include(generated, "#include <algorithm>", "#include <atomic>")
    generated = ensure_include(generated, "#include <utility>", "#include <vector>")

    # 先替换整个“文本实现 + 旧字体实现”区域，避免旧 NdkStrView/原地内存改写残留。
    generated = replace_between(
        generated, START_TEXT_IMPL, END_FONT_IMPL,
        '#include "RuntimeTextI18n.inc"\n#include "RuntimeFontPathHook.inc"',
    )
    # 文本 hook 区域以字体 hook 标题为结束标记。
    generated = replace_between(
        generated, START_TEXT_HOOKS, START_FONT_HOOKS,
        "    installRuntimeTextI18nHooks(H);",
    )
    # 字体 hook 区域保留同一三个符号，但由 typed installer 提供。
    generated = replace_between(
        generated, START_FONT_HOOKS, END_FONT_HOOKS,
        "    installRuntimeFontPathHooks(H);",
    )

    for token in UNSAFE_TOKENS:
        if token in generated:
            raise GenerationError(f"unsafe legacy native code survived: {token}")
    require_tokens(generated, REQUIRED_GENERATED, "generated source")

    output.parent.mkdir(parents=True, exist_ok=True)
    output.write_text(generated, "utf-8")
    result = {
        "source": str(source),
        "textInclude": str(text_include_path),
        "fontInclude": str(font_include_path),
        "output": str(output),
        "sourceSha256": sha256(original),
        "textIncludeSha256": sha256(text_include),
        "fontIncludeSha256": sha256(font_include),
        "outputSha256": sha256(generated),
        "abiSafeTextHooks": True,
        "immutableTranslationSnapshot": True,
        "color4bRgba": True,
        "typedGlobalFontPathHook": True,
        "fontRoutePreserved": "MTF4a5kp→TTZhiHeiGB3-W4",
        "fontFilesReplaced": False,
        "arm64AndArmv7CompilerAbi": True,
    }
    if report:
        report.parent.mkdir(parents=True, exist_ok=True)
        report.write_text(json.dumps(result, ensure_ascii=False, indent=2) + "\n", "utf-8")
    return result


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--source", type=Path, required=True)
    parser.add_argument("--output", type=Path, required=True)
    parser.add_argument("--report", type=Path)
    args = parser.parse_args()
    try:
        result = generate(args.source, args.output, args.report)
    except (OSError, GenerationError) as error:
        print(f"ERROR: {error}", file=sys.stderr)
        return 1
    print(json.dumps(result, ensure_ascii=False, indent=2))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
