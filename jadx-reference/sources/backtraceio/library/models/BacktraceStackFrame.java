package backtraceio.library.models;

import backtraceio.library.logger.BacktraceLogger;
import cn.thinkingdata.core.router.TRouterMap;
import com.google.gson.annotations.SerializedName;
import java.util.UUID;

/* loaded from: classes.dex */
public class BacktraceStackFrame {
    private static final transient String LOG_TAG = "BacktraceStackFrame";

    @SerializedName("funcName")
    public String functionName;

    @SerializedName("line")
    public Integer line;

    @SerializedName("sourceCode")
    public String sourceCode;
    public transient String sourceCodeFileName;

    public BacktraceStackFrame() {
        this.line = null;
    }

    public BacktraceStackFrame(StackTraceElement frame) {
        this.line = null;
        if (frame == null || frame.getMethodName() == null) {
            BacktraceLogger.w(LOG_TAG, "Frame or method name is null");
            return;
        }
        this.functionName = frame.getClassName() + TRouterMap.DOT + frame.getMethodName();
        this.sourceCodeFileName = frame.getFileName();
        this.sourceCode = UUID.randomUUID().toString();
        this.line = frame.getLineNumber() > 0 ? Integer.valueOf(frame.getLineNumber()) : null;
    }
}
