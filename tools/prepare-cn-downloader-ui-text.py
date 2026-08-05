#!/usr/bin/env python3
"""Prepare Java sources using the editable CN downloader UI text table.

The committed Java files remain the auditable baseline. This tool copies the
source tree, replaces only explicitly listed Java string literal tokens with
constants, and generates CNCNDownloadUIText.java from the zh_CN column.
Any missing, duplicated, stale, or ambiguous table entry fails the build.
"""
from __future__ import annotations

import argparse
import json
import re
import shutil
import sys
from dataclasses import dataclass
from pathlib import Path, PurePosixPath

CLASS_NAME = "CNCNDownloadUIText"
PACKAGE = "io.kamihama.magianative"
GENERATED_RELATIVE = Path("io/kamihama/magianative/CNCNDownloadUIText.java")
KEY_RE = re.compile(r"[A-Z][A-Z0-9_]*\Z")


class TextTableError(RuntimeError):
    pass


@dataclass(frozen=True)
class Entry:
    key: str
    source_file: str
    expected_count: int
    source_text: str
    zh_cn: str
    surface: str
    notes: str


@dataclass(frozen=True)
class JavaStringToken:
    start: int
    end: int
    value: str


def read_table(path: Path) -> list[Entry]:
    lines = path.read_text("utf-8-sig").splitlines()
    if not lines:
        raise TextTableError("empty text table")
    header = lines[0].split("\t")
    required = [
        "key", "source_file", "expected_count", "source_text_json",
        "zh_CN_json", "surface", "notes",
    ]
    if header != required:
        raise TextTableError(f"unexpected table header: {header!r}")

    result: list[Entry] = []
    seen_keys: set[str] = set()
    seen_matches: set[tuple[str, str]] = set()
    for line_number, line in enumerate(lines[1:], 2):
        if not line or line.startswith("#"):
            continue
        fields = line.split("\t")
        if len(fields) != len(required):
            raise TextTableError(
                f"line {line_number}: expected {len(required)} tab-separated fields, "
                f"got {len(fields)}"
            )
        row = dict(zip(required, fields))
        key = row["key"]
        if not KEY_RE.fullmatch(key):
            raise TextTableError(f"line {line_number}: invalid Java constant key {key!r}")
        if key in seen_keys:
            raise TextTableError(f"line {line_number}: duplicate key {key}")
        seen_keys.add(key)

        relative = row["source_file"]
        path_parts = PurePosixPath(relative).parts
        if not relative.startswith("patch/src/main/java/") or any(
            part in {"", ".", ".."} for part in path_parts
        ):
            raise TextTableError(f"line {line_number}: unsafe source path {relative!r}")
        try:
            expected = int(row["expected_count"])
        except ValueError as exc:
            raise TextTableError(
                f"line {line_number}: expected_count is not an integer"
            ) from exc
        if expected <= 0:
            raise TextTableError(f"line {line_number}: expected_count must be positive")
        try:
            source_text = json.loads(row["source_text_json"])
            zh_cn = json.loads(row["zh_CN_json"])
        except json.JSONDecodeError as exc:
            raise TextTableError(f"line {line_number}: invalid JSON string field: {exc}") from exc
        if not isinstance(source_text, str) or not isinstance(zh_cn, str):
            raise TextTableError(f"line {line_number}: text fields must decode to strings")
        if not source_text:
            raise TextTableError(f"line {line_number}: source text is empty")
        match_key = (relative, source_text)
        if match_key in seen_matches:
            raise TextTableError(
                f"line {line_number}: duplicate file/source match; merge it into one row"
            )
        seen_matches.add(match_key)
        result.append(Entry(
            key=key,
            source_file=relative,
            expected_count=expected,
            source_text=source_text,
            zh_cn=zh_cn,
            surface=row["surface"],
            notes=row["notes"],
        ))
    if not result:
        raise TextTableError("text table contains no entries")
    return result


def decode_java_escape(text: str, index: int) -> tuple[str, int]:
    if index >= len(text):
        raise TextTableError("unterminated Java escape")
    ch = text[index]
    simple = {
        "b": "\b", "t": "\t", "n": "\n", "f": "\f", "r": "\r",
        '"': '"', "'": "'", "\\": "\\",
    }
    if ch in simple:
        return simple[ch], index + 1
    if ch == "u":
        while index < len(text) and text[index] == "u":
            index += 1
        digits = text[index:index + 4]
        if len(digits) != 4 or any(c not in "0123456789abcdefABCDEF" for c in digits):
            raise TextTableError("invalid Java Unicode escape")
        return chr(int(digits, 16)), index + 4
    if ch in "01234567":
        digits = ch
        index += 1
        limit = 3 if ch in "0123" else 2
        while len(digits) < limit and index < len(text) and text[index] in "01234567":
            digits += text[index]
            index += 1
        return chr(int(digits, 8)), index
    raise TextTableError(f"unsupported Java escape \\{ch}")


def decode_java_string(raw: str) -> str:
    if len(raw) < 2 or raw[0] != '"' or raw[-1] != '"':
        raise TextTableError("invalid Java string token")
    body = raw[1:-1]
    output: list[str] = []
    i = 0
    while i < len(body):
        if body[i] != "\\":
            output.append(body[i])
            i += 1
            continue
        decoded, i = decode_java_escape(body, i + 1)
        output.append(decoded)
    return "".join(output)


