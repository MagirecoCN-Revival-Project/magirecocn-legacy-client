package backtraceio.library;

import android.content.Context;
import backtraceio.library.base.BacktraceBase;
import backtraceio.library.breadcrumbs.BacktraceBreadcrumbs;
import backtraceio.library.common.FileHelper;
import backtraceio.library.enums.UnwindingMode;
import backtraceio.library.enums.database.RetryBehavior;
import backtraceio.library.events.OnServerResponseEventListener;
import backtraceio.library.interfaces.Api;
import backtraceio.library.interfaces.Breadcrumbs;
import backtraceio.library.interfaces.Database;
import backtraceio.library.interfaces.DatabaseContext;
import backtraceio.library.interfaces.DatabaseFileContext;
import backtraceio.library.logger.BacktraceLogger;
import backtraceio.library.models.BacktraceAttributeConsts;
import backtraceio.library.models.BacktraceData;
import backtraceio.library.models.BacktraceResult;
import backtraceio.library.models.database.BacktraceDatabaseRecord;
import backtraceio.library.models.database.BacktraceDatabaseSettings;
import backtraceio.library.models.json.BacktraceAttributes;
import backtraceio.library.models.json.BacktraceReport;
import backtraceio.library.models.types.BacktraceResultStatus;
import backtraceio.library.services.BacktraceDatabaseContext;
import backtraceio.library.services.BacktraceDatabaseFileContext;
import java.io.File;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes.dex */
public class BacktraceDatabase implements Database {
    private static Timer _timer;
    private static boolean _timerBackgroundWork;
    private Api BacktraceApi;
    private final transient String LOG_TAG;
    private Context _applicationContext;
    private final String _crashpadDatabasePathPrefix;
    private final String _crashpadHandlerName;
    private boolean _enable;
    private DatabaseContext backtraceDatabaseContext;
    private DatabaseFileContext backtraceDatabaseFileContext;
    private Breadcrumbs breadcrumbs;
    private BacktraceDatabaseSettings databaseSettings;

    private native void disable();

    private native boolean initialize(String url, String databasePath, String handlerPath, String[] attributeKeys, String[] attributeValues, String[] attachmentPaths, boolean enableClientSideUnwinding, UnwindingMode unwindingMode);

    public native void addAttribute(String name, String value);

    public BacktraceDatabase() {
        this._crashpadHandlerName = "/libcrashpad_handler.so";
        this._crashpadDatabasePathPrefix = "/crashpad";
        this.LOG_TAG = "BacktraceDatabase";
        this._enable = false;
        BacktraceLogger.w("BacktraceDatabase", "Disabled instance of BacktraceDatabase created, native crashes won't be captured");
    }

    public BacktraceDatabase(Context context, String path) {
        this(context, new BacktraceDatabaseSettings(path));
    }

    public BacktraceDatabase(Context context, BacktraceDatabaseSettings databaseSettings) {
        this._crashpadHandlerName = "/libcrashpad_handler.so";
        this._crashpadDatabasePathPrefix = "/crashpad";
        this.LOG_TAG = "BacktraceDatabase";
        this._enable = false;
        if (databaseSettings == null || context == null) {
            throw new IllegalArgumentException("Database settings or application context is null");
        }
        if (databaseSettings.getDatabasePath() == null || databaseSettings.getDatabasePath().isEmpty()) {
            throw new IllegalArgumentException("Database path is null or empty");
        }
        if (!FileHelper.isFileExists(databaseSettings.getDatabasePath()) && (!new File(databaseSettings.getDatabasePath()).mkdirs() || !FileHelper.isFileExists(databaseSettings.getDatabasePath()))) {
            throw new IllegalArgumentException("Incorrect database path or application doesn't have permission to write to this path");
        }
        this._applicationContext = context;
        this.databaseSettings = databaseSettings;
        this.backtraceDatabaseContext = new BacktraceDatabaseContext(this._applicationContext, databaseSettings);
        this.backtraceDatabaseFileContext = new BacktraceDatabaseFileContext(getDatabasePath(), this.databaseSettings.getMaxDatabaseSize(), this.databaseSettings.getMaxRecordCount());
        this.breadcrumbs = new BacktraceBreadcrumbs(getDatabasePath());
    }

    private String getDatabasePath() {
        return this.databaseSettings.getDatabasePath();
    }

    @Override // backtraceio.library.interfaces.Database
    public Boolean setupNativeIntegration(BacktraceBase client, BacktraceCredentials credentials) {
        return setupNativeIntegration(client, credentials, false);
    }

    @Override // backtraceio.library.interfaces.Database
    public Boolean setupNativeIntegration(BacktraceBase client, BacktraceCredentials credentials, boolean enableClientSideUnwinding) {
        return setupNativeIntegration(client, credentials, enableClientSideUnwinding, UnwindingMode.REMOTE_DUMPWITHOUTCRASH);
    }

