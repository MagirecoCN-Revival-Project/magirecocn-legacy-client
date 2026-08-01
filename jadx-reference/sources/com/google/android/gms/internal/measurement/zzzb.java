package com.google.android.gms.internal.measurement;

/* loaded from: classes.dex */
final class zzzb extends zzze {
    private final int zzbrk;
    private final int zzbrl;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzzb(byte[] bArr, int i, int i2) {
        super(bArr);
        zzb(i, i + i2, bArr.length);
        this.zzbrk = i;
        this.zzbrl = i2;
    }

    @Override // com.google.android.gms.internal.measurement.zzze, com.google.android.gms.internal.measurement.zzyy
    public final int size() {
        return this.zzbrl;
    }

    @Override // com.google.android.gms.internal.measurement.zzze, com.google.android.gms.internal.measurement.zzyy
    public final byte zzae(int i) {
        int size = size();
        if (((size - (i + 1)) | i) >= 0) {
            return this.zzbrm[this.zzbrk + i];
        }
        if (i < 0) {
            StringBuilder sb = new StringBuilder(22);
            sb.append("Index < 0: ");
            sb.append(i);
            throw new ArrayIndexOutOfBoundsException(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder(40);
        sb2.append("Index > length: ");
        sb2.append(i);
        sb2.append(", ");
        sb2.append(size);
        throw new ArrayIndexOutOfBoundsException(sb2.toString());
    }

    @Override // com.google.android.gms.internal.measurement.zzze
    protected final int zzth() {
        return this.zzbrk;
    }
}
