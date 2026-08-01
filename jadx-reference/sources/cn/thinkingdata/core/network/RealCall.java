package cn.thinkingdata.core.network;

import cn.thinkingdata.core.utils.TDLog;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.zip.GZIPOutputStream;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

/* loaded from: classes.dex */
public class RealCall implements Call {
    private static final String TAG = "ThinkingAnalytics.RealCall";
    final TEHttpClient client;
    final Request originalRequest;

    /* loaded from: classes.dex */
    class AsyncCall implements Runnable {
        private final TEHttpCallback responseCallback;

        public AsyncCall(TEHttpCallback tEHttpCallback) {
            this.responseCallback = tEHttpCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.responseCallback.onResponse(RealCall.this.performRequest());
            } catch (IOException e) {
                this.responseCallback.onError(e.getMessage());
            }
        }
    }

    private RealCall(TEHttpClient tEHttpClient, Request request) {
        this.client = tEHttpClient;
        this.originalRequest = request;
    }

    private String encodeData(String str) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(str.getBytes().length);
        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        gZIPOutputStream.write(str.getBytes());
        gZIPOutputStream.close();
        byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return "";
    }

    private HttpURLConnection getHttpURLConnection() {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(this.originalRequest.url).openConnection();
        SSLSocketFactory sSLSocketFactory = this.client.sslSocketFactory;
        if (sSLSocketFactory != null && (httpURLConnection instanceof HttpsURLConnection)) {
            ((HttpsURLConnection) httpURLConnection).setSSLSocketFactory(sSLSocketFactory);
        }
        httpURLConnection.setConnectTimeout(this.client.connectTimeout);
        httpURLConnection.setReadTimeout(this.client.readTimeout);
        if (HttpPost.METHOD_NAME.equals(this.originalRequest.method)) {
            httpURLConnection.setDoOutput(true);
        }
        httpURLConnection.setRequestMethod(this.originalRequest.method);
        return httpURLConnection;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static RealCall newRealCall(TEHttpClient tEHttpClient, Request request) {
        return new RealCall(tEHttpClient, request);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0101  */
    /* JADX WARN: Removed duplicated region for block: B:31:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00fa A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00f3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00ec A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00e5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String performRequest() {
        HttpURLConnection httpURLConnection;
        OutputStream outputStream;
        Throwable th;
        InputStream inputStream;
        BufferedReader bufferedReader;
        InputStream inputStream2;
        BufferedOutputStream bufferedOutputStream;
        BufferedOutputStream bufferedOutputStream2 = null;
        try {
            httpURLConnection = getHttpURLConnection();
            try {
                httpURLConnection.setUseCaches(this.originalRequest.useCache);
                setHeaders(httpURLConnection);
                if (HttpPost.METHOD_NAME.equals(this.originalRequest.method)) {
                    String str = this.originalRequest.body;
                    if (this.originalRequest.gzip) {
                        str = encodeData(str);
                    }
                    httpURLConnection.setFixedLengthStreamingMode(str.getBytes("UTF-8").length);
                    outputStream = httpURLConnection.getOutputStream();
                    try {
                        bufferedOutputStream = new BufferedOutputStream(outputStream);
                        try {
                            bufferedOutputStream.write(str.getBytes("UTF-8"));
                            bufferedOutputStream.flush();
                            bufferedOutputStream.close();
                            try {
                                outputStream.close();
                            } catch (Throwable th2) {
                                th = th2;
                                inputStream2 = null;
                                bufferedOutputStream = null;
                                BufferedOutputStream bufferedOutputStream3 = bufferedOutputStream;
                                th = th;
                                inputStream = inputStream2;
                                bufferedReader = null;
                                bufferedOutputStream2 = bufferedOutputStream3;
                                if (bufferedOutputStream2 != null) {
                                    try {
                                        bufferedOutputStream2.close();
                                    } catch (IOException unused) {
                                    }
                                }
                                if (outputStream != null) {
                                    try {
                                        outputStream.close();
                                    } catch (IOException unused2) {
                                    }
                                }
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException unused3) {
                                    }
                                }
                                if (bufferedReader != null) {
                                    try {
                                        bufferedReader.close();
                                    } catch (IOException unused4) {
                                    }
                                }
                                if (httpURLConnection != null) {
                                    throw th;
                                }
                                httpURLConnection.disconnect();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            inputStream2 = null;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        th = th;
                        inputStream = null;
                        bufferedReader = null;
                        if (bufferedOutputStream2 != null) {
                        }
                        if (outputStream != null) {
                        }
                        if (inputStream != null) {
                        }
                        if (bufferedReader != null) {
                        }
                        if (httpURLConnection != null) {
                        }
                    }
                }
                try {
                    int responseCode = httpURLConnection.getResponseCode();
                    TDLog.d(TAG, "ret_code:" + responseCode);
                    if (responseCode != 200) {
                        throw new IOException("Service unavailable with response code: " + responseCode);
                    }
                    inputStream = httpURLConnection.getInputStream();
                    try {
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        try {
                            StringBuilder sb = new StringBuilder();
                            while (true) {
                                String readLine = bufferedReader.readLine();
                                if (readLine == null) {
                                    break;
                                }
                                sb.append(readLine);
                            }
                            inputStream.close();
                            bufferedReader.close();
                            String sb2 = sb.toString();
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (IOException unused5) {
                                }
                            }
                            try {
                                bufferedReader.close();
                            } catch (IOException unused6) {
                            }
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                            return sb2;
                        } catch (Throwable th5) {
                            th = th5;
                            outputStream = null;
                            if (bufferedOutputStream2 != null) {
                            }
                            if (outputStream != null) {
                            }
                            if (inputStream != null) {
                            }
                            if (bufferedReader != null) {
                            }
                            if (httpURLConnection != null) {
                            }
                        }
                    } catch (Throwable th6) {
                        outputStream = null;
                        bufferedOutputStream = null;
                        inputStream2 = inputStream;
                        th = th6;
                        BufferedOutputStream bufferedOutputStream32 = bufferedOutputStream;
                        th = th;
                        inputStream = inputStream2;
                        bufferedReader = null;
                        bufferedOutputStream2 = bufferedOutputStream32;
                        if (bufferedOutputStream2 != null) {
                        }
                        if (outputStream != null) {
                        }
                        if (inputStream != null) {
                        }
                        if (bufferedReader != null) {
                        }
                        if (httpURLConnection != null) {
                        }
                    }
                } catch (Throwable th7) {
                    th = th7;
                    inputStream2 = null;
                    outputStream = null;
                    bufferedOutputStream = null;
                }
            } catch (Throwable th8) {
                th = th8;
                outputStream = null;
            }
        } catch (Throwable th9) {
            th = th9;
            httpURLConnection = null;
            outputStream = null;
        }
    }

    private void setHeaders(HttpURLConnection httpURLConnection) {
        Map<String, String> map = this.originalRequest.headers;
        if (map.size() > 0) {
            for (String str : map.keySet()) {
                httpURLConnection.setRequestProperty(str, map.get(str));
            }
        }
    }

    @Override // cn.thinkingdata.core.network.Call
    public void enqueue(TEHttpCallback tEHttpCallback) {
        tEHttpCallback.callBackOnMainThread = this.originalRequest.callBackOnMainThread;
        this.client.dispatcher.execute(new AsyncCall(tEHttpCallback));
    }

    @Override // cn.thinkingdata.core.network.Call
    public String execute() {
        return performRequest();
    }
}
