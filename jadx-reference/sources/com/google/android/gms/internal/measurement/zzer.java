package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class zzer {
    final String name;
    private final String origin;
    final long timestamp;
    final long zzafq;
    final zzet zzafr;
    final String zzti;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzer(zzgm zzgmVar, String str, String str2, String str3, long j, long j2, Bundle bundle) {
        zzet zzetVar;
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkNotEmpty(str3);
        this.zzti = str2;
        this.name = str3;
        this.origin = TextUtils.isEmpty(str) ? null : str;
        this.timestamp = j;
        this.zzafq = j2;
        if (j2 != 0 && j2 > j) {
            zzgmVar.zzgf().zziv().zzg("Event created with reverse previous/current timestamps. appId", zzfh.zzbl(str2));
        }
        if (bundle == null || bundle.isEmpty()) {
            zzetVar = new zzet(new Bundle());
        } else {
            Bundle bundle2 = new Bundle(bundle);
            Iterator<String> it = bundle2.keySet().iterator();
            while (it.hasNext()) {
                String next = it.next();
                if (next == null) {
                    zzgmVar.zzgf().zzis().log("Param name can't be null");
                } else {
                    Object zzh = zzgmVar.zzgc().zzh(next, bundle2.get(next));
                    if (zzh == null) {
                        zzgmVar.zzgf().zziv().zzg("Param value can't be null", zzgmVar.zzgb().zzbj(next));
                    } else {
                        zzgmVar.zzgc().zza(bundle2, next, zzh);
                    }
                }
                it.remove();
            }
            zzetVar = new zzet(bundle2);
        }
        this.zzafr = zzetVar;
    }

    private zzer(zzgm zzgmVar, String str, String str2, String str3, long j, long j2, zzet zzetVar) {
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkNotEmpty(str3);
        Preconditions.checkNotNull(zzetVar);
        this.zzti = str2;
        this.name = str3;
        this.origin = TextUtils.isEmpty(str) ? null : str;
        this.timestamp = j;
        this.zzafq = j2;
        if (j2 != 0 && j2 > j) {
            zzgmVar.zzgf().zziv().zze("Event created with reverse previous/current timestamps. appId, name", zzfh.zzbl(str2), zzfh.zzbl(str3));
        }
        this.zzafr = zzetVar;
    }

    public final String toString() {
        String str = this.zzti;
        String str2 = this.name;
        String valueOf = String.valueOf(this.zzafr);
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 33 + String.valueOf(str2).length() + String.valueOf(valueOf).length());
        sb.append("Event{appId='");
        sb.append(str);
        sb.append("', name='");
        sb.append(str2);
        sb.append("', params=");
        sb.append(valueOf);
        sb.append('}');
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final zzer zza(zzgm zzgmVar, long j) {
        return new zzer(zzgmVar, this.origin, this.zzti, this.name, this.timestamp, j, this.zzafr);
    }
}
