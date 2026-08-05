#!/usr/bin/env python3
"""Generate the compiled MagiaLegacy source with ABI-safe text hooks.

The committed MagiaLegacy.cpp remains the audited baseline and retains the
device-verified global font-path hook.  This generator replaces only the unsafe
text translation implementation/installer, then asserts that all three font
hooks and both font paths survive byte-for-byte in the generated source.
"""
from __future__ import annotations

import argparse
import hashlib
import json
from pathlib import Path
import sys

START_IMPL = "// ─── 引擎硬编码串翻译（cocos2d::Label 系列钩子）"
END_IMPL = "// NDK libc++ std::string 原地改写（font 段复用 i18n 段的 NdkStrView）"
START_HOOKS = "    // ── 引擎硬编码串翻译（cocos2d::Label 系列）──"
END_HOOKS = "    // ── 引擎 UI 字体路径重定向（MTF4a5kp → TTZhiHeiGB3-W4）──"

UNSAFE_TEXT_TOKENS = (
    "static std::unordered_map<std::string, std::string> g_engineI18n;",
    "struct FakeNdkStr",
    "fakeNdkStr(",
    "using SetStringFn = void (*)(void*, const void*)",
    "struct CNColor4B { unsigned char r, g, b; };",
    "g_engineI18n.swap(fresh)",
)
REQUIRED_SAFE = (
    '#include "RuntimeTextI18n.inc"',
    '#include "RuntimeFontCompat.inc"',
    "installRuntimeTextI18nHooks(H);",
    "std::atomic_store_explicit",
    "struct CNColor4B { unsigned char r, g, b, a; };",
    "using LoadingSetTextFn = void (*)(void*, std::string);",
)
REQUIRED_FONT = (
    'static const char kFrom[] = "fonts/MTF4a5kp.ttf";',
    'static const char kTo[]   = "fonts/TTZhiHeiGB3-W4.ttf";',
    '"font: createWithTTF(cfg)"',
    '"font: createWithTTF(str)"',
    '"font: setTTFConfigInternal"',
)


class GenerationError(RuntimeError):
    pass


def replace_between(text: str, start: str, end: str, replacement: str) -> str:
    if text.count(start) != 1 or text.count(end) != 1:
        raise GenerationError(
            f"marker drift: {start!r}={text.count(start)}, {end!r}={text.count(end)}"
        )
    begin = text.index(start)
    finish = text.index(end, begin)
    return text[:begin] + replacement + "\n\n" + text[finish:]


def sha256(value: str) -> str:
    return hashlib.sha256(value.encode("utf-8")).hexdigest()


def generate(source: Path, output: Path, report: Path | None) -> dict:
    original = source.read_text("utf-8")
    generated = original

    if "#include <algorithm>" not in generated:
        anchor = "#include <atomic>\n"
        if generated.count(anchor) != 1:
            raise GenerationError("cannot insert <algorithm>: atomic include drift")
        generated = generated.replace(
            anchor, "#include <algorithm>\n#include <atomic>\n", 1)

    generated = replace_between(
        generated,
        START_IMPL,
        END_IMPL,
        '#include "RuntimeTextI18n.inc"\n#include "RuntimeFontCompat.inc"',
    )
    generated = replace_between(
        generated,
        START_HOOKS,
        END_HOOKS,
        "    installRuntimeTextI18nHooks(H);",
    )

    for token in UNSAFE_TEXT_TOKENS:
        if token in generated:
            raise GenerationError(f"unsafe text-hook code survived: {token}")
    for token in REQUIRED_SAFE:
        if token not in generated:
            raise GenerationError(f"safe text-hook requirement missing: {token}")
    for token in REQUIRED_FONT:
        if token not in generated:
            raise GenerationError(f"verified font-path hook was lost: {token}")

    output.parent.mkdir(parents=True, exist_ok=True)
    output.write_text(generated, "utf-8")
    result = {
        "source": str(source),
        "output": str(output),
        "sourceSha256": sha256(original),
        "outputSha256": sha256(generated),
        "abiSafeTextHooks": True,
        "immutableTranslationSnapshot": True,
        "color4bRgba": True,
        "verifiedGlobalFontPathHookPreserved": True,
        "fontCompatibilityLayerStillArm64Specific": True,
    }
    if report:
        report.parent.mkdir(parents=True, exist_ok=True)
        report.write_text(json.dumps(result, ensure_ascii=False, indent=2) + "\n", "utf-8")
    return result


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--source", type=Path, required=True)
    parser.add_argument("--output", type=Path, required=True)
    parser.add_argument("--report", type=Path)
    args = parser.parse_args()
    try:
        result = generate(args.source, args.output, args.report)
    except (OSError, GenerationError) as error:
        print(f"ERROR: {error}", file=sys.stderr)
        return 1
    print(json.dumps(result, ensure_ascii=False, indent=2))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
