package cn.thinkingdata.analytics.e;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import cn.thinkingdata.analytics.ScreenAutoTracker;
import cn.thinkingdata.analytics.TDPresetProperties;
import cn.thinkingdata.analytics.ThinkingAnalyticsSDK;
import cn.thinkingdata.analytics.ThinkingDataAutoTrackAppViewScreenUrl;
import cn.thinkingdata.analytics.f.d;
import cn.thinkingdata.analytics.utils.f;
import cn.thinkingdata.analytics.utils.j;
import cn.thinkingdata.analytics.utils.p;
import cn.thinkingdata.core.utils.TDLog;
import com.google.android.gms.common.internal.ImagesContract;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class b implements Application.ActivityLifecycleCallbacks {
    private final ThinkingAnalyticsSDK c;
    private d e;
    private WeakReference<Activity> f;
    private boolean a = false;
    private final Object b = new Object();
    private volatile Boolean d = true;
    private final List<WeakReference<Activity>> g = new ArrayList();
    private boolean h = false;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class a implements Runnable {
        final /* synthetic */ JSONObject a;
        final /* synthetic */ cn.thinkingdata.analytics.utils.d b;
        final /* synthetic */ String c;
        final /* synthetic */ String d;
        final /* synthetic */ boolean e;

        a(JSONObject jSONObject, cn.thinkingdata.analytics.utils.d dVar, String str, String str2, boolean z) {
            this.a = jSONObject;
            this.b = dVar;
            this.c = str;
            this.d = str2;
            this.e = z;
        }

        @Override // java.lang.Runnable
        public void run() {
            JSONObject autoTrackStartProperties = b.this.c.getAutoTrackStartProperties();
            try {
                p.a(this.a, autoTrackStartProperties, b.this.c.mConfig.getDefaultTimeZone());
            } catch (JSONException e) {
                TDLog.i("ThinkingAnalytics.ThinkingDataActivityLifecycleCallbacks", e);
            }
            cn.thinkingdata.analytics.f.a aVar = new cn.thinkingdata.analytics.f.a(b.this.c, j.TRACK, autoTrackStartProperties, this.b, this.c, this.d, this.e);
            aVar.a = "ta_app_start";
            b.this.c.trackInternal(aVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: cn.thinkingdata.analytics.e.b$b, reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public class C0010b extends TimerTask {
        final /* synthetic */ cn.thinkingdata.analytics.utils.d a;

        C0010b(cn.thinkingdata.analytics.utils.d dVar) {
            this.a = dVar;
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            if (b.this.d.booleanValue()) {
                b.this.d = false;
                JSONObject jSONObject = new JSONObject();
                try {
                    if (!TDPresetProperties.disableList.contains("#resume_from_background")) {
                        jSONObject.put("#resume_from_background", b.this.a);
                    }
                    if (!TDPresetProperties.disableList.contains("#start_reason")) {
                        String a = b.this.a();
                        if (!a.equals(new JSONObject().toString())) {
                            jSONObject.put("#start_reason", a);
                        }
                    }
                } catch (Exception unused) {
                } catch (Throwable th) {
                    b.this.c.autoTrack("ta_app_start", jSONObject, this.a);
                    b.this.c.flush();
                    b.this.h = true;
                    throw th;
                }
                b.this.c.autoTrack("ta_app_start", jSONObject, this.a);
                b.this.c.flush();
                b.this.h = true;
            }
        }
    }

    public b(ThinkingAnalyticsSDK thinkingAnalyticsSDK, String str) {
        this.c = thinkingAnalyticsSDK;
    }

    public static JSONArray a(Object obj) {
        JSONArray jSONArray = new JSONArray();
        if (!obj.getClass().isArray()) {
            throw new JSONException("Not a primitive array: " + obj.getClass());
        }
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            jSONArray.put(b(Array.get(obj, i)));
        }
        return jSONArray;
    }

    private void a(Activity activity, cn.thinkingdata.analytics.utils.d dVar) {
        if (this.d.booleanValue() || this.a) {
            this.c.mSessionManager.b();
            if (this.c.isAutoTrackEnabled()) {
                try {
                    if (!this.c.isAutoTrackEventTypeIgnored(ThinkingAnalyticsSDK.AutoTrackEventType.APP_START)) {
                        this.d = false;
                        JSONObject jSONObject = new JSONObject();
                        if (!TDPresetProperties.disableList.contains("#resume_from_background")) {
                            jSONObject.put("#resume_from_background", this.a);
                        }
                        if (!TDPresetProperties.disableList.contains("#start_reason")) {
                            String a2 = a();
                            if (!a2.equals(new JSONObject().toString())) {
                                jSONObject.put("#start_reason", a2);
                            }
                        }
                        p.a(jSONObject, activity);
                        if (this.e != null) {
                            double parseDouble = Double.parseDouble(this.e.a(SystemClock.elapsedRealtime()));
                            if (parseDouble > 0.0d && !TDPresetProperties.disableList.contains("#background_duration")) {
                                jSONObject.put("#background_duration", parseDouble);
                            }
                        }
                        if (dVar == null) {
                            this.c.autoTrack("ta_app_start", jSONObject);
                        } else {
                            if (this.c.getStatusHasDisabled()) {
                                return;
                            }
                            String statusAccountId = this.c.getStatusAccountId();
                            this.c.mTrackTaskManager.a(new a(jSONObject, dVar, this.c.getStatusIdentifyId(), statusAccountId, this.c.isStatusTrackSaveOnly()));
                            this.h = true;
                        }
                    }
                    if (dVar == null && !this.c.isAutoTrackEventTypeIgnored(ThinkingAnalyticsSDK.AutoTrackEventType.APP_END)) {
                        this.c.timeEvent("ta_app_end");
                        this.h = true;
                    }
                } catch (Exception e) {
                    TDLog.i("ThinkingAnalytics.ThinkingDataActivityLifecycleCallbacks", e);
                }
            }
            try {
                this.c.appBecomeActive();
                this.e = null;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private boolean a(Activity activity, boolean z) {
        synchronized (this.b) {
            Iterator<WeakReference<Activity>> it = this.g.iterator();
            while (it.hasNext()) {
                if (it.next().get() == activity) {
                    if (z) {
                        it.remove();
                    }
                    return false;
                }
            }
            return true;
        }
    }

    public static Object b(Object obj) {
        if (obj == null) {
            return JSONObject.NULL;
        }
        if ((obj instanceof JSONArray) || (obj instanceof JSONObject) || obj.equals(JSONObject.NULL)) {
            return obj;
        }
        if (obj instanceof Collection) {
            return new JSONArray((Collection) obj);
        }
        if (obj.getClass().isArray()) {
            return a(obj);
        }
        if (obj instanceof Map) {
            return new JSONObject((Map) obj);
        }
        if (!(obj instanceof Boolean) && !(obj instanceof Byte) && !(obj instanceof Character) && !(obj instanceof Double) && !(obj instanceof Float) && !(obj instanceof Integer) && !(obj instanceof Long) && !(obj instanceof Short) && !(obj instanceof String)) {
            if (obj.getClass().getPackage().getName().startsWith("java.")) {
                return obj.toString();
            }
            return null;
        }
        return obj;
    }

    String a() {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        WeakReference<Activity> weakReference = this.f;
        if (weakReference != null) {
            try {
                Intent intent = weakReference.get().getIntent();
                if (intent != null) {
                    String dataString = intent.getDataString();
                    if (!TextUtils.isEmpty(dataString)) {
                        jSONObject.put(ImagesContract.URL, dataString);
                    }
                    Bundle extras = intent.getExtras();
                    if (extras != null) {
                        for (String str : extras.keySet()) {
                            Object obj = extras.get(str);
                            Object b = b(obj);
                            if (b != null && b != JSONObject.NULL) {
                                jSONObject2.put(str, b(obj));
                            }
                        }
                        jSONObject.put("data", jSONObject2);
                    }
                }
            } catch (Exception unused) {
                return jSONObject.toString();
            }
        }
        return jSONObject.toString();
    }

    public void a(JSONObject jSONObject) {
        this.c.autoTrack("ta_app_crash", jSONObject);
        this.c.autoTrack("ta_app_end", new JSONObject());
        this.h = false;
        this.c.flush();
    }

    public void a(boolean z) {
        this.h = z;
    }

    boolean a(Context context) {
        try {
            Resources resources = context.getResources();
            return resources.getBoolean(resources.getIdentifier("TAEnableBackgroundStartEvent", "bool", context.getPackageName()));
        } catch (Exception unused) {
            return false;
        }
    }

    public void b() {
        synchronized (this.b) {
            if (this.d.booleanValue()) {
                this.c.mSessionManager.b();
                if (this.c.isAutoTrackEnabled()) {
                    try {
                        if (!this.c.isAutoTrackEventTypeIgnored(ThinkingAnalyticsSDK.AutoTrackEventType.APP_START) && (p.e(this.c.mConfig.mContext) || a(this.c.mConfig.mContext))) {
                            new Timer().schedule(new C0010b(this.c.mCalibratedTimeManager.a()), 100L);
                        }
                    } catch (Exception e) {
                        TDLog.i("ThinkingAnalytics.ThinkingDataActivityLifecycleCallbacks", e);
                    }
                }
            }
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
        TDLog.i("ThinkingAnalytics.ThinkingDataActivityLifecycleCallbacks", "onActivityCreated");
        this.f = new WeakReference<>(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        synchronized (this.b) {
            if (a(activity, false)) {
                TDLog.i("ThinkingAnalytics.ThinkingDataActivityLifecycleCallbacks", "onActivityPaused: the SDK was initialized after the onActivityStart of " + activity);
                this.g.add(new WeakReference<>(activity));
                if (this.g.size() == 1) {
                    a(activity, this.c.getAutoTrackStartTime());
                    this.c.flush();
                }
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r6v0, resolved type: android.app.Activity */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        String url;
        ThinkingAnalyticsSDK thinkingAnalyticsSDK;
        synchronized (this.b) {
            if (a(activity, false)) {
                TDLog.i("ThinkingAnalytics.ThinkingDataActivityLifecycleCallbacks", "onActivityResumed: the SDK was initialized after the onActivityStart of " + activity);
                this.g.add(new WeakReference<>(activity));
                if (this.g.size() == 1) {
                    a(activity, this.c.getAutoTrackStartTime());
                    this.c.flush();
                }
            }
        }
        try {
            boolean z = !this.c.isActivityAutoTrackAppViewScreenIgnored(activity.getClass());
            if (this.c.isAutoTrackEnabled() && z && !this.c.isAutoTrackEventTypeIgnored(ThinkingAnalyticsSDK.AutoTrackEventType.APP_VIEW_SCREEN)) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    if (!TDPresetProperties.disableList.contains("#screen_name")) {
                        jSONObject.put("#screen_name", activity.getClass().getCanonicalName());
                    }
                    p.a(jSONObject, activity);
                    if (activity instanceof ScreenAutoTracker) {
                        ScreenAutoTracker screenAutoTracker = (ScreenAutoTracker) activity;
                        url = screenAutoTracker.getScreenUrl();
                        JSONObject trackProperties = screenAutoTracker.getTrackProperties();
                        if (trackProperties == null || !f.a(trackProperties)) {
                            TDLog.d("ThinkingAnalytics.ThinkingDataActivityLifecycleCallbacks", "invalid properties: " + trackProperties);
                        } else {
                            p.a(trackProperties, jSONObject, this.c.mConfig.getDefaultTimeZone());
                        }
                        thinkingAnalyticsSDK = this.c;
                    } else {
                        ThinkingDataAutoTrackAppViewScreenUrl thinkingDataAutoTrackAppViewScreenUrl = (ThinkingDataAutoTrackAppViewScreenUrl) activity.getClass().getAnnotation(ThinkingDataAutoTrackAppViewScreenUrl.class);
                        if (thinkingDataAutoTrackAppViewScreenUrl == null || !(TextUtils.isEmpty(thinkingDataAutoTrackAppViewScreenUrl.appId()) || this.c.getToken().equals(thinkingDataAutoTrackAppViewScreenUrl.appId()))) {
                            if (this.c.isIgnoreAppViewInExtPackage()) {
                                return;
                            }
                            this.c.autoTrack("ta_app_view", jSONObject);
                            return;
                        } else {
                            url = thinkingDataAutoTrackAppViewScreenUrl.url();
                            if (TextUtils.isEmpty(url)) {
                                url = activity.getClass().getCanonicalName();
                            }
                            thinkingAnalyticsSDK = this.c;
                        }
                    }
                    thinkingAnalyticsSDK.trackViewScreenInternal(url, jSONObject);
                } catch (Exception e) {
                    TDLog.i("ThinkingAnalytics.ThinkingDataActivityLifecycleCallbacks", e);
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        TDLog.i("ThinkingAnalytics.ThinkingDataActivityLifecycleCallbacks", "onActivityStarted");
        this.f = new WeakReference<>(activity);
        try {
            synchronized (this.b) {
                if (this.g.size() == 0) {
                    a(activity, (cn.thinkingdata.analytics.utils.d) null);
                }
                if (a(activity, false)) {
                    this.g.add(new WeakReference<>(activity));
                } else {
                    TDLog.w("ThinkingAnalytics.ThinkingDataActivityLifecycleCallbacks", "Unexpected state. The activity might not be stopped correctly: " + activity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        ThinkingAnalyticsSDK thinkingAnalyticsSDK;
        String str;
        TDLog.i("ThinkingAnalytics.ThinkingDataActivityLifecycleCallbacks", "onActivityStopped");
        try {
            synchronized (this.b) {
                if (a(activity, true)) {
                    TDLog.i("ThinkingAnalytics.ThinkingDataActivityLifecycleCallbacks", "onActivityStopped: the SDK might be initialized after the onActivityStart of " + activity);
                    return;
                }
                if (this.g.size() == 0) {
                    this.f = null;
                    if (this.h) {
                        try {
                            this.c.appEnterBackground();
                            this.a = true;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (this.c.isAutoTrackEnabled()) {
                            JSONObject jSONObject = new JSONObject();
                            if (!this.c.isAutoTrackEventTypeIgnored(ThinkingAnalyticsSDK.AutoTrackEventType.APP_END)) {
                                try {
                                    try {
                                        p.a(jSONObject, activity);
                                        thinkingAnalyticsSDK = this.c;
                                        str = "ta_app_end";
                                    } catch (Exception e2) {
                                        TDLog.i("ThinkingAnalytics.ThinkingDataActivityLifecycleCallbacks", e2);
                                        thinkingAnalyticsSDK = this.c;
                                        str = "ta_app_end";
                                    }
                                    thinkingAnalyticsSDK.autoTrack(str, jSONObject);
                                    this.h = false;
                                } catch (Throwable th) {
                                    this.c.autoTrack("ta_app_end", jSONObject);
                                    this.h = false;
                                    throw th;
                                }
                            }
                        }
                        try {
                            this.e = new d(TimeUnit.SECONDS, SystemClock.elapsedRealtime());
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    }
                    this.c.flush();
                }
            }
        } catch (Exception e4) {
            e4.printStackTrace();
        }
    }
}
