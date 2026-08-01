package backtraceio.library.models.metrics;

import backtraceio.library.models.types.BacktraceResultStatus;

/* loaded from: classes.dex */
public class EventsResult {
    private EventsPayload eventsPayload;
    public String message;
    public BacktraceResultStatus status;
    private int statusCode;

    public EventsResult(EventsPayload payload, String message, BacktraceResultStatus status, int statusCode) {
        this.status = BacktraceResultStatus.Ok;
        this.statusCode = -1;
        setEventsPayload(payload);
        this.message = message;
        this.status = status;
        this.statusCode = statusCode;
    }

    public static EventsResult OnError(EventsPayload payload, Exception exception, int statusCode) {
        return new EventsResult(payload, exception.getMessage(), BacktraceResultStatus.ServerError, statusCode);
    }

    public EventsPayload getEventsPayload() {
        return this.eventsPayload;
    }

    public void setEventsPayload(EventsPayload eventsPayload) {
        this.eventsPayload = eventsPayload;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
