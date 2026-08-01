package backtraceio.library.interfaces;

import java.io.IOException;

/* loaded from: classes.dex */
public interface DatabaseRecordWriter {
    String write(Object data, String prefix) throws IOException;

    String write(byte[] data, String prefix) throws IOException;
}
