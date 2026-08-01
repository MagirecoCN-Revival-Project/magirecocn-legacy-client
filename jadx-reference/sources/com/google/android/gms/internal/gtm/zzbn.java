package com.google.android.gms.internal.gtm;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzbn implements Runnable {
    final /* synthetic */ zzbq zza;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzbn(zzbq zzbqVar) {
        this.zza = zzbqVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzck zzckVar;
        zzckVar = this.zza.zza;
        zzckVar.zzh();
    }
}
