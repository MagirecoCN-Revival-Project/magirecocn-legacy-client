package cn.thinkingdata.module.routes;

import cn.thinkingdata.core.router.TRouterMap;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class AnalyticModuleRouter {
    public static Map<String, String> getRouterMap() {
        HashMap hashMap = new HashMap();
        hashMap.put(TRouterMap.ANALYTIC_ROUTE_PATH, "{name=cn.thinkingdata.analytics.ThinkingAnalyticsPlugin, needCache=true, type=1}");
        return hashMap;
    }
}
