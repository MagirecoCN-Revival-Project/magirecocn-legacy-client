.class public final Lio/kamihama/magianative/CNChunkedDownload;
.super Ljava/lang/Object;
.source "CNChunkedDownload.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lio/kamihama/magianative/CNChunkedDownload$Sink;,
        Lio/kamihama/magianative/CNChunkedDownload$Probe;,
        Lio/kamihama/magianative/CNChunkedDownload$Resume;,
        Lio/kamihama/magianative/CNChunkedDownload$Result;,
        Lio/kamihama/magianative/CNChunkedDownload$ChunkThreadFactory;,
        Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;
    }
.end annotation


# static fields
.field private static final CONNECT_TIMEOUT_MS:I = 0x3a98

.field private static final META_MAGIC:Ljava/lang/String; = "CNVPROG3"

.field private static final READ_TIMEOUT_MS:I = 0x7530

.field private static final TAG:Ljava/lang/String; = "MagiaCNChunk"


# direct methods
.method private constructor <init>()V
    .locals 0

    .line 72
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static synthetic access$200(Ljava/lang/String;Ljava/io/File;JJLjava/util/concurrent/atomic/AtomicLongArray;IZLjava/io/File;JLjava/lang/String;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicBoolean;Ljava/util/concurrent/atomic/AtomicBoolean;Lio/kamihama/magianative/CNChunkedDownload$Sink;)V
    .locals 0
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 62
    invoke-static/range {p0 .. p19}, Lio/kamihama/magianative/CNChunkedDownload;->oneChunk(Ljava/lang/String;Ljava/io/File;JJLjava/util/concurrent/atomic/AtomicLongArray;IZLjava/io/File;JLjava/lang/String;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicBoolean;Ljava/util/concurrent/atomic/AtomicBoolean;Lio/kamihama/magianative/CNChunkedDownload$Sink;)V

    return-void
.end method

.method private static deleteQuietly(Ljava/io/File;)V
    .locals 2

    .line 658
    if-eqz p0, :cond_0

    invoke-virtual {p0}, Ljava/io/File;->exists()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Ljava/io/File;->delete()Z

    move-result v0

    if-nez v0, :cond_0

    .line 659
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "\u65e0\u6cd5\u5220\u9664 "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    const-string v0, "MagiaCNChunk"

    invoke-static {v0, p0}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;)V

    .line 661
    :cond_0
    return-void
.end method

