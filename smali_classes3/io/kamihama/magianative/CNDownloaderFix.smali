.class public final Lio/kamihama/magianative/CNDownloaderFix;
.super Ljava/lang/Object;
.source "CNDownloaderFix.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lio/kamihama/magianative/CNDownloaderFix$ArchiveTask;,
        Lio/kamihama/magianative/CNDownloaderFix$SizeProbeTask;,
        Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;,
        Lio/kamihama/magianative/CNDownloaderFix$ResetRequired;,
        Lio/kamihama/magianative/CNDownloaderFix$ArchiveSink;,
        Lio/kamihama/magianative/CNDownloaderFix$ContentRange;,
        Lio/kamihama/magianative/CNDownloaderFix$SpeedWatchdog;
    }
.end annotation


# static fields
.field private static final ACTIVE:Ljava/util/concurrent/atomic/AtomicIntegerArray;

.field private static final ARCHIVE_COUNT:I = 0xf

.field private static final BOOTSTRAP_URL:Ljava/lang/String; = "https://totentanz-9b.magi-reco.com/magica/api/snaa"

.field private static final CONNECT_TIMEOUT_MS:I = 0x3a98

.field private static final EXTRACT_LOCK:Ljava/lang/Object;

.field private static final FILE_NAMES:[Ljava/lang/String;

.field private static final FILE_ROOT:Ljava/lang/String; = "/data/data/io.kamihama.totentanz/files"

.field private static final FINAL_FLAG:Ljava/lang/String; = "/data/data/io.kamihama.totentanz/files/madomagi/magica/cn_base_done.flag"

.field private static final INSTALL_ROOT:Ljava/lang/String; = "/data/data/io.kamihama.totentanz/files/"

.field private static final LAST_PROGRESS_NS:Ljava/util/concurrent/atomic/AtomicLongArray;

.field private static final MAX_ATTEMPTS:I = 0x4

.field private static final MAX_DOWNLOADS:I = 0x4

.field private static final MIN_SNAA_VERSION:I = 0x80

.field private static final NO_RESTART_FLAG:Ljava/lang/String; = "/data/data/io.kamihama.totentanz/files/madomagi/magica/.cn_installer/r128-downloader-v1/no_restart"

.field private static final READ_TIMEOUT_MS:I = 0x7530

.field private static final RESOURCE_BASE_URL:Ljava/lang/String; = "https://assets.magireco.top/"

.field private static final RETRY_LOCK:Ljava/lang/Object;

.field private static final STALE_SPEED_NS:J

.field private static final STATE_ROOT:Ljava/lang/String; = "/data/data/io.kamihama.totentanz/files/madomagi/magica/.cn_installer/r128-downloader-v1"

.field private static final TAG:Ljava/lang/String; = "MagiaCNDownloader"

.field private static final installerStarted:Ljava/util/concurrent/atomic/AtomicBoolean;

.field private static volatile retryRequested:Z


# direct methods
.method static constructor <clinit>()V
    .locals 16

    .line 76
    sget-object v0, Ljava/util/concurrent/TimeUnit;->SECONDS:Ljava/util/concurrent/TimeUnit;

    const-wide/16 v1, 0x2

    invoke-virtual {v0, v1, v2}, Ljava/util/concurrent/TimeUnit;->toNanos(J)J

    move-result-wide v0

    sput-wide v0, Lio/kamihama/magianative/CNDownloaderFix;->STALE_SPEED_NS:J

    .line 77
    new-instance v0, Ljava/lang/Object;

    invoke-direct {v0}, Ljava/lang/Object;-><init>()V

    sput-object v0, Lio/kamihama/magianative/CNDownloaderFix;->EXTRACT_LOCK:Ljava/lang/Object;

    .line 79
    const-string v1, "cn_base_00_db.zip"

    const-string v2, "cn_base_01_json.zip"

    const-string v3, "cn_base_02.zip"

    const-string v4, "cn_base_03.zip"

    const-string v5, "cn_base_04.zip"

    const-string v6, "cn_base_05.zip"

    const-string v7, "cn_base_06.zip"

    const-string v8, "cn_magica_resource.zip"

    const-string v9, "cn_scenario_img.zip"

    const-string v10, "cn_voice_01.zip"

    const-string v11, "cn_voice_02_done.zip"

    const-string v12, "cn_js_update.zip"

    const-string v13, "movie.zip"

    const-string v14, "movie2.zip"

    const-string v15, "cn_scenario_update.zip"

    filled-new-array/range {v1 .. v15}, [Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lio/kamihama/magianative/CNDownloaderFix;->FILE_NAMES:[Ljava/lang/String;

    .line 90
    new-instance v0, Ljava/util/concurrent/atomic/AtomicBoolean;

    const/4 v1, 0x0

    invoke-direct {v0, v1}, Ljava/util/concurrent/atomic/AtomicBoolean;-><init>(Z)V

    sput-object v0, Lio/kamihama/magianative/CNDownloaderFix;->installerStarted:Ljava/util/concurrent/atomic/AtomicBoolean;

    .line 93
    new-instance v0, Ljava/lang/Object;

    invoke-direct {v0}, Ljava/lang/Object;-><init>()V

    sput-object v0, Lio/kamihama/magianative/CNDownloaderFix;->RETRY_LOCK:Ljava/lang/Object;

    .line 94
    sput-boolean v1, Lio/kamihama/magianative/CNDownloaderFix;->retryRequested:Z

    .line 96
    new-instance v0, Ljava/util/concurrent/atomic/AtomicLongArray;

    const/16 v1, 0xf

    invoke-direct {v0, v1}, Ljava/util/concurrent/atomic/AtomicLongArray;-><init>(I)V

    sput-object v0, Lio/kamihama/magianative/CNDownloaderFix;->LAST_PROGRESS_NS:Ljava/util/concurrent/atomic/AtomicLongArray;

    .line 97
    new-instance v0, Ljava/util/concurrent/atomic/AtomicIntegerArray;

    invoke-direct {v0, v1}, Ljava/util/concurrent/atomic/AtomicIntegerArray;-><init>(I)V

    sput-object v0, Lio/kamihama/magianative/CNDownloaderFix;->ACTIVE:Ljava/util/concurrent/atomic/AtomicIntegerArray;

    return-void
.end method

.method private constructor <init>()V
    .locals 0

    .line 99
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 100
    return-void
.end method

.method static synthetic access$000()[Ljava/lang/String;
    .locals 1

    .line 59
    sget-object v0, Lio/kamihama/magianative/CNDownloaderFix;->FILE_NAMES:[Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$100(Ljava/lang/String;)Ljava/io/File;
    .locals 0

    .line 59
    invoke-static {p0}, Lio/kamihama/magianative/CNDownloaderFix;->markerFor(Ljava/lang/String;)Ljava/io/File;

    move-result-object p0

    return-object p0
.end method

.method static synthetic access$200(Ljava/io/File;)J
    .locals 2

    .line 59
    invoke-static {p0}, Lio/kamihama/magianative/CNDownloaderFix;->readMarkerBytes(Ljava/io/File;)J

    move-result-wide v0

    return-wide v0
.end method

.method static synthetic access$300(IJ)V
    .locals 0

    .line 59
    invoke-static {p0, p1, p2}, Lio/kamihama/magianative/CNDownloaderFix;->updateSize(IJ)V

    return-void
.end method

.method static synthetic access$400(I)Z
    .locals 0

    .line 59
    invoke-static {p0}, Lio/kamihama/magianative/CNDownloaderFix;->installArchive(I)Z

    move-result p0

    return p0
.end method

.method static synthetic access$500()Ljava/util/concurrent/atomic/AtomicLongArray;
    .locals 1

    .line 59
    sget-object v0, Lio/kamihama/magianative/CNDownloaderFix;->LAST_PROGRESS_NS:Ljava/util/concurrent/atomic/AtomicLongArray;

    return-object v0
.end method

.method static synthetic access$600(IJJ)V
    .locals 0

    .line 59
    invoke-static {p0, p1, p2, p3, p4}, Lio/kamihama/magianative/CNDownloaderFix;->updateProgress(IJJ)V

    return-void
.end method

.method static synthetic access$800()Ljava/util/concurrent/atomic/AtomicIntegerArray;
    .locals 1

    .line 59
    sget-object v0, Lio/kamihama/magianative/CNDownloaderFix;->ACTIVE:Ljava/util/concurrent/atomic/AtomicIntegerArray;

    return-object v0
.end method

.method static synthetic access$900()J
    .locals 2

    .line 59
    sget-wide v0, Lio/kamihama/magianative/CNDownloaderFix;->STALE_SPEED_NS:J

    return-wide v0
.end method

.method private static allMarkersValid()Z
    .locals 8

    .line 1016
    sget-object v0, Lio/kamihama/magianative/CNDownloaderFix;->FILE_NAMES:[Ljava/lang/String;

    array-length v1, v0

    const/4 v2, 0x0

    const/4 v3, 0x0

    :goto_0
    if-ge v3, v1, :cond_1

    aget-object v4, v0, v3

    .line 1017
    invoke-static {v4}, Lio/kamihama/magianative/CNDownloaderFix;->markerFor(Ljava/lang/String;)Ljava/io/File;

    move-result-object v5

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "https://assets.magireco.top/"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v4, v6}, Lio/kamihama/magianative/CNDownloaderFix;->isMarkerValid(Ljava/io/File;Ljava/lang/String;Ljava/lang/String;)Z

    move-result v5

    if-nez v5, :cond_0

    .line 1018
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "Marker verification failed for "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "MagiaCNDownloader"

    invoke-static {v1, v0}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;)V

    .line 1019
    return v2

    .line 1016
    :cond_0
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    .line 1022
    :cond_1
    const/4 v0, 0x1

    return v0
.end method

.method private static cleanHeader(Ljava/lang/String;)Ljava/lang/String;
    .locals 0

    .line 1273
    if-nez p0, :cond_0

    const-string p0, ""

    goto :goto_0

    :cond_0
    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    :goto_0
    return-object p0
.end method

.method private static closeQuietly(Ljava/io/InputStream;)V
    .locals 0

    .line 1287
    if-eqz p0, :cond_0

    .line 1289
    :try_start_0
    invoke-virtual {p0}, Ljava/io/InputStream;->close()V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    .line 1291
    goto :goto_0

    .line 1290
    :catch_0
    move-exception p0

    .line 1293
    :cond_0
    :goto_0
    return-void
.end method

.method private static closeQuietly(Ljava/io/OutputStream;)V
    .locals 0

    .line 1296
    if-eqz p0, :cond_0

    .line 1298
    :try_start_0
    invoke-virtual {p0}, Ljava/io/OutputStream;->close()V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    .line 1300
    goto :goto_0

    .line 1299
    :catch_0
    move-exception p0

    .line 1302
    :cond_0
    :goto_0
    return-void
.end method

.method private static deleteQuietly(Ljava/io/File;)V
    .locals 2

    .line 1281
    invoke-virtual {p0}, Ljava/io/File;->exists()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Ljava/io/File;->delete()Z

    move-result v0

    if-nez v0, :cond_0

    .line 1282
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "Cannot delete "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    const-string v0, "MagiaCNDownloader"

    invoke-static {v0, p0}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;)V

    .line 1284
    :cond_0
    return-void
.end method

.method private static downloadOnce(Ljava/lang/String;Ljava/io/File;IZ)Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;
    .locals 27
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 676
    move-object/from16 v0, p0

    move-object/from16 v1, p1

    move/from16 v2, p2

    move/from16 v3, p3

    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->isFile()Z

    move-result v4

    if-eqz v4, :cond_0

    .line 677
    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->length()J

    move-result-wide v3

    .line 678
    invoke-static {v2, v3, v4}, Lio/kamihama/magianative/CNDownloaderFix;->updateSize(IJ)V

    .line 679
    new-instance v0, Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;

    invoke-static/range {p1 .. p1}, Lio/kamihama/magianative/CNDownloaderFix;->readSidecarEtag(Ljava/io/File;)Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v3, v4, v1}, Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;-><init>(JLjava/lang/String;)V

    return-object v0

    .line 682
    :cond_0
    new-instance v4, Ljava/io/File;

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->getPath()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, ".part"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-direct {v4, v5}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 683
    new-instance v5, Ljava/io/File;

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->getPath()Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, ".part.meta"

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-direct {v5, v6}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 684
    invoke-virtual {v4}, Ljava/io/File;->getParentFile()Ljava/io/File;

    move-result-object v6

    .line 685
    if-eqz v6, :cond_2

    invoke-virtual {v6}, Ljava/io/File;->isDirectory()Z

    move-result v7

    if-nez v7, :cond_2

    invoke-virtual {v6}, Ljava/io/File;->mkdirs()Z

    move-result v7

    if-nez v7, :cond_2

    invoke-virtual {v6}, Ljava/io/File;->isDirectory()Z

    move-result v7

    if-eqz v7, :cond_1

    goto :goto_0

    .line 686
    :cond_1
    new-instance v0, Ljava/io/IOException;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "Cannot create download directory: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v0

    .line 694
    :cond_2
    :goto_0
    invoke-virtual {v4}, Ljava/io/File;->isFile()Z

    move-result v6

    const-wide/16 v7, 0x0

    if-eqz v6, :cond_3

    invoke-virtual {v4}, Ljava/io/File;->length()J

    move-result-wide v9

    goto :goto_1

    :cond_3
    move-wide v9, v7

    .line 695
    :goto_1
    const-string v6, "MagiaCNDownloader"

    cmp-long v11, v9, v7

    if-lez v11, :cond_4

    .line 696
    invoke-static/range {p1 .. p1}, Lio/kamihama/magianative/CNDownloaderFix;->readSidecarBytes(Ljava/io/File;)J

    move-result-wide v11

    .line 697
    cmp-long v13, v11, v7

    if-lez v13, :cond_4

    cmp-long v13, v9, v11

    if-lez v13, :cond_4

    .line 698
    new-instance v13, Ljava/lang/StringBuilder;

    invoke-direct {v13}, Ljava/lang/StringBuilder;-><init>()V

    const-string v14, "resume-reset file="

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v14

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    const-string v14, " \u6b8b\u7247\u8d85\u957f "

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13, v9, v10}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v9

    const-string v10, " > "

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9, v11, v12}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v9

    const-string v10, "\uff0c\u4e22\u5f03\u91cd\u4e0b"

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v6, v9}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;)V

    .line 700
    invoke-static {v4}, Lio/kamihama/magianative/CNDownloaderFix;->truncate(Ljava/io/File;)V

    .line 701
    invoke-static {v5}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    .line 702
    invoke-static/range {p2 .. p2}, Lio/kamihama/magianative/CNDownloaderFix;->resetProgress(I)V

    .line 703
    move-wide v9, v7

    .line 706
    :cond_4
    new-instance v11, Ljava/lang/StringBuilder;

    invoke-direct {v11}, Ljava/lang/StringBuilder;-><init>()V

    const-string v12, "download-open file="

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->getName()Ljava/lang/String;

    move-result-object v12

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    const-string v12, " offset="

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11, v9, v10}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v11

    const-string v13, " direct="

    invoke-virtual {v11, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v11

    invoke-static {v6, v11}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 709
    new-instance v6, Ljava/net/URL;

    invoke-direct {v6, v0}, Ljava/net/URL;-><init>(Ljava/lang/String;)V

    .line 711
    if-eqz v3, :cond_5

    sget-object v3, Ljava/net/Proxy;->NO_PROXY:Ljava/net/Proxy;

    invoke-virtual {v6, v3}, Ljava/net/URL;->openConnection(Ljava/net/Proxy;)Ljava/net/URLConnection;

    move-result-object v3

    goto :goto_2

    :cond_5
    invoke-virtual {v6}, Ljava/net/URL;->openConnection()Ljava/net/URLConnection;

    move-result-object v3

    :goto_2
    check-cast v3, Ljava/net/HttpURLConnection;

    .line 712
    const/16 v6, 0x3a98

    invoke-virtual {v3, v6}, Ljava/net/HttpURLConnection;->setConnectTimeout(I)V

    .line 713
    const/16 v6, 0x7530

    invoke-virtual {v3, v6}, Ljava/net/HttpURLConnection;->setReadTimeout(I)V

    .line 714
    const/4 v6, 0x0

    invoke-virtual {v3, v6}, Ljava/net/HttpURLConnection;->setUseCaches(Z)V

    .line 715
    const-string v11, "Accept-Encoding"

    const-string v13, "identity"

    invoke-virtual {v3, v11, v13}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 716
    const-string v11, "Connection"

    const-string v13, "close"

    invoke-virtual {v3, v11, v13}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 718
    invoke-static/range {p1 .. p1}, Lio/kamihama/magianative/CNDownloaderFix;->readSidecarEtag(Ljava/io/File;)Ljava/lang/String;

    move-result-object v11

    .line 719
    cmp-long v13, v9, v7

    if-lez v13, :cond_6

    .line 720
    new-instance v14, Ljava/lang/StringBuilder;

    invoke-direct {v14}, Ljava/lang/StringBuilder;-><init>()V

    const-string v15, "bytes="

    invoke-virtual {v14, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v14

    invoke-virtual {v14, v9, v10}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v14

    const-string v15, "-"

    invoke-virtual {v14, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v14

    invoke-virtual {v14}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v14

    const-string v15, "Range"

    invoke-virtual {v3, v15, v14}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 721
    invoke-virtual {v11}, Ljava/lang/String;->length()I

    move-result v14

    if-lez v14, :cond_6

    .line 722
    const-string v14, "If-Range"

    invoke-virtual {v3, v14, v11}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 726
    :cond_6
    nop

    .line 727
    nop

    .line 729
    :try_start_0
    invoke-virtual {v3}, Ljava/net/HttpURLConnection;->getResponseCode()I

    move-result v15

    .line 730
    const-string v14, "ETag"

    invoke-virtual {v3, v14}, Ljava/net/HttpURLConnection;->getHeaderField(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v14

    invoke-static {v14}, Lio/kamihama/magianative/CNDownloaderFix;->cleanHeader(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v14
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_8

    .line 736
    const/16 v6, 0xc8

    if-lez v13, :cond_8

    if-eq v15, v6, :cond_7

    goto :goto_5

    .line 738
    :cond_7
    :try_start_1
    invoke-static {v4}, Lio/kamihama/magianative/CNDownloaderFix;->truncate(Ljava/io/File;)V

    .line 739
    invoke-static {v5}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    .line 740
    invoke-static/range {p2 .. p2}, Lio/kamihama/magianative/CNDownloaderFix;->resetProgress(I)V

    .line 741
    new-instance v0, Lio/kamihama/magianative/CNDownloaderFix$ResetRequired;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "server returned 200 for Range offset "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v9, v10}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Lio/kamihama/magianative/CNDownloaderFix$ResetRequired;-><init>(Ljava/lang/String;)V

    throw v0
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 836
    :catchall_0
    move-exception v0

    :goto_3
    const/4 v2, 0x0

    :goto_4
    const/4 v14, 0x0

    goto/16 :goto_d

    .line 742
    :cond_8
    :goto_5
    const-string v7, "Content-Length"

    const-string v8, "Content-Range"

    if-lez v13, :cond_c

    const/16 v6, 0xce

    if-ne v15, v6, :cond_c

    .line 743
    :try_start_2
    invoke-virtual {v3, v8}, Ljava/net/HttpURLConnection;->getHeaderField(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lio/kamihama/magianative/CNDownloaderFix;->parseContentRange(Ljava/lang/String;)Lio/kamihama/magianative/CNDownloaderFix$ContentRange;

    move-result-object v0

    .line 744
    if-eqz v0, :cond_b

    iget-wide v12, v0, Lio/kamihama/magianative/CNDownloaderFix$ContentRange;->start:J

    cmp-long v6, v12, v9

    if-nez v6, :cond_b

    iget-wide v12, v0, Lio/kamihama/magianative/CNDownloaderFix$ContentRange;->end:J

    iget-wide v1, v0, Lio/kamihama/magianative/CNDownloaderFix$ContentRange;->start:J

    cmp-long v6, v12, v1

    if-ltz v6, :cond_b

    iget-wide v1, v0, Lio/kamihama/magianative/CNDownloaderFix$ContentRange;->total:J

    iget-wide v12, v0, Lio/kamihama/magianative/CNDownloaderFix$ContentRange;->end:J

    cmp-long v6, v1, v12

    if-lez v6, :cond_b

    .line 750
    invoke-virtual {v11}, Ljava/lang/String;->length()I

    move-result v1

    if-lez v1, :cond_a

    invoke-virtual {v14}, Ljava/lang/String;->length()I

    move-result v1

    if-lez v1, :cond_a

    invoke-virtual {v11, v14}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_9

    goto :goto_6

    .line 751
    :cond_9
    invoke-static {v4}, Lio/kamihama/magianative/CNDownloaderFix;->truncate(Ljava/io/File;)V

    .line 752
    invoke-static {v5}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    .line 753
    invoke-static/range {p2 .. p2}, Lio/kamihama/magianative/CNDownloaderFix;->resetProgress(I)V

    .line 754
    new-instance v0, Lio/kamihama/magianative/CNDownloaderFix$ResetRequired;

    const-string v1, "ETag changed while resuming"

    invoke-direct {v0, v1}, Lio/kamihama/magianative/CNDownloaderFix$ResetRequired;-><init>(Ljava/lang/String;)V

    throw v0

    .line 756
    :cond_a
    :goto_6
    iget-wide v1, v0, Lio/kamihama/magianative/CNDownloaderFix$ContentRange;->total:J

    .line 757
    iget-wide v11, v0, Lio/kamihama/magianative/CNDownloaderFix$ContentRange;->end:J

    move-wide/from16 v21, v1

    iget-wide v0, v0, Lio/kamihama/magianative/CNDownloaderFix$ContentRange;->start:J

    sub-long/2addr v11, v0

    const-wide/16 v0, 0x1

    add-long/2addr v11, v0

    .line 758
    nop

    .line 759
    const/4 v0, 0x1

    move-wide/from16 v23, v11

    move-wide/from16 v11, v21

    const-wide/16 v1, -0x1

    goto :goto_7

    .line 745
    :cond_b
    invoke-static {v4}, Lio/kamihama/magianative/CNDownloaderFix;->truncate(Ljava/io/File;)V

    .line 746
    invoke-static {v5}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    .line 747
    invoke-static/range {p2 .. p2}, Lio/kamihama/magianative/CNDownloaderFix;->resetProgress(I)V

    .line 748
    new-instance v0, Lio/kamihama/magianative/CNDownloaderFix$ResetRequired;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "invalid Content-Range for offset "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v9, v10}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Lio/kamihama/magianative/CNDownloaderFix$ResetRequired;-><init>(Ljava/lang/String;)V

    throw v0

    .line 759
    :cond_c
    if-nez v13, :cond_15

    const/16 v1, 0xc8

    if-ne v15, v1, :cond_15

    .line 760
    invoke-virtual {v3, v7}, Ljava/net/HttpURLConnection;->getHeaderField(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-wide/16 v1, -0x1

    invoke-static {v0, v1, v2}, Lio/kamihama/magianative/CNDownloaderFix;->parsePositiveLong(Ljava/lang/String;J)J

    move-result-wide v11

    .line 761
    nop

    .line 762
    move-wide/from16 v23, v11

    const/4 v0, 0x0

    .line 780
    :goto_7
    invoke-virtual {v3, v7}, Ljava/net/HttpURLConnection;->getHeaderField(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v6

    invoke-static {v6, v1, v2}, Lio/kamihama/magianative/CNDownloaderFix;->parsePositiveLong(Ljava/lang/String;J)J

    move-result-wide v1

    .line 781
    move-wide/from16 v6, v23

    const-wide/16 v16, 0x0

    cmp-long v8, v6, v16

    if-ltz v8, :cond_e

    cmp-long v13, v1, v16

    if-ltz v13, :cond_e

    cmp-long v13, v6, v1

    if-nez v13, :cond_d

    goto :goto_8

    .line 782
    :cond_d
    new-instance v0, Ljava/io/IOException;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "Content-Length mismatch expected="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v6, v7}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, " header="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v1, v2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v0

    .line 785
    :cond_e
    :goto_8
    const-wide/16 v1, 0x0

    cmp-long v13, v11, v1

    if-lez v13, :cond_14

    .line 789
    invoke-static {v5, v14, v11, v12}, Lio/kamihama/magianative/CNDownloaderFix;->writeSidecar(Ljava/io/File;Ljava/lang/String;J)V

    .line 790
    move/from16 v1, p2

    invoke-static {v1, v11, v12}, Lio/kamihama/magianative/CNDownloaderFix;->updateSize(IJ)V

    .line 791
    invoke-static {v1, v9, v10, v11, v12}, Lio/kamihama/magianative/CNDownloaderFix;->updateProgress(IJJ)V

    .line 793
    new-instance v2, Ljava/io/BufferedInputStream;

    invoke-virtual {v3}, Ljava/net/HttpURLConnection;->getInputStream()Ljava/io/InputStream;

    move-result-object v13

    const/high16 v15, 0x10000

    invoke-direct {v2, v13, v15}, Ljava/io/BufferedInputStream;-><init>(Ljava/io/InputStream;I)V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    .line 794
    :try_start_3
    new-instance v13, Ljava/io/FileOutputStream;

    invoke-direct {v13, v4, v0}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;Z)V
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_5

    .line 795
    :try_start_4
    move-object v0, v13

    check-cast v0, Ljava/io/FileOutputStream;

    .line 797
    new-array v0, v15, [B

    .line 798
    invoke-static {}, Ljava/lang/System;->nanoTime()J

    move-result-wide v18
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_4

    .line 799
    nop

    .line 800
    move-object/from16 v20, v14

    const-wide/16 v14, 0x0

    const-wide/16 v16, 0x0

    .line 802
    :goto_9
    move-object/from16 v21, v3

    :try_start_5
    invoke-virtual {v2, v0}, Ljava/io/InputStream;->read([B)I

    move-result v3

    if-ltz v3, :cond_10

    .line 803
    move-object/from16 v22, v5

    const/4 v5, 0x0

    invoke-virtual {v13, v0, v5, v3}, Ljava/io/FileOutputStream;->write([BII)V

    .line 804
    move-wide/from16 v23, v6

    int-to-long v5, v3

    add-long/2addr v14, v5

    .line 805
    invoke-static {}, Ljava/lang/System;->nanoTime()J

    move-result-wide v5

    .line 806
    sget-object v3, Lio/kamihama/magianative/CNDownloaderFix;->LAST_PROGRESS_NS:Ljava/util/concurrent/atomic/AtomicLongArray;

    invoke-virtual {v3, v1, v5, v6}, Ljava/util/concurrent/atomic/AtomicLongArray;->set(IJ)V
    :try_end_5
    .catchall {:try_start_5 .. :try_end_5} :catchall_3

    .line 807
    move-object/from16 p0, v2

    add-long v2, v9, v14

    :try_start_6
    invoke-static {v1, v2, v3, v11, v12}, Lio/kamihama/magianative/CNDownloaderFix;->updateProgress(IJJ)V

    .line 808
    sub-long v2, v5, v18

    .line 809
    sget-object v7, Ljava/util/concurrent/TimeUnit;->MILLISECONDS:Ljava/util/concurrent/TimeUnit;

    move-wide/from16 v25, v5

    const-wide/16 v5, 0x1f4

    invoke-virtual {v7, v5, v6}, Ljava/util/concurrent/TimeUnit;->toNanos(J)J

    move-result-wide v5

    cmp-long v7, v2, v5

    if-ltz v7, :cond_f

    .line 810
    sub-long v5, v14, v16

    long-to-double v5, v5

    const-wide v16, 0x41cdcd6500000000L    # 1.0E9

    mul-double v5, v5, v16

    long-to-double v2, v2

    div-double/2addr v5, v2

    const-wide v2, 0x412e848000000000L    # 1000000.0

    div-double/2addr v5, v2

    double-to-float v2, v5

    invoke-static {v1, v2}, Lio/kamihama/magianative/CNCNDownloadUI;->setDownloadSpeed(IF)V

    .line 812
    nop

    .line 813
    move-wide/from16 v16, v14

    move-wide/from16 v18, v25

    .line 815
    :cond_f
    move-object/from16 v2, p0

    move-object/from16 v3, v21

    move-object/from16 v5, v22

    move-wide/from16 v6, v23

    goto :goto_9

    .line 816
    :cond_10
    move-object/from16 p0, v2

    move-object/from16 v22, v5

    move-wide/from16 v23, v6

    invoke-virtual {v13}, Ljava/io/FileOutputStream;->flush()V

    .line 817
    invoke-virtual {v13}, Ljava/io/FileOutputStream;->getFD()Ljava/io/FileDescriptor;

    move-result-object v0

    invoke-virtual {v0}, Ljava/io/FileDescriptor;->sync()V

    .line 819
    if-ltz v8, :cond_12

    cmp-long v0, v14, v23

    if-nez v0, :cond_11

    goto :goto_a

    .line 820
    :cond_11
    new-instance v0, Ljava/io/IOException;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "Short response expected="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    move-wide/from16 v11, v23

    invoke-virtual {v1, v11, v12}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, " received="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v14, v15}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v0

    .line 823
    :cond_12
    :goto_a
    invoke-virtual {v4}, Ljava/io/File;->length()J

    move-result-wide v0

    .line 824
    cmp-long v2, v0, v11

    if-nez v2, :cond_13

    .line 829
    invoke-static {v13}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/OutputStream;)V
    :try_end_6
    .catchall {:try_start_6 .. :try_end_6} :catchall_2

    .line 830
    :try_start_7
    invoke-static/range {p0 .. p0}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/InputStream;)V
    :try_end_7
    .catchall {:try_start_7 .. :try_end_7} :catchall_1

    .line 832
    move-object/from16 v2, p1

    :try_start_8
    invoke-static {v4, v2}, Lio/kamihama/magianative/CNDownloaderFix;->promotePart(Ljava/io/File;Ljava/io/File;)V

    .line 833
    invoke-static/range {v22 .. v22}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    .line 834
    new-instance v0, Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;

    move-object/from16 v1, v20

    invoke-direct {v0, v11, v12, v1}, Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;-><init>(JLjava/lang/String;)V
    :try_end_8
    .catchall {:try_start_8 .. :try_end_8} :catchall_6

    .line 836
    const/4 v1, 0x0

    invoke-static {v1}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/OutputStream;)V

    .line 837
    invoke-static {v1}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/InputStream;)V

    .line 838
    invoke-virtual/range {v21 .. v21}, Ljava/net/HttpURLConnection;->disconnect()V

    .line 834
    return-object v0

    .line 836
    :catchall_1
    move-exception v0

    move-object/from16 v2, p0

    move-object/from16 v3, v21

    goto/16 :goto_4

    .line 825
    :cond_13
    :try_start_9
    new-instance v2, Ljava/io/IOException;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "Partial file length mismatch expected="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v11, v12}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, " actual="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v0, v1}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {v2, v0}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v2
    :try_end_9
    .catchall {:try_start_9 .. :try_end_9} :catchall_2

    .line 836
    :catchall_2
    move-exception v0

    move-object/from16 v2, p0

    goto :goto_b

    :catchall_3
    move-exception v0

    move-object/from16 p0, v2

    :goto_b
    move-object v14, v13

    move-object/from16 v3, v21

    goto/16 :goto_d

    :catchall_4
    move-exception v0

    move-object/from16 p0, v2

    move-object v14, v13

    goto/16 :goto_d

    :catchall_5
    move-exception v0

    move-object/from16 p0, v2

    goto/16 :goto_4

    .line 786
    :cond_14
    move-object/from16 v21, v3

    :try_start_a
    new-instance v0, Ljava/io/IOException;

    const-string v1, "Response does not declare a positive total length"

    invoke-direct {v0, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v0
    :try_end_a
    .catchall {:try_start_a .. :try_end_a} :catchall_6

    .line 836
    :catchall_6
    move-exception v0

    move-object/from16 v3, v21

    goto/16 :goto_3

    .line 759
    :cond_15
    move-object/from16 v2, p1

    move/from16 v1, p2

    move-object/from16 v21, v3

    move-object/from16 v22, v5

    .line 763
    if-lez v13, :cond_17

    const/16 v3, 0x1a0

    if-ne v15, v3, :cond_17

    .line 764
    move-object/from16 v3, v21

    :try_start_b
    invoke-virtual {v3, v8}, Ljava/net/HttpURLConnection;->getHeaderField(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lio/kamihama/magianative/CNDownloaderFix;->parseUnsatisfiedTotal(Ljava/lang/String;)J

    move-result-wide v5

    .line 765
    const-wide/16 v7, 0x0

    cmp-long v0, v5, v7

    if-lez v0, :cond_16

    cmp-long v0, v5, v9

    if-nez v0, :cond_16

    .line 772
    invoke-static {v4, v2}, Lio/kamihama/magianative/CNDownloaderFix;->promotePart(Ljava/io/File;Ljava/io/File;)V

    .line 773
    invoke-static/range {v22 .. v22}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    .line 774
    new-instance v0, Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;

    invoke-direct {v0, v5, v6, v11}, Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;-><init>(JLjava/lang/String;)V
    :try_end_b
    .catchall {:try_start_b .. :try_end_b} :catchall_8

    .line 836
    const/4 v2, 0x0

    invoke-static {v2}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/OutputStream;)V

    .line 837
    invoke-static {v2}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/InputStream;)V

    .line 838
    invoke-virtual {v3}, Ljava/net/HttpURLConnection;->disconnect()V

    .line 774
    return-object v0

    .line 765
    :cond_16
    const/4 v2, 0x0

    .line 766
    :try_start_c
    invoke-static {v4}, Lio/kamihama/magianative/CNDownloaderFix;->truncate(Ljava/io/File;)V

    .line 767
    invoke-static/range {v22 .. v22}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    .line 768
    invoke-static/range {p2 .. p2}, Lio/kamihama/magianative/CNDownloaderFix;->resetProgress(I)V

    .line 769
    new-instance v0, Lio/kamihama/magianative/CNDownloaderFix$ResetRequired;

    const-string v1, "HTTP 416 did not match local length"

    invoke-direct {v0, v1}, Lio/kamihama/magianative/CNDownloaderFix$ResetRequired;-><init>(Ljava/lang/String;)V

    throw v0

    .line 763
    :cond_17
    move-object/from16 v3, v21

    const/4 v2, 0x0

    .line 776
    new-instance v1, Ljava/io/IOException;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "Unexpected HTTP status "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v15}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v9, v10}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, " url="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {v1, v0}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v1
    :try_end_c
    .catchall {:try_start_c .. :try_end_c} :catchall_7

    .line 836
    :catchall_7
    move-exception v0

    goto :goto_c

    :catchall_8
    move-exception v0

    const/4 v2, 0x0

    :goto_c
    move-object v14, v2

    :goto_d
    invoke-static {v14}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/OutputStream;)V

    .line 837
    invoke-static {v2}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/InputStream;)V

    .line 838
    invoke-virtual {v3}, Ljava/net/HttpURLConnection;->disconnect()V

    .line 839
    throw v0
