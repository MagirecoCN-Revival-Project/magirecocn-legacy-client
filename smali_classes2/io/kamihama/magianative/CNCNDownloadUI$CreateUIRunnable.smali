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

    .line 1173
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 1174
    iput-object p1, p0, Lio/kamihama/magianative/CNCNDownloadUI$CreateUIRunnable;->context:Landroid/app/Activity;

    .line 1175
    return-void
.end method


# virtual methods
.method public run()V
    .locals 5

    .line 1180
    const-string v0, "\u754c\u9762"

    :try_start_0
    iget-object v1, p0, Lio/kamihama/magianative/CNCNDownloadUI$CreateUIRunnable;->context:Landroid/app/Activity;

    .line 1181
    if-nez v1, :cond_0

    return-void

    .line 1183
    :cond_0
    invoke-static {v1}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1302(Landroid/app/Activity;)Landroid/app/Activity;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 1187
    :try_start_1
    invoke-virtual {v1}, Landroid/app/Activity;->getFilesDir()Ljava/io/File;

    move-result-object v2

    invoke-static {v2}, Lio/kamihama/magianative/CNLog;->init(Ljava/io/File;)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_0

    .line 1188
    :catchall_0
    move-exception v2

    :goto_0
    nop

    .line 1189
    :try_start_2
    new-instance v2, Lio/kamihama/magianative/CNCNDownloadUI$LogChanged;

    const/4 v3, 0x0

    invoke-direct {v2, v3}, Lio/kamihama/magianative/CNCNDownloadUI$LogChanged;-><init>(Lio/kamihama/magianative/CNCNDownloadUI$1;)V

    invoke-static {v2}, Lio/kamihama/magianative/CNLog;->setListener(Ljava/lang/Runnable;)V
    :try_end_2
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_2} :catch_0

    .line 1192
    const/4 v2, 0x0

    :try_start_3
    const-string v3, "cnv_bootstrap_ui"

    .line 1193
    invoke-virtual {v1, v3, v2}, Landroid/app/Activity;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v3

    const-string v4, "dark_mode"

    .line 1194
    invoke-interface {v3, v4, v2}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v3

    .line 1192
    invoke-static {v3}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1502(Z)Z
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_1

    .line 1197
    goto :goto_1

    .line 1195
    :catchall_1
    move-exception v3

    .line 1196
    :try_start_4
    invoke-static {v2}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1502(Z)Z

    .line 1198
    :goto_1
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1500()Z

    move-result v2

    invoke-static {v2}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1600(Z)V

    .line 1199
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "\u4e0b\u8f7d\u6d6e\u5c42\u5df2\u521b\u5efa\uff0c\u4e3b\u9898="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1500()Z

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

    .line 1201
    nop

    .line 1202
    invoke-virtual {v1}, Landroid/app/Activity;->getWindow()Landroid/view/Window;

    move-result-object v2

    invoke-virtual {v2}, Landroid/view/Window;->getDecorView()Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/view/ViewGroup;

    sput-object v2, Lio/kamihama/magianative/CNCNDownloadUI;->decorView:Landroid/view/ViewGroup;

    .line 1203
    invoke-static {v1}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1700(Landroid/app/Activity;)Landroid/widget/FrameLayout;

    move-result-object v1

    .line 1204
    sget-object v2, Lio/kamihama/magianative/CNCNDownloadUI;->decorView:Landroid/view/ViewGroup;

    new-instance v3, Landroid/view/ViewGroup$LayoutParams;

    const/4 v4, -0x1

    invoke-direct {v3, v4, v4}, Landroid/view/ViewGroup$LayoutParams;-><init>(II)V

    invoke-virtual {v2, v1, v3}, Landroid/view/ViewGroup;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 1208
    sput-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->overlayView:Landroid/widget/FrameLayout;

    .line 1209
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1800()V
    :try_end_4
    .catch Ljava/lang/Exception; {:try_start_4 .. :try_end_4} :catch_0

    .line 1212
    goto :goto_3

    .line 1210
    :catch_0
    move-exception v1

    .line 1211
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "\u6d6e\u5c42\u64cd\u4f5c\u5931\u8d25: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v1}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2, v1}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 1213
    :goto_3
    return-void
.end method
