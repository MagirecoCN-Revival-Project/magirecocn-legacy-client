package backtraceio.library.watchdog;

/* loaded from: classes.dex */
public interface OnApplicationNotRespondingEvent {
    void onEvent(BacktraceWatchdogTimeoutException exception);
}
