.class public final Lio/kamihama/magianative/CNLog;
.super Ljava/lang/Object;
.source "CNLog.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lio/kamihama/magianative/CNLog$LogcatReader;
    }
.end annotation


# static fields
.field private static final BUFFER:Ljava/util/ArrayDeque;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayDeque<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field public static final BUFFER_MAX:I = 0xbb8

.field private static final FILE_LOCK:Ljava/lang/Object;

.field private static final LOG_FILE:Ljava/lang/String; = "cnv_installer.log"

.field private static final OWN_TAGS:[Ljava/lang/String;

.field private static final TS:Ljava/text/SimpleDateFormat;

.field private static volatile listener:Ljava/lang/Runnable;

.field private static volatile logcatProc:Ljava/lang/Process;

.field private static volatile logcatThread:Ljava/lang/Thread;

.field private static openedOnce:Z

.field private static rawSinceFlush:I

.field private static writer:Ljava/io/BufferedWriter;


# direct methods
.method static constructor <clinit>()V
    .locals 6

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

    .line 50
    new-instance v0, Ljava/text/SimpleDateFormat;

    const-string v1, "yyyy-MM-dd HH:mm:ss"

    sget-object v2, Ljava/util/Locale;->US:Ljava/util/Locale;

    invoke-direct {v0, v1, v2}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;Ljava/util/Locale;)V

    sput-object v0, Lio/kamihama/magianative/CNLog;->TS:Ljava/text/SimpleDateFormat;

    .line 53
    new-instance v0, Ljava/util/ArrayDeque;

    invoke-direct {v0}, Ljava/util/ArrayDeque;-><init>()V

    sput-object v0, Lio/kamihama/magianative/CNLog;->BUFFER:Ljava/util/ArrayDeque;

    .line 54
    new-instance v0, Ljava/lang/Object;

    invoke-direct {v0}, Ljava/lang/Object;-><init>()V

    sput-object v0, Lio/kamihama/magianative/CNLog;->FILE_LOCK:Ljava/lang/Object;

    .line 65
    const/4 v0, 0x0

    sput-boolean v0, Lio/kamihama/magianative/CNLog;->openedOnce:Z

    .line 69
    sput v0, Lio/kamihama/magianative/CNLog;->rawSinceFlush:I

    return-void
.end method

.method private constructor <init>()V
    .locals 0

    .line 71
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static synthetic access$100()Ljava/lang/Process;
    .locals 1

    .line 33
    sget-object v0, Lio/kamihama/magianative/CNLog;->logcatProc:Ljava/lang/Process;

    return-object v0
.end method

.method static synthetic access$102(Ljava/lang/Process;)Ljava/lang/Process;
    .locals 0

    .line 33
    sput-object p0, Lio/kamihama/magianative/CNLog;->logcatProc:Ljava/lang/Process;

    return-object p0
.end method

