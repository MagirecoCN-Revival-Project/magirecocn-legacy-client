package com.google.android.gms.common.api;

import android.os.Bundle;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataBuffer;
import java.util.Iterator;

/* compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* loaded from: classes.dex */
public class DataBufferResponse<T, R extends AbstractDataBuffer<T> & Result> extends Response<R> implements DataBuffer<T> {
    public DataBufferResponse() {
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v0, resolved type: R */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.data.DataBuffer, java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        ((AbstractDataBuffer) getResult()).close();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v0, resolved type: R */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.data.DataBuffer
    public final T get(int i) {
        return (T) ((AbstractDataBuffer) getResult()).get(i);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v0, resolved type: R */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.data.DataBuffer
    public final int getCount() {
        return ((AbstractDataBuffer) getResult()).getCount();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v0, resolved type: R */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.data.DataBuffer
    public final Bundle getMetadata() {
        return ((AbstractDataBuffer) getResult()).getMetadata();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v0, resolved type: R */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.data.DataBuffer
    public final boolean isClosed() {
        return ((AbstractDataBuffer) getResult()).isClosed();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v0, resolved type: R */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.data.DataBuffer, java.lang.Iterable
    public final Iterator<T> iterator() {
        return ((AbstractDataBuffer) getResult()).iterator();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v0, resolved type: R */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.data.DataBuffer, com.google.android.gms.common.api.Releasable
    public final void release() {
        ((AbstractDataBuffer) getResult()).release();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v0, resolved type: R */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.data.DataBuffer
    public final Iterator<T> singleRefIterator() {
        return ((AbstractDataBuffer) getResult()).singleRefIterator();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r1v0, resolved type: com.google.android.gms.common.data.AbstractDataBuffer */
    /* JADX WARN: Incorrect types in method signature: (TR;)V */
    /* JADX WARN: Multi-variable type inference failed */
    public DataBufferResponse(AbstractDataBuffer abstractDataBuffer) {
        super(abstractDataBuffer);
    }
}
