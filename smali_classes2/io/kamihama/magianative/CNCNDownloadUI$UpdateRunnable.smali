.class public Lio/kamihama/magianative/CNCNDownloadUI$UpdateRunnable;
.super Ljava/lang/Object;
.source "CNCNDownloadUI.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lio/kamihama/magianative/CNCNDownloadUI;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "UpdateRunnable"
.end annotation


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 1315
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 1

    .line 1319
    :try_start_0
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1700()V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 1321
    goto :goto_0

    .line 1320
    :catchall_0
    move-exception v0

    .line 1322
    :goto_0
    return-void
.end method
