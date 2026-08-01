.class public final Lio/kamihama/magianative/CNDownloaderFix;
.super Ljava/lang/Object;
.source "CNDownloaderFix.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lio/kamihama/magianative/CNDownloaderFix$ArchiveTask;,
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

.field private static final STALE_SPEED_NS:J

.field private static final STATE_ROOT:Ljava/lang/String; = "/data/data/io.kamihama.totentanz/files/madomagi/magica/.cn_installer/r128-downloader-v1"

.field private static final TAG:Ljava/lang/String; = "MagiaCNDownloader"


# direct methods
.method static constructor <clinit>()V
    .locals 16

    .line 75
    sget-object v0, Ljava/util/concurrent/TimeUnit;->SECONDS:Ljava/util/concurrent/TimeUnit;

    const-wide/16 v1, 0x2

    invoke-virtual {v0, v1, v2}, Ljava/util/concurrent/TimeUnit;->toNanos(J)J

    move-result-wide v0

    sput-wide v0, Lio/kamihama/magianative/CNDownloaderFix;->STALE_SPEED_NS:J

    .line 76
    new-instance v0, Ljava/lang/Object;

    invoke-direct {v0}, Ljava/lang/Object;-><init>()V

    sput-object v0, Lio/kamihama/magianative/CNDownloaderFix;->EXTRACT_LOCK:Ljava/lang/Object;

    .line 78
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

    .line 88
    new-instance v0, Ljava/util/concurrent/atomic/AtomicLongArray;

    const/16 v1, 0xf

    invoke-direct {v0, v1}, Ljava/util/concurrent/atomic/AtomicLongArray;-><init>(I)V

    sput-object v0, Lio/kamihama/magianative/CNDownloaderFix;->LAST_PROGRESS_NS:Ljava/util/concurrent/atomic/AtomicLongArray;

    .line 89
    new-instance v0, Ljava/util/concurrent/atomic/AtomicIntegerArray;

    invoke-direct {v0, v1}, Ljava/util/concurrent/atomic/AtomicIntegerArray;-><init>(I)V

    sput-object v0, Lio/kamihama/magianative/CNDownloaderFix;->ACTIVE:Ljava/util/concurrent/atomic/AtomicIntegerArray;

    return-void
.end method

.method private constructor <init>()V
    .locals 0

    .line 91
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 92
    return-void
.end method

.method static synthetic access$000(I)Z
    .locals 0

    .line 58
    invoke-static {p0}, Lio/kamihama/magianative/CNDownloaderFix;->installArchive(I)Z

    move-result p0

    return p0
.end method

.method static synthetic access$100(IJ)V
    .locals 0

    .line 58
    invoke-static {p0, p1, p2}, Lio/kamihama/magianative/CNDownloaderFix;->updateSize(IJ)V

    return-void
.end method

.method static synthetic access$200()Ljava/util/concurrent/atomic/AtomicLongArray;
    .locals 1

    .line 58
    sget-object v0, Lio/kamihama/magianative/CNDownloaderFix;->LAST_PROGRESS_NS:Ljava/util/concurrent/atomic/AtomicLongArray;

    return-object v0
.end method

.method static synthetic access$300(IJJ)V
    .locals 0

    .line 58
    invoke-static {p0, p1, p2, p3, p4}, Lio/kamihama/magianative/CNDownloaderFix;->updateProgress(IJJ)V

    return-void
.end method

.method static synthetic access$500()Ljava/util/concurrent/atomic/AtomicIntegerArray;
    .locals 1

    .line 58
    sget-object v0, Lio/kamihama/magianative/CNDownloaderFix;->ACTIVE:Ljava/util/concurrent/atomic/AtomicIntegerArray;

    return-object v0
.end method

.method static synthetic access$600()J
    .locals 2

    .line 58
    sget-wide v0, Lio/kamihama/magianative/CNDownloaderFix;->STALE_SPEED_NS:J

    return-wide v0
.end method

