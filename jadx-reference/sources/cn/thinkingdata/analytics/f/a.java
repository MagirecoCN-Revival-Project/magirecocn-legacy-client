package cn.thinkingdata.analytics.f;

import cn.thinkingdata.analytics.ThinkingAnalyticsSDK;
import cn.thinkingdata.analytics.utils.j;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class a {
    public String a;
    private final cn.thinkingdata.analytics.utils.d b;
    final j c;
    private String d;
    private String e;
    private final JSONObject f;
    private Map<String, String> g;
    public boolean h = true;
    boolean i;
    final String j;

    public a(ThinkingAnalyticsSDK thinkingAnalyticsSDK, j jVar, JSONObject jSONObject, cn.thinkingdata.analytics.utils.d dVar, String str, String str2, boolean z) {
        this.i = false;
        this.c = jVar;
        this.f = jSONObject;
        this.b = dVar;
        this.j = thinkingAnalyticsSDK.getToken();
        this.d = str;
        this.e = str2;
        this.i = z;
    }

    public JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("#type", this.c.a());
            jSONObject.put("#time", this.b.b());
            jSONObject.put("#distinct_id", this.d);
            String str = this.e;
            if (str != null) {
                jSONObject.put("#account_id", str);
            }
            Map<String, String> map = this.g;
            if (map != null) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    jSONObject.put(entry.getKey(), entry.getValue());
                }
            }
            if (this.c.b()) {
                jSONObject.put("#event_name", this.a);
                Double a = this.b.a();
                if (a != null) {
                    this.f.put("#zone_offset", a);
                }
            }
            jSONObject.put("properties", this.f);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public void a(Map<String, String> map) {
        this.g = map;
    }

    public void b() {
        this.h = false;
    }
}
