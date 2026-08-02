.class final Lio/kamihama/magianative/CNCNDownloadUI$LogScrollWatcher;
.super Ljava/lang/Object;
.source "CNCNDownloadUI.java"

# interfaces
.implements Landroid/view/ViewTreeObserver$OnScrollChangedListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lio/kamihama/magianative/CNCNDownloadUI;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1a
    name = "LogScrollWatcher"
.end annotation


# direct methods
.method private constructor <init>()V
    .locals 0

    .line 1218
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method synthetic constructor <init>(Lio/kamihama/magianative/CNCNDownloadUI$1;)V
    .locals 0

    .line 1218
    invoke-direct {p0}, Lio/kamihama/magianative/CNCNDownloadUI$LogScrollWatcher;-><init>()V

    return-void
.end method


# virtual methods
.method public onScrollChanged()V
    .locals 4

    .line 1222
    :try_start_0
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1500()Landroid/widget/ScrollView;

    move-result-object v0

    .line 1223
    if-eqz v0, :cond_2

    invoke-virtual {v0}, Landroid/widget/ScrollView;->getChildCount()I

    move-result v1

    if-nez v1, :cond_0

    goto :goto_0

    .line 1224
    :cond_0
    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/widget/ScrollView;->getChildAt(I)Landroid/view/View;

    move-result-object v2

    .line 1225
    invoke-virtual {v2}, Landroid/view/View;->getHeight()I

    move-result v2

    invoke-virtual {v0}, Landroid/widget/ScrollView;->getHeight()I

    move-result v3

    sub-int/2addr v2, v3

    invoke-virtual {v0}, Landroid/widget/ScrollView;->getScrollY()I

    move-result v3

    sub-int/2addr v2, v3

    .line 1227
    const/16 v3, 0x20

    invoke-static {v3}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1600(I)I

    move-result v3

    invoke-virtual {v0}, Landroid/widget/ScrollView;->getHeight()I

    move-result v0

    div-int/lit8 v0, v0, 0x6

    invoke-static {v3, v0}, Ljava/lang/Math;->max(II)I

    move-result v0

    if-gt v2, v0, :cond_1

    const/4 v1, 0x1

    :cond_1
    invoke-static {v1}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1402(Z)Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    goto :goto_1

    .line 1223
    :cond_2
    :goto_0
    return-void

    .line 1228
    :catchall_0
    move-exception v0

    :goto_1
    nop

    .line 1229
    return-void
.end method
