#!/usr/bin/env python3
"""Inventory and safely extract raster images from an APK/ZIP archive."""

from __future__ import annotations

import argparse
import csv
import hashlib
import json
from pathlib import Path, PurePosixPath
from zipfile import ZipFile

from PIL import Image


IMAGE_EXTENSIONS = {
    ".png", ".jpg", ".jpeg", ".webp", ".gif", ".bmp", ".dib",
    ".ico", ".tif", ".tiff", ".ktx", ".ktx2", ".pvr", ".astc",
    ".dds", ".pkm", ".qoi", ".avif", ".heic", ".heif",
}


def detect_image_format(data: bytes) -> str | None:
    if data.startswith(b"\x89PNG\r\n\x1a\n"):
        return "PNG"
    if data.startswith(b"\xff\xd8\xff"):
        return "JPEG"
    if data.startswith((b"GIF87a", b"GIF89a")):
        return "GIF"
    if data.startswith(b"RIFF") and data[8:12] == b"WEBP":
        return "WEBP"
    if data.startswith(b"BM"):
        return "BMP"
    if data.startswith(b"\x00\x00\x01\x00"):
        return "ICO"
    if data.startswith((b"II*\x00", b"MM\x00*")):
        return "TIFF"
    if data.startswith(b"\xabKTX 11\xbb\r\n\x1a\n"):
        return "KTX1"
    if data.startswith(b"\xabKTX 20\xbb\r\n\x1a\n"):
        return "KTX2"
    if data.startswith(b"PVR\x03"):
        return "PVR3"
    if data.startswith(b"DDS "):
        return "DDS"
    if data.startswith(b"\x13\xab\xa1\x5c"):
        return "ASTC"
    if data.startswith(b"PKM "):
        return "PKM"
    if data.startswith(b"qoif"):
        return "QOI"
    if len(data) >= 12 and data[4:8] == b"ftyp":
        brand = data[8:12]
        if brand in {b"avif", b"avis"}:
            return "AVIF"
        if brand in {b"heic", b"heix", b"hevc", b"hevx", b"mif1", b"msf1"}:
            return "HEIF"
    return None


def safe_output_path(root: Path, archive_name: str) -> Path:
    posix = PurePosixPath(archive_name)
    if posix.is_absolute() or ".." in posix.parts:
        raise ValueError(f"unsafe archive path: {archive_name}")
    output = root.joinpath(*posix.parts)
    output.resolve().relative_to(root.resolve())
    return output


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("apk", type=Path)
    parser.add_argument("output", type=Path)
    args = parser.parse_args()

    apk = args.apk.resolve(strict=True)
    output = args.output.resolve()
    images_root = output / "all-images"
    images_root.mkdir(parents=True, exist_ok=True)

    rows: list[dict[str, object]] = []
    extension_candidates = 0
    magic_candidates = 0

    with ZipFile(apk) as archive:
        for entry in archive.infolist():
            if entry.is_dir():
                continue
            suffix = PurePosixPath(entry.filename).suffix.lower()
            extension_match = suffix in IMAGE_EXTENSIONS
            with archive.open(entry) as source:
                data = source.read()
            magic_format = detect_image_format(data[:64])
            if extension_match:
                extension_candidates += 1
            if magic_format:
                magic_candidates += 1
            if not extension_match and not magic_format:
                continue

            target = safe_output_path(images_root, entry.filename)
            target.parent.mkdir(parents=True, exist_ok=True)
            target.write_bytes(data)

            width = height = frames = None
            mode = ""
            has_alpha = False
            decode_ok = False
            decode_error = ""
            try:
                with Image.open(target) as image:
                    width, height = image.size
                    mode = image.mode
                    frames = getattr(image, "n_frames", 1)
                    has_alpha = "A" in image.getbands() or "transparency" in image.info
                    image.verify()
                decode_ok = True
            except Exception as error:  # Preserve non-Pillow texture images in the inventory.
                decode_error = f"{type(error).__name__}: {error}"

            rows.append(
                {
                    "index": len(rows) + 1,
                    "apk_path": entry.filename,
                    "extension": suffix,
                    "magic_format": magic_format or "",
                    "extension_match": extension_match,
                    "compressed_bytes": entry.compress_size,
                    "uncompressed_bytes": entry.file_size,
                    "sha256": hashlib.sha256(data).hexdigest().upper(),
                    "width": width if width is not None else "",
                    "height": height if height is not None else "",
                    "mode": mode,
                    "frames": frames if frames is not None else "",
                    "has_alpha": has_alpha,
                    "decode_ok": decode_ok,
                    "decode_error": decode_error,
                    "extracted_path": target.relative_to(output).as_posix(),
                }
            )

    fieldnames = list(rows[0]) if rows else []
    with (output / "all-images.csv").open("w", encoding="utf-8-sig", newline="") as handle:
        writer = csv.DictWriter(handle, fieldnames=fieldnames)
        writer.writeheader()
        writer.writerows(rows)

    summary = {
        "apk": str(apk),
        "apk_bytes": apk.stat().st_size,
        "apk_sha256": hashlib.sha256(apk.read_bytes()).hexdigest().upper(),
        "image_count": len(rows),
        "extension_candidates": extension_candidates,
        "magic_candidates": magic_candidates,
        "magic_without_known_extension": sum(
            1 for row in rows if row["magic_format"] and not row["extension_match"]
        ),
        "known_extension_without_magic": sum(
            1 for row in rows if row["extension_match"] and not row["magic_format"]
        ),
        "pillow_decodable": sum(1 for row in rows if row["decode_ok"]),
        "pillow_decode_failures": sum(1 for row in rows if not row["decode_ok"]),
        "formats": {},
    }
    for row in rows:
        key = str(row["magic_format"] or row["extension"] or "UNKNOWN")
        summary["formats"][key] = summary["formats"].get(key, 0) + 1

    (output / "all-images-summary.json").write_text(
        json.dumps(summary, ensure_ascii=False, indent=2) + "\n", encoding="utf-8"
    )
    print(json.dumps(summary, ensure_ascii=False, indent=2))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
