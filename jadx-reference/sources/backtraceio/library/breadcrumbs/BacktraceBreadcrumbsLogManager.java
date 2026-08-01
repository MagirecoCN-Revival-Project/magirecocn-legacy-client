package backtraceio.library.breadcrumbs;

import backtraceio.library.enums.BacktraceBreadcrumbLevel;
import backtraceio.library.enums.BacktraceBreadcrumbType;
import backtraceio.library.logger.BacktraceLogger;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.IOException;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class BacktraceBreadcrumbsLogManager {
    private final BacktraceQueueFileHelper backtraceQueueFileHelper;
    private final String LOG_TAG = "BacktraceBreadcrumbsLogManager";
    private long breadcrumbId = System.currentTimeMillis();
    private final int maxMessageSizeBytes = 1024;
    private final int maxAttributeSizeBytes = 1024;

    public BacktraceBreadcrumbsLogManager(String breadcrumbLogPath, int maxQueueFileSizeBytes) throws IOException, NoSuchMethodException {
        this.backtraceQueueFileHelper = new BacktraceQueueFileHelper(breadcrumbLogPath, maxQueueFileSizeBytes);
    }

    public boolean addBreadcrumb(String message, Map<String, Object> attributes, BacktraceBreadcrumbType type, BacktraceBreadcrumbLevel level) {
        long currentTimeMillis = System.currentTimeMillis();
        String substring = message.substring(0, Math.min(message.length(), 1024));
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(AppMeasurement.Param.TIMESTAMP, currentTimeMillis);
            long j = this.breadcrumbId;
            this.breadcrumbId = 1 + j;
            jSONObject.put("id", j);
            jSONObject.put(FirebaseAnalytics.Param.LEVEL, level.toString());
            jSONObject.put(AppMeasurement.Param.TYPE, type.toString());
            jSONObject.put("message", substring);
            if (attributes != null) {
                JSONObject jSONObject2 = new JSONObject();
                int i = 0;
                for (Map.Entry<String, Object> entry : attributes.entrySet()) {
                    i += entry.getKey().length() + entry.getValue().toString().length();
                    if (i < 1024) {
                        jSONObject2.put(entry.getKey(), entry.getValue());
                    }
                }
                if (jSONObject2.length() > 0) {
                    jSONObject.put("attributes", jSONObject2);
                }
            }
            return this.backtraceQueueFileHelper.add(("\n" + jSONObject.toString().replace("\\n", "") + "\n").getBytes());
        } catch (Exception unused) {
            BacktraceLogger.e(this.LOG_TAG, "Could not create the breadcrumb JSON");
            return false;
        }
    }

    public boolean clear() {
        boolean clear = this.backtraceQueueFileHelper.clear();
        if (clear) {
            this.breadcrumbId = 0L;
        }
        return clear;
    }

    public void setCurrentBreadcrumbId(long breadcrumbId) {
        this.breadcrumbId = breadcrumbId;
    }

    public long getCurrentBreadcrumbId() {
        return this.breadcrumbId;
    }
}
