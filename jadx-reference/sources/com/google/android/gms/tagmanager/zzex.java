package com.google.android.gms.tagmanager;

import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzex implements zzec {
    private long zzb;
    private final Object zzc = new Object();
    private double zza = 60.0d;
    private final Clock zzd = DefaultClock.getInstance();

    @Override // com.google.android.gms.tagmanager.zzec
    public final boolean zza() {
        synchronized (this.zzc) {
            long currentTimeMillis = this.zzd.currentTimeMillis();
            double d = this.zza;
            if (d < 60.0d) {
                double d2 = (currentTimeMillis - this.zzb) / 2000.0d;
                if (d2 > 0.0d) {
                    d = Math.min(60.0d, d + d2);
                    this.zza = d;
                }
            }
            this.zzb = currentTimeMillis;
            if (d >= 1.0d) {
                this.zza = d - 1.0d;
                return true;
            }
            zzdh.zzc("No more tokens available.");
            return false;
        }
    }
}