.method static synthetic access$700()[Ljava/lang/String;
    .locals 1

    .line 58
    sget-object v0, Lio/kamihama/magianative/CNDownloaderFix;->FILE_NAMES:[Ljava/lang/String;

    return-object v0
.end method

.method private static allMarkersValid()Z
    .locals 8

    .line 776
    sget-object v0, Lio/kamihama/magianative/CNDownloaderFix;->FILE_NAMES:[Ljava/lang/String;

    array-length v1, v0

    const/4 v2, 0x0

    const/4 v3, 0x0

    :goto_0
    if-ge v3, v1, :cond_1

    aget-object v4, v0, v3

    .line 777
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

    .line 778
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

    .line 779
    return v2

    .line 776
    :cond_0
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    .line 782
    :cond_1
    const/4 v0, 0x1

    return v0
.end method

.method private static cleanHeader(Ljava/lang/String;)Ljava/lang/String;
    .locals 0

    .line 1033
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

    .line 1047
    if-eqz p0, :cond_0

    .line 1049
    :try_start_0
    invoke-virtual {p0}, Ljava/io/InputStream;->close()V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    .line 1051
    goto :goto_0

    .line 1050
    :catch_0
    move-exception p0

    .line 1053
    :cond_0
    :goto_0
    return-void
.end method

.method private static closeQuietly(Ljava/io/OutputStream;)V
    .locals 0

    .line 1056
    if-eqz p0, :cond_0

    .line 1058
    :try_start_0
    invoke-virtual {p0}, Ljava/io/OutputStream;->close()V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    .line 1060
    goto :goto_0

    .line 1059
    :catch_0
    move-exception p0

    .line 1062
    :cond_0
    :goto_0
    return-void
.end method

.method private static deleteQuietly(Ljava/io/File;)V
    .locals 2

    .line 1041
    invoke-virtual {p0}, Ljava/io/File;->exists()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-virtual {p0}, Ljava/io/File;->delete()Z

    move-result v0

    if-nez v0, :cond_0

    .line 1042
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

    .line 1044
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

    .line 443
    move-object/from16 v0, p0

    move-object/from16 v1, p1

    move/from16 v2, p2

    move/from16 v3, p3

    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->isFile()Z

    move-result v4

    if-eqz v4, :cond_0

    .line 444
    new-instance v0, Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;

    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->length()J

    move-result-wide v2

    invoke-static/range {p1 .. p1}, Lio/kamihama/magianative/CNDownloaderFix;->readSidecarEtag(Ljava/io/File;)Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v2, v3, v1}, Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;-><init>(JLjava/lang/String;)V

    return-object v0

    .line 447
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

    .line 448
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

    .line 449
    invoke-virtual {v4}, Ljava/io/File;->getParentFile()Ljava/io/File;

    move-result-object v6

    .line 450
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

    .line 451
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

    .line 459
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

    .line 460
    :goto_1
    const-string v6, "MagiaCNDownloader"

    cmp-long v11, v9, v7

    if-lez v11, :cond_4

    .line 461
    invoke-static/range {p1 .. p1}, Lio/kamihama/magianative/CNDownloaderFix;->readSidecarBytes(Ljava/io/File;)J

    move-result-wide v11

    .line 462
    cmp-long v13, v11, v7

    if-lez v13, :cond_4

    cmp-long v13, v9, v11

    if-lez v13, :cond_4

    .line 463
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

    .line 465
    invoke-static {v4}, Lio/kamihama/magianative/CNDownloaderFix;->truncate(Ljava/io/File;)V

    .line 466
    invoke-static {v5}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    .line 467
    invoke-static/range {p2 .. p2}, Lio/kamihama/magianative/CNDownloaderFix;->resetProgress(I)V

    .line 468
    move-wide v9, v7

    .line 471
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

    .line 474
    new-instance v6, Ljava/net/URL;

    invoke-direct {v6, v0}, Ljava/net/URL;-><init>(Ljava/lang/String;)V

    .line 476
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

    .line 477
    const/16 v6, 0x3a98

    invoke-virtual {v3, v6}, Ljava/net/HttpURLConnection;->setConnectTimeout(I)V

    .line 478
    const/16 v6, 0x7530

    invoke-virtual {v3, v6}, Ljava/net/HttpURLConnection;->setReadTimeout(I)V

    .line 479
    const/4 v6, 0x0

    invoke-virtual {v3, v6}, Ljava/net/HttpURLConnection;->setUseCaches(Z)V

    .line 480
    const-string v11, "Accept-Encoding"

    const-string v13, "identity"

    invoke-virtual {v3, v11, v13}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 481
    const-string v11, "Connection"

    const-string v13, "close"

    invoke-virtual {v3, v11, v13}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 483
    invoke-static/range {p1 .. p1}, Lio/kamihama/magianative/CNDownloaderFix;->readSidecarEtag(Ljava/io/File;)Ljava/lang/String;

    move-result-object v11

    .line 484
    cmp-long v13, v9, v7

    if-lez v13, :cond_6

    .line 485
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

    .line 486
    invoke-virtual {v11}, Ljava/lang/String;->length()I

    move-result v14

    if-lez v14, :cond_6

    .line 487
    const-string v14, "If-Range"

    invoke-virtual {v3, v14, v11}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 491
    :cond_6
    nop

    .line 492
    nop

    .line 494
    :try_start_0
    invoke-virtual {v3}, Ljava/net/HttpURLConnection;->getResponseCode()I

    move-result v15

    .line 495
    const-string v14, "ETag"

    invoke-virtual {v3, v14}, Ljava/net/HttpURLConnection;->getHeaderField(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v14

    invoke-static {v14}, Lio/kamihama/magianative/CNDownloaderFix;->cleanHeader(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v14
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_8

    .line 501
    const/16 v6, 0xc8

    if-lez v13, :cond_8

    if-eq v15, v6, :cond_7

    goto :goto_5

    .line 503
    :cond_7
    :try_start_1
    invoke-static {v4}, Lio/kamihama/magianative/CNDownloaderFix;->truncate(Ljava/io/File;)V

    .line 504
    invoke-static {v5}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    .line 505
    invoke-static/range {p2 .. p2}, Lio/kamihama/magianative/CNDownloaderFix;->resetProgress(I)V

    .line 506
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

    .line 601
    :catchall_0
    move-exception v0

    :goto_3
    const/4 v2, 0x0

    :goto_4
    const/4 v14, 0x0

    goto/16 :goto_d

    .line 507
    :cond_8
    :goto_5
    const-string v7, "Content-Length"

    const-string v8, "Content-Range"

    if-lez v13, :cond_c

    const/16 v6, 0xce

    if-ne v15, v6, :cond_c

    .line 508
    :try_start_2
    invoke-virtual {v3, v8}, Ljava/net/HttpURLConnection;->getHeaderField(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lio/kamihama/magianative/CNDownloaderFix;->parseContentRange(Ljava/lang/String;)Lio/kamihama/magianative/CNDownloaderFix$ContentRange;

    move-result-object v0

    .line 509
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

    .line 515
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

    .line 516
    :cond_9
    invoke-static {v4}, Lio/kamihama/magianative/CNDownloaderFix;->truncate(Ljava/io/File;)V

    .line 517
    invoke-static {v5}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    .line 518
    invoke-static/range {p2 .. p2}, Lio/kamihama/magianative/CNDownloaderFix;->resetProgress(I)V

    .line 519
    new-instance v0, Lio/kamihama/magianative/CNDownloaderFix$ResetRequired;

    const-string v1, "ETag changed while resuming"

    invoke-direct {v0, v1}, Lio/kamihama/magianative/CNDownloaderFix$ResetRequired;-><init>(Ljava/lang/String;)V

    throw v0

    .line 521
    :cond_a
    :goto_6
    iget-wide v1, v0, Lio/kamihama/magianative/CNDownloaderFix$ContentRange;->total:J

    .line 522
    iget-wide v11, v0, Lio/kamihama/magianative/CNDownloaderFix$ContentRange;->end:J

    move-wide/from16 v21, v1

    iget-wide v0, v0, Lio/kamihama/magianative/CNDownloaderFix$ContentRange;->start:J

    sub-long/2addr v11, v0

    const-wide/16 v0, 0x1

    add-long/2addr v11, v0

    .line 523
    nop

    .line 524
    const/4 v0, 0x1

    move-wide/from16 v23, v11

    move-wide/from16 v11, v21

    const-wide/16 v1, -0x1

    goto :goto_7

    .line 510
    :cond_b
    invoke-static {v4}, Lio/kamihama/magianative/CNDownloaderFix;->truncate(Ljava/io/File;)V

    .line 511
    invoke-static {v5}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    .line 512
    invoke-static/range {p2 .. p2}, Lio/kamihama/magianative/CNDownloaderFix;->resetProgress(I)V

    .line 513
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

    .line 524
    :cond_c
    if-nez v13, :cond_15

    const/16 v1, 0xc8

    if-ne v15, v1, :cond_15

    .line 525
    invoke-virtual {v3, v7}, Ljava/net/HttpURLConnection;->getHeaderField(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-wide/16 v1, -0x1

    invoke-static {v0, v1, v2}, Lio/kamihama/magianative/CNDownloaderFix;->parsePositiveLong(Ljava/lang/String;J)J

    move-result-wide v11

    .line 526
    nop

    .line 527
    move-wide/from16 v23, v11

    const/4 v0, 0x0

    .line 545
    :goto_7
    invoke-virtual {v3, v7}, Ljava/net/HttpURLConnection;->getHeaderField(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v6

    invoke-static {v6, v1, v2}, Lio/kamihama/magianative/CNDownloaderFix;->parsePositiveLong(Ljava/lang/String;J)J

    move-result-wide v1

    .line 546
    move-wide/from16 v6, v23

    const-wide/16 v16, 0x0

    cmp-long v8, v6, v16

    if-ltz v8, :cond_e

    cmp-long v13, v1, v16

    if-ltz v13, :cond_e

    cmp-long v13, v6, v1

    if-nez v13, :cond_d

    goto :goto_8

    .line 547
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

    .line 550
    :cond_e
    :goto_8
    const-wide/16 v1, 0x0

    cmp-long v13, v11, v1

    if-lez v13, :cond_14

    .line 554
    invoke-static {v5, v14, v11, v12}, Lio/kamihama/magianative/CNDownloaderFix;->writeSidecar(Ljava/io/File;Ljava/lang/String;J)V

    .line 555
    move/from16 v1, p2

    invoke-static {v1, v11, v12}, Lio/kamihama/magianative/CNDownloaderFix;->updateSize(IJ)V

    .line 556
    invoke-static {v1, v9, v10, v11, v12}, Lio/kamihama/magianative/CNDownloaderFix;->updateProgress(IJJ)V

    .line 558
    new-instance v2, Ljava/io/BufferedInputStream;

    invoke-virtual {v3}, Ljava/net/HttpURLConnection;->getInputStream()Ljava/io/InputStream;

    move-result-object v13

    const/high16 v15, 0x10000

    invoke-direct {v2, v13, v15}, Ljava/io/BufferedInputStream;-><init>(Ljava/io/InputStream;I)V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    .line 559
    :try_start_3
    new-instance v13, Ljava/io/FileOutputStream;

    invoke-direct {v13, v4, v0}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;Z)V
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_5

    .line 560
    :try_start_4
    move-object v0, v13

    check-cast v0, Ljava/io/FileOutputStream;

    .line 562
    new-array v0, v15, [B

    .line 563
    invoke-static {}, Ljava/lang/System;->nanoTime()J

    move-result-wide v18
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_4

    .line 564
    nop

    .line 565
    move-object/from16 v20, v14

    const-wide/16 v14, 0x0

    const-wide/16 v16, 0x0

    .line 567
    :goto_9
    move-object/from16 v21, v3

    :try_start_5
    invoke-virtual {v2, v0}, Ljava/io/InputStream;->read([B)I

    move-result v3

    if-ltz v3, :cond_10

    .line 568
    move-object/from16 v22, v5

    const/4 v5, 0x0

    invoke-virtual {v13, v0, v5, v3}, Ljava/io/FileOutputStream;->write([BII)V

    .line 569
    move-wide/from16 v23, v6

    int-to-long v5, v3

    add-long/2addr v14, v5

    .line 570
    invoke-static {}, Ljava/lang/System;->nanoTime()J

    move-result-wide v5

    .line 571
    sget-object v3, Lio/kamihama/magianative/CNDownloaderFix;->LAST_PROGRESS_NS:Ljava/util/concurrent/atomic/AtomicLongArray;

    invoke-virtual {v3, v1, v5, v6}, Ljava/util/concurrent/atomic/AtomicLongArray;->set(IJ)V
    :try_end_5
    .catchall {:try_start_5 .. :try_end_5} :catchall_3

    .line 572
    move-object/from16 p0, v2

    add-long v2, v9, v14

    :try_start_6
    invoke-static {v1, v2, v3, v11, v12}, Lio/kamihama/magianative/CNDownloaderFix;->updateProgress(IJJ)V

    .line 573
    sub-long v2, v5, v18

    .line 574
    sget-object v7, Ljava/util/concurrent/TimeUnit;->MILLISECONDS:Ljava/util/concurrent/TimeUnit;

    move-wide/from16 v25, v5

    const-wide/16 v5, 0x1f4

    invoke-virtual {v7, v5, v6}, Ljava/util/concurrent/TimeUnit;->toNanos(J)J

    move-result-wide v5

    cmp-long v7, v2, v5

    if-ltz v7, :cond_f

    .line 575
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

    .line 577
    nop

    .line 578
    move-wide/from16 v16, v14

    move-wide/from16 v18, v25

    .line 580
    :cond_f
    move-object/from16 v2, p0

    move-object/from16 v3, v21

    move-object/from16 v5, v22

    move-wide/from16 v6, v23

    goto :goto_9

    .line 581
    :cond_10
    move-object/from16 p0, v2

    move-object/from16 v22, v5

    move-wide/from16 v23, v6

    invoke-virtual {v13}, Ljava/io/FileOutputStream;->flush()V

    .line 582
    invoke-virtual {v13}, Ljava/io/FileOutputStream;->getFD()Ljava/io/FileDescriptor;

    move-result-object v0

    invoke-virtual {v0}, Ljava/io/FileDescriptor;->sync()V

    .line 584
    if-ltz v8, :cond_12

    cmp-long v0, v14, v23

    if-nez v0, :cond_11

    goto :goto_a

    .line 585
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

    .line 588
    :cond_12
    :goto_a
    invoke-virtual {v4}, Ljava/io/File;->length()J

    move-result-wide v0

    .line 589
    cmp-long v2, v0, v11

    if-nez v2, :cond_13

    .line 594
    invoke-static {v13}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/OutputStream;)V
    :try_end_6
    .catchall {:try_start_6 .. :try_end_6} :catchall_2

    .line 595
    :try_start_7
    invoke-static/range {p0 .. p0}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/InputStream;)V
    :try_end_7
    .catchall {:try_start_7 .. :try_end_7} :catchall_1

    .line 597
    move-object/from16 v2, p1

    :try_start_8
    invoke-static {v4, v2}, Lio/kamihama/magianative/CNDownloaderFix;->promotePart(Ljava/io/File;Ljava/io/File;)V

    .line 598
    invoke-static/range {v22 .. v22}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    .line 599
    new-instance v0, Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;

    move-object/from16 v1, v20

    invoke-direct {v0, v11, v12, v1}, Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;-><init>(JLjava/lang/String;)V
    :try_end_8
    .catchall {:try_start_8 .. :try_end_8} :catchall_6

    .line 601
    const/4 v1, 0x0

    invoke-static {v1}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/OutputStream;)V

    .line 602
    invoke-static {v1}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/InputStream;)V

    .line 603
    invoke-virtual/range {v21 .. v21}, Ljava/net/HttpURLConnection;->disconnect()V

    .line 599
    return-object v0

    .line 601
    :catchall_1
    move-exception v0

    move-object/from16 v2, p0

    move-object/from16 v3, v21

    goto/16 :goto_4

    .line 590
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

    .line 601
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

    .line 551
    :cond_14
    move-object/from16 v21, v3

    :try_start_a
    new-instance v0, Ljava/io/IOException;

    const-string v1, "Response does not declare a positive total length"

    invoke-direct {v0, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw v0
    :try_end_a
    .catchall {:try_start_a .. :try_end_a} :catchall_6

    .line 601
    :catchall_6
    move-exception v0

    move-object/from16 v3, v21

    goto/16 :goto_3

    .line 524
    :cond_15
    move-object/from16 v2, p1

    move/from16 v1, p2

    move-object/from16 v21, v3

    move-object/from16 v22, v5

    .line 528
    if-lez v13, :cond_17

    const/16 v3, 0x1a0

    if-ne v15, v3, :cond_17

    .line 529
    move-object/from16 v3, v21

    :try_start_b
    invoke-virtual {v3, v8}, Ljava/net/HttpURLConnection;->getHeaderField(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lio/kamihama/magianative/CNDownloaderFix;->parseUnsatisfiedTotal(Ljava/lang/String;)J

    move-result-wide v5

    .line 530
    const-wide/16 v7, 0x0

    cmp-long v0, v5, v7

    if-lez v0, :cond_16

    cmp-long v0, v5, v9

    if-nez v0, :cond_16

    .line 537
    invoke-static {v4, v2}, Lio/kamihama/magianative/CNDownloaderFix;->promotePart(Ljava/io/File;Ljava/io/File;)V

    .line 538
    invoke-static/range {v22 .. v22}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    .line 539
    new-instance v0, Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;

    invoke-direct {v0, v5, v6, v11}, Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;-><init>(JLjava/lang/String;)V
    :try_end_b
    .catchall {:try_start_b .. :try_end_b} :catchall_8

    .line 601
    const/4 v2, 0x0

    invoke-static {v2}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/OutputStream;)V

    .line 602
    invoke-static {v2}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/InputStream;)V

    .line 603
    invoke-virtual {v3}, Ljava/net/HttpURLConnection;->disconnect()V

    .line 539
    return-object v0

    .line 530
    :cond_16
    const/4 v2, 0x0

    .line 531
    :try_start_c
    invoke-static {v4}, Lio/kamihama/magianative/CNDownloaderFix;->truncate(Ljava/io/File;)V

    .line 532
    invoke-static/range {v22 .. v22}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    .line 533
    invoke-static/range {p2 .. p2}, Lio/kamihama/magianative/CNDownloaderFix;->resetProgress(I)V

    .line 534
    new-instance v0, Lio/kamihama/magianative/CNDownloaderFix$ResetRequired;

    const-string v1, "HTTP 416 did not match local length"

    invoke-direct {v0, v1}, Lio/kamihama/magianative/CNDownloaderFix$ResetRequired;-><init>(Ljava/lang/String;)V

    throw v0

    .line 528
    :cond_17
    move-object/from16 v3, v21

    const/4 v2, 0x0

    .line 541
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

    .line 601
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

    .line 602
    invoke-static {v2}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/InputStream;)V

    .line 603
    invoke-virtual {v3}, Ljava/net/HttpURLConnection;->disconnect()V

    .line 604
    throw v0
.end method

.method private static extractChecked(Ljava/io/File;Ljava/io/File;)V
    .locals 17
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 612
    move-object/from16 v0, p0

    move-object/from16 v1, p1

    invoke-virtual/range {p0 .. p0}, Ljava/io/File;->isFile()Z

    move-result v2

    if-eqz v2, :cond_e

    .line 615
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

    .line 616
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

    .line 618
    :cond_1
    :goto_0
    invoke-virtual/range {p1 .. p1}, Ljava/io/File;->getCanonicalPath()Ljava/lang/String;

    move-result-object v2

    .line 619
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v3, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    sget-object v4, Ljava/io/File;->separator:Ljava/lang/String;

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    .line 621
    new-instance v4, Ljava/util/zip/ZipFile;

    invoke-direct {v4, v0}, Ljava/util/zip/ZipFile;-><init>(Ljava/io/File;)V

    .line 623
    :try_start_0
    invoke-virtual {v4}, Ljava/util/zip/ZipFile;->entries()Ljava/util/Enumeration;

    move-result-object v5

    .line 624
    const/4 v6, 0x0

    const/4 v7, 0x0

    .line 625
    :cond_2
    :goto_1
    invoke-interface {v5}, Ljava/util/Enumeration;->hasMoreElements()Z

    move-result v8

    if-eqz v8, :cond_c

    .line 626
    invoke-interface {v5}, Ljava/util/Enumeration;->nextElement()Ljava/lang/Object;

    move-result-object v8

    check-cast v8, Ljava/util/zip/ZipEntry;

    .line 627
    new-instance v9, Ljava/io/File;

    invoke-virtual {v8}, Ljava/util/zip/ZipEntry;->getName()Ljava/lang/String;

    move-result-object v10

    invoke-direct {v9, v1, v10}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    .line 628
    invoke-virtual {v9}, Ljava/io/File;->getCanonicalPath()Ljava/lang/String;

    move-result-object v10

    .line 630
    invoke-virtual {v10, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v11

    if-nez v11, :cond_4

    invoke-virtual {v10, v3}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v10

    if-eqz v10, :cond_3

    goto :goto_2

    .line 631
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

    .line 633
    :cond_4
    :goto_2
    invoke-virtual {v8}, Ljava/util/zip/ZipEntry;->isDirectory()Z

    move-result v10
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_3

    const-string v11, "Cannot create directory "

    if-eqz v10, :cond_6

    .line 634
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

    .line 635
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

    .line 639
    :cond_6
    invoke-virtual {v9}, Ljava/io/File;->getParentFile()Ljava/io/File;

    move-result-object v7

    .line 640
    if-eqz v7, :cond_8

    invoke-virtual {v7}, Ljava/io/File;->isDirectory()Z

    move-result v10

    if-nez v10, :cond_8

    invoke-virtual {v7}, Ljava/io/File;->mkdirs()Z

    move-result v10

    if-nez v10, :cond_8

    .line 641
    invoke-virtual {v7}, Ljava/io/File;->isDirectory()Z

    move-result v10

    if-eqz v10, :cond_7

    goto :goto_3

    .line 642
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

    .line 644
    :cond_8
    :goto_3
    nop

    .line 645
    nop

    .line 647
    const/4 v7, 0x0

    :try_start_2
    new-instance v10, Ljava/io/BufferedInputStream;

    invoke-virtual {v4, v8}, Ljava/util/zip/ZipFile;->getInputStream(Ljava/util/zip/ZipEntry;)Ljava/io/InputStream;

    move-result-object v11

    const/high16 v12, 0x10000

    invoke-direct {v10, v11, v12}, Ljava/io/BufferedInputStream;-><init>(Ljava/io/InputStream;I)V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_2

    .line 648
    :try_start_3
    new-instance v11, Ljava/io/BufferedOutputStream;

    new-instance v13, Ljava/io/FileOutputStream;

    invoke-direct {v13, v9}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;)V

    invoke-direct {v11, v13, v12}, Ljava/io/BufferedOutputStream;-><init>(Ljava/io/OutputStream;I)V
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_1

    .line 649
    :try_start_4
    new-array v7, v12, [B

    .line 650
    const-wide/16 v12, 0x0

    move-wide v14, v12

    .line 652
    :goto_4
    invoke-virtual {v10, v7}, Ljava/io/InputStream;->read([B)I

    move-result v9

    if-ltz v9, :cond_9

    .line 653
    invoke-virtual {v11, v7, v6, v9}, Ljava/io/OutputStream;->write([BII)V

    .line 654
    move-object/from16 v16, v7

    int-to-long v6, v9

    add-long/2addr v14, v6

    move-object/from16 v7, v16

    const/4 v6, 0x0

    goto :goto_4

    .line 656
    :cond_9
    invoke-virtual {v11}, Ljava/io/OutputStream;->flush()V

    .line 657
    invoke-virtual {v8}, Ljava/util/zip/ZipEntry;->getSize()J

    move-result-wide v6

    cmp-long v9, v6, v12

    if-ltz v9, :cond_b

    invoke-virtual {v8}, Ljava/util/zip/ZipEntry;->getSize()J

    move-result-wide v6

    cmp-long v9, v14, v6

    if-nez v9, :cond_a

    goto :goto_5

    .line 658
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

    .line 660
    :cond_b
    :goto_5
    nop

    .line 662
    :try_start_5
    invoke-static {v11}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/OutputStream;)V

    .line 663
    invoke-static {v10}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/InputStream;)V

    .line 664
    nop

    .line 665
    const/4 v7, 0x1

    const/4 v6, 0x0

    goto/16 :goto_1

    .line 662
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

    .line 663
    invoke-static {v10}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/InputStream;)V

    .line 664
    throw v0
    :try_end_5
    .catchall {:try_start_5 .. :try_end_5} :catchall_3

    .line 666
    :cond_c
    if-eqz v7, :cond_d

    .line 670
    invoke-virtual {v4}, Ljava/util/zip/ZipFile;->close()V

    .line 671
    nop

    .line 672
    return-void

    .line 667
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

    .line 670
    :catchall_3
    move-exception v0

    invoke-virtual {v4}, Ljava/util/zip/ZipFile;->close()V

    .line 671
    throw v0

    .line 613
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

    .line 151
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

    .line 152
    invoke-virtual {p0}, Ljava/util/regex/Matcher;->find()Z

    move-result p1

    const/4 v0, -0x1

    if-nez p1, :cond_0

    .line 153
    return v0

    .line 156
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

    .line 157
    :catch_0
    move-exception p0

    .line 158
    return v0
.end method

.method private static failInstaller(Ljava/lang/String;Ljava/lang/Throwable;)V
    .locals 1

    .line 1019
    invoke-static {}, Lio/kamihama/magianative/CNDownloaderFix;->zeroAllSpeeds()V

    .line 1020
    const-string v0, "MagiaCNDownloader"

    if-nez p1, :cond_0

    .line 1021
    invoke-static {v0, p0}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0

    .line 1023
    :cond_0
    invoke-static {v0, p0, p1}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 1025
    :goto_0
    const-string p1, "\u5b89\u88c5\u6682\u505c"

    const/4 v0, 0x0

    invoke-static {p1, p0, v0}, Lio/kamihama/magianative/CNCNDownloadUI;->updateSimple(Ljava/lang/String;Ljava/lang/String;I)V

    .line 1026
    return-void
.end method

.method private static fetchArchive(Lio/kamihama/magianative/CNMirrors$Mirror;Ljava/lang/String;Ljava/io/File;IZ)Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;
    .locals 15
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 384
    move-object v0, p0

    move-object/from16 v1, p1

    move/from16 v2, p3

    move/from16 v3, p4

    invoke-virtual/range {p2 .. p2}, Ljava/io/File;->isFile()Z

    move-result v4

    if-eqz v4, :cond_0

    .line 385
    new-instance v0, Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;

    invoke-virtual/range {p2 .. p2}, Ljava/io/File;->length()J

    move-result-wide v1

    invoke-static/range {p2 .. p2}, Lio/kamihama/magianative/CNDownloaderFix;->readSidecarEtag(Ljava/io/File;)Ljava/lang/String;

    move-result-object v3

    invoke-direct {v0, v1, v2, v3}, Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;-><init>(JLjava/lang/String;)V

    return-object v0

    .line 388
    :cond_0
    invoke-virtual/range {p0 .. p1}, Lio/kamihama/magianative/CNMirrors$Mirror;->urlFor(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    .line 389
    invoke-virtual {p0}, Lio/kamihama/magianative/CNMirrors$Mirror;->effectiveChunks()I

    move-result v5

    .line 391
    const/4 v6, 0x1

    if-le v5, v6, :cond_3

    .line 392
    invoke-static {v4, v3}, Lio/kamihama/magianative/CNChunkedDownload;->probe(Ljava/lang/String;Z)Lio/kamihama/magianative/CNChunkedDownload$Probe;

    move-result-object v7

    .line 393
    iget-boolean v8, v7, Lio/kamihama/magianative/CNChunkedDownload$Probe;->rangeSupported:Z

    const-string v9, " mirror="

    const-string v10, "MagiaCNDownloader"

    if-eqz v8, :cond_2

    iget-wide v11, v7, Lio/kamihama/magianative/CNChunkedDownload$Probe;->total:J

    const-wide/16 v13, 0x0

    cmp-long v8, v11, v13

    if-lez v8, :cond_2

    .line 394
    nop

    .line 395
    invoke-static {}, Lio/kamihama/magianative/CNMirrors;->minChunkBytes()J

    move-result-wide v11

    .line 396
    cmp-long v8, v11, v13

    if-lez v8, :cond_1

    .line 397
    iget-wide v13, v7, Lio/kamihama/magianative/CNChunkedDownload$Probe;->total:J

    div-long/2addr v13, v11

    .line 398
    int-to-long v11, v5

    cmp-long v8, v13, v11

    if-gez v8, :cond_1

    const-wide/16 v11, 0x1

    invoke-static {v11, v12, v13, v14}, Ljava/lang/Math;->max(JJ)J

    move-result-wide v11

    long-to-int v5, v11

    .line 400
    :cond_1
    if-le v5, v6, :cond_2

    .line 401
    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "chunked-download file="

    invoke-virtual {v6, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-object v0, v0, Lio/kamihama/magianative/CNMirrors$Mirror;->name:Ljava/lang/String;

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " chunks="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v5}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " bytes="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-wide v8, v7, Lio/kamihama/magianative/CNChunkedDownload$Probe;->total:J

    invoke-virtual {v0, v8, v9}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " direct="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v10, v0}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 403
    iget-wide v0, v7, Lio/kamihama/magianative/CNChunkedDownload$Probe;->total:J

    invoke-static {v2, v0, v1}, Lio/kamihama/magianative/CNDownloaderFix;->updateSize(IJ)V

    .line 404
    iget-wide v0, v7, Lio/kamihama/magianative/CNChunkedDownload$Probe;->total:J

    const-wide/16 v8, 0x0

    invoke-static {v2, v8, v9, v0, v1}, Lio/kamihama/magianative/CNDownloaderFix;->updateProgress(IJJ)V

    .line 405
    new-instance v6, Lio/kamihama/magianative/CNDownloaderFix$ArchiveSink;

    invoke-direct {v6, v2}, Lio/kamihama/magianative/CNDownloaderFix$ArchiveSink;-><init>(I)V

    move-object v0, v4

    move-object/from16 v1, p2

    move v2, v5

    move/from16 v3, p4

    move-object v4, v7

    move-object v5, v6

    invoke-static/range {v0 .. v5}, Lio/kamihama/magianative/CNChunkedDownload;->download(Ljava/lang/String;Ljava/io/File;IZLio/kamihama/magianative/CNChunkedDownload$Probe;Lio/kamihama/magianative/CNChunkedDownload$Sink;)Lio/kamihama/magianative/CNChunkedDownload$Result;

    move-result-object v0

    .line 407
    new-instance v1, Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;

    iget-wide v2, v0, Lio/kamihama/magianative/CNChunkedDownload$Result;->totalBytes:J

    iget-object v0, v0, Lio/kamihama/magianative/CNChunkedDownload$Result;->etag:Ljava/lang/String;

    invoke-direct {v1, v2, v3, v0}, Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;-><init>(JLjava/lang/String;)V

    return-object v1

    .line 410
    :cond_2
    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "range-unsupported-or-small file="

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-object v0, v0, Lio/kamihama/magianative/CNMirrors$Mirror;->name:Ljava/lang/String;

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " \u2192 \u5355\u7ebf\u7a0b\u7eed\u4f20"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v10, v0}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 413
    :cond_3
    move-object/from16 v0, p2

    invoke-static {v4, v0, v2, v3}, Lio/kamihama/magianative/CNDownloaderFix;->downloadOnce(Ljava/lang/String;Ljava/io/File;IZ)Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;

    move-result-object v0

    return-object v0
.end method

.method public static getEndpoint(I)Ljava/lang/String;
    .locals 2

    .line 105
    :try_start_0
    invoke-static {p0}, Lio/kamihama/magianative/CNDownloaderFix;->getEndpointInner(I)Ljava/lang/String;

    move-result-object p0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    return-object p0

    .line 106
    :catchall_0
    move-exception p0

    .line 107
    :try_start_1
    const-string v0, "MagiaCNDownloader"

    const-string v1, "getEndpoint \u53d1\u751f\u672a\u9884\u671f\u9519\u8bef\uff0c\u8fd4\u56de\u7a7a\u4e32"

    invoke-static {v0, v1, p0}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    goto :goto_0

    :catchall_1
    move-exception p0

    .line 108
    :goto_0
    const-string p0, ""

    return-object p0
.end method

.method private static getEndpointInner(I)Ljava/lang/String;
    .locals 8

    .line 113
    const-string v0, "snaa-response direct=true body="

    const-string v1, "https://totentanz-9b.magi-reco.com/magica/api/snaa"

    const/16 v2, 0x80

    invoke-static {p0, v2}, Ljava/lang/Math;->max(II)I

    move-result v2

    .line 114
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

    .line 115
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

    .line 116
    nop

    .line 118
    const/4 p0, 0x1

    const/4 v5, 0x0

    const/4 v6, 0x0

    :try_start_0
    invoke-static {v1, v3, v6}, Lio/kamihama/magianative/CNDownloaderFix;->postJson(Ljava/lang/String;Ljava/lang/String;Z)Ljava/lang/String;

    move-result-object v5

    .line 119
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

    .line 120
    invoke-static {v5, v2}, Lio/kamihama/magianative/CNDownloaderFix;->isSnaaResponseCurrent(Ljava/lang/String;I)Z

    move-result v2

    if-eqz v2, :cond_0

    .line 121
    return-object v5

    .line 123
    :cond_0
    const-string v2, "SNAA response is stale/incompatible; retrying direct"

    invoke-static {v4, v2}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;)V

    .line 124
    invoke-static {v1, v3, p0}, Lio/kamihama/magianative/CNDownloaderFix;->postJson(Ljava/lang/String;Ljava/lang/String;Z)Ljava/lang/String;

    move-result-object v2

    .line 125
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

    .line 126
    return-object v2

    .line 127
    :catch_0
    move-exception v2

    .line 128
    const-string v6, "SNAA via configured network failed; retrying direct"

    invoke-static {v4, v6, v2}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 130
    :try_start_1
    invoke-static {v1, v3, p0}, Lio/kamihama/magianative/CNDownloaderFix;->postJson(Ljava/lang/String;Ljava/lang/String;Z)Ljava/lang/String;

    move-result-object p0

    .line 131
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

    .line 132
    return-object p0

    .line 133
    :catch_1
    move-exception p0

    .line 134
    invoke-virtual {p0, v2}, Ljava/io/IOException;->addSuppressed(Ljava/lang/Throwable;)V

    .line 135
    const-string v0, "SNAA discovery failed"

    invoke-static {v4, v0, p0}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 136
    if-nez v5, :cond_1

    const-string v5, ""

    :cond_1
    return-object v5
.end method

.method private static installArchive(I)Z
    .locals 14

    .line 287
    sget-object v0, Lio/kamihama/magianative/CNDownloaderFix;->FILE_NAMES:[Ljava/lang/String;

    aget-object v0, v0, p0

    .line 288
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "https://assets.magireco.top/"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 289
    new-instance v2, Ljava/io/File;

    const-string v3, "/data/data/io.kamihama.totentanz/files"

    invoke-direct {v2, v3, v0}, Ljava/io/File;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    .line 290
    invoke-static {v0}, Lio/kamihama/magianative/CNDownloaderFix;->markerFor(Ljava/lang/String;)Ljava/io/File;

    move-result-object v3

    .line 292
    invoke-static {v3, v0, v1}, Lio/kamihama/magianative/CNDownloaderFix;->isMarkerValid(Ljava/io/File;Ljava/lang/String;Ljava/lang/String;)Z

    move-result v4

    const/4 v5, 0x1

    if-eqz v4, :cond_0

    .line 293
    invoke-static {p0}, Lio/kamihama/magianative/CNDownloaderFix;->markDone(I)V

    .line 294
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

    .line 295
    return v5

    .line 298
    :cond_0
    const/4 v4, 0x1

    :goto_0
    const/4 v6, 0x4

    const/4 v7, 0x0

    if-gt v4, v6, :cond_6

    .line 299
    invoke-static {}, Ljava/lang/Thread;->currentThread()Ljava/lang/Thread;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/Thread;->isInterrupted()Z

    move-result v8

    if-eqz v8, :cond_1

    .line 300
    invoke-static {p0}, Lio/kamihama/magianative/CNDownloaderFix;->markFailed(I)V

    .line 301
    return v7

    .line 304
    :cond_1
    invoke-static {v4}, Lio/kamihama/magianative/CNMirrors;->pick(I)Lio/kamihama/magianative/CNMirrors$Mirror;

    move-result-object v8

    .line 305
    rem-int/lit8 v9, v4, 0x2

    if-nez v9, :cond_2

    const/4 v9, 0x1

    goto :goto_1

    :cond_2
    const/4 v9, 0x0

    .line 307
    :goto_1
    invoke-static {p0, v5}, Lio/kamihama/magianative/CNDownloaderFix;->setActive(IZ)V

    .line 308
    const/4 v10, 0x0

    invoke-static {p0, v10}, Lio/kamihama/magianative/CNCNDownloadUI;->setDownloadSpeed(IF)V

    .line 310
    :try_start_0
    invoke-static {v8, v0, v2, p0, v9}, Lio/kamihama/magianative/CNDownloaderFix;->fetchArchive(Lio/kamihama/magianative/CNMirrors$Mirror;Ljava/lang/String;Ljava/io/File;IZ)Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;

    move-result-object v9

    .line 311
    sget-object v11, Lio/kamihama/magianative/CNDownloaderFix;->EXTRACT_LOCK:Ljava/lang/Object;

    monitor-enter v11
    :try_end_0
    .catch Lio/kamihama/magianative/CNDownloaderFix$ResetRequired; {:try_start_0 .. :try_end_0} :catch_3
    .catch Ljava/util/zip/ZipException; {:try_start_0 .. :try_end_0} :catch_2
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_1
    .catch Ljava/lang/RuntimeException; {:try_start_0 .. :try_end_0} :catch_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    .line 312
    :try_start_1
    new-instance v12, Ljava/io/File;

    const-string v13, "/data/data/io.kamihama.totentanz/files/"

    invoke-direct {v12, v13}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-static {v2, v12}, Lio/kamihama/magianative/CNDownloaderFix;->extractChecked(Ljava/io/File;Ljava/io/File;)V

    .line 313
    monitor-exit v11
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 314
    :try_start_2
    invoke-static {v3, v0, v1, v9}, Lio/kamihama/magianative/CNDownloaderFix;->writeMarker(Ljava/io/File;Ljava/lang/String;Ljava/lang/String;Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;)V

    .line 315
    invoke-virtual {v2}, Ljava/io/File;->delete()Z

    move-result v9

    if-nez v9, :cond_3

    invoke-virtual {v2}, Ljava/io/File;->exists()Z

    move-result v9

    if-eqz v9, :cond_3

    .line 316
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

    .line 320
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

    .line 321
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

    .line 322
    invoke-static {v2}, Lio/kamihama/magianative/CNChunkedDownload;->partFileFor(Ljava/io/File;)Ljava/io/File;

    move-result-object v9

    invoke-static {v9}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    .line 323
    invoke-static {v2}, Lio/kamihama/magianative/CNChunkedDownload;->metaFileFor(Ljava/io/File;)Ljava/io/File;

    move-result-object v9

    invoke-static {v9}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    .line 324
    invoke-static {v8}, Lio/kamihama/magianative/CNMirrors;->reportSuccess(Lio/kamihama/magianative/CNMirrors$Mirror;)V

    .line 325
    invoke-static {p0}, Lio/kamihama/magianative/CNDownloaderFix;->markDone(I)V

    .line 326
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

    .line 328
    nop

    .line 351
    invoke-static {p0, v7}, Lio/kamihama/magianative/CNDownloaderFix;->setActive(IZ)V

    .line 352
    invoke-static {p0, v10}, Lio/kamihama/magianative/CNCNDownloadUI;->setDownloadSpeed(IF)V

    .line 353
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->throttledUpdate()V

    .line 328
    return v5

    .line 313
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

    .line 351
    :catchall_1
    move-exception v0

    goto/16 :goto_4

    .line 347
    :catch_0
    move-exception v9

    .line 348
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

    .line 349
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

    .line 340
    :catch_1
    move-exception v9

    .line 341
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

    .line 343
    invoke-virtual {v9}, Ljava/io/IOException;->getMessage()Ljava/lang/String;

    move-result-object v9

    invoke-static {v9}, Ljava/lang/String;->valueOf(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9}, Lio/kamihama/magianative/CNMirrors;->reportFailure(Lio/kamihama/magianative/CNMirrors$Mirror;Ljava/lang/String;)V

    .line 344
    invoke-virtual {v2}, Ljava/io/File;->isFile()Z

    move-result v8

    if-eqz v8, :cond_4

    .line 345
    invoke-static {v2}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    goto/16 :goto_2

    .line 332
    :catch_2
    move-exception v9

    .line 333
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

    .line 334
    const-string v9, "corrupt-zip"

    invoke-static {v8, v9}, Lio/kamihama/magianative/CNMirrors;->reportFailure(Lio/kamihama/magianative/CNMirrors$Mirror;Ljava/lang/String;)V

    .line 335
    invoke-static {v2}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    .line 336
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

    .line 337
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

    .line 338
    invoke-static {v2}, Lio/kamihama/magianative/CNChunkedDownload;->partFileFor(Ljava/io/File;)Ljava/io/File;

    move-result-object v8

    invoke-static {v8}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    .line 339
    invoke-static {v2}, Lio/kamihama/magianative/CNChunkedDownload;->metaFileFor(Ljava/io/File;)Ljava/io/File;

    move-result-object v8

    invoke-static {v8}, Lio/kamihama/magianative/CNDownloaderFix;->deleteQuietly(Ljava/io/File;)V

    goto :goto_2

    .line 329
    :catch_3
    move-exception v8

    .line 330
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

    .line 331
    invoke-virtual {v8}, Lio/kamihama/magianative/CNDownloaderFix$ResetRequired;->getMessage()Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v11, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    .line 330
    invoke-static {v9, v8}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_5
    .catchall {:try_start_5 .. :try_end_5} :catchall_1

    .line 351
    :cond_4
    :goto_2
    invoke-static {p0, v7}, Lio/kamihama/magianative/CNDownloaderFix;->setActive(IZ)V

    .line 352
    invoke-static {p0, v10}, Lio/kamihama/magianative/CNCNDownloadUI;->setDownloadSpeed(IF)V

    .line 353
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->throttledUpdate()V

    .line 354
    nop

    .line 356
    if-ge v4, v6, :cond_5

    .line 357
    add-int/lit8 v6, v4, -0x1

    const-wide/16 v8, 0x7d0

    shl-long/2addr v8, v6

    .line 358
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

    .line 360
    :try_start_6
    invoke-static {v8, v9}, Ljava/lang/Thread;->sleep(J)V
    :try_end_6
    .catch Ljava/lang/InterruptedException; {:try_start_6 .. :try_end_6} :catch_4

    .line 365
    goto :goto_3

    .line 361
    :catch_4
    move-exception v0

    .line 362
    invoke-static {}, Ljava/lang/Thread;->currentThread()Ljava/lang/Thread;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Thread;->interrupt()V

    .line 363
    invoke-static {p0}, Lio/kamihama/magianative/CNDownloaderFix;->markFailed(I)V

    .line 364
    return v7

    .line 298
    :cond_5
    :goto_3
    add-int/lit8 v4, v4, 0x1

    goto/16 :goto_0

    .line 351
    :goto_4
    invoke-static {p0, v7}, Lio/kamihama/magianative/CNDownloaderFix;->setActive(IZ)V

    .line 352
    invoke-static {p0, v10}, Lio/kamihama/magianative/CNCNDownloadUI;->setDownloadSpeed(IF)V

    .line 353
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->throttledUpdate()V

    .line 354
    throw v0

    .line 369
    :cond_6
    invoke-static {p0}, Lio/kamihama/magianative/CNDownloaderFix;->markFailed(I)V

    .line 370
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

    .line 371
    return v7
.end method

.method private static isMarkerValid(Ljava/io/File;Ljava/lang/String;Ljava/lang/String;)Z
    .locals 7

    .line 758
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

    .line 762
    :cond_0
    :try_start_0
    invoke-static {p0}, Lio/kamihama/magianative/CNDownloaderFix;->readSmallUtf8(Ljava/io/File;)Ljava/lang/String;

    move-result-object v1

    .line 763
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

    .line 764
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

    .line 765
    invoke-virtual {v1, p1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result p1

    if-eqz p1, :cond_1

    .line 766
    const-string p1, "(?s).*\\nbytes=[1-9][0-9]*\\n.*"

    invoke-virtual {v1, p1}, Ljava/lang/String;->matches(Ljava/lang/String;)Z

    move-result p0
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    return p0

    .line 768
    :cond_1
    return v2

    .line 769
    :catch_0
    move-exception p1

    .line 770
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

    .line 771
    return v2

    .line 759
    :cond_2
    :goto_0
    return v2
.end method

.method private static isSnaaResponseCurrent(Ljava/lang/String;I)Z
    .locals 3

    .line 142
    const/4 v0, 0x0

    if-eqz p0, :cond_2

    const-string v1, "(?s).*\"endpoint\"\\s*:\\s*\"https://[^\"]+\".*"

    invoke-virtual {p0, v1}, Ljava/lang/String;->matches(Ljava/lang/String;)Z

    move-result v1

    if-nez v1, :cond_0

    goto :goto_1

    .line 145
    :cond_0
    const-string v1, "status"

    invoke-static {p0, v1}, Lio/kamihama/magianative/CNDownloaderFix;->extractJsonInt(Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    const/16 v2, 0xc8

    if-ne v1, v2, :cond_1

    .line 146
    const-string v1, "version"

    invoke-static {p0, v1}, Lio/kamihama/magianative/CNDownloaderFix;->extractJsonInt(Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    if-lt v1, p1, :cond_1

    .line 147
    const-string p1, "max_threads"

    invoke-static {p0, p1}, Lio/kamihama/magianative/CNDownloaderFix;->extractJsonInt(Ljava/lang/String;Ljava/lang/String;)I

    move-result p0

    if-lez p0, :cond_1

    const/4 v0, 0x1

    goto :goto_0

    :cond_1
    nop

    .line 145
    :goto_0
    return v0

    .line 143
    :cond_2
    :goto_1
    return v0
.end method

.method private static markDone(I)V
    .locals 1

    .line 990
    const/4 v0, 0x0

    invoke-static {p0, v0}, Lio/kamihama/magianative/CNDownloaderFix;->setActive(IZ)V

    .line 991
    const/4 v0, 0x0

    invoke-static {p0, v0}, Lio/kamihama/magianative/CNCNDownloadUI;->setDownloadSpeed(IF)V

    .line 992
    invoke-static {p0}, Lio/kamihama/magianative/CNCNDownloadUI;->markFileDone(I)V

    .line 993
    return-void
.end method

.method private static markFailed(I)V
    .locals 2

    .line 996
    const/4 v0, 0x0

    invoke-static {p0, v0}, Lio/kamihama/magianative/CNDownloaderFix;->setActive(IZ)V

    .line 997
    const/4 v0, 0x0

    invoke-static {p0, v0}, Lio/kamihama/magianative/CNCNDownloadUI;->setDownloadSpeed(IF)V

    .line 998
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->fileStatus:[I

    if-eqz v0, :cond_0

    .line 999
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->fileStatus:[I

    const/4 v1, 0x3

    aput v1, v0, p0

    .line 1001
    :cond_0
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->throttledUpdate()V

    .line 1002
    return-void
.end method

.method private static markerFor(Ljava/lang/String;)Ljava/io/File;
    .locals 2

    .line 786
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

    .line 900
    const/4 v0, 0x0

    if-nez p0, :cond_0

    .line 901
    return-object v0

    .line 903
    :cond_0
    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    sget-object v1, Ljava/util/Locale;->US:Ljava/util/Locale;

    invoke-virtual {p0, v1}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object p0

    .line 904
    const-string v1, "bytes "

    invoke-virtual {p0, v1}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v1

    if-nez v1, :cond_1

    .line 905
    return-object v0

    .line 907
    :cond_1
    const/16 v1, 0x2d

    const/4 v2, 0x6

    invoke-virtual {p0, v1, v2}, Ljava/lang/String;->indexOf(II)I

    move-result v1

    .line 908
    add-int/lit8 v3, v1, 0x1

    const/16 v4, 0x2f

    invoke-virtual {p0, v4, v3}, Ljava/lang/String;->indexOf(II)I

    move-result v4

    .line 909
    if-ltz v1, :cond_3

    if-gez v4, :cond_2

    goto :goto_0

    .line 913
    :cond_2
    :try_start_0
    new-instance v12, Lio/kamihama/magianative/CNDownloaderFix$ContentRange;

    .line 914
    invoke-virtual {p0, v2, v1}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v6

    .line 915
    invoke-virtual {p0, v3, v4}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v8

    add-int/lit8 v4, v4, 0x1

    .line 916
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

    .line 913
    return-object v12

    .line 917
    :catch_0
    move-exception p0

    .line 918
    return-object v0

    .line 910
    :cond_3
    :goto_0
    return-object v0
.end method

.method private static parsePositiveLong(Ljava/lang/String;J)J
    .locals 4

    .line 934
    if-nez p0, :cond_0

    .line 935
    return-wide p1

    .line 938
    :cond_0
    :try_start_0
    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Ljava/lang/Long;->parseLong(Ljava/lang/String;)J

    move-result-wide v0
    :try_end_0
    .catch Ljava/lang/NumberFormatException; {:try_start_0 .. :try_end_0} :catch_0

    .line 939
    const-wide/16 v2, 0x0

    cmp-long p0, v0, v2

    if-ltz p0, :cond_1

    move-wide p1, v0

    :cond_1
    return-wide p1

    .line 940
    :catch_0
    move-exception p0

    .line 941
    return-wide p1
.end method

.method private static parseUnsatisfiedTotal(Ljava/lang/String;)J
    .locals 3

    .line 923
    const-wide/16 v0, -0x1

    if-nez p0, :cond_0

    .line 924
    return-wide v0

    .line 926
    :cond_0
    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    sget-object v2, Ljava/util/Locale;->US:Ljava/util/Locale;

    invoke-virtual {p0, v2}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object p0

    .line 927
    const-string v2, "bytes */"

    invoke-virtual {p0, v2}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v2

    if-nez v2, :cond_1

    .line 928
    return-wide v0

    .line 930
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

    .line 710
    new-instance v0, Ljava/net/URL;

    invoke-direct {v0, p0}, Ljava/net/URL;-><init>(Ljava/lang/String;)V

    .line 712
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

    .line 713
    const/16 p2, 0x3a98

    invoke-virtual {p0, p2}, Ljava/net/HttpURLConnection;->setConnectTimeout(I)V

    .line 714
    const/16 p2, 0x7530

    invoke-virtual {p0, p2}, Ljava/net/HttpURLConnection;->setReadTimeout(I)V

    .line 715
    const-string p2, "POST"

    invoke-virtual {p0, p2}, Ljava/net/HttpURLConnection;->setRequestMethod(Ljava/lang/String;)V

    .line 716
    const/4 p2, 0x1

    invoke-virtual {p0, p2}, Ljava/net/HttpURLConnection;->setDoOutput(Z)V

    .line 717
    const/4 p2, 0x0

    invoke-virtual {p0, p2}, Ljava/net/HttpURLConnection;->setUseCaches(Z)V

    .line 718
    const-string v0, "Content-Type"

    const-string v1, "application/json; charset=utf-8"

    invoke-virtual {p0, v0, v1}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 719
    const-string v0, "Accept"

    const-string v1, "application/json"

    invoke-virtual {p0, v0, v1}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 720
    const-string v0, "Connection"

    const-string v1, "close"

    invoke-virtual {p0, v0, v1}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 722
    nop

    .line 723
    nop

    .line 725
    const/4 v0, 0x0

    :try_start_0
    sget-object v1, Ljava/nio/charset/StandardCharsets;->UTF_8:Ljava/nio/charset/Charset;

    invoke-virtual {p1, v1}, Ljava/lang/String;->getBytes(Ljava/nio/charset/Charset;)[B

    move-result-object p1

    .line 726
    array-length v1, p1

    invoke-virtual {p0, v1}, Ljava/net/HttpURLConnection;->setFixedLengthStreamingMode(I)V

    .line 727
    invoke-virtual {p0}, Ljava/net/HttpURLConnection;->getOutputStream()Ljava/io/OutputStream;

    move-result-object v1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    .line 728
    :try_start_1
    invoke-virtual {v1, p1}, Ljava/io/OutputStream;->write([B)V

    .line 729
    invoke-virtual {v1}, Ljava/io/OutputStream;->flush()V

    .line 731
    invoke-virtual {p0}, Ljava/net/HttpURLConnection;->getResponseCode()I

    move-result p1

    .line 732
    const/16 v2, 0xc8

    if-lt p1, v2, :cond_2

    const/16 v2, 0x12c

    if-ge p1, v2, :cond_2

    .line 735
    invoke-virtual {p0}, Ljava/net/HttpURLConnection;->getInputStream()Ljava/io/InputStream;

    move-result-object v0

    .line 736
    new-instance p1, Ljava/io/ByteArrayOutputStream;

    invoke-direct {p1}, Ljava/io/ByteArrayOutputStream;-><init>()V

    .line 737
    const/16 v2, 0x2000

    new-array v2, v2, [B

    .line 739
    :goto_1
    invoke-virtual {v0, v2}, Ljava/io/InputStream;->read([B)I

    move-result v3

    if-ltz v3, :cond_1

    .line 740
    invoke-virtual {p1, v2, p2, v3}, Ljava/io/ByteArrayOutputStream;->write([BII)V

    goto :goto_1

    .line 742
    :cond_1
    new-instance p2, Ljava/lang/String;

    invoke-virtual {p1}, Ljava/io/ByteArrayOutputStream;->toByteArray()[B

    move-result-object p1

    sget-object v2, Ljava/nio/charset/StandardCharsets;->UTF_8:Ljava/nio/charset/Charset;

    invoke-direct {p2, p1, v2}, Ljava/lang/String;-><init>([BLjava/nio/charset/Charset;)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 744
    invoke-static {v1}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/OutputStream;)V

    .line 745
    invoke-static {v0}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/InputStream;)V

    .line 746
    invoke-virtual {p0}, Ljava/net/HttpURLConnection;->disconnect()V

    .line 742
    return-object p2

    .line 733
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

    .line 744
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

    .line 745
    invoke-static {p2}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/InputStream;)V

    .line 746
    invoke-virtual {p0}, Ljava/net/HttpURLConnection;->disconnect()V

    .line 747
    throw p1
.end method

.method private static promotePart(Ljava/io/File;Ljava/io/File;)V
    .locals 3
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 880
    invoke-virtual {p1}, Ljava/io/File;->exists()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-virtual {p1}, Ljava/io/File;->delete()Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_0

    .line 881
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

    .line 883
    :cond_1
    :goto_0
    invoke-virtual {p0, p1}, Ljava/io/File;->renameTo(Ljava/io/File;)Z

    move-result v0

    if-eqz v0, :cond_2

    .line 886
    return-void

    .line 884
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

.method private static readSidecarBytes(Ljava/io/File;)J
    .locals 7

    .line 823
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

    .line 824
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

    .line 828
    :cond_0
    :try_start_0
    invoke-static {v0}, Lio/kamihama/magianative/CNDownloaderFix;->readSmallUtf8(Ljava/io/File;)Ljava/lang/String;

    move-result-object p0

    const-string v3, "\\n"

    invoke-virtual {p0, v3}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p0

    .line 829
    array-length v3, p0

    const/4 v4, 0x0

    :goto_0
    if-ge v4, v3, :cond_2

    aget-object v5, p0, v4

    .line 830
    const-string v6, "bytes="

    invoke-virtual {v5, v6}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v6

    if-eqz v6, :cond_1

    .line 831
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

    .line 829
    :cond_1
    add-int/lit8 v4, v4, 0x1

    goto :goto_0

    .line 836
    :cond_2
    goto :goto_1

    .line 834
    :catch_0
    move-exception p0

    .line 835
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

    .line 837
    :goto_1
    return-wide v1

    .line 825
    :cond_3
    :goto_2
    return-wide v1
.end method

.method private static readSidecarEtag(Ljava/io/File;)Ljava/lang/String;
    .locals 6

    .line 841
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

    .line 842
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

    .line 846
    :cond_0
    :try_start_0
    invoke-static {v0}, Lio/kamihama/magianative/CNDownloaderFix;->readSmallUtf8(Ljava/io/File;)Ljava/lang/String;

    move-result-object p0

    const-string v2, "\\n"

    invoke-virtual {p0, v2}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p0

    .line 847
    array-length v2, p0

    const/4 v3, 0x0

    :goto_0
    if-ge v3, v2, :cond_2

    aget-object v4, p0, v3

    .line 848
    const-string v5, "etag="

    invoke-virtual {v4, v5}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v5

    if-eqz v5, :cond_1

    .line 849
    const/4 p0, 0x5

    invoke-virtual {v4, p0}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    return-object p0

    .line 847
    :cond_1
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    .line 854
    :cond_2
    goto :goto_1

    .line 852
    :catch_0
    move-exception p0

    .line 853
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

    .line 855
    :goto_1
    return-object v1

    .line 843
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

    .line 859
    new-instance v0, Ljava/io/ByteArrayOutputStream;

    invoke-direct {v0}, Ljava/io/ByteArrayOutputStream;-><init>()V

    .line 860
    nop

    .line 862
    const/4 v1, 0x0

    :try_start_0
    new-instance v2, Ljava/io/FileInputStream;

    invoke-direct {v2, p0}, Ljava/io/FileInputStream;-><init>(Ljava/io/File;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    .line 863
    const/16 v1, 0x1000

    :try_start_1
    new-array v1, v1, [B

    .line 864
    const/4 v3, 0x0

    const/4 v4, 0x0

    .line 866
    :goto_0
    invoke-virtual {v2, v1}, Ljava/io/FileInputStream;->read([B)I

    move-result v5

    if-ltz v5, :cond_1

    .line 867
    add-int/2addr v4, v5

    .line 868
    const/16 v6, 0x4000

    if-gt v4, v6, :cond_0

    .line 871
    invoke-virtual {v0, v1, v3, v5}, Ljava/io/ByteArrayOutputStream;->write([BII)V

    goto :goto_0

    .line 869
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

    .line 873
    :cond_1
    new-instance p0, Ljava/lang/String;

    invoke-virtual {v0}, Ljava/io/ByteArrayOutputStream;->toByteArray()[B

    move-result-object v0

    sget-object v1, Ljava/nio/charset/StandardCharsets;->UTF_8:Ljava/nio/charset/Charset;

    invoke-direct {p0, v0, v1}, Ljava/lang/String;-><init>([BLjava/nio/charset/Charset;)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 875
    invoke-static {v2}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/InputStream;)V

    .line 873
    return-object p0

    .line 875
    :catchall_0
    move-exception p0

    move-object v1, v2

    goto :goto_1

    :catchall_1
    move-exception p0

    :goto_1
    invoke-static {v1}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/InputStream;)V

    .line 876
    throw p0
.end method

.method private static resetProgress(I)V
    .locals 1

    .line 984
    const/4 v0, 0x0

    invoke-static {p0, v0}, Lio/kamihama/magianative/CNCNDownloadUI;->setDownloadSpeed(IF)V

    .line 985
    invoke-static {p0, v0}, Lio/kamihama/magianative/CNCNDownloadUI;->setFileDownloaded(IF)V

    .line 986
    const/4 v0, 0x0

    invoke-static {p0, v0}, Lio/kamihama/magianative/CNCNDownloadUI;->updateFileProgress(II)V

    .line 987
    return-void
.end method

.method private static resetUiForRun()V
    .locals 7

    .line 950
    const/4 v0, 0x0

    const/4 v1, 0x0

    :goto_0
    const/16 v2, 0xf

    if-ge v1, v2, :cond_3

    .line 951
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

    .line 953
    sget-object v2, Lio/kamihama/magianative/CNCNDownloadUI;->fileStatus:[I

    if-eqz v2, :cond_0

    .line 954
    sget-object v2, Lio/kamihama/magianative/CNCNDownloadUI;->fileStatus:[I

    aput v0, v2, v1

    .line 956
    :cond_0
    sget-object v2, Lio/kamihama/magianative/CNCNDownloadUI;->fileProgress:[I

    if-eqz v2, :cond_1

    .line 957
    sget-object v2, Lio/kamihama/magianative/CNCNDownloadUI;->fileProgress:[I

    aput v0, v2, v1

    .line 959
    :cond_1
    const/4 v2, 0x0

    invoke-static {v1, v2}, Lio/kamihama/magianative/CNCNDownloadUI;->setDownloadSpeed(IF)V

    .line 960
    invoke-static {v1, v2}, Lio/kamihama/magianative/CNCNDownloadUI;->setFileDownloaded(IF)V

    goto :goto_1

    .line 962
    :cond_2
    invoke-static {v1}, Lio/kamihama/magianative/CNDownloaderFix;->markDone(I)V

    .line 950
    :goto_1
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    .line 965
    :cond_3
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->throttledUpdate()V

    .line 966
    return-void
.end method

.method public static runInstaller()V
    .locals 3

    .line 179
    :try_start_0
    invoke-static {}, Lio/kamihama/magianative/CNDownloaderFix;->runInstallerInner()V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 187
    goto :goto_1

    .line 180
    :catchall_0
    move-exception v0

    .line 184
    :try_start_1
    const-string v1, "MagiaCNDownloader"

    const-string v2, "\u5b89\u88c5\u5668\u53d1\u751f\u672a\u9884\u671f\u9519\u8bef\uff0c\u5df2\u62e6\u622a\u4ee5\u907f\u514d\u56de\u9000\u5230\u539f\u751f\u4e0b\u8f7d\u754c\u9762"

    invoke-static {v1, v2, v0}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 185
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

    .line 186
    :catchall_1
    move-exception v0

    :goto_0
    nop

    .line 188
    :goto_1
    return-void
.end method

.method private static runInstallerInner()V
    .locals 12

    .line 191
    const-string v0, "installer=v2 max_downloads=4"

    const-string v1, "MagiaCNDownloader"

    invoke-static {v1, v0}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 193
    :try_start_0
    invoke-static {}, Lio/kamihama/magianative/RestClient;->getCurrentActivity()Landroid/app/Activity;

    move-result-object v0

    .line 194
    if-eqz v0, :cond_0

    .line 195
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->show(Landroid/app/Activity;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 199
    :cond_0
    goto :goto_0

    .line 197
    :catchall_0
    move-exception v0

    .line 198
    const-string v2, "Unable to show installer UI"

    invoke-static {v1, v2, v0}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 201
    :goto_0
    new-instance v0, Ljava/io/File;

    const-string v2, "/data/data/io.kamihama.totentanz/files/madomagi/magica/cn_base_done.flag"

    invoke-direct {v0, v2}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 202
    invoke-virtual {v0}, Ljava/io/File;->isFile()Z

    move-result v2

    if-eqz v2, :cond_1

    .line 203
    const-string v0, "Final flag already exists; installer skipped"

    invoke-static {v1, v0}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 204
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->hide()V

    .line 205
    return-void

    .line 208
    :cond_1
    new-instance v2, Ljava/io/File;

    const-string v3, "/data/data/io.kamihama.totentanz/files/madomagi/magica/.cn_installer/r128-downloader-v1"

    invoke-direct {v2, v3}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 209
    invoke-virtual {v2}, Ljava/io/File;->isDirectory()Z

    move-result v3

    const/4 v4, 0x0

    if-nez v3, :cond_2

    invoke-virtual {v2}, Ljava/io/File;->mkdirs()Z

    move-result v3

    if-nez v3, :cond_2

    invoke-virtual {v2}, Ljava/io/File;->isDirectory()Z

    move-result v2

    if-nez v2, :cond_2

    .line 210
    const-string v0, "Cannot create installer state directory"

    invoke-static {v0, v4}, Lio/kamihama/magianative/CNDownloaderFix;->failInstaller(Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 211
    return-void

    .line 215
    :cond_2
    const-string v2, "\u51c6\u5907\u4e2d"

    const-string v3, "\u6b63\u5728\u83b7\u53d6\u4e0b\u8f7d\u7ebf\u8def\u2026"

    const/4 v5, 0x0

    invoke-static {v2, v3, v5}, Lio/kamihama/magianative/CNCNDownloadUI;->updateSimple(Ljava/lang/String;Ljava/lang/String;I)V

    .line 216
    invoke-static {v5}, Lio/kamihama/magianative/CNMirrors;->refresh(Z)V

    .line 217
    invoke-static {}, Lio/kamihama/magianative/CNMirrors;->isLoaded()Z

    move-result v2

    const/4 v3, 0x1

    if-nez v2, :cond_3

    .line 218
    invoke-static {v3}, Lio/kamihama/magianative/CNMirrors;->refresh(Z)V

    .line 220
    :cond_3
    invoke-static {}, Lio/kamihama/magianative/CNMirrors;->healthy()Ljava/util/List;

    move-result-object v2

    invoke-interface {v2}, Ljava/util/List;->size()I

    move-result v2

    .line 221
    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "mirrors ready count="

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

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

    invoke-static {v1, v6}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 222
    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "\u53ef\u7528\u7ebf\u8def "

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v6, " \u6761\uff0c\u5355\u6587\u4ef6\u5206\u7247 "

    invoke-virtual {v2, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    .line 223
    invoke-static {}, Lio/kamihama/magianative/CNMirrors;->chunks()I

    move-result v6

    invoke-virtual {v2, v6}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v6, " \u7ebf\u7a0b"

    invoke-virtual {v2, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    .line 222
    const-string v6, "\u5f00\u59cb\u4e0b\u8f7d"

    invoke-static {v6, v2, v5}, Lio/kamihama/magianative/CNCNDownloadUI;->updateSimple(Ljava/lang/String;Ljava/lang/String;I)V

    .line 225
    invoke-static {}, Lio/kamihama/magianative/CNDownloaderFix;->resetUiForRun()V

    .line 226
    invoke-static {}, Lio/kamihama/magianative/CNDownloaderFix;->startSpeedWatchdog()Ljava/util/concurrent/ScheduledExecutorService;

    move-result-object v2

    .line 227
    const/4 v6, 0x4

    invoke-static {v6}, Ljava/util/concurrent/Executors;->newFixedThreadPool(I)Ljava/util/concurrent/ExecutorService;

    move-result-object v6

    .line 228
    new-instance v7, Ljava/util/ArrayList;

    const/16 v8, 0xf

    invoke-direct {v7, v8}, Ljava/util/ArrayList;-><init>(I)V

    .line 229
    const/4 v9, 0x0

    :goto_1
    if-ge v9, v8, :cond_4

    .line 230
    new-instance v10, Lio/kamihama/magianative/CNDownloaderFix$ArchiveTask;

    invoke-direct {v10, v9}, Lio/kamihama/magianative/CNDownloaderFix$ArchiveTask;-><init>(I)V

    invoke-interface {v6, v10}, Ljava/util/concurrent/ExecutorService;->submit(Ljava/util/concurrent/Callable;)Ljava/util/concurrent/Future;

    move-result-object v10

    invoke-interface {v7, v10}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 229
    add-int/lit8 v9, v9, 0x1

    goto :goto_1

    .line 232
    :cond_4
    invoke-interface {v6}, Ljava/util/concurrent/ExecutorService;->shutdown()V

    .line 234
    nop

    .line 235
    const/4 v8, 0x0

    :goto_2
    invoke-interface {v7}, Ljava/util/List;->size()I

    move-result v9

    if-ge v8, v9, :cond_6

    .line 237
    :try_start_1
    invoke-interface {v7, v8}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v9

    check-cast v9, Ljava/util/concurrent/Future;

    invoke-interface {v9}, Ljava/util/concurrent/Future;->get()Ljava/lang/Object;

    move-result-object v9

    check-cast v9, Ljava/lang/Boolean;

    invoke-virtual {v9}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v9
    :try_end_1
    .catch Ljava/lang/InterruptedException; {:try_start_1 .. :try_end_1} :catch_1
    .catch Ljava/util/concurrent/ExecutionException; {:try_start_1 .. :try_end_1} :catch_0

    if-nez v9, :cond_5

    .line 238
    const/4 v3, 0x0

    goto :goto_3

    .line 243
    :catch_0
    move-exception v3

    .line 244
    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "Installer worker crashed for "

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    sget-object v10, Lio/kamihama/magianative/CNDownloaderFix;->FILE_NAMES:[Ljava/lang/String;

    aget-object v10, v10, v8

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v1, v9, v3}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 245
    const/4 v3, 0x0

    goto :goto_4

    .line 240
    :catch_1
    move-exception v9

    .line 241
    invoke-static {}, Ljava/lang/Thread;->currentThread()Ljava/lang/Thread;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/Thread;->interrupt()V

    .line 242
    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "Installer interrupted while waiting for "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    sget-object v11, Lio/kamihama/magianative/CNDownloaderFix;->FILE_NAMES:[Ljava/lang/String;

    aget-object v11, v11, v8

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v1, v10, v9}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 246
    :cond_5
    :goto_3
    nop

    .line 235
    :goto_4
    add-int/lit8 v8, v8, 0x1

    goto :goto_2

    .line 248
    :cond_6
    invoke-interface {v6}, Ljava/util/concurrent/ExecutorService;->shutdownNow()Ljava/util/List;

    .line 249
    invoke-interface {v2}, Ljava/util/concurrent/ScheduledExecutorService;->shutdownNow()Ljava/util/List;

    .line 250
    invoke-static {}, Lio/kamihama/magianative/CNDownloaderFix;->zeroAllSpeeds()V

    .line 252
    if-eqz v3, :cond_9

    invoke-static {}, Lio/kamihama/magianative/CNDownloaderFix;->allMarkersValid()Z

    move-result v2

    if-nez v2, :cond_7

    goto :goto_7

    .line 258
    :cond_7
    :try_start_2
    const-string v2, "schema=2\narchives=15\n"

    invoke-static {v0, v2}, Lio/kamihama/magianative/CNDownloaderFix;->writeAtomic(Ljava/io/File;Ljava/lang/String;)V

    .line 259
    const-string v0, "\u5b89\u88c5\u5b8c\u6210"

    const-string v2, "\u6240\u6709\u8d44\u6e90\u5df2\u9a8c\u8bc1\u5e76\u63d0\u4ea4\u5b8c\u6210\u6807\u8bb0"

    const/16 v3, 0x64

    invoke-static {v0, v2, v3}, Lio/kamihama/magianative/CNCNDownloadUI;->updateSimple(Ljava/lang/String;Ljava/lang/String;I)V

    .line 260
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->hide()V

    .line 261
    const-string v0, "All archives installed; final flag committed atomically"

    invoke-static {v1, v0}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 262
    new-instance v0, Ljava/io/File;

    const-string v2, "/data/data/io.kamihama.totentanz/files/madomagi/magica/.cn_installer/r128-downloader-v1/no_restart"

    invoke-direct {v0, v2}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {v0}, Ljava/io/File;->isFile()Z

    move-result v0

    if-eqz v0, :cond_8

    .line 263
    const-string v0, "Test no-restart marker present; restart suppressed"

    invoke-static {v1, v0}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_2
    .catch Ljava/io/IOException; {:try_start_2 .. :try_end_2} :catch_3

    .line 264
    return-void

    .line 267
    :cond_8
    const-wide/16 v0, 0x7d0

    :try_start_3
    invoke-static {v0, v1}, Ljava/lang/Thread;->sleep(J)V

    .line 268
    invoke-static {}, Lio/kamihama/magianative/RestClient;->restartApp()V
    :try_end_3
    .catch Ljava/lang/InterruptedException; {:try_start_3 .. :try_end_3} :catch_2
    .catch Ljava/io/IOException; {:try_start_3 .. :try_end_3} :catch_3

    .line 271
    goto :goto_5

    .line 269
    :catch_2
    move-exception v0

    .line 270
    :try_start_4
    invoke-static {}, Ljava/lang/Thread;->currentThread()Ljava/lang/Thread;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Thread;->interrupt()V
    :try_end_4
    .catch Ljava/io/IOException; {:try_start_4 .. :try_end_4} :catch_3

    .line 274
    :goto_5
    goto :goto_6

    .line 272
    :catch_3
    move-exception v0

    .line 273
    const-string v1, "Final flag commit failed"

    invoke-static {v1, v0}, Lio/kamihama/magianative/CNDownloaderFix;->failInstaller(Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 275
    :goto_6
    return-void

    .line 253
    :cond_9
    :goto_7
    const-string v0, "One or more archives failed; restart to resume"

    invoke-static {v0, v4}, Lio/kamihama/magianative/CNDownloaderFix;->failInstaller(Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 254
    return-void
.end method

.method private static sanitizeLine(Ljava/lang/String;)Ljava/lang/String;
    .locals 2

    .line 1037
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

    .line 1005
    sget-object v0, Lio/kamihama/magianative/CNDownloaderFix;->ACTIVE:Ljava/util/concurrent/atomic/AtomicIntegerArray;

    invoke-virtual {v0, p0, p1}, Ljava/util/concurrent/atomic/AtomicIntegerArray;->set(II)V

    .line 1006
    sget-object v0, Lio/kamihama/magianative/CNDownloaderFix;->LAST_PROGRESS_NS:Ljava/util/concurrent/atomic/AtomicLongArray;

    if-eqz p1, :cond_0

    invoke-static {}, Ljava/lang/System;->nanoTime()J

    move-result-wide v1

    goto :goto_0

    :cond_0
    const-wide/16 v1, 0x0

    :goto_0
    invoke-virtual {v0, p0, v1, v2}, Ljava/util/concurrent/atomic/AtomicLongArray;->set(IJ)V

    .line 1007
    return-void
.end method

.method private static startSpeedWatchdog()Ljava/util/concurrent/ScheduledExecutorService;
    .locals 8

    .line 679
    invoke-static {}, Ljava/util/concurrent/Executors;->newSingleThreadScheduledExecutor()Ljava/util/concurrent/ScheduledExecutorService;

    move-result-object v7

    .line 680
    new-instance v1, Lio/kamihama/magianative/CNDownloaderFix$SpeedWatchdog;

    const/4 v0, 0x0

    invoke-direct {v1, v0}, Lio/kamihama/magianative/CNDownloaderFix$SpeedWatchdog;-><init>(Lio/kamihama/magianative/CNDownloaderFix$1;)V

    const-wide/16 v2, 0x1

    const-wide/16 v4, 0x1

    sget-object v6, Ljava/util/concurrent/TimeUnit;->SECONDS:Ljava/util/concurrent/TimeUnit;

    move-object v0, v7

    invoke-interface/range {v0 .. v6}, Ljava/util/concurrent/ScheduledExecutorService;->scheduleAtFixedRate(Ljava/lang/Runnable;JJLjava/util/concurrent/TimeUnit;)Ljava/util/concurrent/ScheduledFuture;

    .line 681
    return-object v7
.end method

.method private static truncate(Ljava/io/File;)V
    .locals 3
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 889
    nop

    .line 891
    const/4 v0, 0x0

    :try_start_0
    new-instance v1, Ljava/io/FileOutputStream;

    const/4 v2, 0x0

    invoke-direct {v1, p0, v2}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;Z)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    .line 892
    :try_start_1
    invoke-virtual {v1}, Ljava/io/FileOutputStream;->flush()V

    .line 893
    invoke-virtual {v1}, Ljava/io/FileOutputStream;->getFD()Ljava/io/FileDescriptor;

    move-result-object p0

    invoke-virtual {p0}, Ljava/io/FileDescriptor;->sync()V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 895
    invoke-static {v1}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/OutputStream;)V

    .line 896
    nop

    .line 897
    return-void

    .line 895
    :catchall_0
    move-exception p0

    move-object v0, v1

    goto :goto_0

    :catchall_1
    move-exception p0

    :goto_0
    invoke-static {v0}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/OutputStream;)V

    .line 896
    throw p0
.end method

.method private static updateProgress(IJJ)V
    .locals 6

    .line 974
    const-wide/16 v0, 0x0

    cmp-long v2, p3, v0

    if-lez v2, :cond_0

    .line 975
    const-wide/16 v2, 0x64

    mul-long v4, p1, v2

    div-long/2addr v4, p3

    invoke-static {v0, v1, v4, v5}, Ljava/lang/Math;->max(JJ)J

    move-result-wide p3

    invoke-static {v2, v3, p3, p4}, Ljava/lang/Math;->min(JJ)J

    move-result-wide p3

    long-to-int p4, p3

    goto :goto_0

    .line 977
    :cond_0
    const/4 p4, 0x0

    .line 979
    :goto_0
    long-to-double p1, p1

    const-wide v0, 0x412e848000000000L    # 1000000.0

    div-double/2addr p1, v0

    double-to-float p1, p1

    invoke-static {p0, p1}, Lio/kamihama/magianative/CNCNDownloadUI;->setFileDownloaded(IF)V

    .line 980
    invoke-static {p0, p4}, Lio/kamihama/magianative/CNCNDownloadUI;->updateFileProgress(II)V

    .line 981
    return-void
.end method

.method private static updateSize(IJ)V
    .locals 2

    .line 969
    long-to-double p1, p1

    const-wide v0, 0x412e848000000000L    # 1000000.0

    div-double/2addr p1, v0

    double-to-float p1, p1

    invoke-static {p0, p1}, Lio/kamihama/magianative/CNCNDownloadUI;->setFileSize(IF)V

    .line 970
    return-void
.end method

.method private static writeAtomic(Ljava/io/File;Ljava/lang/String;)V
    .locals 4
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 790
    invoke-virtual {p0}, Ljava/io/File;->getParentFile()Ljava/io/File;

    move-result-object v0

    .line 791
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

    .line 792
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

    .line 794
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

    .line 795
    nop

    .line 797
    const/4 v1, 0x0

    :try_start_0
    new-instance v2, Ljava/io/FileOutputStream;

    const/4 v3, 0x0

    invoke-direct {v2, v0, v3}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;Z)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    .line 798
    :try_start_1
    sget-object v3, Ljava/nio/charset/StandardCharsets;->UTF_8:Ljava/nio/charset/Charset;

    invoke-virtual {p1, v3}, Ljava/lang/String;->getBytes(Ljava/nio/charset/Charset;)[B

    move-result-object p1

    invoke-virtual {v2, p1}, Ljava/io/FileOutputStream;->write([B)V

    .line 799
    invoke-virtual {v2}, Ljava/io/FileOutputStream;->flush()V

    .line 800
    invoke-virtual {v2}, Ljava/io/FileOutputStream;->getFD()Ljava/io/FileDescriptor;

    move-result-object p1

    invoke-virtual {p1}, Ljava/io/FileDescriptor;->sync()V

    .line 801
    invoke-static {v2}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/OutputStream;)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 802
    nop

    .line 803
    :try_start_2
    invoke-virtual {p0}, Ljava/io/File;->exists()Z

    move-result p1

    if-eqz p1, :cond_3

    invoke-virtual {p0}, Ljava/io/File;->delete()Z

    move-result p1

    if-eqz p1, :cond_2

    goto :goto_1

    .line 804
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

    .line 806
    :cond_3
    :goto_1
    invoke-virtual {v0, p0}, Ljava/io/File;->renameTo(Ljava/io/File;)Z

    move-result p1
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_1

    if-eqz p1, :cond_4

    .line 810
    invoke-static {v1}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/OutputStream;)V

    .line 811
    nop

    .line 812
    return-void

    .line 807
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

    .line 810
    :catchall_0
    move-exception p0

    move-object v1, v2

    goto :goto_2

    :catchall_1
    move-exception p0

    :goto_2
    invoke-static {v1}, Lio/kamihama/magianative/CNDownloaderFix;->closeQuietly(Ljava/io/OutputStream;)V

    .line 811
    throw p0
.end method

.method private static writeMarker(Ljava/io/File;Ljava/lang/String;Ljava/lang/String;Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;)V
    .locals 2
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 752
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

    .line 754
    invoke-static {p2}, Lio/kamihama/magianative/CNDownloaderFix;->sanitizeLine(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p2

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string p2, "\n"

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    .line 752
    invoke-static {p0, p1}, Lio/kamihama/magianative/CNDownloaderFix;->writeAtomic(Ljava/io/File;Ljava/lang/String;)V

    .line 755
    return-void
.end method

.method private static writeSidecar(Ljava/io/File;Ljava/lang/String;J)V
    .locals 2
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 815
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

    .line 816
    return-void
.end method

.method private static zeroAllSpeeds()V
    .locals 5

    .line 1010
    const/4 v0, 0x0

    const/4 v1, 0x0

    :goto_0
    const/16 v2, 0xf

    if-ge v1, v2, :cond_0

    .line 1011
    sget-object v2, Lio/kamihama/magianative/CNDownloaderFix;->ACTIVE:Ljava/util/concurrent/atomic/AtomicIntegerArray;

    invoke-virtual {v2, v1, v0}, Ljava/util/concurrent/atomic/AtomicIntegerArray;->set(II)V

    .line 1012
    sget-object v2, Lio/kamihama/magianative/CNDownloaderFix;->LAST_PROGRESS_NS:Ljava/util/concurrent/atomic/AtomicLongArray;

    const-wide/16 v3, 0x0

    invoke-virtual {v2, v1, v3, v4}, Ljava/util/concurrent/atomic/AtomicLongArray;->set(IJ)V

    .line 1013
    const/4 v2, 0x0

    invoke-static {v1, v2}, Lio/kamihama/magianative/CNCNDownloadUI;->setDownloadSpeed(IF)V

    .line 1010
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    .line 1015
    :cond_0
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->throttledUpdate()V

    .line 1016
    return-void
.end method
