.class public Lio/kamihama/magianative/CNCNDownloadUI$CreateUIRunnable;
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
    name = "CreateUIRunnable"
.end annotation


# instance fields
.field private final context:Landroid/app/Activity;


# direct methods
.method public constructor <init>(Landroid/app/Activity;)V
    .locals 0

    .line 1328
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 1329
    iput-object p1, p0, Lio/kamihama/magianative/CNCNDownloadUI$CreateUIRunnable;->context:Landroid/app/Activity;

    .line 1330
    return-void
.end method


# virtual methods
.method public run()V
    .locals 5

    .line 1335
    const-string v0, "\u754c\u9762"

    :try_start_0
    iget-object v1, p0, Lio/kamihama/magianative/CNCNDownloadUI$CreateUIRunnable;->context:Landroid/app/Activity;

    .line 1336
    if-nez v1, :cond_0

    return-void

    .line 1338
    :cond_0
    invoke-static {v1}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1302(Landroid/app/Activity;)Landroid/app/Activity;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_2

    .line 1342
    :try_start_1
    invoke-virtual {v1}, Landroid/app/Activity;->getFilesDir()Ljava/io/File;

    move-result-object v2

    invoke-static {v2}, Lio/kamihama/magianative/CNLog;->init(Ljava/io/File;)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_0

    .line 1343
    :catchall_0
    move-exception v2

    :goto_0
    nop

    .line 1344
    :try_start_2
    new-instance v2, Lio/kamihama/magianative/CNCNDownloadUI$LogChanged;

    const/4 v3, 0x0

    invoke-direct {v2, v3}, Lio/kamihama/magianative/CNCNDownloadUI$LogChanged;-><init>(Lio/kamihama/magianative/CNCNDownloadUI$1;)V

    invoke-static {v2}, Lio/kamihama/magianative/CNLog;->setListener(Ljava/lang/Runnable;)V

    .line 1347
    invoke-static {}, Lio/kamihama/magianative/CNLog;->startLogcatCapture()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_2

    .line 1350
    const/4 v2, 0x0

    :try_start_3
    const-string v3, "cnv_bootstrap_ui"

    .line 1351
    invoke-virtual {v1, v3, v2}, Landroid/app/Activity;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v3

    const-string v4, "dark_mode"

    .line 1352
    invoke-interface {v3, v4, v2}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v3

    .line 1350
    invoke-static {v3}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1402(Z)Z
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_1

    .line 1355
    goto :goto_1

    .line 1353
    :catchall_1
    move-exception v3

    .line 1354
    :try_start_4
    invoke-static {v2}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1402(Z)Z

    .line 1356
    :goto_1
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1400()Z

    move-result v2

    invoke-static {v2}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1500(Z)V

    .line 1357
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "\u4e0b\u8f7d\u6d6e\u5c42\u5df2\u521b\u5efa\uff0c\u4e3b\u9898="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1400()Z

    move-result v3

    if-eqz v3, :cond_1

    const-string v3, "\u591c\u95f4"

    goto :goto_2

    :cond_1
    const-string v3, "\u4eae\u8272"

    :goto_2
    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 1359
    nop

    .line 1360
    invoke-virtual {v1}, Landroid/app/Activity;->getWindow()Landroid/view/Window;

    move-result-object v2

    invoke-virtual {v2}, Landroid/view/Window;->getDecorView()Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/view/ViewGroup;

    sput-object v2, Lio/kamihama/magianative/CNCNDownloadUI;->decorView:Landroid/view/ViewGroup;

    .line 1361
    invoke-static {v1}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1600(Landroid/app/Activity;)Landroid/widget/FrameLayout;

    move-result-object v1

    .line 1362
    sget-object v2, Lio/kamihama/magianative/CNCNDownloadUI;->decorView:Landroid/view/ViewGroup;

    new-instance v3, Landroid/view/ViewGroup$LayoutParams;

    const/4 v4, -0x1

    invoke-direct {v3, v4, v4}, Landroid/view/ViewGroup$LayoutParams;-><init>(II)V

    invoke-virtual {v2, v1, v3}, Landroid/view/ViewGroup;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 1366
    sput-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->overlayView:Landroid/widget/FrameLayout;

    .line 1367
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1700()V
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_2

    .line 1373
    goto :goto_3

    .line 1368
    :catchall_2
    move-exception v1

    .line 1372
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "\u6d6e\u5c42\u521b\u5efa\u5931\u8d25: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2, v1}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 1374
    :goto_3
    return-void
.end method
