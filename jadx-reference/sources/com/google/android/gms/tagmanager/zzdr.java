package com.google.android.gms.tagmanager;

import java.util.Map;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
abstract class zzdr extends zzdy {
    public zzdr(String str) {
        super(str);
    }

    protected abstract boolean zzc(zzfu zzfuVar, zzfu zzfuVar2, Map<String, com.google.android.gms.internal.gtm.zzak> map);

    @Override // com.google.android.gms.tagmanager.zzdy
    protected final boolean zzd(com.google.android.gms.internal.gtm.zzak zzakVar, com.google.android.gms.internal.gtm.zzak zzakVar2, Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        zzfu zze = zzfv.zze(zzfv.zzl(zzakVar));
        zzfu zze2 = zzfv.zze(zzfv.zzl(zzakVar2));
        if (zze == zzfv.zzd() || zze2 == zzfv.zzd()) {
            return false;
        }
        return zzc(zze, zze2, map);
    }
}
