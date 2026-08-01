package cn.thinkingdata.analytics.g;

import android.content.SharedPreferences;
import java.util.concurrent.Future;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class u extends i<JSONObject> {
    public u(Future<SharedPreferences> future) {
        super(future, "superProperties");
    }

    /* JADX DEBUG: Method merged with bridge method: a()Ljava/lang/Object; */
    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // cn.thinkingdata.analytics.g.i
    public JSONObject a() {
        return new JSONObject();
    }

    /* JADX DEBUG: Method merged with bridge method: a(Landroid/content/SharedPreferences$Editor;Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // cn.thinkingdata.analytics.g.i
    public void a(SharedPreferences.Editor editor, JSONObject jSONObject) {
        editor.putString(this.b, jSONObject == null ? null : jSONObject.toString());
        editor.apply();
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [org.json.JSONObject, T] */
    @Override // cn.thinkingdata.analytics.g.i
    void a(SharedPreferences sharedPreferences) {
        String string = sharedPreferences.getString(this.b, null);
        if (string == null) {
            a((u) a());
            return;
        }
        try {
            this.a = new JSONObject(string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
