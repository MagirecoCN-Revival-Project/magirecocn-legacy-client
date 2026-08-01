.class final Lio/kamihama/magianative/CNDownloaderFix$ContentRange;
.super Ljava/lang/Object;
.source "CNDownloaderFix.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lio/kamihama/magianative/CNDownloaderFix;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x18
    name = "ContentRange"
.end annotation


# instance fields
.field final end:J

.field final start:J

.field final total:J


# direct methods
.method constructor <init>(JJJ)V
    .locals 0

    .line 1099
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 1100
    iput-wide p1, p0, Lio/kamihama/magianative/CNDownloaderFix$ContentRange;->start:J

    .line 1101
    iput-wide p3, p0, Lio/kamihama/magianative/CNDownloaderFix$ContentRange;->end:J

    .line 1102
    iput-wide p5, p0, Lio/kamihama/magianative/CNDownloaderFix$ContentRange;->total:J

    .line 1103
    return-void
.end method
