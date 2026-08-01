package cn.thinkingdata.analytics.utils;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import cn.thinkingdata.analytics.utils.g;
import cn.thinkingdata.core.utils.Base64Coder;
import cn.thinkingdata.core.utils.TDLog;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.utils.URLEncodedUtils;
import cz.msebera.android.httpclient.protocol.HTTP;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.Map;
import java.util.zip.GZIPOutputStream;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

/* loaded from: classes.dex */
public class b implements g {
    private String a(String str) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(str.getBytes().length);
        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        gZIPOutputStream.write(str.getBytes());
        gZIPOutputStream.close();
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return new String(Base64Coder.encode(byteArray));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0167  */
    /* JADX WARN: Removed duplicated region for block: B:30:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0160 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0159 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0152 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x014b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r10v1, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r10v11 */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v5 */
    @Override // cn.thinkingdata.analytics.utils.g
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String a(String str, String str2, boolean z, SSLSocketFactory sSLSocketFactory, Map<String, String> map) {
        Throwable th;
        HttpURLConnection httpURLConnection;
        OutputStream outputStream;
        InputStream inputStream;
        ?? r10;
        BufferedOutputStream bufferedOutputStream;
        OutputStream outputStream2;
        InputStream inputStream2;
        BufferedOutputStream bufferedOutputStream2 = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            if (sSLSocketFactory != null) {
                try {
                    if (httpURLConnection instanceof HttpsURLConnection) {
                        ((HttpsURLConnection) httpURLConnection).setSSLSocketFactory(sSLSocketFactory);
                    }
                } catch (Throwable th2) {
                    th = th2;
                    outputStream = null;
                    inputStream = null;
                    r10 = inputStream;
                    if (bufferedOutputStream2 != null) {
                    }
                    if (outputStream != null) {
                    }
                    if (inputStream != null) {
                    }
                    if (r10 != 0) {
                    }
                    if (httpURLConnection != null) {
                    }
                }
            }
            try {
                if (str2 == null) {
                    throw new InvalidParameterException("Content is null");
                }
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setReadTimeout(AccessibilityNodeInfoCompat.EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_MAX_LENGTH);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod(HttpPost.METHOD_NAME);
                if (z) {
                    httpURLConnection.setRequestProperty("Content-Type", URLEncodedUtils.CONTENT_TYPE);
                    httpURLConnection.setUseCaches(false);
                    httpURLConnection.setRequestProperty("charset", "utf-8");
                } else {
                    httpURLConnection.setRequestProperty("Content-Type", HTTP.PLAIN_TEXT_TYPE);
                    try {
                        str2 = a(str2);
                    } catch (IOException e) {
                        throw new InvalidParameterException(e.getMessage());
                    }
                }
                if (map != null) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
                    }
                }
                httpURLConnection.setFixedLengthStreamingMode(str2.getBytes("UTF-8").length);
                outputStream = httpURLConnection.getOutputStream();
                try {
                    BufferedOutputStream bufferedOutputStream3 = new BufferedOutputStream(outputStream);
                    try {
                        bufferedOutputStream3.write(str2.getBytes("UTF-8"));
                        bufferedOutputStream3.flush();
                        bufferedOutputStream3.close();
                        try {
                            outputStream.close();
                            try {
                                int responseCode = httpURLConnection.getResponseCode();
                                TDLog.d("ThinkingAnalytics.HttpService", "ret_code:" + responseCode);
                                if (responseCode != 200) {
                                    throw new g.a("Service unavailable with response code: " + responseCode);
                                }
                                InputStream inputStream3 = httpURLConnection.getInputStream();
                                try {
                                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream3));
                                    try {
                                        StringBuilder sb = new StringBuilder();
                                        while (true) {
                                            String readLine = bufferedReader.readLine();
                                            if (readLine == null) {
                                                break;
                                            }
                                            sb.append(readLine);
                                        }
                                        inputStream3.close();
                                        bufferedReader.close();
                                        String sb2 = sb.toString();
                                        if (inputStream3 != null) {
                                            try {
                                                inputStream3.close();
                                            } catch (IOException unused) {
                                            }
                                        }
                                        try {
                                            bufferedReader.close();
                                        } catch (IOException unused2) {
                                        }
                                        if (httpURLConnection != null) {
                                            httpURLConnection.disconnect();
                                        }
                                        return sb2;
                                    } catch (Throwable th3) {
                                        r10 = bufferedReader;
                                        outputStream = null;
                                        inputStream = inputStream3;
                                        th = th3;
                                        if (bufferedOutputStream2 != null) {
                                        }
                                        if (outputStream != null) {
                                        }
                                        if (inputStream != null) {
                                        }
                                        if (r10 != 0) {
                                        }
                                        if (httpURLConnection != null) {
                                        }
                                    }
                                } catch (Throwable th4) {
                                    outputStream2 = null;
                                    bufferedOutputStream = null;
                                    inputStream2 = inputStream3;
                                    th = th4;
                                    OutputStream outputStream3 = outputStream2;
                                    inputStream = inputStream2;
                                    outputStream = outputStream3;
                                    bufferedOutputStream2 = bufferedOutputStream;
                                    r10 = 0;
                                    if (bufferedOutputStream2 != null) {
                                    }
                                    if (outputStream != null) {
                                    }
                                    if (inputStream != null) {
                                    }
                                    if (r10 != 0) {
                                    }
                                    if (httpURLConnection != null) {
                                    }
                                }
                            } catch (Throwable th5) {
                                th = th5;
                                inputStream2 = null;
                                outputStream2 = null;
                                bufferedOutputStream = null;
                            }
                        } catch (Throwable th6) {
                            th = th6;
                            outputStream2 = outputStream;
                            inputStream2 = null;
                            bufferedOutputStream = null;
                        }
                    } catch (Throwable th7) {
                        th = th7;
                        bufferedOutputStream = bufferedOutputStream3;
                        outputStream2 = outputStream;
                        inputStream2 = null;
                    }
                } catch (Throwable th8) {
                    th = th8;
                    inputStream = null;
                    r10 = inputStream;
                    if (bufferedOutputStream2 != null) {
                        try {
                            bufferedOutputStream2.close();
                        } catch (IOException unused3) {
                        }
                    }
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException unused4) {
                        }
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException unused5) {
                        }
                    }
                    if (r10 != 0) {
                        try {
                            r10.close();
                        } catch (IOException unused6) {
                        }
                    }
                    if (httpURLConnection != null) {
                        throw th;
                    }
                    httpURLConnection.disconnect();
                    throw th;
                }
            } catch (Throwable th9) {
                th = th9;
                outputStream = null;
            }
        } catch (Throwable th10) {
            th = th10;
            httpURLConnection = null;
            outputStream = null;
        }
    }
}
