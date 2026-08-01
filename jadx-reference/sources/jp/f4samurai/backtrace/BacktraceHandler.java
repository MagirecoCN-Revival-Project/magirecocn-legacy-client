package jp.f4samurai.backtrace;

import android.content.Context;
import backtraceio.library.BacktraceClient;
import backtraceio.library.BacktraceCredentials;
import backtraceio.library.BacktraceDatabase;
import backtraceio.library.enums.database.RetryBehavior;
import backtraceio.library.enums.database.RetryOrder;
import backtraceio.library.models.BacktraceExceptionHandler;
import backtraceio.library.models.database.BacktraceDatabaseSettings;
import java.util.HashMap;

/* loaded from: classes.dex */
public class BacktraceHandler {
    private static final int ANR_TIMEOUT = 3000;
    private static final String ENDPOINT_URL = "https://totentanz-9b.magica-us.com/backtrace";
    private static final String SUBMISSION_TOKEN = "snaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    private static BacktraceClient sBacktraceClient;

    public static void startBacktrace(Context context) {
        if (sBacktraceClient != null) {
            return;
        }
        BacktraceCredentials backtraceCredentials = new BacktraceCredentials(ENDPOINT_URL, SUBMISSION_TOKEN);
        BacktraceDatabaseSettings backtraceDatabaseSettings = new BacktraceDatabaseSettings(context.getFilesDir().getAbsolutePath());
        backtraceDatabaseSettings.setMaxRecordCount(100);
        backtraceDatabaseSettings.setMaxDatabaseSize(100L);
        backtraceDatabaseSettings.setRetryBehavior(RetryBehavior.ByInterval);
        backtraceDatabaseSettings.setAutoSendMode(true);
        backtraceDatabaseSettings.setRetryOrder(RetryOrder.Queue);
        BacktraceDatabase backtraceDatabase = new BacktraceDatabase(context, backtraceDatabaseSettings);
        BacktraceClient backtraceClient = new BacktraceClient(context, backtraceCredentials, backtraceDatabase);
        sBacktraceClient = backtraceClient;
        backtraceClient.enableNativeIntegration();
        sBacktraceClient.enableAnr(3000);
        sBacktraceClient.enableBreadcrumbs(context);
        sBacktraceClient.enableProguard();
        BacktraceExceptionHandler.enable(sBacktraceClient);
        backtraceDatabase.setupNativeIntegration(sBacktraceClient, backtraceCredentials, true);
    }

    public static void setUserId(String str) {
        sBacktraceClient.addBreadcrumb("UserId", new HashMap<String, Object>(str) { // from class: jp.f4samurai.backtrace.BacktraceHandler.1
            final /* synthetic */ String val$userId;

            {
                this.val$userId = str;
                put("id", str);
            }
        });
    }

    public static void setLog(String str) {
        sBacktraceClient.addBreadcrumb(str);
    }

    public static void setLogData(String str, String str2) {
        sBacktraceClient.addBreadcrumb("LogData", new HashMap<String, Object>(str, str2) { // from class: jp.f4samurai.backtrace.BacktraceHandler.2
            final /* synthetic */ String val$key;
            final /* synthetic */ String val$value;

            {
                this.val$key = str;
                this.val$value = str2;
                put(str, str2);
            }
        });
    }
}
