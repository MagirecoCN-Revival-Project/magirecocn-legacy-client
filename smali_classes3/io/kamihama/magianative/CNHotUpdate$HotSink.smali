.class final Lio/kamihama/magianative/CNHotUpdate$HotSink;
.super Ljava/lang/Object;
.source "CNHotUpdate.java"

# interfaces
.implements Lio/kamihama/magianative/CNChunkedDownload$Sink;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lio/kamihama/magianative/CNHotUpdate;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1a
    name = "HotSink"
.end annotation


# instance fields
.field private final index:I


# direct methods
.method constructor <init>(I)V
    .locals 0

    .line 161
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput p1, p0, Lio/kamihama/magianative/CNHotUpdate$HotSink;->index:I

    return-void
.end method


# virtual methods
.method public isCancelled()Z
    .locals 1

    .line 175
    invoke-static {}, Ljava/lang/Thread;->currentThread()Ljava/lang/Thread;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Thread;->isInterrupted()Z

    move-result v0

    return v0
.end method

.method public onProgress(JJ)V
    .locals 5

    .line 166
    iget v0, p0, Lio/kamihama/magianative/CNHotUpdate$HotSink;->index:I

    long-to-double v1, p1

    const-wide v3, 0x412e848000000000L    # 1000000.0

    div-double/2addr v1, v3

    double-to-float v1, v1

    invoke-static {v0, v1}, Lio/kamihama/magianative/CNCNDownloadUI;->setFileDownloaded(IF)V

    .line 167
    const-wide/16 v0, 0x0

    cmp-long v2, p3, v0

    if-lez v2, :cond_0

    .line 168
    const-wide/16 v2, 0x64

    mul-long p1, p1, v2

    div-long/2addr p1, p3

    invoke-static {v0, v1, p1, p2}, Ljava/lang/Math;->max(JJ)J

    move-result-wide p1

    invoke-static {v2, v3, p1, p2}, Ljava/lang/Math;->min(JJ)J

    move-result-wide p1

    long-to-int p2, p1

    goto :goto_0

    :cond_0
    const/4 p2, 0x0

    .line 169
    :goto_0
    iget p1, p0, Lio/kamihama/magianative/CNHotUpdate$HotSink;->index:I

    invoke-static {p1, p2}, Lio/kamihama/magianative/CNCNDownloadUI;->updateFileProgress(II)V

    .line 170
    return-void
.end method

.method public onSpeed(F)V
    .locals 1

    .line 172
    iget v0, p0, Lio/kamihama/magianative/CNHotUpdate$HotSink;->index:I

    invoke-static {v0, p1}, Lio/kamihama/magianative/CNCNDownloadUI;->setDownloadSpeed(IF)V

    .line 173
    return-void
.end method

.method public onTotal(J)V
    .locals 3

    .line 163
    iget v0, p0, Lio/kamihama/magianative/CNHotUpdate$HotSink;->index:I

    long-to-double p1, p1

    const-wide v1, 0x412e848000000000L    # 1000000.0

    div-double/2addr p1, v1

    double-to-float p1, p1

    invoke-static {v0, p1}, Lio/kamihama/magianative/CNCNDownloadUI;->setFileSize(IF)V

    .line 164
    return-void
.end method
