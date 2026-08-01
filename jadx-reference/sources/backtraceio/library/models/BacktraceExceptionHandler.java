package backtraceio.library.models;

import backtraceio.library.BacktraceClient;
import backtraceio.library.events.OnServerResponseEventListener;
import backtraceio.library.logger.BacktraceLogger;
import backtraceio.library.models.json.BacktraceReport;
import java.lang.Thread;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes.dex */
public class BacktraceExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static final transient String LOG_TAG = "BacktraceExceptionHandler";
    private static Map<String, Object> customAttributes;
    private final BacktraceClient client;
    private final Thread.UncaughtExceptionHandler rootHandler;
    private final CountDownLatch signal = new CountDownLatch(1);

    private BacktraceExceptionHandler(BacktraceClient client) {
        BacktraceLogger.d(LOG_TAG, "BacktraceExceptionHandler initialization");
        this.client = client;
        this.rootHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public static void setCustomAttributes(Map<String, Object> attributes) {
        customAttributes = attributes;
    }

    public static void enable(BacktraceClient client) {
        new BacktraceExceptionHandler(client);
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(final Thread thread, final Throwable throwable) {
        OnServerResponseEventListener callbackToDefaultHandler = getCallbackToDefaultHandler(thread, throwable);
        if (throwable instanceof Exception) {
            String str = LOG_TAG;
            BacktraceLogger.e(str, "Sending uncaught exception to Backtrace API", throwable);
            BacktraceReport backtraceReport = new BacktraceReport((Exception) throwable, customAttributes);
            backtraceReport.attributes.put(BacktraceAttributeConsts.ErrorType, BacktraceAttributeConsts.UnhandledExceptionAttributeType);
            this.client.send(backtraceReport, callbackToDefaultHandler);
            BacktraceLogger.d(str, "Uncaught exception sent to Backtrace API");
        }
        BacktraceLogger.d(LOG_TAG, "Default uncaught exception handler");
        try {
            this.signal.await();
        } catch (Exception e) {
            BacktraceLogger.e(LOG_TAG, "Exception during waiting for response", e);
        }
    }

    private OnServerResponseEventListener getCallbackToDefaultHandler(final Thread thread, final Throwable throwable) {
        return new OnServerResponseEventListener() { // from class: backtraceio.library.models.BacktraceExceptionHandler.1
            @Override // backtraceio.library.events.OnServerResponseEventListener
            public void onEvent(BacktraceResult backtraceResult) {
                BacktraceLogger.d(BacktraceExceptionHandler.LOG_TAG, "Root handler event callback");
                BacktraceExceptionHandler.this.rootHandler.uncaughtException(thread, throwable);
                BacktraceExceptionHandler.this.signal.countDown();
            }
        };
    }
}
