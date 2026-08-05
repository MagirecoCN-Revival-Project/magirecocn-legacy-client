#!/usr/bin/env python3
"""Static audit of WebView JS/HTML overlay activation and high-risk defects."""
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
    "webview": Path("smali/jp/f4samurai/web/WebViewImpl.smali"),
    "hot": Path("patch/src/main/java/io/kamihama/magianative/CNHotUpdateCheck.java"),
    "downloader": Path("patch/src/main/java/io/kamihama/magianative/CNDownloaderFix.java"),
    "native": Path("magia-native/src/MagiaLegacy.cpp"),
    "workflow": Path(".github/workflows/build-apk.yml"),
}
TEMPLATE_REF = re.compile(r"text!template/([^'\"\\?\s,\)]+\.html)")


def load(root: Path) -> dict[str, str]:
    out = {}
    for key, rel in FILES.items():
        path = root / rel
        if not path.is_file():
            raise FileNotFoundError(rel)
        out[key] = path.read_text("utf-8")
    return out


def finding(severity: str, code: str, summary: str) -> dict[str, str]:
    return {"severity": severity, "code": code, "summary": summary}


def inspect_zip(path: Path) -> dict:
    with zipfile.ZipFile(path) as zf:
        names = [n for n in zf.namelist() if not n.endswith("/")]
        refs = []
        for name in (n for n in names if n.endswith(".js")):
            text = zf.read(name).decode("utf-8-sig")
            refs.extend("magica/template/" + m.group(1)
                        for m in TEMPLATE_REF.finditer(text))
        name_set = set(names)
        unsafe = []
        for name in names:
            p = PurePosixPath(name)
            if (not name.startswith("magica/") or name.startswith("/")
                    or "\\" in name or any(x in {"", ".", ".."} for x in p.parts)):
                unsafe.append(name)
        suffix = Counter(PurePosixPath(n).suffix.lower() for n in names)
        return {
            "entries": len(names),
            "javascript": suffix[".js"],
            "html": suffix[".html"],
            "json": suffix[".json"],
            "duplicates": len(names) - len(name_set),
            "unsafeEntries": unsafe,
            "jqueryInjectorPresent": "magica/js/libs/jquery-3.7.1.min.js" in name_set,
            "templateReferences": len(refs),
            "packagedHtmlReferencedByPackagedJs": len(set(refs) & name_set),
        }


