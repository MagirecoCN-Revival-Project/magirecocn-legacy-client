#!/usr/bin/env python3
"""Audit the original JS/HTML loader and the effective hardened build pipeline."""
from __future__ import annotations

import argparse
from collections import Counter
import json
from pathlib import Path, PurePosixPath
import re
import sys
import zipfile

FILES = {
    # Original runtime evidence: proves the shipped game really intercepts /magica/.
    "interceptor": Path("smali/jp/f4samurai/web/WebViewImpl$WebViewClientImpl.smali"),
    # Effective build inputs.
    "webview_java": Path("patch/src/main/java/jp/f4samurai/web/WebViewImpl.java"),
    "hot": Path("patch/src/main/java/io/kamihama/magianative/CNHotUpdateCheck.java"),
    "transaction": Path("patch/src/main/java/io/kamihama/magianative/CNHotUpdateTransaction.java"),
    "transaction_prepare": Path("tools/prepare-hot-update-transaction.py"),
    "build_wrapper": Path("tools/build-local.sh"),
    "build_core": Path("tools/build-apk-core.sh"),
    "apk_workflow": Path(".github/workflows/build-apk-runtime-i18n.yml"),
    "native_cmake": Path("magia-native/CMakeLists.txt"),
    "native_text": Path("magia-native/src/RuntimeTextI18n.inc"),
    "native_font": Path("magia-native/src/RuntimeFontCompat.inc"),
    "native_prepare": Path("tools/prepare-native-text-i18n.py"),
    "ui": Path("patch/src/main/java/io/kamihama/magianative/CNCNDownloadUI.java"),
}
TEMPLATE_REF = re.compile(r"text!template/([^'\"\\?\s,\)]+\.html)")


class AuditError(RuntimeError):
    pass


def load(root: Path) -> dict[str, str]:
    result: dict[str, str] = {}
    for key, relative in FILES.items():
        path = root / relative
        if not path.is_file():
            raise AuditError(f"missing repository file: {relative}")
        result[key] = path.read_text("utf-8")
    return result


def finding(severity: str, code: str, summary: str,
            status: str = "OPEN") -> dict[str, str]:
    return {
        "severity": severity,
        "code": code,
        "status": status,
        "summary": summary,
    }


def inspect_zip(path: Path) -> dict:
    if not path.is_file():
        raise AuditError(f"runtime ZIP not found: {path}")
    with zipfile.ZipFile(path) as archive:
        names = [name for name in archive.namelist() if not name.endswith("/")]
        name_set = set(names)
        unsafe = []
        references = []
        for name in names:
            posix = PurePosixPath(name)
            if (not name.startswith("magica/") or name.startswith("/")
                    or "\\" in name
                    or any(part in {"", ".", ".."} for part in posix.parts)):
                unsafe.append(name)
            if name.endswith(".js"):
                text = archive.read(name).decode("utf-8-sig")
                references.extend(
                    "magica/template/" + match.group(1)
                    for match in TEMPLATE_REF.finditer(text)
                )
        suffixes = Counter(PurePosixPath(name).suffix.lower() for name in names)
        return {
            "path": str(path),
            "entries": len(names),
            "javascript": suffixes[".js"],
            "html": suffixes[".html"],
            "json": suffixes[".json"],
            "duplicates": len(names) - len(name_set),
            "unsafeEntries": unsafe,
            "jqueryInjectorPresent":
                "magica/js/libs/jquery-3.7.1.min.js" in name_set,
            "templateReferenceOccurrences": len(references),
            "uniqueTemplateTargets": len(set(references)),
            "packagedHtmlReferencedByPackagedJs":
                len(set(references).intersection(name_set)),
        }


