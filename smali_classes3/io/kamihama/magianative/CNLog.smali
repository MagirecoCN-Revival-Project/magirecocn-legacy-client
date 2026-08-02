.class public final Lio/kamihama/magianative/CNLog;
.super Ljava/lang/Object;
.source "CNLog.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lio/kamihama/magianative/CNLog$CrashHandler;,
        Lio/kamihama/magianative/CNLog$Entry;,
        Lio/kamihama/magianative/CNLog$LogcatReader;
    }
.end annotation


# static fields
.field private static final BUFFER:Ljava/util/ArrayDeque;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayDeque<",
            "Lio/kamihama/magianative/CNLog$Entry;",
            ">;"
        }
    .end annotation
.end field

.field public static final BUFFER_MAX:I = 0xbb8

.field private static final FILE_LOCK:Ljava/lang/Object;

.field private static final FILE_TS:Ljava/text/SimpleDateFormat;

.field private static final KEEP_LOGS:I = 0x1e

.field private static final LOG_DIR:Ljava/lang/String; = "log"

.field private static final NATIVE_HINTS:[Ljava/lang/String;

.field private static final OWN_TAGS:[Ljava/lang/String;

.field private static final PRIV_DIR:Ljava/lang/String; = "/data/data/io.kamihama.totentanz/files"

.field private static final PUB_DIR:Ljava/lang/String; = "/sdcard/Android/data/io.kamihama.totentanz/files"

.field private static final SEQ_FILE:Ljava/lang/String; = ".seq"

.field public static final SRC_APP:I = 0x0

.field public static final SRC_LOGCAT:I = 0x1

.field public static final SRC_NATIVE:I = 0x2

.field private static final TS:Ljava/text/SimpleDateFormat;

.field private static volatile launchSeq:I

.field private static volatile listener:Ljava/lang/Runnable;

.field private static volatile logName:Ljava/lang/String;

.field private static volatile logcatProc:Ljava/lang/Process;

.field private static volatile logcatThread:Ljava/lang/Thread;

.field private static openedOnce:Z

.field private static rawSinceFlush:I

.field private static volatile showLogcat:Z

.field private static volatile showNative:Z

.field private static writer:Ljava/io/BufferedWriter;

.field private static writer2:Ljava/io/BufferedWriter;


# direct methods
.method static constructor <clinit>()V
    .locals 15

    .line 42
    const-string v0, "MagiaCNDownloader"

    const-string v1, "MagiaCNChunk"

    const-string v2, "MagiaCNMirrors"

    const-string v3, "MagiaCNHotUpdate"

    const-string v4, "CNLog"

    const-string v5, "\u754c\u9762"

    filled-new-array/range {v0 .. v5}, [Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lio/kamihama/magianative/CNLog;->OWN_TAGS:[Ljava/lang/String;

    .line 62
    new-instance v0, Ljava/text/SimpleDateFormat;

    const-string v1, "yyyyMMdd-HHmmss"

    sget-object v2, Ljava/util/Locale;->US:Ljava/util/Locale;

    invoke-direct {v0, v1, v2}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;Ljava/util/Locale;)V

    sput-object v0, Lio/kamihama/magianative/CNLog;->FILE_TS:Ljava/text/SimpleDateFormat;

    .line 66
    const/4 v0, 0x0

    sput v0, Lio/kamihama/magianative/CNLog;->launchSeq:I

    .line 67
    const-string v1, ""

    sput-object v1, Lio/kamihama/magianative/CNLog;->logName:Ljava/lang/String;

    .line 78
    new-instance v1, Ljava/text/SimpleDateFormat;

    const-string v2, "yyyy-MM-dd HH:mm:ss"

    sget-object v3, Ljava/util/Locale;->US:Ljava/util/Locale;

    invoke-direct {v1, v2, v3}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;Ljava/util/Locale;)V

    sput-object v1, Lio/kamihama/magianative/CNLog;->TS:Ljava/text/SimpleDateFormat;

    .line 98
    const-string v4, "MagiaClientJNI"

    const-string v5, "MagiaCNDownloader"

    const-string v6, "Cocos2dx"

    const-string v7, "cocos2d"

    const-string v8, "DownloadScene"

    const-string v9, "AssetLoad"

    const-string v10, "magia"

    const-string v11, "Magia"

    const-string v12, "libcn_hook"

    const-string v13, "madomagi"

    const-string v14, "f4samurai"

    filled-new-array/range {v4 .. v14}, [Ljava/lang/String;

    move-result-object v1

    sput-object v1, Lio/kamihama/magianative/CNLog;->NATIVE_HINTS:[Ljava/lang/String;

    .line 104
    new-instance v1, Ljava/util/ArrayDeque;

    invoke-direct {v1}, Ljava/util/ArrayDeque;-><init>()V

    sput-object v1, Lio/kamihama/magianative/CNLog;->BUFFER:Ljava/util/ArrayDeque;

    .line 107
    const/4 v1, 0x1

    sput-boolean v1, Lio/kamihama/magianative/CNLog;->showLogcat:Z

    .line 108
    sput-boolean v1, Lio/kamihama/magianative/CNLog;->showNative:Z

    .line 121
    new-instance v1, Ljava/lang/Object;

    invoke-direct {v1}, Ljava/lang/Object;-><init>()V

    sput-object v1, Lio/kamihama/magianative/CNLog;->FILE_LOCK:Ljava/lang/Object;

    .line 144
    sput-boolean v0, Lio/kamihama/magianative/CNLog;->openedOnce:Z

    .line 148
    sput v0, Lio/kamihama/magianative/CNLog;->rawSinceFlush:I

    return-void
.end method

.method private constructor <init>()V
    .locals 0

    .line 150
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static synthetic access$000()Ljava/lang/Object;
    .locals 1

    .line 33
    sget-object v0, Lio/kamihama/magianative/CNLog;->FILE_LOCK:Ljava/lang/Object;

    return-object v0
.end method

.method static synthetic access$100()Ljava/io/BufferedWriter;
    .locals 1

    .line 33
    sget-object v0, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;

    return-object v0
.end method

.method static synthetic access$200()Ljava/io/BufferedWriter;
    .locals 1

    .line 33
    sget-object v0, Lio/kamihama/magianative/CNLog;->writer2:Ljava/io/BufferedWriter;

    return-object v0
.end method

.method static synthetic access$400()Ljava/lang/Process;
    .locals 1

    .line 33
    sget-object v0, Lio/kamihama/magianative/CNLog;->logcatProc:Ljava/lang/Process;

    return-object v0
.end method

.method static synthetic access$402(Ljava/lang/Process;)Ljava/lang/Process;
    .locals 0

    .line 33
    sput-object p0, Lio/kamihama/magianative/CNLog;->logcatProc:Ljava/lang/Process;

    return-object p0
.end method

.method static synthetic access$500()[Ljava/lang/String;
    .locals 1

    .line 33
    sget-object v0, Lio/kamihama/magianative/CNLog;->OWN_TAGS:[Ljava/lang/String;

    return-object v0
.end method

.method static classify(Ljava/lang/String;)I
    .locals 3

    .line 557
    const/4 v0, 0x0

    :goto_0
    sget-object v1, Lio/kamihama/magianative/CNLog;->NATIVE_HINTS:[Ljava/lang/String;

    array-length v2, v1

    if-ge v0, v2, :cond_1

    .line 558
    aget-object v1, v1, v0

    invoke-virtual {p0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v1

    if-eqz v1, :cond_0

    const/4 p0, 0x2

    return p0

    .line 557
    :cond_0
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    .line 560
    :cond_1
    const/4 p0, 0x1

    return p0
.end method

.method public static close()V
    .locals 2

    .line 314
    sget-object v0, Lio/kamihama/magianative/CNLog;->FILE_LOCK:Ljava/lang/Object;

    monitor-enter v0

    .line 315
    :try_start_0
    invoke-static {}, Lio/kamihama/magianative/CNLog;->closeWriterLocked()V

    .line 316
    monitor-exit v0

    .line 317
    return-void

    .line 316
    :catchall_0
    move-exception v1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v1
.end method

.method private static closeWriterLocked()V
    .locals 2

    .line 320
    const/4 v0, 0x0

    sput v0, Lio/kamihama/magianative/CNLog;->rawSinceFlush:I

    .line 321
    sget-object v0, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    .line 322
    :try_start_0
    invoke-virtual {v0}, Ljava/io/BufferedWriter;->flush()V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    goto :goto_0

    :catchall_0
    move-exception v0

    .line 323
    :goto_0
    :try_start_1
    sget-object v0, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;

    invoke-virtual {v0}, Ljava/io/BufferedWriter;->close()V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    goto :goto_1

    :catchall_1
    move-exception v0

    .line 324
    :goto_1
    sput-object v1, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;

    .line 326
    :cond_0
    sget-object v0, Lio/kamihama/magianative/CNLog;->writer2:Ljava/io/BufferedWriter;

    if-eqz v0, :cond_1

    .line 327
    :try_start_2
    invoke-virtual {v0}, Ljava/io/BufferedWriter;->flush()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_2

    goto :goto_2

    :catchall_2
    move-exception v0

    .line 328
    :goto_2
    :try_start_3
    sget-object v0, Lio/kamihama/magianative/CNLog;->writer2:Ljava/io/BufferedWriter;

    invoke-virtual {v0}, Ljava/io/BufferedWriter;->close()V
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_3

    goto :goto_3

    :catchall_3
    move-exception v0

    .line 329
    :goto_3
    sput-object v1, Lio/kamihama/magianative/CNLog;->writer2:Ljava/io/BufferedWriter;

    .line 331
    :cond_1
    return-void
.end method

.method public static currentLogPath()Ljava/lang/String;
    .locals 2

    .line 71
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "/data/data/io.kamihama.totentanz/files/log/"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    sget-object v1, Lio/kamihama/magianative/CNLog;->logName:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static e(Ljava/lang/String;Ljava/lang/String;)V
    .locals 2

    .line 342
    const-string v0, "ERROR"

    const/4 v1, 0x0

    invoke-static {p0, v0, p1, v1}, Lio/kamihama/magianative/CNLog;->write(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    return-void
.end method

.method public static e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    .locals 1

    .line 344
    const-string v0, "ERROR"

    invoke-static {p0, v0, p1, p2}, Lio/kamihama/magianative/CNLog;->write(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    return-void
.end method

.method public static i(Ljava/lang/String;Ljava/lang/String;)V
    .locals 2

    .line 340
    const-string v0, "INFO"

    const/4 v1, 0x0

    invoke-static {p0, v0, p1, v1}, Lio/kamihama/magianative/CNLog;->write(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    return-void
.end method

.method public static init(Ljava/io/File;)V
    .locals 9

    .line 214
    sget-object v0, Lio/kamihama/magianative/CNLog;->FILE_LOCK:Ljava/lang/Object;

    monitor-enter v0

    .line 215
    :try_start_0
    invoke-static {}, Lio/kamihama/magianative/CNLog;->closeWriterLocked()V

    .line 216
    if-nez p0, :cond_0

    monitor-exit v0

    return-void

    .line 217
    :cond_0
    sget-boolean v1, Lio/kamihama/magianative/CNLog;->openedOnce:Z

    .line 218
    const/4 v2, 0x1

    if-nez v1, :cond_1

    .line 220
    new-instance v3, Ljava/util/Date;

    invoke-direct {v3}, Ljava/util/Date;-><init>()V

    .line 221
    new-instance v4, Ljava/io/File;

    const-string v5, "log"

    invoke-direct {v4, p0, v5}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    invoke-static {v4}, Lio/kamihama/magianative/CNLog;->nextSeq(Ljava/io/File;)I

    move-result v4

    sput v4, Lio/kamihama/magianative/CNLog;->launchSeq:I

    .line 222
    sget-object v4, Ljava/util/Locale;->US:Ljava/util/Locale;

    const-string v5, "%04d_%s.log"

    const/4 v6, 0x2

    new-array v6, v6, [Ljava/lang/Object;

    sget v7, Lio/kamihama/magianative/CNLog;->launchSeq:I

    .line 223
    invoke-static {v7}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    const/4 v8, 0x0

    aput-object v7, v6, v8

    sget-object v7, Lio/kamihama/magianative/CNLog;->FILE_TS:Ljava/text/SimpleDateFormat;

    invoke-virtual {v7, v3}, Ljava/text/SimpleDateFormat;->format(Ljava/util/Date;)Ljava/lang/String;

    move-result-object v3

    aput-object v3, v6, v2

    .line 222
    invoke-static {v4, v5, v6}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v3

    sput-object v3, Lio/kamihama/magianative/CNLog;->logName:Ljava/lang/String;

    .line 225
    :cond_1
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    if-eqz v1, :cond_2

    const-string v4, "---- \u65e5\u5fd7\u7ee7\u7eed\uff08"

    goto :goto_0

    .line 226
    :cond_2
    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "==== \u9b54\u6cd5\u7eaa\u5f55 \u8d44\u6e90\u5b89\u88c5\u5668\u65e5\u5fd7\uff08\u7b2c "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    sget v5, Lio/kamihama/magianative/CNLog;->launchSeq:I

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, " \u6b21\u542f\u52a8\uff0c\u5f00\u59cb\u4e8e "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    :goto_0
    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    sget-object v4, Lio/kamihama/magianative/CNLog;->TS:Ljava/text/SimpleDateFormat;

    new-instance v5, Ljava/util/Date;

    invoke-direct {v5}, Ljava/util/Date;-><init>()V

    .line 227
    invoke-virtual {v4, v5}, Ljava/text/SimpleDateFormat;->format(Ljava/util/Date;)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    .line 228
    if-eqz v1, :cond_3

    const-string v4, "\uff09 ----\n"

    goto :goto_1

    :cond_3
    const-string v4, "\uff09 ====\n"

    :goto_1
    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    .line 229
    new-instance v4, Ljava/io/File;

    const-string v5, "log"

    invoke-direct {v4, p0, v5}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    invoke-static {v4, v1, v3}, Lio/kamihama/magianative/CNLog;->openOne(Ljava/io/File;ZLjava/lang/String;)Ljava/io/BufferedWriter;

    move-result-object p0

    sput-object p0, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;

    .line 231
    new-instance p0, Ljava/io/File;

    const-string v4, "/sdcard/Android/data/io.kamihama.totentanz/files"

    const-string v5, "log"

    invoke-direct {p0, v4, v5}, Ljava/io/File;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    invoke-static {p0, v1, v3}, Lio/kamihama/magianative/CNLog;->openOne(Ljava/io/File;ZLjava/lang/String;)Ljava/io/BufferedWriter;

    move-result-object p0

    sput-object p0, Lio/kamihama/magianative/CNLog;->writer2:Ljava/io/BufferedWriter;

    .line 232
    sget-object v1, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;

    if-nez v1, :cond_4

    if-eqz p0, :cond_5

    :cond_4
    sput-boolean v2, Lio/kamihama/magianative/CNLog;->openedOnce:Z

    .line 233
    :cond_5
    if-nez v1, :cond_6

    if-nez p0, :cond_6

    .line 234
    const-string p0, "CNLog"

    const-string v1, "\u4e24\u4e2a\u65e5\u5fd7\u8def\u5f84\u90fd\u6253\u4e0d\u5f00"

    invoke-static {p0, v1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 236
    :cond_6
    monitor-exit v0

    .line 237
    return-void

    .line 236
    :catchall_0
    move-exception p0

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw p0
.end method

.method public static declared-synchronized initEarly()V
    .locals 5

    const-class v0, Lio/kamihama/magianative/CNLog;

    monitor-enter v0

    .line 167
    :try_start_0
    sget-boolean v1, Lio/kamihama/magianative/CNLog;->openedOnce:Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v1, :cond_0

    monitor-exit v0

    return-void

    .line 168
    :cond_0
    :try_start_1
    new-instance v1, Ljava/io/File;

    const-string v2, "/data/data/io.kamihama.totentanz/files"

    invoke-direct {v1, v2}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-static {v1}, Lio/kamihama/magianative/CNLog;->init(Ljava/io/File;)V

    .line 169
    invoke-static {}, Lio/kamihama/magianative/CNLog;->startLogcatCapture()V

    .line 170
    invoke-static {}, Lio/kamihama/magianative/CNLog;->installCrashHandler()V

    .line 171
    const-string v1, "\u65e5\u5fd7"

    const-string v2, "INFO"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "\u65e5\u5fd7\u5df2\u542f\u52a8\uff08\u7b2c "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    sget v4, Lio/kamihama/magianative/CNLog;->launchSeq:I

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, " \u6b21\u542f\u52a8\uff09 \u79c1\u6709="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    .line 172
    invoke-static {}, Lio/kamihama/magianative/CNLog;->currentLogPath()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, " \u5916\u90e8="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    .line 173
    invoke-static {}, Lio/kamihama/magianative/CNLog;->publicLogPath()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, " \u4fdd\u7559\u6700\u8fd1 "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    const/16 v4, 0x1e

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, " \u6b21"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    .line 171
    const/4 v4, 0x0

    invoke-static {v1, v2, v3, v4}, Lio/kamihama/magianative/CNLog;->write(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 175
    monitor-exit v0

    return-void

    .line 166
    :catchall_0
    move-exception v1

    monitor-exit v0

    throw v1
.end method

.method private static installCrashHandler()V
    .locals 3

    .line 184
    :try_start_0
    invoke-static {}, Ljava/lang/Thread;->getDefaultUncaughtExceptionHandler()Ljava/lang/Thread$UncaughtExceptionHandler;

    move-result-object v0

    .line 185
    instance-of v1, v0, Lio/kamihama/magianative/CNLog$CrashHandler;

    if-eqz v1, :cond_0

    return-void

    .line 186
    :cond_0
    new-instance v1, Lio/kamihama/magianative/CNLog$CrashHandler;

    invoke-direct {v1, v0}, Lio/kamihama/magianative/CNLog$CrashHandler;-><init>(Ljava/lang/Thread$UncaughtExceptionHandler;)V

    invoke-static {v1}, Ljava/lang/Thread;->setDefaultUncaughtExceptionHandler(Ljava/lang/Thread$UncaughtExceptionHandler;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 189
    goto :goto_0

    .line 187
    :catchall_0
    move-exception v0

    .line 188
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "\u65e0\u6cd5\u5b89\u88c5\u5d29\u6e83\u5904\u7406\u5668: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "CNLog"

    invoke-static {v1, v0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 190
    :goto_0
    return-void
.end method

.method public static isShowLogcat()Z
    .locals 1

    .line 112
    sget-boolean v0, Lio/kamihama/magianative/CNLog;->showLogcat:Z

    return v0
.end method

.method public static isShowNative()Z
    .locals 1

    .line 113
    sget-boolean v0, Lio/kamihama/magianative/CNLog;->showNative:Z

    return v0
.end method

.method public static launchSeq()I
    .locals 1

    .line 76
    sget v0, Lio/kamihama/magianative/CNLog;->launchSeq:I

    return v0
.end method

.method private static nextSeq(Ljava/io/File;)I
    .locals 6

    .line 267
    const-string v0, "UTF-8"

    .line 268
    new-instance v1, Ljava/io/File;

    const-string v2, ".seq"

    invoke-direct {v1, p0, v2}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    .line 270
    const/4 v2, 0x0

    :try_start_0
    invoke-virtual {p0}, Ljava/io/File;->isDirectory()Z

    move-result v3

    if-nez v3, :cond_0

    invoke-virtual {p0}, Ljava/io/File;->mkdirs()Z

    .line 271
    :cond_0
    invoke-virtual {v1}, Ljava/io/File;->isFile()Z

    move-result v3

    if-eqz v3, :cond_2

    .line 272
    new-instance v3, Ljava/io/BufferedReader;

    new-instance v4, Ljava/io/InputStreamReader;

    new-instance v5, Ljava/io/FileInputStream;

    invoke-direct {v5, v1}, Ljava/io/FileInputStream;-><init>(Ljava/io/File;)V

    invoke-direct {v4, v5, v0}, Ljava/io/InputStreamReader;-><init>(Ljava/io/InputStream;Ljava/lang/String;)V

    invoke-direct {v3, v4}, Ljava/io/BufferedReader;-><init>(Ljava/io/Reader;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_3

    .line 275
    :try_start_1
    invoke-virtual {v3}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object v4

    .line 276
    if-eqz v4, :cond_1

    invoke-virtual {v4}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v4

    invoke-static {v4}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v4
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    goto :goto_0

    :cond_1
    const/4 v4, 0x0

    .line 277
    :goto_0
    :try_start_2
    invoke-virtual {v3}, Ljava/io/BufferedReader;->close()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    goto :goto_2

    :catchall_0
    move-exception v3

    goto :goto_2

    :catchall_1
    move-exception v4

    :try_start_3
    invoke-virtual {v3}, Ljava/io/BufferedReader;->close()V
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_2

    goto :goto_1

    :catchall_2
    move-exception v3

    :goto_1
    :try_start_4
    throw v4
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_3

    .line 271
    :cond_2
    const/4 v4, 0x0

    .line 279
    :goto_2
    goto :goto_3

    :catchall_3
    move-exception v3

    const/4 v4, 0x0

    .line 280
    :goto_3
    if-gtz v4, :cond_4

    .line 281
    invoke-virtual {p0}, Ljava/io/File;->list()[Ljava/lang/String;

    move-result-object p0

    .line 282
    if-nez p0, :cond_3

    const/4 v4, 0x0

    goto :goto_4

    :cond_3
    array-length p0, p0

    move v4, p0

    .line 284
    :cond_4
    :goto_4
    add-int/lit8 v4, v4, 0x1

    .line 286
    :try_start_5
    new-instance p0, Ljava/io/OutputStreamWriter;

    new-instance v3, Ljava/io/FileOutputStream;

    invoke-direct {v3, v1, v2}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;Z)V

    invoke-direct {p0, v3, v0}, Ljava/io/OutputStreamWriter;-><init>(Ljava/io/OutputStream;Ljava/lang/String;)V
    :try_end_5
    .catchall {:try_start_5 .. :try_end_5} :catchall_7

    .line 287
    :try_start_6
    invoke-static {v4}, Ljava/lang/Integer;->toString(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p0, v0}, Ljava/io/Writer;->write(Ljava/lang/String;)V

    invoke-virtual {p0}, Ljava/io/Writer;->flush()V
    :try_end_6
    .catchall {:try_start_6 .. :try_end_6} :catchall_5

    .line 288
    :try_start_7
    invoke-virtual {p0}, Ljava/io/Writer;->close()V
    :try_end_7
    .catchall {:try_start_7 .. :try_end_7} :catchall_4

    goto :goto_6

    :catchall_4
    move-exception p0

    goto :goto_6

    :catchall_5
    move-exception v0

    :try_start_8
    invoke-virtual {p0}, Ljava/io/Writer;->close()V
    :try_end_8
    .catchall {:try_start_8 .. :try_end_8} :catchall_6

    goto :goto_5

    :catchall_6
    move-exception p0

    :goto_5
    :try_start_9
    throw v0
    :try_end_9
    .catchall {:try_start_9 .. :try_end_9} :catchall_7

    .line 289
    :catchall_7
    move-exception p0

    :goto_6
    nop

    .line 290
    return v4
.end method

.method private static openOne(Ljava/io/File;ZLjava/lang/String;)Ljava/io/BufferedWriter;
    .locals 6

    .line 246
    const/4 v0, 0x0

    if-nez p0, :cond_0

    return-object v0

    .line 247
    :cond_0
    :try_start_0
    invoke-virtual {p0}, Ljava/io/File;->isDirectory()Z

    move-result v1

    if-nez v1, :cond_1

    invoke-virtual {p0}, Ljava/io/File;->mkdirs()Z

    move-result v1

    if-nez v1, :cond_1

    invoke-virtual {p0}, Ljava/io/File;->isDirectory()Z

    move-result v1

    if-nez v1, :cond_1

    return-object v0

    .line 248
    :cond_1
    if-nez p1, :cond_2

    invoke-static {p0}, Lio/kamihama/magianative/CNLog;->pruneOldLogs(Ljava/io/File;)V

    .line 249
    :cond_2
    new-instance v1, Ljava/io/BufferedWriter;

    new-instance v2, Ljava/io/OutputStreamWriter;

    new-instance v3, Ljava/io/FileOutputStream;

    new-instance v4, Ljava/io/File;

    sget-object v5, Lio/kamihama/magianative/CNLog;->logName:Ljava/lang/String;

    invoke-direct {v4, p0, v5}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    invoke-direct {v3, v4, p1}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;Z)V

    const-string p1, "UTF-8"

    invoke-direct {v2, v3, p1}, Ljava/io/OutputStreamWriter;-><init>(Ljava/io/OutputStream;Ljava/lang/String;)V

    invoke-direct {v1, v2}, Ljava/io/BufferedWriter;-><init>(Ljava/io/Writer;)V

    .line 251
    invoke-virtual {v1, p2}, Ljava/io/BufferedWriter;->write(Ljava/lang/String;)V

    .line 252
    invoke-virtual {v1}, Ljava/io/BufferedWriter;->flush()V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 253
    return-object v1

    .line 254
    :catchall_0
    move-exception p1

    .line 255
    new-instance p2, Ljava/lang/StringBuilder;

    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "\u65e5\u5fd7\u6587\u4ef6\u6253\u5f00\u5931\u8d25 "

    invoke-virtual {p2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string p2, ": "

    invoke-virtual {p0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    const-string p1, "CNLog"

    invoke-static {p1, p0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 256
    return-object v0
.end method

.method private static pruneOldLogs(Ljava/io/File;)V
    .locals 7

    .line 296
    :try_start_0
    invoke-virtual {p0}, Ljava/io/File;->list()[Ljava/lang/String;

    move-result-object v0

    .line 297
    if-eqz v0, :cond_5

    array-length v1, v0

    const/16 v2, 0x1e

    if-gt v1, v2, :cond_0

    goto :goto_3

    .line 298
    :cond_0
    new-instance v1, Ljava/util/ArrayList;

    invoke-direct {v1}, Ljava/util/ArrayList;-><init>()V

    .line 299
    const/4 v3, 0x0

    const/4 v4, 0x0

    :goto_0
    array-length v5, v0

    if-ge v4, v5, :cond_2

    .line 300
    aget-object v5, v0, v4

    const-string v6, ".log"

    invoke-virtual {v5, v6}, Ljava/lang/String;->endsWith(Ljava/lang/String;)Z

    move-result v5

    if-eqz v5, :cond_1

    aget-object v5, v0, v4

    invoke-virtual {v1, v5}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 299
    :cond_1
    add-int/lit8 v4, v4, 0x1

    goto :goto_0

    .line 302
    :cond_2
    invoke-virtual {v1}, Ljava/util/ArrayList;->size()I

    move-result v0

    if-gt v0, v2, :cond_3

    return-void

    .line 303
    :cond_3
    invoke-static {v1}, Ljava/util/Collections;->sort(Ljava/util/List;)V

    .line 304
    invoke-virtual {v1}, Ljava/util/ArrayList;->size()I

    move-result v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    sub-int/2addr v0, v2

    .line 305
    nop

    :goto_1
    if-ge v3, v0, :cond_4

    .line 306
    :try_start_1
    new-instance v2, Ljava/io/File;

    invoke-virtual {v1, v3}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/lang/String;

    invoke-direct {v2, p0, v4}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    invoke-virtual {v2}, Ljava/io/File;->delete()Z
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_2

    :catchall_0
    move-exception v2

    .line 305
    :goto_2
    add-int/lit8 v3, v3, 0x1

    goto :goto_1

    .line 308
    :cond_4
    :try_start_2
    const-string p0, "CNLog"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "\u6e05\u7406\u4e86 "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " \u4e2a\u65e7\u65e5\u5fd7"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p0, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_1

    goto :goto_4

    .line 297
    :cond_5
    :goto_3
    return-void

    .line 309
    :catchall_1
    move-exception p0

    :goto_4
    nop

    .line 310
    return-void
.end method

.method public static publicLogPath()Ljava/lang/String;
    .locals 2

    .line 74
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "/sdcard/Android/data/io.kamihama.totentanz/files/log/"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    sget-object v1, Lio/kamihama/magianative/CNLog;->logName:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public static setListener(Ljava/lang/Runnable;)V
    .locals 0

    .line 335
    sput-object p0, Lio/kamihama/magianative/CNLog;->listener:Ljava/lang/Runnable;

    .line 336
    return-void
.end method

.method public static setShowLogcat(Z)V
    .locals 0

    .line 110
    sput-boolean p0, Lio/kamihama/magianative/CNLog;->showLogcat:Z

    return-void
.end method

.method public static setShowNative(Z)V
    .locals 0

    .line 111
    sput-boolean p0, Lio/kamihama/magianative/CNLog;->showNative:Z

    return-void
.end method

.method public static size()I
    .locals 2

    .line 550
    sget-object v0, Lio/kamihama/magianative/CNLog;->BUFFER:Ljava/util/ArrayDeque;

    monitor-enter v0

    .line 551
    :try_start_0
    invoke-virtual {v0}, Ljava/util/ArrayDeque;->size()I

    move-result v1

    monitor-exit v0

    return v1

    .line 552
    :catchall_0
    move-exception v1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v1
.end method

.method public static snapshot()Ljava/lang/String;
    .locals 5

    .line 537
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    .line 538
    sget-object v1, Lio/kamihama/magianative/CNLog;->BUFFER:Ljava/util/ArrayDeque;

    monitor-enter v1

    .line 539
    :try_start_0
    invoke-virtual {v1}, Ljava/util/ArrayDeque;->iterator()Ljava/util/Iterator;

    move-result-object v2

    .line 540
    :goto_0
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_1

    .line 541
    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lio/kamihama/magianative/CNLog$Entry;

    .line 542
    iget v4, v3, Lio/kamihama/magianative/CNLog$Entry;->src:I

    invoke-static {v4}, Lio/kamihama/magianative/CNLog;->visible(I)Z

    move-result v4

    if-eqz v4, :cond_0

    iget-object v3, v3, Lio/kamihama/magianative/CNLog$Entry;->line:Ljava/lang/String;

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    const/16 v4, 0xa

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 543
    :cond_0
    goto :goto_0

    .line 544
    :cond_1
    monitor-exit v1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 545
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0

    .line 544
    :catchall_0
    move-exception v0

    :try_start_1
    monitor-exit v1
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    throw v0
.end method

.method public static declared-synchronized startLogcatCapture()V
    .locals 4

    const-class v0, Lio/kamihama/magianative/CNLog;

    monitor-enter v0

    .line 449
    :try_start_0
    sget-object v1, Lio/kamihama/magianative/CNLog;->logcatThread:Ljava/lang/Thread;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v1, :cond_0

    monitor-exit v0

    return-void

    .line 450
    :cond_0
    :try_start_1
    new-instance v1, Ljava/lang/Thread;

    new-instance v2, Lio/kamihama/magianative/CNLog$LogcatReader;

    const/4 v3, 0x0

    invoke-direct {v2, v3}, Lio/kamihama/magianative/CNLog$LogcatReader;-><init>(Lio/kamihama/magianative/CNLog$1;)V

    const-string v3, "cnv-logcat"

    invoke-direct {v1, v2, v3}, Ljava/lang/Thread;-><init>(Ljava/lang/Runnable;Ljava/lang/String;)V

    .line 451
    const/4 v2, 0x1

    invoke-virtual {v1, v2}, Ljava/lang/Thread;->setDaemon(Z)V

    .line 452
    sput-object v1, Lio/kamihama/magianative/CNLog;->logcatThread:Ljava/lang/Thread;

    .line 453
    invoke-virtual {v1}, Ljava/lang/Thread;->start()V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 454
    monitor-exit v0

    return-void

    .line 448
    :catchall_0
    move-exception v1

    monitor-exit v0

    throw v1
.end method

.method public static declared-synchronized stopLogcatCapture()V
    .locals 3

    const-class v0, Lio/kamihama/magianative/CNLog;

    monitor-enter v0

    .line 458
    :try_start_0
    sget-object v1, Lio/kamihama/magianative/CNLog;->logcatProc:Ljava/lang/Process;

    .line 459
    const/4 v2, 0x0

    sput-object v2, Lio/kamihama/magianative/CNLog;->logcatProc:Ljava/lang/Process;

    .line 460
    sput-object v2, Lio/kamihama/magianative/CNLog;->logcatThread:Ljava/lang/Thread;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    .line 461
    if-eqz v1, :cond_0

    .line 462
    :try_start_1
    invoke-virtual {v1}, Ljava/lang/Process;->destroy()V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_0

    :catchall_0
    move-exception v1

    .line 464
    :cond_0
    :goto_0
    monitor-exit v0

    return-void

    .line 457
    :catchall_1
    move-exception v1

    monitor-exit v0

    throw v1
.end method

.method public static tail(I)Ljava/lang/String;
    .locals 5

    .line 508
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    .line 509
    sget-object v1, Lio/kamihama/magianative/CNLog;->BUFFER:Ljava/util/ArrayDeque;

    monitor-enter v1

    .line 510
    :try_start_0
    invoke-virtual {v1}, Ljava/util/ArrayDeque;->iterator()Ljava/util/Iterator;

    move-result-object v2

    .line 511
    :goto_0
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_1

    .line 512
    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lio/kamihama/magianative/CNLog$Entry;

    .line 513
    iget v4, v3, Lio/kamihama/magianative/CNLog$Entry;->src:I

    invoke-static {v4}, Lio/kamihama/magianative/CNLog;->visible(I)Z

    move-result v4

    if-eqz v4, :cond_0

    iget-object v3, v3, Lio/kamihama/magianative/CNLog$Entry;->line:Ljava/lang/String;

    invoke-virtual {v0, v3}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 514
    :cond_0
    goto :goto_0

    .line 515
    :cond_1
    monitor-exit v1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 516
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    .line 517
    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v2

    sub-int/2addr v2, p0

    .line 518
    const/4 p0, 0x0

    :goto_1
    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v3

    if-ge p0, v3, :cond_3

    .line 519
    if-ge p0, v2, :cond_2

    goto :goto_2

    .line 520
    :cond_2
    invoke-virtual {v0, p0}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/String;

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    const/16 v4, 0xa

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 518
    :goto_2
    add-int/lit8 p0, p0, 0x1

    goto :goto_1

    .line 522
    :cond_3
    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0

    .line 515
    :catchall_0
    move-exception p0

    :try_start_1
    monitor-exit v1
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    throw p0
.end method

.method private static visible(I)Z
    .locals 1

    .line 117
    const/4 v0, 0x2

    if-ne p0, v0, :cond_0

    sget-boolean p0, Lio/kamihama/magianative/CNLog;->showNative:Z

    return p0

    .line 118
    :cond_0
    const/4 v0, 0x1

    if-ne p0, v0, :cond_1

    sget-boolean p0, Lio/kamihama/magianative/CNLog;->showLogcat:Z

    return p0

    .line 119
    :cond_1
    return v0
.end method

.method public static visibleSize()I
    .locals 4

    .line 527
    nop

    .line 528
    sget-object v0, Lio/kamihama/magianative/CNLog;->BUFFER:Ljava/util/ArrayDeque;

    monitor-enter v0

    .line 529
    :try_start_0
    invoke-virtual {v0}, Ljava/util/ArrayDeque;->iterator()Ljava/util/Iterator;

    move-result-object v1

    const/4 v2, 0x0

    .line 530
    :cond_0
    :goto_0
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_1

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lio/kamihama/magianative/CNLog$Entry;

    iget v3, v3, Lio/kamihama/magianative/CNLog$Entry;->src:I

    invoke-static {v3}, Lio/kamihama/magianative/CNLog;->visible(I)Z

    move-result v3

    if-eqz v3, :cond_0

    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    .line 531
    :cond_1
    monitor-exit v0

    .line 532
    return v2

    .line 531
    :catchall_0
    move-exception v1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v1
.end method

.method public static w(Ljava/lang/String;Ljava/lang/String;)V
    .locals 2

    .line 341
    const-string v0, "WARN"

    const/4 v1, 0x0

    invoke-static {p0, v0, p1, v1}, Lio/kamihama/magianative/CNLog;->write(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    return-void
.end method

.method public static w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    .locals 1

    .line 343
    const-string v0, "WARN"

    invoke-static {p0, v0, p1, p2}, Lio/kamihama/magianative/CNLog;->write(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    return-void
.end method

.method public static write(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    .locals 2

    .line 351
    if-nez p0, :cond_0

    const-string p0, "\u5e94\u7528"

    .line 352
    :cond_0
    if-nez p1, :cond_1

    const-string p1, "INFO"

    .line 353
    :cond_1
    if-nez p2, :cond_2

    const-string p2, ""

    .line 354
    :cond_2
    if-eqz p3, :cond_3

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    const-string v0, " / "

    invoke-virtual {p2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p2

    .line 358
    :cond_3
    :try_start_0
    const-string v0, "ERROR"

    invoke-virtual {v0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_7

    const-string v0, "FATAL"

    invoke-virtual {v0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_4

    goto :goto_0

    .line 360
    :cond_4
    const-string v0, "WARN"

    invoke-virtual {v0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_6

    .line 361
    if-eqz p3, :cond_5

    invoke-static {p0, p2, p3}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto :goto_1

    :cond_5
    invoke-static {p0, p2}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_1

    .line 363
    :cond_6
    invoke-static {p0, p2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_1

    .line 359
    :cond_7
    :goto_0
    if-eqz p3, :cond_8

    invoke-static {p0, p2, p3}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto :goto_1

    :cond_8
    invoke-static {p0, p2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    goto :goto_1

    .line 365
    :catchall_0
    move-exception p3

    :goto_1
    nop

    .line 369
    sget-object p3, Lio/kamihama/magianative/CNLog;->TS:Ljava/text/SimpleDateFormat;

    monitor-enter p3

    .line 370
    :try_start_1
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "\uff3b"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    new-instance v1, Ljava/util/Date;

    invoke-direct {v1}, Ljava/util/Date;-><init>()V

    invoke-virtual {p3, v1}, Ljava/text/SimpleDateFormat;->format(Ljava/util/Date;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "\uff3d["

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string v0, "]["

    invoke-virtual {p0, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string p1, "] "

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    .line 371
    monitor-exit p3
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_4

    .line 372
    sget-object p1, Lio/kamihama/magianative/CNLog;->BUFFER:Ljava/util/ArrayDeque;

    monitor-enter p1

    .line 373
    :try_start_2
    new-instance p2, Lio/kamihama/magianative/CNLog$Entry;

    const/4 p3, 0x0

    invoke-direct {p2, p3, p0}, Lio/kamihama/magianative/CNLog$Entry;-><init>(ILjava/lang/String;)V

    invoke-virtual {p1, p2}, Ljava/util/ArrayDeque;->addLast(Ljava/lang/Object;)V

    .line 374
    :goto_2
    sget-object p2, Lio/kamihama/magianative/CNLog;->BUFFER:Ljava/util/ArrayDeque;

    invoke-virtual {p2}, Ljava/util/ArrayDeque;->size()I

    move-result p3

    const/16 v0, 0xbb8

    if-le p3, v0, :cond_9

    invoke-virtual {p2}, Ljava/util/ArrayDeque;->removeFirst()Ljava/lang/Object;

    goto :goto_2

    .line 375
    :cond_9
    monitor-exit p1
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_3

    .line 378
    sget-object p2, Lio/kamihama/magianative/CNLog;->FILE_LOCK:Ljava/lang/Object;

    monitor-enter p2

    .line 379
    const/4 p1, 0x1

    :try_start_3
    invoke-static {p0, p1}, Lio/kamihama/magianative/CNLog;->writeFileLocked(Ljava/lang/String;Z)V

    .line 380
    monitor-exit p2
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_2

    .line 382
    sget-object p0, Lio/kamihama/magianative/CNLog;->listener:Ljava/lang/Runnable;

    .line 383
    if-eqz p0, :cond_a

    .line 384
    :try_start_4
    invoke-interface {p0}, Ljava/lang/Runnable;->run()V
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_1

    goto :goto_3

    :catchall_1
    move-exception p0

    .line 386
    :cond_a
    :goto_3
    return-void

    .line 380
    :catchall_2
    move-exception p0

    :try_start_5
    monitor-exit p2
    :try_end_5
    .catchall {:try_start_5 .. :try_end_5} :catchall_2

    throw p0

    .line 375
    :catchall_3
    move-exception p0

    :try_start_6
    monitor-exit p1
    :try_end_6
    .catchall {:try_start_6 .. :try_end_6} :catchall_3

    throw p0

    .line 371
    :catchall_4
    move-exception p0

    :try_start_7
    monitor-exit p3
    :try_end_7
    .catchall {:try_start_7 .. :try_end_7} :catchall_4

    throw p0
.end method

.method private static writeFileLocked(Ljava/lang/String;Z)V
    .locals 2

    .line 390
    sget-object v0, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;

    const/16 v1, 0xa

    if-eqz v0, :cond_1

    .line 392
    :try_start_0
    invoke-virtual {v0, p0}, Ljava/io/BufferedWriter;->write(Ljava/lang/String;)V

    sget-object v0, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;

    invoke-virtual {v0, v1}, Ljava/io/BufferedWriter;->write(I)V

    .line 393
    if-eqz p1, :cond_0

    sget-object v0, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;

    invoke-virtual {v0}, Ljava/io/BufferedWriter;->flush()V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    goto :goto_0

    .line 394
    :catchall_0
    move-exception v0

    :cond_0
    :goto_0
    nop

    .line 396
    :cond_1
    sget-object v0, Lio/kamihama/magianative/CNLog;->writer2:Ljava/io/BufferedWriter;

    if-eqz v0, :cond_3

    .line 398
    :try_start_1
    invoke-virtual {v0, p0}, Ljava/io/BufferedWriter;->write(Ljava/lang/String;)V

    sget-object p0, Lio/kamihama/magianative/CNLog;->writer2:Ljava/io/BufferedWriter;

    invoke-virtual {p0, v1}, Ljava/io/BufferedWriter;->write(I)V

    .line 399
    if-eqz p1, :cond_2

    sget-object p0, Lio/kamihama/magianative/CNLog;->writer2:Ljava/io/BufferedWriter;

    invoke-virtual {p0}, Ljava/io/BufferedWriter;->flush()V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    goto :goto_1

    .line 400
    :catchall_1
    move-exception p0

    :cond_2
    :goto_1
    nop

    .line 402
    :cond_3
    return-void
.end method

.method public static writeRaw(Ljava/lang/String;)V
    .locals 1

    .line 411
    const/4 v0, 0x1

    invoke-static {p0, v0}, Lio/kamihama/magianative/CNLog;->writeRaw(Ljava/lang/String;I)V

    .line 412
    return-void
.end method

.method public static writeRaw(Ljava/lang/String;I)V
    .locals 4

    .line 416
    if-eqz p0, :cond_5

    invoke-virtual {p0}, Ljava/lang/String;->length()I

    move-result v0

    if-nez v0, :cond_0

    goto :goto_3

    .line 417
    :cond_0
    sget-object v0, Lio/kamihama/magianative/CNLog;->BUFFER:Ljava/util/ArrayDeque;

    monitor-enter v0

    .line 418
    :try_start_0
    new-instance v1, Lio/kamihama/magianative/CNLog$Entry;

    invoke-direct {v1, p1, p0}, Lio/kamihama/magianative/CNLog$Entry;-><init>(ILjava/lang/String;)V

    invoke-virtual {v0, v1}, Ljava/util/ArrayDeque;->addLast(Ljava/lang/Object;)V

    .line 419
    :goto_0
    sget-object p1, Lio/kamihama/magianative/CNLog;->BUFFER:Ljava/util/ArrayDeque;

    invoke-virtual {p1}, Ljava/util/ArrayDeque;->size()I

    move-result v1

    const/16 v2, 0xbb8

    if-le v1, v2, :cond_1

    invoke-virtual {p1}, Ljava/util/ArrayDeque;->removeFirst()Ljava/lang/Object;

    goto :goto_0

    .line 420
    :cond_1
    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_2

    .line 421
    sget-object p1, Lio/kamihama/magianative/CNLog;->FILE_LOCK:Ljava/lang/Object;

    monitor-enter p1

    .line 424
    :try_start_1
    sget v0, Lio/kamihama/magianative/CNLog;->rawSinceFlush:I

    const/4 v1, 0x1

    add-int/2addr v0, v1

    sput v0, Lio/kamihama/magianative/CNLog;->rawSinceFlush:I

    const/16 v2, 0x32

    const/4 v3, 0x0

    if-lt v0, v2, :cond_2

    goto :goto_1

    :cond_2
    const/4 v1, 0x0

    .line 425
    :goto_1
    if-eqz v1, :cond_3

    sput v3, Lio/kamihama/magianative/CNLog;->rawSinceFlush:I

    .line 426
    :cond_3
    invoke-static {p0, v1}, Lio/kamihama/magianative/CNLog;->writeFileLocked(Ljava/lang/String;Z)V

    .line 427
    monitor-exit p1
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    .line 428
    sget-object p0, Lio/kamihama/magianative/CNLog;->listener:Ljava/lang/Runnable;

    .line 429
    if-eqz p0, :cond_4

    .line 430
    :try_start_2
    invoke-interface {p0}, Ljava/lang/Runnable;->run()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    goto :goto_2

    :catchall_0
    move-exception p0

    .line 432
    :cond_4
    :goto_2
    return-void

    .line 427
    :catchall_1
    move-exception p0

    :try_start_3
    monitor-exit p1
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_1

    throw p0

    .line 420
    :catchall_2
    move-exception p0

    :try_start_4
    monitor-exit v0
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_2

    throw p0

    .line 416
    :cond_5
    :goto_3
    return-void
.end method
