package cn.thinkingdata.analytics;

import cn.thinkingdata.analytics.utils.j;
import java.util.Date;
import java.util.TimeZone;
import org.json.JSONObject;

/* loaded from: classes.dex */
public abstract class ThinkingAnalyticsEvent {
    private final String mEventName;
    private Date mEventTime;
    private final JSONObject mProperties;
    private TimeZone mTimeZone;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ThinkingAnalyticsEvent(String str, JSONObject jSONObject) {
        this.mEventName = str;
        this.mProperties = jSONObject;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract j getDataType();

    /* JADX INFO: Access modifiers changed from: package-private */
    public String getEventName() {
        return this.mEventName;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Date getEventTime() {
        return this.mEventTime;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract String getExtraField();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract String getExtraValue();

    /* JADX INFO: Access modifiers changed from: package-private */
    public JSONObject getProperties() {
        return this.mProperties;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public TimeZone getTimeZone() {
        return this.mTimeZone;
    }

    public void setEventTime(Date date) {
        this.mEventTime = date;
    }

    public void setEventTime(Date date, TimeZone timeZone) {
        this.mEventTime = date;
        this.mTimeZone = timeZone;
    }
}
