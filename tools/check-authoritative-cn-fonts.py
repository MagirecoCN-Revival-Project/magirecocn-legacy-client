#!/usr/bin/env python3
"""Fail closed unless APK assets contain the four untouched CN 2.2.1 fonts."""
from __future__ import annotations

import argparse
import hashlib
import json
from pathlib import Path
import sys

EXPECTED = {
    "MTF4a5kp.ttf": {
        "size": 2618612,
        "sha256": "36dbe7b91d30d9d95713ba4b46bfa9b70f5d16bf759e45d3a043eae97da948a1",
        "role": "original JP UI source; runtime maps to TTZhiHei",
    },
    "TTDaYuanGB3.ttf": {
        "size": 17507340,
        "sha256": "01bbb65b3b21f8d445fe15412fc3b5864425033f534464be26de0aa7ed8150c0",
        "role": "authoritative CN story font",
    },
    "TTZhiHeiGB3-W4.ttf": {
        "size": 8367096,
        "sha256": "01a4be2e5fca489c30219b3bec5edac0b7c98128c5fa629c34a0208ed5b0ba34",
        "role": "authoritative CN UI and WebView font",
    },
    "mbm_20160902.ttf": {
        "size": 2450636,
        "sha256": "37f266883643ca3e3168049a130396a4993b981747f73c4f5068afec2412f5c5",
        "role": "original JP story source; runtime maps to TTDaYuan",
    },
}


def digest(path: Path) -> str:
    h = hashlib.sha256()
    with path.open("rb") as stream:
        for chunk in iter(lambda: stream.read(1024 * 1024), b""):
            h.update(chunk)
    return h.hexdigest()


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--font-root", type=Path, default=Path("assets/fonts"))
    parser.add_argument("--report", type=Path)
    args = parser.parse_args()

    rows = {}
    errors = []
    for name, expected in EXPECTED.items():
        path = args.font_root / name
        if not path.is_file():
            errors.append(f"missing {path}")
            continue
        actual = {"size": path.stat().st_size, "sha256": digest(path)}
        rows[name] = {**actual, "expected": expected, "valid": actual == {
            "size": expected["size"], "sha256": expected["sha256"]}}
        if actual["size"] != expected["size"]:
            errors.append(f"{name}: size {actual['size']} != {expected['size']}")
        if actual["sha256"] != expected["sha256"]:
            errors.append(f"{name}: sha256 {actual['sha256']} != {expected['sha256']}")

    # Explicitly detect the historical corruption where source font filenames were
    # overwritten with the large CN target font bytes.
    hashes = {name: row["sha256"] for name, row in rows.items()}
    for source in ("MTF4a5kp.ttf", "mbm_20160902.ttf"):
        for target in ("TTZhiHeiGB3-W4.ttf", "TTDaYuanGB3.ttf"):
            if hashes.get(source) and hashes.get(source) == hashes.get(target):
                errors.append(f"binary replacement pollution: {source} == {target}")

    report = {
        "schema": 1,
        "source": "official CN 2.2.1 APK supplied by project owner",
        "fontRoot": str(args.font_root),
        "fonts": rows,
        "directFontFileReplacement": False,
        "valid": not errors,
        "errors": errors,
    }
    if args.report:
        args.report.parent.mkdir(parents=True, exist_ok=True)
        args.report.write_text(json.dumps(report, ensure_ascii=False, indent=2) + "\n", "utf-8")
    print(json.dumps(report, ensure_ascii=False, indent=2))
    return 0 if not errors else 1


if __name__ == "__main__":
    raise SystemExit(main())
