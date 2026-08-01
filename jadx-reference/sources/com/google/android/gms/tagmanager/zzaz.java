package com.google.android.gms.tagmanager;

import java.util.List;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzaz implements Runnable {
    final /* synthetic */ List zza;
    final /* synthetic */ long zzb;
    final /* synthetic */ zzbe zzc;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzaz(zzbe zzbeVar, List list, long j) {
        this.zzc = zzbeVar;
        this.zza = list;
        this.zzb = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzc.zzl(this.zza, this.zzb);
    }
}
