package com.google.android.gms.tagmanager;

import android.os.Handler;
import android.os.Message;
import backtraceio.library.services.BacktraceMetrics;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzfc implements Handler.Callback {
    final /* synthetic */ zzfe zza;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzfc(zzfe zzfeVar) {
        this.zza = zzfeVar;
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        Object obj;
        boolean zzm;
        if (message.what == 1) {
            obj = zzff.zza;
            if (obj.equals(message.obj)) {
                this.zza.zza.zza();
                zzm = this.zza.zza.zzm();
                if (!zzm) {
                    this.zza.zzc(BacktraceMetrics.defaultTimeIntervalMs);
                }
            }
        }
        return true;
    }
}
