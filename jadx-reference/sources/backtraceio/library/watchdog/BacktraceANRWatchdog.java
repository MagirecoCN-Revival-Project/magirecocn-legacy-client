package backtraceio.library.watchdog;

import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import backtraceio.library.BacktraceClient;
import backtraceio.library.logger.BacktraceLogger;
import java.util.Calendar;

/* loaded from: classes.dex */
public class BacktraceANRWatchdog extends Thread {
    private static final transient int DEFAULT_ANR_TIMEOUT = 5000;
    private static final transient String LOG_TAG = "BacktraceANRWatchdog";
    private final BacktraceClient backtraceClient;
    private final boolean debug;
    private final Handler mainThreadHandler;
    private OnApplicationNotRespondingEvent onApplicationNotRespondingEvent;
    private volatile boolean shouldStop;
    private final int timeout;

    public BacktraceANRWatchdog(BacktraceClient client) {
        this(client, DEFAULT_ANR_TIMEOUT);
    }

    public BacktraceANRWatchdog(BacktraceClient client, int timeout) {
        this(client, timeout, false);
    }

    public BacktraceANRWatchdog(BacktraceClient client, int timeout, boolean debug) {
        this.mainThreadHandler = new Handler(Looper.getMainLooper());
        this.shouldStop = false;
        BacktraceLogger.d(LOG_TAG, "Start monitoring ANR");
        this.backtraceClient = client;
        this.timeout = timeout;
        this.debug = debug;
        start();
    }

    public void setOnApplicationNotRespondingEvent(OnApplicationNotRespondingEvent onApplicationNotRespondingEvent) {
        this.onApplicationNotRespondingEvent = onApplicationNotRespondingEvent;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        if (this.debug && (Debug.isDebuggerConnected() || Debug.waitingForDebugger())) {
            BacktraceLogger.w(LOG_TAG, "Detected a debugger connection. ANR Watchdog is disabled");
            return;
        }
        Boolean bool = false;
        while (!this.shouldStop && !isInterrupted()) {
            String date = Calendar.getInstance().getTime().toString();
            String str = LOG_TAG;
            BacktraceLogger.d(str, "ANR WATCHDOG - " + date);
            final BacktraceThreadWatcher backtraceThreadWatcher = new BacktraceThreadWatcher(0, 0);
            this.mainThreadHandler.post(new Runnable() { // from class: backtraceio.library.watchdog.BacktraceANRWatchdog.1
                @Override // java.lang.Runnable
                public void run() {
                    backtraceThreadWatcher.tickCounter();
                }
            });
            try {
                Thread.sleep(this.timeout);
                backtraceThreadWatcher.tickPrivateCounter();
                if (backtraceThreadWatcher.getCounter() == backtraceThreadWatcher.getPrivateCounter()) {
                    bool = false;
                    BacktraceLogger.d(str, "ANR is not detected");
                } else if (!bool.booleanValue()) {
                    bool = true;
                    BacktraceWatchdogShared.sendReportCauseBlockedThread(this.backtraceClient, Looper.getMainLooper().getThread(), this.onApplicationNotRespondingEvent, str);
                }
            } catch (InterruptedException e) {
                BacktraceLogger.e(LOG_TAG, "Thread is interrupted", e);
                return;
            }
        }
    }

    public void stopMonitoringAnr() {
        BacktraceLogger.d(LOG_TAG, "Stop monitoring ANR");
        this.shouldStop = true;
    }
}
