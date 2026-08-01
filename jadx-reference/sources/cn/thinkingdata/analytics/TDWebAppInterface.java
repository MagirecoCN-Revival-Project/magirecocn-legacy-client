package cn.thinkingdata.analytics;

import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import cn.thinkingdata.analytics.ThinkingAnalyticsSDK;
import cn.thinkingdata.analytics.utils.j;
import cn.thinkingdata.analytics.utils.o;
import cn.thinkingdata.core.utils.TDLog;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class TDWebAppInterface {
    private static final String TAG = "ThinkingAnalytics.TDWebAppInterface";
    private final ThinkingAnalyticsSDK defaultInstance;
    private Map<String, Object> deviceInfoMap;

    /* loaded from: classes.dex */
    class a implements ThinkingAnalyticsSDK.l {
        final /* synthetic */ String a;
        final /* synthetic */ c b;
        final /* synthetic */ String c;

        a(String str, c cVar, String str2) {
            this.a = str;
            this.b = cVar;
            this.c = str2;
        }

        @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK.l
        public void process(ThinkingAnalyticsSDK thinkingAnalyticsSDK) {
            if (thinkingAnalyticsSDK.getToken().equals(this.a)) {
                this.b.b();
                TDWebAppInterface.this.trackFromH5(this.c, thinkingAnalyticsSDK);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class b implements Runnable {
        final /* synthetic */ ThinkingAnalyticsSDK a;
        final /* synthetic */ j b;
        final /* synthetic */ JSONObject c;
        final /* synthetic */ cn.thinkingdata.analytics.utils.d d;
        final /* synthetic */ String e;
        final /* synthetic */ String f;
        final /* synthetic */ boolean g;

        b(TDWebAppInterface tDWebAppInterface, ThinkingAnalyticsSDK thinkingAnalyticsSDK, j jVar, JSONObject jSONObject, cn.thinkingdata.analytics.utils.d dVar, String str, String str2, boolean z) {
            this.a = thinkingAnalyticsSDK;
            this.b = jVar;
            this.c = jSONObject;
            this.d = dVar;
            this.e = str;
            this.f = str2;
            this.g = z;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.a.trackInternal(new cn.thinkingdata.analytics.f.a(this.a, this.b, this.c, this.d, this.e, this.f, this.g));
        }
    }

    /* loaded from: classes.dex */
    private class c {
        private boolean a;

        private c(TDWebAppInterface tDWebAppInterface) {
        }

        /* synthetic */ c(TDWebAppInterface tDWebAppInterface, a aVar) {
            this(tDWebAppInterface);
        }

        boolean a() {
            return !this.a;
        }

        void b() {
            this.a = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public TDWebAppInterface(ThinkingAnalyticsSDK thinkingAnalyticsSDK, Map<String, Object> map) {
        this.defaultInstance = thinkingAnalyticsSDK;
        this.deviceInfoMap = map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void trackFromH5(String str, ThinkingAnalyticsSDK thinkingAnalyticsSDK) {
        String str2;
        String str3 = "#zone_offset";
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            JSONArray jSONArray = new JSONObject(str).getJSONArray("data");
            int i = 0;
            while (i < jSONArray.length()) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                o oVar = new o(jSONObject.getString("#time"), jSONObject.has(str3) ? Double.valueOf(jSONObject.getDouble(str3)) : null);
                j a2 = j.a(jSONObject.getString("#type"));
                if (a2 == null) {
                    TDLog.w(TAG, "Unknown data type from H5. ignoring...");
                    return;
                }
                JSONObject jSONObject2 = jSONObject.getJSONObject("properties");
                Iterator<String> keys = jSONObject2.keys();
                while (keys.hasNext()) {
                    try {
                        String next = keys.next();
                        if (!next.equals("#account_id") && !next.equals("#distinct_id")) {
                            if (this.deviceInfoMap.containsKey(next)) {
                            }
                        }
                        keys.remove();
                    } catch (Exception e) {
                        e = e;
                        TDLog.w(TAG, "Exception occurred when track data from H5.");
                        e.printStackTrace();
                        return;
                    }
                }
                if (a2.b()) {
                    String string = jSONObject.getString("#event_name");
                    HashMap hashMap = new HashMap();
                    if (jSONObject.has("#first_check_id")) {
                        hashMap.put("#first_check_id", jSONObject.getString("#first_check_id"));
                    }
                    if (jSONObject.has("#event_id")) {
                        hashMap.put("#event_id", jSONObject.getString("#event_id"));
                    }
                    thinkingAnalyticsSDK.track(string, jSONObject2, oVar, false, hashMap, a2);
                    str2 = str3;
                } else {
                    str2 = str3;
                    thinkingAnalyticsSDK.mTrackTaskManager.a(new b(this, thinkingAnalyticsSDK, a2, jSONObject2, oVar, thinkingAnalyticsSDK.getStatusIdentifyId(), thinkingAnalyticsSDK.getStatusAccountId(), thinkingAnalyticsSDK.isStatusTrackSaveOnly()));
                }
                i++;
                str3 = str2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    @JavascriptInterface
    public void thinkingdata_track(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        TDLog.d(TAG, str);
        try {
            String string = new JSONObject(str).getString("#app_id");
            c cVar = new c(this, null);
            ThinkingAnalyticsSDK.allInstances(new a(string, cVar, str));
            if (cVar.a()) {
                trackFromH5(str, this.defaultInstance);
            }
        } catch (JSONException e) {
            TDLog.w(TAG, "Unexpected exception occurred: " + e.toString());
        }
    }
}
