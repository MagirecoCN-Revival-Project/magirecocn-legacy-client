package cn.thinkingdata.core.network;

import cz.msebera.android.httpclient.client.methods.HttpPost;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes.dex */
public class Request {
    final String body;
    final boolean callBackOnMainThread;
    final boolean gzip;
    final Map<String, String> headers;
    final String method;
    final String url;
    final boolean useCache;

    /* loaded from: classes.dex */
    public static class Builder {
        String body;
        String url;
        String method = "GET";
        Map<String, String> headers = new HashMap();
        boolean gzip = false;
        boolean useCache = true;
        boolean callBackOnMainThread = false;

        public Builder addHeader(String str, String str2) {
            this.headers.put(str, str2);
            return this;
        }

        public Request build() {
            if (this.url != null) {
                return new Request(this);
            }
            throw new IllegalStateException("url == null");
        }

        public Builder get() {
            return method("GET", null);
        }

        public Builder gzip() {
            this.gzip = true;
            return this;
        }

        public Builder headers(Map<String, String> map) {
            this.headers.putAll(map);
            return this;
        }

        public Builder mainThread() {
            this.callBackOnMainThread = true;
            return this;
        }

        public Builder method(String str, String str2) {
            if (str2 != null || !HttpPost.METHOD_NAME.equals(str)) {
                this.method = str;
                this.body = str2;
                return this;
            }
            throw new IllegalArgumentException("method " + str + " must have a request body.");
        }

        public Builder post(String str) {
            return method(HttpPost.METHOD_NAME, str);
        }

        public Builder removeHeader(String str) {
            this.headers.remove(str);
            return this;
        }

        public Builder url(String str) {
            Objects.requireNonNull(str, "http url == null");
            this.url = str;
            return this;
        }

        public Builder useCache(boolean z) {
            this.useCache = z;
            return this;
        }
    }

    Request(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.headers = builder.headers;
        this.body = builder.body;
        this.gzip = builder.gzip;
        this.useCache = builder.useCache;
        this.callBackOnMainThread = builder.callBackOnMainThread;
    }
}
