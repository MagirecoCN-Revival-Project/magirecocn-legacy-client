package com.google.android.gms.tagmanager;

import android.content.Context;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzcb implements Runnable {
    final /* synthetic */ long zza;
    final /* synthetic */ String zzb;
    final /* synthetic */ zzcc zzc;
    final /* synthetic */ zzcc zzd;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzcb(zzcc zzccVar, zzcc zzccVar2, long j, String str, byte[] bArr) {
        this.zzc = zzccVar;
        this.zzd = zzccVar2;
        this.zza = j;
        this.zzb = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzcd zzcdVar;
        zzcd zzcdVar2;
        Context context;
        zzcdVar = this.zzc.zze;
        if (zzcdVar == null) {
            zzff zzg = zzff.zzg();
            context = this.zzc.zzf;
            zzg.zzl(context, this.zzd);
            this.zzc.zze = zzg.zzf();
        }
        zzcdVar2 = this.zzc.zze;
        zzcdVar2.zzb(this.zza, this.zzb);
    }
}
