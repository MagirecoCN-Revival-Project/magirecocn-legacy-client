package cn.thinkingdata.core.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public abstract class AbstractTEDatabaseHelper extends SQLiteOpenHelper {
    private final ExecutorService mPool;

    public AbstractTEDatabaseHelper(Context context, String str, int i) {
        super(context, str, (SQLiteDatabase.CursorFactory) null, i);
        this.mPool = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue());
    }

    protected void deleteAsync(final String str, final String str2, final String[] strArr, final ITESqliteDeleteCallback iTESqliteDeleteCallback) {
        this.mPool.execute(new Runnable() { // from class: cn.thinkingdata.core.sqlite.AbstractTEDatabaseHelper.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int delete = AbstractTEDatabaseHelper.this.getWritableDatabase().delete(str, str2, strArr);
                ITESqliteDeleteCallback iTESqliteDeleteCallback2 = iTESqliteDeleteCallback;
                if (iTESqliteDeleteCallback2 != null) {
                    iTESqliteDeleteCallback2.onDeleteCallback(delete);
                }
            }
        });
    }

    protected void insertAsync(final String str, final ContentValues contentValues, final ITESqliteInsertCallback iTESqliteInsertCallback) {
        this.mPool.execute(new Runnable() { // from class: cn.thinkingdata.core.sqlite.AbstractTEDatabaseHelper.1
            @Override // java.lang.Runnable
            public void run() {
                long insert = AbstractTEDatabaseHelper.this.getWritableDatabase().insert(str, null, contentValues);
                ITESqliteInsertCallback iTESqliteInsertCallback2 = iTESqliteInsertCallback;
                if (iTESqliteInsertCallback2 != null) {
                    iTESqliteInsertCallback2.onInsertCallback(insert);
                }
            }
        });
    }

    protected void rawQueryAsync(final String str, final String[] strArr, final ITESqliteQueryCallback iTESqliteQueryCallback) {
        this.mPool.execute(new Runnable() { // from class: cn.thinkingdata.core.sqlite.AbstractTEDatabaseHelper.4
            /* JADX DEBUG: Another duplicated slice has different insns count: {[IF]}, finally: {[IF, INVOKE] complete} */
            @Override // java.lang.Runnable
            public void run() {
                Cursor cursor = null;
                try {
                    try {
                        cursor = AbstractTEDatabaseHelper.this.getReadableDatabase().rawQuery(str, strArr);
                        ITESqliteQueryCallback iTESqliteQueryCallback2 = iTESqliteQueryCallback;
                        if (iTESqliteQueryCallback2 != null) {
                            if (cursor != null) {
                                iTESqliteQueryCallback2.onQuerySuccess(cursor);
                            } else {
                                iTESqliteQueryCallback2.onQueryFail();
                            }
                        }
                        if (cursor == null) {
                            return;
                        }
                    } catch (Exception unused) {
                        ITESqliteQueryCallback iTESqliteQueryCallback3 = iTESqliteQueryCallback;
                        if (iTESqliteQueryCallback3 != null) {
                            iTESqliteQueryCallback3.onQueryFail();
                        }
                        if (0 == 0) {
                            return;
                        }
                    }
                    cursor.close();
                } catch (Throwable th) {
                    if (0 != 0) {
                        cursor.close();
                    }
                    throw th;
                }
            }
        });
    }

    protected void updateAsync(final String str, final ContentValues contentValues, final String str2, final String[] strArr, final ITESqliteUpdateCallback iTESqliteUpdateCallback) {
        this.mPool.execute(new Runnable() { // from class: cn.thinkingdata.core.sqlite.AbstractTEDatabaseHelper.3
            @Override // java.lang.Runnable
            public void run() {
                int update = AbstractTEDatabaseHelper.this.getWritableDatabase().update(str, contentValues, str2, strArr);
                ITESqliteUpdateCallback iTESqliteUpdateCallback2 = iTESqliteUpdateCallback;
                if (iTESqliteUpdateCallback2 != null) {
                    iTESqliteUpdateCallback2.onUpdateCallback(update);
                }
            }
        });
    }
}
