package com.google.android.gms.internal.gtm;

import android.text.TextUtils;
import java.util.HashMap;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzbe extends com.google.android.gms.analytics.zzj<zzbe> {
    private String zza;
    private String zzb;
    private String zzc;
    private String zzd;
    private boolean zze;
    private boolean zzf;

    public final String toString() {
        HashMap hashMap = new HashMap();
        hashMap.put("hitType", this.zza);
        hashMap.put("clientId", this.zzb);
        hashMap.put("userId", this.zzc);
        hashMap.put("androidAdId", this.zzd);
        hashMap.put("AdTargetingEnabled", Boolean.valueOf(this.zze));
        hashMap.put("sessionControl", null);
        hashMap.put("nonInteraction", Boolean.valueOf(this.zzf));
        hashMap.put("sampleRate", Double.valueOf(0.0d));
        return zza(hashMap);
    }

    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [com.google.android.gms.analytics.zzj] */
    @Override // com.google.android.gms.analytics.zzj
    public final /* bridge */ /* synthetic */ void zzc(zzbe zzbeVar) {
        zzbe zzbeVar2 = zzbeVar;
        if (!TextUtils.isEmpty(this.zza)) {
            zzbeVar2.zza = this.zza;
        }
        if (!TextUtils.isEmpty(this.zzb)) {
            zzbeVar2.zzb = this.zzb;
        }
        if (!TextUtils.isEmpty(this.zzc)) {
            zzbeVar2.zzc = this.zzc;
        }
        if (!TextUtils.isEmpty(this.zzd)) {
            zzbeVar2.zzd = this.zzd;
        }
        if (this.zze) {
            zzbeVar2.zze = true;
        }
        TextUtils.isEmpty(null);
        if (this.zzf) {
            zzbeVar2.zzf = true;
        }
    }

    public final String zzd() {
        return this.zzd;
    }

    public final String zze() {
        return this.zzb;
    }

    public final String zzf() {
        return this.zza;
    }

    public final String zzg() {
        return this.zzc;
    }

    public final void zzh(boolean z) {
        this.zze = z;
    }

    public final void zzi(String str) {
        this.zzd = str;
    }

    public final void zzj(String str) {
        this.zzb = str;
    }

    public final void zzk(String str) {
        this.zza = "data";
    }

    public final void zzl(boolean z) {
        this.zzf = true;
    }

    public final void zzm(String str) {
        this.zzc = str;
    }

    public final boolean zzn() {
        return this.zze;
    }

    public final boolean zzo() {
        return this.zzf;
    }
}
