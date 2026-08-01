package com.google.android.gms.tagmanager;

import java.util.Map;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
abstract class zzft extends zzbu {
    public zzft(String str, String... strArr) {
        super(str, strArr);
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final com.google.android.gms.internal.gtm.zzak zza(Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        zzc(map);
        return zzfv.zzb();
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public boolean zzb() {
        return false;
    }

    public abstract void zzc(Map<String, com.google.android.gms.internal.gtm.zzak> map);
}
