package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Random;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzam {
    private final Context zza;
    private final Random zzb;
    private final String zzc;

    public zzam(Context context, String str) {
        Random random = new Random();
        this.zza = (Context) Preconditions.checkNotNull(context);
        this.zzc = (String) Preconditions.checkNotNull(str);
        this.zzb = random;
    }

    private final long zze(long j, long j2) {
        long max = Math.max(0L, zzf().getLong("FORBIDDEN_COUNT", 0L));
        return this.zzb.nextFloat() * ((float) (j + ((((float) max) / ((float) ((max + Math.max(0L, r0.getLong("SUCCESSFUL_COUNT", 0L))) + 1))) * ((float) (j2 - j)))));
    }

    private final SharedPreferences zzf() {
        Context context = this.zza;
        String valueOf = String.valueOf(this.zzc);
        return context.getSharedPreferences(valueOf.length() != 0 ? "_gtmContainerRefreshPolicy_".concat(valueOf) : new String("_gtmContainerRefreshPolicy_"), 0);
    }

    public final long zza() {
        return zze(7200000L, 259200000L) + 43200000;
    }

    public final long zzb() {
        return zze(600000L, 86400000L) + 3600000;
    }

    public final void zzc() {
        SharedPreferences zzf = zzf();
        long j = zzf.getLong("FORBIDDEN_COUNT", 0L);
        long j2 = zzf.getLong("SUCCESSFUL_COUNT", 0L);
        SharedPreferences.Editor edit = zzf.edit();
        long min = j == 0 ? 3L : Math.min(10L, j + 1);
        long max = Math.max(0L, Math.min(j2, 10 - min));
        edit.putLong("FORBIDDEN_COUNT", min);
        edit.putLong("SUCCESSFUL_COUNT", max);
        edit.apply();
    }

    public final void zzd() {
        SharedPreferences zzf = zzf();
        long j = zzf.getLong("SUCCESSFUL_COUNT", 0L);
        long j2 = zzf.getLong("FORBIDDEN_COUNT", 0L);
        long min = Math.min(10L, j + 1);
        long max = Math.max(0L, Math.min(j2, 10 - min));
        SharedPreferences.Editor edit = zzf.edit();
        edit.putLong("SUCCESSFUL_COUNT", min);
        edit.putLong("FORBIDDEN_COUNT", max);
        edit.apply();
    }
}
