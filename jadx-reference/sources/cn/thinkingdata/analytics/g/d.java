package cn.thinkingdata.analytics.g;

import android.content.Context;

/* loaded from: classes.dex */
public class d extends cn.thinkingdata.analytics.g.a {
    private l c;
    private k d;

    /* loaded from: classes.dex */
    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[g.values().length];
            a = iArr;
            try {
                iArr[g.FLUSH_INTERVAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[g.FLUSH_SIZE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public d(Context context, String str) {
        super(context, "cn.thinkingdata.android.config_" + str);
    }

    @Override // cn.thinkingdata.analytics.g.a
    protected void a() {
        this.c = new l(this.b, 15000);
        this.d = new k(this.b, 20);
    }

    @Override // cn.thinkingdata.analytics.g.a
    protected <T> i<T> b(g gVar) {
        int i = a.a[gVar.ordinal()];
        if (i == 1) {
            return this.c;
        }
        if (i != 2) {
            return null;
        }
        return this.d;
    }
}
