package com.squareup.tape;

import com.squareup.tape.FileObjectQueue;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/* loaded from: classes.dex */
public class SerializedConverter<T extends Serializable> implements FileObjectQueue.Converter<T> {
    private T deserialize(InputStream inputStream) throws IOException {
        try {
            return (T) new ObjectInputStream(new BufferedInputStream(inputStream, 1024)).readUnshared();
        } catch (ClassNotFoundException e) {
            throw new AssertionError(e);
        }
    }

    /* JADX DEBUG: Method merged with bridge method: from([B)Ljava/lang/Object; */
    @Override // com.squareup.tape.FileObjectQueue.Converter
    public T from(byte[] bArr) throws IOException {
        return deserialize(new ByteArrayInputStream(bArr));
    }

    /* JADX DEBUG: Method merged with bridge method: toStream(Ljava/lang/Object;Ljava/io/OutputStream;)V */
    @Override // com.squareup.tape.FileObjectQueue.Converter
    public void toStream(T t, OutputStream outputStream) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeUnshared(t);
        objectOutputStream.close();
    }
}
