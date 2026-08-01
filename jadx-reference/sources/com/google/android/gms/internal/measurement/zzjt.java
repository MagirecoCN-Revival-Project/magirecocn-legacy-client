package com.google.android.gms.internal.measurement;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzjt implements Runnable {
    private final /* synthetic */ zzjx zzare;
    private final /* synthetic */ zzjs zzarf;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzjt(zzjs zzjsVar, zzjx zzjxVar) {
        this.zzarf = zzjsVar;
        this.zzare = zzjxVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzarf.zza(this.zzare);
        this.zzarf.start();
    }
}
