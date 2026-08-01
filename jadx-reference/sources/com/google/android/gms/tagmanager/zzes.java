package com.google.android.gms.tagmanager;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzes {
    private final zzds<com.google.android.gms.internal.gtm.zzak> zza;
    private final com.google.android.gms.internal.gtm.zzak zzb;

    public zzes(zzds<com.google.android.gms.internal.gtm.zzak> zzdsVar, com.google.android.gms.internal.gtm.zzak zzakVar) {
        this.zza = zzdsVar;
        this.zzb = zzakVar;
    }

    public final int zza() {
        int zzX = this.zza.zza().zzX();
        com.google.android.gms.internal.gtm.zzak zzakVar = this.zzb;
        return zzX + (zzakVar == null ? 0 : zzakVar.zzX());
    }

    public final com.google.android.gms.internal.gtm.zzak zzb() {
        return this.zzb;
    }

    public final zzds<com.google.android.gms.internal.gtm.zzak> zzc() {
        return this.zza;
    }
}
