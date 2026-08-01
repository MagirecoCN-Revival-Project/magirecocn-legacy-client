package com.google.android.gms.internal.measurement;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.measurement.AppMeasurement;
import kotlinx.coroutines.DebugKt;

/* loaded from: classes.dex */
final class zzge implements Runnable {
    private final /* synthetic */ Context val$context;
    private final /* synthetic */ zzgm zzalk;
    private final /* synthetic */ zzfh zzall;
    private final /* synthetic */ long zzalm;
    private final /* synthetic */ Bundle zzaln;
    private final /* synthetic */ BroadcastReceiver.PendingResult zzqu;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzge(zzgc zzgcVar, zzgm zzgmVar, long j, Bundle bundle, Context context, zzfh zzfhVar, BroadcastReceiver.PendingResult pendingResult) {
        this.zzalk = zzgmVar;
        this.zzalm = j;
        this.zzaln = bundle;
        this.val$context = context;
        this.zzall = zzfhVar;
        this.zzqu = pendingResult;
    }

    @Override // java.lang.Runnable
    public final void run() {
        long j = this.zzalk.zzgg().zzaki.get();
        long j2 = this.zzalm;
        if (j > 0 && (j2 >= j || j2 <= 0)) {
            j2 = j - 1;
        }
        if (j2 > 0) {
            this.zzaln.putLong("click_timestamp", j2);
        }
        this.zzaln.putString("_cis", "referrer broadcast");
        AppMeasurement.getInstance(this.val$context).logEventInternal(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_cmp", this.zzaln);
        this.zzall.zziz().log("Install campaign recorded");
        BroadcastReceiver.PendingResult pendingResult = this.zzqu;
        if (pendingResult != null) {
            pendingResult.finish();
        }
    }
}
