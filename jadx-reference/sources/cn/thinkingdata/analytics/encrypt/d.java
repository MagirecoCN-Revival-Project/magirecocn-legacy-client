package cn.thinkingdata.analytics.encrypt;

/* loaded from: classes.dex */
public class d implements a {
    byte[] a;
    String b;

    @Override // cn.thinkingdata.analytics.encrypt.a
    public String a() {
        return "AES";
    }

    @Override // cn.thinkingdata.analytics.encrypt.a
    public String a(String str) {
        return c.a(this.a, str);
    }

    @Override // cn.thinkingdata.analytics.encrypt.a
    public String b() {
        return "RSA";
    }

    @Override // cn.thinkingdata.analytics.encrypt.a
    public String b(String str) {
        try {
            byte[] a = c.a();
            this.a = a;
            String a2 = c.a(str, a);
            this.b = a2;
            return a2;
        } catch (Exception unused) {
            return null;
        }
    }
}
