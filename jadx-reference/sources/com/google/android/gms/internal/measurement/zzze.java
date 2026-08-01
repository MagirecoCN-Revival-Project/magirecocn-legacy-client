package com.google.android.gms.internal.measurement;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class zzze extends zzzd {
    protected final byte[] zzbrm;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzze(byte[] bArr) {
        this.zzbrm = bArr;
    }

    @Override // com.google.android.gms.internal.measurement.zzyy
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzyy) || size() != ((zzyy) obj).size()) {
            return false;
        }
        if (size() == 0) {
            return true;
        }
        if (!(obj instanceof zzze)) {
            return obj.equals(this);
        }
        zzze zzzeVar = (zzze) obj;
        int zztg = zztg();
        int zztg2 = zzzeVar.zztg();
        if (zztg == 0 || zztg2 == 0 || zztg == zztg2) {
            return zza(zzzeVar, 0, size());
        }
        return false;
    }

    @Override // com.google.android.gms.internal.measurement.zzyy
    public int size() {
        return this.zzbrm.length;
    }

    @Override // com.google.android.gms.internal.measurement.zzyy
    protected final int zza(int i, int i2, int i3) {
        return zzzt.zza(i, this.zzbrm, zzth(), i3);
    }

    @Override // com.google.android.gms.internal.measurement.zzzd
    final boolean zza(zzyy zzyyVar, int i, int i2) {
        if (i2 > zzyyVar.size()) {
            int size = size();
            StringBuilder sb = new StringBuilder(40);
            sb.append("Length too large: ");
            sb.append(i2);
            sb.append(size);
            throw new IllegalArgumentException(sb.toString());
        }
        if (i2 > zzyyVar.size()) {
            int size2 = zzyyVar.size();
            StringBuilder sb2 = new StringBuilder(59);
            sb2.append("Ran off end of other: 0, ");
            sb2.append(i2);
            sb2.append(", ");
            sb2.append(size2);
            throw new IllegalArgumentException(sb2.toString());
        }
        if (!(zzyyVar instanceof zzze)) {
            return zzyyVar.zzb(0, i2).equals(zzb(0, i2));
        }
        zzze zzzeVar = (zzze) zzyyVar;
        byte[] bArr = this.zzbrm;
        byte[] bArr2 = zzzeVar.zzbrm;
        int zzth = zzth() + i2;
        int zzth2 = zzth();
        int zzth3 = zzzeVar.zzth();
        while (zzth2 < zzth) {
            if (bArr[zzth2] != bArr2[zzth3]) {
                return false;
            }
            zzth2++;
            zzth3++;
        }
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzyy
    public byte zzae(int i) {
        return this.zzbrm[i];
    }

    @Override // com.google.android.gms.internal.measurement.zzyy
    public final zzyy zzb(int i, int i2) {
        int zzb = zzb(0, i2, size());
        return zzb == 0 ? zzyy.zzbrh : new zzzb(this.zzbrm, zzth(), zzb);
    }

    protected int zzth() {
        return 0;
    }
}
