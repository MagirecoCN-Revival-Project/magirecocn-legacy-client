package com.google.android.gms.internal.measurement;

import java.io.Serializable;
import java.util.Iterator;

/* loaded from: classes.dex */
public abstract class zzyy implements Serializable, Iterable<Byte> {
    public static final zzyy zzbrh = new zzze(zzzt.zzbta);
    private static final zzzc zzbri;
    private int zzbon = 0;

    /* JADX DEBUG: Multi-variable search result rejected for r0v3, resolved type: com.google.android.gms.internal.measurement.zzzc */
    /* JADX DEBUG: Multi-variable search result rejected for r0v5, resolved type: com.google.android.gms.internal.measurement.zzzc */
    /* JADX DEBUG: Multi-variable search result rejected for r0v6, resolved type: com.google.android.gms.internal.measurement.zzzc */
    /* JADX WARN: Multi-variable type inference failed */
    static {
        zzyz zzyzVar = null;
        zzbri = zzyx.zzte() ? new zzzf(zzyzVar) : new zzza(zzyzVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int zzb(int i, int i2, int i3) {
        int i4 = i2 - i;
        if ((i | i2 | i4 | (i3 - i2)) >= 0) {
            return i4;
        }
        if (i < 0) {
            StringBuilder sb = new StringBuilder(32);
            sb.append("Beginning index: ");
            sb.append(i);
            sb.append(" < 0");
            throw new IndexOutOfBoundsException(sb.toString());
        }
        if (i2 < i) {
            StringBuilder sb2 = new StringBuilder(66);
            sb2.append("Beginning index larger than ending index: ");
            sb2.append(i);
            sb2.append(", ");
            sb2.append(i2);
            throw new IndexOutOfBoundsException(sb2.toString());
        }
        StringBuilder sb3 = new StringBuilder(37);
        sb3.append("End index: ");
        sb3.append(i2);
        sb3.append(" >= ");
        sb3.append(i3);
        throw new IndexOutOfBoundsException(sb3.toString());
    }

    public static zzyy zzfg(String str) {
        return new zzze(str.getBytes(zzzt.UTF_8));
    }

    public abstract boolean equals(Object obj);

    public final int hashCode() {
        int i = this.zzbon;
        if (i == 0) {
            int size = size();
            i = zza(size, 0, size);
            if (i == 0) {
                i = 1;
            }
            this.zzbon = i;
        }
        return i;
    }

    /* JADX DEBUG: Return type fixed from 'java.util.Iterator' to match base method */
    @Override // java.lang.Iterable
    public /* synthetic */ Iterator<Byte> iterator() {
        return new zzyz(this);
    }

    public abstract int size();

    public final String toString() {
        return String.format("<ByteString@%s size=%d>", Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(size()));
    }

    protected abstract int zza(int i, int i2, int i3);

    public abstract byte zzae(int i);

    public abstract zzyy zzb(int i, int i2);

    /* JADX INFO: Access modifiers changed from: protected */
    public final int zztg() {
        return this.zzbon;
    }
}
