package com.google.android.gms.tagmanager;

import com.google.android.gms.analytics.Logger;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzfr implements Logger {
    @Override // com.google.android.gms.analytics.Logger
    public final void error(Exception exc) {
        zzdh.zzb("", exc);
    }

    @Override // com.google.android.gms.analytics.Logger
    public final int getLogLevel() {
        int i = zzdh.zza;
        if (i == 2) {
            return 0;
        }
        if (i == 3 || i == 4) {
            return 1;
        }
        return i != 5 ? 3 : 2;
    }

    @Override // com.google.android.gms.analytics.Logger
    public final void info(String str) {
        zzdh.zzb.zzb(str);
    }

    @Override // com.google.android.gms.analytics.Logger
    public final void setLogLevel(int i) {
        zzdh.zzc("GA uses GTM logger. Please use TagManager.setLogLevel(int) instead.");
    }

    @Override // com.google.android.gms.analytics.Logger
    public final void verbose(String str) {
        zzdh.zzb.zzd(str);
    }

    @Override // com.google.android.gms.analytics.Logger
    public final void warn(String str) {
        zzdh.zzc(str);
    }

    @Override // com.google.android.gms.analytics.Logger
    public final void error(String str) {
        zzdh.zza(str);
    }
}
