#!/usr/bin/env python3
"""Verify MagiaCN cn_js_update runtime overlays and vNext source packages.

The legacy client extracts cn_js_update.zip directly below the app files directory.
Therefore a deployable package must contain the final ``magica/js/**`` and
``magica/template/**`` paths.  A deduplicated uiTextList JSON is a build source,
not a runtime overlay, until an external compiler expands it back to those paths.
"""
from __future__ import annotations

import argparse
from collections import Counter
import hashlib
import json
from pathlib import Path, PurePosixPath
import shutil
import subprocess
import tempfile
from typing import Any
import zipfile

DICT_NAMES = {
    "cardList", "charaList", "chapterList", "doppelList", "giftList",
    "itemList", "pieceList", "enemyList", "patrolAreaList", "shopItemList",
    "formationSheetList", "sectionList", "eventList", "eventStoryList",
    "arenaClassList", "charaMessageList", "live2dList", "cardMagiaMap",
    "cardSkillMap", "doppelCardMagiaMap", "emotionSkillMap", "pieceSkillMap",
    "placeSkillMap",
}
SOURCE_PATH = "magica/js/libs/uiTextList.json"
JQUERY_PATH = "magica/js/libs/jquery-3.7.1.min.js"


def sha256_bytes(data: bytes) -> str:
    return hashlib.sha256(data).hexdigest()


def sha1_id(prefix: str, text: str) -> str:
    return prefix + hashlib.sha1(text.encode("utf-8")).hexdigest()[:12].upper()


def safe_names(zf: zipfile.ZipFile) -> tuple[list[str], list[str]]:
    names: list[str] = []
    errors: list[str] = []
    seen: Counter[str] = Counter()
    seen_fold: dict[str, str] = {}
    for info in zf.infolist():
        if info.is_dir():
            continue
        name = info.filename.replace("\\", "/")
        names.append(name)
        seen[name] += 1
        p = PurePosixPath(name)
        if p.is_absolute() or not name or any(part in {"", ".", ".."} for part in p.parts):
            errors.append(f"unsafe ZIP path: {name!r}")
        folded = name.casefold()
        prior = seen_fold.get(folded)
        if prior is not None and prior != name:
            errors.append(f"case-colliding paths: {prior!r} / {name!r}")
        seen_fold[folded] = name
    errors.extend(f"duplicate ZIP entry: {name!r}" for name, n in seen.items() if n > 1)
    return names, errors


