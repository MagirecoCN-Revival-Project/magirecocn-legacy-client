package cn.thinkingdata.analytics;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.view.View;
import android.webkit.WebView;
import cn.thinkingdata.analytics.TDAnalytics;
import cn.thinkingdata.analytics.ThinkingAnalyticsSDK;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class TDAnalyticsAPI {

    /* loaded from: classes.dex */
    static class a implements ThinkingAnalyticsSDK.AutoTrackEventListener {
        final /* synthetic */ TDAnalytics.TDAutoTrackEventHandler a;

        a(TDAnalytics.TDAutoTrackEventHandler tDAutoTrackEventHandler) {
            this.a = tDAutoTrackEventHandler;
        }

        @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK.AutoTrackEventListener
        public JSONObject eventCallback(ThinkingAnalyticsSDK.AutoTrackEventType autoTrackEventType, JSONObject jSONObject) {
            return this.a.getAutoTrackEventProperties(autoTrackEventType == ThinkingAnalyticsSDK.AutoTrackEventType.APP_START ? 1 : autoTrackEventType == ThinkingAnalyticsSDK.AutoTrackEventType.APP_END ? 2 : autoTrackEventType == ThinkingAnalyticsSDK.AutoTrackEventType.APP_CLICK ? 4 : autoTrackEventType == ThinkingAnalyticsSDK.AutoTrackEventType.APP_VIEW_SCREEN ? 8 : autoTrackEventType == ThinkingAnalyticsSDK.AutoTrackEventType.APP_CRASH ? 16 : autoTrackEventType == ThinkingAnalyticsSDK.AutoTrackEventType.APP_INSTALL ? 32 : 0, jSONObject);
        }
    }

    /* loaded from: classes.dex */
    static class b implements ThinkingAnalyticsSDK.DynamicSuperPropertiesTracker {
        final /* synthetic */ TDAnalytics.TDDynamicSuperPropertiesHandler a;

        b(TDAnalytics.TDDynamicSuperPropertiesHandler tDDynamicSuperPropertiesHandler) {
            this.a = tDDynamicSuperPropertiesHandler;
        }

        @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK.DynamicSuperPropertiesTracker
        public JSONObject getDynamicSuperProperties() {
            return this.a.getDynamicSuperProperties();
        }
    }

    /* loaded from: classes.dex */
    static /* synthetic */ class c {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b;

        static {
            int[] iArr = new int[TDAnalytics.TDTrackStatus.values().length];
            b = iArr;
            try {
                iArr[TDAnalytics.TDTrackStatus.PAUSE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                b[TDAnalytics.TDTrackStatus.STOP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                b[TDAnalytics.TDTrackStatus.SAVE_ONLY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                b[TDAnalytics.TDTrackStatus.NORMAL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[TDAnalytics.TDNetworkType.values().length];
            a = iArr2;
            try {
                iArr2[TDAnalytics.TDNetworkType.WIFI.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[TDAnalytics.TDNetworkType.ALL.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public static void clearSuperProperties(String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.clearSuperProperties();
        }
    }

    public static void enableAutoTrack(int i, TDAnalytics.TDAutoTrackEventHandler tDAutoTrackEventHandler, String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI;
        if (tDAutoTrackEventHandler == null || (tDAnalyticsAPI = getInstance(str)) == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        if ((i & 1) > 0) {
            arrayList.add(ThinkingAnalyticsSDK.AutoTrackEventType.APP_START);
        }
        if ((i & 2) > 0) {
            arrayList.add(ThinkingAnalyticsSDK.AutoTrackEventType.APP_END);
        }
        if ((i & 4) > 0) {
            arrayList.add(ThinkingAnalyticsSDK.AutoTrackEventType.APP_CLICK);
        }
        if ((i & 8) > 0) {
            arrayList.add(ThinkingAnalyticsSDK.AutoTrackEventType.APP_VIEW_SCREEN);
        }
        if ((i & 16) > 0) {
            arrayList.add(ThinkingAnalyticsSDK.AutoTrackEventType.APP_CRASH);
        }
        if ((i & 32) > 0) {
            arrayList.add(ThinkingAnalyticsSDK.AutoTrackEventType.APP_INSTALL);
        }
        tDAnalyticsAPI.enableAutoTrack(arrayList, new a(tDAutoTrackEventHandler));
    }

    public static void enableAutoTrack(int i, String str) {
        enableAutoTrack(i, new JSONObject(), str);
    }

    public static void enableAutoTrack(int i, JSONObject jSONObject, String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI != null) {
            ArrayList arrayList = new ArrayList();
            if ((i & 1) > 0) {
                arrayList.add(ThinkingAnalyticsSDK.AutoTrackEventType.APP_START);
            }
            if ((i & 2) > 0) {
                arrayList.add(ThinkingAnalyticsSDK.AutoTrackEventType.APP_END);
            }
            if ((i & 4) > 0) {
                arrayList.add(ThinkingAnalyticsSDK.AutoTrackEventType.APP_CLICK);
            }
            if ((i & 8) > 0) {
                arrayList.add(ThinkingAnalyticsSDK.AutoTrackEventType.APP_VIEW_SCREEN);
            }
            if ((i & 16) > 0) {
                arrayList.add(ThinkingAnalyticsSDK.AutoTrackEventType.APP_CRASH);
            }
            if ((i & 32) > 0) {
                arrayList.add(ThinkingAnalyticsSDK.AutoTrackEventType.APP_INSTALL);
            }
            tDAnalyticsAPI.enableAutoTrack(arrayList, jSONObject);
        }
    }

    public static void enableThirdPartySharing(int i, Object obj, String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.enableThirdPartySharing(i, obj);
        }
    }

    public static void enableThirdPartySharing(int i, String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.enableThirdPartySharing(i);
        }
    }

    public static void flush(String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.flush();
        }
    }

    public static String getDeviceId(String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        return tDAnalyticsAPI != null ? tDAnalyticsAPI.getDeviceId() : "";
    }

    public static String getDistinctId(String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        return tDAnalyticsAPI != null ? tDAnalyticsAPI.getDistinctId() : "";
    }

    private static ThinkingAnalyticsSDK getInstance(String str) {
        if (str == null) {
            return null;
        }
        return TDAnalytics.sInstances.get(str.replace(" ", ""));
    }

    public static TDPresetProperties getPresetProperties(String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        return tDAnalyticsAPI != null ? tDAnalyticsAPI.getPresetProperties() : new TDPresetProperties();
    }

    public static JSONObject getSuperProperties(String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        return tDAnalyticsAPI != null ? tDAnalyticsAPI.getSuperProperties() : new JSONObject();
    }

    public static void ignoreAppViewEventInExtPackage(String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.ignoreAppViewEventInExtPackage();
        }
    }

    public static void ignoreAutoTrackActivities(List<Class<?>> list, String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.ignoreAutoTrackActivities(list);
        }
    }

    public static void ignoreAutoTrackActivity(Class<?> cls, String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.ignoreAutoTrackActivity(cls);
        }
    }

    public static void ignoreView(View view, String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.ignoreView(view);
        }
    }

    public static void ignoreViewType(Class<?> cls, String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.ignoreViewType(cls);
        }
    }

    public static String lightInstance(String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI == null) {
            return "";
        }
        ThinkingAnalyticsSDK m37createLightInstance = tDAnalyticsAPI.m37createLightInstance();
        String uuid = UUID.randomUUID().toString();
        TDAnalytics.sInstances.put(uuid, m37createLightInstance);
        return uuid;
    }

    public static void login(String str, String str2) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str2);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.login(str);
        }
    }

    public static void logout(String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.logout();
        }
    }

    public static void setDistinctId(String str, String str2) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str2);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.identify(str);
        }
    }

    public static void setDynamicSuperProperties(TDAnalytics.TDDynamicSuperPropertiesHandler tDDynamicSuperPropertiesHandler, String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI == null || tDDynamicSuperPropertiesHandler == null) {
            return;
        }
        tDAnalyticsAPI.setDynamicSuperPropertiesTracker(new b(tDDynamicSuperPropertiesHandler));
    }

    public static void setJsBridge(WebView webView, String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.setJsBridge(webView);
        }
    }

    public static void setJsBridgeForX5WebView(Object obj, String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.setJsBridgeForX5WebView(obj);
        }
    }

    public static void setNetworkType(TDAnalytics.TDNetworkType tDNetworkType, String str) {
        ThinkingAnalyticsSDK.ThinkingdataNetworkType thinkingdataNetworkType;
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI != null) {
            int i = c.a[tDNetworkType.ordinal()];
            if (i == 1) {
                thinkingdataNetworkType = ThinkingAnalyticsSDK.ThinkingdataNetworkType.NETWORKTYPE_WIFI;
            } else if (i != 2) {
                return;
            } else {
                thinkingdataNetworkType = ThinkingAnalyticsSDK.ThinkingdataNetworkType.NETWORKTYPE_ALL;
            }
            tDAnalyticsAPI.setNetworkType(thinkingdataNetworkType);
        }
    }

    public static void setSuperProperties(JSONObject jSONObject, String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.setSuperProperties(jSONObject);
        }
    }

    public static void setTrackStatus(TDAnalytics.TDTrackStatus tDTrackStatus, String str) {
        ThinkingAnalyticsSDK.TATrackStatus tATrackStatus;
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI != null) {
            int i = c.b[tDTrackStatus.ordinal()];
            if (i == 1) {
                tATrackStatus = ThinkingAnalyticsSDK.TATrackStatus.PAUSE;
            } else if (i == 2) {
                tATrackStatus = ThinkingAnalyticsSDK.TATrackStatus.STOP;
            } else if (i == 3) {
                tATrackStatus = ThinkingAnalyticsSDK.TATrackStatus.SAVE_ONLY;
            } else if (i != 4) {
                return;
            } else {
                tATrackStatus = ThinkingAnalyticsSDK.TATrackStatus.NORMAL;
            }
            tDAnalyticsAPI.setTrackStatus(tATrackStatus);
        }
    }

    public static void setViewID(Dialog dialog, String str, String str2) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str2);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.setViewID(dialog, str);
        }
    }

    public static void setViewID(View view, String str, String str2) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str2);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.setViewID(view, str);
        }
    }

    public static void setViewProperties(View view, JSONObject jSONObject, String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.setViewProperties(view, jSONObject);
        }
    }

    public static void timeEvent(String str, String str2) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str2);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.timeEvent(str);
        }
    }

    public static void track(d dVar, String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.track(dVar);
        }
    }

    public static void track(String str, String str2) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str2);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.track(str);
        }
    }

    public static void track(String str, JSONObject jSONObject, String str2) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str2);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.track(str, jSONObject);
        }
    }

    public static void track(String str, JSONObject jSONObject, Date date, TimeZone timeZone, String str2) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str2);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.track(str, jSONObject, date, timeZone);
        }
    }

    public static void trackFragmentAppViewScreen(String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.trackFragmentAppViewScreen();
        }
    }

    public static void trackViewScreen(Activity activity, String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.trackViewScreen(activity);
        }
    }

    public static void trackViewScreen(Fragment fragment, String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.trackViewScreen(fragment);
        }
    }

    public static void trackViewScreen(Object obj, String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.trackViewScreen(obj);
        }
    }

    public static void unsetSuperProperty(String str, String str2) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str2);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.unsetSuperProperty(str);
        }
    }

    public static void userAdd(JSONObject jSONObject, String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.user_add(jSONObject);
        }
    }

    public static void userAppend(JSONObject jSONObject, String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.user_append(jSONObject);
        }
    }

    public static void userDelete(String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.user_delete();
        }
    }

    public static void userSet(JSONObject jSONObject, String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.user_set(jSONObject);
        }
    }

    public static void userSetOnce(JSONObject jSONObject, String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.user_setOnce(jSONObject);
        }
    }

    public static void userUniqAppend(JSONObject jSONObject, String str) {
        ThinkingAnalyticsSDK tDAnalyticsAPI = getInstance(str);
        if (tDAnalyticsAPI != null) {
            tDAnalyticsAPI.user_uniqAppend(jSONObject);
        }
    }

    public static void userUnset(String... strArr) {
        ThinkingAnalyticsSDK tDAnalyticsAPI;
        if (strArr.length <= 0 || (tDAnalyticsAPI = getInstance(strArr[strArr.length - 1])) == null) {
            return;
        }
        for (int i = 0; i < strArr.length - 1; i++) {
            tDAnalyticsAPI.user_unset(strArr[i]);
        }
    }
}
