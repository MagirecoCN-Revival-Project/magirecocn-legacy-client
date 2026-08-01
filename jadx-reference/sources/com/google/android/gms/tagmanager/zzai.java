package com.google.android.gms.tagmanager;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzai implements zzy {
    final /* synthetic */ zzal zza;

    /* JADX DEBUG: Marked for inline */
    /* JADX DEBUG: Method not inlined, still used in: [com.google.android.gms.tagmanager.zzal.<init>(android.content.Context, com.google.android.gms.tagmanager.TagManager, android.os.Looper, java.lang.String, int, com.google.android.gms.tagmanager.zzap):void] */
    /* JADX INFO: Access modifiers changed from: package-private */
    public /* synthetic */ zzai(zzal zzalVar, zzah zzahVar) {
        this.zza = zzalVar;
    }

    @Override // com.google.android.gms.tagmanager.zzy
    public final String zza() {
        return this.zza.zzh();
    }

    @Override // com.google.android.gms.tagmanager.zzy
    public final void zzb() {
        if (zzal.zzg(this.zza).zza()) {
            zzal.zzi(this.zza, 0L);
        }
    }

    @Override // com.google.android.gms.tagmanager.zzy
    public final void zzc(String str) {
        this.zza.zzo(str);
    }
}
