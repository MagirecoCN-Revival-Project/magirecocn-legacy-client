package com.google.android.gms.internal.measurement;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes.dex */
public final class zzen {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static void zza(zzfh zzfhVar, SQLiteDatabase sQLiteDatabase) {
        if (zzfhVar == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        File file = new File(sQLiteDatabase.getPath());
        if (!file.setReadable(false, false)) {
            zzfhVar.zziv().log("Failed to turn off database read permission");
        }
        if (!file.setWritable(false, false)) {
            zzfhVar.zziv().log("Failed to turn off database write permission");
        }
        if (!file.setReadable(true, true)) {
            zzfhVar.zziv().log("Failed to turn on database read permission for owner");
        }
        if (file.setWritable(true, true)) {
            return;
        }
        zzfhVar.zziv().log("Failed to turn on database write permission for owner");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void zza(zzfh zzfhVar, SQLiteDatabase sQLiteDatabase, String str, String str2, String str3, String[] strArr) throws SQLiteException {
        if (zzfhVar == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        if (!zza(zzfhVar, sQLiteDatabase, str)) {
            sQLiteDatabase.execSQL(str2);
        }
        try {
            if (zzfhVar == null) {
                throw new IllegalArgumentException("Monitor must not be null");
            }
            Set<String> zzb = zzb(sQLiteDatabase, str);
            for (String str4 : str3.split(",")) {
                if (!zzb.remove(str4)) {
                    StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 35 + String.valueOf(str4).length());
                    sb.append("Table ");
                    sb.append(str);
                    sb.append(" is missing required column: ");
                    sb.append(str4);
                    throw new SQLiteException(sb.toString());
                }
            }
            if (strArr != null) {
                for (int i = 0; i < strArr.length; i += 2) {
                    if (!zzb.remove(strArr[i])) {
                        sQLiteDatabase.execSQL(strArr[i + 1]);
                    }
                }
            }
            if (zzb.isEmpty()) {
                return;
            }
            zzfhVar.zziv().zze("Table has extra columns. table, columns", str, TextUtils.join(", ", zzb));
        } catch (SQLiteException e) {
            zzfhVar.zzis().zzg("Failed to verify columns on table that was just created", str);
            throw e;
        }
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[IF]}, finally: {[IF, INVOKE] complete} */
    private static boolean zza(zzfh zzfhVar, SQLiteDatabase sQLiteDatabase, String str) {
        if (zzfhVar == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        Cursor cursor = null;
        try {
            try {
                cursor = sQLiteDatabase.query("SQLITE_MASTER", new String[]{"name"}, "name=?", new String[]{str}, null, null, null);
                boolean moveToFirst = cursor.moveToFirst();
                if (cursor != null) {
                    cursor.close();
                }
                return moveToFirst;
            } catch (SQLiteException e) {
                zzfhVar.zziv().zze("Error querying for table", str, e);
                if (cursor != null) {
                    cursor.close();
                }
                return false;
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    private static Set<String> zzb(SQLiteDatabase sQLiteDatabase, String str) {
        HashSet hashSet = new HashSet();
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 22);
        sb.append("SELECT * FROM ");
        sb.append(str);
        sb.append(" LIMIT 0");
        Cursor rawQuery = sQLiteDatabase.rawQuery(sb.toString(), null);
        try {
            Collections.addAll(hashSet, rawQuery.getColumnNames());
            return hashSet;
        } finally {
            rawQuery.close();
        }
    }
}
