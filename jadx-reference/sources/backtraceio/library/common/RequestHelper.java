package backtraceio.library.common;

import backtraceio.library.logger.BacktraceLogger;
import com.loopj.android.http.RequestParams;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class RequestHelper {
    private static final String CRLF = "\r\n";
    private static final String ENCODING = "utf-8";
    private static final transient String LOG_TAG = "RequestHelper";

    public static String getContentType() {
        return RequestParams.APPLICATION_JSON;
    }

    public static void addJson(OutputStream outputStream, String json) throws IOException {
        if (BacktraceStringHelper.isNullOrEmpty(json)) {
            BacktraceLogger.w(LOG_TAG, "JSON is null or empty");
        } else if (outputStream == null) {
            BacktraceLogger.w(LOG_TAG, "Output stream is null");
        } else {
            outputStream.write(json.getBytes(ENCODING));
        }
    }

    public static void addEndOfRequest(OutputStream outputStream) throws IOException {
        if (outputStream == null) {
            BacktraceLogger.w(LOG_TAG, "Output stream is null");
        } else {
            outputStream.write(CRLF.getBytes());
        }
    }
}
