package io.kamihama.magianative;

import android.util.Log;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

/* loaded from: classes3.dex */
public final class CNLog {
    public static final int BUFFER_MAX = 1000;
    private static final String LOG_FILE = "cnv_installer.log";
    private static volatile Runnable listener;
    private static BufferedWriter writer;
    private static final SimpleDateFormat TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    private static final ArrayDeque<String> BUFFER = new ArrayDeque<>();
    private static final Object FILE_LOCK = new Object();
    private static boolean openedOnce = false;

    private CNLog() {
    }

    public static void init(File file) {
        synchronized (FILE_LOCK) {
            closeWriterLocked();
            if (file == null) {
                return;
            }
            try {
            } catch (Throwable th) {
                writer = null;
                Log.w("CNLog", "日志文件打开失败: " + th);
            }
            if (file.isDirectory() || file.mkdirs() || file.isDirectory()) {
                File file2 = new File(file, LOG_FILE);
                boolean z = openedOnce;
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2, z), "UTF-8"));
                writer = bufferedWriter;
                bufferedWriter.write((z ? "---- 日志继续（" : "==== 魔法纪录 资源安装器日志（开始于 ") + TS.format(new Date()) + (z ? "） ----\n" : "） ====\n"));
                writer.flush();
                openedOnce = true;
            }
        }
    }

    public static void close() {
        synchronized (FILE_LOCK) {
            closeWriterLocked();
        }
    }

    private static void closeWriterLocked() {
        BufferedWriter bufferedWriter = writer;
        if (bufferedWriter != null) {
            try {
                bufferedWriter.flush();
            } catch (Throwable th) {
            }
            try {
                writer.close();
            } catch (Throwable th2) {
            }
            writer = null;
        }
    }

    public static void setListener(Runnable runnable) {
        listener = runnable;
    }

    public static void i(String str, String str2) {
        write(str, "INFO", str2, null);
    }

    public static void w(String str, String str2) {
        write(str, "WARN", str2, null);
    }

    public static void e(String str, String str2) {
        write(str, "ERROR", str2, null);
    }

    public static void w(String str, String str2, Throwable th) {
        write(str, "WARN", str2, th);
    }

    public static void e(String str, String str2, Throwable th) {
        write(str, "ERROR", str2, th);
    }

    /* JADX WARN: Code restructure failed: missing block: B:68:0x004e, code lost:
    
        android.util.Log.e(r2, r4, r5);
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x005b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void write(String str, String str2, String str3, Throwable th) {
        SimpleDateFormat simpleDateFormat;
        String str4;
        if (str == null) {
            str = "应用";
        }
        if (str2 == null) {
            str2 = "INFO";
        }
        if (str3 == null) {
            str3 = "";
        }
        if (th != null) {
            str3 = str3 + " / " + th;
        }
        if (!"ERROR".equals(str2) && !"FATAL".equals(str2)) {
            if ("WARN".equals(str2)) {
                if (th != null) {
                    Log.w(str, str3, th);
                } else {
                    Log.w(str, str3);
                }
            } else {
                Log.i(str, str3);
            }
            simpleDateFormat = TS;
            synchronized (simpleDateFormat) {
                str4 = "［" + simpleDateFormat.format(new Date()) + "］[" + str + "][" + str2 + "] " + str3;
            }
            ArrayDeque<String> arrayDeque = BUFFER;
            synchronized (arrayDeque) {
                arrayDeque.addLast(str4);
                while (true) {
                    ArrayDeque<String> arrayDeque2 = BUFFER;
                    if (arrayDeque2.size() <= 1000) {
                        break;
                    } else {
                        arrayDeque2.removeFirst();
                    }
                }
            }
            synchronized (FILE_LOCK) {
                BufferedWriter bufferedWriter = writer;
                if (bufferedWriter != null) {
                    try {
                        bufferedWriter.write(str4);
                        writer.write(10);
                        writer.flush();
                    } catch (Throwable th2) {
                    }
                }
            }
            Runnable runnable = listener;
            if (runnable != null) {
                try {
                    runnable.run();
                    return;
                } catch (Throwable th3) {
                    return;
                }
            }
            return;
        }
        Log.e(str, str3);
        simpleDateFormat = TS;
        synchronized (simpleDateFormat) {
        }
    }

    public static String snapshot() {
        StringBuilder sb = new StringBuilder();
        ArrayDeque<String> arrayDeque = BUFFER;
        synchronized (arrayDeque) {
            Iterator<String> it = arrayDeque.iterator();
            while (it.hasNext()) {
                sb.append(it.next()).append('\n');
            }
        }
        return sb.toString();
    }

    public static int size() {
        int size;
        ArrayDeque<String> arrayDeque = BUFFER;
        synchronized (arrayDeque) {
            size = arrayDeque.size();
        }
        return size;
    }
}
