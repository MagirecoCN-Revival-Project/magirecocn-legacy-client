package com.google.android.gms.internal.gtm;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzbl implements Runnable {
    final /* synthetic */ int zza;
    final /* synthetic */ zzbq zzb;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzbl(zzbq zzbqVar, int i) {
        this.zzb = zzbqVar;
        this.zza = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzck zzckVar;
        zzckVar = this.zzb.zza;
        zzckVar.zzZ(this.zza * 1000);
    }
}
