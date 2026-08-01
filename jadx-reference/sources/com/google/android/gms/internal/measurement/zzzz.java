package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzzz<K> implements Iterator<Map.Entry<K, Object>> {
    private Iterator<Map.Entry<K, Object>> zzbtf;

    public zzzz(Iterator<Map.Entry<K, Object>> it) {
        this.zzbtf = it;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzbtf.hasNext();
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        Map.Entry<K, Object> next = this.zzbtf.next();
        return next.getValue() instanceof zzzw ? new zzzy(next) : next;
    }

    @Override // java.util.Iterator
    public final void remove() {
        this.zzbtf.remove();
    }
}
