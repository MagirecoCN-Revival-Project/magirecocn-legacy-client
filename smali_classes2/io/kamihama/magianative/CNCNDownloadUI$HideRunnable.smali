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

    .line 1377
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    .line 1381
    :try_start_0
    const-string v0, "\u754c\u9762"

    const-string v1, "\u4e0b\u8f7d\u6d6e\u5c42\u5173\u95ed"

    invoke-static {v0, v1}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 1383
    const/4 v0, 0x0

    invoke-static {v0}, Lio/kamihama/magianative/CNLog;->setListener(Ljava/lang/Runnable;)V

    .line 1384
    invoke-static {}, Lio/kamihama/magianative/CNLog;->stopLogcatCapture()V

    .line 1385
    invoke-static {}, Lio/kamihama/magianative/CNLog;->close()V

    .line 1386
    sget-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->decorView:Landroid/view/ViewGroup;

    .line 1387
    sget-object v2, Lio/kamihama/magianative/CNCNDownloadUI;->overlayView:Landroid/widget/FrameLayout;

    .line 1388
    if-eqz v1, :cond_1

    if-nez v2, :cond_0

    goto :goto_0

    .line 1389
    :cond_0
    invoke-virtual {v1, v2}, Landroid/view/ViewGroup;->removeView(Landroid/view/View;)V

    .line 1390
    sput-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->overlayView:Landroid/widget/FrameLayout;

    .line 1391
    sput-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->tvLog:Landroid/widget/TextView;

    .line 1392
    sput-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->progressBarOverall:Landroid/widget/ProgressBar;

    .line 1393
    sput-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->tvSpeed:Landroid/widget/TextView;

    .line 1394
    sput-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->decorView:Landroid/view/ViewGroup;

    .line 1395
    sput-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->uiHandler:Landroid/os/Handler;

    .line 1397
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1902(Landroid/widget/TextView;)Landroid/widget/TextView;

    .line 1398
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$2002(Landroid/widget/TextView;)Landroid/widget/TextView;

    .line 1399
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$2102(Landroid/widget/TextView;)Landroid/widget/TextView;

    .line 1400
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$2202(Landroid/widget/TextView;)Landroid/widget/TextView;

    .line 1401
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$2302(Landroid/widget/LinearLayout;)Landroid/widget/LinearLayout;

    .line 1402
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$2402(Landroid/widget/LinearLayout;)Landroid/widget/LinearLayout;

    .line 1403
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$2502(Landroid/widget/TextView;)Landroid/widget/TextView;

    .line 1404
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$2602(Landroid/widget/TextView;)Landroid/widget/TextView;

    .line 1405
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$802(Landroid/widget/FrameLayout;)Landroid/widget/FrameLayout;

    .line 1406
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1002(Landroid/widget/ScrollView;)Landroid/widget/ScrollView;

    .line 1407
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$2702(Landroid/graphics/drawable/GradientDrawable;)Landroid/graphics/drawable/GradientDrawable;

    .line 1408
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$2802(Landroid/graphics/drawable/GradientDrawable;)Landroid/graphics/drawable/GradientDrawable;

    .line 1409
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1302(Landroid/app/Activity;)Landroid/app/Activity;

    .line 1410
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$2900()Ljava/util/List;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/List;->clear()V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 1412
    goto :goto_1

    .line 1388
    :cond_1
    :goto_0
    return-void

    .line 1411
    :catchall_0
    move-exception v0

    .line 1413
    :goto_1
    return-void
.end method
