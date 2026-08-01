package cn.thinkingdata.analytics;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import cn.thinkingdata.analytics.ThinkingAnalyticsSDK;
import cn.thinkingdata.analytics.utils.f;
import cn.thinkingdata.analytics.utils.j;
import cn.thinkingdata.analytics.utils.k;
import cn.thinkingdata.analytics.utils.p;
import cn.thinkingdata.core.router.TRouterMap;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class c extends ThinkingAnalyticsSDK {
    Context a;
    String b;
    private final JSONObject c;

    /* loaded from: classes.dex */
    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[j.values().length];
            a = iArr;
            try {
                iArr[j.TRACK_OVERWRITE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[j.TRACK_UPDATE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[j.TRACK.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public c(TDConfig tDConfig) {
        super(tDConfig, new boolean[0]);
        this.a = tDConfig.mContext;
        this.c = new JSONObject();
        this.b = p.b(this.a);
    }

    double a(String str, long j) {
        cn.thinkingdata.analytics.f.d dVar;
        synchronized (this.mTrackTimer) {
            dVar = this.mTrackTimer.get(str);
            this.mTrackTimer.remove(str);
        }
        if (dVar != null) {
            return Double.parseDouble(dVar.a(j));
        }
        return 0.0d;
    }

    public Intent a() {
        Intent intent = new Intent();
        String d = p.d(this.a);
        String str = "cn.thinkingdata.receiver";
        if (d.length() != 0) {
            str = d + TRouterMap.DOT + "cn.thinkingdata.receiver";
        }
        intent.setAction(str);
        intent.putExtra("#app_id", this.mConfig.getName());
        return intent;
    }

    public JSONObject a(String str, JSONObject jSONObject) {
        JSONObject dynamicSuperProperties;
        JSONObject jSONObject2 = new JSONObject();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        try {
            jSONObject2.put("TA_KEY_SUBPROCESS_TAG__TA__", true);
            if (!TDPresetProperties.disableList.contains("#bundle_id")) {
                jSONObject2.put("#bundle_id", this.b);
            }
            double a2 = a(str, elapsedRealtime);
            if (a2 > 0.0d && !TDPresetProperties.disableList.contains("#duration")) {
                jSONObject2.put("#duration", a2);
            }
        } catch (JSONException unused) {
        }
        if (getDynamicSuperPropertiesTracker() != null && (dynamicSuperProperties = getDynamicSuperPropertiesTracker().getDynamicSuperProperties()) != null) {
            try {
                p.a(dynamicSuperProperties, jSONObject2, this.mConfig.getDefaultTimeZone());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            p.a(jSONObject, jSONObject2, this.mConfig.getDefaultTimeZone());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject2;
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void autoTrack(String str, JSONObject jSONObject) {
        Intent a2 = a();
        a2.putExtra("#event_name", str);
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        JSONObject a3 = a(str, jSONObject);
        try {
            JSONObject optJSONObject = getAutoTrackProperties().optJSONObject(str);
            if (optJSONObject != null) {
                p.a(optJSONObject, a3, this.mConfig.getDefaultTimeZone());
            }
            a2.putExtra("properties", a3.toString());
            a2.putExtra("TD_ACTION", 1048582);
            Context context = this.a;
            if (context != null) {
                context.sendBroadcast(a2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void clearSuperProperties() {
        Intent a2 = a();
        a2.putExtra("TD_ACTION", 2097159);
        Context context = this.a;
        if (context != null) {
            context.sendBroadcast(a2);
        }
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void enableAutoTrack(List<ThinkingAnalyticsSDK.AutoTrackEventType> list, ThinkingAnalyticsSDK.AutoTrackEventListener autoTrackEventListener) {
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void enableTracking(boolean z) {
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void flush() {
        Intent a2 = a();
        a2.putExtra("TD_ACTION", 2097157);
        Context context = this.a;
        if (context != null) {
            context.sendBroadcast(a2);
        }
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public JSONObject getAutoTrackProperties() {
        return this.c;
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public boolean hasOptOut() {
        return false;
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void identify(String str) {
        Intent a2 = a();
        a2.putExtra("TD_ACTION", 2097156);
        if (str == null || str.length() <= 0) {
            str = "";
        }
        a2.putExtra("#distinct_id", str);
        Context context = this.a;
        if (context != null) {
            context.sendBroadcast(a2);
        }
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void login(String str) {
        Intent a2 = a();
        a2.putExtra("TD_ACTION", 2097154);
        if (str == null || str.length() <= 0) {
            str = "";
        }
        a2.putExtra("#account_id", str);
        Context context = this.a;
        if (context != null) {
            context.sendBroadcast(a2);
        }
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void logout() {
        Intent a2 = a();
        a2.putExtra("TD_ACTION", 2097155);
        Context context = this.a;
        if (context != null) {
            context.sendBroadcast(a2);
        }
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void optInTracking() {
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void optOutTracking() {
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void optOutTrackingAndDeleteUser() {
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void setAutoTrackProperties(List<ThinkingAnalyticsSDK.AutoTrackEventType> list, JSONObject jSONObject) {
        if (hasDisabled()) {
            return;
        }
        if (jSONObject != null) {
            try {
                if (f.a(jSONObject)) {
                    JSONObject jSONObject2 = new JSONObject();
                    for (ThinkingAnalyticsSDK.AutoTrackEventType autoTrackEventType : list) {
                        JSONObject jSONObject3 = new JSONObject();
                        p.a(jSONObject, jSONObject3, this.mConfig.getDefaultTimeZone());
                        jSONObject2.put(autoTrackEventType.getEventName(), jSONObject3);
                    }
                    synchronized (this.c) {
                        p.b(jSONObject2, this.c, this.mConfig.getDefaultTimeZone());
                    }
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        if (this.mConfig.shouldThrowException()) {
            throw new k("Set autoTrackEvent properties failed. Please refer to the SDK debug log for details.");
        }
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void setNetworkType(ThinkingAnalyticsSDK.ThinkingdataNetworkType thinkingdataNetworkType) {
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void setSuperProperties(JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        try {
            p.a(jSONObject, jSONObject2, this.mConfig.getDefaultTimeZone());
            Intent a2 = a();
            a2.putExtra("TD_ACTION", 2097153);
            if (jSONObject != null) {
                a2.putExtra("properties", jSONObject2.toString());
            }
            Context context = this.a;
            if (context != null) {
                context.sendBroadcast(a2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void setTrackStatus(ThinkingAnalyticsSDK.TATrackStatus tATrackStatus) {
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x003f  */
    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void track(ThinkingAnalyticsEvent thinkingAnalyticsEvent) {
        int i;
        Context context;
        Intent a2 = a();
        int i2 = a.a[thinkingAnalyticsEvent.getDataType().ordinal()];
        if (i2 == 1) {
            i = 1048581;
        } else {
            if (i2 != 2) {
                if (i2 == 3) {
                    i = 1048579;
                }
                a2.putExtra("#event_name", thinkingAnalyticsEvent.getEventName());
                a2.putExtra("properties", a(thinkingAnalyticsEvent.getEventName(), thinkingAnalyticsEvent.getProperties() != null ? new JSONObject() : thinkingAnalyticsEvent.getProperties()).toString());
                if (thinkingAnalyticsEvent.getEventTime() != null) {
                    a2.putExtra("TD_DATE", thinkingAnalyticsEvent.getEventTime().getTime());
                }
                if (thinkingAnalyticsEvent.getTimeZone() != null) {
                    a2.putExtra("TD_KEY_TIMEZONE", thinkingAnalyticsEvent.getTimeZone().getID());
                }
                a2.putExtra("TD_KEY_EXTRA_FIELD", thinkingAnalyticsEvent.getExtraValue());
                context = this.a;
                if (context == null) {
                    context.sendBroadcast(a2);
                    return;
                }
                return;
            }
            i = 1048580;
        }
        a2.putExtra("TD_ACTION", i);
        a2.putExtra("#event_name", thinkingAnalyticsEvent.getEventName());
        a2.putExtra("properties", a(thinkingAnalyticsEvent.getEventName(), thinkingAnalyticsEvent.getProperties() != null ? new JSONObject() : thinkingAnalyticsEvent.getProperties()).toString());
        if (thinkingAnalyticsEvent.getEventTime() != null) {
        }
        if (thinkingAnalyticsEvent.getTimeZone() != null) {
        }
        a2.putExtra("TD_KEY_EXTRA_FIELD", thinkingAnalyticsEvent.getExtraValue());
        context = this.a;
        if (context == null) {
        }
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void track(String str) {
        track(str, (JSONObject) null, (Date) null, (TimeZone) null);
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void track(String str, JSONObject jSONObject) {
        track(str, jSONObject, (Date) null, (TimeZone) null);
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void track(String str, JSONObject jSONObject, Date date) {
        track(str, jSONObject, date, (TimeZone) null);
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void track(String str, JSONObject jSONObject, Date date, TimeZone timeZone) {
        Intent a2 = a();
        a2.putExtra("TD_ACTION", 1048578);
        a2.putExtra("#event_name", str);
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        a2.putExtra("properties", a(str, jSONObject).toString());
        if (date != null) {
            a2.putExtra("TD_DATE", date.getTime());
        }
        if (timeZone != null) {
            a2.putExtra("TD_KEY_TIMEZONE", timeZone.getID());
        }
        Context context = this.a;
        if (context != null) {
            context.sendBroadcast(a2);
        }
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void unsetSuperProperty(String str) {
        Intent a2 = a();
        a2.putExtra("TD_ACTION", 2097158);
        if (str != null) {
            a2.putExtra("properties", str);
        }
        Context context = this.a;
        if (context != null) {
            context.sendBroadcast(a2);
        }
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void user_operations(j jVar, JSONObject jSONObject, Date date) {
        Intent a2 = a();
        a2.putExtra("TD_ACTION", 2097152);
        a2.putExtra("TD_KEY_USER_PROPERTY_SET_TYPE", jVar.a());
        if (jSONObject != null) {
            JSONObject jSONObject2 = new JSONObject();
            try {
                p.a(jSONObject, jSONObject2, this.mConfig.getDefaultTimeZone());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            a2.putExtra("properties", jSONObject2.toString());
        }
        if (date != null) {
            a2.putExtra("TD_DATE", date.getTime());
        }
        Context context = this.a;
        if (context != null) {
            context.sendBroadcast(a2);
        }
    }
}