def validate_source(data: Any, runtime_names: set[str]) -> dict[str, Any]:
    out: dict[str, Any] = {"valid": False, "errors": []}
    errors: list[str] = out["errors"]
    if not isinstance(data, dict):
        errors.append("uiTextList root is not an object")
        return out
    meta, entries = data.get("_meta"), data.get("entries")
    if not isinstance(meta, dict) or not isinstance(entries, list):
        errors.append("uiTextList requires object _meta and array entries")
        return out

    text_ids: set[str] = set()
    source_ids: set[str] = set()
    zh_values: set[str] = set()
    ja_values: set[str] = set()
    refs = 0
    ref_files: set[str] = set()
    resource_types: Counter[str] = Counter()
    for ei, entry in enumerate(entries):
        if not isinstance(entry, dict):
            errors.append(f"entries[{ei}] is not an object")
            continue
        zh = entry.get("zhCN")
        tid = entry.get("id")
        sources = entry.get("sources")
        if not isinstance(zh, str) or not isinstance(tid, str) or not isinstance(sources, list):
            errors.append(f"entries[{ei}] has invalid id/zhCN/sources")
            continue
        expected_tid = sha1_id("TXT_", zh)
        if tid != expected_tid:
            errors.append(f"bad text id at entries[{ei}]: {tid} != {expected_tid}")
        if tid in text_ids:
            errors.append(f"duplicate text id: {tid}")
        if zh in zh_values:
            errors.append(f"duplicate zhCN value at entries[{ei}]")
        text_ids.add(tid)
        zh_values.add(zh)
        for si, src in enumerate(sources):
            if not isinstance(src, dict):
                errors.append(f"entries[{ei}].sources[{si}] is not an object")
                continue
            ja, sid = src.get("sourceJa"), src.get("sourceId")
            references = src.get("references")
            if not isinstance(ja, str) or not isinstance(sid, str) or not isinstance(references, list):
                errors.append(f"entries[{ei}].sources[{si}] has invalid fields")
                continue
            expected_sid = sha1_id("SRC_", ja)
            if sid != expected_sid:
                errors.append(f"bad source id at {ei}/{si}: {sid} != {expected_sid}")
            if sid in source_ids:
                errors.append(f"duplicate source id: {sid}")
            if ja in ja_values:
                errors.append(f"duplicate sourceJa at {ei}/{si}")
            source_ids.add(sid)
            ja_values.add(ja)
            for ri, ref in enumerate(references):
                refs += 1
                if not isinstance(ref, dict) or not isinstance(ref.get("file"), str):
                    errors.append(f"invalid reference at {ei}/{si}/{ri}")
                    continue
                file = ref["file"]
                ref_files.add(file)
                resource_types[str(ref.get("resourceType", "unknown"))] += 1

    expected_meta = {
        "entryCount": len(entries),
        "sourceCount": len(source_ids),
        "occurrenceCount": refs,
        "duplicateZhCNCount": 0,
        "duplicateSourceJaCount": 0,
    }
    for key, value in expected_meta.items():
        if meta.get(key) != value:
            errors.append(f"_meta.{key}={meta.get(key)!r}, calculated {value!r}")
    missing_refs = sorted(ref_files - runtime_names) if runtime_names else []
    out.update({
        "schema": meta.get("schema"),
        "entries": len(entries),
        "sources": len(source_ids),
        "references": refs,
        "referenced_files": len(ref_files),
        "reference_resource_types": dict(resource_types),
        "missing_runtime_reference_files": missing_refs,
        "valid": not errors and not missing_refs,
    })
    if missing_refs:
        errors.append(f"{len(missing_refs)} referenced files are absent from runtime overlay")
    return out


