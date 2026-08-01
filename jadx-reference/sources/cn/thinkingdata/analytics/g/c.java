package cn.thinkingdata.analytics.g;

import android.content.Context;

/* loaded from: classes.dex */
public class c extends cn.thinkingdata.analytics.g.a {
    private o c;
    private m d;
    private j e;
    private p f;
    private q g;
    private u h;
    private t i;

    /* loaded from: classes.dex */
    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[g.values().length];
            a = iArr;
            try {
                iArr[g.LOGIN_ID.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[g.IDENTIFY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[g.SUPER_PROPERTIES.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[g.OPT_OUT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[g.ENABLE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[g.PAUSE_POST.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                a[g.SESSION_ID.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public c(Context context, String str) {
        super(context, "com.thinkingdata.analyse_" + str);
    }

    @Override // cn.thinkingdata.analytics.g.a
    protected void a() {
        this.c = new o(this.b);
        this.d = new m(this.b);
        this.h = new u(this.b);
        this.f = new p(this.b);
        this.e = new j(this.b);
        this.g = new q(this.b);
        this.i = new t(this.b);
    }

    @Override // cn.thinkingdata.analytics.g.a
    protected <T> i<T> b(g gVar) {
        switch (a.a[gVar.ordinal()]) {
            case 1:
                return this.c;
            case 2:
                return this.d;
            case 3:
                return this.h;
            case 4:
                return this.f;
            case 5:
                return this.e;
            case 6:
                return this.g;
            case 7:
                return this.i;
            default:
                return null;
        }
    }
}
