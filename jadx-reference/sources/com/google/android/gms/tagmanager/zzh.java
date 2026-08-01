package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzh extends zzbu {
    private static final String zza = com.google.android.gms.internal.gtm.zza.ADWORDS_CLICK_REFERRER.toString();
    private static final String zzb = com.google.android.gms.internal.gtm.zzb.COMPONENT.toString();
    private static final String zzc = com.google.android.gms.internal.gtm.zzb.CONVERSION_ID.toString();
    private final Context zzd;

    public zzh(Context context) {
        super(zza, zzc);
        this.zzd = context;
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final com.google.android.gms.internal.gtm.zzak zza(Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        com.google.android.gms.internal.gtm.zzak zzakVar = map.get(zzc);
        if (zzakVar == null) {
            return zzfv.zzb();
        }
        String zzn = zzfv.zzn(zzfv.zzl(zzakVar));
        com.google.android.gms.internal.gtm.zzak zzakVar2 = map.get(zzb);
        String zzn2 = zzakVar2 != null ? zzfv.zzn(zzfv.zzl(zzakVar2)) : null;
        Context context = this.zzd;
        String str = zzcx.zza.get(zzn);
        if (str == null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("gtm_click_referrers", 0);
            str = sharedPreferences != null ? sharedPreferences.getString(zzn, "") : "";
            zzcx.zza.put(zzn, str);
        }
        String zza2 = zzcx.zza(str, zzn2);
        return zza2 != null ? zzfv.zzc(zza2) : zzfv.zzb();
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final boolean zzb() {
        return true;
    }
}
