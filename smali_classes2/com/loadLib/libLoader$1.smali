.class final Lcom/loadLib/libLoader$1;
.super Ljava/lang/Object;
.source "libLoader.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/JvRuit/Ldr;->loadLib()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = null
.end annotation


# direct methods
.method constructor <init>()V
    .locals 0

    .prologue
    .line 14
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 0

    # ---- 复兴计划补丁：不再加载 libuwasa.so ----
    # libuwasa 是英文汉化组（Kamihama）的一改产物：它把引擎的资源下载地址
    # 改指 Totentanz，同时按**英文行宽**重排剧情文本与对话框几何。后者在中文
    # 汉化下是净损害。端点重定向已由 libMagiaLegacy.so 按逆向规格重新实现，
    # 排版那套一概不要，故整库停用。
    # 加载点见 Cocos2dxActivity.onLoadNativeLibraries。
    return-void
.end method
