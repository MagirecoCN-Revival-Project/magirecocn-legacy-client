package backtraceio.library.models.json;

import backtraceio.library.models.BacktraceStackFrame;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class ThreadInformation {

    @SerializedName("fault")
    private final Boolean fault;

    @SerializedName("name")
    public String name;

    @SerializedName("stack")
    private final ArrayList<BacktraceStackFrame> stack;

    private ThreadInformation(String threadName, Boolean fault, ArrayList<BacktraceStackFrame> stack) {
        this.stack = stack == null ? new ArrayList<>() : stack;
        this.name = threadName;
        this.fault = fault;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ThreadInformation(Thread thread, ArrayList<BacktraceStackFrame> stack, Boolean currentThread) {
        this(thread.getName().toLowerCase(), currentThread, stack);
    }
}
