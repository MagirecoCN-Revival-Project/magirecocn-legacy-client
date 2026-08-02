.class public Lio/kamihama/magianative/CNCNDownloadUI$HideRunnable;
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
    name = "HideRunnable"
.end annotation


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 1535
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    .line 1539
    :try_start_0
    const-string v0, "\u754c\u9762"

    const-string v1, "\u4e0b\u8f7d\u6d6e\u5c42\u5173\u95ed"

    invoke-static {v0, v1}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 1541
    const/4 v0, 0x0

    invoke-static {v0}, Lio/kamihama/magianative/CNLog;->setListener(Ljava/lang/Runnable;)V

    .line 1542
    invoke-static {}, Lio/kamihama/magianative/CNLog;->stopLogcatCapture()V

    .line 1543
    invoke-static {}, Lio/kamihama/magianative/CNLog;->close()V

    .line 1544
    sget-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->decorView:Landroid/view/ViewGroup;

    .line 1545
    sget-object v2, Lio/kamihama/magianative/CNCNDownloadUI;->overlayView:Landroid/widget/FrameLayout;

    .line 1546
    if-eqz v1, :cond_1

    if-nez v2, :cond_0

    goto :goto_0

    .line 1547
    :cond_0
    invoke-virtual {v1, v2}, Landroid/view/ViewGroup;->removeView(Landroid/view/View;)V

    .line 1548
    sput-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->overlayView:Landroid/widget/FrameLayout;

    .line 1549
    sput-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->tvLog:Landroid/widget/TextView;

    .line 1550
    sput-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->progressBarOverall:Landroid/widget/ProgressBar;

    .line 1551
    sput-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->tvSpeed:Landroid/widget/TextView;

    .line 1552
    sput-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->decorView:Landroid/view/ViewGroup;

    .line 1553
    sput-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->uiHandler:Landroid/os/Handler;

    .line 1555
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$2302(Landroid/widget/TextView;)Landroid/widget/TextView;

    .line 1556
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$2402(Landroid/widget/TextView;)Landroid/widget/TextView;

    .line 1557
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$2502(Landroid/widget/TextView;)Landroid/widget/TextView;

    .line 1558
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$2602(Landroid/widget/TextView;)Landroid/widget/TextView;

    .line 1559
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$2702(Landroid/widget/LinearLayout;)Landroid/widget/LinearLayout;

    .line 1560
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$2802(Landroid/widget/LinearLayout;)Landroid/widget/LinearLayout;

    .line 1561
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$2902(Landroid/widget/TextView;)Landroid/widget/TextView;

    .line 1562
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$3002(Landroid/widget/TextView;)Landroid/widget/TextView;

    .line 1563
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$3102(Landroid/widget/FrameLayout;)Landroid/widget/FrameLayout;

    .line 1564
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1502(Landroid/widget/ScrollView;)Landroid/widget/ScrollView;

    .line 1565
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$3202(Landroid/graphics/drawable/GradientDrawable;)Landroid/graphics/drawable/GradientDrawable;

    .line 1566
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$3302(Landroid/graphics/drawable/GradientDrawable;)Landroid/graphics/drawable/GradientDrawable;

    .line 1567
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1702(Landroid/app/Activity;)Landroid/app/Activity;

    .line 1568
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$3400()Ljava/util/List;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/List;->clear()V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 1570
    goto :goto_1

    .line 1546
    :cond_1
    :goto_0
    return-void

    .line 1569
    :catchall_0
    move-exception v0

    .line 1571
    :goto_1
    return-void
.end method
