package okhttp3.internal.http;

import cz.msebera.android.httpclient.HttpStatus;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.HttpRetryException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.CertificatePinner;
import okhttp3.EventListener;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RouteException;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.http2.ConnectionShutdownException;

/* loaded from: classes2.dex */
public final class RetryAndFollowUpInterceptor implements Interceptor {
    private static final int MAX_FOLLOW_UPS = 20;
    private Object callStackTrace;
    private volatile boolean canceled;
    private final OkHttpClient client;
    private final boolean forWebSocket;
    private volatile StreamAllocation streamAllocation;

    public RetryAndFollowUpInterceptor(OkHttpClient client, boolean forWebSocket) {
        this.client = client;
        this.forWebSocket = forWebSocket;
    }

    public void cancel() {
        this.canceled = true;
        StreamAllocation streamAllocation = this.streamAllocation;
        if (streamAllocation != null) {
            streamAllocation.cancel();
        }
    }

    public boolean isCanceled() {
        return this.canceled;
    }

    public void setCallStackTrace(Object callStackTrace) {
        this.callStackTrace = callStackTrace;
    }

    public StreamAllocation streamAllocation() {
        return this.streamAllocation;
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [144=5, 145=4, 146=4] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x015d  */
    @Override // okhttp3.Interceptor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Response intercept(Interceptor.Chain chain) throws IOException {
        RealInterceptorChain realChain;
        Response response;
        Request followUp;
        RealInterceptorChain realChain2;
        int followUpCount;
        Request request = chain.request();
        RealInterceptorChain realChain3 = (RealInterceptorChain) chain;
        Call call = realChain3.call();
        EventListener eventListener = realChain3.eventListener();
        StreamAllocation streamAllocation = new StreamAllocation(this.client.connectionPool(), createAddress(request.url()), call, eventListener, this.callStackTrace);
        this.streamAllocation = streamAllocation;
        int followUpCount2 = 0;
        Request request2 = request;
        Response priorResponse = null;
        while (!this.canceled) {
            try {
                try {
                    Response response2 = realChain3.proceed(request2, streamAllocation, null, null);
                    if (0 != 0) {
                        streamAllocation.streamFailed(null);
                        streamAllocation.release();
                    }
                    response = priorResponse != null ? response2.newBuilder().priorResponse(priorResponse.newBuilder().body(null).build()).build() : response2;
                    try {
                        followUp = followUpRequest(response, streamAllocation.route());
                    } catch (IOException e) {
                        streamAllocation.release();
                        throw e;
                    }
                } catch (IOException e2) {
                    realChain = realChain3;
                    try {
                        boolean requestSendStarted = e2 instanceof ConnectionShutdownException ? false : true;
                        if (!recover(e2, streamAllocation, requestSendStarted, request2)) {
                            throw e2;
                        }
                        if (0 != 0) {
                            streamAllocation.streamFailed(null);
                            streamAllocation.release();
                        }
                        realChain3 = realChain;
                    } catch (Throwable th) {
                        e = th;
                        if (1 != 0) {
                            streamAllocation.streamFailed(null);
                            streamAllocation.release();
                        }
                        throw e;
                    }
                }
            } catch (RouteException e3) {
                realChain = realChain3;
                if (!recover(e3.getLastConnectException(), streamAllocation, false, request2)) {
                    throw e3.getFirstConnectException();
                }
                if (0 != 0) {
                    streamAllocation.streamFailed(null);
                    streamAllocation.release();
                }
                realChain3 = realChain;
            } catch (Throwable th2) {
                e = th2;
                if (1 != 0) {
                }
                throw e;
            }
            if (followUp == null) {
                streamAllocation.release();
                return response;
            }
            Util.closeQuietly(response.body());
            int followUpCount3 = followUpCount2 + 1;
            if (followUpCount3 > 20) {
                streamAllocation.release();
                throw new ProtocolException("Too many follow-up requests: " + followUpCount3);
            }
            if (followUp.body() instanceof UnrepeatableRequestBody) {
                streamAllocation.release();
                throw new HttpRetryException("Cannot retry streamed HTTP body", response.code());
            }
            if (sameConnection(response, followUp.url())) {
                realChain2 = realChain3;
                followUpCount = followUpCount3;
                if (streamAllocation.codec() != null) {
                    throw new IllegalStateException("Closing the body of " + response + " didn't close its backing stream. Bad interceptor?");
                }
            } else {
                streamAllocation.release();
                realChain2 = realChain3;
                followUpCount = followUpCount3;
                StreamAllocation streamAllocation2 = new StreamAllocation(this.client.connectionPool(), createAddress(followUp.url()), call, eventListener, this.callStackTrace);
                this.streamAllocation = streamAllocation2;
                streamAllocation = streamAllocation2;
            }
            request2 = followUp;
            priorResponse = response;
            followUpCount2 = followUpCount;
            realChain3 = realChain2;
        }
        streamAllocation.release();
        throw new IOException("Canceled");
    }

