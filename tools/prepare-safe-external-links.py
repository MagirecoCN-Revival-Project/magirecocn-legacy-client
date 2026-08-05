#!/usr/bin/env python3
"""Replace both raw ACTION_VIEW blocks with the validated HTTPS opener."""
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
        count = text.count(ANCHOR)
        if count != 2:
            raise PrepareError(f"expected two raw ACTION_VIEW blocks, found {count}")
        text = text.replace(ANCHOR, REPLACEMENT)
        if "new Intent(Intent.ACTION_VIEW, Uri.parse(url))" in text:
            raise PrepareError("raw ACTION_VIEW block survived")
        if text.count("CNSafeExternalLinks.open(act, url);") != 2:
            raise PrepareError("validated opener count is not two")
        target.write_text(text, "utf-8")
        report = {
            "target": str(target),
            "rawActionViewBlocksReplaced": count,
            "validatedHttpsOpeners": 2,
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
