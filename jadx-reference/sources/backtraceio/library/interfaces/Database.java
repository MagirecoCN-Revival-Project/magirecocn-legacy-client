package backtraceio.library.interfaces;

import backtraceio.library.BacktraceCredentials;
import backtraceio.library.base.BacktraceBase;
import backtraceio.library.enums.UnwindingMode;
import backtraceio.library.models.database.BacktraceDatabaseRecord;
import backtraceio.library.models.database.BacktraceDatabaseSettings;
import backtraceio.library.models.json.BacktraceReport;
import java.util.Map;

/* loaded from: classes.dex */
public interface Database {
    BacktraceDatabaseRecord add(BacktraceReport backtraceReport, Map<String, Object> attributes);

    BacktraceDatabaseRecord add(BacktraceReport backtraceReport, Map<String, Object> attributes, boolean isProguardEnabled);

    void clear();

    void delete(BacktraceDatabaseRecord record);

    void disableNativeIntegration();

    void flush();

    Iterable<BacktraceDatabaseRecord> get();

    Breadcrumbs getBreadcrumbs();

    long getDatabaseSize();

    BacktraceDatabaseSettings getSettings();

    void setApi(Api backtraceApi);

    Boolean setupNativeIntegration(BacktraceBase client, BacktraceCredentials credentials);

    Boolean setupNativeIntegration(BacktraceBase client, BacktraceCredentials credentials, boolean enableClientSideUnwinding);

    Boolean setupNativeIntegration(BacktraceBase client, BacktraceCredentials credentials, boolean enableClientSideUnwinding, UnwindingMode unwindingMode);

    void start();

    boolean validConsistency();
}
