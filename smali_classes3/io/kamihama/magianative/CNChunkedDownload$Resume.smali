.class final Lio/kamihama/magianative/CNChunkedDownload$Resume;
.super Ljava/lang/Object;
.source "CNChunkedDownload.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lio/kamihama/magianative/CNChunkedDownload;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1a
    name = "Resume"
.end annotation


# instance fields
.field chunks:I

.field done:[J

.field etag:Ljava/lang/String;

.field total:J


# direct methods
.method private constructor <init>()V
    .locals 1

    .line 107
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 110
    const-string v0, ""

    iput-object v0, p0, Lio/kamihama/magianative/CNChunkedDownload$Resume;->etag:Ljava/lang/String;

    return-void
.end method

.method synthetic constructor <init>(Lio/kamihama/magianative/CNChunkedDownload$1;)V
    .locals 0

    .line 107
    invoke-direct {p0}, Lio/kamihama/magianative/CNChunkedDownload$Resume;-><init>()V

    return-void
.end method
