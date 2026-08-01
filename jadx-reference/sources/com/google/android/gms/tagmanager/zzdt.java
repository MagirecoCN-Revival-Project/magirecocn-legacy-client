package com.google.android.gms.tagmanager;

import android.os.Build;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzdt extends zzbu {
    private static final String zza = com.google.android.gms.internal.gtm.zza.OS_VERSION.toString();

    public zzdt() {
        super(zza, new String[0]);
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final com.google.android.gms.internal.gtm.zzak zza(Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        return zzfv.zzc(Build.VERSION.RELEASE);
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final boolean zzb() {
        return true;
    }
}
