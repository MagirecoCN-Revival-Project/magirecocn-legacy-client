package com.google.android.gms.internal.gtm;

import java.util.AbstractList;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzvg<F, T> extends AbstractList<T> {
    private final List<F> zza;
    private final zzvf<F, T> zzb;

    public zzvg(List<F> list, zzvf<F, T> zzvfVar) {
        this.zza = list;
        this.zzb = zzvfVar;
    }

    @Override // java.util.AbstractList, java.util.List
    public final T get(int i) {
        return (T) this.zzb.zzb(this.zza.get(i));
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zza.size();
    }
}
