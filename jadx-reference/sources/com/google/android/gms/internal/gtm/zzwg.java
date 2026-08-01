package com.google.android.gms.internal.gtm;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzwg {
    private static final zzwf zza;
    private static final zzwf zzb;

    static {
        zzwf zzwfVar;
        try {
            zzwfVar = (zzwf) Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            zzwfVar = null;
        }
        zza = zzwfVar;
        zzb = new zzwf();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static zzwf zza() {
        return zza;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static zzwf zzb() {
        return zzb;
    }
}
