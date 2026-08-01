package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.os.Bundle;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
public final class zzdu extends zzhh {
    private final Map<String, Long> zzadf;
    private final Map<String, Integer> zzadg;
    private long zzadh;

    public zzdu(zzgm zzgmVar) {
        super(zzgmVar);
        this.zzadg = new ArrayMap();
        this.zzadf = new ArrayMap();
    }

    private final void zza(long j, zzif zzifVar) {
        if (zzifVar == null) {
            zzgf().zziz().log("Not logging ad exposure. No active activity");
            return;
        }
        if (j < 1000) {
            zzgf().zziz().zzg("Not logging ad exposure. Less than 1000 ms. exposure", Long.valueOf(j));
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putLong("_xt", j);
        zzig.zza(zzifVar, bundle, true);
        zzfv().logEvent("am", "_xa", bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(String str, long j) {
        zzfs();
        zzab();
        Preconditions.checkNotEmpty(str);
        if (this.zzadg.isEmpty()) {
            this.zzadh = j;
        }
        Integer num = this.zzadg.get(str);
        if (num != null) {
            this.zzadg.put(str, Integer.valueOf(num.intValue() + 1));
        } else if (this.zzadg.size() >= 100) {
            zzgf().zziv().log("Too many ads visible");
        } else {
            this.zzadg.put(str, 1);
            this.zzadf.put(str, Long.valueOf(j));
        }
    }

    private final void zza(String str, long j, zzif zzifVar) {
        if (zzifVar == null) {
            zzgf().zziz().log("Not logging ad unit exposure. No active activity");
            return;
        }
        if (j < 1000) {
            zzgf().zziz().zzg("Not logging ad unit exposure. Less than 1000 ms. exposure", Long.valueOf(j));
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("_ai", str);
        bundle.putLong("_xt", j);
        zzig.zza(zzifVar, bundle, true);
        zzfv().logEvent("am", "_xu", bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzb(String str, long j) {
        zzfs();
        zzab();
        Preconditions.checkNotEmpty(str);
        Integer num = this.zzadg.get(str);
        if (num == null) {
            zzgf().zzis().zzg("Call to endAdUnitExposure for unknown ad unit id", str);
            return;
        }
        zzif zzkk = zzfz().zzkk();
        int intValue = num.intValue() - 1;
        if (intValue != 0) {
            this.zzadg.put(str, Integer.valueOf(intValue));
            return;
        }
        this.zzadg.remove(str);
        Long l = this.zzadf.get(str);
        if (l == null) {
            zzgf().zzis().log("First ad unit exposure time was never set");
        } else {
            long longValue = j - l.longValue();
            this.zzadf.remove(str);
            zza(str, longValue, zzkk);
        }
        if (this.zzadg.isEmpty()) {
            long j2 = this.zzadh;
            if (j2 == 0) {
                zzgf().zzis().log("First ad exposure time was never set");
            } else {
                zza(j - j2, zzkk);
                this.zzadh = 0L;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzl(long j) {
        Iterator<String> it = this.zzadf.keySet().iterator();
        while (it.hasNext()) {
            this.zzadf.put(it.next(), Long.valueOf(j));
        }
        if (this.zzadf.isEmpty()) {
            return;
        }
        this.zzadh = j;
    }

    public final void beginAdUnitExposure(String str) {
        if (str == null || str.length() == 0) {
            zzgf().zzis().log("Ad unit id must be a non-empty string");
        } else {
            zzge().zzc(new zzdv(this, str, zzbt().elapsedRealtime()));
        }
    }

    public final void endAdUnitExposure(String str) {
        if (str == null || str.length() == 0) {
            zzgf().zzis().log("Ad unit id must be a non-empty string");
        } else {
            zzge().zzc(new zzdw(this, str, zzbt().elapsedRealtime()));
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ Clock zzbt() {
        return super.zzbt();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ void zzfr() {
        super.zzfr();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ void zzfs() {
        super.zzfs();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ void zzft() {
        super.zzft();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzdu zzfu() {
        return super.zzfu();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzhl zzfv() {
        return super.zzfv();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzfc zzfw() {
        return super.zzfw();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzeq zzfx() {
        return super.zzfx();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzij zzfy() {
        return super.zzfy();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzig zzfz() {
        return super.zzfz();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzfd zzga() {
        return super.zzga();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzff zzgb() {
        return super.zzgb();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzkc zzgc() {
        return super.zzgc();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzji zzgd() {
        return super.zzgd();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ zzgh zzge() {
        return super.zzge();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ zzfh zzgf() {
        return super.zzgf();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzfs zzgg() {
        return super.zzgg();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzeg zzgh() {
        return super.zzgh();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ zzec zzgi() {
        return super.zzgi();
    }

    public final void zzk(long j) {
        zzif zzkk = zzfz().zzkk();
        for (String str : this.zzadf.keySet()) {
            zza(str, j - this.zzadf.get(str).longValue(), zzkk);
        }
        if (!this.zzadf.isEmpty()) {
            zza(j - this.zzadh, zzkk);
        }
        zzl(j);
    }
}
