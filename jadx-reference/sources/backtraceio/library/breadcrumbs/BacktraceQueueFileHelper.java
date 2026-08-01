package backtraceio.library.breadcrumbs;

import backtraceio.library.logger.BacktraceLogger;
import com.squareup.tape.QueueFile;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

/* loaded from: classes.dex */
public class BacktraceQueueFileHelper {
    private final String breadcrumbLogDirectory;
    private final QueueFile breadcrumbStore;
    private final int maxQueueFileSizeBytes;
    private final Method usedBytes;
    private final String LOG_TAG = "BacktraceQueueFileHelper";
    private final int minimumQueueFileSizeBytes = 4096;

    public BacktraceQueueFileHelper(String breadcrumbLogDirectory, int maxQueueFileSizeBytes) throws IOException, NoSuchMethodException {
        this.breadcrumbLogDirectory = breadcrumbLogDirectory;
        this.breadcrumbStore = new QueueFile(new File(breadcrumbLogDirectory));
        Method declaredMethod = QueueFile.class.getDeclaredMethod("usedBytes", new Class[0]);
        this.usedBytes = declaredMethod;
        declaredMethod.setAccessible(true);
        if (maxQueueFileSizeBytes < 4096) {
            this.maxQueueFileSizeBytes = 4096;
        } else {
            this.maxQueueFileSizeBytes = maxQueueFileSizeBytes;
        }
    }

    public boolean add(byte[] bytes) {
        try {
            int intValue = ((Integer) this.usedBytes.invoke(this.breadcrumbStore, new Object[0])).intValue();
            int length = bytes.length;
            if (length > 4096) {
                BacktraceLogger.e(this.LOG_TAG, "We should not have a breadcrumb this big, this is a bug!");
                return false;
            }
            while (!this.breadcrumbStore.isEmpty() && intValue + length > this.maxQueueFileSizeBytes) {
                this.breadcrumbStore.remove();
            }
            this.breadcrumbStore.add(bytes);
            return true;
        } catch (Exception e) {
            BacktraceLogger.w(this.LOG_TAG, "Exception: " + e.getMessage() + "\nWhen adding breadcrumb: " + new String(bytes, StandardCharsets.UTF_8));
            return false;
        }
    }

    public boolean clear() {
        try {
            this.breadcrumbStore.clear();
            return true;
        } catch (Exception e) {
            BacktraceLogger.w(this.LOG_TAG, "Exception: " + e.getMessage() + "\nWhen clearing breadcrumbs");
            return false;
        }
    }
}
