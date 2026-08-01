package backtraceio.library.base;

import android.content.Context;
import backtraceio.library.BacktraceCredentials;
import backtraceio.library.BacktraceDatabase;
import backtraceio.library.BuildConfig;
import backtraceio.library.enums.BacktraceBreadcrumbLevel;
import backtraceio.library.enums.BacktraceBreadcrumbType;
import backtraceio.library.enums.UnwindingMode;
import backtraceio.library.events.OnBeforeSendEventListener;
import backtraceio.library.events.OnServerErrorEventListener;
import backtraceio.library.events.OnServerResponseEventListener;
import backtraceio.library.events.RequestHandler;
import backtraceio.library.interfaces.Api;
import backtraceio.library.interfaces.Breadcrumbs;
import backtraceio.library.interfaces.Client;
import backtraceio.library.interfaces.Database;
import backtraceio.library.interfaces.Metrics;
import backtraceio.library.models.BacktraceData;
import backtraceio.library.models.BacktraceResult;
import backtraceio.library.models.database.BacktraceDatabaseRecord;
import backtraceio.library.models.database.BacktraceDatabaseSettings;
import backtraceio.library.models.json.BacktraceReport;
import backtraceio.library.models.types.BacktraceResultStatus;
import backtraceio.library.services.BacktraceApi;
import backtraceio.library.services.BacktraceMetrics;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class BacktraceBase implements Client {
    private static final transient String LOG_TAG = "BacktraceBase";
    public static String version;
    public final List<String> attachments;
    public final Map<String, Object> attributes;
    private Api backtraceApi;
    private OnBeforeSendEventListener beforeSendEventListener;
    protected Context context;
    private final BacktraceCredentials credentials;
    public final Database database;
    private boolean isProguardEnabled;
    public Metrics metrics;

    public native void crash();

    public native void dumpWithoutCrash(String message);

    public native void dumpWithoutCrash(String message, boolean setMainThreadAsFaultingThread);

    static {
        System.loadLibrary("backtrace-native");
        version = BuildConfig.VERSION_NAME;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BacktraceBase(Context context, BacktraceCredentials credentials) {
        this(context, credentials, (Database) null);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BacktraceBase(Context context, BacktraceCredentials credentials, List<String> attachments) {
        this(context, credentials, (Database) null, attachments);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BacktraceBase(Context context, BacktraceCredentials credentials, Map<String, Object> attributes) {
        this(context, credentials, (Database) null, attributes);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BacktraceBase(Context context, BacktraceCredentials credentials, Map<String, Object> attributes, List<String> attachments) {
        this(context, credentials, (Database) null, attributes, attachments);
    }

    public BacktraceBase(Context context, BacktraceCredentials credentials, BacktraceDatabaseSettings databaseSettings) {
        this(context, credentials, new BacktraceDatabase(context, databaseSettings));
    }

    public BacktraceBase(Context context, BacktraceCredentials credentials, BacktraceDatabaseSettings databaseSettings, List<String> attachments) {
        this(context, credentials, new BacktraceDatabase(context, databaseSettings), attachments);
    }

    public BacktraceBase(Context context, BacktraceCredentials credentials, BacktraceDatabaseSettings databaseSettings, Map<String, Object> attributes) {
        this(context, credentials, new BacktraceDatabase(context, databaseSettings), attributes);
    }

    public BacktraceBase(Context context, BacktraceCredentials credentials, BacktraceDatabaseSettings databaseSettings, Map<String, Object> attributes, List<String> attachments) {
        this(context, credentials, new BacktraceDatabase(context, databaseSettings), attributes, attachments);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BacktraceBase(Context context, BacktraceCredentials credentials, Database database) {
        this(context, credentials, database, (Map<String, Object>) null);
    }

    public BacktraceBase(Context context, BacktraceCredentials credentials, Database database, List<String> attachments) {
        this(context, credentials, database, (Map<String, Object>) null, attachments);
    }

    public BacktraceBase(Context context, BacktraceCredentials credentials, Database database, Map<String, Object> attributes) {
        this(context, credentials, database, attributes, (List<String>) null);
    }

    public BacktraceBase(Context context, BacktraceCredentials credentials, Database database, Map<String, Object> attributes, List<String> attachments) {
        this.metrics = null;
        this.beforeSendEventListener = null;
        this.isProguardEnabled = false;
        this.context = context;
        this.credentials = credentials;
        this.attributes = attributes != null ? attributes : new HashMap<>();
        this.attachments = attachments == null ? new ArrayList<>() : attachments;
        database = database == null ? new BacktraceDatabase() : database;
        this.database = database;
        setBacktraceApi(new BacktraceApi(credentials));
        database.start();
        this.metrics = new BacktraceMetrics(context, attributes, this.backtraceApi);
    }

    private void setBacktraceApi(Api backtraceApi) {
        this.backtraceApi = backtraceApi;
        Database database = this.database;
        if (database != null) {
            database.setApi(backtraceApi);
        }
    }

    @Override // backtraceio.library.interfaces.Client
    public void enableNativeIntegration() {
        this.database.setupNativeIntegration(this, this.credentials);
    }

    public void enableNativeIntegration(boolean enableClientSideUnwinding) {
        this.database.setupNativeIntegration(this, this.credentials, enableClientSideUnwinding);
    }

    public void enableNativeIntegration(boolean enableClientSideUnwinding, UnwindingMode unwindingMode) {
        this.database.setupNativeIntegration(this, this.credentials, enableClientSideUnwinding, unwindingMode);
    }

    public void disableNativeIntegration() {
        this.database.disableNativeIntegration();
    }

    public void enableProguard() {
        this.isProguardEnabled = true;
    }

    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    public void setOnBeforeSendEventListener(OnBeforeSendEventListener eventListener) {
        this.beforeSendEventListener = eventListener;
    }

    public void setOnServerErrorEventListener(OnServerErrorEventListener eventListener) {
        this.backtraceApi.setOnServerError(eventListener);
    }

    public void setOnRequestHandler(RequestHandler requestHandler) {
        this.backtraceApi.setRequestHandler(requestHandler);
    }

    public boolean enableBreadcrumbs(Context context) {
        return this.database.getBreadcrumbs().enableBreadcrumbs(context);
    }

    public boolean enableBreadcrumbs(Context context, int maxBreadcrumbLogSizeBytes) {
        return this.database.getBreadcrumbs().enableBreadcrumbs(context, maxBreadcrumbLogSizeBytes);
    }

    public boolean enableBreadcrumbs(Context context, EnumSet<BacktraceBreadcrumbType> breadcrumbTypesToEnable) {
        return this.database.getBreadcrumbs().enableBreadcrumbs(context, breadcrumbTypesToEnable);
    }

    public boolean enableBreadcrumbs(Context context, EnumSet<BacktraceBreadcrumbType> breadcrumbTypesToEnable, int maxBreadcrumbLogSizeBytes) {
        return this.database.getBreadcrumbs().enableBreadcrumbs(context, breadcrumbTypesToEnable, maxBreadcrumbLogSizeBytes);
    }

    public boolean clearBreadcrumbs() {
        return this.database.getBreadcrumbs().clearBreadcrumbs();
    }

    public boolean addBreadcrumb(String message) {
        return this.database.getBreadcrumbs().addBreadcrumb(message);
    }

    public boolean addBreadcrumb(String message, BacktraceBreadcrumbLevel level) {
        return this.database.getBreadcrumbs().addBreadcrumb(message, level);
    }

    public boolean addBreadcrumb(String message, Map<String, Object> attributes) {
        return this.database.getBreadcrumbs().addBreadcrumb(message, attributes);
    }

    public boolean addBreadcrumb(String message, Map<String, Object> attributes, BacktraceBreadcrumbLevel level) {
        return this.database.getBreadcrumbs().addBreadcrumb(message, attributes, level);
    }

    public boolean addBreadcrumb(String message, BacktraceBreadcrumbType type) {
        return this.database.getBreadcrumbs().addBreadcrumb(message, type);
    }

    public boolean addBreadcrumb(String message, BacktraceBreadcrumbType type, BacktraceBreadcrumbLevel level) {
        return this.database.getBreadcrumbs().addBreadcrumb(message, type, level);
    }

    public boolean addBreadcrumb(String message, Map<String, Object> attributes, BacktraceBreadcrumbType type) {
        return this.database.getBreadcrumbs().addBreadcrumb(message, attributes, type);
    }

    public boolean addBreadcrumb(String message, Map<String, Object> attributes, BacktraceBreadcrumbType type, BacktraceBreadcrumbLevel level) {
        return this.database.getBreadcrumbs().addBreadcrumb(message, attributes, type, level);
    }

    public void nativeCrash() {
        crash();
    }

    @Override // backtraceio.library.interfaces.Client
    public void send(BacktraceReport report) {
        send(report, null);
    }

    public void send(BacktraceReport report, final OnServerResponseEventListener callback) {
        Breadcrumbs breadcrumbs = this.database.getBreadcrumbs();
        if (breadcrumbs != null) {
            breadcrumbs.processReportBreadcrumbs(report);
        }
        addReportAttachments(report);
        BacktraceData backtraceData = new BacktraceData(this.context, report, this.attributes);
        backtraceData.symbolication = this.isProguardEnabled ? "proguard" : null;
        BacktraceDatabaseRecord add = this.database.add(report, this.attributes, this.isProguardEnabled);
        OnBeforeSendEventListener onBeforeSendEventListener = this.beforeSendEventListener;
        if (onBeforeSendEventListener != null) {
            backtraceData = onBeforeSendEventListener.onEvent(backtraceData);
        }
        this.backtraceApi.send(backtraceData, getDatabaseCallback(add, callback));
    }

    private OnServerResponseEventListener getDatabaseCallback(final BacktraceDatabaseRecord record, final OnServerResponseEventListener customCallback) {
        return new OnServerResponseEventListener() { // from class: backtraceio.library.base.BacktraceBase.1
            @Override // backtraceio.library.events.OnServerResponseEventListener
            public void onEvent(BacktraceResult backtraceResult) {
                OnServerResponseEventListener onServerResponseEventListener = customCallback;
                if (onServerResponseEventListener != null) {
                    onServerResponseEventListener.onEvent(backtraceResult);
                }
                BacktraceDatabaseRecord backtraceDatabaseRecord = record;
                if (backtraceDatabaseRecord != null) {
                    backtraceDatabaseRecord.close();
                }
                if (backtraceResult == null || backtraceResult.status != BacktraceResultStatus.Ok) {
                    return;
                }
                BacktraceBase.this.database.delete(record);
            }
        };
    }

    private void addReportAttachments(BacktraceReport report) {
        List<String> list = this.attachments;
        if (list != null) {
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                report.attachmentPaths.add(it.next());
            }
        }
    }
}
