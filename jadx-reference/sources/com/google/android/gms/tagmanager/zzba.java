package com.google.android.gms.tagmanager;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzba implements Runnable {
    final /* synthetic */ zzaw zza;
    final /* synthetic */ zzbe zzb;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzba(zzbe zzbeVar, zzaw zzawVar) {
        this.zzb = zzbeVar;
        this.zza = zzawVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zza.zza(zzbe.zzf(this.zzb));
    }
}
