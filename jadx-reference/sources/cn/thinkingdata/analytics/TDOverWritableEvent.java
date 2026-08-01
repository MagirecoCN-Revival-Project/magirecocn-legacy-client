package cn.thinkingdata.analytics;

import cn.thinkingdata.analytics.utils.j;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class TDOverWritableEvent extends d {
    private final String mEventId;

    public TDOverWritableEvent(String str, JSONObject jSONObject, String str2) {
        super(str, jSONObject);
        this.mEventId = str2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsEvent
    public j getDataType() {
        return j.TRACK_OVERWRITE;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsEvent
    public String getExtraField() {
        return "#event_id";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsEvent
    public String getExtraValue() {
        return this.mEventId;
    }
}
