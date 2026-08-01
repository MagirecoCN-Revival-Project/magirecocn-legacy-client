package com.google.android.gms.internal.measurement;

import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzgk<V> extends FutureTask<V> implements Comparable<zzgk> {
    private final String zzamh;
    private final /* synthetic */ zzgh zzami;
    private final long zzamj;
    final boolean zzamk;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzgk(zzgh zzghVar, Runnable runnable, boolean z, String str) {
        super(runnable, null);
        AtomicLong atomicLong;
        this.zzami = zzghVar;
        Preconditions.checkNotNull(str);
        atomicLong = zzgh.zzamg;
        long andIncrement = atomicLong.getAndIncrement();
        this.zzamj = andIncrement;
        this.zzamh = str;
        this.zzamk = false;
        if (andIncrement == Long.MAX_VALUE) {
            zzghVar.zzgf().zzis().log("Tasks index overflow");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzgk(zzgh zzghVar, Callable<V> callable, boolean z, String str) {
        super(callable);
        AtomicLong atomicLong;
        this.zzami = zzghVar;
        Preconditions.checkNotNull(str);
        atomicLong = zzgh.zzamg;
        long andIncrement = atomicLong.getAndIncrement();
        this.zzamj = andIncrement;
        this.zzamh = str;
        this.zzamk = z;
        if (andIncrement == Long.MAX_VALUE) {
            zzghVar.zzgf().zzis().log("Tasks index overflow");
        }
    }

    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object] */
    @Override // java.lang.Comparable
    public final /* synthetic */ int compareTo(zzgk zzgkVar) {
        zzgk zzgkVar2 = zzgkVar;
        boolean z = this.zzamk;
        if (z != zzgkVar2.zzamk) {
            return z ? -1 : 1;
        }
        long j = this.zzamj;
        long j2 = zzgkVar2.zzamj;
        if (j < j2) {
            return -1;
        }
        if (j > j2) {
            return 1;
        }
        this.zzami.zzgf().zzit().zzg("Two tasks share the same index. index", Long.valueOf(this.zzamj));
        return 0;
    }

    @Override // java.util.concurrent.FutureTask
    protected final void setException(Throwable th) {
        this.zzami.zzgf().zzis().zzg(this.zzamh, th);
        if (th instanceof zzgi) {
            Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), th);
        }
        super.setException(th);
    }
}
