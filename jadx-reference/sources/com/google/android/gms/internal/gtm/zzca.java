package com.google.android.gms.internal.gtm;

import android.content.ComponentName;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzca implements Runnable {
    final /* synthetic */ ComponentName zza;
    final /* synthetic */ zzcb zzb;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzca(zzcb zzcbVar, ComponentName componentName) {
        this.zzb = zzcbVar;
        this.zza = componentName;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzcc.zzb(this.zzb.zza, this.zza);
    }
}
