package com.google.android.gms.tagmanager;

import java.util.Map;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzt extends zzbu {
    private static final String zza = com.google.android.gms.internal.gtm.zza.CONSTANT.toString();
    private static final String zzb = com.google.android.gms.internal.gtm.zzb.VALUE.toString();

    public zzt() {
        super(zza, zzb);
    }

    public static String zzc() {
        return zza;
    }

    public static String zzd() {
        return zzb;
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final com.google.android.gms.internal.gtm.zzak zza(Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        return map.get(zzb);
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final boolean zzb() {
        return true;
    }
}
