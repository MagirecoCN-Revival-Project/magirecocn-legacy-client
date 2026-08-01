package com.google.android.gms.internal.measurement;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public abstract class zzjr extends zzjq {
    private boolean zzvo;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzjr(zzjs zzjsVar) {
        super(zzjsVar);
        this.zzajy.zzb(this);
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

    public final void zzm() {
        if (this.zzvo) {
            throw new IllegalStateException("Can't initialize twice");
        }
        zzhh();
        this.zzajy.zzli();
        this.zzvo = true;
    }
}
