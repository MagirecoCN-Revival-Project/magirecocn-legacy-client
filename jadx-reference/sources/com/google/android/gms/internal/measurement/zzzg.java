package com.google.android.gms.internal.measurement;

/* loaded from: classes.dex */
public abstract class zzzg {
    private static volatile boolean zzbrq;
    int zzbrn;
    private int zzbro;
    private boolean zzbrp;

    private zzzg() {
        this.zzbrn = 100;
        this.zzbro = Integer.MAX_VALUE;
        this.zzbrp = false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static zzzg zza(byte[] bArr, int i, int i2, boolean z) {
        zzzi zzziVar = new zzzi(bArr, i, i2);
        try {
            zzziVar.zzaf(i2);
            return zzziVar;
        } catch (zzzv e) {
            throw new IllegalArgumentException(e);
        }
    }

    public abstract int zzti();
}
