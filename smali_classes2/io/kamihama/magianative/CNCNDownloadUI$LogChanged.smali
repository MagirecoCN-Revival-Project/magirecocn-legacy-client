.class final Lio/kamihama/magianative/CNCNDownloadUI$LogChanged;
.super Ljava/lang/Object;
.source "CNCNDownloadUI.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lio/kamihama/magianative/CNCNDownloadUI;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1a
    name = "LogChanged"
.end annotation


# direct methods
.method private constructor <init>()V
    .locals 0

    .line 1058
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method synthetic constructor <init>(Lio/kamihama/magianative/CNCNDownloadUI$1;)V
    .locals 0

    .line 1058
    invoke-direct {p0}, Lio/kamihama/magianative/CNCNDownloadUI$LogChanged;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    .line 1060
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->uiHandler:Landroid/os/Handler;

    .line 1061
    if-eqz v0, :cond_2

    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$800()Landroid/widget/FrameLayout;

    move-result-object v1

    if-nez v1, :cond_0

    goto :goto_0

    .line 1062
    :cond_0
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$800()Landroid/widget/FrameLayout;

    move-result-object v1

    invoke-virtual {v1}, Landroid/widget/FrameLayout;->getVisibility()I

    move-result v1

    if-eqz v1, :cond_1

    return-void

    .line 1063
    :cond_1
    new-instance v1, Lio/kamihama/magianative/CNCNDownloadUI$RenderLog;

    const/4 v2, 0x0

    invoke-direct {v1, v2}, Lio/kamihama/magianative/CNCNDownloadUI$RenderLog;-><init>(Lio/kamihama/magianative/CNCNDownloadUI$1;)V

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 1064
    return-void

    .line 1061
    :cond_2
    :goto_0
    return-void
.end method
