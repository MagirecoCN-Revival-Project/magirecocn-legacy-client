.class public final Lio/kamihama/magianative/CNChunkedDownload$Probe;
.super Ljava/lang/Object;
.source "CNChunkedDownload.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lio/kamihama/magianative/CNChunkedDownload;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x19
    name = "Probe"
.end annotation


# instance fields
.field public final etag:Ljava/lang/String;

.field public final rangeSupported:Z

.field public final total:J


# direct methods
.method constructor <init>(JLjava/lang/String;Z)V
    .locals 0

    .line 91
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 92
    iput-wide p1, p0, Lio/kamihama/magianative/CNChunkedDownload$Probe;->total:J

    .line 93
    if-nez p3, :cond_0

    const-string p1, ""

    goto :goto_0

    :cond_0
    invoke-virtual {p3}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p1

    :goto_0
    iput-object p1, p0, Lio/kamihama/magianative/CNChunkedDownload$Probe;->etag:Ljava/lang/String;

    .line 94
    iput-boolean p4, p0, Lio/kamihama/magianative/CNChunkedDownload$Probe;->rangeSupported:Z

    .line 95
    return-void
.end method
