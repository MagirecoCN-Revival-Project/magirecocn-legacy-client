.class final Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;
.super Landroid/view/View;
.source "CNCNDownloadUI.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lio/kamihama/magianative/CNCNDownloadUI;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1a
    name = "GlassPanelView"
.end annotation


# instance fields
.field private blurBitmap:Landroid/graphics/Bitmap;

.field private final bounds:Landroid/graphics/RectF;

.field private final fillColor:I

.field private final paint:Landroid/graphics/Paint;

.field private final radius:F

.field private final strokeColor:I


# direct methods
.method constructor <init>(Landroid/content/Context;IIF)V
    .locals 1

    .line 320
    invoke-direct {p0, p1}, Landroid/view/View;-><init>(Landroid/content/Context;)V

    .line 312
    new-instance p1, Landroid/graphics/Paint;

    const/4 v0, 0x1

    invoke-direct {p1, v0}, Landroid/graphics/Paint;-><init>(I)V

    iput-object p1, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->paint:Landroid/graphics/Paint;

    .line 313
    new-instance p1, Landroid/graphics/RectF;

    invoke-direct {p1}, Landroid/graphics/RectF;-><init>()V

    iput-object p1, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->bounds:Landroid/graphics/RectF;

    .line 321
    iput p2, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->fillColor:I

    .line 322
    iput p3, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->strokeColor:I

    .line 323
    iput p4, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->radius:F

    .line 324
    return-void
.end method


