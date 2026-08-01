package cn.thinkingdata.core.network;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import cn.thinkingdata.core.network.Call;
import java.util.concurrent.ExecutorService;
import javax.net.ssl.SSLSocketFactory;

/* loaded from: classes.dex */
public class TEHttpClient implements Call.Factory {
    final int connectTimeout;
    final ExecutorService dispatcher;
    final int readTimeout;
    final SSLSocketFactory sslSocketFactory;

    /* loaded from: classes.dex */
    public static final class Builder {
        SSLSocketFactory sslSocketFactory;
        ExecutorService dispatcher = TEHttpTaskManager.getExecutor();
        int connectTimeout = 15000;
        int readTimeout = AccessibilityNodeInfoCompat.EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_MAX_LENGTH;

        public TEHttpClient build() {
            return new TEHttpClient(this);
        }

        public Builder connectTimeout(int i) {
            this.connectTimeout = i;
            return this;
        }

        public Builder dispatcher(ExecutorService executorService) {
            if (executorService == null) {
                throw new IllegalArgumentException("dispatcher == null");
            }
            this.dispatcher = executorService;
            return this;
        }

        public Builder readTimeout(int i) {
            this.readTimeout = i;
            return this;
        }

        public Builder sslSocketFactory(SSLSocketFactory sSLSocketFactory) {
            this.sslSocketFactory = sSLSocketFactory;
            return this;
        }
    }

    public TEHttpClient() {
        this(new Builder());
    }

    TEHttpClient(Builder builder) {
        this.dispatcher = builder.dispatcher;
        this.connectTimeout = builder.connectTimeout;
        this.readTimeout = builder.readTimeout;
        this.sslSocketFactory = builder.sslSocketFactory;
    }

    @Override // cn.thinkingdata.core.network.Call.Factory
    public Call newCall(Request request) {
        return RealCall.newRealCall(this, request);
    }
}
