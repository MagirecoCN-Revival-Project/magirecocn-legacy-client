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

    .line 1207
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method synthetic constructor <init>(Lio/kamihama/magianative/CNCNDownloadUI$1;)V
    .locals 0

    .line 1207
    invoke-direct {p0}, Lio/kamihama/magianative/CNCNDownloadUI$RenderLog;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    .line 1209
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1300()Ljava/util/concurrent/atomic/AtomicBoolean;

    move-result-object v0

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Ljava/util/concurrent/atomic/AtomicBoolean;->set(Z)V

    .line 1210
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$900()V

    .line 1211
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1400()Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1500()Landroid/widget/ScrollView;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 1212
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1500()Landroid/widget/ScrollView;

    move-result-object v0

    new-instance v1, Lio/kamihama/magianative/CNCNDownloadUI$ScrollToBottom;

    const/4 v2, 0x0

    invoke-direct {v1, v2}, Lio/kamihama/magianative/CNCNDownloadUI$ScrollToBottom;-><init>(Lio/kamihama/magianative/CNCNDownloadUI$1;)V

    invoke-virtual {v0, v1}, Landroid/widget/ScrollView;->post(Ljava/lang/Runnable;)Z

    .line 1214
    :cond_0
    return-void
.end method
