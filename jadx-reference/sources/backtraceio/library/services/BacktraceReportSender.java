package backtraceio.library.services;

import backtraceio.library.common.BacktraceSerializeHelper;
import backtraceio.library.common.BacktraceStringHelper;
import backtraceio.library.common.MultiFormRequestHelper;
import backtraceio.library.common.RequestHelper;
import backtraceio.library.events.OnServerErrorEventListener;
import backtraceio.library.logger.BacktraceLogger;
import backtraceio.library.models.BacktraceResult;
import backtraceio.library.models.json.BacktraceReport;
import backtraceio.library.models.metrics.EventsPayload;
import backtraceio.library.models.metrics.EventsResult;
import backtraceio.library.models.types.BacktraceResultStatus;
import backtraceio.library.models.types.HttpException;
import cz.msebera.android.httpclient.client.cache.HeaderConstants;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.protocol.HTTP;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/* loaded from: classes.dex */
class BacktraceReportSender {
    private static final int CHUNK_SIZE = 131072;
    private static final String LOG_TAG = "BacktraceReportSender";

    BacktraceReportSender() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static BacktraceResult sendReport(String serverUrl, String json, List<String> attachments, BacktraceReport report, OnServerErrorEventListener errorCallback) {
        BacktraceResult OnError;
        HttpURLConnection httpURLConnection;
        String str;
        int responseCode;
        HttpURLConnection httpURLConnection2 = null;
        try {
            try {
                try {
                    httpURLConnection = (HttpURLConnection) new URL(serverUrl).openConnection();
                } catch (Exception e) {
                    BacktraceLogger.e(LOG_TAG, "Disconnecting HttpUrlConnection failed", e);
                    return BacktraceResult.OnError(report, e);
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            httpURLConnection.setRequestMethod(HttpPost.METHOD_NAME);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setChunkedStreamingMode(131072);
            httpURLConnection.setRequestProperty("Connection", HTTP.CONN_KEEP_ALIVE);
            httpURLConnection.setRequestProperty("Cache-Control", HeaderConstants.CACHE_CONTROL_NO_CACHE);
            httpURLConnection.setRequestProperty("Content-Type", MultiFormRequestHelper.getContentType());
            str = LOG_TAG;
            BacktraceLogger.d(str, "HttpURLConnection successfully initialized");
            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            MultiFormRequestHelper.addJson(dataOutputStream, json);
            MultiFormRequestHelper.addFiles(dataOutputStream, attachments);
            MultiFormRequestHelper.addEndOfRequest(dataOutputStream);
            dataOutputStream.flush();
            dataOutputStream.close();
            responseCode = httpURLConnection.getResponseCode();
            BacktraceLogger.d(str, "Received response status from Backtrace API for HTTP request is: " + responseCode);
        } catch (Exception e3) {
            e = e3;
            httpURLConnection2 = httpURLConnection;
            if (errorCallback != null) {
                BacktraceLogger.d(LOG_TAG, "Custom handler on server error");
                errorCallback.onEvent(e);
            }
            String str2 = LOG_TAG;
            BacktraceLogger.e(str2, "Sending HTTP request failed to Backtrace API", e);
            OnError = BacktraceResult.OnError(report, e);
            if (httpURLConnection2 == null) {
                return OnError;
            }
            httpURLConnection2.disconnect();
            BacktraceLogger.d(str2, "Disconnecting HttpUrlConnection successful");
            return OnError;
        } catch (Throwable th2) {
            th = th2;
            httpURLConnection2 = httpURLConnection;
            if (httpURLConnection2 != null) {
                try {
                    httpURLConnection2.disconnect();
                    BacktraceLogger.d(LOG_TAG, "Disconnecting HttpUrlConnection successful");
                } catch (Exception e4) {
                    BacktraceLogger.e(LOG_TAG, "Disconnecting HttpUrlConnection failed", e4);
                    BacktraceResult.OnError(report, e4);
                }
            }
            throw th;
        }
        if (responseCode == 200) {
            OnError = BacktraceSerializeHelper.backtraceResultFromJson(getResponse(httpURLConnection));
            OnError.setBacktraceReport(report);
            if (httpURLConnection == null) {
                return OnError;
            }
            httpURLConnection.disconnect();
            BacktraceLogger.d(str, "Disconnecting HttpUrlConnection successful");
            return OnError;
        }
        String response = getResponse(httpURLConnection);
        if (BacktraceStringHelper.isNullOrEmpty(response)) {
            response = httpURLConnection.getResponseMessage();
        }
        throw new HttpException(Integer.valueOf(responseCode), String.format("%s: %s", Integer.valueOf(responseCode), response));
    }

    public static EventsResult sendEvents(String serverUrl, String json, EventsPayload payload, OnServerErrorEventListener errorCallback) {
        EventsResult OnError;
        HttpURLConnection httpURLConnection;
        String str;
        HttpURLConnection httpURLConnection2 = null;
        int i = -1;
        try {
            try {
                try {
                    httpURLConnection = (HttpURLConnection) new URL(serverUrl).openConnection();
                } catch (Exception e) {
                    e = e;
                }
            } catch (Throwable th) {
                th = th;
            }
            try {
                httpURLConnection.setRequestMethod(HttpPost.METHOD_NAME);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestProperty("Connection", HTTP.CONN_KEEP_ALIVE);
                httpURLConnection.setRequestProperty("Content-Type", RequestHelper.getContentType());
                str = LOG_TAG;
                BacktraceLogger.d(str, "HttpURLConnection successfully initialized");
                DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                RequestHelper.addJson(dataOutputStream, json);
                RequestHelper.addEndOfRequest(dataOutputStream);
                dataOutputStream.flush();
                dataOutputStream.close();
                i = httpURLConnection.getResponseCode();
                BacktraceLogger.d(str, "Received response status from Backtrace API for HTTP request is: " + i);
            } catch (Exception e2) {
                e = e2;
                httpURLConnection2 = httpURLConnection;
                if (errorCallback != null) {
                    BacktraceLogger.d(LOG_TAG, "Custom handler on server error");
                    errorCallback.onEvent(e);
                }
                String str2 = LOG_TAG;
                BacktraceLogger.e(str2, "Sending HTTP request failed to Backtrace API", e);
                BacktraceLogger.e(str2, "Failed HTTP request URL " + serverUrl);
                OnError = EventsResult.OnError(payload, e, -1);
                if (httpURLConnection2 == null) {
                    return OnError;
                }
                httpURLConnection2.disconnect();
                BacktraceLogger.d(str2, "Disconnecting HttpUrlConnection successful");
                return OnError;
            } catch (Throwable th2) {
                th = th2;
                httpURLConnection2 = httpURLConnection;
                if (httpURLConnection2 != null) {
                    try {
                        httpURLConnection2.disconnect();
                        BacktraceLogger.d(LOG_TAG, "Disconnecting HttpUrlConnection successful");
                    } catch (Exception e3) {
                        BacktraceLogger.e(LOG_TAG, "Disconnecting HttpUrlConnection failed", e3);
                        EventsResult.OnError(payload, e3, -1);
                    }
                }
                throw th;
            }
            if (i == 200) {
                OnError = new EventsResult(payload, httpURLConnection.getResponseMessage(), BacktraceResultStatus.Ok, i);
                if (httpURLConnection == null) {
                    return OnError;
                }
                httpURLConnection.disconnect();
                BacktraceLogger.d(str, "Disconnecting HttpUrlConnection successful");
                return OnError;
            }
            String response = getResponse(httpURLConnection);
            if (BacktraceStringHelper.isNullOrEmpty(response)) {
                response = httpURLConnection.getResponseMessage();
            }
            throw new HttpException(Integer.valueOf(i), String.format("%s: %s", Integer.valueOf(i), response));
        } catch (Exception e4) {
            BacktraceLogger.e(LOG_TAG, "Disconnecting HttpUrlConnection failed", e4);
            return EventsResult.OnError(payload, e4, i);
        }
    }

    private static String getResponse(HttpURLConnection urlConnection) throws IOException {
        InputStream errorStream;
        BacktraceLogger.d(LOG_TAG, "Reading response from HTTP request");
        if (urlConnection.getResponseCode() < 400) {
            errorStream = urlConnection.getInputStream();
        } else {
            errorStream = urlConnection.getErrorStream();
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(errorStream));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine != null) {
                sb.append(readLine);
            } else {
                bufferedReader.close();
                return sb.toString();
            }
        }
    }
}
