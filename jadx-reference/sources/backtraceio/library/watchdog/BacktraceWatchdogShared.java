package backtraceio.library.watchdog;

import backtraceio.library.BacktraceClient;
import backtraceio.library.logger.BacktraceLogger;
import backtraceio.library.models.BacktraceAttributeConsts;
import backtraceio.library.models.json.BacktraceReport;
import java.util.HashMap;

/* loaded from: classes.dex */
class BacktraceWatchdogShared {
    BacktraceWatchdogShared() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void sendReportCauseBlockedThread(BacktraceClient backtraceClient, Thread thread, OnApplicationNotRespondingEvent onApplicationNotRespondingEvent, String LOG_TAG) {
        BacktraceWatchdogTimeoutException backtraceWatchdogTimeoutException = new BacktraceWatchdogTimeoutException();
        backtraceWatchdogTimeoutException.setStackTrace(thread.getStackTrace());
        BacktraceLogger.e(LOG_TAG, "Blocked thread detected, sending a report", backtraceWatchdogTimeoutException);
        if (onApplicationNotRespondingEvent != null) {
            onApplicationNotRespondingEvent.onEvent(backtraceWatchdogTimeoutException);
        } else if (backtraceClient != null) {
            backtraceClient.send(new BacktraceReport(backtraceWatchdogTimeoutException, new HashMap<String, Object>() { // from class: backtraceio.library.watchdog.BacktraceWatchdogShared.1
                {
                    put(BacktraceAttributeConsts.ErrorType, BacktraceAttributeConsts.AnrAttributeType);
                }
            }));
        }
    }
}
