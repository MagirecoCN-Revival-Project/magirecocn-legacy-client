package com.google.android.gms.internal.measurement;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzia implements Runnable {
    private final /* synthetic */ zzhl zzaog;
    private final /* synthetic */ boolean zzaom;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzia(zzhl zzhlVar, boolean z) {
        this.zzaog = zzhlVar;
        this.zzaom = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzaog.zzi(this.zzaom);
    }
}
