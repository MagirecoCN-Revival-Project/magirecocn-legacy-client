package com.google.android.gms.internal.measurement;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public abstract class zzhi extends zzhh {
    private boolean zzvo;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzhi(zzgm zzgmVar) {
        super(zzgmVar);
        this.zzacw.zzb(this);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean isInitialized() {
        return this.zzvo;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void zzch() {
        if (!isInitialized()) {
            throw new IllegalStateException("Not initialized");
        }
    }

    protected abstract boolean zzhh();

    protected void zzin() {
    }

    public final void zzke() {
        if (this.zzvo) {
            throw new IllegalStateException("Can't initialize twice");
        }
        zzin();
        this.zzacw.zzkc();
        this.zzvo = true;
    }

    public final void zzm() {
        if (this.zzvo) {
            throw new IllegalStateException("Can't initialize twice");
        }
        if (zzhh()) {
            return;
        }
        this.zzacw.zzkc();
        this.zzvo = true;
    }
}
