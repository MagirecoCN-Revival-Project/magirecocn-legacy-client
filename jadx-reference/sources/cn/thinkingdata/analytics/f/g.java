package cn.thinkingdata.analytics.f;

import cn.thinkingdata.analytics.TDConfig;
import cn.thinkingdata.analytics.ThinkingAnalyticsSDK;
import cn.thinkingdata.analytics.utils.j;
import cn.thinkingdata.analytics.utils.k;
import cn.thinkingdata.analytics.utils.p;
import cn.thinkingdata.core.utils.TDLog;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class g {
    private final ThinkingAnalyticsSDK a;
    private final TDConfig b;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class a implements Runnable {
        final /* synthetic */ JSONObject a;
        final /* synthetic */ j b;
        final /* synthetic */ cn.thinkingdata.analytics.utils.d c;
        final /* synthetic */ String d;
        final /* synthetic */ String e;
        final /* synthetic */ boolean f;

        a(JSONObject jSONObject, j jVar, cn.thinkingdata.analytics.utils.d dVar, String str, String str2, boolean z) {
            this.a = jSONObject;
            this.b = jVar;
            this.c = dVar;
            this.d = str;
            this.e = str2;
            this.f = z;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (!cn.thinkingdata.analytics.utils.f.a(this.a)) {
                TDLog.w("ThinkingAnalytics.UserOperation", "The data contains invalid key or value: " + this.a.toString());
                if (g.this.b.shouldThrowException()) {
                    throw new k("Invalid properties. Please refer to SDK debug log for detail reasons.");
                }
            }
            try {
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = this.a;
                if (jSONObject2 != null) {
                    p.a(jSONObject2, jSONObject, g.this.b.getDefaultTimeZone());
                }
                g.this.a.trackInternal(new cn.thinkingdata.analytics.f.a(g.this.a, this.b, jSONObject, this.c, this.d, this.e, this.f));
            } catch (Exception e) {
                TDLog.w("ThinkingAnalytics.UserOperation", e.getMessage());
            }
        }
    }

    public g(ThinkingAnalyticsSDK thinkingAnalyticsSDK, TDConfig tDConfig) {
        this.a = thinkingAnalyticsSDK;
        this.b = tDConfig;
    }

    public void a(j jVar, JSONObject jSONObject, Date date) {
        if (this.a.getStatusHasDisabled()) {
            return;
        }
        this.a.mTrackTaskManager.a(new a(jSONObject, jVar, date == null ? this.a.mCalibratedTimeManager.a() : this.a.mCalibratedTimeManager.a(date, null), this.a.getStatusIdentifyId(), this.a.getStatusAccountId(), this.a.isStatusTrackSaveOnly()));
    }

    public void a(String str, Number number) {
        try {
            if (number == null) {
                TDLog.d("ThinkingAnalytics.UserOperation", "user_add value must be Number");
                if (this.b.shouldThrowException()) {
                    throw new k("Invalid property values for user add.");
                }
            } else {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(str, number);
                a(jSONObject, (Date) null);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            if (this.b.shouldThrowException()) {
                throw new k(e);
            }
        }
    }

    public void a(Date date) {
        this.a.user_operations(j.USER_DEL, null, date);
    }

    public void a(JSONObject jSONObject, Date date) {
        this.a.user_operations(j.USER_ADD, jSONObject, date);
    }

    public void a(String... strArr) {
        if (strArr == null) {
            return;
        }
        JSONObject jSONObject = new JSONObject();
        for (String str : strArr) {
            try {
                jSONObject.put(str, 0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jSONObject.length() > 0) {
            f(jSONObject, null);
        }
    }

    public void b(JSONObject jSONObject, Date date) {
        this.a.user_operations(j.USER_APPEND, jSONObject, date);
    }

    public void c(JSONObject jSONObject, Date date) {
        this.a.user_operations(j.USER_SET, jSONObject, date);
    }

    public void d(JSONObject jSONObject, Date date) {
        this.a.user_operations(j.USER_SET_ONCE, jSONObject, date);
    }

    public void e(JSONObject jSONObject, Date date) {
        this.a.user_operations(j.USER_UNIQ_APPEND, jSONObject, date);
    }

    public void f(JSONObject jSONObject, Date date) {
        this.a.user_operations(j.USER_UNSET, jSONObject, date);
    }
}
