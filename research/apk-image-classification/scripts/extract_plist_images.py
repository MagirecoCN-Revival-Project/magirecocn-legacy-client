#!/usr/bin/env python3
"""Extract plist-embedded textures and restore logical sprite-atlas frames.

The APK is read-only. Outputs preserve their source hierarchy so duplicate frame
names from different atlases never overwrite one another.
"""

from __future__ import annotations

import argparse
import base64
import csv
import gzip
import hashlib
import io
import json
import plistlib
import re
from collections import Counter, defaultdict
from datetime import datetime, timezone
from pathlib import Path, PurePosixPath
from zipfile import ZipFile

from PIL import Image


NUMBER_RE = re.compile(r"-?\d+(?:\.\d+)?")


def sha256_bytes(data: bytes) -> str:
    return hashlib.sha256(data).hexdigest().upper()


def sha256_file(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as source:
        for block in iter(lambda: source.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest().upper()


def detect_image_format(data: bytes) -> str | None:
    if data.startswith(b"\x89PNG\r\n\x1a\n"):
        return "PNG"
    if data.startswith(b"\xff\xd8\xff"):
        return "JPEG"
    if data.startswith((b"II*\x00", b"MM\x00*")):
        return "TIFF"
    if data.startswith((b"GIF87a", b"GIF89a")):
        return "GIF"
    if data.startswith(b"RIFF") and data[8:12] == b"WEBP":
        return "WEBP"
    if data.startswith(b"BM"):
        return "BMP"
    return None


def extension_for_format(image_format: str) -> str:
    return {
        "PNG": ".png",
        "JPEG": ".jpg",
        "TIFF": ".tiff",
        "GIF": ".gif",
        "WEBP": ".webp",
        "BMP": ".bmp",
    }.get(image_format, ".bin")


def safe_relative_path(value: str) -> PurePosixPath:
    value = value.replace("\\", "/")
    result = PurePosixPath(value)
    if result.is_absolute() or ".." in result.parts or not result.parts:
        raise ValueError(f"unsafe relative path: {value!r}")
    return result


def output_path(root: Path, relative: PurePosixPath) -> Path:
    target = root.joinpath(*relative.parts)
    target.resolve().relative_to(root.resolve())
    return target


def parse_numbers(value: object, expected: int, field: str) -> list[float]:
    if isinstance(value, (tuple, list)):
        numbers = [float(item) for item in value]
    else:
        numbers = [float(item) for item in NUMBER_RE.findall(str(value))]
    if len(numbers) != expected:
        raise ValueError(f"{field}: expected {expected} numbers, got {value!r}")
    return numbers


def integral(value: float, field: str) -> int:
    rounded = round(value)
    if abs(value - rounded) > 1e-6:
        raise ValueError(f"{field}: expected integral pixel coordinate, got {value}")
    return int(rounded)


def resolve_texture_entry(plist_entry: str, payload: dict, archive_names: set[str]) -> tuple[str, list[str]]:
    metadata = payload.get("metadata") or {}
    candidates: list[str] = []
    if isinstance(metadata, dict):
        candidates.extend(
            str(item)
            for item in (metadata.get("realTextureFileName"), metadata.get("textureFileName"))
            if item
        )
    if payload.get("textureFileName"):
        candidates.append(str(payload["textureFileName"]))
    candidates.append(PurePosixPath(plist_entry).stem + ".png")

    for candidate in dict.fromkeys(candidates):
        candidate = candidate.replace("\\", "/")
        if candidate in archive_names:
            return candidate, candidates
        sibling = str(PurePosixPath(plist_entry).parent / candidate)
        if sibling in archive_names:
            return sibling, candidates
    raise FileNotFoundError(f"texture not found for {plist_entry}: {candidates}")


def unpack_frame(frame: dict, plist_format: int) -> dict[str, object]:
    """Normalize TexturePacker/Cocos plist formats 0, 2, and 3."""
    if plist_format == 0:
        x = integral(float(frame["x"]), "x")
        y = integral(float(frame["y"]), "y")
        packed_w = integral(float(frame["width"]), "width")
        packed_h = integral(float(frame["height"]), "height")
        trim_w, trim_h = packed_w, packed_h
        source_w = integral(float(frame["originalWidth"]), "originalWidth")
        source_h = integral(float(frame["originalHeight"]), "originalHeight")
        offset_x = float(frame.get("offsetX", 0))
        offset_y = float(frame.get("offsetY", 0))
        paste_x = integral((source_w - trim_w) / 2 + offset_x, "paste_x")
        # Cocos offsets use an upward-positive Y axis; raster rows point downward.
        paste_y = integral((source_h - trim_h) / 2 - offset_y, "paste_y")
        rotated = bool(frame.get("rotated", False))
    elif plist_format == 2:
        x, y, rect_w, rect_h = parse_numbers(frame["frame"], 4, "frame")
        x, y = integral(x, "frame.x"), integral(y, "frame.y")
        packed_w, packed_h = integral(rect_w, "frame.width"), integral(rect_h, "frame.height")
        source_x, source_y, trim_w, trim_h = parse_numbers(frame["sourceColorRect"], 4, "sourceColorRect")
        paste_x, paste_y = integral(source_x, "sourceColorRect.x"), integral(source_y, "sourceColorRect.y")
        trim_w, trim_h = integral(trim_w, "sourceColorRect.width"), integral(trim_h, "sourceColorRect.height")
        source_w, source_h = parse_numbers(frame["sourceSize"], 2, "sourceSize")
        source_w, source_h = integral(source_w, "sourceSize.width"), integral(source_h, "sourceSize.height")
        offset_x, offset_y = parse_numbers(frame.get("offset", "{0,0}"), 2, "offset")
        rotated = bool(frame.get("rotated", False))
    elif plist_format == 3:
        x, y, rect_w, rect_h = parse_numbers(frame["textureRect"], 4, "textureRect")
        x, y = integral(x, "textureRect.x"), integral(y, "textureRect.y")
        packed_w, packed_h = integral(rect_w, "textureRect.width"), integral(rect_h, "textureRect.height")
        trim_w, trim_h = parse_numbers(frame["spriteSize"], 2, "spriteSize")
        trim_w, trim_h = integral(trim_w, "spriteSize.width"), integral(trim_h, "spriteSize.height")
        source_w, source_h = parse_numbers(frame["spriteSourceSize"], 2, "spriteSourceSize")
        source_w, source_h = integral(source_w, "spriteSourceSize.width"), integral(source_h, "spriteSourceSize.height")
        offset_x, offset_y = parse_numbers(frame.get("spriteOffset", "{0,0}"), 2, "spriteOffset")
        paste_x = integral((source_w - trim_w) / 2 + offset_x, "paste_x")
        paste_y = integral((source_h - trim_h) / 2 - offset_y, "paste_y")
        rotated = bool(frame.get("textureRotated", False))
    else:
        raise ValueError(f"unsupported plist atlas format: {plist_format}")

    # TexturePacker variants disagree on whether a rotated textureRect reports
    # packed dimensions or logical dimensions. Normalize from the independently
    # declared trimmed size so both representations are handled.
    crop_w, crop_h = packed_w, packed_h
    if rotated:
        if (packed_w, packed_h) == (trim_w, trim_h):
            crop_w, crop_h = packed_h, packed_w
        elif (packed_w, packed_h) != (trim_h, trim_w):
            raise ValueError(
                f"rotated dimensions disagree: packed={packed_w}x{packed_h}, trimmed={trim_w}x{trim_h}"
            )
    elif (packed_w, packed_h) != (trim_w, trim_h):
        raise ValueError(
            f"unrotated dimensions disagree: packed={packed_w}x{packed_h}, trimmed={trim_w}x{trim_h}"
        )

    # A few shipped TexturePacker descriptors use x=-1 or y=-1 for a frame
    # touching an atlas edge. Pillow's crop deliberately supplies transparent
    # pixels outside the RGBA texture, preserving the declared logical size.
    if min(crop_w, crop_h, trim_w, trim_h, source_w, source_h) < 0:
        raise ValueError("negative frame dimensions")
    if paste_x < 0 or paste_y < 0 or paste_x + trim_w > source_w or paste_y + trim_h > source_h:
        raise ValueError(
            f"trimmed frame outside source canvas: paste={paste_x},{paste_y} "
            f"trimmed={trim_w}x{trim_h} source={source_w}x{source_h}"
        )
    return {
        "x": x,
        "y": y,
        "packed_width": packed_w,
        "packed_height": packed_h,
        "crop_width": crop_w,
        "crop_height": crop_h,
        "trimmed_width": trim_w,
        "trimmed_height": trim_h,
        "source_width": source_w,
        "source_height": source_h,
        "offset_x": offset_x,
        "offset_y": offset_y,
        "paste_x": paste_x,
        "paste_y": paste_y,
        "rotated": rotated,
    }


def write_csv(path: Path, rows: list[dict[str, object]], fieldnames: list[str]) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    with path.open("w", encoding="utf-8-sig", newline="") as handle:
        writer = csv.DictWriter(handle, fieldnames=fieldnames, extrasaction="ignore")
        writer.writeheader()
        writer.writerows(rows)


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("apk", type=Path)
    parser.add_argument("output", type=Path, help="research/apk-image-audit-* root")
    args = parser.parse_args()

    apk = args.apk.resolve(strict=True)
    root = args.output.resolve()
    embedded_root = root / "embedded-images"
    atlas_root = root / "atlas-frames"
    embedded_root.mkdir(parents=True, exist_ok=True)
    atlas_root.mkdir(parents=True, exist_ok=True)

    embedded_rows: list[dict[str, object]] = []
    atlas_rows: list[dict[str, object]] = []
    direct_hashes: defaultdict[str, list[str]] = defaultdict(list)
    embedded_hashes: defaultdict[str, list[str]] = defaultdict(list)
    atlas_descriptors = 0
    atlas_failures = 0
    embedded_failures = 0

    with ZipFile(apk) as archive:
        entries = [entry for entry in archive.infolist() if not entry.is_dir()]
        archive_names = {entry.filename for entry in entries}
        for entry in entries:
            data = archive.read(entry)
            if detect_image_format(data):
                direct_hashes[sha256_bytes(data)].append(entry.filename)

        for entry in entries:
            if not entry.filename.lower().endswith(".plist"):
                continue
            try:
                payload = plistlib.loads(archive.read(entry))
            except Exception:
                continue
            if not isinstance(payload, dict):
                continue

            embedded_text = payload.get("textureImageData")
            if embedded_text:
                logical_name = str(payload.get("textureFileName") or (PurePosixPath(entry.filename).stem + ".png"))
                row: dict[str, object] = {
                    "index": len(embedded_rows) + 1,
                    "source_plist": entry.filename,
                    "logical_texture_name": logical_name,
                    "texture_path": logical_name,
                    "wrapper": "plist string -> base64 -> gzip",
                    "format": "",
                    "logical_extension": PurePosixPath(logical_name).suffix.lower(),
                    "extension_magic_match": False,
                    "width": "",
                    "height": "",
                    "mode": "",
                    "sha256": "",
                    "duplicate_direct_count": 0,
                    "duplicate_direct_entries": "[]",
                    "duplicate_embedded_count": 0,
                    "duplicate_embedded_sources": "[]",
                    "output_path": "",
                    "success": False,
                    "error": "",
                }
                try:
                    compressed = base64.b64decode(embedded_text, validate=True)
                    image_data = gzip.decompress(compressed)
                    image_format = detect_image_format(image_data)
                    if not image_format:
                        raise ValueError("unrecognized embedded image magic")
                    with Image.open(io.BytesIO(image_data)) as image:
                        width, height = image.size
                        mode = image.mode
                        image.verify()

                    logical = safe_relative_path(logical_name)
                    actual_suffix = extension_for_format(image_format)
                    output_name = logical.with_suffix(actual_suffix)
                    relative = safe_relative_path(str(PurePosixPath(entry.filename).with_suffix("")) + "/" + str(output_name))
                    target = output_path(embedded_root, relative)
                    target.parent.mkdir(parents=True, exist_ok=True)
                    target.write_bytes(image_data)
                    digest = sha256_bytes(image_data)
                    if sha256_file(target) != digest:
                        raise RuntimeError("written embedded image hash mismatch")
                    embedded_hashes[digest].append(f"{entry.filename}::{logical_name}")
                    row.update(
                        {
                            "format": image_format,
                            "extension_magic_match": PurePosixPath(logical_name).suffix.lower() == actual_suffix,
                            "width": width,
                            "height": height,
                            "mode": mode,
                            "sha256": digest,
                            "output_path": target.relative_to(root).as_posix(),
                            "success": True,
                        }
                    )
                except Exception as error:
                    embedded_failures += 1
                    row["error"] = f"{type(error).__name__}: {error}"
                embedded_rows.append(row)

            if "frames" not in payload:
                continue
            atlas_descriptors += 1
            metadata = payload.get("metadata") or {}
            plist_format = int(metadata.get("format", 0)) if isinstance(metadata, dict) else 0
            texture_entry = ""
            texture_candidates: list[str] = []
            texture_digest = ""
            texture_image: Image.Image | None = None
            texture_error = ""
            try:
                texture_entry, texture_candidates = resolve_texture_entry(entry.filename, payload, archive_names)
                texture_data = archive.read(texture_entry)
                texture_digest = sha256_bytes(texture_data)
                with Image.open(io.BytesIO(texture_data)) as image:
                    image.verify()
                texture_image = Image.open(io.BytesIO(texture_data)).convert("RGBA")
            except Exception as error:
                texture_error = f"{type(error).__name__}: {error}"

            for frame_name, frame in (payload.get("frames") or {}).items():
                row = {
                    "index": len(atlas_rows) + 1,
                    "source_plist": entry.filename,
                    "plist_format": plist_format,
                    "texture_path": texture_entry,
                    "texture_sha256": texture_digest,
                    "frame_name": str(frame_name),
                    "frame_x": "",
                    "frame_y": "",
                    "packed_width": "",
                    "packed_height": "",
                    "trimmed_width": "",
                    "trimmed_height": "",
                    "source_width": "",
                    "source_height": "",
                    "offset_x": "",
                    "offset_y": "",
                    "paste_x": "",
                    "paste_y": "",
                    "rotated": False,
                    "sha256": "",
                    "output_path": "",
                    "success": False,
                    "error": "",
                }
                try:
                    if texture_image is None:
                        raise RuntimeError(texture_error or f"texture unavailable; candidates={texture_candidates}")
                    geometry = unpack_frame(frame, plist_format)
                    x, y = int(geometry["x"]), int(geometry["y"])
                    crop_w, crop_h = int(geometry["crop_width"]), int(geometry["crop_height"])
                    if x >= texture_image.width or y >= texture_image.height or x + crop_w <= 0 or y + crop_h <= 0:
                        raise ValueError(
                            f"frame has no intersection with texture: rect={x},{y},{crop_w},{crop_h} "
                            f"texture={texture_image.width}x{texture_image.height}"
                        )
                    cropped = texture_image.crop((x, y, x + crop_w, y + crop_h))
                    if geometry["rotated"]:
                        cropped = cropped.transpose(Image.Transpose.ROTATE_90)
                    expected_trim = (int(geometry["trimmed_width"]), int(geometry["trimmed_height"]))
                    if cropped.size != expected_trim:
                        raise ValueError(f"restored trim size {cropped.size} != declared {expected_trim}")
                    restored = Image.new(
                        "RGBA",
                        (int(geometry["source_width"]), int(geometry["source_height"])),
                        (0, 0, 0, 0),
                    )
                    restored.alpha_composite(cropped, (int(geometry["paste_x"]), int(geometry["paste_y"])))

                    logical = safe_relative_path(str(frame_name))
                    if logical.suffix.lower() != ".png":
                        logical = logical.with_name(logical.name + ".png")
                    relative = safe_relative_path(str(PurePosixPath(entry.filename).with_suffix("")) + "/" + str(logical))
                    target = output_path(atlas_root, relative)
                    target.parent.mkdir(parents=True, exist_ok=True)
                    restored.save(target, format="PNG", optimize=False)
                    digest = sha256_file(target)
                    with Image.open(target) as check:
                        if check.size != restored.size:
                            raise RuntimeError("written atlas frame dimensions changed")
                        check.verify()
                    row.update(
                        {
                            "frame_x": geometry["x"],
                            "frame_y": geometry["y"],
                            "packed_width": geometry["packed_width"],
                            "packed_height": geometry["packed_height"],
                            "trimmed_width": geometry["trimmed_width"],
                            "trimmed_height": geometry["trimmed_height"],
                            "source_width": geometry["source_width"],
                            "source_height": geometry["source_height"],
                            "offset_x": geometry["offset_x"],
                            "offset_y": geometry["offset_y"],
                            "paste_x": geometry["paste_x"],
                            "paste_y": geometry["paste_y"],
                            "rotated": geometry["rotated"],
                            "sha256": digest,
                            "output_path": target.relative_to(root).as_posix(),
                            "success": True,
                        }
                    )
                except Exception as error:
                    atlas_failures += 1
                    row["error"] = f"{type(error).__name__}: {error}"
                atlas_rows.append(row)
            if texture_image is not None:
                texture_image.close()

    # Populate duplicate evidence only after all embedded payloads are known.
    for row in embedded_rows:
        digest = str(row["sha256"])
        if not digest:
            continue
        direct_entries = sorted(direct_hashes.get(digest, []))
        embedded_sources = sorted(embedded_hashes.get(digest, []))
        current_source = f"{row['source_plist']}::{row['logical_texture_name']}"
        embedded_peers = [item for item in embedded_sources if item != current_source]
        row["duplicate_direct_count"] = len(direct_entries)
        row["duplicate_direct_entries"] = json.dumps(direct_entries, ensure_ascii=False, separators=(",", ":"))
        row["duplicate_embedded_count"] = len(embedded_peers)
        row["duplicate_embedded_sources"] = json.dumps(embedded_peers, ensure_ascii=False, separators=(",", ":"))

    embedded_csv = root / "embedded-images.csv"
    atlas_csv = root / "atlas-frames.csv"
    write_csv(
        embedded_csv,
        embedded_rows,
        [
            "index", "source_plist", "logical_texture_name", "texture_path", "wrapper", "format", "logical_extension",
            "extension_magic_match", "width", "height", "mode", "sha256", "duplicate_direct_count",
            "duplicate_direct_entries", "duplicate_embedded_count", "duplicate_embedded_sources", "output_path",
            "success", "error",
        ],
    )
    write_csv(
        atlas_csv,
        atlas_rows,
        [
            "index", "source_plist", "plist_format", "texture_path", "texture_sha256", "frame_name",
            "frame_x", "frame_y", "packed_width", "packed_height", "trimmed_width", "trimmed_height",
            "source_width", "source_height", "offset_x", "offset_y", "paste_x", "paste_y", "rotated",
            "sha256", "output_path", "success", "error",
        ],
    )

    summary = {
        "schema_version": 1,
        "generated_utc": datetime.now(timezone.utc).isoformat(),
        "apk": str(apk),
        "apk_bytes": apk.stat().st_size,
        "apk_sha256": sha256_file(apk),
        "embedded": {
            "count": len(embedded_rows),
            "success": sum(bool(row["success"]) for row in embedded_rows),
            "failures": embedded_failures,
            "formats": dict(sorted(Counter(str(row["format"]) for row in embedded_rows if row["success"]).items())),
            "extension_magic_mismatches": sum(bool(row["success"]) and not bool(row["extension_magic_match"]) for row in embedded_rows),
            "unique_payload_sha256": len({str(row["sha256"]) for row in embedded_rows if row["sha256"]}),
            "instances_duplicate_of_direct_texture": sum(int(row["duplicate_direct_count"]) > 0 for row in embedded_rows),
            "unique_payloads_duplicate_of_direct_texture": len(
                {str(row["sha256"]) for row in embedded_rows if int(row["duplicate_direct_count"]) > 0}
            ),
            "instances_duplicate_of_other_embedded_payload": sum(int(row["duplicate_embedded_count"]) > 0 for row in embedded_rows),
        },
        "atlases": {
            "descriptor_count": atlas_descriptors,
            "frame_count": len(atlas_rows),
            "success": sum(bool(row["success"]) for row in atlas_rows),
            "failures": atlas_failures,
            "formats": dict(sorted(Counter(str(row["plist_format"]) for row in atlas_rows).items())),
            "rotated_frames": sum(bool(row["rotated"]) for row in atlas_rows),
            "trimmed_frames": sum(
                bool(row["success"])
                and (int(row["trimmed_width"]) != int(row["source_width"]) or int(row["trimmed_height"]) != int(row["source_height"]))
                for row in atlas_rows
            ),
            "unique_output_sha256": len({str(row["sha256"]) for row in atlas_rows if row["sha256"]}),
        },
        "outputs": {
            "embedded_images": embedded_root.relative_to(root).as_posix(),
            "atlas_frames": atlas_root.relative_to(root).as_posix(),
            "embedded_csv": embedded_csv.relative_to(root).as_posix(),
            "atlas_csv": atlas_csv.relative_to(root).as_posix(),
        },
    }
    summary_path = root / "plist-images-summary.json"
    summary["outputs"]["summary_json"] = summary_path.relative_to(root).as_posix()
    summary_path.write_text(json.dumps(summary, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")
    print(json.dumps(summary, ensure_ascii=False, indent=2))
    return 0 if embedded_failures == 0 and atlas_failures == 0 else 1


if __name__ == "__main__":
    raise SystemExit(main())
