.class final Lio/kamihama/magianative/CNLog$LogcatReader;
.super Ljava/lang/Object;
.source "CNLog.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lio/kamihama/magianative/CNLog;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1a
    name = "LogcatReader"
.end annotation


# direct methods
.method private constructor <init>()V
    .locals 0

    .line 243
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method synthetic constructor <init>(Lio/kamihama/magianative/CNLog$1;)V
    .locals 0

    .line 243
    invoke-direct {p0}, Lio/kamihama/magianative/CNLog$LogcatReader;-><init>()V

    return-void
.end method

.method private isOwnLine(Ljava/lang/String;)Z
    .locals 3

    .line 270
    const/4 v0, 0x0

    const/4 v1, 0x0

    :goto_0
    invoke-static {}, Lio/kamihama/magianative/CNLog;->access$200()[Ljava/lang/String;

    move-result-object v2

    array-length v2, v2

    if-ge v1, v2, :cond_1

    .line 271
    invoke-static {}, Lio/kamihama/magianative/CNLog;->access$200()[Ljava/lang/String;

    move-result-object v2

    aget-object v2, v2, v1

    invoke-virtual {p1, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v2

    if-eqz v2, :cond_0

    const/4 p1, 0x1

    return p1

    .line 270
    :cond_0
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    .line 273
    :cond_1
    return v0
.end method


# virtual methods
.method public run()V
    .locals 7

    .line 245
    const-string v0, "\u65e5\u5fd7"

    .line 247
    const/4 v1, 0x0

    :try_start_0
    new-instance v2, Ljava/lang/ProcessBuilder;

    const/4 v3, 0x5

    new-array v3, v3, [Ljava/lang/String;

    const-string v4, "logcat"

    const/4 v5, 0x0

    aput-object v4, v3, v5

    const-string v4, "-v"

    const/4 v5, 0x1

    aput-object v4, v3, v5

    const-string v4, "time"

    const/4 v6, 0x2

    aput-object v4, v3, v6

    const-string v4, "-T"

    const/4 v6, 0x3

    aput-object v4, v3, v6

    const-string v4, "1"

    const/4 v6, 0x4

    aput-object v4, v3, v6

    invoke-direct {v2, v3}, Ljava/lang/ProcessBuilder;-><init>([Ljava/lang/String;)V

    .line 249
    invoke-virtual {v2, v5}, Ljava/lang/ProcessBuilder;->redirectErrorStream(Z)Ljava/lang/ProcessBuilder;

    .line 250
    invoke-virtual {v2}, Ljava/lang/ProcessBuilder;->start()Ljava/lang/Process;

    move-result-object v2

    .line 251
    invoke-static {v2}, Lio/kamihama/magianative/CNLog;->access$102(Ljava/lang/Process;)Ljava/lang/Process;

    .line 252
    const-string v3, "INFO"

    const-string v4, "logcat \u56de\u6536\u5df2\u542f\u52a8"

    invoke-static {v0, v3, v4, v1}, Lio/kamihama/magianative/CNLog;->write(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 253
    new-instance v3, Ljava/io/BufferedReader;

    new-instance v4, Ljava/io/InputStreamReader;

    .line 254
    invoke-virtual {v2}, Ljava/lang/Process;->getInputStream()Ljava/io/InputStream;

    move-result-object v2

    const-string v5, "UTF-8"

    invoke-direct {v4, v2, v5}, Ljava/io/InputStreamReader;-><init>(Ljava/io/InputStream;Ljava/lang/String;)V

    invoke-direct {v3, v4}, Ljava/io/BufferedReader;-><init>(Ljava/io/Reader;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_2

    .line 256
    :goto_0
    :try_start_1
    invoke-virtual {v3}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object v2

    if-eqz v2, :cond_2

    .line 257
    invoke-static {}, Lio/kamihama/magianative/CNLog;->access$100()Ljava/lang/Process;

    move-result-object v4

    if-nez v4, :cond_0

    goto :goto_1

    .line 258
    :cond_0
    invoke-direct {p0, v2}, Lio/kamihama/magianative/CNLog$LogcatReader;->isOwnLine(Ljava/lang/String;)Z

    move-result v4

    if-eqz v4, :cond_1

    goto :goto_0

    .line 259
    :cond_1
    invoke-static {v2}, Lio/kamihama/magianative/CNLog;->writeRaw(Ljava/lang/String;)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    goto :goto_0

    .line 264
    :cond_2
    :goto_1
    :try_start_2
    invoke-virtual {v3}, Ljava/io/BufferedReader;->close()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    goto :goto_4

    :catchall_0
    move-exception v0

    goto :goto_4

    .line 261
    :catchall_1
    move-exception v2

    goto :goto_2

    :catchall_2
    move-exception v2

    move-object v3, v1

    .line 262
    :goto_2
    :try_start_3
    const-string v4, "WARN"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "logcat \u56de\u6536\u4e0d\u53ef\u7528: "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v4, v2, v1}, Lio/kamihama/magianative/CNLog;->write(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_3

    goto :goto_3

    :catchall_3
    move-exception v0

    .line 264
    :goto_3
    if-eqz v3, :cond_3

    :try_start_4
    invoke-virtual {v3}, Ljava/io/BufferedReader;->close()V
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_0

    .line 266
    :cond_3
    :goto_4
    return-void
.end method
