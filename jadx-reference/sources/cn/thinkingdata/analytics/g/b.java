package cn.thinkingdata.analytics.g;

import android.content.Context;
import android.text.TextUtils;
import cn.thinkingdata.core.utils.TDLog;
import java.util.TimeZone;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class b {
    private final c a;
    private final Object b = new Object();
    private final Object c = new Object();
    private final Object d = new Object();

    public b(Context context, String str) {
        this.a = new c(context, str);
    }

    public String a(boolean z, Context context) {
        String str;
        synchronized (this.b) {
            str = (String) this.a.a(g.LOGIN_ID);
            if (TextUtils.isEmpty(str) && z) {
                str = e.a(context).c();
                if (!TextUtils.isEmpty(str)) {
                    this.a.a(g.LOGIN_ID, str);
                    e.a(context).a();
                }
            }
        }
        return str;
    }

    public void a() {
        synchronized (this.c) {
            this.a.a(g.IDENTIFY, null);
        }
    }

    public void a(String str) {
        if (str == null) {
            return;
        }
        try {
            synchronized (this.d) {
                JSONObject jSONObject = (JSONObject) this.a.a(g.SUPER_PROPERTIES);
                jSONObject.remove(str);
                this.a.a(g.SUPER_PROPERTIES, jSONObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void a(String str, boolean z) {
        try {
            if (TextUtils.isEmpty(str)) {
                TDLog.d("ThinkingAnalytics.Storage", "The account id cannot be empty.");
                if (z) {
                    throw new cn.thinkingdata.analytics.utils.k("account id cannot be empty");
                }
            } else {
                synchronized (this.b) {
                    if (!str.equals(this.a.a(g.LOGIN_ID))) {
                        this.a.a(g.LOGIN_ID, str);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void a(JSONObject jSONObject, TimeZone timeZone, boolean z) {
        if (jSONObject != null) {
            try {
                if (cn.thinkingdata.analytics.utils.f.a(jSONObject)) {
                    synchronized (this.d) {
                        JSONObject jSONObject2 = (JSONObject) this.a.a(g.SUPER_PROPERTIES);
                        cn.thinkingdata.analytics.utils.p.a(jSONObject, jSONObject2, timeZone);
                        this.a.a(g.SUPER_PROPERTIES, jSONObject2);
                    }
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        if (z) {
            throw new cn.thinkingdata.analytics.utils.k("Set super properties failed. Please refer to the SDK debug log for details.");
        }
    }

    public void a(boolean z) {
        this.a.a(g.ENABLE, Boolean.valueOf(z));
    }

    public void b() {
        synchronized (this.b) {
            this.a.a(g.LOGIN_ID, null);
        }
    }

    public void b(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            TDLog.w("ThinkingAnalytics.Storage", "The identity cannot be empty.");
            if (z) {
                throw new cn.thinkingdata.analytics.utils.k("distinct id cannot be empty");
            }
        } else {
            synchronized (this.c) {
                this.a.a(g.IDENTIFY, str);
            }
        }
    }

    public void b(boolean z) {
        this.a.a(g.OPT_OUT, Boolean.valueOf(z));
    }

    public void b(boolean z, Context context) {
        try {
            synchronized (this.b) {
                this.a.a(g.LOGIN_ID, null);
                if (z && !TextUtils.isEmpty(e.a(context).c())) {
                    e.a(context).a();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void c() {
        synchronized (this.d) {
            this.a.a(g.SUPER_PROPERTIES, new JSONObject());
        }
    }

    public void c(boolean z) {
        this.a.a(g.PAUSE_POST, Boolean.valueOf(z));
    }

    public boolean d() {
        return ((Boolean) this.a.a(g.ENABLE)).booleanValue();
    }

    public String e() {
        String str;
        synchronized (this.c) {
            str = (String) this.a.a(g.IDENTIFY);
        }
        return str;
    }

    public boolean f() {
        return ((Boolean) this.a.a(g.OPT_OUT)).booleanValue();
    }

    public boolean g() {
        return ((Boolean) this.a.a(g.PAUSE_POST)).booleanValue();
    }

    public JSONObject h() {
        JSONObject jSONObject;
        synchronized (this.d) {
            jSONObject = (JSONObject) this.a.a(g.SUPER_PROPERTIES);
        }
        return jSONObject;
    }
}
