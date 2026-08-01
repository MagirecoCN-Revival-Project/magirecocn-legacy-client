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

    .line 1276
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    .line 1280
    :try_start_0
    const-string v0, "\u754c\u9762"

    const-string v1, "\u4e0b\u8f7d\u6d6e\u5c42\u5173\u95ed"

    invoke-static {v0, v1}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 1282
    const/4 v0, 0x0

    invoke-static {v0}, Lio/kamihama/magianative/CNLog;->setListener(Ljava/lang/Runnable;)V

    .line 1283
    invoke-static {}, Lio/kamihama/magianative/CNLog;->stopLogcatCapture()V

    .line 1284
    invoke-static {}, Lio/kamihama/magianative/CNLog;->close()V

    .line 1285
    sget-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->decorView:Landroid/view/ViewGroup;

    .line 1286
    sget-object v2, Lio/kamihama/magianative/CNCNDownloadUI;->overlayView:Landroid/widget/FrameLayout;

    .line 1287
    if-eqz v1, :cond_1

    if-nez v2, :cond_0

    goto :goto_0

    .line 1288
    :cond_0
    invoke-virtual {v1, v2}, Landroid/view/ViewGroup;->removeView(Landroid/view/View;)V

    .line 1289
    sput-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->overlayView:Landroid/widget/FrameLayout;

    .line 1290
    sput-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->tvLog:Landroid/widget/TextView;

    .line 1291
    sput-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->progressBarOverall:Landroid/widget/ProgressBar;

    .line 1292
    sput-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->tvSpeed:Landroid/widget/TextView;

    .line 1293
    sput-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->decorView:Landroid/view/ViewGroup;

    .line 1294
    sput-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->uiHandler:Landroid/os/Handler;

    .line 1296
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1902(Landroid/widget/TextView;)Landroid/widget/TextView;

    .line 1297
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$2002(Landroid/widget/TextView;)Landroid/widget/TextView;

    .line 1298
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$2102(Landroid/widget/TextView;)Landroid/widget/TextView;

    .line 1299
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$2202(Landroid/widget/TextView;)Landroid/widget/TextView;

    .line 1300
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$2302(Landroid/widget/LinearLayout;)Landroid/widget/LinearLayout;

    .line 1301
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$2402(Landroid/widget/LinearLayout;)Landroid/widget/LinearLayout;

    .line 1302
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$2502(Landroid/widget/TextView;)Landroid/widget/TextView;

    .line 1303
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$2602(Landroid/widget/TextView;)Landroid/widget/TextView;

    .line 1304
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$802(Landroid/widget/FrameLayout;)Landroid/widget/FrameLayout;

    .line 1305
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1002(Landroid/widget/ScrollView;)Landroid/widget/ScrollView;

    .line 1306
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$2702(Landroid/graphics/drawable/GradientDrawable;)Landroid/graphics/drawable/GradientDrawable;

    .line 1307
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$2802(Landroid/graphics/drawable/GradientDrawable;)Landroid/graphics/drawable/GradientDrawable;

    .line 1308
    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1302(Landroid/app/Activity;)Landroid/app/Activity;

    .line 1309
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$2900()Ljava/util/List;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/List;->clear()V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 1311
    goto :goto_1

    .line 1287
    :cond_1
    :goto_0
    return-void

    .line 1310
    :catchall_0
    move-exception v0

    .line 1312
    :goto_1
    return-void
.end method
