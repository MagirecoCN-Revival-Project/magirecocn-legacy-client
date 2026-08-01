package backtraceio.library.models.json;

import backtraceio.library.logger.BacktraceLogger;
import backtraceio.library.models.BacktraceStackFrame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
public class SourceCodeData {
    private static final transient String LOG_TAG = "SourceCodeData";
    public Map<String, SourceCode> data = new HashMap();

    public SourceCodeData(ArrayList<BacktraceStackFrame> exceptionStack) {
        String str = LOG_TAG;
        BacktraceLogger.d(str, "Initialization source code data");
        if (exceptionStack == null || exceptionStack.size() == 0) {
            BacktraceLogger.w(str, "Exception stack is null or empty");
            return;
        }
        Iterator<BacktraceStackFrame> it = exceptionStack.iterator();
        while (it.hasNext()) {
            BacktraceStackFrame next = it.next();
            if (next == null || next.sourceCode.equals("")) {
                BacktraceLogger.w(LOG_TAG, "Stack frame is null or sourceCode is empty");
            } else {
                this.data.put(next.sourceCode, new SourceCode(next));
            }
        }
    }
}
