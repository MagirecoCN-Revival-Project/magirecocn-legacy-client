package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* loaded from: classes.dex */
final class zzabg<K, V> implements Iterator<Map.Entry<K, V>> {
    private int pos;
    private final /* synthetic */ zzaba zzbup;
    private boolean zzbuq;
    private Iterator<Map.Entry<K, V>> zzbur;

    private zzabg(zzaba zzabaVar) {
        this.zzbup = zzabaVar;
        this.pos = -1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public /* synthetic */ zzabg(zzaba zzabaVar, zzabb zzabbVar) {
        this(zzabaVar);
    }

    private final Iterator<Map.Entry<K, V>> zzuy() {
        Map map;
        if (this.zzbur == null) {
            map = this.zzbup.zzbuj;
            this.zzbur = map.entrySet().iterator();
        }
        return this.zzbur;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        List list;
        Map map;
        int i = this.pos + 1;
        list = this.zzbup.zzbui;
        if (i >= list.size()) {
            map = this.zzbup.zzbuj;
            if (map.isEmpty() || !zzuy().hasNext()) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        List list;
        Map.Entry<K, V> next;
        List list2;
        this.zzbuq = true;
        int i = this.pos + 1;
        this.pos = i;
        list = this.zzbup.zzbui;
        if (i < list.size()) {
            list2 = this.zzbup.zzbui;
            next = (Map.Entry<K, V>) list2.get(this.pos);
        } else {
            next = zzuy().next();
        }
        return next;
    }

    @Override // java.util.Iterator
    public final void remove() {
        List list;
        if (!this.zzbuq) {
            throw new IllegalStateException("remove() was called before next()");
        }
        this.zzbuq = false;
        this.zzbup.zzuu();
        int i = this.pos;
        list = this.zzbup.zzbui;
        if (i >= list.size()) {
            zzuy().remove();
            return;
        }
        zzaba zzabaVar = this.zzbup;
        int i2 = this.pos;
        this.pos = i2 - 1;
        zzabaVar.zzai(i2);
    }
}
