package com.google.android.gms.internal.measurement;

/* loaded from: classes.dex */
final class zzjm implements Runnable {
    private final /* synthetic */ long zzadj;
    private final /* synthetic */ zzji zzaqg;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzjm(zzji zzjiVar, long j) {
        this.zzaqg = zzjiVar;
        this.zzadj = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzaqg.zzag(this.zzadj);
    }
}