    @Override // backtraceio.library.interfaces.Database
    public Boolean setupNativeIntegration(BacktraceBase client, BacktraceCredentials credentials, boolean enableClientSideUnwinding, UnwindingMode unwindingMode) {
        String uri;
        if (getSettings() == null || (uri = credentials.getMinidumpSubmissionUrl().toString()) == null) {
            return false;
        }
        String str = this._applicationContext.getApplicationInfo().nativeLibraryDir + "/libcrashpad_handler.so";
        BacktraceAttributes backtraceAttributes = new BacktraceAttributes(this._applicationContext, client.attributes);
        backtraceAttributes.attributes.put(BacktraceAttributeConsts.ErrorType, BacktraceAttributeConsts.CrashAttributeType);
        String[] strArr = (String[]) backtraceAttributes.attributes.keySet().toArray(new String[0]);
        String[] strArr2 = (String[]) backtraceAttributes.attributes.values().toArray(new String[0]);
        int size = client.attachments.size() + 1;
        String[] strArr3 = new String[size];
        if (client.attachments != null) {
            for (int i = 0; i < client.attachments.size(); i++) {
                strArr3[i] = client.attachments.get(i);
            }
        }
        strArr3[size - 1] = this.breadcrumbs.getBreadcrumbLogPath();
        String str2 = getSettings().getDatabasePath() + "/crashpad";
        new File(str2).mkdir();
        return Boolean.valueOf(initialize(uri, str2, str, strArr, strArr2, strArr3, enableClientSideUnwinding, unwindingMode));
    }

    @Override // backtraceio.library.interfaces.Database
    public void disableNativeIntegration() {
        disable();
    }

    @Override // backtraceio.library.interfaces.Database
    public Breadcrumbs getBreadcrumbs() {
        return this.breadcrumbs;
    }

    @Override // backtraceio.library.interfaces.Database
    public void start() {
        if (this.databaseSettings == null) {
            return;
        }
        DatabaseContext databaseContext = this.backtraceDatabaseContext;
        if (databaseContext != null && !databaseContext.isEmpty()) {
            this._enable = true;
            return;
        }
        loadReports();
        removeOrphaned();
        if (this.databaseSettings.getRetryBehavior() == RetryBehavior.ByInterval || this.databaseSettings.isAutoSendMode()) {
            setupTimer();
        }
        this._enable = true;
    }

    @Override // backtraceio.library.interfaces.Database
    public BacktraceDatabaseSettings getSettings() {
        return this.databaseSettings;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setupTimer() {
        Timer timer = new Timer();
        _timer = timer;
        timer.scheduleAtFixedRate(new TimerTask() { // from class: backtraceio.library.BacktraceDatabase.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                String date = Calendar.getInstance().getTime().toString();
                BacktraceLogger.d(BacktraceDatabase.this.LOG_TAG, "Timer - " + date);
                if (BacktraceDatabase.this.backtraceDatabaseContext == null) {
                    BacktraceLogger.w(BacktraceDatabase.this.LOG_TAG, "Timer - database context is null: " + date);
                    return;
                }
                if (BacktraceDatabase.this.backtraceDatabaseContext.isEmpty()) {
                    BacktraceLogger.d(BacktraceDatabase.this.LOG_TAG, "Timer - database is empty (no records): " + date);
                    return;
                }
                if (BacktraceDatabase._timerBackgroundWork) {
                    BacktraceLogger.d(BacktraceDatabase.this.LOG_TAG, "Timer - another timer works now: " + date);
                    return;
                }
                BacktraceLogger.d(BacktraceDatabase.this.LOG_TAG, "Timer - continue working: " + date);
                boolean unused = BacktraceDatabase._timerBackgroundWork = true;
                BacktraceDatabase._timer.cancel();
                BacktraceDatabase._timer.purge();
                Timer unused2 = BacktraceDatabase._timer = null;
                final BacktraceDatabaseRecord first = BacktraceDatabase.this.backtraceDatabaseContext.first();
                while (true) {
                    if (first == null) {
                        break;
                    }
                    final CountDownLatch countDownLatch = new CountDownLatch(1);
                    BacktraceData backtraceData = first.getBacktraceData(BacktraceDatabase.this._applicationContext);
                    if (backtraceData == null || backtraceData.report == null) {
                        BacktraceLogger.d(BacktraceDatabase.this.LOG_TAG, "Timer - backtrace data or report is null - deleting record");
                        BacktraceDatabase.this.delete(first);
                    } else {
                        BacktraceDatabase.this.BacktraceApi.send(backtraceData, new OnServerResponseEventListener() { // from class: backtraceio.library.BacktraceDatabase.1.1
                            @Override // backtraceio.library.events.OnServerResponseEventListener
                            public void onEvent(BacktraceResult backtraceResult) {
                                if (backtraceResult.status == BacktraceResultStatus.Ok) {
                                    BacktraceLogger.d(BacktraceDatabase.this.LOG_TAG, "Timer - deleting record");
                                    BacktraceDatabase.this.delete(first);
                                } else {
                                    BacktraceLogger.d(BacktraceDatabase.this.LOG_TAG, "Timer - closing record");
                                    first.close();
                                }
                                countDownLatch.countDown();
                            }
                        });
                        try {
                            countDownLatch.await();
                        } catch (Exception e) {
                            BacktraceLogger.e(BacktraceDatabase.this.LOG_TAG, "Error during waiting for result in Timer", e);
                        }
                        if (first.valid() && !first.locked) {
                            BacktraceLogger.d(BacktraceDatabase.this.LOG_TAG, "Timer - record is valid and unlocked");
                            break;
                        }
                    }
                    first = BacktraceDatabase.this.backtraceDatabaseContext.first();
                }
                BacktraceLogger.d(BacktraceDatabase.this.LOG_TAG, "Setup new timer");
                boolean unused3 = BacktraceDatabase._timerBackgroundWork = false;
                BacktraceDatabase.this.setupTimer();
            }
        }, this.databaseSettings.getRetryInterval() * 1000, this.databaseSettings.getRetryInterval() * 1000);
    }