# virtual methods
.method protected onDraw(Landroid/graphics/Canvas;)V
    .locals 6

    .line 333
    iget-object v0, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->bounds:Landroid/graphics/RectF;

    invoke-virtual {p0}, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->getWidth()I

    move-result v1

    int-to-float v1, v1

    invoke-virtual {p0}, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->getHeight()I

    move-result v2

    int-to-float v2, v2

    const/4 v3, 0x0

    invoke-virtual {v0, v3, v3, v1, v2}, Landroid/graphics/RectF;->set(FFFF)V

    .line 334
    iget-object v0, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->blurBitmap:Landroid/graphics/Bitmap;

    if-eqz v0, :cond_0

    .line 335
    new-instance v0, Landroid/graphics/Paint;

    const/4 v1, 0x1

    invoke-direct {v0, v1}, Landroid/graphics/Paint;-><init>(I)V

    .line 336
    new-instance v1, Landroid/graphics/Matrix;

    invoke-direct {v1}, Landroid/graphics/Matrix;-><init>()V

    .line 337
    invoke-virtual {p0}, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->getWidth()I

    move-result v2

    int-to-float v2, v2

    iget-object v3, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->blurBitmap:Landroid/graphics/Bitmap;

    invoke-virtual {v3}, Landroid/graphics/Bitmap;->getWidth()I

    move-result v3

    int-to-float v3, v3

    div-float/2addr v2, v3

    .line 338
    invoke-virtual {p0}, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->getHeight()I

    move-result v3

    int-to-float v3, v3

    iget-object v4, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->blurBitmap:Landroid/graphics/Bitmap;

    invoke-virtual {v4}, Landroid/graphics/Bitmap;->getHeight()I

    move-result v4

    int-to-float v4, v4

    div-float/2addr v3, v4

    .line 337
    invoke-virtual {v1, v2, v3}, Landroid/graphics/Matrix;->setScale(FF)V

    .line 339
    new-instance v2, Landroid/graphics/BitmapShader;

    iget-object v3, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->blurBitmap:Landroid/graphics/Bitmap;

    sget-object v4, Landroid/graphics/Shader$TileMode;->CLAMP:Landroid/graphics/Shader$TileMode;

    sget-object v5, Landroid/graphics/Shader$TileMode;->CLAMP:Landroid/graphics/Shader$TileMode;

    invoke-direct {v2, v3, v4, v5}, Landroid/graphics/BitmapShader;-><init>(Landroid/graphics/Bitmap;Landroid/graphics/Shader$TileMode;Landroid/graphics/Shader$TileMode;)V

    .line 341
    invoke-virtual {v2, v1}, Landroid/graphics/BitmapShader;->setLocalMatrix(Landroid/graphics/Matrix;)V

    .line 342
    invoke-virtual {v0, v2}, Landroid/graphics/Paint;->setShader(Landroid/graphics/Shader;)Landroid/graphics/Shader;

    .line 343
    iget-object v1, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->bounds:Landroid/graphics/RectF;

    iget v2, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->radius:F

    invoke-virtual {p1, v1, v2, v2, v0}, Landroid/graphics/Canvas;->drawRoundRect(Landroid/graphics/RectF;FFLandroid/graphics/Paint;)V

    .line 344
    iget-object v0, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->paint:Landroid/graphics/Paint;

    iget v1, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->fillColor:I

    const v2, -0x77000001

    and-int/2addr v1, v2

    invoke-virtual {v0, v1}, Landroid/graphics/Paint;->setColor(I)V

    .line 345
    iget-object v0, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->paint:Landroid/graphics/Paint;

    sget-object v1, Landroid/graphics/Paint$Style;->FILL:Landroid/graphics/Paint$Style;

    invoke-virtual {v0, v1}, Landroid/graphics/Paint;->setStyle(Landroid/graphics/Paint$Style;)V

    .line 346
    iget-object v0, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->bounds:Landroid/graphics/RectF;

    iget v1, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->radius:F

    iget-object v2, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->paint:Landroid/graphics/Paint;

    invoke-virtual {p1, v0, v1, v1, v2}, Landroid/graphics/Canvas;->drawRoundRect(Landroid/graphics/RectF;FFLandroid/graphics/Paint;)V

    .line 347
    goto :goto_0

    .line 348
    :cond_0
    iget-object v0, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->paint:Landroid/graphics/Paint;

    iget v1, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->fillColor:I

    invoke-virtual {v0, v1}, Landroid/graphics/Paint;->setColor(I)V

    .line 349
    iget-object v0, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->paint:Landroid/graphics/Paint;

    sget-object v1, Landroid/graphics/Paint$Style;->FILL:Landroid/graphics/Paint$Style;

    invoke-virtual {v0, v1}, Landroid/graphics/Paint;->setStyle(Landroid/graphics/Paint$Style;)V

    .line 350
    iget-object v0, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->bounds:Landroid/graphics/RectF;

    iget v1, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->radius:F

    iget-object v2, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->paint:Landroid/graphics/Paint;

    invoke-virtual {p1, v0, v1, v1, v2}, Landroid/graphics/Canvas;->drawRoundRect(Landroid/graphics/RectF;FFLandroid/graphics/Paint;)V

    .line 352
    :goto_0
    iget-object v0, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->paint:Landroid/graphics/Paint;

    iget v1, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->strokeColor:I

    invoke-virtual {v0, v1}, Landroid/graphics/Paint;->setColor(I)V

    .line 353
    iget-object v0, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->paint:Landroid/graphics/Paint;

    sget-object v1, Landroid/graphics/Paint$Style;->STROKE:Landroid/graphics/Paint$Style;

    invoke-virtual {v0, v1}, Landroid/graphics/Paint;->setStyle(Landroid/graphics/Paint$Style;)V

    .line 354
    iget-object v0, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->paint:Landroid/graphics/Paint;

    const/high16 v1, 0x40000000    # 2.0f

    invoke-virtual {v0, v1}, Landroid/graphics/Paint;->setStrokeWidth(F)V

    .line 355
    iget-object v0, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->bounds:Landroid/graphics/RectF;

    iget v1, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->radius:F

    iget-object v2, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->paint:Landroid/graphics/Paint;

    invoke-virtual {p1, v0, v1, v1, v2}, Landroid/graphics/Canvas;->drawRoundRect(Landroid/graphics/RectF;FFLandroid/graphics/Paint;)V

    .line 356
    return-void
.end method

.method setBlurBitmap(Landroid/graphics/Bitmap;)V
    .locals 0

    .line 327
    iput-object p1, p0, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->blurBitmap:Landroid/graphics/Bitmap;

    .line 328
    invoke-virtual {p0}, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;->postInvalidate()V

    .line 329
    return-void
.end method
