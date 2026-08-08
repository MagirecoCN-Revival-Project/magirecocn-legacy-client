#!/usr/bin/env python3
"""``recover-official-cn-i18n.py`` 的无第三方依赖回归测试。"""

from __future__ import annotations

import importlib.util
import json
import mmap
import sys
import tempfile
import unittest
from pathlib import Path


SCRIPT = Path(__file__).with_name("recover-official-cn-i18n.py")
SPEC = importlib.util.spec_from_file_location("recover_official_cn_i18n", SCRIPT)
assert SPEC and SPEC.loader
MODULE = importlib.util.module_from_spec(SPEC)
sys.modules[SPEC.name] = MODULE
SPEC.loader.exec_module(MODULE)


class RecoveryTest(unittest.TestCase):
    def test_engine_field_round_trip(self) -> None:
        raw = "第一行\n第二\t列\\尾"
        self.assertEqual(MODULE.unescape_engine_field(MODULE.escape_engine_field(raw)), raw)
        self.assertEqual(MODULE.unescape_engine_field(r"保留\q"), r"保留\q")

    def test_voice_key_candidates_are_deterministic(self) -> None:
        self.assertEqual(
            MODULE.voice_key_candidates("1041", "44"),
            ["vo_char_1041_00_44_hca", "vo_char_1041_00_044_hca"],
        )
        self.assertEqual(MODULE.message_scope("44"), "battle_result")
        self.assertEqual(MODULE.message_scope("6"), "character_profile_or_lobby")
        self.assertEqual(MODULE.message_scope("101"), "unknown")

    def test_complete_c_string_rejects_suffix_match(self) -> None:
        with tempfile.TemporaryDirectory() as directory:
            path = Path(directory) / "fixture.so"
            path.write_bytes("\0权威中文\0前缀权威中文\0".encode("utf-8"))
            with path.open("rb") as handle:
                with mmap.mmap(handle.fileno(), 0, access=mmap.ACCESS_READ) as blob:
                    self.assertEqual(
                        MODULE.count_complete_c_string(blob, "权威中文"), 1
                    )
        self.assertEqual(
            MODULE.voice_key_candidates("1001", "6"),
            [
                "vo_char_1001_00_6_hca",
                "vo_char_1001_00_06_hca",
                "vo_char_1001_00_006_hca",
            ],
        )

    def test_key_alignment_and_conflict_quarantine(self) -> None:
        jp = [
            {
                "charaMessageList": [
                    {
                        "charaNo": 1041,
                        "messageId": 44,
                        "message": MODULE.KNOWN_REGRESSION_JA,
                    },
                    {"charaNo": 1001, "messageId": 6, "message": "同一句"},
                    {"charaNo": 1002, "messageId": 6, "message": "同一句"},
                ]
            }
        ]
        cn = {
            MODULE.KNOWN_REGRESSION_KEY: MODULE.KNOWN_REGRESSION_ZH,
            "vo_char_1001_00_06_hca": "译文甲",
            "vo_char_1002_00_06_hca": "译文乙",
        }
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            jp_path = root / "jp.json"
            cn_path = root / "cn.json"
            jp_path.write_text(json.dumps(jp, ensure_ascii=False), encoding="utf-8")
            cn_path.write_text(json.dumps(cn, ensure_ascii=False), encoding="utf-8")
            evidence, additions, summary = MODULE.build_voice_evidence(
                jp_path, cn_path, None
            )

        target = [row for row in additions if row["ja"] == MODULE.KNOWN_REGRESSION_JA]
        self.assertEqual(len(target), 1)
        self.assertEqual(target[0]["zhCN"], MODULE.KNOWN_REGRESSION_ZH)
        self.assertEqual(target[0]["message_scope"], "battle_result")
        self.assertFalse(target[0]["requires_runtime_capture"])
        self.assertFalse(any(row["ja"] == "同一句" for row in additions))
        self.assertEqual(summary["same_ja_conflicts"], 1)
        self.assertEqual(
            sum(row["confidence"] == "fuzzy_review" for row in evidence), 2
        )


if __name__ == "__main__":
    unittest.main()
