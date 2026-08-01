package com.google.android.gms.measurement;

import android.content.Context;
import android.os.Bundle;
import androidx.collection.ArrayMap;
import com.android.vending.expansion.zipfile.APEZProvider;
import com.google.android.gms.common.api.internal.GoogleServices;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzgm;
import com.google.android.gms.internal.measurement.zzif;
import com.google.android.gms.internal.measurement.zzjz;
import com.google.android.gms.internal.measurement.zzkc;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import java.util.Map;

@Deprecated
/* loaded from: classes.dex */
public class AppMeasurement {
    public static final String CRASH_ORIGIN = "crash";
    public static final String FCM_ORIGIN = "fcm";
    private final zzgm zzacw;

    /* loaded from: classes.dex */
    public static class ConditionalUserProperty {
        public boolean mActive;
        public String mAppId;
        public long mCreationTimestamp;
        public String mExpiredEventName;
        public Bundle mExpiredEventParams;
        public String mName;
        public String mOrigin;
        public long mTimeToLive;
        public String mTimedOutEventName;
        public Bundle mTimedOutEventParams;
        public String mTriggerEventName;
        public long mTriggerTimeout;
        public String mTriggeredEventName;
        public Bundle mTriggeredEventParams;
        public long mTriggeredTimestamp;
        public Object mValue;

        public ConditionalUserProperty() {
        }

        public ConditionalUserProperty(ConditionalUserProperty conditionalUserProperty) {
            Preconditions.checkNotNull(conditionalUserProperty);
            this.mAppId = conditionalUserProperty.mAppId;
            this.mOrigin = conditionalUserProperty.mOrigin;
            this.mCreationTimestamp = conditionalUserProperty.mCreationTimestamp;
            this.mName = conditionalUserProperty.mName;
            Object obj = conditionalUserProperty.mValue;
            if (obj != null) {
                Object zzf = zzkc.zzf(obj);
                this.mValue = zzf;
                if (zzf == null) {
                    this.mValue = conditionalUserProperty.mValue;
                }
            }
            this.mActive = conditionalUserProperty.mActive;
            this.mTriggerEventName = conditionalUserProperty.mTriggerEventName;
            this.mTriggerTimeout = conditionalUserProperty.mTriggerTimeout;
            this.mTimedOutEventName = conditionalUserProperty.mTimedOutEventName;
            if (conditionalUserProperty.mTimedOutEventParams != null) {
                this.mTimedOutEventParams = new Bundle(conditionalUserProperty.mTimedOutEventParams);
            }
            this.mTriggeredEventName = conditionalUserProperty.mTriggeredEventName;
            if (conditionalUserProperty.mTriggeredEventParams != null) {
                this.mTriggeredEventParams = new Bundle(conditionalUserProperty.mTriggeredEventParams);
            }
            this.mTriggeredTimestamp = conditionalUserProperty.mTriggeredTimestamp;
            this.mTimeToLive = conditionalUserProperty.mTimeToLive;
            this.mExpiredEventName = conditionalUserProperty.mExpiredEventName;
            if (conditionalUserProperty.mExpiredEventParams != null) {
                this.mExpiredEventParams = new Bundle(conditionalUserProperty.mExpiredEventParams);
            }
        }
    }

    /* loaded from: classes.dex */
    public static final class Event extends FirebaseAnalytics.Event {
        public static final String[] zzacx = {"app_clear_data", "app_exception", "app_remove", "app_upgrade", "app_install", "app_update", "firebase_campaign", "error", "first_open", "first_visit", "in_app_purchase", "notification_dismiss", "notification_foreground", "notification_open", "notification_receive", "os_update", "session_start", "user_engagement", "ad_exposure", "adunit_exposure", "ad_query", "ad_activeview", "ad_impression", "ad_click", "ad_reward", "screen_view", "ga_extra_parameter"};
        public static final String APP_EXCEPTION = "_ae";
        public static final String AD_REWARD = "_ar";
        public static final String[] zzacy = {"_cd", APP_EXCEPTION, "_ui", "_ug", "_in", "_au", "_cmp", "_err", "_f", "_v", "_iap", "_nd", "_nf", "_no", "_nr", "_ou", "_s", "_e", "_xa", "_xu", "_aq", "_aa", "_ai", "_ac", AD_REWARD, "_vs", "_ep"};

