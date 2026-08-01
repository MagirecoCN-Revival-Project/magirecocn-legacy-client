package cn.thinkingdata.analytics.utils;

import android.os.SystemClock;
import cn.thinkingdata.core.utils.TDLog;
import java.util.Date;

/* loaded from: classes.dex */
public class i implements c {
    private long a;
    private long b;
    private final String[] c;
    private final Thread d;

    /* loaded from: classes.dex */
    class a implements Runnable {
        final l a = new l();

        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            for (String str : i.this.c) {
                if (this.a.a(str, 3000)) {
                    TDLog.i("ThinkingAnalytics.NTP", "[ThinkingData] Info: Time Calibration with NTP(" + str + "), diff = " + this.a.a());
                    i.this.a = System.currentTimeMillis() + this.a.a();
                    i.this.b = SystemClock.elapsedRealtime();
                    return;
                }
            }
        }
    }

    public i(String... strArr) {
        Thread thread = new Thread(new a());
        this.d = thread;
        this.c = strArr;
        thread.start();
    }

    @Override // cn.thinkingdata.analytics.utils.c
    public Date a(long j) {
        try {
            this.d.join(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.b == 0 ? new Date((System.currentTimeMillis() - SystemClock.elapsedRealtime()) + j) : new Date((j - this.b) + this.a);
    }
}
