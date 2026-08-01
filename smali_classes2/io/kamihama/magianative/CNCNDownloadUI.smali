.class public Lio/kamihama/magianative/CNCNDownloadUI;
.super Ljava/lang/Object;
.source "CNCNDownloadUI.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lio/kamihama/magianative/CNCNDownloadUI$AssetBitmapLoader;,
        Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;,
        Lio/kamihama/magianative/CNCNDownloadUI$CreditLinkClick;,
        Lio/kamihama/magianative/CNCNDownloadUI$CopyLogClick;,
        Lio/kamihama/magianative/CNCNDownloadUI$LogScrollWatcher;,
        Lio/kamihama/magianative/CNCNDownloadUI$DotView;,
        Lio/kamihama/magianative/CNCNDownloadUI$RetryClick;,
        Lio/kamihama/magianative/CNCNDownloadUI$SlotViews;,
        Lio/kamihama/magianative/CNCNDownloadUI$ScrollToBottom;,
        Lio/kamihama/magianative/CNCNDownloadUI$RenderLog;,
        Lio/kamihama/magianative/CNCNDownloadUI$EnsureVisible;,
        Lio/kamihama/magianative/CNCNDownloadUI$HideRunnable;,
        Lio/kamihama/magianative/CNCNDownloadUI$UpdateRunnable;,
        Lio/kamihama/magianative/CNCNDownloadUI$CreateUIRunnable;,
        Lio/kamihama/magianative/CNCNDownloadUI$LogChanged;,
        Lio/kamihama/magianative/CNCNDownloadUI$ApplyBitmap;
    }
.end annotation


# static fields
.field private static final BG_ASSET:Ljava/lang/String; = "cnv/background_light.png"

.field private static COLOR_ACCENT:I = 0x0

.field private static COLOR_ACCENT2:I = 0x0

.field private static COLOR_BAR_BG:I = 0x0

.field private static COLOR_CARD_STK:I = 0x0

.field private static COLOR_DIM:I = 0x0

.field private static COLOR_GLASS:I = 0x0

.field private static COLOR_GLASS_STK:I = 0x0

.field private static COLOR_LINK:I = 0x0

.field private static COLOR_LOG_PANEL_BG:I = 0x0

.field private static COLOR_LOG_PANEL_TEXT:I = 0x0

.field private static COLOR_LOG_PILL:I = 0x0

.field private static COLOR_SUB:I = 0x0

.field private static COLOR_TEXT:I = 0x0

.field private static final CONFIRM_WINDOW_MS:J = 0x1770L

