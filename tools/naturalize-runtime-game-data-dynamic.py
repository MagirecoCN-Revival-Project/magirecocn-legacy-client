#!/usr/bin/env python3
"""Adapt the audited 23-dictionary naturaliser to current runtime dictionaries.

The existing naturaliser is intentionally strict and historically expects 23
standalone JSON dictionaries.  Current runtime packages contain 22 while their
jQuery embedded `cn` object has the same 22 keys.  This wrapper temporarily adds
non-semantic padding dictionaries, invokes the audited core unchanged, then
removes every padding entry from both the ZIP and embedded jQuery object.  The
final archive must have exactly the original path set and dictionary key set.
"""
from __future__ import annotations

import argparse
import copy
import hashlib
import json
from pathlib import Path, PurePosixPath
import subprocess
import sys
import tempfile
import zipfile

CORE = Path(__file__).with_name("naturalize-runtime-game-data.py")
JQUERY = "magica/js/libs/jquery-3.7.1.min.js"
PREFIX = "magica/js/libs/"
TARGET_COUNT = 23
PAD_PREFIX = "__runtimeCompatPad"


class CompatError(RuntimeError):
    pass


def clone_info(info: zipfile.ZipInfo) -> zipfile.ZipInfo:
    value = copy.copy(info)
    value.CRC = value.file_size = value.compress_size = 0
    return value


def parse_embedded(jquery: str):
    marker = "var cn = "
    at = jquery.find(marker)
    if at < 0:
        raise CompatError("jQuery cn dictionary marker not found")
    start = at + len(marker)
    value, consumed = json.JSONDecoder().raw_decode(jquery[start:])
    if not isinstance(value, dict):
        raise CompatError("jQuery cn dictionary is not an object")
    return start, start + consumed, value


