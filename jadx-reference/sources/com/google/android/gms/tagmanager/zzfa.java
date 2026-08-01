package com.google.android.gms.tagmanager;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzfa implements Runnable {
    final /* synthetic */ zzff zza;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzfa(zzff zzffVar) {
        this.zza = zzffVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzcd zzcdVar;
        zzcdVar = this.zza.zzd;
        zzcdVar.zza();
    }
}
