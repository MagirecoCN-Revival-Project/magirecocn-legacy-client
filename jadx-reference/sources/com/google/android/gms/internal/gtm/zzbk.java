package com.google.android.gms.internal.gtm;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzbk implements Runnable {
    final /* synthetic */ String zza;
    final /* synthetic */ Runnable zzb;
    final /* synthetic */ zzbq zzc;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzbk(zzbq zzbqVar, String str, Runnable runnable) {
        this.zzc = zzbqVar;
        this.zza = str;
        this.zzb = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzck zzckVar;
        zzckVar = this.zzc.zza;
        zzckVar.zzn(this.zza);
        this.zzb.run();
    }
}
