.class final Lio/kamihama/magianative/CNCNDownloadUI$AssetBitmapLoader;
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
    name = "AssetBitmapLoader"
.end annotation


# instance fields
.field private final act:Landroid/app/Activity;

.field private final assetPath:Ljava/lang/String;

.field private final target:Landroid/widget/ImageView;


# direct methods
.method constructor <init>(Landroid/app/Activity;Ljava/lang/String;Landroid/widget/ImageView;)V
    .locals 0

    .line 408
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 409
    iput-object p1, p0, Lio/kamihama/magianative/CNCNDownloadUI$AssetBitmapLoader;->act:Landroid/app/Activity;

    iput-object p2, p0, Lio/kamihama/magianative/CNCNDownloadUI$AssetBitmapLoader;->assetPath:Ljava/lang/String;

    iput-object p3, p0, Lio/kamihama/magianative/CNCNDownloadUI$AssetBitmapLoader;->target:Landroid/widget/ImageView;

    .line 410
    return-void
.end method


# virtual methods
.method public run()V
    .locals 4

    .line 414
    :try_start_0
    iget-object v0, p0, Lio/kamihama/magianative/CNCNDownloadUI$AssetBitmapLoader;->act:Landroid/app/Activity;

    invoke-virtual {v0}, Landroid/app/Activity;->getAssets()Landroid/content/res/AssetManager;

    move-result-object v0

    iget-object v1, p0, Lio/kamihama/magianative/CNCNDownloadUI$AssetBitmapLoader;->assetPath:Ljava/lang/String;

    invoke-virtual {v0, v1}, Landroid/content/res/AssetManager;->open(Ljava/lang/String;)Ljava/io/InputStream;

    move-result-object v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_3

    .line 416
    :try_start_1
    invoke-static {v0}, Landroid/graphics/BitmapFactory;->decodeStream(Ljava/io/InputStream;)Landroid/graphics/Bitmap;

    move-result-object v1
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    .line 418
    :try_start_2
    invoke-virtual {v0}, Ljava/io/InputStream;->close()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    goto :goto_0

    :catchall_0
    move-exception v0

    .line 419
    nop

    .line 420
    :goto_0
    if-nez v1, :cond_0

    return-void

    .line 421
    :cond_0
    :try_start_3
    iget-object v0, p0, Lio/kamihama/magianative/CNCNDownloadUI$AssetBitmapLoader;->act:Landroid/app/Activity;

    new-instance v2, Lio/kamihama/magianative/CNCNDownloadUI$ApplyBitmap;

    iget-object v3, p0, Lio/kamihama/magianative/CNCNDownloadUI$AssetBitmapLoader;->target:Landroid/widget/ImageView;

    invoke-direct {v2, v3, v1}, Lio/kamihama/magianative/CNCNDownloadUI$ApplyBitmap;-><init>(Landroid/widget/ImageView;Landroid/graphics/Bitmap;)V

    invoke-virtual {v0, v2}, Landroid/app/Activity;->runOnUiThread(Ljava/lang/Runnable;)V
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_3

    goto :goto_2

    .line 418
    :catchall_1
    move-exception v1

    :try_start_4
    invoke-virtual {v0}, Ljava/io/InputStream;->close()V
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_2

    goto :goto_1

    :catchall_2
    move-exception v0

    .line 419
    :goto_1
    :try_start_5
    throw v1
    :try_end_5
    .catchall {:try_start_5 .. :try_end_5} :catchall_3

    .line 422
    :catchall_3
    move-exception v0

    :goto_2
    nop

    .line 423
    return-void
.end method
