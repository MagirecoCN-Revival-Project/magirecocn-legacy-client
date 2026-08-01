package com.google.android.gms.internal.measurement;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.adjust.sdk.Constants;
import kotlinx.coroutines.DebugKt;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzie implements Application.ActivityLifecycleCallbacks {
    private final /* synthetic */ zzhl zzaog;

    private zzie(zzhl zzhlVar) {
        this.zzaog = zzhlVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public /* synthetic */ zzie(zzhl zzhlVar, zzhm zzhmVar) {
        this(zzhlVar);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityCreated(Activity activity, Bundle bundle) {
        Uri data;
        try {
            this.zzaog.zzgf().zziz().log("onActivityCreated");
            Intent intent = activity.getIntent();
            if (intent != null && (data = intent.getData()) != null && data.isHierarchical()) {
                if (bundle == null) {
                    Bundle zza = this.zzaog.zzgc().zza(data);
                    this.zzaog.zzgc();
                    String str = zzkc.zzd(intent) ? "gs" : DebugKt.DEBUG_PROPERTY_VALUE_AUTO;
                    if (zza != null) {
                        this.zzaog.logEvent(str, "_cmp", zza);
                    }
                }
                String queryParameter = data.getQueryParameter(Constants.REFERRER);
                if (TextUtils.isEmpty(queryParameter)) {
                    return;
                }
                if (!(queryParameter.contains("gclid") && (queryParameter.contains("utm_campaign") || queryParameter.contains("utm_source") || queryParameter.contains("utm_medium") || queryParameter.contains("utm_term") || queryParameter.contains("utm_content")))) {
                    this.zzaog.zzgf().zziy().log("Activity created with data 'referrer' param without gclid and at least one utm field");
                    return;
                } else {
                    this.zzaog.zzgf().zziy().zzg("Activity created with referrer", queryParameter);
                    if (!TextUtils.isEmpty(queryParameter)) {
                        this.zzaog.setUserProperty(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_ldl", queryParameter);
                    }
                }
            }
        } catch (Exception e) {
            this.zzaog.zzgf().zzis().zzg("Throwable caught in onActivityCreated", e);
        }
        this.zzaog.zzfz().onActivityCreated(activity, bundle);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityDestroyed(Activity activity) {
        this.zzaog.zzfz().onActivityDestroyed(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityPaused(Activity activity) {
        this.zzaog.zzfz().onActivityPaused(activity);
        zzji zzgd = this.zzaog.zzgd();
        zzgd.zzge().zzc(new zzjm(zzgd, zzgd.zzbt().elapsedRealtime()));
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityResumed(Activity activity) {
        this.zzaog.zzfz().onActivityResumed(activity);
        zzji zzgd = this.zzaog.zzgd();
        zzgd.zzge().zzc(new zzjl(zzgd, zzgd.zzbt().elapsedRealtime()));
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        this.zzaog.zzfz().onActivitySaveInstanceState(activity, bundle);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStopped(Activity activity) {
    }
}
