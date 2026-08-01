package cz.msebera.android.httpclient.impl.execchain;

import cz.msebera.android.httpclient.ConnectionReuseStrategy;
import cz.msebera.android.httpclient.HttpClientConnection;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpException;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.ProtocolException;
import cz.msebera.android.httpclient.client.config.RequestConfig;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpExecutionAware;
import cz.msebera.android.httpclient.client.methods.HttpRequestWrapper;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.client.protocol.HttpClientContext;
import cz.msebera.android.httpclient.client.protocol.RequestClientConnControl;
import cz.msebera.android.httpclient.client.utils.URIUtils;
import cz.msebera.android.httpclient.conn.ConnectionKeepAliveStrategy;
import cz.msebera.android.httpclient.conn.ConnectionRequest;
import cz.msebera.android.httpclient.conn.HttpClientConnectionManager;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.impl.conn.ConnectionShutdownException;
import cz.msebera.android.httpclient.protocol.HttpProcessor;
import cz.msebera.android.httpclient.protocol.HttpRequestExecutor;
import cz.msebera.android.httpclient.protocol.ImmutableHttpProcessor;
import cz.msebera.android.httpclient.protocol.RequestContent;
import cz.msebera.android.httpclient.protocol.RequestTargetHost;
import cz.msebera.android.httpclient.protocol.RequestUserAgent;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.VersionInfo;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class MinimalClientExec implements ClientExecChain {
    private final HttpClientConnectionManager connManager;
    private final HttpProcessor httpProcessor;
    private final ConnectionKeepAliveStrategy keepAliveStrategy;
    public HttpClientAndroidLog log = new HttpClientAndroidLog(getClass());
    private final HttpRequestExecutor requestExecutor;
    private final ConnectionReuseStrategy reuseStrategy;

    public MinimalClientExec(HttpRequestExecutor httpRequestExecutor, HttpClientConnectionManager httpClientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy) {
        Args.notNull(httpRequestExecutor, "HTTP request executor");
        Args.notNull(httpClientConnectionManager, "Client connection manager");
        Args.notNull(connectionReuseStrategy, "Connection reuse strategy");
        Args.notNull(connectionKeepAliveStrategy, "Connection keep alive strategy");
        this.httpProcessor = new ImmutableHttpProcessor(new RequestContent(), new RequestTargetHost(), new RequestClientConnControl(), new RequestUserAgent(VersionInfo.getUserAgent("Apache-HttpClient", "cz.msebera.android.httpclient.client", getClass())));
        this.requestExecutor = httpRequestExecutor;
        this.connManager = httpClientConnectionManager;
        this.reuseStrategy = connectionReuseStrategy;
        this.keepAliveStrategy = connectionKeepAliveStrategy;
    }

    static void rewriteRequestURI(HttpRequestWrapper httpRequestWrapper, HttpRoute httpRoute) throws ProtocolException {
        URI rewriteURI;
        try {
            URI uri = httpRequestWrapper.getURI();
            if (uri != null) {
                if (uri.isAbsolute()) {
                    rewriteURI = URIUtils.rewriteURI(uri, null, true);
                } else {
                    rewriteURI = URIUtils.rewriteURI(uri);
                }
                httpRequestWrapper.setURI(rewriteURI);
            }
        } catch (URISyntaxException e) {
            throw new ProtocolException("Invalid URI: " + httpRequestWrapper.getRequestLine().getUri(), e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00ac A[Catch: RuntimeException -> 0x010a, IOException -> 0x010f, HttpException -> 0x0114, ConnectionShutdownException -> 0x0119, TryCatch #3 {HttpException -> 0x0114, ConnectionShutdownException -> 0x0119, IOException -> 0x010f, RuntimeException -> 0x010a, blocks: (B:49:0x004f, B:51:0x0055, B:52:0x0059, B:53:0x0061, B:17:0x0062, B:19:0x0068, B:22:0x0072, B:24:0x007a, B:26:0x0080, B:27:0x0083, B:29:0x008b, B:31:0x0097, B:33:0x00ac, B:34:0x00b0, B:36:0x00dc, B:37:0x00ee, B:39:0x00f4, B:42:0x00fb, B:44:0x0101, B:46:0x00eb), top: B:48:0x004f }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00dc A[Catch: RuntimeException -> 0x010a, IOException -> 0x010f, HttpException -> 0x0114, ConnectionShutdownException -> 0x0119, TryCatch #3 {HttpException -> 0x0114, ConnectionShutdownException -> 0x0119, IOException -> 0x010f, RuntimeException -> 0x010a, blocks: (B:49:0x004f, B:51:0x0055, B:52:0x0059, B:53:0x0061, B:17:0x0062, B:19:0x0068, B:22:0x0072, B:24:0x007a, B:26:0x0080, B:27:0x0083, B:29:0x008b, B:31:0x0097, B:33:0x00ac, B:34:0x00b0, B:36:0x00dc, B:37:0x00ee, B:39:0x00f4, B:42:0x00fb, B:44:0x0101, B:46:0x00eb), top: B:48:0x004f }] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00eb A[Catch: RuntimeException -> 0x010a, IOException -> 0x010f, HttpException -> 0x0114, ConnectionShutdownException -> 0x0119, TryCatch #3 {HttpException -> 0x0114, ConnectionShutdownException -> 0x0119, IOException -> 0x010f, RuntimeException -> 0x010a, blocks: (B:49:0x004f, B:51:0x0055, B:52:0x0059, B:53:0x0061, B:17:0x0062, B:19:0x0068, B:22:0x0072, B:24:0x007a, B:26:0x0080, B:27:0x0083, B:29:0x008b, B:31:0x0097, B:33:0x00ac, B:34:0x00b0, B:36:0x00dc, B:37:0x00ee, B:39:0x00f4, B:42:0x00fb, B:44:0x0101, B:46:0x00eb), top: B:48:0x004f }] */
    @Override // cz.msebera.android.httpclient.impl.execchain.ClientExecChain
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public CloseableHttpResponse execute(HttpRoute httpRoute, HttpRequestWrapper httpRequestWrapper, HttpClientContext httpClientContext, HttpExecutionAware httpExecutionAware) throws IOException, HttpException {
        Object obj;
        HttpResponse execute;
        HttpEntity entity;
        Args.notNull(httpRoute, "HTTP route");
        Args.notNull(httpRequestWrapper, "HTTP request");
        Args.notNull(httpClientContext, "HTTP context");
        rewriteRequestURI(httpRequestWrapper, httpRoute);
        ConnectionRequest requestConnection = this.connManager.requestConnection(httpRoute, null);
        if (httpExecutionAware != null) {
            if (httpExecutionAware.isAborted()) {
                requestConnection.cancel();
                throw new RequestAbortedException("Request aborted");
            }
            httpExecutionAware.setCancellable(requestConnection);
        }
        RequestConfig requestConfig = httpClientContext.getRequestConfig();
        try {
            int connectionRequestTimeout = requestConfig.getConnectionRequestTimeout();
            HttpClientConnection httpClientConnection = requestConnection.get(connectionRequestTimeout > 0 ? connectionRequestTimeout : 0L, TimeUnit.MILLISECONDS);
            ConnectionHolder connectionHolder = new ConnectionHolder(this.log, this.connManager, httpClientConnection);
            if (httpExecutionAware != null) {
                try {
                    if (httpExecutionAware.isAborted()) {
                        connectionHolder.close();
                        throw new RequestAbortedException("Request aborted");
                    }
                    httpExecutionAware.setCancellable(connectionHolder);
                } catch (HttpException e) {
                    connectionHolder.abortConnection();
                    throw e;
                } catch (ConnectionShutdownException e2) {
                    InterruptedIOException interruptedIOException = new InterruptedIOException("Connection has been shut down");
                    interruptedIOException.initCause(e2);
                    throw interruptedIOException;
                } catch (IOException e3) {
                    connectionHolder.abortConnection();
                    throw e3;
                } catch (RuntimeException e4) {
                    connectionHolder.abortConnection();
                    throw e4;
                }
            }
            if (!httpClientConnection.isOpen()) {
                int connectTimeout = requestConfig.getConnectTimeout();
                HttpClientConnectionManager httpClientConnectionManager = this.connManager;
                if (connectTimeout <= 0) {
                    connectTimeout = 0;
                }
                httpClientConnectionManager.connect(httpClientConnection, httpRoute, connectTimeout, httpClientContext);
                this.connManager.routeComplete(httpClientConnection, httpRoute, httpClientContext);
            }
            int socketTimeout = requestConfig.getSocketTimeout();
            if (socketTimeout >= 0) {
                httpClientConnection.setSocketTimeout(socketTimeout);
            }
            HttpRequest original = httpRequestWrapper.getOriginal();
            if (original instanceof HttpUriRequest) {
                URI uri = ((HttpUriRequest) original).getURI();
                if (uri.isAbsolute()) {
                    obj = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
                    if (obj == null) {
                        obj = httpRoute.getTargetHost();
                    }
                    httpClientContext.setAttribute("http.target_host", obj);
                    httpClientContext.setAttribute("http.request", httpRequestWrapper);
                    httpClientContext.setAttribute("http.connection", httpClientConnection);
                    httpClientContext.setAttribute("http.route", httpRoute);
                    this.httpProcessor.process(httpRequestWrapper, httpClientContext);
                    execute = this.requestExecutor.execute(httpRequestWrapper, httpClientConnection, httpClientContext);
                    this.httpProcessor.process(execute, httpClientContext);
                    if (!this.reuseStrategy.keepAlive(execute, httpClientContext)) {
                        connectionHolder.setValidFor(this.keepAliveStrategy.getKeepAliveDuration(execute, httpClientContext), TimeUnit.MILLISECONDS);
                        connectionHolder.markReusable();
                    } else {
                        connectionHolder.markNonReusable();
                    }
                    entity = execute.getEntity();
                    if (entity != null && entity.isStreaming()) {
                        return new HttpResponseProxy(execute, connectionHolder);
                    }
                    connectionHolder.releaseConnection();
                    return new HttpResponseProxy(execute, null);
                }
            }
            obj = null;
            if (obj == null) {
            }
            httpClientContext.setAttribute("http.target_host", obj);
            httpClientContext.setAttribute("http.request", httpRequestWrapper);
            httpClientContext.setAttribute("http.connection", httpClientConnection);
            httpClientContext.setAttribute("http.route", httpRoute);
            this.httpProcessor.process(httpRequestWrapper, httpClientContext);
            execute = this.requestExecutor.execute(httpRequestWrapper, httpClientConnection, httpClientContext);
            this.httpProcessor.process(execute, httpClientContext);
            if (!this.reuseStrategy.keepAlive(execute, httpClientContext)) {
            }
            entity = execute.getEntity();
            if (entity != null) {
                return new HttpResponseProxy(execute, connectionHolder);
            }
            connectionHolder.releaseConnection();
            return new HttpResponseProxy(execute, null);
        } catch (InterruptedException e5) {
            Thread.currentThread().interrupt();
            throw new RequestAbortedException("Request aborted", e5);
        } catch (ExecutionException e6) {
            e = e6;
            Throwable cause = e.getCause();
            if (cause != null) {
                e = cause;
            }
            throw new RequestAbortedException("Request execution failed", e);
        }
    }
}
