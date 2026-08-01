package com.google.android.gms.internal.measurement;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzip extends zzeo {
    private final /* synthetic */ zzij zzapn;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzip(zzij zzijVar, zzhj zzhjVar) {
        super(zzhjVar);
        this.zzapn = zzijVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzeo
    public final void run() {
        this.zzapn.zzgf().zziv().log("Tasks have been queued for a long time");
    }
}
