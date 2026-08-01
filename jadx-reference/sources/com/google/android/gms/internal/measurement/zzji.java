package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.common.util.Clock;
import kotlinx.coroutines.DebugKt;

/* loaded from: classes.dex */
public final class zzji extends zzhi {
    private Handler handler;
    private long zzaqd;
    private final zzeo zzaqe;
    private final zzeo zzaqf;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzji(zzgm zzgmVar) {
        super(zzgmVar);
        this.zzaqe = new zzjj(this, this.zzacw);
        this.zzaqf = new zzjk(this, this.zzacw);
        this.zzaqd = zzbt().elapsedRealtime();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzaf(long j) {
        zzeo zzeoVar;
        long j2;
        zzab();
        zzkr();
        this.zzaqe.cancel();
        this.zzaqf.cancel();
        zzgf().zziz().zzg("Activity resumed, time", Long.valueOf(j));
        this.zzaqd = j;
        if (zzbt().currentTimeMillis() - zzgg().zzaks.get() > zzgg().zzaku.get()) {
            zzgg().zzakt.set(true);
            zzgg().zzakv.set(0L);
        }
        if (zzgg().zzakt.get()) {
            zzeoVar = this.zzaqe;
            j2 = zzgg().zzakr.get();
        } else {
            zzeoVar = this.zzaqf;
            j2 = 3600000;
        }
        zzeoVar.zzh(Math.max(0L, j2 - zzgg().zzakv.get()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzag(long j) {
        zzab();
        zzkr();
        this.zzaqe.cancel();
        this.zzaqf.cancel();
        zzgf().zziz().zzg("Activity paused, time", Long.valueOf(j));
        if (this.zzaqd != 0) {
            zzgg().zzakv.set(zzgg().zzakv.get() + (j - this.zzaqd));
        }
    }

    private final void zzkr() {
        synchronized (this) {
            if (this.handler == null) {
                this.handler = new Handler(Looper.getMainLooper());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzkt() {
        zzab();
        zzl(false);
        zzfu().zzk(zzbt().elapsedRealtime());
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

    @Override // com.google.android.gms.internal.measurement.zzhi
    protected final boolean zzhh() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzks() {
        this.zzaqe.cancel();
        this.zzaqf.cancel();
        this.zzaqd = 0L;
    }

    public final boolean zzl(boolean z) {
        zzab();
        zzch();
        long elapsedRealtime = zzbt().elapsedRealtime();
        zzgg().zzaku.set(zzbt().currentTimeMillis());
        long j = elapsedRealtime - this.zzaqd;
        if (!z && j < 1000) {
            zzgf().zziz().zzg("Screen exposed for less than 1000 ms. Event not sent. time", Long.valueOf(j));
            return false;
        }
        zzgg().zzakv.set(j);
        zzgf().zziz().zzg("Recording user engagement, ms", Long.valueOf(j));
        Bundle bundle = new Bundle();
        bundle.putLong("_et", j);
        zzig.zza(zzfz().zzkk(), bundle, true);
        zzfv().logEvent(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_e", bundle);
        this.zzaqd = elapsedRealtime;
        this.zzaqf.cancel();
        this.zzaqf.zzh(Math.max(0L, 3600000 - zzgg().zzakv.get()));
        return true;
    }
}
