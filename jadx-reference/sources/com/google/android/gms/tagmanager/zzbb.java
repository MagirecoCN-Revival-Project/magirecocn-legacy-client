package com.google.android.gms.tagmanager;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzbb implements Runnable {
    final /* synthetic */ String zza;
    final /* synthetic */ zzbe zzb;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzbb(zzbe zzbeVar, String str) {
        this.zzb = zzbeVar;
        this.zza = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzbe.zzg(this.zzb, this.zza);
    }
}
