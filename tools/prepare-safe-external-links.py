#!/usr/bin/env python3
"""Replace every remaining raw ACTION_VIEW block with the HTTPS allowlist opener."""
from __future__ import annotations

import argparse
import json
from pathlib import Path
import sys

RELATIVE = Path("io/kamihama/magianative/CNCNDownloadUI.java")
ANCHOR = '''                Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                act.startActivity(it);'''
REPLACEMENT = '''                CNSafeExternalLinks.open(act, url);'''
RAW_MARKER = "new Intent(Intent.ACTION_VIEW, Uri.parse(url))"


class PrepareError(RuntimeError):
    pass


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--java-root", type=Path, required=True)
    parser.add_argument("--report", type=Path)
    args = parser.parse_args()
    target = args.java_root / RELATIVE
    try:
        text = target.read_text("utf-8")
        before_validated = text.count("CNSafeExternalLinks.open(act, url);")
        count = text.count(ANCHOR)
        # Older audited source had two blocks; latest main has already removed/refactored
        # one and leaves only the actual browser-opening entry that still needs hardening.
        if count not in (1, 2):
            raise PrepareError(
                f"expected one or two remaining raw ACTION_VIEW blocks, found {count}")
        text = text.replace(ANCHOR, REPLACEMENT)
        if RAW_MARKER in text:
            raise PrepareError("raw ACTION_VIEW block survived")
        validated = text.count("CNSafeExternalLinks.open(act, url);")
        if validated != before_validated + count:
            raise PrepareError(
                f"validated opener count mismatch: before={before_validated}, "
                f"replaced={count}, after={validated}")
        target.write_text(text, "utf-8")
        report = {
            "schema": 2,
            "target": str(target),
            "rawActionViewBlocksReplaced": count,
            "preexistingValidatedOpeners": before_validated,
            "validatedHttpsOpeners": validated,
            "rawActionViewBlocksRemaining": 0,
            "latestMainPartialFixPreserved": count == 1,
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
