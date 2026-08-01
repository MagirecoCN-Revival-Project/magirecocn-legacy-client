package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* loaded from: classes.dex */
final class zzyz implements Iterator {
    private final int limit;
    private int position = 0;
    private final /* synthetic */ zzyy zzbrj;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzyz(zzyy zzyyVar) {
        this.zzbrj = zzyyVar;
        this.limit = zzyyVar.size();
    }

    private final byte nextByte() {
        try {
            zzyy zzyyVar = this.zzbrj;
            int i = this.position;
            this.position = i + 1;
            return zzyyVar.zzae(i);
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.position < this.limit;
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        return Byte.valueOf(nextByte());
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
