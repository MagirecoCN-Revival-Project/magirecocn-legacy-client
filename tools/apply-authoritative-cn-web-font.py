#!/usr/bin/env python3
"""Apply the official CN 2.2.1 WebView font bridge to a runtime ZIP.

The official CN client registers both CSS families, ``motoya`` and ``mbm``,
from the single ``motoya`` payload returned by native code.  This tool changes
only that exact minified anchor, preserves all ZIP metadata, and fails closed
on source drift.
"""
from __future__ import annotations

import argparse
import copy
import hashlib
import json
from pathlib import Path
import sys
import zipfile

TARGET = "magica/js/_common/base.js"
OLD = b"font-family: 'mbm'; src: url('data:font/ttf;base64,\"+\nString(a.mbm)+\"');}"
NEW = b"font-family: 'mbm'; src: url('data:font/ttf;base64,\"+\nString(a.motoya)+\"');}"


class ApplyError(RuntimeError):
    pass


def sha256(path: Path) -> str:
    return hashlib.sha256(path.read_bytes()).hexdigest()


def apply(source: Path, destination: Path) -> dict:
    destination.parent.mkdir(parents=True, exist_ok=True)
    entries = 0
    replacement_count = 0
    with zipfile.ZipFile(source, "r") as zin, zipfile.ZipFile(destination, "w") as zout:
        names = [info.filename for info in zin.infolist()]
        if TARGET not in names:
            raise ApplyError(f"missing runtime entry: {TARGET}")
        if len(names) != len(set(names)):
            raise ApplyError("duplicate ZIP entries are not allowed")
        for info in zin.infolist():
            data = zin.read(info.filename)
            if info.filename == TARGET:
                old_count = data.count(OLD)
                already_count = data.count(NEW)
                if old_count == 1 and already_count == 0:
                    data = data.replace(OLD, NEW)
                    replacement_count = 1
                elif old_count == 0 and already_count == 1:
                    replacement_count = 0
                else:
                    raise ApplyError(
                        f"official WebView font anchor drift: old={old_count}, "
                        f"official={already_count}"
                    )
            copied = copy.copy(info)
            copied.CRC = copied.file_size = copied.compress_size = 0
            zout.writestr(copied, data)
            if not info.is_dir():
                entries += 1
    with zipfile.ZipFile(destination, "r") as check:
        base = check.read(TARGET)
        start = base.index(b"font-family: 'mbm'")
        segment = base[start:start + 180]
        if b"String(a.mbm)" in segment or b"String(a.motoya)" not in segment:
            raise ApplyError("official WebView font behavior was not materialised")
    return {
        "schema": 1,
        "authority": "official CN 2.2.1 assets/resource/standalone_collection/magica/js/sa/main.js",
        "target": TARGET,
        "inputSha256": sha256(source),
        "outputSha256": sha256(destination),
        "entries": entries,
        "replacements": replacement_count,
        "motoyaFamilyPayload": "motoya",
        "mbmFamilyPayload": "motoya",
        "traditionalMachineTranslationUsed": False,
    }


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("input_zip", type=Path)
    parser.add_argument("output_zip", type=Path)
    parser.add_argument("--report", type=Path)
    args = parser.parse_args()
    try:
        report = apply(args.input_zip, args.output_zip)
        if args.report:
            args.report.parent.mkdir(parents=True, exist_ok=True)
            args.report.write_text(
                json.dumps(report, ensure_ascii=False, indent=2) + "\n", "utf-8"
            )
        print(json.dumps(report, ensure_ascii=False, indent=2))
        return 0
    except (OSError, zipfile.BadZipFile, ApplyError) as error:
        print(f"ERROR: {error}", file=sys.stderr)
        return 1


if __name__ == "__main__":
    raise SystemExit(main())
