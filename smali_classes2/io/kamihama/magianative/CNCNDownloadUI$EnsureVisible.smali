.class final Lio/kamihama/magianative/CNCNDownloadUI$EnsureVisible;
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
    name = "EnsureVisible"
.end annotation


# instance fields
.field private final act:Landroid/app/Activity;


# direct methods
.method constructor <init>(Landroid/app/Activity;)V
    .locals 0

    .line 1171
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lio/kamihama/magianative/CNCNDownloadUI$EnsureVisible;->act:Landroid/app/Activity;

    return-void
.end method


# virtual methods
.method public run()V
    .locals 5

    .line 1174
    const-string v0, "\u754c\u9762"

    :try_start_0
    sget-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->overlayView:Landroid/widget/FrameLayout;

    .line 1175
    if-eqz v1, :cond_0

    invoke-virtual {v1}, Landroid/widget/FrameLayout;->getParent()Landroid/view/ViewParent;

    move-result-object v2

    if-eqz v2, :cond_0

    return-void

    .line 1177
    :cond_0
    iget-object v2, p0, Lio/kamihama/magianative/CNCNDownloadUI$EnsureVisible;->act:Landroid/app/Activity;

    invoke-virtual {v2}, Landroid/app/Activity;->getWindow()Landroid/view/Window;

    move-result-object v2

    invoke-virtual {v2}, Landroid/view/Window;->getDecorView()Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/view/ViewGroup;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    .line 1178
    const/4 v3, -0x1

    if-eqz v1, :cond_1

    .line 1180
    :try_start_1
    new-instance v4, Landroid/view/ViewGroup$LayoutParams;

    invoke-direct {v4, v3, v3}, Landroid/view/ViewGroup$LayoutParams;-><init>(II)V

    invoke-virtual {v2, v1, v4}, Landroid/view/ViewGroup;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_0

    .line 1182
    :catchall_0
    move-exception v1

    :goto_0
    nop

    .line 1183
    :try_start_2
    sput-object v2, Lio/kamihama/magianative/CNCNDownloadUI;->decorView:Landroid/view/ViewGroup;

    .line 1184
    const-string v1, "\u6d6e\u5c42\u66fe\u8131\u79bb\u89c6\u56fe\u6811\uff0c\u5df2\u91cd\u65b0\u6302\u4e0a"

    invoke-static {v0, v1}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;)V

    .line 1185
    return-void

    .line 1188
    :cond_1
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1600()Landroid/app/Activity;

    move-result-object v1

    if-nez v1, :cond_2

    iget-object v1, p0, Lio/kamihama/magianative/CNCNDownloadUI$EnsureVisible;->act:Landroid/app/Activity;

    invoke-static {v1}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1602(Landroid/app/Activity;)Landroid/app/Activity;

    .line 1189
    :cond_2
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1700()Z

    move-result v1

    invoke-static {v1}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1800(Z)V

    .line 1190
    iget-object v1, p0, Lio/kamihama/magianative/CNCNDownloadUI$EnsureVisible;->act:Landroid/app/Activity;

    invoke-static {v1}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1900(Landroid/app/Activity;)Landroid/widget/FrameLayout;

    move-result-object v1

    .line 1191
    new-instance v4, Landroid/view/ViewGroup$LayoutParams;

    invoke-direct {v4, v3, v3}, Landroid/view/ViewGroup$LayoutParams;-><init>(II)V

    invoke-virtual {v2, v1, v4}, Landroid/view/ViewGroup;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 1194
    sput-object v2, Lio/kamihama/magianative/CNCNDownloadUI;->decorView:Landroid/view/ViewGroup;

    .line 1195
    sput-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->overlayView:Landroid/widget/FrameLayout;

    .line 1196
    const/4 v1, 0x1

    sput-boolean v1, Lio/kamihama/magianative/CNCNDownloadUI;->isShowing:Z

    .line 1197
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$2000()V

    .line 1198
    const-string v1, "\u6d6e\u5c42\u7f3a\u5931\uff0c\u5df2\u91cd\u5efa\u5e76\u6302\u4e0a"

    invoke-static {v0, v1}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_1

    .line 1201
    goto :goto_1

    .line 1199
    :catchall_1
    move-exception v1

    .line 1200
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "\u91cd\u6302\u6d6e\u5c42\u5931\u8d25: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2, v1}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 1202
    :goto_1
    return-void
.end method
