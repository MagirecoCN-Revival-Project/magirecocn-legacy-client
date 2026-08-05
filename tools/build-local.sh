#!/usr/bin/env bash
# latest-main 基线 + 六层 Java 物化 + 国服字体恢复/守卫；APK 构建由 build-apk-core.sh 完成。
set -euo pipefail

REPO="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
OUT="${1:-${REPO}/.build-local}"
mkdir -p "$OUT"
OUT="$(cd "$OUT" && pwd)"
SOURCE_ROOT="$REPO/patch/src/main/java"
GENERATED="$OUT/generated-java"
BACKUP="$OUT/committed-java-backup"
MAIN_REPORT="$OUT/main-runtime-baseline.json"
FONT_PREP_REPORT="$OUT/authoritative-cn-fonts-materialisation.json"
FONT_REPORT="$OUT/authoritative-cn-fonts.json"
TEXT_REPORT="$OUT/downloader-ui-text-materialisation.json"
HARDEN_REPORT="$OUT/main-hot-update-hardening.json"
TX_REPORT="$OUT/hot-update-transaction-materialisation.json"
LINK_REPORT="$OUT/safe-external-links-materialisation.json"
PROBE_REPORT="$OUT/webview-overlay-probe-materialisation.json"

MAIN_SHA="${PINNED_MAIN_SHA:-}"
if [ -z "$MAIN_SHA" ]; then
    MAIN_SHA="$(git -C "$REPO" rev-parse refs/remotes/origin/main 2>/dev/null || true)"
fi
if ! printf '%s' "$MAIN_SHA" | grep -Eq '^[0-9a-f]{40}$'; then
    echo '✘ 无法确定有效的 latest-main 提交 SHA' >&2
    exit 1
fi
if [ "${REQUIRE_ORIGIN_MAIN:-0}" = "1" ]; then
    REMOTE_MAIN="$(git -C "$REPO" rev-parse refs/remotes/origin/main)"
    if [ "$MAIN_SHA" != "$REMOTE_MAIN" ]; then
        echo "✘ main 基线不是当前 origin/main：$MAIN_SHA != $REMOTE_MAIN" >&2
        exit 1
    fi
    if ! git -C "$REPO" merge-base --is-ancestor "$REMOTE_MAIN" HEAD; then
        echo "✘ 当前 feature 尚未纳入 latest main：$REMOTE_MAIN" >&2
        exit 1
    fi
fi
printf 'latest-main baseline: %s\n' "$MAIN_SHA"

# 仓库历史中两个日服源字体名曾被错误写入其他字体字节。构建环境必须提供
# 项目方上传的国服 2.2.1 APK 解包目录或 7z；物化器按原文件名和精确哈希恢复，
# 绝不以复制目标字体到源字体名的方式“修复”。已经恢复过且四文件均通过哈希时，
# 可不再设置 AUTHORITATIVE_CN_FONT_SOURCE。
if [ -n "${AUTHORITATIVE_CN_FONT_SOURCE:-}" ]; then
    python3 "$REPO/tools/prepare-authoritative-cn-font-assets.py" \
        --source "$AUTHORITATIVE_CN_FONT_SOURCE" \
        --font-root "$REPO/assets/fonts" \
        --report "$FONT_PREP_REPORT"
fi
python3 "$REPO/tools/check-authoritative-cn-fonts.py" \
    --font-root "$REPO/assets/fonts" \
    --report "$FONT_REPORT"

restore_sources() {
    if [ -d "$BACKUP" ]; then
        rm -rf "$SOURCE_ROOT"
        mv "$BACKUP" "$SOURCE_ROOT"
    fi
}
trap restore_sources EXIT INT TERM

rm -rf "$GENERATED" "$BACKUP"

# 在当前进程内建立临时源树。main 只读，任何时候都不写 main。
mv "$SOURCE_ROOT" "$BACKUP"
cp -a "$BACKUP" "$SOURCE_ROOT"

BASELINE_ARGS=(
    --git-root "$REPO"
    --java-root "$SOURCE_ROOT"
    --main-sha "$MAIN_SHA"
    --report "$MAIN_REPORT"
)
if [ "${REQUIRE_ORIGIN_MAIN:-0}" = "1" ]; then
    BASELINE_ARGS+=(--require-origin-main)
fi
python3 "$REPO/tools/prepare-main-runtime-baseline.py" "${BASELINE_ARGS[@]}"

python3 "$REPO/tools/prepare-changed-downloader-ui-text.py" \
    --table "$REPO/i18n/cn-downloader-ui-text.tsv" \
    --repo-root "$REPO" \
    --output-root "$GENERATED" \
    --report "$TEXT_REPORT"

python3 "$REPO/tools/prepare-main-hot-update-hardening.py" \
    --java-root "$GENERATED" \
    --report "$HARDEN_REPORT"
python3 "$REPO/tools/prepare-hot-update-transaction.py" \
    --java-root "$GENERATED" \
    --report "$TX_REPORT"
python3 "$REPO/tools/prepare-safe-external-links.py" \
    --java-root "$GENERATED" \
    --report "$LINK_REPORT"
python3 "$REPO/tools/prepare-webview-overlay-probe.py" \
    --java-root "$GENERATED" \
    --report "$PROBE_REPORT"

CHECK="$GENERATED/io/kamihama/magianative/CNHotUpdateCheck.java"
UI="$GENERATED/io/kamihama/magianative/CNCNDownloadUI.java"
WEBVIEW="$GENERATED/jp/f4samurai/web/WebViewImpl.java"
CHUNK="$GENERATED/io/kamihama/magianative/CNChunkedDownload.java"
MIRRORS="$GENERATED/io/kamihama/magianative/CNMirrors.java"

grep -q 'chunks_across_mirrors' "$CHUNK"
grep -q 'keep-alive' "$CHUNK"
grep -q 'mirror_race' "$MIRRORS"
grep -q 'java.util.LinkedHashMap<Integer, java.util.concurrent.Future<Boolean>>' "$CHECK"
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

rm -rf "$SOURCE_ROOT"
cp -a "$GENERATED" "$SOURCE_ROOT"

bash "$REPO/tools/build-apk-core.sh" "$OUT"
