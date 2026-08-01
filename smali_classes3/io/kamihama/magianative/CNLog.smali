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

    return-void
.end method

.method private constructor <init>()V
    .locals 0

    .line 69
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

    .line 99
    sget-object v0, Lio/kamihama/magianative/CNLog;->FILE_LOCK:Ljava/lang/Object;

    monitor-enter v0

    .line 100
    :try_start_0
    invoke-static {}, Lio/kamihama/magianative/CNLog;->closeWriterLocked()V

    .line 101
    monitor-exit v0

    .line 102
    return-void

    .line 101
    :catchall_0
    move-exception v1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v1
.end method

.method private static closeWriterLocked()V
    .locals 1

    .line 105
    sget-object v0, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;

    if-eqz v0, :cond_0

    .line 106
    :try_start_0
    invoke-virtual {v0}, Ljava/io/BufferedWriter;->flush()V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    goto :goto_0

    :catchall_0
    move-exception v0

    .line 107
    :goto_0
    :try_start_1
    sget-object v0, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;

    invoke-virtual {v0}, Ljava/io/BufferedWriter;->close()V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    goto :goto_1

    :catchall_1
    move-exception v0

    .line 108
    :goto_1
    const/4 v0, 0x0

    sput-object v0, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;

    .line 110
    :cond_0
    return-void
.end method