.method public static download(Ljava/lang/String;Ljava/io/File;IZLio/kamihama/magianative/CNChunkedDownload$Probe;Lio/kamihama/magianative/CNChunkedDownload$Sink;)Lio/kamihama/magianative/CNChunkedDownload$Result;
    .locals 40
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 197
    move-object/from16 v1, p1

    move-object/from16 v2, p4

    move-object/from16 v3, p5

    const-string v4, "\u5df2\u53d6\u6d88"

    iget-wide v11, v2, Lio/kamihama/magianative/CNChunkedDownload$Probe;->total:J

    .line 198
    const-wide/16 v13, 0x0

    cmp-long v0, v11, v13

    if-lez v0, :cond_1a

    .line 200
    invoke-static/range {p1 .. p1}, Lio/kamihama/magianative/CNChunkedDownload;->partFileFor(Ljava/io/File;)Ljava/io/File;

    move-result-object v15

    .line 201
    invoke-static/range {p1 .. p1}, Lio/kamihama/magianative/CNChunkedDownload;->metaFileFor(Ljava/io/File;)Ljava/io/File;

    move-result-object v10

    .line 203
    invoke-virtual {v15}, Ljava/io/File;->getParentFile()Ljava/io/File;

    move-result-object v0

    .line 204
    if-eqz v0, :cond_1

    invoke-virtual {v0}, Ljava/io/File;->isDirectory()Z

    move-result v5

    if-nez v5, :cond_1

    invoke-virtual {v0}, Ljava/io/File;->mkdirs()Z

    move-result v5

    if-nez v5, :cond_1

    invoke-virtual {v0}, Ljava/io/File;->isDirectory()Z

    move-result v5

    if-eqz v5, :cond_0

    goto :goto_0

    .line 205
    :cond_0
    new-instance v1, Ljava/io/IOException;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "\u65e0\u6cd5\u521b\u5efa\u4e0b\u8f7d\u76ee\u5f55: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {v1, v0}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v1

    .line 209
    :cond_1
    :goto_0
    const/4 v9, 0x1

    move/from16 v0, p2

    if-ge v0, v9, :cond_2

    const/4 v0, 0x1

    .line 210
    :cond_2
    nop

    .line 211
    invoke-static {v10}, Lio/kamihama/magianative/CNChunkedDownload;->readResume(Ljava/io/File;)Lio/kamihama/magianative/CNChunkedDownload$Resume;

    move-result-object v8

    .line 212
    const-string v6, " chunks="

    const-string v7, "MagiaCNChunk"

    const/4 v5, 0x0

    if-eqz v8, :cond_5

    .line 213
    iget-object v14, v2, Lio/kamihama/magianative/CNChunkedDownload$Probe;->etag:Ljava/lang/String;

    move-object v5, v8

    move-object/from16 v18, v4

    move-object v13, v6

    move-object v4, v7

    move-wide v6, v11

    move/from16 v19, v0

    move-object v0, v8

    move-object v8, v14

    const/4 v14, 0x1

    move-object/from16 v9, p0

    move-object/from16 v20, v10

    move-object v10, v15

    invoke-static/range {v5 .. v10}, Lio/kamihama/magianative/CNChunkedDownload;->resumeRejectReason(Lio/kamihama/magianative/CNChunkedDownload$Resume;JLjava/lang/String;Ljava/lang/String;Ljava/io/File;)Ljava/lang/String;

    move-result-object v5

    .line 214
    if-nez v5, :cond_4

    .line 216
    iget v5, v0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->chunks:I

    .line 217
    iget-object v0, v0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->done:[J

    .line 218
    nop

    .line 219
    const/4 v6, 0x0

    const-wide/16 v7, 0x0

    :goto_1
    array-length v9, v0

    if-ge v6, v9, :cond_3

    aget-wide v9, v0, v6

    add-long/2addr v7, v9

    add-int/lit8 v6, v6, 0x1

    goto :goto_1

    .line 220
    :cond_3
    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "resume-accept file="

    invoke-virtual {v6, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v6, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v5}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v9, " have="

    invoke-virtual {v6, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v7, v8}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, "/"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v11, v12}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v4, v6}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 222
    move v10, v5

    goto :goto_3

    .line 223
    :cond_4
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "resume-reject file="

    invoke-virtual {v0, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v0, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v6, " reason="

    invoke-virtual {v0, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v4, v0}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;)V

    .line 224
    invoke-static/range {v20 .. v20}, Lio/kamihama/magianative/CNChunkedDownload;->deleteQuietly(Ljava/io/File;)V

    goto :goto_2

    .line 212
    :cond_5
    move/from16 v19, v0

    move-object/from16 v18, v4

    move-object v13, v6

    move-object v4, v7

    move-object/from16 v20, v10

    const/4 v14, 0x1

    .line 228
    :goto_2
    move/from16 v10, v19

    const/4 v0, 0x0

    :goto_3
    int-to-long v5, v10

    add-long v7, v11, v5

    move-object/from16 v19, v15

    const-wide/16 v14, 0x1

    sub-long/2addr v7, v14

    div-long/2addr v7, v5

    .line 229
    new-array v9, v10, [J

    .line 230
    new-array v6, v10, [J

    .line 231
    new-instance v5, Ljava/util/concurrent/atomic/AtomicLongArray;

    invoke-direct {v5, v10}, Ljava/util/concurrent/atomic/AtomicLongArray;-><init>(I)V

    .line 232
    const/4 v14, 0x0

    :goto_4
    if-ge v14, v10, :cond_7

    .line 233
    move-object v15, v4

    int-to-long v3, v14

    mul-long v3, v3, v7

    aput-wide v3, v9, v14

    .line 234
    add-long/2addr v3, v7

    const-wide/16 v21, 0x1

    sub-long v3, v3, v21

    move-wide/from16 v23, v7

    sub-long v7, v11, v21

    invoke-static {v3, v4, v7, v8}, Ljava/lang/Math;->min(JJ)J

    move-result-wide v3

    aput-wide v3, v6, v14

    .line 235
    if-eqz v0, :cond_6

    aget-wide v3, v0, v14

    goto :goto_5

    :cond_6
    const-wide/16 v3, 0x0

    :goto_5
    invoke-virtual {v5, v14, v3, v4}, Ljava/util/concurrent/atomic/AtomicLongArray;->set(IJ)V

    .line 232
    add-int/lit8 v14, v14, 0x1

    move-object/from16 v3, p5

    move-object v4, v15

    move-wide/from16 v7, v23

    goto :goto_4

    .line 240
    :cond_7
    move-object v15, v4

    new-instance v3, Ljava/io/RandomAccessFile;

    const-string v0, "rw"

    move-object/from16 v4, v19

    invoke-direct {v3, v4, v0}, Ljava/io/RandomAccessFile;-><init>(Ljava/io/File;Ljava/lang/String;)V

    .line 242
    :try_start_0
    invoke-virtual {v3}, Ljava/io/RandomAccessFile;->length()J

    move-result-wide v7

    cmp-long v0, v7, v11

    if-eqz v0, :cond_8

    invoke-virtual {v3, v11, v12}, Ljava/io/RandomAccessFile;->setLength(J)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    .line 244
    :cond_8
    :try_start_1
    invoke-virtual {v3}, Ljava/io/RandomAccessFile;->close()V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_6

    :catchall_0
    move-exception v0

    .line 245
    nop

    .line 246
    :goto_6
    iget-object v8, v2, Lio/kamihama/magianative/CNChunkedDownload$Probe;->etag:Ljava/lang/String;

    move-object v3, v5

    move-object/from16 v5, v20

    move-object v14, v6

    move-wide v6, v11

    move-object/from16 v19, v9

    move-object/from16 v9, p0

    move-object/from16 v23, v15

    move v15, v10

    move-object v10, v3

    invoke-static/range {v5 .. v10}, Lio/kamihama/magianative/CNChunkedDownload;->saveMeta(Ljava/io/File;JLjava/lang/String;Ljava/lang/String;Ljava/util/concurrent/atomic/AtomicLongArray;)V

    .line 248
    new-instance v0, Ljava/util/concurrent/atomic/AtomicLong;

    const-wide/16 v5, 0x0

    invoke-direct {v0, v5, v6}, Ljava/util/concurrent/atomic/AtomicLong;-><init>(J)V

    .line 249
    const/4 v5, 0x0

    :goto_7
    if-ge v5, v15, :cond_9

    invoke-virtual {v3, v5}, Ljava/util/concurrent/atomic/AtomicLongArray;->get(I)J

    move-result-wide v6

    invoke-virtual {v0, v6, v7}, Ljava/util/concurrent/atomic/AtomicLong;->addAndGet(J)J

    add-int/lit8 v5, v5, 0x1

    goto :goto_7

    .line 251
    :cond_9
    move-object/from16 v10, p5

    if-eqz v10, :cond_a

    .line 252
    invoke-interface {v10, v11, v12}, Lio/kamihama/magianative/CNChunkedDownload$Sink;->onTotal(J)V

    .line 253
    invoke-virtual {v0}, Ljava/util/concurrent/atomic/AtomicLong;->get()J

    move-result-wide v5

    invoke-interface {v10, v5, v6, v11, v12}, Lio/kamihama/magianative/CNChunkedDownload$Sink;->onProgress(JJ)V

    .line 258
    :cond_a
    invoke-virtual {v0}, Ljava/util/concurrent/atomic/AtomicLong;->get()J

    move-result-wide v5

    cmp-long v7, v5, v11

    if-ltz v7, :cond_c

    .line 259
    invoke-static {v4, v1}, Lio/kamihama/magianative/CNChunkedDownload;->promote(Ljava/io/File;Ljava/io/File;)V

    .line 260
    invoke-static/range {v20 .. v20}, Lio/kamihama/magianative/CNChunkedDownload;->deleteQuietly(Ljava/io/File;)V

    .line 261
    if-eqz v10, :cond_b

    invoke-interface {v10, v11, v12, v11, v12}, Lio/kamihama/magianative/CNChunkedDownload$Sink;->onProgress(JJ)V

    .line 262
    :cond_b
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "resume-complete file="

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " \u65e0\u9700\u518d\u4e0b\u8f7d"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    move-object/from16 v9, v23

    invoke-static {v9, v0}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 263
    new-instance v0, Lio/kamihama/magianative/CNChunkedDownload$Result;

    iget-object v1, v2, Lio/kamihama/magianative/CNChunkedDownload$Probe;->etag:Ljava/lang/String;

    invoke-direct {v0, v11, v12, v1}, Lio/kamihama/magianative/CNChunkedDownload$Result;-><init>(JLjava/lang/String;)V

    return-object v0

    .line 266
    :cond_c
    move-object/from16 v9, v23

    new-instance v8, Ljava/util/concurrent/atomic/AtomicReference;

    const/4 v5, 0x0

    invoke-direct {v8, v5}, Ljava/util/concurrent/atomic/AtomicReference;-><init>(Ljava/lang/Object;)V

    .line 267
    new-instance v5, Ljava/util/concurrent/atomic/AtomicBoolean;

    const/4 v6, 0x0

    invoke-direct {v5, v6}, Ljava/util/concurrent/atomic/AtomicBoolean;-><init>(Z)V

    .line 269
    new-instance v7, Ljava/util/concurrent/atomic/AtomicBoolean;

    invoke-direct {v7, v6}, Ljava/util/concurrent/atomic/AtomicBoolean;-><init>(Z)V

    .line 270
    new-instance v6, Ljava/util/concurrent/atomic/AtomicLong;

    move-object/from16 v24, v8

    invoke-static {}, Ljava/lang/System;->nanoTime()J

    move-result-wide v8

    invoke-direct {v6, v8, v9}, Ljava/util/concurrent/atomic/AtomicLong;-><init>(J)V

    .line 271
    new-instance v8, Ljava/util/concurrent/atomic/AtomicLong;

    invoke-static {}, Ljava/lang/System;->nanoTime()J

    move-result-wide v9

    invoke-direct {v8, v9, v10}, Ljava/util/concurrent/atomic/AtomicLong;-><init>(J)V

    .line 272
    new-instance v9, Ljava/util/concurrent/atomic/AtomicLong;

    move-object v10, v5

    move-object/from16 v25, v6

    const-wide/16 v5, 0x0

    invoke-direct {v9, v5, v6}, Ljava/util/concurrent/atomic/AtomicLong;-><init>(J)V

    .line 274
    new-instance v5, Lio/kamihama/magianative/CNChunkedDownload$ChunkThreadFactory;

    const/4 v6, 0x0

    invoke-direct {v5, v6}, Lio/kamihama/magianative/CNChunkedDownload$ChunkThreadFactory;-><init>(Lio/kamihama/magianative/CNChunkedDownload$1;)V

    invoke-static {v15, v5}, Ljava/util/concurrent/Executors;->newFixedThreadPool(ILjava/util/concurrent/ThreadFactory;)Ljava/util/concurrent/ExecutorService;

    move-result-object v5

    .line 275
    new-instance v6, Ljava/util/concurrent/CountDownLatch;

    invoke-direct {v6, v15}, Ljava/util/concurrent/CountDownLatch;-><init>(I)V

    .line 277
    move-object/from16 v26, v10

    const/4 v10, 0x0

    :goto_8
    if-ge v10, v15, :cond_d

    .line 278
    move-object/from16 v27, v13

    new-instance v13, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;

    const/4 v1, 0x0

    invoke-direct {v13, v1}, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;-><init>(Lio/kamihama/magianative/CNChunkedDownload$1;)V

    .line 279
    move-object/from16 v1, p0

    iput-object v1, v13, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->url:Ljava/lang/String;

    iput-object v4, v13, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->part:Ljava/io/File;

    .line 280
    move-object/from16 v28, v4

    move-object/from16 v29, v5

    aget-wide v4, v19, v10

    iput-wide v4, v13, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->start:J

    aget-wide v4, v14, v10

    iput-wide v4, v13, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->end:J

    .line 281
    iput-object v3, v13, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->done:Ljava/util/concurrent/atomic/AtomicLongArray;

    iput v10, v13, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->idx:I

    .line 282
    move/from16 v4, p3

    iput-boolean v4, v13, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->direct:Z

    move-object/from16 v5, v20

    iput-object v5, v13, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->meta:Ljava/io/File;

    .line 283
    iput-wide v11, v13, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->total:J

    iget-object v1, v2, Lio/kamihama/magianative/CNChunkedDownload$Probe;->etag:Ljava/lang/String;

    iput-object v1, v13, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->etag:Ljava/lang/String;

    .line 284
    iput-object v0, v13, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->totalDone:Ljava/util/concurrent/atomic/AtomicLong;

    .line 285
    iput-object v8, v13, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->windowStart:Ljava/util/concurrent/atomic/AtomicLong;

    iput-object v9, v13, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->windowBytes:Ljava/util/concurrent/atomic/AtomicLong;

    .line 286
    move-object/from16 v1, v25

    iput-object v1, v13, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->lastMoveNs:Ljava/util/concurrent/atomic/AtomicLong;

    move-object/from16 v4, v26

    iput-object v4, v13, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->abort:Ljava/util/concurrent/atomic/AtomicBoolean;

    .line 287
    iput-object v7, v13, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->rangeIgnored:Ljava/util/concurrent/atomic/AtomicBoolean;

    .line 288
    move-object/from16 v20, v14

    move-object/from16 v14, p5

    iput-object v14, v13, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->sink:Lio/kamihama/magianative/CNChunkedDownload$Sink;

    move-object/from16 v25, v8

    move-object/from16 v8, v24

    iput-object v8, v13, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->firstErr:Ljava/util/concurrent/atomic/AtomicReference;

    .line 289
    iput-object v6, v13, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->latch:Ljava/util/concurrent/CountDownLatch;

    .line 290
    move-object/from16 v24, v5

    move-object/from16 v5, v29

    invoke-interface {v5, v13}, Ljava/util/concurrent/ExecutorService;->submit(Ljava/lang/Runnable;)Ljava/util/concurrent/Future;

    .line 277
    add-int/lit8 v10, v10, 0x1

    move-object/from16 v14, v20

    move-object/from16 v20, v24

    move-object/from16 v13, v27

    move-object/from16 v4, v28

    move-object/from16 v24, v8

    move-object/from16 v8, v25

    move-object/from16 v25, v1

    move-object/from16 v1, p1

    goto :goto_8

    .line 294
    :cond_d
    move-object/from16 v14, p5

    move-object/from16 v28, v4

    move-object/from16 v27, v13

    move-object/from16 v8, v24

    move-object/from16 v1, v25

    move-object/from16 v4, v26

    move-object/from16 v24, v20

    sget-object v9, Ljava/util/concurrent/TimeUnit;->SECONDS:Ljava/util/concurrent/TimeUnit;

    invoke-static {}, Lio/kamihama/magianative/CNMirrors;->stallSeconds()I

    move-result v10

    move-wide/from16 v19, v11

    int-to-long v10, v10

    invoke-virtual {v9, v10, v11}, Ljava/util/concurrent/TimeUnit;->toNanos(J)J

    move-result-wide v9

    .line 299
    invoke-static {}, Lio/kamihama/magianative/CNMirrors;->minSpeedKbps()I

    move-result v11

    int-to-long v11, v11

    const-wide/16 v25, 0x3e8

    mul-long v11, v11, v25

    const-wide/16 v29, 0x8

    div-long v11, v11, v29

    .line 300
    invoke-static {}, Ljava/lang/System;->nanoTime()J

    move-result-wide v31

    .line 301
    invoke-virtual {v0}, Ljava/util/concurrent/atomic/AtomicLong;->get()J

    move-result-wide v33

    .line 303
    :goto_9
    :try_start_2
    sget-object v13, Ljava/util/concurrent/TimeUnit;->SECONDS:Ljava/util/concurrent/TimeUnit;
    :try_end_2
    .catch Ljava/lang/InterruptedException; {:try_start_2 .. :try_end_2} :catch_5

    move-object/from16 v35, v3

    const-wide/16 v2, 0x1

    :try_start_3
    invoke-virtual {v6, v2, v3, v13}, Ljava/util/concurrent/CountDownLatch;->await(JLjava/util/concurrent/TimeUnit;)Z

    move-result v13

    if-nez v13, :cond_13

    .line 304
    invoke-static {}, Ljava/lang/System;->nanoTime()J

    move-result-wide v21
    :try_end_3
    .catch Ljava/lang/InterruptedException; {:try_start_3 .. :try_end_3} :catch_4

    .line 305
    if-eqz v14, :cond_e

    :try_start_4
    invoke-interface/range {p5 .. p5}, Lio/kamihama/magianative/CNChunkedDownload$Sink;->isCancelled()Z

    move-result v13

    if-eqz v13, :cond_e

    .line 306
    const/4 v1, 0x1

    invoke-virtual {v4, v1}, Ljava/util/concurrent/atomic/AtomicBoolean;->set(Z)V

    .line 307
    new-instance v0, Ljava/io/IOException;
    :try_end_4
    .catch Ljava/lang/InterruptedException; {:try_start_4 .. :try_end_4} :catch_0

    move-object/from16 v13, v18

    :try_start_5
    invoke-direct {v0, v13}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    const/4 v1, 0x0

    invoke-static {v8, v1, v0}, Lio/kamihama/magianative/CNChunkedDownload$$ExternalSyntheticBackportWithForwarding0;->m(Ljava/util/concurrent/atomic/AtomicReference;Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 308
    move-object/from16 v38, v7

    const-wide/16 v16, 0x0

    goto/16 :goto_c

    .line 331
    :catch_0
    move-exception v0

    move-object/from16 v13, v18

    goto/16 :goto_b

    .line 305
    :cond_e
    move-object/from16 v13, v18

    .line 310
    invoke-virtual {v1}, Ljava/util/concurrent/atomic/AtomicLong;->get()J

    move-result-wide v36

    sub-long v36, v21, v36

    cmp-long v18, v36, v9

    if-lez v18, :cond_f

    .line 311
    const/4 v1, 0x1

    invoke-virtual {v4, v1}, Ljava/util/concurrent/atomic/AtomicBoolean;->set(Z)V

    .line 312
    new-instance v0, Ljava/io/IOException;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "\u7ebf\u8def\u505c\u6ede\uff1a"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    .line 313
    invoke-static {}, Lio/kamihama/magianative/CNMirrors;->stallSeconds()I

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, " \u79d2\u5185\u6ca1\u6709\u4efb\u4f55\u6570\u636e"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    .line 312
    const/4 v1, 0x0

    invoke-static {v8, v1, v0}, Lio/kamihama/magianative/CNChunkedDownload$$ExternalSyntheticBackportWithForwarding0;->m(Ljava/util/concurrent/atomic/AtomicReference;Ljava/lang/Object;Ljava/lang/Object;)Z
    :try_end_5
    .catch Ljava/lang/InterruptedException; {:try_start_5 .. :try_end_5} :catch_3

    .line 314
    move-object/from16 v38, v7

    const-wide/16 v16, 0x0

    goto/16 :goto_c

    .line 316
    :cond_f
    sub-long v2, v21, v31

    .line 317
    const-wide/16 v16, 0x0

    cmp-long v18, v11, v16

    if-lez v18, :cond_11

    move-object/from16 v18, v1

    :try_start_6
    sget-object v1, Ljava/util/concurrent/TimeUnit;->SECONDS:Ljava/util/concurrent/TimeUnit;
    :try_end_6
    .catch Ljava/lang/InterruptedException; {:try_start_6 .. :try_end_6} :catch_2

    move-object/from16 v39, v6

    move-object/from16 v38, v7

    const-wide/16 v6, 0xa

    :try_start_7
    invoke-virtual {v1, v6, v7}, Ljava/util/concurrent/TimeUnit;->toNanos(J)J

    move-result-wide v6

    cmp-long v1, v2, v6

    if-ltz v1, :cond_12

    .line 318
    invoke-virtual {v0}, Ljava/util/concurrent/atomic/AtomicLong;->get()J

    move-result-wide v6

    sub-long v6, v6, v33

    .line 319
    long-to-double v6, v6

    long-to-double v1, v2

    const-wide v31, 0x41cdcd6500000000L    # 1.0E9

    div-double v1, v1, v31

    div-double/2addr v6, v1

    double-to-long v1, v6

    .line 320
    cmp-long v3, v1, v11

    if-gez v3, :cond_10

    .line 321
    const/4 v3, 0x1

    invoke-virtual {v4, v3}, Ljava/util/concurrent/atomic/AtomicBoolean;->set(Z)V

    .line 322
    new-instance v0, Ljava/io/IOException;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "\u7ebf\u8def\u8fc7\u6162\uff1a"

    invoke-virtual {v3, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    mul-long v1, v1, v29

    div-long v1, v1, v25

    invoke-virtual {v3, v1, v2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, " kbps < "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    .line 324
    invoke-static {}, Lio/kamihama/magianative/CNMirrors;->minSpeedKbps()I

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, " kbps"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    .line 322
    const/4 v1, 0x0

    invoke-static {v8, v1, v0}, Lio/kamihama/magianative/CNChunkedDownload$$ExternalSyntheticBackportWithForwarding0;->m(Ljava/util/concurrent/atomic/AtomicReference;Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 325
    goto :goto_c

    .line 327
    :cond_10
    nop

    .line 328
    invoke-virtual {v0}, Ljava/util/concurrent/atomic/AtomicLong;->get()J

    move-result-wide v1
    :try_end_7
    .catch Ljava/lang/InterruptedException; {:try_start_7 .. :try_end_7} :catch_1

    move-wide/from16 v33, v1

    move-wide/from16 v31, v21

    goto :goto_a

    .line 331
    :catch_1
    move-exception v0

    goto :goto_f

    :catch_2
    move-exception v0

    move-object/from16 v38, v7

    goto :goto_f

    .line 317
    :cond_11
    move-object/from16 v18, v1

    move-object/from16 v39, v6

    move-object/from16 v38, v7

    .line 330
    :cond_12
    :goto_a
    move-object/from16 v2, p4

    move-object/from16 v1, v18

    move-object/from16 v3, v35

    move-object/from16 v7, v38

    move-object/from16 v6, v39

    move-object/from16 v18, v13

    goto/16 :goto_9

    .line 331
    :catch_3
    move-exception v0

    :goto_b
    move-object/from16 v38, v7

    goto :goto_e

    .line 303
    :cond_13
    move-object/from16 v38, v7

    const-wide/16 v16, 0x0

    .line 335
    :goto_c
    goto :goto_10

    .line 331
    :catch_4
    move-exception v0

    goto :goto_d

    :catch_5
    move-exception v0

    move-object/from16 v35, v3

    :goto_d
    move-object/from16 v38, v7

    move-object/from16 v13, v18

    :goto_e
    const-wide/16 v16, 0x0

    .line 332
    :goto_f
    invoke-static {}, Ljava/lang/Thread;->currentThread()Ljava/lang/Thread;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Thread;->interrupt()V

    .line 333
    const/4 v1, 0x1

    invoke-virtual {v4, v1}, Ljava/util/concurrent/atomic/AtomicBoolean;->set(Z)V

    .line 334
    new-instance v0, Ljava/io/IOException;

    invoke-direct {v0, v13}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    const/4 v1, 0x0

    invoke-static {v8, v1, v0}, Lio/kamihama/magianative/CNChunkedDownload$$ExternalSyntheticBackportWithForwarding0;->m(Ljava/util/concurrent/atomic/AtomicReference;Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 337
    :goto_10
    invoke-interface {v5}, Ljava/util/concurrent/ExecutorService;->shutdownNow()Ljava/util/List;

    .line 338
    :try_start_8
    sget-object v0, Ljava/util/concurrent/TimeUnit;->SECONDS:Ljava/util/concurrent/TimeUnit;

    const-wide/16 v1, 0x5

    invoke-interface {v5, v1, v2, v0}, Ljava/util/concurrent/ExecutorService;->awaitTermination(JLjava/util/concurrent/TimeUnit;)Z
    :try_end_8
    .catch Ljava/lang/InterruptedException; {:try_start_8 .. :try_end_8} :catch_6

    goto :goto_11

    :catch_6
    move-exception v0

    .line 340
    :goto_11
    move-object/from16 v1, p4

    iget-object v0, v1, Lio/kamihama/magianative/CNChunkedDownload$Probe;->etag:Ljava/lang/String;

    move-object/from16 v2, v24

    move-object v5, v2

    move-object/from16 v3, v38

    const/4 v4, 0x0

    move-wide/from16 v6, v19

    move-object v11, v8

    move-object v8, v0

    move-object/from16 v12, v23

    move-object/from16 v9, p0

    move-object v13, v14

    move-object/from16 v10, v35

    invoke-static/range {v5 .. v10}, Lio/kamihama/magianative/CNChunkedDownload;->saveMeta(Ljava/io/File;JLjava/lang/String;Ljava/lang/String;Ljava/util/concurrent/atomic/AtomicLongArray;)V

    .line 342
    invoke-virtual {v11}, Ljava/util/concurrent/atomic/AtomicReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/io/IOException;

    .line 343
    if-eqz v0, :cond_15

    .line 344
    invoke-virtual {v3}, Ljava/util/concurrent/atomic/AtomicBoolean;->get()Z

    move-result v1

    if-eqz v1, :cond_14

    .line 347
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "\u670d\u52a1\u7aef\u5ffd\u7565 Range\uff0c\u6e05\u9664\u65ad\u70b9\u540e\u6574\u4efd\u91cd\u4e0b: "

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v12, v1}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;)V

    .line 348
    invoke-static {v2}, Lio/kamihama/magianative/CNChunkedDownload;->deleteQuietly(Ljava/io/File;)V

    .line 349
    invoke-static/range {v28 .. v28}, Lio/kamihama/magianative/CNChunkedDownload;->deleteQuietly(Ljava/io/File;)V

    .line 351
    :cond_14
    throw v0

    .line 356
    :cond_15
    nop

    .line 357
    move-wide/from16 v3, v16

    const/4 v5, 0x0

    :goto_12
    if-ge v5, v15, :cond_16

    move-object/from16 v6, v35

    invoke-virtual {v6, v5}, Ljava/util/concurrent/atomic/AtomicLongArray;->get(I)J

    move-result-wide v7

    add-long/2addr v3, v7

    add-int/lit8 v5, v5, 0x1

    goto :goto_12

    .line 358
    :cond_16
    const-string v0, " / "

    cmp-long v5, v3, v19

    if-nez v5, :cond_19

    .line 361
    invoke-virtual/range {v28 .. v28}, Ljava/io/File;->length()J

    move-result-wide v3

    .line 362
    cmp-long v5, v3, v19

    if-nez v5, :cond_18

    .line 366
    move-object/from16 v3, p1

    move-object/from16 v4, v28

    invoke-static {v4, v3}, Lio/kamihama/magianative/CNChunkedDownload;->promote(Ljava/io/File;Ljava/io/File;)V

    .line 367
    invoke-static {v2}, Lio/kamihama/magianative/CNChunkedDownload;->deleteQuietly(Ljava/io/File;)V

    .line 368
    if-eqz v13, :cond_17

    .line 369
    move-wide/from16 v5, v19

    invoke-interface {v13, v5, v6, v5, v6}, Lio/kamihama/magianative/CNChunkedDownload$Sink;->onProgress(JJ)V

    .line 370
    const/4 v0, 0x0

    invoke-interface {v13, v0}, Lio/kamihama/magianative/CNChunkedDownload$Sink;->onSpeed(F)V

    goto :goto_13

    .line 368
    :cond_17
    move-wide/from16 v5, v19

    .line 372
    :goto_13
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "\u5206\u7247\u4e0b\u8f7d\u5b8c\u6210 file="

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v2, " bytes="

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v5, v6}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v0

    move-object/from16 v2, v27

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v15}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v12, v0}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 374
    new-instance v0, Lio/kamihama/magianative/CNChunkedDownload$Result;

    iget-object v1, v1, Lio/kamihama/magianative/CNChunkedDownload$Probe;->etag:Ljava/lang/String;

    invoke-direct {v0, v5, v6, v1}, Lio/kamihama/magianative/CNChunkedDownload$Result;-><init>(JLjava/lang/String;)V

    return-object v0

    .line 363
    :cond_18
    move-wide/from16 v5, v19

    new-instance v1, Ljava/io/IOException;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "\u4e34\u65f6\u6587\u4ef6\u5927\u5c0f\u5f02\u5e38: "

    invoke-virtual {v2, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v3, v4}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v5, v6}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {v1, v0}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v1

    .line 359
    :cond_19
    move-wide/from16 v5, v19

    new-instance v1, Ljava/io/IOException;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "\u4e0b\u8f7d\u4e0d\u5b8c\u6574: \u5df2\u5199 "

    invoke-virtual {v2, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v3, v4}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v5, v6}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {v1, v0}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v1

    .line 244
    :catchall_1
    move-exception v0

    move-object v1, v0

    :try_start_9
    invoke-virtual {v3}, Ljava/io/RandomAccessFile;->close()V
    :try_end_9
    .catchall {:try_start_9 .. :try_end_9} :catchall_2

    goto :goto_14

    :catchall_2
    move-exception v0

    .line 245
    :goto_14
    throw v1

    .line 198
    :cond_1a
    new-instance v0, Ljava/io/IOException;

    const-string v1, "\u672a\u77e5\u7684\u6587\u4ef6\u957f\u5ea6"

    invoke-direct {v0, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v0
.end method

.method public static metaFileFor(Ljava/io/File;)Ljava/io/File;
    .locals 2

    .line 183
    new-instance v0, Ljava/io/File;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p0}, Ljava/io/File;->getPath()Ljava/lang/String;

    move-result-object p0

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string v1, ".cpart.prog"

    invoke-virtual {p0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-direct {v0, p0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    return-object v0
.end method

.method private static oneChunk(Ljava/lang/String;Ljava/io/File;JJLjava/util/concurrent/atomic/AtomicLongArray;IZLjava/io/File;JLjava/lang/String;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicBoolean;Ljava/util/concurrent/atomic/AtomicBoolean;Lio/kamihama/magianative/CNChunkedDownload$Sink;)V
    .locals 24
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 475
    move-wide/from16 v0, p4

    move/from16 v2, p7

    move-object/from16 v9, p12

    move-object/from16 v10, p15

    move-object/from16 v11, p19

    sub-long v3, v0, p2

    const-wide/16 v5, 0x1

    add-long v12, v3, v5

    .line 476
    invoke-virtual/range {p6 .. p7}, Ljava/util/concurrent/atomic/AtomicLongArray;->get(I)J

    move-result-wide v3

    .line 477
    cmp-long v5, v3, v12

    if-ltz v5, :cond_0

    return-void

    .line 479
    :cond_0
    add-long v3, p2, v3

    .line 480
    move-object/from16 v14, p0

    move/from16 v5, p8

    invoke-static {v14, v5}, Lio/kamihama/magianative/CNChunkedDownload;->open(Ljava/lang/String;Z)Ljava/net/HttpURLConnection;

    move-result-object v15

    .line 481
    const-string v5, "GET"

    invoke-virtual {v15, v5}, Ljava/net/HttpURLConnection;->setRequestMethod(Ljava/lang/String;)V

    .line 482
    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "bytes="

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v3, v4}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, "-"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v0, v1}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "Range"

    invoke-virtual {v15, v1, v0}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 483
    if-eqz v9, :cond_1

    invoke-virtual/range {p12 .. p12}, Ljava/lang/String;->length()I

    move-result v0

    if-lez v0, :cond_1

    .line 485
    const-string v0, "If-Range"

    invoke-virtual {v15, v0, v9}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 487
    :cond_1
    invoke-virtual {v15}, Ljava/net/HttpURLConnection;->connect()V

    .line 488
    invoke-virtual {v15}, Ljava/net/HttpURLConnection;->getResponseCode()I

    move-result v1

    .line 489
    const/16 v0, 0xce

    const-string v8, "\u5206\u7247 "

    if-eq v1, v0, :cond_3

    .line 490
    :try_start_0
    invoke-virtual {v15}, Ljava/net/HttpURLConnection;->disconnect()V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    goto :goto_0

    :catchall_0
    move-exception v0

    .line 491
    :goto_0
    const/16 v0, 0xc8

    if-ne v1, v0, :cond_2

    .line 497
    const/4 v0, 0x1

    move-object/from16 v1, p18

    invoke-virtual {v1, v0}, Ljava/util/concurrent/atomic/AtomicBoolean;->set(Z)V

    .line 498
    new-instance v0, Ljava/io/IOException;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, " \u7684 Range \u88ab\u670d\u52a1\u7aef\u5ffd\u7565\uff08HTTP 200\uff09"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v0

    .line 500
    :cond_2
    new-instance v0, Ljava/io/IOException;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v3, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " \u671f\u671b 206\uff0c\u5b9e\u5f97 HTTP "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v0

    .line 504
    :cond_3
    const-string v0, "Content-Range"

    invoke-virtual {v15, v0}, Ljava/net/HttpURLConnection;->getHeaderField(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lio/kamihama/magianative/CNChunkedDownload;->rangeStart(Ljava/lang/String;)J

    move-result-wide v5

    .line 505
    const-wide/16 v0, 0x0

    cmp-long v7, v5, v0

    if-ltz v7, :cond_5

    cmp-long v7, v5, v3

    if-nez v7, :cond_4

    goto :goto_2

    .line 506
    :cond_4
    :try_start_1
    invoke-virtual {v15}, Ljava/net/HttpURLConnection;->disconnect()V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    goto :goto_1

    :catchall_1
    move-exception v0

    .line 507
    :goto_1
    new-instance v0, Ljava/io/IOException;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, " Content-Range \u8d77\u70b9\u4e0d\u7b26: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v5, v6}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, " != "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v3, v4}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v0

    .line 511
    :cond_5
    :goto_2
    nop

    .line 512
    nop

    .line 514
    :try_start_2
    new-instance v7, Ljava/io/BufferedInputStream;

    invoke-virtual {v15}, Ljava/net/HttpURLConnection;->getInputStream()Ljava/io/InputStream;

    move-result-object v6

    const/high16 v5, 0x10000

    invoke-direct {v7, v6, v5}, Ljava/io/BufferedInputStream;-><init>(Ljava/io/InputStream;I)V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_d

    .line 515
    :try_start_3
    new-instance v6, Ljava/io/RandomAccessFile;

    const-string v5, "rw"

    move-object/from16 v0, p1

    invoke-direct {v6, v0, v5}, Ljava/io/RandomAccessFile;-><init>(Ljava/io/File;Ljava/lang/String;)V
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_c

    .line 516
    :try_start_4
    invoke-virtual {v6, v3, v4}, Ljava/io/RandomAccessFile;->seek(J)V

    .line 517
    const v0, 0x8000

    new-array v0, v0, [B

    .line 518
    invoke-static {}, Ljava/lang/System;->nanoTime()J

    move-result-wide v3

    .line 520
    :goto_3
    invoke-virtual {v7, v0}, Ljava/io/InputStream;->read([B)I

    move-result v1

    const/4 v5, -0x1

    if-eq v1, v5, :cond_f

    .line 521
    if-nez v1, :cond_6

    goto :goto_3

    .line 522
    :cond_6
    invoke-virtual/range {p17 .. p17}, Ljava/util/concurrent/atomic/AtomicBoolean;->get()Z

    move-result v5
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_b

    if-nez v5, :cond_e

    .line 523
    if-eqz v11, :cond_8

    :try_start_5
    invoke-interface/range {p19 .. p19}, Lio/kamihama/magianative/CNChunkedDownload$Sink;->isCancelled()Z

    move-result v5

    if-nez v5, :cond_7

    goto :goto_4

    :cond_7
    new-instance v0, Ljava/io/IOException;

    const-string v1, "\u5df2\u53d6\u6d88"

    invoke-direct {v0, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v0
    :try_end_5
    .catchall {:try_start_5 .. :try_end_5} :catchall_2

    .line 560
    :catchall_2
    move-exception v0

    move-wide/from16 v9, p10

    move-object v1, v0

    move-object v5, v6

    move-object/from16 v16, v15

    move-object v15, v7

    goto/16 :goto_d

    .line 526
    :cond_8
    :goto_4
    :try_start_6
    invoke-virtual/range {p6 .. p7}, Ljava/util/concurrent/atomic/AtomicLongArray;->get(I)J

    move-result-wide v16
    :try_end_6
    .catchall {:try_start_6 .. :try_end_6} :catchall_b

    move-object/from16 p5, v7

    move-object/from16 p8, v8

    sub-long v7, v12, v16

    .line 527
    move-object/from16 v16, v15

    int-to-long v14, v1

    :try_start_7
    invoke-static {v14, v15, v7, v8}, Ljava/lang/Math;->min(JJ)J

    move-result-wide v7

    long-to-int v1, v7

    .line 528
    if-gtz v1, :cond_9

    move-object/from16 v15, p5

    move-object/from16 v1, p8

    move-wide/from16 v9, p10

    move-object v14, v6

    move-wide/from16 v18, v12

    goto/16 :goto_8

    .line 529
    :cond_9
    const/4 v5, 0x0

    invoke-virtual {v6, v0, v5, v1}, Ljava/io/RandomAccessFile;->write([BII)V

    .line 531
    int-to-long v7, v1

    move-object/from16 v1, p6

    invoke-virtual {v1, v2, v7, v8}, Ljava/util/concurrent/atomic/AtomicLongArray;->addAndGet(IJ)J

    move-result-wide v14

    .line 532
    move-object/from16 v5, p13

    move-object/from16 v17, v0

    invoke-virtual {v5, v7, v8}, Ljava/util/concurrent/atomic/AtomicLong;->addAndGet(J)J

    move-result-wide v0

    .line 533
    move-wide/from16 v18, v12

    invoke-static {}, Ljava/lang/System;->nanoTime()J

    move-result-wide v12

    .line 534
    move-object/from16 v9, p16

    invoke-virtual {v9, v12, v13}, Ljava/util/concurrent/atomic/AtomicLong;->set(J)V

    .line 536
    invoke-virtual {v10, v7, v8}, Ljava/util/concurrent/atomic/AtomicLong;->addAndGet(J)J

    move-result-wide v7
    :try_end_7
    .catchall {:try_start_7 .. :try_end_7} :catchall_6

    .line 537
    move-object/from16 p18, v6

    :try_start_8
    invoke-virtual/range {p14 .. p14}, Ljava/util/concurrent/atomic/AtomicLong;->get()J

    move-result-wide v5

    .line 538
    sub-long v20, v12, v5

    const-wide/32 v22, 0xf4240

    move-wide/from16 p1, v14

    div-long v14, v20, v22
    :try_end_8
    .catchall {:try_start_8 .. :try_end_8} :catchall_5

    .line 539
    const-wide/16 v20, 0x1f4

    cmp-long v22, v14, v20

    if-ltz v22, :cond_b

    move-object/from16 v9, p14

    :try_start_9
    invoke-virtual {v9, v5, v6, v12, v13}, Ljava/util/concurrent/atomic/AtomicLong;->compareAndSet(JJ)Z

    move-result v5

    if-eqz v5, :cond_b

    .line 540
    const-wide/16 v5, 0x0

    invoke-virtual {v10, v5, v6}, Ljava/util/concurrent/atomic/AtomicLong;->set(J)V
    :try_end_9
    .catchall {:try_start_9 .. :try_end_9} :catchall_4

    .line 541
    if-eqz v11, :cond_a

    .line 542
    move-wide/from16 v9, p10

    :try_start_a
    invoke-interface {v11, v0, v1, v9, v10}, Lio/kamihama/magianative/CNChunkedDownload$Sink;->onProgress(JJ)V

    .line 543
    long-to-double v0, v7

    const-wide v7, 0x408f400000000000L    # 1000.0

    mul-double v0, v0, v7

    long-to-double v7, v14

    div-double/2addr v0, v7

    const-wide v7, 0x412e848000000000L    # 1000000.0

    div-double/2addr v0, v7

    double-to-float v0, v0

    .line 544
    invoke-interface {v11, v0}, Lio/kamihama/magianative/CNChunkedDownload$Sink;->onSpeed(F)V
    :try_end_a
    .catchall {:try_start_a .. :try_end_a} :catchall_3

    goto :goto_6

    .line 560
    :catchall_3
    move-exception v0

    goto :goto_5

    .line 541
    :cond_a
    move-wide/from16 v9, p10

    goto :goto_6

    .line 560
    :catchall_4
    move-exception v0

    move-wide/from16 v9, p10

    :goto_5
    move-object/from16 v15, p5

    move-object/from16 v5, p18

    move-object v1, v0

    goto/16 :goto_d

    .line 539
    :cond_b
    move-wide/from16 v9, p10

    const-wide/16 v5, 0x0

    .line 547
    :goto_6
    sub-long v0, v12, v3

    const-wide/32 v7, 0x77359400

    cmp-long v14, v0, v7

    if-lez v14, :cond_c

    .line 548
    move-object/from16 v3, p9

    move-wide v0, v5

    move-wide/from16 v4, p10

    move-object/from16 v14, p18

    move-object/from16 v6, p12

    move-object/from16 v15, p5

    move-object/from16 v7, p0

    move-object/from16 v1, p8

    move-object/from16 v8, p6

    :try_start_b
    invoke-static/range {v3 .. v8}, Lio/kamihama/magianative/CNChunkedDownload;->saveMeta(Ljava/io/File;JLjava/lang/String;Ljava/lang/String;Ljava/util/concurrent/atomic/AtomicLongArray;)V

    .line 549
    move-wide v3, v12

    goto :goto_7

    .line 547
    :cond_c
    move-object/from16 v15, p5

    move-object/from16 v1, p8

    move-object/from16 v14, p18

    .line 551
    :goto_7
    move-wide/from16 v5, p1

    cmp-long v0, v5, v18

    if-ltz v0, :cond_d

    goto :goto_8

    .line 552
    :cond_d
    move-object/from16 v9, p12

    move-object/from16 v10, p15

    move-object v8, v1

    move-object v6, v14

    move-object v7, v15

    move-object/from16 v15, v16

    move-object/from16 v0, v17

    move-wide/from16 v12, v18

    move-object/from16 v14, p0

    goto/16 :goto_3

    .line 560
    :catchall_5
    move-exception v0

    move-object/from16 v15, p5

    move-wide/from16 v9, p10

    move-object/from16 v14, p18

    goto/16 :goto_c

    :catchall_6
    move-exception v0

    move-object/from16 v15, p5

    move-wide/from16 v9, p10

    move-object v14, v6

    goto/16 :goto_c

    .line 522
    :cond_e
    move-wide/from16 v9, p10

    move-object v14, v6

    move-object/from16 v16, v15

    move-object v15, v7

    new-instance v0, Ljava/io/IOException;

    const-string v1, "\u5df2\u4e2d\u65ad"

    invoke-direct {v0, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v0

    .line 520
    :cond_f
    move-wide/from16 v9, p10

    move-object v14, v6

    move-object v1, v8

    move-wide/from16 v18, v12

    move-object/from16 v16, v15

    move-object v15, v7

    .line 555
    :goto_8
    invoke-virtual/range {p6 .. p7}, Ljava/util/concurrent/atomic/AtomicLongArray;->get(I)J

    move-result-wide v3
    :try_end_b
    .catchall {:try_start_b .. :try_end_b} :catchall_a

    .line 556
    cmp-long v0, v3, v18

    if-ltz v0, :cond_10

    .line 560
    move-object/from16 p13, p9

    move-wide/from16 p14, p10

    move-object/from16 p16, p12

    move-object/from16 p17, p0

    move-object/from16 p18, p6

    invoke-static/range {p13 .. p18}, Lio/kamihama/magianative/CNChunkedDownload;->saveMeta(Ljava/io/File;JLjava/lang/String;Ljava/lang/String;Ljava/util/concurrent/atomic/AtomicLongArray;)V

    .line 561
    :try_start_c
    invoke-virtual {v14}, Ljava/io/RandomAccessFile;->close()V
    :try_end_c
    .catchall {:try_start_c .. :try_end_c} :catchall_7

    goto :goto_9

    :catchall_7
    move-exception v0

    .line 562
    :goto_9
    :try_start_d
    invoke-virtual {v15}, Ljava/io/InputStream;->close()V
    :try_end_d
    .catchall {:try_start_d .. :try_end_d} :catchall_8

    goto :goto_a

    :catchall_8
    move-exception v0

    .line 563
    :goto_a
    :try_start_e
    invoke-virtual/range {v16 .. v16}, Ljava/net/HttpURLConnection;->disconnect()V
    :try_end_e
    .catchall {:try_start_e .. :try_end_e} :catchall_9

    goto :goto_b

    :catchall_9
    move-exception v0

    .line 564
    nop

    .line 565
    :goto_b
    return-void

    .line 557
    :cond_10
    :try_start_f
    new-instance v0, Ljava/io/IOException;

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v5, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, " \u77ed\u8bfb: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v3, v4}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, " / "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    move-wide/from16 v3, v18

    invoke-virtual {v1, v3, v4}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v0
    :try_end_f
    .catchall {:try_start_f .. :try_end_f} :catchall_a

    .line 560
    :catchall_a
    move-exception v0

    goto :goto_c

    :catchall_b
    move-exception v0

    move-wide/from16 v9, p10

    move-object v14, v6

    move-object/from16 v16, v15

    move-object v15, v7

    :goto_c
    move-object v1, v0

    move-object v5, v14

    goto :goto_d

    :catchall_c
    move-exception v0

    move-wide/from16 v9, p10

    move-object/from16 v16, v15

    move-object v15, v7

    move-object v1, v0

    const/4 v5, 0x0

    goto :goto_d

    :catchall_d
    move-exception v0

    move-wide/from16 v9, p10

    move-object/from16 v16, v15

    move-object v1, v0

    const/4 v5, 0x0

    const/4 v15, 0x0

    :goto_d
    move-object/from16 p13, p9

    move-wide/from16 p14, p10

    move-object/from16 p16, p12

    move-object/from16 p17, p0

    move-object/from16 p18, p6

    invoke-static/range {p13 .. p18}, Lio/kamihama/magianative/CNChunkedDownload;->saveMeta(Ljava/io/File;JLjava/lang/String;Ljava/lang/String;Ljava/util/concurrent/atomic/AtomicLongArray;)V

    .line 561
    if-eqz v5, :cond_11

    :try_start_10
    invoke-virtual {v5}, Ljava/io/RandomAccessFile;->close()V
    :try_end_10
    .catchall {:try_start_10 .. :try_end_10} :catchall_e

    goto :goto_e

    :catchall_e
    move-exception v0

    .line 562
    :cond_11
    :goto_e
    if-eqz v15, :cond_12

    :try_start_11
    invoke-virtual {v15}, Ljava/io/InputStream;->close()V
    :try_end_11
    .catchall {:try_start_11 .. :try_end_11} :catchall_f

    goto :goto_f

    :catchall_f
    move-exception v0

    .line 563
    :cond_12
    :goto_f
    :try_start_12
    invoke-virtual/range {v16 .. v16}, Ljava/net/HttpURLConnection;->disconnect()V
    :try_end_12
    .catchall {:try_start_12 .. :try_end_12} :catchall_10

    goto :goto_10

    :catchall_10
    move-exception v0

    .line 564
    :goto_10
    throw v1
.end method

.method private static open(Ljava/lang/String;Z)Ljava/net/HttpURLConnection;
    .locals 1
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 119
    new-instance v0, Ljava/net/URL;

    invoke-direct {v0, p0}, Ljava/net/URL;-><init>(Ljava/lang/String;)V

    .line 121
    if-eqz p1, :cond_0

    sget-object p0, Ljava/net/Proxy;->NO_PROXY:Ljava/net/Proxy;

    invoke-virtual {v0, p0}, Ljava/net/URL;->openConnection(Ljava/net/Proxy;)Ljava/net/URLConnection;

    move-result-object p0

    goto :goto_0

    :cond_0
    invoke-virtual {v0}, Ljava/net/URL;->openConnection()Ljava/net/URLConnection;

    move-result-object p0

    :goto_0
    check-cast p0, Ljava/net/HttpURLConnection;

    .line 122
    const/16 p1, 0x3a98

    invoke-virtual {p0, p1}, Ljava/net/HttpURLConnection;->setConnectTimeout(I)V

    .line 123
    const/16 p1, 0x7530

    invoke-virtual {p0, p1}, Ljava/net/HttpURLConnection;->setReadTimeout(I)V

    .line 124
    const/4 p1, 0x0

    invoke-virtual {p0, p1}, Ljava/net/HttpURLConnection;->setUseCaches(Z)V

    .line 125
    const/4 p1, 0x1

    invoke-virtual {p0, p1}, Ljava/net/HttpURLConnection;->setInstanceFollowRedirects(Z)V

    .line 126
    const-string p1, "Accept-Encoding"

    const-string v0, "identity"

    invoke-virtual {p0, p1, v0}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 127
    const-string p1, "Connection"

    const-string v0, "close"

    invoke-virtual {p0, p1, v0}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 128
    return-object p0
.end method

.method private static parseLong(Ljava/lang/String;J)J
    .locals 4

    .line 664
    if-nez p0, :cond_0

    return-wide p1

    .line 666
    :cond_0
    :try_start_0
    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v0
    :try_end_0
    .catch Ljava/lang/NumberFormatException; {:try_start_0 .. :try_end_0} :catch_0

    .line 667
    const-wide/16 v2, 0x0

    cmp-long p0, v0, v2

    if-ltz p0, :cond_1

    move-wide p1, v0

    :cond_1
    return-wide p1

    .line 668
    :catch_0
    move-exception p0

    .line 669
    return-wide p1
.end method

.method public static partFileFor(Ljava/io/File;)Ljava/io/File;
    .locals 2

    .line 178
    new-instance v0, Ljava/io/File;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p0}, Ljava/io/File;->getPath()Ljava/lang/String;

    move-result-object p0

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string v1, ".cpart"

    invoke-virtual {p0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-direct {v0, p0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    return-object v0
.end method

.method public static probe(Ljava/lang/String;Z)Lio/kamihama/magianative/CNChunkedDownload$Probe;
    .locals 16

    .line 137
    const-string v1, "ETag"

    const-string v2, "Content-Length"

    .line 139
    const/16 v3, 0x12c

    const/16 v4, 0xc8

    const/4 v5, 0x1

    const-wide/16 v6, 0x0

    const-wide/16 v8, -0x1

    const/4 v10, 0x0

    :try_start_0
    invoke-static/range {p0 .. p1}, Lio/kamihama/magianative/CNChunkedDownload;->open(Ljava/lang/String;Z)Ljava/net/HttpURLConnection;

    move-result-object v12
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_4

    .line 140
    :try_start_1
    const-string v0, "HEAD"

    invoke-virtual {v12, v0}, Ljava/net/HttpURLConnection;->setRequestMethod(Ljava/lang/String;)V

    .line 141
    invoke-virtual {v12}, Ljava/net/HttpURLConnection;->getResponseCode()I

    move-result v0

    .line 142
    if-lt v0, v4, :cond_4

    if-ge v0, v3, :cond_4

    .line 143
    invoke-virtual {v12, v2}, Ljava/net/HttpURLConnection;->getHeaderField(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0, v8, v9}, Lio/kamihama/magianative/CNChunkedDownload;->parseLong(Ljava/lang/String;J)J

    move-result-wide v13

    .line 144
    const-string v0, "Accept-Ranges"

    invoke-virtual {v12, v0}, Ljava/net/HttpURLConnection;->getHeaderField(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 145
    invoke-virtual {v12, v1}, Ljava/net/HttpURLConnection;->getHeaderField(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v15

    .line 146
    if-eqz v0, :cond_0

    sget-object v11, Ljava/util/Locale;->US:Ljava/util/Locale;

    invoke-virtual {v0, v11}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object v0

    const-string v11, "bytes"

    invoke-virtual {v0, v11}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    .line 147
    :goto_0
    cmp-long v11, v13, v6

    if-lez v11, :cond_2

    if-eqz v0, :cond_2

    new-instance v11, Lio/kamihama/magianative/CNChunkedDownload$Probe;

    invoke-direct {v11, v13, v14, v15, v5}, Lio/kamihama/magianative/CNChunkedDownload$Probe;-><init>(JLjava/lang/String;Z)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_3

    .line 152
    if-eqz v12, :cond_1

    :try_start_2
    invoke-virtual {v12}, Ljava/net/HttpURLConnection;->disconnect()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    goto :goto_1

    :catchall_0
    move-exception v0

    .line 147
    :cond_1
    :goto_1
    return-object v11

    .line 148
    :cond_2
    if-lez v11, :cond_4

    :try_start_3
    new-instance v11, Lio/kamihama/magianative/CNChunkedDownload$Probe;

    invoke-direct {v11, v13, v14, v15, v10}, Lio/kamihama/magianative/CNChunkedDownload$Probe;-><init>(JLjava/lang/String;Z)V
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_3

    .line 152
    if-eqz v12, :cond_3

    :try_start_4
    invoke-virtual {v12}, Ljava/net/HttpURLConnection;->disconnect()V
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_1

    goto :goto_2

    :catchall_1
    move-exception v0

    .line 148
    :cond_3
    :goto_2
    return-object v11

    .line 152
    :cond_4
    if-eqz v12, :cond_5

    :try_start_5
    invoke-virtual {v12}, Ljava/net/HttpURLConnection;->disconnect()V

    goto :goto_4

    :catchall_2
    move-exception v0

    goto :goto_4

    .line 150
    :catchall_3
    move-exception v0

    goto :goto_3

    :catchall_4
    move-exception v0

    const/4 v12, 0x0

    .line 152
    :goto_3
    if-eqz v12, :cond_5

    invoke-virtual {v12}, Ljava/net/HttpURLConnection;->disconnect()V
    :try_end_5
    .catchall {:try_start_5 .. :try_end_5} :catchall_2

    .line 155
    :cond_5
    :goto_4
    nop

    .line 157
    :try_start_6
    invoke-static/range {p0 .. p1}, Lio/kamihama/magianative/CNChunkedDownload;->open(Ljava/lang/String;Z)Ljava/net/HttpURLConnection;

    move-result-object v11
    :try_end_6
    .catchall {:try_start_6 .. :try_end_6} :catchall_9

    .line 158
    :try_start_7
    const-string v0, "GET"

    invoke-virtual {v11, v0}, Ljava/net/HttpURLConnection;->setRequestMethod(Ljava/lang/String;)V

    .line 159
    const-string v0, "Range"

    const-string v12, "bytes=0-0"

    invoke-virtual {v11, v0, v12}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 160
    invoke-virtual {v11}, Ljava/net/HttpURLConnection;->getResponseCode()I

    move-result v0

    .line 161
    invoke-virtual {v11, v1}, Ljava/net/HttpURLConnection;->getHeaderField(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 162
    const/16 v12, 0xce

    if-ne v0, v12, :cond_7

    .line 163
    const-string v0, "Content-Range"

    invoke-virtual {v11, v0}, Ljava/net/HttpURLConnection;->getHeaderField(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lio/kamihama/magianative/CNChunkedDownload;->totalFromContentRange(Ljava/lang/String;)J

    move-result-wide v2

    .line 164
    cmp-long v0, v2, v6

    if-lez v0, :cond_9

    new-instance v4, Lio/kamihama/magianative/CNChunkedDownload$Probe;

    invoke-direct {v4, v2, v3, v1, v5}, Lio/kamihama/magianative/CNChunkedDownload$Probe;-><init>(JLjava/lang/String;Z)V
    :try_end_7
    .catchall {:try_start_7 .. :try_end_7} :catchall_8

    .line 171
    if-eqz v11, :cond_6

    :try_start_8
    invoke-virtual {v11}, Ljava/net/HttpURLConnection;->disconnect()V
    :try_end_8
    .catchall {:try_start_8 .. :try_end_8} :catchall_5

    goto :goto_5

    :catchall_5
    move-exception v0

    .line 164
    :cond_6
    :goto_5
    return-object v4

    .line 165
    :cond_7
    if-lt v0, v4, :cond_9

    if-ge v0, v3, :cond_9

    .line 166
    :try_start_9
    invoke-virtual {v11, v2}, Ljava/net/HttpURLConnection;->getHeaderField(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0, v8, v9}, Lio/kamihama/magianative/CNChunkedDownload;->parseLong(Ljava/lang/String;J)J

    move-result-wide v2

    .line 167
    cmp-long v0, v2, v6

    if-lez v0, :cond_a

    new-instance v4, Lio/kamihama/magianative/CNChunkedDownload$Probe;

    invoke-direct {v4, v2, v3, v1, v10}, Lio/kamihama/magianative/CNChunkedDownload$Probe;-><init>(JLjava/lang/String;Z)V
    :try_end_9
    .catchall {:try_start_9 .. :try_end_9} :catchall_8

    .line 171
    if-eqz v11, :cond_8

    :try_start_a
    invoke-virtual {v11}, Ljava/net/HttpURLConnection;->disconnect()V
    :try_end_a
    .catchall {:try_start_a .. :try_end_a} :catchall_6

    goto :goto_6

    :catchall_6
    move-exception v0

    .line 167
    :cond_8
    :goto_6
    return-object v4

    .line 165
    :cond_9
    nop

    .line 171
    :cond_a
    if-eqz v11, :cond_b

    :try_start_b
    invoke-virtual {v11}, Ljava/net/HttpURLConnection;->disconnect()V

    goto :goto_8

    :catchall_7
    move-exception v0

    goto :goto_8

    .line 169
    :catchall_8
    move-exception v0

    goto :goto_7

    :catchall_9
    move-exception v0

    const/4 v11, 0x0

    .line 171
    :goto_7
    if-eqz v11, :cond_b

    invoke-virtual {v11}, Ljava/net/HttpURLConnection;->disconnect()V
    :try_end_b
    .catchall {:try_start_b .. :try_end_b} :catchall_7

    .line 173
    :cond_b
    :goto_8
    new-instance v0, Lio/kamihama/magianative/CNChunkedDownload$Probe;

    const-string v1, ""

    invoke-direct {v0, v8, v9, v1, v10}, Lio/kamihama/magianative/CNChunkedDownload$Probe;-><init>(JLjava/lang/String;Z)V

    return-object v0
.end method

.method private static promote(Ljava/io/File;Ljava/io/File;)V
    .locals 3
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 649
    invoke-virtual {p1}, Ljava/io/File;->exists()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-virtual {p1}, Ljava/io/File;->delete()Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    .line 650
    :cond_0
    new-instance p0, Ljava/io/IOException;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "\u65e0\u6cd5\u66ff\u6362\u76ee\u6807\u6587\u4ef6 "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0, p1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw p0

    .line 652
    :cond_1
    :goto_0
    invoke-virtual {p0, p1}, Ljava/io/File;->renameTo(Ljava/io/File;)Z

    move-result v0

    if-eqz v0, :cond_2

    .line 655
    return-void

    .line 653
    :cond_2
    new-instance v0, Ljava/io/IOException;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "\u65e0\u6cd5\u91cd\u547d\u540d "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string v1, " -> "

    invoke-virtual {p0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-direct {v0, p0}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v0
.end method

.method private static rangeStart(Ljava/lang/String;)J
    .locals 4

    .line 675
    const-wide/16 v0, -0x1

    if-nez p0, :cond_0

    return-wide v0

    .line 676
    :cond_0
    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    sget-object v2, Ljava/util/Locale;->US:Ljava/util/Locale;

    invoke-virtual {p0, v2}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object p0

    .line 677
    const-string v2, "bytes "

    invoke-virtual {p0, v2}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v2

    if-nez v2, :cond_1

    return-wide v0

    .line 678
    :cond_1
    const/16 v2, 0x2d

    const/4 v3, 0x6

    invoke-virtual {p0, v2, v3}, Ljava/lang/String;->indexOf(II)I

    move-result v2

    .line 679
    if-gez v2, :cond_2

    return-wide v0

    .line 680
    :cond_2
    invoke-virtual {p0, v3, v2}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object p0

    invoke-static {p0, v0, v1}, Lio/kamihama/magianative/CNChunkedDownload;->parseLong(Ljava/lang/String;J)J

    move-result-wide v0

    return-wide v0
.end method

.method private static declared-synchronized readResume(Ljava/io/File;)Lio/kamihama/magianative/CNChunkedDownload$Resume;
    .locals 10

    const-class v0, Lio/kamihama/magianative/CNChunkedDownload;

    monitor-enter v0

    .line 605
    :try_start_0
    invoke-virtual {p0}, Ljava/io/File;->isFile()Z

    move-result v1

    const/4 v2, 0x0

    if-eqz v1, :cond_b

    invoke-virtual {p0}, Ljava/io/File;->length()J

    move-result-wide v3
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_9

    const-wide/32 v5, 0x100000

    cmp-long v1, v3, v5

    if-lez v1, :cond_0

    goto/16 :goto_c

    .line 606
    :cond_0
    nop

    .line 608
    :try_start_1
    new-instance v1, Ljava/io/BufferedReader;

    new-instance v3, Ljava/io/InputStreamReader;

    new-instance v4, Ljava/io/FileInputStream;

    invoke-direct {v4, p0}, Ljava/io/FileInputStream;-><init>(Ljava/io/File;)V

    const-string p0, "UTF-8"

    invoke-direct {v3, v4, p0}, Ljava/io/InputStreamReader;-><init>(Ljava/io/InputStream;Ljava/lang/String;)V

    invoke-direct {v1, v3}, Ljava/io/BufferedReader;-><init>(Ljava/io/Reader;)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_7

    .line 610
    :try_start_2
    invoke-virtual {v1}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object p0

    .line 611
    const-string v3, "CNVPROG3"

    invoke-virtual {v3, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p0
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_6

    if-nez p0, :cond_1

    .line 637
    :try_start_3
    invoke-virtual {v1}, Ljava/io/BufferedReader;->close()V
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_0

    goto :goto_0

    :catchall_0
    move-exception p0

    .line 611
    :goto_0
    monitor-exit v0

    return-object v2

    .line 612
    :cond_1
    :try_start_4
    invoke-virtual {v1}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object p0
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_6

    .line 613
    if-nez p0, :cond_2

    .line 637
    :try_start_5
    invoke-virtual {v1}, Ljava/io/BufferedReader;->close()V
    :try_end_5
    .catchall {:try_start_5 .. :try_end_5} :catchall_1

    goto :goto_1

    :catchall_1
    move-exception p0

    .line 613
    :goto_1
    monitor-exit v0

    return-object v2

    .line 614
    :cond_2
    :try_start_6
    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    const-string v3, "\\s+"

    invoke-virtual {p0, v3}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p0

    .line 615
    array-length v3, p0
    :try_end_6
    .catchall {:try_start_6 .. :try_end_6} :catchall_6

    const/4 v4, 0x2

    if-ge v3, v4, :cond_3

    .line 637
    :try_start_7
    invoke-virtual {v1}, Ljava/io/BufferedReader;->close()V
    :try_end_7
    .catchall {:try_start_7 .. :try_end_7} :catchall_2

    goto :goto_2

    :catchall_2
    move-exception p0

    .line 615
    :goto_2
    monitor-exit v0

    return-object v2

    .line 617
    :cond_3
    :try_start_8
    new-instance v3, Lio/kamihama/magianative/CNChunkedDownload$Resume;

    invoke-direct {v3, v2}, Lio/kamihama/magianative/CNChunkedDownload$Resume;-><init>(Lio/kamihama/magianative/CNChunkedDownload$1;)V

    .line 618
    const/4 v4, 0x0

    aget-object v5, p0, v4

    invoke-static {v5}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v5

    iput-wide v5, v3, Lio/kamihama/magianative/CNChunkedDownload$Resume;->total:J

    .line 619
    const/4 v5, 0x1

    aget-object p0, p0, v5

    invoke-static {p0}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result p0

    iput p0, v3, Lio/kamihama/magianative/CNChunkedDownload$Resume;->chunks:I

    .line 620
    iget-wide v6, v3, Lio/kamihama/magianative/CNChunkedDownload$Resume;->total:J

    const-wide/16 v8, 0x0

    cmp-long p0, v6, v8

    if-lez p0, :cond_9

    iget p0, v3, Lio/kamihama/magianative/CNChunkedDownload$Resume;->chunks:I

    if-lt p0, v5, :cond_9

    iget p0, v3, Lio/kamihama/magianative/CNChunkedDownload$Resume;->chunks:I

    const/16 v5, 0x40

    if-le p0, v5, :cond_4

    goto :goto_8

    .line 622
    :cond_4
    invoke-virtual {v1}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object p0

    .line 623
    if-nez p0, :cond_5

    const-string p0, ""

    goto :goto_3

    :cond_5
    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    :goto_3
    iput-object p0, v3, Lio/kamihama/magianative/CNChunkedDownload$Resume;->etag:Ljava/lang/String;

    .line 624
    invoke-virtual {v1}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object p0

    .line 625
    if-nez p0, :cond_6

    const-string p0, ""

    goto :goto_4

    :cond_6
    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    :goto_4
    iput-object p0, v3, Lio/kamihama/magianative/CNChunkedDownload$Resume;->url:Ljava/lang/String;

    .line 627
    iget p0, v3, Lio/kamihama/magianative/CNChunkedDownload$Resume;->chunks:I

    new-array p0, p0, [J

    iput-object p0, v3, Lio/kamihama/magianative/CNChunkedDownload$Resume;->done:[J

    .line 628
    nop

    :goto_5
    iget p0, v3, Lio/kamihama/magianative/CNChunkedDownload$Resume;->chunks:I

    if-ge v4, p0, :cond_8

    .line 629
    invoke-virtual {v1}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object p0
    :try_end_8
    .catchall {:try_start_8 .. :try_end_8} :catchall_6

    .line 630
    if-nez p0, :cond_7

    .line 637
    :try_start_9
    invoke-virtual {v1}, Ljava/io/BufferedReader;->close()V
    :try_end_9
    .catchall {:try_start_9 .. :try_end_9} :catchall_3

    goto :goto_6

    :catchall_3
    move-exception p0

    .line 630
    :goto_6
    monitor-exit v0

    return-object v2

    .line 631
    :cond_7
    :try_start_a
    iget-object v5, v3, Lio/kamihama/magianative/CNChunkedDownload$Resume;->done:[J

    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v6

    aput-wide v6, v5, v4
    :try_end_a
    .catchall {:try_start_a .. :try_end_a} :catchall_6

    .line 628
    add-int/lit8 v4, v4, 0x1

    goto :goto_5

    .line 633
    :cond_8
    nop

    .line 637
    :try_start_b
    invoke-virtual {v1}, Ljava/io/BufferedReader;->close()V
    :try_end_b
    .catchall {:try_start_b .. :try_end_b} :catchall_4

    goto :goto_7

    :catchall_4
    move-exception p0

    .line 633
    :goto_7
    monitor-exit v0

    return-object v3

    .line 637
    :cond_9
    :goto_8
    :try_start_c
    invoke-virtual {v1}, Ljava/io/BufferedReader;->close()V
    :try_end_c
    .catchall {:try_start_c .. :try_end_c} :catchall_5

    goto :goto_9

    :catchall_5
    move-exception p0

    .line 620
    :goto_9
    monitor-exit v0

    return-object v2

    .line 634
    :catchall_6
    move-exception p0

    goto :goto_a

    :catchall_7
    move-exception p0

    move-object v1, v2

    .line 635
    :goto_a
    nop

    .line 637
    if-eqz v1, :cond_a

    :try_start_d
    invoke-virtual {v1}, Ljava/io/BufferedReader;->close()V
    :try_end_d
    .catchall {:try_start_d .. :try_end_d} :catchall_8

    goto :goto_b

    :catchall_8
    move-exception p0

    .line 635
    :cond_a
    :goto_b
    monitor-exit v0

    return-object v2

    .line 605
    :cond_b
    :goto_c
    monitor-exit v0

    return-object v2

    .line 604
    :catchall_9
    move-exception p0

    monitor-exit v0

    throw p0
.end method

.method private static resumeRejectReason(Lio/kamihama/magianative/CNChunkedDownload$Resume;JLjava/lang/String;Ljava/lang/String;Ljava/io/File;)Ljava/lang/String;
    .locals 9

    .line 382
    iget-wide v0, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->total:J

    const-string v2, " != "

    cmp-long v3, v0, p1

    if-eqz v3, :cond_0

    .line 383
    new-instance p3, Ljava/lang/StringBuilder;

    invoke-direct {p3}, Ljava/lang/StringBuilder;-><init>()V

    const-string p4, "\u603b\u957f\u5ea6\u4e0d\u7b26 "

    invoke-virtual {p3, p4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p3

    iget-wide p4, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->total:J

    invoke-virtual {p3, p4, p5}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, p1, p2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0

    .line 385
    :cond_0
    iget v0, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->chunks:I

    const/4 v1, 0x1

    if-lt v0, v1, :cond_9

    iget-object v0, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->done:[J

    if-eqz v0, :cond_9

    iget-object v0, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->done:[J

    array-length v0, v0

    iget v3, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->chunks:I

    if-eq v0, v3, :cond_1

    goto/16 :goto_3

    .line 390
    :cond_1
    invoke-virtual {p5}, Ljava/io/File;->isFile()Z

    move-result v0

    if-nez v0, :cond_2

    .line 391
    const-string p0, "\u4e34\u65f6\u6587\u4ef6\u4e0d\u5b58\u5728"

    return-object p0

    .line 393
    :cond_2
    invoke-virtual {p5}, Ljava/io/File;->length()J

    move-result-wide v3

    cmp-long v0, v3, p1

    if-eqz v0, :cond_3

    .line 394
    new-instance p0, Ljava/lang/StringBuilder;

    invoke-direct {p0}, Ljava/lang/StringBuilder;-><init>()V

    const-string p3, "\u4e34\u65f6\u6587\u4ef6\u957f\u5ea6\u4e0d\u7b26 "

    invoke-virtual {p0, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p5}, Ljava/io/File;->length()J

    move-result-wide p3

    invoke-virtual {p0, p3, p4}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, p1, p2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0

    .line 401
    :cond_3
    iget-object p5, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->url:Ljava/lang/String;

    invoke-virtual {p5}, Ljava/lang/String;->length()I

    move-result p5

    const/4 v0, 0x0

    if-lez p5, :cond_4

    iget-object p5, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->url:Ljava/lang/String;

    invoke-virtual {p5, p4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p4

    if-eqz p4, :cond_4

    goto :goto_0

    :cond_4
    const/4 v1, 0x0

    .line 402
    :goto_0
    if-eqz v1, :cond_5

    iget-object p4, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->etag:Ljava/lang/String;

    invoke-virtual {p4}, Ljava/lang/String;->length()I

    move-result p4

    if-lez p4, :cond_5

    if-eqz p3, :cond_5

    invoke-virtual {p3}, Ljava/lang/String;->length()I

    move-result p4

    if-lez p4, :cond_5

    iget-object p4, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->etag:Ljava/lang/String;

    .line 403
    invoke-virtual {p4, p3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p3

    if-nez p3, :cond_5

    .line 404
    const-string p0, "ETag \u5df2\u53d8\u5316"

    return-object p0

    .line 406
    :cond_5
    iget p3, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->chunks:I

    int-to-long p3, p3

    add-long/2addr p3, p1

    const-wide/16 v1, 0x1

    sub-long/2addr p3, v1

    iget p5, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->chunks:I

    int-to-long v3, p5

    div-long/2addr p3, v3

    .line 407
    nop

    :goto_1
    iget p5, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->chunks:I

    if-ge v0, p5, :cond_8

    .line 408
    int-to-long v3, v0

    mul-long v3, v3, p3

    .line 409
    add-long v5, v3, p3

    sub-long/2addr v5, v1

    sub-long v7, p1, v1

    invoke-static {v5, v6, v7, v8}, Ljava/lang/Math;->min(JJ)J

    move-result-wide v5

    .line 410
    sub-long/2addr v5, v3

    add-long/2addr v5, v1

    .line 411
    iget-object p5, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->done:[J

    aget-wide v3, p5, v0

    const-wide/16 v7, 0x0

    cmp-long p5, v3, v7

    if-ltz p5, :cond_7

    iget-object p5, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->done:[J

    aget-wide v3, p5, v0

    cmp-long p5, v3, v5

    if-lez p5, :cond_6

    goto :goto_2

    .line 407
    :cond_6
    add-int/lit8 v0, v0, 0x1

    goto :goto_1

    .line 412
    :cond_7
    :goto_2
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string p2, "\u5206\u7247 "

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string p2, " \u8fdb\u5ea6\u8d8a\u754c "

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    iget-object p0, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->done:[J

    aget-wide p2, p0, v0

    invoke-virtual {p1, p2, p3}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string p1, " / "

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, v5, v6}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0

    .line 415
    :cond_8
    const/4 p0, 0x0

    return-object p0

    .line 386
    :cond_9
    :goto_3
    const-string p0, "\u5206\u7247\u4fe1\u606f\u635f\u574f"

    return-object p0
.end method

.method private static sanitize(Ljava/lang/String;)Ljava/lang/String;
    .locals 2

    .line 644
    if-nez p0, :cond_0

    const-string p0, ""

    return-object p0

    .line 645
    :cond_0
    const/16 v0, 0xd

    const/16 v1, 0x20

    invoke-virtual {p0, v0, v1}, Ljava/lang/String;->replace(CC)Ljava/lang/String;

    move-result-object p0

    const/16 v0, 0xa

    invoke-virtual {p0, v0, v1}, Ljava/lang/String;->replace(CC)Ljava/lang/String;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method private static declared-synchronized saveMeta(Ljava/io/File;JLjava/lang/String;Ljava/lang/String;Ljava/util/concurrent/atomic/AtomicLongArray;)V
    .locals 8

    const-class v0, Lio/kamihama/magianative/CNChunkedDownload;

    monitor-enter v0

    .line 579
    :try_start_0
    new-instance v1, Ljava/io/File;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p0}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, ".tmp"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-direct {v1, v2}, Ljava/io/File;-><init>(Ljava/lang/String;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_7

    .line 580
    nop

    .line 582
    const/4 v2, 0x0

    :try_start_1
    new-instance v3, Ljava/io/OutputStreamWriter;

    new-instance v4, Ljava/io/FileOutputStream;

    const/4 v5, 0x0

    invoke-direct {v4, v1, v5}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;Z)V

    const-string v6, "UTF-8"

    invoke-direct {v3, v4, v6}, Ljava/io/OutputStreamWriter;-><init>(Ljava/io/OutputStream;Ljava/lang/String;)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_2

    .line 583
    :try_start_2
    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    .line 584
    const-string v6, "CNVPROG3"

    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const/16 v7, 0xa

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 585
    invoke-virtual {v4, p1, p2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object p1

    const/16 p2, 0x20

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p5}, Ljava/util/concurrent/atomic/AtomicLongArray;->length()I

    move-result p2

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, v7}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 586
    invoke-static {p3}, Lio/kamihama/magianative/CNChunkedDownload;->sanitize(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v4, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, v7}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 587
    invoke-static {p4}, Lio/kamihama/magianative/CNChunkedDownload;->sanitize(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v4, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, v7}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 588
    nop

    :goto_0
    invoke-virtual {p5}, Ljava/util/concurrent/atomic/AtomicLongArray;->length()I

    move-result p1

    if-ge v5, p1, :cond_0

    invoke-virtual {p5, v5}, Ljava/util/concurrent/atomic/AtomicLongArray;->get(I)J

    move-result-wide p1

    invoke-virtual {v4, p1, p2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, v7}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    add-int/lit8 v5, v5, 0x1

    goto :goto_0

    .line 589
    :cond_0
    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v3, p1}, Ljava/io/Writer;->write(Ljava/lang/String;)V

    .line 590
    invoke-virtual {v3}, Ljava/io/Writer;->flush()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_1

    .line 596
    :try_start_3
    invoke-virtual {v3}, Ljava/io/Writer;->close()V
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_0

    goto :goto_1

    :catchall_0
    move-exception p1

    .line 598
    :goto_1
    :try_start_4
    invoke-virtual {v1, p0}, Ljava/io/File;->renameTo(Ljava/io/File;)Z

    move-result p1

    if-nez p1, :cond_1

    .line 599
    invoke-static {p0}, Lio/kamihama/magianative/CNChunkedDownload;->deleteQuietly(Ljava/io/File;)V

    .line 600
    invoke-virtual {v1, p0}, Ljava/io/File;->renameTo(Ljava/io/File;)Z

    move-result p0

    if-nez p0, :cond_1

    invoke-static {v1}, Lio/kamihama/magianative/CNChunkedDownload;->deleteQuietly(Ljava/io/File;)V
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_7

    .line 602
    :cond_1
    monitor-exit v0

    return-void

    .line 591
    :catchall_1
    move-exception p0

    goto :goto_2

    :catchall_2
    move-exception p0

    move-object v3, v2

    .line 592
    :goto_2
    if-eqz v3, :cond_2

    :try_start_5
    invoke-virtual {v3}, Ljava/io/Writer;->close()V
    :try_end_5
    .catchall {:try_start_5 .. :try_end_5} :catchall_3

    goto :goto_3

    :catchall_3
    move-exception p0

    goto :goto_3

    :cond_2
    move-object v2, v3

    .line 593
    :goto_3
    :try_start_6
    invoke-static {v1}, Lio/kamihama/magianative/CNChunkedDownload;->deleteQuietly(Ljava/io/File;)V
    :try_end_6
    .catchall {:try_start_6 .. :try_end_6} :catchall_5

    .line 596
    if-eqz v2, :cond_3

    :try_start_7
    invoke-virtual {v2}, Ljava/io/Writer;->close()V
    :try_end_7
    .catchall {:try_start_7 .. :try_end_7} :catchall_4

    goto :goto_4

    :catchall_4
    move-exception p0

    .line 594
    :cond_3
    :goto_4
    monitor-exit v0

    return-void

    .line 596
    :catchall_5
    move-exception p0

    if-eqz v2, :cond_4

    :try_start_8
    invoke-virtual {v2}, Ljava/io/Writer;->close()V
    :try_end_8
    .catchall {:try_start_8 .. :try_end_8} :catchall_6

    goto :goto_5

    :catchall_6
    move-exception p1

    .line 597
    :cond_4
    :goto_5
    :try_start_9
    throw p0
    :try_end_9
    .catchall {:try_start_9 .. :try_end_9} :catchall_7

    .line 578
    :catchall_7
    move-exception p0

    monitor-exit v0

    throw p0
.end method

.method private static totalFromContentRange(Ljava/lang/String;)J
    .locals 3

    .line 685
    const-wide/16 v0, -0x1

    if-nez p0, :cond_0

    return-wide v0

    .line 686
    :cond_0
    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    sget-object v2, Ljava/util/Locale;->US:Ljava/util/Locale;

    invoke-virtual {p0, v2}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object p0

    .line 687
    const/16 v2, 0x2f

    invoke-virtual {p0, v2}, Ljava/lang/String;->indexOf(I)I

    move-result v2

    .line 688
    if-gez v2, :cond_1

    return-wide v0

    .line 689
    :cond_1
    add-int/lit8 v2, v2, 0x1

    invoke-virtual {p0, v2}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object p0

    invoke-static {p0, v0, v1}, Lio/kamihama/magianative/CNChunkedDownload;->parseLong(Ljava/lang/String;J)J

    move-result-wide v0

    return-wide v0
.end method
