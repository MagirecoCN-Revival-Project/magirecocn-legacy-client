#!/usr/bin/env python3
"""Insert runtime overlay hit/execute probes into generated WebViewImpl.java."""
from __future__ import annotations

import argparse
import json
from pathlib import Path
import sys

RELATIVE = Path("jp/f4samurai/web/WebViewImpl.java")
LOCAL_ANCHOR = '''            Log.i(TAG_FOUND, candidatePath + " mime=" + mime);
            return new WebResourceResponse(mime, textual(mime) ? "utf-8" : null, stream);'''
LOCAL_REPLACEMENT = '''            Log.i(TAG_FOUND, candidatePath + " mime=" + mime);
            RuntimeOverlayProbe.onLocalFile(candidatePath, mime);
            return new WebResourceResponse(mime, textual(mime) ? "utf-8" : null, stream);'''
FINISH_ANCHOR = '''        public void onPageFinished(WebView view, String url) {
            WebViewHelper._didFinishLoading(url);
        }'''
FINISH_REPLACEMENT = '''        public void onPageFinished(WebView view, String url) {
            RuntimeOverlayProbe.onPageFinished(view, url);
            WebViewHelper._didFinishLoading(url);
        }'''


class PrepareError(RuntimeError):
    pass


def replace_once(text: str, old: str, new: str, label: str) -> tuple[str, int]:
    count = text.count(old)
    if count != 1:
        raise PrepareError(f"{label}: expected one exact match, found {count}")
    return text.replace(old, new, 1), count


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--java-root", type=Path, required=True)
    parser.add_argument("--report", type=Path)
    args = parser.parse_args()
    target = args.java_root / RELATIVE
    try:
        text = target.read_text("utf-8")
        text, local_count = replace_once(
            text, LOCAL_ANCHOR, LOCAL_REPLACEMENT, "local overlay hit probe")
        text, finish_count = replace_once(
            text, FINISH_ANCHOR, FINISH_REPLACEMENT, "page-finished execute probe")
        if text.count("RuntimeOverlayProbe.onLocalFile(candidatePath, mime);") != 1:
            raise PrepareError("local probe count is not one")
        if text.count("RuntimeOverlayProbe.onPageFinished(view, url);") != 1:
            raise PrepareError("page-finished probe count is not one")
        target.write_text(text, "utf-8")
        report = {
            "target": str(target),
            "localFileHitProbes": local_count,
            "pageFinishedExecuteProbes": finish_count,
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
