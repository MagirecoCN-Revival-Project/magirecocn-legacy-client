.class Lio/kamihama/magianative/CNDownloaderFix$1;
.super Ljava/lang/Thread;
.source "CNDownloaderFix.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lio/kamihama/magianative/CNDownloaderFix;->triggerInstaller()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# direct methods
.method constructor <init>(Ljava/lang/String;)V
    .locals 0

    .line 114
    invoke-direct {p0, p1}, Ljava/lang/Thread;-><init>(Ljava/lang/String;)V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 4

    .line 117
    const-string v0, "MagiaCNDownloader"

    :try_start_0
    new-instance v1, Ljava/io/File;

    const-string v2, "/data/data/io.kamihama.totentanz/files/madomagi/magica/cn_base_done.flag"

    invoke-direct {v1, v2}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 118
    invoke-virtual {v1}, Ljava/io/File;->isFile()Z

    move-result v1

    if-eqz v1, :cond_0

    .line 119
    const-string v1, "triggerInstaller: flag \u5df2\u5b58\u5728\uff0c\u65e0\u9700\u5b89\u88c5"

    invoke-static {v0, v1}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 120
    return-void

    .line 122
    :cond_0
    const-string v1, "triggerInstaller: flag \u4e0d\u5b58\u5728\uff0c\u542f\u52a8\u5b89\u88c5\u5668"

    invoke-static {v0, v1}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 123
    invoke-static {}, Lio/kamihama/magianative/CNDownloaderFix;->runInstaller()V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 126
    goto :goto_0

    .line 124
    :catchall_0
    move-exception v1

    .line 125
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "triggerInstaller \u5f02\u5e38: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2, v1}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 127
    :goto_0
    return-void
.end method
