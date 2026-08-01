package cn.thinkingdata.analytics.g;

import android.content.SharedPreferences;
import java.util.concurrent.Future;

/* loaded from: classes.dex */
public class p extends i<Boolean> {
    public p(Future<SharedPreferences> future) {
        super(future, "optOutFlag");
    }

    /* JADX DEBUG: Method merged with bridge method: a(Landroid/content/SharedPreferences$Editor;Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // cn.thinkingdata.analytics.g.i
    public void a(SharedPreferences.Editor editor, Boolean bool) {
        editor.putBoolean(this.b, bool.booleanValue());
        editor.apply();
    }

    /* JADX WARN: Type inference failed for: r3v2, types: [T, java.lang.Boolean] */
    @Override // cn.thinkingdata.analytics.g.i
    protected void a(SharedPreferences sharedPreferences) {
        this.a = Boolean.valueOf(sharedPreferences.getBoolean(this.b, false));
    }
}
