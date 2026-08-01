package cn.thinkingdata.analytics.f;

import cn.thinkingdata.analytics.utils.p;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class d {
    private final TimeUnit a;
    private long b;
    private long c = 0;
    private long d;

    public d(TimeUnit timeUnit, long j) {
        this.b = j;
        this.a = timeUnit;
    }

    public String a() {
        return b(this.d);
    }

    public String a(long j) {
        return b((j - this.b) + this.c);
    }

    public long b() {
        return this.d;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0048 A[Catch: Exception -> 0x000c, TryCatch #0 {Exception -> 0x000c, blocks: (B:4:0x0007, B:9:0x0015, B:11:0x001a, B:14:0x0042, B:17:0x0048, B:19:0x004d, B:21:0x0021, B:23:0x0029, B:24:0x002c, B:26:0x0034, B:27:0x0036, B:28:0x0038, B:30:0x003e), top: B:2:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x004d A[Catch: Exception -> 0x000c, TRY_LEAVE, TryCatch #0 {Exception -> 0x000c, blocks: (B:4:0x0007, B:9:0x0015, B:11:0x001a, B:14:0x0042, B:17:0x0048, B:19:0x004d, B:21:0x0021, B:23:0x0029, B:24:0x002c, B:26:0x0034, B:27:0x0036, B:28:0x0038, B:30:0x003e), top: B:2:0x0005 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    String b(long j) {
        float f;
        float f2;
        try {
            if (j < 0) {
                return String.valueOf(0);
            }
            if (j > 86400000) {
                return b(86400000L);
            }
            if (this.a != TimeUnit.MILLISECONDS) {
                if (this.a == TimeUnit.SECONDS) {
                    f2 = ((float) j) / 1000.0f;
                } else {
                    if (this.a == TimeUnit.MINUTES) {
                        f = ((float) j) / 1000.0f;
                    } else if (this.a == TimeUnit.HOURS) {
                        f = (((float) j) / 1000.0f) / 60.0f;
                    }
                    f2 = f / 60.0f;
                }
                return f2 >= 0.0f ? String.valueOf(0) : String.valueOf(p.a(f2, 3));
            }
            f2 = (float) j;
            if (f2 >= 0.0f) {
            }
        } catch (Exception e) {
            e.printStackTrace();
            return String.valueOf(0);
        }
    }

    public long c() {
        return this.c;
    }

    public void c(long j) {
        this.d = j;
    }

    public long d() {
        return this.b;
    }

    public void d(long j) {
        this.c = j;
    }

    public void e(long j) {
        this.b = j;
    }
}
