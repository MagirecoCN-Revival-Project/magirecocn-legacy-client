#!/usr/bin/env python3
"""Wire recoverable hot-update transactions into a generated Java source tree."""
from __future__ import annotations

import argparse
import json
from pathlib import Path
import sys

RELATIVE = Path("io/kamihama/magianative/CNHotUpdateCheck.java")
START_ANCHOR = '        CNLog.i(TAG, "热更检查开始");\n'
START_REPLACEMENT = '''        CNLog.i(TAG, "热更检查开始");

        // 上次进程若死在事务提交中，先完整回滚再查询新版本。恢复失败时必须
        // 停止本轮更新，不能继续覆盖一个状态未知的活动前端树。
        File filesRoot = new File(FILES_DIR);
        try {
            CNHotUpdateTransaction.recover(filesRoot);
        } catch (Throwable error) {
            CNLog.e(TAG, "未完成热更新事务恢复失败，已阻止继续更新", error);
            CNCNDownloadUI.updateSimple("恢复热更新",
                    "上次更新未完整提交且自动回滚失败，请保留日志并重启后重试", 0);
            return;
        }
'''
APPLY_ANCHOR = '            CNDownloaderFix.extractChecked(tmp, new File(FILES_DIR));'
APPLY_REPLACEMENT = '            CNHotUpdateTransaction.apply(tmp, new File(FILES_DIR));'


class PrepareError(RuntimeError):
    pass


def replace_once(text: str, old: str, new: str, label: str) -> tuple[str, int]:
    count = text.count(old)
    if count != 1:
        raise PrepareError(f"{label}: expected exactly one match, found {count}")
    return text.replace(old, new, 1), count


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--java-root", type=Path, required=True)
    parser.add_argument("--report", type=Path)
    args = parser.parse_args()
    target = args.java_root / RELATIVE
    try:
        text = target.read_text("utf-8")
        text, start_count = replace_once(
            text, START_ANCHOR, START_REPLACEMENT, "recovery insertion")
        text, apply_count = replace_once(
            text, APPLY_ANCHOR, APPLY_REPLACEMENT, "transactional apply")
        if "CNHotUpdateTransaction.recover(filesRoot);" not in text:
            raise PrepareError("recovery call did not survive")
        if "CNHotUpdateTransaction.apply(tmp, new File(FILES_DIR));" not in text:
            raise PrepareError("transactional apply call did not survive")
        if APPLY_ANCHOR in text:
            raise PrepareError("legacy live-tree extraction call survived")
        target.write_text(text, "utf-8")
        report = {
            "target": str(target),
            "recoveryInsertions": start_count,
            "transactionApplyReplacements": apply_count,
            "legacyLiveTreeExtractionRemaining": False,
        }
        if args.report:
            args.report.parent.mkdir(parents=True, exist_ok=True)
            args.report.write_text(
                json.dumps(report, ensure_ascii=False, indent=2) + "\n", "utf-8")
        print(json.dumps(report, ensure_ascii=False, indent=2))
        return 0
    except (OSError, PrepareError) as error:
        print(f"ERROR: {error}", file=sys.stderr)
        return 1


if __name__ == "__main__":
    raise SystemExit(main())
