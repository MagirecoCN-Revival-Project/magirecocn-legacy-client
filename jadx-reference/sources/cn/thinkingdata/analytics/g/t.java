package cn.thinkingdata.analytics.g;

import android.content.SharedPreferences;
import java.util.concurrent.Future;

/* loaded from: classes.dex */
public class t extends i<Integer> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public t(Future<SharedPreferences> future) {
        super(future, "sessionId");
    }

    /* JADX DEBUG: Method merged with bridge method: a(Landroid/content/SharedPreferences$Editor;Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // cn.thinkingdata.analytics.g.i
    public void a(SharedPreferences.Editor editor, Integer num) {
        editor.putInt(this.b, num.intValue());
        editor.apply();
    }

    /* JADX WARN: Type inference failed for: r3v2, types: [T, java.lang.Integer] */
    @Override // cn.thinkingdata.analytics.g.i
    void a(SharedPreferences sharedPreferences) {
        this.a = Integer.valueOf(sharedPreferences.getInt(this.b, 0));
    }
}
