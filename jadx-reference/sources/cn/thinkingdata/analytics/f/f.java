package cn.thinkingdata.analytics.f;

import android.content.Context;
import android.content.res.Resources;
import cn.thinkingdata.analytics.TDPresetProperties;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class f {
    private static final Map<Context, f> d = new HashMap();
    private String a;
    private int b;
    private int c;

    private f(Context context) {
        this.b = 10;
        this.c = 10000;
        Resources resources = context.getResources();
        String packageName = context.getPackageName();
        try {
            this.a = packageName;
            this.a = resources.getString(resources.getIdentifier("TADeFaultMainProcessName", "string", packageName));
        } catch (Exception unused) {
        }
        try {
            this.b = resources.getInteger(resources.getIdentifier("TARetentionDays", "integer", packageName));
        } catch (Exception unused2) {
        }
        try {
            this.c = resources.getInteger(resources.getIdentifier("TADatabaseLimit", "integer", packageName));
        } catch (Exception unused3) {
        }
        TDPresetProperties.initDisableList(context);
    }

    public static f a(Context context) {
        f fVar;
        Map<Context, f> map = d;
        synchronized (map) {
            fVar = map.get(context);
            if (fVar == null) {
                fVar = new f(context);
                map.put(context, fVar);
            }
        }
        return fVar;
    }

    public long a() {
        int i = this.b;
        if (i > 10 || i < 0) {
            i = 10;
        }
        return 86400000 * i;
    }

    public String b() {
        return this.a;
    }

    public int c() {
        return Math.max(this.c, 5000);
    }
}
