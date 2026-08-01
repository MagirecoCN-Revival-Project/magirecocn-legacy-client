package cn.thinkingdata.analytics;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import cn.thinkingdata.analytics.ThinkingAnalyticsSDK;
import cn.thinkingdata.analytics.utils.f;
import cn.thinkingdata.analytics.utils.k;
import cn.thinkingdata.analytics.utils.p;
import cn.thinkingdata.core.utils.TDLog;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class b extends ThinkingAnalyticsSDK {
    private final JSONObject a;
    private boolean b;

    /* loaded from: classes.dex */
    class a implements Runnable {
        final /* synthetic */ JSONObject a;

        a(JSONObject jSONObject) {
            this.a = jSONObject;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                JSONObject jSONObject = this.a;
                if (jSONObject != null && f.a(jSONObject)) {
                    p.a(this.a, b.this.a, b.this.mConfig.getDefaultTimeZone());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: cn.thinkingdata.analytics.b$b, reason: collision with other inner class name */
    /* loaded from: classes.dex */
    class RunnableC0005b implements Runnable {
        final /* synthetic */ String a;

        RunnableC0005b(String str) {
            this.a = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                if (this.a == null) {
                    return;
                }
                synchronized (b.this.a) {
                    b.this.a.remove(this.a);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* loaded from: classes.dex */
    class c implements Runnable {
        c() {
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (b.this.a) {
                Iterator<String> keys = b.this.a.keys();
                while (keys.hasNext()) {
                    keys.next();
                    keys.remove();
                }
            }
        }
    }

    /* loaded from: classes.dex */
    class d implements Runnable {
        final /* synthetic */ ThinkingAnalyticsSDK.TATrackStatus a;

        d(ThinkingAnalyticsSDK.TATrackStatus tATrackStatus) {
            this.a = tATrackStatus;
        }

        @Override // java.lang.Runnable
        public void run() {
            int i = e.a[this.a.ordinal()];
            if (i == 1) {
                b bVar = b.this;
                bVar.mMessages.a(bVar.getToken(), false);
                b.this.enableTracking(false);
                return;
            }
            if (i == 2) {
                b.this.b = true;
                b bVar2 = b.this;
                bVar2.mMessages.a(bVar2.getToken(), false);
                b.this.optOutTracking();
                return;
            }
            if (i == 3) {
                b.this.b = true;
                b bVar3 = b.this;
                bVar3.mMessages.a(bVar3.getToken(), true);
            } else {
                if (i != 4) {
                    return;
                }
                b.this.b = true;
                b bVar4 = b.this;
                bVar4.mMessages.a(bVar4.getToken(), false);
                b.this.flush();
            }
        }
    }

    /* loaded from: classes.dex */
    static /* synthetic */ class e {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[ThinkingAnalyticsSDK.TATrackStatus.values().length];
            a = iArr;
            try {
                iArr[ThinkingAnalyticsSDK.TATrackStatus.PAUSE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[ThinkingAnalyticsSDK.TATrackStatus.STOP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[ThinkingAnalyticsSDK.TATrackStatus.SAVE_ONLY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[ThinkingAnalyticsSDK.TATrackStatus.NORMAL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(TDConfig tDConfig) {
        super(tDConfig, true);
        this.a = new JSONObject();
        setStatusIdentifyId(getRandomID());
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void clearSuperProperties() {
        if (getStatusHasDisabled()) {
            return;
        }
        this.mTrackTaskManager.a(new c());
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void enableAutoTrack(List<ThinkingAnalyticsSDK.AutoTrackEventType> list) {
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void enableTracking(boolean z) {
        setStatusTrackStatus(z ? ThinkingAnalyticsSDK.TATrackStatus.NORMAL : ThinkingAnalyticsSDK.TATrackStatus.PAUSE);
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public String getDistinctId() {
        String statusIdentifyId = getStatusIdentifyId();
        return statusIdentifyId == null ? getRandomID() : statusIdentifyId;
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    String getLoginId() {
        return getStatusAccountId();
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public JSONObject getSuperProperties() {
        return this.a;
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public boolean hasOptOut() {
        return false;
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void identify(String str) {
        if (!TextUtils.isEmpty(str)) {
            setStatusIdentifyId(str);
            return;
        }
        TDLog.w("ThinkingAnalyticsSDK", "The identity cannot be empty.");
        if (this.mConfig.shouldThrowException()) {
            throw new k("distinct id cannot be empty");
        }
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void ignoreAutoTrackActivities(List<Class<?>> list) {
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void ignoreAutoTrackActivity(Class<?> cls) {
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void ignoreView(View view) {
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void ignoreViewType(Class cls) {
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public boolean isEnabled() {
        return !getStatusHasDisabled();
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void login(String str) {
        if (getStatusHasDisabled()) {
            return;
        }
        setStatusAccountId(str);
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void logout() {
        if (getStatusHasDisabled()) {
            return;
        }
        setStatusAccountId(null);
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void optInTracking() {
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void optOutTracking() {
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void optOutTrackingAndDeleteUser() {
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void setJsBridge(WebView webView) {
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void setJsBridgeForX5WebView(Object obj) {
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void setNetworkType(ThinkingAnalyticsSDK.ThinkingdataNetworkType thinkingdataNetworkType) {
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void setSuperProperties(JSONObject jSONObject) {
        if (getStatusHasDisabled()) {
            return;
        }
        this.mTrackTaskManager.a(new a(jSONObject));
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void setTrackStatus(ThinkingAnalyticsSDK.TATrackStatus tATrackStatus) {
        setStatusTrackStatus(tATrackStatus);
        this.mTrackTaskManager.a(new d(tATrackStatus));
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void setViewID(Dialog dialog, String str) {
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void setViewID(View view, String str) {
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void setViewProperties(View view, JSONObject jSONObject) {
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void trackFragmentAppViewScreen() {
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void trackViewScreen(Activity activity) {
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void trackViewScreen(Fragment fragment) {
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void trackViewScreen(Object obj) {
    }

    @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK
    public void unsetSuperProperty(String str) {
        if (getStatusHasDisabled()) {
            return;
        }
        this.mTrackTaskManager.a(new RunnableC0005b(str));
    }
}
