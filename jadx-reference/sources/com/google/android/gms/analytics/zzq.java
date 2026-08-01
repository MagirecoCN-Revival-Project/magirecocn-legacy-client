package com.google.android.gms.analytics;

import android.os.Process;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzq extends Thread {
    /* JADX INFO: Access modifiers changed from: package-private */
    public zzq(Runnable runnable, String str) {
        super(runnable, str);
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        Process.setThreadPriority(10);
        super.run();
    }
}
