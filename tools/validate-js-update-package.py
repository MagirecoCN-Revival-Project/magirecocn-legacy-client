#!/usr/bin/env python3
"""Validate and classify MagiaCN front-end hot-update ZIP packages."""
from __future__ import annotations

import argparse
import hashlib
import json
import shutil
import subprocess
import tempfile
import zipfile
from collections import Counter
from pathlib import Path, PurePosixPath

SCHEMA = "magireco-cn-ui-text-source/v2"
OVERLAY_SUFFIXES = {".js", ".html", ".htm", ".css"}


def sha256(path: Path) -> str:
    h = hashlib.sha256()
    with path.open("rb") as f:
        for block in iter(lambda: f.read(1024 * 1024), b""):
            h.update(block)
    return h.hexdigest()


def safe_name(name: str) -> bool:
    path = PurePosixPath(name)
    return bool(name) and not name.startswith("/") and "\\" not in name \
        and all(part not in {"", ".", ".."} for part in path.parts)


def validate_ui_source(data: dict, errors: list[str]) -> dict:
    meta = data.get("_meta")
    if not isinstance(meta, dict) or meta.get("schema") != SCHEMA:
        errors.append(f"unsupported UI text schema; expected {SCHEMA}")
        return {}
    entries = data.get("entries")
    if not isinstance(entries, list):
        errors.append("UI text source: entries must be an array")
        return {}

    entry_ids: list[str] = []
    zh_values: list[str] = []
    source_ids: list[str] = []
    ja_values: list[str] = []
    references = 0
    needs_review = 0
    for ei, entry in enumerate(entries):
        if not isinstance(entry, dict):
            errors.append(f"entries[{ei}] is not an object")
            continue
        entry_id, zh, sources = entry.get("id"), entry.get("zhCN"), entry.get("sources")
        if not isinstance(entry_id, str) or not entry_id:
            errors.append(f"entries[{ei}].id is missing")
        else:
            entry_ids.append(entry_id)
        if not isinstance(zh, str) or not zh:
            errors.append(f"entries[{ei}].zhCN is empty")
        else:
            zh_values.append(zh)
        if not isinstance(sources, list) or not sources:
            errors.append(f"entries[{ei}].sources is empty")
            continue
        for si, source in enumerate(sources):
            if not isinstance(source, dict):
                errors.append(f"entries[{ei}].sources[{si}] is not an object")
                continue
            source_id, ja, refs = source.get("sourceId"), source.get("sourceJa"), source.get("references")
            if not isinstance(source_id, str) or not source_id:
                errors.append(f"entries[{ei}].sources[{si}].sourceId is missing")
            else:
                source_ids.append(source_id)
            if not isinstance(ja, str) or not ja:
                errors.append(f"entries[{ei}].sources[{si}].sourceJa is empty")
            else:
                ja_values.append(ja)
            if not isinstance(refs, list):
                errors.append(f"entries[{ei}].sources[{si}].references is not an array")
                continue
            references += len(refs)
            needs_review += sum(
                isinstance(ref, dict) and ref.get("needsHumanReview") is True for ref in refs
            )

    for label, values in (
        ("entry id", entry_ids), ("source id", source_ids),
        ("zhCN", zh_values), ("sourceJa", ja_values),
    ):
        duplicates = [value for value, count in Counter(values).items() if count > 1]
        if duplicates:
            errors.append(f"duplicate {label}: {len(duplicates)} (example: {duplicates[:3]})")

    return {
        "schema": SCHEMA,
        "uniqueZhCN": len(zh_values),
        "uniqueSourceJa": len(ja_values),
        "references": references,
        "needsHumanReview": needs_review,
    }


