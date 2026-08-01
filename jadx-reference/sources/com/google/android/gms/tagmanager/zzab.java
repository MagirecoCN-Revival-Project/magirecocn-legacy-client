package com.google.android.gms.tagmanager;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzab implements zzy {
    final /* synthetic */ zzal zza;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzab(zzal zzalVar) {
        this.zza = zzalVar;
    }

    @Override // com.google.android.gms.tagmanager.zzy
    public final String zza() {
        return this.zza.zzh();
    }

    @Override // com.google.android.gms.tagmanager.zzy
    public final void zzb() {
        zzdh.zzc("Refresh ignored: container loaded as default only.");
    }

    @Override // com.google.android.gms.tagmanager.zzy
    public final void zzc(String str) {
        this.zza.zzo(str);
    }
}