def scan_java_strings(text: str) -> list[JavaStringToken]:
    tokens: list[JavaStringToken] = []
    i = 0
    n = len(text)
    while i < n:
        ch = text[i]
        if ch == "/" and i + 1 < n and text[i + 1] == "/":
            i += 2
            while i < n and text[i] not in "\r\n":
                i += 1
            continue
        if ch == "/" and i + 1 < n and text[i + 1] == "*":
            end = text.find("*/", i + 2)
            if end < 0:
                raise TextTableError("unterminated Java block comment")
            i = end + 2
            continue
        if ch == "'":
            i += 1
            while i < n:
                if text[i] == "\\":
                    i += 2
                elif text[i] == "'":
                    i += 1
                    break
                else:
                    i += 1
            else:
                raise TextTableError("unterminated Java character literal")
            continue
        if ch != '"':
            i += 1
            continue
        start = i
        i += 1
        while i < n:
            if text[i] == "\\":
                i += 2
            elif text[i] == '"':
                i += 1
                raw = text[start:i]
                tokens.append(JavaStringToken(start, i, decode_java_string(raw)))
                break
            elif text[i] in "\r\n":
                raise TextTableError("newline inside Java string literal")
            else:
                i += 1
        else:
            raise TextTableError("unterminated Java string literal")
    return tokens


def java_quote(value: str) -> str:
    output = ['"']
    for ch in value:
        code = ord(ch)
        if ch == "\\": output.append("\\\\")
        elif ch == '"': output.append('\\"')
        elif ch == "\n": output.append("\\n")
        elif ch == "\r": output.append("\\r")
        elif ch == "\t": output.append("\\t")
        elif ch == "\b": output.append("\\b")
        elif ch == "\f": output.append("\\f")
        elif code < 0x20 or code == 0x7F: output.append(f"\\u{code:04X}")
        else: output.append(ch)
    output.append('"')
    return "".join(output)


def generated_java(entries: list[Entry]) -> str:
    lines = [
        "// Generated by tools/prepare-cn-downloader-ui-text.py. Do not edit.",
        f"package {PACKAGE};", "", f"public final class {CLASS_NAME} {{",
        f"    private {CLASS_NAME}() {{}}", "",
    ]
    for entry in entries:
        if entry.notes:
            lines.append(f"    // {entry.surface}: {entry.notes}")
        else:
            lines.append(f"    // {entry.surface}")
        lines.append(
            f"    public static final String {entry.key} = {java_quote(entry.zh_cn)};"
        )
    lines.extend(["}", ""])
    return "\n".join(lines)


def prepare(entries: list[Entry], repo_root: Path, output_root: Path) -> dict:
    source_root = repo_root / "patch/src/main/java"
    if not source_root.is_dir():
        raise TextTableError(f"Java source root does not exist: {source_root}")
    if output_root.resolve() == source_root.resolve():
        raise TextTableError("output root must not be the committed source root")
    if output_root.exists():
        shutil.rmtree(output_root)
    shutil.copytree(source_root, output_root)

    by_file: dict[str, list[Entry]] = {}
    for entry in entries:
        by_file.setdefault(entry.source_file, []).append(entry)

    replaced_total = 0
    report_files: dict[str, dict[str, int]] = {}
    for source_file, file_entries in sorted(by_file.items()):
        committed = repo_root / source_file
        if not committed.is_file():
            raise TextTableError(f"listed source file does not exist: {source_file}")
        relative_under_java = Path(source_file).relative_to("patch/src/main/java")
        generated = output_root / relative_under_java
        text = committed.read_text("utf-8")
        tokens = scan_java_strings(text)
        entry_by_source = {entry.source_text: entry for entry in file_entries}
        counts = {entry.key: 0 for entry in file_entries}
        replacements: list[tuple[int, int, str]] = []
        for token in tokens:
            entry = entry_by_source.get(token.value)
            if entry is None:
                continue
            counts[entry.key] += 1
            replacements.append((
                token.start, token.end, f"{CLASS_NAME}.{entry.key}"
            ))
        for entry in file_entries:
            actual = counts[entry.key]
            if actual != entry.expected_count:
                raise TextTableError(
                    f"{source_file}: {entry.key} expected {entry.expected_count} exact Java "
                    f"string literal(s), found {actual}; source/table drift detected"
                )
        for start, end, replacement in reversed(replacements):
            text = text[:start] + replacement + text[end:]
        generated.write_text(text, "utf-8")
        replaced_total += len(replacements)
        report_files[source_file] = counts

    class_path = output_root / GENERATED_RELATIVE
    class_path.parent.mkdir(parents=True, exist_ok=True)
    class_path.write_text(generated_java(entries), "utf-8")
    return {
        "entries": len(entries),
        "replacements": replaced_total,
        "files": report_files,
        "generatedClass": str(class_path),
    }


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--table", type=Path, required=True)
    parser.add_argument("--repo-root", type=Path, default=Path("."))
    parser.add_argument("--output-root", type=Path, required=True)
    parser.add_argument("--report", type=Path)
    args = parser.parse_args()
    try:
        repo_root = args.repo_root.resolve()
        entries = read_table(args.table)
        report = prepare(entries, repo_root, args.output_root.resolve())
        if args.report:
            args.report.parent.mkdir(parents=True, exist_ok=True)
            args.report.write_text(
                json.dumps(report, ensure_ascii=False, indent=2) + "\n", "utf-8"
            )
        print(json.dumps(report, ensure_ascii=False, indent=2))
        return 0
    except (OSError, TextTableError) as exc:
        print(f"ERROR: {exc}", file=sys.stderr)
        return 1


if __name__ == "__main__":
    raise SystemExit(main())
