package com.google.android.gms.internal.measurement;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzhp implements Callable<String> {
    private final /* synthetic */ zzhl zzaog;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzhp(zzhl zzhlVar) {
        this.zzaog = zzhlVar;
    }

    /* JADX DEBUG: Return type fixed from 'java.lang.Object' to match base method */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x006f  */
    @Override // java.util.concurrent.Callable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final /* synthetic */ String call() throws Exception {
        zzfj zzis;
        String str;
        String zzjh = this.zzaog.zzgg().zzjh();
        if (zzjh != null) {
            return zzjh;
        }
        zzhl zzfv = this.zzaog.zzfv();
        String str2 = null;
        if (zzfv.zzge().zzjr()) {
            zzis = zzfv.zzgf().zzis();
            str = "Cannot retrieve app instance id from analytics worker thread";
        } else {
            if (!zzec.isMainThread()) {
                long elapsedRealtime = zzfv.zzbt().elapsedRealtime();
                String zzae = zzfv.zzae(120000L);
                long elapsedRealtime2 = zzfv.zzbt().elapsedRealtime() - elapsedRealtime;
                str2 = (zzae != null || elapsedRealtime2 >= 120000) ? zzae : zzfv.zzae(120000 - elapsedRealtime2);
                if (str2 != null) {
                    throw new TimeoutException();
                }
                this.zzaog.zzgg().zzbq(str2);
                return str2;
            }
            zzis = zzfv.zzgf().zzis();
            str = "Cannot retrieve app instance id from main thread";
        }
        zzis.log(str);
        if (str2 != null) {
        }
    }
}
