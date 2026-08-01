package com.google.android.gms.tagmanager;

import android.os.Build;
import androidx.core.os.EnvironmentCompat;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzbj extends zzbu {
    private static final String zza = com.google.android.gms.internal.gtm.zza.DEVICE_NAME.toString();

    public zzbj() {
        super(zza, new String[0]);
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final com.google.android.gms.internal.gtm.zzak zza(Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        String str = Build.MANUFACTURER;
        String str2 = Build.MODEL;
        if (!str2.startsWith(str) && !str.equals(EnvironmentCompat.MEDIA_UNKNOWN)) {
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(str2).length());
            sb.append(str);
            sb.append(" ");
            sb.append(str2);
            str2 = sb.toString();
        }
        return zzfv.zzc(str2);
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final boolean zzb() {
        return true;
    }
}