.end method

.method private static extractChecked(Ljava/io/File;Ljava/io/File;)V
    .locals 17
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 847
    move-object/from16 v0, p0

    move-object/from16 v1, p1

    invoke-virtual/range {p0 .. p0}, Ljava/io/File;->isFile()Z

    move-result v2

    if-eqz v2, :cond_e

    .line 850
    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->isDirectory()Z

    move-result v2

    if-nez v2, :cond_1

    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->mkdirs()Z

    move-result v2

    if-nez v2, :cond_1

    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->isDirectory()Z

    move-result v2

    if-eqz v2, :cond_0

    goto :goto_0

    .line 851
    :cond_0
    new-instance v0, Ljava/io/IOException;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Cannot create extraction root: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v0

    .line 853
    :cond_1
    :goto_0
    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->getCanonicalPath()Ljava/lang/String;

    move-result-object v2

    .line 854
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v3, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    sget-object v4, Ljava/io/File;->separator:Ljava/lang/String;

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    .line 856
    new-instance v4, Ljava/util/zip/ZipFile;

    invoke-direct {v4, v0}, Ljava/util/zip/ZipFile;-><init>(Ljava/io/File;)V

    .line 858
    :try_start_0
    invoke-virtual {v4}, Ljava/util/zip/ZipFile;->entries()Ljava/util/Enumeration;

    move-result-object v5

    .line 859
    const/4 v6, 0x0

    const/4 v7, 0x0

    .line 860
    :cond_2
    :goto_1
    invoke-interface {v5}, Ljava/util/Enumeration;->hasMoreElements()Z

    move-result v8

    if-eqz v8, :cond_c

    .line 861
    invoke-interface {v5}, Ljava/util/Enumeration;->nextElement()Ljava/lang/Object;

    move-result-object v8

    check-cast v8, Ljava/util/zip/ZipEntry;

    .line 862
    new-instance v9, Ljava/io/File;

    invoke-virtual {v8}, Ljava/util/zip/ZipEntry;->getName()Ljava/lang/String;

    move-result-object v10

    invoke-direct {v9, v1, v10}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    .line 863
    invoke-virtual {v9}, Ljava/io/File;->getCanonicalPath()Ljava/lang/String;

    move-result-object v10

    .line 865
    invoke-virtual {v10, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v11

    if-nez v11, :cond_4

    invoke-virtual {v10, v3}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v10

    if-eqz v10, :cond_3

    goto :goto_2

    .line 866
    :cond_3
    new-instance v0, Ljava/util/zip/ZipException;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "ZIP entry escapes extraction root: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v8}, Ljava/util/zip/ZipEntry;->getName()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/util/zip/ZipException;-><init>(Ljava/lang/String;)V

    throw v0

    .line 868
    :cond_4
    :goto_2
    invoke-virtual {v8}, Ljava/util/zip/ZipEntry;->isDirectory()Z

    move-result v10
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_3

    const-string v11, "Cannot create directory "

    if-eqz v10, :cond_6

    .line 869
    :try_start_1
    invoke-virtual {v9}, Ljava/io/File;->isDirectory()Z

    move-result v8

    if-nez v8, :cond_2

    invoke-virtual {v9}, Ljava/io/File;->mkdirs()Z

    move-result v8

    if-nez v8, :cond_2

    invoke-virtual {v9}, Ljava/io/File;->isDirectory()Z

    move-result v8

    if-eqz v8, :cond_5

    goto :goto_1

    .line 870
    :cond_5
    new-instance v0, Ljava/io/IOException;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v0

    .line 874
    :cond_6
    invoke-virtual {v9}, Ljava/io/File;->getParentFile()Ljava/io/File;

    move-result-object v7

    .line 875
    if-eqz v7, :cond_8

    invoke-virtual {v7}, Ljava/io/File;->isDirectory()Z

    move-result v10

    if-nez v10, :cond_8

    invoke-virtual {v7}, Ljava/io/File;->mkdirs()Z

    move-result v10

    if-nez v10, :cond_8

    .line 876
    invoke-virtual {v7}, Ljava/io/File;->isDirectory()Z

    move-result v10

    if-eqz v10, :cond_7

    goto :goto_3

    .line 877
    :cond_7
    new-instance v0, Ljava/io/IOException;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v0
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_3

    .line 879
    :cond_8
    :goto_3
    nop

    .line 880
    nop

    .line 882
    const/4 v7, 0x0

    :try_start_2
    new-instance v10, Ljava/io/BufferedInputStream;

    invoke-virtual {v4, v8}, Ljava/util/zip/ZipFile;->getInputStream(Ljava/util/zip/ZipEntry;)Ljava/io/InputStream;

    move-result-object v11

    const/high16 v12, 0x10000

    invoke-direct {v10, v11, v12}, Ljava/io/BufferedInputStream;-><init>(Ljava/io/InputStream;I)V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_2

    .line 883
    :try_start_3
    new-instance v11, Ljava/io/BufferedOutputStream;

    new-instance v13, Ljava/io/FileOutputStream;

    invoke-direct {v13, v9}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;)V

    invoke-direct {v11, v13, v12}, Ljava/io/BufferedOutputStream;-><init>(Ljava/io/OutputStream;I)V
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_1

    .line 884
    :try_start_4
    new-array v7, v12, [B

    .line 885
    const-wide/16 v12, 0x0

    move-wide v14, v12

    .line 887
    :goto_4
    invoke-virtual {v10, v7}, Ljava/io/InputStream;->read([B)I

    move-result v9

    if-ltz v9, :cond_9

    .line 888
    invoke-virtual {v11, v7, v6, v9}, Ljava/io/OutputStream;->write([BII)V

    .line 889
    move-object/from16 v16, v7

    int-to-long v6, v9

    add-long/2addr v14, v6

    move-object/from16 v7, v16

    const/4 v6, 0x0

    goto :goto_4

    .line 891
    :cond_9
    invoke-virtual {v11}, Ljava/io/OutputStream;->flush()V

    .line 892
    invoke-virtual {v8}, Ljava/util/zip/ZipEntry;->getSize()J

    move-result-wide v6

    cmp-long v9, v6, v12

    if-ltz v9, :cond_b

    invoke-virtual {v8}, Ljava/util/zip/ZipEntry;->getSize()J

    move-result-wide v6

    cmp-long v9, v14, v6

    if-nez v9, :cond_a

    goto :goto_5

    .line 893
    :cond_a
    new-instance v0, Ljava/util/zip/ZipException;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "Entry size mismatch: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v8}, Ljava/util/zip/ZipEntry;->getName()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/util/zip/ZipException;-><init>(Ljava/lang/String;)V

    throw v0
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_0

    .line 895
    :cond_b
    :goto_5
    nop

    .line 897
    :try_start_5
    invoke-static {v11}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/OutputStream;)V

    .line 898
    invoke-static {v10}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/InputStream;)V

    .line 899
    nop

    .line 900
    const/4 v7, 0x1

    const/4 v6, 0x0

    goto/16 :goto_1

    .line 897
    :catchall_0
    move-exception v0

    move-object v7, v11

    goto :goto_6

    :catchall_1
    move-exception v0

    goto :goto_6

    :catchall_2
    move-exception v0

    move-object v10, v7

    :goto_6
    invoke-static {v7}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/OutputStream;)V

    .line 898
    invoke-static {v10}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/InputStream;)V

    .line 899
    throw v0
    :try_end_5
    .catchall {:try_start_5 .. :try_end_5} :catchall_3

    .line 901
    :cond_c
    if-eqz v7, :cond_d

    .line 905
    invoke-virtual {v4}, Ljava/util/zip/ZipFile;->close()V

    .line 906
    nop

    .line 907
    return-void

    .line 902
    :cond_d
    :try_start_6
    new-instance v1, Ljava/util/zip/ZipException;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Archive contains no file entries: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {v1, v0}, Ljava/util/zip/ZipException;-><init>(Ljava/lang/String;)V

    throw v1
    :try_end_6
    .catchall {:try_start_6 .. :try_end_6} :catchall_3

    .line 905
    :catchall_3
    move-exception v0

    invoke-virtual {v4}, Ljava/util/zip/ZipFile;->close()V

    .line 906
    throw v0

    .line 848
    :cond_e
    new-instance v1, Ljava/io/IOException;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Archive is missing: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {v1, v0}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v1
