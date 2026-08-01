package backtraceio.library.common;

import backtraceio.library.logger.BacktraceLogger;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class MultiFormRequestHelper {
    private static final String BOUNDARY = "*****";
    private static final String CRLF = "\r\n";
    private static final String ENCODING = "utf-8";
    private static final transient String LOG_TAG = "MultiFormRequestHelper";
    private static final String TWO_HYPHENS = "--";

    public static String getContentType() {
        return "multipart/form-data;boundary=*****";
    }

    public static void addEndOfRequest(OutputStream outputStream) throws IOException {
        if (outputStream == null) {
            BacktraceLogger.w(LOG_TAG, "Output stream is null");
        } else {
            outputStream.write("--*****--\r\n".getBytes());
        }
    }

    public static void addJson(OutputStream outputStream, String json) throws IOException {
        if (BacktraceStringHelper.isNullOrEmpty(json)) {
            BacktraceLogger.w(LOG_TAG, "JSON is null or empty");
            return;
        }
        if (outputStream == null) {
            BacktraceLogger.w(LOG_TAG, "Output stream is null");
            return;
        }
        outputStream.write("--*****\r\n".getBytes());
        outputStream.write(getFileInfo("upload_file").getBytes());
        outputStream.write(CRLF.getBytes());
        outputStream.write(json.getBytes(ENCODING));
        outputStream.write(CRLF.getBytes());
    }

    public static void addFiles(OutputStream outputStream, List<String> attachments) throws IOException {
        if (attachments == null || outputStream == null) {
            BacktraceLogger.w(LOG_TAG, "Attachments or output stream is null");
            return;
        }
        Iterator<String> it = attachments.iterator();
        while (it.hasNext()) {
            addFile(outputStream, it.next());
        }
    }

    private static void addFile(OutputStream outputStream, String absolutePath) throws IOException {
        if (absolutePath == null || outputStream == null) {
            BacktraceLogger.w(LOG_TAG, "Absolute path or output stream is null");
            return;
        }
        String guessContentTypeFromName = URLConnection.guessContentTypeFromName(FileHelper.getFileNameFromPath(absolutePath));
        outputStream.write("--*****\r\n".getBytes());
        outputStream.write(getFileInfo("attachment_" + FileHelper.getFileNameFromPath(absolutePath)).getBytes());
        outputStream.write(("Content-Type: " + guessContentTypeFromName + CRLF).getBytes());
        outputStream.write(CRLF.getBytes());
        streamFile(outputStream, absolutePath);
        outputStream.write(CRLF.getBytes());
    }

    public static void streamFile(OutputStream outputStream, String absolutePath) throws IOException {
        if (outputStream == null || absolutePath == null) {
            BacktraceLogger.w(LOG_TAG, "Absolute path or output stream is null");
            return;
        }
        FileInputStream fileInputStream = new FileInputStream(absolutePath);
        byte[] bArr = new byte[4096];
        while (true) {
            int read = fileInputStream.read(bArr);
            if (read == -1) {
                return;
            } else {
                outputStream.write(bArr, 0, read);
            }
        }
    }

    private static String getFileInfo(String fileName) {
        return "Content-Disposition: form-data; name=\"" + fileName + "\";filename=\"" + fileName + "\"" + CRLF;
    }
}
