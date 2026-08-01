package backtraceio.library.models.json;

import backtraceio.library.models.BacktraceStackFrame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class ThreadData {
    public HashMap<String, ThreadInformation> threadInformation = new HashMap<>();
    private String mainThread = "";

    public ThreadData(ArrayList<BacktraceStackFrame> exceptionStack) {
        generateCurrentThreadInformation(exceptionStack);
        processThreads();
    }

    public String getMainThread() {
        return this.mainThread;
    }

    private void generateCurrentThreadInformation(ArrayList<BacktraceStackFrame> exceptionStack) {
        Thread currentThread = Thread.currentThread();
        String lowerCase = currentThread.getName().toLowerCase();
        this.mainThread = lowerCase;
        this.threadInformation.put(lowerCase, new ThreadInformation(currentThread, exceptionStack, (Boolean) true));
    }

    private void processThreads() {
        for (Map.Entry<Thread, StackTraceElement[]> entry : Thread.getAllStackTraces().entrySet()) {
            if (entry.getKey() != null) {
                Thread key = entry.getKey();
                StackTraceElement[] value = entry.getValue();
                String lowerCase = key.getName().toLowerCase();
                ArrayList arrayList = new ArrayList();
                if (!getMainThread().equals(lowerCase)) {
                    if (value != null && value.length != 0) {
                        for (StackTraceElement stackTraceElement : value) {
                            arrayList.add(new BacktraceStackFrame(stackTraceElement));
                        }
                    }
                    this.threadInformation.put(lowerCase, new ThreadInformation(key, (ArrayList<BacktraceStackFrame>) arrayList, (Boolean) false));
                }
            }
        }
    }
}