.end method

.method private static extractJsonInt(Ljava/lang/String;Ljava/lang/String;)I
    .locals 2

    .line 193
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "\""

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {p1}, Ljava/util/regex/Pattern;->quote(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v0, "\"\\s*:\\s*(\\d+)"

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p1}, Ljava/util/regex/Pattern;->compile(Ljava/lang/String;)Ljava/util/regex/Pattern;

    move-result-object p1

    invoke-virtual {p1, p0}, Ljava/util/regex/Pattern;->matcher(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;

    move-result-object p0

    .line 194
    invoke-virtual {p0}, Ljava/util/regex/Matcher;->find()Z

    move-result p1

    const/4 v0, -0x1

    if-nez p1, :cond_0

    .line 195
    return v0

    .line 198
    :cond_0
    const/4 p1, 0x1

    :try_start_0
    invoke-virtual {p0, p1}, Ljava/util/regex/Matcher;->group(I)Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result p0
    :try_end_0
    .catch Ljava/lang/NumberFormatException; {:try_start_0 .. :try_end_0} :catch_0

    return p0

    .line 199
    :catch_0
    move-exception p0

    .line 200
    return v0
.end method

.method private static failInstaller(Ljava/lang/String;Ljava/lang/Throwable;)V
    .locals 1

    .line 1259
    invoke-static {}, Lio/kamihama/magianative/CNDownloaderFix;->zeroAllSpeeds()V

    .line 1260
    const-string v0, "MagiaCNDownloader"

    if-nez p1, :cond_0

    .line 1261
    invoke-static {v0, p0}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0

    .line 1263
    :cond_0
    invoke-static {v0, p0, p1}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 1265
    :goto_0
    const-string p1, "\u5b89\u88c5\u6682\u505c"

    const/4 v0, 0x0

    invoke-static {p1, p0, v0}, Lio/kamihama/magianative/CNCNDownloadUI;->updateSimple(Ljava/lang/String;Ljava/lang/String;I)V

    .line 1266
    return-void
.end method

.method private static fetchArchive(Lio/kamihama/magianative/CNMirrors$Mirror;Ljava/lang/String;Ljava/io/File;IZ)Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;
    .locals 15
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 615
    move-object v6, p0

    move-object/from16 v0, p1

    move/from16 v1, p3

    move/from16 v3, p4

    invoke-virtual/range {p2 .. p2}, Ljava/io/File;->isFile()Z

    move-result v2

    if-eqz v2, :cond_0

    .line 616
    invoke-virtual/range {p2 .. p2}, Ljava/io/File;->length()J

    move-result-wide v2

    .line 617
    invoke-static {v1, v2, v3}, Lio/kamihama/magianative/CNDownloaderFix;->updateSize(IJ)V

    .line 618
    new-instance v0, Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;

    invoke-static/range {p2 .. p2}, Lio/kamihama/magianative/CNDownloaderFix;->readSidecarEtag(Ljava/io/File;)Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v2, v3, v1}, Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;-><init>(JLjava/lang/String;)V

    return-object v0

    .line 621
    :cond_0
    invoke-virtual/range {p0 .. p1}, Lio/kamihama/magianative/CNMirrors$Mirror;->urlFor(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    .line 622
    invoke-virtual {p0}, Lio/kamihama/magianative/CNMirrors$Mirror;->effectiveChunks()I

    move-result v4

    .line 624
    const/4 v5, 0x1

    if-le v4, v5, :cond_3

    .line 625
    invoke-static {v2, v3}, Lio/kamihama/magianative/CNChunkedDownload;->probe(Ljava/lang/String;Z)Lio/kamihama/magianative/CNChunkedDownload$Probe;

    move-result-object v7

    .line 626
    iget-boolean v8, v7, Lio/kamihama/magianative/CNChunkedDownload$Probe;->rangeSupported:Z

    const-string v9, " mirror="

    const-string v10, "MagiaCNDownloader"

    if-eqz v8, :cond_2

    iget-wide v11, v7, Lio/kamihama/magianative/CNChunkedDownload$Probe;->total:J

    const-wide/16 v13, 0x0

    cmp-long v8, v11, v13

    if-lez v8, :cond_2

    .line 627
    nop

    .line 628
    invoke-static {}, Lio/kamihama/magianative/CNMirrors;->minChunkBytes()J

    move-result-wide v11

    .line 629
    cmp-long v8, v11, v13

    if-lez v8, :cond_1

    .line 630
    iget-wide v13, v7, Lio/kamihama/magianative/CNChunkedDownload$Probe;->total:J

    div-long/2addr v13, v11

    .line 631
    int-to-long v11, v4

    cmp-long v8, v13, v11

    if-gez v8, :cond_1

    const-wide/16 v11, 0x1

    invoke-static {v11, v12, v13, v14}, Ljava/lang/Math;->max(JJ)J

    move-result-wide v11

    long-to-int v4, v11

    .line 633
    :cond_1
    if-le v4, v5, :cond_2

    .line 634
    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "chunked-download file="

    invoke-virtual {v5, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v5, v6, Lio/kamihama/magianative/CNMirrors$Mirror;->name:Ljava/lang/String;

    invoke-virtual {v0, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v5, " chunks="

    invoke-virtual {v0, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v5, " bytes="

    invoke-virtual {v0, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-wide v8, v7, Lio/kamihama/magianative/CNChunkedDownload$Probe;->total:J

    invoke-virtual {v0, v8, v9}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v5, " direct="

    invoke-virtual {v0, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v10, v0}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 636
    iget-wide v8, v7, Lio/kamihama/magianative/CNChunkedDownload$Probe;->total:J

    invoke-static {v1, v8, v9}, Lio/kamihama/magianative/CNDownloaderFix;->updateSize(IJ)V

    .line 637
    iget-wide v8, v7, Lio/kamihama/magianative/CNChunkedDownload$Probe;->total:J

    const-wide/16 v10, 0x0

    invoke-static {v1, v10, v11, v8, v9}, Lio/kamihama/magianative/CNDownloaderFix;->updateProgress(IJJ)V

    .line 638
    new-instance v5, Lio/kamihama/magianative/CNDownloaderFix$ArchiveSink;

    invoke-direct {v5, v1}, Lio/kamihama/magianative/CNDownloaderFix$ArchiveSink;-><init>(I)V

    move-object v0, v2

    move-object/from16 v1, p2

    move v2, v4

    move/from16 v3, p4

    move-object v4, v7

    move-object v6, p0

    invoke-static/range {v0 .. v6}, Lio/kamihama/magianative/CNChunkedDownload;->download(Ljava/lang/String;Ljava/io/File;IZLio/kamihama/magianative/CNChunkedDownload$Probe;Lio/kamihama/magianative/CNChunkedDownload$Sink;Lio/kamihama/magianative/CNMirrors$Mirror;)Lio/kamihama/magianative/CNChunkedDownload$Result;

    move-result-object v0

    .line 640
    new-instance v1, Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;

    iget-wide v2, v0, Lio/kamihama/magianative/CNChunkedDownload$Result;->totalBytes:J

    iget-object v0, v0, Lio/kamihama/magianative/CNChunkedDownload$Result;->etag:Ljava/lang/String;

    invoke-direct {v1, v2, v3, v0}, Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;-><init>(JLjava/lang/String;)V

    return-object v1

    .line 643
    :cond_2
    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "range-unsupported-or-small file="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v4, v6, Lio/kamihama/magianative/CNMirrors$Mirror;->name:Ljava/lang/String;

    invoke-virtual {v0, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v4, " \u2192 \u5355\u7ebf\u7a0b\u7eed\u4f20"

    invoke-virtual {v0, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v10, v0}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 646
    :cond_3
    move-object/from16 v0, p2

    invoke-static {v2, v0, v1, v3}, Lio/kamihama/magianative/CNDownloaderFix;->downloadOnce(Ljava/lang/String;Ljava/io/File;IZ)Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;

    move-result-object v0

    return-object v0
.end method

.method public static getEndpoint(I)Ljava/lang/String;
    .locals 2

    .line 146
    :try_start_0
    invoke-static {}, Lio/kamihama/magianative/CNLog;->initEarly()V

    .line 147
    invoke-static {p0}, Lio/kamihama/magianative/CNDownloaderFix;->getEndpointInner(I)Ljava/lang/String;

    move-result-object p0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    return-object p0

    .line 148
    :catchall_0
    move-exception p0

    .line 149
    :try_start_1
    const-string v0, "MagiaCNDownloader"

    const-string v1, "getEndpoint \u53d1\u751f\u672a\u9884\u671f\u9519\u8bef\uff0c\u8fd4\u56de\u7a7a\u4e32"

    invoke-static {v0, v1, p0}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    goto :goto_0

    :catchall_1
    move-exception p0

    .line 150
    :goto_0
    const-string p0, ""

    return-object p0
.end method

.method private static getEndpointInner(I)Ljava/lang/String;
    .locals 8

    .line 155
    const-string v0, "snaa-response direct=true body="

    const-string v1, "https://totentanz-9b.magi-reco.com/magica/api/snaa"

    const/16 v2, 0x80

    invoke-static {p0, v2}, Ljava/lang/Math;->max(II)I

    move-result v2

    .line 156
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "{\"version\":"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, "}"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    .line 157
    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "snaa-request native_version="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, p0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string v4, " sent_version="

    invoke-virtual {p0, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    const-string v4, "MagiaCNDownloader"

    invoke-static {v4, p0}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 158
    nop

    .line 160
    const/4 p0, 0x1

    const/4 v5, 0x0

    const/4 v6, 0x0

    :try_start_0
    invoke-static {v1, v3, v6}, Lio/kamihama/magianative/CNDownloaderFix;->postJson(Ljava/lang/String;Ljava/lang/String;Z)Ljava/lang/String;

    move-result-object v5

    .line 161
    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "snaa-response direct=false body="

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v4, v6}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 162
    invoke-static {v5, v2}, Lio/kamihama/magianative/CNDownloaderFix;->isSnaaResponseCurrent(Ljava/lang/String;I)Z

    move-result v2

    if-eqz v2, :cond_0

    .line 163
    return-object v5

    .line 165
    :cond_0
    const-string v2, "SNAA response is stale/incompatible; retrying direct"

    invoke-static {v4, v2}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;)V

    .line 166
    invoke-static {v1, v3, p0}, Lio/kamihama/magianative/CNDownloaderFix;->postJson(Ljava/lang/String;Ljava/lang/String;Z)Ljava/lang/String;

    move-result-object v2

    .line 167
    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v6, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v4, v6}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    .line 168
    return-object v2

    .line 169
    :catch_0
    move-exception v2

    .line 170
    const-string v6, "SNAA via configured network failed; retrying direct"

    invoke-static {v4, v6, v2}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 172
    :try_start_1
    invoke-static {v1, v3, p0}, Lio/kamihama/magianative/CNDownloaderFix;->postJson(Ljava/lang/String;Ljava/lang/String;Z)Ljava/lang/String;

    move-result-object p0

    .line 173
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v4, v0}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_1
    .catch Ljava/io/IOException; {:try_start_1 .. :try_end_1} :catch_1

    .line 174
    return-object p0

    .line 175
    :catch_1
    move-exception p0

    .line 176
    invoke-virtual {p0, v2}, Ljava/io/IOException;->addSuppressed(Ljava/lang/Throwable;)V

    .line 177
    const-string v0, "SNAA discovery failed"

    invoke-static {v4, v0, p0}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 178
    if-nez v5, :cond_1

    const-string v5, ""

    :cond_1
    return-object v5
.end method

.method private static installArchive(I)Z
    .locals 14

    .line 518
    sget-object v0, Lio/kamihama/magianative/CNDownloaderFix;->FILE_NAMES:[Ljava/lang/String;

    aget-object v0, v0, p0

    .line 519
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "https://assets.magireco.top/"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 520
    new-instance v2, Ljava/io/File;

    const-string v3, "/data/data/io.kamihama.totentanz/files"

    invoke-direct {v2, v3, v0}, Ljava/io/File;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    .line 521
    invoke-static {v0}, Lio/kamihama/magianative/CNDownloaderFix;->markerFor(Ljava/lang/String;)Ljava/io/File;

    move-result-object v3

    .line 523
    invoke-static {v3, v0, v1}, Lio/kamihama/magianative/CNDownloaderFix;->isMarkerValid(Ljava/io/File;Ljava/lang/String;Ljava/lang/String;)Z

    move-result v4

    const/4 v5, 0x1

    if-eqz v4, :cond_0

    .line 524
    invoke-static {p0}, Lio/kamihama/magianative/CNDownloaderFix;->markDone(I)V

    .line 525
    const-string p0, "MagiaCNDownloader"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "marker-hit file="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p0, v0}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 526
    return v5

    .line 529
    :cond_0
    const/4 v4, 0x1

    :goto_0
    const/4 v6, 0x4

    const/4 v7, 0x0

    if-gt v4, v6, :cond_6

    .line 530
    invoke-static {}, Ljava/lang/Thread;->currentThread()Ljava/lang/Thread;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/Thread;->isInterrupted()Z

    move-result v8

    if-eqz v8, :cond_1

    .line 531
    invoke-static {p0}, Lio/kamihama/magianative/CNDownloaderFix;->markFailed(I)V

    .line 532
    return v7

    .line 535
    :cond_1
    invoke-static {v4}, Lio/kamihama/magianative/CNMirrors;->pick(I)Lio/kamihama/magianative/CNMirrors$Mirror;

    move-result-object v8

    .line 536
    rem-int/lit8 v9, v4, 0x2

    if-nez v9, :cond_2

    const/4 v9, 0x1

    goto :goto_1

    :cond_2
    const/4 v9, 0x0

    .line 538
    :goto_1
    invoke-static {p0, v5}, Lio/kamihama/magianative/CNDownloaderFix;->setActive(IZ)V

    .line 539
    const/4 v10, 0x0

    invoke-static {p0, v10}, Lio/kamihama/magianative/CNCNDownloadUI;->setDownloadSpeed(IF)V

    .line 541
    :try_start_0
    invoke-static {v8, v0, v2, p0, v9}, Lio/kamihama/magianative/CNDownloaderFix;->fetchArchive(Lio/kamihama/magianative/CNMirrors$Mirror;Ljava/lang/String;Ljava/io/File;IZ)Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;

    move-result-object v9

    .line 542
    sget-object v11, Lio/kamihama/magianative/CNDownloaderFix;->EXTRACT_LOCK:Ljava/lang/Object;

    monitor-enter v11
    :try_end_0
    .catch Lio/kamihama/magianative/CNDownloaderFix$ResetRequired; {:try_start_0 .. :try_end_0} :catch_3
    .catch Ljava/util/zip/ZipException; {:try_start_0 .. :try_end_0} :catch_2
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_1
    .catch Ljava/lang/RuntimeException; {:try_start_0 .. :try_end_0} :catch_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    .line 543
    :try_start_1
    new-instance v12, Ljava/io/File;

    const-string v13, "/data/data/io.kamihama.totentanz/files/"

    invoke-direct {v12, v13}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-static {v2, v12}, Lio/kamihama/magianative/CNDownloaderFix;->extractChecked(Ljava/io/File;Ljava/io/File;)V

    .line 544
    monitor-exit v11
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 545
    :try_start_2
    invoke-static {v3, v0, v1, v9}, Lio/kamihama/magianative/CNDownloaderFix;->writeMarker(Ljava/io/File;Ljava/lang/String;Ljava/lang/String;Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;)V

    .line 546
    invoke-virtual {v2}, Ljava/io/File;->delete()Z

    move-result v9

    if-nez v9, :cond_3

    invoke-virtual {v2}, Ljava/io/File;->exists()Z

    move-result v9

    if-eqz v9, :cond_3

    .line 547
    const-string v9, "MagiaCNDownloader"

    new-instance v11, Ljava/lang/StringBuilder;

    invoke-direct {v11}, Ljava/lang/StringBuilder;-><init>()V

    const-string v12, "Installed archive retained because delete failed: "

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v11

    invoke-static {v9, v11}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;)V

    .line 551
    :cond_3
    new-instance v9, Ljava/io/File;

    new-instance v11, Ljava/lang/StringBuilder;

    invoke-direct {v11}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v2}, Ljava/io/File;->getPath()Ljava/lang/String;

    move-result-object v12

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    const-string v12, ".part"

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v11

    invoke-direct {v9, v11}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-static {v9}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    .line 552
    new-instance v9, Ljava/io/File;

    new-instance v11, Ljava/lang/StringBuilder;

    invoke-direct {v11}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v2}, Ljava/io/File;->getPath()Ljava/lang/String;

    move-result-object v12

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    const-string v12, ".part.meta"

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v11

    invoke-direct {v9, v11}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-static {v9}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    .line 553
    invoke-static {v2}, Lio/kamihama/magianative/CNChunkedDownload;->partFileFor(Ljava/io/File;)Ljava/io/File;

    move-result-object v9

    invoke-static {v9}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    .line 554
    invoke-static {v2}, Lio/kamihama/magianative/CNChunkedDownload;->metaFileFor(Ljava/io/File;)Ljava/io/File;

    move-result-object v9

    invoke-static {v9}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    .line 555
    invoke-static {v8}, Lio/kamihama/magianative/CNMirrors;->reportSuccess(Lio/kamihama/magianative/CNMirrors$Mirror;)V

    .line 556
    invoke-static {p0}, Lio/kamihama/magianative/CNDownloaderFix;->markDone(I)V

    .line 557
    const-string v9, "MagiaCNDownloader"

    new-instance v11, Ljava/lang/StringBuilder;

    invoke-direct {v11}, Ljava/lang/StringBuilder;-><init>()V

    const-string v12, "installed file="

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    const-string v12, " attempt="

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v11

    const-string v12, " mirror="

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    iget-object v12, v8, Lio/kamihama/magianative/CNMirrors$Mirror;->name:Ljava/lang/String;

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v11

    invoke-static {v9, v11}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_2
    .catch Lio/kamihama/magianative/CNDownloaderFix$ResetRequired; {:try_start_2 .. :try_end_2} :catch_3
    .catch Ljava/util/zip/ZipException; {:try_start_2 .. :try_end_2} :catch_2
    .catch Ljava/io/IOException; {:try_start_2 .. :try_end_2} :catch_1
    .catch Ljava/lang/RuntimeException; {:try_start_2 .. :try_end_2} :catch_0
    .catchall {:try_start_2 .. :try_end_2} :catchall_1

    .line 559
    nop

    .line 582
    invoke-static {p0, v7}, Lio/kamihama/magianative/CNDownloaderFix;->setActive(IZ)V

    .line 583
    invoke-static {p0, v10}, Lio/kamihama/magianative/CNCNDownloadUI;->setDownloadSpeed(IF)V

    .line 584
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->throttledUpdate()V

    .line 559
    return v5

    .line 544
    :catchall_0
    move-exception v9

    :try_start_3
    monitor-exit v11
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_0

    :try_start_4
    throw v9
    :try_end_4
    .catch Lio/kamihama/magianative/CNDownloaderFix$ResetRequired; {:try_start_4 .. :try_end_4} :catch_3
    .catch Ljava/util/zip/ZipException; {:try_start_4 .. :try_end_4} :catch_2
    .catch Ljava/io/IOException; {:try_start_4 .. :try_end_4} :catch_1
    .catch Ljava/lang/RuntimeException; {:try_start_4 .. :try_end_4} :catch_0
    .catchall {:try_start_4 .. :try_end_4} :catchall_1

    .line 582
    :catchall_1
    move-exception v0

    goto/16 :goto_4

    .line 578
    :catch_0
    move-exception v9

    .line 579
    :try_start_5
    const-string v11, "MagiaCNDownloader"

    new-instance v12, Ljava/lang/StringBuilder;

    invoke-direct {v12}, Ljava/lang/StringBuilder;-><init>()V

    const-string v13, "archive-runtime-failure file="

    invoke-virtual {v12, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v12

    invoke-virtual {v12, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v12

    const-string v13, " attempt="

    invoke-virtual {v12, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v12

    invoke-virtual {v12, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v12

    invoke-virtual {v12}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v12

    invoke-static {v11, v12, v9}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 580
    new-instance v11, Ljava/lang/StringBuilder;

    invoke-direct {v11}, Ljava/lang/StringBuilder;-><init>()V

    const-string v12, "runtime:"

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9}, Lio/kamihama/magianative/CNMirrors;->reportFailure(Lio/kamihama/magianative/CNMirrors$Mirror;Ljava/lang/String;)V

    goto/16 :goto_2

    .line 571
    :catch_1
    move-exception v9

    .line 572
    const-string v11, "MagiaCNDownloader"

    new-instance v12, Ljava/lang/StringBuilder;

    invoke-direct {v12}, Ljava/lang/StringBuilder;-><init>()V

    const-string v13, "archive-failed file="

    invoke-virtual {v12, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v12

    invoke-virtual {v12, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v12

    const-string v13, " attempt="

    invoke-virtual {v12, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v12

    invoke-virtual {v12, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v12

    const-string v13, " mirror="

    invoke-virtual {v12, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v12

    iget-object v13, v8, Lio/kamihama/magianative/CNMirrors$Mirror;->name:Ljava/lang/String;

    invoke-virtual {v12, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v12

    invoke-virtual {v12}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v12

    invoke-static {v11, v12, v9}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 574
    invoke-virtual {v9}, Ljava/io/IOException;->getMessage()Ljava/lang/String;

    move-result-object v9

    invoke-static {v9}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9}, Lio/kamihama/magianative/CNMirrors;->reportFailure(Lio/kamihama/magianative/CNMirrors$Mirror;Ljava/lang/String;)V

    .line 575
    invoke-virtual {v2}, Ljava/io/File;->isFile()Z

    move-result v8

    if-eqz v8, :cond_4

    .line 576
    invoke-static {v2}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    goto/16 :goto_2

    .line 563
    :catch_2
    move-exception v9

    .line 564
    const-string v11, "MagiaCNDownloader"

    new-instance v12, Ljava/lang/StringBuilder;

    invoke-direct {v12}, Ljava/lang/StringBuilder;-><init>()V

    const-string v13, "corrupt-zip file="

    invoke-virtual {v12, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v12

    invoke-virtual {v12, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v12

    const-string v13, " attempt="

    invoke-virtual {v12, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v12

    invoke-virtual {v12, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v12

    invoke-virtual {v12}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v12

    invoke-static {v11, v12, v9}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 565
    const-string v9, "corrupt-zip"

    invoke-static {v8, v9}, Lio/kamihama/magianative/CNMirrors;->reportFailure(Lio/kamihama/magianative/CNMirrors$Mirror;Ljava/lang/String;)V

    .line 566
    invoke-static {v2}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    .line 567
    new-instance v8, Ljava/io/File;

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v2}, Ljava/io/File;->getPath()Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v9, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    const-string v11, ".part"

    invoke-virtual {v9, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-direct {v8, v9}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-static {v8}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    .line 568
    new-instance v8, Ljava/io/File;

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v2}, Ljava/io/File;->getPath()Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v9, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    const-string v11, ".part.meta"

    invoke-virtual {v9, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-direct {v8, v9}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-static {v8}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    .line 569
    invoke-static {v2}, Lio/kamihama/magianative/CNChunkedDownload;->partFileFor(Ljava/io/File;)Ljava/io/File;

    move-result-object v8

    invoke-static {v8}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    .line 570
    invoke-static {v2}, Lio/kamihama/magianative/CNChunkedDownload;->metaFileFor(Ljava/io/File;)Ljava/io/File;

    move-result-object v8

    invoke-static {v8}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    goto :goto_2

    .line 560
    :catch_3
    move-exception v8

    .line 561
    const-string v9, "MagiaCNDownloader"

    new-instance v11, Ljava/lang/StringBuilder;

    invoke-direct {v11}, Ljava/lang/StringBuilder;-><init>()V

    const-string v12, "resume-reset file="

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    const-string v12, " attempt="

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v11

    const-string v12, " reason="

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    .line 562
    invoke-virtual {v8}, Lio/kamihama/magianative/CNDownloaderFix$ResetRequired;->getMessage()Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v11, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    .line 561
    invoke-static {v9, v8}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_5
    .catchall {:try_start_5 .. :try_end_5} :catchall_1

    .line 582
    :cond_4
    :goto_2
    invoke-static {p0, v7}, Lio/kamihama/magianative/CNDownloaderFix;->setActive(IZ)V

    .line 583
    invoke-static {p0, v10}, Lio/kamihama/magianative/CNCNDownloadUI;->setDownloadSpeed(IF)V

    .line 584
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->throttledUpdate()V

    .line 585
    nop

    .line 587
    if-ge v4, v6, :cond_5

    .line 588
    add-int/lit8 v6, v4, -0x1

    const-wide/16 v8, 0x7d0

    shl-long/2addr v8, v6

    .line 589
    const-string v6, "MagiaCNDownloader"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "retry-wait file="

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    const-string v11, " delay_ms="

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, v8, v9}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v6, v10}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 591
    :try_start_6
    invoke-static {v8, v9}, Ljava/lang/Thread;->sleep(J)V
    :try_end_6
    .catch Ljava/lang/InterruptedException; {:try_start_6 .. :try_end_6} :catch_4

    .line 596
    goto :goto_3

    .line 592
    :catch_4
    move-exception v0

    .line 593
    invoke-static {}, Ljava/lang/Thread;->currentThread()Ljava/lang/Thread;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Thread;->interrupt()V

    .line 594
    invoke-static {p0}, Lio/kamihama/magianative/CNDownloaderFix;->markFailed(I)V

    .line 595
    return v7

    .line 529
    :cond_5
    :goto_3
    add-int/lit8 v4, v4, 0x1

    goto/16 :goto_0

    .line 582
    :goto_4
    invoke-static {p0, v7}, Lio/kamihama/magianative/CNDownloaderFix;->setActive(IZ)V

    .line 583
    invoke-static {p0, v10}, Lio/kamihama/magianative/CNCNDownloadUI;->setDownloadSpeed(IF)V

    .line 584
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->throttledUpdate()V

    .line 585
    throw v0

    .line 600
    :cond_6
    invoke-static {p0}, Lio/kamihama/magianative/CNDownloaderFix;->markFailed(I)V

    .line 601
    const-string p0, "MagiaCNDownloader"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "retry-exhausted file="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p0, v0}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;)V

    .line 602
    return v7
.end method

.method private static isMarkerValid(Ljava/io/File;Ljava/lang/String;Ljava/lang/String;)Z
    .locals 7

    .line 998
    const-string v0, "\n"

    invoke-virtual {p0}, Ljava/io/File;->isFile()Z

    move-result v1

    const/4 v2, 0x0

    if-eqz v1, :cond_2

    invoke-virtual {p0}, Ljava/io/File;->length()J

    move-result-wide v3

    const-wide/16 v5, 0x0

    cmp-long v1, v3, v5

    if-lez v1, :cond_2

    invoke-virtual {p0}, Ljava/io/File;->length()J

    move-result-wide v3

    const-wide/16 v5, 0x4000

    cmp-long v1, v3, v5

    if-lez v1, :cond_0

    goto :goto_0

    .line 1002
    :cond_0
    :try_start_0
    invoke-static {p0}, Lio/kamihama/magianative/CNDownloaderFix;->readSmallUtf8(Ljava/io/File;)Ljava/lang/String;

    move-result-object v1

    .line 1003
    const-string v3, "schema=1\n"

    invoke-virtual {v1, v3}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v3

    if-eqz v3, :cond_1

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "file="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    .line 1004
    invoke-virtual {v1, p1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result p1

    if-eqz p1, :cond_1

    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "url="

    invoke-virtual {p1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    .line 1005
    invoke-virtual {v1, p1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result p1

    if-eqz p1, :cond_1

    .line 1006
    const-string p1, "(?s).*\\nbytes=[1-9][0-9]*\\n.*"

    invoke-virtual {v1, p1}, Ljava/lang/String;->matches(Ljava/lang/String;)Z

    move-result p0
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    return p0

    .line 1008
    :cond_1
    return v2

    .line 1009
    :catch_0
    move-exception p1

    .line 1010
    new-instance p2, Ljava/lang/StringBuilder;

    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "Cannot read marker "

    invoke-virtual {p2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    const-string p2, "MagiaCNDownloader"

    invoke-static {p2, p0, p1}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 1011
    return v2

    .line 999
    :cond_2
    :goto_0
    return v2
.end method

.method private static isSnaaResponseCurrent(Ljava/lang/String;I)Z
    .locals 3

    .line 184
    const/4 v0, 0x0

    if-eqz p0, :cond_2

    const-string v1, "(?s).*\"endpoint\"\\s*:\\s*\"https://[^\"]+\".*"

    invoke-virtual {p0, v1}, Ljava/lang/String;->matches(Ljava/lang/String;)Z

    move-result v1

    if-nez v1, :cond_0

    goto :goto_1

    .line 187
    :cond_0
    const-string v1, "status"

    invoke-static {p0, v1}, Lio/kamihama/magianative/CNDownloaderFix;->extractJsonInt(Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    const/16 v2, 0xc8

    if-ne v1, v2, :cond_1

    .line 188
    const-string v1, "version"

    invoke-static {p0, v1}, Lio/kamihama/magianative/CNDownloaderFix;->extractJsonInt(Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    if-lt v1, p1, :cond_1

    .line 189
    const-string p1, "max_threads"

    invoke-static {p0, p1}, Lio/kamihama/magianative/CNDownloaderFix;->extractJsonInt(Ljava/lang/String;Ljava/lang/String;)I

    move-result p0

    if-lez p0, :cond_1

    const/4 v0, 0x1

    goto :goto_0

    :cond_1
    nop

    .line 187
    :goto_0
    return v0

    .line 185
    :cond_2
    :goto_1
    return v0
.end method

.method private static markDone(I)V
    .locals 1

    .line 1230
    const/4 v0, 0x0

    invoke-static {p0, v0}, Lio/kamihama/magianative/CNDownloaderFix;->setActive(IZ)V

    .line 1231
    const/4 v0, 0x0

    invoke-static {p0, v0}, Lio/kamihama/magianative/CNCNDownloadUI;->setDownloadSpeed(IF)V

    .line 1232
    invoke-static {p0}, Lio/kamihama/magianative/CNCNDownloadUI;->markFileDone(I)V

    .line 1233
    return-void
.end method

.method private static markFailed(I)V
    .locals 2

    .line 1236
    const/4 v0, 0x0

    invoke-static {p0, v0}, Lio/kamihama/magianative/CNDownloaderFix;->setActive(IZ)V

    .line 1237
    const/4 v0, 0x0

    invoke-static {p0, v0}, Lio/kamihama/magianative/CNCNDownloadUI;->setDownloadSpeed(IF)V

    .line 1238
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->fileStatus:[I

    if-eqz v0, :cond_0

    .line 1239
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->fileStatus:[I

    const/4 v1, 0x3

    aput v1, v0, p0

    .line 1241
    :cond_0
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->throttledUpdate()V

    .line 1242
    return-void
.end method

.method private static markerFor(Ljava/lang/String;)Ljava/io/File;
    .locals 2

    .line 1026
    new-instance v0, Ljava/io/File;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string v1, ".done"

    invoke-virtual {p0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    const-string v1, "/data/data/io.kamihama.totentanz/files/madomagi/magica/.cn_installer/r128-downloader-v1"

    invoke-direct {v0, v1, p0}, Ljava/io/File;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    return-object v0
.end method

.method private static parseContentRange(Ljava/lang/String;)Lio/kamihama/magianative/CNDownloaderFix$ContentRange;
    .locals 13

    .line 1140
    const/4 v0, 0x0

    if-nez p0, :cond_0

    .line 1141
    return-object v0

    .line 1143
    :cond_0
    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    sget-object v1, Ljava/util/Locale;->US:Ljava/util/Locale;

    invoke-virtual {p0, v1}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object p0

    .line 1144
    const-string v1, "bytes "

    invoke-virtual {p0, v1}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v1

    if-nez v1, :cond_1

    .line 1145
    return-object v0

    .line 1147
    :cond_1
    const/16 v1, 0x2d

    const/4 v2, 0x6

    invoke-virtual {p0, v1, v2}, Ljava/lang/String;->indexOf(II)I

    move-result v1

    .line 1148
    add-int/lit8 v3, v1, 0x1

    const/16 v4, 0x2f

    invoke-virtual {p0, v4, v3}, Ljava/lang/String;->indexOf(II)I

    move-result v4

    .line 1149
    if-ltz v1, :cond_3

    if-gez v4, :cond_2

    goto :goto_0

    .line 1153
    :cond_2
    :try_start_0
    new-instance v12, Lio/kamihama/magianative/CNDownloaderFix$ContentRange;

    .line 1154
    invoke-virtual {p0, v2, v1}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v6

    .line 1155
    invoke-virtual {p0, v3, v4}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v8

    add-int/lit8 v4, v4, 0x1

    .line 1156
    invoke-virtual {p0, v4}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v10

    move-object v5, v12

    invoke-direct/range {v5 .. v11}, Lio/kamihama/magianative/CNDownloaderFix$ContentRange;-><init>(JJJ)V
    :try_end_0
    .catch Ljava/lang/NumberFormatException; {:try_start_0 .. :try_end_0} :catch_0

    .line 1153
    return-object v12

    .line 1157
    :catch_0
    move-exception p0

    .line 1158
    return-object v0

    .line 1150
    :cond_3
    :goto_0
    return-object v0
.end method

.method private static parsePositiveLong(Ljava/lang/String;J)J
    .locals 4

    .line 1174
    if-nez p0, :cond_0

    .line 1175
    return-wide p1

    .line 1178
    :cond_0
    :try_start_0
    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v0
    :try_end_0
    .catch Ljava/lang/NumberFormatException; {:try_start_0 .. :try_end_0} :catch_0

    .line 1179
    const-wide/16 v2, 0x0

    cmp-long p0, v0, v2

    if-ltz p0, :cond_1

    move-wide p1, v0

    :cond_1
    return-wide p1

    .line 1180
    :catch_0
    move-exception p0

    .line 1181
    return-wide p1
.end method

.method private static parseUnsatisfiedTotal(Ljava/lang/String;)J
    .locals 3

    .line 1163
    const-wide/16 v0, -0x1

    if-nez p0, :cond_0

    .line 1164
    return-wide v0

    .line 1166
    :cond_0
    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    sget-object v2, Ljava/util/Locale;->US:Ljava/util/Locale;

    invoke-virtual {p0, v2}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object p0

    .line 1167
    const-string v2, "bytes */"

    invoke-virtual {p0, v2}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v2

    if-nez v2, :cond_1

    .line 1168
    return-wide v0

    .line 1170
    :cond_1
    const/16 v2, 0x8

    invoke-virtual {p0, v2}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object p0

    invoke-static {p0, v0, v1}, Lio/kamihama/magianative/CNDownloaderFix;->parsePositiveLong(Ljava/lang/String;J)J

    move-result-wide v0

    return-wide v0
.end method

.method private static postJson(Ljava/lang/String;Ljava/lang/String;Z)Ljava/lang/String;
    .locals 4
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 950
    new-instance v0, Ljava/net/URL;

    invoke-direct {v0, p0}, Ljava/net/URL;-><init>(Ljava/lang/String;)V

    .line 952
    if-eqz p2, :cond_0

    sget-object p0, Ljava/net/Proxy;->NO_PROXY:Ljava/net/Proxy;

    invoke-virtual {v0, p0}, Ljava/net/URL;->openConnection(Ljava/net/Proxy;)Ljava/net/URLConnection;

    move-result-object p0

    goto :goto_0

    :cond_0
    invoke-virtual {v0}, Ljava/net/URL;->openConnection()Ljava/net/URLConnection;

    move-result-object p0

    :goto_0
    check-cast p0, Ljava/net/HttpURLConnection;

    .line 953
    const/16 p2, 0x3a98

    invoke-virtual {p0, p2}, Ljava/net/HttpURLConnection;->setConnectTimeout(I)V

    .line 954
    const/16 p2, 0x7530

    invoke-virtual {p0, p2}, Ljava/net/HttpURLConnection;->setReadTimeout(I)V

    .line 955
    const-string p2, "POST"

    invoke-virtual {p0, p2}, Ljava/net/HttpURLConnection;->setRequestMethod(Ljava/lang/String;)V

    .line 956
    const/4 p2, 0x1

    invoke-virtual {p0, p2}, Ljava/net/HttpURLConnection;->setDoOutput(Z)V

    .line 957
    const/4 p2, 0x0

    invoke-virtual {p0, p2}, Ljava/net/HttpURLConnection;->setUseCaches(Z)V

    .line 958
    const-string v0, "Content-Type"

    const-string v1, "application/json; charset=utf-8"

    invoke-virtual {p0, v0, v1}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 959
    const-string v0, "Accept"

    const-string v1, "application/json"

    invoke-virtual {p0, v0, v1}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 960
    const-string v0, "Connection"

    const-string v1, "close"

    invoke-virtual {p0, v0, v1}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 962
    nop

    .line 963
    nop

    .line 965
    const/4 v0, 0x0

    :try_start_0
    sget-object v1, Ljava/nio/charset/StandardCharsets;->UTF_8:Ljava/nio/charset/Charset;

    invoke-virtual {p1, v1}, Ljava/lang/String;->getBytes(Ljava/nio/charset/Charset;)[B

    move-result-object p1

    .line 966
    array-length v1, p1

    invoke-virtual {p0, v1}, Ljava/net/HttpURLConnection;->setFixedLengthStreamingMode(I)V

    .line 967
    invoke-virtual {p0}, Ljava/net/HttpURLConnection;->getOutputStream()Ljava/io/OutputStream;

    move-result-object v1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    .line 968
    :try_start_1
    invoke-virtual {v1, p1}, Ljava/io/OutputStream;->write([B)V

    .line 969
    invoke-virtual {v1}, Ljava/io/OutputStream;->flush()V

    .line 971
    invoke-virtual {p0}, Ljava/net/HttpURLConnection;->getResponseCode()I

    move-result p1

    .line 972
    const/16 v2, 0xc8

    if-lt p1, v2, :cond_2

    const/16 v2, 0x12c

    if-ge p1, v2, :cond_2

    .line 975
    invoke-virtual {p0}, Ljava/net/HttpURLConnection;->getInputStream()Ljava/io/InputStream;

    move-result-object v0

    .line 976
    new-instance p1, Ljava/io/ByteArrayOutputStream;

    invoke-direct {p1}, Ljava/io/ByteArrayOutputStream;-><init>()V

    .line 977
    const/16 v2, 0x2000

    new-array v2, v2, [B

    .line 979
    :goto_1
    invoke-virtual {v0, v2}, Ljava/io/InputStream;->read([B)I

    move-result v3

    if-ltz v3, :cond_1

    .line 980
    invoke-virtual {p1, v2, p2, v3}, Ljava/io/ByteArrayOutputStream;->write([BII)V

    goto :goto_1

    .line 982
    :cond_1
    new-instance p2, Ljava/lang/String;

    invoke-virtual {p1}, Ljava/io/ByteArrayOutputStream;->toByteArray()[B

    move-result-object p1

    sget-object v2, Ljava/nio/charset/StandardCharsets;->UTF_8:Ljava/nio/charset/Charset;

    invoke-direct {p2, p1, v2}, Ljava/lang/String;-><init>([BLjava/nio/charset/Charset;)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 984
    invoke-static {v1}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/OutputStream;)V

    .line 985
    invoke-static {v0}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/InputStream;)V

    .line 986
    invoke-virtual {p0}, Ljava/net/HttpURLConnection;->disconnect()V

    .line 982
    return-object p2

    .line 973
    :cond_2
    :try_start_2
    new-instance p2, Ljava/io/IOException;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "SNAA returned HTTP "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-direct {p2, p1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw p2
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    .line 984
    :catchall_0
    move-exception p1

    move-object p2, v0

    move-object v0, v1

    goto :goto_2

    :catchall_1
    move-exception p1

    move-object p2, v0

    :goto_2
    invoke-static {v0}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/OutputStream;)V

    .line 985
    invoke-static {p2}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/InputStream;)V

    .line 986
    invoke-virtual {p0}, Ljava/net/HttpURLConnection;->disconnect()V

    .line 987
    throw p1
.end method

.method private static probeAllSizes()V
    .locals 8

    .line 421
    const-string v0, "\u51c6\u5907\u4e2d"

    const-string v1, "\u6b63\u5728\u83b7\u53d6\u6587\u4ef6\u5927\u5c0f\u2026"

    const/4 v2, 0x0

    invoke-static {v0, v1, v2}, Lio/kamihama/magianative/CNCNDownloadUI;->updateSimple(Ljava/lang/String;Ljava/lang/String;I)V

    .line 422
    const/4 v0, 0x4

    invoke-static {v0}, Ljava/util/concurrent/Executors;->newFixedThreadPool(I)Ljava/util/concurrent/ExecutorService;

    move-result-object v0

    .line 423
    new-instance v1, Ljava/util/ArrayList;

    const/16 v3, 0xf

    invoke-direct {v1, v3}, Ljava/util/ArrayList;-><init>(I)V

    .line 424
    const/4 v4, 0x0

    :goto_0
    if-ge v4, v3, :cond_0

    .line 425
    new-instance v5, Lio/kamihama/magianative/CNDownloaderFix$SizeProbeTask;

    invoke-direct {v5, v4}, Lio/kamihama/magianative/CNDownloaderFix$SizeProbeTask;-><init>(I)V

    invoke-interface {v0, v5}, Ljava/util/concurrent/ExecutorService;->submit(Ljava/util/concurrent/Callable;)Ljava/util/concurrent/Future;

    move-result-object v5

    invoke-interface {v1, v5}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 424
    add-int/lit8 v4, v4, 0x1

    goto :goto_0

    .line 427
    :cond_0
    invoke-interface {v0}, Ljava/util/concurrent/ExecutorService;->shutdown()V

    .line 428
    const/4 v4, 0x0

    :goto_1
    invoke-interface {v1}, Ljava/util/List;->size()I

    move-result v5

    if-ge v4, v5, :cond_1

    .line 429
    :try_start_0
    invoke-interface {v1, v4}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/util/concurrent/Future;

    invoke-interface {v5}, Ljava/util/concurrent/Future;->get()Ljava/lang/Object;
    :try_end_0
    .catch Ljava/lang/InterruptedException; {:try_start_0 .. :try_end_0} :catch_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    goto :goto_2

    .line 431
    :catchall_0
    move-exception v5

    goto :goto_2

    .line 430
    :catch_0
    move-exception v5

    invoke-static {}, Ljava/lang/Thread;->currentThread()Ljava/lang/Thread;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/Thread;->interrupt()V

    .line 431
    :goto_2
    nop

    .line 428
    add-int/lit8 v4, v4, 0x1

    goto :goto_1

    .line 433
    :cond_1
    invoke-interface {v0}, Ljava/util/concurrent/ExecutorService;->shutdownNow()Ljava/util/List;

    .line 435
    nop

    .line 436
    nop

    .line 437
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->fileSize:[F

    const-wide/16 v4, 0x0

    if-eqz v0, :cond_4

    .line 438
    const/4 v0, 0x0

    :goto_3
    if-ge v2, v3, :cond_3

    .line 439
    sget-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->fileSize:[F

    aget v1, v1, v2

    const/4 v6, 0x0

    cmpl-float v1, v1, v6

    if-lez v1, :cond_2

    sget-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->fileSize:[F

    aget v1, v1, v2

    float-to-long v6, v1

    add-long/2addr v4, v6

    add-int/lit8 v0, v0, 0x1

    .line 438
    :cond_2
    add-int/lit8 v2, v2, 0x1

    goto :goto_3

    :cond_3
    move v2, v0

    .line 442
    :cond_4
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "\u5c3a\u5bf8\u63a2\u6d4b\u5b8c\u6210 "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "/"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v6, " \u4e2a\uff0c\u5408\u8ba1\u7ea6 "

    invoke-virtual {v0, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v4, v5}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v6, " MB"

    invoke-virtual {v0, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v7, "MagiaCNDownloader"

    invoke-static {v7, v0}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 443
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "\u5df2\u63a2\u660e "

    invoke-virtual {v0, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " \u4e2a\u6587\u4ef6\uff0c\u5408\u8ba1\u7ea6 "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v4, v5}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "\u5f00\u59cb\u4e0b\u8f7d"

    invoke-static {v1, v0}, Lio/kamihama/magianative/CNCNDownloadUI;->updateSimple(Ljava/lang/String;Ljava/lang/String;)V

    .line 445
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->throttledUpdate()V

    .line 446
    return-void
.end method

.method private static promotePart(Ljava/io/File;Ljava/io/File;)V
    .locals 3
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 1120
    invoke-virtual {p1}, Ljava/io/File;->exists()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-virtual {p1}, Ljava/io/File;->delete()Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    .line 1121
    :cond_0
    new-instance p0, Ljava/io/IOException;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "Cannot replace destination "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0, p1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw p0

    .line 1123
    :cond_1
    :goto_0
    invoke-virtual {p0, p1}, Ljava/io/File;->renameTo(Ljava/io/File;)Z

    move-result v0

    if-eqz v0, :cond_2

    .line 1126
    return-void

    .line 1124
    :cond_2
    new-instance v0, Ljava/io/IOException;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "Cannot rename "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string v1, " to "

    invoke-virtual {p0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-direct {v0, p0}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v0
.end method

.method private static readMarkerBytes(Ljava/io/File;)J
    .locals 7

    .line 496
    invoke-virtual {p0}, Ljava/io/File;->isFile()Z

    move-result v0

    const-wide/16 v1, -0x1

    if-eqz v0, :cond_3

    invoke-virtual {p0}, Ljava/io/File;->length()J

    move-result-wide v3

    const-wide/16 v5, 0x4000

    cmp-long v0, v3, v5

    if-lez v0, :cond_0

    goto :goto_1

    .line 498
    :cond_0
    :try_start_0
    invoke-static {p0}, Lio/kamihama/magianative/CNDownloaderFix;->readSmallUtf8(Ljava/io/File;)Ljava/lang/String;

    move-result-object p0

    const-string v0, "\\n"

    invoke-virtual {p0, v0}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p0

    .line 499
    array-length v0, p0

    const/4 v3, 0x0

    :goto_0
    if-ge v3, v0, :cond_2

    aget-object v4, p0, v3

    .line 500
    const-string v5, "bytes="

    invoke-virtual {v4, v5}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v5

    if-eqz v5, :cond_1

    .line 501
    const/4 p0, 0x6

    invoke-virtual {v4, p0}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    invoke-static {p0, v1, v2}, Lio/kamihama/magianative/CNDownloaderFix;->parsePositiveLong(Ljava/lang/String;J)J

    move-result-wide v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    return-wide v0

    .line 499
    :cond_1
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    .line 504
    :catchall_0
    move-exception p0

    :cond_2
    nop

    .line 505
    return-wide v1

    .line 496
    :cond_3
    :goto_1
    return-wide v1
.end method

.method private static readSidecarBytes(Ljava/io/File;)J
    .locals 7

    .line 1063
    new-instance v0, Ljava/io/File;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p0}, Ljava/io/File;->getPath()Ljava/lang/String;

    move-result-object p0

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string v1, ".part.meta"

    invoke-virtual {p0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-direct {v0, p0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 1064
    invoke-virtual {v0}, Ljava/io/File;->isFile()Z

    move-result p0

    const-wide/16 v1, -0x1

    if-eqz p0, :cond_3

    invoke-virtual {v0}, Ljava/io/File;->length()J

    move-result-wide v3

    const-wide/16 v5, 0x4000

    cmp-long p0, v3, v5

    if-lez p0, :cond_0

    goto :goto_2

    .line 1068
    :cond_0
    :try_start_0
    invoke-static {v0}, Lio/kamihama/magianative/CNDownloaderFix;->readSmallUtf8(Ljava/io/File;)Ljava/lang/String;

    move-result-object p0

    const-string v3, "\\n"

    invoke-virtual {p0, v3}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p0

    .line 1069
    array-length v3, p0

    const/4 v4, 0x0

    :goto_0
    if-ge v4, v3, :cond_2

    aget-object v5, p0, v4

    .line 1070
    const-string v6, "bytes="

    invoke-virtual {v5, v6}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v6

    if-eqz v6, :cond_1

    .line 1071
    const/4 p0, 0x6

    invoke-virtual {v5, p0}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    invoke-static {p0, v1, v2}, Lio/kamihama/magianative/CNDownloaderFix;->parsePositiveLong(Ljava/lang/String;J)J

    move-result-wide v0
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    return-wide v0

    .line 1069
    :cond_1
    add-int/lit8 v4, v4, 0x1

    goto :goto_0

    .line 1076
    :cond_2
    goto :goto_1

    .line 1074
    :catch_0
    move-exception p0

    .line 1075
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "Cannot read resume metadata "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v3, "MagiaCNDownloader"

    invoke-static {v3, v0, p0}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 1077
    :goto_1
    return-wide v1

    .line 1065
    :cond_3
    :goto_2
    return-wide v1
.end method

.method private static readSidecarEtag(Ljava/io/File;)Ljava/lang/String;
    .locals 6

    .line 1081
    new-instance v0, Ljava/io/File;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p0}, Ljava/io/File;->getPath()Ljava/lang/String;

    move-result-object p0

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string v1, ".part.meta"

    invoke-virtual {p0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-direct {v0, p0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 1082
    invoke-virtual {v0}, Ljava/io/File;->isFile()Z

    move-result p0

    const-string v1, ""

    if-eqz p0, :cond_3

    invoke-virtual {v0}, Ljava/io/File;->length()J

    move-result-wide v2

    const-wide/16 v4, 0x4000

    cmp-long p0, v2, v4

    if-lez p0, :cond_0

    goto :goto_2

    .line 1086
    :cond_0
    :try_start_0
    invoke-static {v0}, Lio/kamihama/magianative/CNDownloaderFix;->readSmallUtf8(Ljava/io/File;)Ljava/lang/String;

    move-result-object p0

    const-string v2, "\\n"

    invoke-virtual {p0, v2}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p0

    .line 1087
    array-length v2, p0

    const/4 v3, 0x0

    :goto_0
    if-ge v3, v2, :cond_2

    aget-object v4, p0, v3

    .line 1088
    const-string v5, "etag="

    invoke-virtual {v4, v5}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v5

    if-eqz v5, :cond_1

    .line 1089
    const/4 p0, 0x5

    invoke-virtual {v4, p0}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    return-object p0

    .line 1087
    :cond_1
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    .line 1094
    :cond_2
    goto :goto_1

    .line 1092
    :catch_0
    move-exception p0

    .line 1093
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Cannot read resume metadata "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v2, "MagiaCNDownloader"

    invoke-static {v2, v0, p0}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 1095
    :goto_1
    return-object v1

    .line 1083
    :cond_3
    :goto_2
    return-object v1
.end method

.method private static readSmallUtf8(Ljava/io/File;)Ljava/lang/String;
    .locals 7
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 1099
    new-instance v0, Ljava/io/ByteArrayOutputStream;

    invoke-direct {v0}, Ljava/io/ByteArrayOutputStream;-><init>()V

    .line 1100
    nop

    .line 1102
    const/4 v1, 0x0

    :try_start_0
    new-instance v2, Ljava/io/FileInputStream;

    invoke-direct {v2, p0}, Ljava/io/FileInputStream;-><init>(Ljava/io/File;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    .line 1103
    const/16 v1, 0x1000

    :try_start_1
    new-array v1, v1, [B

    .line 1104
    const/4 v3, 0x0

    const/4 v4, 0x0

    .line 1106
    :goto_0
    invoke-virtual {v2, v1}, Ljava/io/FileInputStream;->read([B)I

    move-result v5

    if-ltz v5, :cond_1

    .line 1107
    add-int/2addr v4, v5

    .line 1108
    const/16 v6, 0x4000

    if-gt v4, v6, :cond_0

    .line 1111
    invoke-virtual {v0, v1, v3, v5}, Ljava/io/ByteArrayOutputStream;->write([BII)V

    goto :goto_0

    .line 1109
    :cond_0
    new-instance v0, Ljava/io/IOException;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "State file is too large: "

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-direct {v0, p0}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v0

    .line 1113
    :cond_1
    new-instance p0, Ljava/lang/String;

    invoke-virtual {v0}, Ljava/io/ByteArrayOutputStream;->toByteArray()[B

    move-result-object v0

    sget-object v1, Ljava/nio/charset/StandardCharsets;->UTF_8:Ljava/nio/charset/Charset;

    invoke-direct {p0, v0, v1}, Ljava/lang/String;-><init>([BLjava/nio/charset/Charset;)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 1115
    invoke-static {v2}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/InputStream;)V

    .line 1113
    return-object p0

    .line 1115
    :catchall_0
    move-exception p0

    move-object v1, v2

    goto :goto_1

    :catchall_1
    move-exception p0

    :goto_1
    invoke-static {v1}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/InputStream;)V

    .line 1116
    throw p0
.end method

.method public static requestRetry(I)V
    .locals 3

    .line 213
    if-ltz p0, :cond_2

    const/16 v0, 0xf

    if-ge p0, v0, :cond_2

    .line 214
    :try_start_0
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->fileStatus:[I

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->fileStatus:[I

    aput v1, v0, p0

    .line 215
    :cond_0
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->fileProgress:[I

    if-eqz v0, :cond_1

    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->fileProgress:[I

    aput v1, v0, p0

    .line 216
    :cond_1
    const/4 v0, 0x0

    invoke-static {p0, v0}, Lio/kamihama/magianative/CNCNDownloadUI;->setFileDownloaded(IF)V

    .line 217
    invoke-static {p0, v0}, Lio/kamihama/magianative/CNCNDownloadUI;->setDownloadSpeed(IF)V

    .line 219
    :cond_2
    const-string v0, "MagiaCNDownloader"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "\u6536\u5230\u91cd\u8bd5\u8bf7\u6c42 index="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-static {v0, p0}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    goto :goto_0

    .line 220
    :catchall_0
    move-exception p0

    :goto_0
    nop

    .line 221
    sget-object p0, Lio/kamihama/magianative/CNDownloaderFix;->RETRY_LOCK:Ljava/lang/Object;

    monitor-enter p0

    .line 222
    const/4 v0, 0x1

    :try_start_1
    sput-boolean v0, Lio/kamihama/magianative/CNDownloaderFix;->retryRequested:Z

    .line 223
    invoke-virtual {p0}, Ljava/lang/Object;->notifyAll()V

    .line 224
    monitor-exit p0

    .line 225
    return-void

    .line 224
    :catchall_1
    move-exception v0

    monitor-exit p0
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    throw v0
.end method

.method private static resetProgress(I)V
    .locals 1

    .line 1224
    const/4 v0, 0x0

    invoke-static {p0, v0}, Lio/kamihama/magianative/CNCNDownloadUI;->setDownloadSpeed(IF)V

    .line 1225
    invoke-static {p0, v0}, Lio/kamihama/magianative/CNCNDownloadUI;->setFileDownloaded(IF)V

    .line 1226
    const/4 v0, 0x0

    invoke-static {p0, v0}, Lio/kamihama/magianative/CNCNDownloadUI;->updateFileProgress(II)V

    .line 1227
    return-void
.end method

.method private static resetUiForRun()V
    .locals 7

    .line 1190
    const/4 v0, 0x0

    const/4 v1, 0x0

    :goto_0
    const/16 v2, 0xf

    if-ge v1, v2, :cond_3

    .line 1191
    sget-object v2, Lio/kamihama/magianative/CNDownloaderFix;->FILE_NAMES:[Ljava/lang/String;

    aget-object v3, v2, v1

    invoke-static {v3}, Lio/kamihama/magianative/CNDownloaderFix;->markerFor(Ljava/lang/String;)Ljava/io/File;

    move-result-object v3

    aget-object v4, v2, v1

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "https://assets.magireco.top/"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    aget-object v2, v2, v1

    invoke-virtual {v5, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v3, v4, v2}, Lio/kamihama/magianative/CNDownloaderFix;->isMarkerValid(Ljava/io/File;Ljava/lang/String;Ljava/lang/String;)Z

    move-result v2

    if-nez v2, :cond_2

    .line 1193
    sget-object v2, Lio/kamihama/magianative/CNCNDownloadUI;->fileStatus:[I

    if-eqz v2, :cond_0

    .line 1194
    sget-object v2, Lio/kamihama/magianative/CNCNDownloadUI;->fileStatus:[I

    aput v0, v2, v1

    .line 1196
    :cond_0
    sget-object v2, Lio/kamihama/magianative/CNCNDownloadUI;->fileProgress:[I

    if-eqz v2, :cond_1

    .line 1197
    sget-object v2, Lio/kamihama/magianative/CNCNDownloadUI;->fileProgress:[I

    aput v0, v2, v1

    .line 1199
    :cond_1
    const/4 v2, 0x0

    invoke-static {v1, v2}, Lio/kamihama/magianative/CNCNDownloadUI;->setDownloadSpeed(IF)V

    .line 1200
    invoke-static {v1, v2}, Lio/kamihama/magianative/CNCNDownloadUI;->setFileDownloaded(IF)V

    goto :goto_1

    .line 1202
    :cond_2
    invoke-static {v1}, Lio/kamihama/magianative/CNDownloaderFix;->markDone(I)V

    .line 1190
    :goto_1
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    .line 1205
    :cond_3
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->throttledUpdate()V

    .line 1206
    return-void
.end method

.method public static runInstaller()V
    .locals 3

    .line 248
    invoke-static {}, Lio/kamihama/magianative/CNLog;->initEarly()V

    .line 249
    sget-object v0, Lio/kamihama/magianative/CNDownloaderFix;->installerStarted:Ljava/util/concurrent/atomic/AtomicBoolean;

    const/4 v1, 0x0

    const/4 v2, 0x1

    invoke-virtual {v0, v1, v2}, Ljava/util/concurrent/atomic/AtomicBoolean;->compareAndSet(ZZ)Z

    move-result v0

    const-string v1, "MagiaCNDownloader"

    if-nez v0, :cond_0

    .line 250
    const-string v0, "\u5b89\u88c5\u5668\u5df2\u5728\u8fd0\u884c\u4e2d\uff0c\u8df3\u8fc7\u91cd\u590d\u8c03\u7528"

    invoke-static {v1, v0}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;)V

    .line 251
    return-void

    .line 254
    :cond_0
    :try_start_0
    invoke-static {}, Lio/kamihama/magianative/CNDownloaderFix;->runInstallerInner()V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 262
    goto :goto_1

    .line 255
    :catchall_0
    move-exception v0

    .line 259
    :try_start_1
    const-string v2, "\u5b89\u88c5\u5668\u53d1\u751f\u672a\u9884\u671f\u9519\u8bef\uff0c\u5df2\u62e6\u622a\u4ee5\u907f\u514d\u56de\u9000\u5230\u539f\u751f\u4e0b\u8f7d\u754c\u9762"

    invoke-static {v1, v2, v0}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 260
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "\u5b89\u88c5\u5668\u5f02\u5e38\uff1a"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1, v0}, Lio/kamihama/magianative/CNDownloaderFix;->failInstaller(Ljava/lang/String;Ljava/lang/Throwable;)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    goto :goto_0

    .line 261
    :catchall_1
    move-exception v0

    :goto_0
    nop

    .line 263
    :goto_1
    return-void
.end method

.method private static runInstallerInner()V
    .locals 13

    .line 266
    const-string v0, "MagiaCNDownloader"

    const-string v1, "installer=v2 max_downloads=4"

    invoke-static {v0, v1}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 272
    nop

    .line 273
    const/4 v0, 0x0

    const/4 v1, 0x0

    move-object v3, v0

    const/4 v2, 0x0

    :goto_0
    const/16 v4, 0x32

    if-ge v2, v4, :cond_1

    .line 274
    :try_start_0
    invoke-static {}, Lio/kamihama/magianative/RestClient;->getCurrentActivity()Landroid/app/Activity;

    move-result-object v3
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 275
    if-eqz v3, :cond_0

    goto :goto_1

    .line 276
    :cond_0
    const-wide/16 v4, 0x64

    :try_start_1
    invoke-static {v4, v5}, Ljava/lang/Thread;->sleep(J)V
    :try_end_1
    .catch Ljava/lang/InterruptedException; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 279
    nop

    .line 273
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    .line 276
    :catch_0
    move-exception v2

    .line 277
    :try_start_2
    invoke-static {}, Ljava/lang/Thread;->currentThread()Ljava/lang/Thread;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/Thread;->interrupt()V

    .line 278
    goto :goto_1

    .line 290
    :catchall_0
    move-exception v2

    goto :goto_3

    .line 281
    :cond_1
    :goto_1
    if-eqz v3, :cond_2

    .line 282
    invoke-static {v3}, Lio/kamihama/magianative/CNCNDownloadUI;->show(Landroid/app/Activity;)V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    .line 286
    :try_start_3
    invoke-static {v3}, Lio/kamihama/magianative/CNCNDownloadUI;->ensureVisible(Landroid/app/Activity;)V
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_1

    goto :goto_2

    :catchall_1
    move-exception v2

    goto :goto_2

    .line 288
    :cond_2
    :try_start_4
    const-string v2, "MagiaCNDownloader"

    const-string v3, "\u53d6\u4e0d\u5230 Activity\uff0c\u6d6e\u5c42\u65e0\u6cd5\u663e\u793a\uff08\u5f15\u64ce\u573a\u666f\u53ef\u80fd\u5916\u9732\uff09"

    invoke-static {v2, v3}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_0

    .line 292
    :goto_2
    goto :goto_4

    .line 291
    :goto_3
    const-string v3, "MagiaCNDownloader"

    const-string v4, "Unable to show installer UI"

    invoke-static {v3, v4, v2}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 294
    :goto_4
    new-instance v2, Ljava/io/File;

    const-string v3, "/data/data/io.kamihama.totentanz/files/madomagi/magica/cn_base_done.flag"

    invoke-direct {v2, v3}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 295
    invoke-virtual {v2}, Ljava/io/File;->isFile()Z

    move-result v3

    if-eqz v3, :cond_3

    .line 296
    const-string v0, "MagiaCNDownloader"

    const-string v1, "Final flag already exists; installer skipped"

    invoke-static {v0, v1}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 297
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->hide()V

    .line 298
    return-void

    .line 301
    :cond_3
    new-instance v3, Ljava/io/File;

    const-string v4, "/data/data/io.kamihama.totentanz/files/madomagi/magica/.cn_installer/r128-downloader-v1"

    invoke-direct {v3, v4}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 302
    invoke-virtual {v3}, Ljava/io/File;->isDirectory()Z

    move-result v4

    if-nez v4, :cond_4

    invoke-virtual {v3}, Ljava/io/File;->mkdirs()Z

    move-result v4

    if-nez v4, :cond_4

    invoke-virtual {v3}, Ljava/io/File;->isDirectory()Z

    move-result v3

    if-nez v3, :cond_4

    .line 303
    const-string v1, "Cannot create installer state directory"

    invoke-static {v1, v0}, Lio/kamihama/magianative/CNDownloaderFix;->failInstaller(Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 304
    return-void

    .line 308
    :cond_4
    const-string v3, "\u51c6\u5907\u4e2d"

    const-string v4, "\u6b63\u5728\u83b7\u53d6\u4e0b\u8f7d\u7ebf\u8def\u2026"

    invoke-static {v3, v4, v1}, Lio/kamihama/magianative/CNCNDownloadUI;->updateSimple(Ljava/lang/String;Ljava/lang/String;I)V

    .line 309
    invoke-static {v1}, Lio/kamihama/magianative/CNMirrors;->refresh(Z)V

    .line 310
    invoke-static {}, Lio/kamihama/magianative/CNMirrors;->isLoaded()Z

    move-result v3

    const/4 v4, 0x1

    if-nez v3, :cond_5

    .line 311
    invoke-static {v4}, Lio/kamihama/magianative/CNMirrors;->refresh(Z)V

    .line 313
    :cond_5
    invoke-static {}, Lio/kamihama/magianative/CNMirrors;->healthy()Ljava/util/List;

    move-result-object v3

    invoke-interface {v3}, Ljava/util/List;->size()I

    move-result v3

    .line 314
    const-string v5, "MagiaCNDownloader"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "mirrors ready count="

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, " loaded="

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-static {}, Lio/kamihama/magianative/CNMirrors;->isLoaded()Z

    move-result v7

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 315
    const-string v5, "\u5f00\u59cb\u4e0b\u8f7d"

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "\u53ef\u7528\u7ebf\u8def "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v6, " \u6761\uff0c\u5355\u6587\u4ef6\u5206\u7247 "

    invoke-virtual {v3, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    .line 316
    invoke-static {}, Lio/kamihama/magianative/CNMirrors;->chunks()I

    move-result v6

    invoke-virtual {v3, v6}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v6, " \u7ebf\u7a0b"

    invoke-virtual {v3, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    .line 315
    invoke-static {v5, v3, v1}, Lio/kamihama/magianative/CNCNDownloadUI;->updateSimple(Ljava/lang/String;Ljava/lang/String;I)V

    .line 322
    invoke-static {}, Lio/kamihama/magianative/CNDownloaderFix;->startSpeedWatchdog()Ljava/util/concurrent/ScheduledExecutorService;

    move-result-object v3

    .line 324
    invoke-static {}, Lio/kamihama/magianative/CNDownloaderFix;->resetUiForRun()V

    .line 329
    invoke-static {}, Lio/kamihama/magianative/CNDownloaderFix;->probeAllSizes()V

    .line 331
    nop

    .line 335
    :goto_5
    const/4 v5, 0x4

    invoke-static {v5}, Ljava/util/concurrent/Executors;->newFixedThreadPool(I)Ljava/util/concurrent/ExecutorService;

    move-result-object v5

    .line 336
    new-instance v6, Ljava/util/ArrayList;

    const/16 v7, 0xf

    invoke-direct {v6, v7}, Ljava/util/ArrayList;-><init>(I)V

    .line 337
    const/4 v8, 0x0

    :goto_6
    if-ge v8, v7, :cond_6

    .line 338
    new-instance v9, Lio/kamihama/magianative/CNDownloaderFix$ArchiveTask;

    invoke-direct {v9, v8}, Lio/kamihama/magianative/CNDownloaderFix$ArchiveTask;-><init>(I)V

    invoke-interface {v5, v9}, Ljava/util/concurrent/ExecutorService;->submit(Ljava/util/concurrent/Callable;)Ljava/util/concurrent/Future;

    move-result-object v9

    invoke-interface {v6, v9}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 337
    add-int/lit8 v8, v8, 0x1

    goto :goto_6

    .line 340
    :cond_6
    invoke-interface {v5}, Ljava/util/concurrent/ExecutorService;->shutdown()V

    .line 342
    nop

    .line 343
    const/4 v8, 0x0

    const/4 v9, 0x1

    :goto_7
    invoke-interface {v6}, Ljava/util/List;->size()I

    move-result v10

    if-ge v8, v10, :cond_8

    .line 345
    :try_start_5
    invoke-interface {v6, v8}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v10

    check-cast v10, Ljava/util/concurrent/Future;

    invoke-interface {v10}, Ljava/util/concurrent/Future;->get()Ljava/lang/Object;

    move-result-object v10

    check-cast v10, Ljava/lang/Boolean;

    invoke-virtual {v10}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v10
    :try_end_5
    .catch Ljava/lang/InterruptedException; {:try_start_5 .. :try_end_5} :catch_2
    .catch Ljava/util/concurrent/ExecutionException; {:try_start_5 .. :try_end_5} :catch_1

    if-nez v10, :cond_7

    .line 346
    const/4 v9, 0x0

    .line 355
    :cond_7
    goto :goto_8

    .line 352
    :catch_1
    move-exception v9

    .line 353
    const-string v10, "MagiaCNDownloader"

    new-instance v11, Ljava/lang/StringBuilder;

    invoke-direct {v11}, Ljava/lang/StringBuilder;-><init>()V

    const-string v12, "Installer worker crashed for "

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    sget-object v12, Lio/kamihama/magianative/CNDownloaderFix;->FILE_NAMES:[Ljava/lang/String;

    aget-object v12, v12, v8

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v11

    invoke-static {v10, v11, v9}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 354
    const/4 v9, 0x0

    goto :goto_8

    .line 348
    :catch_2
    move-exception v9

    .line 349
    invoke-static {}, Ljava/lang/Thread;->currentThread()Ljava/lang/Thread;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/Thread;->interrupt()V

    .line 350
    const-string v10, "MagiaCNDownloader"

    new-instance v11, Ljava/lang/StringBuilder;

    invoke-direct {v11}, Ljava/lang/StringBuilder;-><init>()V

    const-string v12, "Installer interrupted while waiting for "

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    sget-object v12, Lio/kamihama/magianative/CNDownloaderFix;->FILE_NAMES:[Ljava/lang/String;

    aget-object v12, v12, v8

    invoke-virtual {v11, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v11

    invoke-static {v10, v11, v9}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 351
    nop

    .line 355
    const/4 v9, 0x0

    .line 343
    :goto_8
    add-int/lit8 v8, v8, 0x1

    goto :goto_7

    .line 357
    :cond_8
    invoke-interface {v5}, Ljava/util/concurrent/ExecutorService;->shutdownNow()Ljava/util/List;

    .line 358
    invoke-static {}, Lio/kamihama/magianative/CNDownloaderFix;->zeroAllSpeeds()V

    .line 360
    if-eqz v9, :cond_a

    invoke-static {}, Lio/kamihama/magianative/CNDownloaderFix;->allMarkersValid()Z

    move-result v5

    if-eqz v5, :cond_a

    .line 385
    invoke-interface {v3}, Ljava/util/concurrent/ScheduledExecutorService;->shutdownNow()Ljava/util/List;

    .line 388
    :try_start_6
    const-string v0, "schema=2\narchives=15\n"

    invoke-static {v2, v0}, Lio/kamihama/magianative/CNDownloaderFix;->writeAtomic(Ljava/io/File;Ljava/lang/String;)V

    .line 389
    const-string v0, "\u5b89\u88c5\u5b8c\u6210"

    const-string v1, "\u6240\u6709\u8d44\u6e90\u5df2\u9a8c\u8bc1\u5e76\u63d0\u4ea4\u5b8c\u6210\u6807\u8bb0"

    const/16 v2, 0x64

    invoke-static {v0, v1, v2}, Lio/kamihama/magianative/CNCNDownloadUI;->updateSimple(Ljava/lang/String;Ljava/lang/String;I)V

    .line 390
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->hide()V

    .line 391
    const-string v0, "MagiaCNDownloader"

    const-string v1, "All archives installed; final flag committed atomically"

    invoke-static {v0, v1}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 392
    new-instance v0, Ljava/io/File;

    const-string v1, "/data/data/io.kamihama.totentanz/files/madomagi/magica/.cn_installer/r128-downloader-v1/no_restart"

    invoke-direct {v0, v1}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/io/File;->isFile()Z

    move-result v0

    if-eqz v0, :cond_9

    .line 393
    const-string v0, "MagiaCNDownloader"

    const-string v1, "Test no-restart marker present; restart suppressed"

    invoke-static {v0, v1}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_6
    .catch Ljava/io/IOException; {:try_start_6 .. :try_end_6} :catch_4

    .line 394
    return-void

    .line 397
    :cond_9
    const-wide/16 v0, 0x7d0

    :try_start_7
    invoke-static {v0, v1}, Ljava/lang/Thread;->sleep(J)V

    .line 398
    invoke-static {}, Lio/kamihama/magianative/RestClient;->restartApp()V
    :try_end_7
    .catch Ljava/lang/InterruptedException; {:try_start_7 .. :try_end_7} :catch_3
    .catch Ljava/io/IOException; {:try_start_7 .. :try_end_7} :catch_4

    .line 401
    goto :goto_9

    .line 399
    :catch_3
    move-exception v0

    .line 400
    :try_start_8
    invoke-static {}, Ljava/lang/Thread;->currentThread()Ljava/lang/Thread;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Thread;->interrupt()V
    :try_end_8
    .catch Ljava/io/IOException; {:try_start_8 .. :try_end_8} :catch_4

    .line 404
    :goto_9
    goto :goto_a

    .line 402
    :catch_4
    move-exception v0

    .line 403
    const-string v1, "Final flag commit failed"

    invoke-static {v1, v0}, Lio/kamihama/magianative/CNDownloaderFix;->failInstaller(Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 405
    :goto_a
    return-void

    .line 362
    :cond_a
    nop

    .line 363
    sget-object v5, Lio/kamihama/magianative/CNCNDownloadUI;->fileStatus:[I

    if-eqz v5, :cond_c

    .line 364
    const/4 v5, 0x0

    const/4 v6, 0x0

    :goto_b
    if-ge v5, v7, :cond_d

    .line 365
    sget-object v8, Lio/kamihama/magianative/CNCNDownloadUI;->fileStatus:[I

    aget v8, v8, v5

    const/4 v9, 0x3

    if-ne v8, v9, :cond_b

    add-int/lit8 v6, v6, 0x1

    .line 364
    :cond_b
    add-int/lit8 v5, v5, 0x1

    goto :goto_b

    .line 363
    :cond_c
    const/4 v6, 0x0

    .line 368
    :cond_d
    const-string v5, "MagiaCNDownloader"

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "\u672c\u8f6e\u6709 "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, v6}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v7

    const-string v8, " \u4e2a\u6587\u4ef6\u5931\u8d25\uff0c\u7b49\u5f85\u73a9\u5bb6\u91cd\u8bd5"

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v5, v7}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;)V

    .line 369
    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "\u6709 "

    invoke-virtual {v5, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v5

    const-string v6, " \u4e2a\u6587\u4ef6\u4e0b\u8f7d\u5931\u8d25\uff0c\u70b9\u51fb\u6587\u4ef6\u53f3\u4fa7\u7684\u300c\u91cd\u8bd5\u300d\u7ee7\u7eed"

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v5, v0}, Lio/kamihama/magianative/CNDownloaderFix;->failInstaller(Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 372
    sget-object v5, Lio/kamihama/magianative/CNDownloaderFix;->RETRY_LOCK:Ljava/lang/Object;

    monitor-enter v5

    .line 373
    :goto_c
    :try_start_9
    sget-boolean v6, Lio/kamihama/magianative/CNDownloaderFix;->retryRequested:Z
    :try_end_9
    .catchall {:try_start_9 .. :try_end_9} :catchall_2

    if-nez v6, :cond_e

    .line 375
    :try_start_a
    sget-object v6, Lio/kamihama/magianative/CNDownloaderFix;->RETRY_LOCK:Ljava/lang/Object;

    invoke-virtual {v6}, Ljava/lang/Object;->wait()V
    :try_end_a
    .catch Ljava/lang/InterruptedException; {:try_start_a .. :try_end_a} :catch_5
    .catchall {:try_start_a .. :try_end_a} :catchall_2

    .line 379
    :goto_d
    goto :goto_c

    .line 376
    :catch_5
    move-exception v6

    .line 377
    :try_start_b
    invoke-static {}, Ljava/lang/Thread;->currentThread()Ljava/lang/Thread;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/Thread;->interrupt()V

    .line 378
    const-string v6, "MagiaCNDownloader"

    const-string v7, "\u7b49\u5f85\u91cd\u8bd5\u65f6\u88ab\u4e2d\u65ad\uff0c\u7ee7\u7eed\u7b49\u5f85\u4ee5\u907f\u514d\u9000\u56de\u539f\u751f\u754c\u9762"

    invoke-static {v6, v7}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_d

    .line 381
    :cond_e
    sput-boolean v1, Lio/kamihama/magianative/CNDownloaderFix;->retryRequested:Z

    .line 382
    monitor-exit v5
    :try_end_b
    .catchall {:try_start_b .. :try_end_b} :catchall_2

    .line 383
    const-string v5, "\u91cd\u8bd5\u4e2d"

    const-string v6, "\u6b63\u5728\u91cd\u65b0\u4e0b\u8f7d\u5931\u8d25\u7684\u6587\u4ef6\u2026"

    invoke-static {v5, v6, v1}, Lio/kamihama/magianative/CNCNDownloadUI;->updateSimple(Ljava/lang/String;Ljava/lang/String;I)V

    .line 384
    goto/16 :goto_5

    .line 382
    :catchall_2
    move-exception v0

    :try_start_c
    monitor-exit v5
    :try_end_c
    .catchall {:try_start_c .. :try_end_c} :catchall_2

    throw v0
.end method

.method private static sanitizeLine(Ljava/lang/String;)Ljava/lang/String;
    .locals 2

    .line 1277
    if-nez p0, :cond_0

    const-string p0, ""

    goto :goto_0

    :cond_0
    const/16 v0, 0xd

    const/16 v1, 0x20

    invoke-virtual {p0, v0, v1}, Ljava/lang/String;->replace(CC)Ljava/lang/String;

    move-result-object p0

    const/16 v0, 0xa

    invoke-virtual {p0, v0, v1}, Ljava/lang/String;->replace(CC)Ljava/lang/String;

    move-result-object p0

    :goto_0
    return-object p0
.end method

.method private static setActive(IZ)V
    .locals 3

    .line 1245
    sget-object v0, Lio/kamihama/magianative/CNDownloaderFix;->ACTIVE:Ljava/util/concurrent/atomic/AtomicIntegerArray;

    invoke-virtual {v0, p0, p1}, Ljava/util/concurrent/atomic/AtomicIntegerArray;->set(II)V

    .line 1246
    sget-object v0, Lio/kamihama/magianative/CNDownloaderFix;->LAST_PROGRESS_NS:Ljava/util/concurrent/atomic/AtomicLongArray;

    if-eqz p1, :cond_0

    invoke-static {}, Ljava/lang/System;->nanoTime()J

    move-result-wide v1

    goto :goto_0

    :cond_0
    const-wide/16 v1, 0x0

    :goto_0
    invoke-virtual {v0, p0, v1, v2}, Ljava/util/concurrent/atomic/AtomicLongArray;->set(IJ)V

    .line 1247
    return-void
.end method

.method private static startSpeedWatchdog()Ljava/util/concurrent/ScheduledExecutorService;
    .locals 8

    .line 914
    invoke-static {}, Ljava/util/concurrent/Executors;->newSingleThreadScheduledExecutor()Ljava/util/concurrent/ScheduledExecutorService;

    move-result-object v7

    .line 915
    new-instance v1, Lio/kamihama/magianative/CNDownloaderFix$SpeedWatchdog;

    const/4 v0, 0x0

    invoke-direct {v1, v0}, Lio/kamihama/magianative/CNDownloaderFix$SpeedWatchdog;-><init>(Lio/kamihama/magianative/CNDownloaderFix$1;)V

    const-wide/16 v2, 0x1

    const-wide/16 v4, 0x1

    sget-object v6, Ljava/util/concurrent/TimeUnit;->SECONDS:Ljava/util/concurrent/TimeUnit;

    move-object v0, v7

    invoke-interface/range {v0 .. v6}, Ljava/util/concurrent/ScheduledExecutorService;->scheduleAtFixedRate(Ljava/lang/Runnable;JJLjava/util/concurrent/TimeUnit;)Ljava/util/concurrent/ScheduledFuture;

    .line 916
    return-object v7
.end method

.method public static triggerInstaller()V
    .locals 2

    .line 114
    new-instance v0, Lio/kamihama/magianative/CNDownloaderFix$1;

    const-string v1, "cnv-installer-trigger"

    invoke-direct {v0, v1}, Lio/kamihama/magianative/CNDownloaderFix$1;-><init>(Ljava/lang/String;)V

    .line 129
    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Ljava/lang/Thread;->setDaemon(Z)V

    .line 130
    invoke-virtual {v0}, Ljava/lang/Thread;->start()V

    .line 131
    return-void
.end method

.method private static truncate(Ljava/io/File;)V
    .locals 3
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 1129
    nop

    .line 1131
    const/4 v0, 0x0

    :try_start_0
    new-instance v1, Ljava/io/FileOutputStream;

    const/4 v2, 0x0

    invoke-direct {v1, p0, v2}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;Z)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    .line 1132
    :try_start_1
    invoke-virtual {v1}, Ljava/io/FileOutputStream;->flush()V

    .line 1133
    invoke-virtual {v1}, Ljava/io/FileOutputStream;->getFD()Ljava/io/FileDescriptor;

    move-result-object p0

    invoke-virtual {p0}, Ljava/io/FileDescriptor;->sync()V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 1135
    invoke-static {v1}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/OutputStream;)V

    .line 1136
    nop

    .line 1137
    return-void

    .line 1135
    :catchall_0
    move-exception p0

    move-object v0, v1

    goto :goto_0

    :catchall_1
    move-exception p0

    :goto_0
    invoke-static {v0}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/OutputStream;)V

    .line 1136
    throw p0
.end method

.method private static updateProgress(IJJ)V
    .locals 6

    .line 1214
    const-wide/16 v0, 0x0

    cmp-long v2, p3, v0

    if-lez v2, :cond_0

    .line 1215
    const-wide/16 v2, 0x64

    mul-long v4, p1, v2

    div-long/2addr v4, p3

    invoke-static {v0, v1, v4, v5}, Ljava/lang/Math;->max(JJ)J

    move-result-wide p3

    invoke-static {v2, v3, p3, p4}, Ljava/lang/Math;->min(JJ)J

    move-result-wide p3

    long-to-int p4, p3

    goto :goto_0

    .line 1217
    :cond_0
    const/4 p4, 0x0

    .line 1219
    :goto_0
    long-to-double p1, p1

    const-wide v0, 0x412e848000000000L    # 1000000.0

    div-double/2addr p1, v0

    double-to-float p1, p1

    invoke-static {p0, p1}, Lio/kamihama/magianative/CNCNDownloadUI;->setFileDownloaded(IF)V

    .line 1220
    invoke-static {p0, p4}, Lio/kamihama/magianative/CNCNDownloadUI;->updateFileProgress(II)V

    .line 1221
    return-void
.end method

.method private static updateSize(IJ)V
    .locals 2

    .line 1209
    long-to-double p1, p1

    const-wide v0, 0x412e848000000000L    # 1000000.0

    div-double/2addr p1, v0

    double-to-float p1, p1

    invoke-static {p0, p1}, Lio/kamihama/magianative/CNCNDownloadUI;->setFileSize(IF)V

    .line 1210
    return-void
.end method

.method private static writeAtomic(Ljava/io/File;Ljava/lang/String;)V
    .locals 4
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 1030
    invoke-virtual {p0}, Ljava/io/File;->getParentFile()Ljava/io/File;

    move-result-object v0

    .line 1031
    if-eqz v0, :cond_1

    invoke-virtual {v0}, Ljava/io/File;->isDirectory()Z

    move-result v1

    if-nez v1, :cond_1

    invoke-virtual {v0}, Ljava/io/File;->mkdirs()Z

    move-result v1

    if-nez v1, :cond_1

    invoke-virtual {v0}, Ljava/io/File;->isDirectory()Z

    move-result v1

    if-eqz v1, :cond_0

    goto :goto_0

    .line 1032
    :cond_0
    new-instance p0, Ljava/io/IOException;

    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "Cannot create parent directory: "

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0, p1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw p0

    .line 1034
    :cond_1
    :goto_0
    new-instance v0, Ljava/io/File;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p0}, Ljava/io/File;->getPath()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, ".tmp"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 1035
    nop

    .line 1037
    const/4 v1, 0x0

    :try_start_0
    new-instance v2, Ljava/io/FileOutputStream;

    const/4 v3, 0x0

    invoke-direct {v2, v0, v3}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;Z)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    .line 1038
    :try_start_1
    sget-object v3, Ljava/nio/charset/StandardCharsets;->UTF_8:Ljava/nio/charset/Charset;

    invoke-virtual {p1, v3}, Ljava/lang/String;->getBytes(Ljava/nio/charset/Charset;)[B

    move-result-object p1

    invoke-virtual {v2, p1}, Ljava/io/FileOutputStream;->write([B)V

    .line 1039
    invoke-virtual {v2}, Ljava/io/FileOutputStream;->flush()V

    .line 1040
    invoke-virtual {v2}, Ljava/io/FileOutputStream;->getFD()Ljava/io/FileDescriptor;

    move-result-object p1

    invoke-virtual {p1}, Ljava/io/FileDescriptor;->sync()V

    .line 1041
    invoke-static {v2}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/OutputStream;)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 1042
    nop

    .line 1043
    :try_start_2
    invoke-virtual {p0}, Ljava/io/File;->exists()Z

    move-result p1

    if-eqz p1, :cond_3

    invoke-virtual {p0}, Ljava/io/File;->delete()Z

    move-result p1

    if-eqz p1, :cond_2

    goto :goto_1

    .line 1044
    :cond_2
    new-instance p1, Ljava/io/IOException;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "Cannot replace "

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-direct {p1, p0}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw p1

    .line 1046
    :cond_3
    :goto_1
    invoke-virtual {v0, p0}, Ljava/io/File;->renameTo(Ljava/io/File;)Z

    move-result p1
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_1

    if-eqz p1, :cond_4

    .line 1050
    invoke-static {v1}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/OutputStream;)V

    .line 1051
    nop

    .line 1052
    return-void

    .line 1047
    :cond_4
    :try_start_3
    new-instance p1, Ljava/io/IOException;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Atomic rename failed: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v2, " -> "

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-direct {p1, p0}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw p1
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_1

    .line 1050
    :catchall_0
    move-exception p0

    move-object v1, v2

    goto :goto_2

    :catchall_1
    move-exception p0

    :goto_2
    invoke-static {v1}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/OutputStream;)V

    .line 1051
    throw p0
.end method

.method private static writeMarker(Ljava/io/File;Ljava/lang/String;Ljava/lang/String;Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;)V
    .locals 2
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 992
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "schema=1\nfile="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v0, "\nurl="

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string p2, "\nbytes="

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    iget-wide v0, p3, Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;->totalBytes:J

    invoke-virtual {p1, v0, v1}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string p2, "\netag="

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    iget-object p2, p3, Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;->etag:Ljava/lang/String;

    .line 994
    invoke-static {p2}, Lio/kamihama/magianative/CNDownloaderFix;->sanitizeLine(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p2

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string p2, "\n"

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    .line 992
    invoke-static {p0, p1}, Lio/kamihama/magianative/CNDownloaderFix;->writeAtomic(Ljava/io/File;Ljava/lang/String;)V

    .line 995
    return-void
.end method

.method private static writeSidecar(Ljava/io/File;Ljava/lang/String;J)V
    .locals 2
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 1055
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "etag="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {p1}, Lio/kamihama/magianative/CNDownloaderFix;->sanitizeLine(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v0, "\nbytes="

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, p2, p3}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string p2, "\n"

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1}, Lio/kamihama/magianative/CNDownloaderFix;->writeAtomic(Ljava/io/File;Ljava/lang/String;)V

    .line 1056
    return-void
.end method

.method private static zeroAllSpeeds()V
    .locals 5

    .line 1250
    const/4 v0, 0x0

    const/4 v1, 0x0

    :goto_0
    const/16 v2, 0xf

    if-ge v1, v2, :cond_0

    .line 1251
    sget-object v2, Lio/kamihama/magianative/CNDownloaderFix;->ACTIVE:Ljava/util/concurrent/atomic/AtomicIntegerArray;

    invoke-virtual {v2, v1, v0}, Ljava/util/concurrent/atomic/AtomicIntegerArray;->set(II)V

    .line 1252
    sget-object v2, Lio/kamihama/magianative/CNDownloaderFix;->LAST_PROGRESS_NS:Ljava/util/concurrent/atomic/AtomicLongArray;

    const-wide/16 v3, 0x0

    invoke-virtual {v2, v1, v3, v4}, Ljava/util/concurrent/atomic/AtomicLongArray;->set(IJ)V

    .line 1253
    const/4 v2, 0x0

    invoke-static {v1, v2}, Lio/kamihama/magianative/CNCNDownloadUI;->setDownloadSpeed(IF)V

    .line 1250
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    .line 1255
    :cond_0
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->throttledUpdate()V

    .line 1256
    return-void
.end method
