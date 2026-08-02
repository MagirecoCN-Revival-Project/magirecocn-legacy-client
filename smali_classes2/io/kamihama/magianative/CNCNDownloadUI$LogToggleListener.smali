.class final Lio/kamihama/magianative/CNCNDownloadUI$LogToggleListener;
.super Ljava/lang/Object;
.source "CNCNDownloadUI.java"

# interfaces
.implements Landroid/widget/CompoundButton$OnCheckedChangeListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lio/kamihama/magianative/CNCNDownloadUI;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1a
    name = "LogToggleListener"
.end annotation


# instance fields
.field private final act:Landroid/app/Activity;

.field private final prefKey:Ljava/lang/String;

.field private final which:I


# direct methods
.method constructor <init>(Landroid/app/Activity;Ljava/lang/String;I)V
    .locals 0

    .line 1075
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 1076
    iput-object p1, p0, Lio/kamihama/magianative/CNCNDownloadUI$LogToggleListener;->act:Landroid/app/Activity;

    iput-object p2, p0, Lio/kamihama/magianative/CNCNDownloadUI$LogToggleListener;->prefKey:Ljava/lang/String;

    iput p3, p0, Lio/kamihama/magianative/CNCNDownloadUI$LogToggleListener;->which:I

    .line 1077
    return-void
.end method


# virtual methods
.method public onCheckedChanged(Landroid/widget/CompoundButton;Z)V
    .locals 2

    .line 1081
    :try_start_0
    iget-object p1, p0, Lio/kamihama/magianative/CNCNDownloadUI$LogToggleListener;->act:Landroid/app/Activity;

    const-string v0, "cnv_bootstrap_ui"

    const/4 v1, 0x0

    invoke-virtual {p1, v0, v1}, Landroid/app/Activity;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object p1

    .line 1082
    invoke-interface {p1}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object p1

    iget-object v0, p0, Lio/kamihama/magianative/CNCNDownloadUI$LogToggleListener;->prefKey:Ljava/lang/String;

    invoke-interface {p1, v0, p2}, Landroid/content/SharedPreferences$Editor;->putBoolean(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;

    move-result-object p1

    invoke-interface {p1}, Landroid/content/SharedPreferences$Editor;->apply()V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    goto :goto_0

    .line 1083
    :catchall_0
    move-exception p1

    :goto_0
    nop

    .line 1084
    iget p1, p0, Lio/kamihama/magianative/CNCNDownloadUI$LogToggleListener;->which:I

    invoke-static {p1, p2}, Lio/kamihama/magianative/CNCNDownloadUI;->access$800(IZ)V

    .line 1085
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "\u65e5\u5fd7\u5f00\u5173 "

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    iget-object v0, p0, Lio/kamihama/magianative/CNCNDownloadUI$LogToggleListener;->prefKey:Ljava/lang/String;

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v0, " = "

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    const-string p2, "\u754c\u9762"

    invoke-static {p2, p1}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 1086
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$900()V

    .line 1087
    return-void
.end method
