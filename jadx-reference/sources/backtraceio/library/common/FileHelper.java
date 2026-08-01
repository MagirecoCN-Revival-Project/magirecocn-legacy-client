package backtraceio.library.common;

import android.content.Context;
import android.util.Log;
import backtraceio.library.logger.BacktraceLogger;
import cn.thinkingdata.core.router.TRouterMap;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/* loaded from: classes.dex */
public class FileHelper {
    private static final String LOG_TAG = "FileHelper";

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String getFileNameFromPath(String absolutePath) {
        return absolutePath.substring(absolutePath.lastIndexOf("/") + 1);
    }

    public static ArrayList<String> filterOutFiles(Context context, List<String> paths) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (paths == null) {
            return arrayList;
        }
        for (String str : new ArrayList(new HashSet(paths))) {
            if (isFilePathInvalid(str)) {
                Log.e(LOG_TAG, String.format("Path for file %s is invalid", str));
            } else {
                if (!isPathToInternalStorage(context, str)) {
                    String str2 = LOG_TAG;
                    Log.d(str2, String.format("Passed path is path to external storage %s", str));
                    if (!PermissionHelper.isPermissionForReadExternalStorageGranted(context)) {
                        Log.e(str2, "Permission READ_EXTERNAL_STORAGE is not granted.");
                    }
                }
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    public static String getFileExtension(File file) {
        String substring = file.getName().substring(Math.max(file.getName().lastIndexOf(47), file.getName().lastIndexOf(92)) < 0 ? 0 : Math.max(file.getName().lastIndexOf(47), file.getName().lastIndexOf(92)));
        int lastIndexOf = substring.lastIndexOf(TRouterMap.DOT);
        return lastIndexOf == -1 ? "" : substring.substring(lastIndexOf + 1);
    }

    private static boolean isFilePathInvalid(String filePath) {
        return filePath == null || filePath.isEmpty() || !isFileExists(filePath);
    }

    public static boolean isFileExists(String absoluteFilePath) {
        return new File(absoluteFilePath).exists();
    }

    private static boolean isPathToInternalStorage(Context context, String path) {
        if (context == null || path == null) {
            return false;
        }
        String str = context.getApplicationInfo().dataDir;
        String absolutePath = context.getCacheDir().getAbsolutePath();
        String path2 = context.getFilesDir().getPath();
        BacktraceLogger.d(LOG_TAG, String.format("Passed path %s, Internal paths %s, %s, %s", path, str, absolutePath, path2));
        return path.startsWith(str) || path.startsWith(absolutePath) || path.startsWith(path2);
    }

    public static String readFile(File file) {
        try {
            Scanner scanner = new Scanner(file);
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNext()) {
                sb.append(scanner.nextLine());
            }
            scanner.close();
            return sb.toString();
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
            return null;
        }
    }
}
