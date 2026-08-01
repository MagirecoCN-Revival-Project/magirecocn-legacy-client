package com.google.android.gms.internal.gtm;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzwb implements zzwi {
    private final zzwi[] zza;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzwb(zzwi... zzwiVarArr) {
        this.zza = zzwiVarArr;
    }

    @Override // com.google.android.gms.internal.gtm.zzwi
    public final zzwh zzb(Class<?> cls) {
        zzwi[] zzwiVarArr = this.zza;
        for (int i = 0; i < 2; i++) {
            zzwi zzwiVar = zzwiVarArr[i];
            if (zzwiVar.zzc(cls)) {
                return zzwiVar.zzb(cls);
            }
        }
        String valueOf = String.valueOf(cls.getName());
        throw new UnsupportedOperationException(valueOf.length() != 0 ? "No factory is available for message type: ".concat(valueOf) : new String("No factory is available for message type: "));
    }

    @Override // com.google.android.gms.internal.gtm.zzwi
    public final boolean zzc(Class<?> cls) {
        zzwi[] zzwiVarArr = this.zza;
        for (int i = 0; i < 2; i++) {
            if (zzwiVarArr[i].zzc(cls)) {
                return true;
            }
        }
        return false;
    }
}
