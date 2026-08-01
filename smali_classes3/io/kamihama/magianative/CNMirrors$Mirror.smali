.class public final Lio/kamihama/magianative/CNMirrors$Mirror;
.super Ljava/lang/Object;
.source "CNMirrors.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lio/kamihama/magianative/CNMirrors;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x19
    name = "Mirror"
.end annotation


# instance fields
.field public final base:Ljava/lang/String;

.field volatile baselineBps:J

.field public final chunks:I

.field volatile cooldownUntilNs:J

.field volatile demoteUntilNs:J

.field public final enabled:Z

.field final failures:Ljava/util/concurrent/atomic/AtomicInteger;

.field public final name:Ljava/lang/String;

.field public final weight:I


# direct methods
.method constructor <init>(Ljava/lang/String;Ljava/lang/String;IIZ)V
    .locals 4

    .line 103
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 95
    const-wide/16 v0, 0x0

    iput-wide v0, p0, Lio/kamihama/magianative/CNMirrors$Mirror;->baselineBps:J

    .line 97
    iput-wide v0, p0, Lio/kamihama/magianative/CNMirrors$Mirror;->demoteUntilNs:J

    .line 99
    new-instance v2, Ljava/util/concurrent/atomic/AtomicInteger;

    const/4 v3, 0x0

    invoke-direct {v2, v3}, Ljava/util/concurrent/atomic/AtomicInteger;-><init>(I)V

    iput-object v2, p0, Lio/kamihama/magianative/CNMirrors$Mirror;->failures:Ljava/util/concurrent/atomic/AtomicInteger;

    .line 101
    iput-wide v0, p0, Lio/kamihama/magianative/CNMirrors$Mirror;->cooldownUntilNs:J

    .line 104
    iput-object p1, p0, Lio/kamihama/magianative/CNMirrors$Mirror;->name:Ljava/lang/String;

    .line 105
    iput-object p2, p0, Lio/kamihama/magianative/CNMirrors$Mirror;->base:Ljava/lang/String;

    .line 106
    iput p3, p0, Lio/kamihama/magianative/CNMirrors$Mirror;->weight:I

    .line 107
    iput p4, p0, Lio/kamihama/magianative/CNMirrors$Mirror;->chunks:I

    .line 108
    iput-boolean p5, p0, Lio/kamihama/magianative/CNMirrors$Mirror;->enabled:Z

    .line 109
    return-void
.end method


# virtual methods
.method public effectiveChunks()I
    .locals 1

    .line 118
    iget v0, p0, Lio/kamihama/magianative/CNMirrors$Mirror;->chunks:I

    if-lez v0, :cond_0

    goto :goto_0

    :cond_0
    invoke-static {}, Lio/kamihama/magianative/CNMirrors;->access$000()I

    move-result v0

    :goto_0
    return v0
.end method

.method public toString()Ljava/lang/String;
    .locals 2

    .line 122
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v1, p0, Lio/kamihama/magianative/CNMirrors$Mirror;->name:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " <"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v1, p0, Lio/kamihama/magianative/CNMirrors$Mirror;->base:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, ">"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public urlFor(Ljava/lang/String;)Ljava/lang/String;
    .locals 2

    .line 113
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v1, p0, Lio/kamihama/magianative/CNMirrors$Mirror;->base:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    return-object p1
.end method
