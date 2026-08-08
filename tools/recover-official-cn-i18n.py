#!/usr/bin/env python3
"""从旧国服留档回收可追溯的官方中文证据。

这个工具只生成研究证据与待审阅清单，不生成或覆盖运行时
``madomagi/engine_i18n.tsv``。运行时表的唯一权威源仍在补丁仓库。

目前实现两条保守路径：

1. 检查现行引擎译文是否以完整 C 字符串存在于旧国服双 ABI 原生库；
2. 用 ``(charaNo, messageId)`` 将日文 ``charaMessageList`` 与旧国服
   ``vo_char_<charaNo>_00_<messageId>_hca`` 字典精确对齐。

没有稳定键、同一键出现冲突、同一日文对应多个中文等情况一律降为
``fuzzy_review``，不会进入可直接审阅的增量清单。
"""

from __future__ import annotations

import argparse
import csv
import hashlib
import json
import mmap
import sys
from collections import Counter, defaultdict
from dataclasses import dataclass
from pathlib import Path
from typing import Any, Iterable, Iterator, Mapping, Sequence


KNOWN_REGRESSION_JA = "カーテンコールで終いやな"
KNOWN_REGRESSION_ZH = "在欢声中谢幕吧"
KNOWN_REGRESSION_KEY = "vo_char_1041_00_44_hca"


