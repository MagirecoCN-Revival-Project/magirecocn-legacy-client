package com.google.android.gms.internal.gtm;

import java.util.Collections;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzro {
    private final Map<String, zzak> zza;
    private final zzak zzb;

    /* JADX INFO: Access modifiers changed from: package-private */
    public /* synthetic */ zzro(Map map, zzak zzakVar, zzrn zzrnVar) {
        this.zza = map;
        this.zzb = zzakVar;
    }

    public static zzrq zzb() {
        return new zzrq(null);
    }

    public final String toString() {
        String valueOf = String.valueOf(Collections.unmodifiableMap(this.zza));
        String valueOf2 = String.valueOf(this.zzb);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 32 + String.valueOf(valueOf2).length());
        sb.append("Properties: ");
        sb.append(valueOf);
        sb.append(" pushAfterEvaluate: ");
        sb.append(valueOf2);
        return sb.toString();
    }

    public final zzak zza() {
        return this.zzb;
    }

    public final Map<String, zzak> zzc() {
        return Collections.unmodifiableMap(this.zza);
    }

    public final void zzd(String str, zzak zzakVar) {
        this.zza.put(str, zzakVar);
    }
}
