package com.google.android.gms.tagmanager;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzee extends zzfl {
    private static final String zza = com.google.android.gms.internal.gtm.zza.REGEX.toString();
    private static final String zzb = com.google.android.gms.internal.gtm.zzb.IGNORE_CASE.toString();

    public zzee() {
        super(zza);
    }

    @Override // com.google.android.gms.tagmanager.zzfl
    protected final boolean zzc(String str, String str2, Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        try {
            return Pattern.compile(str2, true != zzfv.zzg(zzfv.zzl(map.get(zzb))).booleanValue() ? 64 : 66).matcher(str).find();
        } catch (PatternSyntaxException unused) {
            return false;
        }
    }
}
