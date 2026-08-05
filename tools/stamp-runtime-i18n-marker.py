#!/usr/bin/env python3
"""Stamp a deterministic execution marker into the runtime jQuery overlay."""
from __future__ import annotations

import argparse
import hashlib
import json
from pathlib import Path, PurePosixPath
import shutil
import subprocess
import tempfile
import zipfile

TARGET = "magica/js/libs/jquery-3.7.1.min.js"
SCHEMA = "magireco-cn-runtime-i18n/v1"
MARKER_NAME = "__MAGIACN_RUNTIME_I18N__"


class StampError(RuntimeError):
    pass


def sha256_bytes(value: bytes) -> str:
    return hashlib.sha256(value).hexdigest()


def sha256_file(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as handle:
        for block in iter(lambda: handle.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def safe_name(name: str) -> None:
    path = PurePosixPath(name)
    if (name.startswith("/") or "\\" in name
            or any(part in {"", ".", ".."} for part in path.parts)):
        raise StampError(f"unsafe ZIP path: {name!r}")


def marker_script(package_id: str, input_sha: str) -> bytes:
    payload = {
        "__schema": SCHEMA,
        "packageId": package_id,
        "inputSha256": input_sha,
        "jqueryOverlay": True,
    }
    encoded = json.dumps(payload, ensure_ascii=False, separators=(",", ":"))
    script = (
        "\n;(function(g){g." + MARKER_NAME + "=" + encoded
        + ";})(typeof window!==\"undefined\"?window:this);\n"
    )
    return script.encode("utf-8")


def node_check(content: bytes) -> str | None:
    node = shutil.which("node")
    if node is None:
        return None
    with tempfile.NamedTemporaryFile(suffix=".js", delete=False) as handle:
        handle.write(content)
        path = Path(handle.name)
    try:
        completed = subprocess.run(
            [node, "--check", str(path)], text=True,
            stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
        if completed.returncode:
            raise StampError("node --check failed: " + completed.stdout.strip())
        return node
    finally:
        path.unlink(missing_ok=True)


def stamp(input_zip: Path, output_zip: Path, package_id: str) -> dict:
    if not input_zip.is_file():
        raise StampError(f"input ZIP does not exist: {input_zip}")
    input_sha = sha256_file(input_zip)
    with zipfile.ZipFile(input_zip) as source:
        infos = [info for info in source.infolist() if not info.is_dir()]
        names = [info.filename for info in infos]
        for name in names:
            safe_name(name)
        if len(names) != len(set(names)):
            raise StampError("input ZIP has duplicate entries")
        if names.count(TARGET) != 1:
            raise StampError(f"expected exactly one {TARGET}, found {names.count(TARGET)}")
        original = {info.filename: source.read(info.filename) for info in infos}

    jquery = original[TARGET]
    try:
        jquery.decode("utf-8-sig")
    except UnicodeDecodeError as error:
        raise StampError(f"jQuery is not UTF-8: {error}") from error
    if MARKER_NAME.encode("ascii") in jquery:
        raise StampError("runtime marker already exists; refusing double stamp")
    stamped = jquery + marker_script(package_id, input_sha)
    node = node_check(stamped)

    output_zip.parent.mkdir(parents=True, exist_ok=True)
    with zipfile.ZipFile(
            output_zip, "w", compression=zipfile.ZIP_DEFLATED,
            compresslevel=9) as target:
        for info in infos:
            name = info.filename
            content = stamped if name == TARGET else original[name]
            generated = zipfile.ZipInfo(name, (1980, 1, 1, 0, 0, 0))
            generated.compress_type = zipfile.ZIP_DEFLATED
            generated.external_attr = 0o100644 << 16
            target.writestr(generated, content)

    with zipfile.ZipFile(output_zip) as result:
        result_names = [info.filename for info in result.infolist() if not info.is_dir()]
        if result_names != names:
            raise StampError("entry order/path drift after stamping")
        output_contents = {name: result.read(name) for name in result_names}
    changed = [
        name for name in names
        if sha256_bytes(original[name]) != sha256_bytes(output_contents[name])
    ]
    if changed != [TARGET]:
        raise StampError(f"unexpected changed entries: {changed}")
    if MARKER_NAME.encode("ascii") not in output_contents[TARGET]:
        raise StampError("marker missing from output jQuery")

    return {
        "schema": SCHEMA,
        "packageId": package_id,
        "input": str(input_zip),
        "inputSha256": input_sha,
        "output": str(output_zip),
        "outputSha256": sha256_file(output_zip),
        "entries": len(names),
        "changedEntries": changed,
        "jqueryBeforeSha256": sha256_bytes(jquery),
        "jqueryAfterSha256": sha256_bytes(stamped),
        "nodeChecked": node is not None,
        "nodeBinary": node,
        "markerObject": {
            "__schema": SCHEMA,
            "packageId": package_id,
            "inputSha256": input_sha,
            "jqueryOverlay": True,
        },
    }


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("input_zip", type=Path)
    parser.add_argument("output_zip", type=Path)
    parser.add_argument("--package-id", default="runtime-i18n-v7")
    parser.add_argument("--report", type=Path)
    args = parser.parse_args()
    try:
        report = stamp(args.input_zip, args.output_zip, args.package_id)
    except (OSError, zipfile.BadZipFile, StampError) as error:
        parser.exit(1, f"ERROR: {error}\n")
    rendered = json.dumps(report, ensure_ascii=False, indent=2) + "\n"
    if args.report:
        args.report.parent.mkdir(parents=True, exist_ok=True)
        args.report.write_text(rendered, "utf-8")
    print(rendered, end="")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