def write_zip(path: Path, infos, payload) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    with zipfile.ZipFile(path, "w") as output:
        for info in infos:
            output.writestr(
                clone_info(info), b"" if info.is_dir() else payload[info.filename])


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("input_zip", type=Path)
    parser.add_argument("output_zip", type=Path)
    parser.add_argument("--jp-source-dir", type=Path, required=True)
    parser.add_argument("--report", type=Path)
    parser.add_argument("--minimum-source-translations", type=int, default=50)
    args = parser.parse_args()

    try:
        with zipfile.ZipFile(args.input_zip) as source:
            original_infos = source.infolist()
            original_payload = {
                info.filename: source.read(info.filename)
                for info in original_infos if not info.is_dir()
            }
        original_paths = [i.filename for i in original_infos if not i.is_dir()]
        dictionary_paths = sorted(
            name for name in original_paths
            if name.startswith(PREFIX) and name.endswith(".json")
        )
        jquery = original_payload[JQUERY].decode("utf-8-sig")
        start, end, embedded = parse_embedded(jquery)
        embedded_paths = {PREFIX + key + ".json" for key in embedded}
        if set(dictionary_paths) != embedded_paths:
            raise CompatError(
                "standalone dictionary paths do not exactly match jQuery keys: "
                f"standalone={len(dictionary_paths)} embedded={len(embedded_paths)}"
            )
        original_count = len(dictionary_paths)
        if original_count > TARGET_COUNT:
            raise CompatError(
                f"runtime has {original_count} dictionaries, audited core supports "
                f"at most {TARGET_COUNT}; update core instead of dropping data")

        pads = []
        padded_input = args.input_zip
        with tempfile.TemporaryDirectory(prefix="naturalize-dynamic-") as temp_dir:
            temp = Path(temp_dir)
            if original_count < TARGET_COUNT:
                padded_input = temp / "padded-input.zip"
                payload = dict(original_payload)
                infos = list(original_infos)
                padded_embedded = dict(embedded)
                for index in range(TARGET_COUNT - original_count):
                    key = f"{PAD_PREFIX}{index + 1:02d}"
                    path = PREFIX + key + ".json"
                    pads.append((key, path))
                    padded_embedded[key] = {}
                    payload[path] = b"{}\n"
                    info = zipfile.ZipInfo(path)
                    info.date_time = (1980, 1, 1, 0, 0, 0)
                    info.compress_type = zipfile.ZIP_DEFLATED
                    info.external_attr = 0o100644 << 16
                    infos.append(info)
                payload[JQUERY] = (
                    jquery[:start]
                    + json.dumps(padded_embedded, ensure_ascii=False,
                                 separators=(",", ":"))
                    + jquery[end:]
                ).encode("utf-8")
                write_zip(padded_input, infos, payload)

            padded_output = temp / "padded-output.zip"
            core_report = temp / "core-report.json"
            command = [
                sys.executable, str(CORE), str(padded_input), str(padded_output),
                "--jp-source-dir", str(args.jp_source_dir),
                "--minimum-source-translations",
                str(args.minimum_source_translations),
                "--report", str(core_report),
            ]
            result = subprocess.run(command, check=False)
            if result.returncode != 0:
                return result.returncode

            with zipfile.ZipFile(padded_output) as source:
                output_infos = source.infolist()
                output_payload = {
                    info.filename: source.read(info.filename)
                    for info in output_infos if not info.is_dir()
                }
            if pads:
                pad_paths = {path for _key, path in pads}
                output_infos = [
                    info for info in output_infos
                    if info.is_dir() or info.filename not in pad_paths
                ]
                for path in pad_paths:
                    output_payload.pop(path, None)
                output_jquery = output_payload[JQUERY].decode("utf-8-sig")
                out_start, out_end, out_embedded = parse_embedded(output_jquery)
                for key, _path in pads:
                    if key not in out_embedded:
                        raise CompatError(f"padding key disappeared before cleanup: {key}")
                    del out_embedded[key]
                output_payload[JQUERY] = (
                    output_jquery[:out_start]
                    + json.dumps(out_embedded, ensure_ascii=False,
                                 separators=(",", ":"))
                    + output_jquery[out_end:]
                ).encode("utf-8")
            write_zip(args.output_zip, output_infos, output_payload)

            final_paths = [i.filename for i in output_infos if not i.is_dir()]
            if final_paths != original_paths:
                raise CompatError("final ZIP file/path order differs from original runtime")
            final_jquery = output_payload[JQUERY].decode("utf-8-sig")
            _s, _e, final_embedded = parse_embedded(final_jquery)
            if list(final_embedded.keys()) != list(embedded.keys()):
                raise CompatError("final jQuery dictionary key order differs from input")
            for _key, path in pads:
                if path in final_paths or _key in final_embedded:
                    raise CompatError("padding entry survived final package")

            natural_report = json.loads(core_report.read_text("utf-8"))
            report = dict(natural_report)
            report.update({
                "dynamicDictionarySchema": 1,
                "inputRuntimeDictionaryCount": original_count,
                "jQueryDictionaryCount": len(embedded),
                "temporaryPaddingDictionaryCount": len(pads),
                "temporaryPaddingRemoved": True,
                "finalRuntimeDictionaryCount": len(final_embedded),
                "originalPathCount": len(original_paths),
                "finalPathCount": len(final_paths),
                "pathSetAndOrderPreserved": True,
                "output": str(args.output_zip),
                "outputSha256": hashlib.sha256(
                    args.output_zip.read_bytes()).hexdigest(),
            })
            rendered = json.dumps(report, ensure_ascii=False, indent=2) + "\n"
            if args.report:
                args.report.parent.mkdir(parents=True, exist_ok=True)
                args.report.write_text(rendered, "utf-8")
            print(rendered, end="")
        return 0
    except (OSError, KeyError, ValueError, CompatError, zipfile.BadZipFile) as error:
        print(f"ERROR: {error}", file=sys.stderr)
        return 1


if __name__ == "__main__":
    raise SystemExit(main())
