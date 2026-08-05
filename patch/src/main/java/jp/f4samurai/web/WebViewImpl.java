package jp.f4samurai.web;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import jp.f4samurai.AppActivity;

/**
 * 可重新编译的 WebView 实现。
 *
 * <p>原始类把任意包含 {@code /magica/} 的 URL 直接拼到应用私有目录，既不验证
 * 来源，也不做 canonical containment；同时外部导航会在 UI 线程上无限等待
 * GL 线程。本实现保留原调用协议，只收紧本地覆盖边界并给同步回调加超时。
 */
public class WebViewImpl extends WebView {

    private static final String TAG_URL = "MagiaHook-URL";
    private static final String TAG_PATH = "MagiaHook-Path";
    private static final String TAG_FOUND = "MagiaHook-Found";
    private static final String TAG_REJECT = "MagiaHook-Reject";
    private static final String TAG_ERROR = "MagiaHook-Err";
    private static final String JS_SCHEME = "game";
    private static final String OVERLAY_PREFIX = "/magica/";
    private static final long SHOULD_START_TIMEOUT_SECONDS = 5L;

    private static AppActivity sAppActivity;
    private boolean mTouchEnabled;

    public WebViewImpl(Context context) {
        super(context);
        sAppActivity = (AppActivity) context;
        mTouchEnabled = true;

        setTag("WebViewImpl");
        setFocusable(true);
        setFocusableInTouchMode(true);
        getSettings().setTextZoom(100);
        setVisibility(GONE);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        setBackgroundColor(0);
        if (Build.VERSION.SDK_INT >= 19) {
            setLayerType(LAYER_TYPE_HARDWARE, null);
        }
        setInitialScale(1);

        WebSettings settings = getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(false);
        settings.setJavaScriptEnabled(true);
        // 本地覆盖由 shouldInterceptRequest 自己打开 FileInputStream，不依赖 file://。
        // 暂时保留原值，避免旧页面的 file URL 行为发生无关回归。
        settings.setAllowFileAccess(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= 21) {
            // 前端与资源端点均应使用 HTTPS；禁止 HTTPS 页面静默引入 HTTP 子资源。
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
        }
        if (Build.VERSION.SDK_INT >= 19
                && (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        setWebViewClient(new WebViewClientImpl());
        setWebChromeClient(new WebChromeClientImpl());
        addJavascriptInterface(new Javascript(), "androidCommand");
    }

    public void setTouchEnable(boolean enabled) {
        mTouchEnabled = enabled;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mTouchEnabled ? super.onTouchEvent(event) : true;
    }

    public void setWebViewRect(int x, int y, int width, int height) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.leftMargin = x;
        params.topMargin = y;
        params.gravity = 51;
        setLayoutParams(params);
    }

    private final class WebViewClientImpl extends WebViewClient {

        @Override
        public WebResourceResponse shouldInterceptRequest(
                WebView view, WebResourceRequest request) {
            if (request == null || !"GET".equalsIgnoreCase(request.getMethod())) {
                return super.shouldInterceptRequest(view, request);
            }
            WebResourceResponse local = serveOverlay(view, request.getUrl());
            return local != null ? local : super.shouldInterceptRequest(view, request);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            WebResourceResponse local = serveOverlay(view, safeParse(url));
            return local != null ? local : super.shouldInterceptRequest(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return request != null && shouldOverrideUrlLoading(view, request.getUrl().toString());
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i("shouldUrlLoading", String.valueOf(url));
            Uri uri = safeParse(url);
            if (uri != null && JS_SCHEME.equalsIgnoreCase(uri.getScheme())) {
                WebViewHelper._onJsCallback(url);
                return true;
            }

            final boolean[] result = {true};  // 超时/异常时默认阻止未知导航
            final CountDownLatch latch = new CountDownLatch(1);
            try {
                AppActivity activity = sAppActivity;
                if (activity == null) {
                    Log.e(TAG_ERROR, "AppActivity is null; block navigation");
                    return true;
                }
                activity.runOnGLThread(new ShouldStartLoadingWorker(latch, result, url));
                if (!latch.await(SHOULD_START_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
                    Log.e(TAG_ERROR, "shouldOverrideUrlLoading timed out: " + url);
                    return true;
                }
            } catch (InterruptedException interrupted) {
                Thread.currentThread().interrupt();
                Log.e(TAG_ERROR, "shouldOverrideUrlLoading interrupted");
                return true;
            } catch (Throwable error) {
                Log.e(TAG_ERROR, "shouldOverrideUrlLoading failed", error);
                return true;
            }
            return result[0];
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (view.getVisibility() == VISIBLE) {
                WebViewHelper._onJsCallback("game:LOAD_SHOW");
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            WebViewHelper._didFinishLoading(url);
        }

        @Override
        public void onReceivedError(
                WebView view, int errorCode, String description, String failingUrl) {
            WebViewHelper._didFailLoading(failingUrl, errorCode);
        }
    }

    private WebResourceResponse serveOverlay(WebView view, Uri requestUri) {
        if (requestUri == null) return null;
        Log.i(TAG_URL, requestUri.toString());

        String scheme = lower(requestUri.getScheme());
        if (!"https".equals(scheme) && !"http".equals(scheme)) return null;
        if (!sameOrigin(view, requestUri)) {
            Log.w(TAG_REJECT, "cross-origin overlay request: " + requestUri);
            return null;
        }

        String path = requestUri.getPath();
        if (path == null || !path.startsWith(OVERLAY_PREFIX)) return null;
        String relative = path.substring(OVERLAY_PREFIX.length());
        if (relative.length() == 0 || relative.startsWith("api/")
                || relative.indexOf('\u0000') >= 0) {
            return null;
        }

        File root = new File(view.getContext().getFilesDir(), "magica");
        File candidate = new File(root, relative);
        try {
            String rootPath = root.getCanonicalPath();
            String candidatePath = candidate.getCanonicalPath();
            String prefix = rootPath + File.separator;
            if (!candidatePath.startsWith(prefix) || !candidate.isFile()) {
                Log.w(TAG_REJECT, "outside overlay root or not a file: " + candidatePath);
                return null;
            }
            Log.i(TAG_PATH, candidatePath);
            String mime = mimeFor(relative);
            FileInputStream stream = new FileInputStream(candidate);
            Log.i(TAG_FOUND, candidatePath + " mime=" + mime);
            return new WebResourceResponse(mime, textual(mime) ? "utf-8" : null, stream);
        } catch (IOException error) {
            Log.e(TAG_ERROR, "overlay open failed: " + candidate, error);
            return null;
        }
    }

    private static boolean sameOrigin(WebView view, Uri request) {
        String current = view.getUrl();
        if (TextUtils.isEmpty(current)) current = view.getOriginalUrl();
        Uri page = safeParse(current);
        if (page == null) return false;
        String pageScheme = lower(page.getScheme());
        String requestScheme = lower(request.getScheme());
        String pageHost = lower(page.getHost());
        String requestHost = lower(request.getHost());
        return !TextUtils.isEmpty(pageHost)
                && pageScheme.equals(requestScheme)
                && pageHost.equals(requestHost)
                && effectivePort(page) == effectivePort(request);
    }

    private static int effectivePort(Uri uri) {
        int port = uri.getPort();
        if (port >= 0) return port;
        return "https".equalsIgnoreCase(uri.getScheme()) ? 443 : 80;
    }

    private static Uri safeParse(String value) {
        if (TextUtils.isEmpty(value)) return null;
        try {
            return Uri.parse(value);
        } catch (Throwable ignored) {
            return null;
        }
    }

    private static String lower(String value) {
        return value == null ? "" : value.toLowerCase(Locale.US);
    }

    private static String mimeFor(String relative) {
        String name = relative.toLowerCase(Locale.US);
        if (name.endsWith(".png")) return "image/png";
        if (name.endsWith(".jpg") || name.endsWith(".jpeg")) return "image/jpeg";
        if (name.endsWith(".webp")) return "image/webp";
        if (name.endsWith(".gif")) return "image/gif";
        if (name.endsWith(".svg")) return "image/svg+xml";
        if (name.endsWith(".json")) return "application/json";
        if (name.endsWith(".js")) return "application/javascript";
        if (name.endsWith(".css")) return "text/css";
        if (name.endsWith(".html")) return "text/html";
        if (name.endsWith(".txt")) return "text/plain";
        if (name.endsWith(".woff")) return "font/woff";
        if (name.endsWith(".woff2")) return "font/woff2";
        if (name.endsWith(".ttf")) return "font/ttf";
        return "application/octet-stream";
    }

    private static boolean textual(String mime) {
        return mime.startsWith("text/")
                || "application/json".equals(mime)
                || "application/javascript".equals(mime)
                || "image/svg+xml".equals(mime);
    }

    private final class Javascript {
        @JavascriptInterface
        public void jsCallback(String command) {
            Uri uri = safeParse(command);
            if (uri != null && JS_SCHEME.equalsIgnoreCase(uri.getScheme())) {
                WebViewHelper._onJsCallback(command);
            }
        }
    }

    private final class WebChromeClientImpl extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Uri uri = safeParse(message);
            if (uri != null && JS_SCHEME.equalsIgnoreCase(uri.getScheme())) {
                WebViewHelper._onJsCallback(message);
                result.cancel();
                return true;
            }
            return super.onJsAlert(view, url, message, result);
        }
    }
}
