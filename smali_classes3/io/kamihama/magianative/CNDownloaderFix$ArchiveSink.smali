.class final Lio/kamihama/magianative/CNDownloaderFix$ArchiveSink;
.super Ljava/lang/Object;
.source "CNDownloaderFix.java"

# interfaces
.implements Lio/kamihama/magianative/CNChunkedDownload$Sink;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lio/kamihama/magianative/CNDownloaderFix;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1a
    name = "ArchiveSink"
.end annotation


# instance fields
.field private final index:I


# direct methods
.method constructor <init>(I)V
    .locals 0

    .line 577
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput p1, p0, Lio/kamihama/magianative/CNDownloaderFix$ArchiveSink;->index:I

    return-void
.end method


# virtual methods
.method public isCancelled()Z
    .locals 1

    .line 590
    invoke-static {}, Ljava/lang/Thread;->currentThread()Ljava/lang/Thread;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Thread;->isInterrupted()Z

    move-result v0

    return v0
.end method

.method public onProgress(JJ)V
    .locals 4

    .line 583
    invoke-static {}, Lio/kamihama/magianative/CNDownloaderFix;->access$500()Ljava/util/concurrent/atomic/AtomicLongArray;

    move-result-object v0

    iget v1, p0, Lio/kamihama/magianative/CNDownloaderFix$ArchiveSink;->index:I

    invoke-static {}, Ljava/lang/System;->nanoTime()J

    move-result-wide v2

    invoke-virtual {v0, v1, v2, v3}, Ljava/util/concurrent/atomic/AtomicLongArray;->set(IJ)V

    .line 584
    iget v0, p0, Lio/kamihama/magianative/CNDownloaderFix$ArchiveSink;->index:I

    invoke-static {v0, p1, p2, p3, p4}, Lio/kamihama/magianative/CNDownloaderFix;->access$600(IJJ)V

    .line 585
    return-void
.end method

.method public onSpeed(F)V
    .locals 1

    .line 587
    iget v0, p0, Lio/kamihama/magianative/CNDownloaderFix$ArchiveSink;->index:I

    invoke-static {v0, p1}, Lio/kamihama/magianative/CNCNDownloadUI;->setDownloadSpeed(IF)V

    .line 588
    return-void
.end method

.method public onTotal(J)V
    .locals 1

    .line 580
    iget v0, p0, Lio/kamihama/magianative/CNDownloaderFix$ArchiveSink;->index:I

    invoke-static {v0, p1, p2}, Lio/kamihama/magianative/CNDownloaderFix;->access$300(IJ)V

    .line 581
    return-void
.end method
