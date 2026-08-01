package com.google.android.gms.internal.gtm;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzcx extends zzbs {
    /* JADX INFO: Access modifiers changed from: package-private */
    public zzcx(zzbv zzbvVar) {
        super(zzbvVar);
    }

    public final zzba zza() {
        zzW();
        return zzq().zzd();
    }

    public final String zzb() {
        zzW();
        zzba zza = zza();
        int i = zza.zza;
        int i2 = zza.zzb;
        StringBuilder sb = new StringBuilder(23);
        sb.append(i);
        sb.append("x");
        sb.append(i2);
        return sb.toString();
    }

    @Override // com.google.android.gms.internal.gtm.zzbs
    protected final void zzd() {
    }
}
