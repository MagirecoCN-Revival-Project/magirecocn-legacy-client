package backtraceio.library.models.metrics;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes.dex */
public class EventsMetadata {

    @SerializedName("dropped_events")
    private int droppedEvents;

    public EventsMetadata(int droppedEvents) {
        this.droppedEvents = droppedEvents;
    }

    public int getDroppedEvents() {
        return this.droppedEvents;
    }

    public void setDroppedEvents(int droppedEvents) {
        this.droppedEvents = droppedEvents;
    }
}
