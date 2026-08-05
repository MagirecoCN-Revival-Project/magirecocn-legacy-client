#!/usr/bin/env python3
"""Wire and harden recoverable hot-update transactions in generated Java."""
from __future__ import annotations

import argparse
import json
from pathlib import Path
import sys

CHECK_RELATIVE = Path("io/kamihama/magianative/CNHotUpdateCheck.java")
TX_RELATIVE = Path("io/kamihama/magianative/CNHotUpdateTransaction.java")
RECOVERY_ANCHOR = '''        } else {
            showOverlay(activity);
        }

        java.util.concurrent.ScheduledExecutorService watchdog = startWatchdog(activity);
'''
RECOVERY_REPLACEMENT = '''        } else {
            showOverlay(activity);
        }

        // 上次进程若死在事务提交中，先完整回滚再查询新版本。此时浮层已经
        // 创建，恢复失败的阻断原因能够直接显示给玩家。
        File filesRoot = new File(FILES_DIR);
        try {
            CNHotUpdateTransaction.recover(filesRoot);
        } catch (Throwable error) {
            CNLog.e(TAG, "未完成热更新事务恢复失败，已阻止继续更新", error);
            CNCNDownloadUI.updateSimple("恢复热更新",
                    "上次更新未完整提交且自动回滚失败，请保留日志并重启后重试", 0);
            return;
        }

        java.util.concurrent.ScheduledExecutorService watchdog = startWatchdog(activity);
'''
APPLY_ANCHOR = '            CNDownloaderFix.extractChecked(tmp, new File(FILES_DIR));'
APPLY_REPLACEMENT = '            CNHotUpdateTransaction.apply(tmp, new File(FILES_DIR));'
ROLLBACK_STATE_WRITE = '        writeState(txRoot, STATE_ROLLING_BACK);\n'
CONTAINMENT_ANCHOR = '''    private static void requireContained(File root, File child) throws IOException {
        String rootCanonical = root.getCanonicalPath();
        String childCanonical = child.getCanonicalPath();
        if (!childCanonical.startsWith(rootCanonical + File.separator)) {
            throw new IOException("事务目录越界: " + child);
        }
    }
'''
CONTAINMENT_REPLACEMENT = '''    private static void requireContained(File root, File child) throws IOException {
        String rootCanonical = root.getCanonicalPath();
        String childCanonical = child.getCanonicalPath();
        if (!childCanonical.equals(rootCanonical)
                && !childCanonical.startsWith(rootCanonical + File.separator)) {
            throw new IOException("事务目录越界: " + child);
        }
    }
'''


class PrepareError(RuntimeError):
    pass


def replace_exact(text: str, old: str, new: str,
                  expected: int, label: str) -> tuple[str, int]:
    count = text.count(old)
    if count != expected:
        raise PrepareError(
            f"{label}: expected {expected} exact match(es), found {count}")
    return text.replace(old, new), count


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--java-root", type=Path, required=True)
    parser.add_argument("--report", type=Path)
    args = parser.parse_args()
    check_target = args.java_root / CHECK_RELATIVE
    tx_target = args.java_root / TX_RELATIVE
    try:
        check_text = check_target.read_text("utf-8")
        check_text, recovery_count = replace_exact(
            check_text, RECOVERY_ANCHOR, RECOVERY_REPLACEMENT, 1,
            "visible recovery insertion")
        check_text, apply_count = replace_exact(
            check_text, APPLY_ANCHOR, APPLY_REPLACEMENT, 1,
            "transactional apply")
        if "CNHotUpdateTransaction.recover(filesRoot);" not in check_text:
            raise PrepareError("recovery call did not survive")
        if "CNHotUpdateTransaction.apply(tmp, new File(FILES_DIR));" not in check_text:
            raise PrepareError("transactional apply call did not survive")
        if APPLY_ANCHOR in check_text:
            raise PrepareError("legacy live-tree extraction call survived")
        check_target.write_text(check_text, "utf-8")

        tx_text = tx_target.read_text("utf-8")
        # COMMITTING 本身已经表示“下次必须回滚”。回滚前再次替换状态会制造
        # 删除旧状态与提交新状态之间的危险空窗；保持 COMMITTING 可让重复恢复幂等。
        tx_text, rollback_state_count = replace_exact(
            tx_text, ROLLBACK_STATE_WRITE,
            "        // 保持 COMMITTING；恢复过程可重复执行，直至完整回滚。\n",
            2, "rollback state hardening")
        tx_text, containment_count = replace_exact(
            tx_text, CONTAINMENT_ANCHOR, CONTAINMENT_REPLACEMENT,
            1, "transaction-root containment")
        if "writeState(txRoot, STATE_ROLLING_BACK);" in tx_text:
            raise PrepareError("ROLLING_BACK state rewrite survived")
        if "childCanonical.equals(rootCanonical)" not in tx_text:
            raise PrepareError("transaction root equality was not allowed")
        tx_target.write_text(tx_text, "utf-8")

        report = {
            "checkTarget": str(check_target),
            "transactionTarget": str(tx_target),
            "visibleRecoveryInsertions": recovery_count,
            "transactionApplyReplacements": apply_count,
            "rollbackStateWritesRemoved": rollback_state_count,
            "rootContainmentFixes": containment_count,
            "legacyLiveTreeExtractionRemaining": False,
            "rollbackStateGapRemaining": False,
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
