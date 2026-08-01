package cn.thinkingdata.analytics.g;

import android.content.SharedPreferences;
import java.util.concurrent.Future;

/* loaded from: classes.dex */
public class r extends i<String> {
    public r(Future<SharedPreferences> future) {
        super(future, "randomDeviceID");
    }

    /* JADX DEBUG: Method merged with bridge method: a()Ljava/lang/Object; */
    @Override // cn.thinkingdata.analytics.g.i
    public String a() {
        return cn.thinkingdata.analytics.utils.p.a(16);
    }

    /* JADX DEBUG: Method merged with bridge method: a(Landroid/content/SharedPreferences$Editor;Ljava/lang/Object;)V */
    @Override // cn.thinkingdata.analytics.g.i
    public void a(SharedPreferences.Editor editor, String str) {
        editor.putString(this.b, str);
        editor.apply();
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [T, java.lang.String] */
    @Override // cn.thinkingdata.analytics.g.i
    public void a(SharedPreferences sharedPreferences) {
        this.a = sharedPreferences.getString(this.b, "");
    }
}
