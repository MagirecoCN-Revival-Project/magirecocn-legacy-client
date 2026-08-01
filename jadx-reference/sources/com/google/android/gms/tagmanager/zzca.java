package com.google.android.gms.tagmanager;

import android.text.TextUtils;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzca {
    private final long zza;
    private final long zzb;
    private String zzc;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzca(long j, long j2, long j3) {
        this.zza = j;
        this.zzb = j3;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final long zza() {
        return this.zzb;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final long zzb() {
        return this.zza;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final String zzc() {
        return this.zzc;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzd(String str) {
        if (str == null || TextUtils.isEmpty(str.trim())) {
            return;
        }
        this.zzc = str;
    }
}
