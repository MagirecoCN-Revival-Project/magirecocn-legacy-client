package com.google.android.gms.tagmanager;

import java.util.Map;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzao extends zzfl {
    private static final String zza = com.google.android.gms.internal.gtm.zza.CONTAINS.toString();

    public zzao() {
        super(zza);
    }

    @Override // com.google.android.gms.tagmanager.zzfl
    protected final boolean zzc(String str, String str2, Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        return str.contains(str2);
    }
}
