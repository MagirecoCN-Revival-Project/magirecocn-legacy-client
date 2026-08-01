package cn.thinkingdata.analytics.g;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.concurrent.Future;

/* loaded from: classes.dex */
public abstract class a {
    protected h a;
    protected Future<SharedPreferences> b;

    public a(Context context, String str) {
        h hVar = new h();
        this.a = hVar;
        this.b = hVar.a(context, str);
        a();
    }

    public <T> T a(g gVar) {
        i<T> b = b(gVar);
        if (b != null) {
            return b.b();
        }
        return null;
    }

    protected abstract void a();

    public <T> void a(g gVar, T t) {
        i<T> b = b(gVar);
        if (b != null) {
            b.a((i<T>) t);
        }
    }

    protected abstract <T> i<T> b(g gVar);
}
