package com.google.android.gms.tagmanager;

import com.google.android.gms.common.util.Clock;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzac {
    final /* synthetic */ boolean zza;
    final /* synthetic */ zzal zzb;
    private Long zzc;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzac(zzal zzalVar, boolean z) {
        this.zzb = zzalVar;
        this.zza = z;
    }

    public final boolean zza(Container container) {
        Clock clock;
        zzam zzamVar;
        if (!this.zza) {
            return !container.isDefault();
        }
        long lastRefreshTime = container.getLastRefreshTime();
        if (this.zzc == null) {
            zzamVar = this.zzb.zzi;
            this.zzc = Long.valueOf(zzamVar.zza());
        }
        long longValue = lastRefreshTime + this.zzc.longValue();
        clock = this.zzb.zza;
        return longValue >= clock.currentTimeMillis();
    }
}
