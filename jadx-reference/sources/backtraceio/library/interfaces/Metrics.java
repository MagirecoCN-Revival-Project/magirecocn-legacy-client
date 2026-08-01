package backtraceio.library.interfaces;

import backtraceio.library.events.EventsOnServerResponseEventListener;
import backtraceio.library.events.EventsRequestHandler;
import backtraceio.library.models.BacktraceMetricsSettings;
import backtraceio.library.models.metrics.SummedEvent;
import backtraceio.library.models.metrics.UniqueEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

/* loaded from: classes.dex */
public interface Metrics {
    boolean addSummedEvent(String metricGroupName);

    boolean addSummedEvent(String metricGroupName, Map<String, Object> attributes);

    boolean addUniqueEvent(String name);

    boolean addUniqueEvent(String name, Map<String, Object> attributes);

    int count();

    void enable(BacktraceMetricsSettings settings);

    ConcurrentLinkedDeque<SummedEvent> getSummedEvents();

    ConcurrentLinkedDeque<UniqueEvent> getUniqueEvents();

    void send();

    void sendStartupEvent();

    void setMaximumNumberOfEvents(int maximumNumberOfEvents);

    void setSummedEventsOnServerResponse(EventsOnServerResponseEventListener callback);

    void setSummedEventsRequestHandler(EventsRequestHandler eventsRequestHandler);

    void setUniqueEventsOnServerResponse(EventsOnServerResponseEventListener callback);

    void setUniqueEventsRequestHandler(EventsRequestHandler eventsRequestHandler);
}
