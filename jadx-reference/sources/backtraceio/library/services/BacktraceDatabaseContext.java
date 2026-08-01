package backtraceio.library.services;

import android.content.Context;
import backtraceio.library.enums.database.RetryOrder;
import backtraceio.library.interfaces.DatabaseContext;
import backtraceio.library.logger.BacktraceLogger;
import backtraceio.library.models.BacktraceData;
import backtraceio.library.models.database.BacktraceDatabaseRecord;
import backtraceio.library.models.database.BacktraceDatabaseSettings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes.dex */
public class BacktraceDatabaseContext implements DatabaseContext {
    private static final transient String LOG_TAG = "BacktraceDatabaseContext";
    private final Context _applicationContext;
    private final String _path;
    private final int _retryNumber;
    private final Map<Integer, List<BacktraceDatabaseRecord>> batchRetry;
    private final RetryOrder retryOrder;
    private int totalRecords;
    private long totalSize;

    public BacktraceDatabaseContext(Context context, BacktraceDatabaseSettings settings) {
        this(context, settings.getDatabasePath(), settings.getRetryLimit(), settings.getRetryOrder());
    }

    private BacktraceDatabaseContext(Context context, String path, int retryNumber, RetryOrder retryOrder) {
        this.batchRetry = new HashMap();
        this.totalSize = 0L;
        this.totalRecords = 0;
        this._applicationContext = context;
        this._path = path;
        this._retryNumber = retryNumber;
        this.retryOrder = retryOrder;
        SetupBatch();
    }

    private void SetupBatch() {
        if (this._retryNumber == 0) {
            throw new IllegalArgumentException("Retry number must be greater than 0!");
        }
        for (int i = 0; i < this._retryNumber; i++) {
            this.batchRetry.put(Integer.valueOf(i), new ArrayList());
        }
    }

    @Override // backtraceio.library.interfaces.DatabaseContext
    public BacktraceDatabaseRecord add(BacktraceData backtraceData) throws NullPointerException {
        String str = LOG_TAG;
        BacktraceLogger.d(str, "Adding new record to database context");
        if (backtraceData == null) {
            BacktraceLogger.e(str, "BacktraceData is null");
            throw new NullPointerException("BacktraceData");
        }
        BacktraceDatabaseRecord backtraceDatabaseRecord = new BacktraceDatabaseRecord(backtraceData, this._path);
        backtraceDatabaseRecord.save();
        return add(backtraceDatabaseRecord);
    }

    @Override // backtraceio.library.interfaces.DatabaseContext
    public BacktraceDatabaseRecord add(BacktraceDatabaseRecord backtraceDatabaseRecord) {
        String str = LOG_TAG;
        BacktraceLogger.d(str, "Adding new record to database context");
        if (backtraceDatabaseRecord == null) {
            BacktraceLogger.e(str, "Backtrace database record is null");
            throw new NullPointerException("BacktraceDatabaseRecord");
        }
        backtraceDatabaseRecord.locked = true;
        this.totalSize += backtraceDatabaseRecord.getSize();
        this.batchRetry.get(0).add(backtraceDatabaseRecord);
        this.totalRecords++;
        return backtraceDatabaseRecord;
    }

    @Override // backtraceio.library.interfaces.DatabaseContext
    public BacktraceDatabaseRecord first() {
        if (this.retryOrder == RetryOrder.Queue) {
            return getFirstRecord();
        }
        return getLastRecord();
    }

    @Override // backtraceio.library.interfaces.DatabaseContext
    public BacktraceDatabaseRecord last() {
        return this.retryOrder == RetryOrder.Queue ? getLastRecord() : getFirstRecord();
    }

    @Override // backtraceio.library.interfaces.DatabaseContext
    public Iterable<BacktraceDatabaseRecord> get() {
        ArrayList arrayList = new ArrayList();
        Iterator<Map.Entry<Integer, List<BacktraceDatabaseRecord>>> it = this.batchRetry.entrySet().iterator();
        while (it.hasNext()) {
            arrayList.addAll(it.next().getValue());
        }
        return arrayList;
    }

