package com.google.android.gms.tagmanager;

import java.util.Map;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzfq extends zzbu {
    private static final String zza = com.google.android.gms.internal.gtm.zza.TIME.toString();

    public zzfq() {
        super(zza, new String[0]);
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final com.google.android.gms.internal.gtm.zzak zza(Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        return zzfv.zzc(Long.valueOf(System.currentTimeMillis()));
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final boolean zzb() {
        return false;
    }
}
