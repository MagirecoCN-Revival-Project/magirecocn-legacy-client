.class final Lio/kamihama/magianative/CNCNDownloadUI$RenderLog;
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
    name = "RenderLog"
.end annotation


# direct methods
.method private constructor <init>()V
    .locals 0

    .line 1100
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method synthetic constructor <init>(Lio/kamihama/magianative/CNCNDownloadUI$1;)V
    .locals 0

    .line 1100
    invoke-direct {p0}, Lio/kamihama/magianative/CNCNDownloadUI$RenderLog;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 4

    .line 1102
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1000()Ljava/util/concurrent/atomic/AtomicBoolean;

    move-result-object v0

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Ljava/util/concurrent/atomic/AtomicBoolean;->set(Z)V

    .line 1103
    nop

    .line 1104
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1100()Landroid/widget/ScrollView;

    move-result-object v0

    if-eqz v0, :cond_0

    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->tvLog:Landroid/widget/TextView;

    if-eqz v0, :cond_0

    .line 1105
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1100()Landroid/widget/ScrollView;

    move-result-object v0

    invoke-virtual {v0}, Landroid/widget/ScrollView;->getScrollY()I

    move-result v0

    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1100()Landroid/widget/ScrollView;

    move-result-object v2

    invoke-virtual {v2}, Landroid/widget/ScrollView;->getHeight()I

    move-result v2

    add-int/2addr v0, v2

    .line 1106
    sget-object v2, Lio/kamihama/magianative/CNCNDownloadUI;->tvLog:Landroid/widget/TextView;

    invoke-virtual {v2}, Landroid/widget/TextView;->getHeight()I

    move-result v2

    const/16 v3, 0x18

    invoke-static {v3}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1200(I)I

    move-result v3

    sub-int/2addr v2, v3

    if-lt v0, v2, :cond_0

    const/4 v0, 0x1

    const/4 v1, 0x1

    .line 1108
    :cond_0
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1300()V

    .line 1109
    if-eqz v1, :cond_1

    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1100()Landroid/widget/ScrollView;

    move-result-object v0

    if-eqz v0, :cond_1

    .line 1110
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1100()Landroid/widget/ScrollView;

    move-result-object v0

    new-instance v1, Lio/kamihama/magianative/CNCNDownloadUI$ScrollToBottom;

    const/4 v2, 0x0

    invoke-direct {v1, v2}, Lio/kamihama/magianative/CNCNDownloadUI$ScrollToBottom;-><init>(Lio/kamihama/magianative/CNCNDownloadUI$1;)V

    invoke-virtual {v0, v1}, Landroid/widget/ScrollView;->post(Ljava/lang/Runnable;)Z

    .line 1112
    :cond_1
    return-void
.end method
