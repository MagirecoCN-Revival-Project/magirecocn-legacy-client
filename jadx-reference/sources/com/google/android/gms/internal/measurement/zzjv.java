package com.google.android.gms.internal.measurement;

import java.util.List;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzjv implements zzfn {
    private final /* synthetic */ zzjs zzarf;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzjv(zzjs zzjsVar) {
        this.zzarf = zzjsVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzfn
    public final void zza(String str, int i, Throwable th, byte[] bArr, Map<String, List<String>> map) {
        this.zzarf.zzb(str, i, th, bArr, map);
    }
}
