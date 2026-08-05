#!/usr/bin/env python3
"""Materialise only downloader UI rows whose zh_CN differs from source text."""
from __future__ import annotations

import argparse
import importlib.util
import json
from pathlib import Path
import sys


def load_impl(path: Path):
    spec = importlib.util.spec_from_file_location("cn_downloader_text_impl", path)
    if spec is None or spec.loader is None:
        raise RuntimeError("cannot load prepare-cn-downloader-ui-text.py")
    module = importlib.util.module_from_spec(spec)
    sys.modules[spec.name] = module
    spec.loader.exec_module(module)
    return module


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--table", type=Path, required=True)
    parser.add_argument("--repo-root", type=Path, default=Path("."))
    parser.add_argument("--output-root", type=Path, required=True)
    parser.add_argument("--report", type=Path)
    args = parser.parse_args()
    try:
        tool = load_impl(Path(__file__).with_name("prepare-cn-downloader-ui-text.py"))
        all_entries = tool.read_table(args.table)
        changed = [entry for entry in all_entries if entry.source_text != entry.zh_cn]
        if not changed:
            raise tool.TextTableError("text table has no changed rows to materialise")
        report = tool.prepare(
            changed, args.repo_root.resolve(), args.output_root.resolve())
        report["inventoryEntries"] = len(all_entries)
        report["materialisedChangedEntries"] = len(changed)
        report["unchangedInventoryEntries"] = len(all_entries) - len(changed)
        if args.report:
            args.report.parent.mkdir(parents=True, exist_ok=True)
            args.report.write_text(
                json.dumps(report, ensure_ascii=False, indent=2) + "\n", "utf-8")
        print(json.dumps(report, ensure_ascii=False, indent=2))
        return 0
    except (OSError, RuntimeError) as error:
        print(f"ERROR: {error}", file=sys.stderr)
        return 1


if __name__ == "__main__":
    raise SystemExit(main())
