package jp.f4samurai.web;

import android.os.Build;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/** 记录本地 JS/HTML 覆盖命中，并确认运行时 jQuery 标记已经进入页面上下文。 */
final class RuntimeOverlayProbe {

    private static final String TAG = "MagiaHook-Status";
    private static final AtomicInteger JS = new AtomicInteger();
    private static final AtomicInteger HTML = new AtomicInteger();
    private static final AtomicInteger JSON = new AtomicInteger();
    private static final AtomicInteger CSS = new AtomicInteger();
    private static final AtomicInteger OTHER = new AtomicInteger();
    private static final AtomicBoolean JQUERY_LOCAL = new AtomicBoolean(false);
    private static final AtomicBoolean MARKER_CONFIRMED = new AtomicBoolean(false);

    private RuntimeOverlayProbe() {}

    static void onLocalFile(String path, String mime) {
        if ("application/javascript".equals(mime)) JS.incrementAndGet();
        else if ("text/html".equals(mime)) HTML.incrementAndGet();
        else if ("application/json".equals(mime)) JSON.incrementAndGet();
        else if ("text/css".equals(mime)) CSS.incrementAndGet();
        else OTHER.incrementAndGet();
        if (path != null && path.endsWith("/magica/js/libs/jquery-3.7.1.min.js")) {
            JQUERY_LOCAL.set(true);
        }
    }

    static void onPageFinished(WebView view, String url) {
        Log.i(TAG, "page=" + url
                + " local_js=" + JS.get()
                + " local_html=" + HTML.get()
                + " local_json=" + JSON.get()
                + " local_css=" + CSS.get()
                + " local_other=" + OTHER.get()
                + " jquery_local=" + JQUERY_LOCAL.get()
                + " marker_confirmed=" + MARKER_CONFIRMED.get());
        if (view == null || Build.VERSION.SDK_INT < 19) return;
        try {
            view.evaluateJavascript(
                "(function(){try{var m=window.__MAGIACN_RUNTIME_I18N__;"
              + "return m?JSON.stringify(m):'';}catch(e){return 'ERROR:'+String(e);}})();",
                new MarkerCallback());
        } catch (Throwable error) {
            Log.e(TAG, "runtime marker probe failed", error);
        }
    }

    /** 具名、原始 ValueCallback：避免旧 d8 在嵌套匿名类/泛型签名上的已知崩溃。 */
    @SuppressWarnings("rawtypes")
    private static final class MarkerCallback implements ValueCallback {
        @Override public void onReceiveValue(Object value) {
            String text = value == null ? "null" : String.valueOf(value);
            boolean found = text.contains("__schema")
                    || text.contains("runtime-i18n")
                    || text.contains("packageId");
            if (found) MARKER_CONFIRMED.set(true);
            Log.i(TAG, "runtime_marker=" + text
                    + " confirmed=" + MARKER_CONFIRMED.get());
        }
    }
}
