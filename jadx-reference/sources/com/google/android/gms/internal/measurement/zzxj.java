package com.google.android.gms.internal.measurement;

import java.io.PrintStream;
import java.util.List;

/* loaded from: classes.dex */
final class zzxj extends zzxg {
    private final zzxh zzboo = new zzxh();

    @Override // com.google.android.gms.internal.measurement.zzxg
    public final void zza(Throwable th, PrintStream printStream) {
        th.printStackTrace(printStream);
        List<Throwable> zza = this.zzboo.zza(th, false);
        if (zza == null) {
            return;
        }
        synchronized (zza) {
            for (Throwable th2 : zza) {
                printStream.print("Suppressed: ");
                th2.printStackTrace(printStream);
            }
        }
    }
}
