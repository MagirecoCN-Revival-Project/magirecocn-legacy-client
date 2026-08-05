#!/usr/bin/env bash
# 固定 latest-main 基线 + 六层 Java 物化；实际 APK 构建由 build-apk-core.sh 完成。
set -euo pipefail

REPO="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
OUT="${1:-${REPO}/.build-local}"
mkdir -p "$OUT"
OUT="$(cd "$OUT" && pwd)"
SOURCE_ROOT="$REPO/patch/src/main/java"
GENERATED="$OUT/generated-java"
BACKUP="$OUT/committed-java-backup"
MAIN_REPORT="$OUT/main-runtime-baseline.json"
TEXT_REPORT="$OUT/downloader-ui-text-materialisation.json"
HARDEN_REPORT="$OUT/main-hot-update-hardening.json"
TX_REPORT="$OUT/hot-update-transaction-materialisation.json"
LINK_REPORT="$OUT/safe-external-links-materialisation.json"
PROBE_REPORT="$OUT/webview-overlay-probe-materialisation.json"
PINNED_MAIN_SHA="d33561a24233e27b6e592d08bfb398c94c87329f"

restore_sources() {
    if [ -d "$BACKUP" ]; then
        rm -rf "$SOURCE_ROOT"
        mv "$BACKUP" "$SOURCE_ROOT"
    fi
}
trap restore_sources EXIT INT TERM

rm -rf "$GENERATED" "$BACKUP"

# 在当前进程内建立临时源树。提交到 feature 分支的 Java 文件保持可审计原样；
# main 只读，任何时候都不写 main。
mv "$SOURCE_ROOT" "$BACKUP"
cp -a "$BACKUP" "$SOURCE_ROOT"

BASELINE_ARGS=(
    --git-root "$REPO"
    --java-root "$SOURCE_ROOT"
    --main-sha "$PINNED_MAIN_SHA"
    --report "$MAIN_REPORT"
)
if [ "${REQUIRE_ORIGIN_MAIN:-0}" = "1" ]; then
    BASELINE_ARGS+=(--require-origin-main)
fi
python3 "$REPO/tools/prepare-main-runtime-baseline.py" "${BASELINE_ARGS[@]}"

# 第一层：仅把 TSV 中 source != zh_CN 的可见文本物化为常量。
python3 "$REPO/tools/prepare-changed-downloader-ui-text.py" \
    --table "$REPO/i18n/cn-downloader-ui-text.tsv" \
    --repo-root "$REPO" \
    --output-root "$GENERATED" \
    --report "$TEXT_REPORT"

# 第二层：在 latest-main 的并行热更新实现上叠加 SHA-256 与安全重启。
python3 "$REPO/tools/prepare-main-hot-update-hardening.py" \
    --java-root "$GENERATED" \
    --report "$HARDEN_REPORT"

# 第三层：活动前端树改为可恢复事务提交。
python3 "$REPO/tools/prepare-hot-update-transaction.py" \
    --java-root "$GENERATED" \
    --report "$TX_REPORT"

# 第四层：云端配置外链限制为 HTTPS 项目域名白名单。
python3 "$REPO/tools/prepare-safe-external-links.py" \
    --java-root "$GENERATED" \
    --report "$LINK_REPORT"

# 第五层：记录 JS/HTML 本地命中并查询 window 执行标记。
python3 "$REPO/tools/prepare-webview-overlay-probe.py" \
    --java-root "$GENERATED" \
    --report "$PROBE_REPORT"

CHECK="$GENERATED/io/kamihama/magianative/CNHotUpdateCheck.java"
UI="$GENERATED/io/kamihama/magianative/CNCNDownloadUI.java"
WEBVIEW="$GENERATED/jp/f4samurai/web/WebViewImpl.java"
CHUNK="$GENERATED/io/kamihama/magianative/CNChunkedDownload.java"
MIRRORS="$GENERATED/io/kamihama/magianative/CNMirrors.java"

# latest-main 性能基线必须真实进入最终树。
grep -q 'chunks_across_mirrors' "$CHUNK"
grep -q 'keep-alive' "$CHUNK"
grep -q 'mirror_race' "$MIRRORS"
grep -q 'java.util.LinkedHashMap<Integer, java.util.concurrent.Future<Boolean>>' "$CHECK"

# 本分支安全增强必须在性能基线上继续成立。
grep -q 'o.optString("sha256", "")' "$CHECK"
grep -q 'algorithm = "SHA-256"' "$CHECK"
grep -q '热更新已应用，3 秒后自动重启游戏' "$CHECK"
grep -q 'CNHotUpdateTransaction.recover(filesRoot);' "$CHECK"
grep -q 'CNHotUpdateTransaction.apply(tmp, new File(FILES_DIR));' "$CHECK"
if grep -q 'CNDownloaderFix.extractChecked(tmp, new File(FILES_DIR));' "$CHECK"; then
    echo '✘ 旧的活动目录直接解压调用仍存在' >&2
    exit 1
fi

grep -q 'CNSafeExternalLinks.open(act, url);' "$UI"
if grep -q 'new Intent(Intent.ACTION_VIEW, Uri.parse(url))' "$UI"; then
    echo '✘ 未限制的 ACTION_VIEW 调用仍存在' >&2
    exit 1
fi
grep -q 'RuntimeOverlayProbe.onLocalFile(candidatePath, mime);' "$WEBVIEW"
grep -q 'RuntimeOverlayProbe.onPageFinished(view, url);' "$WEBVIEW"

# build-apk-core.sh 只读固定路径，因此用最终物化树替换临时 source root。
rm -rf "$SOURCE_ROOT"
cp -a "$GENERATED" "$SOURCE_ROOT"

bash "$REPO/tools/build-apk-core.sh" "$OUT"
