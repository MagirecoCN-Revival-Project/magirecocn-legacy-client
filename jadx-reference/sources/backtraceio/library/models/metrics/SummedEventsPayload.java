package backtraceio.library.models.metrics;

import com.google.gson.annotations.SerializedName;
import java.util.concurrent.ConcurrentLinkedDeque;

/* loaded from: classes.dex */
public class SummedEventsPayload extends EventsPayload<SummedEvent> {

    @SerializedName("summed_events")
    private final ConcurrentLinkedDeque<SummedEvent> summedEvents;

    public SummedEventsPayload(ConcurrentLinkedDeque<SummedEvent> events, String application, String appVersion) {
        super(application, appVersion);
        this.summedEvents = events;
    }

    @Override // backtraceio.library.models.metrics.EventsPayload
    public ConcurrentLinkedDeque<SummedEvent> getEvents() {
        return this.summedEvents;
    }
}
