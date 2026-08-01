package backtraceio.library.models;

import backtraceio.library.BacktraceCredentials;
import backtraceio.library.services.BacktraceMetrics;

/* loaded from: classes.dex */
public class BacktraceMetricsSettings {
    private final String baseUrl;
    private final int timeBetweenRetriesMillis;
    private final long timeIntervalMillis;
    private final String token;
    private final String universe;

    public BacktraceMetricsSettings(BacktraceCredentials credentials) {
        this(credentials, BacktraceMetrics.defaultBaseUrl, BacktraceMetrics.defaultTimeIntervalMs, 10000);
    }

    public BacktraceMetricsSettings(BacktraceCredentials credentials, String baseUrl) {
        this(credentials, baseUrl, BacktraceMetrics.defaultTimeIntervalMs, 10000);
    }

    public BacktraceMetricsSettings(BacktraceCredentials credentials, long timeIntervalMillis) {
        this(credentials, BacktraceMetrics.defaultBaseUrl, timeIntervalMillis, 10000);
    }

    public BacktraceMetricsSettings(BacktraceCredentials credentials, String baseUrl, long timeIntervalMillis) {
        this(credentials, baseUrl, timeIntervalMillis, 10000);
    }

    public BacktraceMetricsSettings(BacktraceCredentials credentials, String baseUrl, long timeIntervalMillis, int timeBetweenRetriesMillis) {
        this.universe = credentials.getUniverseName();
        this.token = credentials.getSubmissionToken();
        this.baseUrl = baseUrl;
        this.timeIntervalMillis = timeIntervalMillis;
        this.timeBetweenRetriesMillis = timeBetweenRetriesMillis;
    }

    public String getUniverseName() {
        return this.universe;
    }

    public String getToken() {
        return this.token;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public long getTimeIntervalMillis() {
        return this.timeIntervalMillis;
    }

    public int getTimeBetweenRetriesMillis() {
        return this.timeBetweenRetriesMillis;
    }

    public String getSubmissionUrl(String urlPrefix) {
        return getBaseUrl() + "/" + urlPrefix + "/submit?token=" + getToken() + "&universe=" + getUniverseName();
    }
}
