package com.google.android.gms.tagmanager;

import java.util.Map;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzdi extends zzbu {
    private static final String zza = com.google.android.gms.internal.gtm.zza.LOWERCASE_STRING.toString();
    private static final String zzb = com.google.android.gms.internal.gtm.zzb.ARG0.toString();

    public zzdi() {
        super(zza, zzb);
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final com.google.android.gms.internal.gtm.zzak zza(Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        return zzfv.zzc(zzfv.zzn(zzfv.zzl(map.get(zzb))).toLowerCase());
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final boolean zzb() {
        return true;
    }
}
