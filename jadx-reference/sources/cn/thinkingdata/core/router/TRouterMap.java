package cn.thinkingdata.core.router;

import android.text.TextUtils;
import cn.thinkingdata.core.utils.TDLog;
import com.google.android.gms.measurement.AppMeasurement;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class TRouterMap {
    public static final String ANALYTIC_ROUTE_PATH = "/thinkingdata/analytic";
    public static final String DOT = ".";
    public static final String PUSH_ROUTE_PATH = "/thinkingdata/tpush";
    public static final String ROUTE_ROOT_PACKAGE = "cn.thinkingdata.module.routes";
    public static final String SUFFIX_NAME = "ModuleRouter";
    private static final String TAG = "ThinkingAnalytics.TRouterMap";
    private static final String[] modules = {"ThirdParty", "TPush", "Analytic"};

    public static Map<String, RouteMeta> getDefaultRouters() {
        HashMap hashMap = new HashMap();
        for (String str : modules) {
            try {
                Map map = (Map) Class.forName("cn.thinkingdata.module.routes." + str + SUFFIX_NAME).getDeclaredMethod("getRouterMap", new Class[0]).invoke(null, new Object[0]);
                if (map != null) {
                    for (String str2 : map.keySet()) {
                        String str3 = (String) map.get(str2);
                        if (str3 != null && !TextUtils.isEmpty(str3)) {
                            JSONObject jSONObject = new JSONObject(str3);
                            hashMap.put(str2, RouteMeta.build(RouteType.parse(jSONObject.optInt(AppMeasurement.Param.TYPE)), str2, jSONObject.optString("name"), jSONObject.optBoolean("needCache")));
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                TDLog.d(TAG, "[ThinkingData] Info: No routing table found:" + e.getMessage());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return hashMap;
    }
}
