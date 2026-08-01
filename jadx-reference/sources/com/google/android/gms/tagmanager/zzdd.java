package com.google.android.gms.tagmanager;

import java.util.Map;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzdd extends zzdr {
    private static final String zza = com.google.android.gms.internal.gtm.zza.LESS_EQUALS.toString();

    public zzdd() {
        super(zza);
    }

    @Override // com.google.android.gms.tagmanager.zzdr
    protected final boolean zzc(zzfu zzfuVar, zzfu zzfuVar2, Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        return zzfuVar.compareTo(zzfuVar2) <= 0;
    }
}
