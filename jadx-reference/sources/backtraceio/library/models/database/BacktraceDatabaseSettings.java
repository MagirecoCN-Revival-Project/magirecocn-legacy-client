package backtraceio.library.models.database;

import backtraceio.library.enums.database.RetryBehavior;
import backtraceio.library.enums.database.RetryOrder;

/* loaded from: classes.dex */
public class BacktraceDatabaseSettings {
    private long _maxDatabaseSize;
    private boolean autoSendMode;
    private String databasePath;
    private int maxRecordCount;
    private RetryBehavior retryBehavior;
    private int retryInterval;
    private int retryLimit;
    private RetryOrder retryOrder;

    public BacktraceDatabaseSettings(String path) {
        this(path, RetryOrder.Queue);
    }

    public BacktraceDatabaseSettings(String path, RetryOrder retryOrder) {
        this.maxRecordCount = 0;
        this._maxDatabaseSize = 0L;
        this.autoSendMode = false;
        this.retryBehavior = RetryBehavior.ByInterval;
        this.retryInterval = 5;
        this.retryLimit = 3;
        RetryOrder retryOrder2 = RetryOrder.Stack;
        this.databasePath = path;
        this.retryOrder = retryOrder;
    }

    public long getMaxDatabaseSize() {
        return this._maxDatabaseSize * 1000 * 1000;
    }

    public void setMaxDatabaseSize(long value) {
        this._maxDatabaseSize = value;
    }

    public String getDatabasePath() {
        return this.databasePath;
    }

    public void setDatabasePath(String databasePath) {
        this.databasePath = databasePath;
    }

    public int getMaxRecordCount() {
        return this.maxRecordCount;
    }

    public void setMaxRecordCount(int maxRecordCount) {
        this.maxRecordCount = maxRecordCount;
    }

    public boolean isAutoSendMode() {
        return this.autoSendMode;
    }

    public void setAutoSendMode(boolean autoSendMode) {
        this.autoSendMode = autoSendMode;
    }

    public RetryBehavior getRetryBehavior() {
        return this.retryBehavior;
    }

    public void setRetryBehavior(RetryBehavior retryBehavior) {
        this.retryBehavior = retryBehavior;
    }

    public int getRetryInterval() {
        return this.retryInterval;
    }

    public void setRetryInterval(int retryInterval) {
        if (retryInterval <= 0) {
            throw new IllegalArgumentException("Retry interval value must be grader than zero");
        }
        this.retryInterval = retryInterval;
    }

    public int getRetryLimit() {
        return this.retryLimit;
    }

    public void setRetryLimit(int retryLimit) {
        if (this.retryInterval <= 0) {
            throw new IllegalArgumentException("Retry limit value must be grader than zero");
        }
        this.retryLimit = retryLimit;
    }

    public RetryOrder getRetryOrder() {
        return this.retryOrder;
    }

    public void setRetryOrder(RetryOrder retryOrder) {
        this.retryOrder = retryOrder;
    }
}
