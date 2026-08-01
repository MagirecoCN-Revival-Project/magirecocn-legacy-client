package com.loopj.android.http;

import com.adjust.sdk.Constants;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpVersion;
import cz.msebera.android.httpclient.conn.scheme.PlainSocketFactory;
import cz.msebera.android.httpclient.conn.scheme.Scheme;
import cz.msebera.android.httpclient.conn.scheme.SchemeRegistry;
import cz.msebera.android.httpclient.conn.ssl.SSLSocketFactory;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.impl.conn.tsccm.ThreadSafeClientConnManager;
import cz.msebera.android.httpclient.params.BasicHttpParams;
import cz.msebera.android.httpclient.params.HttpProtocolParams;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/* loaded from: classes.dex */
public class MySSLSocketFactory extends SSLSocketFactory {
    final SSLContext sslContext;

    public MySSLSocketFactory(KeyStore keyStore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        super(keyStore);
        SSLContext sSLContext = SSLContext.getInstance("TLS");
        this.sslContext = sSLContext;
        sSLContext.init(null, new TrustManager[]{new X509TrustManager() { // from class: com.loopj.android.http.MySSLSocketFactory.1
            @Override // javax.net.ssl.X509TrustManager
            public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            }

            @Override // javax.net.ssl.X509TrustManager
            public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            }

            @Override // javax.net.ssl.X509TrustManager
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        }}, null);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r1v1, resolved type: java.security.KeyStore */
    /* JADX DEBUG: Multi-variable search result rejected for r1v2, resolved type: java.security.KeyStore */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:40:0x004b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static KeyStore getKeystoreOfCA(InputStream inputStream) {
        BufferedInputStream bufferedInputStream;
        Certificate certificate;
        KeyStore keyStore;
        BufferedInputStream bufferedInputStream2 = null;
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            bufferedInputStream = new BufferedInputStream(inputStream);
            try {
                try {
                    certificate = certificateFactory.generateCertificate(bufferedInputStream);
                    try {
                        bufferedInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (CertificateException e2) {
                    e = e2;
                    e.printStackTrace();
                    if (bufferedInputStream != null) {
                        try {
                            bufferedInputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    certificate = null;
                    keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                    try {
                        keyStore.load(null, null);
                        keyStore.setCertificateEntry("ca", certificate);
                        return keyStore;
                    } catch (Exception e4) {
                        e = e4;
                        bufferedInputStream2 = keyStore;
                        e.printStackTrace();
                        return bufferedInputStream2;
                    }
                }
            } catch (Throwable th) {
                th = th;
                bufferedInputStream2 = bufferedInputStream;
                if (bufferedInputStream2 != null) {
                    try {
                        bufferedInputStream2.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (CertificateException e6) {
            e = e6;
            bufferedInputStream = null;
        } catch (Throwable th2) {
            th = th2;
            if (bufferedInputStream2 != null) {
            }
            throw th;
        }
        try {
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", certificate);
            return keyStore;
        } catch (Exception e7) {
            e = e7;
        }
    }

    public static KeyStore getKeystore() {
        KeyStore keyStore;
        Throwable th;
        try {
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            try {
                keyStore.load(null, null);
            } catch (Throwable th2) {
                th = th2;
                th.printStackTrace();
                return keyStore;
            }
        } catch (Throwable th3) {
            keyStore = null;
            th = th3;
        }
        return keyStore;
    }

    public static SSLSocketFactory getFixedSocketFactory() {
        try {
            MySSLSocketFactory mySSLSocketFactory = new MySSLSocketFactory(getKeystore());
            mySSLSocketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            return mySSLSocketFactory;
        } catch (Throwable th) {
            th.printStackTrace();
            return SSLSocketFactory.getSocketFactory();
        }
    }

    public static DefaultHttpClient getNewHttpClient(KeyStore keyStore) {
        try {
            MySSLSocketFactory mySSLSocketFactory = new MySSLSocketFactory(keyStore);
            SchemeRegistry schemeRegistry = new SchemeRegistry();
            schemeRegistry.register(new Scheme(HttpHost.DEFAULT_SCHEME_NAME, PlainSocketFactory.getSocketFactory(), 80));
            schemeRegistry.register(new Scheme(Constants.SCHEME, mySSLSocketFactory, 443));
            BasicHttpParams basicHttpParams = new BasicHttpParams();
            HttpProtocolParams.setVersion(basicHttpParams, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(basicHttpParams, "UTF-8");
            return new DefaultHttpClient(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
        } catch (Exception unused) {
            return new DefaultHttpClient();
        }
    }

    @Override // cz.msebera.android.httpclient.conn.ssl.SSLSocketFactory, cz.msebera.android.httpclient.conn.scheme.LayeredSocketFactory
    public Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException {
        return this.sslContext.getSocketFactory().createSocket(socket, str, i, z);
    }

    @Override // cz.msebera.android.httpclient.conn.ssl.SSLSocketFactory, cz.msebera.android.httpclient.conn.scheme.SocketFactory
    public Socket createSocket() throws IOException {
        return this.sslContext.getSocketFactory().createSocket();
    }

    public void fixHttpsURLConnection() {
        HttpsURLConnection.setDefaultSSLSocketFactory(this.sslContext.getSocketFactory());
    }
}
