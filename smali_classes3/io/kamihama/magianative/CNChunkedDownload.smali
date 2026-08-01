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

.field private static final META_MAGIC:Ljava/lang/String; = "CNVPROG2"

.field private static final READ_TIMEOUT_MS:I = 0x7530

.field private static final TAG:Ljava/lang/String; = "MagiaCNChunk"


# direct methods
.method private constructor <init>()V
    .locals 0

    .line 70
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static synthetic access$200(Ljava/lang/String;Ljava/io/File;JJLjava/util/concurrent/atomic/AtomicLongArray;IZLjava/io/File;JLjava/lang/String;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicBoolean;Lio/kamihama/magianative/CNChunkedDownload$Sink;)V
    .locals 0
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 60
    invoke-static/range {p0 .. p18}, Lio/kamihama/magianative/CNChunkedDownload;->oneChunk(Ljava/lang/String;Ljava/io/File;JJLjava/util/concurrent/atomic/AtomicLongArray;IZLjava/io/File;JLjava/lang/String;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicBoolean;Lio/kamihama/magianative/CNChunkedDownload$Sink;)V

    return-void
.end method

.method private static deleteQuietly(Ljava/io/File;)V
    .locals 2

    .line 616
    if-eqz p0, :cond_0

    invoke-virtual {p0}, Ljava/io/File;->exists()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Ljava/io/File;->delete()Z

    move-result v0

    if-nez v0, :cond_0

    .line 617
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

    .line 619
    :cond_0
    return-void
.end method

