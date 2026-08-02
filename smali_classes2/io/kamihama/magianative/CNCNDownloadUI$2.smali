.class Lio/kamihama/magianative/CNCNDownloadUI$2;
.super Ljava/lang/Object;
.source "CNCNDownloadUI.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lio/kamihama/magianative/CNCNDownloadUI;->buildOverlay(Landroid/app/Activity;)Landroid/widget/FrameLayout;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$act:Landroid/app/Activity;


# direct methods
.method constructor <init>(Landroid/app/Activity;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 623
    iput-object p1, p0, Lio/kamihama/magianative/CNCNDownloadUI$2;->val$act:Landroid/app/Activity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 0

    .line 624
    iget-object p1, p0, Lio/kamihama/magianative/CNCNDownloadUI$2;->val$act:Landroid/app/Activity;

    invoke-static {p1}, Lio/kamihama/magianative/CNCNDownloadUI;->access$100(Landroid/app/Activity;)V

    return-void
.end method
