package backtraceio.library.services;

import backtraceio.library.BacktraceCredentials;
import backtraceio.library.events.EventsOnServerResponseEventListener;
import backtraceio.library.events.EventsRequestHandler;
import backtraceio.library.events.OnServerErrorEventListener;
import backtraceio.library.events.OnServerResponseEventListener;
import backtraceio.library.events.RequestHandler;
import backtraceio.library.interfaces.Api;
import backtraceio.library.logger.BacktraceLogger;
import backtraceio.library.models.BacktraceData;
import backtraceio.library.models.metrics.SummedEventsPayload;
import backtraceio.library.models.metrics.UniqueEventsPayload;

/* loaded from: classes.dex */
public class BacktraceApi implements Api {
    private static final transient String LOG_TAG = "BacktraceApi";
    private final String reportSubmissionUrl;
    private String summedEventsSubmissionUrl;
    private final transient BacktraceHandlerThread threadSender;
    private String uniqueEventsSubmissionUrl;
    private OnServerErrorEventListener onServerError = null;
    private RequestHandler requestHandler = null;
    private EventsRequestHandler uniqueEventsRequestHandler = null;
    private EventsRequestHandler summedEventsRequestHandler = null;
    private EventsOnServerResponseEventListener uniqueEventsServerResponse = null;
    private EventsOnServerResponseEventListener summedEventsServerResponse = null;

    public BacktraceApi(BacktraceCredentials credentials) {
        if (credentials == null) {
            BacktraceLogger.e(LOG_TAG, "BacktraceCredentials parameter passed to BacktraceApi constructor is null");
            throw new IllegalArgumentException("BacktraceCredentials cannot be null");
        }
        String uri = credentials.getSubmissionUrl().toString();
        this.reportSubmissionUrl = uri;
        this.threadSender = new BacktraceHandlerThread("BacktraceHandlerThread", uri);
    }

    @Override // backtraceio.library.interfaces.Api
    public void setUniqueEventsRequestHandler(EventsRequestHandler uniqueEventsRequestHandler) {
        this.uniqueEventsRequestHandler = uniqueEventsRequestHandler;
    }

    @Override // backtraceio.library.interfaces.Api
    public void setSummedEventsRequestHandler(EventsRequestHandler summedEventsRequestHandler) {
        this.summedEventsRequestHandler = summedEventsRequestHandler;
    }

    @Override // backtraceio.library.interfaces.Api
    public void setUniqueEventsOnServerResponse(EventsOnServerResponseEventListener callback) {
        this.uniqueEventsServerResponse = callback;
    }

    @Override // backtraceio.library.interfaces.Api
    public void setSummedEventsOnServerResponse(EventsOnServerResponseEventListener callback) {
        this.summedEventsServerResponse = callback;
    }

    @Override // backtraceio.library.interfaces.Api
    public void setOnServerError(OnServerErrorEventListener onServerError) {
        this.onServerError = onServerError;
    }

    @Override // backtraceio.library.interfaces.Api
    public void setRequestHandler(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @Override // backtraceio.library.interfaces.Api
    public UniqueEventsHandler enableUniqueEvents(BacktraceMetrics backtraceMetrics) {
        return this.threadSender.createUniqueEventsHandler(backtraceMetrics, this);
    }

    @Override // backtraceio.library.interfaces.Api
    public SummedEventsHandler enableSummedEvents(BacktraceMetrics backtraceMetrics) {
        return this.threadSender.createSummedEventsHandler(backtraceMetrics, this);
    }

    @Override // backtraceio.library.interfaces.Api
    public void send(BacktraceData data, OnServerResponseEventListener callback) {
        this.threadSender.sendReport(new BacktraceHandlerInputReport(data, callback, this.onServerError, this.requestHandler));
    }

    @Override // backtraceio.library.interfaces.Api
    public void sendEventsPayload(UniqueEventsPayload payload) {
        this.threadSender.sendUniqueEvents(new BacktraceHandlerInputEvents(payload, this.uniqueEventsServerResponse, this.onServerError, this.uniqueEventsRequestHandler));
    }

    @Override // backtraceio.library.interfaces.Api
    public void sendEventsPayload(SummedEventsPayload payload) {
        this.threadSender.sendSummedEvents(new BacktraceHandlerInputEvents(payload, this.summedEventsServerResponse, this.onServerError, this.summedEventsRequestHandler));
    }
}
