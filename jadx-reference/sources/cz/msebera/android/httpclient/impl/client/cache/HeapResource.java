package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.client.cache.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/* loaded from: classes.dex */
public class HeapResource implements Resource {
    private static final long serialVersionUID = -2078599905620463394L;
    private final byte[] b;

    @Override // cz.msebera.android.httpclient.client.cache.Resource
    public void dispose() {
    }

    public HeapResource(byte[] bArr) {
        this.b = bArr;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public byte[] getByteArray() {
        return this.b;
    }

    @Override // cz.msebera.android.httpclient.client.cache.Resource
    public InputStream getInputStream() {
        return new ByteArrayInputStream(this.b);
    }

    @Override // cz.msebera.android.httpclient.client.cache.Resource
    public long length() {
        return this.b.length;
    }
}
