package backtraceio.library.watchdog;

/* loaded from: classes.dex */
public class BacktraceThreadWatcher {
    private boolean active;
    private int counter;
    private final int delay;
    private long lastTimestamp;
    private int privateCounter;
    private final int timeout;

    /* JADX INFO: Access modifiers changed from: package-private */
    public BacktraceThreadWatcher(int timeout, int delay) {
        this.timeout = timeout;
        this.delay = delay;
        setActive(true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getTimeout() {
        return this.timeout;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getDelay() {
        return this.delay;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public long getLastTimestamp() {
        return this.lastTimestamp;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setLastTimestamp(long lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized boolean isActive() {
        return this.active;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void setActive(boolean active) {
        this.active = active;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void tickPrivateCounter() {
        this.privateCounter++;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getPrivateCounter() {
        return this.privateCounter;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setPrivateCounter(int privateCounter) {
        this.privateCounter = privateCounter;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized int getCounter() {
        return this.counter;
    }

    public synchronized void tickCounter() {
        this.counter++;
    }
}
