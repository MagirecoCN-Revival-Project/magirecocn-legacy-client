#!/usr/bin/env python3
"""Materialise the pinned latest-main runtime downloader baseline.

The work branch remains the only writable branch.  This tool reads selected
files from one audited main commit and writes them into a temporary Java tree;
it never changes main or the committed work-tree sources.
"""
from __future__ import annotations

import argparse
import hashlib
import json
from pathlib import Path
import subprocess
import sys

PINNED_MAIN_SHA = "d33561a24233e27b6e592d08bfb398c94c87329f"
PREFIX = "patch/src/main/java/"
FILES = (
    "patch/src/main/java/io/kamihama/magianative/CNChunkedDownload.java",
    "patch/src/main/java/io/kamihama/magianative/CNDownloaderFix.java",
    "patch/src/main/java/io/kamihama/magianative/CNHotUpdate.java",
    "patch/src/main/java/io/kamihama/magianative/CNHotUpdateCheck.java",
    "patch/src/main/java/io/kamihama/magianative/CNMirrors.java",
)


class BaselineError(RuntimeError):
    pass


def git(root: Path, *args: str, binary: bool = False):
    result = subprocess.run(
        ["git", "-C", str(root), *args],
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        check=False,
    )
    if result.returncode != 0:
        raise BaselineError(
            f"git {' '.join(args)} failed: "
            + result.stderr.decode("utf-8", "replace").strip()
        )
    return result.stdout if binary else result.stdout.decode("utf-8").strip()


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--git-root", type=Path, default=Path("."))
    parser.add_argument("--java-root", type=Path, required=True)
    parser.add_argument("--main-sha", default=PINNED_MAIN_SHA)
    parser.add_argument("--report", type=Path)
    parser.add_argument(
        "--require-origin-main",
        action="store_true",
        help="fail unless refs/remotes/origin/main equals the pinned commit",
    )
    args = parser.parse_args()

    try:
        root = args.git_root.resolve()
        java_root = args.java_root.resolve()
        resolved = git(root, "rev-parse", f"{args.main_sha}^{{commit}}")
        if resolved != args.main_sha:
            raise BaselineError(
                f"main baseline resolved to {resolved}, expected {args.main_sha}"
            )
        if args.require_origin_main:
            remote = git(root, "rev-parse", "refs/remotes/origin/main")
            if remote != args.main_sha:
                raise BaselineError(
                    "origin/main advanced or moved: "
                    f"expected {args.main_sha}, found {remote}. Review main first."
                )

        records = []
        for repository_path in FILES:
            if not repository_path.startswith(PREFIX):
                raise BaselineError(f"unexpected path outside Java root: {repository_path}")
            raw = git(root, "show", f"{args.main_sha}:{repository_path}", binary=True)
            if not raw:
                raise BaselineError(f"empty baseline file: {repository_path}")
            relative = Path(repository_path[len(PREFIX):])
            destination = java_root / relative
            destination.parent.mkdir(parents=True, exist_ok=True)
            destination.write_bytes(raw)
            blob = git(root, "rev-parse", f"{args.main_sha}:{repository_path}")
            records.append({
                "path": repository_path,
                "blob": blob,
                "bytes": len(raw),
                "sha256": hashlib.sha256(raw).hexdigest(),
            })

        report = {
            "schema": 1,
            "pinnedMainCommit": args.main_sha,
            "originMainRequired": args.require_origin_main,
            "javaRoot": str(java_root),
            "files": records,
        }
        rendered = json.dumps(report, ensure_ascii=False, indent=2) + "\n"
        if args.report:
            args.report.parent.mkdir(parents=True, exist_ok=True)
            args.report.write_text(rendered, "utf-8")
        print(rendered, end="")
        return 0
    except (OSError, BaselineError) as error:
        print(f"ERROR: {error}", file=sys.stderr)
        return 1


if __name__ == "__main__":
    raise SystemExit(main())
