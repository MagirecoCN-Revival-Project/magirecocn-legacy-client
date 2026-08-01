package backtraceio.library.models;

import android.content.Context;
import backtraceio.library.BacktraceClient;
import backtraceio.library.common.FileHelper;
import backtraceio.library.logger.BacktraceLogger;
import backtraceio.library.models.json.Annotations;
import backtraceio.library.models.json.BacktraceAttributes;
import backtraceio.library.models.json.BacktraceReport;
import backtraceio.library.models.json.SourceCode;
import backtraceio.library.models.json.SourceCodeData;
import backtraceio.library.models.json.ThreadData;
import backtraceio.library.models.json.ThreadInformation;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class BacktraceData {
    private static final transient String LOG_TAG = "BacktraceData";

    @SerializedName("agentVersion")
    public String agentVersion;

    @SerializedName("annotations")
    public Map<String, Object> annotations;

    @SerializedName("attributes")
    public Map<String, String> attributes;

    @SerializedName("classifiers")
    public String[] classifiers;
    public transient Context context;

    @SerializedName("langVersion")
    public String langVersion;

    @SerializedName("mainThread")
    public String mainThread;
    public transient BacktraceReport report;

    @SerializedName("sourceCode")
    public Map<String, SourceCode> sourceCode;

    @SerializedName("symbolication")
    public String symbolication;

    @SerializedName("threads")
    Map<String, ThreadInformation> threadInformationMap;

    @SerializedName(AppMeasurement.Param.TIMESTAMP)
    public long timestamp;

    @SerializedName("uuid")
    public String uuid;

    @SerializedName("lang")
    public final String lang = "java";

    @SerializedName("agent")
    public final String agent = "backtrace-android";

    public BacktraceData(Context context, BacktraceReport report, Map<String, Object> clientAttributes) {
        if (report == null) {
            return;
        }
        this.context = context;
        this.report = report;
        setReportInformation();
        setThreadsInformation();
        setAttributes(clientAttributes);
    }

    public List<String> getAttachments() {
        return FileHelper.filterOutFiles(this.context, this.report.attachmentPaths);
    }

    private void setAnnotations(Map<String, Object> complexAttributes) {
        BacktraceLogger.d(LOG_TAG, "Setting annotations");
        Map<String, String> map = this.attributes;
        this.annotations = Annotations.getAnnotations((map == null || !map.containsKey("error.message")) ? null : this.attributes.get("error.message"), complexAttributes);
    }

    private void setAttributes(Map<String, Object> clientAttributes) {
        BacktraceLogger.d(LOG_TAG, "Setting attributes");
        BacktraceAttributes backtraceAttributes = new BacktraceAttributes(this.context, this.report, clientAttributes);
        this.attributes = backtraceAttributes.attributes;
        setAnnotations(backtraceAttributes.getComplexAttributes());
    }

    private void setReportInformation() {
        BacktraceLogger.d(LOG_TAG, "Setting report information");
        this.uuid = this.report.uuid.toString();
        this.timestamp = this.report.timestamp;
        this.classifiers = this.report.exceptionTypeReport.booleanValue() ? new String[]{this.report.classifier} : null;
        this.langVersion = System.getProperty("java.version");
        this.agentVersion = BacktraceClient.version;
    }

    private void setThreadsInformation() {
        BacktraceLogger.d(LOG_TAG, "Setting threads information");
        ThreadData threadData = new ThreadData(this.report.diagnosticStack);
        this.mainThread = threadData.getMainThread();
        this.threadInformationMap = threadData.threadInformation;
        SourceCodeData sourceCodeData = new SourceCodeData(this.report.diagnosticStack);
        this.sourceCode = sourceCodeData.data.isEmpty() ? null : sourceCodeData.data;
    }
}
