#!/usr/bin/env python3
"""Materialise a vNext UI text source into deployable JS/HTML overlay files.

The input JSON is an authoring source. This tool requires a clean front-end
baseline and resolves every recorded reference against the original Japanese
text before writing anything. Ambiguous or stale references fail closed.
"""
from __future__ import annotations

import argparse
import hashlib
import json
import re
import shutil
import zipfile
from dataclasses import dataclass
from pathlib import Path, PurePosixPath
from typing import Iterable

SCHEMA = "magireco-cn-ui-text-source/v2"
OFFSET_RE = re.compile(r"@(\d+)$")


class MaterializeError(RuntimeError):
    pass


@dataclass(frozen=True)
class Replacement:
    start: int
    end: int
    source_id: str
    text_id: str
    source: str
    target: str
    location: str


class SourceTree:
    def __init__(self, path: Path):
        self.path = path
        self.archive = zipfile.ZipFile(path) if path.is_file() else None

    def close(self) -> None:
        if self.archive is not None:
            self.archive.close()

    def read_bytes(self, relative: str) -> bytes:
        safe_relative(relative)
        if self.archive is not None:
            try:
                return self.archive.read(relative)
            except KeyError as exc:
                raise MaterializeError(f"baseline ZIP is missing {relative}") from exc
        target = self.path / relative
        if not target.is_file():
            raise MaterializeError(f"baseline directory is missing {relative}")
        return target.read_bytes()


def safe_relative(name: str) -> None:
    path = PurePosixPath(name)
    if not name.startswith("magica/") or name.startswith("/") or "\\" in name \
            or any(part in {"", ".", ".."} for part in path.parts):
        raise MaterializeError(f"unsafe or unsupported reference path: {name!r}")


def stable_id(prefix: str, value: str) -> str:
    return prefix + hashlib.sha1(value.encode("utf-8")).hexdigest()[:12].upper()


def read_ui_source(path: Path) -> dict:
    if path.suffix.lower() == ".zip":
        with zipfile.ZipFile(path) as zf:
            candidates = [
                name for name in zf.namelist()
                if name.endswith("uiTextList.json") and not name.endswith("/")
            ]
            if len(candidates) != 1:
                raise MaterializeError(
                    f"expected one uiTextList.json in source ZIP, found {len(candidates)}"
                )
            data = json.loads(zf.read(candidates[0]).decode("utf-8-sig"))
    else:
        data = json.loads(path.read_text("utf-8-sig"))
    if not isinstance(data, dict) or data.get("_meta", {}).get("schema") != SCHEMA:
        raise MaterializeError(f"unsupported UI text source; expected schema {SCHEMA}")
    if not isinstance(data.get("entries"), list):
        raise MaterializeError("UI text source has no entries array")
    return data


def char_index_from_byte(text: str, byte_offset: int) -> int | None:
    raw = text.encode("utf-8")
    if byte_offset < 0 or byte_offset > len(raw):
        return None
    try:
        return len(raw[:byte_offset].decode("utf-8"))
    except UnicodeDecodeError:
        return None


def all_occurrences(text: str, needle: str) -> list[int]:
    if not needle:
        return []
    result: list[int] = []
    start = 0
    while True:
        found = text.find(needle, start)
        if found < 0:
            return result
        result.append(found)
        start = found + len(needle)


def resolve_offset(text: str, source: str, raw_offset: int, tolerance: int) -> int:
    candidates: list[int] = []
    if text.startswith(source, raw_offset):
        candidates.append(raw_offset)
    byte_index = char_index_from_byte(text, raw_offset)
    if byte_index is not None and text.startswith(source, byte_index):
        candidates.append(byte_index)
    if candidates:
        return min(candidates)

    near: list[tuple[int, int]] = []
    for index in all_occurrences(text, source):
        byte_position = len(text[:index].encode("utf-8"))
        distance = min(abs(index - raw_offset), abs(byte_position - raw_offset))
        if distance <= tolerance:
            near.append((distance, index))
    near.sort()
    if not near:
        raise MaterializeError(
            f"source text not found near recorded offset {raw_offset} (±{tolerance})"
        )
    if len(near) > 1 and near[0][0] == near[1][0]:
        raise MaterializeError(
            f"recorded offset {raw_offset} resolves ambiguously to {near[0][1]} and {near[1][1]}"
        )
    return near[0][1]


