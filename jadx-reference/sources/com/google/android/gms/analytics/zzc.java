package com.google.android.gms.analytics;

import android.content.BroadcastReceiver;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzc implements Runnable {
    final /* synthetic */ BroadcastReceiver.PendingResult zza;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzc(CampaignTrackingReceiver campaignTrackingReceiver, BroadcastReceiver.PendingResult pendingResult) {
        this.zza = pendingResult;
    }

    @Override // java.lang.Runnable
    public final void run() {
        BroadcastReceiver.PendingResult pendingResult = this.zza;
        if (pendingResult != null) {
            pendingResult.finish();
        }
    }
}
