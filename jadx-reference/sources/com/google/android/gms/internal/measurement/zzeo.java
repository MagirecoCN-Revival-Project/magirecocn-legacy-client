package com.google.android.gms.internal.measurement;

import android.os.Handler;
import com.google.android.gms.common.internal.Preconditions;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public abstract class zzeo {
    private static volatile Handler handler;
    private final zzhj zzafk;
    private final Runnable zzyd;
    private volatile long zzye;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzeo(zzhj zzhjVar) {
        Preconditions.checkNotNull(zzhjVar);
        this.zzafk = zzhjVar;
        this.zzyd = new zzep(this, zzhjVar);
    }

    private final Handler getHandler() {
        Handler handler2;
        if (handler != null) {
            return handler;
        }
        synchronized (zzeo.class) {
            if (handler == null) {
                handler = new Handler(this.zzafk.getContext().getMainLooper());
            }
            handler2 = handler;
        }
        return handler2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ long zza(zzeo zzeoVar, long j) {
        zzeoVar.zzye = 0L;
        return 0L;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void cancel() {
        this.zzye = 0L;
        getHandler().removeCallbacks(this.zzyd);
    }

    public abstract void run();

    public final boolean zzef() {
        return this.zzye != 0;
    }

    public final void zzh(long j) {
        cancel();
        if (j >= 0) {
            this.zzye = this.zzafk.zzbt().currentTimeMillis();
            if (getHandler().postDelayed(this.zzyd, j)) {
                return;
            }
            this.zzafk.zzgf().zzis().zzg("Failed to schedule delayed post. time", Long.valueOf(j));
        }
    }
}
