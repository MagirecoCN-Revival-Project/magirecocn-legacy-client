.class final Lio/kamihama/magianative/CNCNDownloadUI$DotView;
.super Landroid/view/View;
.source "CNCNDownloadUI.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lio/kamihama/magianative/CNCNDownloadUI;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1a
    name = "DotView"
.end annotation


# instance fields
.field private final p:Landroid/graphics/Paint;


# direct methods
.method constructor <init>(Landroid/content/Context;I)V
    .locals 1

    .line 370
    invoke-direct {p0, p1}, Landroid/view/View;-><init>(Landroid/content/Context;)V

    .line 368
    new-instance p1, Landroid/graphics/Paint;

    const/4 v0, 0x1

    invoke-direct {p1, v0}, Landroid/graphics/Paint;-><init>(I)V

    iput-object p1, p0, Lio/kamihama/magianative/CNCNDownloadUI$DotView;->p:Landroid/graphics/Paint;

    .line 371
    invoke-virtual {p1, p2}, Landroid/graphics/Paint;->setColor(I)V

    .line 372
    sget-object p2, Landroid/graphics/Paint$Style;->FILL:Landroid/graphics/Paint$Style;

    invoke-virtual {p1, p2}, Landroid/graphics/Paint;->setStyle(Landroid/graphics/Paint$Style;)V

    .line 373
    return-void
.end method


# virtual methods
.method protected onDraw(Landroid/graphics/Canvas;)V
    .locals 4

    .line 375
    invoke-virtual {p0}, Lio/kamihama/magianative/CNCNDownloadUI$DotView;->getWidth()I

    move-result v0

    invoke-virtual {p0}, Lio/kamihama/magianative/CNCNDownloadUI$DotView;->getHeight()I

    move-result v1

    invoke-static {v0, v1}, Ljava/lang/Math;->min(II)I

    move-result v0

    int-to-float v0, v0

    const/high16 v1, 0x40000000    # 2.0f

    div-float/2addr v0, v1

    .line 376
    invoke-virtual {p0}, Lio/kamihama/magianative/CNCNDownloadUI$DotView;->getWidth()I

    move-result v2

    int-to-float v2, v2

    div-float/2addr v2, v1

    invoke-virtual {p0}, Lio/kamihama/magianative/CNCNDownloadUI$DotView;->getHeight()I

    move-result v3

    int-to-float v3, v3

    div-float/2addr v3, v1

    iget-object v1, p0, Lio/kamihama/magianative/CNCNDownloadUI$DotView;->p:Landroid/graphics/Paint;

    invoke-virtual {p1, v2, v3, v0, v1}, Landroid/graphics/Canvas;->drawCircle(FFFLandroid/graphics/Paint;)V

    .line 377
    return-void
.end method
