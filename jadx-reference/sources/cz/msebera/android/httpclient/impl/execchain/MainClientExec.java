package cz.msebera.android.httpclient.impl.execchain;

import cz.msebera.android.httpclient.ConnectionReuseStrategy;
import cz.msebera.android.httpclient.HttpClientConnection;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpEntityEnclosingRequest;
import cz.msebera.android.httpclient.HttpException;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.auth.AuthProtocolState;
import cz.msebera.android.httpclient.auth.AuthState;
import cz.msebera.android.httpclient.client.AuthenticationStrategy;
import cz.msebera.android.httpclient.client.NonRepeatableRequestException;
import cz.msebera.android.httpclient.client.UserTokenHandler;
import cz.msebera.android.httpclient.client.config.RequestConfig;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpExecutionAware;
import cz.msebera.android.httpclient.client.methods.HttpRequestWrapper;
import cz.msebera.android.httpclient.client.protocol.HttpClientContext;
import cz.msebera.android.httpclient.conn.ConnectionKeepAliveStrategy;
import cz.msebera.android.httpclient.conn.ConnectionRequest;
import cz.msebera.android.httpclient.conn.HttpClientConnectionManager;
import cz.msebera.android.httpclient.conn.routing.BasicRouteDirector;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.conn.routing.HttpRouteDirector;
import cz.msebera.android.httpclient.conn.routing.RouteTracker;
import cz.msebera.android.httpclient.entity.BufferedHttpEntity;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.impl.auth.HttpAuthenticator;
import cz.msebera.android.httpclient.impl.conn.ConnectionShutdownException;
import cz.msebera.android.httpclient.message.BasicHttpRequest;
import cz.msebera.android.httpclient.protocol.HttpProcessor;
import cz.msebera.android.httpclient.protocol.HttpRequestExecutor;
import cz.msebera.android.httpclient.protocol.ImmutableHttpProcessor;
import cz.msebera.android.httpclient.protocol.RequestTargetHost;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.EntityUtils;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class MainClientExec implements ClientExecChain {
    private final HttpAuthenticator authenticator;
    private final HttpClientConnectionManager connManager;
    private final ConnectionKeepAliveStrategy keepAliveStrategy;
    public HttpClientAndroidLog log;
    private final AuthenticationStrategy proxyAuthStrategy;
    private final HttpProcessor proxyHttpProcessor;
    private final HttpRequestExecutor requestExecutor;
    private final ConnectionReuseStrategy reuseStrategy;
    private final HttpRouteDirector routeDirector;
    private final AuthenticationStrategy targetAuthStrategy;
    private final UserTokenHandler userTokenHandler;

    public MainClientExec(HttpRequestExecutor httpRequestExecutor, HttpClientConnectionManager httpClientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy, HttpProcessor httpProcessor, AuthenticationStrategy authenticationStrategy, AuthenticationStrategy authenticationStrategy2, UserTokenHandler userTokenHandler) {
        this.log = new HttpClientAndroidLog(getClass());
        Args.notNull(httpRequestExecutor, "HTTP request executor");
        Args.notNull(httpClientConnectionManager, "Client connection manager");
        Args.notNull(connectionReuseStrategy, "Connection reuse strategy");
        Args.notNull(connectionKeepAliveStrategy, "Connection keep alive strategy");
        Args.notNull(httpProcessor, "Proxy HTTP processor");
        Args.notNull(authenticationStrategy, "Target authentication strategy");
        Args.notNull(authenticationStrategy2, "Proxy authentication strategy");
        Args.notNull(userTokenHandler, "User token handler");
        this.authenticator = new HttpAuthenticator();
        this.routeDirector = new BasicRouteDirector();
        this.requestExecutor = httpRequestExecutor;
        this.connManager = httpClientConnectionManager;
        this.reuseStrategy = connectionReuseStrategy;
        this.keepAliveStrategy = connectionKeepAliveStrategy;
        this.proxyHttpProcessor = httpProcessor;
        this.targetAuthStrategy = authenticationStrategy;
        this.proxyAuthStrategy = authenticationStrategy2;
        this.userTokenHandler = userTokenHandler;
    }

    public MainClientExec(HttpRequestExecutor httpRequestExecutor, HttpClientConnectionManager httpClientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy, AuthenticationStrategy authenticationStrategy, AuthenticationStrategy authenticationStrategy2, UserTokenHandler userTokenHandler) {
        this(httpRequestExecutor, httpClientConnectionManager, connectionReuseStrategy, connectionKeepAliveStrategy, new ImmutableHttpProcessor(new RequestTargetHost()), authenticationStrategy, authenticationStrategy2, userTokenHandler);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r26v0, resolved type: cz.msebera.android.httpclient.client.methods.HttpRequestWrapper */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00e3, code lost:
    
        if (r28.isAborted() != false) goto L212;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00eb, code lost:
    
        throw new cz.msebera.android.httpclient.impl.execchain.RequestAbortedException(r5);
     */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // cz.msebera.android.httpclient.impl.execchain.ClientExecChain
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public CloseableHttpResponse execute(HttpRoute httpRoute, HttpRequestWrapper httpRequestWrapper, HttpClientContext httpClientContext, HttpExecutionAware httpExecutionAware) throws IOException, HttpException {
        RuntimeException runtimeException;
        ConnectionHolder connectionHolder;
        IOException iOException;
        HttpException httpException;
        int i;
        ConnectionHolder connectionHolder2;
        HttpClientConnection httpClientConnection;
        Object obj;
        AuthState authState;
        String str;
        HttpResponse httpResponse;
        Object obj2;
        AuthState authState2;
        HttpResponse httpResponse2;
        String str2;
        HttpRoute httpRoute2 = httpRoute;
        HttpExecutionAware httpExecutionAware2 = httpExecutionAware;
        Args.notNull(httpRoute2, "HTTP route");
        Args.notNull(httpRequestWrapper, "HTTP request");
        Args.notNull(httpClientContext, "HTTP context");
        AuthState targetAuthState = httpClientContext.getTargetAuthState();
        if (targetAuthState == null) {
            targetAuthState = new AuthState();
            httpClientContext.setAttribute("http.auth.target-scope", targetAuthState);
        }
        AuthState authState3 = targetAuthState;
        AuthState proxyAuthState = httpClientContext.getProxyAuthState();
        if (proxyAuthState == null) {
            proxyAuthState = new AuthState();
            httpClientContext.setAttribute("http.auth.proxy-scope", proxyAuthState);
        }
        AuthState authState4 = proxyAuthState;
        if (httpRequestWrapper instanceof HttpEntityEnclosingRequest) {
            RequestEntityProxy.enhance((HttpEntityEnclosingRequest) httpRequestWrapper);
        }
        Object userToken = httpClientContext.getUserToken();
        ConnectionRequest requestConnection = this.connManager.requestConnection(httpRoute2, userToken);
        String str3 = "Request aborted";
        if (httpExecutionAware2 != null) {
            if (httpExecutionAware.isAborted()) {
                requestConnection.cancel();
                throw new RequestAbortedException("Request aborted");
            }
            httpExecutionAware2.setCancellable(requestConnection);
        }
        RequestConfig requestConfig = httpClientContext.getRequestConfig();
        try {
            int connectionRequestTimeout = requestConfig.getConnectionRequestTimeout();
            HttpClientConnection httpClientConnection2 = requestConnection.get(connectionRequestTimeout > 0 ? connectionRequestTimeout : 0L, TimeUnit.MILLISECONDS);
            httpClientContext.setAttribute("http.connection", httpClientConnection2);
            if (requestConfig.isStaleConnectionCheckEnabled() && httpClientConnection2.isOpen()) {
                this.log.debug("Stale connection check");
                if (httpClientConnection2.isStale()) {
                    this.log.debug("Stale connection detected");
                    httpClientConnection2.close();
                }
            }
            ConnectionHolder connectionHolder3 = new ConnectionHolder(this.log, this.connManager, httpClientConnection2);
            if (httpExecutionAware2 != null) {
                try {
                    try {
                        httpExecutionAware2.setCancellable(connectionHolder3);
                    } catch (ConnectionShutdownException e) {
                        InterruptedIOException interruptedIOException = new InterruptedIOException("Connection has been shut down");
                        interruptedIOException.initCause(e);
                        throw interruptedIOException;
                    }
                } catch (HttpException e2) {
                    httpException = e2;
                    connectionHolder = connectionHolder3;
                    connectionHolder.abortConnection();
                    throw httpException;
                } catch (IOException e3) {
                    iOException = e3;
                    connectionHolder = connectionHolder3;
                    connectionHolder.abortConnection();
                    throw iOException;
                } catch (RuntimeException e4) {
                    runtimeException = e4;
                    connectionHolder = connectionHolder3;
                    connectionHolder.abortConnection();
                    throw runtimeException;
                }
            }
            int i2 = 1;
            int i3 = 1;
            while (true) {
                if (i3 > i2 && !RequestEntityProxy.isRepeatable(httpRequestWrapper)) {
                    throw new NonRepeatableRequestException("Cannot retry request with a non-repeatable request entity.");
                }
                try {
                    if (httpClientConnection2.isOpen()) {
                        i = i3;
                        connectionHolder2 = connectionHolder3;
                        httpClientConnection = httpClientConnection2;
                        obj = userToken;
                        authState = authState4;
                        str = str3;
                    } else {
                        HttpClientAndroidLog httpClientAndroidLog = this.log;
                        i = i3;
                        StringBuilder sb = new StringBuilder();
                        connectionHolder2 = connectionHolder3;
                        try {
                            sb.append("Opening connection ");
                            sb.append(httpRoute2);
                            httpClientAndroidLog.debug(sb.toString());
                            AuthState authState5 = authState4;
                            httpClientConnection = httpClientConnection2;
                            authState = authState4;
                            str = str3;
                            obj = userToken;
                            try {
                                establishRoute(authState5, httpClientConnection2, httpRoute, httpRequestWrapper, httpClientContext);
                            } catch (TunnelRefusedException e5) {
                                if (this.log.isDebugEnabled()) {
                                    this.log.debug(e5.getMessage());
                                }
                                httpResponse = e5.getResponse();
                                connectionHolder = connectionHolder2;
                            }
                        } catch (HttpException e6) {
                            httpException = e6;
                            connectionHolder = connectionHolder2;
                            connectionHolder.abortConnection();
                            throw httpException;
                        } catch (IOException e7) {
                            iOException = e7;
                            connectionHolder = connectionHolder2;
                            connectionHolder.abortConnection();
                            throw iOException;
                        } catch (RuntimeException e8) {
                            runtimeException = e8;
                            connectionHolder = connectionHolder2;
                            connectionHolder.abortConnection();
                            throw runtimeException;
                        }
                    }
                    try {
                        int socketTimeout = requestConfig.getSocketTimeout();
                        if (socketTimeout >= 0) {
                            httpClientConnection.setSocketTimeout(socketTimeout);
                        }
                        if (httpExecutionAware2 != null && httpExecutionAware.isAborted()) {
                            throw new RequestAbortedException(str);
                        }
                        if (this.log.isDebugEnabled()) {
                            this.log.debug("Executing request " + httpRequestWrapper.getRequestLine());
                        }
                        if (!httpRequestWrapper.containsHeader("Authorization")) {
                            if (this.log.isDebugEnabled()) {
                                this.log.debug("Target auth state: " + authState3.getState());
                            }
                            this.authenticator.generateAuthResponse(httpRequestWrapper, authState3, httpClientContext);
                        }
                        if (httpRequestWrapper.containsHeader("Proxy-Authorization") || httpRoute.isTunnelled()) {
                            authState2 = authState;
                        } else {
                            if (this.log.isDebugEnabled()) {
                                this.log.debug("Proxy auth state: " + authState.getState());
                            }
                            authState2 = authState;
                            this.authenticator.generateAuthResponse(httpRequestWrapper, authState2, httpClientContext);
                        }
                        HttpResponse execute = this.requestExecutor.execute(httpRequestWrapper, httpClientConnection, httpClientContext);
                        if (this.reuseStrategy.keepAlive(execute, httpClientContext)) {
                            long keepAliveDuration = this.keepAliveStrategy.getKeepAliveDuration(execute, httpClientContext);
                            if (this.log.isDebugEnabled()) {
                                if (keepAliveDuration > 0) {
                                    str2 = "for " + keepAliveDuration + " " + TimeUnit.MILLISECONDS;
                                } else {
                                    str2 = "indefinitely";
                                }
                                httpResponse2 = execute;
                                this.log.debug("Connection can be kept alive " + str2);
                            } else {
                                httpResponse2 = execute;
                            }
                            connectionHolder = connectionHolder2;
                            try {
                                connectionHolder.setValidFor(keepAliveDuration, TimeUnit.MILLISECONDS);
                                connectionHolder.markReusable();
                            } catch (HttpException e9) {
                                e = e9;
                                httpException = e;
                                connectionHolder.abortConnection();
                                throw httpException;
                            } catch (IOException e10) {
                                e = e10;
                                iOException = e;
                                connectionHolder.abortConnection();
                                throw iOException;
                            } catch (RuntimeException e11) {
                                e = e11;
                                runtimeException = e;
                                connectionHolder.abortConnection();
                                throw runtimeException;
                            }
                        } else {
                            httpResponse2 = execute;
                            connectionHolder = connectionHolder2;
                            connectionHolder.markNonReusable();
                        }
                        HttpResponse httpResponse3 = httpResponse2;
                        AuthState authState6 = authState2;
                        if (!needAuthentication(authState3, authState2, httpRoute, httpResponse3, httpClientContext)) {
                            httpResponse = httpResponse3;
                            break;
                        }
                        HttpEntity entity = httpResponse3.getEntity();
                        if (connectionHolder.isReusable()) {
                            EntityUtils.consume(entity);
                        } else {
                            httpClientConnection.close();
                            if (authState6.getState() == AuthProtocolState.SUCCESS && authState6.getAuthScheme() != null && authState6.getAuthScheme().isConnectionBased()) {
                                this.log.debug("Resetting proxy auth state");
                                authState6.reset();
                            }
                            if (authState3.getState() == AuthProtocolState.SUCCESS && authState3.getAuthScheme() != null && authState3.getAuthScheme().isConnectionBased()) {
                                this.log.debug("Resetting target auth state");
                                authState3.reset();
                            }
                        }
                        HttpRequest original = httpRequestWrapper.getOriginal();
                        if (!original.containsHeader("Authorization")) {
                            httpRequestWrapper.removeHeaders("Authorization");
                        }
                        if (!original.containsHeader("Proxy-Authorization")) {
                            httpRequestWrapper.removeHeaders("Proxy-Authorization");
                        }
                        i3 = i + 1;
                        httpClientConnection2 = httpClientConnection;
                        connectionHolder3 = connectionHolder;
                        str3 = str;
                        authState4 = authState6;
                        userToken = obj;
                        i2 = 1;
                        httpRoute2 = httpRoute;
                        httpExecutionAware2 = httpExecutionAware;
                    } catch (HttpException e12) {
                        e = e12;
                        connectionHolder = connectionHolder2;
                    } catch (IOException e13) {
                        e = e13;
                        connectionHolder = connectionHolder2;
                    } catch (RuntimeException e14) {
                        e = e14;
                        connectionHolder = connectionHolder2;
                    }
                } catch (HttpException e15) {
                    e = e15;
                    connectionHolder = connectionHolder3;
                } catch (IOException e16) {
                    e = e16;
                    connectionHolder = connectionHolder3;
                } catch (RuntimeException e17) {
                    e = e17;
                    connectionHolder = connectionHolder3;
                }
            }
            if (obj == null) {
                obj2 = this.userTokenHandler.getUserToken(httpClientContext);
                httpClientContext.setAttribute("http.user-token", obj2);
            } else {
                obj2 = obj;
            }
            if (obj2 != null) {
                connectionHolder.setState(obj2);
            }
            HttpEntity entity2 = httpResponse.getEntity();
            if (entity2 != null && entity2.isStreaming()) {
                return new HttpResponseProxy(httpResponse, connectionHolder);
            }
            connectionHolder.releaseConnection();
            return new HttpResponseProxy(httpResponse, null);
        } catch (InterruptedException e18) {
            Thread.currentThread().interrupt();
            throw new RequestAbortedException("Request aborted", e18);
        } catch (ExecutionException e19) {
            ExecutionException executionException = e19;
            Throwable cause = executionException.getCause();
            if (cause != null) {
                executionException = cause;
            }
            throw new RequestAbortedException("Request execution failed", executionException);
        }
    }

    void establishRoute(AuthState authState, HttpClientConnection httpClientConnection, HttpRoute httpRoute, HttpRequest httpRequest, HttpClientContext httpClientContext) throws HttpException, IOException {
        int nextStep;
        int connectTimeout = httpClientContext.getRequestConfig().getConnectTimeout();
        RouteTracker routeTracker = new RouteTracker(httpRoute);
        do {
            HttpRoute route = routeTracker.toRoute();
            nextStep = this.routeDirector.nextStep(httpRoute, route);
            switch (nextStep) {
                case -1:
                    throw new HttpException("Unable to establish route: planned = " + httpRoute + "; current = " + route);
                case 0:
                    this.connManager.routeComplete(httpClientConnection, httpRoute, httpClientContext);
                    break;
                case 1:
                    this.connManager.connect(httpClientConnection, httpRoute, connectTimeout > 0 ? connectTimeout : 0, httpClientContext);
                    routeTracker.connectTarget(httpRoute.isSecure());
                    break;
                case 2:
                    this.connManager.connect(httpClientConnection, httpRoute, connectTimeout > 0 ? connectTimeout : 0, httpClientContext);
                    routeTracker.connectProxy(httpRoute.getProxyHost(), false);
                    break;
                case 3:
                    boolean createTunnelToTarget = createTunnelToTarget(authState, httpClientConnection, httpRoute, httpRequest, httpClientContext);
                    this.log.debug("Tunnel to target created.");
                    routeTracker.tunnelTarget(createTunnelToTarget);
                    break;
                case 4:
                    int hopCount = route.getHopCount() - 1;
                    boolean createTunnelToProxy = createTunnelToProxy(httpRoute, hopCount, httpClientContext);
                    this.log.debug("Tunnel to proxy created.");
                    routeTracker.tunnelProxy(httpRoute.getHopTarget(hopCount), createTunnelToProxy);
                    break;
                case 5:
                    this.connManager.upgrade(httpClientConnection, httpRoute, httpClientContext);
                    routeTracker.layerProtocol(httpRoute.isSecure());
                    break;
                default:
                    throw new IllegalStateException("Unknown step indicator " + nextStep + " from RouteDirector.");
            }
        } while (nextStep > 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0093, code lost:
    
        if (r16.reuseStrategy.keepAlive(r7, r21) == false) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00a4, code lost:
    
        r18.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0095, code lost:
    
        r16.log.debug("Connection kept alive");
        cz.msebera.android.httpclient.util.EntityUtils.consume(r7.getEntity());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean createTunnelToTarget(AuthState authState, HttpClientConnection httpClientConnection, HttpRoute httpRoute, HttpRequest httpRequest, HttpClientContext httpClientContext) throws HttpException, IOException {
        HttpResponse httpResponse;
        RequestConfig requestConfig = httpClientContext.getRequestConfig();
        int connectTimeout = requestConfig.getConnectTimeout();
        HttpHost targetHost = httpRoute.getTargetHost();
        HttpHost proxyHost = httpRoute.getProxyHost();
        BasicHttpRequest basicHttpRequest = new BasicHttpRequest("CONNECT", targetHost.toHostString(), httpRequest.getProtocolVersion());
        this.requestExecutor.preProcess(basicHttpRequest, this.proxyHttpProcessor, httpClientContext);
        while (true) {
            HttpResponse httpResponse2 = null;
            while (true) {
                if (httpResponse2 == null) {
                    if (!httpClientConnection.isOpen()) {
                        this.connManager.connect(httpClientConnection, httpRoute, connectTimeout > 0 ? connectTimeout : 0, httpClientContext);
                    }
                    basicHttpRequest.removeHeaders("Proxy-Authorization");
                    this.authenticator.generateAuthResponse(basicHttpRequest, authState, httpClientContext);
                    HttpResponse execute = this.requestExecutor.execute(basicHttpRequest, httpClientConnection, httpClientContext);
                    if (execute.getStatusLine().getStatusCode() < 200) {
                        throw new HttpException("Unexpected response to CONNECT request: " + execute.getStatusLine());
                    }
                    if (!requestConfig.isAuthenticationEnabled()) {
                        httpResponse = execute;
                    } else {
                        if (this.authenticator.isAuthenticationRequested(proxyHost, execute, this.proxyAuthStrategy, authState, httpClientContext) && this.authenticator.handleAuthChallenge(proxyHost, execute, this.proxyAuthStrategy, authState, httpClientContext)) {
                            break;
                        }
                        httpResponse = execute;
                    }
                    httpResponse2 = httpResponse;
                } else {
                    if (httpResponse2.getStatusLine().getStatusCode() <= 299) {
                        return false;
                    }
                    HttpEntity entity = httpResponse2.getEntity();
                    if (entity != null) {
                        httpResponse2.setEntity(new BufferedHttpEntity(entity));
                    }
                    httpClientConnection.close();
                    throw new TunnelRefusedException("CONNECT refused by proxy: " + httpResponse2.getStatusLine(), httpResponse2);
                }
            }
        }
    }

    private boolean createTunnelToProxy(HttpRoute httpRoute, int i, HttpClientContext httpClientContext) throws HttpException {
        throw new HttpException("Proxy chains are not supported.");
    }

    private boolean needAuthentication(AuthState authState, AuthState authState2, HttpRoute httpRoute, HttpResponse httpResponse, HttpClientContext httpClientContext) {
        if (!httpClientContext.getRequestConfig().isAuthenticationEnabled()) {
            return false;
        }
        HttpHost targetHost = httpClientContext.getTargetHost();
        if (targetHost == null) {
            targetHost = httpRoute.getTargetHost();
        }
        if (targetHost.getPort() < 0) {
            targetHost = new HttpHost(targetHost.getHostName(), httpRoute.getTargetHost().getPort(), targetHost.getSchemeName());
        }
        boolean isAuthenticationRequested = this.authenticator.isAuthenticationRequested(targetHost, httpResponse, this.targetAuthStrategy, authState, httpClientContext);
        HttpHost proxyHost = httpRoute.getProxyHost();
        if (proxyHost == null) {
            proxyHost = httpRoute.getTargetHost();
        }
        boolean isAuthenticationRequested2 = this.authenticator.isAuthenticationRequested(proxyHost, httpResponse, this.proxyAuthStrategy, authState2, httpClientContext);
        if (isAuthenticationRequested) {
            return this.authenticator.handleAuthChallenge(targetHost, httpResponse, this.targetAuthStrategy, authState, httpClientContext);
        }
        if (!isAuthenticationRequested2) {
            return false;
        }
        return this.authenticator.handleAuthChallenge(proxyHost, httpResponse, this.proxyAuthStrategy, authState2, httpClientContext);
    }
}