        private Event() {
        }

        public static String zzaj(String str) {
            return zzkc.zza(str, zzacx, zzacy);
        }
    }

    /* loaded from: classes.dex */
    public interface EventInterceptor {
        void interceptEvent(String str, String str2, Bundle bundle, long j);
    }

    /* loaded from: classes.dex */
    public interface OnEventListener {
        void onEvent(String str, String str2, Bundle bundle, long j);
    }

    /* loaded from: classes.dex */
    public static final class Param extends FirebaseAnalytics.Param {
        public static final String FATAL = "fatal";
        public static final String TIMESTAMP = "timestamp";
        public static final String TYPE = "type";
        public static final String[] zzacz = {"firebase_conversion", "engagement_time_msec", "exposure_time", "ad_event_id", "ad_unit_id", "firebase_error", "firebase_error_value", "firebase_error_length", "firebase_event_origin", "firebase_screen", "firebase_screen_class", "firebase_screen_id", "firebase_previous_screen", "firebase_previous_class", "firebase_previous_id", "message_device_time", "message_id", "message_name", "message_time", "previous_app_version", "previous_os_version", "topic", "update_with_analytics", "previous_first_open_count", "system_app", "system_app_update", "previous_install_count", "ga_event_id", "ga_extra_params_ct", "ga_group_name", "ga_list_length", "ga_index", "ga_event_name", "campaign_info_source", "deferred_analytics_collection"};
        public static final String[] zzada = {"_c", "_et", "_xt", "_aeid", "_ai", "_err", "_ev", "_el", "_o", "_sn", "_sc", "_si", "_pn", "_pc", "_pi", "_ndt", "_nmid", "_nmn", "_nmt", "_pv", "_po", "_nt", "_uwa", "_pfo", "_sys", "_sysu", "_pin", "_eid", "_epc", "_gn", "_ll", "_i", "_en", "_cis", "_dac"};

        private Param() {
        }

        public static String zzaj(String str) {
            return zzkc.zza(str, zzacz, zzada);
        }
    }

    /* loaded from: classes.dex */
    public static final class UserProperty extends FirebaseAnalytics.UserProperty {
        public static final String[] zzadb = {"firebase_last_notification", "first_open_time", "first_visit_time", "last_deep_link_referrer", "user_id", "first_open_after_install", "lifetime_user_engagement"};
        public static final String FIREBASE_LAST_NOTIFICATION = "_ln";
        public static final String[] zzadc = {FIREBASE_LAST_NOTIFICATION, "_fot", "_fvt", "_ldl", APEZProvider.FILEID, "_fi", "_lte"};

        private UserProperty() {
        }

        public static String zzaj(String str) {
            return zzkc.zza(str, zzadb, zzadc);
        }
    }

    public AppMeasurement(zzgm zzgmVar) {
        Preconditions.checkNotNull(zzgmVar);
        this.zzacw = zzgmVar;
    }

    @Deprecated
    public static AppMeasurement getInstance(Context context) {
        return zzgm.zza(context, null, null).zzjy();
    }

    public void beginAdUnitExposure(String str) {
        this.zzacw.zzfu().beginAdUnitExposure(str);
    }

    public void clearConditionalUserProperty(String str, String str2, Bundle bundle) {
        this.zzacw.zzfv().clearConditionalUserProperty(str, str2, bundle);
    }

    protected void clearConditionalUserPropertyAs(String str, String str2, String str3, Bundle bundle) {
        this.zzacw.zzfv().clearConditionalUserPropertyAs(str, str2, str3, bundle);
    }

    public void endAdUnitExposure(String str) {
        this.zzacw.zzfu().endAdUnitExposure(str);
    }

    public long generateEventId() {
        return this.zzacw.zzgc().zzlk();
    }

    public String getAppInstanceId() {
        return this.zzacw.zzfv().zzjh();
    }

    public Boolean getBoolean() {
        return this.zzacw.zzfv().zzkf();
    }

    public List<ConditionalUserProperty> getConditionalUserProperties(String str, String str2) {
        return this.zzacw.zzfv().getConditionalUserProperties(str, str2);
    }

    protected List<ConditionalUserProperty> getConditionalUserPropertiesAs(String str, String str2, String str3) {
        return this.zzacw.zzfv().getConditionalUserPropertiesAs(str, str2, str3);
    }