def sha256_file(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as handle:
        for block in iter(lambda: handle.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def source_record(path: Path) -> dict[str, Any]:
    stat = path.stat()
    return {
        "path": str(path.resolve()),
        "size": stat.st_size,
        "sha256": sha256_file(path),
    }


def unescape_engine_field(value: str) -> str:
    """按 native 加载器约定反转 ``\\n``、``\\t`` 与 ``\\\\``。"""

    out: list[str] = []
    index = 0
    while index < len(value):
        char = value[index]
        if char != "\\" or index + 1 >= len(value):
            out.append(char)
            index += 1
            continue
        nxt = value[index + 1]
        if nxt == "n":
            out.append("\n")
        elif nxt == "t":
            out.append("\t")
        elif nxt == "\\":
            out.append("\\")
        else:
            # 与 native 行为保持保守：未知转义保留反斜杠。
            out.extend(("\\", nxt))
        index += 2
    return "".join(out)


def escape_engine_field(value: str) -> str:
    return value.replace("\\", "\\\\").replace("\t", "\\t").replace("\n", "\\n")


@dataclass(frozen=True)
class EngineRow:
    line: int
    rule: str
    ja_source: str
    zh_source: str
    ja: str
    zh: str


def read_engine_table(path: Path) -> list[EngineRow]:
    rows: list[EngineRow] = []
    for line_number, line in enumerate(
        path.read_text(encoding="utf-8-sig").splitlines(), start=1
    ):
        if not line or line.startswith("#"):
            continue
        fields = line.split("\t")
        if len(fields) != 2:
            raise ValueError(f"{path}:{line_number}: 需要且只能有一个 TAB")
        ja_source, zh_source = fields
        rule = "prefix" if ja_source.startswith("^") else "exact"
        ja_body = ja_source[1:] if rule == "prefix" else ja_source
        rows.append(
            EngineRow(
                line=line_number,
                rule=rule,
                ja_source=ja_source,
                zh_source=zh_source,
                ja=unescape_engine_field(ja_body),
                zh=unescape_engine_field(zh_source),
            )
        )
    return rows


def count_complete_c_string(blob: mmap.mmap, value: str) -> int:
    """统计以 NUL 结尾的完整 UTF-8 C 字符串。

    同时要求开头位于文件起点或前一字节为 NUL，并要求结尾 NUL，避免把长字符串
    的中文后缀误认成一条独立的官方字符串。
    """

    if not value:
        return 0
    pattern = value.encode("utf-8") + b"\0"
    count = 0
    start = 0
    while True:
        found = blob.find(pattern, start)
        if found < 0:
            break
        if found == 0 or blob[found - 1] == 0:
            count += 1
        start = found + 1
    return count


def audit_engine_literals(
    table: Path, cn_arm64: Path, cn_armv7: Path
) -> tuple[list[dict[str, Any]], Counter[str]]:
    rows = read_engine_table(table)
    results: list[dict[str, Any]] = []
    status_counts: Counter[str] = Counter()
    with cn_arm64.open("rb") as handle64, cn_armv7.open("rb") as handle32:
        with mmap.mmap(handle64.fileno(), 0, access=mmap.ACCESS_READ) as blob64:
            with mmap.mmap(handle32.fileno(), 0, access=mmap.ACCESS_READ) as blob32:
                for row in rows:
                    count64 = count_complete_c_string(blob64, row.zh)
                    count32 = count_complete_c_string(blob32, row.zh)
                    if not row.zh:
                        confidence = "fuzzy_review"
                        reason = "intentional_empty_translation_requires_context"
                    elif count64 > 0 and count32 > 0:
                        confidence = "exact_authoritative"
                        reason = (
                            "current_translation_is_complete_c_string_in_both_"
                            "official_cn_abis"
                        )
                    else:
                        confidence = "fuzzy_review"
                        reason = (
                            "current_translation_not_found_as_complete_c_string_"
                            "in_both_abis"
                        )
                    status_counts[confidence] += 1
                    results.append(
                        {
                            "line": row.line,
                            "rule": row.rule,
                            "ja": row.ja,
                            "current_zhCN": row.zh,
                            "confidence": confidence,
                            "reason": reason,
                            "old_cn_arm64_occurrences": count64,
                            "old_cn_armv7_occurrences": count32,
                        }
                    )
    return results, status_counts


def walk_message_records(
    value: Any, path: tuple[str, ...] = ()
) -> Iterator[tuple[str, str, str, tuple[str, ...]]]:
    if isinstance(value, dict):
        if {"charaNo", "messageId", "message"}.issubset(value):
            message = value.get("message")
            if isinstance(message, str) and message:
                yield str(value["charaNo"]), str(value["messageId"]), message, path
        for key, child in value.items():
            yield from walk_message_records(child, path + (str(key),))
    elif isinstance(value, list):
        for index, child in enumerate(value):
            yield from walk_message_records(child, path + (str(index),))


def voice_key_candidates(chara_no: str, message_id: str) -> list[str]:
    candidates: list[str] = []
    for normalized_id in (message_id, message_id.zfill(2), message_id.zfill(3)):
        key = f"vo_char_{chara_no}_00_{normalized_id}_hca"
        if key not in candidates:
            candidates.append(key)
    return candidates


def message_scope(message_id: str) -> str:
    if message_id.isdigit() and 43 <= int(message_id) <= 46:
        return "battle_result"
    if message_id.isdigit() and 6 <= int(message_id) <= 12:
        return "character_profile_or_lobby"
    return "unknown"


def load_json(path: Path) -> Any:
    with path.open("r", encoding="utf-8-sig") as handle:
        return json.load(handle)


def natural_id(value: str) -> tuple[int, int | str]:
    return (0, int(value)) if value.isdigit() else (1, value)


def build_voice_evidence(
    jp_bundle: Path,
    cn_voice_json: Path,
    existing_engine_table: Path | None,
) -> tuple[list[dict[str, Any]], list[dict[str, Any]], dict[str, Any]]:
    jp_data = load_json(jp_bundle)
    cn_data = load_json(cn_voice_json)
    if not isinstance(cn_data, dict):
        raise ValueError(f"{cn_voice_json}: 顶层必须是 key -> 中文 的对象")

    occurrences: defaultdict[tuple[str, str, str], list[str]] = defaultdict(list)
    for chara_no, message_id, message, record_path in walk_message_records(jp_data):
        occurrences[(chara_no, message_id, message)].append("/".join(record_path))

    evidence: list[dict[str, Any]] = []
    for (chara_no, message_id, ja), paths in sorted(
        occurrences.items(),
        key=lambda item: (
            natural_id(item[0][0]),
            natural_id(item[0][1]),
            item[0][2],
        ),
    ):
        matching_keys = [
            key for key in voice_key_candidates(chara_no, message_id) if key in cn_data
        ]
        values = {str(cn_data[key]) for key in matching_keys}
        if len(values) == 1:
            confidence = "key_aligned"
            zh = next(iter(values))
            reason = "unique_charaNo_messageId_key_and_unique_cn_value"
        elif matching_keys:
            confidence = "fuzzy_review"
            zh = ""
            reason = "same_structural_key_has_conflicting_cn_values"
        else:
            confidence = "fuzzy_review"
            zh = ""
            reason = "official_cn_key_not_present"
        evidence.append(
            {
                "charaNo": chara_no,
                "messageId": message_id,
                "ja": ja,
                "zhCN": zh,
                "cn_keys": matching_keys,
                "confidence": confidence,
                "reason": reason,
                "evidence_occurrences": len(paths),
                "jp_record_paths": paths,
            }
        )

    # 运行时表只能按日文原文查找，同一句日文若有多个权威中文就不能自动放行。
    by_ja: defaultdict[str, list[dict[str, Any]]] = defaultdict(list)
    for row in evidence:
        if row["confidence"] == "key_aligned":
            by_ja[row["ja"]].append(row)

    conflicting_ja: set[str] = set()
    for ja, rows in by_ja.items():
        if len({row["zhCN"] for row in rows}) != 1:
            conflicting_ja.add(ja)
            for row in rows:
                row["confidence"] = "fuzzy_review"
                row["reason"] = "same_ja_has_multiple_official_cn_values"

    existing: dict[str, str] = {}
    if existing_engine_table is not None:
        existing = {row.ja: row.zh for row in read_engine_table(existing_engine_table)}

    additions: list[dict[str, Any]] = []
    for ja, rows in sorted(by_ja.items()):
        if ja in conflicting_ja:
            continue
        zh = rows[0]["zhCN"]
        if not ja or not zh:
            continue
        state = "new"
        if ja in existing:
            state = "already_same" if existing[ja] == zh else "existing_conflict"
        scopes = {message_scope(row["messageId"]) for row in rows}
        scope = next(iter(scopes)) if len(scopes) == 1 else "mixed"
        contains_at = "@" in ja
        additions.append(
            {
                "ja": ja,
                "zhCN": zh,
                "ja_tsv": escape_engine_field(ja),
                "zhCN_tsv": escape_engine_field(zh),
                "confidence": "key_aligned",
                "state": state,
                "charaNo": ";".join(sorted({row["charaNo"] for row in rows})),
                "messageId": ";".join(sorted({row["messageId"] for row in rows})),
                "message_scope": scope,
                "cn_keys": ";".join(
                    sorted({key for row in rows for key in row["cn_keys"]})
                ),
                "contains_at_marker": contains_at,
                # 43-46 的战斗结束文本已有 native hook 实证；其他界面仍要先判路由。
                "requires_runtime_capture": contains_at or scope != "battle_result",
            }
        )

    regression = [row for row in additions if row["ja"] == KNOWN_REGRESSION_JA]
    if regression:
        if len(regression) != 1 or regression[0]["zhCN"] != KNOWN_REGRESSION_ZH:
            raise AssertionError("已知战斗结束台词的国服键对齐结果发生漂移")
        if KNOWN_REGRESSION_KEY not in regression[0]["cn_keys"]:
            raise AssertionError("已知战斗结束台词没有指向预期国服 voice key")

    summary = {
        "raw_jp_occurrences": sum(len(paths) for paths in occurrences.values()),
        "unique_jp_records": len(evidence),
        "confidence_counts": dict(Counter(row["confidence"] for row in evidence)),
        "unique_key_aligned_ja": len(additions),
        "new_engine_candidates": sum(row["state"] == "new" for row in additions),
        "already_same": sum(row["state"] == "already_same" for row in additions),
        "existing_conflicts": sum(row["state"] == "existing_conflict" for row in additions),
        "at_marker_candidates_requiring_capture": sum(
            row["contains_at_marker"] for row in additions
        ),
        "battle_result_candidates": sum(
            row["message_scope"] == "battle_result" for row in additions
        ),
        "battle_result_without_at_marker": sum(
            row["message_scope"] == "battle_result"
            and not row["contains_at_marker"]
            for row in additions
        ),
        "non_battle_candidates_requiring_route_capture": sum(
            row["message_scope"] != "battle_result" for row in additions
        ),
        "all_candidates_requiring_runtime_capture": sum(
            row["requires_runtime_capture"] for row in additions
        ),
        "same_ja_conflicts": len(conflicting_ja),
    }
    return evidence, additions, summary


def write_tsv(path: Path, fieldnames: Sequence[str], rows: Iterable[Mapping[str, Any]]) -> None:
    with path.open("w", encoding="utf-8", newline="") as handle:
        writer = csv.DictWriter(
            handle,
            fieldnames=fieldnames,
            delimiter="\t",
            lineterminator="\n",
            extrasaction="ignore",
        )
        writer.writeheader()
        for row in rows:
            escaped = dict(row)
            for key, value in escaped.items():
                if isinstance(value, list):
                    escaped[key] = json.dumps(value, ensure_ascii=False, separators=(",", ":"))
            writer.writerow(escaped)


def build_parser() -> argparse.ArgumentParser:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--decoded-root", type=Path, required=True)
    parser.add_argument("--engine-table", type=Path)
    parser.add_argument("--jp-bundle", type=Path)
    parser.add_argument("--cn-voice-json", type=Path)
    parser.add_argument("--output-dir", type=Path, required=True)
    return parser


def main(argv: Sequence[str] | None = None) -> int:
    args = build_parser().parse_args(argv)
    decoded_root = args.decoded_root.resolve()
    cn_arm64 = decoded_root / "lib" / "arm64-v8a" / "libmadomagi_native.so"
    cn_armv7 = decoded_root / "lib" / "armeabi-v7a" / "libmadomagi_native.so"
    required = [decoded_root, cn_arm64, cn_armv7]
    if args.engine_table:
        required.append(args.engine_table)
    if bool(args.jp_bundle) != bool(args.cn_voice_json):
        raise ValueError("--jp-bundle 与 --cn-voice-json 必须同时给出")
    if args.jp_bundle:
        required.extend((args.jp_bundle, args.cn_voice_json))
    missing = [path for path in required if not path.exists()]
    if missing:
        raise FileNotFoundError("缺少输入：" + ", ".join(map(str, missing)))

    output = args.output_dir.resolve()
    output.mkdir(parents=True, exist_ok=True)
    manifest: dict[str, Any] = {
        "schema": 1,
        "policy": {
            "runtime_table_written": False,
            "allowed_direct_confidence": ["exact_authoritative", "key_aligned"],
            "review_only_confidence": ["binary_context_aligned", "fuzzy_review"],
            "at_marker_requires_runtime_capture": True,
            "non_battle_message_requires_route_capture": True,
        },
        "sources": {
            "old_cn_arm64": source_record(cn_arm64),
            "old_cn_armv7": source_record(cn_armv7),
        },
    }

    if args.engine_table:
        engine_rows, engine_counts = audit_engine_literals(
            args.engine_table, cn_arm64, cn_armv7
        )
        manifest["sources"]["engine_table"] = source_record(args.engine_table)
        manifest["engine_literal_audit"] = {
            "rows": len(engine_rows),
            "confidence_counts": dict(engine_counts),
        }
        write_tsv(
            output / "engine-current-official-literal-audit.tsv",
            [
                "line",
                "rule",
                "ja",
                "current_zhCN",
                "confidence",
                "reason",
                "old_cn_arm64_occurrences",
                "old_cn_armv7_occurrences",
            ],
            engine_rows,
        )
        (output / "engine-current-official-literal-audit.json").write_text(
            json.dumps(engine_rows, ensure_ascii=False, indent=2) + "\n", encoding="utf-8"
        )

    if args.jp_bundle:
        evidence, additions, voice_summary = build_voice_evidence(
            args.jp_bundle, args.cn_voice_json, args.engine_table
        )
        manifest["sources"]["jp_bundle"] = source_record(args.jp_bundle)
        manifest["sources"]["official_cn_voice_json"] = source_record(
            args.cn_voice_json
        )
        manifest["voice_key_alignment"] = voice_summary
        write_tsv(
            output / "voice-official-cn-evidence.tsv",
            [
                "charaNo",
                "messageId",
                "ja",
                "zhCN",
                "cn_keys",
                "confidence",
                "reason",
                "evidence_occurrences",
                "jp_record_paths",
            ],
            evidence,
        )
        write_tsv(
            output / "voice-engine-additions-review.tsv",
            [
                "ja_tsv",
                "zhCN_tsv",
                "confidence",
                "state",
                "charaNo",
                "messageId",
                "message_scope",
                "cn_keys",
                "contains_at_marker",
                "requires_runtime_capture",
            ],
            additions,
        )
        (output / "voice-official-cn-evidence.json").write_text(
            json.dumps(evidence, ensure_ascii=False, indent=2) + "\n", encoding="utf-8"
        )

    (output / "manifest.json").write_text(
        json.dumps(manifest, ensure_ascii=False, indent=2) + "\n", encoding="utf-8"
    )
    print(json.dumps(manifest, ensure_ascii=False, indent=2))
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except (AssertionError, FileNotFoundError, ValueError) as error:
        print(f"ERROR: {error}", file=sys.stderr)
        raise SystemExit(2)
