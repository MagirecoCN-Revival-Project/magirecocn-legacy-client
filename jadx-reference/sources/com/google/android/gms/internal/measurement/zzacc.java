package com.google.android.gms.internal.measurement;

/* loaded from: classes.dex */
public final class zzacc implements Cloneable {
    private static final zzacd zzbxk = new zzacd();
    private int mSize;
    private boolean zzbxl;
    private int[] zzbxm;
    private zzacd[] zzbxn;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzacc() {
        this(10);
    }

    private zzacc(int i) {
        this.zzbxl = false;
        int idealIntArraySize = idealIntArraySize(i);
        this.zzbxm = new int[idealIntArraySize];
        this.zzbxn = new zzacd[idealIntArraySize];
        this.mSize = 0;
    }

    private static int idealIntArraySize(int i) {
        int i2 = i << 2;
        int i3 = 4;
        while (true) {
            if (i3 >= 32) {
                break;
            }
            int i4 = (1 << i3) - 12;
            if (i2 <= i4) {
                i2 = i4;
                break;
            }
            i3++;
        }
        return i2 / 4;
    }

    private final int zzav(int i) {
        int i2 = this.mSize - 1;
        int i3 = 0;
        while (i3 <= i2) {
            int i4 = (i3 + i2) >>> 1;
            int i5 = this.zzbxm[i4];
            if (i5 < i) {
                i3 = i4 + 1;
            } else {
                if (i5 <= i) {
                    return i4;
                }
                i2 = i4 - 1;
            }
        }
        return ~i3;
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        int i = this.mSize;
        zzacc zzaccVar = new zzacc(i);
        System.arraycopy(this.zzbxm, 0, zzaccVar.zzbxm, 0, i);
        for (int i2 = 0; i2 < i; i2++) {
            zzacd[] zzacdVarArr = this.zzbxn;
            if (zzacdVarArr[i2] != null) {
                zzaccVar.zzbxn[i2] = (zzacd) zzacdVarArr[i2].clone();
            }
        }
        zzaccVar.mSize = i;
        return zzaccVar;
    }

    public final boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzacc)) {
            return false;
        }
        zzacc zzaccVar = (zzacc) obj;
        int i = this.mSize;
        if (i != zzaccVar.mSize) {
            return false;
        }
        int[] iArr = this.zzbxm;
        int[] iArr2 = zzaccVar.zzbxm;
        int i2 = 0;
        while (true) {
            if (i2 >= i) {
                z = true;
                break;
            }
            if (iArr[i2] != iArr2[i2]) {
                z = false;
                break;
            }
            i2++;
        }
        if (z) {
            zzacd[] zzacdVarArr = this.zzbxn;
            zzacd[] zzacdVarArr2 = zzaccVar.zzbxn;
            int i3 = this.mSize;
            int i4 = 0;
            while (true) {
                if (i4 >= i3) {
                    z2 = true;
                    break;
                }
                if (!zzacdVarArr[i4].equals(zzacdVarArr2[i4])) {
                    z2 = false;
                    break;
                }
                i4++;
            }
            if (z2) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int i = 17;
        for (int i2 = 0; i2 < this.mSize; i2++) {
            i = (((i * 31) + this.zzbxm[i2]) * 31) + this.zzbxn[i2].hashCode();
        }
        return i;
    }

    public final boolean isEmpty() {
        return this.mSize == 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final int size() {
        return this.mSize;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zza(int i, zzacd zzacdVar) {
        int zzav = zzav(i);
        if (zzav >= 0) {
            this.zzbxn[zzav] = zzacdVar;
            return;
        }
        int i2 = ~zzav;
        int i3 = this.mSize;
        if (i2 < i3) {
            zzacd[] zzacdVarArr = this.zzbxn;
            if (zzacdVarArr[i2] == zzbxk) {
                this.zzbxm[i2] = i;
                zzacdVarArr[i2] = zzacdVar;
                return;
            }
        }
        if (i3 >= this.zzbxm.length) {
            int idealIntArraySize = idealIntArraySize(i3 + 1);
            int[] iArr = new int[idealIntArraySize];
            zzacd[] zzacdVarArr2 = new zzacd[idealIntArraySize];
            int[] iArr2 = this.zzbxm;
            System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
            zzacd[] zzacdVarArr3 = this.zzbxn;
            System.arraycopy(zzacdVarArr3, 0, zzacdVarArr2, 0, zzacdVarArr3.length);
            this.zzbxm = iArr;
            this.zzbxn = zzacdVarArr2;
        }
        int i4 = this.mSize;
        if (i4 - i2 != 0) {
            int[] iArr3 = this.zzbxm;
            int i5 = i2 + 1;
            System.arraycopy(iArr3, i2, iArr3, i5, i4 - i2);
            zzacd[] zzacdVarArr4 = this.zzbxn;
            System.arraycopy(zzacdVarArr4, i2, zzacdVarArr4, i5, this.mSize - i2);
        }
        this.zzbxm[i2] = i;
        this.zzbxn[i2] = zzacdVar;
        this.mSize++;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final zzacd zzat(int i) {
        int zzav = zzav(i);
        if (zzav < 0) {
            return null;
        }
        zzacd[] zzacdVarArr = this.zzbxn;
        if (zzacdVarArr[zzav] == zzbxk) {
            return null;
        }
        return zzacdVarArr[zzav];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final zzacd zzau(int i) {
        return this.zzbxn[i];
    }
}
