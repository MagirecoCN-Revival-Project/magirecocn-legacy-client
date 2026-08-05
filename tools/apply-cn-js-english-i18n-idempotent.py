#!/usr/bin/env python3
"""Run the reviewed UI patch idempotently without weakening its strict QA.

The original patcher requires every path-specific source phrase to occur.  That
is correct for a clean English baseline, but newer runtime packages may already
contain the reviewed Chinese target.  This adapter removes a rule only after it
proves that the target already exists in the same semantic surface:

* JavaScript: complete string literal;
* HTML/EJS: exact visible text node/prefix;
* raw rules: exact reviewed fragment.

If neither source nor target exists, the rule remains active and the original
patcher fails closed as before.
"""
from __future__ import annotations

import argparse
from collections import Counter
import importlib.util
import json
from pathlib import Path
import re
import sys
import tempfile
import zipfile

BASE = Path(__file__).with_name("apply-cn-js-english-i18n.py")


def load_base():
    spec = importlib.util.spec_from_file_location("cn_js_i18n_strict", BASE)
    if spec is None or spec.loader is None:
        raise RuntimeError(f"cannot load {BASE}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


def js_literal_counts(text: str) -> Counter[str]:
    counts: Counter[str] = Counter()
    i, n = 0, len(text)
    while i < n:
        quote = text[i]
        if quote not in "'\"`":
            i += 1
            continue
        start = i + 1
        i = start
        while i < n:
            char = text[i]
            if char == "\\" and i + 1 < n:
                i += 2
                continue
            if char == quote:
                counts[text[start:i]] += 1
                i += 1
                break
            i += 1
        else:
            break
    return counts


def html_visible_count(text: str, value: str) -> int:
    pattern = re.compile(
        r">(?P<lead>\s*)" + re.escape(value) + r"(?P<trail>\s*)(?=<)"
    )
    return sum(1 for _ in pattern.finditer(text))


def classify_rules(module, root: Path):
    already: list[dict[str, object]] = []
    unresolved: list[dict[str, str]] = []

    js_filtered: dict[str, dict[str, str]] = {}
    for relative, mapping in module.JS_BY_PATH.items():
        path = root / relative
        text = path.read_text("utf-8") if path.is_file() else ""
        literals = js_literal_counts(text)
        keep: dict[str, str] = {}
        for source, target in mapping.items():
            source_count = literals[source]
            target_count = literals[target]
            if source_count > 0:
                keep[source] = target
            elif target_count > 0:
                already.append({
                    "surface": "javascript-literal", "path": relative,
                    "source": source, "target": target, "count": target_count,
                })
            else:
                keep[source] = target
                unresolved.append({
                    "surface": "javascript-literal", "path": relative,
                    "source": source, "target": target,
                })
        js_filtered[relative] = keep

    html_filtered: dict[str, dict[str, str]] = {}
    for relative, mapping in module.HTML_BY_PATH.items():
        path = root / relative
        text = path.read_text("utf-8") if path.is_file() else ""
        keep: dict[str, str] = {}
        for source, target in mapping.items():
            source_count = html_visible_count(text, source)
            target_count = html_visible_count(text, target)
            if source_count > 0:
                keep[source] = target
            elif target_count > 0:
                already.append({
                    "surface": "html-visible", "path": relative,
                    "source": source, "target": target, "count": target_count,
                })
            else:
                keep[source] = target
                unresolved.append({
                    "surface": "html-visible", "path": relative,
                    "source": source, "target": target,
                })
        html_filtered[relative] = keep

    raw_filtered: dict[str, dict[str, str]] = {}
    for relative, mapping in module.RAW_BY_PATH.items():
        path = root / relative
        text = path.read_text("utf-8") if path.is_file() else ""
        keep: dict[str, str] = {}
        for source, target in mapping.items():
            source_count = text.count(source)
            target_count = text.count(target)
            if source_count > 0:
                keep[source] = target
            elif target_count > 0:
                already.append({
                    "surface": "reviewed-raw", "path": relative,
                    "source": source, "target": target, "count": target_count,
                })
            else:
                keep[source] = target
                unresolved.append({
                    "surface": "reviewed-raw", "path": relative,
                    "source": source, "target": target,
                })
        raw_filtered[relative] = keep

    return js_filtered, html_filtered, raw_filtered, already, unresolved


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("input_zip", type=Path)
    parser.add_argument("output_zip", type=Path)
    parser.add_argument("--report", type=Path)
    args = parser.parse_args()

    module = load_base()
    with tempfile.TemporaryDirectory(prefix="cn-js-idempotent-scan-") as temp:
        root = Path(temp) / "root"
        root.mkdir()
        with zipfile.ZipFile(args.input_zip) as archive:
            archive.extractall(root)
        (js_rules, html_rules, raw_rules,
         already, unresolved) = classify_rules(module, root)

    module.JS_BY_PATH = js_rules
    module.HTML_BY_PATH = html_rules
    module.RAW_BY_PATH = raw_rules

    report_path = args.report or args.output_zip.with_suffix(
        args.output_zip.suffix + ".qa.json"
    )
    previous_argv = sys.argv
    try:
        sys.argv = [
            str(BASE), str(args.input_zip), str(args.output_zip),
            "--report", str(report_path),
        ]
        result = int(module.main())
    finally:
        sys.argv = previous_argv

    if result != 0:
        return result

    report = json.loads(report_path.read_text("utf-8"))
    report["idempotentSchema"] = 1
    report["already_applied"] = already
    report["already_applied_count"] = len(already)
    report["rules_missing_source_and_target_before_strict_run"] = unresolved
    report["strictBasePatcherStillUsed"] = True
    report_path.write_text(
        json.dumps(report, ensure_ascii=False, indent=2) + "\n", "utf-8"
    )
    print(json.dumps({
        "output": str(args.output_zip),
        "already_applied": len(already),
        "strict_unresolved_rules": len(unresolved),
        "report": str(report_path),
    }, ensure_ascii=False, indent=2))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
