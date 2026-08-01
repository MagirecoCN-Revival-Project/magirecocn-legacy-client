package com.google.android.gms.tagmanager;

import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzbt extends zzbu {
    private static final String zza = com.google.android.gms.internal.gtm.zza.EVENT.toString();
    private final zzeu zzb;

    public zzbt(zzeu zzeuVar) {
        super(zza, new String[0]);
        this.zzb = zzeuVar;
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final com.google.android.gms.internal.gtm.zzak zza(Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        String zzb = this.zzb.zzb();
        if (zzb == null) {
            return zzfv.zzb();
        }
        return zzfv.zzc(zzb);
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final boolean zzb() {
        return false;
    }
}
