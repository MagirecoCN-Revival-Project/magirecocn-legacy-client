package backtraceio.library.models.database;

import backtraceio.library.common.BacktraceSerializeHelper;
import backtraceio.library.interfaces.DatabaseRecordWriter;
import backtraceio.library.logger.BacktraceLogger;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/* loaded from: classes.dex */
public class BacktraceDatabaseRecordWriter implements DatabaseRecordWriter {
    private static final transient String LOG_TAG = "BacktraceDatabaseRecordWriter";
    private final String _destinationPath;

    public BacktraceDatabaseRecordWriter(String path) {
        this._destinationPath = path;
    }

    @Override // backtraceio.library.interfaces.DatabaseRecordWriter
    public String write(Object data, String prefix) throws IOException {
        return write(toJsonFile(data).getBytes(StandardCharsets.UTF_8), prefix);
    }

    @Override // backtraceio.library.interfaces.DatabaseRecordWriter
    public String write(byte[] data, String prefix) throws IOException {
        String format = String.format("%s.json", prefix);
        String absolutePath = new File(this._destinationPath, String.format("temp_%s", format)).getAbsolutePath();
        saveTemporaryFile(absolutePath, data);
        String absolutePath2 = new File(this._destinationPath, format).getAbsolutePath();
        saveValidRecord(absolutePath, absolutePath2);
        return absolutePath2;
    }

    private String toJsonFile(Object data) {
        if (data == null) {
            BacktraceLogger.w(LOG_TAG, "Passed object to serialization is null");
            return "";
        }
        return BacktraceSerializeHelper.toJson(data);
    }

    private void saveValidRecord(String sourcePath, String destinationPath) throws IOException {
        if (new File(sourcePath).renameTo(new File(destinationPath))) {
            return;
        }
        BacktraceLogger.e(LOG_TAG, "Can not rename file");
        throw new IOException(String.format("Can not rename file. Source path: %s, destination path: %s", sourcePath, destinationPath));
    }

    private void saveTemporaryFile(String path, byte[] file) throws IOException {
        BacktraceLogger.d(LOG_TAG, "Saving temporary file");
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        fileOutputStream.write(file);
        fileOutputStream.close();
    }
}
