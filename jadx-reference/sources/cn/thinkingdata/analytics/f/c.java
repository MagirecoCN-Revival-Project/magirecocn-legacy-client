package cn.thinkingdata.analytics.f;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import cn.thinkingdata.core.utils.TDLog;
import com.android.vending.expansion.zipfile.APEZProvider;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class c {
    private static final String b = "CREATE TABLE " + EnumC0013c.EVENTS.a() + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, clickdata TEXT NOT NULL, creattime INTEGER NOT NULL, token TEXT NOT NULL DEFAULT '')";
    private static final String c;
    private static final Map<Context, c> d;
    private final a a;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a extends SQLiteOpenHelper {
        private final File a;
        private final int b;

        public a(Context context, String str) {
            super(context, str, (SQLiteDatabase.CursorFactory) null, 1);
            this.a = context.getDatabasePath(str);
            this.b = f.a(context).c();
        }

        boolean a() {
            return !this.a.exists() || b() < this.b;
        }

        /* JADX DEBUG: Another duplicated slice has different insns count: {[IF]}, finally: {[IF, INVOKE] complete} */
        /* JADX WARN: Code restructure failed: missing block: B:10:0x003c, code lost:
        
            r0.close();
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x003a, code lost:
        
            if (r0 == null) goto L14;
         */
        /* JADX WARN: Code restructure failed: missing block: B:7:0x0031, code lost:
        
            if (r0 != null) goto L13;
         */
        /* JADX WARN: Code restructure failed: missing block: B:8:0x003f, code lost:
        
            return r1;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        int b() {
            Cursor cursor = null;
            try {
                try {
                    cursor = getReadableDatabase().rawQuery("SELECT count(*) FROM " + EnumC0013c.EVENTS.a(), null);
                    r1 = cursor.moveToNext() ? cursor.getInt(cursor.getColumnIndex("count(*)")) : 0;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        }

        void c() {
            close();
            this.a.delete();
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            TDLog.d("ThinkingAnalytics.DatabaseAdapter", "Creating a new ThinkingData events database");
            sQLiteDatabase.execSQL(c.b);
            sQLiteDatabase.execSQL(c.c);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            TDLog.d("ThinkingAnalytics.DatabaseAdapter", "Upgrading ThinkingData events database");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EnumC0013c.EVENTS.a());
            sQLiteDatabase.execSQL(c.b);
            sQLiteDatabase.execSQL(c.c);
        }
    }

    /* loaded from: classes.dex */
    private class b extends SQLiteOpenHelper {
        b(c cVar, Context context, String str) {
            super(context, str, (SQLiteDatabase.CursorFactory) null, 1);
        }

        /* JADX DEBUG: Another duplicated slice has different insns count: {[INVOKE]}, finally: {[INVOKE, INVOKE, IF] complete} */
        /* JADX WARN: Code restructure failed: missing block: B:10:0x0067, code lost:
        
            return r2;
         */
        /* JADX WARN: Code restructure failed: missing block: B:12:0x0064, code lost:
        
            r3.close();
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x0062, code lost:
        
            if (r3 == null) goto L18;
         */
        /* JADX WARN: Code restructure failed: missing block: B:9:0x0056, code lost:
        
            if (r3 != null) goto L17;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        JSONArray a() {
            JSONArray jSONArray = new JSONArray();
            Cursor cursor = null;
            try {
                try {
                    cursor = getReadableDatabase().rawQuery("SELECT * FROM " + EnumC0013c.EVENTS + " ORDER BY ?", new String[]{"creattime"});
                    while (cursor.moveToNext()) {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("creattime", cursor.getString(cursor.getColumnIndex("creattime")));
                        jSONObject.put("clickdata", cursor.getString(cursor.getColumnIndex("clickdata")));
                        jSONArray.put(jSONObject);
                    }
                    close();
                } catch (Exception e) {
                    e.printStackTrace();
                    close();
                }
            } catch (Throwable th) {
                close();
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        }
    }

    /* renamed from: cn.thinkingdata.analytics.f.c$c, reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public enum EnumC0013c {
        EVENTS("events");

        private final String a;

        EnumC0013c(String str) {
            this.a = str;
        }

        public String a() {
            return this.a;
        }
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE INDEX IF NOT EXISTS time_idx ON ");
        sb.append(EnumC0013c.EVENTS.a());
        sb.append(" (");
        sb.append("creattime");
        sb.append(");");
        c = sb.toString();
        d = new HashMap();
    }

    c(Context context) {
        this(context, "thinkingdata");
    }

    c(Context context, String str) {
        this.a = new a(context, str);
        try {
            File databasePath = context.getDatabasePath(context.getPackageName());
            if (databasePath.exists()) {
                JSONArray a2 = new b(this, context, context.getPackageName()).a();
                for (int i = 0; i < a2.length(); i++) {
                    try {
                        JSONObject jSONObject = a2.getJSONObject(i);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("clickdata", jSONObject.getString("clickdata"));
                        contentValues.put("creattime", jSONObject.getString("creattime"));
                        TDLog.d("ThinkingAnalytics.DatabaseAdapter", contentValues.toString());
                        this.a.getWritableDatabase().insert(EnumC0013c.EVENTS.a(), null, contentValues);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                databasePath.delete();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static c a(Context context) {
        c cVar;
        Map<Context, c> map = d;
        synchronized (map) {
            Context applicationContext = context.getApplicationContext();
            if (map.containsKey(applicationContext)) {
                cVar = map.get(applicationContext);
            } else {
                cVar = new c(applicationContext);
                map.put(applicationContext, cVar);
            }
        }
        return cVar;
    }

    private boolean c() {
        return this.a.a();
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[IF]}, finally: {[IF, INVOKE] complete} */
    /* JADX WARN: Code restructure failed: missing block: B:10:0x0063, code lost:
    
        if (r0 != null) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x008f, code lost:
    
        return r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x008c, code lost:
    
        r0.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x008a, code lost:
    
        if (r0 == null) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int a(String str, EnumC0013c enumC0013c, String str2) {
        int i;
        String a2 = enumC0013c.a();
        Cursor cursor = null;
        try {
            try {
                SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
                StringBuilder sb = new StringBuilder("_id <= ?");
                if (str2 != null) {
                    sb.append(" AND ");
                    sb.append("token");
                    sb.append(" = ?");
                }
                writableDatabase.delete(a2, sb.toString(), new String[]{str, str2});
                StringBuilder sb2 = new StringBuilder("SELECT COUNT(*) FROM " + a2);
                if (str2 != null) {
                    sb2.append(" WHERE token= ?");
                }
                cursor = writableDatabase.rawQuery(sb2.toString(), new String[]{str2});
                cursor.moveToFirst();
                i = cursor.getInt(0);
            } catch (SQLiteException e) {
                TDLog.e("ThinkingAnalytics.DatabaseAdapter", "could not clean data from " + a2, e);
                if (cursor != null) {
                    cursor.close();
                }
                this.a.c();
                i = -1;
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[IF]}, finally: {[IF, INVOKE] complete} */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00a2, code lost:
    
        if (r3 != null) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00d0, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00cd, code lost:
    
        r3.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00cb, code lost:
    
        if (r3 == null) goto L29;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int a(JSONObject jSONObject, EnumC0013c enumC0013c, String str) {
        Cursor cursor = null;
        if (!c()) {
            TDLog.d("ThinkingAnalytics.DatabaseAdapter", "The data has reached the limit, oldest data will be deleted");
            String[] a2 = a(enumC0013c, (String) null, 100);
            if (a2 == null || a(a2[0], EnumC0013c.EVENTS, (String) null) <= 0) {
                return -2;
            }
        }
        String a3 = enumC0013c.a();
        int i = -1;
        try {
            try {
                SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                if (cn.thinkingdata.analytics.encrypt.e.a(str) != null) {
                    jSONObject = cn.thinkingdata.analytics.encrypt.e.a(str).a(jSONObject);
                }
                contentValues.put("clickdata", jSONObject.toString() + "#td#" + jSONObject.toString().hashCode());
                contentValues.put("creattime", Long.valueOf(System.currentTimeMillis()));
                contentValues.put("token", str);
                writableDatabase.insert(a3, null, contentValues);
                cursor = writableDatabase.rawQuery("SELECT COUNT(*) FROM " + a3 + " WHERE token=?", new String[]{str});
                cursor.moveToFirst();
                i = cursor.getInt(0);
            } catch (SQLiteException e) {
                TDLog.e("ThinkingAnalytics.DatabaseAdapter", "could not add data to table " + a3 + ". Re-initializing database.", e);
                if (cursor != null) {
                    cursor.close();
                }
                this.a.c();
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public void a(long j, EnumC0013c enumC0013c) {
        String a2 = enumC0013c.a();
        try {
            this.a.getWritableDatabase().delete(a2, "creattime <= ?", new String[]{j + ""});
        } catch (SQLiteException e) {
            TDLog.e("ThinkingAnalytics.DatabaseAdapter", "Could not clean timed-out records. Re-initializing database.", e);
            this.a.c();
        }
    }

    public void a(EnumC0013c enumC0013c, String str) {
        try {
            this.a.getWritableDatabase().delete(enumC0013c.a(), "token = ?", new String[]{str});
        } catch (SQLiteException e) {
            TDLog.e("ThinkingAnalytics.DatabaseAdapter", "Could not clean records. Re-initializing database.", e);
            this.a.c();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:52:0x00df, code lost:
    
        if (r14 != null) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0103, code lost:
    
        if (r6 == null) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0105, code lost:
    
        if (r12 == null) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x010d, code lost:
    
        return new java.lang.String[]{r6, r12};
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x010e, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0100, code lost:
    
        r14.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00fe, code lost:
    
        if (r14 == null) goto L51;
     */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0113  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String[] a(EnumC0013c enumC0013c, String str, int i) {
        Cursor cursor;
        String str2;
        String str3;
        String a2 = enumC0013c.a();
        Cursor cursor2 = null;
        try {
            SQLiteDatabase readableDatabase = this.a.getReadableDatabase();
            StringBuilder sb = new StringBuilder("SELECT * FROM ");
            sb.append(a2);
            if (str != null) {
                sb.append(" WHERE ");
                sb.append("token");
                sb.append(" = ?");
            }
            sb.append(" ORDER BY ?");
            sb.append(" ASC LIMIT ?");
            JSONArray jSONArray = new JSONArray();
            cursor = readableDatabase.rawQuery(sb.toString(), new String[]{str, "creattime", i + ""});
            if (cursor != null) {
                str3 = null;
                while (cursor.moveToNext()) {
                    try {
                        try {
                            if (cursor.isLast()) {
                                str3 = cursor.getString(cursor.getColumnIndex(APEZProvider.FILEID));
                            }
                            try {
                                String string = cursor.getString(cursor.getColumnIndex("clickdata"));
                                if (!TextUtils.isEmpty(string)) {
                                    int lastIndexOf = string.lastIndexOf("#td#");
                                    if (lastIndexOf > -1) {
                                        String replaceFirst = string.substring(lastIndexOf).replaceFirst("#td#", "");
                                        string = string.substring(0, lastIndexOf);
                                        if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(replaceFirst) && replaceFirst.equals(String.valueOf(string.hashCode()))) {
                                        }
                                    }
                                    JSONObject jSONObject = new JSONObject(string);
                                    cn.thinkingdata.analytics.encrypt.e a3 = cn.thinkingdata.analytics.encrypt.e.a(str);
                                    if (a3 != null && !cn.thinkingdata.analytics.encrypt.c.a(jSONObject)) {
                                        jSONObject = a3.a(jSONObject);
                                    }
                                    jSONArray.put(jSONObject);
                                }
                            } catch (JSONException unused) {
                            }
                        } catch (SQLiteException e) {
                            e = e;
                            TDLog.e("ThinkingAnalytics.DatabaseAdapter", "Could not pull records out of database " + a2, e);
                            str2 = null;
                            str3 = null;
                        }
                    } catch (Throwable th) {
                        th = th;
                        cursor2 = cursor;
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                        throw th;
                    }
                }
                str2 = jSONArray.length() > 0 ? jSONArray.toString() : null;
            } else {
                str2 = null;
                str3 = null;
            }
        } catch (SQLiteException e2) {
            e = e2;
            cursor = null;
        } catch (Throwable th2) {
            th = th2;
            if (cursor2 != null) {
            }
            throw th;
        }
    }
}
