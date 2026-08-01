package cn.thinkingdata.analytics.g;

import android.content.SharedPreferences;
import java.util.concurrent.Future;

/* loaded from: classes.dex */
public class n extends i<Long> {
    public n(Future<SharedPreferences> future) {
        super(future, "lastInstallTime");
    }

    /* JADX DEBUG: Method merged with bridge method: a()Ljava/lang/Object; */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // cn.thinkingdata.analytics.g.i
    public Long a() {
        return 0L;
    }

    /* JADX DEBUG: Method merged with bridge method: a(Landroid/content/SharedPreferences$Editor;Ljava/lang/Object;)V */
    @Override // cn.thinkingdata.analytics.g.i
    public void a(SharedPreferences.Editor editor, Long l) {
        editor.putLong(this.b, l.longValue());
        editor.apply();
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [T, java.lang.Long] */
    @Override // cn.thinkingdata.analytics.g.i
    public void a(SharedPreferences sharedPreferences) {
        this.a = Long.valueOf(sharedPreferences.getLong(this.b, 0L));
    }
}