.field private static final CONTRIB_PALETTE:[I

.field private static final CREDIT_KINDS:[I

.field private static final CREDIT_LINK_SPANS:[Ljava/lang/String;

.field private static final CREDIT_TEXTS:[Ljava/lang/String;

.field private static final CREDIT_URLS:[Ljava/lang/String;

.field private static final FILE_COUNT:I = 0xf

.field public static final FILE_NAMES:[Ljava/lang/String;

.field public static final FILE_URLS:[Ljava/lang/String;

.field private static final FOOTER_CREDIT:Ljava/lang/String; = "\u6838\u5fc3\u5f00\u53d1: B\u7ad9 @MadeInMagius\u3010B\u7ad9xhs tx\u540c\u540d\u3011 | \u56fd\u5185\u52a0\u901f+\u4fee\u590d\uff1a@PhotonFlow | \u5982\u679c\u9700\u8981\u8054\u7cfb\u8bf7\u5148b\u7ad9\u79c1\u4fe1\uff0c\u4f1a\u63d0\u4f9b\u7fa4\u804a | \u8be5\u6e38\u620f\u652f\u6301\u540e\u7eed\u5267\u60c5\u66f4\u65b0"

.field private static final KIND_HEAD:I = 0x1

.field private static final KIND_ITEM:I = 0x2

.field private static final KIND_SUB:I = 0x3

.field private static final KIND_TITLE:I = 0x0

.field private static final LOGO_ASSET:Ljava/lang/String; = "cnv/logo.png"

.field private static final LOG_DIRTY:Ljava/util/concurrent/atomic/AtomicBoolean;

.field private static final LOG_REFRESH_MS:J = 0xfaL

.field private static final PANEL_LOG_LINES:I = 0x12c

.field private static final PREFS_NAME:Ljava/lang/String; = "cnv_bootstrap_ui"

.field private static final PREF_DARK_MODE:Ljava/lang/String; = "dark_mode"

.field private static final URL_GITHUB:Ljava/lang/String; = "https://github.com/MagirecoCN-Revival-Project"

.field private static darkMode:Z

.field public static decorView:Landroid/view/ViewGroup;

.field private static volatile detailText:Ljava/lang/String;

.field public static fileDownloaded:[F

.field public static fileProgress:[I

.field public static fileSize:[F

.field public static fileSpeed:[F

.field public static fileStatus:[I

.field private static githubChipBg:Landroid/graphics/drawable/GradientDrawable;

.field private static hostActivity:Landroid/app/Activity;

.field public static isShowing:Z

.field public static lastUpdateTime:J

.field private static logAutoScroll:Z

.field private static logModal:Landroid/widget/FrameLayout;

.field private static logPillBg:Landroid/graphics/drawable/GradientDrawable;

.field public static overlayView:Landroid/widget/FrameLayout;

.field private static pendingAtMs:J

.field private static pendingUrl:Ljava/lang/String;

.field private static volatile phaseText:Ljava/lang/String;

.field public static progressBarOverall:Landroid/widget/ProgressBar;

.field private static slotContainer:Landroid/widget/LinearLayout;

.field private static final slotList:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Lio/kamihama/magianative/CNCNDownloadUI$SlotViews;",
            ">;"
        }
    .end annotation
.end field

.field private static themeChipBg:Landroid/graphics/drawable/GradientDrawable;

.field public static tvLog:Landroid/widget/TextView;

.field public static tvSpeed:Landroid/widget/TextView;

.field public static uiHandler:Landroid/os/Handler;

.field private static vAggregate:Landroid/widget/TextView;

.field private static vContribList:Landroid/widget/LinearLayout;

.field private static vGitHubChip:Landroid/widget/TextView;

.field private static vLogPill:Landroid/widget/TextView;

.field private static vLogScroll:Landroid/widget/ScrollView;

.field private static vOverallText:Landroid/widget/TextView;

.field private static vPhase:Landroid/widget/TextView;

.field private static vStatus:Landroid/widget/TextView;

.field private static vThemeChip:Landroid/widget/TextView;


# direct methods
.method static constructor <clinit>()V
    .locals 16

    .line 71
    const-string v0, "https://assets.magireco.top/cn_base_00_db.zip"

    const-string v1, "https://assets.magireco.top/cn_base_01_json.zip"

    const-string v2, "https://assets.magireco.top/cn_base_02.zip"

    const-string v3, "https://assets.magireco.top/cn_base_03.zip"

    const-string v4, "https://assets.magireco.top/cn_base_04.zip"

    const-string v5, "https://assets.magireco.top/cn_base_05.zip"

    const-string v6, "https://assets.magireco.top/cn_base_06.zip"

    const-string v7, "https://assets.magireco.top/cn_magica_resource.zip"

    const-string v8, "https://assets.magireco.top/cn_scenario_img.zip"

    const-string v9, "https://assets.magireco.top/cn_voice_01.zip"

    const-string v10, "https://assets.magireco.top/cn_voice_02_done.zip"

    const-string v11, "https://assets.magireco.top/cn_js_update.zip"

    const-string v12, "https://assets.magireco.top/movie.zip"

    const-string v13, "https://assets.magireco.top/movie2.zip"

    const-string v14, "https://assets.magireco.top/cn_scenario_update.zip"

    filled-new-array/range {v0 .. v14}, [Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->FILE_URLS:[Ljava/lang/String;

    .line 89
    const-string v1, "cn_base_00_db.zip"

    const-string v2, "cn_base_01_json.zip"

    const-string v3, "cn_base_02.zip"

    const-string v4, "cn_base_03.zip"

    const-string v5, "cn_base_04.zip"

    const-string v6, "cn_base_05.zip"

    const-string v7, "cn_base_06.zip"

    const-string v8, "cn_magica_resource.zip"

    const-string v9, "cn_scenario_img.zip"

    const-string v10, "cn_voice_01.zip"

    const-string v11, "cn_voice_02_done.zip"

    const-string v12, "cn_js_update.zip"

    const-string v13, "movie.zip"

    const-string v14, "movie2.zip"

    const-string v15, "cn_scenario_update.zip"

    filled-new-array/range {v1 .. v15}, [Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->FILE_NAMES:[Ljava/lang/String;

    .line 97
    const/16 v0, 0xf

    new-array v1, v0, [I

    sput-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->fileStatus:[I

    .line 98
    new-array v1, v0, [I

    sput-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->fileProgress:[I

    .line 99
    new-array v1, v0, [F

    sput-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->fileSize:[F

    .line 100
    new-array v1, v0, [F

    sput-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->fileSpeed:[F

    .line 101
    new-array v0, v0, [F

    sput-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->fileDownloaded:[F

    .line 122
    const-string v0, "\u51c6\u5907\u4e2d"

    sput-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->phaseText:Ljava/lang/String;

    .line 123
    const-string v0, "\u6b63\u5728\u521d\u59cb\u5316\u4e0b\u8f7d\u5668\u2026"

    sput-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->detailText:Ljava/lang/String;

    .line 139
    const/4 v0, 0x0

    sput-boolean v0, Lio/kamihama/magianative/CNCNDownloadUI;->darkMode:Z

    .line 174
    const/16 v1, 0x8

    new-array v1, v1, [I

    fill-array-data v1, :array_0

    sput-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->CONTRIB_PALETTE:[I

    .line 186
    const/16 v1, 0xd

    new-array v1, v1, [I

    fill-array-data v1, :array_1

    sput-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->CREDIT_KINDS:[I

    .line 191
    const-string v2, "\u9b54\u6cd5\u7eaa\u5f55Totentanz\u4e2d\u6587\u5316"

    const-string v3, "\u3010\u6838\u5fc3\u9006\u5411\u5f00\u53d1\u3011MadeInMagius\u3010B\u7ad9ID\u3011"

    const-string v4, "(\u72ec\u7acb\u5b8c\u6210\u6c49\u5316\u5f15\u64ce\u4ee5\u53ca\u4e0b\u8f7d\u7cfb\u7edf\u548c\u65e5\u670d\u56fd\u670d\u8d44\u6e90\u5408\u5e76)"

    const-string v5, "\u5176\u4ed6\u4e2a\u4eba\u7f51\u7ad9"

    const-string v6, "magireader.pages.dev\u3010\u9b54\u6cd5\u7eaa\u5f55\u5267\u60c5\u4e2d\u65e5\u53cc\u8bed\u9605\u8bfb\u7f51\u7ad9\u3011"

    const-string v7, "magiaexedralive2dviewer.pages.dev\u3010MagiaExedra\u548c\u9b54\u6cd5\u7eaa\u5f55Live2D\u7f51\u7ad9\u3011"

    const-string v8, "magireco-call-search-cn.pages.dev\u3010\u9b54\u6cd5\u5c11\u5973\u79f0\u547c\u5173\u7cfb\u641c\u7d22\u4e0e\u8eab\u9ad8\u5bf9\u6bd4\u7f51\u7ad9\u3011"

    const-string v9, "\u3010\u534f\u52a9\u4e0e\u9e23\u8c22\u3011"

    const-string v10, "\u56fd\u670d\u6587\u4ef6\u4e4b\u5916\u7684\u7ffb\u8bd1\u548c\u6821\u5bf9\uff1a\u6c34\u94f6h2oag\u3010\u9605\u8bfb\u5668\u7f51\u7ad9\u4e3a\u4e3b\uff0c\u8d44\u6e90\u5df2\u540c\u6b65\u81f3\u6e38\u620f\u3011"

    const-string v11, "\u4e0b\u8f7d\u52a0\u901f\u53ca\u8d44\u6e90\u81ea\u52a8\u5316\u63a8\u9001\uff1aCyberNova"

    const-string v12, "\u56fd\u670d\u6570\u636e\u7559\u5b58\uff1asegfault"

    const-string v13, "\u9879\u76ee\u5b98\u7f51\uff1awww.magireco.top\u3010\u901a\u5f80\u5176\u4ed6\u4e2a\u4eba\u7f51\u7ad9\u548c\u63d0\u4f9b\u8054\u7cfb\u65b9\u5f0f\u3011"

    const-string v14, "bilibili\u89c6\u9891\u6559\u7a0b\uff1aBV1faRiBBExk"

    filled-new-array/range {v2 .. v14}, [Ljava/lang/String;

    move-result-object v1

    sput-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->CREDIT_TEXTS:[Ljava/lang/String;

    .line 213
    const-string v2, ""

    const-string v3, "https://b23.tv/aNjcz1p"

    const-string v4, ""

    const-string v5, ""

    const-string v6, "https://magireader.pages.dev"

    const-string v7, "https://magiaexedralive2dviewer.pages.dev"

    const-string v8, "https://magireco-call-search-cn.pages.dev"

    const-string v9, ""

    const-string v10, "https://b23.tv/ovvbrNw"

    const-string v11, "https://b23.tv/9vyRcI8"

    const-string v12, "https://b23.tv/xjXW9DI"

    const-string v13, "https://www.magireco.top"

    const-string v14, "https://www.bilibili.com/video/BV1faRiBBExk"

    filled-new-array/range {v2 .. v14}, [Ljava/lang/String;

    move-result-object v1

    sput-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->CREDIT_URLS:[Ljava/lang/String;

    .line 236
    const-string v2, ""

    const-string v3, "MadeInMagius"

    const-string v4, ""

    const-string v5, ""

    const-string v6, "magireader.pages.dev"

    const-string v7, "magiaexedralive2dviewer.pages.dev"

    const-string v8, "magireco-call-search-cn.pages.dev"

    const-string v9, ""

    const-string v10, "\u6c34\u94f6h2oag"

    const-string v11, "CyberNova"

    const-string v12, "segfault"

    const-string v13, "www.magireco.top"

    const-string v14, "BV1faRiBBExk"

    filled-new-array/range {v2 .. v14}, [Ljava/lang/String;

    move-result-object v1

    sput-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->CREDIT_LINK_SPANS:[Ljava/lang/String;

    .line 264
    const/4 v1, 0x0

    sput-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->pendingUrl:Ljava/lang/String;

    .line 266
    const-wide/16 v1, 0x0

    sput-wide v1, Lio/kamihama/magianative/CNCNDownloadUI;->pendingAtMs:J

    .line 301
    new-instance v1, Ljava/util/ArrayList;

    invoke-direct {v1}, Ljava/util/ArrayList;-><init>()V

    sput-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->slotList:Ljava/util/List;

    .line 1082
    new-instance v1, Ljava/util/concurrent/atomic/AtomicBoolean;

    invoke-direct {v1, v0}, Ljava/util/concurrent/atomic/AtomicBoolean;-><init>(Z)V

    sput-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->LOG_DIRTY:Ljava/util/concurrent/atomic/AtomicBoolean;

    .line 1113
    const/4 v0, 0x1

    sput-boolean v0, Lio/kamihama/magianative/CNCNDownloadUI;->logAutoScroll:Z

    return-void

    :array_0
    .array-data 4
        -0xc28401
        -0x744786
        -0x199860
        -0xb0481a
        -0xd59a6
        -0x647301
        -0xad3848
        -0x1a8c8d
    .end array-data

    :array_1
    .array-data 4
        0x0
        0x2
        0x3
        0x1
        0x2
        0x2
        0x2
        0x1
        0x2
        0x2
        0x2
        0x2
        0x2
    .end array-data
.end method

.method public constructor <init>()V
    .locals 0

    .line 54
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static synthetic access$000()V
    .locals 0

    .line 54
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->openLogModal()V

    return-void
.end method

.method static synthetic access$100(Landroid/app/Activity;)V
    .locals 0

    .line 54
    invoke-static {p0}, Lio/kamihama/magianative/CNCNDownloadUI;->toggleTheme(Landroid/app/Activity;)V

    return-void
.end method

.method static synthetic access$1000()V
    .locals 0

    .line 54
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->scheduleLogRefresh()V

    return-void
.end method

.method static synthetic access$1100()Ljava/util/concurrent/atomic/AtomicBoolean;
    .locals 1

    .line 54
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->LOG_DIRTY:Ljava/util/concurrent/atomic/AtomicBoolean;

    return-object v0
.end method

.method static synthetic access$1200()V
    .locals 0

    .line 54
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->renderLogModal()V

    return-void
.end method

.method static synthetic access$1300()Z
    .locals 1

    .line 54
    sget-boolean v0, Lio/kamihama/magianative/CNCNDownloadUI;->logAutoScroll:Z

    return v0
.end method

.method static synthetic access$1302(Z)Z
    .locals 0

    .line 54
    sput-boolean p0, Lio/kamihama/magianative/CNCNDownloadUI;->logAutoScroll:Z

    return p0
.end method

.method static synthetic access$1400()Landroid/widget/ScrollView;
    .locals 1

    .line 54
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->vLogScroll:Landroid/widget/ScrollView;

    return-object v0
.end method

.method static synthetic access$1402(Landroid/widget/ScrollView;)Landroid/widget/ScrollView;
    .locals 0

    .line 54
    sput-object p0, Lio/kamihama/magianative/CNCNDownloadUI;->vLogScroll:Landroid/widget/ScrollView;

    return-object p0
.end method

.method static synthetic access$1500(I)I
    .locals 0

    .line 54
    invoke-static {p0}, Lio/kamihama/magianative/CNCNDownloadUI;->dpStatic(I)I

    move-result p0

    return p0
.end method

.method static synthetic access$1600()Landroid/app/Activity;
    .locals 1

    .line 54
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->hostActivity:Landroid/app/Activity;

    return-object v0
.end method

.method static synthetic access$1602(Landroid/app/Activity;)Landroid/app/Activity;
    .locals 0

    .line 54
    sput-object p0, Lio/kamihama/magianative/CNCNDownloadUI;->hostActivity:Landroid/app/Activity;

    return-object p0
.end method

.method static synthetic access$1700()Z
    .locals 1

    .line 54
    sget-boolean v0, Lio/kamihama/magianative/CNCNDownloadUI;->darkMode:Z

    return v0
.end method

.method static synthetic access$1702(Z)Z
    .locals 0

    .line 54
    sput-boolean p0, Lio/kamihama/magianative/CNCNDownloadUI;->darkMode:Z

    return p0
.end method

.method static synthetic access$1800(Z)V
    .locals 0

    .line 54
    invoke-static {p0}, Lio/kamihama/magianative/CNCNDownloadUI;->loadPalette(Z)V

    return-void
.end method

.method static synthetic access$1900(Landroid/app/Activity;)Landroid/widget/FrameLayout;
    .locals 0

    .line 54
    invoke-static {p0}, Lio/kamihama/magianative/CNCNDownloadUI;->buildOverlay(Landroid/app/Activity;)Landroid/widget/FrameLayout;

    move-result-object p0

    return-object p0
.end method

.method static synthetic access$200()V
    .locals 0

    .line 54
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->closeLogModal()V

    return-void
.end method

.method static synthetic access$2000()V
    .locals 0

    .line 54
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->renderAll()V

    return-void
.end method

.method static synthetic access$2202(Landroid/widget/TextView;)Landroid/widget/TextView;
    .locals 0

    .line 54
    sput-object p0, Lio/kamihama/magianative/CNCNDownloadUI;->vPhase:Landroid/widget/TextView;

    return-object p0
.end method

.method static synthetic access$2302(Landroid/widget/TextView;)Landroid/widget/TextView;
    .locals 0

    .line 54
    sput-object p0, Lio/kamihama/magianative/CNCNDownloadUI;->vStatus:Landroid/widget/TextView;

    return-object p0
.end method

.method static synthetic access$2402(Landroid/widget/TextView;)Landroid/widget/TextView;
    .locals 0

    .line 54
    sput-object p0, Lio/kamihama/magianative/CNCNDownloadUI;->vAggregate:Landroid/widget/TextView;

    return-object p0
.end method

.method static synthetic access$2502(Landroid/widget/TextView;)Landroid/widget/TextView;
    .locals 0

    .line 54
    sput-object p0, Lio/kamihama/magianative/CNCNDownloadUI;->vOverallText:Landroid/widget/TextView;

    return-object p0
.end method

.method static synthetic access$2602(Landroid/widget/LinearLayout;)Landroid/widget/LinearLayout;
    .locals 0

    .line 54
    sput-object p0, Lio/kamihama/magianative/CNCNDownloadUI;->slotContainer:Landroid/widget/LinearLayout;

    return-object p0
.end method

.method static synthetic access$2702(Landroid/widget/LinearLayout;)Landroid/widget/LinearLayout;
    .locals 0

    .line 54
    sput-object p0, Lio/kamihama/magianative/CNCNDownloadUI;->vContribList:Landroid/widget/LinearLayout;

    return-object p0
.end method

.method static synthetic access$2802(Landroid/widget/TextView;)Landroid/widget/TextView;
    .locals 0

    .line 54
    sput-object p0, Lio/kamihama/magianative/CNCNDownloadUI;->vThemeChip:Landroid/widget/TextView;

    return-object p0
.end method

.method static synthetic access$2902(Landroid/widget/TextView;)Landroid/widget/TextView;
    .locals 0

    .line 54
    sput-object p0, Lio/kamihama/magianative/CNCNDownloadUI;->vLogPill:Landroid/widget/TextView;

    return-object p0
.end method

.method static synthetic access$3002(Landroid/widget/FrameLayout;)Landroid/widget/FrameLayout;
    .locals 0

    .line 54
    sput-object p0, Lio/kamihama/magianative/CNCNDownloadUI;->logModal:Landroid/widget/FrameLayout;

    return-object p0
.end method

.method static synthetic access$3102(Landroid/graphics/drawable/GradientDrawable;)Landroid/graphics/drawable/GradientDrawable;
    .locals 0

    .line 54
    sput-object p0, Lio/kamihama/magianative/CNCNDownloadUI;->themeChipBg:Landroid/graphics/drawable/GradientDrawable;

    return-object p0
.end method

.method static synthetic access$3202(Landroid/graphics/drawable/GradientDrawable;)Landroid/graphics/drawable/GradientDrawable;
    .locals 0

    .line 54
    sput-object p0, Lio/kamihama/magianative/CNCNDownloadUI;->logPillBg:Landroid/graphics/drawable/GradientDrawable;

    return-object p0
.end method

.method static synthetic access$3300()Ljava/util/List;
    .locals 1

    .line 54
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->slotList:Ljava/util/List;

    return-object v0
.end method

.method static synthetic access$400()Ljava/lang/String;
    .locals 1

    .line 54
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->pendingUrl:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$402(Ljava/lang/String;)Ljava/lang/String;
    .locals 0

    .line 54
    sput-object p0, Lio/kamihama/magianative/CNCNDownloadUI;->pendingUrl:Ljava/lang/String;

    return-object p0
.end method

.method static synthetic access$500()J
    .locals 2

    .line 54
    sget-wide v0, Lio/kamihama/magianative/CNCNDownloadUI;->pendingAtMs:J

    return-wide v0
.end method

.method static synthetic access$502(J)J
    .locals 0

    .line 54
    sput-wide p0, Lio/kamihama/magianative/CNCNDownloadUI;->pendingAtMs:J

    return-wide p0
.end method

.method static synthetic access$600(Landroid/app/Activity;Ljava/lang/String;)V
    .locals 0

    .line 54
    invoke-static {p0, p1}, Lio/kamihama/magianative/CNCNDownloadUI;->toast(Landroid/app/Activity;Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$700(Z)Ljava/lang/String;
    .locals 0

    .line 54
    invoke-static {p0}, Lio/kamihama/magianative/CNCNDownloadUI;->composeLogText(Z)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method private static buildOverlay(Landroid/app/Activity;)Landroid/widget/FrameLayout;
    .locals 16

    .line 431
    move-object/from16 v0, p0

    new-instance v1, Landroid/widget/FrameLayout;

    invoke-direct {v1, v0}, Landroid/widget/FrameLayout;-><init>(Landroid/content/Context;)V

    .line 432
    const/4 v2, 0x1

    invoke-virtual {v1, v2}, Landroid/widget/FrameLayout;->setClickable(Z)V

    .line 435
    new-instance v3, Landroid/widget/ImageView;

    invoke-direct {v3, v0}, Landroid/widget/ImageView;-><init>(Landroid/content/Context;)V

    .line 436
    sget-object v4, Landroid/widget/ImageView$ScaleType;->CENTER_CROP:Landroid/widget/ImageView$ScaleType;

    invoke-virtual {v3, v4}, Landroid/widget/ImageView;->setScaleType(Landroid/widget/ImageView$ScaleType;)V

    .line 438
    sget-boolean v4, Lio/kamihama/magianative/CNCNDownloadUI;->darkMode:Z

    if-eqz v4, :cond_0

    const v4, -0xeaf1de

    goto :goto_0

    :cond_0
    const v4, -0xc160b

    :goto_0
    invoke-virtual {v3, v4}, Landroid/widget/ImageView;->setBackgroundColor(I)V

    .line 439
    const-string v4, "cnv/background_light.png"

    invoke-static {v0, v4, v3}, Lio/kamihama/magianative/CNCNDownloadUI;->loadBitmapFromAssets(Landroid/app/Activity;Ljava/lang/String;Landroid/widget/ImageView;)V

    .line 440
    sget-boolean v4, Lio/kamihama/magianative/CNCNDownloadUI;->darkMode:Z

    if-eqz v4, :cond_1

    const/high16 v4, -0x56000000

    sget-object v5, Landroid/graphics/PorterDuff$Mode;->SRC_ATOP:Landroid/graphics/PorterDuff$Mode;

    invoke-virtual {v3, v4, v5}, Landroid/widget/ImageView;->setColorFilter(ILandroid/graphics/PorterDuff$Mode;)V

    .line 441
    :cond_1
    new-instance v4, Landroid/widget/FrameLayout$LayoutParams;

    const/4 v5, -0x1

    invoke-direct {v4, v5, v5}, Landroid/widget/FrameLayout$LayoutParams;-><init>(II)V

    invoke-virtual {v1, v3, v4}, Landroid/widget/FrameLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 446
    new-instance v3, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;

    sget v4, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_GLASS:I

    sget v6, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_GLASS_STK:I

    .line 447
    const/16 v7, 0x14

    invoke-static {v0, v7}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v8

    int-to-float v8, v8

    invoke-direct {v3, v0, v4, v6, v8}, Lio/kamihama/magianative/CNCNDownloadUI$GlassPanelView;-><init>(Landroid/content/Context;IIF)V

    .line 448
    new-instance v4, Landroid/widget/FrameLayout$LayoutParams;

    invoke-direct {v4, v5, v5}, Landroid/widget/FrameLayout$LayoutParams;-><init>(II)V

    .line 451
    const/16 v6, 0xe

    invoke-static {v0, v6}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v8

    iput v8, v4, Landroid/widget/FrameLayout$LayoutParams;->leftMargin:I

    .line 452
    invoke-static {v0, v6}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v8

    iput v8, v4, Landroid/widget/FrameLayout$LayoutParams;->rightMargin:I

    .line 453
    const/16 v8, 0x34

    invoke-static {v0, v8}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v9

    iput v9, v4, Landroid/widget/FrameLayout$LayoutParams;->topMargin:I

    .line 454
    const/16 v9, 0x28

    invoke-static {v0, v9}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v10

    iput v10, v4, Landroid/widget/FrameLayout$LayoutParams;->bottomMargin:I

    .line 455
    invoke-virtual {v1, v3, v4}, Landroid/widget/FrameLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 458
    new-instance v3, Landroid/widget/LinearLayout;

    invoke-direct {v3, v0}, Landroid/widget/LinearLayout;-><init>(Landroid/content/Context;)V

    .line 459
    const/4 v4, 0x0

    invoke-virtual {v3, v4}, Landroid/widget/LinearLayout;->setOrientation(I)V

    .line 460
    new-instance v10, Landroid/widget/FrameLayout$LayoutParams;

    invoke-direct {v10, v5, v5}, Landroid/widget/FrameLayout$LayoutParams;-><init>(II)V

    .line 463
    invoke-static {v0, v6}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v11

    invoke-static {v0, v6}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v12

    add-int/2addr v11, v12

    iput v11, v10, Landroid/widget/FrameLayout$LayoutParams;->leftMargin:I

    .line 464
    invoke-static {v0, v6}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v11

    invoke-static {v0, v6}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v12

    add-int/2addr v11, v12

    iput v11, v10, Landroid/widget/FrameLayout$LayoutParams;->rightMargin:I

    .line 465
    invoke-static {v0, v8}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v8

    const/16 v11, 0xc

    invoke-static {v0, v11}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v12

    add-int/2addr v8, v12

    iput v8, v10, Landroid/widget/FrameLayout$LayoutParams;->topMargin:I

    .line 466
    invoke-static {v0, v9}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v8

    invoke-static {v0, v11}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v9

    add-int/2addr v8, v9

    iput v8, v10, Landroid/widget/FrameLayout$LayoutParams;->bottomMargin:I

    .line 467
    invoke-virtual {v1, v3, v10}, Landroid/widget/FrameLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 470
    new-instance v8, Landroid/widget/LinearLayout;

    invoke-direct {v8, v0}, Landroid/widget/LinearLayout;-><init>(Landroid/content/Context;)V

    .line 471
    invoke-virtual {v8, v2}, Landroid/widget/LinearLayout;->setOrientation(I)V

    .line 472
    const/4 v9, 0x4

    invoke-static {v0, v9}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v10

    invoke-static {v0, v11}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v12

    invoke-virtual {v8, v10, v4, v12, v4}, Landroid/widget/LinearLayout;->setPadding(IIII)V

    .line 473
    new-instance v10, Landroid/widget/LinearLayout$LayoutParams;

    const v12, 0x3ec28f5c    # 0.38f

    invoke-direct {v10, v4, v5, v12}, Landroid/widget/LinearLayout$LayoutParams;-><init>(IIF)V

    invoke-virtual {v3, v8, v10}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 476
    new-instance v10, Landroid/widget/ImageView;

    invoke-direct {v10, v0}, Landroid/widget/ImageView;-><init>(Landroid/content/Context;)V

    .line 477
    sget-object v12, Landroid/widget/ImageView$ScaleType;->FIT_CENTER:Landroid/widget/ImageView$ScaleType;

    invoke-virtual {v10, v12}, Landroid/widget/ImageView;->setScaleType(Landroid/widget/ImageView$ScaleType;)V

    .line 478
    const-string v12, "cnv/logo.png"

    invoke-static {v0, v12, v10}, Lio/kamihama/magianative/CNCNDownloadUI;->loadBitmapFromAssets(Landroid/app/Activity;Ljava/lang/String;Landroid/widget/ImageView;)V

    .line 479
    new-instance v12, Landroid/widget/LinearLayout$LayoutParams;

    .line 480
    const/16 v13, 0x40

    invoke-static {v0, v13}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v13

    invoke-direct {v12, v5, v13}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    .line 481
    const/16 v13, 0x8

    invoke-static {v0, v13}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v14

    iput v14, v12, Landroid/widget/LinearLayout$LayoutParams;->bottomMargin:I

    .line 482
    invoke-virtual {v8, v10, v12}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 484
    new-instance v10, Landroid/view/View;

    invoke-direct {v10, v0}, Landroid/view/View;-><init>(Landroid/content/Context;)V

    .line 485
    sget v12, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_CARD_STK:I

    invoke-virtual {v10, v12}, Landroid/view/View;->setBackgroundColor(I)V

    .line 486
    new-instance v12, Landroid/widget/LinearLayout$LayoutParams;

    .line 487
    invoke-static {v0, v2}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v14

    invoke-direct {v12, v5, v14}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    .line 488
    invoke-static {v0, v13}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v14

    iput v14, v12, Landroid/widget/LinearLayout$LayoutParams;->bottomMargin:I

    .line 489
    invoke-virtual {v8, v10, v12}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 491
    new-instance v10, Landroid/widget/ScrollView;

    invoke-direct {v10, v0}, Landroid/widget/ScrollView;-><init>(Landroid/content/Context;)V

    .line 492
    invoke-virtual {v10, v2}, Landroid/widget/ScrollView;->setFillViewport(Z)V

    .line 493
    new-instance v12, Landroid/widget/LinearLayout;

    invoke-direct {v12, v0}, Landroid/widget/LinearLayout;-><init>(Landroid/content/Context;)V

    .line 494
    invoke-virtual {v12, v2}, Landroid/widget/LinearLayout;->setOrientation(I)V

    .line 495
    new-instance v14, Landroid/widget/FrameLayout$LayoutParams;

    const/4 v15, -0x2

    invoke-direct {v14, v5, v15}, Landroid/widget/FrameLayout$LayoutParams;-><init>(II)V

    invoke-virtual {v10, v12, v14}, Landroid/widget/ScrollView;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 498
    sput-object v12, Lio/kamihama/magianative/CNCNDownloadUI;->vContribList:Landroid/widget/LinearLayout;

    .line 499
    new-instance v12, Landroid/widget/LinearLayout$LayoutParams;

    const/high16 v14, 0x3f800000    # 1.0f

    invoke-direct {v12, v5, v4, v14}, Landroid/widget/LinearLayout$LayoutParams;-><init>(IIF)V

    invoke-virtual {v8, v10, v12}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 501
    invoke-static/range {p0 .. p0}, Lio/kamihama/magianative/CNCNDownloadUI;->populateContributors(Landroid/app/Activity;)V

    .line 504
    new-instance v8, Landroid/widget/LinearLayout;

    invoke-direct {v8, v0}, Landroid/widget/LinearLayout;-><init>(Landroid/content/Context;)V

    .line 505
    invoke-virtual {v8, v2}, Landroid/widget/LinearLayout;->setOrientation(I)V

    .line 506
    const/16 v10, 0xa

    invoke-static {v0, v10}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v12

    invoke-static {v0, v9}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v6

    invoke-static {v0, v9}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v11

    invoke-static {v0, v9}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v7

    invoke-virtual {v8, v12, v6, v11, v7}, Landroid/widget/LinearLayout;->setPadding(IIII)V

    .line 507
    new-instance v6, Landroid/widget/LinearLayout$LayoutParams;

    const v7, 0x3f1eb852    # 0.62f

    invoke-direct {v6, v4, v5, v7}, Landroid/widget/LinearLayout$LayoutParams;-><init>(IIF)V

    invoke-virtual {v3, v8, v6}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 510
    new-instance v3, Landroid/widget/LinearLayout;

    invoke-direct {v3, v0}, Landroid/widget/LinearLayout;-><init>(Landroid/content/Context;)V

    .line 511
    invoke-virtual {v3, v4}, Landroid/widget/LinearLayout;->setOrientation(I)V

    .line 512
    const/16 v6, 0x10

    invoke-virtual {v3, v6}, Landroid/widget/LinearLayout;->setGravity(I)V

    .line 513
    invoke-static {v0, v9}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v7

    invoke-static {v4, v7}, Lio/kamihama/magianative/CNCNDownloadUI;->lpRow(II)Landroid/widget/LinearLayout$LayoutParams;

    move-result-object v7

    invoke-virtual {v8, v3, v7}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 515
    new-instance v7, Landroid/widget/TextView;

    invoke-direct {v7, v0}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    sput-object v7, Lio/kamihama/magianative/CNCNDownloadUI;->vPhase:Landroid/widget/TextView;

    .line 516
    sget-object v9, Lio/kamihama/magianative/CNCNDownloadUI;->phaseText:Ljava/lang/String;

    invoke-virtual {v7, v9}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 517
    sget-object v7, Lio/kamihama/magianative/CNCNDownloadUI;->vPhase:Landroid/widget/TextView;

    sget v9, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_ACCENT:I

    invoke-virtual {v7, v9}, Landroid/widget/TextView;->setTextColor(I)V

    .line 518
    sget-object v7, Lio/kamihama/magianative/CNCNDownloadUI;->vPhase:Landroid/widget/TextView;

    const/high16 v9, 0x41500000    # 13.0f

    const/4 v11, 0x2

    invoke-virtual {v7, v11, v9}, Landroid/widget/TextView;->setTextSize(IF)V

    .line 519
    sget-object v7, Lio/kamihama/magianative/CNCNDownloadUI;->vPhase:Landroid/widget/TextView;

    invoke-virtual {v7}, Landroid/widget/TextView;->getTypeface()Landroid/graphics/Typeface;

    move-result-object v9

    invoke-virtual {v7, v9, v2}, Landroid/widget/TextView;->setTypeface(Landroid/graphics/Typeface;I)V

    .line 520
    sget-object v7, Lio/kamihama/magianative/CNCNDownloadUI;->vPhase:Landroid/widget/TextView;

    invoke-virtual {v7, v2}, Landroid/widget/TextView;->setSingleLine(Z)V

    .line 521
    sget-object v7, Lio/kamihama/magianative/CNCNDownloadUI;->vPhase:Landroid/widget/TextView;

    sget-object v9, Landroid/text/TextUtils$TruncateAt;->END:Landroid/text/TextUtils$TruncateAt;

    invoke-virtual {v7, v9}, Landroid/widget/TextView;->setEllipsize(Landroid/text/TextUtils$TruncateAt;)V

    .line 522
    sget-object v7, Lio/kamihama/magianative/CNCNDownloadUI;->vPhase:Landroid/widget/TextView;

    new-instance v9, Landroid/widget/LinearLayout$LayoutParams;

    invoke-direct {v9, v4, v15, v14}, Landroid/widget/LinearLayout$LayoutParams;-><init>(IIF)V

    invoke-virtual {v3, v7, v9}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 525
    new-instance v7, Landroid/widget/TextView;

    invoke-direct {v7, v0}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    sput-object v7, Lio/kamihama/magianative/CNCNDownloadUI;->vAggregate:Landroid/widget/TextView;

    .line 526
    const-string v9, ""

    invoke-virtual {v7, v9}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 527
    sget-object v7, Lio/kamihama/magianative/CNCNDownloadUI;->vAggregate:Landroid/widget/TextView;

    sget v12, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_SUB:I

    invoke-virtual {v7, v12}, Landroid/widget/TextView;->setTextColor(I)V

    .line 528
    sget-object v7, Lio/kamihama/magianative/CNCNDownloadUI;->vAggregate:Landroid/widget/TextView;

    const/high16 v12, 0x41300000    # 11.0f

    invoke-virtual {v7, v11, v12}, Landroid/widget/TextView;->setTextSize(IF)V

    .line 529
    sget-object v7, Lio/kamihama/magianative/CNCNDownloadUI;->vAggregate:Landroid/widget/TextView;

    const v10, 0x800005

    invoke-virtual {v7, v10}, Landroid/widget/TextView;->setGravity(I)V

    .line 530
    sget-object v7, Lio/kamihama/magianative/CNCNDownloadUI;->vAggregate:Landroid/widget/TextView;

    new-instance v10, Landroid/widget/LinearLayout$LayoutParams;

    invoke-direct {v10, v15, v15}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    invoke-virtual {v3, v7, v10}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 534
    new-instance v3, Landroid/widget/TextView;

    invoke-direct {v3, v0}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    sput-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vStatus:Landroid/widget/TextView;

    .line 535
    sget-object v7, Lio/kamihama/magianative/CNCNDownloadUI;->detailText:Ljava/lang/String;

    invoke-virtual {v3, v7}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 536
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vStatus:Landroid/widget/TextView;

    sget v7, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_TEXT:I

    invoke-virtual {v3, v7}, Landroid/widget/TextView;->setTextColor(I)V

    .line 537
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vStatus:Landroid/widget/TextView;

    const/high16 v7, 0x41400000    # 12.0f

    invoke-virtual {v3, v11, v7}, Landroid/widget/TextView;->setTextSize(IF)V

    .line 538
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vStatus:Landroid/widget/TextView;

    invoke-virtual {v3, v2}, Landroid/widget/TextView;->setSingleLine(Z)V

    .line 539
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vStatus:Landroid/widget/TextView;

    sget-object v10, Landroid/text/TextUtils$TruncateAt;->MIDDLE:Landroid/text/TextUtils$TruncateAt;

    invoke-virtual {v3, v10}, Landroid/widget/TextView;->setEllipsize(Landroid/text/TextUtils$TruncateAt;)V

    .line 540
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vStatus:Landroid/widget/TextView;

    const/4 v10, 0x6

    invoke-static {v0, v10}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v7

    invoke-static {v4, v7}, Lio/kamihama/magianative/CNCNDownloadUI;->lpRow(II)Landroid/widget/LinearLayout$LayoutParams;

    move-result-object v7

    invoke-virtual {v8, v3, v7}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 542
    new-instance v3, Landroid/widget/ScrollView;

    invoke-direct {v3, v0}, Landroid/widget/ScrollView;-><init>(Landroid/content/Context;)V

    .line 543
    new-instance v7, Landroid/widget/LinearLayout;

    invoke-direct {v7, v0}, Landroid/widget/LinearLayout;-><init>(Landroid/content/Context;)V

    sput-object v7, Lio/kamihama/magianative/CNCNDownloadUI;->slotContainer:Landroid/widget/LinearLayout;

    .line 544
    invoke-virtual {v7, v2}, Landroid/widget/LinearLayout;->setOrientation(I)V

    .line 545
    sget-object v7, Lio/kamihama/magianative/CNCNDownloadUI;->slotContainer:Landroid/widget/LinearLayout;

    new-instance v10, Landroid/widget/FrameLayout$LayoutParams;

    invoke-direct {v10, v5, v15}, Landroid/widget/FrameLayout$LayoutParams;-><init>(II)V

    invoke-virtual {v3, v7, v10}, Landroid/widget/ScrollView;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 548
    new-instance v7, Landroid/widget/LinearLayout$LayoutParams;

    invoke-direct {v7, v5, v4, v14}, Landroid/widget/LinearLayout$LayoutParams;-><init>(IIF)V

    invoke-virtual {v8, v3, v7}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 550
    invoke-static/range {p0 .. p0}, Lio/kamihama/magianative/CNCNDownloadUI;->rebuildSlots(Landroid/app/Activity;)V

    .line 552
    new-instance v3, Landroid/widget/LinearLayout;

    invoke-direct {v3, v0}, Landroid/widget/LinearLayout;-><init>(Landroid/content/Context;)V

    .line 553
    invoke-virtual {v3, v4}, Landroid/widget/LinearLayout;->setOrientation(I)V

    .line 554
    invoke-virtual {v3, v6}, Landroid/widget/LinearLayout;->setGravity(I)V

    .line 555
    invoke-static {v0, v13}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v7

    invoke-static {v0, v11}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v10

    invoke-static {v7, v10}, Lio/kamihama/magianative/CNCNDownloadUI;->lpRow(II)Landroid/widget/LinearLayout$LayoutParams;

    move-result-object v7

    invoke-virtual {v8, v3, v7}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 557
    new-instance v7, Landroid/widget/TextView;

    invoke-direct {v7, v0}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    sput-object v7, Lio/kamihama/magianative/CNCNDownloadUI;->vOverallText:Landroid/widget/TextView;

    .line 558
    const-string v10, "\u603b\u8fdb\u5ea6"

    invoke-virtual {v7, v10}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 559
    sget-object v7, Lio/kamihama/magianative/CNCNDownloadUI;->vOverallText:Landroid/widget/TextView;

    sget v10, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_TEXT:I

    invoke-virtual {v7, v10}, Landroid/widget/TextView;->setTextColor(I)V

    .line 560
    sget-object v7, Lio/kamihama/magianative/CNCNDownloadUI;->vOverallText:Landroid/widget/TextView;

    invoke-virtual {v7, v11, v12}, Landroid/widget/TextView;->setTextSize(IF)V

    .line 561
    sget-object v7, Lio/kamihama/magianative/CNCNDownloadUI;->vOverallText:Landroid/widget/TextView;

    new-instance v10, Landroid/widget/LinearLayout$LayoutParams;

    invoke-direct {v10, v4, v15, v14}, Landroid/widget/LinearLayout$LayoutParams;-><init>(IIF)V

    invoke-virtual {v3, v7, v10}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 564
    new-instance v7, Landroid/widget/TextView;

    invoke-direct {v7, v0}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    sput-object v7, Lio/kamihama/magianative/CNCNDownloadUI;->tvSpeed:Landroid/widget/TextView;

    .line 565
    invoke-virtual {v7, v9}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 566
    sget-object v7, Lio/kamihama/magianative/CNCNDownloadUI;->tvSpeed:Landroid/widget/TextView;

    sget v9, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_SUB:I

    invoke-virtual {v7, v9}, Landroid/widget/TextView;->setTextColor(I)V

    .line 567
    sget-object v7, Lio/kamihama/magianative/CNCNDownloadUI;->tvSpeed:Landroid/widget/TextView;

    invoke-virtual {v7, v11, v12}, Landroid/widget/TextView;->setTextSize(IF)V

    .line 568
    sget-object v7, Lio/kamihama/magianative/CNCNDownloadUI;->tvSpeed:Landroid/widget/TextView;

    const v9, 0x800005

    invoke-virtual {v7, v9}, Landroid/widget/TextView;->setGravity(I)V

    .line 569
    sget-object v7, Lio/kamihama/magianative/CNCNDownloadUI;->tvSpeed:Landroid/widget/TextView;

    new-instance v9, Landroid/widget/LinearLayout$LayoutParams;

    invoke-direct {v9, v15, v15}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    invoke-virtual {v3, v7, v9}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 573
    new-instance v3, Landroid/widget/ProgressBar;

    const v7, 0x1010078

    const/4 v9, 0x0

    invoke-direct {v3, v0, v9, v7}, Landroid/widget/ProgressBar;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    sput-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->progressBarOverall:Landroid/widget/ProgressBar;

    .line 575
    const/16 v7, 0x64

    invoke-virtual {v3, v7}, Landroid/widget/ProgressBar;->setMax(I)V

    .line 576
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->progressBarOverall:Landroid/widget/ProgressBar;

    invoke-virtual {v3, v4}, Landroid/widget/ProgressBar;->setProgress(I)V

    .line 577
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->progressBarOverall:Landroid/widget/ProgressBar;

    sget v7, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_ACCENT:I

    invoke-static {v3, v7}, Lio/kamihama/magianative/CNCNDownloadUI;->tintBar(Landroid/widget/ProgressBar;I)V

    .line 578
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->progressBarOverall:Landroid/widget/ProgressBar;

    new-instance v7, Landroid/widget/LinearLayout$LayoutParams;

    .line 579
    const/16 v10, 0xa

    invoke-static {v0, v10}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v9

    invoke-direct {v7, v5, v9}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    .line 578
    invoke-virtual {v8, v3, v7}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 582
    new-instance v3, Landroid/graphics/drawable/GradientDrawable;

    invoke-direct {v3}, Landroid/graphics/drawable/GradientDrawable;-><init>()V

    sput-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->logPillBg:Landroid/graphics/drawable/GradientDrawable;

    .line 583
    sget v7, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_LOG_PILL:I

    invoke-virtual {v3, v7}, Landroid/graphics/drawable/GradientDrawable;->setColor(I)V

    .line 584
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->logPillBg:Landroid/graphics/drawable/GradientDrawable;

    const/16 v7, 0x14

    invoke-static {v0, v7}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v8

    int-to-float v7, v8

    invoke-virtual {v3, v7}, Landroid/graphics/drawable/GradientDrawable;->setCornerRadius(F)V

    .line 585
    new-instance v3, Landroid/widget/TextView;

    invoke-direct {v3, v0}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    sput-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vLogPill:Landroid/widget/TextView;

    .line 586
    const-string v7, "LOG"

    invoke-virtual {v3, v7}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 587
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vLogPill:Landroid/widget/TextView;

    invoke-virtual {v3, v5}, Landroid/widget/TextView;->setTextColor(I)V

    .line 588
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vLogPill:Landroid/widget/TextView;

    invoke-virtual {v3, v11, v12}, Landroid/widget/TextView;->setTextSize(IF)V

    .line 589
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vLogPill:Landroid/widget/TextView;

    invoke-virtual {v3}, Landroid/widget/TextView;->getTypeface()Landroid/graphics/Typeface;

    move-result-object v7

    invoke-virtual {v3, v7, v2}, Landroid/widget/TextView;->setTypeface(Landroid/graphics/Typeface;I)V

    .line 590
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vLogPill:Landroid/widget/TextView;

    const/16 v7, 0x11

    invoke-virtual {v3, v7}, Landroid/widget/TextView;->setGravity(I)V

    .line 591
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vLogPill:Landroid/widget/TextView;

    const/16 v8, 0xc

    invoke-static {v0, v8}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v9

    const/4 v10, 0x6

    invoke-static {v0, v10}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v14

    invoke-static {v0, v8}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v13

    invoke-static {v0, v10}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v8

    invoke-virtual {v3, v9, v14, v13, v8}, Landroid/widget/TextView;->setPadding(IIII)V

    .line 592
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vLogPill:Landroid/widget/TextView;

    sget-object v8, Lio/kamihama/magianative/CNCNDownloadUI;->logPillBg:Landroid/graphics/drawable/GradientDrawable;

    invoke-virtual {v3, v8}, Landroid/widget/TextView;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 593
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vLogPill:Landroid/widget/TextView;

    new-instance v8, Lio/kamihama/magianative/CNCNDownloadUI$1;

    invoke-direct {v8}, Lio/kamihama/magianative/CNCNDownloadUI$1;-><init>()V

    invoke-virtual {v3, v8}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 596
    new-instance v3, Landroid/widget/FrameLayout$LayoutParams;

    invoke-direct {v3, v15, v15}, Landroid/widget/FrameLayout$LayoutParams;-><init>(II)V

    .line 599
    const v8, 0x800033

    iput v8, v3, Landroid/widget/FrameLayout$LayoutParams;->gravity:I

    .line 600
    const/16 v8, 0xa

    invoke-static {v0, v8}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v9

    iput v9, v3, Landroid/widget/FrameLayout$LayoutParams;->topMargin:I

    .line 601
    const/16 v8, 0xe

    invoke-static {v0, v8}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v9

    iput v9, v3, Landroid/widget/FrameLayout$LayoutParams;->leftMargin:I

    .line 602
    sget-object v8, Lio/kamihama/magianative/CNCNDownloadUI;->vLogPill:Landroid/widget/TextView;

    invoke-virtual {v1, v8, v3}, Landroid/widget/FrameLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 605
    new-instance v3, Landroid/graphics/drawable/GradientDrawable;

    invoke-direct {v3}, Landroid/graphics/drawable/GradientDrawable;-><init>()V

    sput-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->themeChipBg:Landroid/graphics/drawable/GradientDrawable;

    .line 606
    const/16 v8, 0x14

    invoke-static {v0, v8}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v9

    int-to-float v8, v9

    invoke-virtual {v3, v8}, Landroid/graphics/drawable/GradientDrawable;->setCornerRadius(F)V

    .line 607
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->themeChipBg:Landroid/graphics/drawable/GradientDrawable;

    sget-boolean v8, Lio/kamihama/magianative/CNCNDownloadUI;->darkMode:Z

    if-eqz v8, :cond_2

    const v8, -0x33001b60

    goto :goto_1

    :cond_2
    sget v8, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_ACCENT2:I

    :goto_1
    invoke-virtual {v3, v8}, Landroid/graphics/drawable/GradientDrawable;->setColor(I)V

    .line 608
    new-instance v3, Landroid/widget/TextView;

    invoke-direct {v3, v0}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    sput-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vThemeChip:Landroid/widget/TextView;

    .line 609
    sget-boolean v8, Lio/kamihama/magianative/CNCNDownloadUI;->darkMode:Z

    if-eqz v8, :cond_3

    const-string v8, "\u2600  \u4eae\u8272"

    goto :goto_2

    :cond_3
    const-string v8, "\u263e  \u591c\u95f4"

    :goto_2
    invoke-virtual {v3, v8}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 610
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vThemeChip:Landroid/widget/TextView;

    sget-boolean v8, Lio/kamihama/magianative/CNCNDownloadUI;->darkMode:Z

    if-eqz v8, :cond_4

    const v8, -0xd5e5c5

    goto :goto_3

    :cond_4
    const/4 v8, -0x1

    :goto_3
    invoke-virtual {v3, v8}, Landroid/widget/TextView;->setTextColor(I)V

    .line 611
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vThemeChip:Landroid/widget/TextView;

    invoke-virtual {v3, v11, v12}, Landroid/widget/TextView;->setTextSize(IF)V

    .line 612
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vThemeChip:Landroid/widget/TextView;

    invoke-virtual {v3}, Landroid/widget/TextView;->getTypeface()Landroid/graphics/Typeface;

    move-result-object v8

    invoke-virtual {v3, v8, v2}, Landroid/widget/TextView;->setTypeface(Landroid/graphics/Typeface;I)V

    .line 613
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vThemeChip:Landroid/widget/TextView;

    invoke-virtual {v3, v7}, Landroid/widget/TextView;->setGravity(I)V

    .line 614
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vThemeChip:Landroid/widget/TextView;

    const/16 v8, 0xc

    invoke-static {v0, v8}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v9

    const/4 v10, 0x6

    invoke-static {v0, v10}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v13

    invoke-static {v0, v8}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v14

    invoke-static {v0, v10}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v8

    invoke-virtual {v3, v9, v13, v14, v8}, Landroid/widget/TextView;->setPadding(IIII)V

    .line 615
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vThemeChip:Landroid/widget/TextView;

    sget-object v8, Lio/kamihama/magianative/CNCNDownloadUI;->themeChipBg:Landroid/graphics/drawable/GradientDrawable;

    invoke-virtual {v3, v8}, Landroid/widget/TextView;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 616
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vThemeChip:Landroid/widget/TextView;

    new-instance v8, Lio/kamihama/magianative/CNCNDownloadUI$2;

    invoke-direct {v8, v0}, Lio/kamihama/magianative/CNCNDownloadUI$2;-><init>(Landroid/app/Activity;)V

    invoke-virtual {v3, v8}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 620
    new-instance v3, Landroid/graphics/drawable/GradientDrawable;

    invoke-direct {v3}, Landroid/graphics/drawable/GradientDrawable;-><init>()V

    sput-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->githubChipBg:Landroid/graphics/drawable/GradientDrawable;

    .line 621
    const/16 v8, 0x14

    invoke-static {v0, v8}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v9

    int-to-float v8, v9

    invoke-virtual {v3, v8}, Landroid/graphics/drawable/GradientDrawable;->setCornerRadius(F)V

    .line 622
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->githubChipBg:Landroid/graphics/drawable/GradientDrawable;

    sget v8, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_ACCENT2:I

    invoke-virtual {v3, v8}, Landroid/graphics/drawable/GradientDrawable;->setColor(I)V

    .line 623
    new-instance v3, Landroid/widget/TextView;

    invoke-direct {v3, v0}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    sput-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vGitHubChip:Landroid/widget/TextView;

    .line 624
    const-string v8, "</>  GitHub"

    invoke-virtual {v3, v8}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 625
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vGitHubChip:Landroid/widget/TextView;

    invoke-virtual {v3, v5}, Landroid/widget/TextView;->setTextColor(I)V

    .line 626
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vGitHubChip:Landroid/widget/TextView;

    invoke-virtual {v3, v11, v12}, Landroid/widget/TextView;->setTextSize(IF)V

    .line 627
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vGitHubChip:Landroid/widget/TextView;

    invoke-virtual {v3}, Landroid/widget/TextView;->getTypeface()Landroid/graphics/Typeface;

    move-result-object v8

    invoke-virtual {v3, v8, v2}, Landroid/widget/TextView;->setTypeface(Landroid/graphics/Typeface;I)V

    .line 628
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vGitHubChip:Landroid/widget/TextView;

    invoke-virtual {v3, v7}, Landroid/widget/TextView;->setGravity(I)V

    .line 629
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vGitHubChip:Landroid/widget/TextView;

    const/16 v8, 0xc

    invoke-static {v0, v8}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v9

    const/4 v10, 0x6

    invoke-static {v0, v10}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v13

    invoke-static {v0, v8}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v8

    invoke-static {v0, v10}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v14

    invoke-virtual {v3, v9, v13, v8, v14}, Landroid/widget/TextView;->setPadding(IIII)V

    .line 630
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vGitHubChip:Landroid/widget/TextView;

    sget-object v8, Lio/kamihama/magianative/CNCNDownloadUI;->githubChipBg:Landroid/graphics/drawable/GradientDrawable;

    invoke-virtual {v3, v8}, Landroid/widget/TextView;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 631
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->vGitHubChip:Landroid/widget/TextView;

    new-instance v8, Lio/kamihama/magianative/CNCNDownloadUI$CreditLinkClick;

    const-string v9, "https://github.com/MagirecoCN-Revival-Project"

    invoke-direct {v8, v0, v9}, Lio/kamihama/magianative/CNCNDownloadUI$CreditLinkClick;-><init>(Landroid/app/Activity;Ljava/lang/String;)V

    invoke-virtual {v3, v8}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 633
    new-instance v3, Landroid/widget/LinearLayout;

    invoke-direct {v3, v0}, Landroid/widget/LinearLayout;-><init>(Landroid/content/Context;)V

    .line 634
    invoke-virtual {v3, v4}, Landroid/widget/LinearLayout;->setOrientation(I)V

    .line 635
    invoke-virtual {v3, v6}, Landroid/widget/LinearLayout;->setGravity(I)V

    .line 636
    sget-object v8, Lio/kamihama/magianative/CNCNDownloadUI;->vThemeChip:Landroid/widget/TextView;

    new-instance v9, Landroid/widget/LinearLayout$LayoutParams;

    invoke-direct {v9, v15, v15}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    invoke-virtual {v3, v8, v9}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 639
    new-instance v8, Landroid/widget/LinearLayout$LayoutParams;

    invoke-direct {v8, v15, v15}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    .line 642
    const/16 v9, 0x8

    invoke-static {v0, v9}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v10

    iput v10, v8, Landroid/widget/LinearLayout$LayoutParams;->leftMargin:I

    .line 643
    sget-object v9, Lio/kamihama/magianative/CNCNDownloadUI;->vGitHubChip:Landroid/widget/TextView;

    invoke-virtual {v3, v9, v8}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 645
    new-instance v8, Landroid/widget/FrameLayout$LayoutParams;

    invoke-direct {v8, v15, v15}, Landroid/widget/FrameLayout$LayoutParams;-><init>(II)V

    .line 648
    const v9, 0x800035

    iput v9, v8, Landroid/widget/FrameLayout$LayoutParams;->gravity:I

    .line 649
    const/16 v9, 0xa

    invoke-static {v0, v9}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v9

    iput v9, v8, Landroid/widget/FrameLayout$LayoutParams;->topMargin:I

    .line 650
    const/16 v9, 0xe

    invoke-static {v0, v9}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v10

    iput v10, v8, Landroid/widget/FrameLayout$LayoutParams;->rightMargin:I

    .line 651
    invoke-virtual {v1, v3, v8}, Landroid/widget/FrameLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 654
    new-instance v3, Landroid/widget/TextView;

    invoke-direct {v3, v0}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    .line 655
    const-string v8, "\u6838\u5fc3\u5f00\u53d1: B\u7ad9 @MadeInMagius\u3010B\u7ad9xhs tx\u540c\u540d\u3011 | \u56fd\u5185\u52a0\u901f+\u4fee\u590d\uff1a@PhotonFlow | \u5982\u679c\u9700\u8981\u8054\u7cfb\u8bf7\u5148b\u7ad9\u79c1\u4fe1\uff0c\u4f1a\u63d0\u4f9b\u7fa4\u804a | \u8be5\u6e38\u620f\u652f\u6301\u540e\u7eed\u5267\u60c5\u66f4\u65b0"

    invoke-virtual {v3, v8}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 656
    sget v8, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_SUB:I

    invoke-virtual {v3, v8}, Landroid/widget/TextView;->setTextColor(I)V

    .line 657
    const/high16 v8, 0x41200000    # 10.0f

    invoke-virtual {v3, v11, v8}, Landroid/widget/TextView;->setTextSize(IF)V

    .line 658
    invoke-virtual {v3, v2}, Landroid/widget/TextView;->setSingleLine(Z)V

    .line 659
    sget-object v8, Landroid/text/TextUtils$TruncateAt;->MARQUEE:Landroid/text/TextUtils$TruncateAt;

    invoke-virtual {v3, v8}, Landroid/widget/TextView;->setEllipsize(Landroid/text/TextUtils$TruncateAt;)V

    .line 660
    invoke-virtual {v3, v5}, Landroid/widget/TextView;->setMarqueeRepeatLimit(I)V

    .line 661
    invoke-virtual {v3, v2}, Landroid/widget/TextView;->setSelected(Z)V

    .line 662
    invoke-virtual {v3, v2}, Landroid/widget/TextView;->setHorizontallyScrolling(Z)V

    .line 663
    invoke-static {v0, v6}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v8

    invoke-static {v0, v6}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v9

    const/16 v10, 0x8

    invoke-static {v0, v10}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v13

    invoke-virtual {v3, v8, v4, v9, v13}, Landroid/widget/TextView;->setPadding(IIII)V

    .line 664
    new-instance v8, Landroid/widget/FrameLayout$LayoutParams;

    invoke-direct {v8, v5, v15}, Landroid/widget/FrameLayout$LayoutParams;-><init>(II)V

    .line 667
    const v9, 0x800053

    iput v9, v8, Landroid/widget/FrameLayout$LayoutParams;->gravity:I

    .line 668
    invoke-virtual {v1, v3, v8}, Landroid/widget/FrameLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 671
    new-instance v3, Landroid/widget/FrameLayout;

    invoke-direct {v3, v0}, Landroid/widget/FrameLayout;-><init>(Landroid/content/Context;)V

    sput-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->logModal:Landroid/widget/FrameLayout;

    .line 672
    sget v8, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_DIM:I

    invoke-virtual {v3, v8}, Landroid/widget/FrameLayout;->setBackgroundColor(I)V

    .line 673
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->logModal:Landroid/widget/FrameLayout;

    const/16 v8, 0x8

    invoke-virtual {v3, v8}, Landroid/widget/FrameLayout;->setVisibility(I)V

    .line 674
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->logModal:Landroid/widget/FrameLayout;

    invoke-virtual {v3, v2}, Landroid/widget/FrameLayout;->setClickable(Z)V

    .line 675
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->logModal:Landroid/widget/FrameLayout;

    new-instance v8, Lio/kamihama/magianative/CNCNDownloadUI$3;

    invoke-direct {v8}, Lio/kamihama/magianative/CNCNDownloadUI$3;-><init>()V

    invoke-virtual {v3, v8}, Landroid/widget/FrameLayout;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 678
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->logModal:Landroid/widget/FrameLayout;

    new-instance v8, Landroid/widget/FrameLayout$LayoutParams;

    invoke-direct {v8, v5, v5}, Landroid/widget/FrameLayout$LayoutParams;-><init>(II)V

    invoke-virtual {v1, v3, v8}, Landroid/widget/FrameLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 682
    new-instance v3, Landroid/widget/LinearLayout;

    invoke-direct {v3, v0}, Landroid/widget/LinearLayout;-><init>(Landroid/content/Context;)V

    .line 683
    invoke-virtual {v3, v2}, Landroid/widget/LinearLayout;->setOrientation(I)V

    .line 684
    invoke-virtual {v3, v2}, Landroid/widget/LinearLayout;->setClickable(Z)V

    .line 685
    new-instance v8, Lio/kamihama/magianative/CNCNDownloadUI$4;

    invoke-direct {v8}, Lio/kamihama/magianative/CNCNDownloadUI$4;-><init>()V

    invoke-virtual {v3, v8}, Landroid/widget/LinearLayout;->setOnTouchListener(Landroid/view/View$OnTouchListener;)V

    .line 688
    invoke-static {v0, v6}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v8

    invoke-static {v0, v6}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v9

    invoke-static {v0, v6}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v10

    invoke-static {v0, v6}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v13

    invoke-virtual {v3, v8, v9, v10, v13}, Landroid/widget/LinearLayout;->setPadding(IIII)V

    .line 689
    new-instance v8, Landroid/graphics/drawable/GradientDrawable;

    invoke-direct {v8}, Landroid/graphics/drawable/GradientDrawable;-><init>()V

    .line 690
    sget v9, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_LOG_PANEL_BG:I

    invoke-virtual {v8, v9}, Landroid/graphics/drawable/GradientDrawable;->setColor(I)V

    .line 691
    invoke-static {v0, v6}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v9

    int-to-float v9, v9

    invoke-virtual {v8, v9}, Landroid/graphics/drawable/GradientDrawable;->setCornerRadius(F)V

    .line 692
    invoke-static {v0, v2}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v9

    sget v10, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_CARD_STK:I

    invoke-virtual {v8, v9, v10}, Landroid/graphics/drawable/GradientDrawable;->setStroke(II)V

    .line 693
    invoke-virtual {v3, v8}, Landroid/widget/LinearLayout;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 694
    new-instance v8, Landroid/widget/FrameLayout$LayoutParams;

    invoke-direct {v8, v5, v5}, Landroid/widget/FrameLayout$LayoutParams;-><init>(II)V

    .line 697
    const/16 v9, 0x14

    invoke-static {v0, v9}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v10

    iput v10, v8, Landroid/widget/FrameLayout$LayoutParams;->leftMargin:I

    .line 698
    invoke-static {v0, v9}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v10

    iput v10, v8, Landroid/widget/FrameLayout$LayoutParams;->rightMargin:I

    .line 699
    invoke-static {v0, v9}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v10

    iput v10, v8, Landroid/widget/FrameLayout$LayoutParams;->topMargin:I

    .line 700
    invoke-static {v0, v9}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v9

    iput v9, v8, Landroid/widget/FrameLayout$LayoutParams;->bottomMargin:I

    .line 701
    sget-object v9, Lio/kamihama/magianative/CNCNDownloadUI;->logModal:Landroid/widget/FrameLayout;

    invoke-virtual {v9, v3, v8}, Landroid/widget/FrameLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 703
    new-instance v8, Landroid/widget/LinearLayout;

    invoke-direct {v8, v0}, Landroid/widget/LinearLayout;-><init>(Landroid/content/Context;)V

    .line 704
    invoke-virtual {v8, v4}, Landroid/widget/LinearLayout;->setOrientation(I)V

    .line 705
    invoke-virtual {v8, v6}, Landroid/widget/LinearLayout;->setGravity(I)V

    .line 706
    const/16 v9, 0x8

    invoke-static {v0, v9}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v10

    invoke-static {v4, v10}, Lio/kamihama/magianative/CNCNDownloadUI;->lpRow(II)Landroid/widget/LinearLayout$LayoutParams;

    move-result-object v9

    invoke-virtual {v3, v8, v9}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 708
    new-instance v9, Landroid/widget/TextView;

    invoke-direct {v9, v0}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    .line 709
    const-string v10, "\u5b89\u88c5\u65e5\u5fd7"

    invoke-virtual {v9, v10}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 710
    sget v10, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_ACCENT:I

    invoke-virtual {v9, v10}, Landroid/widget/TextView;->setTextColor(I)V

    .line 711
    const/high16 v10, 0x41800000    # 16.0f

    invoke-virtual {v9, v11, v10}, Landroid/widget/TextView;->setTextSize(IF)V

    .line 712
    new-instance v10, Landroid/widget/LinearLayout$LayoutParams;

    const/high16 v13, 0x3f800000    # 1.0f

    invoke-direct {v10, v4, v15, v13}, Landroid/widget/LinearLayout$LayoutParams;-><init>(IIF)V

    invoke-virtual {v8, v9, v10}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 715
    new-instance v9, Landroid/widget/TextView;

    invoke-direct {v9, v0}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    .line 716
    const-string v10, "\u590d\u5236\u5168\u90e8"

    invoke-virtual {v9, v10}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 717
    invoke-virtual {v9, v5}, Landroid/widget/TextView;->setTextColor(I)V

    .line 718
    const/high16 v10, 0x41400000    # 12.0f

    invoke-virtual {v9, v11, v10}, Landroid/widget/TextView;->setTextSize(IF)V

    .line 719
    invoke-virtual {v9, v7}, Landroid/widget/TextView;->setGravity(I)V

    .line 720
    const/16 v10, 0xe

    invoke-static {v0, v10}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v13

    const/4 v14, 0x6

    invoke-static {v0, v14}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v12

    invoke-static {v0, v10}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v10

    invoke-static {v0, v14}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v4

    invoke-virtual {v9, v13, v12, v10, v4}, Landroid/widget/TextView;->setPadding(IIII)V

    .line 721
    new-instance v4, Landroid/graphics/drawable/GradientDrawable;

    invoke-direct {v4}, Landroid/graphics/drawable/GradientDrawable;-><init>()V

    .line 722
    sget v10, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_ACCENT2:I

    invoke-virtual {v4, v10}, Landroid/graphics/drawable/GradientDrawable;->setColor(I)V

    .line 723
    const/16 v10, 0x8

    invoke-static {v0, v10}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v12

    int-to-float v10, v12

    invoke-virtual {v4, v10}, Landroid/graphics/drawable/GradientDrawable;->setCornerRadius(F)V

    .line 724
    invoke-virtual {v9, v4}, Landroid/widget/TextView;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 725
    new-instance v4, Lio/kamihama/magianative/CNCNDownloadUI$CopyLogClick;

    invoke-direct {v4, v0}, Lio/kamihama/magianative/CNCNDownloadUI$CopyLogClick;-><init>(Landroid/app/Activity;)V

    invoke-virtual {v9, v4}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 726
    new-instance v4, Landroid/widget/LinearLayout$LayoutParams;

    invoke-direct {v4, v15, v15}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    invoke-virtual {v8, v9, v4}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 730
    new-instance v4, Landroid/widget/TextView;

    invoke-direct {v4, v0}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    .line 731
    const-string v9, "\u5173\u95ed"

    invoke-virtual {v4, v9}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 732
    invoke-virtual {v4, v5}, Landroid/widget/TextView;->setTextColor(I)V

    .line 733
    const/high16 v9, 0x41400000    # 12.0f

    invoke-virtual {v4, v11, v9}, Landroid/widget/TextView;->setTextSize(IF)V

    .line 734
    invoke-virtual {v4, v7}, Landroid/widget/TextView;->setGravity(I)V

    .line 735
    invoke-static {v0, v6}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v7

    const/4 v9, 0x6

    invoke-static {v0, v9}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v10

    invoke-static {v0, v6}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v6

    invoke-static {v0, v9}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v12

    invoke-virtual {v4, v7, v10, v6, v12}, Landroid/widget/TextView;->setPadding(IIII)V

    .line 736
    new-instance v6, Landroid/graphics/drawable/GradientDrawable;

    invoke-direct {v6}, Landroid/graphics/drawable/GradientDrawable;-><init>()V

    .line 737
    sget v7, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_ACCENT:I

    invoke-virtual {v6, v7}, Landroid/graphics/drawable/GradientDrawable;->setColor(I)V

    .line 738
    const/16 v7, 0x8

    invoke-static {v0, v7}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v9

    int-to-float v7, v9

    invoke-virtual {v6, v7}, Landroid/graphics/drawable/GradientDrawable;->setCornerRadius(F)V

    .line 739
    invoke-virtual {v4, v6}, Landroid/widget/TextView;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 740
    new-instance v6, Lio/kamihama/magianative/CNCNDownloadUI$5;

    invoke-direct {v6}, Lio/kamihama/magianative/CNCNDownloadUI$5;-><init>()V

    invoke-virtual {v4, v6}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 743
    new-instance v6, Landroid/widget/LinearLayout$LayoutParams;

    invoke-direct {v6, v15, v15}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    .line 746
    const/16 v7, 0x8

    invoke-static {v0, v7}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v9

    iput v9, v6, Landroid/widget/LinearLayout$LayoutParams;->leftMargin:I

    .line 747
    invoke-virtual {v8, v4, v6}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 749
    new-instance v4, Landroid/widget/ScrollView;

    invoke-direct {v4, v0}, Landroid/widget/ScrollView;-><init>(Landroid/content/Context;)V

    sput-object v4, Lio/kamihama/magianative/CNCNDownloadUI;->vLogScroll:Landroid/widget/ScrollView;

    .line 750
    const/high16 v6, 0x60000

    invoke-virtual {v4, v6}, Landroid/widget/ScrollView;->setDescendantFocusability(I)V

    .line 751
    new-instance v4, Landroid/graphics/drawable/GradientDrawable;

    invoke-direct {v4}, Landroid/graphics/drawable/GradientDrawable;-><init>()V

    .line 752
    sget-boolean v6, Lio/kamihama/magianative/CNCNDownloadUI;->darkMode:Z

    if-eqz v6, :cond_5

    const v6, 0x44ffffff    # 2047.9999f

    goto :goto_4

    :cond_5
    const/high16 v6, 0x14000000

    :goto_4
    invoke-virtual {v4, v6}, Landroid/graphics/drawable/GradientDrawable;->setColor(I)V

    .line 753
    const/16 v6, 0x8

    invoke-static {v0, v6}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v7

    int-to-float v6, v7

    invoke-virtual {v4, v6}, Landroid/graphics/drawable/GradientDrawable;->setCornerRadius(F)V

    .line 754
    sget-boolean v6, Lio/kamihama/magianative/CNCNDownloadUI;->darkMode:Z

    if-eqz v6, :cond_6

    const v6, 0x33ffffff

    goto :goto_5

    :cond_6
    const/high16 v6, 0x22000000

    :goto_5
    invoke-virtual {v4, v2, v6}, Landroid/graphics/drawable/GradientDrawable;->setStroke(II)V

    .line 755
    sget-object v2, Lio/kamihama/magianative/CNCNDownloadUI;->vLogScroll:Landroid/widget/ScrollView;

    invoke-virtual {v2, v4}, Landroid/widget/ScrollView;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 756
    sget-object v2, Lio/kamihama/magianative/CNCNDownloadUI;->vLogScroll:Landroid/widget/ScrollView;

    const/16 v4, 0x8

    invoke-static {v0, v4}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v6

    const/4 v7, 0x6

    invoke-static {v0, v7}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v8

    invoke-static {v0, v4}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v4

    invoke-static {v0, v7}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v7

    invoke-virtual {v2, v6, v8, v4, v7}, Landroid/widget/ScrollView;->setPadding(IIII)V

    .line 757
    sget-object v2, Lio/kamihama/magianative/CNCNDownloadUI;->vLogScroll:Landroid/widget/ScrollView;

    invoke-virtual {v2}, Landroid/widget/ScrollView;->getViewTreeObserver()Landroid/view/ViewTreeObserver;

    move-result-object v2

    new-instance v4, Lio/kamihama/magianative/CNCNDownloadUI$LogScrollWatcher;

    const/4 v6, 0x0

    invoke-direct {v4, v6}, Lio/kamihama/magianative/CNCNDownloadUI$LogScrollWatcher;-><init>(Lio/kamihama/magianative/CNCNDownloadUI$1;)V

    invoke-virtual {v2, v4}, Landroid/view/ViewTreeObserver;->addOnScrollChangedListener(Landroid/view/ViewTreeObserver$OnScrollChangedListener;)V

    .line 758
    sget-object v2, Lio/kamihama/magianative/CNCNDownloadUI;->vLogScroll:Landroid/widget/ScrollView;

    new-instance v4, Landroid/widget/LinearLayout$LayoutParams;

    const/high16 v6, 0x3f800000    # 1.0f

    const/4 v7, 0x0

    invoke-direct {v4, v5, v7, v6}, Landroid/widget/LinearLayout$LayoutParams;-><init>(IIF)V

    invoke-virtual {v3, v2, v4}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 762
    new-instance v2, Landroid/widget/TextView;

    invoke-direct {v2, v0}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    sput-object v2, Lio/kamihama/magianative/CNCNDownloadUI;->tvLog:Landroid/widget/TextView;

    .line 763
    const-string v0, "=== MagiaCN Installer ===\n(waiting...)"

    invoke-virtual {v2, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 764
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->tvLog:Landroid/widget/TextView;

    sget v2, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_LOG_PANEL_TEXT:I

    invoke-virtual {v0, v2}, Landroid/widget/TextView;->setTextColor(I)V

    .line 765
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->tvLog:Landroid/widget/TextView;

    const/high16 v2, 0x41300000    # 11.0f

    invoke-virtual {v0, v11, v2}, Landroid/widget/TextView;->setTextSize(IF)V

    .line 766
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->tvLog:Landroid/widget/TextView;

    sget-object v2, Landroid/graphics/Typeface;->MONOSPACE:Landroid/graphics/Typeface;

    invoke-virtual {v0, v2}, Landroid/widget/TextView;->setTypeface(Landroid/graphics/Typeface;)V

    .line 770
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->vLogScroll:Landroid/widget/ScrollView;

    sget-object v2, Lio/kamihama/magianative/CNCNDownloadUI;->tvLog:Landroid/widget/TextView;

    new-instance v3, Landroid/view/ViewGroup$LayoutParams;

    invoke-direct {v3, v5, v15}, Landroid/view/ViewGroup$LayoutParams;-><init>(II)V

    invoke-virtual {v0, v2, v3}, Landroid/widget/ScrollView;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 774
    return-object v1
.end method

.method public static buildStatusText()Ljava/lang/String;
    .locals 14

    .line 1499
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->FILE_NAMES:[Ljava/lang/String;

    .line 1500
    sget-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->fileStatus:[I

    .line 1501
    sget-object v2, Lio/kamihama/magianative/CNCNDownloadUI;->fileProgress:[I

    .line 1502
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->fileSize:[F

    .line 1503
    sget-object v4, Lio/kamihama/magianative/CNCNDownloadUI;->fileSpeed:[F

    .line 1504
    sget-object v5, Lio/kamihama/magianative/CNCNDownloadUI;->fileDownloaded:[F

    .line 1505
    if-eqz v0, :cond_d

    if-eqz v1, :cond_d

    if-nez v2, :cond_0

    goto/16 :goto_4

    .line 1508
    :cond_0
    new-instance v6, Ljava/lang/StringBuilder;

    const-string v7, "=== MagiaCN Installer ===\n"

    invoke-direct {v6, v7}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 1509
    const/4 v7, 0x0

    const/4 v8, 0x0

    :goto_0
    const/16 v9, 0xf

    if-ge v8, v9, :cond_c

    .line 1510
    aget v9, v1, v8

    .line 1511
    const/4 v10, 0x2

    const/4 v11, 0x1

    if-ne v9, v10, :cond_1

    const-string v10, "[OK] "

    goto :goto_1

    :cond_1
    if-ne v9, v11, :cond_2

    const-string v10, "[ > ] "

    goto :goto_1

    :cond_2
    const/4 v10, 0x3

    if-ne v9, v10, :cond_3

    const-string v10, "[ERR] "

    goto :goto_1

    :cond_3
    const-string v10, "[  ] "

    :goto_1
    invoke-virtual {v6, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    add-int/lit8 v12, v8, 0x1

    .line 1512
    invoke-virtual {v10, v12}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v10

    const-string v13, "."

    invoke-virtual {v10, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    aget-object v13, v0, v8

    invoke-virtual {v10, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 1513
    if-ne v9, v11, :cond_b

    .line 1514
    const-string v9, "  "

    invoke-virtual {v6, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    aget v11, v2, v8

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v10

    const-string v11, "%"

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 1515
    if-eqz v5, :cond_6

    if-eqz v3, :cond_6

    .line 1516
    aget v10, v5, v8

    invoke-static {v10}, Ljava/lang/Float;->toString(F)Ljava/lang/String;

    move-result-object v10

    .line 1517
    invoke-virtual {v10}, Ljava/lang/String;->length()I

    move-result v11

    const/4 v13, 0x6

    if-le v11, v13, :cond_4

    invoke-virtual {v10, v7, v13}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v10

    .line 1518
    :cond_4
    invoke-virtual {v6, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    const-string v11, "/"

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 1519
    aget v10, v3, v8

    invoke-static {v10}, Ljava/lang/Float;->toString(F)Ljava/lang/String;

    move-result-object v10

    .line 1520
    invoke-virtual {v10}, Ljava/lang/String;->length()I

    move-result v11

    if-le v11, v13, :cond_5

    invoke-virtual {v10, v7, v13}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v10

    .line 1521
    :cond_5
    invoke-virtual {v6, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    const-string v11, "MB"

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 1523
    :cond_6
    if-eqz v4, :cond_8

    .line 1524
    aget v10, v4, v8

    invoke-static {v10}, Ljava/lang/Float;->toString(F)Ljava/lang/String;

    move-result-object v10

    .line 1525
    invoke-virtual {v10}, Ljava/lang/String;->length()I

    move-result v11

    const/4 v13, 0x4

    if-le v11, v13, :cond_7

    invoke-virtual {v10, v7, v13}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v10

    .line 1526
    :cond_7
    invoke-virtual {v6, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    const-string v10, "MB/s"

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 1528
    :cond_8
    aget v9, v1, v8

    if-eqz v9, :cond_b

    .line 1529
    aget v8, v2, v8

    .line 1530
    const-string v9, "\n  ["

    invoke-virtual {v6, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 1531
    const/4 v9, 0x0

    :goto_2
    const/16 v10, 0xa

    if-ge v9, v10, :cond_a

    .line 1532
    mul-int/lit8 v10, v9, 0xa

    if-ge v10, v8, :cond_9

    const-string v10, "\u2588"

    goto :goto_3

    :cond_9
    const-string v10, "\u2591"

    :goto_3
    invoke-virtual {v6, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 1531
    add-int/lit8 v9, v9, 0x1

    goto :goto_2

    .line 1534
    :cond_a
    const-string v8, "]"

    invoke-virtual {v6, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 1537
    :cond_b
    const-string v8, "\n"

    invoke-virtual {v6, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 1509
    move v8, v12

    goto/16 :goto_0

    .line 1539
    :cond_c
    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0

    .line 1506
    :cond_d
    :goto_4
    const-string v0, "=== MagiaCN Installer ===\n(initializing...)"

    return-object v0
.end method

.method private static closeLogModal()V
    .locals 2

    .line 1070
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->logModal:Landroid/widget/FrameLayout;

    if-eqz v0, :cond_0

    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Landroid/widget/FrameLayout;->setVisibility(I)V

    .line 1071
    :cond_0
    return-void
.end method

.method private static composeLogText(Z)Ljava/lang/String;
    .locals 4

    .line 1036
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    .line 1037
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->buildStatusText()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 1038
    const-string v1, "\n\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500 \u8fd0\u884c\u65e5\u5fd7 \u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\n"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 1039
    const/16 v1, 0x12c

    if-eqz p0, :cond_0

    invoke-static {}, Lio/kamihama/magianative/CNLog;->snapshot()Ljava/lang/String;

    move-result-object v2

    goto :goto_0

    :cond_0
    invoke-static {v1}, Lio/kamihama/magianative/CNLog;->tail(I)Ljava/lang/String;

    move-result-object v2

    .line 1040
    :goto_0
    invoke-virtual {v2}, Ljava/lang/String;->length()I

    move-result v3

    if-nez v3, :cond_1

    .line 1041
    const-string p0, "\uff08\u6682\u65e0\u65e5\u5fd7\uff09\n"

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    goto :goto_1

    .line 1043
    :cond_1
    if-nez p0, :cond_2

    invoke-static {}, Lio/kamihama/magianative/CNLog;->size()I

    move-result p0

    if-le p0, v1, :cond_2

    .line 1044
    const-string p0, "\uff08\u4ec5\u663e\u793a\u6700\u8fd1 "

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string v1, " \u884c\uff0c\u5171 "

    invoke-virtual {p0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    .line 1045
    invoke-static {}, Lio/kamihama/magianative/CNLog;->size()I

    move-result v1

    invoke-virtual {p0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string v1, " \u884c\uff1b\u300c\u590d\u5236\u5168\u90e8\u300d\u53ef\u53d6\u5b8c\u6574\u65e5\u5fd7\uff09\n"

    invoke-virtual {p0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 1047
    :cond_2
    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 1049
    :goto_1
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method private static dp(Landroid/content/Context;I)I
    .locals 0

    .line 374
    int-to-float p1, p1

    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p0

    invoke-virtual {p0}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object p0

    iget p0, p0, Landroid/util/DisplayMetrics;->density:F

    mul-float p1, p1, p0

    const/high16 p0, 0x3f000000    # 0.5f

    add-float/2addr p1, p0

    float-to-int p0, p1

    return p0
.end method

.method private static dpStatic(I)I
    .locals 1

    .line 1149
    int-to-float p0, p0

    invoke-static {}, Landroid/content/res/Resources;->getSystem()Landroid/content/res/Resources;

    move-result-object v0

    .line 1150
    invoke-virtual {v0}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object v0

    iget v0, v0, Landroid/util/DisplayMetrics;->density:F

    mul-float p0, p0, v0

    const/high16 v0, 0x3f000000    # 0.5f

    add-float/2addr p0, v0

    float-to-int p0, p0

    .line 1149
    return p0
.end method

.method public static ensureVisible(Landroid/app/Activity;)V
    .locals 1

    .line 1163
    if-nez p0, :cond_0

    return-void

    .line 1165
    :cond_0
    :try_start_0
    new-instance v0, Lio/kamihama/magianative/CNCNDownloadUI$EnsureVisible;

    invoke-direct {v0, p0}, Lio/kamihama/magianative/CNCNDownloadUI$EnsureVisible;-><init>(Landroid/app/Activity;)V

    invoke-virtual {p0, v0}, Landroid/app/Activity;->runOnUiThread(Ljava/lang/Runnable;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    goto :goto_0

    .line 1166
    :catchall_0
    move-exception p0

    :goto_0
    nop

    .line 1167
    return-void
.end method

.method private static formatMb(F)Ljava/lang/String;
    .locals 4

    .line 1235
    const/4 v0, 0x0

    cmpg-float v0, p0, v0

    if-gtz v0, :cond_0

    const-string p0, "0 MB"

    return-object p0

    .line 1236
    :cond_0
    const/4 v0, 0x0

    const/4 v1, 0x1

    const/high16 v2, 0x44800000    # 1024.0f

    cmpg-float v3, p0, v2

    if-gez v3, :cond_1

    sget-object v2, Ljava/util/Locale;->US:Ljava/util/Locale;

    new-array v1, v1, [Ljava/lang/Object;

    invoke-static {p0}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object p0

    aput-object p0, v1, v0

    const-string p0, "%.1f MB"

    invoke-static {v2, p0, v1}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p0

    return-object p0

    .line 1237
    :cond_1
    sget-object v3, Ljava/util/Locale;->US:Ljava/util/Locale;

    new-array v1, v1, [Ljava/lang/Object;

    div-float/2addr p0, v2

    invoke-static {p0}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object p0

    aput-object p0, v1, v0

    const-string p0, "%.2f GB"

    invoke-static {v3, p0, v1}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method private static formatMbps(F)Ljava/lang/String;
    .locals 3

    .line 1241
    const/4 v0, 0x0

    cmpg-float v0, p0, v0

    if-gtz v0, :cond_0

    const-string p0, ""

    return-object p0

    .line 1242
    :cond_0
    sget-object v0, Ljava/util/Locale;->US:Ljava/util/Locale;

    const/4 v1, 0x1

    new-array v1, v1, [Ljava/lang/Object;

    const/4 v2, 0x0

    invoke-static {p0}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object p0

    aput-object p0, v1, v2

    const-string p0, "%.2f MB/s"

    invoke-static {v0, p0, v1}, Ljava/lang/String;->format(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public static hide()V
    .locals 2

    .line 1544
    sget-boolean v0, Lio/kamihama/magianative/CNCNDownloadUI;->isShowing:Z

    if-eqz v0, :cond_1

    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->uiHandler:Landroid/os/Handler;

    if-nez v0, :cond_0

    goto :goto_0

    .line 1547
    :cond_0
    new-instance v1, Lio/kamihama/magianative/CNCNDownloadUI$HideRunnable;

    invoke-direct {v1}, Lio/kamihama/magianative/CNCNDownloadUI$HideRunnable;-><init>()V

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 1548
    const/4 v0, 0x0

    sput-boolean v0, Lio/kamihama/magianative/CNCNDownloadUI;->isShowing:Z

    .line 1549
    return-void

    .line 1545
    :cond_1
    :goto_0
    return-void
.end method

.method private static highlight(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/CharSequence;
    .locals 3

    .line 819
    if-nez p0, :cond_0

    const-string p0, ""

    return-object p0

    .line 820
    :cond_0
    if-eqz p1, :cond_3

    invoke-virtual {p1}, Ljava/lang/String;->length()I

    move-result v0

    if-nez v0, :cond_1

    goto :goto_0

    .line 821
    :cond_1
    invoke-virtual {p0, p1}, Ljava/lang/String;->indexOf(Ljava/lang/String;)I

    move-result v0

    .line 822
    if-gez v0, :cond_2

    return-object p0

    .line 823
    :cond_2
    new-instance v1, Landroid/text/SpannableString;

    invoke-direct {v1, p0}, Landroid/text/SpannableString;-><init>(Ljava/lang/CharSequence;)V

    .line 824
    new-instance p0, Landroid/text/style/ForegroundColorSpan;

    sget v2, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_LINK:I

    invoke-direct {p0, v2}, Landroid/text/style/ForegroundColorSpan;-><init>(I)V

    .line 825
    invoke-virtual {p1}, Ljava/lang/String;->length()I

    move-result p1

    add-int/2addr p1, v0

    .line 824
    const/16 v2, 0x21

    invoke-virtual {v1, p0, v0, p1, v2}, Landroid/text/SpannableString;->setSpan(Ljava/lang/Object;III)V

    .line 827
    return-object v1

    .line 820
    :cond_3
    :goto_0
    return-object p0
.end method

.method private static loadBitmapFromAssets(Landroid/app/Activity;Ljava/lang/String;Landroid/widget/ImageView;)V
    .locals 2

    .line 423
    new-instance v0, Ljava/lang/Thread;

    new-instance v1, Lio/kamihama/magianative/CNCNDownloadUI$AssetBitmapLoader;

    invoke-direct {v1, p0, p1, p2}, Lio/kamihama/magianative/CNCNDownloadUI$AssetBitmapLoader;-><init>(Landroid/app/Activity;Ljava/lang/String;Landroid/widget/ImageView;)V

    const-string p0, "cnv-img-load"

    invoke-direct {v0, v1, p0}, Ljava/lang/Thread;-><init>(Ljava/lang/Runnable;Ljava/lang/String;)V

    .line 424
    invoke-virtual {v0}, Ljava/lang/Thread;->start()V

    .line 425
    return-void
.end method

.method private static loadPalette(Z)V
    .locals 2

    .line 142
    if-eqz p0, :cond_0

    .line 143
    const p0, 0x55ff80c0

    sput p0, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_CARD_STK:I

    .line 144
    const p0, -0x853e

    sput p0, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_ACCENT:I

    .line 145
    const p0, -0x478020

    sput p0, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_ACCENT2:I

    .line 146
    const p0, -0x101b08

    sput p0, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_TEXT:I

    .line 147
    const p0, -0x465938

    sput p0, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_SUB:I

    .line 148
    const p0, 0x44ffffff    # 2047.9999f

    sput p0, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_BAR_BG:I

    .line 149
    const p0, -0x1900904b

    sput p0, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_LOG_PILL:I

    .line 150
    const/high16 p0, -0x56000000

    sput p0, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_DIM:I

    .line 151
    const p0, -0xe4efd7

    sput p0, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_LOG_PANEL_BG:I

    .line 152
    const p0, -0xa1305

    sput p0, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_LOG_PANEL_TEXT:I

    .line 153
    const p0, -0x703910

    sput p0, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_LINK:I

    .line 154
    const p0, -0x33e7eed6    # -3.9863464E7f

    sput p0, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_GLASS:I

    .line 155
    const p0, 0x44ff80c0

    sput p0, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_GLASS_STK:I

    goto :goto_0

    .line 157
    :cond_0
    const p0, 0x33b53c8c

    sput p0, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_CARD_STK:I

    .line 158
    const v0, -0x29cc7c

    sput v0, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_ACCENT:I

    .line 159
    const v0, -0x63a43e

    sput v0, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_ACCENT2:I

    .line 160
    const v0, -0xd5e5c5

    sput v0, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_TEXT:I

    .line 161
    const v1, -0x91ad8a

    sput v1, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_SUB:I

    .line 162
    const/high16 v1, 0x22000000

    sput v1, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_BAR_BG:I

    .line 163
    const v1, -0x1929cc7c

    sput v1, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_LOG_PILL:I

    .line 164
    const/high16 v1, -0x78000000

    sput v1, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_DIM:I

    .line 165
    const/4 v1, -0x1

    sput v1, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_LOG_PANEL_BG:I

    .line 166
    sput v0, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_LOG_PANEL_TEXT:I

    .line 167
    const v0, -0xd39458

    sput v0, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_LINK:I

    .line 168
    const v0, -0x33000001    # -1.3421772E8f

    sput v0, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_GLASS:I

    .line 169
    sput p0, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_GLASS_STK:I

    .line 171
    :goto_0
    return-void
.end method

.method private static lpRow(II)Landroid/widget/LinearLayout$LayoutParams;
    .locals 3

    .line 378
    new-instance v0, Landroid/widget/LinearLayout$LayoutParams;

    const/4 v1, -0x1

    const/4 v2, -0x2

    invoke-direct {v0, v1, v2}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    .line 381
    iput p0, v0, Landroid/widget/LinearLayout$LayoutParams;->topMargin:I

    .line 382
    iput p1, v0, Landroid/widget/LinearLayout$LayoutParams;->bottomMargin:I

    .line 383
    return-object v0
.end method

.method public static markFileDone(I)V
    .locals 2

    .line 1552
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->fileStatus:[I

    .line 1553
    if-eqz v0, :cond_0

    .line 1554
    const/4 v1, 0x2

    aput v1, v0, p0

    .line 1555
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->fileProgress:[I

    .line 1556
    if-eqz v0, :cond_0

    .line 1557
    const/16 v1, 0x64

    aput v1, v0, p0

    .line 1560
    :cond_0
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->fileSpeed:[F

    .line 1561
    if-eqz v0, :cond_1

    .line 1562
    const/4 v1, 0x0

    aput v1, v0, p0

    .line 1564
    :cond_1
    sget-object p0, Lio/kamihama/magianative/CNCNDownloadUI;->uiHandler:Landroid/os/Handler;

    .line 1565
    if-eqz p0, :cond_2

    .line 1566
    new-instance v0, Lio/kamihama/magianative/CNCNDownloadUI$UpdateRunnable;

    invoke-direct {v0}, Lio/kamihama/magianative/CNCNDownloadUI$UpdateRunnable;-><init>()V

    invoke-virtual {p0, v0}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 1568
    :cond_2
    return-void
.end method

.method private static openLogModal()V
    .locals 3

    .line 1060
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->logModal:Landroid/widget/FrameLayout;

    if-nez v0, :cond_0

    return-void

    .line 1061
    :cond_0
    const/4 v1, 0x1

    sput-boolean v1, Lio/kamihama/magianative/CNCNDownloadUI;->logAutoScroll:Z

    .line 1062
    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/widget/FrameLayout;->setVisibility(I)V

    .line 1063
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->renderLogModal()V

    .line 1064
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->vLogScroll:Landroid/widget/ScrollView;

    if-eqz v0, :cond_1

    .line 1065
    new-instance v1, Lio/kamihama/magianative/CNCNDownloadUI$ScrollToBottom;

    const/4 v2, 0x0

    invoke-direct {v1, v2}, Lio/kamihama/magianative/CNCNDownloadUI$ScrollToBottom;-><init>(Lio/kamihama/magianative/CNCNDownloadUI$1;)V

    invoke-virtual {v0, v1}, Landroid/widget/ScrollView;->post(Ljava/lang/Runnable;)Z

    .line 1067
    :cond_1
    return-void
.end method

.method private static populateContributors(Landroid/app/Activity;)V
    .locals 14

    .line 855
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->vContribList:Landroid/widget/LinearLayout;

    if-nez v0, :cond_0

    return-void

    .line 856
    :cond_0
    invoke-virtual {v0}, Landroid/widget/LinearLayout;->removeAllViews()V

    .line 857
    nop

    .line 858
    const/4 v0, 0x0

    const/4 v1, 0x0

    const/4 v2, 0x0

    :goto_0
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->CREDIT_TEXTS:[Ljava/lang/String;

    array-length v4, v3

    if-ge v1, v4, :cond_7

    .line 859
    sget-object v4, Lio/kamihama/magianative/CNCNDownloadUI;->CREDIT_KINDS:[I

    aget v4, v4, v1

    .line 860
    const/4 v5, -0x1

    const/4 v6, -0x2

    const/4 v7, 0x1

    const/4 v8, 0x2

    if-ne v4, v8, :cond_4

    .line 861
    new-instance v4, Landroid/widget/LinearLayout;

    invoke-direct {v4, p0}, Landroid/widget/LinearLayout;-><init>(Landroid/content/Context;)V

    .line 862
    invoke-virtual {v4, v0}, Landroid/widget/LinearLayout;->setOrientation(I)V

    .line 863
    const/16 v9, 0x10

    invoke-virtual {v4, v9}, Landroid/widget/LinearLayout;->setGravity(I)V

    .line 864
    new-instance v9, Landroid/widget/LinearLayout$LayoutParams;

    invoke-direct {v9, v5, v6}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    .line 867
    const/4 v5, 0x3

    invoke-static {p0, v5}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v10

    iput v10, v9, Landroid/widget/LinearLayout$LayoutParams;->topMargin:I

    .line 868
    sget-object v10, Lio/kamihama/magianative/CNCNDownloadUI;->vContribList:Landroid/widget/LinearLayout;

    invoke-virtual {v10, v4, v9}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 870
    new-instance v9, Lio/kamihama/magianative/CNCNDownloadUI$DotView;

    sget-object v10, Lio/kamihama/magianative/CNCNDownloadUI;->CONTRIB_PALETTE:[I

    array-length v11, v10

    rem-int v11, v2, v11

    aget v10, v10, v11

    invoke-direct {v9, p0, v10}, Lio/kamihama/magianative/CNCNDownloadUI$DotView;-><init>(Landroid/content/Context;I)V

    .line 872
    new-instance v10, Landroid/widget/LinearLayout$LayoutParams;

    .line 873
    const/4 v11, 0x7

    invoke-static {p0, v11}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v12

    invoke-static {p0, v11}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v13

    invoke-direct {v10, v12, v13}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    .line 874
    invoke-static {p0, v11}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v11

    iput v11, v10, Landroid/widget/LinearLayout$LayoutParams;->rightMargin:I

    .line 875
    invoke-virtual {v4, v9, v10}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 876
    add-int/lit8 v2, v2, 0x1

    .line 878
    sget-object v9, Lio/kamihama/magianative/CNCNDownloadUI;->CREDIT_URLS:[Ljava/lang/String;

    array-length v10, v9

    const-string v11, ""

    if-ge v1, v10, :cond_1

    aget-object v9, v9, v1

    goto :goto_1

    :cond_1
    move-object v9, v11

    .line 879
    :goto_1
    sget-object v10, Lio/kamihama/magianative/CNCNDownloadUI;->CREDIT_LINK_SPANS:[Ljava/lang/String;

    array-length v12, v10

    if-ge v1, v12, :cond_2

    aget-object v11, v10, v1

    .line 880
    :cond_2
    new-instance v10, Landroid/widget/TextView;

    invoke-direct {v10, p0}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    .line 881
    sget v12, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_TEXT:I

    invoke-virtual {v10, v12}, Landroid/widget/TextView;->setTextColor(I)V

    .line 882
    const/high16 v12, 0x41200000    # 10.0f

    invoke-virtual {v10, v8, v12}, Landroid/widget/TextView;->setTextSize(IF)V

    .line 883
    aget-object v3, v3, v1

    invoke-static {v3, v11}, Lio/kamihama/magianative/CNCNDownloadUI;->highlight(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/CharSequence;

    move-result-object v3

    invoke-virtual {v10, v3}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 884
    invoke-virtual {v9}, Ljava/lang/String;->length()I

    move-result v3

    if-lez v3, :cond_3

    .line 885
    invoke-static {p0, v5}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v3

    invoke-static {p0, v5}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v5

    invoke-virtual {v4, v0, v3, v0, v5}, Landroid/widget/LinearLayout;->setPadding(IIII)V

    .line 886
    invoke-virtual {v4, v7}, Landroid/widget/LinearLayout;->setClickable(Z)V

    .line 887
    new-instance v3, Lio/kamihama/magianative/CNCNDownloadUI$CreditLinkClick;

    invoke-direct {v3, p0, v9}, Lio/kamihama/magianative/CNCNDownloadUI$CreditLinkClick;-><init>(Landroid/app/Activity;Ljava/lang/String;)V

    invoke-virtual {v4, v3}, Landroid/widget/LinearLayout;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 889
    :cond_3
    new-instance v3, Landroid/widget/LinearLayout$LayoutParams;

    const/high16 v5, 0x3f800000    # 1.0f

    invoke-direct {v3, v0, v6, v5}, Landroid/widget/LinearLayout$LayoutParams;-><init>(IIF)V

    invoke-virtual {v4, v10, v3}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 891
    goto :goto_3

    .line 892
    :cond_4
    new-instance v9, Landroid/widget/TextView;

    invoke-direct {v9, p0}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    .line 893
    aget-object v3, v3, v1

    invoke-virtual {v9, v3}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 894
    new-instance v3, Landroid/widget/LinearLayout$LayoutParams;

    invoke-direct {v3, v5, v6}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    .line 897
    if-nez v4, :cond_5

    .line 898
    sget v4, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_ACCENT:I

    invoke-virtual {v9, v4}, Landroid/widget/TextView;->setTextColor(I)V

    .line 899
    const/high16 v4, 0x41400000    # 12.0f

    invoke-virtual {v9, v8, v4}, Landroid/widget/TextView;->setTextSize(IF)V

    .line 900
    invoke-virtual {v9}, Landroid/widget/TextView;->getTypeface()Landroid/graphics/Typeface;

    move-result-object v4

    invoke-virtual {v9, v4, v7}, Landroid/widget/TextView;->setTypeface(Landroid/graphics/Typeface;I)V

    .line 901
    const/4 v4, 0x4

    invoke-static {p0, v4}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v4

    iput v4, v3, Landroid/widget/LinearLayout$LayoutParams;->bottomMargin:I

    goto :goto_2

    .line 902
    :cond_5
    if-ne v4, v7, :cond_6

    .line 903
    sget v4, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_ACCENT2:I

    invoke-virtual {v9, v4}, Landroid/widget/TextView;->setTextColor(I)V

    .line 904
    const/high16 v4, 0x41300000    # 11.0f

    invoke-virtual {v9, v8, v4}, Landroid/widget/TextView;->setTextSize(IF)V

    .line 905
    invoke-virtual {v9}, Landroid/widget/TextView;->getTypeface()Landroid/graphics/Typeface;

    move-result-object v4

    invoke-virtual {v9, v4, v7}, Landroid/widget/TextView;->setTypeface(Landroid/graphics/Typeface;I)V

    .line 906
    const/16 v4, 0x8

    invoke-static {p0, v4}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v4

    iput v4, v3, Landroid/widget/LinearLayout$LayoutParams;->topMargin:I

    .line 907
    invoke-static {p0, v8}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v4

    iput v4, v3, Landroid/widget/LinearLayout$LayoutParams;->bottomMargin:I

    goto :goto_2

    .line 909
    :cond_6
    sget v4, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_SUB:I

    invoke-virtual {v9, v4}, Landroid/widget/TextView;->setTextColor(I)V

    .line 910
    const/high16 v4, 0x41100000    # 9.0f

    invoke-virtual {v9, v8, v4}, Landroid/widget/TextView;->setTextSize(IF)V

    .line 911
    const/16 v4, 0xe

    invoke-static {p0, v4}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v4

    iput v4, v3, Landroid/widget/LinearLayout$LayoutParams;->leftMargin:I

    .line 913
    :goto_2
    sget-object v4, Lio/kamihama/magianative/CNCNDownloadUI;->vContribList:Landroid/widget/LinearLayout;

    invoke-virtual {v4, v9, v3}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 858
    :goto_3
    add-int/lit8 v1, v1, 0x1

    goto/16 :goto_0

    .line 916
    :cond_7
    return-void
.end method

.method private static rebuildSlots(Landroid/app/Activity;)V
    .locals 16

    .line 920
    move-object/from16 v0, p0

    sget-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->slotContainer:Landroid/widget/LinearLayout;

    if-nez v1, :cond_0

    return-void

    .line 921
    :cond_0
    invoke-virtual {v1}, Landroid/widget/LinearLayout;->removeAllViews()V

    .line 922
    sget-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->slotList:Ljava/util/List;

    invoke-interface {v1}, Ljava/util/List;->clear()V

    .line 923
    const/4 v1, 0x0

    const/4 v2, 0x0

    :goto_0
    const/16 v3, 0xf

    if-ge v2, v3, :cond_2

    .line 924
    new-instance v3, Landroid/widget/LinearLayout;

    invoke-direct {v3, v0}, Landroid/widget/LinearLayout;-><init>(Landroid/content/Context;)V

    .line 925
    const/4 v4, 0x1

    invoke-virtual {v3, v4}, Landroid/widget/LinearLayout;->setOrientation(I)V

    .line 926
    sget-object v5, Lio/kamihama/magianative/CNCNDownloadUI;->slotContainer:Landroid/widget/LinearLayout;

    new-instance v6, Landroid/widget/LinearLayout$LayoutParams;

    const/4 v7, -0x1

    const/4 v8, -0x2

    invoke-direct {v6, v7, v8}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    invoke-virtual {v5, v3, v6}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 930
    new-instance v5, Landroid/widget/LinearLayout;

    invoke-direct {v5, v0}, Landroid/widget/LinearLayout;-><init>(Landroid/content/Context;)V

    .line 931
    invoke-virtual {v5, v1}, Landroid/widget/LinearLayout;->setOrientation(I)V

    .line 932
    const/16 v6, 0x10

    invoke-virtual {v5, v6}, Landroid/widget/LinearLayout;->setGravity(I)V

    .line 933
    new-instance v6, Landroid/widget/LinearLayout$LayoutParams;

    invoke-direct {v6, v7, v8}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    .line 936
    const/4 v9, 0x5

    invoke-static {v0, v9}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v9

    iput v9, v6, Landroid/widget/LinearLayout$LayoutParams;->topMargin:I

    .line 937
    invoke-virtual {v3, v5, v6}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 939
    new-instance v11, Landroid/widget/TextView;

    invoke-direct {v11, v0}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    .line 940
    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    add-int/lit8 v9, v2, 0x1

    invoke-virtual {v6, v9}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v10, ". "

    invoke-virtual {v6, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    sget-object v10, Lio/kamihama/magianative/CNCNDownloadUI;->FILE_NAMES:[Ljava/lang/String;

    aget-object v10, v10, v2

    invoke-virtual {v6, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v11, v6}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 941
    sget v6, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_TEXT:I

    invoke-virtual {v11, v6}, Landroid/widget/TextView;->setTextColor(I)V

    .line 942
    const/high16 v6, 0x41300000    # 11.0f

    const/4 v10, 0x2

    invoke-virtual {v11, v10, v6}, Landroid/widget/TextView;->setTextSize(IF)V

    .line 943
    invoke-virtual {v11, v4}, Landroid/widget/TextView;->setSingleLine(Z)V

    .line 944
    sget-object v6, Landroid/text/TextUtils$TruncateAt;->MIDDLE:Landroid/text/TextUtils$TruncateAt;

    invoke-virtual {v11, v6}, Landroid/widget/TextView;->setEllipsize(Landroid/text/TextUtils$TruncateAt;)V

    .line 945
    new-instance v6, Landroid/widget/LinearLayout$LayoutParams;

    const/high16 v12, 0x3f800000    # 1.0f

    invoke-direct {v6, v1, v8, v12}, Landroid/widget/LinearLayout$LayoutParams;-><init>(IIF)V

    invoke-virtual {v5, v11, v6}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 948
    new-instance v12, Landroid/widget/TextView;

    invoke-direct {v12, v0}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    .line 949
    const-string v6, ""

    invoke-virtual {v12, v6}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 950
    sget v6, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_SUB:I

    invoke-virtual {v12, v6}, Landroid/widget/TextView;->setTextColor(I)V

    .line 951
    const/high16 v6, 0x41200000    # 10.0f

    invoke-virtual {v12, v10, v6}, Landroid/widget/TextView;->setTextSize(IF)V

    .line 952
    const v13, 0x800005

    invoke-virtual {v12, v13}, Landroid/widget/TextView;->setGravity(I)V

    .line 953
    new-instance v13, Landroid/widget/LinearLayout$LayoutParams;

    invoke-direct {v13, v8, v8}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    invoke-virtual {v5, v12, v13}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 958
    new-instance v13, Landroid/widget/TextView;

    invoke-direct {v13, v0}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    .line 959
    const-string v14, "\u91cd\u8bd5"

    invoke-virtual {v13, v14}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 960
    invoke-virtual {v13, v7}, Landroid/widget/TextView;->setTextColor(I)V

    .line 961
    invoke-virtual {v13, v10, v6}, Landroid/widget/TextView;->setTextSize(IF)V

    .line 962
    const/16 v6, 0x11

    invoke-virtual {v13, v6}, Landroid/widget/TextView;->setGravity(I)V

    .line 963
    const/16 v6, 0xa

    invoke-static {v0, v6}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v14

    const/4 v15, 0x3

    invoke-static {v0, v15}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v4

    invoke-static {v0, v6}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v10

    invoke-static {v0, v15}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v15

    invoke-virtual {v13, v14, v4, v10, v15}, Landroid/widget/TextView;->setPadding(IIII)V

    .line 964
    new-instance v4, Landroid/graphics/drawable/GradientDrawable;

    invoke-direct {v4}, Landroid/graphics/drawable/GradientDrawable;-><init>()V

    .line 965
    const v10, -0x1ac6cb

    invoke-virtual {v4, v10}, Landroid/graphics/drawable/GradientDrawable;->setColor(I)V

    .line 966
    invoke-static {v0, v6}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v6

    int-to-float v6, v6

    invoke-virtual {v4, v6}, Landroid/graphics/drawable/GradientDrawable;->setCornerRadius(F)V

    .line 967
    invoke-virtual {v13, v4}, Landroid/widget/TextView;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 968
    const/16 v4, 0x8

    invoke-virtual {v13, v4}, Landroid/widget/TextView;->setVisibility(I)V

    .line 969
    new-instance v6, Lio/kamihama/magianative/CNCNDownloadUI$RetryClick;

    invoke-direct {v6, v0, v2}, Lio/kamihama/magianative/CNCNDownloadUI$RetryClick;-><init>(Landroid/app/Activity;I)V

    invoke-virtual {v13, v6}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 970
    new-instance v2, Landroid/widget/LinearLayout$LayoutParams;

    invoke-direct {v2, v8, v8}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    .line 973
    invoke-static {v0, v4}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v4

    iput v4, v2, Landroid/widget/LinearLayout$LayoutParams;->leftMargin:I

    .line 974
    invoke-virtual {v5, v13, v2}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 976
    new-instance v14, Landroid/widget/ProgressBar;

    const/4 v2, 0x0

    const v4, 0x1010078

    invoke-direct {v14, v0, v2, v4}, Landroid/widget/ProgressBar;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    .line 978
    const/16 v2, 0x64

    invoke-virtual {v14, v2}, Landroid/widget/ProgressBar;->setMax(I)V

    .line 979
    invoke-virtual {v14, v1}, Landroid/widget/ProgressBar;->setProgress(I)V

    .line 980
    const v2, 0x55888888

    invoke-static {v14, v2}, Lio/kamihama/magianative/CNCNDownloadUI;->tintBar(Landroid/widget/ProgressBar;I)V

    .line 981
    new-instance v2, Landroid/widget/LinearLayout$LayoutParams;

    .line 982
    const/4 v4, 0x6

    invoke-static {v0, v4}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v4

    invoke-direct {v2, v7, v4}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    .line 983
    const/4 v4, 0x2

    invoke-static {v0, v4}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v4

    iput v4, v2, Landroid/widget/LinearLayout$LayoutParams;->topMargin:I

    .line 984
    invoke-virtual {v3, v14, v2}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 986
    new-instance v15, Landroid/view/View;

    invoke-direct {v15, v0}, Landroid/view/View;-><init>(Landroid/content/Context;)V

    .line 987
    sget-boolean v2, Lio/kamihama/magianative/CNCNDownloadUI;->darkMode:Z

    if-eqz v2, :cond_1

    const v2, 0x22ffffff

    goto :goto_1

    :cond_1
    const/high16 v2, 0x18000000

    :goto_1
    invoke-virtual {v15, v2}, Landroid/view/View;->setBackgroundColor(I)V

    .line 988
    new-instance v2, Landroid/widget/LinearLayout$LayoutParams;

    const/4 v4, 0x1

    invoke-direct {v2, v7, v4}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    .line 990
    const/4 v4, 0x4

    invoke-static {v0, v4}, Lio/kamihama/magianative/CNCNDownloadUI;->dp(Landroid/content/Context;I)I

    move-result v4

    iput v4, v2, Landroid/widget/LinearLayout$LayoutParams;->topMargin:I

    .line 991
    invoke-virtual {v3, v15, v2}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 993
    sget-object v2, Lio/kamihama/magianative/CNCNDownloadUI;->slotList:Ljava/util/List;

    new-instance v3, Lio/kamihama/magianative/CNCNDownloadUI$SlotViews;

    move-object v10, v3

    invoke-direct/range {v10 .. v15}, Lio/kamihama/magianative/CNCNDownloadUI$SlotViews;-><init>(Landroid/widget/TextView;Landroid/widget/TextView;Landroid/widget/TextView;Landroid/widget/ProgressBar;Landroid/view/View;)V

    invoke-interface {v2, v3}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 923
    move v2, v9

    goto/16 :goto_0

    .line 995
    :cond_2
    return-void
.end method

.method private static renderAll()V
    .locals 17

    .line 1256
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->scheduleLogRefresh()V

    .line 1258
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->fileStatus:[I

    .line 1259
    sget-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->fileProgress:[I

    .line 1260
    sget-object v2, Lio/kamihama/magianative/CNCNDownloadUI;->fileSize:[F

    .line 1261
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->fileSpeed:[F

    .line 1262
    sget-object v4, Lio/kamihama/magianative/CNCNDownloadUI;->fileDownloaded:[F

    .line 1273
    nop

    .line 1274
    const/4 v5, 0x2

    const/16 v6, 0xf

    const/4 v8, 0x0

    if-eqz v2, :cond_2

    if-eqz v4, :cond_2

    if-eqz v0, :cond_2

    .line 1275
    const/4 v9, 0x0

    const/4 v10, 0x0

    const/4 v11, 0x0

    :goto_0
    if-ge v9, v6, :cond_3

    .line 1276
    aget v12, v2, v9

    cmpg-float v13, v12, v8

    if-gtz v13, :cond_0

    goto :goto_2

    .line 1277
    :cond_0
    add-float/2addr v10, v12

    .line 1280
    aget v13, v0, v9

    if-ne v13, v5, :cond_1

    goto :goto_1

    :cond_1
    aget v13, v4, v9

    invoke-static {v13, v12}, Ljava/lang/Math;->min(FF)F

    move-result v12

    :goto_1
    add-float/2addr v11, v12

    .line 1275
    :goto_2
    add-int/lit8 v9, v9, 0x1

    goto :goto_0

    .line 1284
    :cond_2
    const/4 v10, 0x0

    const/4 v11, 0x0

    :cond_3
    cmpl-float v9, v10, v8

    if-lez v9, :cond_4

    .line 1285
    const/high16 v9, 0x42c80000    # 100.0f

    mul-float v11, v11, v9

    div-float/2addr v11, v10

    float-to-long v9, v11

    const-wide/16 v11, 0x0

    invoke-static {v11, v12, v9, v10}, Ljava/lang/Math;->max(JJ)J

    move-result-wide v9

    const-wide/16 v11, 0x64

    invoke-static {v11, v12, v9, v10}, Ljava/lang/Math;->min(JJ)J

    move-result-wide v9

    long-to-int v10, v9

    goto :goto_4

    .line 1286
    :cond_4
    if-eqz v1, :cond_6

    .line 1287
    nop

    .line 1288
    const/4 v9, 0x0

    const/4 v10, 0x0

    :goto_3
    if-ge v9, v6, :cond_5

    aget v11, v1, v9

    add-int/2addr v10, v11

    add-int/lit8 v9, v9, 0x1

    goto :goto_3

    .line 1289
    :cond_5
    div-int/2addr v10, v6

    .line 1290
    goto :goto_4

    .line 1291
    :cond_6
    const/4 v10, 0x0

    .line 1293
    :goto_4
    sget-object v9, Lio/kamihama/magianative/CNCNDownloadUI;->progressBarOverall:Landroid/widget/ProgressBar;

    .line 1294
    if-eqz v9, :cond_7

    invoke-virtual {v9, v10}, Landroid/widget/ProgressBar;->setProgress(I)V

    .line 1298
    :cond_7
    nop

    .line 1299
    const/4 v9, 0x1

    if-eqz v3, :cond_9

    if-eqz v0, :cond_9

    .line 1300
    const/4 v10, 0x0

    const/4 v11, 0x0

    :goto_5
    if-ge v10, v6, :cond_a

    .line 1301
    aget v12, v0, v10

    if-ne v12, v9, :cond_8

    aget v12, v3, v10

    add-float/2addr v11, v12

    .line 1300
    :cond_8
    add-int/lit8 v10, v10, 0x1

    goto :goto_5

    .line 1304
    :cond_9
    const/4 v11, 0x0

    :cond_a
    sget-object v10, Lio/kamihama/magianative/CNCNDownloadUI;->tvSpeed:Landroid/widget/TextView;

    .line 1305
    if-eqz v10, :cond_b

    invoke-static {v11}, Lio/kamihama/magianative/CNCNDownloadUI;->formatMbps(F)Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 1308
    :cond_b
    sget-object v10, Lio/kamihama/magianative/CNCNDownloadUI;->vPhase:Landroid/widget/TextView;

    if-eqz v10, :cond_c

    sget-object v11, Lio/kamihama/magianative/CNCNDownloadUI;->phaseText:Ljava/lang/String;

    invoke-virtual {v10, v11}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 1309
    :cond_c
    sget-object v10, Lio/kamihama/magianative/CNCNDownloadUI;->vStatus:Landroid/widget/TextView;

    if-eqz v10, :cond_d

    sget-object v11, Lio/kamihama/magianative/CNCNDownloadUI;->detailText:Ljava/lang/String;

    invoke-virtual {v10, v11}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 1312
    :cond_d
    sget-object v10, Lio/kamihama/magianative/CNCNDownloadUI;->slotList:Ljava/util/List;

    invoke-interface {v10}, Ljava/util/List;->isEmpty()Z

    move-result v10

    const-string v11, "  "

    const-string v12, " / "

    if-nez v10, :cond_16

    if-eqz v0, :cond_16

    if-eqz v1, :cond_16

    .line 1313
    const/4 v10, 0x0

    :goto_6
    if-ge v10, v6, :cond_16

    sget-object v13, Lio/kamihama/magianative/CNCNDownloadUI;->slotList:Ljava/util/List;

    invoke-interface {v13}, Ljava/util/List;->size()I

    move-result v14

    if-ge v10, v14, :cond_16

    .line 1314
    invoke-interface {v13, v10}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v13

    check-cast v13, Lio/kamihama/magianative/CNCNDownloadUI$SlotViews;

    .line 1315
    aget v14, v0, v10

    .line 1316
    aget v15, v1, v10

    .line 1317
    iget-object v7, v13, Lio/kamihama/magianative/CNCNDownloadUI$SlotViews;->bar:Landroid/widget/ProgressBar;

    invoke-virtual {v7, v15}, Landroid/widget/ProgressBar;->setProgress(I)V

    .line 1320
    const v6, -0x994496

    packed-switch v14, :pswitch_data_0

    .line 1324
    const v16, 0x55888888

    goto :goto_7

    .line 1323
    :pswitch_0
    const v16, -0x1ac6cb

    goto :goto_7

    .line 1322
    :pswitch_1
    const v16, -0x994496

    goto :goto_7

    .line 1321
    :pswitch_2
    sget v16, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_ACCENT:I

    .line 1326
    :goto_7
    nop

    .line 1327
    iget-object v9, v13, Lio/kamihama/magianative/CNCNDownloadUI$SlotViews;->bar:Landroid/widget/ProgressBar;

    .line 1328
    invoke-static/range {v16 .. v16}, Landroid/content/res/ColorStateList;->valueOf(I)Landroid/content/res/ColorStateList;

    move-result-object v7

    .line 1327
    invoke-virtual {v9, v7}, Landroid/widget/ProgressBar;->setProgressTintList(Landroid/content/res/ColorStateList;)V

    .line 1331
    iget-object v7, v13, Lio/kamihama/magianative/CNCNDownloadUI$SlotViews;->retryView:Landroid/widget/TextView;

    const/4 v9, 0x3

    if-ne v14, v9, :cond_e

    const/4 v9, 0x0

    goto :goto_8

    :cond_e
    const/16 v16, 0x8

    const/16 v9, 0x8

    :goto_8
    invoke-virtual {v7, v9}, Landroid/widget/TextView;->setVisibility(I)V

    .line 1332
    if-ne v14, v5, :cond_10

    .line 1333
    iget-object v7, v13, Lio/kamihama/magianative/CNCNDownloadUI$SlotViews;->infoView:Landroid/widget/TextView;

    invoke-virtual {v7, v6}, Landroid/widget/TextView;->setTextColor(I)V

    .line 1334
    iget-object v6, v13, Lio/kamihama/magianative/CNCNDownloadUI$SlotViews;->infoView:Landroid/widget/TextView;

    if-eqz v2, :cond_f

    aget v7, v2, v10

    cmpl-float v7, v7, v8

    if-lez v7, :cond_f

    .line 1335
    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "\u2713 "

    invoke-virtual {v7, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    aget v9, v2, v10

    invoke-static {v9}, Lio/kamihama/magianative/CNCNDownloadUI;->formatMb(F)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v7, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    goto :goto_9

    :cond_f
    const-string v7, "\u2713"

    .line 1334
    :goto_9
    invoke-virtual {v6, v7}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    const/4 v6, 0x1

    goto/16 :goto_a

    .line 1336
    :cond_10
    const/4 v6, 0x3

    if-ne v14, v6, :cond_11

    .line 1337
    iget-object v6, v13, Lio/kamihama/magianative/CNCNDownloadUI$SlotViews;->infoView:Landroid/widget/TextView;

    const v7, -0x1ac6cb

    invoke-virtual {v6, v7}, Landroid/widget/TextView;->setTextColor(I)V

    .line 1338
    iget-object v6, v13, Lio/kamihama/magianative/CNCNDownloadUI$SlotViews;->infoView:Landroid/widget/TextView;

    const-string v7, "\u2717"

    invoke-virtual {v6, v7}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    const/4 v6, 0x1

    goto/16 :goto_a

    .line 1339
    :cond_11
    const/4 v6, 0x1

    if-ne v14, v6, :cond_14

    .line 1340
    iget-object v7, v13, Lio/kamihama/magianative/CNCNDownloadUI$SlotViews;->infoView:Landroid/widget/TextView;

    sget v9, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_SUB:I

    invoke-virtual {v7, v9}, Landroid/widget/TextView;->setTextColor(I)V

    .line 1341
    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    .line 1342
    invoke-virtual {v7, v15}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v9

    const/16 v14, 0x25

    invoke-virtual {v9, v14}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 1343
    if-eqz v4, :cond_12

    if-eqz v2, :cond_12

    aget v9, v2, v10

    cmpl-float v9, v9, v8

    if-lez v9, :cond_12

    .line 1344
    invoke-virtual {v7, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    aget v14, v4, v10

    invoke-static {v14}, Lio/kamihama/magianative/CNCNDownloadUI;->formatMb(F)Ljava/lang/String;

    move-result-object v14

    invoke-virtual {v9, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    .line 1345
    invoke-virtual {v9, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    aget v14, v2, v10

    invoke-static {v14}, Lio/kamihama/magianative/CNCNDownloadUI;->formatMb(F)Ljava/lang/String;

    move-result-object v14

    invoke-virtual {v9, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 1347
    :cond_12
    if-eqz v3, :cond_13

    aget v9, v3, v10

    cmpl-float v9, v9, v8

    if-lez v9, :cond_13

    .line 1348
    invoke-virtual {v7, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    aget v14, v3, v10

    invoke-static {v14}, Lio/kamihama/magianative/CNCNDownloadUI;->formatMbps(F)Ljava/lang/String;

    move-result-object v14

    invoke-virtual {v9, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 1350
    :cond_13
    iget-object v9, v13, Lio/kamihama/magianative/CNCNDownloadUI$SlotViews;->infoView:Landroid/widget/TextView;

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-virtual {v9, v7}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 1351
    goto :goto_a

    .line 1355
    :cond_14
    iget-object v7, v13, Lio/kamihama/magianative/CNCNDownloadUI$SlotViews;->infoView:Landroid/widget/TextView;

    sget v9, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_SUB:I

    invoke-virtual {v7, v9}, Landroid/widget/TextView;->setTextColor(I)V

    .line 1356
    if-eqz v2, :cond_15

    aget v7, v2, v10

    cmpl-float v7, v7, v8

    if-lez v7, :cond_15

    .line 1357
    iget-object v7, v13, Lio/kamihama/magianative/CNCNDownloadUI$SlotViews;->infoView:Landroid/widget/TextView;

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v13, "\u7b49\u5f85\u4e2d \u00b7 "

    invoke-virtual {v9, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    aget v13, v2, v10

    invoke-static {v13}, Lio/kamihama/magianative/CNCNDownloadUI;->formatMb(F)Ljava/lang/String;

    move-result-object v13

    invoke-virtual {v9, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v7, v9}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    goto :goto_a

    .line 1359
    :cond_15
    iget-object v7, v13, Lio/kamihama/magianative/CNCNDownloadUI$SlotViews;->infoView:Landroid/widget/TextView;

    const-string v9, "\u7b49\u5f85\u4e2d"

    invoke-virtual {v7, v9}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 1313
    :goto_a
    add-int/lit8 v10, v10, 0x1

    const/16 v6, 0xf

    const/4 v9, 0x1

    goto/16 :goto_6

    .line 1366
    :cond_16
    sget-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->vAggregate:Landroid/widget/TextView;

    if-eqz v1, :cond_19

    if-eqz v0, :cond_19

    .line 1367
    nop

    .line 1368
    const/4 v1, 0x0

    const/4 v3, 0x0

    :goto_b
    const/16 v6, 0xf

    if-ge v1, v6, :cond_18

    aget v6, v0, v1

    if-ne v6, v5, :cond_17

    add-int/lit8 v3, v3, 0x1

    :cond_17
    add-int/lit8 v1, v1, 0x1

    goto :goto_b

    .line 1369
    :cond_18
    sget-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->vAggregate:Landroid/widget/TextView;

    new-instance v6, Ljava/lang/StringBuilder;

    invoke-direct {v6}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v6, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    const/16 v6, 0xf

    invoke-virtual {v3, v6}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v6, " \u6587\u4ef6"

    invoke-virtual {v3, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v1, v3}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 1371
    :cond_19
    sget-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->vOverallText:Landroid/widget/TextView;

    if-eqz v1, :cond_1e

    .line 1372
    nop

    .line 1373
    const-string v1, "\u603b\u8fdb\u5ea6"

    if-eqz v2, :cond_1d

    if-eqz v4, :cond_1d

    if-eqz v0, :cond_1d

    .line 1374
    nop

    .line 1375
    const/4 v3, 0x0

    const/4 v6, 0x0

    const/4 v7, 0x0

    :goto_c
    const/16 v9, 0xf

    if-ge v7, v9, :cond_1c

    .line 1376
    aget v10, v2, v7

    cmpg-float v13, v10, v8

    if-gtz v13, :cond_1a

    goto :goto_e

    .line 1377
    :cond_1a
    add-float/2addr v3, v10

    .line 1378
    aget v13, v0, v7

    if-ne v13, v5, :cond_1b

    goto :goto_d

    :cond_1b
    aget v13, v4, v7

    invoke-static {v13, v10}, Ljava/lang/Math;->min(FF)F

    move-result v10

    :goto_d
    add-float/2addr v6, v10

    .line 1375
    :goto_e
    add-int/lit8 v7, v7, 0x1

    goto :goto_c

    .line 1380
    :cond_1c
    cmpl-float v0, v3, v8

    if-lez v0, :cond_1d

    .line 1381
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {v6}, Lio/kamihama/magianative/CNCNDownloadUI;->formatMb(F)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-static {v3}, Lio/kamihama/magianative/CNCNDownloadUI;->formatMb(F)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 1384
    :cond_1d
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->vOverallText:Landroid/widget/TextView;

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 1386
    :cond_1e
    return-void

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method private static renderLogModal()V
    .locals 2

    .line 1054
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->logModal:Landroid/widget/FrameLayout;

    if-eqz v0, :cond_2

    sget-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->tvLog:Landroid/widget/TextView;

    if-nez v1, :cond_0

    goto :goto_0

    .line 1055
    :cond_0
    invoke-virtual {v0}, Landroid/widget/FrameLayout;->getVisibility()I

    move-result v0

    if-eqz v0, :cond_1

    return-void

    .line 1056
    :cond_1
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->tvLog:Landroid/widget/TextView;

    const/4 v1, 0x0

    invoke-static {v1}, Lio/kamihama/magianative/CNCNDownloadUI;->composeLogText(Z)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 1057
    return-void

    .line 1054
    :cond_2
    :goto_0
    return-void
.end method

.method private static scheduleLogRefresh()V
    .locals 4

    .line 1088
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->uiHandler:Landroid/os/Handler;

    .line 1089
    if-eqz v0, :cond_3

    sget-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->logModal:Landroid/widget/FrameLayout;

    if-nez v1, :cond_0

    goto :goto_0

    .line 1090
    :cond_0
    invoke-virtual {v1}, Landroid/widget/FrameLayout;->getVisibility()I

    move-result v1

    if-eqz v1, :cond_1

    return-void

    .line 1091
    :cond_1
    sget-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->LOG_DIRTY:Ljava/util/concurrent/atomic/AtomicBoolean;

    const/4 v2, 0x0

    const/4 v3, 0x1

    invoke-virtual {v1, v2, v3}, Ljava/util/concurrent/atomic/AtomicBoolean;->compareAndSet(ZZ)Z

    move-result v1

    if-eqz v1, :cond_2

    .line 1092
    new-instance v1, Lio/kamihama/magianative/CNCNDownloadUI$RenderLog;

    const/4 v2, 0x0

    invoke-direct {v1, v2}, Lio/kamihama/magianative/CNCNDownloadUI$RenderLog;-><init>(Lio/kamihama/magianative/CNCNDownloadUI$1;)V

    const-wide/16 v2, 0xfa

    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 1094
    :cond_2
    return-void

    .line 1089
    :cond_3
    :goto_0
    return-void
.end method

.method public static setDownloadSpeed(IF)V
    .locals 1

    .line 1571
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->fileSpeed:[F

    .line 1572
    if-eqz v0, :cond_0

    .line 1573
    aput p1, v0, p0

    .line 1575
    :cond_0
    return-void
.end method

.method public static setFileDownloaded(IF)V
    .locals 1

    .line 1578
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->fileDownloaded:[F

    .line 1579
    if-eqz v0, :cond_0

    .line 1580
    aput p1, v0, p0

    .line 1582
    :cond_0
    return-void
.end method

.method public static setFileSize(IF)V
    .locals 1

    .line 1585
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->fileSize:[F

    .line 1586
    if-eqz v0, :cond_0

    .line 1587
    aput p1, v0, p0

    .line 1589
    :cond_0
    return-void
.end method

.method public static show(Landroid/app/Activity;)V
    .locals 5

    .line 1592
    const-string v0, "\u754c\u9762"

    sget-boolean v1, Lio/kamihama/magianative/CNCNDownloadUI;->isShowing:Z

    if-eqz v1, :cond_0

    .line 1593
    return-void

    .line 1596
    :cond_0
    const/4 v1, 0x1

    const/4 v2, 0x0

    :try_start_0
    new-instance v3, Landroid/os/Handler;

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v4

    invoke-direct {v3, v4}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    sput-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->uiHandler:Landroid/os/Handler;

    .line 1597
    new-instance v3, Lio/kamihama/magianative/CNCNDownloadUI$CreateUIRunnable;

    invoke-direct {v3, p0}, Lio/kamihama/magianative/CNCNDownloadUI$CreateUIRunnable;-><init>(Landroid/app/Activity;)V

    invoke-virtual {p0, v3}, Landroid/app/Activity;->runOnUiThread(Ljava/lang/Runnable;)V

    .line 1598
    const/4 p0, 0x0

    .line 1599
    :goto_0
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->tvLog:Landroid/widget/TextView;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-nez v3, :cond_1

    const/16 v3, 0x1e

    if-ge p0, v3, :cond_1

    .line 1600
    add-int/lit8 p0, p0, 0x1

    .line 1602
    const-wide/16 v3, 0x64

    :try_start_1
    invoke-static {v3, v4}, Ljava/lang/Thread;->sleep(J)V
    :try_end_1
    .catch Ljava/lang/InterruptedException; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 1604
    :goto_1
    goto :goto_0

    .line 1603
    :catch_0
    move-exception v3

    goto :goto_1

    .line 1610
    :cond_1
    :try_start_2
    sget-object p0, Lio/kamihama/magianative/CNCNDownloadUI;->overlayView:Landroid/widget/FrameLayout;

    if-eqz p0, :cond_2

    const/4 p0, 0x1

    goto :goto_2

    :cond_2
    const/4 p0, 0x0

    :goto_2
    sput-boolean p0, Lio/kamihama/magianative/CNCNDownloadUI;->isShowing:Z

    .line 1611
    if-nez p0, :cond_3

    .line 1612
    const-string p0, "\u6d6e\u5c42\u521b\u5efa\u5931\u8d25\uff08overlayView \u4e3a\u7a7a\uff09\uff0c\u5c06\u5141\u8bb8\u540e\u7eed\u91cd\u8bd5"

    invoke-static {v0, p0}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    .line 1617
    :cond_3
    goto :goto_4

    .line 1614
    :catchall_0
    move-exception p0

    .line 1615
    sget-object v3, Lio/kamihama/magianative/CNCNDownloadUI;->overlayView:Landroid/widget/FrameLayout;

    if-eqz v3, :cond_4

    goto :goto_3

    :cond_4
    const/4 v1, 0x0

    :goto_3
    sput-boolean v1, Lio/kamihama/magianative/CNCNDownloadUI;->isShowing:Z

    .line 1616
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "show() \u5931\u8d25: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1, p0}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 1618
    :goto_4
    return-void
.end method

.method public static throttledUpdate()V
    .locals 6

    .line 1621
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->uiHandler:Landroid/os/Handler;

    .line 1622
    if-eqz v0, :cond_1

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v1

    sget-wide v3, Lio/kamihama/magianative/CNCNDownloadUI;->lastUpdateTime:J

    sub-long/2addr v1, v3

    const-wide/16 v3, 0x1f4

    cmp-long v5, v1, v3

    if-gez v5, :cond_0

    goto :goto_0

    .line 1625
    :cond_0
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v1

    sput-wide v1, Lio/kamihama/magianative/CNCNDownloadUI;->lastUpdateTime:J

    .line 1626
    new-instance v1, Lio/kamihama/magianative/CNCNDownloadUI$UpdateRunnable;

    invoke-direct {v1}, Lio/kamihama/magianative/CNCNDownloadUI$UpdateRunnable;-><init>()V

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 1627
    return-void

    .line 1623
    :cond_1
    :goto_0
    return-void
.end method

.method private static tintBar(Landroid/widget/ProgressBar;I)V
    .locals 0

    .line 998
    nop

    .line 999
    nop

    .line 1000
    invoke-static {p1}, Landroid/content/res/ColorStateList;->valueOf(I)Landroid/content/res/ColorStateList;

    move-result-object p1

    .line 999
    invoke-virtual {p0, p1}, Landroid/widget/ProgressBar;->setProgressTintList(Landroid/content/res/ColorStateList;)V

    .line 1001
    sget p1, Lio/kamihama/magianative/CNCNDownloadUI;->COLOR_BAR_BG:I

    .line 1002
    invoke-static {p1}, Landroid/content/res/ColorStateList;->valueOf(I)Landroid/content/res/ColorStateList;

    move-result-object p1

    .line 1001
    invoke-virtual {p0, p1}, Landroid/widget/ProgressBar;->setProgressBackgroundTintList(Landroid/content/res/ColorStateList;)V

    .line 1004
    return-void
.end method

.method private static toast(Landroid/app/Activity;Ljava/lang/String;)V
    .locals 1

    .line 849
    const/4 v0, 0x1

    :try_start_0
    invoke-static {p0, p1, v0}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object p0

    invoke-virtual {p0}, Landroid/widget/Toast;->show()V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    goto :goto_0

    .line 850
    :catchall_0
    move-exception p0

    :goto_0
    nop

    .line 851
    return-void
.end method

.method private static toggleTheme(Landroid/app/Activity;)V
    .locals 3

    .line 1208
    :try_start_0
    sget-boolean v0, Lio/kamihama/magianative/CNCNDownloadUI;->darkMode:Z

    const/4 v1, 0x0

    if-nez v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    sput-boolean v0, Lio/kamihama/magianative/CNCNDownloadUI;->darkMode:Z

    .line 1209
    const-string v0, "cnv_bootstrap_ui"

    invoke-virtual {p0, v0, v1}, Landroid/app/Activity;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v0

    .line 1211
    invoke-interface {v0}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    const-string v1, "dark_mode"

    sget-boolean v2, Lio/kamihama/magianative/CNCNDownloadUI;->darkMode:Z

    invoke-interface {v0, v1, v2}, Landroid/content/SharedPreferences$Editor;->putBoolean(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->apply()V

    .line 1212
    sget-boolean v0, Lio/kamihama/magianative/CNCNDownloadUI;->darkMode:Z

    invoke-static {v0}, Lio/kamihama/magianative/CNCNDownloadUI;->loadPalette(Z)V

    .line 1214
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->decorView:Landroid/view/ViewGroup;

    .line 1215
    sget-object v1, Lio/kamihama/magianative/CNCNDownloadUI;->overlayView:Landroid/widget/FrameLayout;

    .line 1216
    if-nez v0, :cond_1

    return-void

    .line 1217
    :cond_1
    invoke-static {p0}, Lio/kamihama/magianative/CNCNDownloadUI;->buildOverlay(Landroid/app/Activity;)Landroid/widget/FrameLayout;

    move-result-object p0

    .line 1218
    if-eqz v1, :cond_2

    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->removeView(Landroid/view/View;)V

    .line 1219
    :cond_2
    new-instance v1, Landroid/view/ViewGroup$LayoutParams;

    const/4 v2, -0x1

    invoke-direct {v1, v2, v2}, Landroid/view/ViewGroup$LayoutParams;-><init>(II)V

    invoke-virtual {v0, p0, v1}, Landroid/view/ViewGroup;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 1222
    sput-object p0, Lio/kamihama/magianative/CNCNDownloadUI;->overlayView:Landroid/widget/FrameLayout;

    .line 1224
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->renderAll()V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 1227
    goto :goto_1

    .line 1225
    :catchall_0
    move-exception p0

    .line 1226
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "\u4e3b\u9898\u5207\u6362\u5931\u8d25: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    const-string v0, "\u754c\u9762"

    invoke-static {v0, p0}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;)V

    .line 1228
    :goto_1
    return-void
.end method

.method public static updateFileProgress(II)V
    .locals 2

    .line 1630
    sget-object v0, Lio/kamihama/magianative/CNCNDownloadUI;->fileProgress:[I

    .line 1631
    if-eqz v0, :cond_0

    .line 1632
    aput p1, v0, p0

    .line 1633
    sget-object p1, Lio/kamihama/magianative/CNCNDownloadUI;->fileStatus:[I

    .line 1634
    if-eqz p1, :cond_0

    aget v0, p1, p0

    const/4 v1, 0x2

    if-eq v0, v1, :cond_0

    .line 1635
    const/4 v0, 0x1

    aput v0, p1, p0

    .line 1638
    :cond_0
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->throttledUpdate()V

    .line 1639
    return-void
.end method

.method public static updateSimple(Ljava/lang/String;Ljava/lang/String;I)V
    .locals 0

    .line 1648
    if-eqz p0, :cond_0

    invoke-virtual {p0}, Ljava/lang/String;->length()I

    move-result p2

    if-lez p2, :cond_0

    sput-object p0, Lio/kamihama/magianative/CNCNDownloadUI;->phaseText:Ljava/lang/String;

    .line 1649
    :cond_0
    if-eqz p1, :cond_1

    invoke-virtual {p1}, Ljava/lang/String;->length()I

    move-result p0

    if-lez p0, :cond_1

    sput-object p1, Lio/kamihama/magianative/CNCNDownloadUI;->detailText:Ljava/lang/String;

    .line 1650
    :cond_1
    sget-object p0, Lio/kamihama/magianative/CNCNDownloadUI;->uiHandler:Landroid/os/Handler;

    .line 1651
    if-eqz p0, :cond_2

    .line 1652
    new-instance p1, Lio/kamihama/magianative/CNCNDownloadUI$UpdateRunnable;

    invoke-direct {p1}, Lio/kamihama/magianative/CNCNDownloadUI$UpdateRunnable;-><init>()V

    invoke-virtual {p0, p1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 1654
    :cond_2
    return-void
.end method
