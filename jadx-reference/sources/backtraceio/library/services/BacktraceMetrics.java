package backtraceio.library.services;

import android.content.Context;
import backtraceio.library.common.BacktraceStringHelper;
import backtraceio.library.common.BacktraceTimeHelper;
import backtraceio.library.events.EventsOnServerResponseEventListener;
import backtraceio.library.events.EventsRequestHandler;
import backtraceio.library.events.RequestHandler;
import backtraceio.library.interfaces.Api;
import backtraceio.library.interfaces.Metrics;
import backtraceio.library.logger.BacktraceLogger;
import backtraceio.library.models.BacktraceMetricsSettings;
import backtraceio.library.models.json.BacktraceAttributes;
import backtraceio.library.models.metrics.SummedEvent;
import backtraceio.library.models.metrics.UniqueEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

/* loaded from: classes.dex */
public final class BacktraceMetrics implements Metrics {
    private static final transient String LOG_TAG = "BacktraceMetrics";
    public static final String defaultBaseUrl = "https://events.backtrace.io/api";
    public static final int defaultTimeBetweenRetriesMs = 10000;
    public static final int defaultTimeIntervalInMin = 30;
    public static final long defaultTimeIntervalMs = 1800000;
    public static final int maxNumberOfAttempts = 3;
    public static final int maxTimeBetweenRetriesMs = 300000;
    private final Api backtraceApi;
    protected Context context;
    protected Map<String, Object> customReportAttributes;
    public SummedEventsHandler summedEventsHandler;
    public UniqueEventsHandler uniqueEventsHandler;
    public final String defaultUniqueEventName = "guid";
    private final String startupSummedEventName = "Application Launches";
    protected BacktraceMetricsSettings settings = null;
    private String startupUniqueEventName = "guid";
    private int maximumNumberOfEvents = 350;
    private final RequestHandler requestHandler = null;

    public BacktraceMetrics(Context context, Map<String, Object> customReportAttributes, Api backtraceApi) {
        this.context = context;
        this.customReportAttributes = customReportAttributes;
        this.backtraceApi = backtraceApi;
    }

    @Override // backtraceio.library.interfaces.Metrics
    public void enable(BacktraceMetricsSettings settings) {
        this.settings = settings;
        BacktraceAttributes.enableMetrics();
        try {
            startMetricsEventHandlers(this.backtraceApi);
            sendStartupEvent();
            BacktraceLogger.d(LOG_TAG, "Metrics enabled");
        } catch (Exception e) {
            BacktraceLogger.e(LOG_TAG, "Could not enable metrics, exception " + e.getMessage());
        }
    }

    private void startMetricsEventHandlers(Api backtraceApi) {
        this.uniqueEventsHandler = backtraceApi.enableUniqueEvents(this);
        this.summedEventsHandler = backtraceApi.enableSummedEvents(this);
    }

    protected String getStartupUniqueEventName() {
        return this.startupUniqueEventName;
    }

    public void setStartupUniqueEventName(String startupUniqueEventName) {
        this.startupUniqueEventName = startupUniqueEventName;
    }

    public String getBaseUrl() {
        return this.settings.getBaseUrl();
    }

    @Override // backtraceio.library.interfaces.Metrics
    public void sendStartupEvent() {
        addUniqueEvent(this.startupUniqueEventName);
        addSummedEvent("Application Launches");
        this.uniqueEventsHandler.send();
        this.summedEventsHandler.send();
    }

    @Override // backtraceio.library.interfaces.Metrics
    public void send() {
        this.uniqueEventsHandler.send();
        this.summedEventsHandler.send();
    }

    @Override // backtraceio.library.interfaces.Metrics
    public boolean addUniqueEvent(String attributeName) {
        return addUniqueEvent(attributeName, null);
    }

    @Override // backtraceio.library.interfaces.Metrics
    public boolean addUniqueEvent(String attributeName, Map<String, Object> attributes) {
        if (!shouldProcessEvent(attributeName)) {
            BacktraceLogger.w(LOG_TAG, "Skipping report");
            return false;
        }
        Map<String, Object> createLocalAttributes = createLocalAttributes(attributes);
        if (!BacktraceStringHelper.isObjectNotNullOrNotEmptyString(createLocalAttributes.get(attributeName))) {
            BacktraceLogger.w(LOG_TAG, "Attribute name for Unique Event is not available in attribute scope");
            return false;
        }
        Iterator it = this.uniqueEventsHandler.events.iterator();
        while (it.hasNext()) {
            if (((UniqueEvent) it.next()).getName().equals(attributeName)) {
                BacktraceLogger.w(LOG_TAG, "Already defined unique event with this attribute name, skipping");
                return false;
            }
        }
        this.uniqueEventsHandler.events.addLast(new UniqueEvent(attributeName, BacktraceTimeHelper.getTimestampSeconds(), createLocalAttributes));
        if (count() != this.maximumNumberOfEvents) {
            return true;
        }
        this.uniqueEventsHandler.send();
        this.summedEventsHandler.send();
        return true;
    }