    private Address createAddress(HttpUrl url) {
        SSLSocketFactory sslSocketFactory = null;
        HostnameVerifier hostnameVerifier = null;
        CertificatePinner certificatePinner = null;
        if (url.isHttps()) {
            sslSocketFactory = this.client.sslSocketFactory();
            hostnameVerifier = this.client.hostnameVerifier();
            certificatePinner = this.client.certificatePinner();
        }
        return new Address(url.host(), url.port(), this.client.dns(), this.client.socketFactory(), sslSocketFactory, hostnameVerifier, certificatePinner, this.client.proxyAuthenticator(), this.client.proxy(), this.client.protocols(), this.client.connectionSpecs(), this.client.proxySelector());
    }

    private boolean recover(IOException e, StreamAllocation streamAllocation, boolean requestSendStarted, Request userRequest) {
        streamAllocation.streamFailed(e);
        if (this.client.retryOnConnectionFailure()) {
            return !(requestSendStarted && requestIsUnrepeatable(e, userRequest)) && isRecoverable(e, requestSendStarted) && streamAllocation.hasMoreRoutes();
        }
        return false;
    }

    private boolean requestIsUnrepeatable(IOException e, Request userRequest) {
        return (userRequest.body() instanceof UnrepeatableRequestBody) || (e instanceof FileNotFoundException);
    }

    private boolean isRecoverable(IOException e, boolean requestSendStarted) {
        if (e instanceof ProtocolException) {
            return false;
        }
        return e instanceof InterruptedIOException ? (e instanceof SocketTimeoutException) && !requestSendStarted : (((e instanceof SSLHandshakeException) && (e.getCause() instanceof CertificateException)) || (e instanceof SSLPeerUnverifiedException)) ? false : true;
    }

    private Request followUpRequest(Response userResponse, Route route) throws IOException {
        String location;
        HttpUrl url;
        if (userResponse == null) {
            throw new IllegalStateException();
        }
        int responseCode = userResponse.code();
        String method = userResponse.request().method();
        if (responseCode == 307 || responseCode == 308) {
            if (!method.equals("GET") && !method.equals("HEAD")) {
                return null;
            }
        } else {
            if (responseCode == 401) {
                return this.client.authenticator().authenticate(route, userResponse);
            }
            if (responseCode == 503) {
                if ((userResponse.priorResponse() == null || userResponse.priorResponse().code() != 503) && retryAfter(userResponse, Integer.MAX_VALUE) == 0) {
                    return userResponse.request();
                }
                return null;
            }
            if (responseCode == 407) {
                Proxy selectedProxy = route.proxy();
                if (selectedProxy.type() != Proxy.Type.HTTP) {
                    throw new ProtocolException("Received HTTP_PROXY_AUTH (407) code while not using proxy");
                }
                return this.client.proxyAuthenticator().authenticate(route, userResponse);
            }
            if (responseCode == 408) {
                if (!this.client.retryOnConnectionFailure() || (userResponse.request().body() instanceof UnrepeatableRequestBody)) {
                    return null;
                }
                if ((userResponse.priorResponse() == null || userResponse.priorResponse().code() != 408) && retryAfter(userResponse, 0) <= 0) {
                    return userResponse.request();
                }
                return null;
            }
            switch (responseCode) {
                case HttpStatus.SC_MULTIPLE_CHOICES /* 300 */:
                case HttpStatus.SC_MOVED_PERMANENTLY /* 301 */:
                case HttpStatus.SC_MOVED_TEMPORARILY /* 302 */:
                case HttpStatus.SC_SEE_OTHER /* 303 */:
                    break;
                default:
                    return null;
            }
        }
        if (!this.client.followRedirects() || (location = userResponse.header(cz.msebera.android.httpclient.HttpHeaders.LOCATION)) == null || (url = userResponse.request().url().resolve(location)) == null) {
            return null;
        }
        boolean sameScheme = url.scheme().equals(userResponse.request().url().scheme());
        if (!sameScheme && !this.client.followSslRedirects()) {
            return null;
        }
        Request.Builder requestBuilder = userResponse.request().newBuilder();
        if (HttpMethod.permitsRequestBody(method)) {
            boolean maintainBody = HttpMethod.redirectsWithBody(method);
            if (HttpMethod.redirectsToGet(method)) {
                requestBuilder.method("GET", null);
            } else {
                RequestBody requestBody = maintainBody ? userResponse.request().body() : null;
                requestBuilder.method(method, requestBody);
            }
            if (!maintainBody) {
                requestBuilder.removeHeader("Transfer-Encoding");
                requestBuilder.removeHeader("Content-Length");
                requestBuilder.removeHeader("Content-Type");
            }
        }
        if (!sameConnection(userResponse, url)) {
            requestBuilder.removeHeader("Authorization");
        }
        return requestBuilder.url(url).build();
    }

    private int retryAfter(Response userResponse, int defaultDelay) {
        String header = userResponse.header(cz.msebera.android.httpclient.HttpHeaders.RETRY_AFTER);
        if (header == null) {
            return defaultDelay;
        }
        if (header.matches("\\d+")) {
            return Integer.valueOf(header).intValue();
        }
        return Integer.MAX_VALUE;
    }

    private boolean sameConnection(Response response, HttpUrl followUp) {
        HttpUrl url = response.request().url();
        return url.host().equals(followUp.host()) && url.port() == followUp.port() && url.scheme().equals(followUp.scheme());
    }
}
