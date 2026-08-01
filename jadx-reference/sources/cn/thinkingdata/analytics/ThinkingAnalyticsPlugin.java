package cn.thinkingdata.analytics;

import android.text.TextUtils;
import cn.thinkingdata.analytics.ThinkingAnalyticsSDK;
import cn.thinkingdata.core.router.plugin.IPlugin;
import cn.thinkingdata.core.router.plugin.MethodCall;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class ThinkingAnalyticsPlugin implements IPlugin {

    /* loaded from: classes.dex */
    class a implements ThinkingAnalyticsSDK.l {
        final /* synthetic */ String a;
        final /* synthetic */ JSONObject b;

        a(ThinkingAnalyticsPlugin thinkingAnalyticsPlugin, String str, JSONObject jSONObject) {
            this.a = str;
            this.b = jSONObject;
        }

        @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK.l
        public void process(ThinkingAnalyticsSDK thinkingAnalyticsSDK) {
            if (TextUtils.equals(thinkingAnalyticsSDK.getToken(), this.a)) {
                thinkingAnalyticsSDK.user_set(this.b);
            }
        }
    }

    /* loaded from: classes.dex */
    class b implements ThinkingAnalyticsSDK.l {
        final /* synthetic */ String a;
        final /* synthetic */ JSONObject b;

        b(ThinkingAnalyticsPlugin thinkingAnalyticsPlugin, String str, JSONObject jSONObject) {
            this.a = str;
            this.b = jSONObject;
        }

        @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK.l
        public void process(ThinkingAnalyticsSDK thinkingAnalyticsSDK) {
            if (TextUtils.equals(thinkingAnalyticsSDK.getToken(), this.a)) {
                thinkingAnalyticsSDK.autoTrack("ops_push_click", this.b);
                thinkingAnalyticsSDK.flush();
            }
        }
    }

    @Override // cn.thinkingdata.core.router.plugin.IPlugin
    public void onMethodCall(MethodCall methodCall) {
        String str = methodCall.method;
        str.hashCode();
        if (!str.equals("updatePushToken")) {
            if (str.equals("uploadPushClick")) {
                String str2 = (String) methodCall.argument("appId");
                JSONObject jSONObject = (JSONObject) methodCall.argument("ops_properties");
                JSONObject jSONObject2 = new JSONObject();
                try {
                    jSONObject2.put("#ops_receipt_properties", jSONObject);
                    ThinkingAnalyticsSDK.allInstances(new b(this, str2, jSONObject2));
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            return;
        }
        String str3 = (String) methodCall.argument("token");
        String str4 = (String) methodCall.argument("user_language");
        double doubleValue = ((Double) methodCall.argument("local_zone")).doubleValue();
        String str5 = (String) methodCall.argument("appId");
        JSONObject jSONObject3 = new JSONObject();
        try {
            jSONObject3.put("token", str3);
            jSONObject3.put("user_language", str4);
            jSONObject3.put("local_zone", doubleValue);
            ThinkingAnalyticsSDK.allInstances(new a(this, str5, jSONObject3));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }
}
