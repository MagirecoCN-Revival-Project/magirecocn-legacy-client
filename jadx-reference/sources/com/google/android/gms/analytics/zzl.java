package com.google.android.gms.analytics;

import android.net.Uri;
import com.google.android.gms.common.internal.Preconditions;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzl implements Runnable {
    final /* synthetic */ zzh zza;
    final /* synthetic */ zzr zzb;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzl(zzr zzrVar, zzh zzhVar) {
        this.zzb = zzrVar;
        this.zza = zzhVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        List list;
        this.zza.zzd().zze(this.zza);
        list = this.zzb.zzc;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((zzs) it.next()).zza();
        }
        zzh zzhVar = this.zza;
        Preconditions.checkNotMainThread("deliver should be called from worker thread");
        Preconditions.checkArgument(zzhVar.zzm(), "Measurement must be submitted");
        List<zzt> zzf = zzhVar.zzf();
        if (zzf.isEmpty()) {
            return;
        }
        HashSet hashSet = new HashSet();
        for (zzt zztVar : zzf) {
            Uri zzb = zztVar.zzb();
            if (!hashSet.contains(zzb)) {
                hashSet.add(zzb);
                zztVar.zze(zzhVar);
            }
        }
    }
}
