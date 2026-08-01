package com.google.android.gms.internal.gtm;

import android.os.Handler;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzfl implements zzcz {
    final /* synthetic */ Runnable zza;
    final /* synthetic */ zzfn zzb;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzfl(zzfn zzfnVar, Runnable runnable) {
        this.zzb = zzfnVar;
        this.zza = runnable;
    }

    @Override // com.google.android.gms.internal.gtm.zzcz
    public final void zza(Throwable th) {
        Handler handler;
        handler = this.zzb.zzb;
        handler.post(this.zza);
    }
}