def validate(path: Path, require_deployable: bool) -> dict:
    errors: list[str] = []
    warnings: list[str] = []
    report = {
        "archive": str(path), "sha256": sha256(path), "ok": False,
        "classification": "unknown", "errors": errors, "warnings": warnings,
        "metrics": {},
    }
    try:
        zf = zipfile.ZipFile(path)
    except (OSError, zipfile.BadZipFile) as exc:
        errors.append(f"cannot open ZIP: {exc}")
        return report

    node = shutil.which("node")
    with zf, tempfile.TemporaryDirectory(prefix="magia-js-qa-") as temp:
        infos = [info for info in zf.infolist() if not info.is_dir()]
        names = [info.filename for info in infos]
        report["fileCount"] = len(names)
        if len(names) != len(set(names)):
            errors.append("duplicate ZIP member names")
        if len(names) != len(set(name.casefold() for name in names)):
            errors.append("case-insensitive path collision")
        for name in names:
            if not safe_name(name):
                errors.append(f"unsafe member path: {name!r}")
            elif not name.startswith("magica/") and name != "README.txt":
                errors.append(f"member outside magica/: {name}")

        suffix = lambda name: PurePosixPath(name).suffix.lower()
        js_files = [name for name in names if suffix(name) == ".js"]
        html_files = [name for name in names if suffix(name) in {".html", ".htm"}]
        json_files = [name for name in names if suffix(name) == ".json"]
        overlay_files = [
            name for name in names
            if name.startswith("magica/") and suffix(name) in OVERLAY_SUFFIXES
        ]
        ui_source = None

        for info in infos:
            name = info.filename
            try:
                raw = zf.read(info)
            except (OSError, RuntimeError, zipfile.BadZipFile) as exc:
                errors.append(f"{name}: cannot read: {exc}")
                continue
            ext = suffix(name)
            if ext in {".js", ".html", ".htm", ".json"}:
                try:
                    text = raw.decode("utf-8-sig")
                except UnicodeDecodeError as exc:
                    errors.append(f"{name}: invalid UTF-8: {exc}")
                    continue
            if ext == ".json":
                try:
                    data = json.loads(text)
                except json.JSONDecodeError as exc:
                    errors.append(f"{name}: invalid JSON: {exc}")
                    continue
                if isinstance(data, dict) and data.get("_meta", {}).get("schema") == SCHEMA:
                    ui_source = validate_ui_source(data, errors)
            elif ext == ".js" and node:
                target = Path(temp, name)
                target.parent.mkdir(parents=True, exist_ok=True)
                target.write_bytes(raw)
                proc = subprocess.run(
                    [node, "--check", str(target)], text=True,
                    stdout=subprocess.PIPE, stderr=subprocess.STDOUT, check=False,
                )
                if proc.returncode:
                    errors.append(f"{name}: node --check failed: {proc.stdout.strip()[-500:]}")

        report["metrics"] = {
            "jsFiles": len(js_files), "htmlFiles": len(html_files),
            "jsonFiles": len(json_files), "deployableOverlayFiles": len(overlay_files),
            "nodeSyntaxCheck": "executed" if node else "skipped-node-not-found",
            "hasInjectedJQuery": "magica/js/libs/jquery-3.7.1.min.js" in names,
        }
        if ui_source is not None:
            report["metrics"]["uiTextSource"] = ui_source
        if overlay_files:
            report["classification"] = "deployable-overlay"
            if not html_files:
                warnings.append("overlay contains no HTML templates")
        elif ui_source is not None:
            report["classification"] = "authoring-source-only"
            warnings.append(
                "current client does not consume this JSON; materialise a full JS/HTML overlay"
            )
        else:
            report["classification"] = "unknown-nondeployable"
            errors.append("neither deployable overlay files nor a supported UI source were found")
        if require_deployable and report["classification"] != "deployable-overlay":
            errors.append(f"{report['classification']} package cannot be published as cn_js_update.zip")

    report["ok"] = not errors
    return report


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("archives", nargs="+", type=Path)
    parser.add_argument("--require-deployable", action="store_true")
    parser.add_argument("--json-report", type=Path)
    args = parser.parse_args()
    reports = [validate(path, args.require_deployable) for path in args.archives]
    for report in reports:
        print(f"[{'PASS' if report['ok'] else 'FAIL'}] {report['archive']}")
        print(f"  sha256: {report['sha256']}")
        print(f"  files: {report.get('fileCount', 0)}")
        print(f"  classification: {report['classification']}")
        for key, value in report["metrics"].items():
            print(f"  {key}: {value}")
        for warning in report["warnings"]:
            print(f"  warning: {warning}")
        for error in report["errors"]:
            print(f"  error: {error}")
    if args.json_report:
        args.json_report.parent.mkdir(parents=True, exist_ok=True)
        args.json_report.write_text(
            json.dumps(reports, ensure_ascii=False, indent=2) + "\n", encoding="utf-8"
        )
    return 0 if all(report["ok"] for report in reports) else 1


if __name__ == "__main__":
    raise SystemExit(main())
