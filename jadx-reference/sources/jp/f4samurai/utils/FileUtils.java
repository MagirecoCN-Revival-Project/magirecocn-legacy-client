package jp.f4samurai.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/* loaded from: classes.dex */
public class FileUtils {
    private static final String DIR_NAME = "Magireco";
    private static final int IMAGE_QUALITY = 100;

    public static boolean canReadSd() {
        String externalStorageState = Environment.getExternalStorageState();
        return "mounted".equals(externalStorageState) || "mounted_ro".equals(externalStorageState);
    }

    public static boolean canWriteSd() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static boolean canUseSd() {
        return canReadSd() && canWriteSd();
    }

    public static void saveToSd(Context context, Bitmap bitmap) {
        String fileName = getFileName();
        String str = getSdStorageDir().getAbsolutePath() + "/" + fileName;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception unused) {
        } catch (Throwable th) {
            addGallery(context, fileName, str);
            throw th;
        }
        addGallery(context, fileName, str);
    }

    public static String getFileName() {
        Date date = new Date();
        return new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(date) + ".jpg";
    }

    public static void addGallery(Context context, String str, String str2) {
        try {
            ContentValues contentValues = new ContentValues();
            ContentResolver contentResolver = context.getContentResolver();
            contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
            contentValues.put("mime_type", "image/jpeg");
            contentValues.put("title", str);
            contentValues.put("_data", str2);
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        } catch (Exception unused) {
        }
    }

    public static File getSdStorageDir() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), DIR_NAME);
        if (!file.exists()) {
            file.mkdir();
        }
        file.setWritable(true);
        file.setReadable(true);
        return file;
    }

    public static void saveToMedia(Context context, Bitmap bitmap) {
        String fileName = getFileName();
        String str = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + DIR_NAME + "/" + fileName;
        String relativeDir = getRelativeDir(str);
        ContentValues contentValues = new ContentValues();
        contentValues.put("mime_type", "image/jpeg");
        contentValues.put("_display_name", fileName);
        contentValues.put("_data", str);
        contentValues.put("relative_path", relativeDir);
        contentValues.put("title", fileName);
        contentValues.put("is_pending", (Integer) 1);
        ContentResolver contentResolver = context.getContentResolver();
        Uri insert = contentResolver.insert(MediaStore.Images.Media.getContentUri("external_primary"), contentValues);
        try {
            OutputStream openOutputStream = context.getContentResolver().openOutputStream(insert);
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, openOutputStream);
                if (openOutputStream != null) {
                    openOutputStream.close();
                }
            } finally {
            }
        } catch (IOException unused) {
        }
        contentValues.clear();
        contentValues.put("is_pending", (Integer) 0);
        contentResolver.update(insert, contentValues, null, null);
    }

    private static String getRelativeDir(String str) {
        return new File(str.replace(Environment.getExternalStoragePublicDirectory("").getPath() + "/", "")).getParent();
    }
}
