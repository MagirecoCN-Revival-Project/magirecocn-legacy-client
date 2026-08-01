package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzfs {
    private GoogleAnalytics zza;
    private final Context zzb;
    private Tracker zzc;

    public zzfs(Context context) {
        this.zzb = context;
    }

    private final synchronized void zzb(String str) {
        if (this.zza == null) {
            GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(this.zzb);
            this.zza = googleAnalytics;
            googleAnalytics.setLogger(new zzfr());
            this.zzc = this.zza.newTracker("_GTM_DEFAULT_TRACKER_");
        }
    }

    public final Tracker zza(String str) {
        zzb("_GTM_DEFAULT_TRACKER_");
        return this.zzc;
    }
}