.method static synthetic access$200()[Ljava/lang/String;
    .locals 1

    .line 33
    sget-object v0, Lio/kamihama/magianative/CNLog;->OWN_TAGS:[Ljava/lang/String;

    return-object v0
.end method

.method public static close()V
    .locals 2

    .line 101
    sget-object v0, Lio/kamihama/magianative/CNLog;->FILE_LOCK:Ljava/lang/Object;

    monitor-enter v0

    .line 102
    :try_start_0
    invoke-static {}, Lio/kamihama/magianative/CNLog;->closeWriterLocked()V

    .line 103
    monitor-exit v0

    .line 104
    return-void

    .line 103
    :catchall_0
    move-exception v1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v1
.end method

.method private static closeWriterLocked()V
    .locals 1

    .line 107
    const/4 v0, 0x0

    sput v0, Lio/kamihama/magianative/CNLog;->rawSinceFlush:I

    .line 108
    sget-object v0, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;

    if-eqz v0, :cond_0

    .line 109
    :try_start_0
    invoke-virtual {v0}, Ljava/io/BufferedWriter;->flush()V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    goto :goto_0

    :catchall_0
    move-exception v0

    .line 110
    :goto_0
    :try_start_1
    sget-object v0, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;

    invoke-virtual {v0}, Ljava/io/BufferedWriter;->close()V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    goto :goto_1

    :catchall_1
    move-exception v0

    .line 111
    :goto_1
    const/4 v0, 0x0

    sput-object v0, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;

    .line 113
    :cond_0
    return-void
.end method

.method public static e(Ljava/lang/String;Ljava/lang/String;)V
    .locals 2

    .line 124
    const-string v0, "ERROR"

    const/4 v1, 0x0

    invoke-static {p0, v0, p1, v1}, Lio/kamihama/magianative/CNLog;->write(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    return-void
.end method

.method public static e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    .locals 1

    .line 126
    const-string v0, "ERROR"

    invoke-static {p0, v0, p1, p2}, Lio/kamihama/magianative/CNLog;->write(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    return-void
.end method

.method public static i(Ljava/lang/String;Ljava/lang/String;)V
    .locals 2

    .line 122
    const-string v0, "INFO"

    const/4 v1, 0x0

    invoke-static {p0, v0, p1, v1}, Lio/kamihama/magianative/CNLog;->write(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    return-void
.end method

.method public static init(Ljava/io/File;)V
    .locals 5

    .line 78
    sget-object v0, Lio/kamihama/magianative/CNLog;->FILE_LOCK:Ljava/lang/Object;

    monitor-enter v0

    .line 79
    :try_start_0
    invoke-static {}, Lio/kamihama/magianative/CNLog;->closeWriterLocked()V

    .line 80
    if-nez p0, :cond_0

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    return-void

    .line 82
    :cond_0
    :try_start_1
    invoke-virtual {p0}, Ljava/io/File;->isDirectory()Z

    move-result v1

    if-nez v1, :cond_1

    invoke-virtual {p0}, Ljava/io/File;->mkdirs()Z

    move-result v1

    if-nez v1, :cond_1

    invoke-virtual {p0}, Ljava/io/File;->isDirectory()Z

    move-result v1
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    if-nez v1, :cond_1

    :try_start_2
    monitor-exit v0
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_1

    return-void

    .line 83
    :cond_1
    :try_start_3
    new-instance v1, Ljava/io/File;

    const-string v2, "cnv_installer.log"

    invoke-direct {v1, p0, v2}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    .line 84
    sget-boolean p0, Lio/kamihama/magianative/CNLog;->openedOnce:Z

    .line 85
    new-instance v2, Ljava/io/BufferedWriter;

    new-instance v3, Ljava/io/OutputStreamWriter;

    new-instance v4, Ljava/io/FileOutputStream;

    invoke-direct {v4, v1, p0}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;Z)V

    const-string v1, "UTF-8"

    invoke-direct {v3, v4, v1}, Ljava/io/OutputStreamWriter;-><init>(Ljava/io/OutputStream;Ljava/lang/String;)V

    invoke-direct {v2, v3}, Ljava/io/BufferedWriter;-><init>(Ljava/io/Writer;)V

    sput-object v2, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;

    .line 87
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    if-eqz p0, :cond_2

    const-string v3, "---- \u65e5\u5fd7\u7ee7\u7eed\uff08"

    goto :goto_0

    :cond_2
    const-string v3, "==== \u9b54\u6cd5\u7eaa\u5f55 \u8d44\u6e90\u5b89\u88c5\u5668\u65e5\u5fd7\uff08\u5f00\u59cb\u4e8e "

    :goto_0
    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    sget-object v3, Lio/kamihama/magianative/CNLog;->TS:Ljava/text/SimpleDateFormat;

    new-instance v4, Ljava/util/Date;

    invoke-direct {v4}, Ljava/util/Date;-><init>()V

    .line 88
    invoke-virtual {v3, v4}, Ljava/text/SimpleDateFormat;->format(Ljava/util/Date;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    .line 89
    if-eqz p0, :cond_3

    const-string p0, "\uff09 ----\n"

    goto :goto_1

    :cond_3
    const-string p0, "\uff09 ====\n"

    :goto_1
    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    .line 87
    invoke-virtual {v2, p0}, Ljava/io/BufferedWriter;->write(Ljava/lang/String;)V

    .line 90
    sget-object p0, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;

    invoke-virtual {p0}, Ljava/io/BufferedWriter;->flush()V

    .line 91
    const/4 p0, 0x1

    sput-boolean p0, Lio/kamihama/magianative/CNLog;->openedOnce:Z
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_0

    .line 95
    goto :goto_2

    .line 92
    :catchall_0
    move-exception p0

    .line 93
    const/4 v1, 0x0

    :try_start_4
    sput-object v1, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;

    .line 94
    const-string v1, "CNLog"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "\u65e5\u5fd7\u6587\u4ef6\u6253\u5f00\u5931\u8d25: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-static {v1, p0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 96
    :goto_2
    monitor-exit v0

    .line 97
    return-void

    .line 96
    :catchall_1
    move-exception p0

    monitor-exit v0
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_1

    throw p0
.end method

.method public static setListener(Ljava/lang/Runnable;)V
    .locals 0

    .line 117
    sput-object p0, Lio/kamihama/magianative/CNLog;->listener:Ljava/lang/Runnable;

    .line 118
    return-void
.end method

.method public static size()I
    .locals 2

    .line 311
    sget-object v0, Lio/kamihama/magianative/CNLog;->BUFFER:Ljava/util/ArrayDeque;

    monitor-enter v0

    .line 312
    :try_start_0
    invoke-virtual {v0}, Ljava/util/ArrayDeque;->size()I

    move-result v1

    monitor-exit v0

    return v1

    .line 313
    :catchall_0
    move-exception v1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v1
.end method

.method public static snapshot()Ljava/lang/String;
    .locals 5

    .line 301
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    .line 302
    sget-object v1, Lio/kamihama/magianative/CNLog;->BUFFER:Ljava/util/ArrayDeque;

    monitor-enter v1

    .line 303
    :try_start_0
    invoke-virtual {v1}, Ljava/util/ArrayDeque;->iterator()Ljava/util/Iterator;

    move-result-object v2

    .line 304
    :goto_0
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_0

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/String;

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    const/16 v4, 0xa

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    goto :goto_0

    .line 305
    :cond_0
    monitor-exit v1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 306
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0

    .line 305
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

    .line 226
    :try_start_0
    sget-object v1, Lio/kamihama/magianative/CNLog;->logcatThread:Ljava/lang/Thread;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v1, :cond_0

    monitor-exit v0

    return-void

    .line 227
    :cond_0
    :try_start_1
    new-instance v1, Ljava/lang/Thread;

    new-instance v2, Lio/kamihama/magianative/CNLog$LogcatReader;

    const/4 v3, 0x0

    invoke-direct {v2, v3}, Lio/kamihama/magianative/CNLog$LogcatReader;-><init>(Lio/kamihama/magianative/CNLog$1;)V

    const-string v3, "cnv-logcat"

    invoke-direct {v1, v2, v3}, Ljava/lang/Thread;-><init>(Ljava/lang/Runnable;Ljava/lang/String;)V

    .line 228
    const/4 v2, 0x1

    invoke-virtual {v1, v2}, Ljava/lang/Thread;->setDaemon(Z)V

    .line 229
    sput-object v1, Lio/kamihama/magianative/CNLog;->logcatThread:Ljava/lang/Thread;

    .line 230
    invoke-virtual {v1}, Ljava/lang/Thread;->start()V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 231
    monitor-exit v0

    return-void

    .line 225
    :catchall_0
    move-exception v1

    monitor-exit v0

    throw v1
.end method

.method public static declared-synchronized stopLogcatCapture()V
    .locals 3

    const-class v0, Lio/kamihama/magianative/CNLog;

    monitor-enter v0

    .line 235
    :try_start_0
    sget-object v1, Lio/kamihama/magianative/CNLog;->logcatProc:Ljava/lang/Process;

    .line 236
    const/4 v2, 0x0

    sput-object v2, Lio/kamihama/magianative/CNLog;->logcatProc:Ljava/lang/Process;

    .line 237
    sput-object v2, Lio/kamihama/magianative/CNLog;->logcatThread:Ljava/lang/Thread;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    .line 238
    if-eqz v1, :cond_0

    .line 239
    :try_start_1
    invoke-virtual {v1}, Ljava/lang/Process;->destroy()V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_0

    :catchall_0
    move-exception v1

    .line 241
    :cond_0
    :goto_0
    monitor-exit v0

    return-void

    .line 234
    :catchall_1
    move-exception v1

    monitor-exit v0

    throw v1
.end method

.method public static tail(I)Ljava/lang/String;
    .locals 6

    .line 285
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    .line 286
    sget-object v1, Lio/kamihama/magianative/CNLog;->BUFFER:Ljava/util/ArrayDeque;

    monitor-enter v1

    .line 287
    :try_start_0
    invoke-virtual {v1}, Ljava/util/ArrayDeque;->size()I

    move-result v2

    sub-int/2addr v2, p0

    .line 288
    invoke-virtual {v1}, Ljava/util/ArrayDeque;->iterator()Ljava/util/Iterator;

    move-result-object p0

    .line 289
    const/4 v3, 0x0

    .line 290
    :goto_0
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result v4

    if-eqz v4, :cond_1

    .line 291
    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/lang/String;

    .line 292
    add-int/lit8 v5, v3, 0x1

    if-ge v3, v2, :cond_0

    goto :goto_1

    .line 293
    :cond_0
    invoke-virtual {v0, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    const/16 v4, 0xa

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 294
    nop

    .line 290
    :goto_1
    move v3, v5

    goto :goto_0

    .line 295
    :cond_1
    monitor-exit v1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 296
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0

    .line 295
    :catchall_0
    move-exception p0

    :try_start_1
    monitor-exit v1
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    throw p0
.end method

.method public static w(Ljava/lang/String;Ljava/lang/String;)V
    .locals 2

    .line 123
    const-string v0, "WARN"

    const/4 v1, 0x0

    invoke-static {p0, v0, p1, v1}, Lio/kamihama/magianative/CNLog;->write(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    return-void
.end method

.method public static w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    .locals 1

    .line 125
    const-string v0, "WARN"

    invoke-static {p0, v0, p1, p2}, Lio/kamihama/magianative/CNLog;->write(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    return-void
.end method

.method public static write(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    .locals 2

    .line 133
    if-nez p0, :cond_0

    const-string p0, "\u5e94\u7528"

    .line 134
    :cond_0
    if-nez p1, :cond_1

    const-string p1, "INFO"

    .line 135
    :cond_1
    if-nez p2, :cond_2

    const-string p2, ""

    .line 136
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

    .line 140
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

    .line 142
    :cond_4
    const-string v0, "WARN"

    invoke-virtual {v0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_6

    .line 143
    if-eqz p3, :cond_5

    invoke-static {p0, p2, p3}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto :goto_1

    :cond_5
    invoke-static {p0, p2}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_1

    .line 145
    :cond_6
    invoke-static {p0, p2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_1

    .line 141
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

    .line 147
    :catchall_0
    move-exception p3

    :goto_1
    nop

    .line 151
    sget-object p3, Lio/kamihama/magianative/CNLog;->TS:Ljava/text/SimpleDateFormat;

    monitor-enter p3

    .line 152
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

    .line 153
    monitor-exit p3
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_5

    .line 154
    sget-object p1, Lio/kamihama/magianative/CNLog;->BUFFER:Ljava/util/ArrayDeque;

    monitor-enter p1

    .line 155
    :try_start_2
    invoke-virtual {p1, p0}, Ljava/util/ArrayDeque;->addLast(Ljava/lang/Object;)V

    .line 156
    :goto_2
    sget-object p2, Lio/kamihama/magianative/CNLog;->BUFFER:Ljava/util/ArrayDeque;

    invoke-virtual {p2}, Ljava/util/ArrayDeque;->size()I

    move-result p3

    const/16 v0, 0xbb8

    if-le p3, v0, :cond_9

    invoke-virtual {p2}, Ljava/util/ArrayDeque;->removeFirst()Ljava/lang/Object;

    goto :goto_2

    .line 157
    :cond_9
    monitor-exit p1
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_4

    .line 160
    sget-object p2, Lio/kamihama/magianative/CNLog;->FILE_LOCK:Ljava/lang/Object;

    monitor-enter p2

    .line 161
    :try_start_3
    sget-object p1, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_3

    if-eqz p1, :cond_a

    .line 163
    :try_start_4
    invoke-virtual {p1, p0}, Ljava/io/BufferedWriter;->write(Ljava/lang/String;)V

    .line 164
    sget-object p0, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;

    const/16 p1, 0xa

    invoke-virtual {p0, p1}, Ljava/io/BufferedWriter;->write(I)V

    .line 165
    sget-object p0, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;

    invoke-virtual {p0}, Ljava/io/BufferedWriter;->flush()V
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_1

    .line 168
    goto :goto_3

    .line 166
    :catchall_1
    move-exception p0

    .line 170
    :cond_a
    :goto_3
    :try_start_5
    monitor-exit p2
    :try_end_5
    .catchall {:try_start_5 .. :try_end_5} :catchall_3

    .line 172
    sget-object p0, Lio/kamihama/magianative/CNLog;->listener:Ljava/lang/Runnable;

    .line 173
    if-eqz p0, :cond_b

    .line 174
    :try_start_6
    invoke-interface {p0}, Ljava/lang/Runnable;->run()V
    :try_end_6
    .catchall {:try_start_6 .. :try_end_6} :catchall_2

    goto :goto_4

    :catchall_2
    move-exception p0

    .line 176
    :cond_b
    :goto_4
    return-void

    .line 170
    :catchall_3
    move-exception p0

    :try_start_7
    monitor-exit p2
    :try_end_7
    .catchall {:try_start_7 .. :try_end_7} :catchall_3

    throw p0

    .line 157
    :catchall_4
    move-exception p0

    :try_start_8
    monitor-exit p1
    :try_end_8
    .catchall {:try_start_8 .. :try_end_8} :catchall_4

    throw p0

    .line 153
    :catchall_5
    move-exception p0

    :try_start_9
    monitor-exit p3
    :try_end_9
    .catchall {:try_start_9 .. :try_end_9} :catchall_5

    throw p0
.end method

.method public static writeRaw(Ljava/lang/String;)V
    .locals 4

    .line 185
    if-eqz p0, :cond_5

    invoke-virtual {p0}, Ljava/lang/String;->length()I

    move-result v0

    if-nez v0, :cond_0

    goto :goto_3

    .line 186
    :cond_0
    sget-object v0, Lio/kamihama/magianative/CNLog;->BUFFER:Ljava/util/ArrayDeque;

    monitor-enter v0

    .line 187
    :try_start_0
    invoke-virtual {v0, p0}, Ljava/util/ArrayDeque;->addLast(Ljava/lang/Object;)V

    .line 188
    :goto_0
    sget-object v1, Lio/kamihama/magianative/CNLog;->BUFFER:Ljava/util/ArrayDeque;

    invoke-virtual {v1}, Ljava/util/ArrayDeque;->size()I

    move-result v2

    const/16 v3, 0xbb8

    if-le v2, v3, :cond_1

    invoke-virtual {v1}, Ljava/util/ArrayDeque;->removeFirst()Ljava/lang/Object;

    goto :goto_0

    .line 189
    :cond_1
    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_3

    .line 190
    sget-object v1, Lio/kamihama/magianative/CNLog;->FILE_LOCK:Ljava/lang/Object;

    monitor-enter v1

    .line 191
    :try_start_1
    sget-object v0, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_2

    if-eqz v0, :cond_3

    .line 193
    :try_start_2
    invoke-virtual {v0, p0}, Ljava/io/BufferedWriter;->write(Ljava/lang/String;)V

    .line 194
    sget-object p0, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;

    const/16 v0, 0xa

    invoke-virtual {p0, v0}, Ljava/io/BufferedWriter;->write(I)V

    .line 198
    sget p0, Lio/kamihama/magianative/CNLog;->rawSinceFlush:I

    add-int/lit8 p0, p0, 0x1

    sput p0, Lio/kamihama/magianative/CNLog;->rawSinceFlush:I

    const/16 v0, 0x32

    if-lt p0, v0, :cond_2

    .line 199
    sget-object p0, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;

    invoke-virtual {p0}, Ljava/io/BufferedWriter;->flush()V

    .line 200
    const/4 p0, 0x0

    sput p0, Lio/kamihama/magianative/CNLog;->rawSinceFlush:I
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    goto :goto_1

    .line 202
    :catchall_0
    move-exception p0

    :cond_2
    :goto_1
    nop

    .line 204
    :cond_3
    :try_start_3
    monitor-exit v1
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_2

    .line 205
    sget-object p0, Lio/kamihama/magianative/CNLog;->listener:Ljava/lang/Runnable;

    .line 206
    if-eqz p0, :cond_4

    .line 207
    :try_start_4
    invoke-interface {p0}, Ljava/lang/Runnable;->run()V
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_1

    goto :goto_2

    :catchall_1
    move-exception p0

    .line 209
    :cond_4
    :goto_2
    return-void

    .line 204
    :catchall_2
    move-exception p0

    :try_start_5
    monitor-exit v1
    :try_end_5
    .catchall {:try_start_5 .. :try_end_5} :catchall_2

    throw p0

    .line 189
    :catchall_3
    move-exception p0

    :try_start_6
    monitor-exit v0
    :try_end_6
    .catchall {:try_start_6 .. :try_end_6} :catchall_3

    throw p0

    .line 185
    :cond_5
    :goto_3
    return-void
.end method
