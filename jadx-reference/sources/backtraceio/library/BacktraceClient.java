package backtraceio.library;

import android.content.Context;
import backtraceio.library.base.BacktraceBase;
import backtraceio.library.events.OnServerResponseEventListener;
import backtraceio.library.interfaces.Database;
import backtraceio.library.models.database.BacktraceDatabaseSettings;
import backtraceio.library.models.json.BacktraceReport;
import backtraceio.library.watchdog.BacktraceANRWatchdog;
import backtraceio.library.watchdog.OnApplicationNotRespondingEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class BacktraceClient extends BacktraceBase {
    private BacktraceANRWatchdog anrWatchdog;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BacktraceClient(Context context, BacktraceCredentials credentials) {
        this(context, credentials, (Database) null);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BacktraceClient(Context context, BacktraceCredentials credentials, List<String> attachments) {
        this(context, credentials, (Database) null, attachments);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BacktraceClient(Context context, BacktraceCredentials credentials, Map<String, Object> attributes) {
        this(context, credentials, (Database) null, attributes);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BacktraceClient(Context context, BacktraceCredentials credentials, Map<String, Object> attributes, List<String> attachments) {
        this(context, credentials, (Database) null, attributes, attachments);
    }

    public BacktraceClient(Context context, BacktraceCredentials credentials, BacktraceDatabaseSettings databaseSettings) {
        this(context, credentials, new BacktraceDatabase(context, databaseSettings));
    }

    public BacktraceClient(Context context, BacktraceCredentials credentials, BacktraceDatabaseSettings databaseSettings, List<String> attachments) {
        this(context, credentials, new BacktraceDatabase(context, databaseSettings), attachments);
    }

    public BacktraceClient(Context context, BacktraceCredentials credentials, BacktraceDatabaseSettings databaseSettings, Map<String, Object> attributes) {
        this(context, credentials, new BacktraceDatabase(context, databaseSettings), attributes);
    }

    public BacktraceClient(Context context, BacktraceCredentials credentials, BacktraceDatabaseSettings databaseSettings, Map<String, Object> attributes, List<String> attachments) {
        this(context, credentials, new BacktraceDatabase(context, databaseSettings), attributes, attachments);
    }

    public BacktraceClient(Context context, BacktraceCredentials credentials, Database database) {
        this(context, credentials, database, new HashMap());
    }

    public BacktraceClient(Context context, BacktraceCredentials credentials, Database database, List<String> attachments) {
        this(context, credentials, database, (Map<String, Object>) null, attachments);
    }

    public BacktraceClient(Context context, BacktraceCredentials credentials, Database database, Map<String, Object> attributes) {
        this(context, credentials, database, attributes, (List<String>) null);
    }

    public BacktraceClient(Context context, BacktraceCredentials credentials, Database database, Map<String, Object> attributes, List<String> attachments) {
        super(context, credentials, database, attributes, attachments);
    }

    public void send(String message) {
        send(message, (OnServerResponseEventListener) null);
    }

    public void send(String message, OnServerResponseEventListener serverResponseEventListener) {
        super.send(new BacktraceReport(message), serverResponseEventListener);
    }

    public void send(Exception exception) {
        send(exception, (OnServerResponseEventListener) null);
    }

    public void send(Exception exception, OnServerResponseEventListener serverResponseEventListener) {
        super.send(new BacktraceReport(exception), serverResponseEventListener);
    }

    @Override // backtraceio.library.base.BacktraceBase, backtraceio.library.interfaces.Client
    public void send(BacktraceReport report) {
        send(report, (OnServerResponseEventListener) null);
    }

    @Override // backtraceio.library.base.BacktraceBase
    public void send(BacktraceReport report, OnServerResponseEventListener serverResponseEventListener) {
        super.send(report, serverResponseEventListener);
    }

    public void enableAnr() {
        this.anrWatchdog = new BacktraceANRWatchdog(this);
    }

    public void enableAnr(int timeout) {
        enableAnr(timeout, (OnApplicationNotRespondingEvent) null);
    }

    public void enableAnr(int timeout, OnApplicationNotRespondingEvent onApplicationNotRespondingEvent) {
        enableAnr(timeout, onApplicationNotRespondingEvent, false);
    }

    public void enableAnr(int timeout, boolean debug) {
        enableAnr(timeout, null, debug);
    }

    public void enableAnr(int timeout, OnApplicationNotRespondingEvent onApplicationNotRespondingEvent, boolean debug) {
        BacktraceANRWatchdog backtraceANRWatchdog = new BacktraceANRWatchdog(this, timeout, debug);
        this.anrWatchdog = backtraceANRWatchdog;
        backtraceANRWatchdog.setOnApplicationNotRespondingEvent(onApplicationNotRespondingEvent);
    }

    public void disableAnr() {
        BacktraceANRWatchdog backtraceANRWatchdog = this.anrWatchdog;
        if (backtraceANRWatchdog == null || backtraceANRWatchdog.isInterrupted()) {
            return;
        }
        this.anrWatchdog.stopMonitoringAnr();
    }
}