def audit(root: Path, runtime: Path | None) -> dict:
    source = load(root)
    interceptor = source["interceptor"]
    webview = source["webview_java"]
    hot = source["hot"]
    transaction = source["transaction"]
    tx_prepare = source["transaction_prepare"]
    wrapper = source["build_wrapper"]
    core = source["build_core"]
    workflow = source["apk_workflow"]
    cmake = source["native_cmake"]
    native_text = source["native_text"]
    native_font = source["native_font"]
    native_prepare = source["native_prepare"]
    ui = source["ui"]

    original_loader = {
        "interceptsMagica": 'const-string v0, "/magica/"' in interceptor,
        "mapsToPrivateFilesTree":
            "/data/data/io.kamihama.totentanz/files/magica/" in interceptor,
        "excludesApi": 'const-string v2, "api/"' in interceptor,
        "servesJavascriptMime":
            'const-string v4, "application/javascript"' in interceptor,
        "servesHtmlMime": 'const-string v4, "text/html"' in interceptor,
        "opensLocalFile": "Ljava/io/FileInputStream;" in interceptor,
        "returnsWebResourceResponse":
            "Landroid/webkit/WebResourceResponse;" in interceptor,
    }

    effective_webview = {
        "javaReplacementExists": "class WebViewImpl extends WebView" in webview,
        "sameOriginRequired": "sameOrigin(view, requestUri)" in webview,
        "getOnlyModernRequests":
            '"GET".equalsIgnoreCase(request.getMethod())' in webview,
        "exactMagicaPathPrefix": 'OVERLAY_PREFIX = "/magica/"' in webview,
        "canonicalContainment": "getCanonicalPath" in webview,
        "regularFileRequired": "candidate.isFile()" in webview,
        "mixedContentDisabled": "MIXED_CONTENT_NEVER_ALLOW" in webview,
        "navigationTimeout": "SHOULD_START_TIMEOUT_SECONDS" in webview,
        "networkFallbackPreserved": "super.shouldInterceptRequest" in webview,
        "replacesOriginalClassesDex":
            "DEX_MAIN" in core
            and "rm -f smali/jp/f4samurai/web/WebViewImpl*.smali" in core
            and "WebViewImpl 被重复放进 classes3" in core,
        "branchOnlyManualWorkflow":
            "workflow_dispatch" in workflow
            and "feature/legacy-client-runtime-i18n" in workflow
            and "tools/build-local.sh" in workflow,
    }

    hot_update = {
        "manifestLengthParsed": "json.optLong(\"size\"" in hot,
        "sha256Supported": "SHA-256" in hot and "MessageDigest" in hot,
        "md5CompatibilityFallback": 'algorithm = "MD5"' in hot,
        "digestBeforeApplication":
            hot.find("verifyArchive(tmp") >= 0
            and hot.find("verifyArchive(tmp") < hot.find("extractChecked(tmp"),
        "restartAfterAppliedUpdate":
            "热更新已应用，3 秒后自动重启游戏" in hot,
        "transactionClassPresent": "class CNHotUpdateTransaction" in transaction,
        "stagingAndBackup":
            'STAGE_NAME = "stage"' in transaction
            and 'BACKUP_NAME = "backup"' in transaction,
        "persistentManifestAndState":
            'MANIFEST_NAME = "manifest.json"' in transaction
            and 'STATE_NAME = "state"' in transaction,
        "recoveryMaterialised":
            "CNHotUpdateTransaction.recover(filesRoot);" in tx_prepare,
        "transactionApplyMaterialised":
            "CNHotUpdateTransaction.apply(tmp, new File(FILES_DIR));" in tx_prepare,
        "legacyLiveWriteRejectedByBuild":
            "旧的活动目录直接解压调用仍存在" in wrapper,
        "rollbackStateGapRemoved":
            "rollbackStateWritesRemoved" in tx_prepare
            and "childCanonical.equals(rootCanonical)" in tx_prepare,
    }

    native_effective = {
        "generatedByCMake":
            "prepare-native-text-i18n.py" in cmake
            and "GENERATED_CPP" in cmake,
        "typedStdStringHooks":
            "using RuntimeSetStringFn = void (*)(void*, const std::string&);"
            in native_text,
        "loadingTextByValue":
            "using LoadingSetTextFn = void (*)(void*, std::string);"
            in native_text,
        "color4bRgba":
            "struct CNColor4B { unsigned char r, g, b, a; };" in native_text,
        "immutableSnapshot":
            "std::atomic_store_explicit" in native_text
            and "std::atomic_load_explicit" in native_text,
        "unsafeTextTokensRejected": "UNSAFE_TEXT_TOKENS" in native_prepare,
        "verifiedFontHooksRequired":
            "REQUIRED_FONT" in native_prepare
            and "fonts/MTF4a5kp.ttf" in native_prepare
            and "fonts/TTZhiHeiGB3-W4.ttf" in native_prepare,
        "fontCompatibilityRiskDeclared":
            "armeabi-v7a" in native_font
            and "arm64" in native_font,
    }

    package = inspect_zip(runtime) if runtime else None
    loader_enabled = all(original_loader.values())
    effective_build_ready = (
        all(effective_webview.values())
        and all(hot_update.values())
        and all(native_effective.values())
    )
    package_valid = True
    if package:
        package_valid = (
            not package["unsafeEntries"]
            and package["duplicates"] == 0
            and package["javascript"] > 0
            and package["html"] > 0
            and package["jqueryInjectorPresent"]
            and package["packagedHtmlReferencedByPackagedJs"] > 0
        )

    findings = []
    if hot_update["md5CompatibilityFallback"]:
        findings.append(finding(
            "HIGH", "SERVER_MANIFEST_SHA256_NOT_MANDATORY",
            "客户端支持 SHA-256，但当前兼容路径仍接受 MD5；分发端必须发布 sha256 后才能取消弱摘要。"
        ))
    if native_effective["fontCompatibilityRiskDeclared"]:
        findings.append(finding(
            "HIGH", "ARMV7_FONT_PATH_LAYOUT_NOT_DEVICE_VERIFIED",
            "文本 hook 已双 ABI 安全，但保留的成功字体路径 hook 仍使用 arm64 libc++ 视图；armv7 必须单独构建和真机验证。"
        ))
    if "new Intent(Intent.ACTION_VIEW, Uri.parse(url))" in ui:
        findings.append(finding(
            "MEDIUM", "REMOTE_ACTION_VIEW_URL_NOT_RESTRICTED",
            "云端署名/更新地址仍可直接触发任意 URI scheme，应限制为 HTTPS 和允许域名。"
        ))
    findings.append(finding(
        "TEST", "ACTIONS_AND_DEVICE_VALIDATION_PENDING",
        "有效构建链已建立，但尚未实际运行双 ABI Actions，也未完成 v6 热更新、WebView 同源约束和 mixed-content 的真机回归。"
    ))

    return {
        "schema": 2,
        "originalGameLoader": original_loader,
        "effectiveWebViewBuild": effective_webview,
        "effectiveHotUpdateBuild": hot_update,
        "effectiveNativeTextBuild": native_effective,
        "runtimePackage": package,
        "originalLoaderEnabled": loader_enabled,
        "effectiveBuildStaticallyReady": effective_build_ready,
        "runtimePackageValid": package_valid,
        "findings": findings,
        "findingCounts": dict(Counter(item["severity"] for item in findings)),
        "conclusion": {
            "jsHtmlLoader": (
                "Confirmed in original smali: non-API /magica/ requests are served "
                "from the app-private files tree with explicit JS and HTML MIME types."
            ),
            "effectiveBuild": (
                "The branch build replaces the original WebView class with hardened Java, "
                "verifies archive length/digest, transactionally commits files, restarts "
                "after application, and compiles ABI-safe native text hooks while preserving "
                "the verified global font-path hook."
            ),
        },
    }


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--repo-root", type=Path, default=Path("."))
    parser.add_argument("--runtime", type=Path)
    parser.add_argument("--report", type=Path)
    parser.add_argument("--fail-on-open-high", action="store_true")
    args = parser.parse_args()
    try:
        result = audit(args.repo_root.resolve(), args.runtime)
    except (OSError, UnicodeError, zipfile.BadZipFile, AuditError) as error:
        print(f"ERROR: {error}", file=sys.stderr)
        return 2
    rendered = json.dumps(result, ensure_ascii=False, indent=2) + "\n"
    if args.report:
        args.report.parent.mkdir(parents=True, exist_ok=True)
        args.report.write_text(rendered, "utf-8")
    print(rendered, end="")
    if not result["originalLoaderEnabled"]:
        return 1
    if not result["effectiveBuildStaticallyReady"]:
        return 1
    if not result["runtimePackageValid"]:
        return 1
    if args.fail_on_open_high and any(
        item["severity"] == "HIGH" and item["status"] == "OPEN"
        for item in result["findings"]
    ):
        return 1
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
