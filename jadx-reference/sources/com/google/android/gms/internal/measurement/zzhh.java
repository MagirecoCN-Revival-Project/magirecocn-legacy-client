package com.google.android.gms.internal.measurement;

import android.content.Context;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class zzhh implements zzhj {
    protected final zzgm zzacw;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzhh(zzgm zzgmVar) {
        Preconditions.checkNotNull(zzgmVar);
        this.zzacw = zzgmVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzed
    public Context getContext() {
        return this.zzacw.getContext();
    }

    public void zzab() {
        this.zzacw.zzge().zzab();
    }

    @Override // com.google.android.gms.internal.measurement.zzed
    public Clock zzbt() {
        return this.zzacw.zzbt();
    }

    public void zzfr() {
        this.zzacw.zzfr();
    }

    public void zzfs() {
        this.zzacw.zzfs();
    }

    public void zzft() {
        this.zzacw.zzge().zzft();
    }

    public zzdu zzfu() {
        return this.zzacw.zzfu();
    }

    public zzhl zzfv() {
        return this.zzacw.zzfv();
    }

    public zzfc zzfw() {
        return this.zzacw.zzfw();
    }

    public zzeq zzfx() {
        return this.zzacw.zzfx();
    }

    public zzij zzfy() {
        return this.zzacw.zzfy();
    }

    public zzig zzfz() {
        return this.zzacw.zzfz();
    }

    public zzfd zzga() {
        return this.zzacw.zzga();
    }

    public zzff zzgb() {
        return this.zzacw.zzgb();
    }

    public zzkc zzgc() {
        return this.zzacw.zzgc();
    }

    public zzji zzgd() {
        return this.zzacw.zzgd();
    }

    @Override // com.google.android.gms.internal.measurement.zzed
    public zzgh zzge() {
        return this.zzacw.zzge();
    }

    @Override // com.google.android.gms.internal.measurement.zzed
    public zzfh zzgf() {
        return this.zzacw.zzgf();
    }

    public zzfs zzgg() {
        return this.zzacw.zzgg();
    }

    public zzeg zzgh() {
        return this.zzacw.zzgh();
    }

    @Override // com.google.android.gms.internal.measurement.zzed
    public zzec zzgi() {
        return this.zzacw.zzgi();
    }
}
