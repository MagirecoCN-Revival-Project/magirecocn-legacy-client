package backtraceio.library.models;

import backtraceio.library.models.json.BacktraceReport;
import backtraceio.library.models.types.BacktraceResultStatus;
import com.google.gson.annotations.SerializedName;

/* loaded from: classes.dex */
public class BacktraceResult {
    private BacktraceReport backtraceReport;
    public String message;

    @SerializedName("_rxid")
    public String rxId;
    public BacktraceResultStatus status;

    public BacktraceResult() {
        this.status = BacktraceResultStatus.Ok;
    }

    public BacktraceResult(BacktraceReport report, String message, BacktraceResultStatus status) {
        this.status = BacktraceResultStatus.Ok;
        setBacktraceReport(report);
        this.message = message;
        this.status = status;
    }

    public static BacktraceResult OnError(BacktraceReport report, Exception exception) {
        return new BacktraceResult(report, exception.getMessage(), BacktraceResultStatus.ServerError);
    }

    public BacktraceReport getBacktraceReport() {
        return this.backtraceReport;
    }

    public void setBacktraceReport(BacktraceReport backtraceReport) {
        this.backtraceReport = backtraceReport;
    }
}
