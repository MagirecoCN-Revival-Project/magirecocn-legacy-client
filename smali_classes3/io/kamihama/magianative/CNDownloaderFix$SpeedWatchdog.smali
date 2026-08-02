.class final Lio/kamihama/magianative/CNDownloaderFix$SpeedWatchdog;
.super Ljava/lang/Object;
.source "CNDownloaderFix.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lio/kamihama/magianative/CNDownloaderFix;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1a
    name = "SpeedWatchdog"
.end annotation


# direct methods
.method private constructor <init>()V
    .locals 0

    .line 920
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method synthetic constructor <init>(Lio/kamihama/magianative/CNDownloaderFix$1;)V
    .locals 0

    .line 920
    invoke-direct {p0}, Lio/kamihama/magianative/CNDownloaderFix$SpeedWatchdog;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 11

    .line 925
    :try_start_0
    invoke-static {}, Lio/kamihama/magianative/RestClient;->getCurrentActivity()Landroid/app/Activity;

    move-result-object v0

    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->ensureVisible(Landroid/app/Activity;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    goto :goto_0

    .line 926
    :catchall_0
    move-exception v0

    :goto_0
    nop

    .line 927
    invoke-static {}, Ljava/lang/System;->nanoTime()J

    move-result-wide v0

    .line 928
    nop

    .line 929
    const/4 v2, 0x0

    const/4 v9, 0x0

    :goto_1
    const/16 v3, 0xf

    if-ge v2, v3, :cond_2

    .line 930
    invoke-static {}, Lio/kamihama/magianative/CNDownloaderFix;->access$800()Ljava/util/concurrent/atomic/AtomicIntegerArray;

    move-result-object v3

    invoke-virtual {v3, v2}, Ljava/util/concurrent/atomic/AtomicIntegerArray;->get(I)I

    move-result v3

    if-nez v3, :cond_0

    goto :goto_2

    .line 931
    :cond_0
    invoke-static {}, Lio/kamihama/magianative/CNDownloaderFix;->access$500()Ljava/util/concurrent/atomic/AtomicLongArray;

    move-result-object v3

    invoke-virtual {v3, v2}, Ljava/util/concurrent/atomic/AtomicLongArray;->get(I)J

    move-result-wide v5

    .line 932
    const-wide/16 v3, 0x0

    cmp-long v7, v5, v3

    if-eqz v7, :cond_1

    sub-long v3, v0, v5

    invoke-static {}, Lio/kamihama/magianative/CNDownloaderFix;->access$900()J

    move-result-wide v7

    cmp-long v10, v3, v7

    if-ltz v10, :cond_1

    .line 933
    invoke-static {}, Lio/kamihama/magianative/CNDownloaderFix;->access$500()Ljava/util/concurrent/atomic/AtomicLongArray;

    move-result-object v3

    const-wide/16 v7, 0x0

    move v4, v2

    invoke-virtual/range {v3 .. v8}, Ljava/util/concurrent/atomic/AtomicLongArray;->compareAndSet(IJJ)Z

    move-result v3

    if-eqz v3, :cond_1

    .line 934
    const/4 v3, 0x0

    invoke-static {v2, v3}, Lio/kamihama/magianative/CNCNDownloadUI;->setDownloadSpeed(IF)V

    .line 935
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "stale-speed-zero file="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-static {}, Lio/kamihama/magianative/CNDownloaderFix;->access$000()[Ljava/lang/String;

    move-result-object v4

    aget-object v4, v4, v2

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    const-string v4, "MagiaCNDownloader"

    invoke-static {v4, v3}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 936
    const/4 v3, 0x1

    const/4 v9, 0x1

    .line 929
    :cond_1
    :goto_2
    add-int/lit8 v2, v2, 0x1

    goto :goto_1

    .line 939
    :cond_2
    if-eqz v9, :cond_3

    .line 940
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->throttledUpdate()V

    .line 942
    :cond_3
    return-void
.end method
