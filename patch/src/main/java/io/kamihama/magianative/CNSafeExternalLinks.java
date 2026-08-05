package io.kamihama.magianative;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.util.Locale;

/** 对云端署名和客户端更新地址执行 HTTPS/域名限制后再交给系统浏览器。 */
final class CNSafeExternalLinks {

    private CNSafeExternalLinks() {}

    static void open(Activity activity, String value) {
        if (activity == null) throw new IllegalArgumentException("Activity 为空");
        Uri uri = validate(value);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage(null);
        intent.setComponent(null);
        activity.startActivity(intent);
    }

    static Uri validate(String value) {
        if (value == null) throw new IllegalArgumentException("链接为空");
        String trimmed = value.trim();
        if (trimmed.length() == 0 || containsControl(trimmed)) {
            throw new IllegalArgumentException("链接为空或含控制字符");
        }
        Uri uri;
        try {
            uri = Uri.parse(trimmed);
        } catch (Throwable error) {
            throw new IllegalArgumentException("链接无法解析", error);
        }
        if (!uri.isHierarchical()
                || !"https".equalsIgnoreCase(uri.getScheme())) {
            throw new IllegalArgumentException("只允许 HTTPS 链接");
        }
        if (uri.getUserInfo() != null || trimmed.indexOf('@') >= 0) {
            throw new IllegalArgumentException("链接不得包含 userinfo");
        }
        int port = uri.getPort();
        if (port != -1 && port != 443) {
            throw new IllegalArgumentException("链接端口不在允许范围");
        }
        String host = uri.getHost();
        if (host == null || !allowedHost(host.toLowerCase(Locale.US))) {
            throw new IllegalArgumentException("链接域名不在允许列表: " + host);
        }
        return uri;
    }

    private static boolean allowedHost(String host) {
        if (host.equals("magireco.top") || host.endsWith(".magireco.top")) {
            return true;
        }
        if (host.equals("github.com") || host.equals("www.github.com")) {
            return true;
        }
        if (host.equals("b23.tv") || host.equals("www.b23.tv")
                || host.equals("bilibili.com") || host.equals("www.bilibili.com")) {
            return true;
        }
        return host.equals("magireader.pages.dev")
                || host.equals("magiaexedralive2dviewer.pages.dev")
                || host.equals("magireco-call-search-cn.pages.dev");
    }

    private static boolean containsControl(String value) {
        for (int i = 0; i < value.length(); ++i) {
            char ch = value.charAt(i);
            if (ch <= 0x1f || ch == 0x7f) return true;
        }
        return false;
    }
}
