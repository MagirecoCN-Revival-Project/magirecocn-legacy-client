package okhttp3.internal.http;

import cz.msebera.android.httpclient.client.methods.HttpPatch;
import cz.msebera.android.httpclient.client.methods.HttpPost;

/* loaded from: classes2.dex */
public final class HttpMethod {
    public static boolean invalidatesCache(String method) {
        return method.equals(HttpPost.METHOD_NAME) || method.equals(HttpPatch.METHOD_NAME) || method.equals("PUT") || method.equals("DELETE") || method.equals("MOVE");
    }

    public static boolean requiresRequestBody(String method) {
        return method.equals(HttpPost.METHOD_NAME) || method.equals("PUT") || method.equals(HttpPatch.METHOD_NAME) || method.equals("PROPPATCH") || method.equals("REPORT");
    }

    public static boolean permitsRequestBody(String method) {
        return (method.equals("GET") || method.equals("HEAD")) ? false : true;
    }

    public static boolean redirectsWithBody(String method) {
        return method.equals("PROPFIND");
    }

    public static boolean redirectsToGet(String method) {
        return !method.equals("PROPFIND");
    }

    private HttpMethod() {
    }
}
