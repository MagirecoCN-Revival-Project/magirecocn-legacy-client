package com.google.android.gms.tagmanager;

import com.google.android.gms.common.util.Clock;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzdf implements zzec {
    private long zzb;
    private final Clock zze;
    private final Object zzc = new Object();
    private double zza = Math.min(1, 5);
    private final String zzd = "refreshing";

    public zzdf(int i, int i2, long j, long j2, String str, Clock clock) {
        this.zze = clock;
    }

    @Override // com.google.android.gms.tagmanager.zzec
    public final boolean zza() {
        synchronized (this.zzc) {
            long currentTimeMillis = this.zze.currentTimeMillis();
            long j = currentTimeMillis - this.zzb;
            if (j < 5000) {
                String str = this.zzd;
                StringBuilder sb = new StringBuilder(str.length() + 34);
                sb.append("Excessive ");
                sb.append(str);
                sb.append(" detected; call ignored.");
                zzdh.zzc(sb.toString());
                return false;
            }
            double d = this.zza;
            if (d < 5.0d) {
                double d2 = j / 900000.0d;
                if (d2 > 0.0d) {
                    d = Math.min(5.0d, d + d2);
                    this.zza = d;
                }
            }
            this.zzb = currentTimeMillis;
            if (d >= 1.0d) {
                this.zza = d - 1.0d;
                return true;
            }
            String str2 = this.zzd;
            StringBuilder sb2 = new StringBuilder(str2.length() + 34);
            sb2.append("Excessive ");
            sb2.append(str2);
            sb2.append(" detected; call ignored.");
            zzdh.zzc(sb2.toString());
            return false;
        }
    }
}
