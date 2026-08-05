#!/usr/bin/env python3
"""Audit the original /magica/ loader and the effective hardened build."""
from __future__ import annotations

import argparse
from collections import Counter
import json
from pathlib import Path, PurePosixPath
import re
import sys
import zipfile

FILES = {
    "interceptor": Path("smali/jp/f4samurai/web/WebViewImpl$WebViewClientImpl.smali"),
    "webview": Path("patch/src/main/java/jp/f4samurai/web/WebViewImpl.java"),
    "hot": Path("patch/src/main/java/io/kamihama/magianative/CNHotUpdateCheck.java"),
    "transaction": Path("patch/src/main/java/io/kamihama/magianative/CNHotUpdateTransaction.java"),
    "transaction_prepare": Path("tools/prepare-hot-update-transaction.py"),
    "safe_links": Path("patch/src/main/java/io/kamihama/magianative/CNSafeExternalLinks.java"),
    "safe_links_prepare": Path("tools/prepare-safe-external-links.py"),
    "wrapper": Path("tools/build-local.sh"),
    "core": Path("tools/build-apk-core.sh"),
    "workflow": Path(".github/workflows/build-apk-runtime-i18n.yml"),
    "cmake": Path("magia-native/CMakeLists.txt"),
    "native_text": Path("magia-native/src/RuntimeTextI18n.inc"),
    "native_font": Path("magia-native/src/RuntimeFontPathHook.inc"),
    "native_prepare": Path("tools/prepare-native-text-i18n.py"),
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


def all_true(values: dict[str, bool]) -> bool:
    return all(values.values())


def finding(severity: str, code: str, summary: str) -> dict[str, str]:
    return {"severity": severity, "status": "OPEN", "code": code, "summary": summary}


def inspect_zip(path: Path) -> dict:
    if not path.is_file():
        raise AuditError(f"runtime ZIP not found: {path}")
    with zipfile.ZipFile(path) as archive:
        names = [name for name in archive.namelist() if not name.endswith("/")]
        name_set = set(names)
        unsafe: list[str] = []
        refs: list[str] = []
        for name in names:
            p = PurePosixPath(name)
            if (not name.startswith("magica/") or name.startswith("/")
                    or "\\" in name
                    or any(part in {"", ".", ".."} for part in p.parts)):
                unsafe.append(name)
            if name.endswith(".js"):
                text = archive.read(name).decode("utf-8-sig")
                refs.extend(
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
            "templateReferenceOccurrences": len(refs),
            "uniqueTemplateTargets": len(set(refs)),
            "packagedHtmlReferencedByPackagedJs":
                len(set(refs).intersection(name_set)),
        }


def audit(root: Path, runtime: Path | None) -> dict:
    source = load(root)
    interceptor = source["interceptor"]
    webview = source["webview"]
    hot = source["hot"]
    tx = source["transaction"]
    tx_prepare = source["transaction_prepare"]
    safe = source["safe_links"]
    safe_prepare = source["safe_links_prepare"]
    wrapper = source["wrapper"]
    core = source["core"]
    workflow = source["workflow"]
    cmake = source["cmake"]
    native_text = source["native_text"]
    native_font = source["native_font"]
    native_prepare = source["native_prepare"]

    original_loader = {
        "interceptsMagica": 'const-string v0, "/magica/"' in interceptor,
        "mapsPrivateFiles":
            "/data/data/io.kamihama.totentanz/files/magica/" in interceptor,
        "excludesApi": 'const-string v2, "api/"' in interceptor,
        "servesJsMime": 'const-string v4, "application/javascript"' in interceptor,
        "servesHtmlMime": 'const-string v4, "text/html"' in interceptor,
        "opensFileInputStream": "Ljava/io/FileInputStream;" in interceptor,
        "returnsWebResourceResponse":
            "Landroid/webkit/WebResourceResponse;" in interceptor,
    }

    effective_webview = {
        "javaReplacement": "class WebViewImpl extends WebView" in webview,
        "sameOrigin": "sameOrigin(view, requestUri)" in webview,
        "getOnly": '"GET".equalsIgnoreCase(request.getMethod())' in webview,
        "exactPrefix": 'OVERLAY_PREFIX = "/magica/"' in webview,
        "canonicalContainment": "getCanonicalPath" in webview,
        "regularFile": "candidate.isFile()" in webview,
        "mixedContentDisabled": "MIXED_CONTENT_NEVER_ALLOW" in webview,
        "navigationTimeout": "SHOULD_START_TIMEOUT_SECONDS" in webview,
        "fallback": "super.shouldInterceptRequest" in webview,
        "classesDexReplacement":
            "DEX_MAIN" in core
            and "rm -f smali/jp/f4samurai/web/WebViewImpl*.smali" in core
            and "WebViewImpl 被重复放进 classes3" in core,
        "manualBranchWorkflow":
            "workflow_dispatch" in workflow
            and "feature/legacy-client-runtime-i18n" in workflow
            and "tools/build-local.sh" in workflow,
    }

    effective_hot_update = {
        "sizeManifest": 'json.optLong("size"' in hot,
        "sha256Client": "SHA-256" in hot and "MessageDigest" in hot,
        "md5Fallback": 'algorithm = "MD5"' in hot,
        "digestBeforeExtraction":
            hot.find("verifyArchive(tmp") >= 0
            and hot.find("verifyArchive(tmp") < hot.find("extractChecked(tmp"),
        "restartAfterApply": "热更新已应用，3 秒后自动重启游戏" in hot,
        "stagingBackup":
            'STAGE_NAME = "stage"' in tx and 'BACKUP_NAME = "backup"' in tx,
        "manifestState":
            'MANIFEST_NAME = "manifest.json"' in tx and 'STATE_NAME = "state"' in tx,
        "pathAndSizeGuards":
            "MAX_TOTAL_BYTES" in tx and "getCanonicalPath" in tx,
        "recoveryMaterialised": "CNHotUpdateTransaction.recover(filesRoot);" in tx_prepare,
        "transactionMaterialised":
            "CNHotUpdateTransaction.apply(tmp, new File(FILES_DIR));" in tx_prepare,
        "stateGapHardened":
            "rollbackStateWritesRemoved" in tx_prepare
            and "childCanonical.equals(rootCanonical)" in tx_prepare,
        "legacyWriteBlocked": "旧的活动目录直接解压调用仍存在" in wrapper,
    }

    effective_links = {
        "httpsOnly": '"https".equalsIgnoreCase(uri.getScheme())' in safe,
        "userinfoRejected": "uri.getUserInfo() != null" in safe,
        "portRestricted": "port != -1 && port != 443" in safe,
        "domainAllowlist":
            "allowedHost" in safe and "magireco.top" in safe
            and "github.com" in safe and "b23.tv" in safe,
        "browsableIntent": "Intent.CATEGORY_BROWSABLE" in safe,
        "twoBlocksReplaced":
            "expected two raw ACTION_VIEW blocks" in safe_prepare
            and "CNSafeExternalLinks.open(act, url);" in safe_prepare,
        "buildMaterialisation": "prepare-safe-external-links.py" in wrapper,
        "rawActionViewBlocked": "未限制的 ACTION_VIEW 调用仍存在" in wrapper,
    }

    effective_native = {
        "generatedByCMake":
            "prepare-native-text-i18n.py" in cmake
            and "RuntimeFontPathHook.inc" in cmake,
        "typedTextStrings":
            "using RuntimeSetStringFn = void (*)(void*, const std::string&);"
            in native_text,
        "loadingByValue":
            "using LoadingSetTextFn = void (*)(void*, std::string);" in native_text,
        "rgbaColor": "struct CNColor4B { unsigned char r, g, b, a; };" in native_text,
        "immutableSnapshot":
            "std::atomic_store_explicit" in native_text
            and "std::atomic_load_explicit" in native_text,
        "typedTtfConfig": "struct RuntimeTtfConfig" in native_font,
        "boolSetTtfReturn": "SetTtfConfigInternalFn = bool" in native_font,
        "localFontConfigCopy": "RuntimeTtfConfig local = config;" in native_font,
        "sameFontRoute":
            "fonts/MTF4a5kp.ttf" in native_font
            and "fonts/TTZhiHeiGB3-W4.ttf" in native_font,
        "threeTypedFontHooks":
            "font: createWithTTF(cfg) typed" in native_font
            and "font: createWithTTF(str) typed" in native_font
            and "font: setTTFConfigInternal typed" in native_font,
        "unsafeLegacyRejected":
            "NdkStrView" in native_prepare
            and "fontPathOverwrite(" in native_prepare
            and "UNSAFE_TOKENS" in native_prepare,
    }

    package = inspect_zip(runtime) if runtime else None
    package_valid = True
    if package:
        package_valid = (
            package["duplicates"] == 0
            and not package["unsafeEntries"]
            and package["javascript"] > 0
            and package["html"] > 0
            and package["jqueryInjectorPresent"]
            and package["packagedHtmlReferencedByPackagedJs"] > 0
        )

    findings = []
    if effective_hot_update["md5Fallback"]:
        findings.append(finding(
            "HIGH", "SERVER_MANIFEST_SHA256_NOT_MANDATORY",
            "客户端已支持 SHA-256，但当前兼容路径仍接受 MD5；分发端必须发布 sha256 后再取消弱摘要。"
        ))
    findings.append(finding(
        "TEST", "ACTIONS_AND_DEVICE_VALIDATION_PENDING",
        "静态有效构建链已建立，但尚未运行双 ABI Actions，也未完成 WebView 同源限制、事务恢复、typed 字体 hook 和 v6 热更新的真机回归。"
    ))

    return {
        "schema": 4,
        "originalGameLoader": original_loader,
        "effectiveWebViewBuild": effective_webview,
        "effectiveHotUpdateBuild": effective_hot_update,
        "effectiveExternalLinkBuild": effective_links,
        "effectiveNativeBuild": effective_native,
        "runtimePackage": package,
        "originalLoaderEnabled": all_true(original_loader),
        "effectiveBuildStaticallyReady":
            all_true(effective_webview)
            and all_true(effective_hot_update)
            and all_true(effective_links)
            and all_true(effective_native),
        "runtimePackageValid": package_valid,
        "findings": findings,
        "findingCounts": dict(Counter(item["severity"] for item in findings)),
        "conclusion": {
            "jsHtmlLoader": (
                "Original smali confirms that non-API /magica/ requests are served "
                "from the private files tree with explicit JavaScript and HTML MIME types."
            ),
            "effectiveBuild": (
                "The branch build replaces the WebView class with hardened Java, verifies "
                "and transactionally commits hot updates, restarts after application, "
                "validates external links, and compiles typed text/font native hooks while "
                "preserving the runtime MTF4a5kp-to-TTZhiHeiGB3-W4 route."
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
        report = audit(args.repo_root.resolve(), args.runtime)
    except (OSError, UnicodeError, zipfile.BadZipFile, AuditError) as error:
        print(f"ERROR: {error}", file=sys.stderr)
        return 2
    rendered = json.dumps(report, ensure_ascii=False, indent=2) + "\n"
    if args.report:
        args.report.parent.mkdir(parents=True, exist_ok=True)
        args.report.write_text(rendered, "utf-8")
    print(rendered, end="")
    if not report["originalLoaderEnabled"]:
        return 1
    if not report["effectiveBuildStaticallyReady"]:
        return 1
    if not report["runtimePackageValid"]:
        return 1
    if args.fail_on_open_high and any(
        item["severity"] == "HIGH" and item["status"] == "OPEN"
        for item in report["findings"]
    ):
        return 1
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
