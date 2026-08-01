package com.google.android.gms.internal.measurement;

import android.os.Process;
import androidx.work.WorkRequest;
import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzgl extends Thread {
    private final /* synthetic */ zzgh zzami;
    private final Object zzaml;
    private final BlockingQueue<zzgk<?>> zzamm;

    public zzgl(zzgh zzghVar, String str, BlockingQueue<zzgk<?>> blockingQueue) {
        this.zzami = zzghVar;
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(blockingQueue);
        this.zzaml = new Object();
        this.zzamm = blockingQueue;
        setName(str);
    }

    private final void zza(InterruptedException interruptedException) {
        this.zzami.zzgf().zziv().zzg(String.valueOf(getName()).concat(" was interrupted"), interruptedException);
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        Object obj;
        Semaphore semaphore;
        Object obj2;
        zzgl zzglVar;
        zzgl zzglVar2;
        Object obj3;
        Object obj4;
        Semaphore semaphore2;
        Object obj5;
        zzgl zzglVar3;
        zzgl zzglVar4;
        boolean z;
        Semaphore semaphore3;
        boolean z2 = false;
        while (!z2) {
            try {
                semaphore3 = this.zzami.zzame;
                semaphore3.acquire();
                z2 = true;
            } catch (InterruptedException e) {
                zza(e);
            }
        }
        try {
            int threadPriority = Process.getThreadPriority(Process.myTid());
            while (true) {
                zzgk<?> poll = this.zzamm.poll();
                if (poll == null) {
                    synchronized (this.zzaml) {
                        if (this.zzamm.peek() == null) {
                            z = this.zzami.zzamf;
                            if (!z) {
                                try {
                                    this.zzaml.wait(WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS);
                                } catch (InterruptedException e2) {
                                    zza(e2);
                                }
                            }
                        }
                    }
                    obj3 = this.zzami.zzamd;
                    synchronized (obj3) {
                        if (this.zzamm.peek() == null) {
                            break;
                        }
                    }
                } else {
                    Process.setThreadPriority(poll.zzamk ? threadPriority : 10);
                    poll.run();
                }
            }
            obj4 = this.zzami.zzamd;
            synchronized (obj4) {
                semaphore2 = this.zzami.zzame;
                semaphore2.release();
                obj5 = this.zzami.zzamd;
                obj5.notifyAll();
                zzglVar3 = this.zzami.zzalx;
                if (this == zzglVar3) {
                    zzgh.zza(this.zzami, null);
                } else {
                    zzglVar4 = this.zzami.zzaly;
                    if (this == zzglVar4) {
                        zzgh.zzb(this.zzami, null);
                    } else {
                        this.zzami.zzgf().zzis().log("Current scheduler thread is neither worker nor network");
                    }
                }
            }
        } catch (Throwable th) {
            obj = this.zzami.zzamd;
            synchronized (obj) {
                semaphore = this.zzami.zzame;
                semaphore.release();
                obj2 = this.zzami.zzamd;
                obj2.notifyAll();
                zzglVar = this.zzami.zzalx;
                if (this != zzglVar) {
                    zzglVar2 = this.zzami.zzaly;
                    if (this == zzglVar2) {
                        zzgh.zzb(this.zzami, null);
                    } else {
                        this.zzami.zzgf().zzis().log("Current scheduler thread is neither worker nor network");
                    }
                } else {
                    zzgh.zza(this.zzami, null);
                }
                throw th;
            }
        }
    }

    public final void zzju() {
        synchronized (this.zzaml) {
            this.zzaml.notifyAll();
        }
    }
}
