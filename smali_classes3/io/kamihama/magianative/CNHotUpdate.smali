.class public final Lio/kamihama/magianative/CNHotUpdate;
.super Ljava/lang/Object;
.source "CNHotUpdate.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lio/kamihama/magianative/CNHotUpdate$HotSink;
    }
.end annotation


# static fields
.field private static final CONNECT_TIMEOUT_MS:I = 0x3a98

.field private static final MAX_ATTEMPTS:I = 0x4

.field private static final READ_TIMEOUT_MS:I = 0x7530

.field private static final TAG:Ljava/lang/String; = "MagiaCNHotUpdate"


# direct methods
.method private constructor <init>()V
    .locals 0

    .line 40
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method private static closeQuietly(Ljava/io/Closeable;)V
    .locals 0

    .line 317
    if-eqz p0, :cond_0

    .line 318
    :try_start_0
    invoke-interface {p0}, Ljava/io/Closeable;->close()V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    goto :goto_0

    :catchall_0
    move-exception p0

    .line 320
    :cond_0
    :goto_0
    return-void
.end method

.method public static download(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)Z
    .locals 10

    .line 55
    const-string v0, " mirror="

    const-string v1, " attempt="

    const/4 v2, 0x0

    const-string v3, "MagiaCNHotUpdate"

    if-eqz p0, :cond_8

    if-nez p1, :cond_0

    goto/16 :goto_3

    .line 59
    :cond_0
    new-instance v4, Ljava/io/File;

    invoke-direct {v4, p1}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 62
    invoke-virtual {v4}, Ljava/io/File;->isFile()Z

    move-result v5

    const/4 v6, 0x1

    if-eqz v5, :cond_1

    .line 63
    new-instance p0, Ljava/lang/StringBuilder;

    invoke-direct {p0}, Ljava/lang/StringBuilder;-><init>()V

    const-string p2, "\u76ee\u6807\u5df2\u5b58\u5728\uff0c\u8df3\u8fc7\u4e0b\u8f7d: "

    invoke-virtual {p0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-static {v3, p0}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 64
    invoke-virtual {v4}, Ljava/io/File;->length()J

    move-result-wide p0

    long-to-double p0, p0

    const-wide v0, 0x412e848000000000L    # 1000000.0

    div-double/2addr p0, v0

    double-to-float p0, p0

    invoke-static {p3, p0}, Lio/kamihama/magianative/CNCNDownloadUI;->setFileSize(IF)V

    .line 65
    invoke-static {p3}, Lio/kamihama/magianative/CNHotUpdate;->markDone(I)V

    .line 66
    return v6

    .line 69
    :cond_1
    invoke-static {p0}, Lio/kamihama/magianative/CNHotUpdate;->mainLineFileName(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    .line 70
    if-nez p1, :cond_2

    .line 72
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string p2, "\u975e\u4e3b\u7ebf\u5730\u5740\uff0c\u76f4\u8fde\u4e0b\u8f7d: "

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v3, p1}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 74
    :try_start_0
    invoke-static {p0, v4, p3, v2}, Lio/kamihama/magianative/CNHotUpdate;->singleStream(Ljava/lang/String;Ljava/io/File;IZ)V

    .line 75
    invoke-static {p3}, Lio/kamihama/magianative/CNHotUpdate;->markDone(I)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 76
    return v6

    .line 77
    :catchall_0
    move-exception p1

    .line 78
    new-instance p2, Ljava/lang/StringBuilder;

    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    const-string p3, "\u76f4\u8fde\u4e0b\u8f7d\u5931\u8d25: "

    invoke-virtual {p2, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-static {v3, p0, p1}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 79
    return v2

    .line 84
    :cond_2
    invoke-static {}, Lio/kamihama/magianative/CNMirrors;->isLoaded()Z

    move-result p0

    if-nez p0, :cond_3

    .line 85
    invoke-static {v2}, Lio/kamihama/magianative/CNMirrors;->refresh(Z)V

    .line 86
    invoke-static {}, Lio/kamihama/magianative/CNMirrors;->isLoaded()Z

    move-result p0

    if-nez p0, :cond_3

    invoke-static {v6}, Lio/kamihama/magianative/CNMirrors;->refresh(Z)V

    .line 89
    :cond_3
    new-instance p0, Ljava/lang/StringBuilder;

    invoke-direct {p0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "\u5f00\u59cb\u4e0b\u8f7d "

    invoke-virtual {p0, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string p2, " file="

    invoke-virtual {p0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string p2, " \u53ef\u7528\u7ebf\u8def="

    invoke-virtual {p0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    .line 90
    invoke-static {}, Lio/kamihama/magianative/CNMirrors;->healthy()Ljava/util/List;

    move-result-object p2

    invoke-interface {p2}, Ljava/util/List;->size()I

    move-result p2

    invoke-virtual {p0, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    .line 89
    invoke-static {v3, p0}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 92
    const/4 p0, 0x1

    :goto_0
    const/4 p2, 0x4

    if-gt p0, p2, :cond_7

    .line 93
    invoke-static {}, Ljava/lang/Thread;->currentThread()Ljava/lang/Thread;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/Thread;->isInterrupted()Z

    move-result v5

    if-eqz v5, :cond_4

    .line 94
    new-instance p0, Ljava/lang/StringBuilder;

    invoke-direct {p0}, Ljava/lang/StringBuilder;-><init>()V

    const-string p2, "\u7ebf\u7a0b\u88ab\u4e2d\u65ad\uff0c\u653e\u5f03 "

    invoke-virtual {p0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-static {v3, p0}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;)V

    .line 95
    invoke-static {p3}, Lio/kamihama/magianative/CNHotUpdate;->markFailed(I)V

    .line 96
    return v2

    .line 98
    :cond_4
    invoke-static {p0}, Lio/kamihama/magianative/CNMirrors;->pick(I)Lio/kamihama/magianative/CNMirrors$Mirror;

    move-result-object v5

    .line 99
    rem-int/lit8 v7, p0, 0x2

    if-nez v7, :cond_5

    const/4 v7, 0x1

    goto :goto_1

    :cond_5
    const/4 v7, 0x0

    .line 100
    :goto_1
    invoke-virtual {v5, p1}, Lio/kamihama/magianative/CNMirrors$Mirror;->urlFor(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v8

    .line 101
    const/4 v9, 0x0

    invoke-static {p3, v9}, Lio/kamihama/magianative/CNCNDownloadUI;->setDownloadSpeed(IF)V

    .line 104
    :try_start_1
    invoke-static {v8, v4, p3, v7, v5}, Lio/kamihama/magianative/CNHotUpdate;->fetch(Ljava/lang/String;Ljava/io/File;IZLio/kamihama/magianative/CNMirrors$Mirror;)V

    .line 105
    invoke-static {v5}, Lio/kamihama/magianative/CNMirrors;->reportSuccess(Lio/kamihama/magianative/CNMirrors$Mirror;)V

    .line 106
    invoke-static {p3}, Lio/kamihama/magianative/CNHotUpdate;->markDone(I)V

    .line 107
    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "\u4e0b\u8f7d\u5b8c\u6210 "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, p0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    iget-object v8, v5, Lio/kamihama/magianative/CNMirrors$Mirror;->name:Ljava/lang/String;

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v3, v7}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    .line 109
    return v6

    .line 110
    :catchall_1
    move-exception v7

    .line 111
    invoke-virtual {v7}, Ljava/lang/Throwable;->getMessage()Ljava/lang/String;

    move-result-object v8

    invoke-static {v8}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v8

    invoke-static {v5, v8}, Lio/kamihama/magianative/CNMirrors;->reportFailure(Lio/kamihama/magianative/CNMirrors$Mirror;Ljava/lang/String;)V

    .line 112
    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "\u4e0b\u8f7d\u5931\u8d25 "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8, p0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    iget-object v5, v5, Lio/kamihama/magianative/CNMirrors$Mirror;->name:Ljava/lang/String;

    invoke-virtual {v8, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v3, v5, v7}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 114
    if-ge p0, p2, :cond_6

    .line 115
    add-int/lit8 p2, p0, -0x1

    const-wide/16 v7, 0x7d0

    shl-long/2addr v7, p2

    .line 116
    new-instance p2, Ljava/lang/StringBuilder;

    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "\u7b49\u5f85 "

    invoke-virtual {p2, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2, v7, v8}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object p2

    const-string v5, "ms \u540e\u6362\u7ebf\u91cd\u8bd5"

    invoke-virtual {p2, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p2

    invoke-static {v3, p2}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 118
    :try_start_2
    invoke-static {v7, v8}, Ljava/lang/Thread;->sleep(J)V
    :try_end_2
    .catch Ljava/lang/InterruptedException; {:try_start_2 .. :try_end_2} :catch_0

    .line 123
    goto :goto_2

    .line 119
    :catch_0
    move-exception p0

    .line 120
    invoke-static {}, Ljava/lang/Thread;->currentThread()Ljava/lang/Thread;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/Thread;->interrupt()V

    .line 121
    invoke-static {p3}, Lio/kamihama/magianative/CNHotUpdate;->markFailed(I)V

    .line 122
    return v2

    .line 92
    :cond_6
    :goto_2
    add-int/lit8 p0, p0, 0x1

    goto/16 :goto_0

    .line 127
    :cond_7
    new-instance p0, Ljava/lang/StringBuilder;

    invoke-direct {p0}, Ljava/lang/StringBuilder;-><init>()V

    const-string p2, "\u5168\u90e8\u7ebf\u8def\u5747\u5931\u8d25: "

    invoke-virtual {p0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-static {v3, p0}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;)V

    .line 128
    invoke-static {p3}, Lio/kamihama/magianative/CNHotUpdate;->markFailed(I)V

    .line 129
    return v2

    .line 56
    :cond_8
    :goto_3
    new-instance p2, Ljava/lang/StringBuilder;

    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    const-string p3, "\u53c2\u6570\u4e3a\u7a7a\uff0c\u653e\u5f03\u4e0b\u8f7d url="

    invoke-virtual {p2, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string p2, " dest="

    invoke-virtual {p0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-static {v3, p0}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;)V

    .line 57
    return v2
.end method

.method private static fetch(Ljava/lang/String;Ljava/io/File;IZLio/kamihama/magianative/CNMirrors$Mirror;)V
    .locals 9
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 135
    invoke-virtual {p4}, Lio/kamihama/magianative/CNMirrors$Mirror;->effectiveChunks()I

    move-result v0

    .line 136
    const/4 v1, 0x1

    if-le v0, v1, :cond_2

    .line 137
    invoke-static {p0, p3}, Lio/kamihama/magianative/CNChunkedDownload;->probe(Ljava/lang/String;Z)Lio/kamihama/magianative/CNChunkedDownload$Probe;

    move-result-object v6

    .line 138
    iget-boolean v2, v6, Lio/kamihama/magianative/CNChunkedDownload$Probe;->rangeSupported:Z

    const-string v3, "MagiaCNHotUpdate"

    if-eqz v2, :cond_1

    iget-wide v4, v6, Lio/kamihama/magianative/CNChunkedDownload$Probe;->total:J

    const-wide/16 v7, 0x0

    cmp-long v2, v4, v7

    if-lez v2, :cond_1

    .line 139
    nop

    .line 140
    invoke-static {}, Lio/kamihama/magianative/CNMirrors;->minChunkBytes()J

    move-result-wide v4

    .line 141
    cmp-long v2, v4, v7

    if-lez v2, :cond_0

    .line 142
    iget-wide v7, v6, Lio/kamihama/magianative/CNChunkedDownload$Probe;->total:J

    div-long/2addr v7, v4

    .line 143
    int-to-long v4, v0

    cmp-long v2, v7, v4

    if-gez v2, :cond_0

    const-wide/16 v4, 0x1

    invoke-static {v4, v5, v7, v8}, Ljava/lang/Math;->max(JJ)J

    move-result-wide v4

    long-to-int v0, v4

    move v4, v0

    goto :goto_0

    .line 145
    :cond_0
    move v4, v0

    :goto_0
    if-le v4, v1, :cond_1

    .line 146
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "\u5206\u7247\u4e0b\u8f7d "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {p1}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " chunks="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " bytes="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-wide v1, v6, Lio/kamihama/magianative/CNChunkedDownload$Probe;->total:J

    invoke-virtual {v0, v1, v2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " mirror="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v1, p4, Lio/kamihama/magianative/CNMirrors$Mirror;->name:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v3, v0}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 148
    iget-wide v0, v6, Lio/kamihama/magianative/CNChunkedDownload$Probe;->total:J

    long-to-double v0, v0

    const-wide v2, 0x412e848000000000L    # 1000000.0

    div-double/2addr v0, v2

    double-to-float v0, v0

    invoke-static {p2, v0}, Lio/kamihama/magianative/CNCNDownloadUI;->setFileSize(IF)V

    .line 149
    new-instance v7, Lio/kamihama/magianative/CNHotUpdate$HotSink;

    invoke-direct {v7, p2}, Lio/kamihama/magianative/CNHotUpdate$HotSink;-><init>(I)V

    move-object v2, p0

    move-object v3, p1

    move v5, p3

    move-object v8, p4

    invoke-static/range {v2 .. v8}, Lio/kamihama/magianative/CNChunkedDownload;->download(Ljava/lang/String;Ljava/io/File;IZLio/kamihama/magianative/CNChunkedDownload$Probe;Lio/kamihama/magianative/CNChunkedDownload$Sink;Lio/kamihama/magianative/CNMirrors$Mirror;)Lio/kamihama/magianative/CNChunkedDownload$Result;

    .line 151
    return-void

    .line 154
    :cond_1
    new-instance p4, Ljava/lang/StringBuilder;

    invoke-direct {p4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "\u4e0d\u652f\u6301 Range \u6216\u6587\u4ef6\u8fc7\u5c0f\uff0c\u6539\u7528\u5355\u7ebf\u7a0b: "

    invoke-virtual {p4, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p4

    invoke-virtual {p1}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p4, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p4

    invoke-virtual {p4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p4

    invoke-static {v3, p4}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 156
    :cond_2
    invoke-static {p0, p1, p2, p3}, Lio/kamihama/magianative/CNHotUpdate;->singleStream(Ljava/lang/String;Ljava/io/File;IZ)V

    .line 157
    return-void
.end method

.method private static mainLineFileName(Ljava/lang/String;)Ljava/lang/String;
    .locals 3

    .line 281
    nop

    .line 282
    const-string v0, "https://assets.magireco.top/"

    invoke-virtual {p0, v0}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v1

    const/4 v2, 0x0

    if-nez v1, :cond_0

    return-object v2

    .line 283
    :cond_0
    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v0

    invoke-virtual {p0, v0}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object p0

    .line 284
    invoke-virtual {p0}, Ljava/lang/String;->length()I

    move-result v0

    if-nez v0, :cond_1

    return-object v2

    .line 286
    :cond_1
    const/16 v0, 0x2f

    invoke-virtual {p0, v0}, Ljava/lang/String;->indexOf(I)I

    move-result v0

    if-gez v0, :cond_3

    const/16 v0, 0x3f

    invoke-virtual {p0, v0}, Ljava/lang/String;->indexOf(I)I

    move-result v0

    if-gez v0, :cond_3

    const/16 v0, 0x23

    invoke-virtual {p0, v0}, Ljava/lang/String;->indexOf(I)I

    move-result v0

    if-ltz v0, :cond_2

    goto :goto_0

    .line 289
    :cond_2
    return-object p0

    .line 287
    :cond_3
    :goto_0
    return-object v2
.end method

.method private static markDone(I)V
    .locals 1

    .line 293
    const/4 v0, 0x0

    invoke-static {p0, v0}, Lio/kamihama/magianative/CNCNDownloadUI;->setDownloadSpeed(IF)V

    .line 294
    invoke-static {p0}, Lio/kamihama/magianative/CNCNDownloadUI;->markFileDone(I)V

    .line 295
    return-void
.end method

.method private static markFailed(I)V
    .locals 2

    .line 298
    const/4 v0, 0x0

    invoke-static {p0, v0}, Lio/kamihama/magianative/CNCNDownloadUI;->setDownloadSpeed(IF)V

    .line 299
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->fileStatus:[I

    if-eqz v0, :cond_0

    if-ltz p0, :cond_0

    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->fileStatus:[I

    array-length v0, v0

    if-ge p0, v0, :cond_0

    .line 301
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->fileStatus:[I

    const/4 v1, 0x3

    aput v1, v0, p0

    .line 303
    :cond_0
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->throttledUpdate()V

    .line 304
    return-void
.end method

.method private static parseLong(Ljava/lang/String;J)J
    .locals 4

    .line 307
    if-nez p0, :cond_0

    return-wide p1

    .line 309
    :cond_0
    :try_start_0
    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v0
    :try_end_0
    .catch Ljava/lang/NumberFormatException; {:try_start_0 .. :try_end_0} :catch_0

    .line 310
    const-wide/16 v2, 0x0

    cmp-long p0, v0, v2

    if-ltz p0, :cond_1

    move-wide p1, v0

    :cond_1
    return-wide p1

    .line 311
    :catch_0
    move-exception p0

    .line 312
    return-wide p1
.end method

.method private static singleStream(Ljava/lang/String;Ljava/io/File;IZ)V
    .locals 28
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 186
    move-object/from16 v0, p0

    move-object/from16 v1, p1

    move/from16 v2, p2

    new-instance v3, Ljava/io/File;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->getPath()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, ".part"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-direct {v3, v4}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 187
    invoke-virtual {v3}, Ljava/io/File;->getParentFile()Ljava/io/File;

    move-result-object v4

    .line 188
    if-eqz v4, :cond_1

    invoke-virtual {v4}, Ljava/io/File;->isDirectory()Z

    move-result v5

    if-nez v5, :cond_1

    invoke-virtual {v4}, Ljava/io/File;->mkdirs()Z

    move-result v5

    if-nez v5, :cond_1

    .line 189
    invoke-virtual {v4}, Ljava/io/File;->isDirectory()Z

    move-result v5

    if-eqz v5, :cond_0

    goto :goto_0

    .line 190
    :cond_0
    new-instance v0, Ljava/io/IOException;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "\u65e0\u6cd5\u521b\u5efa\u4e0b\u8f7d\u76ee\u5f55: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v0

    .line 192
    :cond_1
    :goto_0
    invoke-virtual {v3}, Ljava/io/File;->isFile()Z

    move-result v4

    const-wide/16 v5, 0x0

    if-eqz v4, :cond_2

    invoke-virtual {v3}, Ljava/io/File;->length()J

    move-result-wide v7

    goto :goto_1

    :cond_2
    move-wide v7, v5

    .line 194
    :goto_1
    new-instance v4, Ljava/net/URL;

    invoke-direct {v4, v0}, Ljava/net/URL;-><init>(Ljava/lang/String;)V

    .line 196
    if-eqz p3, :cond_3

    sget-object v9, Ljava/net/Proxy;->NO_PROXY:Ljava/net/Proxy;

    invoke-virtual {v4, v9}, Ljava/net/URL;->openConnection(Ljava/net/Proxy;)Ljava/net/URLConnection;

    move-result-object v4

    goto :goto_2

    :cond_3
    invoke-virtual {v4}, Ljava/net/URL;->openConnection()Ljava/net/URLConnection;

    move-result-object v4

    :goto_2
    check-cast v4, Ljava/net/HttpURLConnection;

    .line 197
    const/16 v9, 0x3a98

    invoke-virtual {v4, v9}, Ljava/net/HttpURLConnection;->setConnectTimeout(I)V

    .line 198
    const/16 v9, 0x7530

    invoke-virtual {v4, v9}, Ljava/net/HttpURLConnection;->setReadTimeout(I)V

    .line 199
    const/4 v9, 0x0

    invoke-virtual {v4, v9}, Ljava/net/HttpURLConnection;->setUseCaches(Z)V

    .line 200
    const/4 v10, 0x1

    invoke-virtual {v4, v10}, Ljava/net/HttpURLConnection;->setInstanceFollowRedirects(Z)V

    .line 201
    const-string v11, "Accept-Encoding"

    const-string v12, "identity"

    invoke-virtual {v4, v11, v12}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 202
    const-string v11, "Connection"

    const-string v12, "close"

    invoke-virtual {v4, v11, v12}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 203
    cmp-long v11, v7, v5

    if-lez v11, :cond_4

    new-instance v12, Ljava/lang/StringBuilder;

    invoke-direct {v12}, Ljava/lang/StringBuilder;-><init>()V

    const-string v13, "bytes="

    invoke-virtual {v12, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v12

    invoke-virtual {v12, v7, v8}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v12

    const-string v13, "-"

    invoke-virtual {v12, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v12

    invoke-virtual {v12}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v12

    const-string v13, "Range"

    invoke-virtual {v4, v13, v12}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 205
    :cond_4
    nop

    .line 206
    nop

    .line 208
    :try_start_0
    invoke-virtual {v4}, Ljava/net/HttpURLConnection;->getResponseCode()I

    move-result v13
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_5

    .line 211
    const-string v14, "Content-Length"

    const-wide/16 v9, -0x1

    if-lez v11, :cond_6

    const/16 v11, 0xce

    if-ne v13, v11, :cond_6

    .line 212
    nop

    .line 213
    :try_start_1
    invoke-virtual {v4, v14}, Ljava/net/HttpURLConnection;->getHeaderField(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0, v9, v10}, Lio/kamihama/magianative/CNHotUpdate;->parseLong(Ljava/lang/String;J)J

    move-result-wide v13
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 214
    cmp-long v0, v13, v5

    if-ltz v0, :cond_5

    add-long v9, v7, v13

    .line 215
    :cond_5
    const/4 v15, 0x1

    goto :goto_3

    .line 268
    :catchall_0
    move-exception v0

    move-object v1, v0

    const/4 v2, 0x0

    const/4 v12, 0x0

    goto/16 :goto_a

    .line 215
    :cond_6
    const/16 v11, 0xc8

    if-ne v13, v11, :cond_11

    .line 217
    nop

    .line 218
    nop

    .line 219
    :try_start_2
    invoke-virtual {v4, v14}, Ljava/net/HttpURLConnection;->getHeaderField(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0, v9, v10}, Lio/kamihama/magianative/CNHotUpdate;->parseLong(Ljava/lang/String;J)J

    move-result-wide v9
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_5

    move-wide v7, v5

    const/4 v15, 0x0

    .line 223
    :goto_3
    const-wide v13, 0x412e848000000000L    # 1000000.0

    cmp-long v0, v9, v5

    if-lez v0, :cond_7

    .line 224
    long-to-double v5, v9

    div-double/2addr v5, v13

    double-to-float v5, v5

    :try_start_3
    invoke-static {v2, v5}, Lio/kamihama/magianative/CNCNDownloadUI;->setFileSize(IF)V
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_0

    .line 227
    :cond_7
    :try_start_4
    new-instance v5, Ljava/io/BufferedInputStream;

    invoke-virtual {v4}, Ljava/net/HttpURLConnection;->getInputStream()Ljava/io/InputStream;

    move-result-object v6

    const/high16 v11, 0x10000

    invoke-direct {v5, v6, v11}, Ljava/io/BufferedInputStream;-><init>(Ljava/io/InputStream;I)V
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_5

    .line 228
    :try_start_5
    new-instance v6, Ljava/io/FileOutputStream;

    invoke-direct {v6, v3, v15}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;Z)V
    :try_end_5
    .catchall {:try_start_5 .. :try_end_5} :catchall_3

    .line 229
    :try_start_6
    new-array v11, v11, [B

    .line 230
    nop

    .line 231
    invoke-static {}, Ljava/lang/System;->nanoTime()J

    move-result-wide v16

    const-wide/16 v18, 0x0

    const-wide/16 v20, 0x0

    .line 233
    :goto_4
    invoke-virtual {v5, v11}, Ljava/io/InputStream;->read([B)I

    move-result v15

    const/4 v12, -0x1

    if-eq v15, v12, :cond_b

    .line 234
    if-nez v15, :cond_8

    goto :goto_4

    .line 235
    :cond_8
    const/4 v12, 0x0

    invoke-virtual {v6, v11, v12, v15}, Ljava/io/FileOutputStream;->write([BII)V

    .line 236
    int-to-long v12, v15

    add-long v18, v18, v12

    .line 237
    add-long v12, v7, v18

    .line 238
    long-to-double v14, v12

    const-wide v22, 0x412e848000000000L    # 1000000.0

    div-double v14, v14, v22

    double-to-float v14, v14

    invoke-static {v2, v14}, Lio/kamihama/magianative/CNCNDownloadUI;->setFileDownloaded(IF)V

    .line 239
    if-lez v0, :cond_9

    .line 240
    const-wide/16 v14, 0x64

    mul-long v12, v12, v14

    div-long/2addr v12, v9

    const-wide/16 v14, 0x0

    invoke-static {v14, v15, v12, v13}, Ljava/lang/Math;->max(JJ)J

    move-result-wide v12

    const-wide/16 v14, 0x64

    invoke-static {v14, v15, v12, v13}, Ljava/lang/Math;->min(JJ)J

    move-result-wide v12

    long-to-int v13, v12

    .line 241
    invoke-static {v2, v13}, Lio/kamihama/magianative/CNCNDownloadUI;->updateFileProgress(II)V

    .line 243
    :cond_9
    invoke-static {}, Ljava/lang/System;->nanoTime()J

    move-result-wide v12

    .line 244
    sub-long v14, v12, v16

    .line 245
    move-wide/from16 v24, v7

    sget-object v7, Ljava/util/concurrent/TimeUnit;->MILLISECONDS:Ljava/util/concurrent/TimeUnit;

    move-object v8, v11

    move-wide/from16 v26, v12

    const-wide/16 v11, 0x1f4

    invoke-virtual {v7, v11, v12}, Ljava/util/concurrent/TimeUnit;->toNanos(J)J

    move-result-wide v11

    cmp-long v7, v14, v11

    if-ltz v7, :cond_a

    .line 246
    sub-long v11, v18, v20

    long-to-double v11, v11

    const-wide v16, 0x41cdcd6500000000L    # 1.0E9

    mul-double v11, v11, v16

    long-to-double v13, v14

    div-double/2addr v11, v13

    const-wide v13, 0x412e848000000000L    # 1000000.0

    div-double/2addr v11, v13

    double-to-float v7, v11

    invoke-static {v2, v7}, Lio/kamihama/magianative/CNCNDownloadUI;->setDownloadSpeed(IF)V

    .line 248
    nop

    .line 249
    move-wide/from16 v20, v18

    move-wide/from16 v16, v26

    goto :goto_5

    .line 245
    :cond_a
    const-wide v13, 0x412e848000000000L    # 1000000.0

    .line 251
    :goto_5
    move-object v11, v8

    move-wide/from16 v7, v24

    goto :goto_4

    .line 252
    :cond_b
    invoke-virtual {v6}, Ljava/io/FileOutputStream;->flush()V

    .line 253
    invoke-virtual {v6}, Ljava/io/FileOutputStream;->getFD()Ljava/io/FileDescriptor;

    move-result-object v2

    invoke-virtual {v2}, Ljava/io/FileDescriptor;->sync()V

    .line 254
    invoke-static {v6}, Lio/kamihama/magianative/CNHotUpdate;->closeQuietly(Ljava/io/Closeable;)V
    :try_end_6
    .catchall {:try_start_6 .. :try_end_6} :catchall_2

    .line 255
    :try_start_7
    invoke-static {v5}, Lio/kamihama/magianative/CNHotUpdate;->closeQuietly(Ljava/io/Closeable;)V
    :try_end_7
    .catchall {:try_start_7 .. :try_end_7} :catchall_3

    .line 258
    if-lez v0, :cond_d

    :try_start_8
    invoke-virtual {v3}, Ljava/io/File;->length()J

    move-result-wide v5

    cmp-long v0, v5, v9

    if-nez v0, :cond_c

    goto :goto_6

    .line 259
    :cond_c
    new-instance v0, Ljava/io/IOException;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "\u4e0b\u8f7d\u4e0d\u5b8c\u6574: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v3}, Ljava/io/File;->length()J

    move-result-wide v2

    invoke-virtual {v1, v2, v3}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, " / "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v9, v10}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v0
    :try_end_8
    .catchall {:try_start_8 .. :try_end_8} :catchall_0

    .line 261
    :cond_d
    :goto_6
    :try_start_9
    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->exists()Z

    move-result v0
    :try_end_9
    .catchall {:try_start_9 .. :try_end_9} :catchall_5

    if-eqz v0, :cond_f

    :try_start_a
    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->delete()Z

    move-result v0

    if-eqz v0, :cond_e

    goto :goto_7

    .line 262
    :cond_e
    new-instance v0, Ljava/io/IOException;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "\u65e0\u6cd5\u66ff\u6362\u76ee\u6807\u6587\u4ef6 "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v0
    :try_end_a
    .catchall {:try_start_a .. :try_end_a} :catchall_0

    .line 264
    :cond_f
    :goto_7
    :try_start_b
    invoke-virtual {v3, v1}, Ljava/io/File;->renameTo(Ljava/io/File;)Z

    move-result v0
    :try_end_b
    .catchall {:try_start_b .. :try_end_b} :catchall_5

    if-eqz v0, :cond_10

    .line 268
    const/4 v2, 0x0

    invoke-static {v2}, Lio/kamihama/magianative/CNHotUpdate;->closeQuietly(Ljava/io/Closeable;)V

    .line 269
    invoke-static {v2}, Lio/kamihama/magianative/CNHotUpdate;->closeQuietly(Ljava/io/Closeable;)V

    .line 270
    :try_start_c
    invoke-virtual {v4}, Ljava/net/HttpURLConnection;->disconnect()V
    :try_end_c
    .catchall {:try_start_c .. :try_end_c} :catchall_1

    goto :goto_8

    :catchall_1
    move-exception v0

    .line 271
    nop

    .line 272
    :goto_8
    return-void

    .line 265
    :cond_10
    const/4 v2, 0x0

    :try_start_d
    new-instance v0, Ljava/io/IOException;

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "\u65e0\u6cd5\u91cd\u547d\u540d "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v5, " -> "

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v0

    .line 268
    :catchall_2
    move-exception v0

    move-object v1, v0

    move-object v2, v5

    move-object v12, v6

    goto :goto_a

    :catchall_3
    move-exception v0

    const/4 v2, 0x0

    move-object v1, v0

    move-object v12, v2

    move-object v2, v5

    goto :goto_a

    .line 221
    :cond_11
    const/4 v2, 0x0

    new-instance v1, Ljava/io/IOException;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "HTTP "

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v13}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v5, " offset="

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v7, v8}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v5, " url="

    invoke-virtual {v3, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {v1, v0}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v1
    :try_end_d
    .catchall {:try_start_d .. :try_end_d} :catchall_4

    .line 268
    :catchall_4
    move-exception v0

    goto :goto_9

    :catchall_5
    move-exception v0

    const/4 v2, 0x0

    :goto_9
    move-object v1, v0

    move-object v12, v2

    :goto_a
    invoke-static {v12}, Lio/kamihama/magianative/CNHotUpdate;->closeQuietly(Ljava/io/Closeable;)V

    .line 269
    invoke-static {v2}, Lio/kamihama/magianative/CNHotUpdate;->closeQuietly(Ljava/io/Closeable;)V

    .line 270
    :try_start_e
    invoke-virtual {v4}, Ljava/net/HttpURLConnection;->disconnect()V
    :try_end_e
    .catchall {:try_start_e .. :try_end_e} :catchall_6

    goto :goto_b

    :catchall_6
    move-exception v0

    .line 271
    :goto_b
    throw v1
.end method