.method public static e(Ljava/lang/String;Ljava/lang/String;)V
    .locals 2

    .line 121
    const-string v0, "ERROR"

    const/4 v1, 0x0

    invoke-static {p0, v0, p1, v1}, Lio/kamihama/magianative/CNLog;->write(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    return-void
.end method

.method public static e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    .locals 1

    .line 123
    const-string v0, "ERROR"

    invoke-static {p0, v0, p1, p2}, Lio/kamihama/magianative/CNLog;->write(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    return-void
.end method

.method public static i(Ljava/lang/String;Ljava/lang/String;)V
    .locals 2

    .line 119
    const-string v0, "INFO"

    const/4 v1, 0x0

    invoke-static {p0, v0, p1, v1}, Lio/kamihama/magianative/CNLog;->write(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    return-void
.end method

.method public static init(Ljava/io/File;)V
    .locals 5

    .line 76
    sget-object v0, Lio/kamihama/magianative/CNLog;->FILE_LOCK:Ljava/lang/Object;

    monitor-enter v0

    .line 77
    :try_start_0
    invoke-static {}, Lio/kamihama/magianative/CNLog;->closeWriterLocked()V

    .line 78
    if-nez p0, :cond_0

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    return-void

    .line 80
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

    .line 81
    :cond_1
    :try_start_3
    new-instance v1, Ljava/io/File;

    const-string v2, "cnv_installer.log"

    invoke-direct {v1, p0, v2}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    .line 82
    sget-boolean p0, Lio/kamihama/magianative/CNLog;->openedOnce:Z

    .line 83
    new-instance v2, Ljava/io/BufferedWriter;

    new-instance v3, Ljava/io/OutputStreamWriter;

    new-instance v4, Ljava/io/FileOutputStream;

    invoke-direct {v4, v1, p0}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;Z)V

    const-string v1, "UTF-8"

    invoke-direct {v3, v4, v1}, Ljava/io/OutputStreamWriter;-><init>(Ljava/io/OutputStream;Ljava/lang/String;)V

    invoke-direct {v2, v3}, Ljava/io/BufferedWriter;-><init>(Ljava/io/Writer;)V

    sput-object v2, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;

    .line 85
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

    .line 86
    invoke-virtual {v3, v4}, Ljava/text/SimpleDateFormat;->format(Ljava/util/Date;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    .line 87
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

    .line 85
    invoke-virtual {v2, p0}, Ljava/io/BufferedWriter;->write(Ljava/lang/String;)V

    .line 88
    sget-object p0, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;

    invoke-virtual {p0}, Ljava/io/BufferedWriter;->flush()V

    .line 89
    const/4 p0, 0x1

    sput-boolean p0, Lio/kamihama/magianative/CNLog;->openedOnce:Z
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_0

    .line 93
    goto :goto_2

    .line 90
    :catchall_0
    move-exception p0

    .line 91
    const/4 v1, 0x0

    :try_start_4
    sput-object v1, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;

    .line 92
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

    .line 94
    :goto_2
    monitor-exit v0

    .line 95
    return-void

    .line 94
    :catchall_1
    move-exception p0

    monitor-exit v0
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_1

    throw p0
.end method

.method public static setListener(Ljava/lang/Runnable;)V
    .locals 0

    .line 114
    sput-object p0, Lio/kamihama/magianative/CNLog;->listener:Ljava/lang/Runnable;

    .line 115
    return-void
.end method

.method public static size()I
    .locals 2

    .line 280
    sget-object v0, Lio/kamihama/magianative/CNLog;->BUFFER:Ljava/util/ArrayDeque;

    monitor-enter v0

    .line 281
    :try_start_0
    invoke-virtual {v0}, Ljava/util/ArrayDeque;->size()I

    move-result v1

    monitor-exit v0

    return v1

    .line 282
    :catchall_0
    move-exception v1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v1
.end method

.method public static snapshot()Ljava/lang/String;
    .locals 5

    .line 270
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    .line 271
    sget-object v1, Lio/kamihama/magianative/CNLog;->BUFFER:Ljava/util/ArrayDeque;

    monitor-enter v1

    .line 272
    :try_start_0
    invoke-virtual {v1}, Ljava/util/ArrayDeque;->iterator()Ljava/util/Iterator;

    move-result-object v2

    .line 273
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

    .line 274
    :cond_0
    monitor-exit v1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 275
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0

    .line 274
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

    .line 217
    :try_start_0
    sget-object v1, Lio/kamihama/magianative/CNLog;->logcatThread:Ljava/lang/Thread;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v1, :cond_0

    monitor-exit v0

    return-void

    .line 218
    :cond_0
    :try_start_1
    new-instance v1, Ljava/lang/Thread;

    new-instance v2, Lio/kamihama/magianative/CNLog$LogcatReader;

    const/4 v3, 0x0

    invoke-direct {v2, v3}, Lio/kamihama/magianative/CNLog$LogcatReader;-><init>(Lio/kamihama/magianative/CNLog$1;)V

    const-string v3, "cnv-logcat"

    invoke-direct {v1, v2, v3}, Ljava/lang/Thread;-><init>(Ljava/lang/Runnable;Ljava/lang/String;)V

    .line 219
    const/4 v2, 0x1

    invoke-virtual {v1, v2}, Ljava/lang/Thread;->setDaemon(Z)V

    .line 220
    sput-object v1, Lio/kamihama/magianative/CNLog;->logcatThread:Ljava/lang/Thread;

    .line 221
    invoke-virtual {v1}, Ljava/lang/Thread;->start()V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 222
    monitor-exit v0

    return-void

    .line 216
    :catchall_0
    move-exception v1

    monitor-exit v0

    throw v1
.end method

.method public static declared-synchronized stopLogcatCapture()V
    .locals 3

    const-class v0, Lio/kamihama/magianative/CNLog;

    monitor-enter v0

    .line 226
    :try_start_0
    sget-object v1, Lio/kamihama/magianative/CNLog;->logcatProc:Ljava/lang/Process;

    .line 227
    const/4 v2, 0x0

    sput-object v2, Lio/kamihama/magianative/CNLog;->logcatProc:Ljava/lang/Process;

    .line 228
    sput-object v2, Lio/kamihama/magianative/CNLog;->logcatThread:Ljava/lang/Thread;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    .line 229
    if-eqz v1, :cond_0

    .line 230
    :try_start_1
    invoke-virtual {v1}, Ljava/lang/Process;->destroy()V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_0

    :catchall_0
    move-exception v1

    .line 232
    :cond_0
    :goto_0
    monitor-exit v0

    return-void

    .line 225
    :catchall_1
    move-exception v1

    monitor-exit v0

    throw v1
.end method

.method public static w(Ljava/lang/String;Ljava/lang/String;)V
    .locals 2

    .line 120
    const-string v0, "WARN"

    const/4 v1, 0x0

    invoke-static {p0, v0, p1, v1}, Lio/kamihama/magianative/CNLog;->write(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    return-void
.end method

.method public static w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    .locals 1

    .line 122
    const-string v0, "WARN"

    invoke-static {p0, v0, p1, p2}, Lio/kamihama/magianative/CNLog;->write(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    return-void
.end method

.method public static write(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    .locals 2

    .line 130
    if-nez p0, :cond_0

    const-string p0, "\u5e94\u7528"

    .line 131
    :cond_0
    if-nez p1, :cond_1

    const-string p1, "INFO"

    .line 132
    :cond_1
    if-nez p2, :cond_2

    const-string p2, ""

    .line 133
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

    .line 137
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

    .line 139
    :cond_4
    const-string v0, "WARN"

    invoke-virtual {v0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_6

    .line 140
    if-eqz p3, :cond_5

    invoke-static {p0, p2, p3}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    goto :goto_1

    :cond_5
    invoke-static {p0, p2}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_1

    .line 142
    :cond_6
    invoke-static {p0, p2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_1

    .line 138
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

    .line 144
    :catchall_0
    move-exception p3

    :goto_1
    nop

    .line 148
    sget-object p3, Lio/kamihama/magianative/CNLog;->TS:Ljava/text/SimpleDateFormat;

    monitor-enter p3

    .line 149
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

    .line 150
    monitor-exit p3
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_5

    .line 151
    sget-object p1, Lio/kamihama/magianative/CNLog;->BUFFER:Ljava/util/ArrayDeque;

    monitor-enter p1

    .line 152
    :try_start_2
    invoke-virtual {p1, p0}, Ljava/util/ArrayDeque;->addLast(Ljava/lang/Object;)V

    .line 153
    :goto_2
    sget-object p2, Lio/kamihama/magianative/CNLog;->BUFFER:Ljava/util/ArrayDeque;

    invoke-virtual {p2}, Ljava/util/ArrayDeque;->size()I

    move-result p3

    const/16 v0, 0xbb8

    if-le p3, v0, :cond_9

    invoke-virtual {p2}, Ljava/util/ArrayDeque;->removeFirst()Ljava/lang/Object;

    goto :goto_2

    .line 154
    :cond_9
    monitor-exit p1
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_4

    .line 157
    sget-object p2, Lio/kamihama/magianative/CNLog;->FILE_LOCK:Ljava/lang/Object;

    monitor-enter p2

    .line 158
    :try_start_3
    sget-object p1, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_3

    if-eqz p1, :cond_a

    .line 160
    :try_start_4
    invoke-virtual {p1, p0}, Ljava/io/BufferedWriter;->write(Ljava/lang/String;)V

    .line 161
    sget-object p0, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;

    const/16 p1, 0xa

    invoke-virtual {p0, p1}, Ljava/io/BufferedWriter;->write(I)V

    .line 162
    sget-object p0, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;

    invoke-virtual {p0}, Ljava/io/BufferedWriter;->flush()V
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_1

    .line 165
    goto :goto_3

    .line 163
    :catchall_1
    move-exception p0

    .line 167
    :cond_a
    :goto_3
    :try_start_5
    monitor-exit p2
    :try_end_5
    .catchall {:try_start_5 .. :try_end_5} :catchall_3

    .line 169
    sget-object p0, Lio/kamihama/magianative/CNLog;->listener:Ljava/lang/Runnable;

    .line 170
    if-eqz p0, :cond_b

    .line 171
    :try_start_6
    invoke-interface {p0}, Ljava/lang/Runnable;->run()V
    :try_end_6
    .catchall {:try_start_6 .. :try_end_6} :catchall_2

    goto :goto_4

    :catchall_2
    move-exception p0

    .line 173
    :cond_b
    :goto_4
    return-void

    .line 167
    :catchall_3
    move-exception p0

    :try_start_7
    monitor-exit p2
    :try_end_7
    .catchall {:try_start_7 .. :try_end_7} :catchall_3

    throw p0

    .line 154
    :catchall_4
    move-exception p0

    :try_start_8
    monitor-exit p1
    :try_end_8
    .catchall {:try_start_8 .. :try_end_8} :catchall_4

    throw p0

    .line 150
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

    .line 182
    if-eqz p0, :cond_4

    invoke-virtual {p0}, Ljava/lang/String;->length()I

    move-result v0

    if-nez v0, :cond_0

    goto :goto_3

    .line 183
    :cond_0
    sget-object v0, Lio/kamihama/magianative/CNLog;->BUFFER:Ljava/util/ArrayDeque;

    monitor-enter v0

    .line 184
    :try_start_0
    invoke-virtual {v0, p0}, Ljava/util/ArrayDeque;->addLast(Ljava/lang/Object;)V

    .line 185
    :goto_0
    sget-object v1, Lio/kamihama/magianative/CNLog;->BUFFER:Ljava/util/ArrayDeque;

    invoke-virtual {v1}, Ljava/util/ArrayDeque;->size()I

    move-result v2

    const/16 v3, 0xbb8

    if-le v2, v3, :cond_1

    invoke-virtual {v1}, Ljava/util/ArrayDeque;->removeFirst()Ljava/lang/Object;

    goto :goto_0

    .line 186
    :cond_1
    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_3

    .line 187
    sget-object v1, Lio/kamihama/magianative/CNLog;->FILE_LOCK:Ljava/lang/Object;

    monitor-enter v1

    .line 188
    :try_start_1
    sget-object v0, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_2

    if-eqz v0, :cond_2

    .line 190
    :try_start_2
    invoke-virtual {v0, p0}, Ljava/io/BufferedWriter;->write(Ljava/lang/String;)V

    .line 191
    sget-object p0, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;

    const/16 v0, 0xa

    invoke-virtual {p0, v0}, Ljava/io/BufferedWriter;->write(I)V

    .line 192
    sget-object p0, Lio/kamihama/magianative/CNLog;->writer:Ljava/io/BufferedWriter;

    invoke-virtual {p0}, Ljava/io/BufferedWriter;->flush()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    goto :goto_1

    .line 193
    :catchall_0
    move-exception p0

    :goto_1
    nop

    .line 195
    :cond_2
    :try_start_3
    monitor-exit v1
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_2

    .line 196
    sget-object p0, Lio/kamihama/magianative/CNLog;->listener:Ljava/lang/Runnable;

    .line 197
    if-eqz p0, :cond_3

    .line 198
    :try_start_4
    invoke-interface {p0}, Ljava/lang/Runnable;->run()V
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_1

    goto :goto_2

    :catchall_1
    move-exception p0

    .line 200
    :cond_3
    :goto_2
    return-void

    .line 195
    :catchall_2
    move-exception p0

    :try_start_5
    monitor-exit v1
    :try_end_5
    .catchall {:try_start_5 .. :try_end_5} :catchall_2

    throw p0

    .line 186
    :catchall_3
    move-exception p0

    :try_start_6
    monitor-exit v0
    :try_end_6
    .catchall {:try_start_6 .. :try_end_6} :catchall_3

    throw p0

    .line 182
    :cond_4
    :goto_3
    return-void
.end method
