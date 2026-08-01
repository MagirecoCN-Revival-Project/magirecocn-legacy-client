.class final Lio/kamihama/magianative/CNCNDownloadUI$ApplyBitmap;
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
    name = "ApplyBitmap"
.end annotation


# instance fields
.field private final bitmap:Landroid/graphics/Bitmap;

.field private final target:Landroid/widget/ImageView;


# direct methods
.method constructor <init>(Landroid/widget/ImageView;Landroid/graphics/Bitmap;)V
    .locals 0

    .line 390
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lio/kamihama/magianative/CNCNDownloadUI$ApplyBitmap;->target:Landroid/widget/ImageView;

    iput-object p2, p0, Lio/kamihama/magianative/CNCNDownloadUI$ApplyBitmap;->bitmap:Landroid/graphics/Bitmap;

    return-void
.end method


# virtual methods
.method public run()V
    .locals 2

    .line 392
    :try_start_0
    iget-object v0, p0, Lio/kamihama/magianative/CNCNDownloadUI$ApplyBitmap;->target:Landroid/widget/ImageView;

    iget-object v1, p0, Lio/kamihama/magianative/CNCNDownloadUI$ApplyBitmap;->bitmap:Landroid/graphics/Bitmap;

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setImageBitmap(Landroid/graphics/Bitmap;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    goto :goto_0

    :catchall_0
    move-exception v0

    .line 393
    :goto_0
    return-void
.end method
