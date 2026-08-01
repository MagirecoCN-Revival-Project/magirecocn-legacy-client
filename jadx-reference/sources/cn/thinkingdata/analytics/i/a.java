package cn.thinkingdata.analytics.i;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class a {
    private static a b;
    private final ExecutorService a;

    /* renamed from: cn.thinkingdata.analytics.i.a$a, reason: collision with other inner class name */
    /* loaded from: classes.dex */
    class ThreadFactoryC0014a implements ThreadFactory {
        ThreadFactoryC0014a(a aVar) {
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "TD.TaskExecuteThread");
        }
    }

    private a() {
        new LinkedBlockingQueue();
        this.a = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new ThreadFactoryC0014a(this));
    }

    public static synchronized a a() {
        a aVar;
        synchronized (a.class) {
            try {
                if (b == null) {
                    b = new a();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            aVar = b;
        }
        return aVar;
    }

    public void a(Runnable runnable) {
        try {
            this.a.execute(runnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
