package cn.thinkingdata.core.network;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class TEHttpTaskManager {
    private static final int POOL_SIZE = 2;
    private static final String THREAD_TE_NET = "TE.NetWorkTask";
    private static volatile ExecutorService executor;

    /* loaded from: classes.dex */
    static class ThreadFactoryWithName implements ThreadFactory {
        private final String name;

        ThreadFactoryWithName(String str) {
            this.name = str;
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, this.name);
        }
    }

    private TEHttpTaskManager() {
    }

    public static ExecutorService getExecutor() {
        if (executor == null) {
            synchronized (TEHttpTaskManager.class) {
                if (executor == null) {
                    executor = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new ThreadFactoryWithName(THREAD_TE_NET));
                }
            }
        }
        return executor;
    }
}
