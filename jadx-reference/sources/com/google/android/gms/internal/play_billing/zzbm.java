package com.google.android.gms.internal.play_billing;

import androidx.core.internal.view.SupportMenu;

/* compiled from: com.android.billingclient:billing@@5.2.1 */
/* loaded from: classes.dex */
final class zzbm {
    private final Object zza;
    private final int zzb;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzbm(Object obj, int i) {
        this.zza = obj;
        this.zzb = i;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzbm)) {
            return false;
        }
        zzbm zzbmVar = (zzbm) obj;
        return this.zza == zzbmVar.zza && this.zzb == zzbmVar.zzb;
    }

    public final int hashCode() {
        return (System.identityHashCode(this.zza) * SupportMenu.USER_MASK) + this.zzb;
    }
}
