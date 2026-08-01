package com.google.firebase.iid;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.tasks.Task;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/* loaded from: classes.dex */
public final class zzv {
    private static zzv zzbm;
    private final ScheduledExecutorService zzbn;
    private zzx zzbo = new zzx(this);
    private int zzbp = 1;
    private final Context zzz;

    private zzv(Context context, ScheduledExecutorService scheduledExecutorService) {
        this.zzbn = scheduledExecutorService;
        this.zzz = context.getApplicationContext();
    }

    private final synchronized <T> Task<T> zza(zzae<T> zzaeVar) {
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            String valueOf = String.valueOf(zzaeVar);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 9);
            sb.append("Queueing ");
            sb.append(valueOf);
            Log.d("MessengerIpcClient", sb.toString());
        }
        if (!this.zzbo.zzb(zzaeVar)) {
            zzx zzxVar = new zzx(this);
            this.zzbo = zzxVar;
            zzxVar.zzb(zzaeVar);
        }
        return zzaeVar.zzbz.getTask();
    }

    public static synchronized zzv zzc(Context context) {
        zzv zzvVar;
        synchronized (zzv.class) {
            if (zzbm == null) {
                zzbm = new zzv(context, Executors.newSingleThreadScheduledExecutor());
            }
            zzvVar = zzbm;
        }
        return zzvVar;
    }

    private final synchronized int zzr() {
        int i;
        i = this.zzbp;
        this.zzbp = i + 1;
        return i;
    }

    public final Task<Void> zza(int i, Bundle bundle) {
        return zza(new zzad(zzr(), 2, bundle));
    }

    public final Task<Bundle> zzb(int i, Bundle bundle) {
        return zza(new zzag(zzr(), 1, bundle));
    }
}
