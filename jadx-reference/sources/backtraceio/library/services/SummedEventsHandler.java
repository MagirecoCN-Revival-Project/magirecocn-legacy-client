package backtraceio.library.services;

import android.os.Message;
import backtraceio.library.interfaces.Api;
import backtraceio.library.models.metrics.EventsPayload;
import backtraceio.library.models.metrics.SummedEvent;
import backtraceio.library.models.metrics.SummedEventsPayload;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

/* loaded from: classes.dex */
public class SummedEventsHandler extends BacktraceEventsHandler<SummedEvent> {
    private static final transient String LOG_TAG = "SummedEventsHandler";
    private static final String urlPrefix = "summed-events";

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

    public SummedEventsHandler(BacktraceMetrics backtraceMetrics, Api api, final BacktraceHandlerThread backtraceHandlerThread) {
        super(backtraceMetrics, api, backtraceHandlerThread, urlPrefix);
    }

    /* JADX DEBUG: Method merged with bridge method: getEventsPayload()Lbacktraceio/library/models/metrics/EventsPayload; */
    /* JADX DEBUG: Return type fixed from 'backtraceio.library.models.metrics.SummedEventsPayload' to match base method */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // backtraceio.library.services.BacktraceEventsHandler
    public EventsPayload<SummedEvent> getEventsPayload() {
        Map<String, Object> createLocalAttributes = this.backtraceMetrics.createLocalAttributes(null);
        ConcurrentLinkedDeque concurrentLinkedDeque = new ConcurrentLinkedDeque();
        Iterator it = this.events.iterator();
        while (it.hasNext()) {
            SummedEvent summedEvent = (SummedEvent) it.next();
            summedEvent.addAttributes(createLocalAttributes);
            concurrentLinkedDeque.addLast(new SummedEvent(summedEvent));
        }
        this.events.clear();
        return new SummedEventsPayload(concurrentLinkedDeque, this.application, this.appVersion);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v0, resolved type: backtraceio.library.interfaces.Api */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [backtraceio.library.models.metrics.SummedEventsPayload] */
    @Override // backtraceio.library.services.BacktraceEventsHandler
    protected void sendEvents(ConcurrentLinkedDeque<SummedEvent> events) {
        this.api.sendEventsPayload((SummedEventsPayload) getEventsPayload());
    }

    @Override // backtraceio.library.services.BacktraceEventsHandler
    protected void sendEventsPayload(EventsPayload<SummedEvent> payload) {
        this.api.sendEventsPayload((SummedEventsPayload) payload);
    }

    @Override // backtraceio.library.services.BacktraceEventsHandler
    protected void onMaximumAttemptsReached(ConcurrentLinkedDeque<SummedEvent> events) {
        if (this.events.size() + events.size() < getMaximumNumberOfEvents()) {
            Iterator<SummedEvent> it = events.iterator();
            while (it.hasNext()) {
                this.events.addFirst(it.next());
            }
        }
    }
}
