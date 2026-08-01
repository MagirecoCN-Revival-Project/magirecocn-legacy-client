package com.google.android.gms.internal.gtm;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzcf extends zzbs {
    private final zzav zza;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzcf(zzbv zzbvVar) {
        super(zzbvVar);
        this.zza = new zzav();
    }

    public final zzav zza() {
        zzW();
        return this.zza;
    }

    @Override // com.google.android.gms.internal.gtm.zzbs
    protected final void zzd() {
        zzq().zzc().zzc(this.zza);
        zzft zzB = zzB();
        zzB.zzW();
        String str = zzB.zzb;
        if (str != null) {
            this.zza.zzk(str);
        }
        zzB.zzW();
        String str2 = zzB.zza;
        if (str2 != null) {
            this.zza.zzl(str2);
        }
    }
}
