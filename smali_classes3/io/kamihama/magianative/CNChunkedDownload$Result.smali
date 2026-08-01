.class public final Lio/kamihama/magianative/CNChunkedDownload$Result;
.super Ljava/lang/Object;
.source "CNChunkedDownload.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lio/kamihama/magianative/CNChunkedDownload;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x19
    name = "Result"
.end annotation


# instance fields
.field public final etag:Ljava/lang/String;

.field public final totalBytes:J


# direct methods
.method constructor <init>(JLjava/lang/String;)V
    .locals 0

    .line 102
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 103
    iput-wide p1, p0, Lio/kamihama/magianative/CNChunkedDownload$Result;->totalBytes:J

    .line 104
    if-nez p3, :cond_0

    const-string p3, ""

    :cond_0
    iput-object p3, p0, Lio/kamihama/magianative/CNChunkedDownload$Result;->etag:Ljava/lang/String;

    .line 105
    return-void
.end method
