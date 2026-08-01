package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import java.util.Iterator;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzeu implements Iterator<String> {
    private Iterator<String> zzaga;
    private final /* synthetic */ zzet zzagb;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzeu(zzet zzetVar) {
        Bundle bundle;
        this.zzagb = zzetVar;
        bundle = zzetVar.zzafz;
        this.zzaga = bundle.keySet().iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzaga.hasNext();
    }

    /* JADX DEBUG: Return type fixed from 'java.lang.Object' to match base method */
    @Override // java.util.Iterator
    public final /* synthetic */ String next() {
        return this.zzaga.next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Remove not supported");
    }
}