def inspect_zip(path: Path, check_js: bool) -> dict[str, Any]:
    raw = path.read_bytes()
    result: dict[str, Any] = {
        "path": str(path),
        "sha256": sha256_bytes(raw),
        "size": len(raw),
        "errors": [],
    }
    with zipfile.ZipFile(path) as zf:
        names, path_errors = safe_names(zf)
        result["errors"].extend(path_errors)
        name_set = set(names)
        ext = Counter(PurePosixPath(n).suffix.lower() for n in names)
        js_names = [n for n in names if n.endswith(".js")]
        html_names = [n for n in names if n.endswith((".html", ".ejs"))]
        json_names = [n for n in names if n.endswith(".json")]
        result.update({
            "entries": len(names),
            "extensions": dict(ext),
            "javascript": len(js_names),
            "html_templates": len(html_names),
            "json": len(json_names),
            "has_jquery_runtime_injector": JQUERY_PATH in name_set,
            "has_ui_text_source": SOURCE_PATH in name_set,
        })

        runtime_payload = bool(js_names or html_names) and all(
            n.startswith(("magica/js/", "magica/template/"))
            for n in js_names + html_names
        )
        source_only = SOURCE_PATH in name_set and not js_names and not html_names
        classification = (
            "runtime-overlay" if runtime_payload else
            "deduplicated-source-only" if source_only else
            "unknown"
        )
        result["classification"] = classification
        result["runtime_ready"] = classification == "runtime-overlay"
        result["html_overlay_present"] = bool(html_names)
        result["client_support"] = (
            "direct extraction overlay" if classification == "runtime-overlay" else
            "not directly consumable; compile to runtime JS/HTML paths first"
            if classification == "deduplicated-source-only" else "undetermined"
        )

        parsed_json = 0
        for name in json_names:
            try:
                json.loads(zf.read(name).decode("utf-8"))
                parsed_json += 1
            except Exception as exc:
                result["errors"].append(f"JSON parse failed: {name}: {exc}")
        result["json_parsed"] = parsed_json

        # These templates use the game's EJS dialect and may contain literal delimiter
        # fragments inside JavaScript strings, so raw opening/closing counts are not a
        # standalone validity test. Record structural signatures for comparison/QA,
        # but do not reject a package merely because the counts differ.
        html_signatures: dict[str, list[int]] = {}
        for name in html_names:
            text = zf.read(name).decode("utf-8")
            if "\x00" in text:
                result["errors"].append(f"NUL byte in template: {name}")
            html_signatures[name] = [
                text.count("<%"), text.count("%>"),
                text.count("<script"), text.count("</script>"),
                text.count("<style"), text.count("</style>"),
            ]
        result["html_structure_signatures"] = html_signatures

        expected_dict_paths = {f"magica/js/libs/{name}.json" for name in DICT_NAMES}
        result["dictionary_json_present"] = len(expected_dict_paths & name_set)
        result["dictionary_json_missing"] = sorted(expected_dict_paths - name_set)

        source_data = None
        if SOURCE_PATH in name_set:
            try:
                source_data = json.loads(zf.read(SOURCE_PATH).decode("utf-8"))
            except Exception:
                pass
        result["_source_data"] = source_data
        result["_names"] = names

        if check_js and js_names:
            node = shutil.which("node")
            if node:
                checked = 0
                with tempfile.TemporaryDirectory(prefix="cn-js-verify-") as td:
                    root = Path(td)
                    for name in js_names:
                        target = root / name
                        target.parent.mkdir(parents=True, exist_ok=True)
                        target.write_bytes(zf.read(name))
                        cp = subprocess.run(
                            [node, "--check", str(target)], text=True,
                            stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
                        if cp.returncode:
                            result["errors"].append(
                                f"JavaScript syntax failed: {name}: {cp.stdout.strip()}")
                        checked += 1
                result["javascript_checked"] = checked
            else:
                result["javascript_checked"] = 0
                result["javascript_check_skipped"] = "node not found"
    return result


def clean_for_json(item: dict[str, Any]) -> dict[str, Any]:
    out = dict(item)
    out.pop("_source_data", None)
    out.pop("_names", None)
    out["valid"] = not out.get("errors")
    return out


def main() -> int:
    ap = argparse.ArgumentParser()
    ap.add_argument("--runtime", type=Path, action="append", default=[],
                    help="deployable cn_js_update ZIP; may be repeated")
    ap.add_argument("--source", type=Path, help="vNext uiTextList source ZIP")
    ap.add_argument("--report", type=Path)
    ap.add_argument("--skip-js-check", action="store_true")
    ns = ap.parse_args()
    if not ns.runtime and not ns.source:
        ap.error("provide --runtime and/or --source")

    runtime_items = [inspect_zip(p, not ns.skip_js_check) for p in ns.runtime]
    runtime_name_union: set[str] = set()
    for item in runtime_items:
        runtime_name_union.update(item.get("_names", []))

    source_item = inspect_zip(ns.source, False) if ns.source else None
    source_validation = None
    if source_item and source_item.get("_source_data") is not None:
        source_validation = validate_source(source_item["_source_data"], runtime_name_union)
        source_item["source_validation"] = source_validation
        if source_validation.get("errors"):
            source_item["errors"].extend(source_validation["errors"])

    report = {
        "runtime_packages": [clean_for_json(x) for x in runtime_items],
        "source_package": clean_for_json(source_item) if source_item else None,
        "decision": {
            "recommended_deployment": "runtime-overlay",
            "recommended_editing_source": "deduplicated-source-only",
            "reason": (
                "The client installs cn_js_update by extracting final magica/js and "
                "magica/template paths. uiTextList.json needs an external build compiler "
                "and cannot replace the overlay by itself."
            ),
            "html_replacement_mechanism_active": any(
                x.get("html_overlay_present") for x in runtime_items
            ),
        },
    }
    if ns.report:
        ns.report.parent.mkdir(parents=True, exist_ok=True)
        ns.report.write_text(json.dumps(report, ensure_ascii=False, indent=2), "utf-8")
    print(json.dumps(report, ensure_ascii=False, indent=2))
    all_valid = all(x["valid"] for x in report["runtime_packages"])
    if report["source_package"] is not None:
        all_valid = all_valid and report["source_package"]["valid"]
    return 0 if all_valid else 1


if __name__ == "__main__":
    raise SystemExit(main())