    @Override // backtraceio.library.interfaces.DatabaseContext
    public boolean delete(BacktraceDatabaseRecord record) {
        if (record == null) {
            return false;
        }
        Iterator<Integer> it = this.batchRetry.keySet().iterator();
        while (it.hasNext()) {
            int intValue = it.next().intValue();
            for (BacktraceDatabaseRecord backtraceDatabaseRecord : this.batchRetry.get(Integer.valueOf(intValue))) {
                if (backtraceDatabaseRecord != null && record.id == backtraceDatabaseRecord.id) {
                    backtraceDatabaseRecord.delete();
                    this.batchRetry.get(Integer.valueOf(intValue)).remove(backtraceDatabaseRecord);
                    this.totalRecords--;
                    this.totalSize -= backtraceDatabaseRecord.getSize();
                    return true;
                }
            }
        }
        return false;
    }

    @Override // backtraceio.library.interfaces.DatabaseContext
    public boolean contains(BacktraceDatabaseRecord record) {
        Objects.requireNonNull(record, "BacktraceDatabaseRecord");
        Iterator<Map.Entry<Integer, List<BacktraceDatabaseRecord>>> it = this.batchRetry.entrySet().iterator();
        while (it.hasNext()) {
            for (BacktraceDatabaseRecord backtraceDatabaseRecord : it.next().getValue()) {
                if (backtraceDatabaseRecord != null && backtraceDatabaseRecord.id == record.id) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // backtraceio.library.interfaces.DatabaseContext
    public boolean isEmpty() {
        return this.totalRecords == 0;
    }

    @Override // backtraceio.library.interfaces.DatabaseContext
    public int count() {
        return this.totalRecords;
    }

    @Override // backtraceio.library.interfaces.DatabaseContext
    public void clear() {
        BacktraceLogger.d(LOG_TAG, "Deleting all records from database context");
        Iterator<Map.Entry<Integer, List<BacktraceDatabaseRecord>>> it = this.batchRetry.entrySet().iterator();
        while (it.hasNext()) {
            Iterator<BacktraceDatabaseRecord> it2 = it.next().getValue().iterator();
            while (it2.hasNext()) {
                it2.next().delete();
            }
        }
        this.totalRecords = 0;
        this.totalSize = 0L;
        Iterator<Map.Entry<Integer, List<BacktraceDatabaseRecord>>> it3 = this.batchRetry.entrySet().iterator();
        while (it3.hasNext()) {
            it3.next().getValue().clear();
        }
    }

    @Override // backtraceio.library.interfaces.DatabaseContext
    public void incrementBatchRetry() {
        removeMaxRetries();
        incrementBatches();
    }

    @Override // backtraceio.library.interfaces.DatabaseContext
    public long getDatabaseSize() {
        return this.totalSize;
    }

    @Override // backtraceio.library.interfaces.DatabaseContext
    public boolean removeOldestRecord() {
        String str = LOG_TAG;
        BacktraceLogger.d(str, "Removing oldest record from database context");
        BacktraceDatabaseRecord first = first();
        if (first == null) {
            BacktraceLogger.w(str, "Oldest record in database is null");
            return false;
        }
        return delete(first);
    }

    private void incrementBatches() {
        for (int i = this._retryNumber - 2; i >= 0; i--) {
            List<BacktraceDatabaseRecord> list = this.batchRetry.get(Integer.valueOf(i));
            this.batchRetry.put(Integer.valueOf(i), new ArrayList());
            this.batchRetry.put(Integer.valueOf(i + 1), list);
        }
    }

    private void removeMaxRetries() {
        for (BacktraceDatabaseRecord backtraceDatabaseRecord : this.batchRetry.get(Integer.valueOf(this._retryNumber - 1))) {
            if (backtraceDatabaseRecord.valid()) {
                backtraceDatabaseRecord.delete();
                this.totalRecords--;
                this.totalSize -= backtraceDatabaseRecord.getSize();
            }
        }
    }

    private BacktraceDatabaseRecord getFirstRecord() {
        return getRecordFromCache(false);
    }

    private BacktraceDatabaseRecord getLastRecord() {
        return getRecordFromCache(true);
    }

    private BacktraceDatabaseRecord getRecordFromCache(boolean reverse) {
        for (int i = this._retryNumber - 1; i >= 0; i--) {
            List<BacktraceDatabaseRecord> list = this.batchRetry.get(Integer.valueOf(i));
            if (reverse) {
                Collections.reverse(list);
            }
            for (BacktraceDatabaseRecord backtraceDatabaseRecord : list) {
                if (!backtraceDatabaseRecord.locked) {
                    backtraceDatabaseRecord.locked = true;
                    return backtraceDatabaseRecord;
                }
            }
        }
        return null;
    }
}
