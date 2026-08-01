package com.google.android.gms.tagmanager;

import android.content.Context;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.LinkedBlockingQueue;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzcc extends Thread {
    private static zzcc zza;
    private final LinkedBlockingQueue<Runnable> zzb;
    private volatile boolean zzc;
    private volatile boolean zzd;
    private volatile zzcd zze;
    private final Context zzf;

    private zzcc(Context context) {
        super("GAThread");
        this.zzb = new LinkedBlockingQueue<>();
        this.zzc = false;
        this.zzd = false;
        if (context != null) {
            this.zzf = context.getApplicationContext();
        } else {
            this.zzf = null;
        }
        start();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static zzcc zzb(Context context) {
        if (zza == null) {
            zza = new zzcc(context);
        }
        return zza;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        while (true) {
            try {
                try {
                    Runnable take = this.zzb.take();
                    if (!this.zzc) {
                        take.run();
                    }
                } catch (InterruptedException e) {
                    zzdh.zzb.zzb(e.toString());
                }
            } catch (Exception e2) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                PrintStream printStream = new PrintStream(byteArrayOutputStream);
                e2.printStackTrace(printStream);
                printStream.flush();
                String str = new String(byteArrayOutputStream.toByteArray());
                zzdh.zza(str.length() != 0 ? "Error on Google TagManager Thread: ".concat(str) : new String("Error on Google TagManager Thread: "));
                zzdh.zza("Google TagManager is shutting down.");
                this.zzc = true;
            }
        }
    }

    public final void zze(Runnable runnable) {
        this.zzb.add(runnable);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzf(String str, long j) {
        this.zzb.add(new zzcb(this, this, j, str, null));
    }
}
