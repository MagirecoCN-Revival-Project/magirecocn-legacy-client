package com.google.android.gms.internal.measurement;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzjp extends zzeo {
    private final /* synthetic */ zzjs zzaqc;
    private final /* synthetic */ zzjo zzaqi;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzjp(zzjo zzjoVar, zzhj zzhjVar, zzjs zzjsVar) {
        super(zzhjVar);
        this.zzaqi = zzjoVar;
        this.zzaqc = zzjsVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzeo
    public final void run() {
        this.zzaqi.cancel();
        this.zzaqi.zzgf().zziz().log("Starting upload from DelayedRunnable");
        this.zzaqc.zzlb();
    }
}
