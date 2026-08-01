package com.google.android.gms.internal.gtm;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzwr {
    private static final zzwq zza;
    private static final zzwq zzb;

    static {
        zzwq zzwqVar;
        try {
            zzwqVar = (zzwq) Class.forName("com.google.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            zzwqVar = null;
        }
        zza = zzwqVar;
        zzb = new zzwq();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static zzwq zza() {
        return zza;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static zzwq zzb() {
        return zzb;
    }
}
