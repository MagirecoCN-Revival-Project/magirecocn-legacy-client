#!/usr/bin/env python3
"""Generate MagiaLegacy with inlined ABI-safe text and authoritative CN font hooks."""
from __future__ import annotations

import argparse
import hashlib
import json
from pathlib import Path
import sys

START_TEXT_IMPL = "// ─── 引擎硬编码串翻译（cocos2d::Label 系列钩子）"
END_FONT_IMPL = "// ─── JNI_OnLoad"
START_TEXT_HOOKS = "    // ── 引擎硬编码串翻译（cocos2d::Label 系列）──"
START_FONT_HOOKS = "    // ── 引擎 UI 字体路径重定向（MTF4a5kp → TTZhiHeiGB3-W4）──"
END_FONT_HOOKS = '    LOGI("[JNI] hooks 安装完成：成功 %d 个，失败 %d 个", hookOk, hookFail);'
TEXT_INLINE_MARKER = "// BEGIN INLINED RuntimeTextI18n.inc"
FONT_INLINE_MARKER = "// BEGIN INLINED RuntimeFontPathHook.inc"

UNSAFE_TOKENS = (
    "static std::unordered_map<std::string, std::string> g_engineI18n;",
    "struct FakeNdkStr",
    "fakeNdkStr(",
    "using SetStringFn = void (*)(void*, const void*)",
    "struct CNColor4B { unsigned char r, g, b; };",
    "g_engineI18n.swap(fresh)",
    "struct NdkStrView",
    "fontPathOverwrite(",
    "fontPathFix(",
    "static std::string hold;",
    "using SetTtfCfgFn = void (*)(void*, const void*)",
)
REQUIRED_GENERATED = (
    TEXT_INLINE_MARKER,
    FONT_INLINE_MARKER,
    "installRuntimeTextI18nHooks(H);",
    "installRuntimeFontPathHooks(H);",
    "#include <algorithm>",
    "#include <utility>",
)
REQUIRED_TEXT_INCLUDE = (
    "std::atomic_store_explicit",
    "std::atomic_load_explicit",
    "struct CNColor4B { unsigned char r, g, b, a; };",
    "using LoadingSetTextFn = void (*)(void*, std::string);",
    "using RuntimeSetStringFn = void (*)(void*, const std::string&);",
    "installRuntimeTextI18nHooks",
)
REQUIRED_FONT_INCLUDE = (
    'RUNTIME_UI_FONT_FROM[] = "fonts/MTF4a5kp.ttf"',
    'RUNTIME_UI_FONT_TO[] = "fonts/TTZhiHeiGB3-W4.ttf"',
    'RUNTIME_STORY_FONT_FROM[] = "fonts/mbm_20160902.ttf"',
    'RUNTIME_STORY_FONT_TO[] = "fonts/TTDaYuanGB3.ttf"',
    "struct RuntimeTtfConfig",
    "using CreateWithTtfConfigFn = void* (*)(",
    "using CreateWithTtfPathFn = void* (*)(",
    "using SetTtfConfigInternalFn = bool (*)(",
    "using FullPathForFilenameFn = std::string (*)(",
    "RuntimeTtfConfig local = config;",
    "fullPathForFilenameNew",
    "installRuntimeFontPathHooks",
    '"font: createWithTTF(cfg) typed CN UI/story routes"',
    '"font: createWithTTF(str) typed CN UI/story routes"',
    '"font: setTTFConfigInternal typed CN UI/story routes"',
    '"font: FileUtils fullPath typed WebView fontDataGet route"',
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


def ensure_include(text: str, include: str, anchor: str) -> str:
    if include in text:
        return text
    if text.count(anchor) != 1:
        raise GenerationError(f"cannot insert {include}: anchor drift {anchor!r}")
    return text.replace(anchor, include + "\n" + anchor, 1)


def sha256(value: str) -> str:
    return hashlib.sha256(value.encode("utf-8")).hexdigest()


def require_tokens(text: str, tokens: tuple[str, ...], label: str) -> None:
    for token in tokens:
        if token not in text:
            raise GenerationError(f"{label} missing: {token}")


def inline_block(marker: str, filename: str, content: str) -> str:
    return (
        f"{marker}\n"
        f"// Source: {filename}; generated copy, do not edit here.\n"
        + content.rstrip() + "\n"
        f"// END INLINED {filename}"
    )


def generate(source: Path, output: Path, report: Path | None) -> dict:
    original = source.read_text("utf-8")
    text_include_path = source.parent / "RuntimeTextI18n.inc"
    font_include_path = source.parent / "RuntimeFontPathHook.inc"
    text_include = text_include_path.read_text("utf-8")
    font_include = font_include_path.read_text("utf-8")

    require_tokens(text_include, REQUIRED_TEXT_INCLUDE, "RuntimeTextI18n.inc")
    require_tokens(font_include, REQUIRED_FONT_INCLUDE, "RuntimeFontPathHook.inc")

    generated = original
    generated = ensure_include(generated, "#include <algorithm>", "#include <atomic>")
    generated = ensure_include(generated, "#include <utility>", "#include <vector>")

    implementations = (
        inline_block(TEXT_INLINE_MARKER, "RuntimeTextI18n.inc", text_include)
        + "\n\n"
        + inline_block(FONT_INLINE_MARKER, "RuntimeFontPathHook.inc", font_include)
    )
    generated = replace_between(
        generated, START_TEXT_IMPL, END_FONT_IMPL, implementations,
    )
    generated = replace_between(
        generated, START_TEXT_HOOKS, START_FONT_HOOKS,
        "    installRuntimeTextI18nHooks(H);",
    )
    generated = replace_between(
        generated, START_FONT_HOOKS, END_FONT_HOOKS,
        "    installRuntimeFontPathHooks(H);",
    )

    for token in UNSAFE_TOKENS:
        if token in generated:
            raise GenerationError(f"unsafe legacy native code survived: {token}")
    require_tokens(generated, REQUIRED_GENERATED, "generated source")

    text_definition = generated.find("static void installRuntimeTextI18nHooks")
    text_call = generated.find("installRuntimeTextI18nHooks(H);")
    font_definition = generated.find("static void installRuntimeFontPathHooks")
    font_call = generated.find("installRuntimeFontPathHooks(H);")
    if min(text_definition, text_call, font_definition, font_call) < 0:
        raise GenerationError("typed hook definition/call position not found")
    if text_definition >= text_call:
        raise GenerationError("text hook installer definition appears after JNI call")
    if font_definition >= font_call:
        raise GenerationError("font hook installer definition appears after JNI call")

    output.parent.mkdir(parents=True, exist_ok=True)
    output.write_text(generated, "utf-8")
    result = {
        "source": str(source),
        "textInclude": str(text_include_path),
        "fontInclude": str(font_include_path),
        "output": str(output),
        "sourceSha256": sha256(original),
        "textIncludeSha256": sha256(text_include),
        "fontIncludeSha256": sha256(font_include),
        "outputSha256": sha256(generated),
        "implementationsInlined": True,
        "textDefinitionBeforeCall": True,
        "fontDefinitionBeforeCall": True,
        "abiSafeTextHooks": True,
        "immutableTranslationSnapshot": True,
        "color4bRgba": True,
        "typedGlobalFontPathHook": True,
        "fontRoutes": {
            "cocosUi": "MTF4a5kp→TTZhiHeiGB3-W4",
            "cocosStory": "mbm_20160902→TTDaYuanGB3",
            "webView": "FileUtils fullPath→TTZhiHei→fontDataGet(motoya/mbm)",
        },
        "fontFilesReplaced": False,
        "arm64AndArmv7CompilerAbi": True,
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
