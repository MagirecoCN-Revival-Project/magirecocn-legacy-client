package cn.thinkingdata.analytics.g;

import android.content.SharedPreferences;
import java.util.UUID;
import java.util.concurrent.Future;

/* loaded from: classes.dex */
public class s extends i<String> {
    public s(Future<SharedPreferences> future) {
        super(future, "randomID");
    }

    /* JADX DEBUG: Method merged with bridge method: a()Ljava/lang/Object; */
    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // cn.thinkingdata.analytics.g.i
    public String a() {
        return UUID.randomUUID().toString();
    }
}