.method public static download(Ljava/lang/String;Ljava/io/File;IZLio/kamihama/magianative/CNChunkedDownload$Probe;Lio/kamihama/magianative/CNChunkedDownload$Sink;)Lio/kamihama/magianative/CNChunkedDownload$Result;
    .locals 41
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 193
    move-object/from16 v1, p1

    move-object/from16 v2, p4

    move-object/from16 v3, p5

    const-string v4, "\u5df2\u53d6\u6d88"

    iget-wide v5, v2, Lio/kamihama/magianative/CNChunkedDownload$Probe;->total:J

    .line 194
    const-wide/16 v7, 0x0

    cmp-long v0, v5, v7

    if-lez v0, :cond_19

    .line 196
    invoke-static/range {p1 .. p1}, Lio/kamihama/magianative/CNChunkedDownload;->partFileFor(Ljava/io/File;)Ljava/io/File;

    move-result-object v9

    .line 197
    invoke-static/range {p1 .. p1}, Lio/kamihama/magianative/CNChunkedDownload;->metaFileFor(Ljava/io/File;)Ljava/io/File;

    move-result-object v10

    .line 199
    invoke-virtual {v9}, Ljava/io/File;->getParentFile()Ljava/io/File;

    move-result-object v0

    .line 200
    if-eqz v0, :cond_1

    invoke-virtual {v0}, Ljava/io/File;->isDirectory()Z

    move-result v11

    if-nez v11, :cond_1

    invoke-virtual {v0}, Ljava/io/File;->mkdirs()Z

    move-result v11

    if-nez v11, :cond_1

    invoke-virtual {v0}, Ljava/io/File;->isDirectory()Z

    move-result v11

    if-eqz v11, :cond_0

    goto :goto_0

    .line 201
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

    .line 205
    :cond_1
    :goto_0
    const/4 v11, 0x1

    move/from16 v0, p2

    if-ge v0, v11, :cond_2

    const/4 v0, 0x1

    .line 206
    :cond_2
    nop

    .line 207
    invoke-static {v10}, Lio/kamihama/magianative/CNChunkedDownload;->readResume(Ljava/io/File;)Lio/kamihama/magianative/CNChunkedDownload$Resume;

    move-result-object v12

    .line 208
    const-string v13, " chunks="

    const-string v14, "MagiaCNChunk"

    if-eqz v12, :cond_5

    .line 209
    iget-object v15, v2, Lio/kamihama/magianative/CNChunkedDownload$Probe;->etag:Ljava/lang/String;

    invoke-static {v12, v5, v6, v15, v9}, Lio/kamihama/magianative/CNChunkedDownload;->resumeRejectReason(Lio/kamihama/magianative/CNChunkedDownload$Resume;JLjava/lang/String;Ljava/io/File;)Ljava/lang/String;

    move-result-object v15

    .line 210
    if-nez v15, :cond_4

    .line 212
    iget v0, v12, Lio/kamihama/magianative/CNChunkedDownload$Resume;->chunks:I

    .line 213
    iget-object v12, v12, Lio/kamihama/magianative/CNChunkedDownload$Resume;->done:[J

    .line 214
    nop

    .line 215
    const/4 v15, 0x0

    :goto_1
    array-length v11, v12

    if-ge v15, v11, :cond_3

    aget-wide v18, v12, v15

    add-long v7, v7, v18

    add-int/lit8 v15, v15, 0x1

    goto :goto_1

    .line 216
    :cond_3
    new-instance v11, Ljava/lang/StringBuilder;

    invoke-direct {v11}, Ljava/lang/StringBuilder;-><init>()V

    const-string v15, "resume-accept file="

    invoke-virtual {v11, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v15

    invoke-virtual {v11, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v11

    const-string v15, " have="

    invoke-virtual {v11, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11, v7, v8}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v7

    const-string v8, "/"

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, v5, v6}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v14, v7}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 218
    move v7, v0

    goto :goto_2

    .line 219
    :cond_4
    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "resume-reject file="

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    const-string v8, " reason="

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v14, v7}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;)V

    .line 220
    invoke-static {v10}, Lio/kamihama/magianative/CNChunkedDownload;->deleteQuietly(Ljava/io/File;)V

    .line 224
    :cond_5
    move v7, v0

    const/4 v12, 0x0

    :goto_2
    move-object v8, v13

    move-object v11, v14

    int-to-long v13, v7

    add-long v18, v5, v13

    move-object v15, v4

    const-wide/16 v3, 0x1

    sub-long v18, v18, v3

    div-long v18, v18, v13

    .line 225
    new-array v13, v7, [J

    .line 226
    new-array v14, v7, [J

    .line 227
    new-instance v3, Ljava/util/concurrent/atomic/AtomicLongArray;

    invoke-direct {v3, v7}, Ljava/util/concurrent/atomic/AtomicLongArray;-><init>(I)V

    .line 228
    const/4 v0, 0x0

    :goto_3
    if-ge v0, v7, :cond_7

    .line 229
    move v4, v7

    move-object/from16 v22, v8

    int-to-long v7, v0

    mul-long v7, v7, v18

    aput-wide v7, v13, v0

    .line 230
    add-long v7, v7, v18

    const-wide/16 v20, 0x1

    sub-long v7, v7, v20

    move-object/from16 v23, v10

    move-object/from16 v24, v11

    sub-long v10, v5, v20

    invoke-static {v7, v8, v10, v11}, Ljava/lang/Math;->min(JJ)J

    move-result-wide v7

    aput-wide v7, v14, v0

    .line 231
    if-eqz v12, :cond_6

    aget-wide v7, v12, v0

    goto :goto_4

    :cond_6
    const-wide/16 v7, 0x0

    :goto_4
    invoke-virtual {v3, v0, v7, v8}, Ljava/util/concurrent/atomic/AtomicLongArray;->set(IJ)V

    .line 228
    add-int/lit8 v0, v0, 0x1

    move v7, v4

    move-object/from16 v8, v22

    move-object/from16 v10, v23

    move-object/from16 v11, v24

    goto :goto_3

    .line 236
    :cond_7
    move v4, v7

    move-object/from16 v22, v8

    move-object/from16 v23, v10

    move-object/from16 v24, v11

    const-wide/16 v20, 0x1

    new-instance v7, Ljava/io/RandomAccessFile;

    const-string v0, "rw"

    invoke-direct {v7, v9, v0}, Ljava/io/RandomAccessFile;-><init>(Ljava/io/File;Ljava/lang/String;)V

    .line 238
    :try_start_0
    invoke-virtual {v7}, Ljava/io/RandomAccessFile;->length()J

    move-result-wide v10

    cmp-long v0, v10, v5

    if-eqz v0, :cond_8

    invoke-virtual {v7, v5, v6}, Ljava/io/RandomAccessFile;->setLength(J)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    .line 240
    :cond_8
    :try_start_1
    invoke-virtual {v7}, Ljava/io/RandomAccessFile;->close()V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_5

    :catchall_0
    move-exception v0

    .line 241
    nop

    .line 242
    :goto_5
    iget-object v0, v2, Lio/kamihama/magianative/CNChunkedDownload$Probe;->etag:Ljava/lang/String;

    move-object/from16 v7, v23

    invoke-static {v7, v5, v6, v0, v3}, Lio/kamihama/magianative/CNChunkedDownload;->saveMeta(Ljava/io/File;JLjava/lang/String;Ljava/util/concurrent/atomic/AtomicLongArray;)V

    .line 244
    new-instance v0, Ljava/util/concurrent/atomic/AtomicLong;

    const-wide/16 v10, 0x0

    invoke-direct {v0, v10, v11}, Ljava/util/concurrent/atomic/AtomicLong;-><init>(J)V

    .line 245
    const/4 v8, 0x0

    :goto_6
    if-ge v8, v4, :cond_9

    invoke-virtual {v3, v8}, Ljava/util/concurrent/atomic/AtomicLongArray;->get(I)J

    move-result-wide v10

    invoke-virtual {v0, v10, v11}, Ljava/util/concurrent/atomic/AtomicLong;->addAndGet(J)J

    add-int/lit8 v8, v8, 0x1

    goto :goto_6

    .line 247
    :cond_9
    move-object/from16 v8, p5

    move-wide/from16 v10, v20

    if-eqz v8, :cond_a

    .line 248
    invoke-interface {v8, v5, v6}, Lio/kamihama/magianative/CNChunkedDownload$Sink;->onTotal(J)V

    .line 249
    invoke-virtual {v0}, Ljava/util/concurrent/atomic/AtomicLong;->get()J

    move-result-wide v10

    invoke-interface {v8, v10, v11, v5, v6}, Lio/kamihama/magianative/CNChunkedDownload$Sink;->onProgress(JJ)V

    .line 254
    :cond_a
    invoke-virtual {v0}, Ljava/util/concurrent/atomic/AtomicLong;->get()J

    move-result-wide v10

    cmp-long v12, v10, v5

    if-ltz v12, :cond_c

    .line 255
    invoke-static {v9, v1}, Lio/kamihama/magianative/CNChunkedDownload;->promote(Ljava/io/File;Ljava/io/File;)V

    .line 256
    invoke-static {v7}, Lio/kamihama/magianative/CNChunkedDownload;->deleteQuietly(Ljava/io/File;)V

    .line 257
    if-eqz v8, :cond_b

    invoke-interface {v8, v5, v6, v5, v6}, Lio/kamihama/magianative/CNChunkedDownload$Sink;->onProgress(JJ)V

    .line 258
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

    move-object/from16 v10, v24

    invoke-static {v10, v0}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 259
    new-instance v0, Lio/kamihama/magianative/CNChunkedDownload$Result;

    iget-object v1, v2, Lio/kamihama/magianative/CNChunkedDownload$Probe;->etag:Ljava/lang/String;

    invoke-direct {v0, v5, v6, v1}, Lio/kamihama/magianative/CNChunkedDownload$Result;-><init>(JLjava/lang/String;)V

    return-object v0

    .line 262
    :cond_c
    move-object/from16 v10, v24

    new-instance v11, Ljava/util/concurrent/atomic/AtomicReference;

    const/4 v12, 0x0

    invoke-direct {v11, v12}, Ljava/util/concurrent/atomic/AtomicReference;-><init>(Ljava/lang/Object;)V

    .line 263
    new-instance v12, Ljava/util/concurrent/atomic/AtomicBoolean;

    move-object/from16 v18, v15

    const/4 v15, 0x0

    invoke-direct {v12, v15}, Ljava/util/concurrent/atomic/AtomicBoolean;-><init>(Z)V

    .line 264
    new-instance v15, Ljava/util/concurrent/atomic/AtomicLong;

    move-object/from16 v19, v11

    invoke-static {}, Ljava/lang/System;->nanoTime()J

    move-result-wide v10

    invoke-direct {v15, v10, v11}, Ljava/util/concurrent/atomic/AtomicLong;-><init>(J)V

    .line 265
    new-instance v10, Ljava/util/concurrent/atomic/AtomicLong;

    move-object/from16 v23, v12

    invoke-static {}, Ljava/lang/System;->nanoTime()J

    move-result-wide v11

    invoke-direct {v10, v11, v12}, Ljava/util/concurrent/atomic/AtomicLong;-><init>(J)V

    .line 266
    new-instance v11, Ljava/util/concurrent/atomic/AtomicLong;

    const-wide/16 v1, 0x0

    invoke-direct {v11, v1, v2}, Ljava/util/concurrent/atomic/AtomicLong;-><init>(J)V

    .line 268
    new-instance v1, Lio/kamihama/magianative/CNChunkedDownload$ChunkThreadFactory;

    const/4 v2, 0x0

    invoke-direct {v1, v2}, Lio/kamihama/magianative/CNChunkedDownload$ChunkThreadFactory;-><init>(Lio/kamihama/magianative/CNChunkedDownload$1;)V

    invoke-static {v4, v1}, Ljava/util/concurrent/Executors;->newFixedThreadPool(ILjava/util/concurrent/ThreadFactory;)Ljava/util/concurrent/ExecutorService;

    move-result-object v1

    .line 269
    new-instance v12, Ljava/util/concurrent/CountDownLatch;

    invoke-direct {v12, v4}, Ljava/util/concurrent/CountDownLatch;-><init>(I)V

    .line 271
    const/4 v2, 0x0

    :goto_7
    if-ge v2, v4, :cond_d

    .line 272
    move/from16 v25, v4

    new-instance v4, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;

    move-object/from16 v26, v1

    const/4 v1, 0x0

    invoke-direct {v4, v1}, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;-><init>(Lio/kamihama/magianative/CNChunkedDownload$1;)V

    .line 273
    move-object/from16 v1, p0

    iput-object v1, v4, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->url:Ljava/lang/String;

    iput-object v9, v4, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->part:Ljava/io/File;

    .line 274
    move-object/from16 v27, v9

    aget-wide v8, v13, v2

    iput-wide v8, v4, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->start:J

    aget-wide v8, v14, v2

    iput-wide v8, v4, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->end:J

    .line 275
    iput-object v3, v4, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->done:Ljava/util/concurrent/atomic/AtomicLongArray;

    iput v2, v4, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->idx:I

    .line 276
    move/from16 v8, p3

    iput-boolean v8, v4, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->direct:Z

    iput-object v7, v4, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->meta:Ljava/io/File;

    .line 277
    iput-wide v5, v4, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->total:J

    move-object/from16 v9, p4

    iget-object v1, v9, Lio/kamihama/magianative/CNChunkedDownload$Probe;->etag:Ljava/lang/String;

    iput-object v1, v4, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->etag:Ljava/lang/String;

    .line 278
    iput-object v0, v4, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->totalDone:Ljava/util/concurrent/atomic/AtomicLong;

    .line 279
    iput-object v10, v4, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->windowStart:Ljava/util/concurrent/atomic/AtomicLong;

    iput-object v11, v4, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->windowBytes:Ljava/util/concurrent/atomic/AtomicLong;

    .line 280
    iput-object v15, v4, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->lastMoveNs:Ljava/util/concurrent/atomic/AtomicLong;

    move-object/from16 v1, v23

    iput-object v1, v4, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->abort:Ljava/util/concurrent/atomic/AtomicBoolean;

    .line 281
    move-object/from16 v8, p5

    iput-object v8, v4, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->sink:Lio/kamihama/magianative/CNChunkedDownload$Sink;

    move-object/from16 v23, v10

    move-object/from16 v10, v19

    iput-object v10, v4, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->firstErr:Ljava/util/concurrent/atomic/AtomicReference;

    .line 282
    iput-object v12, v4, Lio/kamihama/magianative/CNChunkedDownload$ChunkTask;->latch:Ljava/util/concurrent/CountDownLatch;

    .line 283
    move-object/from16 v19, v11

    move-object/from16 v11, v26

    invoke-interface {v11, v4}, Ljava/util/concurrent/ExecutorService;->submit(Ljava/lang/Runnable;)Ljava/util/concurrent/Future;

    .line 271
    add-int/lit8 v2, v2, 0x1

    move/from16 v4, v25

    move-object/from16 v9, v27

    move-object/from16 v40, v23

    move-object/from16 v23, v1

    move-object v1, v11

    move-object/from16 v11, v19

    move-object/from16 v19, v10

    move-object/from16 v10, v40

    goto :goto_7

    .line 287
    :cond_d
    move-object v11, v1

    move/from16 v25, v4

    move-object/from16 v27, v9

    move-object/from16 v10, v19

    move-object/from16 v1, v23

    move-object/from16 v9, p4

    sget-object v2, Ljava/util/concurrent/TimeUnit;->SECONDS:Ljava/util/concurrent/TimeUnit;

    invoke-static {}, Lio/kamihama/magianative/CNMirrors;->stallSeconds()I

    move-result v4

    int-to-long v13, v4

    invoke-virtual {v2, v13, v14}, Ljava/util/concurrent/TimeUnit;->toNanos(J)J

    move-result-wide v13

    .line 288
    invoke-static {}, Lio/kamihama/magianative/CNMirrors;->minSpeedKbps()I

    move-result v2

    move-object v4, v3

    int-to-long v2, v2

    const-wide/16 v28, 0x400

    mul-long v2, v2, v28

    .line 289
    invoke-static {}, Ljava/lang/System;->nanoTime()J

    move-result-wide v30

    .line 290
    invoke-virtual {v0}, Ljava/util/concurrent/atomic/AtomicLong;->get()J

    move-result-wide v32

    .line 292
    :goto_8
    move-object/from16 v19, v4

    :try_start_2
    sget-object v4, Ljava/util/concurrent/TimeUnit;->SECONDS:Ljava/util/concurrent/TimeUnit;
    :try_end_2
    .catch Ljava/lang/InterruptedException; {:try_start_2 .. :try_end_2} :catch_3

    move-wide/from16 v34, v5

    const-wide/16 v5, 0x1

    :try_start_3
    invoke-virtual {v12, v5, v6, v4}, Ljava/util/concurrent/CountDownLatch;->await(JLjava/util/concurrent/TimeUnit;)Z

    move-result v4

    if-nez v4, :cond_13

    .line 293
    invoke-static {}, Ljava/lang/System;->nanoTime()J

    move-result-wide v20

    .line 294
    if-eqz v8, :cond_e

    invoke-interface/range {p5 .. p5}, Lio/kamihama/magianative/CNChunkedDownload$Sink;->isCancelled()Z

    move-result v4

    if-eqz v4, :cond_e

    .line 295
    const/4 v2, 0x1

    invoke-virtual {v1, v2}, Ljava/util/concurrent/atomic/AtomicBoolean;->set(Z)V

    .line 296
    new-instance v0, Ljava/io/IOException;
    :try_end_3
    .catch Ljava/lang/InterruptedException; {:try_start_3 .. :try_end_3} :catch_2

    move-object/from16 v4, v18

    :try_start_4
    invoke-direct {v0, v4}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    const/4 v2, 0x0

    invoke-static {v10, v2, v0}, Lio/kamihama/magianative/CNChunkedDownload$$ExternalSyntheticBackportWithForwarding0;->m(Ljava/util/concurrent/atomic/AtomicReference;Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 297
    const-wide/16 v16, 0x0

    goto/16 :goto_a

    .line 294
    :cond_e
    move-object/from16 v4, v18

    .line 299
    invoke-virtual {v15}, Ljava/util/concurrent/atomic/AtomicLong;->get()J

    move-result-wide v36

    sub-long v36, v20, v36

    cmp-long v18, v36, v13

    if-lez v18, :cond_f

    .line 300
    const/4 v2, 0x1

    invoke-virtual {v1, v2}, Ljava/util/concurrent/atomic/AtomicBoolean;->set(Z)V

    .line 301
    new-instance v0, Ljava/io/IOException;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "\u7ebf\u8def\u505c\u6ede\uff1a"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    .line 302
    invoke-static {}, Lio/kamihama/magianative/CNMirrors;->stallSeconds()I

    move-result v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " \u79d2\u5185\u6ca1\u6709\u4efb\u4f55\u6570\u636e"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-direct {v0, v2}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    .line 301
    const/4 v2, 0x0

    invoke-static {v10, v2, v0}, Lio/kamihama/magianative/CNChunkedDownload$$ExternalSyntheticBackportWithForwarding0;->m(Ljava/util/concurrent/atomic/AtomicReference;Ljava/lang/Object;Ljava/lang/Object;)Z
    :try_end_4
    .catch Ljava/lang/InterruptedException; {:try_start_4 .. :try_end_4} :catch_1

    .line 303
    const-wide/16 v16, 0x0

    goto/16 :goto_a

    .line 305
    :cond_f
    sub-long v5, v20, v30

    .line 306
    const-wide/16 v16, 0x0

    cmp-long v18, v2, v16

    if-lez v18, :cond_11

    move-object/from16 v18, v12

    :try_start_5
    sget-object v12, Ljava/util/concurrent/TimeUnit;->SECONDS:Ljava/util/concurrent/TimeUnit;

    move-wide/from16 v38, v13

    const-wide/16 v13, 0xa

    invoke-virtual {v12, v13, v14}, Ljava/util/concurrent/TimeUnit;->toNanos(J)J

    move-result-wide v12

    cmp-long v14, v5, v12

    if-ltz v14, :cond_12

    .line 307
    invoke-virtual {v0}, Ljava/util/concurrent/atomic/AtomicLong;->get()J

    move-result-wide v12

    sub-long v12, v12, v32

    .line 308
    long-to-double v12, v12

    long-to-double v5, v5

    const-wide v30, 0x41cdcd6500000000L    # 1.0E9

    div-double v5, v5, v30

    div-double/2addr v12, v5

    double-to-long v5, v12

    .line 309
    cmp-long v12, v5, v2

    if-gez v12, :cond_10

    .line 310
    const/4 v2, 0x1

    invoke-virtual {v1, v2}, Ljava/util/concurrent/atomic/AtomicBoolean;->set(Z)V

    .line 311
    new-instance v0, Ljava/io/IOException;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "\u7ebf\u8def\u8fc7\u6162\uff1a"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    div-long v5, v5, v28

    invoke-virtual {v2, v5, v6}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " KB/s < "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    .line 313
    invoke-static {}, Lio/kamihama/magianative/CNMirrors;->minSpeedKbps()I

    move-result v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " KB/s"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-direct {v0, v2}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    .line 311
    const/4 v2, 0x0

    invoke-static {v10, v2, v0}, Lio/kamihama/magianative/CNChunkedDownload$$ExternalSyntheticBackportWithForwarding0;->m(Ljava/util/concurrent/atomic/AtomicReference;Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 314
    goto :goto_a

    .line 316
    :cond_10
    nop

    .line 317
    invoke-virtual {v0}, Ljava/util/concurrent/atomic/AtomicLong;->get()J

    move-result-wide v5
    :try_end_5
    .catch Ljava/lang/InterruptedException; {:try_start_5 .. :try_end_5} :catch_0

    move-wide/from16 v32, v5

    move-wide/from16 v30, v20

    goto :goto_9

    .line 320
    :catch_0
    move-exception v0

    goto :goto_d

    .line 306
    :cond_11
    move-object/from16 v18, v12

    move-wide/from16 v38, v13

    .line 319
    :cond_12
    :goto_9
    move-object/from16 v12, v18

    move-wide/from16 v5, v34

    move-wide/from16 v13, v38

    move-object/from16 v18, v4

    move-object/from16 v4, v19

    goto/16 :goto_8

    .line 320
    :catch_1
    move-exception v0

    goto :goto_c

    .line 292
    :cond_13
    const-wide/16 v16, 0x0

    .line 324
    :goto_a
    goto :goto_e

    .line 320
    :catch_2
    move-exception v0

    goto :goto_b

    :catch_3
    move-exception v0

    move-wide/from16 v34, v5

    :goto_b
    move-object/from16 v4, v18

    :goto_c
    const-wide/16 v16, 0x0

    .line 321
    :goto_d
    invoke-static {}, Ljava/lang/Thread;->currentThread()Ljava/lang/Thread;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Thread;->interrupt()V

    .line 322
    const/4 v2, 0x1

    invoke-virtual {v1, v2}, Ljava/util/concurrent/atomic/AtomicBoolean;->set(Z)V

    .line 323
    new-instance v0, Ljava/io/IOException;

    invoke-direct {v0, v4}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    const/4 v1, 0x0

    invoke-static {v10, v1, v0}, Lio/kamihama/magianative/CNChunkedDownload$$ExternalSyntheticBackportWithForwarding0;->m(Ljava/util/concurrent/atomic/AtomicReference;Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 326
    :goto_e
    invoke-interface {v11}, Ljava/util/concurrent/ExecutorService;->shutdownNow()Ljava/util/List;

    .line 327
    :try_start_6
    sget-object v0, Ljava/util/concurrent/TimeUnit;->SECONDS:Ljava/util/concurrent/TimeUnit;

    const-wide/16 v1, 0x5

    invoke-interface {v11, v1, v2, v0}, Ljava/util/concurrent/ExecutorService;->awaitTermination(JLjava/util/concurrent/TimeUnit;)Z
    :try_end_6
    .catch Ljava/lang/InterruptedException; {:try_start_6 .. :try_end_6} :catch_4

    goto :goto_f

    :catch_4
    move-exception v0

    .line 329
    :goto_f
    iget-object v0, v9, Lio/kamihama/magianative/CNChunkedDownload$Probe;->etag:Ljava/lang/String;

    move-object/from16 v3, v19

    move-wide/from16 v1, v34

    invoke-static {v7, v1, v2, v0, v3}, Lio/kamihama/magianative/CNChunkedDownload;->saveMeta(Ljava/io/File;JLjava/lang/String;Ljava/util/concurrent/atomic/AtomicLongArray;)V

    .line 331
    invoke-virtual {v10}, Ljava/util/concurrent/atomic/AtomicReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/io/IOException;

    .line 332
    if-nez v0, :cond_18

    .line 336
    nop

    .line 337
    move-wide/from16 v4, v16

    const/4 v15, 0x0

    :goto_10
    move/from16 v6, v25

    if-ge v15, v6, :cond_14

    invoke-virtual {v3, v15}, Ljava/util/concurrent/atomic/AtomicLongArray;->get(I)J

    move-result-wide v10

    add-long/2addr v4, v10

    add-int/lit8 v15, v15, 0x1

    move/from16 v25, v6

    goto :goto_10

    .line 338
    :cond_14
    const-string v0, " / "

    cmp-long v3, v4, v1

    if-nez v3, :cond_17

    .line 341
    invoke-virtual/range {v27 .. v27}, Ljava/io/File;->length()J

    move-result-wide v3

    .line 342
    cmp-long v5, v3, v1

    if-nez v5, :cond_16

    .line 346
    move-object/from16 v3, p1

    move-object/from16 v4, v27

    invoke-static {v4, v3}, Lio/kamihama/magianative/CNChunkedDownload;->promote(Ljava/io/File;Ljava/io/File;)V

    .line 347
    invoke-static {v7}, Lio/kamihama/magianative/CNChunkedDownload;->deleteQuietly(Ljava/io/File;)V

    .line 348
    if-eqz v8, :cond_15

    .line 349
    invoke-interface {v8, v1, v2, v1, v2}, Lio/kamihama/magianative/CNChunkedDownload$Sink;->onProgress(JJ)V

    .line 350
    const/4 v0, 0x0

    invoke-interface {v8, v0}, Lio/kamihama/magianative/CNChunkedDownload$Sink;->onSpeed(F)V

    .line 352
    :cond_15
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "\u5206\u7247\u4e0b\u8f7d\u5b8c\u6210 file="

    invoke-virtual {v0, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v3, " bytes="

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v1, v2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v0

    move-object/from16 v3, v22

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v6}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    move-object/from16 v3, v24

    invoke-static {v3, v0}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 354
    new-instance v0, Lio/kamihama/magianative/CNChunkedDownload$Result;

    iget-object v3, v9, Lio/kamihama/magianative/CNChunkedDownload$Probe;->etag:Ljava/lang/String;

    invoke-direct {v0, v1, v2, v3}, Lio/kamihama/magianative/CNChunkedDownload$Result;-><init>(JLjava/lang/String;)V

    return-object v0

    .line 343
    :cond_16
    new-instance v5, Ljava/io/IOException;

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "\u4e34\u65f6\u6587\u4ef6\u5927\u5c0f\u5f02\u5e38: "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v3, v4}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v1, v2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {v5, v0}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v5

    .line 339
    :cond_17
    new-instance v3, Ljava/io/IOException;

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "\u4e0b\u8f7d\u4e0d\u5b8c\u6574: \u5df2\u5199 "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v4, v5}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v1, v2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {v3, v0}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v3

    .line 332
    :cond_18
    throw v0

    .line 240
    :catchall_1
    move-exception v0

    move-object v1, v0

    :try_start_7
    invoke-virtual {v7}, Ljava/io/RandomAccessFile;->close()V
    :try_end_7
    .catchall {:try_start_7 .. :try_end_7} :catchall_2

    goto :goto_11

    :catchall_2
    move-exception v0

    .line 241
    :goto_11
    throw v1

    .line 194
    :cond_19
    new-instance v0, Ljava/io/IOException;

    const-string v1, "\u672a\u77e5\u7684\u6587\u4ef6\u957f\u5ea6"

    invoke-direct {v0, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v0
.end method

.method public static metaFileFor(Ljava/io/File;)Ljava/io/File;
    .locals 2

    .line 179
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

.method private static oneChunk(Ljava/lang/String;Ljava/io/File;JJLjava/util/concurrent/atomic/AtomicLongArray;IZLjava/io/File;JLjava/lang/String;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicLong;Ljava/util/concurrent/atomic/AtomicBoolean;Lio/kamihama/magianative/CNChunkedDownload$Sink;)V
    .locals 25
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 445
    move-wide/from16 v0, p4

    move-object/from16 v2, p6

    move/from16 v3, p7

    move-object/from16 v4, p9

    move-wide/from16 v5, p10

    move-object/from16 v7, p12

    move-object/from16 v8, p15

    move-object/from16 v9, p18

    sub-long v10, v0, p2

    const-wide/16 v12, 0x1

    add-long/2addr v10, v12

    .line 446
    invoke-virtual/range {p6 .. p7}, Ljava/util/concurrent/atomic/AtomicLongArray;->get(I)J

    move-result-wide v12

    .line 447
    cmp-long v14, v12, v10

    if-ltz v14, :cond_0

    return-void

    .line 449
    :cond_0
    add-long v12, p2, v12

    .line 450
    move-object/from16 v14, p0

    move/from16 v15, p8

    invoke-static {v14, v15}, Lio/kamihama/magianative/CNChunkedDownload;->open(Ljava/lang/String;Z)Ljava/net/HttpURLConnection;

    move-result-object v14

    .line 451
    const-string v15, "GET"

    invoke-virtual {v14, v15}, Ljava/net/HttpURLConnection;->setRequestMethod(Ljava/lang/String;)V

    .line 452
    new-instance v15, Ljava/lang/StringBuilder;

    invoke-direct {v15}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "bytes="

    invoke-virtual {v15, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v12, v13}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v15, "-"

    invoke-virtual {v4, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v0, v1}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "Range"

    invoke-virtual {v14, v1, v0}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 453
    if-eqz v7, :cond_1

    invoke-virtual/range {p12 .. p12}, Ljava/lang/String;->length()I

    move-result v0

    if-lez v0, :cond_1

    .line 455
    const-string v0, "If-Range"

    invoke-virtual {v14, v0, v7}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 457
    :cond_1
    invoke-virtual {v14}, Ljava/net/HttpURLConnection;->connect()V

    .line 458
    invoke-virtual {v14}, Ljava/net/HttpURLConnection;->getResponseCode()I

    move-result v1

    .line 459
    const/16 v0, 0xce

    const-string v4, "\u5206\u7247 "

    if-ne v1, v0, :cond_12

    .line 467
    const-string v0, "Content-Range"

    invoke-virtual {v14, v0}, Ljava/net/HttpURLConnection;->getHeaderField(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lio/kamihama/magianative/CNChunkedDownload;->rangeStart(Ljava/lang/String;)J

    move-result-wide v5

    .line 468
    const-wide/16 v0, 0x0

    cmp-long v15, v5, v0

    if-ltz v15, :cond_3

    cmp-long v15, v5, v12

    if-nez v15, :cond_2

    goto :goto_1

    .line 469
    :cond_2
    :try_start_0
    invoke-virtual {v14}, Ljava/net/HttpURLConnection;->disconnect()V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    goto :goto_0

    :catchall_0
    move-exception v0

    .line 470
    :goto_0
    new-instance v0, Ljava/io/IOException;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, " Content-Range \u8d77\u70b9\u4e0d\u7b26: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v5, v6}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, " != "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v12, v13}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v0

    .line 474
    :cond_3
    :goto_1
    nop

    .line 475
    nop

    .line 477
    :try_start_1
    new-instance v6, Ljava/io/BufferedInputStream;

    invoke-virtual {v14}, Ljava/net/HttpURLConnection;->getInputStream()Ljava/io/InputStream;

    move-result-object v15

    const/high16 v5, 0x10000

    invoke-direct {v6, v15, v5}, Ljava/io/BufferedInputStream;-><init>(Ljava/io/InputStream;I)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_d

    .line 478
    :try_start_2
    new-instance v5, Ljava/io/RandomAccessFile;

    const-string v15, "rw"

    move-object/from16 v0, p1

    invoke-direct {v5, v0, v15}, Ljava/io/RandomAccessFile;-><init>(Ljava/io/File;Ljava/lang/String;)V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_c

    .line 479
    :try_start_3
    invoke-virtual {v5, v12, v13}, Ljava/io/RandomAccessFile;->seek(J)V

    .line 480
    const v0, 0x8000

    new-array v0, v0, [B

    .line 481
    invoke-static {}, Ljava/lang/System;->nanoTime()J

    move-result-wide v12

    .line 483
    :goto_2
    invoke-virtual {v6, v0}, Ljava/io/InputStream;->read([B)I

    move-result v1

    const/4 v15, -0x1

    if-eq v1, v15, :cond_e

    .line 484
    if-nez v1, :cond_4

    goto :goto_2

    .line 485
    :cond_4
    invoke-virtual/range {p17 .. p17}, Ljava/util/concurrent/atomic/AtomicBoolean;->get()Z

    move-result v15
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_b

    if-nez v15, :cond_d

    .line 486
    if-eqz v9, :cond_6

    :try_start_4
    invoke-interface/range {p18 .. p18}, Lio/kamihama/magianative/CNChunkedDownload$Sink;->isCancelled()Z

    move-result v15

    if-nez v15, :cond_5

    goto :goto_3

    :cond_5
    new-instance v0, Ljava/io/IOException;

    const-string v1, "\u5df2\u53d6\u6d88"

    invoke-direct {v0, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v0
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_1

    .line 523
    :catchall_1
    move-exception v0

    move-object/from16 v3, p9

    move-object v4, v0

    move-object/from16 v18, v5

    move-object v1, v6

    move-object/from16 p8, v14

    move-wide/from16 v5, p10

    goto/16 :goto_f

    .line 489
    :cond_6
    :goto_3
    :try_start_5
    invoke-virtual/range {p6 .. p7}, Ljava/util/concurrent/atomic/AtomicLongArray;->get(I)J

    move-result-wide v15
    :try_end_5
    .catchall {:try_start_5 .. :try_end_5} :catchall_b

    move-object/from16 p8, v14

    sub-long v14, v10, v15

    .line 490
    move-wide/from16 v16, v10

    int-to-long v10, v1

    :try_start_6
    invoke-static {v10, v11, v14, v15}, Ljava/lang/Math;->min(JJ)J

    move-result-wide v10

    long-to-int v1, v10

    .line 491
    if-gtz v1, :cond_7

    move-object/from16 v3, p9

    move-object/from16 p4, v4

    move-object/from16 v18, v5

    move-object/from16 p5, v6

    move-wide/from16 v5, p10

    goto/16 :goto_7

    .line 492
    :cond_7
    const/4 v10, 0x0

    invoke-virtual {v5, v0, v10, v1}, Ljava/io/RandomAccessFile;->write([BII)V

    .line 494
    int-to-long v10, v1

    invoke-virtual {v2, v3, v10, v11}, Ljava/util/concurrent/atomic/AtomicLongArray;->addAndGet(IJ)J

    move-result-wide v14

    .line 495
    move-object/from16 v1, p13

    move-object/from16 p4, v4

    invoke-virtual {v1, v10, v11}, Ljava/util/concurrent/atomic/AtomicLong;->addAndGet(J)J

    move-result-wide v3

    .line 496
    move-object/from16 p0, v0

    invoke-static {}, Ljava/lang/System;->nanoTime()J

    move-result-wide v0
    :try_end_6
    .catchall {:try_start_6 .. :try_end_6} :catchall_6

    .line 497
    move-object/from16 p5, v6

    move-object/from16 v6, p16

    :try_start_7
    invoke-virtual {v6, v0, v1}, Ljava/util/concurrent/atomic/AtomicLong;->set(J)V

    .line 499
    invoke-virtual {v8, v10, v11}, Ljava/util/concurrent/atomic/AtomicLong;->addAndGet(J)J

    move-result-wide v10
    :try_end_7
    .catchall {:try_start_7 .. :try_end_7} :catchall_5

    .line 500
    move-object/from16 v18, v5

    :try_start_8
    invoke-virtual/range {p14 .. p14}, Ljava/util/concurrent/atomic/AtomicLong;->get()J

    move-result-wide v5

    .line 501
    sub-long v19, v0, v5

    const-wide/32 v21, 0xf4240

    move-wide/from16 v23, v14

    div-long v14, v19, v21
    :try_end_8
    .catchall {:try_start_8 .. :try_end_8} :catchall_4

    .line 502
    const-wide/16 v19, 0x1f4

    cmp-long v21, v14, v19

    if-ltz v21, :cond_a

    move-object/from16 v2, p14

    :try_start_9
    invoke-virtual {v2, v5, v6, v0, v1}, Ljava/util/concurrent/atomic/AtomicLong;->compareAndSet(JJ)Z

    move-result v5

    if-eqz v5, :cond_9

    .line 503
    const-wide/16 v5, 0x0

    invoke-virtual {v8, v5, v6}, Ljava/util/concurrent/atomic/AtomicLong;->set(J)V
    :try_end_9
    .catchall {:try_start_9 .. :try_end_9} :catchall_3

    .line 504
    if-eqz v9, :cond_8

    .line 505
    move-wide/from16 v5, p10

    :try_start_a
    invoke-interface {v9, v3, v4, v5, v6}, Lio/kamihama/magianative/CNChunkedDownload$Sink;->onProgress(JJ)V

    .line 506
    long-to-double v3, v10

    const-wide v10, 0x408f400000000000L    # 1000.0

    mul-double v3, v3, v10

    long-to-double v10, v14

    div-double/2addr v3, v10

    const-wide v10, 0x412e848000000000L    # 1000000.0

    div-double/2addr v3, v10

    double-to-float v3, v3

    .line 507
    invoke-interface {v9, v3}, Lio/kamihama/magianative/CNChunkedDownload$Sink;->onSpeed(F)V
    :try_end_a
    .catchall {:try_start_a .. :try_end_a} :catchall_2

    goto :goto_5

    .line 523
    :catchall_2
    move-exception v0

    goto :goto_4

    .line 504
    :cond_8
    move-wide/from16 v5, p10

    goto :goto_5

    .line 502
    :cond_9
    move-wide/from16 v5, p10

    goto :goto_5

    .line 523
    :catchall_3
    move-exception v0

    move-wide/from16 v5, p10

    :goto_4
    move-object/from16 v1, p5

    move-object/from16 v2, p6

    move-object/from16 v3, p9

    goto/16 :goto_d

    .line 502
    :cond_a
    move-wide/from16 v5, p10

    move-object/from16 v2, p14

    .line 510
    :goto_5
    sub-long v3, v0, v12

    const-wide/32 v10, 0x77359400

    cmp-long v14, v3, v10

    if-lez v14, :cond_b

    .line 511
    move-object/from16 v2, p6

    move-object/from16 v3, p9

    :try_start_b
    invoke-static {v3, v5, v6, v7, v2}, Lio/kamihama/magianative/CNChunkedDownload;->saveMeta(Ljava/io/File;JLjava/lang/String;Ljava/util/concurrent/atomic/AtomicLongArray;)V

    .line 512
    move-wide v12, v0

    goto :goto_6

    .line 510
    :cond_b
    move-object/from16 v2, p6

    move-object/from16 v3, p9

    .line 514
    :goto_6
    cmp-long v0, v23, v16

    if-ltz v0, :cond_c

    goto :goto_7

    .line 515
    :cond_c
    move-object/from16 v0, p0

    move-object/from16 v4, p4

    move-object/from16 v6, p5

    move/from16 v3, p7

    move-object/from16 v14, p8

    move-wide/from16 v10, v16

    move-object/from16 v5, v18

    goto/16 :goto_2

    .line 523
    :catchall_4
    move-exception v0

    move-object/from16 v3, p9

    goto/16 :goto_b

    :catchall_5
    move-exception v0

    move-object/from16 v3, p9

    move-object/from16 v18, v5

    goto/16 :goto_b

    :catchall_6
    move-exception v0

    move-object/from16 v3, p9

    move-object/from16 v18, v5

    move-object/from16 p5, v6

    goto/16 :goto_b

    .line 485
    :cond_d
    move-object/from16 v3, p9

    move-object/from16 v18, v5

    move-object/from16 p5, v6

    move-object/from16 p8, v14

    move-wide/from16 v5, p10

    new-instance v0, Ljava/io/IOException;

    const-string v1, "\u5df2\u4e2d\u65ad"

    invoke-direct {v0, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v0

    .line 483
    :cond_e
    move-object/from16 v3, p9

    move-object/from16 p4, v4

    move-object/from16 v18, v5

    move-object/from16 p5, v6

    move-wide/from16 v16, v10

    move-object/from16 p8, v14

    move-wide/from16 v5, p10

    .line 518
    :goto_7
    invoke-virtual/range {p6 .. p7}, Ljava/util/concurrent/atomic/AtomicLongArray;->get(I)J

    move-result-wide v0
    :try_end_b
    .catchall {:try_start_b .. :try_end_b} :catchall_a

    .line 519
    cmp-long v4, v0, v16

    if-ltz v4, :cond_f

    .line 523
    invoke-static {v3, v5, v6, v7, v2}, Lio/kamihama/magianative/CNChunkedDownload;->saveMeta(Ljava/io/File;JLjava/lang/String;Ljava/util/concurrent/atomic/AtomicLongArray;)V

    .line 524
    :try_start_c
    invoke-virtual/range {v18 .. v18}, Ljava/io/RandomAccessFile;->close()V
    :try_end_c
    .catchall {:try_start_c .. :try_end_c} :catchall_7

    goto :goto_8

    :catchall_7
    move-exception v0

    .line 525
    :goto_8
    :try_start_d
    invoke-virtual/range {p5 .. p5}, Ljava/io/InputStream;->close()V
    :try_end_d
    .catchall {:try_start_d .. :try_end_d} :catchall_8

    goto :goto_9

    :catchall_8
    move-exception v0

    .line 526
    :goto_9
    :try_start_e
    invoke-virtual/range {p8 .. p8}, Ljava/net/HttpURLConnection;->disconnect()V
    :try_end_e
    .catchall {:try_start_e .. :try_end_e} :catchall_9

    goto :goto_a

    :catchall_9
    move-exception v0

    .line 527
    nop

    .line 528
    :goto_a
    return-void

    .line 520
    :cond_f
    :try_start_f
    new-instance v4, Ljava/io/IOException;

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    move-object/from16 v9, p4

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    move/from16 v10, p7

    invoke-virtual {v8, v10}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v8

    const-string v9, " \u77ed\u8bfb: "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8, v0, v1}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " / "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    move-wide/from16 v10, v16

    invoke-virtual {v0, v10, v11}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {v4, v0}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v4
    :try_end_f
    .catchall {:try_start_f .. :try_end_f} :catchall_a

    .line 523
    :catchall_a
    move-exception v0

    goto :goto_c

    :catchall_b
    move-exception v0

    move-object/from16 v3, p9

    move-object/from16 v18, v5

    move-object/from16 p5, v6

    move-object/from16 p8, v14

    :goto_b
    move-wide/from16 v5, p10

    :goto_c
    move-object/from16 v1, p5

    :goto_d
    move-object v4, v0

    goto :goto_f

    :catchall_c
    move-exception v0

    move-object/from16 v3, p9

    move-object/from16 p5, v6

    move-object/from16 p8, v14

    move-wide/from16 v5, p10

    move-object/from16 v1, p5

    move-object v4, v0

    goto :goto_e

    :catchall_d
    move-exception v0

    move-object/from16 v3, p9

    move-wide/from16 v5, p10

    move-object/from16 p8, v14

    move-object v4, v0

    const/4 v1, 0x0

    :goto_e
    const/16 v18, 0x0

    :goto_f
    invoke-static {v3, v5, v6, v7, v2}, Lio/kamihama/magianative/CNChunkedDownload;->saveMeta(Ljava/io/File;JLjava/lang/String;Ljava/util/concurrent/atomic/AtomicLongArray;)V

    .line 524
    if-eqz v18, :cond_10

    :try_start_10
    invoke-virtual/range {v18 .. v18}, Ljava/io/RandomAccessFile;->close()V
    :try_end_10
    .catchall {:try_start_10 .. :try_end_10} :catchall_e

    goto :goto_10

    :catchall_e
    move-exception v0

    .line 525
    :cond_10
    :goto_10
    if-eqz v1, :cond_11

    :try_start_11
    invoke-virtual {v1}, Ljava/io/InputStream;->close()V
    :try_end_11
    .catchall {:try_start_11 .. :try_end_11} :catchall_f

    goto :goto_11

    :catchall_f
    move-exception v0

    .line 526
    :cond_11
    :goto_11
    :try_start_12
    invoke-virtual/range {p8 .. p8}, Ljava/net/HttpURLConnection;->disconnect()V
    :try_end_12
    .catchall {:try_start_12 .. :try_end_12} :catchall_10

    goto :goto_12

    :catchall_10
    move-exception v0

    .line 527
    :goto_12
    throw v4

    .line 460
    :cond_12
    move v10, v3

    move-object v9, v4

    move-object/from16 p8, v14

    :try_start_13
    invoke-virtual/range {p8 .. p8}, Ljava/net/HttpURLConnection;->disconnect()V
    :try_end_13
    .catchall {:try_start_13 .. :try_end_13} :catchall_11

    goto :goto_13

    :catchall_11
    move-exception v0

    .line 463
    :goto_13
    new-instance v0, Ljava/io/IOException;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v2, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v10}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

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
.end method

.method private static open(Ljava/lang/String;Z)Ljava/net/HttpURLConnection;
    .locals 1
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 115
    new-instance v0, Ljava/net/URL;

    invoke-direct {v0, p0}, Ljava/net/URL;-><init>(Ljava/lang/String;)V

    .line 117
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

    .line 118
    const/16 p1, 0x3a98

    invoke-virtual {p0, p1}, Ljava/net/HttpURLConnection;->setConnectTimeout(I)V

    .line 119
    const/16 p1, 0x7530

    invoke-virtual {p0, p1}, Ljava/net/HttpURLConnection;->setReadTimeout(I)V

    .line 120
    const/4 p1, 0x0

    invoke-virtual {p0, p1}, Ljava/net/HttpURLConnection;->setUseCaches(Z)V

    .line 121
    const/4 p1, 0x1

    invoke-virtual {p0, p1}, Ljava/net/HttpURLConnection;->setInstanceFollowRedirects(Z)V

    .line 122
    const-string p1, "Accept-Encoding"

    const-string v0, "identity"

    invoke-virtual {p0, p1, v0}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 123
    const-string p1, "Connection"

    const-string v0, "close"

    invoke-virtual {p0, p1, v0}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 124
    return-object p0
.end method

.method private static parseLong(Ljava/lang/String;J)J
    .locals 4

    .line 622
    if-nez p0, :cond_0

    return-wide p1

    .line 624
    :cond_0
    :try_start_0
    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v0
    :try_end_0
    .catch Ljava/lang/NumberFormatException; {:try_start_0 .. :try_end_0} :catch_0

    .line 625
    const-wide/16 v2, 0x0

    cmp-long p0, v0, v2

    if-ltz p0, :cond_1

    move-wide p1, v0

    :cond_1
    return-wide p1

    .line 626
    :catch_0
    move-exception p0

    .line 627
    return-wide p1
.end method

.method public static partFileFor(Ljava/io/File;)Ljava/io/File;
    .locals 2

    .line 174
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

    .line 133
    const-string v1, "ETag"

    const-string v2, "Content-Length"

    .line 135
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

    .line 136
    :try_start_1
    const-string v0, "HEAD"

    invoke-virtual {v12, v0}, Ljava/net/HttpURLConnection;->setRequestMethod(Ljava/lang/String;)V

    .line 137
    invoke-virtual {v12}, Ljava/net/HttpURLConnection;->getResponseCode()I

    move-result v0

    .line 138
    if-lt v0, v4, :cond_4

    if-ge v0, v3, :cond_4

    .line 139
    invoke-virtual {v12, v2}, Ljava/net/HttpURLConnection;->getHeaderField(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0, v8, v9}, Lio/kamihama/magianative/CNChunkedDownload;->parseLong(Ljava/lang/String;J)J

    move-result-wide v13

    .line 140
    const-string v0, "Accept-Ranges"

    invoke-virtual {v12, v0}, Ljava/net/HttpURLConnection;->getHeaderField(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 141
    invoke-virtual {v12, v1}, Ljava/net/HttpURLConnection;->getHeaderField(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v15

    .line 142
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

    .line 143
    :goto_0
    cmp-long v11, v13, v6

    if-lez v11, :cond_2

    if-eqz v0, :cond_2

    new-instance v11, Lio/kamihama/magianative/CNChunkedDownload$Probe;

    invoke-direct {v11, v13, v14, v15, v5}, Lio/kamihama/magianative/CNChunkedDownload$Probe;-><init>(JLjava/lang/String;Z)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_3

    .line 148
    if-eqz v12, :cond_1

    :try_start_2
    invoke-virtual {v12}, Ljava/net/HttpURLConnection;->disconnect()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    goto :goto_1

    :catchall_0
    move-exception v0

    .line 143
    :cond_1
    :goto_1
    return-object v11

    .line 144
    :cond_2
    if-lez v11, :cond_4

    :try_start_3
    new-instance v11, Lio/kamihama/magianative/CNChunkedDownload$Probe;

    invoke-direct {v11, v13, v14, v15, v10}, Lio/kamihama/magianative/CNChunkedDownload$Probe;-><init>(JLjava/lang/String;Z)V
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_3

    .line 148
    if-eqz v12, :cond_3

    :try_start_4
    invoke-virtual {v12}, Ljava/net/HttpURLConnection;->disconnect()V
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_1

    goto :goto_2

    :catchall_1
    move-exception v0

    .line 144
    :cond_3
    :goto_2
    return-object v11

    .line 148
    :cond_4
    if-eqz v12, :cond_5

    :try_start_5
    invoke-virtual {v12}, Ljava/net/HttpURLConnection;->disconnect()V

    goto :goto_4

    :catchall_2
    move-exception v0

    goto :goto_4

    .line 146
    :catchall_3
    move-exception v0

    goto :goto_3

    :catchall_4
    move-exception v0

    const/4 v12, 0x0

    .line 148
    :goto_3
    if-eqz v12, :cond_5

    invoke-virtual {v12}, Ljava/net/HttpURLConnection;->disconnect()V
    :try_end_5
    .catchall {:try_start_5 .. :try_end_5} :catchall_2

    .line 151
    :cond_5
    :goto_4
    nop

    .line 153
    :try_start_6
    invoke-static/range {p0 .. p1}, Lio/kamihama/magianative/CNChunkedDownload;->open(Ljava/lang/String;Z)Ljava/net/HttpURLConnection;

    move-result-object v11
    :try_end_6
    .catchall {:try_start_6 .. :try_end_6} :catchall_9

    .line 154
    :try_start_7
    const-string v0, "GET"

    invoke-virtual {v11, v0}, Ljava/net/HttpURLConnection;->setRequestMethod(Ljava/lang/String;)V

    .line 155
    const-string v0, "Range"

    const-string v12, "bytes=0-0"

    invoke-virtual {v11, v0, v12}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 156
    invoke-virtual {v11}, Ljava/net/HttpURLConnection;->getResponseCode()I

    move-result v0

    .line 157
    invoke-virtual {v11, v1}, Ljava/net/HttpURLConnection;->getHeaderField(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 158
    const/16 v12, 0xce

    if-ne v0, v12, :cond_7

    .line 159
    const-string v0, "Content-Range"

    invoke-virtual {v11, v0}, Ljava/net/HttpURLConnection;->getHeaderField(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lio/kamihama/magianative/CNChunkedDownload;->totalFromContentRange(Ljava/lang/String;)J

    move-result-wide v2

    .line 160
    cmp-long v0, v2, v6

    if-lez v0, :cond_9

    new-instance v4, Lio/kamihama/magianative/CNChunkedDownload$Probe;

    invoke-direct {v4, v2, v3, v1, v5}, Lio/kamihama/magianative/CNChunkedDownload$Probe;-><init>(JLjava/lang/String;Z)V
    :try_end_7
    .catchall {:try_start_7 .. :try_end_7} :catchall_8

    .line 167
    if-eqz v11, :cond_6

    :try_start_8
    invoke-virtual {v11}, Ljava/net/HttpURLConnection;->disconnect()V
    :try_end_8
    .catchall {:try_start_8 .. :try_end_8} :catchall_5

    goto :goto_5

    :catchall_5
    move-exception v0

    .line 160
    :cond_6
    :goto_5
    return-object v4

    .line 161
    :cond_7
    if-lt v0, v4, :cond_9

    if-ge v0, v3, :cond_9

    .line 162
    :try_start_9
    invoke-virtual {v11, v2}, Ljava/net/HttpURLConnection;->getHeaderField(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0, v8, v9}, Lio/kamihama/magianative/CNChunkedDownload;->parseLong(Ljava/lang/String;J)J

    move-result-wide v2

    .line 163
    cmp-long v0, v2, v6

    if-lez v0, :cond_a

    new-instance v4, Lio/kamihama/magianative/CNChunkedDownload$Probe;

    invoke-direct {v4, v2, v3, v1, v10}, Lio/kamihama/magianative/CNChunkedDownload$Probe;-><init>(JLjava/lang/String;Z)V
    :try_end_9
    .catchall {:try_start_9 .. :try_end_9} :catchall_8

    .line 167
    if-eqz v11, :cond_8

    :try_start_a
    invoke-virtual {v11}, Ljava/net/HttpURLConnection;->disconnect()V
    :try_end_a
    .catchall {:try_start_a .. :try_end_a} :catchall_6

    goto :goto_6

    :catchall_6
    move-exception v0

    .line 163
    :cond_8
    :goto_6
    return-object v4

    .line 161
    :cond_9
    nop

    .line 167
    :cond_a
    if-eqz v11, :cond_b

    :try_start_b
    invoke-virtual {v11}, Ljava/net/HttpURLConnection;->disconnect()V

    goto :goto_8

    :catchall_7
    move-exception v0

    goto :goto_8

    .line 165
    :catchall_8
    move-exception v0

    goto :goto_7

    :catchall_9
    move-exception v0

    const/4 v11, 0x0

    .line 167
    :goto_7
    if-eqz v11, :cond_b

    invoke-virtual {v11}, Ljava/net/HttpURLConnection;->disconnect()V
    :try_end_b
    .catchall {:try_start_b .. :try_end_b} :catchall_7

    .line 169
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

    .line 607
    invoke-virtual {p1}, Ljava/io/File;->exists()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-virtual {p1}, Ljava/io/File;->delete()Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    .line 608
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

    .line 610
    :cond_1
    :goto_0
    invoke-virtual {p0, p1}, Ljava/io/File;->renameTo(Ljava/io/File;)Z

    move-result v0

    if-eqz v0, :cond_2

    .line 613
    return-void

    .line 611
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

    .line 633
    const-wide/16 v0, -0x1

    if-nez p0, :cond_0

    return-wide v0

    .line 634
    :cond_0
    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    sget-object v2, Ljava/util/Locale;->US:Ljava/util/Locale;

    invoke-virtual {p0, v2}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object p0

    .line 635
    const-string v2, "bytes "

    invoke-virtual {p0, v2}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v2

    if-nez v2, :cond_1

    return-wide v0

    .line 636
    :cond_1
    const/16 v2, 0x2d

    const/4 v3, 0x6

    invoke-virtual {p0, v2, v3}, Ljava/lang/String;->indexOf(II)I

    move-result v2

    .line 637
    if-gez v2, :cond_2

    return-wide v0

    .line 638
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

    .line 565
    :try_start_0
    invoke-virtual {p0}, Ljava/io/File;->isFile()Z

    move-result v1

    const/4 v2, 0x0

    if-eqz v1, :cond_a

    invoke-virtual {p0}, Ljava/io/File;->length()J

    move-result-wide v3
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_9

    const-wide/32 v5, 0x100000

    cmp-long v1, v3, v5

    if-lez v1, :cond_0

    goto/16 :goto_b

    .line 566
    :cond_0
    nop

    .line 568
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

    .line 570
    :try_start_2
    invoke-virtual {v1}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object p0

    .line 571
    const-string v3, "CNVPROG2"

    invoke-virtual {v3, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p0
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_6

    if-nez p0, :cond_1

    .line 595
    :try_start_3
    invoke-virtual {v1}, Ljava/io/BufferedReader;->close()V
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_0

    goto :goto_0

    :catchall_0
    move-exception p0

    .line 571
    :goto_0
    monitor-exit v0

    return-object v2

    .line 572
    :cond_1
    :try_start_4
    invoke-virtual {v1}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object p0
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_6

    .line 573
    if-nez p0, :cond_2

    .line 595
    :try_start_5
    invoke-virtual {v1}, Ljava/io/BufferedReader;->close()V
    :try_end_5
    .catchall {:try_start_5 .. :try_end_5} :catchall_1

    goto :goto_1

    :catchall_1
    move-exception p0

    .line 573
    :goto_1
    monitor-exit v0

    return-object v2

    .line 574
    :cond_2
    :try_start_6
    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    const-string v3, "\\s+"

    invoke-virtual {p0, v3}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p0

    .line 575
    array-length v3, p0
    :try_end_6
    .catchall {:try_start_6 .. :try_end_6} :catchall_6

    const/4 v4, 0x2

    if-ge v3, v4, :cond_3

    .line 595
    :try_start_7
    invoke-virtual {v1}, Ljava/io/BufferedReader;->close()V
    :try_end_7
    .catchall {:try_start_7 .. :try_end_7} :catchall_2

    goto :goto_2

    :catchall_2
    move-exception p0

    .line 575
    :goto_2
    monitor-exit v0

    return-object v2

    .line 577
    :cond_3
    :try_start_8
    new-instance v3, Lio/kamihama/magianative/CNChunkedDownload$Resume;

    invoke-direct {v3, v2}, Lio/kamihama/magianative/CNChunkedDownload$Resume;-><init>(Lio/kamihama/magianative/CNChunkedDownload$1;)V

    .line 578
    const/4 v4, 0x0

    aget-object v5, p0, v4

    invoke-static {v5}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v5

    iput-wide v5, v3, Lio/kamihama/magianative/CNChunkedDownload$Resume;->total:J

    .line 579
    const/4 v5, 0x1

    aget-object p0, p0, v5

    invoke-static {p0}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result p0

    iput p0, v3, Lio/kamihama/magianative/CNChunkedDownload$Resume;->chunks:I

    .line 580
    iget-wide v6, v3, Lio/kamihama/magianative/CNChunkedDownload$Resume;->total:J

    const-wide/16 v8, 0x0

    cmp-long p0, v6, v8

    if-lez p0, :cond_8

    iget p0, v3, Lio/kamihama/magianative/CNChunkedDownload$Resume;->chunks:I

    if-lt p0, v5, :cond_8

    iget p0, v3, Lio/kamihama/magianative/CNChunkedDownload$Resume;->chunks:I

    const/16 v5, 0x40

    if-le p0, v5, :cond_4

    goto :goto_7

    .line 582
    :cond_4
    invoke-virtual {v1}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object p0

    .line 583
    if-nez p0, :cond_5

    const-string p0, ""

    goto :goto_3

    :cond_5
    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    :goto_3
    iput-object p0, v3, Lio/kamihama/magianative/CNChunkedDownload$Resume;->etag:Ljava/lang/String;

    .line 585
    iget p0, v3, Lio/kamihama/magianative/CNChunkedDownload$Resume;->chunks:I

    new-array p0, p0, [J

    iput-object p0, v3, Lio/kamihama/magianative/CNChunkedDownload$Resume;->done:[J

    .line 586
    nop

    :goto_4
    iget p0, v3, Lio/kamihama/magianative/CNChunkedDownload$Resume;->chunks:I

    if-ge v4, p0, :cond_7

    .line 587
    invoke-virtual {v1}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object p0
    :try_end_8
    .catchall {:try_start_8 .. :try_end_8} :catchall_6

    .line 588
    if-nez p0, :cond_6

    .line 595
    :try_start_9
    invoke-virtual {v1}, Ljava/io/BufferedReader;->close()V
    :try_end_9
    .catchall {:try_start_9 .. :try_end_9} :catchall_3

    goto :goto_5

    :catchall_3
    move-exception p0

    .line 588
    :goto_5
    monitor-exit v0

    return-object v2

    .line 589
    :cond_6
    :try_start_a
    iget-object v5, v3, Lio/kamihama/magianative/CNChunkedDownload$Resume;->done:[J

    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v6

    aput-wide v6, v5, v4
    :try_end_a
    .catchall {:try_start_a .. :try_end_a} :catchall_6

    .line 586
    add-int/lit8 v4, v4, 0x1

    goto :goto_4

    .line 591
    :cond_7
    nop

    .line 595
    :try_start_b
    invoke-virtual {v1}, Ljava/io/BufferedReader;->close()V
    :try_end_b
    .catchall {:try_start_b .. :try_end_b} :catchall_4

    goto :goto_6

    :catchall_4
    move-exception p0

    .line 591
    :goto_6
    monitor-exit v0

    return-object v3

    .line 595
    :cond_8
    :goto_7
    :try_start_c
    invoke-virtual {v1}, Ljava/io/BufferedReader;->close()V
    :try_end_c
    .catchall {:try_start_c .. :try_end_c} :catchall_5

    goto :goto_8

    :catchall_5
    move-exception p0

    .line 580
    :goto_8
    monitor-exit v0

    return-object v2

    .line 592
    :catchall_6
    move-exception p0

    goto :goto_9

    :catchall_7
    move-exception p0

    move-object v1, v2

    .line 593
    :goto_9
    nop

    .line 595
    if-eqz v1, :cond_9

    :try_start_d
    invoke-virtual {v1}, Ljava/io/BufferedReader;->close()V
    :try_end_d
    .catchall {:try_start_d .. :try_end_d} :catchall_8

    goto :goto_a

    :catchall_8
    move-exception p0

    .line 593
    :cond_9
    :goto_a
    monitor-exit v0

    return-object v2

    .line 565
    :cond_a
    :goto_b
    monitor-exit v0

    return-object v2

    .line 564
    :catchall_9
    move-exception p0

    monitor-exit v0

    throw p0
.end method

.method private static resumeRejectReason(Lio/kamihama/magianative/CNChunkedDownload$Resume;JLjava/lang/String;Ljava/io/File;)Ljava/lang/String;
    .locals 10

    .line 361
    iget-wide v0, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->total:J

    const-string v2, " != "

    cmp-long v3, v0, p1

    if-eqz v3, :cond_0

    .line 362
    new-instance p3, Ljava/lang/StringBuilder;

    invoke-direct {p3}, Ljava/lang/StringBuilder;-><init>()V

    const-string p4, "\u603b\u957f\u5ea6\u4e0d\u7b26 "

    invoke-virtual {p3, p4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p3

    iget-wide v0, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->total:J

    invoke-virtual {p3, v0, v1}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, p1, p2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0

    .line 364
    :cond_0
    iget v0, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->chunks:I

    const/4 v1, 0x1

    if-lt v0, v1, :cond_8

    iget-object v0, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->done:[J

    if-eqz v0, :cond_8

    iget-object v0, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->done:[J

    array-length v0, v0

    iget v1, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->chunks:I

    if-eq v0, v1, :cond_1

    goto/16 :goto_2

    .line 369
    :cond_1
    invoke-virtual {p4}, Ljava/io/File;->isFile()Z

    move-result v0

    if-nez v0, :cond_2

    .line 370
    const-string p0, "\u4e34\u65f6\u6587\u4ef6\u4e0d\u5b58\u5728"

    return-object p0

    .line 372
    :cond_2
    invoke-virtual {p4}, Ljava/io/File;->length()J

    move-result-wide v0

    cmp-long v3, v0, p1

    if-eqz v3, :cond_3

    .line 373
    new-instance p0, Ljava/lang/StringBuilder;

    invoke-direct {p0}, Ljava/lang/StringBuilder;-><init>()V

    const-string p3, "\u4e34\u65f6\u6587\u4ef6\u957f\u5ea6\u4e0d\u7b26 "

    invoke-virtual {p0, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p4}, Ljava/io/File;->length()J

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

    .line 375
    :cond_3
    iget-object p4, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->etag:Ljava/lang/String;

    invoke-virtual {p4}, Ljava/lang/String;->length()I

    move-result p4

    if-lez p4, :cond_4

    if-eqz p3, :cond_4

    invoke-virtual {p3}, Ljava/lang/String;->length()I

    move-result p4

    if-lez p4, :cond_4

    iget-object p4, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->etag:Ljava/lang/String;

    .line 376
    invoke-virtual {p4, p3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p3

    if-nez p3, :cond_4

    .line 377
    const-string p0, "ETag \u5df2\u53d8\u5316"

    return-object p0

    .line 379
    :cond_4
    iget p3, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->chunks:I

    int-to-long p3, p3

    add-long/2addr p3, p1

    const-wide/16 v0, 0x1

    sub-long/2addr p3, v0

    iget v2, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->chunks:I

    int-to-long v2, v2

    div-long/2addr p3, v2

    .line 380
    const/4 v2, 0x0

    :goto_0
    iget v3, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->chunks:I

    if-ge v2, v3, :cond_7

    .line 381
    int-to-long v3, v2

    mul-long v3, v3, p3

    .line 382
    add-long v5, v3, p3

    sub-long/2addr v5, v0

    sub-long v7, p1, v0

    invoke-static {v5, v6, v7, v8}, Ljava/lang/Math;->min(JJ)J

    move-result-wide v5

    .line 383
    sub-long/2addr v5, v3

    add-long/2addr v5, v0

    .line 384
    iget-object v3, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->done:[J

    aget-wide v7, v3, v2

    const-wide/16 v3, 0x0

    cmp-long v9, v7, v3

    if-ltz v9, :cond_6

    iget-object v3, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->done:[J

    aget-wide v7, v3, v2

    cmp-long v3, v7, v5

    if-lez v3, :cond_5

    goto :goto_1

    .line 380
    :cond_5
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    .line 385
    :cond_6
    :goto_1
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string p2, "\u5206\u7247 "

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string p2, " \u8fdb\u5ea6\u8d8a\u754c "

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    iget-object p0, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->done:[J

    aget-wide p2, p0, v2

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

    .line 388
    :cond_7
    const/4 p0, 0x0

    return-object p0

    .line 365
    :cond_8
    :goto_2
    const-string p0, "\u5206\u7247\u4fe1\u606f\u635f\u574f"

    return-object p0
.end method

.method private static sanitize(Ljava/lang/String;)Ljava/lang/String;
    .locals 2

    .line 602
    if-nez p0, :cond_0

    const-string p0, ""

    return-object p0

    .line 603
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

.method private static declared-synchronized saveMeta(Ljava/io/File;JLjava/lang/String;Ljava/util/concurrent/atomic/AtomicLongArray;)V
    .locals 8

    const-class v0, Lio/kamihama/magianative/CNChunkedDownload;

    monitor-enter v0

    .line 540
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

    .line 541
    nop

    .line 543
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

    .line 544
    :try_start_2
    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    .line 545
    const-string v6, "CNVPROG2"

    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const/16 v7, 0xa

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 546
    invoke-virtual {v4, p1, p2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object p1

    const/16 p2, 0x20

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p4}, Ljava/util/concurrent/atomic/AtomicLongArray;->length()I

    move-result p2

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, v7}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 547
    invoke-static {p3}, Lio/kamihama/magianative/CNChunkedDownload;->sanitize(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v4, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, v7}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 548
    nop

    :goto_0
    invoke-virtual {p4}, Ljava/util/concurrent/atomic/AtomicLongArray;->length()I

    move-result p1

    if-ge v5, p1, :cond_0

    invoke-virtual {p4, v5}, Ljava/util/concurrent/atomic/AtomicLongArray;->get(I)J

    move-result-wide p1

    invoke-virtual {v4, p1, p2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, v7}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    add-int/lit8 v5, v5, 0x1

    goto :goto_0

    .line 549
    :cond_0
    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v3, p1}, Ljava/io/Writer;->write(Ljava/lang/String;)V

    .line 550
    invoke-virtual {v3}, Ljava/io/Writer;->flush()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_1

    .line 556
    :try_start_3
    invoke-virtual {v3}, Ljava/io/Writer;->close()V
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_0

    goto :goto_1

    :catchall_0
    move-exception p1

    .line 558
    :goto_1
    :try_start_4
    invoke-virtual {v1, p0}, Ljava/io/File;->renameTo(Ljava/io/File;)Z

    move-result p1

    if-nez p1, :cond_1

    .line 559
    invoke-static {p0}, Lio/kamihama/magianative/CNChunkedDownload;->deleteQuietly(Ljava/io/File;)V

    .line 560
    invoke-virtual {v1, p0}, Ljava/io/File;->renameTo(Ljava/io/File;)Z

    move-result p0

    if-nez p0, :cond_1

    invoke-static {v1}, Lio/kamihama/magianative/CNChunkedDownload;->deleteQuietly(Ljava/io/File;)V
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_7

    .line 562
    :cond_1
    monitor-exit v0

    return-void

    .line 551
    :catchall_1
    move-exception p0

    goto :goto_2

    :catchall_2
    move-exception p0

    move-object v3, v2

    .line 552
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

    .line 553
    :goto_3
    :try_start_6
    invoke-static {v1}, Lio/kamihama/magianative/CNChunkedDownload;->deleteQuietly(Ljava/io/File;)V
    :try_end_6
    .catchall {:try_start_6 .. :try_end_6} :catchall_5

    .line 556
    if-eqz v2, :cond_3

    :try_start_7
    invoke-virtual {v2}, Ljava/io/Writer;->close()V
    :try_end_7
    .catchall {:try_start_7 .. :try_end_7} :catchall_4

    goto :goto_4

    :catchall_4
    move-exception p0

    .line 554
    :cond_3
    :goto_4
    monitor-exit v0

    return-void

    .line 556
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

    .line 557
    :cond_4
    :goto_5
    :try_start_9
    throw p0
    :try_end_9
    .catchall {:try_start_9 .. :try_end_9} :catchall_7

    .line 539
    :catchall_7
    move-exception p0

    monitor-exit v0

    throw p0
.end method

.method private static totalFromContentRange(Ljava/lang/String;)J
    .locals 3

    .line 643
    const-wide/16 v0, -0x1

    if-nez p0, :cond_0

    return-wide v0

    .line 644
    :cond_0
    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    sget-object v2, Ljava/util/Locale;->US:Ljava/util/Locale;

    invoke-virtual {p0, v2}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object p0

    .line 645
    const/16 v2, 0x2f

    invoke-virtual {p0, v2}, Ljava/lang/String;->indexOf(I)I

    move-result v2

    .line 646
    if-gez v2, :cond_1

    return-wide v0

    .line 647
    :cond_1
    add-int/lit8 v2, v2, 0x1

    invoke-virtual {p0, v2}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object p0

    invoke-static {p0, v0, v1}, Lio/kamihama/magianative/CNChunkedDownload;->parseLong(Ljava/lang/String;J)J

    move-result-wide v0

    return-wide v0
.end method
