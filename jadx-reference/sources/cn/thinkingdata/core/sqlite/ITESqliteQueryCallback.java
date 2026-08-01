package cn.thinkingdata.core.sqlite;

import android.database.Cursor;

/* loaded from: classes.dex */
public interface ITESqliteQueryCallback {
    void onQueryFail();

    void onQuerySuccess(Cursor cursor);
}
