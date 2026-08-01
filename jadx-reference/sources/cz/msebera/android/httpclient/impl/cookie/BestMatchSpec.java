package cz.msebera.android.httpclient.impl.cookie;

@Deprecated
/* loaded from: classes.dex */
public class BestMatchSpec extends DefaultCookieSpec {
    @Override // cz.msebera.android.httpclient.impl.cookie.DefaultCookieSpec
    public String toString() {
        return "best-match";
    }

    public BestMatchSpec(String[] strArr, boolean z) {
        super(strArr, z);
    }

    public BestMatchSpec() {
        this(null, false);
    }
}
