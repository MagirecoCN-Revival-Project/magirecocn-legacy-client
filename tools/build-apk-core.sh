#!/usr/bin/env bash
# 本地出包：native → Java 三组 dex → apktool → zipalign → 签名 → 自检。
set -euo pipefail

REPO="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
OUT="${1:-${REPO}/.build-local}"
cd "$REPO"

: "${NDK:?请设置 NDK}"
: "${BUILD_TOOLS:?请设置 BUILD_TOOLS}"
: "${APKTOOL_JAR:?请设置 APKTOOL_JAR}"
: "${BAKSMALI_JAR:?请设置 BAKSMALI_JAR}"
: "${DEPS_DIR:?请设置 DEPS_DIR}"
: "${SIGN_KEY:?请设置 SIGN_KEY}"
: "${SIGN_CERT:?请设置 SIGN_CERT}"

ABIS=(arm64-v8a armeabi-v7a)
say() { printf '\n\033[1m== %s ==\033[0m\n' "$*"; }

say "编译 native（${#ABIS[@]} 个 ABI）"
for abi in "${ABIS[@]}"; do
    bdir="magia-native/build/$abi"
    if [ ! -f "$bdir/CMakeCache.txt" ]; then
        cmake -G Ninja \
              -DCMAKE_TOOLCHAIN_FILE="$NDK/build/cmake/android.toolchain.cmake" \
              -DANDROID_ABI="$abi" -DANDROID_PLATFORM=android-21 \
              -B "$bdir" magia-native
    fi
    cmake --build "$bdir"
done

say "把 native 产物拷进 lib/ 并校验"
for abi in "${ABIS[@]}"; do
    src="magia-native/build/$abi/libMagiaLegacy.so"
    cp "$src" "lib/$abi/libMagiaLegacy.so"
    cmp -s "$src" "lib/$abi/libMagiaLegacy.so" \
        || { echo "✘ $abi/libMagiaLegacy.so 不一致"; exit 1; }
    src_sh="magia-native/build/$abi/_deps/shadowhook-build/libshadowhook.so"
    if [ -f "$src_sh" ]; then
        cp "$src_sh" "lib/$abi/libshadowhook.so"
        cmp -s "$src_sh" "lib/$abi/libshadowhook.so" \
            || { echo "✘ $abi/libshadowhook.so 不一致"; exit 1; }
    fi
    echo "  ✔ $abi"
done

say "编译 Java 补丁源码"
rm -rf "$OUT/classes" "$OUT/stub-classes" "$OUT/stubs.jar" \
       "$OUT/dexmain" "$OUT/dexui" "$OUT/dex3" \
       "$OUT/smalimain" "$OUT/smaliui" "$OUT/smali3" "$OUT/stubs"
mkdir -p "$OUT/classes" "$OUT/stub-classes" \
         "$OUT/dexmain" "$OUT/dexui" "$OUT/dex3" \
         "$OUT/stubs/io/kamihama/magianative" \
         "$OUT/stubs/jp/f4samurai/web" "$OUT/stubs/jp/f4samurai"

# 编译期桩只为 javac/d8 提供原 APK 类签名，单独打成 classpath jar，绝不进入 dex。
cat > "$OUT/stubs/io/kamihama/magianative/RestClient.java" <<'STUB'
package io.kamihama.magianative;
import android.app.Activity;
public class RestClient {
    public static Activity getCurrentActivity() { return null; }
    public static void restartApp() {}
}
STUB
cat > "$OUT/stubs/jp/f4samurai/AppActivity.java" <<'STUB'
package jp.f4samurai;
public class AppActivity extends android.app.Activity {
    public void runOnGLThread(Runnable runnable) {}
}
STUB
cat > "$OUT/stubs/jp/f4samurai/web/WebViewHelper.java" <<'STUB'
package jp.f4samurai.web;
public final class WebViewHelper {
    public static void _onJsCallback(String value) {}
    public static void _didFinishLoading(String url) {}
    public static void _didFailLoading(String url, int code) {}
    public static boolean _shouldStartLoading(String url) { return true; }
}
STUB
cat > "$OUT/stubs/jp/f4samurai/web/ShouldStartLoadingWorker.java" <<'STUB'
package jp.f4samurai.web;
import java.util.concurrent.CountDownLatch;
class ShouldStartLoadingWorker implements Runnable {
    ShouldStartLoadingWorker(CountDownLatch latch, boolean[] result, String url) {}
    public void run() {}
}
STUB

CP="$DEPS_DIR/android.jar:$(ls "$DEPS_DIR"/okhttp-*.jar):$(ls "$DEPS_DIR"/okio-*.jar)"
mapfile -t STUB_SRC < <(find "$OUT/stubs" -name '*.java' | sort)
javac -nowarn -source 8 -target 8 -encoding UTF-8 -cp "$CP" \
      -d "$OUT/stub-classes" "${STUB_SRC[@]}"
jar cf "$OUT/stubs.jar" -C "$OUT/stub-classes" .

mapfile -t SRC < <(find patch/src/main/java -name '*.java' | sort)
javac -nowarn -source 8 -target 8 -encoding UTF-8 \
      -cp "$CP:$OUT/stubs.jar" -d "$OUT/classes" "${SRC[@]}"

# classes.dex：重建游戏原有 WebViewImpl 及其内部类，并把启动期探针放在同一 dex。
# classes2.dex：下载浮层。classes3.dex：其余 Java 补丁。
mapfile -t DEX_MAIN < <(find "$OUT/classes/jp/f4samurai/web" \
    \( -name 'WebViewImpl*.class' -o -name 'RuntimeOverlayProbe*.class' \) | sort)
