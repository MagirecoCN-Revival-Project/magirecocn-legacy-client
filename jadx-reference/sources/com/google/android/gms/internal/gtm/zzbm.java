package com.google.android.gms.internal.gtm;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzbm implements Runnable {
    final /* synthetic */ zzex zza;
    final /* synthetic */ zzbq zzb;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzbm(zzbq zzbqVar, zzex zzexVar) {
        this.zzb = zzbqVar;
        this.zza = zzexVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzck zzckVar;
        zzckVar = this.zzb.zza;
        zzckVar.zzj(this.zza);
    }
}
