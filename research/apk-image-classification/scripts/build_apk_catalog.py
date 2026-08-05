#!/usr/bin/env python3
"""Build deterministic physical-image and logical-frame catalogs.

This script consumes the verified outputs of ``extract_apk_images.py`` and
``extract_plist_images.py``.  It deliberately keeps physical payload instances
separate from restored logical atlas frames: the two layers have different
replacement semantics and their counts must never be added as if equivalent.
"""

from __future__ import annotations

import argparse
import csv
import hashlib
import json
import math
from datetime import datetime, timezone
from pathlib import Path
from typing import Iterable

import numpy as np
from PIL import Image


SCHEMA_VERSION = 1


def sha256_file(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as handle:
        for block in iter(lambda: handle.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest().upper()


def dct_matrix(size: int) -> np.ndarray:
    positions = np.arange(size, dtype=np.float64)
    frequencies = positions[:, None]
    matrix = np.cos((math.pi / size) * (positions + 0.5) * frequencies)
    matrix[0, :] *= math.sqrt(1.0 / size)
    matrix[1:, :] *= math.sqrt(2.0 / size)
    return matrix


DCT32 = dct_matrix(32)


def image_fingerprints(path: Path) -> dict[str, object]:
    """Return format-independent RGBA SHA-256 and a 64-bit perceptual hash."""
    with Image.open(path) as image:
        image.load()
        source_format = image.format or ""
        width, height = image.size
        frames = int(getattr(image, "n_frames", 1))
        mode = image.mode
        has_alpha = "A" in image.getbands() or "transparency" in image.info
        rgba = image.convert("RGBA")
        header = width.to_bytes(8, "big") + height.to_bytes(8, "big") + b"RGBA"
        pixel_sha256 = hashlib.sha256(header + rgba.tobytes()).hexdigest().upper()

        # Composite on both canonical backgrounds before averaging.  This keeps
        # transparent glyphs visible while remaining deterministic for opaque art.
        white = Image.new("RGBA", rgba.size, "white")
        black = Image.new("RGBA", rgba.size, "black")
        white.alpha_composite(rgba)
        black.alpha_composite(rgba)
        gray_white = np.asarray(
            white.convert("L").resize((32, 32), Image.Resampling.LANCZOS),
            dtype=np.float64,
        )
        gray_black = np.asarray(
            black.convert("L").resize((32, 32), Image.Resampling.LANCZOS),
            dtype=np.float64,
        )
        gray = (gray_white + gray_black) / 2.0
        dct = DCT32 @ gray @ DCT32.T
        low = dct[:8, :8]
        median = float(np.median(low.reshape(-1)[1:]))
        bits = (low >= median).reshape(-1)
        value = 0
        for bit in bits:
            value = (value << 1) | int(bit)
        phash64 = f"{value:016X}"

    return {
        "format": source_format,
        "width": width,
        "height": height,
        "mode": mode,
        "frames": frames,
        "has_alpha": has_alpha,
        "encoded_sha256": sha256_file(path),
        "pixel_sha256": pixel_sha256,
        "phash64": phash64,
        "encoded_bytes": path.stat().st_size,
    }


def read_csv(path: Path) -> list[dict[str, str]]:
    with path.open(encoding="utf-8-sig", newline="") as handle:
        return list(csv.DictReader(handle))


def write_csv(path: Path, rows: list[dict[str, object]]) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    fieldnames = list(rows[0]) if rows else []
    with path.open("w", encoding="utf-8-sig", newline="") as handle:
        writer = csv.DictWriter(handle, fieldnames=fieldnames)
        writer.writeheader()
        writer.writerows(rows)


def absolute_source(apk: Path, entry: str) -> str:
    return f"{apk}!/{entry}"


def ensure_hash(expected: str, actual: str, context: str) -> None:
    if expected and expected.upper() != actual.upper():
        raise ValueError(f"{context}: SHA-256 mismatch: manifest={expected} actual={actual}")


def catalog_physical(root: Path, apk: Path) -> list[dict[str, object]]:
    rows: list[dict[str, object]] = []
    direct = read_csv(root / "all-images.csv")
    embedded = read_csv(root / "embedded-images.csv")

    for source in direct:
        image_path = root / source["extracted_path"]
        fp = image_fingerprints(image_path)
        ensure_hash(source["sha256"], str(fp["encoded_sha256"]), source["apk_path"])
        rows.append(
            {
                "record_id": f"P-DIRECT-{int(source['index']):04d}",
                "layer": "physical",
                "physical_kind": "apk_direct",
                "source_container_absolute": str(apk),
                "source_archive_entry": source["apk_path"],
                "source_plist": "",
                "logical_texture_name": "",
                "source_locator_absolute": absolute_source(apk, source["apk_path"]),
                "source_locator_repo_relative": source["apk_path"],
                "export_path": source["extracted_path"],
                **fp,
            }
        )

    for source in embedded:
        if source.get("success", "").casefold() not in {"true", "1"}:
            continue
        image_path = root / source["output_path"]
        fp = image_fingerprints(image_path)
        ensure_hash(source["sha256"], str(fp["encoded_sha256"]), source["source_plist"])
        plist = source["source_plist"]
        logical = source["logical_texture_name"]
        rows.append(
            {
                "record_id": f"P-EMBEDDED-{int(source['index']):04d}",
                "layer": "physical",
                "physical_kind": "plist_embedded",
                "source_container_absolute": str(apk),
                "source_archive_entry": plist,
                "source_plist": plist,
                "logical_texture_name": logical,
                "source_locator_absolute": f"{absolute_source(apk, plist)}#textureImageData:{logical}",
                "source_locator_repo_relative": f"{plist}#textureImageData:{logical}",
                "export_path": source["output_path"],
                **fp,
            }
        )
    return rows


def catalog_logical(root: Path, apk: Path) -> list[dict[str, object]]:
    rows: list[dict[str, object]] = []
    for source in read_csv(root / "atlas-frames.csv"):
        if source.get("success", "").casefold() not in {"true", "1"}:
            continue
        image_path = root / source["output_path"]
        fp = image_fingerprints(image_path)
        ensure_hash(source["sha256"], str(fp["encoded_sha256"]), source["frame_name"])
        plist = source["source_plist"]
        frame = source["frame_name"]
        rows.append(
            {
                "record_id": f"L-FRAME-{int(source['index']):05d}",
                "layer": "logical_frame",
                "source_container_absolute": str(apk),
                "source_plist": plist,
                "atlas_texture_path": source["texture_path"],
                "frame_name": frame,
                "source_locator_absolute": f"{absolute_source(apk, plist)}#frame:{frame}",
                "source_locator_repo_relative": f"{plist}#frame:{frame}",
                "export_path": source["output_path"],
                "atlas_x": source["frame_x"],
                "atlas_y": source["frame_y"],
                "atlas_packed_width": source["packed_width"],
                "atlas_packed_height": source["packed_height"],
                "trimmed_width": source["trimmed_width"],
                "trimmed_height": source["trimmed_height"],
                "logical_width": source["source_width"],
                "logical_height": source["source_height"],
                "offset_x": source["offset_x"],
                "offset_y": source["offset_y"],
                "rotated": source["rotated"],
                **fp,
            }
        )
    return rows


def duplicate_summary(rows: Iterable[dict[str, object]], field: str) -> dict[str, int]:
    values = [str(row[field]) for row in rows]
    return {"instances": len(values), "unique": len(set(values))}


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("audit_root", type=Path)
    parser.add_argument("--apk", required=True, type=Path)
    parser.add_argument("--output", required=True, type=Path)
    args = parser.parse_args()

    root = args.audit_root.resolve(strict=True)
    apk = args.apk.resolve(strict=True)
    output = args.output.resolve()
    output.mkdir(parents=True, exist_ok=True)

    physical = catalog_physical(root, apk)
    logical = catalog_logical(root, apk)
    if len(physical) != 424:
        raise ValueError(f"expected 424 physical payload instances, found {len(physical)}")
    if len(logical) != 1008:
        raise ValueError(f"expected 1008 logical atlas frames, found {len(logical)}")

    write_csv(output / "apk-physical-images.csv", physical)
    write_csv(output / "apk-logical-frames.csv", logical)
    (output / "apk-physical-images.json").write_text(
        json.dumps(physical, ensure_ascii=False, indent=2) + "\n", encoding="utf-8"
    )
    (output / "apk-logical-frames.json").write_text(
        json.dumps(logical, ensure_ascii=False, indent=2) + "\n", encoding="utf-8"
    )

    summary = {
        "schema_version": SCHEMA_VERSION,
        "generated_utc": datetime.now(timezone.utc).isoformat(),
        "apk_absolute": str(apk),
        "apk_bytes": apk.stat().st_size,
        "apk_sha256": sha256_file(apk),
        "physical_payloads": {
            "instances": len(physical),
            "apk_direct": sum(row["physical_kind"] == "apk_direct" for row in physical),
            "plist_embedded": sum(row["physical_kind"] == "plist_embedded" for row in physical),
            "encoded_sha256": duplicate_summary(physical, "encoded_sha256"),
            "pixel_sha256": duplicate_summary(physical, "pixel_sha256"),
            "phash64": duplicate_summary(physical, "phash64"),
        },
        "logical_atlas_frames": {
            "instances": len(logical),
            "encoded_sha256": duplicate_summary(logical, "encoded_sha256"),
            "pixel_sha256": duplicate_summary(logical, "pixel_sha256"),
            "phash64": duplicate_summary(logical, "phash64"),
        },
        "invariant": "physical payload instances and logical atlas frames are separate layers",
    }
    (output / "apk-catalog-summary.json").write_text(
        json.dumps(summary, ensure_ascii=False, indent=2) + "\n", encoding="utf-8"
    )
    print(json.dumps(summary, ensure_ascii=False, indent=2))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
