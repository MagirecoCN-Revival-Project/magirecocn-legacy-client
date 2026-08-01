package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzwk;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzux<ContainingType extends zzwk, Type> extends zzuh<ContainingType, Type> {
    final ContainingType zza;
    final Type zzb;
    final zzwk zzc;
    final zzuw zzd;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzux(ContainingType containingtype, Type type, zzwk zzwkVar, zzuw zzuwVar, Class cls) {
        if (containingtype == null) {
            throw new IllegalArgumentException("Null containingTypeDefaultInstance");
        }
        if (zzuwVar.zzc == zzye.MESSAGE && zzwkVar == null) {
            throw new IllegalArgumentException("Null messageDefaultInstance");
        }
        this.zza = containingtype;
        this.zzb = type;
        this.zzc = zzwkVar;
        this.zzd = zzuwVar;
    }

    public final boolean zza() {
        boolean z = this.zzd.zzd;
        return false;
    }
}
