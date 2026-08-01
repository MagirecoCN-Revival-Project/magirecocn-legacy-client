package com.google.android.gms.tagmanager;

import android.content.Context;
import android.provider.Settings;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzdj extends zzbu {
    private static final String zza = com.google.android.gms.internal.gtm.zza.MOBILE_ADWORDS_UNIQUE_ID.toString();
    private final Context zzb;

    public zzdj(Context context) {
        super(zza, new String[0]);
        this.zzb = context;
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final com.google.android.gms.internal.gtm.zzak zza(Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        String string = Settings.Secure.getString(this.zzb.getContentResolver(), "android_id");
        return string == null ? zzfv.zzb() : zzfv.zzc(string);
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final boolean zzb() {
        return true;
    }
}