def audit(root: Path, runtime: Path | None) -> dict:
    src = load(root)
    i, w, h, d, n, workflow = (src[k] for k in
        ("interceptor", "webview", "hot", "downloader", "native", "workflow"))
    checks = {
        "interceptsMagica": 'const-string v0, "/magica/"' in i,
        "mapsFilesRoot": "/data/data/io.kamihama.totentanz/files/magica/" in i,
        "excludesApi": 'const-string v2, "api/"' in i,
        "servesJs": 'const-string v4, "application/javascript"' in i,
        "servesHtml": 'const-string v4, "text/html"' in i,
        "opensLocalFile": "Ljava/io/FileInputStream;" in i,
        "extractsHotUpdateToFiles":
            "CNDownloaderFix.extractChecked(tmp, new File(FILES_DIR))" in h,
    }
    findings = []
    if "getCanonicalPath" not in i:
        findings.append(finding("HIGH", "WEBVIEW_LOCAL_PATH_NOT_CONTAINED",
            "URL path is concatenated to files/magica without canonical containment."))
    if "getHost" not in i and "getScheme" not in i:
        findings.append(finding("HIGH", "WEBVIEW_OVERLAY_NOT_ORIGIN_SCOPED",
            "Any URL containing /magica/ can enter the private-file interceptor."))
    if "setMixedContentMode(I)V" in w and "const/4 v0, 0x0" in w:
        findings.append(finding("MEDIUM", "WEBVIEW_MIXED_CONTENT_ALWAYS_ALLOW",
            "Production WebView allows mixed HTTP content."))
    if "clearCache" not in h and "noticeAndRestart" not in h:
        findings.append(finding("HIGH", "HOT_UPDATE_ACTIVATION_NOT_GUARANTEED",
            "JS/HTML is overwritten without WebView cache/module reset or restart."))
    if not any(x in h for x in ("MessageDigest", "SHA-256", "sha256", "MD5", "md5")):
        findings.append(finding("CRITICAL", "HOT_UPDATE_CODE_HAS_NO_CONTENT_DIGEST",
            "Executable JS/HTML archive is not cryptographically verified before extraction."))
    if "new FileOutputStream(out" in d and ".cn-stage" not in d:
        findings.append(finding("HIGH", "HOT_UPDATE_WRITES_LIVE_TREE_IN_PLACE",
            "ZIP entries overwrite the live frontend tree one by one without transaction/rollback."))
    if "prepare-cn-downloader-ui-text.py" not in workflow:
        findings.append(finding("MEDIUM", "DOWNLOADER_TEXT_TABLE_NOT_USED_BY_APK_BUILD",
            "Exported Java UI translations are not materialised before javac."))
    if "struct NdkStrView" in n and "armeabi-v7a" in workflow:
        findings.append(finding("CRITICAL", "NATIVE_STD_STRING_LAYOUT_IS_ARM64_SPECIFIC",
            "Text hook manually decodes arm64 libc++ string layout but APK builds armv7 too."))
    if "struct CNColor4B { unsigned char r, g, b; };" in n:
        findings.append(finding("CRITICAL", "NATIVE_COLOR4B_ABI_MISSING_ALPHA",
            "LbUtility hook uses a 3-byte surrogate for the 4-byte RGBA Color4B."))
    if "g_engineI18n.swap(fresh)" in n:
        region = n.split("static void loadEngineI18n", 1)[1].split("// ─── JNI_OnLoad", 1)[0]
        if "std::lock_guard" not in region and "atomic_store" not in region:
            findings.append(finding("CRITICAL", "NATIVE_TRANSLATION_TABLE_DATA_RACE",
                "Rendering hooks can read containers while the mtime reload mutates them."))

    package = inspect_zip(runtime) if runtime else None
    enabled = all(checks.values())
    if package:
        enabled = enabled and not package["unsafeEntries"] and not package["duplicates"]
        enabled = enabled and package["javascript"] > 0 and package["html"] > 0
        enabled = enabled and package["jqueryInjectorPresent"]
        enabled = enabled and package["packagedHtmlReferencedByPackagedJs"] > 0
    return {
        "schema": 1,
        "runtimeOverlayLoaderEnabled": enabled,
        "loaderChecks": checks,
        "runtimePackage": package,
        "findings": findings,
        "findingCounts": dict(Counter(x["severity"] for x in findings)),
        "activationConclusion": (
            "The loader is enabled, but same-process activation is not guaranteed until "
            "cache/module lifetime, digest verification and atomic deployment are fixed."
        ),
    }


def main() -> int:
    p = argparse.ArgumentParser()
    p.add_argument("--repo-root", type=Path, default=Path("."))
    p.add_argument("--runtime", type=Path)
    p.add_argument("--report", type=Path)
    p.add_argument("--fail-on-high", action="store_true")
    a = p.parse_args()
    try:
        result = audit(a.repo_root.resolve(), a.runtime)
    except (OSError, UnicodeError, zipfile.BadZipFile) as exc:
        print(f"ERROR: {exc}", file=sys.stderr)
        return 2
    text = json.dumps(result, ensure_ascii=False, indent=2) + "\n"
    if a.report:
        a.report.parent.mkdir(parents=True, exist_ok=True)
        a.report.write_text(text, "utf-8")
    print(text, end="")
    if not result["runtimeOverlayLoaderEnabled"]:
        return 1
    if a.fail_on_high and any(x["severity"] in {"HIGH", "CRITICAL"}
                              for x in result["findings"]):
        return 1
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
