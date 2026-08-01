package com.google.android.gms.internal.gtm;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzus implements zzwi {
    private static final zzus zza = new zzus();

    private zzus() {
    }

    public static zzus zza() {
        return zza;
    }

    @Override // com.google.android.gms.internal.gtm.zzwi
    public final zzwh zzb(Class<?> cls) {
        if (!zzuz.class.isAssignableFrom(cls)) {
            String valueOf = String.valueOf(cls.getName());
            throw new IllegalArgumentException(valueOf.length() != 0 ? "Unsupported message type: ".concat(valueOf) : new String("Unsupported message type: "));
        }
        try {
            return (zzwh) zzuz.zzab(cls.asSubclass(zzuz.class)).zzb(3, null, null);
        } catch (Exception e) {
            String valueOf2 = String.valueOf(cls.getName());
            throw new RuntimeException(valueOf2.length() != 0 ? "Unable to get message info for ".concat(valueOf2) : new String("Unable to get message info for "), e);
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzwi
    public final boolean zzc(Class<?> cls) {
        return zzuz.class.isAssignableFrom(cls);
    }
}
