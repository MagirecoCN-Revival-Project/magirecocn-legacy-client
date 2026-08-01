package com.google.android.gms.internal.measurement;

import java.io.PrintStream;

/* loaded from: classes.dex */
public final class zzxf {
    private static final zzxg zzboi;
    private static final int zzboj;

    /* loaded from: classes.dex */
    static final class zza extends zzxg {
        zza() {
        }

        @Override // com.google.android.gms.internal.measurement.zzxg
        public final void zza(Throwable th, PrintStream printStream) {
            th.printStackTrace(printStream);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0068  */
    static {
        Integer num;
        zzxg zzaVar;
        try {
            num = zzsm();
        } catch (Throwable th) {
            th = th;
            num = null;
        }
        if (num != null) {
            try {
            } catch (Throwable th2) {
                th = th2;
                PrintStream printStream = System.err;
                String name = zza.class.getName();
                StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 132);
                sb.append("An error has occured when initializing the try-with-resources desuguring strategy. The default strategy ");
                sb.append(name);
                sb.append("will be used. The error is: ");
                printStream.println(sb.toString());
                th.printStackTrace(System.err);
                zzaVar = new zza();
                zzboi = zzaVar;
                zzboj = num != null ? num.intValue() : 1;
            }
            if (num.intValue() >= 19) {
                zzaVar = new zzxk();
                zzboi = zzaVar;
                zzboj = num != null ? num.intValue() : 1;
            }
        }
        zzaVar = Boolean.getBoolean("com.google.devtools.build.android.desugar.runtime.twr_disable_mimic") ^ true ? new zzxj() : new zza();
        zzboi = zzaVar;
        zzboj = num != null ? num.intValue() : 1;
    }

    public static void zza(Throwable th, PrintStream printStream) {
        zzboi.zza(th, printStream);
    }

    private static Integer zzsm() {
        try {
            return (Integer) Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
        } catch (Exception e) {
            System.err.println("Failed to retrieve value from android.os.Build$VERSION.SDK_INT due to the following exception.");
            e.printStackTrace(System.err);
            return null;
        }
    }
}