def decode_text(raw: bytes, filename: str) -> tuple[str, bool]:
    bom = raw.startswith(b"\xef\xbb\xbf")
    try:
        return raw.decode("utf-8-sig"), bom
    except UnicodeDecodeError as exc:
        raise MaterializeError(f"{filename}: baseline is not UTF-8: {exc}") from exc


def build_replacements(
    data: dict, source_tree: SourceTree, tolerance: int
) -> dict[str, tuple[str, bool, list[Replacement]]]:
    refs_by_file: dict[str, list[tuple[str, str, str, str, dict]]] = {}
    entry_ids: set[str] = set()
    source_ids: set[str] = set()

    for entry_index, entry in enumerate(data["entries"]):
        if not isinstance(entry, dict):
            raise MaterializeError(f"entries[{entry_index}] is not an object")
        text_id = entry.get("id")
        target = entry.get("zhCN")
        if not isinstance(text_id, str) or not isinstance(target, str) or not target:
            raise MaterializeError(f"entries[{entry_index}] has invalid id/zhCN")
        expected_text_id = stable_id("TXT_", target)
        if text_id != expected_text_id:
            raise MaterializeError(f"unstable text id {text_id}; expected {expected_text_id}")
        if text_id in entry_ids:
            raise MaterializeError(f"duplicate text id {text_id}")
        entry_ids.add(text_id)
        sources = entry.get("sources")
        if not isinstance(sources, list) or not sources:
            raise MaterializeError(f"{text_id}: sources is empty")

        for source_index, source_entry in enumerate(sources):
            if not isinstance(source_entry, dict):
                raise MaterializeError(f"{text_id}.sources[{source_index}] is not an object")
            source_id = source_entry.get("sourceId")
            source = source_entry.get("sourceJa")
            refs = source_entry.get("references")
            if not isinstance(source_id, str) or not isinstance(source, str) or not source:
                raise MaterializeError(f"{text_id}.sources[{source_index}] has invalid id/sourceJa")
            expected_source_id = stable_id("SRC_", source)
            if source_id != expected_source_id:
                raise MaterializeError(f"unstable source id {source_id}; expected {expected_source_id}")
            if source_id in source_ids:
                raise MaterializeError(f"duplicate source id {source_id}")
            source_ids.add(source_id)
            if not isinstance(refs, list) or not refs:
                raise MaterializeError(f"{source_id}: references is empty")
            for ref in refs:
                if not isinstance(ref, dict) or not isinstance(ref.get("file"), str):
                    raise MaterializeError(f"{source_id}: malformed reference")
                filename = ref["file"]
                safe_relative(filename)
                refs_by_file.setdefault(filename, []).append(
                    (text_id, target, source_id, source, ref)
                )

    output: dict[str, tuple[str, bool, list[Replacement]]] = {}
    for filename, refs in sorted(refs_by_file.items()):
        text, bom = decode_text(source_tree.read_bytes(filename), filename)
        replacements: list[Replacement] = []
        grouped: dict[tuple[str, str, str], list[dict]] = {}
        targets: dict[tuple[str, str, str], str] = {}
        for text_id, target, source_id, source, ref in refs:
            key = (text_id, source_id, source)
            grouped.setdefault(key, []).append(ref)
            targets[key] = target

        for key, source_refs in grouped.items():
            text_id, source_id, source = key
            target = targets[key]
            parsed_offsets: list[tuple[int, str]] = []
            without_offset: list[str] = []
            for ref in source_refs:
                location = str(ref.get("locationV4", ""))
                match = OFFSET_RE.search(location)
                if match:
                    parsed_offsets.append((int(match.group(1)), location))
                else:
                    without_offset.append(location)

            if parsed_offsets and without_offset:
                raise MaterializeError(
                    f"{filename} {source_id}: mixed offset and non-offset references"
                )
            if parsed_offsets:
                seen_positions: set[int] = set()
                for raw_offset, location in parsed_offsets:
                    start = resolve_offset(text, source, raw_offset, tolerance)
                    if start in seen_positions:
                        raise MaterializeError(
                            f"{filename} {source_id}: two references resolve to offset {start}"
                        )
                    seen_positions.add(start)
                    replacements.append(Replacement(
                        start, start + len(source), source_id, text_id,
                        source, target, location,
                    ))
            else:
                positions = all_occurrences(text, source)
                expected = len(source_refs)
                if len(positions) != expected:
                    raise MaterializeError(
                        f"{filename} {source_id}: expected {expected} exact occurrences, "
                        f"found {len(positions)}; reference is ambiguous or baseline is not clean"
                    )
                for start, ref in zip(positions, source_refs):
                    replacements.append(Replacement(
                        start, start + len(source), source_id, text_id,
                        source, target, str(ref.get("locationV4", "")),
                    ))

        replacements.sort(key=lambda item: (item.start, item.end))
        for previous, current in zip(replacements, replacements[1:]):
            if current.start < previous.end:
                raise MaterializeError(
                    f"{filename}: overlapping references {previous.source_id}@{previous.start} and "
                    f"{current.source_id}@{current.start}"
                )
        output[filename] = (text, bom, replacements)
    return output


