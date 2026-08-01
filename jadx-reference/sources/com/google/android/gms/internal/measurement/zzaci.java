package com.google.android.gms.internal.measurement;

import java.util.Arrays;

/* loaded from: classes.dex */
final class zzaci {
    final int tag;
    final byte[] zzbrm;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzaci(int i, byte[] bArr) {
        this.tag = i;
        this.zzbrm = bArr;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzaci)) {
            return false;
        }
        zzaci zzaciVar = (zzaci) obj;
        return this.tag == zzaciVar.tag && Arrays.equals(this.zzbrm, zzaciVar.zzbrm);
    }

    public final int hashCode() {
        return ((this.tag + 527) * 31) + Arrays.hashCode(this.zzbrm);
    }
}
