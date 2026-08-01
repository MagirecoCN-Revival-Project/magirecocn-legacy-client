package backtraceio.library.services;

import backtraceio.library.events.EventsOnServerResponseEventListener;
import backtraceio.library.events.EventsRequestHandler;
import backtraceio.library.events.OnServerErrorEventListener;
import backtraceio.library.models.metrics.EventsPayload;

/* loaded from: classes.dex */
public class BacktraceHandlerInputEvents extends BacktraceHandlerInput {
    public EventsOnServerResponseEventListener eventsOnServerResponseEventListener;
    public EventsRequestHandler eventsRequestHandler;
    public EventsPayload payload;

    public BacktraceHandlerInputEvents(EventsPayload payload, EventsOnServerResponseEventListener eventsOnServerResponseEventListener, OnServerErrorEventListener serverErrorEventListener, EventsRequestHandler eventsRequestHandler) {
        super(serverErrorEventListener);
        this.payload = payload;
        this.eventsOnServerResponseEventListener = eventsOnServerResponseEventListener;
        this.eventsRequestHandler = eventsRequestHandler;
    }
}
