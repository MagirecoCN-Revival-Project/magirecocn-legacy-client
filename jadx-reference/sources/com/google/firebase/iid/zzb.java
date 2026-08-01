package com.google.firebase.iid;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import androidx.legacy.content.WakefulBroadcastReceiver;
import com.google.android.gms.common.util.concurrent.NamedThreadFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes.dex */
public abstract class zzb extends Service {
    private final Object lock;
    final ExecutorService zzl;
    private Binder zzm;
    private int zzn;
    private int zzo;

    public zzb() {
        String valueOf = String.valueOf(getClass().getSimpleName());
        this.zzl = Executors.newSingleThreadExecutor(new NamedThreadFactory(valueOf.length() != 0 ? "Firebase-".concat(valueOf) : new String("Firebase-")));
        this.lock = new Object();
        this.zzo = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(Intent intent) {
        if (intent != null) {
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
        synchronized (this.lock) {
            int i = this.zzo - 1;
            this.zzo = i;
            if (i == 0) {
                stopSelfResult(this.zzn);
            }
        }
    }

    @Override // android.app.Service
    public final synchronized IBinder onBind(Intent intent) {
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            Log.d("EnhancedIntentService", "Service received bind request");
        }
        if (this.zzm == null) {
            this.zzm = new zzf(this);
        }
        return this.zzm;
    }

    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i, int i2) {
        synchronized (this.lock) {
            this.zzn = i2;
            this.zzo++;
        }
        Intent zzb = zzb(intent);
        if (zzb == null) {
            zza(intent);
            return 2;
        }
        if (zzc(zzb)) {
            zza(intent);
            return 2;
        }
        this.zzl.execute(new zzc(this, zzb, intent));
        return 3;
    }

    protected Intent zzb(Intent intent) {
        return intent;
    }

    public boolean zzc(Intent intent) {
        return false;
    }

    public abstract void zzd(Intent intent);
}
