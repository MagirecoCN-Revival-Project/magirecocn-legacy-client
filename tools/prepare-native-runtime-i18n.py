#!/usr/bin/env python3
"""Generate the native runtime-i18n translation unit from audited sources."""
from __future__ import annotations

import argparse
import hashlib
import json
import sys
from pathlib import Path

START_IMPL = "// ─── 引擎硬编码串翻译（cocos2d::Label 系列钩子）"
END_IMPL = "// ─── JNI_OnLoad"
START_HOOKS = "// ── 引擎硬编码串翻译（cocos2d::Label 系列）──"
END_HOOKS = '    LOGI("[JNI] hooks 安装完成：成功 %d 个，失败 %d 个", hookOk, hookFail);'
LEGACY_ANCHORS = (
    "struct NdkStrView",
    "static void fontPathOverwrite",
    'H("_ZN7cocos2d5Label20setTTFConfigInternal',
)
FORBIDDEN = (
    "struct NdkStrView",
    "fontPathOverwrite(",
    "fontPathFix(",
    "static std::string hold;",
    'font: createWithTTF(cfg)',
    'font: createWithTTF(str)',
    'font: setTTFConfigInternal',
)
INCLUDE_REQUIRED = (
    "std::atomic_load_explicit",
    "struct TtfConfigAbi",
    "struct CNColor4B { unsigned char r, g, b, a; };",
    "LoadingSetTextFn = void (*)(void*, std::string)",
    "installRuntimeI18nHooks",
    "i18n-font: observe Label::setTTFConfigInternal",
)


class GenerationError(RuntimeError):
    pass


def replace_between(text: str, start: str, end: str, replacement: str) -> str:
    start_count = text.count(start)
    end_count = text.count(end)
    if start_count != 1 or end_count != 1:
        raise GenerationError(
            f"marker drift: {start!r}={start_count}, {end!r}={end_count}"
        )
    begin = text.index(start)
    finish = text.index(end, begin)
    return text[:begin] + replacement + "\n\n" + text[finish:]


def sha256_text(text: str) -> str:
    return hashlib.sha256(text.encode("utf-8")).hexdigest()


def generate(source: Path, runtime_include: Path, output: Path,
             report: Path | None) -> dict:
    original = source.read_text("utf-8")
    include_text = runtime_include.read_text("utf-8")

    for token in LEGACY_ANCHORS:
        if token not in original:
            raise GenerationError(f"missing audited baseline anchor: {token}")
    for token in INCLUDE_REQUIRED:
        if token not in include_text:
            raise GenerationError(f"runtime include is incomplete: {token}")
    for token in FORBIDDEN:
        if token in include_text:
            raise GenerationError(f"runtime include contains legacy code: {token}")

    generated = original
    include_additions = (
        ("#include <atomic>\n", "#include <algorithm>",
         "#include <algorithm>\n#include <atomic>\n"),
        ("#include <unordered_map>\n", "#include <unordered_set>",
         "#include <unordered_map>\n#include <unordered_set>\n"),
        ("#include <vector>\n", "#include <utility>",
         "#include <utility>\n#include <vector>\n"),
    )
    for anchor, required_include, replacement in include_additions:
        if required_include not in generated:
            if generated.count(anchor) != 1:
                raise GenerationError(f"include anchor drift: {anchor!r}")
            generated = generated.replace(anchor, replacement, 1)
    for _, required_include, _ in include_additions:
        if generated.count(required_include) != 1:
            raise GenerationError(
                f"generated include count is not one: {required_include}"
            )

    generated = replace_between(
        generated, START_IMPL, END_IMPL,
        '#include "RuntimeI18n.inc"'
    )
    generated = replace_between(
        generated, START_HOOKS, END_HOOKS,
        "    installRuntimeI18nHooks(H);"
    )

    for token in FORBIDDEN:
        if token in generated:
            raise GenerationError(f"legacy native code survived: {token}")
    if generated.count('#include "RuntimeI18n.inc"') != 1:
        raise GenerationError("runtime include was not inserted exactly once")
    if generated.count("installRuntimeI18nHooks(H);") != 1:
        raise GenerationError("hook installer was not inserted exactly once")

    output.parent.mkdir(parents=True, exist_ok=True)
    output.write_text(generated, "utf-8")
    result = {
        "source": str(source),
        "runtimeInclude": str(runtime_include),
        "output": str(output),
        "sourceSha256": sha256_text(original),
        "runtimeIncludeSha256": sha256_text(include_text),
        "outputSha256": sha256_text(generated),
        "removedGlobalFontPathMutation": True,
        "architectureNeutralStdStringAbi": True,
        "immutableTranslationSnapshots": True,
        "localizedTranslatedLabelFontSelection": True,
    }
    if report:
        report.parent.mkdir(parents=True, exist_ok=True)
        report.write_text(
            json.dumps(result, ensure_ascii=False, indent=2) + "\n",
            "utf-8",
        )
    return result


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--source", required=True, type=Path)
    parser.add_argument("--runtime-include", required=True, type=Path)
    parser.add_argument("--output", required=True, type=Path)
    parser.add_argument("--report", type=Path)
    args = parser.parse_args()
    try:
        result = generate(
            args.source, args.runtime_include, args.output, args.report
        )
    except (OSError, GenerationError) as exc:
        print(f"ERROR: {exc}", file=sys.stderr)
        return 1
    print(json.dumps(result, ensure_ascii=False, indent=2))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
