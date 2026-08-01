package com.google.android.gms.internal.gtm;

import java.lang.Thread;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzbu implements Thread.UncaughtExceptionHandler {
    final /* synthetic */ zzbv zza;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzbu(zzbv zzbvVar) {
        this.zza = zzbvVar;
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public final void uncaughtException(Thread thread, Throwable th) {
        zzfb zzn = this.zza.zzn();
        if (zzn != null) {
            zzn.zzK("Job execution failed", th);
        }
    }
}
