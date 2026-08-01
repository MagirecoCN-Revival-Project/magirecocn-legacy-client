package com.google.android.gms.tagmanager;

import java.util.Map;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
abstract class zzfl extends zzdy {
    public zzfl(String str) {
        super(str);
    }

    protected abstract boolean zzc(String str, String str2, Map<String, com.google.android.gms.internal.gtm.zzak> map);

    @Override // com.google.android.gms.tagmanager.zzdy
    protected final boolean zzd(com.google.android.gms.internal.gtm.zzak zzakVar, com.google.android.gms.internal.gtm.zzak zzakVar2, Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        String zzn = zzfv.zzn(zzfv.zzl(zzakVar));
        String zzn2 = zzfv.zzn(zzfv.zzl(zzakVar2));
        if (zzn == zzfv.zzm() || zzn2 == zzfv.zzm()) {
            return false;
        }
        return zzc(zzn, zzn2, map);
    }
}
