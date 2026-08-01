package backtraceio.library.models.metrics;

import backtraceio.library.common.BacktraceTimeHelper;
import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public final class SummedEvent extends Event {

    @SerializedName("metric_group")
    private final String name;

    public SummedEvent(String name) {
        this(name, new HashMap());
    }

    public SummedEvent(String name, Map<String, Object> attributes) {
        this(name, BacktraceTimeHelper.getTimestampSeconds(), attributes);
    }

    public SummedEvent(String name, long timestamp, Map<String, Object> attributes) {
        super(timestamp);
        this.name = name;
        addAttributesImpl(attributes);
    }

    public SummedEvent(SummedEvent summedEvent) {
        this(summedEvent.name, summedEvent.timestamp, summedEvent.attributes);
    }

    @Override // backtraceio.library.models.metrics.Event
    public String getName() {
        return this.name;
    }

    public void addAttributes(Map<String, Object> attributes) {
        addAttributesImpl(attributes);
    }
}
