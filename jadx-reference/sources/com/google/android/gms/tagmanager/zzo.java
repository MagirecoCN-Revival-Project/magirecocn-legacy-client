package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.Uri;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzo extends zzft {
    static final String zza;
    private static final String zzb;
    private static final String zzc;
    private static final String zzd;
    private static final String zze;
    private static final Set<String> zzf;
    private final zzn zzg;
    private final Context zzh;

    static {
        String zzaVar = com.google.android.gms.internal.gtm.zza.ARBITRARY_PIXEL.toString();
        zzb = zzaVar;
        zzc = com.google.android.gms.internal.gtm.zzb.URL.toString();
        zzd = com.google.android.gms.internal.gtm.zzb.ADDITIONAL_PARAMS.toString();
        zze = com.google.android.gms.internal.gtm.zzb.UNREPEATABLE.toString();
        StringBuilder sb = new StringBuilder(String.valueOf(zzaVar).length() + 17);
        sb.append("gtm_");
        sb.append(zzaVar);
        sb.append("_unrepeatable");
        zza = sb.toString();
        zzf = new HashSet();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzo(Context context) {
        super(zzb, zzc);
        zzm zzmVar = new zzm(context);
        this.zzg = zzmVar;
        this.zzh = context;
    }

    private final synchronized boolean zzd(String str) {
        Set<String> set = zzf;
        if (set.contains(str)) {
            return true;
        }
        if (!this.zzh.getSharedPreferences(zza, 0).contains(str)) {
            return false;
        }
        set.add(str);
        return true;
    }

    @Override // com.google.android.gms.tagmanager.zzft
    public final void zzc(Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        String str = zze;
        String zzn = map.get(str) != null ? zzfv.zzn(zzfv.zzl(map.get(str))) : null;
        if (zzn == null || !zzd(zzn)) {
            Uri.Builder buildUpon = Uri.parse(zzfv.zzn(zzfv.zzl(map.get(zzc)))).buildUpon();
            com.google.android.gms.internal.gtm.zzak zzakVar = map.get(zzd);
            if (zzakVar != null) {
                Object zzl = zzfv.zzl(zzakVar);
                if (!(zzl instanceof List)) {
                    String valueOf = String.valueOf(buildUpon.build().toString());
                    zzdh.zza(valueOf.length() != 0 ? "ArbitraryPixel: additional params not a list: not sending partial hit: ".concat(valueOf) : new String("ArbitraryPixel: additional params not a list: not sending partial hit: "));
                    return;
                }
                for (Object obj : (List) zzl) {
                    if (obj instanceof Map) {
                        for (Map.Entry entry : ((Map) obj).entrySet()) {
                            buildUpon.appendQueryParameter(entry.getKey().toString(), entry.getValue().toString());
                        }
                    } else {
                        String valueOf2 = String.valueOf(buildUpon.build().toString());
                        zzdh.zza(valueOf2.length() != 0 ? "ArbitraryPixel: additional params contains non-map: not sending partial hit: ".concat(valueOf2) : new String("ArbitraryPixel: additional params contains non-map: not sending partial hit: "));
                        return;
                    }
                }
            }
            String uri = buildUpon.build().toString();
            zzbh.zzb(((zzm) this.zzg).zza).zza(uri);
            String valueOf3 = String.valueOf(uri);
            zzdh.zzb.zzd(valueOf3.length() != 0 ? "ArbitraryPixel: url = ".concat(valueOf3) : new String("ArbitraryPixel: url = "));
            if (zzn != null) {
                synchronized (zzo.class) {
                    zzf.add(zzn);
                    zzfg.zza(this.zzh, zza, zzn, "true");
                }
            }
        }
    }
}
