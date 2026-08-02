.class final Lio/kamihama/magianative/CNCNDownloadUI$SlotViews;
.super Ljava/lang/Object;
.source "CNCNDownloadUI.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lio/kamihama/magianative/CNCNDownloadUI;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1a
    name = "SlotViews"
.end annotation


# instance fields
.field final bar:Landroid/widget/ProgressBar;

.field final divider:Landroid/view/View;

.field final infoView:Landroid/widget/TextView;

.field final nameView:Landroid/widget/TextView;

.field final retryView:Landroid/widget/TextView;


# direct methods
.method constructor <init>(Landroid/widget/TextView;Landroid/widget/TextView;Landroid/widget/TextView;Landroid/widget/ProgressBar;Landroid/view/View;)V
    .locals 0

    .line 303
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 304
    iput-object p1, p0, Lio/kamihama/magianative/CNCNDownloadUI$SlotViews;->nameView:Landroid/widget/TextView;

    iput-object p2, p0, Lio/kamihama/magianative/CNCNDownloadUI$SlotViews;->infoView:Landroid/widget/TextView;

    iput-object p3, p0, Lio/kamihama/magianative/CNCNDownloadUI$SlotViews;->retryView:Landroid/widget/TextView;

    iput-object p4, p0, Lio/kamihama/magianative/CNCNDownloadUI$SlotViews;->bar:Landroid/widget/ProgressBar;

    iput-object p5, p0, Lio/kamihama/magianative/CNCNDownloadUI$SlotViews;->divider:Landroid/view/View;

    .line 305
    return-void
.end method
