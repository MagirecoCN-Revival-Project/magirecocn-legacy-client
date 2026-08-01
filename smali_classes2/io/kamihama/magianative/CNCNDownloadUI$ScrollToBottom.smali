.class final Lio/kamihama/magianative/CNCNDownloadUI$ScrollToBottom;
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
    name = "ScrollToBottom"
.end annotation


# direct methods
.method private constructor <init>()V
    .locals 0

    .line 1082
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method synthetic constructor <init>(Lio/kamihama/magianative/CNCNDownloadUI$1;)V
    .locals 0

    .line 1082
    invoke-direct {p0}, Lio/kamihama/magianative/CNCNDownloadUI$ScrollToBottom;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 2

    .line 1084
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1000()Landroid/widget/ScrollView;

    move-result-object v0

    if-eqz v0, :cond_0

    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$1000()Landroid/widget/ScrollView;

    move-result-object v0

    const/16 v1, 0x82

    invoke-virtual {v0, v1}, Landroid/widget/ScrollView;->fullScroll(I)Z

    .line 1085
    :cond_0
    return-void
.end method
