package backtraceio.library.watchdog;

import backtraceio.library.BacktraceClient;
import backtraceio.library.logger.BacktraceLogger;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class BacktraceWatchdog {
    private static final transient String LOG_TAG = "BacktraceWatchdog";
    private final BacktraceClient backtraceClient;
    private OnApplicationNotRespondingEvent onApplicationNotRespondingEvent;
    private final boolean sendException;
    private final Map<Thread, BacktraceThreadWatcher> threadsIdWatcher;

    public BacktraceWatchdog(BacktraceClient client, boolean sendException) {
        this.threadsIdWatcher = new HashMap();
        this.sendException = sendException;
        this.backtraceClient = client;
    }

    public BacktraceWatchdog(BacktraceClient client) {
        this(client, true);
    }

    public void setOnApplicationNotRespondingEvent(OnApplicationNotRespondingEvent onApplicationNotRespondingEvent) {
        this.onApplicationNotRespondingEvent = onApplicationNotRespondingEvent;
    }

    public boolean checkIsAnyThreadIsBlocked() {
        long timeout;
        long currentTimeMillis = System.currentTimeMillis();
        String l = Long.toString(currentTimeMillis);
        BacktraceLogger.d(LOG_TAG, "Checking watchdog. Timestamp: " + l);
        for (Map.Entry<Thread, BacktraceThreadWatcher> entry : this.threadsIdWatcher.entrySet()) {
            Thread key = entry.getKey();
            BacktraceThreadWatcher value = entry.getValue();
            if (key != null && value != null && key != Thread.currentThread() && key.isAlive() && value.isActive()) {
                if (value.getCounter() != value.getPrivateCounter()) {
                    value.setPrivateCounter(value.getCounter());
                    value.setLastTimestamp(currentTimeMillis);
                } else {
                    String str = LOG_TAG;
                    BacktraceLogger.w(str, String.format("Thread %d %s  might be hung, timestamp: %s", Long.valueOf(key.getId()), key.getName(), l));
                    long lastTimestamp = value.getLastTimestamp();
                    if (lastTimestamp == 0) {
                        timeout = value.getTimeout();
                    } else {
                        timeout = value.getTimeout() + value.getDelay();
                    }
                    if (currentTimeMillis - lastTimestamp > timeout) {
                        if (this.sendException) {
                            BacktraceWatchdogShared.sendReportCauseBlockedThread(this.backtraceClient, key, this.onApplicationNotRespondingEvent, str);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void registerThread(Thread thread, int timeout, int delay) {
        this.threadsIdWatcher.put(thread, new BacktraceThreadWatcher(timeout, delay));
    }

    public void unRegisterThread(Thread thread) {
        this.threadsIdWatcher.remove(thread);
    }

    public void tick(Thread thread) {
        BacktraceThreadWatcher backtraceThreadWatcher;
        if (this.threadsIdWatcher.containsKey(thread) && (backtraceThreadWatcher = this.threadsIdWatcher.get(thread)) != null) {
            backtraceThreadWatcher.tickCounter();
        }
    }

    public void activateWatcher(Thread thread) {
        BacktraceThreadWatcher backtraceThreadWatcher;
        if (this.threadsIdWatcher.containsKey(thread) && (backtraceThreadWatcher = this.threadsIdWatcher.get(thread)) != null) {
            backtraceThreadWatcher.setActive(true);
        }
    }

    public void deactivateWatcher(Thread thread) {
        BacktraceThreadWatcher backtraceThreadWatcher;
        if (this.threadsIdWatcher.containsKey(thread) && (backtraceThreadWatcher = this.threadsIdWatcher.get(thread)) != null) {
            backtraceThreadWatcher.setActive(false);
        }
    }
}
