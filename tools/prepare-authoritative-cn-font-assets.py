#!/usr/bin/env python3
"""Restore the four authoritative fonts from the project owner's CN 2.2.1 APK.

This tool never renames or substitutes one font binary for another. It locates
all four original filenames in an apktool directory, a raw APK extraction, or a
7z archive, verifies exact SHA-256/size, and copies them to assets/fonts.
"""
from __future__ import annotations

import argparse
import hashlib
import json
from pathlib import Path
import shutil
import subprocess
import sys
import tempfile

EXPECTED = {
    "MTF4a5kp.ttf": (2618612, "36dbe7b91d30d9d95713ba4b46bfa9b70f5d16bf759e45d3a043eae97da948a1"),
    "TTDaYuanGB3.ttf": (17507340, "01bbb65b3b21f8d445fe15412fc3b5864425033f534464be26de0aa7ed8150c0"),
    "TTZhiHeiGB3-W4.ttf": (8367096, "01a4be2e5fca489c30219b3bec5edac0b7c98128c5fa629c34a0208ed5b0ba34"),
    "mbm_20160902.ttf": (2450636, "37f266883643ca3e3168049a130396a4993b981747f73c4f5068afec2412f5c5"),
}


class FontSourceError(RuntimeError):
    pass


def sha256(path: Path) -> str:
    h = hashlib.sha256()
    with path.open("rb") as stream:
        for chunk in iter(lambda: stream.read(1024 * 1024), b""):
            h.update(chunk)
    return h.hexdigest()


def find_fonts(root: Path) -> dict[str, Path]:
    candidates: dict[str, list[Path]] = {name: [] for name in EXPECTED}
    direct_roots = (
        root,
        root / "assets/fonts",
        root / "assets" / "fonts",
    )
    for base in direct_roots:
        for name in EXPECTED:
            path = base / name
            if path.is_file() and path not in candidates[name]:
                candidates[name].append(path)
    missing = [name for name, paths in candidates.items() if not paths]
    if missing:
        for path in root.rglob("*.ttf"):
            if path.name in candidates and path not in candidates[path.name]:
                candidates[path.name].append(path)
    selected: dict[str, Path] = {}
    for name, paths in candidates.items():
        valid = []
        size_expected, hash_expected = EXPECTED[name]
        for path in paths:
            if path.stat().st_size == size_expected and sha256(path) == hash_expected:
                valid.append(path)
        if len(valid) != 1:
            raise FontSourceError(
                f"{name}: expected exactly one authoritative match, found {len(valid)} "
                f"under {root}"
            )
        selected[name] = valid[0]
    return selected


def extract_archive(source: Path, destination: Path) -> None:
    seven = shutil.which("7z") or shutil.which("7zz") or shutil.which("7za")
    if not seven:
        raise FontSourceError("7z/7zz/7za is required to read a .7z source archive")
    command = [seven, "x", "-y", f"-o{destination}", str(source), "*assets/fonts/*.ttf", "*assets\\fonts\\*.ttf"]
    result = subprocess.run(command, stdout=subprocess.PIPE, stderr=subprocess.STDOUT, text=True)
    if result.returncode != 0:
        raise FontSourceError(f"7z extraction failed ({result.returncode}):\n{result.stdout[-4000:]}")


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--source", type=Path, required=True,
                        help="CN apktool/raw extraction directory or project-owner supplied .7z")
    parser.add_argument("--font-root", type=Path, default=Path("assets/fonts"))
    parser.add_argument("--report", type=Path)
    args = parser.parse_args()
    temp: tempfile.TemporaryDirectory[str] | None = None
    try:
        source = args.source.resolve()
        if source.is_dir():
            search_root = source
        elif source.is_file() and source.suffix.lower() == ".7z":
            temp = tempfile.TemporaryDirectory(prefix="magireco-cn-fonts-")
            search_root = Path(temp.name)
            extract_archive(source, search_root)
        else:
            raise FontSourceError(f"unsupported or missing font source: {source}")

        found = find_fonts(search_root)
        args.font_root.mkdir(parents=True, exist_ok=True)
        rows = {}
        for name, source_path in found.items():
            destination = args.font_root / name
            temporary = destination.with_suffix(destination.suffix + ".tmp")
            shutil.copyfile(source_path, temporary)
            size_expected, hash_expected = EXPECTED[name]
            if temporary.stat().st_size != size_expected or sha256(temporary) != hash_expected:
                temporary.unlink(missing_ok=True)
                raise FontSourceError(f"post-copy verification failed: {name}")
            temporary.replace(destination)
            rows[name] = {
                "source": str(source_path),
                "destination": str(destination),
                "size": size_expected,
                "sha256": hash_expected,
            }

        report = {
            "schema": 1,
            "authority": "project-owner supplied official CN 2.2.1 APK",
            "source": str(source),
            "fontRoot": str(args.font_root),
            "fonts": rows,
            "binaryRenamesOrSubstitutions": False,
            "valid": True,
        }
        if args.report:
            args.report.parent.mkdir(parents=True, exist_ok=True)
            args.report.write_text(json.dumps(report, ensure_ascii=False, indent=2) + "\n", "utf-8")
        print(json.dumps(report, ensure_ascii=False, indent=2))
        return 0
    except (OSError, FontSourceError) as error:
        print(f"ERROR: {error}", file=sys.stderr)
        return 1
    finally:
        if temp is not None:
            temp.cleanup()


if __name__ == "__main__":
    raise SystemExit(main())
