package cn.thinkingdata.core.router;

import android.content.Context;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class Postcard extends RouteMeta {
    private String action;
    public Map<String, Object> arguments = new HashMap();
    private String path;

    public Postcard(String str) {
        this.path = str;
    }

    public String getAction() {
        return this.action;
    }

    @Override // cn.thinkingdata.core.router.RouteMeta
    public String getPath() {
        return this.path;
    }

    public Object navigation() {
        return navigation(null);
    }

    public Object navigation(Context context) {
        return TRouter.getInstance().navigation(context, this);
    }

    public Postcard withAction(String str) {
        this.action = str;
        return this;
    }

    public Postcard withBoolean(String str, boolean z) {
        this.arguments.put(str, Boolean.valueOf(z));
        return this;
    }

    public Postcard withCharSequence(String str, CharSequence charSequence) {
        this.arguments.put(str, charSequence);
        return this;
    }

    public Postcard withDouble(String str, double d) {
        this.arguments.put(str, Double.valueOf(d));
        return this;
    }

    public Postcard withFloat(String str, float f) {
        this.arguments.put(str, Float.valueOf(f));
        return this;
    }

    public Postcard withInt(String str, int i) {
        this.arguments.put(str, Integer.valueOf(i));
        return this;
    }

    public Postcard withLong(String str, long j) {
        this.arguments.put(str, Long.valueOf(j));
        return this;
    }

    public Postcard withObject(String str, Object obj) {
        this.arguments.put(str, obj);
        return this;
    }

    public Postcard withString(String str, String str2) {
        this.arguments.put(str, str2);
        return this;
    }
}
