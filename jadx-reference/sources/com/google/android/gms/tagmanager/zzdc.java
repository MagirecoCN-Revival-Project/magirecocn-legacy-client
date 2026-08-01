package com.google.android.gms.tagmanager;

import java.util.Locale;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzdc extends zzbu {
    private static final String zza = com.google.android.gms.internal.gtm.zza.LANGUAGE.toString();

    public zzdc() {
        super(zza, new String[0]);
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final com.google.android.gms.internal.gtm.zzak zza(Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        Locale locale = Locale.getDefault();
        if (locale == null) {
            return zzfv.zzb();
        }
        String language = locale.getLanguage();
        if (language == null) {
            return zzfv.zzb();
        }
        return zzfv.zzc(language.toLowerCase());
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final boolean zzb() {
        return false;
    }
}
