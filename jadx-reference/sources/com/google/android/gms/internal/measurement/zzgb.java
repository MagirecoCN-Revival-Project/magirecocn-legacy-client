package com.google.android.gms.internal.measurement;

/* loaded from: classes.dex */
final class zzgb implements Runnable {
    private final /* synthetic */ zzga zzali;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzgb(zzga zzgaVar) {
        this.zzali = zzgaVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzali.zzalh.zzc(this.zzali.zzalh.zzjq());
    }
}
