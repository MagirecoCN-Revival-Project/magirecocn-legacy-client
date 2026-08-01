package backtraceio.library.models.metrics;

import backtraceio.library.common.BacktraceStringHelper;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class Event {

    @SerializedName("attributes")
    protected Map<String, Object> attributes;

    @SerializedName(AppMeasurement.Param.TIMESTAMP)
    protected long timestamp;

    public abstract String getName();

    public Event(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void addAttributesImpl(Map<String, Object> attributes) {
        if (attributes == null || attributes.size() == 0) {
            return;
        }
        HashMap hashMap = new HashMap();
        for (String str : attributes.keySet()) {
            Object obj = attributes.get(str);
            if (BacktraceStringHelper.isObjectNotNullOrNotEmptyString(obj)) {
                hashMap.put(str, obj);
            }
        }
        Map<String, Object> map = this.attributes;
        if (map == null) {
            this.attributes = hashMap;
        } else {
            map.putAll(hashMap);
        }
    }
}
