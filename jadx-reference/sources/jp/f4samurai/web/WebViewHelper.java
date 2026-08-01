package jp.f4samurai.web;

import android.os.Build;
import android.widget.FrameLayout;
import java.util.HashMap;
import java.util.Map;
import jp.f4samurai.AppActivity;

/* loaded from: classes.dex */
public class WebViewHelper {
    private static AppActivity sAppActivity;
    private static FrameLayout sFrameLayout;
    private static Map<String, String> sHeader;
    private static WebViewImpl sWebView;

    /* JADX INFO: Access modifiers changed from: private */
    public static native void didFailLoading(String str, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void didFinishLoading(String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void onJsCallback(String str);

    private static native boolean shouldStartLoading(String str);

    public WebViewHelper(AppActivity appActivity, FrameLayout frameLayout) {
        sAppActivity = appActivity;
        sFrameLayout = frameLayout;
    }

    public static void createWebView() {
        sAppActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.web.WebViewHelper.1
            @Override // java.lang.Runnable
            public void run() {
                WebViewHelper.sWebView = new WebViewImpl(WebViewHelper.sAppActivity);
                WebViewHelper.sWebView.setTag("WebView");
                WebViewHelper.sFrameLayout.addView(WebViewHelper.sWebView, new FrameLayout.LayoutParams(-2, -2));
                WebViewHelper.sHeader = new HashMap();
            }
        });
    }

    public static void removeWebView() {
        sAppActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.web.WebViewHelper.2
            @Override // java.lang.Runnable
            public void run() {
                WebViewHelper.sFrameLayout.removeView(WebViewHelper.sWebView);
                WebViewHelper.sWebView.destroy();
                WebViewHelper.sWebView = null;
            }
        });
    }

    public static void setTouchEnable(final boolean z) {
        sAppActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.web.WebViewHelper.3
            @Override // java.lang.Runnable
            public void run() {
                WebViewHelper.sWebView.setTouchEnable(z);
            }
        });
    }

    public static void loadUrl(final String str) {
        sAppActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.web.WebViewHelper.4
            @Override // java.lang.Runnable
            public void run() {
                WebViewHelper.sWebView.loadUrl(str, WebViewHelper.sHeader);
            }
        });
    }

    public static void loadFile(final String str) {
        sAppActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.web.WebViewHelper.5
            @Override // java.lang.Runnable
            public void run() {
                WebViewHelper.sWebView.loadUrl(str);
            }
        });
    }

    public static void stopLoading() {
        sAppActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.web.WebViewHelper.6
            @Override // java.lang.Runnable
            public void run() {
                WebViewHelper.sWebView.stopLoading();
            }
        });
    }

    public static void loadHtmlString(final String str, final String str2) {
        sAppActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.web.WebViewHelper.7
            @Override // java.lang.Runnable
            public void run() {
                WebViewImpl webViewImpl = WebViewHelper.sWebView;
                String str3 = str2;
                webViewImpl.loadDataWithBaseURL(str3, str, "text/html", "UTF-8", str3);
            }
        });
    }

    public static void evaluateJS(final String str) {
        sAppActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.web.WebViewHelper.8
            @Override // java.lang.Runnable
            public void run() {
                if (Build.VERSION.SDK_INT >= 19) {
                    WebViewHelper.sWebView.evaluateJavascript(str, null);
                    return;
                }
                WebViewHelper.sWebView.loadUrl("javascript:(function(){" + str + "})()");
            }
        });
    }

    public static void addRequestProperty(String str, String str2) {
        sAppActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.web.WebViewHelper.9
            @Override // java.lang.Runnable
            public void run() {
            }
        });
    }

    public static void setRequestProperty(String str, String str2) {
        sAppActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.web.WebViewHelper.10
            @Override // java.lang.Runnable
            public void run() {
            }
        });
    }

    public static void setWebViewRect(final int i, final int i2, final int i3, final int i4) {
        sAppActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.web.WebViewHelper.11
            @Override // java.lang.Runnable
            public void run() {
                WebViewHelper.sWebView.setWebViewRect(i, i2, i3, i4);
            }
        });
    }

    public static void setVisible(final boolean z) {
        sAppActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.web.WebViewHelper.12
            @Override // java.lang.Runnable
            public void run() {
                WebViewHelper.sWebView.setVisibility(z ? 0 : 8);
            }
        });
    }

    public static void clearCache(final boolean z) {
        sAppActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.web.WebViewHelper.13
            @Override // java.lang.Runnable
            public void run() {
                WebViewHelper.sWebView.clearCache(z);
            }
        });
    }

    public static void addHeader(final String str, final String str2) {
        sAppActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.web.WebViewHelper.14
            @Override // java.lang.Runnable
            public void run() {
                WebViewHelper.sHeader.put(str, str2);
            }
        });
    }

    public static boolean _shouldStartLoading(String str) {
        return !shouldStartLoading(str);
    }

    public static void _didFinishLoading(final String str) {
        sAppActivity.runOnGLThread(new Runnable() { // from class: jp.f4samurai.web.WebViewHelper.15
            @Override // java.lang.Runnable
            public void run() {
                WebViewHelper.didFinishLoading(str);
            }
        });
    }

    public static void _didFailLoading(final String str, final int i) {
        sAppActivity.runOnGLThread(new Runnable() { // from class: jp.f4samurai.web.WebViewHelper.16
            @Override // java.lang.Runnable
            public void run() {
                WebViewHelper.didFailLoading(str, i);
            }
        });
    }

    public static void _onJsCallback(final String str) {
        sAppActivity.runOnGLThread(new Runnable() { // from class: jp.f4samurai.web.WebViewHelper.17
            @Override // java.lang.Runnable
            public void run() {
                WebViewHelper.onJsCallback(str);
            }
        });
    }

    public boolean onKeyDown() {
        if (sWebView.getVisibility() != 0) {
            return false;
        }
        evaluateJS("androidBackKey();");
        return true;
    }

    public void onPause() {
        WebViewImpl webViewImpl = sWebView;
        if (webViewImpl != null) {
            webViewImpl.pauseTimers();
        }
    }

    public void onResume() {
        WebViewImpl webViewImpl = sWebView;
        if (webViewImpl != null) {
            webViewImpl.resumeTimers();
        }
    }

    public void onDestroy() {
        WebViewImpl webViewImpl = sWebView;
        if (webViewImpl != null) {
            webViewImpl.destroy();
        }
    }
}