    public String getCurrentScreenClass() {
        zzif zzkl = this.zzacw.zzfz().zzkl();
        if (zzkl != null) {
            return zzkl.zzaos;
        }
        return null;
    }

    public String getCurrentScreenName() {
        zzif zzkl = this.zzacw.zzfz().zzkl();
        if (zzkl != null) {
            return zzkl.zzul;
        }
        return null;
    }

    public Double getDouble() {
        return this.zzacw.zzfv().zzki();
    }

    public String getGmpAppId() {
        if (this.zzacw.zzka() != null) {
            return this.zzacw.zzka();
        }
        try {
            return GoogleServices.getGoogleAppId();
        } catch (IllegalStateException e) {
            this.zzacw.zzgf().zzis().zzg("getGoogleAppId failed with exception", e);
            return null;
        }
    }

    public Integer getInteger() {
        return this.zzacw.zzfv().zzkh();
    }

    public Long getLong() {
        return this.zzacw.zzfv().zzkg();
    }

    public int getMaxUserProperties(String str) {
        this.zzacw.zzfv();
        Preconditions.checkNotEmpty(str);
        return 25;
    }

    public String getString() {
        return this.zzacw.zzfv().zzhq();
    }

    protected Map<String, Object> getUserProperties(String str, String str2, boolean z) {
        return this.zzacw.zzfv().getUserProperties(str, str2, z);
    }

    public Map<String, Object> getUserProperties(boolean z) {
        List<zzjz> zzj = this.zzacw.zzfv().zzj(z);
        ArrayMap arrayMap = new ArrayMap(zzj.size());
        for (zzjz zzjzVar : zzj) {
            arrayMap.put(zzjzVar.name, zzjzVar.getValue());
        }
        return arrayMap;
    }

    protected Map<String, Object> getUserPropertiesAs(String str, String str2, String str3, boolean z) {
        return this.zzacw.zzfv().getUserPropertiesAs(str, str2, str3, z);
    }

    public final void logEvent(String str, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        this.zzacw.zzfv().zza("app", str, bundle, true);
    }

    public void logEventInternal(String str, String str2, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        this.zzacw.zzfv().logEvent(str, str2, bundle);
    }

    public void logEventInternalNoInterceptor(String str, String str2, Bundle bundle, long j) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        this.zzacw.zzfv().zza(str, str2, bundle, j);
    }

    public void registerOnMeasurementEventListener(OnEventListener onEventListener) {
        this.zzacw.zzfv().registerOnMeasurementEventListener(onEventListener);
    }

    public void setConditionalUserProperty(ConditionalUserProperty conditionalUserProperty) {
        this.zzacw.zzfv().setConditionalUserProperty(conditionalUserProperty);
    }

    protected void setConditionalUserPropertyAs(ConditionalUserProperty conditionalUserProperty) {
        this.zzacw.zzfv().setConditionalUserPropertyAs(conditionalUserProperty);
    }

    public void setEventInterceptor(EventInterceptor eventInterceptor) {
        this.zzacw.zzfv().setEventInterceptor(eventInterceptor);
    }

    @Deprecated
    public void setMeasurementEnabled(boolean z) {
        this.zzacw.zzfv().setMeasurementEnabled(z);
    }

    public final void setMinimumSessionDuration(long j) {
        this.zzacw.zzfv().setMinimumSessionDuration(j);
    }

    public final void setSessionTimeoutDuration(long j) {
        this.zzacw.zzfv().setSessionTimeoutDuration(j);
    }

    public final void setUserProperty(String str, String str2) {
        int zzcd = this.zzacw.zzgc().zzcd(str);
        if (zzcd == 0) {
            setUserPropertyInternal("app", str, str2);
        } else {
            this.zzacw.zzgc();
            this.zzacw.zzgc().zza(zzcd, "_ev", zzkc.zza(str, 24, true), str != null ? str.length() : 0);
        }
    }

    public void setUserPropertyInternal(String str, String str2, Object obj) {
        this.zzacw.zzfv().setUserProperty(str, str2, obj);
    }

    public void unregisterOnMeasurementEventListener(OnEventListener onEventListener) {
        this.zzacw.zzfv().unregisterOnMeasurementEventListener(onEventListener);
    }
}
