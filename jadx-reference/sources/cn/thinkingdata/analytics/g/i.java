package cn.thinkingdata.analytics.g;

import android.content.SharedPreferences;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/* loaded from: classes.dex */
abstract class i<T> {
    protected T a;
    final String b;
    private final Future<SharedPreferences> c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public i(Future<SharedPreferences> future, String str) {
        this.c = future;
        this.b = str;
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0016  */
    /* JADX WARN: Removed duplicated region for block: B:8:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private SharedPreferences.Editor c() {
        SharedPreferences sharedPreferences;
        try {
            sharedPreferences = this.c.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            sharedPreferences = null;
            if (sharedPreferences != null) {
            }
        } catch (ExecutionException e2) {
            e2.printStackTrace();
            sharedPreferences = null;
            if (sharedPreferences != null) {
            }
        }
        if (sharedPreferences != null) {
            return sharedPreferences.edit();
        }
        return null;
    }

    T a() {
        return null;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: T */
    /* JADX WARN: Multi-variable type inference failed */
    void a(SharedPreferences.Editor editor, T t) {
        editor.putString(this.b, (String) t);
        editor.apply();
    }

    void a(SharedPreferences sharedPreferences) {
        T t = (T) sharedPreferences.getString(this.b, null);
        if (t == null) {
            a((i<T>) a());
        } else {
            this.a = t;
        }
    }

    public void a(T t) {
        this.a = t;
        synchronized (this.c) {
            SharedPreferences.Editor c = c();
            if (c != null) {
                a(c, this.a);
            }
        }
    }

    public T b() {
        if (this.a == null) {
            synchronized (this.c) {
                SharedPreferences sharedPreferences = null;
                try {
                    sharedPreferences = this.c.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e2) {
                    e2.printStackTrace();
                }
                if (sharedPreferences != null) {
                    a(sharedPreferences);
                }
            }
        }
        return this.a;
    }
}
