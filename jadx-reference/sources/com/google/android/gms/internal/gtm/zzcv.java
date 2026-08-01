package com.google.android.gms.internal.gtm;

import android.os.Looper;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzcv implements Runnable {
    final /* synthetic */ zzcw zza;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzcv(zzcw zzcwVar) {
        this.zza = zzcwVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzbv zzbvVar;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            zzbvVar = this.zza.zzb;
            zzbvVar.zzd().zzi(this);
            return;
        }
        boolean zzh = this.zza.zzh();
        this.zza.zzd = 0L;
        if (zzh) {
            this.zza.zza();
        }
    }
}
