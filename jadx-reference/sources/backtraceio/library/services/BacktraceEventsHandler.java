package backtraceio.library.services;

import android.os.Handler;
import android.os.Message;
import backtraceio.library.common.BacktraceMathHelper;
import backtraceio.library.common.BacktraceSerializeHelper;
import backtraceio.library.interfaces.Api;
import backtraceio.library.logger.BacktraceLogger;
import backtraceio.library.models.json.BacktraceAttributes;
import backtraceio.library.models.metrics.Event;
import backtraceio.library.models.metrics.EventsPayload;
import backtraceio.library.models.metrics.EventsResult;
import java.util.concurrent.ConcurrentLinkedDeque;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public abstract class BacktraceEventsHandler<T extends Event> extends Handler {
    private static final transient String LOG_TAG = "BacktraceEventsHandler";
    protected final Api api;
    protected String appVersion;
    protected String application;
    protected final BacktraceHandlerThread backtraceHandlerThread;
    protected final BacktraceMetrics backtraceMetrics;
    protected ConcurrentLinkedDeque<T> events;
    private int maximumNumberOfEvents;
    private final String submissionUrl;
    private final int timeBetweenRetriesMillis;

    protected abstract EventsPayload<T> getEventsPayload();

    protected void onMaximumAttemptsReached(ConcurrentLinkedDeque<T> events) {
    }

    protected abstract void sendEvents(ConcurrentLinkedDeque<T> events);

    protected abstract void sendEventsPayload(EventsPayload<T> payload);

    public BacktraceEventsHandler(BacktraceMetrics backtraceMetrics, Api api, final BacktraceHandlerThread backtraceHandlerThread, String urlPrefix) {
        super(backtraceHandlerThread.getLooper());
        this.events = new ConcurrentLinkedDeque<>();
        this.maximumNumberOfEvents = 350;
        if (!backtraceHandlerThread.isAlive()) {
            throw new NullPointerException("Handler thread is not alive, this should not happen");
        }
        this.backtraceMetrics = backtraceMetrics;
        this.backtraceHandlerThread = backtraceHandlerThread;
        this.api = api;
        this.submissionUrl = backtraceMetrics.settings.getSubmissionUrl(urlPrefix);
        this.timeBetweenRetriesMillis = backtraceMetrics.settings.getTimeBetweenRetriesMillis();
        final long timeIntervalMillis = backtraceMetrics.settings.getTimeIntervalMillis();
        BacktraceAttributes backtraceAttributes = new BacktraceAttributes(backtraceMetrics.context, null, null);
        this.application = backtraceAttributes.getApplicationName();
        this.appVersion = backtraceAttributes.getApplicationVersionOrEmpty();
        if (timeIntervalMillis != 0) {
            postDelayed(new Runnable() { // from class: backtraceio.library.services.BacktraceEventsHandler.1
                @Override // java.lang.Runnable
                public void run() {
                    this.send();
                    this.postDelayed(this, timeIntervalMillis);
                }
            }, timeIntervalMillis);
        }
    }

    public int getCount() {
        return this.events.size();
    }

    public int getMaximumNumberOfEvents() {
        return this.maximumNumberOfEvents;
    }

    public void setMaximumNumberOfEvents(int maximumNumberOfEvents) {
        this.maximumNumberOfEvents = maximumNumberOfEvents;
    }

    public void send() {
        ConcurrentLinkedDeque<T> concurrentLinkedDeque = this.events;
        if (concurrentLinkedDeque == null || concurrentLinkedDeque.size() == 0) {
            return;
        }
        sendEvents(this.events);
    }

    @Override // android.os.Handler
    public void handleMessage(Message msg) {
        BacktraceHandlerInputEvents backtraceHandlerInputEvents = (BacktraceHandlerInputEvents) msg.obj;
        EventsResult eventsResult = getEventsResult(backtraceHandlerInputEvents);
        if (backtraceHandlerInputEvents.eventsOnServerResponseEventListener != null) {
            BacktraceLogger.d(LOG_TAG, "Processing result using custom event");
            backtraceHandlerInputEvents.eventsOnServerResponseEventListener.onEvent(eventsResult);
        }
        retrySendEvents(backtraceHandlerInputEvents, eventsResult.getStatusCode());
    }

    private long calculateNextRetryTime(int numRetries) {
        double clamp = BacktraceMathHelper.clamp(this.timeBetweenRetriesMillis * Math.pow(10.0d, numRetries - 1), 0.0d, 300000.0d);
        return (long) BacktraceMathHelper.uniform(clamp, (1.0d * clamp) + clamp);
    }

    private EventsResult getEventsResult(BacktraceHandlerInputEvents input) {
        if (input.eventsRequestHandler != null) {
            BacktraceLogger.d(LOG_TAG, "Sending using custom request handler");
            return input.eventsRequestHandler.onRequest(input.payload);
        }
        BacktraceLogger.d(LOG_TAG, "Sending report using default request handler");
        return BacktraceReportSender.sendEvents(this.submissionUrl, BacktraceSerializeHelper.toJson(input.payload), input.payload, input.serverErrorEventListener);
    }

    private void retrySendEvents(final BacktraceHandlerInputEvents input, int statusCode) {
        if (statusCode <= 501 || statusCode == 505) {
            return;
        }
        EventsPayload eventsPayload = input.payload;
        final int i = eventsPayload.numRetries + 1;
        eventsPayload.numRetries = i;
        if (i >= 3 || this.timeBetweenRetriesMillis == 0) {
            onMaximumAttemptsReached(input.payload.getEvents());
        } else {
            postDelayed(new Runnable() { // from class: backtraceio.library.services.BacktraceEventsHandler.2
                @Override // java.lang.Runnable
                public void run() {
                    EventsPayload<T> eventsPayload2 = input.payload;
                    eventsPayload2.setDroppedEvents(i);
                    BacktraceEventsHandler.this.sendEventsPayload(eventsPayload2);
                }
            }, calculateNextRetryTime(i));
        }
    }
}
