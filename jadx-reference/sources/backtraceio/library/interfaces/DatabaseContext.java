package backtraceio.library.interfaces;

import backtraceio.library.models.BacktraceData;
import backtraceio.library.models.database.BacktraceDatabaseRecord;

/* loaded from: classes.dex */
public interface DatabaseContext {
    BacktraceDatabaseRecord add(BacktraceData backtraceData);

    BacktraceDatabaseRecord add(BacktraceDatabaseRecord backtraceDatabaseRecord);

    void clear();

    boolean contains(BacktraceDatabaseRecord n);

    int count();

    boolean delete(BacktraceDatabaseRecord record);

    BacktraceDatabaseRecord first();

    Iterable<BacktraceDatabaseRecord> get();

    long getDatabaseSize();

    void incrementBatchRetry();

    boolean isEmpty();

    BacktraceDatabaseRecord last();

    boolean removeOldestRecord();
}
