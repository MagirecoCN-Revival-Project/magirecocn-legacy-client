.class final Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;
.super Ljava/lang/Object;
.source "CNDownloaderFix.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lio/kamihama/magianative/CNDownloaderFix;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x18
    name = "DownloadMetadata"
.end annotation


# instance fields
.field final etag:Ljava/lang/String;

.field final totalBytes:J


# direct methods
.method constructor <init>(JLjava/lang/String;)V
    .locals 0

    .line 1311
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 1312
    iput-wide p1, p0, Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;->totalBytes:J

    .line 1313
    if-nez p3, :cond_0

    const-string p3, ""

    :cond_0
    iput-object p3, p0, Lio/kamihama/magianative/CNDownloaderFix$DownloadMetadata;->etag:Ljava/lang/String;

    .line 1314
    return-void
.end method
