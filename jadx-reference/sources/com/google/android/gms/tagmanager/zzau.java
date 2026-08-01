package com.google.android.gms.tagmanager;

import com.google.android.gms.common.internal.Preconditions;
import java.util.Arrays;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzau {
    public final String zza;
    public final Object zzb;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzau(String str, Object obj) {
        this.zza = str;
        this.zzb = obj;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzau)) {
            return false;
        }
        zzau zzauVar = (zzau) obj;
        if (!this.zza.equals(zzauVar.zza)) {
            return false;
        }
        Object obj2 = this.zzb;
        if (obj2 == null && zzauVar.zzb == null) {
            return true;
        }
        return obj2 != null && obj2.equals(zzauVar.zzb);
    }

    public final int hashCode() {
        Preconditions.checkNotNull(this.zzb);
        return Arrays.hashCode(new Integer[]{Integer.valueOf(this.zza.hashCode()), Integer.valueOf(this.zzb.hashCode())});
    }

    public final String toString() {
        String str = this.zza;
        String valueOf = String.valueOf(this.zzb);
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 13 + String.valueOf(valueOf).length());
        sb.append("Key: ");
        sb.append(str);
        sb.append(" value: ");
        sb.append(valueOf);
        return sb.toString();
    }
}
