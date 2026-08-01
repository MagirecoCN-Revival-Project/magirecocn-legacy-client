package com.google.android.gms.common;

import java.lang.ref.WeakReference;

/* compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* loaded from: classes.dex */
abstract class zzk extends zzi {
    private static final WeakReference<byte[]> zza = new WeakReference<>(null);
    private WeakReference<byte[]> zzb;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzk(byte[] bArr) {
        super(bArr);
        this.zzb = zza;
    }

    protected abstract byte[] zzb();

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.common.zzi
    public final byte[] zzf() {
        byte[] bArr;
        synchronized (this) {
            bArr = this.zzb.get();
            if (bArr == null) {
                bArr = zzb();
                this.zzb = new WeakReference<>(bArr);
            }
        }
        return bArr;
    }
}
