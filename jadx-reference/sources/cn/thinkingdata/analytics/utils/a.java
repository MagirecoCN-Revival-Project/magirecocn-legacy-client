package cn.thinkingdata.analytics.utils;

import cn.thinkingdata.analytics.TDConfig;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes.dex */
public class a {
    private static c b;
    private static final ReentrantReadWriteLock c = new ReentrantReadWriteLock();
    private final TDConfig a;

    public a(TDConfig tDConfig) {
        this.a = tDConfig;
    }

    public static void a(long j) {
        a(new h(j));
    }

    private static void a(c cVar) {
        ReentrantReadWriteLock reentrantReadWriteLock = c;
        reentrantReadWriteLock.writeLock().lock();
        if (b == null) {
            b = cVar;
        }
        reentrantReadWriteLock.writeLock().unlock();
    }

    public static void a(String... strArr) {
        if (strArr == null) {
            return;
        }
        a(new i(strArr));
    }

    public static c b() {
        return b;
    }

    public d a() {
        ReentrantReadWriteLock reentrantReadWriteLock = c;
        reentrantReadWriteLock.readLock().lock();
        c cVar = b;
        d nVar = cVar != null ? new n(cVar, this.a.getDefaultTimeZone()) : new m(new Date(), this.a.getDefaultTimeZone());
        reentrantReadWriteLock.readLock().unlock();
        return nVar;
    }

    public d a(Date date, TimeZone timeZone) {
        if (timeZone == null) {
            m mVar = new m(date, this.a.getDefaultTimeZone());
            mVar.c();
            return mVar;
        }
        m mVar2 = new m(date, timeZone);
        mVar2.a(true);
        return mVar2;
    }
}
