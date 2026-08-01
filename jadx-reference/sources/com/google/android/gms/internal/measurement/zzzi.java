package com.google.android.gms.internal.measurement;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzzi extends zzzg {
    private final byte[] buffer;
    private int limit;
    private int pos;
    private final boolean zzbrr;
    private int zzbrs;
    private int zzbrt;
    private int zzbru;

    private zzzi(byte[] bArr, int i, int i2, boolean z) {
        super();
        this.zzbru = Integer.MAX_VALUE;
        this.buffer = bArr;
        this.limit = i2 + i;
        this.pos = i;
        this.zzbrt = i;
        this.zzbrr = z;
    }

    private final void zztj() {
        int i = this.limit + this.zzbrs;
        this.limit = i;
        int i2 = i - this.zzbrt;
        int i3 = this.zzbru;
        if (i2 <= i3) {
            this.zzbrs = 0;
            return;
        }
        int i4 = i2 - i3;
        this.zzbrs = i4;
        this.limit = i - i4;
    }

    public final int zzaf(int i) throws zzzv {
        if (i < 0) {
            throw zzzv.zztw();
        }
        int zzti = i + zzti();
        int i2 = this.zzbru;
        if (zzti > i2) {
            throw zzzv.zztv();
        }
        this.zzbru = zzti;
        zztj();
        return i2;
    }

    @Override // com.google.android.gms.internal.measurement.zzzg
    public final int zzti() {
        return this.pos - this.zzbrt;
    }
}
