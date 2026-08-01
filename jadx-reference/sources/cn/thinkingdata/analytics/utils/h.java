package cn.thinkingdata.analytics.utils;

import android.os.SystemClock;
import java.util.Date;

/* loaded from: classes.dex */
public final class h implements c {
    private final long a;
    private final long b = SystemClock.elapsedRealtime();

    public h(long j) {
        this.a = j;
    }

    @Override // cn.thinkingdata.analytics.utils.c
    public Date a(long j) {
        return new Date((j - this.b) + this.a);
    }
}
