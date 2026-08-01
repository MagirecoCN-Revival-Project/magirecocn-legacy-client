package cn.thinkingdata.analytics;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import cn.thinkingdata.analytics.aop.push.TAPushUtils;
import cn.thinkingdata.analytics.utils.broadcast.TDReceiver;
import cn.thinkingdata.analytics.utils.p;
import cn.thinkingdata.core.router.TRouter;
import cn.thinkingdata.core.router.TRouterMap;
import cn.thinkingdata.core.utils.TDLog;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class ThinkingAnalyticsSDK implements cn.thinkingdata.analytics.a {
    static final String TAG = "ThinkingAnalyticsSDK";
    protected String _statusAccountId;
    protected String _statusIdentifyId;
    protected TATrackStatus _statusTrackStatus;
    private boolean mAutoTrack;
    private AutoTrackEventListener mAutoTrackEventListener;
    private List<AutoTrackEventType> mAutoTrackEventTypeList;
    private List<Integer> mAutoTrackIgnoredActivities;
    private JSONObject mAutoTrackStartProperties;
    private cn.thinkingdata.analytics.utils.d mAutoTrackStartTime;
    public cn.thinkingdata.analytics.utils.a mCalibratedTimeManager;
    public TDConfig mConfig;
    private DynamicSuperPropertiesTracker mDynamicSuperPropertiesTracker;
    private final boolean mEnableTrackOldData;
    private String mLastScreenUrl;
    private cn.thinkingdata.analytics.e.b mLifecycleCallbacks;
    protected final cn.thinkingdata.analytics.f.b mMessages;
    public cn.thinkingdata.analytics.h.a mSessionManager;
    private cn.thinkingdata.analytics.g.b mStorageManager;
    private final cn.thinkingdata.analytics.f.e mSystemInformation;
    private boolean mTrackCrash;
    private boolean mTrackFragmentAppViewScreen;
    final Map<String, cn.thinkingdata.analytics.f.d> mTrackTimer;
    private final cn.thinkingdata.analytics.f.g mUserOperationHandler;
    private static final Map<Context, Map<String, ThinkingAnalyticsSDK>> sInstanceMap = new HashMap();
    private static final Map<Context, List<String>> sAppFirstInstallationMap = new HashMap();
    private boolean mIgnoreAppViewInExtPackage = false;
    private List<Class> mIgnoredViewTypeList = new ArrayList();
    private final JSONObject mAutoTrackEventProperties = new JSONObject();
    public cn.thinkingdata.analytics.i.a mTrackTaskManager = cn.thinkingdata.analytics.i.a.a();

    /* loaded from: classes.dex */
    public interface AutoTrackEventListener {
        JSONObject eventCallback(AutoTrackEventType autoTrackEventType, JSONObject jSONObject);
    }

    /* loaded from: classes.dex */
    public enum AutoTrackEventType {
        APP_START("ta_app_start"),
        APP_END("ta_app_end"),
        APP_CLICK("ta_app_click"),
        APP_VIEW_SCREEN("ta_app_view"),
        APP_CRASH("ta_app_crash"),
        APP_INSTALL("ta_app_install");

        private final String eventName;

        AutoTrackEventType(String str) {
            this.eventName = str;
        }

        public static AutoTrackEventType autoTrackEventTypeFromEventName(String str) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            str.hashCode();
            char c = 65535;
            switch (str.hashCode()) {
                case -1123498325:
                    if (str.equals("ta_app_install")) {
                        c = 0;
                        break;
                    }
                    break;
                case -78288232:
                    if (str.equals("ta_app_click")) {
                        c = 1;
                        break;
                    }
                    break;
                case -78116681:
                    if (str.equals("ta_app_crash")) {
                        c = 2;
                        break;
                    }
                    break;
                case -63280782:
                    if (str.equals("ta_app_start")) {
                        c = 3;
                        break;
                    }
                    break;
                case 1014444523:
                    if (str.equals("ta_app_end")) {
                        c = 4;
                        break;
                    }
                    break;
                case 1383510933:
                    if (str.equals("ta_app_view")) {
                        c = 5;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    return APP_INSTALL;
                case 1:
                    return APP_CLICK;
                case 2:
                    return APP_CRASH;
                case 3:
                    return APP_START;
                case 4:
                    return APP_END;
                case 5:
                    return APP_VIEW_SCREEN;
                default:
                    return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public String getEventName() {
            return this.eventName;
        }
    }

    /* loaded from: classes.dex */
    public interface DynamicSuperPropertiesTracker {
        JSONObject getDynamicSuperProperties();
    }

    /* loaded from: classes.dex */
    public enum TATrackStatus {
        PAUSE,
        STOP,
        SAVE_ONLY,
        NORMAL
    }

    /* loaded from: classes.dex */
    public enum ThinkingdataNetworkType {
        NETWORKTYPE_DEFAULT,
        NETWORKTYPE_WIFI,
        NETWORKTYPE_ALL
    }

    /* loaded from: classes.dex */
    class a implements Runnable {
        final /* synthetic */ TATrackStatus a;

        a(TATrackStatus tATrackStatus) {
            this.a = tATrackStatus;
        }

        @Override // java.lang.Runnable
        public void run() {
            String str;
            int i = b.a[this.a.ordinal()];
            if (i == 1) {
                ThinkingAnalyticsSDK.this.mStorageManager.b(false);
                ThinkingAnalyticsSDK.this.mStorageManager.c(false);
                ThinkingAnalyticsSDK thinkingAnalyticsSDK = ThinkingAnalyticsSDK.this;
                thinkingAnalyticsSDK.mMessages.a(thinkingAnalyticsSDK.getToken(), false);
                ThinkingAnalyticsSDK.this.enableTracking(false);
                str = "[ThinkingData] Info: Change Status to Pause";
            } else if (i == 2) {
                ThinkingAnalyticsSDK.this.mStorageManager.a(true);
                ThinkingAnalyticsSDK.this.mStorageManager.c(false);
                ThinkingAnalyticsSDK thinkingAnalyticsSDK2 = ThinkingAnalyticsSDK.this;
                thinkingAnalyticsSDK2.mMessages.a(thinkingAnalyticsSDK2.getToken(), false);
                ThinkingAnalyticsSDK.this.optOutTracking();
                str = "[ThinkingData] Info: Change Status to Stop";
            } else {
                if (i != 3) {
                    if (i != 4) {
                        return;
                    }
                    ThinkingAnalyticsSDK.this.mStorageManager.a(true);
                    ThinkingAnalyticsSDK.this.mStorageManager.b(false);
                    ThinkingAnalyticsSDK.this.mStorageManager.c(false);
                    ThinkingAnalyticsSDK thinkingAnalyticsSDK3 = ThinkingAnalyticsSDK.this;
                    thinkingAnalyticsSDK3.mMessages.a(thinkingAnalyticsSDK3.getToken(), false);
                    TDLog.i(ThinkingAnalyticsSDK.TAG, "[ThinkingData] Info: Change Status to Normal");
                    ThinkingAnalyticsSDK.this.flush();
                    return;
                }
                ThinkingAnalyticsSDK.this.mStorageManager.a(true);
                ThinkingAnalyticsSDK.this.mStorageManager.b(false);
                ThinkingAnalyticsSDK.this.mStorageManager.c(true);
                ThinkingAnalyticsSDK thinkingAnalyticsSDK4 = ThinkingAnalyticsSDK.this;
                thinkingAnalyticsSDK4.mMessages.a(thinkingAnalyticsSDK4.getToken(), true);
                str = "[ThinkingData] Info: Change Status to SaveOnly";
            }
            TDLog.i(ThinkingAnalyticsSDK.TAG, str);
        }
    }

    /* loaded from: classes.dex */
    static /* synthetic */ class b {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[TATrackStatus.values().length];
            a = iArr;
            try {
                iArr[TATrackStatus.PAUSE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[TATrackStatus.STOP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[TATrackStatus.SAVE_ONLY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[TATrackStatus.NORMAL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class c implements Runnable {
        final /* synthetic */ String a;
        final /* synthetic */ JSONObject b;
        final /* synthetic */ boolean c;
        final /* synthetic */ long d;
        final /* synthetic */ cn.thinkingdata.analytics.utils.j e;
        final /* synthetic */ ThinkingAnalyticsSDK f;
        final /* synthetic */ cn.thinkingdata.analytics.utils.d g;
        final /* synthetic */ String h;
        final /* synthetic */ String i;
        final /* synthetic */ boolean j;
        final /* synthetic */ Map k;

        c(String str, JSONObject jSONObject, boolean z, long j, cn.thinkingdata.analytics.utils.j jVar, ThinkingAnalyticsSDK thinkingAnalyticsSDK, cn.thinkingdata.analytics.utils.d dVar, String str2, String str3, boolean z2, Map map) {
            this.a = str;
            this.b = jSONObject;
            this.c = z;
            this.d = j;
            this.e = jVar;
            this.f = thinkingAnalyticsSDK;
            this.g = dVar;
            this.h = str2;
            this.i = str3;
            this.j = z2;
            this.k = map;
        }

        @Override // java.lang.Runnable
        public void run() {
            AutoTrackEventType autoTrackEventTypeFromEventName;
            if (ThinkingAnalyticsSDK.this.mConfig.isDisabledEvent(this.a)) {
                TDLog.d(ThinkingAnalyticsSDK.TAG, "Ignoring disabled event [" + this.a + "]");
                return;
            }
            try {
                JSONObject jSONObject = this.b;
                boolean z = jSONObject != null && jSONObject.has("#bundle_id") && this.b.has("TA_KEY_SUBPROCESS_TAG__TA__");
                if (this.c && cn.thinkingdata.analytics.utils.f.a(this.a)) {
                    TDLog.e(ThinkingAnalyticsSDK.TAG, "[ThinkingData] Error: Incorrect Event name[" + this.a + "]. Event name must be string that starts with English letter, and contains letter, number, and '_'. The max length of the event name is 50.");
                    if (ThinkingAnalyticsSDK.this.mConfig.shouldThrowException()) {
                        throw new cn.thinkingdata.analytics.utils.k("Invalid event name: " + this.a);
                    }
                }
                if (this.c && !cn.thinkingdata.analytics.utils.f.a(this.b)) {
                    TDLog.w(ThinkingAnalyticsSDK.TAG, "[ThinkingData] Warning: The data contains invalid key or value: " + this.b.toString());
                    if (ThinkingAnalyticsSDK.this.mConfig.shouldThrowException()) {
                        throw new cn.thinkingdata.analytics.utils.k("Invalid properties. Please refer to SDK debug log for detail reasons.");
                    }
                }
                JSONObject obtainDefaultEventProperties = ThinkingAnalyticsSDK.this.obtainDefaultEventProperties(this.a, this.d, z);
                JSONObject jSONObject2 = this.b;
                if (jSONObject2 != null) {
                    p.a(jSONObject2, obtainDefaultEventProperties, ThinkingAnalyticsSDK.this.mConfig.getDefaultTimeZone());
                }
                if (!z && (autoTrackEventTypeFromEventName = AutoTrackEventType.autoTrackEventTypeFromEventName(this.a)) != null) {
                    if (ThinkingAnalyticsSDK.this.mAutoTrackEventListener != null) {
                        JSONObject eventCallback = ThinkingAnalyticsSDK.this.mAutoTrackEventListener.eventCallback(autoTrackEventTypeFromEventName, obtainDefaultEventProperties);
                        if (eventCallback != null) {
                            p.a(eventCallback, obtainDefaultEventProperties, ThinkingAnalyticsSDK.this.mConfig.getDefaultTimeZone());
                        }
                    } else {
                        TDLog.i(ThinkingAnalyticsSDK.TAG, "No mAutoTrackEventListener");
                    }
                }
                if (z && obtainDefaultEventProperties.has("TA_KEY_SUBPROCESS_TAG__TA__")) {
                    obtainDefaultEventProperties.remove("TA_KEY_SUBPROCESS_TAG__TA__");
                }
                cn.thinkingdata.analytics.utils.j jVar = this.e;
                if (jVar == null) {
                    jVar = cn.thinkingdata.analytics.utils.j.TRACK;
                }
                cn.thinkingdata.analytics.f.a aVar = new cn.thinkingdata.analytics.f.a(this.f, jVar, obtainDefaultEventProperties, this.g, this.h, this.i, this.j);
                aVar.a = this.a;
                Map<String, String> map = this.k;
                if (map != null) {
                    aVar.a(map);
                }
                ThinkingAnalyticsSDK.this.trackInternal(aVar);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* loaded from: classes.dex */
    class d implements Runnable {
        final /* synthetic */ String a;

        d(String str) {
            this.a = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            ThinkingAnalyticsSDK.this.mStorageManager.b(this.a, ThinkingAnalyticsSDK.this.mConfig.shouldThrowException());
        }
    }

    /* loaded from: classes.dex */
    class e implements Runnable {
        final /* synthetic */ String a;

        e(String str) {
            this.a = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            ThinkingAnalyticsSDK.this.mStorageManager.a(this.a, ThinkingAnalyticsSDK.this.mConfig.shouldThrowException());
        }
    }

    /* loaded from: classes.dex */
    class f implements Runnable {
        f() {
        }

        @Override // java.lang.Runnable
        public void run() {
            ThinkingAnalyticsSDK.this.mStorageManager.b(ThinkingAnalyticsSDK.this.mEnableTrackOldData, ThinkingAnalyticsSDK.this.mConfig.mContext);
        }
    }

    /* loaded from: classes.dex */
    class g implements Runnable {
        final /* synthetic */ JSONObject a;

        g(JSONObject jSONObject) {
            this.a = jSONObject;
        }

        @Override // java.lang.Runnable
        public void run() {
            ThinkingAnalyticsSDK.this.mStorageManager.a(this.a, ThinkingAnalyticsSDK.this.mConfig.getDefaultTimeZone(), ThinkingAnalyticsSDK.this.mConfig.shouldThrowException());
        }
    }

    /* loaded from: classes.dex */
    class h implements Runnable {
        final /* synthetic */ String a;

        h(String str) {
            this.a = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            ThinkingAnalyticsSDK.this.mStorageManager.a(this.a);
        }
    }

    /* loaded from: classes.dex */
    class i implements Runnable {
        i() {
        }

        @Override // java.lang.Runnable
        public void run() {
            ThinkingAnalyticsSDK.this.mStorageManager.c();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class j implements Runnable {
        final /* synthetic */ String a;
        final /* synthetic */ long b;

        j(String str, long j) {
            this.a = str;
            this.b = j;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                if (cn.thinkingdata.analytics.utils.f.a(this.a)) {
                    TDLog.w(ThinkingAnalyticsSDK.TAG, "timeEvent event name[" + this.a + "] is not valid");
                }
                synchronized (ThinkingAnalyticsSDK.this.mTrackTimer) {
                    ThinkingAnalyticsSDK.this.mTrackTimer.put(this.a, new cn.thinkingdata.analytics.f.d(TimeUnit.SECONDS, this.b));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class k implements Runnable {
        k() {
        }

        @Override // java.lang.Runnable
        public void run() {
            ThinkingAnalyticsSDK thinkingAnalyticsSDK = ThinkingAnalyticsSDK.this;
            thinkingAnalyticsSDK.mMessages.b(thinkingAnalyticsSDK.getToken());
        }
    }

    /* loaded from: classes.dex */
    public interface l {
        void process(ThinkingAnalyticsSDK thinkingAnalyticsSDK);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ThinkingAnalyticsSDK(TDConfig tDConfig, boolean... zArr) {
        this.mConfig = tDConfig;
        if (!TDPresetProperties.disableList.contains("#fps")) {
            if (Looper.myLooper() == null) {
                Looper.prepare();
            }
            p.e();
        }
        this.mCalibratedTimeManager = new cn.thinkingdata.analytics.utils.a(tDConfig);
        this.mUserOperationHandler = new cn.thinkingdata.analytics.f.g(this, tDConfig);
        if (zArr.length > 0 && zArr[0]) {
            this.mEnableTrackOldData = false;
            this.mTrackTimer = new HashMap();
            this.mSystemInformation = cn.thinkingdata.analytics.f.e.a(tDConfig.mContext, tDConfig.getDefaultTimeZone());
            this.mMessages = getDataHandleInstance(tDConfig.mContext);
            return;
        }
        boolean z = tDConfig.trackOldData() && !isOldDataTracked();
        this.mEnableTrackOldData = z;
        this.mStorageManager = new cn.thinkingdata.analytics.g.b(tDConfig.mContext, tDConfig.getName());
        this.mSessionManager = new cn.thinkingdata.analytics.h.a(this.mConfig.mToken, this.mStorageManager);
        this.mSystemInformation = cn.thinkingdata.analytics.f.e.a(tDConfig.mContext, tDConfig.getDefaultTimeZone());
        cn.thinkingdata.analytics.f.b dataHandleInstance = getDataHandleInstance(tDConfig.mContext);
        this.mMessages = dataHandleInstance;
        dataHandleInstance.a(getToken(), this.mStorageManager.g());
        String identifyID = getIdentifyID();
        setStatusIdentifyId(identifyID == null ? getRandomID() : identifyID);
        setStatusAccountId(this.mStorageManager.a(z, this.mConfig.mContext));
        TATrackStatus tATrackStatus = TATrackStatus.NORMAL;
        if (this.mStorageManager.g()) {
            tATrackStatus = TATrackStatus.SAVE_ONLY;
        } else if (!this.mStorageManager.d()) {
            tATrackStatus = TATrackStatus.PAUSE;
        } else if (this.mStorageManager.f()) {
            tATrackStatus = TATrackStatus.STOP;
        }
        setStatusTrackStatus(tATrackStatus);
        if (tDConfig.mEnableEncrypt) {
            cn.thinkingdata.analytics.encrypt.e.a(tDConfig.getName(), tDConfig);
        }
        if (z) {
            dataHandleInstance.c(tDConfig.getName());
        }
        this.mTrackTimer = new HashMap();
        this.mAutoTrackIgnoredActivities = new ArrayList();
        this.mAutoTrackEventTypeList = new ArrayList();
        this.mLifecycleCallbacks = new cn.thinkingdata.analytics.e.b(this, this.mConfig.getMainProcessName());
        if (Build.VERSION.SDK_INT >= 14) {
            ((Application) tDConfig.mContext.getApplicationContext()).registerActivityLifecycleCallbacks(this.mLifecycleCallbacks);
        }
        if (!tDConfig.isNormal() || p.d()) {
            enableTrackLog(true);
        }
        TRouter.init(tDConfig.mContext);
        if (tDConfig.isEnableMutiprocess() && p.f(tDConfig.mContext)) {
            TDReceiver.a(tDConfig.mContext);
        }
        TAPushUtils.clearPushEvent(this);
        TDLog.i(TAG, String.format("[ThinkingData] Info: ThinkingData SDK %s initialize success with mode: %s, APP ID ends with: %s, server url: %s, device ID: %s", "3.0.0-beta.1", tDConfig.getMode().name(), p.a(tDConfig.mToken, 4), tDConfig.getServerUrl(), getDeviceId()));
    }

    static void addInstance(ThinkingAnalyticsSDK thinkingAnalyticsSDK, Context context, String str) {
        Map<Context, Map<String, ThinkingAnalyticsSDK>> map = sInstanceMap;
        synchronized (map) {
            Map<String, ThinkingAnalyticsSDK> map2 = map.get(context);
            if (map2 == null) {
                map2 = new HashMap<>();
                map.put(context, map2);
            }
            map2.put(str, thinkingAnalyticsSDK);
        }
    }

    public static void allInstances(l lVar) {
        Map<Context, Map<String, ThinkingAnalyticsSDK>> map = sInstanceMap;
        synchronized (map) {
            Iterator<Map<String, ThinkingAnalyticsSDK>> it = map.values().iterator();
            while (it.hasNext()) {
                Iterator<ThinkingAnalyticsSDK> it2 = it.next().values().iterator();
                while (it2.hasNext()) {
                    lVar.process(it2.next());
                }
            }
        }
    }

    public static void calibrateTime(long j2) {
        TDLog.i(TAG, "[ThinkingData] Info: Time Calibration with timestamp(" + j2 + ")");
        cn.thinkingdata.analytics.utils.a.a(j2);
    }

    public static void calibrateTimeWithNtp(String... strArr) {
        cn.thinkingdata.analytics.utils.a.a(strArr);
    }

    public static void enableTrackLog(boolean z) {
        TDLog.setEnableLog(z);
    }

    public static cn.thinkingdata.analytics.utils.c getCalibratedTime() {
        return cn.thinkingdata.analytics.utils.a.b();
    }

    private String getIdentifyID() {
        return this.mStorageManager.e();
    }

    static Map<String, ThinkingAnalyticsSDK> getInstanceMap(Context context) {
        return sInstanceMap.get(context);
    }

    public static String getLocalRegion() {
        return Locale.getDefault().getCountry();
    }

    private static boolean isOldDataTracked() {
        Map<Context, Map<String, ThinkingAnalyticsSDK>> map = sInstanceMap;
        synchronized (map) {
            if (map.size() > 0) {
                Iterator<Map<String, ThinkingAnalyticsSDK>> it = map.values().iterator();
                while (it.hasNext()) {
                    Iterator<ThinkingAnalyticsSDK> it2 = it.next().values().iterator();
                    while (it2.hasNext()) {
                        if (it2.next().mEnableTrackOldData) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public JSONObject obtainDefaultEventProperties(String str, long j2, boolean z) {
        cn.thinkingdata.analytics.f.d dVar;
        JSONObject dynamicSuperProperties;
        JSONObject optJSONObject;
        JSONObject jSONObject = new JSONObject();
        try {
            p.a(new JSONObject(this.mSystemInformation.d()), jSONObject, this.mConfig.getDefaultTimeZone());
            if (!TextUtils.isEmpty(this.mSystemInformation.b())) {
                jSONObject.put("#app_version", this.mSystemInformation.b());
            }
            if (!TDPresetProperties.disableList.contains("#fps")) {
                jSONObject.put("#fps", p.a());
            }
            p.a(getSuperProperties(), jSONObject, this.mConfig.getDefaultTimeZone());
            if (!z && (optJSONObject = getAutoTrackProperties().optJSONObject(str)) != null) {
                p.a(optJSONObject, jSONObject, this.mConfig.getDefaultTimeZone());
            }
            try {
                DynamicSuperPropertiesTracker dynamicSuperPropertiesTracker = this.mDynamicSuperPropertiesTracker;
                if (dynamicSuperPropertiesTracker != null && (dynamicSuperProperties = dynamicSuperPropertiesTracker.getDynamicSuperProperties()) != null && cn.thinkingdata.analytics.utils.f.a(dynamicSuperProperties)) {
                    p.a(dynamicSuperProperties, jSONObject, this.mConfig.getDefaultTimeZone());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (!z) {
                synchronized (this.mTrackTimer) {
                    dVar = this.mTrackTimer.get(str);
                    this.mTrackTimer.remove(str);
                }
                if (dVar != null) {
                    try {
                        Double valueOf = Double.valueOf(dVar.a(j2));
                        if (valueOf.doubleValue() > 0.0d && !TDPresetProperties.disableList.contains("#duration")) {
                            jSONObject.put("#duration", valueOf);
                        }
                        Double valueOf2 = Double.valueOf(dVar.a());
                        if (valueOf2.doubleValue() > 0.0d && !str.equals("ta_app_end") && !TDPresetProperties.disableList.contains("#background_duration")) {
                            jSONObject.put("#background_duration", valueOf2);
                        }
                    } catch (JSONException e3) {
                        e3.printStackTrace();
                    }
                }
            }
            if (!TDPresetProperties.disableList.contains("#network_type")) {
                jSONObject.put("#network_type", this.mSystemInformation.c());
            }
            if (!TDPresetProperties.disableList.contains("#ram")) {
                jSONObject.put("#ram", this.mSystemInformation.b(this.mConfig.mContext));
            }
            if (!TDPresetProperties.disableList.contains("#disk")) {
                jSONObject.put("#disk", this.mSystemInformation.a(this.mConfig.mContext, false));
            }
            if (!TDPresetProperties.disableList.contains("#device_type")) {
                jSONObject.put("#device_type", p.c(this.mConfig.mContext));
            }
        } catch (Exception unused) {
        }
        return jSONObject;
    }

    public static void setCustomerLibInfo(String str, String str2) {
        cn.thinkingdata.analytics.f.e.a(str, str2);
    }

    public static ThinkingAnalyticsSDK sharedInstance(Context context, String str) {
        return sharedInstance(context, str, null, false);
    }

    public static ThinkingAnalyticsSDK sharedInstance(Context context, String str, String str2) {
        return sharedInstance(context, str, str2, true);
    }

    public static ThinkingAnalyticsSDK sharedInstance(Context context, String str, String str2, boolean z) {
        String str3;
        if (context == null) {
            str3 = "App context is required to get SDK instance.";
        } else if (TextUtils.isEmpty(str)) {
            str3 = "APP ID is required to get SDK instance.";
        } else {
            try {
                TDConfig tDConfig = TDConfig.getInstance(context, str, str2);
                tDConfig.setTrackOldData(z);
                return sharedInstance(tDConfig);
            } catch (IllegalArgumentException unused) {
                str3 = "Cannot get valid TDConfig instance. Returning null";
            }
        }
        TDLog.w(TAG, str3);
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x004f A[Catch: all -> 0x00d1, TryCatch #0 {, blocks: (B:8:0x000e, B:10:0x0019, B:15:0x004f, B:16:0x005c, B:19:0x0064, B:23:0x0070, B:25:0x007c, B:27:0x0084, B:28:0x00ab, B:30:0x00c4, B:31:0x008b, B:33:0x009c, B:34:0x00cf), top: B:7:0x000e }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static ThinkingAnalyticsSDK sharedInstance(TDConfig tDConfig) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK;
        boolean z;
        if (tDConfig == null) {
            TDLog.w(TAG, "Cannot initial SDK instance with null config instance.");
            return null;
        }
        Map<Context, Map<String, ThinkingAnalyticsSDK>> map = sInstanceMap;
        synchronized (map) {
            Map<String, ThinkingAnalyticsSDK> map2 = map.get(tDConfig.mContext);
            if (map2 == null) {
                map2 = new HashMap<>();
                map.put(tDConfig.mContext, map2);
                cn.thinkingdata.analytics.f.e a2 = cn.thinkingdata.analytics.f.e.a(tDConfig.mContext, tDConfig.getDefaultTimeZone());
                long e2 = a2.e();
                long longValue = cn.thinkingdata.analytics.g.e.a(tDConfig.mContext).b().longValue();
                if (longValue > 0 && e2 <= longValue) {
                    z = true;
                    if (!z) {
                        cn.thinkingdata.analytics.g.e.a(tDConfig.mContext).a(Long.valueOf(e2));
                    }
                    boolean g2 = a2.g();
                    if (!z && g2) {
                        sAppFirstInstallationMap.put(tDConfig.mContext, new LinkedList());
                    }
                }
                z = false;
                if (!z) {
                }
                boolean g22 = a2.g();
                if (!z) {
                    sAppFirstInstallationMap.put(tDConfig.mContext, new LinkedList());
                }
            }
            thinkingAnalyticsSDK = map2.get(tDConfig.getName());
            if (thinkingAnalyticsSDK == null) {
                if (p.f(tDConfig.mContext)) {
                    thinkingAnalyticsSDK = new ThinkingAnalyticsSDK(tDConfig, new boolean[0]);
                    Map<Context, List<String>> map3 = sAppFirstInstallationMap;
                    if (map3.containsKey(tDConfig.mContext)) {
                        map3.get(tDConfig.mContext).add(tDConfig.getName());
                    }
                } else {
                    thinkingAnalyticsSDK = new cn.thinkingdata.analytics.c(tDConfig);
                }
                map2.put(tDConfig.getName(), thinkingAnalyticsSDK);
                TRouter.getInstance().build(TRouterMap.PUSH_ROUTE_PATH).withAction("init").withString("appId", tDConfig.getName()).navigation();
            }
        }
        return thinkingAnalyticsSDK;
    }

    private void track(String str, JSONObject jSONObject, cn.thinkingdata.analytics.utils.d dVar) {
        track(str, jSONObject, dVar, true);
    }

    private void track(String str, JSONObject jSONObject, cn.thinkingdata.analytics.utils.d dVar, boolean z) {
        track(str, jSONObject, dVar, z, null, null);
    }

    public void appBecomeActive() {
        cn.thinkingdata.analytics.f.d value;
        synchronized (this.mTrackTimer) {
            try {
                try {
                    for (Map.Entry<String, cn.thinkingdata.analytics.f.d> entry : this.mTrackTimer.entrySet()) {
                        if (entry != null && (value = entry.getValue()) != null) {
                            long b2 = (value.b() + SystemClock.elapsedRealtime()) - value.d();
                            value.e(SystemClock.elapsedRealtime());
                            value.c(b2);
                        }
                    }
                } catch (Exception e2) {
                    TDLog.i(TAG, "appBecomeActive error:" + e2.getMessage());
                }
            } finally {
                flush();
            }
        }
    }

    public void appEnterBackground() {
        cn.thinkingdata.analytics.f.d value;
        synchronized (this.mTrackTimer) {
            try {
                for (Map.Entry<String, cn.thinkingdata.analytics.f.d> entry : this.mTrackTimer.entrySet()) {
                    if (entry != null && !"ta_app_end".equals(entry.getKey().toString()) && (value = entry.getValue()) != null) {
                        value.d((value.c() + SystemClock.elapsedRealtime()) - value.d());
                        value.e(SystemClock.elapsedRealtime());
                    }
                }
            } catch (Exception e2) {
                TDLog.i(TAG, "appEnterBackground error:" + e2.getMessage());
            }
        }
    }

    public void autoTrack(String str, JSONObject jSONObject) {
        track(str, jSONObject, this.mCalibratedTimeManager.a(), false);
    }

    public void autoTrack(String str, JSONObject jSONObject, cn.thinkingdata.analytics.utils.d dVar) {
        track(str, jSONObject, dVar, false);
    }

    public void clearSuperProperties() {
        if (getStatusHasDisabled()) {
            return;
        }
        this.mTrackTaskManager.a(new i());
    }

    /* JADX DEBUG: Method merged with bridge method: createLightInstance()Lcn/thinkingdata/analytics/a; */
    /* renamed from: createLightInstance, reason: merged with bridge method [inline-methods] */
    public ThinkingAnalyticsSDK m37createLightInstance() {
        return new cn.thinkingdata.analytics.b(this.mConfig);
    }

    public void enableAutoTrack(List<AutoTrackEventType> list) {
        if (getStatusHasDisabled()) {
            return;
        }
        this.mAutoTrack = true;
        if (list == null || list.size() == 0) {
            return;
        }
        if (list.contains(AutoTrackEventType.APP_INSTALL)) {
            synchronized (sInstanceMap) {
                Map<Context, List<String>> map = sAppFirstInstallationMap;
                if (map.containsKey(this.mConfig.mContext) && map.get(this.mConfig.mContext).contains(getToken())) {
                    cn.thinkingdata.analytics.h.a aVar = this.mSessionManager;
                    if (aVar != null) {
                        aVar.a();
                    }
                    track("ta_app_install");
                    flush();
                    map.get(this.mConfig.mContext).remove(getToken());
                }
            }
        }
        if (list.contains(AutoTrackEventType.APP_CRASH)) {
            this.mTrackCrash = true;
            cn.thinkingdata.analytics.e.a b2 = cn.thinkingdata.analytics.e.a.b(this.mConfig.mContext);
            if (b2 != null) {
                b2.a();
            }
        }
        if (!this.mAutoTrackEventTypeList.contains(AutoTrackEventType.APP_END) && list.contains(AutoTrackEventType.APP_END)) {
            timeEvent("ta_app_end");
            this.mLifecycleCallbacks.a(true);
        }
        synchronized (this) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            this.mAutoTrackStartTime = this.mCalibratedTimeManager.a();
            this.mAutoTrackStartProperties = obtainDefaultEventProperties("ta_app_start", elapsedRealtime, false);
        }
        this.mAutoTrackEventTypeList.clear();
        this.mAutoTrackEventTypeList.addAll(list);
        if (this.mAutoTrackEventTypeList.contains(AutoTrackEventType.APP_START)) {
            this.mLifecycleCallbacks.b();
        }
    }

    public void enableAutoTrack(List<AutoTrackEventType> list, AutoTrackEventListener autoTrackEventListener) {
        this.mAutoTrackEventListener = autoTrackEventListener;
        enableAutoTrack(list);
    }

    public void enableAutoTrack(List<AutoTrackEventType> list, JSONObject jSONObject) {
        setAutoTrackProperties(list, jSONObject);
        enableAutoTrack(list);
    }

    public void enableThirdPartySharing(int i2) {
        TRouter.getInstance().build("/thingkingdata/third/party").withAction("enableThirdPartySharing").withInt(AppMeasurement.Param.TYPE, i2).withObject("instance", this).withString("loginId", getLoginId()).navigation();
    }

    public void enableThirdPartySharing(int i2, Object obj) {
        TRouter.getInstance().build("/thingkingdata/third/party").withAction("enableThirdPartySharingWithParams").withInt(AppMeasurement.Param.TYPE, i2).withObject("instance", this).withString("loginId", getLoginId()).withObject("params", obj).navigation();
    }

    @Deprecated
    public void enableTracking(boolean z) {
        if (!z) {
            flush();
        }
        setStatusTrackStatus(TATrackStatus.PAUSE);
        this.mStorageManager.a(z);
    }

    public void flush() {
        boolean statusHasDisabled = getStatusHasDisabled();
        boolean isStatusTrackSaveOnly = isStatusTrackSaveOnly();
        if (statusHasDisabled || isStatusTrackSaveOnly) {
            return;
        }
        this.mTrackTaskManager.a(new k());
    }

    public List<AutoTrackEventType> getAutoTrackEventTypeList() {
        return this.mAutoTrackEventTypeList;
    }

    public JSONObject getAutoTrackProperties() {
        return this.mAutoTrackEventProperties;
    }

    public synchronized JSONObject getAutoTrackStartProperties() {
        JSONObject jSONObject;
        jSONObject = this.mAutoTrackStartProperties;
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        return jSONObject;
    }

    public synchronized cn.thinkingdata.analytics.utils.d getAutoTrackStartTime() {
        return this.mAutoTrackStartTime;
    }

    protected cn.thinkingdata.analytics.f.b getDataHandleInstance(Context context) {
        return cn.thinkingdata.analytics.f.b.b(context);
    }

    public String getDeviceId() {
        if (this.mSystemInformation.d().containsKey("#device_id")) {
            return (String) this.mSystemInformation.d().get("#device_id");
        }
        return null;
    }

    public String getDistinctId() {
        String statusIdentifyId = getStatusIdentifyId();
        return statusIdentifyId == null ? getRandomID() : statusIdentifyId;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public DynamicSuperPropertiesTracker getDynamicSuperPropertiesTracker() {
        return this.mDynamicSuperPropertiesTracker;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<Class> getIgnoredViewTypeList() {
        if (this.mIgnoredViewTypeList == null) {
            this.mIgnoredViewTypeList = new ArrayList();
        }
        return this.mIgnoredViewTypeList;
    }

    String getLoginId() {
        return getStatusAccountId();
    }

    public TDPresetProperties getPresetProperties() {
        JSONObject a2 = cn.thinkingdata.analytics.f.e.e(this.mConfig.mContext).a();
        String c2 = cn.thinkingdata.analytics.f.e.e(this.mConfig.mContext).c();
        double doubleValue = this.mCalibratedTimeManager.a().a().doubleValue();
        try {
            if (!TDPresetProperties.disableList.contains("#network_type")) {
                a2.put("#network_type", c2);
            }
            a2.put("#zone_offset", doubleValue);
            if (!TDPresetProperties.disableList.contains("#ram")) {
                a2.put("#ram", this.mSystemInformation.b(this.mConfig.mContext));
            }
            if (!TDPresetProperties.disableList.contains("#disk")) {
                a2.put("#disk", this.mSystemInformation.a(this.mConfig.mContext, false));
            }
            if (!TDPresetProperties.disableList.contains("#fps")) {
                a2.put("#fps", p.a());
            }
            if (!TDPresetProperties.disableList.contains("#device_type")) {
                a2.put("#device_type", p.c(this.mConfig.mContext));
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return new TDPresetProperties(a2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String getRandomID() {
        return cn.thinkingdata.analytics.g.e.a(this.mConfig.mContext).e();
    }

    public synchronized String getStatusAccountId() {
        return this._statusAccountId;
    }

    public synchronized boolean getStatusHasDisabled() {
        boolean z;
        TATrackStatus statusTrackStatus = getStatusTrackStatus();
        if (statusTrackStatus != TATrackStatus.STOP) {
            z = statusTrackStatus == TATrackStatus.PAUSE;
        }
        return z;
    }

    public synchronized String getStatusIdentifyId() {
        return this._statusIdentifyId;
    }

    protected synchronized TATrackStatus getStatusTrackStatus() {
        return this._statusTrackStatus;
    }

    public JSONObject getSuperProperties() {
        return this.mStorageManager.h();
    }

    public String getTimeString(Date date) {
        return this.mCalibratedTimeManager.a(date, this.mConfig.getDefaultTimeZone()).b();
    }

    public String getToken() {
        return this.mConfig.getName();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean hasDisabled() {
        return !isEnabled() || hasOptOut();
    }

    public boolean hasOptOut() {
        return this.mStorageManager.f();
    }

    public void identify(String str) {
        if (getStatusHasDisabled()) {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            TDLog.w(TAG, "The identity cannot be empty.");
            if (this.mConfig.shouldThrowException()) {
                throw new cn.thinkingdata.analytics.utils.k("distinct id cannot be empty");
            }
        } else {
            TDLog.i(TAG, "[ThinkingData] Info: Setting distinct ID, DistinctId = " + str);
            setStatusIdentifyId(str);
            this.mTrackTaskManager.a(new d(str));
        }
    }

    public void ignoreAppViewEventInExtPackage() {
        this.mIgnoreAppViewInExtPackage = true;
    }

    public void ignoreAutoTrackActivities(List<Class<?>> list) {
        if (getStatusHasDisabled() || list == null || list.size() == 0) {
            return;
        }
        if (this.mAutoTrackIgnoredActivities == null) {
            this.mAutoTrackIgnoredActivities = new ArrayList();
        }
        for (Class<?> cls : list) {
            if (cls != null && !this.mAutoTrackIgnoredActivities.contains(Integer.valueOf(cls.hashCode()))) {
                this.mAutoTrackIgnoredActivities.add(Integer.valueOf(cls.hashCode()));
            }
        }
    }

    public void ignoreAutoTrackActivity(Class<?> cls) {
        if (getStatusHasDisabled() || cls == null) {
            return;
        }
        if (this.mAutoTrackIgnoredActivities == null) {
            this.mAutoTrackIgnoredActivities = new ArrayList();
        }
        if (this.mAutoTrackIgnoredActivities.contains(Integer.valueOf(cls.hashCode()))) {
            return;
        }
        this.mAutoTrackIgnoredActivities.add(Integer.valueOf(cls.hashCode()));
    }

    public void ignoreView(View view) {
        if (getStatusHasDisabled() || view == null) {
            return;
        }
        p.a(getToken(), view, R.id.thinking_analytics_tag_view_ignored, "1");
    }

    public void ignoreViewType(Class cls) {
        if (getStatusHasDisabled() || cls == null) {
            return;
        }
        if (this.mIgnoredViewTypeList == null) {
            this.mIgnoredViewTypeList = new ArrayList();
        }
        if (this.mIgnoredViewTypeList.contains(cls)) {
            return;
        }
        this.mIgnoredViewTypeList.add(cls);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isActivityAutoTrackAppClickIgnored(Class<?> cls) {
        if (cls == null) {
            return false;
        }
        List<Integer> list = this.mAutoTrackIgnoredActivities;
        if (list != null && list.contains(Integer.valueOf(cls.hashCode()))) {
            return true;
        }
        ThinkingDataIgnoreTrackAppViewScreenAndAppClick thinkingDataIgnoreTrackAppViewScreenAndAppClick = (ThinkingDataIgnoreTrackAppViewScreenAndAppClick) cls.getAnnotation(ThinkingDataIgnoreTrackAppViewScreenAndAppClick.class);
        if (thinkingDataIgnoreTrackAppViewScreenAndAppClick != null && (TextUtils.isEmpty(thinkingDataIgnoreTrackAppViewScreenAndAppClick.appId()) || getToken().equals(thinkingDataIgnoreTrackAppViewScreenAndAppClick.appId()))) {
            return true;
        }
        ThinkingDataIgnoreTrackAppClick thinkingDataIgnoreTrackAppClick = (ThinkingDataIgnoreTrackAppClick) cls.getAnnotation(ThinkingDataIgnoreTrackAppClick.class);
        if (thinkingDataIgnoreTrackAppClick != null) {
            return TextUtils.isEmpty(thinkingDataIgnoreTrackAppClick.appId()) || getToken().equals(thinkingDataIgnoreTrackAppClick.appId());
        }
        return false;
    }

    public boolean isActivityAutoTrackAppViewScreenIgnored(Class<?> cls) {
        if (cls == null) {
            return false;
        }
        List<Integer> list = this.mAutoTrackIgnoredActivities;
        if (list != null && list.contains(Integer.valueOf(cls.hashCode()))) {
            return true;
        }
        ThinkingDataIgnoreTrackAppViewScreenAndAppClick thinkingDataIgnoreTrackAppViewScreenAndAppClick = (ThinkingDataIgnoreTrackAppViewScreenAndAppClick) cls.getAnnotation(ThinkingDataIgnoreTrackAppViewScreenAndAppClick.class);
        if (thinkingDataIgnoreTrackAppViewScreenAndAppClick != null && (TextUtils.isEmpty(thinkingDataIgnoreTrackAppViewScreenAndAppClick.appId()) || getToken().equals(thinkingDataIgnoreTrackAppViewScreenAndAppClick.appId()))) {
            return true;
        }
        ThinkingDataIgnoreTrackAppViewScreen thinkingDataIgnoreTrackAppViewScreen = (ThinkingDataIgnoreTrackAppViewScreen) cls.getAnnotation(ThinkingDataIgnoreTrackAppViewScreen.class);
        return thinkingDataIgnoreTrackAppViewScreen != null && (TextUtils.isEmpty(thinkingDataIgnoreTrackAppViewScreen.appId()) || getToken().equals(thinkingDataIgnoreTrackAppViewScreen.appId()));
    }

    public boolean isAutoTrackEnabled() {
        if (getStatusHasDisabled()) {
            return false;
        }
        return this.mAutoTrack;
    }

    public boolean isAutoTrackEventTypeIgnored(AutoTrackEventType autoTrackEventType) {
        return (autoTrackEventType == null || this.mAutoTrackEventTypeList.contains(autoTrackEventType)) ? false : true;
    }

    public boolean isEnabled() {
        return this.mStorageManager.d();
    }

    public boolean isIgnoreAppViewInExtPackage() {
        return this.mIgnoreAppViewInExtPackage;
    }

    public synchronized boolean isStatusTrackSaveOnly() {
        return getStatusTrackStatus() == TATrackStatus.SAVE_ONLY;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isTrackFragmentAppViewScreenEnabled() {
        return this.mTrackFragmentAppViewScreen;
    }

    public void login(String str) {
        if (getStatusHasDisabled()) {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            TDLog.w(TAG, "The account id cannot be empty.");
            if (this.mConfig.shouldThrowException()) {
                throw new cn.thinkingdata.analytics.utils.k("account id cannot be empty");
            }
            return;
        }
        TDLog.i(TAG, "[ThinkingData] Info: Login SDK, AccountId = " + str);
        setStatusAccountId(str);
        this.mTrackTaskManager.a(new e(str));
        TRouter.getInstance().build(TRouterMap.PUSH_ROUTE_PATH).withAction(FirebaseAnalytics.Event.LOGIN).withString("appId", getToken()).navigation();
    }

    public void logout() {
        if (getStatusHasDisabled()) {
            return;
        }
        TDLog.i(TAG, "[ThinkingData] Info: Logout SDK");
        setStatusAccountId(null);
        this.mTrackTaskManager.a(new f());
    }

    @Deprecated
    public void optInTracking() {
        setStatusTrackStatus(TATrackStatus.NORMAL);
        this.mStorageManager.b(false);
        this.mMessages.b(getToken());
    }

    @Deprecated
    public void optOutTracking() {
        setStatusTrackStatus(TATrackStatus.PAUSE);
        this.mStorageManager.b(true);
        this.mMessages.a(getToken());
        synchronized (this.mTrackTimer) {
            this.mTrackTimer.clear();
        }
        setStatusAccountId(null);
        setStatusIdentifyId(getRandomID());
        this.mStorageManager.a();
        this.mStorageManager.b();
        this.mStorageManager.c();
    }

    @Deprecated
    public void optOutTrackingAndDeleteUser() {
        setStatusTrackStatus(TATrackStatus.STOP);
        cn.thinkingdata.analytics.f.a aVar = new cn.thinkingdata.analytics.f.a(this, cn.thinkingdata.analytics.utils.j.USER_DEL, null, this.mCalibratedTimeManager.a(), getStatusIdentifyId(), getStatusAccountId(), false);
        aVar.b();
        trackInternal(aVar);
        optOutTracking();
    }

    public void setAutoTrackProperties(List<AutoTrackEventType> list, JSONObject jSONObject) {
        if (getStatusHasDisabled()) {
            return;
        }
        if (jSONObject != null) {
            try {
                if (cn.thinkingdata.analytics.utils.f.a(jSONObject)) {
                    JSONObject jSONObject2 = new JSONObject();
                    for (AutoTrackEventType autoTrackEventType : list) {
                        JSONObject jSONObject3 = new JSONObject();
                        p.a(jSONObject, jSONObject3, this.mConfig.getDefaultTimeZone());
                        jSONObject2.put(autoTrackEventType.getEventName(), jSONObject3);
                    }
                    synchronized (this.mAutoTrackEventProperties) {
                        p.b(jSONObject2, this.mAutoTrackEventProperties, this.mConfig.getDefaultTimeZone());
                    }
                    return;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                return;
            }
        }
        if (this.mConfig.shouldThrowException()) {
            throw new cn.thinkingdata.analytics.utils.k("Set autoTrackEvent properties failed. Please refer to the SDK debug log for details.");
        }
    }

    public void setDynamicSuperPropertiesTracker(DynamicSuperPropertiesTracker dynamicSuperPropertiesTracker) {
        if (getStatusHasDisabled()) {
            return;
        }
        this.mDynamicSuperPropertiesTracker = dynamicSuperPropertiesTracker;
    }

    public void setJsBridge(WebView webView) {
        if (webView != null) {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.addJavascriptInterface(new TDWebAppInterface(this, this.mSystemInformation.d()), "ThinkingData_APP_JS_Bridge");
        } else {
            TDLog.d(TAG, "SetJsBridge failed due to parameter webView is null");
            if (this.mConfig.shouldThrowException()) {
                throw new cn.thinkingdata.analytics.utils.k("webView cannot be null for setJsBridge");
            }
        }
    }

    public void setJsBridgeForX5WebView(Object obj) {
        if (obj == null) {
            TDLog.d(TAG, "SetJsBridge failed due to parameter webView is null");
            return;
        }
        try {
            obj.getClass().getMethod("addJavascriptInterface", Object.class, String.class).invoke(obj, new TDWebAppInterface(this, this.mSystemInformation.d()), "ThinkingData_APP_JS_Bridge");
        } catch (Exception e2) {
            TDLog.w(TAG, "setJsBridgeForX5WebView failed: " + e2.toString());
        }
    }

    public void setNetworkType(ThinkingdataNetworkType thinkingdataNetworkType) {
        if (getStatusHasDisabled()) {
            return;
        }
        this.mConfig.setNetworkType(thinkingdataNetworkType);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public synchronized void setStatusAccountId(String str) {
        this._statusAccountId = str;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public synchronized void setStatusIdentifyId(String str) {
        this._statusIdentifyId = str;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public synchronized void setStatusTrackStatus(TATrackStatus tATrackStatus) {
        this._statusTrackStatus = tATrackStatus;
    }

    public void setSuperProperties(JSONObject jSONObject) {
        if (getStatusHasDisabled()) {
            return;
        }
        this.mTrackTaskManager.a(new g(jSONObject));
    }

    public void setTrackStatus(TATrackStatus tATrackStatus) {
        setStatusTrackStatus(tATrackStatus);
        this.mTrackTaskManager.a(new a(tATrackStatus));
    }

    public void setViewID(Dialog dialog, String str) {
        if (getStatusHasDisabled() || dialog == null) {
            return;
        }
        try {
            if (TextUtils.isEmpty(str) || dialog.getWindow() == null) {
                return;
            }
            p.a(getToken(), dialog.getWindow().getDecorView(), R.id.thinking_analytics_tag_view_id, str);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void setViewID(View view, String str) {
        if (getStatusHasDisabled() || view == null || TextUtils.isEmpty(str)) {
            return;
        }
        p.a(getToken(), view, R.id.thinking_analytics_tag_view_id, str);
    }

    public void setViewProperties(View view, JSONObject jSONObject) {
        if (getStatusHasDisabled() || view == null || jSONObject == null) {
            return;
        }
        p.a(getToken(), view, R.id.thinking_analytics_tag_view_properties, jSONObject);
    }

    public boolean shouldTrackCrash() {
        if (getStatusHasDisabled()) {
            return false;
        }
        return this.mTrackCrash;
    }

    public void timeEvent(String str) {
        if (getStatusHasDisabled()) {
            return;
        }
        this.mTrackTaskManager.a(new j(str, SystemClock.elapsedRealtime()));
    }

    public void track(ThinkingAnalyticsEvent thinkingAnalyticsEvent) {
        if (getStatusHasDisabled()) {
            return;
        }
        if (thinkingAnalyticsEvent == null) {
            TDLog.w(TAG, "Ignoring empty event...");
            return;
        }
        cn.thinkingdata.analytics.utils.d a2 = thinkingAnalyticsEvent.getEventTime() != null ? this.mCalibratedTimeManager.a(thinkingAnalyticsEvent.getEventTime(), thinkingAnalyticsEvent.getTimeZone()) : this.mCalibratedTimeManager.a();
        HashMap hashMap = new HashMap();
        if (TextUtils.isEmpty(thinkingAnalyticsEvent.getExtraField())) {
            TDLog.w(TAG, "Invalid ExtraFields. Ignoring...");
        } else {
            hashMap.put(thinkingAnalyticsEvent.getExtraField(), ((thinkingAnalyticsEvent instanceof TDFirstEvent) && thinkingAnalyticsEvent.getExtraValue() == null) ? getDeviceId() : thinkingAnalyticsEvent.getExtraValue());
        }
        track(thinkingAnalyticsEvent.getEventName(), thinkingAnalyticsEvent.getProperties(), a2, true, hashMap, thinkingAnalyticsEvent.getDataType());
    }

    public void track(String str) {
        track(str, (JSONObject) null, this.mCalibratedTimeManager.a());
    }

    public void track(String str, JSONObject jSONObject) {
        track(str, jSONObject, this.mCalibratedTimeManager.a());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void track(String str, JSONObject jSONObject, cn.thinkingdata.analytics.utils.d dVar, boolean z, Map<String, String> map, cn.thinkingdata.analytics.utils.j jVar) {
        if (getStatusHasDisabled()) {
            return;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        String statusAccountId = getStatusAccountId();
        this.mTrackTaskManager.a(new c(str, jSONObject, z, elapsedRealtime, jVar, this, dVar, getStatusIdentifyId(), statusAccountId, isStatusTrackSaveOnly(), map));
    }

    public void track(String str, JSONObject jSONObject, Date date) {
        track(str, jSONObject, this.mCalibratedTimeManager.a(date, null));
    }

    public void track(String str, JSONObject jSONObject, Date date, TimeZone timeZone) {
        track(str, jSONObject, this.mCalibratedTimeManager.a(date, timeZone));
    }

    public void trackAppCrashAndEndEvent(JSONObject jSONObject) {
        this.mLifecycleCallbacks.a(jSONObject);
    }

    public void trackAppInstall() {
        if (getStatusHasDisabled()) {
            return;
        }
        enableAutoTrack(new ArrayList(Collections.singletonList(AutoTrackEventType.APP_INSTALL)));
    }

    public void trackFragmentAppViewScreen() {
        if (getStatusHasDisabled()) {
            return;
        }
        this.mTrackFragmentAppViewScreen = true;
    }

    public void trackInternal(cn.thinkingdata.analytics.f.a aVar) {
        if (this.mConfig.isDebugOnly() || this.mConfig.isDebug()) {
            this.mMessages.b(aVar);
        } else if (aVar.h) {
            this.mMessages.c(aVar);
        } else {
            this.mMessages.a(aVar);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: android.app.Activity */
    /* JADX WARN: Multi-variable type inference failed */
    public void trackViewScreen(Activity activity) {
        if (getStatusHasDisabled() || activity == 0) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            if (!TDPresetProperties.disableList.contains("#screen_name")) {
                jSONObject.put("#screen_name", activity.getClass().getCanonicalName());
            }
            p.a(jSONObject, activity);
            if (!(activity instanceof ScreenAutoTracker)) {
                autoTrack("ta_app_view", jSONObject);
                return;
            }
            ScreenAutoTracker screenAutoTracker = (ScreenAutoTracker) activity;
            String screenUrl = screenAutoTracker.getScreenUrl();
            JSONObject trackProperties = screenAutoTracker.getTrackProperties();
            if (trackProperties != null) {
                p.a(trackProperties, jSONObject, this.mConfig.getDefaultTimeZone());
            }
            trackViewScreenInternal(screenUrl, jSONObject);
        } catch (Exception e2) {
            TDLog.i(TAG, "trackViewScreen:" + e2);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r11v0, resolved type: android.app.Fragment */
    /* JADX WARN: Multi-variable type inference failed */
    public void trackViewScreen(Fragment fragment) {
        if (getStatusHasDisabled() || fragment == 0) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            Object canonicalName = fragment.getClass().getCanonicalName();
            String a2 = p.a(fragment, getToken());
            Activity activity = fragment.getActivity();
            if (activity != null) {
                if (TextUtils.isEmpty(a2)) {
                    a2 = p.a(activity);
                }
                canonicalName = String.format(Locale.CHINA, "%s|%s", activity.getClass().getCanonicalName(), canonicalName);
            }
            if (!TextUtils.isEmpty(a2) && !TDPresetProperties.disableList.contains("#title")) {
                jSONObject.put("#title", a2);
            }
            if (!TDPresetProperties.disableList.contains("#screen_name")) {
                jSONObject.put("#screen_name", canonicalName);
            }
            if (!(fragment instanceof ScreenAutoTracker)) {
                autoTrack("ta_app_view", jSONObject);
                return;
            }
            ScreenAutoTracker screenAutoTracker = (ScreenAutoTracker) fragment;
            String screenUrl = screenAutoTracker.getScreenUrl();
            JSONObject trackProperties = screenAutoTracker.getTrackProperties();
            if (trackProperties != null) {
                p.a(trackProperties, jSONObject, this.mConfig.getDefaultTimeZone());
            }
            trackViewScreenInternal(screenUrl, jSONObject);
        } catch (Exception e2) {
            TDLog.i(TAG, "trackViewScreen:" + e2);
        }
    }

    public void trackViewScreen(Object obj) {
        Class<?> cls;
        Class<?> cls2;
        Class<?> cls3;
        if (getStatusHasDisabled() || obj == null) {
            return;
        }
        Activity activity = null;
        try {
            cls = Class.forName("androidx.fragment.app.Fragment");
        } catch (Exception unused) {
            cls = null;
        }
        try {
            cls2 = Class.forName("android.app.Fragment");
        } catch (Exception unused2) {
            cls2 = null;
        }
        try {
            cls3 = Class.forName("androidx.fragment.app.Fragment");
        } catch (Exception unused3) {
            cls3 = null;
        }
        if ((cls == null || !cls.isInstance(obj)) && ((cls2 == null || !cls2.isInstance(obj)) && (cls3 == null || !cls3.isInstance(obj)))) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            Object canonicalName = obj.getClass().getCanonicalName();
            String a2 = p.a(obj, getToken());
            try {
                activity = (Activity) obj.getClass().getMethod("getActivity", new Class[0]).invoke(obj, new Object[0]);
            } catch (Exception unused4) {
            }
            if (activity != null) {
                if (TextUtils.isEmpty(a2)) {
                    a2 = p.a(activity);
                }
                canonicalName = String.format(Locale.CHINA, "%s|%s", activity.getClass().getCanonicalName(), canonicalName);
            }
            if (!TextUtils.isEmpty(a2) && !TDPresetProperties.disableList.contains("#title")) {
                jSONObject.put("#title", a2);
            }
            if (!TDPresetProperties.disableList.contains("#screen_name")) {
                jSONObject.put("#screen_name", canonicalName);
            }
            if (!(obj instanceof ScreenAutoTracker)) {
                autoTrack("ta_app_view", jSONObject);
                return;
            }
            ScreenAutoTracker screenAutoTracker = (ScreenAutoTracker) obj;
            String screenUrl = screenAutoTracker.getScreenUrl();
            JSONObject trackProperties = screenAutoTracker.getTrackProperties();
            if (trackProperties != null) {
                p.a(trackProperties, jSONObject, this.mConfig.getDefaultTimeZone());
            }
            trackViewScreenInternal(screenUrl, jSONObject);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void trackViewScreenInternal(String str, JSONObject jSONObject) {
        if (getStatusHasDisabled()) {
            return;
        }
        try {
            if (TextUtils.isEmpty(str) && jSONObject == null) {
                return;
            }
            JSONObject jSONObject2 = new JSONObject();
            if (!TextUtils.isEmpty(this.mLastScreenUrl) && !TDPresetProperties.disableList.contains("#referrer")) {
                jSONObject2.put("#referrer", this.mLastScreenUrl);
            }
            if (!TDPresetProperties.disableList.contains("#url")) {
                jSONObject2.put("#url", str);
            }
            this.mLastScreenUrl = str;
            if (jSONObject != null) {
                p.a(jSONObject, jSONObject2, this.mConfig.getDefaultTimeZone());
            }
            autoTrack("ta_app_view", jSONObject2);
        } catch (JSONException e2) {
            TDLog.i(TAG, "trackViewScreen:" + e2);
        }
    }

    public void unsetSuperProperty(String str) {
        if (getStatusHasDisabled()) {
            return;
        }
        this.mTrackTaskManager.a(new h(str));
    }

    public void user_add(String str, Number number) {
        this.mUserOperationHandler.a(str, number);
    }

    public void user_add(JSONObject jSONObject) {
        this.mUserOperationHandler.a(jSONObject, (Date) null);
    }

    public void user_add(JSONObject jSONObject, Date date) {
        this.mUserOperationHandler.a(jSONObject, date);
    }

    public void user_append(JSONObject jSONObject) {
        this.mUserOperationHandler.b(jSONObject, null);
    }

    public void user_append(JSONObject jSONObject, Date date) {
        this.mUserOperationHandler.b(jSONObject, date);
    }

    public void user_delete() {
        this.mUserOperationHandler.a((Date) null);
    }

    public void user_delete(Date date) {
        this.mUserOperationHandler.a(date);
    }

    public void user_operations(cn.thinkingdata.analytics.utils.j jVar, JSONObject jSONObject, Date date) {
        this.mUserOperationHandler.a(jVar, jSONObject, date);
    }

    public void user_set(JSONObject jSONObject) {
        this.mUserOperationHandler.c(jSONObject, null);
    }

    public void user_set(JSONObject jSONObject, Date date) {
        this.mUserOperationHandler.c(jSONObject, date);
    }

    public void user_setOnce(JSONObject jSONObject) {
        this.mUserOperationHandler.d(jSONObject, null);
    }

    public void user_setOnce(JSONObject jSONObject, Date date) {
        this.mUserOperationHandler.d(jSONObject, date);
    }

    public void user_uniqAppend(JSONObject jSONObject) {
        this.mUserOperationHandler.e(jSONObject, null);
    }

    public void user_uniqAppend(JSONObject jSONObject, Date date) {
        this.mUserOperationHandler.e(jSONObject, date);
    }

    public void user_unset(JSONObject jSONObject, Date date) {
        this.mUserOperationHandler.f(jSONObject, date);
    }

    public void user_unset(String... strArr) {
        this.mUserOperationHandler.a(strArr);
    }
}
