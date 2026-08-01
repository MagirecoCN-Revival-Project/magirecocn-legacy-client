package cn.thinkingdata.core.utils.pool;

import cn.thinkingdata.core.utils.TDLog;
import java.lang.Thread;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public class DefaultThreadFactory implements ThreadFactory {
    private static final String TAG = "ThinkingAnalytics.DefaultThreadFactory";
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final String namePrefix;
    private final AtomicInteger threadNumber = new AtomicInteger(1);

    public DefaultThreadFactory() {
        SecurityManager securityManager = System.getSecurityManager();
        this.group = securityManager != null ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.namePrefix = "task pool No." + poolNumber.getAndIncrement() + ", thread No.";
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        String str = this.namePrefix + this.threadNumber.getAndIncrement();
        TDLog.i(TAG, "Thread production, name is [" + str + "]");
        Thread thread = new Thread(this.group, runnable, str, 0L);
        if (thread.isDaemon()) {
            thread.setDaemon(false);
        }
        if (thread.getPriority() != 5) {
            thread.setPriority(5);
        }
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { // from class: cn.thinkingdata.core.utils.pool.DefaultThreadFactory.1
            @Override // java.lang.Thread.UncaughtExceptionHandler
            public void uncaughtException(Thread thread2, Throwable th) {
                TDLog.i(DefaultThreadFactory.TAG, "Running task appeared exception! Thread [" + thread2.getName() + "], because [" + th.getMessage() + "]");
            }
        });
        return thread;
    }
}
