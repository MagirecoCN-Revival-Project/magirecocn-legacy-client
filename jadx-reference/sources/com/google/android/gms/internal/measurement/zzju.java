package com.google.android.gms.internal.measurement;

import java.util.List;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzju implements zzfn {
    private final /* synthetic */ zzjs zzarf;
    private final /* synthetic */ String zzarg;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzju(zzjs zzjsVar, String str) {
        this.zzarf = zzjsVar;
        this.zzarg = str;
    }

    @Override // com.google.android.gms.internal.measurement.zzfn
    public final void zza(String str, int i, Throwable th, byte[] bArr, Map<String, List<String>> map) {
        this.zzarf.zza(i, th, bArr, this.zzarg);
    }
}
