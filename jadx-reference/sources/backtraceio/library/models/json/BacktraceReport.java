package backtraceio.library.models.json;

import android.content.Context;
import backtraceio.library.common.BacktraceTimeHelper;
import backtraceio.library.models.BacktraceAttributeConsts;
import backtraceio.library.models.BacktraceData;
import backtraceio.library.models.BacktraceStackFrame;
import backtraceio.library.models.BacktraceStackTrace;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes.dex */
public class BacktraceReport {
    public List<String> attachmentPaths;
    public Map<String, Object> attributes;
    public String classifier;
    public ArrayList<BacktraceStackFrame> diagnosticStack;
    public Exception exception;
    public Boolean exceptionTypeReport;
    public String message;
    public long timestamp;
    public UUID uuid;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BacktraceReport(String message) {
        this((Exception) null, (Map<String, Object>) null, (List<String>) null);
        this.message = message;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BacktraceReport(String message, Map<String, Object> attributes) {
        this((Exception) null, attributes, (List<String>) null);
        this.message = message;
    }

    public BacktraceReport(String message, List<String> attachmentPaths) {
        this(message, (Map<String, Object>) null, attachmentPaths);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BacktraceReport(String message, Map<String, Object> attributes, List<String> attachmentPaths) {
        this((Exception) null, attributes, attachmentPaths);
        this.message = message;
    }

    public BacktraceReport(Exception exception) {
        this(exception, (Map<String, Object>) null, (List<String>) null);
    }

    public BacktraceReport(Exception exception, Map<String, Object> attributes) {
        this(exception, attributes, (List<String>) null);
    }

    public BacktraceReport(Exception exception, List<String> attachmentPaths) {
        this(exception, (Map<String, Object>) null, attachmentPaths);
    }

    public BacktraceReport(Exception exception, Map<String, Object> attributes, List<String> attachmentPaths) {
        this.uuid = UUID.randomUUID();
        this.timestamp = BacktraceTimeHelper.getTimestampSeconds();
        this.exceptionTypeReport = false;
        this.classifier = "";
        this.attributes = attributes == null ? new HashMap<String, Object>() { // from class: backtraceio.library.models.json.BacktraceReport.1
        } : attributes;
        this.attachmentPaths = attachmentPaths == null ? new ArrayList<>() : attachmentPaths;
        this.exception = exception;
        this.exceptionTypeReport = Boolean.valueOf(exception != null);
        this.diagnosticStack = new BacktraceStackTrace(exception).getStackFrames();
        if (this.exceptionTypeReport.booleanValue() && exception != null) {
            this.classifier = exception.getClass().getCanonicalName();
        }
        setDefaultErrorTypeAttribute();
    }

    private void setDefaultErrorTypeAttribute() {
        if (this.attributes.containsKey(BacktraceAttributeConsts.ErrorType)) {
            return;
        }
        this.attributes.put(BacktraceAttributeConsts.ErrorType, this.exceptionTypeReport.booleanValue() ? BacktraceAttributeConsts.HandledExceptionAttributeType : BacktraceAttributeConsts.MessageAttributeType);
    }

    public static Map<String, Object> concatAttributes(BacktraceReport report, Map<String, Object> attributes) {
        Map<String, Object> map = report.attributes;
        if (map == null) {
            map = new HashMap<>();
        }
        if (attributes == null) {
            return map;
        }
        map.putAll(attributes);
        return map;
    }

    public BacktraceData toBacktraceData(Context context, Map<String, Object> clientAttributes) {
        return toBacktraceData(context, clientAttributes, false);
    }

    public BacktraceData toBacktraceData(Context context, Map<String, Object> clientAttributes, boolean isProguardEnabled) {
        BacktraceData backtraceData = new BacktraceData(context, this, clientAttributes);
        backtraceData.symbolication = isProguardEnabled ? "proguard" : null;
        return backtraceData;
    }
}
