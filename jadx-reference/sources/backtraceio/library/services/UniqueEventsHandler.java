package backtraceio.library.services;

import android.os.Message;
import backtraceio.library.common.BacktraceTimeHelper;
import backtraceio.library.interfaces.Api;
import backtraceio.library.models.metrics.EventsPayload;
import backtraceio.library.models.metrics.UniqueEvent;
import backtraceio.library.models.metrics.UniqueEventsPayload;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

/* loaded from: classes.dex */
public class UniqueEventsHandler extends BacktraceEventsHandler<UniqueEvent> {
    private static final transient String LOG_TAG = "UniqueEventsHandler";
    private static final String urlPrefix = "unique-events";

    @Override // backtraceio.library.services.BacktraceEventsHandler
    public /* bridge */ /* synthetic */ int getCount() {
        return super.getCount();
    }

    @Override // backtraceio.library.services.BacktraceEventsHandler
    public /* bridge */ /* synthetic */ int getMaximumNumberOfEvents() {
        return super.getMaximumNumberOfEvents();
    }

    @Override // backtraceio.library.services.BacktraceEventsHandler, android.os.Handler
    public /* bridge */ /* synthetic */ void handleMessage(Message msg) {
        super.handleMessage(msg);
    }

    @Override // backtraceio.library.services.BacktraceEventsHandler
    public /* bridge */ /* synthetic */ void send() {
        super.send();
    }

    @Override // backtraceio.library.services.BacktraceEventsHandler
    public /* bridge */ /* synthetic */ void setMaximumNumberOfEvents(int maximumNumberOfEvents) {
        super.setMaximumNumberOfEvents(maximumNumberOfEvents);
    }

    public UniqueEventsHandler(BacktraceMetrics backtraceMetrics, Api api, final BacktraceHandlerThread backtraceHandlerThread) {
        super(backtraceMetrics, api, backtraceHandlerThread, urlPrefix);
    }

    /* JADX DEBUG: Method merged with bridge method: getEventsPayload()Lbacktraceio/library/models/metrics/EventsPayload; */
    /* JADX DEBUG: Return type fixed from 'backtraceio.library.models.metrics.UniqueEventsPayload' to match base method */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // backtraceio.library.services.BacktraceEventsHandler
    public EventsPayload<UniqueEvent> getEventsPayload() {
        Map<String, Object> createLocalAttributes = this.backtraceMetrics.createLocalAttributes(null);
        Iterator it = this.events.iterator();
        while (it.hasNext()) {
            ((UniqueEvent) it.next()).update(BacktraceTimeHelper.getTimestampSeconds(), createLocalAttributes);
        }
        return new UniqueEventsPayload(this.events, this.application, this.appVersion);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v0, resolved type: backtraceio.library.interfaces.Api */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [backtraceio.library.models.metrics.UniqueEventsPayload] */
    @Override // backtraceio.library.services.BacktraceEventsHandler
    protected void sendEvents(ConcurrentLinkedDeque<UniqueEvent> events) {
        this.api.sendEventsPayload((UniqueEventsPayload) getEventsPayload());
    }

    @Override // backtraceio.library.services.BacktraceEventsHandler
    protected void sendEventsPayload(EventsPayload<UniqueEvent> payload) {
        this.api.sendEventsPayload((UniqueEventsPayload) payload);
    }
}
