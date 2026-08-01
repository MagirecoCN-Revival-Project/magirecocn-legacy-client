package cn.thinkingdata.analytics.aop.push;

import com.google.firebase.analytics.FirebaseAnalytics;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class TANotificationInfo {
    String content;
    long time;
    String title;

    /* JADX INFO: Access modifiers changed from: package-private */
    public TANotificationInfo(String str, String str2, long j) {
        this.title = str;
        this.content = str2;
        this.time = j;
    }

    public static TANotificationInfo fromJson(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            return new TANotificationInfo(jSONObject.optString("title"), jSONObject.optString(FirebaseAnalytics.Param.CONTENT), jSONObject.optLong("time"));
        } catch (JSONException unused) {
            return null;
        }
    }

    public String getContent() {
        return this.content;
    }

    public long getTime() {
        return this.time;
    }

    public String getTitle() {
        return this.title;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public void setTime(long j) {
        this.time = j;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String toJson() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("title", this.title);
            jSONObject.put(FirebaseAnalytics.Param.CONTENT, this.content);
            jSONObject.put("time", this.time);
            return jSONObject.toString();
        } catch (JSONException unused) {
            return null;
        }
    }
}