    @Override // backtraceio.library.interfaces.Database
    public void flush() {
        if (this.BacktraceApi == null) {
            throw new IllegalArgumentException("BacktraceApi is required if you want to use Flush method");
        }
        BacktraceDatabaseRecord first = this.backtraceDatabaseContext.first();
        while (first != null) {
            BacktraceData backtraceData = first.getBacktraceData(this._applicationContext);
            delete(first);
            if (backtraceData != null) {
                this.BacktraceApi.send(backtraceData, null);
            }
            first = this.backtraceDatabaseContext.first();
        }
    }

    @Override // backtraceio.library.interfaces.Database
    public void setApi(Api backtraceApi) {
        this.BacktraceApi = backtraceApi;
    }

    @Override // backtraceio.library.interfaces.Database
    public void clear() {
        DatabaseContext databaseContext = this.backtraceDatabaseContext;
        if (databaseContext != null) {
            databaseContext.clear();
        }
        DatabaseFileContext databaseFileContext = this.backtraceDatabaseFileContext;
        if (databaseFileContext != null) {
            databaseFileContext.clear();
        }
    }

    private void removeOrphaned() {
        this.backtraceDatabaseFileContext.removeOrphaned(this.backtraceDatabaseContext.get());
    }

    @Override // backtraceio.library.interfaces.Database
    public boolean validConsistency() {
        return this.backtraceDatabaseFileContext.validFileConsistency();
    }

    @Override // backtraceio.library.interfaces.Database
    public BacktraceDatabaseRecord add(BacktraceReport backtraceReport, Map<String, Object> attributes) {
        return add(backtraceReport, attributes, false);
    }

    @Override // backtraceio.library.interfaces.Database
    public BacktraceDatabaseRecord add(BacktraceReport backtraceReport, Map<String, Object> attributes, boolean isProguardEnabled) {
        if (!this._enable || backtraceReport == null || !validateDatabaseSize()) {
            return null;
        }
        return this.backtraceDatabaseContext.add(backtraceReport.toBacktraceData(this._applicationContext, attributes, isProguardEnabled));
    }

    @Override // backtraceio.library.interfaces.Database
    public Iterable<BacktraceDatabaseRecord> get() {
        DatabaseContext databaseContext = this.backtraceDatabaseContext;
        if (databaseContext == null) {
            return null;
        }
        return databaseContext.get();
    }

    @Override // backtraceio.library.interfaces.Database
    public void delete(BacktraceDatabaseRecord record) {
        DatabaseContext databaseContext = this.backtraceDatabaseContext;
        if (databaseContext == null) {
            return;
        }
        databaseContext.delete(record);
    }

    public int count() {
        return this.backtraceDatabaseContext.count();
    }

    private void loadReports() {
        Iterator<File> it = this.backtraceDatabaseFileContext.getRecords().iterator();
        while (it.hasNext()) {
            BacktraceDatabaseRecord readFromFile = BacktraceDatabaseRecord.readFromFile(it.next());
            if (!readFromFile.valid()) {
                readFromFile.delete();
            } else {
                this.backtraceDatabaseContext.add(readFromFile);
                validateDatabaseSize();
                readFromFile.close();
            }
        }
    }

    private boolean validateDatabaseSize() {
        if (this.backtraceDatabaseContext.count() + 1 > this.databaseSettings.getMaxRecordCount() && this.databaseSettings.getMaxRecordCount() != 0 && !this.backtraceDatabaseContext.removeOldestRecord()) {
            BacktraceLogger.e(this.LOG_TAG, "Can't remove last record. Database size is invalid");
            return false;
        }
        if (this.databaseSettings.getMaxDatabaseSize() == 0 || this.backtraceDatabaseContext.getDatabaseSize() <= this.databaseSettings.getMaxDatabaseSize()) {
            return true;
        }
        int i = 5;
        while (this.backtraceDatabaseContext.getDatabaseSize() > this.databaseSettings.getMaxDatabaseSize()) {
            this.backtraceDatabaseContext.removeOldestRecord();
            i--;
            if (i == 0) {
                break;
            }
        }
        return i != 0;
    }

    @Override // backtraceio.library.interfaces.Database
    public long getDatabaseSize() {
        return this.backtraceDatabaseContext.getDatabaseSize();
    }
}
