package backtraceio.library.models.metrics;

import backtraceio.library.models.metrics.Event;
import com.google.gson.annotations.SerializedName;
import java.util.concurrent.ConcurrentLinkedDeque;

/* loaded from: classes.dex */
public abstract class EventsPayload<T extends Event> {
    private static final transient String LOG_TAG = "EventsPayload";

    @SerializedName("appversion")
    private final String appVersion;

    @SerializedName("application")
    private final String application;
    public transient int numRetries = 0;

    @SerializedName("metadata")
    private final EventsMetadata eventsMetadata = new EventsMetadata(0);

    public abstract ConcurrentLinkedDeque<T> getEvents();

    public EventsPayload(String application, String appVersion) {
        this.application = application;
        this.appVersion = appVersion;
    }

    public int getDroppedEvents() {
        return this.eventsMetadata.getDroppedEvents();
    }

    public void setDroppedEvents(int droppedEvents) {
        this.eventsMetadata.setDroppedEvents(droppedEvents);
    }
}
