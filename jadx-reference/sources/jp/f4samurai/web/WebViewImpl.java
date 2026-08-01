package jp.f4samurai.web;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import com.loopj.android.http.RequestParams;
import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.CountDownLatch;
import jp.f4samurai.AppActivity;
import jp.f4samurai.madomagi.BuildConfig;

/* loaded from: classes.dex */
public class WebViewImpl extends WebView {
    private static AppActivity sAppActivity;
    private final String mJsScheme;
    private boolean mTouchEnabled;

    /* JADX DEBUG: Multi-variable search result rejected for r3v1, resolved type: java.lang.Object[] */
    /* JADX DEBUG: Multi-variable search result rejected for r3v2, resolved type: java.lang.Object[] */
    /* JADX WARN: Multi-variable type inference failed */
    public WebViewImpl(Context context) {
        super(context);
        this.mJsScheme = "game";
        sAppActivity = (AppActivity) context;
        this.mTouchEnabled = true;
        setTag("WebViewImpl");
        setFocusable(true);
        setFocusableInTouchMode(true);
        getSettings().setTextZoom(100);
        setVisibility(8);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        setBackgroundColor(0);
        Object[] objArr = 0;
        Object[] objArr2 = 0;
        if (Build.VERSION.SDK_INT >= 19) {
            setLayerType(2, null);
        }
        setInitialScale(1);
        getSettings().setUseWideViewPort(true);
        getSettings().setLoadWithOverviewMode(true);
        getSettings().setSupportZoom(false);
        getSettings().setJavaScriptEnabled(true);
        getSettings().setAllowFileAccess(true);
        getSettings().setCacheMode(-1);
        getSettings().setDomStorageEnabled(true);
        getSettings().setUseWideViewPort(true);
        if (Build.VERSION.SDK_INT >= 21) {
            getSettings().setMixedContentMode(0);
        }
        if (Build.VERSION.SDK_INT >= 19 && TextUtils.equals("develop", BuildConfig.FLAVOR)) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        setWebViewClient(new WebViewClientImpl());
        setWebChromeClient(new WebChromeClientImpl());
        addJavascriptInterface(new Javascript(), "androidCommand");
    }

    public void setTouchEnable(boolean z) {
        this.mTouchEnabled = z;
    }

    @Override // android.webkit.WebView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mTouchEnabled) {
            return super.onTouchEvent(motionEvent);
        }
        return true;
    }

    /* loaded from: classes.dex */
    private class WebViewClientImpl extends WebViewClient {
        @Override // android.webkit.WebViewClient
        public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
            return shouldInterceptRequest(webView, webResourceRequest.getUrl().toString());
        }

        @Override // android.webkit.WebViewClient
        public WebResourceResponse shouldInterceptRequest(WebView webView, String str) {
            Log.i("MagiaHook-URL", str);
            if (str.contains("/magica/")) {
                String substring = str.substring(str.indexOf("/magica/") + 8);
                int indexOf = substring.indexOf("?");
                if (indexOf != -1) {
                    substring = substring.substring(0, indexOf);
                }
                if (!substring.startsWith("api/")) {
                    String str2 = "/data/data/io.kamihama.totentanz/files/magica/" + substring;
                    Log.i("MagiaHook-Path", str2);
                    File file = new File(str2);
                    if (file.exists()) {
                        Log.i("MagiaHook-Found", str2);
                        String str3 = "application/octet-stream";
                        if (substring.endsWith(".png")) {
                            str3 = "image/png";
                        } else if (substring.endsWith(".jpg")) {
                            str3 = "image/jpeg";
                        } else if (substring.endsWith(".jpeg")) {
                            str3 = "image/jpeg";
                        } else if (substring.endsWith(".json")) {
                            str3 = RequestParams.APPLICATION_JSON;
                        } else if (substring.endsWith(".js")) {
                            str3 = "application/javascript";
                        } else if (substring.endsWith(".css")) {
                            str3 = "text/css";
                        } else if (substring.endsWith(".html")) {
                            str3 = "text/html";
                        }
                        try {
                            return new WebResourceResponse(str3, "utf-8", new FileInputStream(file));
                        } catch (Exception e) {
                            Log.e("MagiaHook-Err", e.toString());
                        }
                    }
                }
            }
            return super.shouldInterceptRequest(webView, str);
        }

        private WebViewClientImpl() {
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            Log.i("shouldUrlLoading", str);
            if (str.startsWith("game")) {
                WebViewHelper._onJsCallback(str);
                return true;
            }
            boolean[] zArr = {true};
            CountDownLatch countDownLatch = new CountDownLatch(1);
            WebViewImpl.sAppActivity.runOnGLThread(new ShouldStartLoadingWorker(countDownLatch, zArr, str));
            try {
                countDownLatch.await();
            } catch (InterruptedException unused) {
                Log.d("DEBUG", "'shouldOverrideUrlLoading' failed");
            }
            return zArr[0];
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            if (webView.getVisibility() == 0) {
                WebViewHelper._onJsCallback("game:LOAD_SHOW");
            }
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView webView, String str) {
            WebViewHelper._didFinishLoading(str);
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView webView, int i, String str, String str2) {
            WebViewHelper._didFailLoading(str2, i);
        }
    }

    public void setWebViewRect(int i, int i2, int i3, int i4) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i3, i4);
        layoutParams.leftMargin = i;
        layoutParams.topMargin = i2;
        layoutParams.gravity = 51;
        setLayoutParams(layoutParams);
    }

    /* loaded from: classes.dex */
    private class Javascript {
        private Javascript() {
        }

        @JavascriptInterface
        public void jsCallback(String str) {
            if (str.startsWith("game")) {
                WebViewHelper._onJsCallback(str);
            }
        }
    }

    /* loaded from: classes.dex */
    private class WebChromeClientImpl extends WebChromeClient {
        private WebChromeClientImpl() {
        }

        @Override // android.webkit.WebChromeClient
        public boolean onJsAlert(WebView webView, String str, String str2, JsResult jsResult) {
            if (str2 == null) {
                return super.onJsAlert(webView, str, str2, jsResult);
            }
            if (str2.startsWith("game")) {
                WebViewHelper._onJsCallback(str2);
                jsResult.cancel();
                return true;
            }
            return super.onJsAlert(webView, str, str2, jsResult);
        }
    }
}