mapfile -t DEX_UI < <(find "$OUT/classes" -name 'CNCNDownloadUI*.class' | sort)
mapfile -t DEX_3 < <(find "$OUT/classes" -name '*.class' \
    ! -name 'WebViewImpl*.class' ! -name 'RuntimeOverlayProbe*.class' \
    ! -name 'CNCNDownloadUI*.class' | sort)
TOTAL=$(find "$OUT/classes" -name '*.class' | wc -l)
echo "  classes 组 ${#DEX_MAIN[@]}，classes2 组 ${#DEX_UI[@]}，classes3 组 ${#DEX_3[@]}，共 $TOTAL"
if [ "${#DEX_MAIN[@]}" -eq 0 ] || [ "${#DEX_UI[@]}" -eq 0 ] \
        || [ "${#DEX_3[@]}" -eq 0 ]; then
    echo "✘ dex 分组为空"; exit 1
fi
if [ "$(( ${#DEX_MAIN[@]} + ${#DEX_UI[@]} + ${#DEX_3[@]} ))" -ne "$TOTAL" ]; then
    echo "✘ dex 分组没覆盖全部 Java 产物"; exit 1
fi

D8_COMMON=(--min-api 21 --lib "$DEPS_DIR/android.jar" --classpath "$OUT/stubs.jar")
"$BUILD_TOOLS/d8" "${D8_COMMON[@]}" --output "$OUT/dexmain" "${DEX_MAIN[@]}"
"$BUILD_TOOLS/d8" "${D8_COMMON[@]}" --output "$OUT/dexui" "${DEX_UI[@]}"
"$BUILD_TOOLS/d8" "${D8_COMMON[@]}" --output "$OUT/dex3" "${DEX_3[@]}"
java -jar "$BAKSMALI_JAR" d "$OUT/dexmain/classes.dex" -o "$OUT/smalimain"
java -jar "$BAKSMALI_JAR" d "$OUT/dexui/classes.dex" -o "$OUT/smaliui"
java -jar "$BAKSMALI_JAR" d "$OUT/dex3/classes.dex" -o "$OUT/smali3"

say "用 Java 编译产物覆盖对应 smali"
rm -f smali/jp/f4samurai/web/WebViewImpl*.smali \
      smali/jp/f4samurai/web/RuntimeOverlayProbe*.smali
cp "$OUT"/smalimain/jp/f4samurai/web/WebViewImpl*.smali \
   smali/jp/f4samurai/web/
cp "$OUT"/smalimain/jp/f4samurai/web/RuntimeOverlayProbe*.smali \
   smali/jp/f4samurai/web/

rm -f smali_classes2/io/kamihama/magianative/CNCNDownloadUI*.smali
cp "$OUT"/smaliui/io/kamihama/magianative/CNCNDownloadUI*.smali \
   smali_classes2/io/kamihama/magianative/

rm -rf smali_classes3 && mkdir -p smali_classes3
cp -r "$OUT"/smali3/. smali_classes3/

# 构建前确认替换发生在 classes.dex 输入树，而不是重复塞进 classes3。
grep -R -q 'MagiaHook-Reject' smali/jp/f4samurai/web/WebViewImpl*.smali \
    || { echo "✘ 加固 WebViewImpl 未进入 smali/"; exit 1; }
grep -R -q 'MagiaHook-Status' smali/jp/f4samurai/web/RuntimeOverlayProbe*.smali \
    || { echo "✘ 运行时覆盖探针未进入 smali/"; exit 1; }
if grep -R -q '/data/data/io.kamihama.totentanz/files/magica/' \
        smali/jp/f4samurai/web/WebViewImpl*.smali; then
    echo "✘ WebViewImpl 仍含旧硬编码私有路径"; exit 1
fi
if find smali_classes3 \( -name 'WebViewImpl*.smali' -o -name 'RuntimeOverlayProbe*.smali' \) \
        | grep -q .; then
    echo "✘ WebViewImpl/RuntimeOverlayProbe 被重复放进 classes3"; exit 1
fi

say "apktool b"
java -jar "$APKTOOL_JAR" b . -o "$OUT/unsigned.apk" --use-aapt2
"$BUILD_TOOLS/zipalign" -f 4 "$OUT/unsigned.apk" "$OUT/aligned.apk"
"$BUILD_TOOLS/apksigner" sign --key "$SIGN_KEY" --cert "$SIGN_CERT" \
    --v1-signing-enabled true --v2-signing-enabled true --v3-signing-enabled true \
    --out "$OUT/magireco-legacy.apk" "$OUT/aligned.apk"

# 生成 smali 只是构建产物，不入库。
git checkout -- smali smali_classes2 smali_classes3 2>/dev/null || true
git clean -fdq smali smali_classes2 smali_classes3 2>/dev/null || true

say "自检"
python3 tools/check-so-deps.py           "$OUT/magireco-legacy.apk"
python3 tools/check-entry-guard.py       "$OUT/magireco-legacy.apk"
python3 tools/check-asset-compression.py "$OUT/magireco-legacy.apk"
python3 tools/check-apk-freshness.py     "$OUT/magireco-legacy.apk"

"$BUILD_TOOLS/apksigner" verify --print-certs "$OUT/magireco-legacy.apk" \
    | grep -i "SHA-256 digest" || true
sha256sum "$OUT/magireco-legacy.apk"
echo "✔ 出包完成：$OUT/magireco-legacy.apk"
