package com.google.android.gms.internal.measurement;

import android.content.Context;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import java.lang.Thread;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class zzgh extends zzhi {
    private static final AtomicLong zzamg = new AtomicLong(Long.MIN_VALUE);
    private ExecutorService zzalw;
    private zzgl zzalx;
    private zzgl zzaly;
    private final PriorityBlockingQueue<zzgk<?>> zzalz;
    private final BlockingQueue<zzgk<?>> zzama;
    private final Thread.UncaughtExceptionHandler zzamb;
    private final Thread.UncaughtExceptionHandler zzamc;
    private final Object zzamd;
    private final Semaphore zzame;
    private volatile boolean zzamf;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzgh(zzgm zzgmVar) {
        super(zzgmVar);
        this.zzamd = new Object();
        this.zzame = new Semaphore(2);
        this.zzalz = new PriorityBlockingQueue<>();
        this.zzama = new LinkedBlockingQueue();
        this.zzamb = new zzgj(this, "Thread death: Uncaught exception on worker thread");
        this.zzamc = new zzgj(this, "Thread death: Uncaught exception on network thread");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ zzgl zza(zzgh zzghVar, zzgl zzglVar) {
        zzghVar.zzalx = null;
        return null;
    }

    private final void zza(zzgk<?> zzgkVar) {
        synchronized (this.zzamd) {
            this.zzalz.add(zzgkVar);
            zzgl zzglVar = this.zzalx;
            if (zzglVar == null) {
                zzgl zzglVar2 = new zzgl(this, "Measurement Worker", this.zzalz);
                this.zzalx = zzglVar2;
                zzglVar2.setUncaughtExceptionHandler(this.zzamb);
                this.zzalx.start();
            } else {
                zzglVar.zzju();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ zzgl zzb(zzgh zzghVar, zzgl zzglVar) {
        zzghVar.zzaly = null;
        return null;
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final <T> T zza(AtomicReference<T> atomicReference, long j, String str, Runnable runnable) {
        synchronized (atomicReference) {
            zzge().zzc(runnable);
            try {
                atomicReference.wait(15000L);
            } catch (InterruptedException unused) {
                zzfj zziv = zzgf().zziv();
                String valueOf = String.valueOf(str);
                zziv.log(valueOf.length() != 0 ? "Interrupted waiting for ".concat(valueOf) : new String("Interrupted waiting for "));
                return null;
            }
        }
        T t = atomicReference.get();
        if (t == null) {
            zzfj zziv2 = zzgf().zziv();
            String valueOf2 = String.valueOf(str);
            zziv2.log(valueOf2.length() != 0 ? "Timed out waiting for ".concat(valueOf2) : new String("Timed out waiting for "));
        }
        return t;
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final void zzab() {
        if (Thread.currentThread() != this.zzalx) {
            throw new IllegalStateException("Call expected from worker thread");
        }
    }

    public final <V> Future<V> zzb(Callable<V> callable) throws IllegalStateException {
        zzch();
        Preconditions.checkNotNull(callable);
        zzgk<?> zzgkVar = new zzgk<>(this, (Callable<?>) callable, false, "Task exception on worker thread");
        if (Thread.currentThread() == this.zzalx) {
            if (!this.zzalz.isEmpty()) {
                zzgf().zziv().log("Callable skipped the worker queue.");
            }
            zzgkVar.run();
        } else {
            zza(zzgkVar);
        }
        return zzgkVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ Clock zzbt() {
        return super.zzbt();
    }

    public final <V> Future<V> zzc(Callable<V> callable) throws IllegalStateException {
        zzch();
        Preconditions.checkNotNull(callable);
        zzgk<?> zzgkVar = new zzgk<>(this, (Callable<?>) callable, true, "Task exception on worker thread");
        if (Thread.currentThread() == this.zzalx) {
            zzgkVar.run();
        } else {
            zza(zzgkVar);
        }
        return zzgkVar;
    }

    public final void zzc(Runnable runnable) throws IllegalStateException {
        zzch();
        Preconditions.checkNotNull(runnable);
        zza(new zzgk<>(this, runnable, false, "Task exception on worker thread"));
    }

    public final void zzd(Runnable runnable) throws IllegalStateException {
        zzch();
        Preconditions.checkNotNull(runnable);
        zzgk<?> zzgkVar = new zzgk<>(this, runnable, false, "Task exception on network thread");
        synchronized (this.zzamd) {
            this.zzama.add(zzgkVar);
            zzgl zzglVar = this.zzaly;
            if (zzglVar == null) {
                zzgl zzglVar2 = new zzgl(this, "Measurement Network", this.zzama);
                this.zzaly = zzglVar2;
                zzglVar2.setUncaughtExceptionHandler(this.zzamc);
                this.zzaly.start();
            } else {
                zzglVar.zzju();
            }
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ void zzfr() {
        super.zzfr();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ void zzfs() {
        super.zzfs();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final void zzft() {
        if (Thread.currentThread() != this.zzaly) {
            throw new IllegalStateException("Call expected from network thread");
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzdu zzfu() {
        return super.zzfu();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzhl zzfv() {
        return super.zzfv();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzfc zzfw() {
        return super.zzfw();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzeq zzfx() {
        return super.zzfx();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzij zzfy() {
        return super.zzfy();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzig zzfz() {
        return super.zzfz();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzfd zzga() {
        return super.zzga();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzff zzgb() {
        return super.zzgb();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzkc zzgc() {
        return super.zzgc();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzji zzgd() {
        return super.zzgd();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ zzgh zzge() {
        return super.zzge();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ zzfh zzgf() {
        return super.zzgf();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzfs zzgg() {
        return super.zzgg();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzeg zzgh() {
        return super.zzgh();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ zzec zzgi() {
        return super.zzgi();
    }

    @Override // com.google.android.gms.internal.measurement.zzhi
    protected final boolean zzhh() {
        return false;
    }

    public final boolean zzjr() {
        return Thread.currentThread() == this.zzalx;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final ExecutorService zzjs() {
        ExecutorService executorService;
        synchronized (this.zzamd) {
            if (this.zzalw == null) {
                this.zzalw = new ThreadPoolExecutor(0, 1, 30L, TimeUnit.SECONDS, new ArrayBlockingQueue(100));
            }
            executorService = this.zzalw;
        }
        return executorService;
    }
}
