.class final Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;
.super Ljava/lang/Object;
.source "CNChunkedDownload.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lio/kamihama/magianative/CNChunkedDownload;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1a
    name = "ChunkTask"
.end annotation


# instance fields
.field abort:Ljava/util/concurrent/atomic/AtomicBoolean;

.field direct:Z

.field done:Ljava/util/concurrent/atomic/AtomicLongArray;

.field end:J

.field etag:Ljava/lang/String;

.field firstErr:Ljava/util/concurrent/atomic/AtomicReference;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/concurrent/atomic/AtomicReference<",
            "Ljava/io/IOException;",
            ">;"
        }
    .end annotation
.end field

.field idx:I

.field lastMoveNs:Ljava/util/concurrent/atomic/AtomicLong;

.field latch:Ljava/util/concurrent/CountDownLatch;

.field meta:Ljava/io/File;

.field part:Ljava/io/File;

.field rangeIgnored:Ljava/util/concurrent/atomic/AtomicBoolean;

.field sink:Lio/kamihama/magianative/CNChunkedDownload$Sink;

.field start:J

.field total:J

.field totalDone:Ljava/util/concurrent/atomic/AtomicLong;

.field url:Ljava/lang/String;

.field windowBytes:Ljava/util/concurrent/atomic/AtomicLong;

.field windowStart:Ljava/util/concurrent/atomic/AtomicLong;


# direct methods
.method private constructor <init>()V
    .locals 0

    .line 428
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method synthetic constructor <init>(Lio/kamihama/magianative/CNChunkedDownload$1;)V
    .locals 0

    .line 428
    invoke-direct {p0}, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 22

    .line 451
    move-object/from16 v1, p0

    :try_start_0
    iget-object v2, v1, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->url:Ljava/lang/String;

    iget-object v3, v1, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->part:Ljava/io/File;

    iget-wide v4, v1, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->start:J

    iget-wide v6, v1, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->end:J

    iget-object v8, v1, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->done:Ljava/util/concurrent/atomic/AtomicLongArray;

    iget v9, v1, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->idx:I

    iget-boolean v10, v1, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->direct:Z

    iget-object v11, v1, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->meta:Ljava/io/File;

    iget-wide v12, v1, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->total:J

    iget-object v14, v1, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->etag:Ljava/lang/String;

    iget-object v15, v1, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->totalDone:Ljava/util/concurrent/atomic/AtomicLong;

    iget-object v0, v1, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->windowStart:Ljava/util/concurrent/atomic/AtomicLong;

    move-object/from16 v16, v0

    iget-object v0, v1, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->windowBytes:Ljava/util/concurrent/atomic/AtomicLong;

    move-object/from16 v17, v0

    iget-object v0, v1, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->lastMoveNs:Ljava/util/concurrent/atomic/AtomicLong;

    move-object/from16 v18, v0

    iget-object v0, v1, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->abort:Ljava/util/concurrent/atomic/AtomicBoolean;

    move-object/from16 v19, v0

    iget-object v0, v1, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->rangeIgnored:Ljava/util/concurrent/atomic/AtomicBoolean;

    move-object/from16 v20, v0

    iget-object v0, v1, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->sink:Lio/kamihama/magianative/CNChunkedDownload$Sink;

    move-object/from16 v21, v0

    invoke-static/range {v2 .. v21}, Lio/kamihama/magianative/CNChunkedDownload;->access$200(Ljava/lang/String;Ljava/io/File;JJLjava/util/concurrent/atomic/AtomicLongArray;IZLjava/io/File;JLjava/lang/String;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicBoolean;Ljava/util/concurrent/atomic/AtomicBoolean;Lio/kamihama/magianative/CNChunkedDownload$Sink;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    goto :goto_1

    .line 454
    :catchall_0
    move-exception v0

    .line 455
    :try_start_1
    iget-object v2, v1, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->firstErr:Ljava/util/concurrent/atomic/AtomicReference;

    .line 456
    instance-of v3, v0, Ljava/io/IOException;

    if-eqz v3, :cond_0

    check-cast v0, Ljava/io/IOException;

    goto :goto_0

    .line 457
    :cond_0
    new-instance v3, Ljava/io/IOException;

    invoke-virtual {v0}, Ljava/lang/Throwable;->getMessage()Ljava/lang/String;

    move-result-object v4

    invoke-static {v4}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v4

    invoke-direct {v3, v4, v0}, Ljava/io/IOException;-><init>(Ljava/lang/String;Ljava/lang/Throwable;)V

    move-object v0, v3

    .line 455
    :goto_0
    const/4 v3, 0x0

    invoke-static {v2, v3, v0}, Lio/kamihama/magianative/CNChunkedDownload$$ExternalSyntheticBackportWithForwarding0;->m(Ljava/util/concurrent/atomic/AtomicReference;Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 458
    iget-object v0, v1, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->abort:Ljava/util/concurrent/atomic/AtomicBoolean;

    const/4 v2, 0x1

    invoke-virtual {v0, v2}, Ljava/util/concurrent/atomic/AtomicBoolean;->set(Z)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    .line 460
    :goto_1
    iget-object v0, v1, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->latch:Ljava/util/concurrent/CountDownLatch;

    invoke-virtual {v0}, Ljava/util/concurrent/CountDownLatch;->countDown()V

    .line 461
    nop

    .line 462
    return-void

    .line 460
    :catchall_1
    move-exception v0

    iget-object v2, v1, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->latch:Ljava/util/concurrent/CountDownLatch;

    invoke-virtual {v2}, Ljava/util/concurrent/CountDownLatch;->countDown()V

    .line 461
    throw v0
.end method
