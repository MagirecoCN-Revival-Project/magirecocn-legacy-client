#!/usr/bin/env bash
# Java 物化包装器；实际 APK 构建统一由 build-apk-core.sh 完成。
set -euo pipefail

REPO="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
OUT="${1:-${REPO}/.build-local}"
mkdir -p "$OUT"
OUT="$(cd "$OUT" && pwd)"
SOURCE_ROOT="$REPO/patch/src/main/java"
GENERATED="$OUT/generated-java"
BACKUP="$OUT/committed-java-backup"
TEXT_REPORT="$OUT/downloader-ui-text-materialisation.json"
TX_REPORT="$OUT/hot-update-transaction-materialisation.json"

restore_sources() {
    if [ -d "$BACKUP" ]; then
        rm -rf "$SOURCE_ROOT"
        mv "$BACKUP" "$SOURCE_ROOT"
    fi
}
trap restore_sources EXIT INT TERM

rm -rf "$GENERATED" "$BACKUP"
python3 "$REPO/tools/prepare-changed-downloader-ui-text.py" \
    --table "$REPO/i18n/cn-downloader-ui-text.tsv" \
    --repo-root "$REPO" \
    --output-root "$GENERATED" \
    --report "$TEXT_REPORT"

python3 "$REPO/tools/prepare-hot-update-transaction.py" \
    --java-root "$GENERATED" \
    --report "$TX_REPORT"

grep -q 'CNHotUpdateTransaction.recover(filesRoot);' \
    "$GENERATED/io/kamihama/magianative/CNHotUpdateCheck.java"
grep -q 'CNHotUpdateTransaction.apply(tmp, new File(FILES_DIR));' \
    "$GENERATED/io/kamihama/magianative/CNHotUpdateCheck.java"
if grep -q 'CNDownloaderFix.extractChecked(tmp, new File(FILES_DIR));' \
        "$GENERATED/io/kamihama/magianative/CNHotUpdateCheck.java"; then
    echo '✘ 旧的活动目录直接解压调用仍存在' >&2
    exit 1
fi

# 核心构建脚本仍从 patch/src/main/java 取事实源。只在当前构建进程内临时换成
# 两层物化后的 Java 树，退出（成功或失败）都由 trap 恢复提交源码。
mv "$SOURCE_ROOT" "$BACKUP"
cp -a "$GENERATED" "$SOURCE_ROOT"

bash "$REPO/tools/build-apk-core.sh" "$OUT"
