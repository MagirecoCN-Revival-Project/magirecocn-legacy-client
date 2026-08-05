#!/usr/bin/env python3
"""Verify APK image catalogs against their exported files."""

from __future__ import annotations

import argparse
import csv
import hashlib
import json
from pathlib import Path

from PIL import Image


def read_csv(path: Path) -> list[dict[str, str]]:
    with path.open(encoding="utf-8-sig", newline="") as handle:
        return list(csv.DictReader(handle))


def sha256_file(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as handle:
        for block in iter(lambda: handle.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest().upper()


def verify_rows(rows: list[dict[str, str]], audit_root: Path) -> dict[str, int]:
    missing = hash_mismatch = size_mismatch = decode_failures = 0
    for row in rows:
        path = audit_root / row["export_path"]
        if not path.is_file():
            missing += 1
            continue
        if sha256_file(path) != row["encoded_sha256"]:
            hash_mismatch += 1
        try:
            with Image.open(path) as image:
                image.load()
                if image.size != (int(row["width"]), int(row["height"])):
                    size_mismatch += 1
        except Exception:
            decode_failures += 1
    return {
        "rows": len(rows),
        "missing": missing,
        "hash_mismatch": hash_mismatch,
        "size_mismatch": size_mismatch,
        "decode_failures": decode_failures,
    }


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("manifest_root", type=Path)
    parser.add_argument("--audit-root", required=True, type=Path)
    args = parser.parse_args()
    manifest_root = args.manifest_root.resolve(strict=True)
    audit_root = args.audit_root.resolve(strict=True)

    physical = read_csv(manifest_root / "apk-physical-images.csv")
    logical = read_csv(manifest_root / "apk-logical-frames.csv")
    physical_json = json.loads((manifest_root / "apk-physical-images.json").read_text(encoding="utf-8"))
    logical_json = json.loads((manifest_root / "apk-logical-frames.json").read_text(encoding="utf-8"))
    summary = json.loads((manifest_root / "apk-catalog-summary.json").read_text(encoding="utf-8"))

    result = {
        "physical": verify_rows(physical, audit_root),
        "logical_frames": verify_rows(logical, audit_root),
        "csv_json_count_equal": len(physical) == len(physical_json) and len(logical) == len(logical_json),
        "summary_count_equal": (
            summary["physical_payloads"]["instances"] == len(physical)
            and summary["logical_atlas_frames"]["instances"] == len(logical)
        ),
        "layers_separate": all(row["layer"] == "physical" for row in physical)
        and all(row["layer"] == "logical_frame" for row in logical),
    }
    failures = sum(
        result[layer][key]
        for layer in ("physical", "logical_frames")
        for key in ("missing", "hash_mismatch", "size_mismatch", "decode_failures")
    )
    ok = (
        failures == 0
        and result["physical"]["rows"] == 424
        and result["logical_frames"]["rows"] == 1008
        and result["csv_json_count_equal"]
        and result["summary_count_equal"]
        and result["layers_separate"]
    )
    result["ok"] = ok
    print(json.dumps(result, ensure_ascii=False, indent=2))
    return 0 if ok else 1


if __name__ == "__main__":
    raise SystemExit(main())
