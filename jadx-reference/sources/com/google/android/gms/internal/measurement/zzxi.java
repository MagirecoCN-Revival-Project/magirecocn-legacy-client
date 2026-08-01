package com.google.android.gms.internal.measurement;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Objects;

/* loaded from: classes.dex */
final class zzxi extends WeakReference<Throwable> {
    private final int zzbon;

    public zzxi(Throwable th, ReferenceQueue<Throwable> referenceQueue) {
        super(th, null);
        Objects.requireNonNull(th, "The referent cannot be null");
        this.zzbon = System.identityHashCode(th);
    }

    public final boolean equals(Object obj) {
        if (obj != null && obj.getClass() == getClass()) {
            if (this == obj) {
                return true;
            }
            zzxi zzxiVar = (zzxi) obj;
            if (this.zzbon == zzxiVar.zzbon && get() == zzxiVar.get()) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return this.zzbon;
    }
}
