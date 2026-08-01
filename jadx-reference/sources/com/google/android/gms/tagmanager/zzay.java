package com.google.android.gms.tagmanager;

import java.util.Map;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzay extends zzbu {
    private static final String zza = com.google.android.gms.internal.gtm.zza.CUSTOM_VAR.toString();
    private static final String zzb = com.google.android.gms.internal.gtm.zzb.NAME.toString();
    private static final String zzc = com.google.android.gms.internal.gtm.zzb.DEFAULT_VALUE.toString();
    private final DataLayer zzd;

    public zzay(DataLayer dataLayer) {
        super(zza, zzb);
        this.zzd = dataLayer;
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final com.google.android.gms.internal.gtm.zzak zza(Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        Object obj = this.zzd.get(zzfv.zzn(zzfv.zzl(map.get(zzb))));
        if (obj == null) {
            com.google.android.gms.internal.gtm.zzak zzakVar = map.get(zzc);
            return zzakVar != null ? zzakVar : zzfv.zzb();
        }
        return zzfv.zzc(obj);
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final boolean zzb() {
        return false;
    }
}