def apply_replacements(text: str, replacements: Iterable[Replacement]) -> str:
    result = text
    for item in sorted(replacements, key=lambda value: value.start, reverse=True):
        if result[item.start:item.end] != item.source:
            raise MaterializeError(f"internal replacement drift at {item.source_id}@{item.start}")
        result = result[:item.start] + item.target + result[item.end:]
    return result


def write_deterministic_zip(root: Path, target: Path) -> None:
    target.parent.mkdir(parents=True, exist_ok=True)
    with zipfile.ZipFile(target, "w", compression=zipfile.ZIP_DEFLATED, compresslevel=9) as zf:
        for file in sorted(path for path in root.rglob("*") if path.is_file()):
            relative = file.relative_to(root).as_posix()
            info = zipfile.ZipInfo(relative, (1980, 1, 1, 0, 0, 0))
            info.compress_type = zipfile.ZIP_DEFLATED
            info.external_attr = 0o100644 << 16
            zf.writestr(info, file.read_bytes())


def materialize(
    source_json: Path, baseline: Path, output: Path, zip_output: Path | None,
    report_path: Path | None, tolerance: int,
) -> dict:
    data = read_ui_source(source_json)
    source_tree = SourceTree(baseline)
    try:
        plan = build_replacements(data, source_tree, tolerance)
    finally:
        source_tree.close()

    if output.exists():
        shutil.rmtree(output)
    output.mkdir(parents=True)
    reference_count = 0
    for filename, (text, bom, replacements) in plan.items():
        rendered = apply_replacements(text, replacements)
        raw = rendered.encode("utf-8")
        if bom:
            raw = b"\xef\xbb\xbf" + raw
        target = output / filename
        target.parent.mkdir(parents=True, exist_ok=True)
        target.write_bytes(raw)
        reference_count += len(replacements)

    if zip_output is not None:
        write_deterministic_zip(output, zip_output)

    report = {
        "schema": SCHEMA,
        "source": str(source_json),
        "baseline": str(baseline),
        "output": str(output),
        "files": len(plan),
        "referencesMaterialized": reference_count,
        "zip": str(zip_output) if zip_output else None,
        "zipSha256": hashlib.sha256(zip_output.read_bytes()).hexdigest() if zip_output else None,
    }
    if report_path is not None:
        report_path.parent.mkdir(parents=True, exist_ok=True)
        report_path.write_text(
            json.dumps(report, ensure_ascii=False, indent=2) + "\n", encoding="utf-8"
        )
    return report


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--source", required=True, type=Path,
                        help="uiTextList.json or a ZIP containing it")
    parser.add_argument("--baseline", required=True, type=Path,
                        help="clean front-end directory or ZIP")
    parser.add_argument("--output", required=True, type=Path,
                        help="generated overlay directory")
    parser.add_argument("--zip", dest="zip_output", type=Path)
    parser.add_argument("--report", type=Path)
    parser.add_argument("--offset-tolerance", type=int, default=128)
    args = parser.parse_args()
    if args.offset_tolerance < 0:
        parser.error("--offset-tolerance must be non-negative")
    try:
        report = materialize(
            args.source, args.baseline, args.output, args.zip_output,
            args.report, args.offset_tolerance,
        )
    except (MaterializeError, OSError, zipfile.BadZipFile, json.JSONDecodeError) as exc:
        print(f"ERROR: {exc}")
        return 1
    print(json.dumps(report, ensure_ascii=False, indent=2))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
