package backtraceio.library.models.metrics;

import backtraceio.library.common.BacktraceStringHelper;
import backtraceio.library.common.BacktraceTimeHelper;
import backtraceio.library.logger.BacktraceLogger;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class UniqueEvent extends Event {
    private static final transient String LOG_TAG = "UniqueEvent";

    @SerializedName("unique")
    private final List<String> name;

    public UniqueEvent(String name) {
        this(name, new HashMap());
    }

    public UniqueEvent(String name, Map<String, Object> attributes) {
        this(name, BacktraceTimeHelper.getTimestampSeconds(), attributes);
    }

    public UniqueEvent(String name, long timestamp, Map<String, Object> attributes) {
        super(timestamp);
        this.name = new ArrayList<String>(name) { // from class: backtraceio.library.models.metrics.UniqueEvent.1
            final /* synthetic */ String val$name;

            {
                this.val$name = name;
                add(name);
            }
        };
        addAttributesImpl(attributes);
    }

    @Override // backtraceio.library.models.metrics.Event
    public String getName() {
        List<String> list = this.name;
        if (list != null && list.size() > 0 && !BacktraceStringHelper.isNullOrEmpty(this.name.get(0))) {
            return this.name.get(0);
        }
        BacktraceLogger.e(LOG_TAG, "Unique Event name must not be null or empty");
        return "";
    }

    public void update(long timestamp, Map<String, Object> attributes) {
        this.timestamp = timestamp;
        addAttributesImpl(attributes);
    }
}
