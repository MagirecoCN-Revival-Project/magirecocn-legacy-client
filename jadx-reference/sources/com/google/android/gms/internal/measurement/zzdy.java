package com.google.android.gms.internal.measurement;

import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzdy {
    private final zzgm zzacw;
    private String zzadl;
    private String zzadm;
    private String zzadn;
    private String zzado;
    private long zzadp;
    private long zzadq;
    private long zzadr;
    private long zzads;
    private String zzadt;
    private long zzadu;
    private long zzadv;
    private boolean zzadw;
    private long zzadx;
    private boolean zzady;
    private boolean zzadz;
    private long zzaea;
    private long zzaeb;
    private long zzaec;
    private long zzaed;
    private long zzaee;
    private long zzaef;
    private String zzaeg;
    private boolean zzaeh;
    private long zzaei;
    private long zzaej;
    private String zzth;
    private final String zzti;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzdy(zzgm zzgmVar, String str) {
        Preconditions.checkNotNull(zzgmVar);
        Preconditions.checkNotEmpty(str);
        this.zzacw = zzgmVar;
        this.zzti = str;
        zzgmVar.zzge().zzab();
    }

    public final String getAppInstanceId() {
        this.zzacw.zzge().zzab();
        return this.zzadl;
    }

    public final String getGmpAppId() {
        this.zzacw.zzge().zzab();
        return this.zzadm;
    }

    public final boolean isMeasurementEnabled() {
        this.zzacw.zzge().zzab();
        return this.zzadw;
    }

    public final void setAppVersion(String str) {
        this.zzacw.zzge().zzab();
        this.zzaeh |= !zzkc.zzs(this.zzth, str);
        this.zzth = str;
    }

    public final void setMeasurementEnabled(boolean z) {
        this.zzacw.zzge().zzab();
        this.zzaeh |= this.zzadw != z;
        this.zzadw = z;
    }

    public final void zzaa(long j) {
        this.zzacw.zzge().zzab();
        this.zzaeh |= this.zzadx != j;
        this.zzadx = j;
    }

    public final String zzag() {
        this.zzacw.zzge().zzab();
        return this.zzth;
    }

    public final String zzah() {
        this.zzacw.zzge().zzab();
        return this.zzti;
    }

    public final void zzak(String str) {
        this.zzacw.zzge().zzab();
        this.zzaeh |= !zzkc.zzs(this.zzadl, str);
        this.zzadl = str;
    }

    public final void zzal(String str) {
        this.zzacw.zzge().zzab();
        if (TextUtils.isEmpty(str)) {
            str = null;
        }
        this.zzaeh |= !zzkc.zzs(this.zzadm, str);
        this.zzadm = str;
    }

    public final void zzam(String str) {
        this.zzacw.zzge().zzab();
        this.zzaeh |= !zzkc.zzs(this.zzadn, str);
        this.zzadn = str;
    }

    public final void zzan(String str) {
        this.zzacw.zzge().zzab();
        this.zzaeh |= !zzkc.zzs(this.zzado, str);
        this.zzado = str;
    }

    public final void zzao(String str) {
        this.zzacw.zzge().zzab();
        this.zzaeh |= !zzkc.zzs(this.zzadt, str);
        this.zzadt = str;
    }

    public final void zzap(String str) {
        this.zzacw.zzge().zzab();
        this.zzaeh |= !zzkc.zzs(this.zzaeg, str);
        this.zzaeg = str;
    }

    public final void zzd(boolean z) {
        this.zzacw.zzge().zzab();
        this.zzaeh = this.zzady != z;
        this.zzady = z;
    }

    public final void zze(boolean z) {
        this.zzacw.zzge().zzab();
        this.zzaeh = this.zzadz != z;
        this.zzadz = z;
    }

    public final void zzgj() {
        this.zzacw.zzge().zzab();
        this.zzaeh = false;
    }

    public final String zzgk() {
        this.zzacw.zzge().zzab();
        return this.zzadn;
    }

    public final String zzgl() {
        this.zzacw.zzge().zzab();
        return this.zzado;
    }

    public final long zzgm() {
        this.zzacw.zzge().zzab();
        return this.zzadq;
    }

    public final long zzgn() {
        this.zzacw.zzge().zzab();
        return this.zzadr;
    }

    public final long zzgo() {
        this.zzacw.zzge().zzab();
        return this.zzads;
    }

    public final String zzgp() {
        this.zzacw.zzge().zzab();
        return this.zzadt;
    }

    public final long zzgq() {
        this.zzacw.zzge().zzab();
        return this.zzadu;
    }

    public final long zzgr() {
        this.zzacw.zzge().zzab();
        return this.zzadv;
    }

    public final long zzgs() {
        this.zzacw.zzge().zzab();
        return this.zzadp;
    }

    public final long zzgt() {
        this.zzacw.zzge().zzab();
        return this.zzaei;
    }

    public final long zzgu() {
        this.zzacw.zzge().zzab();
        return this.zzaej;
    }

    public final void zzgv() {
        this.zzacw.zzge().zzab();
        long j = this.zzadp + 1;
        if (j > 2147483647L) {
            this.zzacw.zzgf().zziv().zzg("Bundle index overflow. appId", zzfh.zzbl(this.zzti));
            j = 0;
        }
        this.zzaeh = true;
        this.zzadp = j;
    }

    public final long zzgw() {
        this.zzacw.zzge().zzab();
        return this.zzaea;
    }

    public final long zzgx() {
        this.zzacw.zzge().zzab();
        return this.zzaeb;
    }

    public final long zzgy() {
        this.zzacw.zzge().zzab();
        return this.zzaec;
    }

    public final long zzgz() {
        this.zzacw.zzge().zzab();
        return this.zzaed;
    }

    public final long zzha() {
        this.zzacw.zzge().zzab();
        return this.zzaef;
    }

    public final long zzhb() {
        this.zzacw.zzge().zzab();
        return this.zzaee;
    }

    public final String zzhc() {
        this.zzacw.zzge().zzab();
        return this.zzaeg;
    }

    public final String zzhd() {
        this.zzacw.zzge().zzab();
        String str = this.zzaeg;
        zzap(null);
        return str;
    }

    public final long zzhe() {
        this.zzacw.zzge().zzab();
        return this.zzadx;
    }

    public final boolean zzhf() {
        this.zzacw.zzge().zzab();
        return this.zzady;
    }

    public final boolean zzhg() {
        this.zzacw.zzge().zzab();
        return this.zzadz;
    }

    public final void zzm(long j) {
        this.zzacw.zzge().zzab();
        this.zzaeh |= this.zzadq != j;
        this.zzadq = j;
    }

    public final void zzn(long j) {
        this.zzacw.zzge().zzab();
        this.zzaeh |= this.zzadr != j;
        this.zzadr = j;
    }

    public final void zzo(long j) {
        this.zzacw.zzge().zzab();
        this.zzaeh |= this.zzads != j;
        this.zzads = j;
    }

    public final void zzp(long j) {
        this.zzacw.zzge().zzab();
        this.zzaeh |= this.zzadu != j;
        this.zzadu = j;
    }

    public final void zzq(long j) {
        this.zzacw.zzge().zzab();
        this.zzaeh |= this.zzadv != j;
        this.zzadv = j;
    }

    public final void zzr(long j) {
        Preconditions.checkArgument(j >= 0);
        this.zzacw.zzge().zzab();
        this.zzaeh = (this.zzadp != j) | this.zzaeh;
        this.zzadp = j;
    }

    public final void zzs(long j) {
        this.zzacw.zzge().zzab();
        this.zzaeh |= this.zzaei != j;
        this.zzaei = j;
    }

    public final void zzt(long j) {
        this.zzacw.zzge().zzab();
        this.zzaeh |= this.zzaej != j;
        this.zzaej = j;
    }

    public final void zzu(long j) {
        this.zzacw.zzge().zzab();
        this.zzaeh |= this.zzaea != j;
        this.zzaea = j;
    }

    public final void zzv(long j) {
        this.zzacw.zzge().zzab();
        this.zzaeh |= this.zzaeb != j;
        this.zzaeb = j;
    }

    public final void zzw(long j) {
        this.zzacw.zzge().zzab();
        this.zzaeh |= this.zzaec != j;
        this.zzaec = j;
    }

    public final void zzx(long j) {
        this.zzacw.zzge().zzab();
        this.zzaeh |= this.zzaed != j;
        this.zzaed = j;
    }

    public final void zzy(long j) {
        this.zzacw.zzge().zzab();
        this.zzaeh |= this.zzaef != j;
        this.zzaef = j;
    }

    public final void zzz(long j) {
        this.zzacw.zzge().zzab();
        this.zzaeh |= this.zzaee != j;
        this.zzaee = j;
    }
}
