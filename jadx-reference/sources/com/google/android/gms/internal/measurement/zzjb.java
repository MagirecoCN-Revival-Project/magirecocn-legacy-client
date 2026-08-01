package com.google.android.gms.internal.measurement;

import android.content.ComponentName;
import android.content.Context;

/* loaded from: classes.dex */
final class zzjb implements Runnable {
    private final /* synthetic */ zzix zzapw;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzjb(zzix zzixVar) {
        this.zzapw = zzixVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzij zzijVar = this.zzapw.zzapn;
        Context context = this.zzapw.zzapn.getContext();
        this.zzapw.zzapn.zzgi();
        zzijVar.onServiceDisconnected(new ComponentName(context, "com.google.android.gms.measurement.AppMeasurementService"));
    }
}
