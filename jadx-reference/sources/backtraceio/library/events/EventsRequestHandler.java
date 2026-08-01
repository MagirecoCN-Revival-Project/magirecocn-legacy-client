package backtraceio.library.events;

import backtraceio.library.models.metrics.EventsPayload;
import backtraceio.library.models.metrics.EventsResult;

/* loaded from: classes.dex */
public interface EventsRequestHandler {
    EventsResult onRequest(EventsPayload data);
}
