.class final Lio/kamihama/magianative/CNDownloaderFix$SizeProbeTask;
.super Ljava/lang/Object;
.source "CNDownloaderFix.java"

# interfaces
.implements Ljava/util/concurrent/Callable;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lio/kamihama/magianative/CNDownloaderFix;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1a
    name = "SizeProbeTask"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Object;",
        "Ljava/util/concurrent/Callable<",
        "Ljava/lang/Boolean;",
        ">;"
    }
.end annotation


# instance fields
.field private final index:I


# direct methods
.method constructor <init>(I)V
    .locals 0

    .line 451
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput p1, p0, Lio/kamihama/magianative/CNDownloaderFix$SizeProbeTask;->index:I

    return-void
.end method


# virtual methods
.method public call()Ljava/lang/Boolean;
    .locals 11

    .line 453
    const-string v0, "MagiaCNDownloader"

    invoke-static {}, Lio/kamihama/magianative/CNDownloaderFix;->access$000()[Ljava/lang/String;

    move-result-object v1

    iget v2, p0, Lio/kamihama/magianative/CNDownloaderFix$SizeProbeTask;->index:I

    aget-object v1, v1, v2

    .line 456
    :try_start_0
    invoke-static {v1}, Lio/kamihama/magianative/CNDownloaderFix;->access$100(Ljava/lang/String;)Ljava/io/File;

    move-result-object v2

    invoke-static {v2}, Lio/kamihama/magianative/CNDownloaderFix;->access$200(Ljava/io/File;)J

    move-result-wide v2

    .line 457
    const-wide/16 v4, 0x0

    cmp-long v6, v2, v4

    if-lez v6, :cond_0

    .line 458
    iget v4, p0, Lio/kamihama/magianative/CNDownloaderFix$SizeProbeTask;->index:I

    invoke-static {v4, v2, v3}, Lio/kamihama/magianative/CNDownloaderFix;->access$300(IJ)V

    .line 459
    sget-object v0, Ljava/lang/Boolean;->TRUE:Ljava/lang/Boolean;

    return-object v0

    .line 462
    :cond_0
    new-instance v2, Ljava/io/File;

    const-string v3, "/data/data/io.kamihama.totentanz/files"

    invoke-direct {v2, v3, v1}, Ljava/io/File;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    .line 463
    invoke-virtual {v2}, Ljava/io/File;->isFile()Z

    move-result v3

    if-eqz v3, :cond_1

    invoke-virtual {v2}, Ljava/io/File;->length()J

    move-result-wide v6

    cmp-long v3, v6, v4

    if-lez v3, :cond_1

    .line 464
    iget v3, p0, Lio/kamihama/magianative/CNDownloaderFix$SizeProbeTask;->index:I

    invoke-virtual {v2}, Ljava/io/File;->length()J

    move-result-wide v4

    invoke-static {v3, v4, v5}, Lio/kamihama/magianative/CNDownloaderFix;->access$300(IJ)V

    .line 465
    sget-object v0, Ljava/lang/Boolean;->TRUE:Ljava/lang/Boolean;

    return-object v0

    .line 470
    :cond_1
    invoke-static {}, Lio/kamihama/magianative/CNMirrors;->healthy()Ljava/util/List;

    move-result-object v2

    .line 471
    invoke-interface {v2}, Ljava/util/List;->size()I

    move-result v2

    const/4 v3, 0x3

    invoke-static {v2, v3}, Ljava/lang/Math;->min(II)I

    move-result v2

    .line 472
    const/4 v3, 0x1

    :goto_0
    if-gt v3, v2, :cond_3

    .line 473
    invoke-static {v3}, Lio/kamihama/magianative/CNMirrors;->pick(I)Lio/kamihama/magianative/CNMirrors$Mirror;

    move-result-object v6
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    .line 475
    nop

    .line 476
    :try_start_1
    invoke-virtual {v6, v1}, Lio/kamihama/magianative/CNMirrors$Mirror;->urlFor(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v7

    .line 475
    const/4 v8, 0x0

    invoke-static {v7, v8}, Lio/kamihama/magianative/CNChunkedDownload;->probe(Ljava/lang/String;Z)Lio/kamihama/magianative/CNChunkedDownload$Probe;

    move-result-object v7

    .line 477
    iget-wide v8, v7, Lio/kamihama/magianative/CNChunkedDownload$Probe;->total:J

    cmp-long v10, v8, v4

    if-lez v10, :cond_2

    .line 478
    iget v8, p0, Lio/kamihama/magianative/CNDownloaderFix$SizeProbeTask;->index:I

    iget-wide v9, v7, Lio/kamihama/magianative/CNChunkedDownload$Probe;->total:J

    invoke-static {v8, v9, v10}, Lio/kamihama/magianative/CNDownloaderFix;->access$300(IJ)V

    .line 479
    sget-object v0, Ljava/lang/Boolean;->TRUE:Ljava/lang/Boolean;
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    return-object v0

    .line 484
    :cond_2
    goto :goto_1

    .line 481
    :catchall_0
    move-exception v7

    .line 482
    :try_start_2
    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "\u5c3a\u5bf8\u63a2\u6d4b\u5f02\u5e38\uff08\u6362\u7ebf\u91cd\u8bd5\uff09: "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    const-string v9, " mirror="

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    iget-object v6, v6, Lio/kamihama/magianative/CNMirrors$Mirror;->name:Ljava/lang/String;

    invoke-virtual {v8, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v0, v6, v7}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 472
    :goto_1
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    .line 486
    :cond_3
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "\u5c3a\u5bf8\u63a2\u6d4b\u5931\u8d25\uff08\u4e0d\u5f71\u54cd\u4e0b\u8f7d\uff09: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_1

    .line 489
    goto :goto_2

    .line 487
    :catchall_1
    move-exception v2

    .line 488
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "\u5c3a\u5bf8\u63a2\u6d4b\u5f02\u5e38\uff08\u4e0d\u5f71\u54cd\u4e0b\u8f7d\uff09: "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1, v2}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 490
    :goto_2
    sget-object v0, Ljava/lang/Boolean;->FALSE:Ljava/lang/Boolean;

    return-object v0
.end method

.method public bridge synthetic call()Ljava/lang/Object;
    .locals 1
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .line 449
    invoke-virtual {p0}, Lio/kamihama/magianative/CNDownloaderFix$SizeProbeTask;->call()Ljava/lang/Boolean;

    move-result-object v0

    return-object v0
.end method
