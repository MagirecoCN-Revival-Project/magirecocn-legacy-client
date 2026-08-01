package backtraceio.library.models;

import backtraceio.library.logger.BacktraceLogger;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class BacktraceStackTrace {
    private static final transient String LOG_TAG = "BacktraceStackTrace";
    private final Exception exception;
    private final ArrayList<BacktraceStackFrame> stackFrames = new ArrayList<>();

    public BacktraceStackTrace(Exception exception) {
        this.exception = exception;
        initialize();
    }

    public ArrayList<BacktraceStackFrame> getStackFrames() {
        return this.stackFrames;
    }

    public Exception getException() {
        return this.exception;
    }

    private void initialize() {
        Exception exc = this.exception;
        StackTraceElement[] stackTrace = exc != null ? exc.getStackTrace() : Thread.currentThread().getStackTrace();
        if (stackTrace == null || stackTrace.length == 0) {
            BacktraceLogger.w(LOG_TAG, "StackTraceElements are null or empty");
        } else {
            setStacktraceInformation(stackTrace);
        }
    }

    private void setStacktraceInformation(StackTraceElement[] frames) {
        if (frames == null || frames.length == 0) {
            BacktraceLogger.w(LOG_TAG, "StackTraceFrames are null or empty");
            return;
        }
        for (StackTraceElement stackTraceElement : frames) {
            if (stackTraceElement != null && stackTraceElement.getFileName() != null && stackTraceElement.getFileName().startsWith("Backtrace")) {
                BacktraceLogger.d(LOG_TAG, "Skipping frame because it comes from inside the Backtrace library");
            } else {
                this.stackFrames.add(new BacktraceStackFrame(stackTraceElement));
            }
        }
    }
}
