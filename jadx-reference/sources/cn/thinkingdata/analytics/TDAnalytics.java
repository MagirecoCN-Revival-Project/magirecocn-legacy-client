package cn.thinkingdata.analytics;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.view.View;
import android.webkit.WebView;
import cn.thinkingdata.analytics.ThinkingAnalyticsSDK;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class TDAnalytics {
    public static Map<String, ThinkingAnalyticsSDK> sInstances = new HashMap();
    private static ThinkingAnalyticsSDK instance = null;

    /* loaded from: classes.dex */
    public interface TDAutoTrackEventHandler {
        JSONObject getAutoTrackEventProperties(int i, JSONObject jSONObject);
    }

    /* loaded from: classes.dex */
    public interface TDAutoTrackEventType {
        public static final int APP_CLICK = 4;
        public static final int APP_CRASH = 16;
        public static final int APP_END = 2;
        public static final int APP_INSTALL = 32;
        public static final int APP_START = 1;
        public static final int APP_VIEW_SCREEN = 8;
    }

    /* loaded from: classes.dex */
    public interface TDDynamicSuperPropertiesHandler {
        JSONObject getDynamicSuperProperties();
    }

    /* loaded from: classes.dex */
    public enum TDNetworkType {
        WIFI,
        ALL
    }

    /* loaded from: classes.dex */
    public enum TDTrackStatus {
        PAUSE,
        STOP,
        SAVE_ONLY,
        NORMAL
    }

    /* loaded from: classes.dex */
    static class a implements ThinkingAnalyticsSDK.AutoTrackEventListener {
        final /* synthetic */ TDAutoTrackEventHandler a;

        a(TDAutoTrackEventHandler tDAutoTrackEventHandler) {
            this.a = tDAutoTrackEventHandler;
        }

        @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK.AutoTrackEventListener
        public JSONObject eventCallback(ThinkingAnalyticsSDK.AutoTrackEventType autoTrackEventType, JSONObject jSONObject) {
            return this.a.getAutoTrackEventProperties(autoTrackEventType == ThinkingAnalyticsSDK.AutoTrackEventType.APP_START ? 1 : autoTrackEventType == ThinkingAnalyticsSDK.AutoTrackEventType.APP_END ? 2 : autoTrackEventType == ThinkingAnalyticsSDK.AutoTrackEventType.APP_CLICK ? 4 : autoTrackEventType == ThinkingAnalyticsSDK.AutoTrackEventType.APP_VIEW_SCREEN ? 8 : autoTrackEventType == ThinkingAnalyticsSDK.AutoTrackEventType.APP_CRASH ? 16 : autoTrackEventType == ThinkingAnalyticsSDK.AutoTrackEventType.APP_INSTALL ? 32 : 0, jSONObject);
        }
    }

    /* loaded from: classes.dex */
    static class b implements ThinkingAnalyticsSDK.DynamicSuperPropertiesTracker {
        final /* synthetic */ TDDynamicSuperPropertiesHandler a;

        b(TDDynamicSuperPropertiesHandler tDDynamicSuperPropertiesHandler) {
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
            int[] iArr = new int[TDTrackStatus.values().length];
            b = iArr;
            try {
                iArr[TDTrackStatus.PAUSE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                b[TDTrackStatus.STOP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                b[TDTrackStatus.SAVE_ONLY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                b[TDTrackStatus.NORMAL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[TDNetworkType.values().length];
            a = iArr2;
            try {
                iArr2[TDNetworkType.WIFI.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[TDNetworkType.ALL.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public static void calibrateTime(long j) {
        ThinkingAnalyticsSDK.calibrateTime(j);
    }

    public static void calibrateTimeWithNtp(String... strArr) {
        ThinkingAnalyticsSDK.calibrateTimeWithNtp(strArr);
    }

    public static void clearSuperProperties() {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.clearSuperProperties();
    }

    public static void enableAutoTrack(int i) {
        enableAutoTrack(i, new JSONObject());
    }

    public static void enableAutoTrack(int i, TDAutoTrackEventHandler tDAutoTrackEventHandler) {
        if (instance == null || tDAutoTrackEventHandler == null) {
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
        instance.enableAutoTrack(arrayList, new a(tDAutoTrackEventHandler));
    }

    public static void enableAutoTrack(int i, JSONObject jSONObject) {
        if (instance == null) {
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
        instance.enableAutoTrack(arrayList, jSONObject);
    }

    public static void enableLog(boolean z) {
        ThinkingAnalyticsSDK.enableTrackLog(z);
    }

    public static void enableThirdPartySharing(int i) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.enableThirdPartySharing(i);
    }

    public static void enableThirdPartySharing(int i, Object obj) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.enableThirdPartySharing(i, obj);
    }

    public static void flush() {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.flush();
    }

    public static String getDeviceId() {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        return thinkingAnalyticsSDK == null ? "" : thinkingAnalyticsSDK.getDeviceId();
    }

    public static String getDistinctId() {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        return thinkingAnalyticsSDK == null ? "" : thinkingAnalyticsSDK.getDistinctId();
    }

    public static String getLocalRegion() {
        return ThinkingAnalyticsSDK.getLocalRegion();
    }

    public static TDPresetProperties getPresetProperties() {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        return thinkingAnalyticsSDK == null ? new TDPresetProperties() : thinkingAnalyticsSDK.getPresetProperties();
    }

    public static String getSDKVersion() {
        return "3.0.0-beta.1";
    }

    public static JSONObject getSuperProperties() {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        return thinkingAnalyticsSDK == null ? new JSONObject() : thinkingAnalyticsSDK.getSuperProperties();
    }

    public static void ignoreAppViewEventInExtPackage() {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.ignoreAppViewEventInExtPackage();
    }

    public static void ignoreAutoTrackActivities(List<Class<?>> list) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.ignoreAutoTrackActivities(list);
    }

    public static void ignoreAutoTrackActivity(Class<?> cls) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.ignoreAutoTrackActivity(cls);
    }

    public static void ignoreView(View view) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.ignoreView(view);
    }

    public static void ignoreViewType(Class<?> cls) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.ignoreViewType(cls);
    }

    public static void init(Context context, String str, String str2) {
        ThinkingAnalyticsSDK sharedInstance = ThinkingAnalyticsSDK.sharedInstance(context, str, str2);
        instance = sharedInstance;
        sInstances.put(str, sharedInstance);
    }

    public static void init(TDConfig tDConfig) {
        instance = ThinkingAnalyticsSDK.sharedInstance(tDConfig);
        sInstances.put(tDConfig.getName(), instance);
    }

    public static String lightInstance() {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return "";
        }
        ThinkingAnalyticsSDK m37createLightInstance = thinkingAnalyticsSDK.m37createLightInstance();
        String uuid = UUID.randomUUID().toString();
        sInstances.put(uuid, m37createLightInstance);
        return uuid;
    }

    public static void login(String str) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.login(str);
    }

    public static void logout() {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.logout();
    }

    public static void setCustomerLibInfo(String str, String str2) {
        ThinkingAnalyticsSDK.setCustomerLibInfo(str, str2);
    }

    public static void setDistinctId(String str) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.identify(str);
    }

    public static void setDynamicSuperProperties(TDDynamicSuperPropertiesHandler tDDynamicSuperPropertiesHandler) {
        if (instance == null || tDDynamicSuperPropertiesHandler == null) {
            return;
        }
        instance.setDynamicSuperPropertiesTracker(new b(tDDynamicSuperPropertiesHandler));
    }

    public static void setJsBridge(WebView webView) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.setJsBridge(webView);
    }

    public static void setJsBridgeForX5WebView(Object obj) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.setJsBridgeForX5WebView(obj);
    }

    public static void setNetworkType(TDNetworkType tDNetworkType) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK;
        ThinkingAnalyticsSDK.ThinkingdataNetworkType thinkingdataNetworkType;
        if (instance == null) {
            return;
        }
        int i = c.a[tDNetworkType.ordinal()];
        if (i == 1) {
            thinkingAnalyticsSDK = instance;
            thinkingdataNetworkType = ThinkingAnalyticsSDK.ThinkingdataNetworkType.NETWORKTYPE_WIFI;
        } else {
            if (i != 2) {
                return;
            }
            thinkingAnalyticsSDK = instance;
            thinkingdataNetworkType = ThinkingAnalyticsSDK.ThinkingdataNetworkType.NETWORKTYPE_ALL;
        }
        thinkingAnalyticsSDK.setNetworkType(thinkingdataNetworkType);
    }

    public static void setSuperProperties(JSONObject jSONObject) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.setSuperProperties(jSONObject);
    }

    public static void setTrackStatus(TDTrackStatus tDTrackStatus) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK;
        ThinkingAnalyticsSDK.TATrackStatus tATrackStatus;
        if (instance == null) {
            return;
        }
        int i = c.b[tDTrackStatus.ordinal()];
        if (i == 1) {
            thinkingAnalyticsSDK = instance;
            tATrackStatus = ThinkingAnalyticsSDK.TATrackStatus.PAUSE;
        } else if (i == 2) {
            thinkingAnalyticsSDK = instance;
            tATrackStatus = ThinkingAnalyticsSDK.TATrackStatus.STOP;
        } else if (i == 3) {
            thinkingAnalyticsSDK = instance;
            tATrackStatus = ThinkingAnalyticsSDK.TATrackStatus.SAVE_ONLY;
        } else {
            if (i != 4) {
                return;
            }
            thinkingAnalyticsSDK = instance;
            tATrackStatus = ThinkingAnalyticsSDK.TATrackStatus.NORMAL;
        }
        thinkingAnalyticsSDK.setTrackStatus(tATrackStatus);
    }

    public static void setViewID(Dialog dialog, String str) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.setViewID(dialog, str);
    }

    public static void setViewID(View view, String str) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.setViewID(view, str);
    }

    public static void setViewProperties(View view, JSONObject jSONObject) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.setViewProperties(view, jSONObject);
    }

    public static void timeEvent(String str) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.timeEvent(str);
    }

    public static void track(d dVar) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.track(dVar);
    }

    public static void track(String str) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.track(str);
    }

    public static void track(String str, JSONObject jSONObject) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.track(str, jSONObject);
    }

    public static void track(String str, JSONObject jSONObject, Date date, TimeZone timeZone) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.track(str, jSONObject, date, timeZone);
    }

    public static void trackFragmentAppViewScreen() {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.trackFragmentAppViewScreen();
    }

    public static void trackViewScreen(Activity activity) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.trackViewScreen(activity);
    }

    public static void trackViewScreen(Fragment fragment) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.trackViewScreen(fragment);
    }

    public static void trackViewScreen(Object obj) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.trackViewScreen(obj);
    }

    public static void unsetSuperProperty(String str) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.unsetSuperProperty(str);
    }

    public static void userAdd(JSONObject jSONObject) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.user_add(jSONObject);
    }

    public static void userAppend(JSONObject jSONObject) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.user_append(jSONObject);
    }

    public static void userDelete() {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.user_delete();
    }

    public static void userSet(JSONObject jSONObject) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.user_set(jSONObject);
    }

    public static void userSetOnce(JSONObject jSONObject) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.user_setOnce(jSONObject);
    }

    public static void userUniqAppend(JSONObject jSONObject) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.user_uniqAppend(jSONObject);
    }

    public static void userUnset(String... strArr) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK = instance;
        if (thinkingAnalyticsSDK == null) {
            return;
        }
        thinkingAnalyticsSDK.user_unset(strArr);
    }
}