    @Override // backtraceio.library.interfaces.Metrics
    public void setMaximumNumberOfEvents(int maximumNumberOfEvents) {
        this.maximumNumberOfEvents = maximumNumberOfEvents;
        this.uniqueEventsHandler.setMaximumNumberOfEvents(maximumNumberOfEvents);
        this.summedEventsHandler.setMaximumNumberOfEvents(maximumNumberOfEvents);
    }

    @Override // backtraceio.library.interfaces.Metrics
    public int count() {
        return getUniqueEvents().size() + getSummedEvents().size();
    }

    @Override // backtraceio.library.interfaces.Metrics
    public boolean addSummedEvent(String metricGroupName) {
        return addSummedEvent(metricGroupName, null);
    }

    @Override // backtraceio.library.interfaces.Metrics
    public boolean addSummedEvent(String metricGroupName, Map<String, Object> attributes) {
        if (!shouldProcessEvent(metricGroupName)) {
            BacktraceLogger.w(LOG_TAG, "Skipping report");
            return false;
        }
        HashMap hashMap = new HashMap();
        if (attributes != null) {
            hashMap.putAll(attributes);
        }
        this.summedEventsHandler.events.addLast(new SummedEvent(metricGroupName, BacktraceTimeHelper.getTimestampSeconds(), hashMap));
        if (count() != this.maximumNumberOfEvents) {
            return true;
        }
        this.uniqueEventsHandler.send();
        this.summedEventsHandler.send();
        return true;
    }

    /* JADX DEBUG: Type inference failed for r0v1. Raw type applied. Possible types: java.util.concurrent.ConcurrentLinkedDeque<T extends backtraceio.library.models.metrics.Event>, java.util.concurrent.ConcurrentLinkedDeque<backtraceio.library.models.metrics.UniqueEvent> */
    @Override // backtraceio.library.interfaces.Metrics
    public ConcurrentLinkedDeque<UniqueEvent> getUniqueEvents() {
        return this.uniqueEventsHandler.events;
    }

    /* JADX DEBUG: Type inference failed for r0v1. Raw type applied. Possible types: java.util.concurrent.ConcurrentLinkedDeque<T extends backtraceio.library.models.metrics.Event>, java.util.concurrent.ConcurrentLinkedDeque<backtraceio.library.models.metrics.SummedEvent> */
    @Override // backtraceio.library.interfaces.Metrics
    public ConcurrentLinkedDeque<SummedEvent> getSummedEvents() {
        return this.summedEventsHandler.events;
    }

    @Override // backtraceio.library.interfaces.Metrics
    public void setUniqueEventsRequestHandler(EventsRequestHandler eventsRequestHandler) {
        this.backtraceApi.setUniqueEventsRequestHandler(eventsRequestHandler);
    }

    @Override // backtraceio.library.interfaces.Metrics
    public void setSummedEventsRequestHandler(EventsRequestHandler eventsRequestHandler) {
        this.backtraceApi.setSummedEventsRequestHandler(eventsRequestHandler);
    }

    private boolean shouldProcessEvent(String name) {
        if (BacktraceStringHelper.isNullOrEmpty(name)) {
            BacktraceLogger.e(LOG_TAG, "Cannot process event, attribute name is null or empty");
            return false;
        }
        if (this.maximumNumberOfEvents <= 0 || count() + 1 <= this.maximumNumberOfEvents) {
            return true;
        }
        BacktraceLogger.e(LOG_TAG, "Cannot process event, reached maximum number of events: " + this.maximumNumberOfEvents + " events count: " + count());
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Map<String, Object> createLocalAttributes(Map<String, Object> attributes) {
        HashMap hashMap = new HashMap();
        if (attributes != null) {
            hashMap.putAll(attributes);
        }
        hashMap.putAll(new BacktraceAttributes(this.context, null, this.customReportAttributes).getAllAttributes());
        return hashMap;
    }

    @Override // backtraceio.library.interfaces.Metrics
    public void setUniqueEventsOnServerResponse(EventsOnServerResponseEventListener callback) {
        this.backtraceApi.setUniqueEventsOnServerResponse(callback);
    }

    @Override // backtraceio.library.interfaces.Metrics
    public void setSummedEventsOnServerResponse(EventsOnServerResponseEventListener callback) {
        this.backtraceApi.setSummedEventsOnServerResponse(callback);
    }
}
