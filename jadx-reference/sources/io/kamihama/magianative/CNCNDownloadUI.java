package io.kamihama.magianative;

import android.R;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.view.GravityCompat;
import cn.thinkingdata.core.router.TRouterMap;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes2.dex */
public class CNCNDownloadUI {
    private static final String BG_ASSET = "cnv/background_light.png";
    private static int COLOR_ACCENT = 0;
    private static int COLOR_ACCENT2 = 0;
    private static int COLOR_BAR_BG = 0;
    private static int COLOR_CARD_STK = 0;
    private static int COLOR_DIM = 0;
    private static int COLOR_GLASS = 0;
    private static int COLOR_GLASS_STK = 0;
    private static int COLOR_LOG_PANEL_BG = 0;
    private static int COLOR_LOG_PANEL_TEXT = 0;
    private static int COLOR_LOG_PILL = 0;
    private static int COLOR_SUB = 0;
    private static int COLOR_TEXT = 0;
    private static final long CONFIRM_WINDOW_MS = 6000;
    private static final int FILE_COUNT = 15;
    private static final String FOOTER_CREDIT = "核心开发: B站 @MadeInMagius【B站xhs tx同名】 | 国内加速+修复：@PhotonFlow | 如果需要联系请先b站私信，会提供群聊 | 该游戏支持后续剧情更新";
    private static final int KIND_HEAD = 1;
    private static final int KIND_ITEM = 2;
    private static final int KIND_SUB = 3;
    private static final int KIND_TITLE = 0;
    private static final String LOGO_ASSET = "cnv/logo.png";
    private static final String PREFS_NAME = "cnv_bootstrap_ui";
    private static final String PREF_DARK_MODE = "dark_mode";
    private static final String URL_GITHUB = "https://github.com/MagirecoCN-Revival-Project";
    public static ViewGroup decorView;
    private static GradientDrawable githubChipBg;
    private static Activity hostActivity;
    public static boolean isShowing;
    public static long lastUpdateTime;
    private static FrameLayout logModal;
    private static GradientDrawable logPillBg;
    public static FrameLayout overlayView;
    public static ProgressBar progressBarOverall;
    private static LinearLayout slotContainer;
    private static GradientDrawable themeChipBg;
    public static TextView tvLog;
    public static TextView tvSpeed;
    public static Handler uiHandler;
    private static TextView vAggregate;
    private static LinearLayout vContribList;
    private static TextView vGitHubChip;
    private static TextView vLogPill;
    private static ScrollView vLogScroll;
    private static TextView vOverallText;
    private static TextView vPhase;
    private static TextView vStatus;
    private static TextView vThemeChip;
    public static final String[] FILE_URLS = {"https://assets.magireco.top/cn_base_00_db.zip", "https://assets.magireco.top/cn_base_01_json.zip", "https://assets.magireco.top/cn_base_02.zip", "https://assets.magireco.top/cn_base_03.zip", "https://assets.magireco.top/cn_base_04.zip", "https://assets.magireco.top/cn_base_05.zip", "https://assets.magireco.top/cn_base_06.zip", "https://assets.magireco.top/cn_magica_resource.zip", "https://assets.magireco.top/cn_scenario_img.zip", "https://assets.magireco.top/cn_voice_01.zip", "https://assets.magireco.top/cn_voice_02_done.zip", "https://assets.magireco.top/cn_js_update.zip", "https://assets.magireco.top/movie.zip", "https://assets.magireco.top/movie2.zip", "https://assets.magireco.top/cn_scenario_update.zip"};
    public static final String[] FILE_NAMES = {"cn_base_00_db.zip", "cn_base_01_json.zip", "cn_base_02.zip", "cn_base_03.zip", "cn_base_04.zip", "cn_base_05.zip", "cn_base_06.zip", "cn_magica_resource.zip", "cn_scenario_img.zip", "cn_voice_01.zip", "cn_voice_02_done.zip", "cn_js_update.zip", "movie.zip", "movie2.zip", "cn_scenario_update.zip"};
    public static int[] fileStatus = new int[15];
    public static int[] fileProgress = new int[15];
    public static float[] fileSize = new float[15];
    public static float[] fileSpeed = new float[15];
    public static float[] fileDownloaded = new float[15];
    private static volatile String phaseText = "准备中";
    private static volatile String detailText = "正在初始化下载器…";
    private static boolean darkMode = false;
    private static final int[] CONTRIB_PALETTE = {-12747777, -7620486, -1677408, -11552794, -874918, -6583041, -11352136, -1739917};
    private static final int[] CREDIT_KINDS = {0, 2, 3, 1, 2, 2, 2, 1, 2, 2, 2, 2, 2};
    private static final String[] CREDIT_TEXTS = {"魔法纪录Totentanz中文化", "【核心逆向开发】MadeInMagius【B站ID】", "(独立完成汉化引擎以及下载系统和日服国服资源合并)", "其他个人网站", "magireader.pages.dev【魔法纪录剧情中日双语阅读网站】", "magiaexedralive2dviewer.pages.dev【MagiaExedra和魔法纪录Live2D网站】", "magireco-call-search-cn.pages.dev【魔法少女称呼关系搜索与身高对比网站】", "【协助与鸣谢】", "国服文件之外的翻译和校对：水银h2oag【阅读器网站为主，资源已同步至游戏】", "下载加速及资源自动化推送：CyberNova", "国服数据留存：segfault", "项目官网：www.magireco.top【通往其他个人网站和提供联系方式】", "bilibili视频教程：BV1faRiBBExk"};
    private static final String[] CREDIT_URLS = {"", "", "", "", "https://magireader.pages.dev", "https://magiaexedralive2dviewer.pages.dev", "https://magireco-call-search-cn.pages.dev", "", "", "", "", "https://www.magireco.top", "https://www.bilibili.com/video/BV1faRiBBExk"};
    private static String pendingUrl = null;
    private static long pendingAtMs = 0;
    private static final List<SlotViews> slotList = new ArrayList();

    static /* synthetic */ String access$600() {
        return composeLogText();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void loadPalette(boolean z) {
        if (z) {
            COLOR_CARD_STK = 1442808000;
            COLOR_ACCENT = -34110;
            COLOR_ACCENT2 = -4685856;
            COLOR_TEXT = -1055496;
            COLOR_SUB = -4610360;
            COLOR_BAR_BG = 1157627903;
            COLOR_LOG_PILL = -419467339;
            COLOR_DIM = -1442840576;
            COLOR_LOG_PANEL_BG = -15003607;
            COLOR_LOG_PANEL_TEXT = -660229;
            COLOR_GLASS = -870837974;
            COLOR_GLASS_STK = 1157595328;
            return;
        }
        COLOR_CARD_STK = 867515532;
        COLOR_ACCENT = -2739324;
        COLOR_ACCENT2 = -6530110;
        COLOR_TEXT = -14017989;
        COLOR_SUB = -9547146;
        COLOR_BAR_BG = 570425344;
        COLOR_LOG_PILL = -422169724;
        COLOR_DIM = -2013265920;
        COLOR_LOG_PANEL_BG = -1;
        COLOR_LOG_PANEL_TEXT = -14017989;
        COLOR_GLASS = -855638017;
        COLOR_GLASS_STK = 867515532;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class SlotViews {
        final ProgressBar bar;
        final View divider;
        final TextView infoView;
        final TextView nameView;

        SlotViews(TextView textView, TextView textView2, ProgressBar progressBar, View view) {
            this.nameView = textView;
            this.infoView = textView2;
            this.bar = progressBar;
            this.divider = view;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class GlassPanelView extends View {
        private Bitmap blurBitmap;
        private final RectF bounds;
        private final int fillColor;
        private final Paint paint;
        private final float radius;
        private final int strokeColor;

        GlassPanelView(Context context, int i, int i2, float f) {
            super(context);
            this.paint = new Paint(1);
            this.bounds = new RectF();
            this.fillColor = i;
            this.strokeColor = i2;
            this.radius = f;
        }

        void setBlurBitmap(Bitmap bitmap) {
            this.blurBitmap = bitmap;
            postInvalidate();
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            this.bounds.set(0.0f, 0.0f, getWidth(), getHeight());
            if (this.blurBitmap != null) {
                Paint paint = new Paint(1);
                Matrix matrix = new Matrix();
                matrix.setScale(getWidth() / this.blurBitmap.getWidth(), getHeight() / this.blurBitmap.getHeight());
                BitmapShader bitmapShader = new BitmapShader(this.blurBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                bitmapShader.setLocalMatrix(matrix);
                paint.setShader(bitmapShader);
                RectF rectF = this.bounds;
                float f = this.radius;
                canvas.drawRoundRect(rectF, f, f, paint);
                this.paint.setColor(this.fillColor & (-1996488705));
                this.paint.setStyle(Paint.Style.FILL);
                RectF rectF2 = this.bounds;
                float f2 = this.radius;
                canvas.drawRoundRect(rectF2, f2, f2, this.paint);
            } else {
                this.paint.setColor(this.fillColor);
                this.paint.setStyle(Paint.Style.FILL);
                RectF rectF3 = this.bounds;
                float f3 = this.radius;
                canvas.drawRoundRect(rectF3, f3, f3, this.paint);
            }
            this.paint.setColor(this.strokeColor);
            this.paint.setStyle(Paint.Style.STROKE);
            this.paint.setStrokeWidth(2.0f);
            RectF rectF4 = this.bounds;
            float f4 = this.radius;
            canvas.drawRoundRect(rectF4, f4, f4, this.paint);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class DotView extends View {
        private final Paint p;

        DotView(Context context, int i) {
            super(context);
            Paint paint = new Paint(1);
            this.p = paint;
            paint.setColor(i);
            paint.setStyle(Paint.Style.FILL);
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            canvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, Math.min(getWidth(), getHeight()) / 2.0f, this.p);
        }
    }

    private static int dp(Context context, int i) {
        return (int) ((i * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    private static LinearLayout.LayoutParams lpRow(int i, int i2) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.topMargin = i;
        layoutParams.bottomMargin = i2;
        return layoutParams;
    }

    /* loaded from: classes2.dex */
    private static final class ApplyBitmap implements Runnable {
        private final Bitmap bitmap;
        private final ImageView target;

        ApplyBitmap(ImageView imageView, Bitmap bitmap) {
            this.target = imageView;
            this.bitmap = bitmap;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.target.setImageBitmap(this.bitmap);
            } catch (Throwable th) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class AssetBitmapLoader implements Runnable {
        private final Activity act;
        private final String assetPath;
        private final ImageView target;

        AssetBitmapLoader(Activity activity, String str, ImageView imageView) {
            this.act = activity;
            this.assetPath = str;
            this.target = imageView;
        }

        /* JADX DEBUG: Another duplicated slice has different insns count: {[]}, finally: {[MOVE_EXCEPTION, INVOKE, MOVE_EXCEPTION] complete} */
        @Override // java.lang.Runnable
        public void run() {
            try {
                InputStream open = this.act.getAssets().open(this.assetPath);
                try {
                    Bitmap decodeStream = BitmapFactory.decodeStream(open);
                    if (decodeStream == null) {
                        return;
                    }
                    this.act.runOnUiThread(new ApplyBitmap(this.target, decodeStream));
                } finally {
                    try {
                        open.close();
                    } catch (Throwable th) {
                    }
                }
            } catch (Throwable th2) {
            }
        }
    }

    private static void loadBitmapFromAssets(Activity activity, String str, ImageView imageView) {
        new Thread(new AssetBitmapLoader(activity, str, imageView), "cnv-img-load").start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static FrameLayout buildOverlay(final Activity activity) {
        FrameLayout frameLayout = new FrameLayout(activity);
        frameLayout.setClickable(true);
        ImageView imageView = new ImageView(activity);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setBackgroundColor(darkMode ? -15397342 : -792075);
        loadBitmapFromAssets(activity, BG_ASSET, imageView);
        if (darkMode) {
            imageView.setColorFilter(-1442840576, PorterDuff.Mode.SRC_ATOP);
        }
        frameLayout.addView(imageView, new FrameLayout.LayoutParams(-1, -1));
        View glassPanelView = new GlassPanelView(activity, COLOR_GLASS, COLOR_GLASS_STK, dp(activity, 20));
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        layoutParams.leftMargin = dp(activity, 14);
        layoutParams.rightMargin = dp(activity, 14);
        layoutParams.topMargin = dp(activity, 52);
        layoutParams.bottomMargin = dp(activity, 40);
        frameLayout.addView(glassPanelView, layoutParams);
        LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setOrientation(0);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, -1);
        layoutParams2.leftMargin = dp(activity, 14) + dp(activity, 14);
        layoutParams2.rightMargin = dp(activity, 14) + dp(activity, 14);
        layoutParams2.topMargin = dp(activity, 52) + dp(activity, 12);
        layoutParams2.bottomMargin = dp(activity, 40) + dp(activity, 12);
        frameLayout.addView(linearLayout, layoutParams2);
        LinearLayout linearLayout2 = new LinearLayout(activity);
        linearLayout2.setOrientation(1);
        linearLayout2.setPadding(dp(activity, 4), 0, dp(activity, 12), 0);
        linearLayout.addView(linearLayout2, new LinearLayout.LayoutParams(0, -1, 0.38f));
        ImageView imageView2 = new ImageView(activity);
        imageView2.setScaleType(ImageView.ScaleType.FIT_CENTER);
        loadBitmapFromAssets(activity, LOGO_ASSET, imageView2);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-1, dp(activity, 64));
        layoutParams3.bottomMargin = dp(activity, 8);
        linearLayout2.addView(imageView2, layoutParams3);
        View view = new View(activity);
        view.setBackgroundColor(COLOR_CARD_STK);
        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(-1, dp(activity, 1));
        layoutParams4.bottomMargin = dp(activity, 8);
        linearLayout2.addView(view, layoutParams4);
        ScrollView scrollView = new ScrollView(activity);
        scrollView.setFillViewport(true);
        LinearLayout linearLayout3 = new LinearLayout(activity);
        linearLayout3.setOrientation(1);
        scrollView.addView(linearLayout3, new FrameLayout.LayoutParams(-1, -2));
        vContribList = linearLayout3;
        linearLayout2.addView(scrollView, new LinearLayout.LayoutParams(-1, 0, 1.0f));
        populateContributors(activity);
        LinearLayout linearLayout4 = new LinearLayout(activity);
        linearLayout4.setOrientation(1);
        linearLayout4.setPadding(dp(activity, 10), dp(activity, 4), dp(activity, 4), dp(activity, 4));
        linearLayout.addView(linearLayout4, new LinearLayout.LayoutParams(0, -1, 0.62f));
        LinearLayout linearLayout5 = new LinearLayout(activity);
        linearLayout5.setOrientation(0);
        linearLayout5.setGravity(16);
        linearLayout4.addView(linearLayout5, lpRow(0, dp(activity, 4)));
        TextView textView = new TextView(activity);
        vPhase = textView;
        textView.setText(phaseText);
        vPhase.setTextColor(COLOR_ACCENT);
        vPhase.setTextSize(2, 13.0f);
        TextView textView2 = vPhase;
        textView2.setTypeface(textView2.getTypeface(), 1);
        vPhase.setSingleLine(true);
        vPhase.setEllipsize(TextUtils.TruncateAt.END);
        linearLayout5.addView(vPhase, new LinearLayout.LayoutParams(0, -2, 1.0f));
        TextView textView3 = new TextView(activity);
        vAggregate = textView3;
        textView3.setText("");
        vAggregate.setTextColor(COLOR_SUB);
        vAggregate.setTextSize(2, 11.0f);
        vAggregate.setGravity(GravityCompat.END);
        linearLayout5.addView(vAggregate, new LinearLayout.LayoutParams(-2, -2));
        TextView textView4 = new TextView(activity);
        vStatus = textView4;
        textView4.setText(detailText);
        vStatus.setTextColor(COLOR_TEXT);
        vStatus.setTextSize(2, 12.0f);
        vStatus.setSingleLine(true);
        vStatus.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        linearLayout4.addView(vStatus, lpRow(0, dp(activity, 6)));
        ScrollView scrollView2 = new ScrollView(activity);
        LinearLayout linearLayout6 = new LinearLayout(activity);
        slotContainer = linearLayout6;
        linearLayout6.setOrientation(1);
        scrollView2.addView(slotContainer, new FrameLayout.LayoutParams(-1, -2));
        linearLayout4.addView(scrollView2, new LinearLayout.LayoutParams(-1, 0, 1.0f));
        rebuildSlots(activity);
        LinearLayout linearLayout7 = new LinearLayout(activity);
        linearLayout7.setOrientation(0);
        linearLayout7.setGravity(16);
        linearLayout4.addView(linearLayout7, lpRow(dp(activity, 8), dp(activity, 2)));
        TextView textView5 = new TextView(activity);
        vOverallText = textView5;
        textView5.setText("总进度");
        vOverallText.setTextColor(COLOR_TEXT);
        vOverallText.setTextSize(2, 11.0f);
        linearLayout7.addView(vOverallText, new LinearLayout.LayoutParams(0, -2, 1.0f));
        TextView textView6 = new TextView(activity);
        tvSpeed = textView6;
        textView6.setText("");
        tvSpeed.setTextColor(COLOR_SUB);
        tvSpeed.setTextSize(2, 11.0f);
        tvSpeed.setGravity(GravityCompat.END);
        linearLayout7.addView(tvSpeed, new LinearLayout.LayoutParams(-2, -2));
        ProgressBar progressBar = new ProgressBar(activity, null, R.attr.progressBarStyleHorizontal);
        progressBarOverall = progressBar;
        progressBar.setMax(100);
        progressBarOverall.setProgress(0);
        tintBar(progressBarOverall, COLOR_ACCENT);
        linearLayout4.addView(progressBarOverall, new LinearLayout.LayoutParams(-1, dp(activity, 10)));
        GradientDrawable gradientDrawable = new GradientDrawable();
        logPillBg = gradientDrawable;
        gradientDrawable.setColor(COLOR_LOG_PILL);
        logPillBg.setCornerRadius(dp(activity, 20));
        TextView textView7 = new TextView(activity);
        vLogPill = textView7;
        textView7.setText("LOG");
        vLogPill.setTextColor(-1);
        vLogPill.setTextSize(2, 11.0f);
        TextView textView8 = vLogPill;
        textView8.setTypeface(textView8.getTypeface(), 1);
        vLogPill.setGravity(17);
        vLogPill.setPadding(dp(activity, 12), dp(activity, 6), dp(activity, 12), dp(activity, 6));
        vLogPill.setBackground(logPillBg);
        vLogPill.setOnClickListener(new View.OnClickListener() { // from class: io.kamihama.magianative.CNCNDownloadUI.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                CNCNDownloadUI.openLogModal();
            }
        });
        FrameLayout.LayoutParams layoutParams5 = new FrameLayout.LayoutParams(-2, -2);
        layoutParams5.gravity = 8388659;
        layoutParams5.topMargin = dp(activity, 10);
        layoutParams5.leftMargin = dp(activity, 14);
        frameLayout.addView(vLogPill, layoutParams5);
        GradientDrawable gradientDrawable2 = new GradientDrawable();
        themeChipBg = gradientDrawable2;
        gradientDrawable2.setCornerRadius(dp(activity, 20));
        themeChipBg.setColor(darkMode ? -855645024 : COLOR_ACCENT2);
        TextView textView9 = new TextView(activity);
        vThemeChip = textView9;
        textView9.setText(darkMode ? "☀  亮色" : "☾  夜间");
        vThemeChip.setTextColor(darkMode ? -14017989 : -1);
        vThemeChip.setTextSize(2, 11.0f);
        TextView textView10 = vThemeChip;
        textView10.setTypeface(textView10.getTypeface(), 1);
        vThemeChip.setGravity(17);
        vThemeChip.setPadding(dp(activity, 12), dp(activity, 6), dp(activity, 12), dp(activity, 6));
        vThemeChip.setBackground(themeChipBg);
        vThemeChip.setOnClickListener(new View.OnClickListener() { // from class: io.kamihama.magianative.CNCNDownloadUI.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                CNCNDownloadUI.toggleTheme(activity);
            }
        });
        GradientDrawable gradientDrawable3 = new GradientDrawable();
        githubChipBg = gradientDrawable3;
        gradientDrawable3.setCornerRadius(dp(activity, 20));
        githubChipBg.setColor(COLOR_ACCENT2);
        TextView textView11 = new TextView(activity);
        vGitHubChip = textView11;
        textView11.setText("</>  GitHub");
        vGitHubChip.setTextColor(-1);
        vGitHubChip.setTextSize(2, 11.0f);
        TextView textView12 = vGitHubChip;
        textView12.setTypeface(textView12.getTypeface(), 1);
        vGitHubChip.setGravity(17);
        vGitHubChip.setPadding(dp(activity, 12), dp(activity, 6), dp(activity, 12), dp(activity, 6));
        vGitHubChip.setBackground(githubChipBg);
        vGitHubChip.setOnClickListener(new CreditLinkClick(activity, URL_GITHUB));
        LinearLayout linearLayout8 = new LinearLayout(activity);
        linearLayout8.setOrientation(0);
        linearLayout8.setGravity(16);
        linearLayout8.addView(vThemeChip, new LinearLayout.LayoutParams(-2, -2));
        LinearLayout.LayoutParams layoutParams6 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams6.leftMargin = dp(activity, 8);
        linearLayout8.addView(vGitHubChip, layoutParams6);
        FrameLayout.LayoutParams layoutParams7 = new FrameLayout.LayoutParams(-2, -2);
        layoutParams7.gravity = 8388661;
        layoutParams7.topMargin = dp(activity, 10);
        layoutParams7.rightMargin = dp(activity, 14);
        frameLayout.addView(linearLayout8, layoutParams7);
        TextView textView13 = new TextView(activity);
        textView13.setText(FOOTER_CREDIT);
        textView13.setTextColor(COLOR_SUB);
        textView13.setTextSize(2, 10.0f);
        textView13.setSingleLine(true);
        textView13.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView13.setMarqueeRepeatLimit(-1);
        textView13.setSelected(true);
        textView13.setHorizontallyScrolling(true);
        textView13.setPadding(dp(activity, 16), 0, dp(activity, 16), dp(activity, 8));
        FrameLayout.LayoutParams layoutParams8 = new FrameLayout.LayoutParams(-1, -2);
        layoutParams8.gravity = 8388691;
        frameLayout.addView(textView13, layoutParams8);
        FrameLayout frameLayout2 = new FrameLayout(activity);
        logModal = frameLayout2;
        frameLayout2.setBackgroundColor(COLOR_DIM);
        logModal.setVisibility(8);
        logModal.setClickable(true);
        logModal.setOnClickListener(new View.OnClickListener() { // from class: io.kamihama.magianative.CNCNDownloadUI.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                CNCNDownloadUI.closeLogModal();
            }
        });
        frameLayout.addView(logModal, new FrameLayout.LayoutParams(-1, -1));
        LinearLayout linearLayout9 = new LinearLayout(activity);
        linearLayout9.setOrientation(1);
        linearLayout9.setClickable(true);
        linearLayout9.setOnTouchListener(new View.OnTouchListener() { // from class: io.kamihama.magianative.CNCNDownloadUI.4
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                return true;
            }
        });
        linearLayout9.setPadding(dp(activity, 16), dp(activity, 16), dp(activity, 16), dp(activity, 16));
        GradientDrawable gradientDrawable4 = new GradientDrawable();
        gradientDrawable4.setColor(COLOR_LOG_PANEL_BG);
        gradientDrawable4.setCornerRadius(dp(activity, 16));
        gradientDrawable4.setStroke(dp(activity, 1), COLOR_CARD_STK);
        linearLayout9.setBackground(gradientDrawable4);
        FrameLayout.LayoutParams layoutParams9 = new FrameLayout.LayoutParams(-1, -1);
        layoutParams9.leftMargin = dp(activity, 20);
        layoutParams9.rightMargin = dp(activity, 20);
        layoutParams9.topMargin = dp(activity, 20);
        layoutParams9.bottomMargin = dp(activity, 20);
        logModal.addView(linearLayout9, layoutParams9);
        LinearLayout linearLayout10 = new LinearLayout(activity);
        linearLayout10.setOrientation(0);
        linearLayout10.setGravity(16);
        linearLayout9.addView(linearLayout10, lpRow(0, dp(activity, 8)));
        TextView textView14 = new TextView(activity);
        textView14.setText("安装日志");
        textView14.setTextColor(COLOR_ACCENT);
        textView14.setTextSize(2, 16.0f);
        linearLayout10.addView(textView14, new LinearLayout.LayoutParams(0, -2, 1.0f));
        TextView textView15 = new TextView(activity);
        textView15.setText("复制全部");
        textView15.setTextColor(-1);
        textView15.setTextSize(2, 12.0f);
        textView15.setGravity(17);
        textView15.setPadding(dp(activity, 14), dp(activity, 6), dp(activity, 14), dp(activity, 6));
        GradientDrawable gradientDrawable5 = new GradientDrawable();
        gradientDrawable5.setColor(COLOR_ACCENT2);
        gradientDrawable5.setCornerRadius(dp(activity, 8));
        textView15.setBackground(gradientDrawable5);
        textView15.setOnClickListener(new CopyLogClick(activity));
        linearLayout10.addView(textView15, new LinearLayout.LayoutParams(-2, -2));
        TextView textView16 = new TextView(activity);
        textView16.setText("关闭");
        textView16.setTextColor(-1);
        textView16.setTextSize(2, 12.0f);
        textView16.setGravity(17);
        textView16.setPadding(dp(activity, 16), dp(activity, 6), dp(activity, 16), dp(activity, 6));
        GradientDrawable gradientDrawable6 = new GradientDrawable();
        gradientDrawable6.setColor(COLOR_ACCENT);
        gradientDrawable6.setCornerRadius(dp(activity, 8));
        textView16.setBackground(gradientDrawable6);
        textView16.setOnClickListener(new View.OnClickListener() { // from class: io.kamihama.magianative.CNCNDownloadUI.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                CNCNDownloadUI.closeLogModal();
            }
        });
        LinearLayout.LayoutParams layoutParams10 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams10.leftMargin = dp(activity, 8);
        linearLayout10.addView(textView16, layoutParams10);
        ScrollView scrollView3 = new ScrollView(activity);
        vLogScroll = scrollView3;
        scrollView3.setDescendantFocusability(393216);
        GradientDrawable gradientDrawable7 = new GradientDrawable();
        gradientDrawable7.setColor(darkMode ? 1157627903 : 335544320);
        gradientDrawable7.setCornerRadius(dp(activity, 8));
        gradientDrawable7.setStroke(1, darkMode ? 872415231 : 570425344);
        vLogScroll.setBackground(gradientDrawable7);
        vLogScroll.setPadding(dp(activity, 8), dp(activity, 6), dp(activity, 8), dp(activity, 6));
        linearLayout9.addView(vLogScroll, new LinearLayout.LayoutParams(-1, 0, 1.0f));
        TextView textView17 = new TextView(activity);
        tvLog = textView17;
        textView17.setText("=== MagiaCN Installer ===\n(waiting...)");
        tvLog.setTextColor(COLOR_LOG_PANEL_TEXT);
        tvLog.setTextSize(2, 11.0f);
        tvLog.setTypeface(Typeface.MONOSPACE);
        tvLog.setTextIsSelectable(true);
        vLogScroll.addView(tvLog, new ViewGroup.LayoutParams(-1, -2));
        return frameLayout;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class CreditLinkClick implements View.OnClickListener {
        private final Activity act;
        private final String url;

        CreditLinkClick(Activity activity, String str) {
            this.act = activity;
            this.url = str;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            long currentTimeMillis = System.currentTimeMillis();
            if (!(this.url.equals(CNCNDownloadUI.pendingUrl) && currentTimeMillis - CNCNDownloadUI.pendingAtMs <= CNCNDownloadUI.CONFIRM_WINDOW_MS)) {
                String unused = CNCNDownloadUI.pendingUrl = this.url;
                long unused2 = CNCNDownloadUI.pendingAtMs = currentTimeMillis;
                CNLog.i("界面", "外链待确认: " + this.url);
                CNCNDownloadUI.toast(this.act, "即将离开游戏打开：" + this.url + "\n再点一次继续");
                return;
            }
            String unused3 = CNCNDownloadUI.pendingUrl = null;
            long unused4 = CNCNDownloadUI.pendingAtMs = 0L;
            CNLog.i("界面", "外链已确认，调起系统浏览器: " + this.url);
            try {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(this.url));
                intent.addFlags(268435456);
                this.act.startActivity(intent);
            } catch (Throwable th) {
                CNLog.w("界面", "打开外链失败: " + this.url, th);
                CNCNDownloadUI.toast(this.act, "无法打开链接：" + this.url);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void toast(Activity activity, String str) {
        try {
            Toast.makeText(activity, str, 1).show();
        } catch (Throwable th) {
        }
    }

    private static void populateContributors(Activity activity) {
        LinearLayout linearLayout = vContribList;
        if (linearLayout == null) {
            return;
        }
        linearLayout.removeAllViews();
        int i = 0;
        int i2 = 0;
        while (true) {
            String[] strArr = CREDIT_TEXTS;
            if (i < strArr.length) {
                int i3 = CREDIT_KINDS[i];
                if (i3 == 2) {
                    LinearLayout linearLayout2 = new LinearLayout(activity);
                    linearLayout2.setOrientation(0);
                    linearLayout2.setGravity(16);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
                    layoutParams.topMargin = dp(activity, 3);
                    vContribList.addView(linearLayout2, layoutParams);
                    int[] iArr = CONTRIB_PALETTE;
                    DotView dotView = new DotView(activity, iArr[i2 % iArr.length]);
                    LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(dp(activity, 7), dp(activity, 7));
                    layoutParams2.rightMargin = dp(activity, 7);
                    linearLayout2.addView(dotView, layoutParams2);
                    i2++;
                    String[] strArr2 = CREDIT_URLS;
                    String str = i < strArr2.length ? strArr2[i] : "";
                    TextView textView = new TextView(activity);
                    textView.setText(strArr[i]);
                    textView.setTextSize(2, 10.0f);
                    if (str.length() > 0) {
                        textView.setTextColor(COLOR_ACCENT);
                        textView.setPaintFlags(textView.getPaintFlags() | 8);
                        linearLayout2.setPadding(0, dp(activity, 3), 0, dp(activity, 3));
                        linearLayout2.setClickable(true);
                        linearLayout2.setOnClickListener(new CreditLinkClick(activity, str));
                    } else {
                        textView.setTextColor(COLOR_TEXT);
                    }
                    linearLayout2.addView(textView, new LinearLayout.LayoutParams(0, -2, 1.0f));
                } else {
                    TextView textView2 = new TextView(activity);
                    textView2.setText(strArr[i]);
                    LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-1, -2);
                    if (i3 == 0) {
                        textView2.setTextColor(COLOR_ACCENT);
                        textView2.setTextSize(2, 12.0f);
                        textView2.setTypeface(textView2.getTypeface(), 1);
                        layoutParams3.bottomMargin = dp(activity, 4);
                    } else if (i3 == 1) {
                        textView2.setTextColor(COLOR_ACCENT2);
                        textView2.setTextSize(2, 11.0f);
                        textView2.setTypeface(textView2.getTypeface(), 1);
                        layoutParams3.topMargin = dp(activity, 8);
                        layoutParams3.bottomMargin = dp(activity, 2);
                    } else {
                        textView2.setTextColor(COLOR_SUB);
                        textView2.setTextSize(2, 9.0f);
                        layoutParams3.leftMargin = dp(activity, 14);
                    }
                    vContribList.addView(textView2, layoutParams3);
                }
                i++;
            } else {
                return;
            }
        }
    }

    private static void rebuildSlots(Activity activity) {
        LinearLayout linearLayout = slotContainer;
        if (linearLayout == null) {
            return;
        }
        linearLayout.removeAllViews();
        slotList.clear();
        int i = 0;
        while (i < 15) {
            LinearLayout linearLayout2 = new LinearLayout(activity);
            linearLayout2.setOrientation(1);
            slotContainer.addView(linearLayout2, new LinearLayout.LayoutParams(-1, -2));
            LinearLayout linearLayout3 = new LinearLayout(activity);
            linearLayout3.setOrientation(0);
            linearLayout3.setGravity(16);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
            layoutParams.topMargin = dp(activity, 5);
            linearLayout2.addView(linearLayout3, layoutParams);
            TextView textView = new TextView(activity);
            int i2 = i + 1;
            textView.setText(i2 + ". " + FILE_NAMES[i]);
            textView.setTextColor(COLOR_TEXT);
            textView.setTextSize(2, 11.0f);
            textView.setSingleLine(true);
            textView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
            linearLayout3.addView(textView, new LinearLayout.LayoutParams(0, -2, 1.0f));
            TextView textView2 = new TextView(activity);
            textView2.setText("");
            textView2.setTextColor(COLOR_SUB);
            textView2.setTextSize(2, 10.0f);
            textView2.setGravity(GravityCompat.END);
            linearLayout3.addView(textView2, new LinearLayout.LayoutParams(-2, -2));
            ProgressBar progressBar = new ProgressBar(activity, null, R.attr.progressBarStyleHorizontal);
            progressBar.setMax(100);
            progressBar.setProgress(0);
            tintBar(progressBar, 1435011208);
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, dp(activity, 6));
            layoutParams2.topMargin = dp(activity, 2);
            linearLayout2.addView(progressBar, layoutParams2);
            View view = new View(activity);
            view.setBackgroundColor(darkMode ? 587202559 : 402653184);
            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-1, 1);
            layoutParams3.topMargin = dp(activity, 4);
            linearLayout2.addView(view, layoutParams3);
            slotList.add(new SlotViews(textView, textView2, progressBar, view));
            i = i2;
        }
    }

    private static void tintBar(ProgressBar progressBar, int i) {
        progressBar.setProgressTintList(ColorStateList.valueOf(i));
        progressBar.setProgressBackgroundTintList(ColorStateList.valueOf(COLOR_BAR_BG));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class CopyLogClick implements View.OnClickListener {
        private final Activity act;

        CopyLogClick(Activity activity) {
            this.act = activity;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            try {
                ClipboardManager clipboardManager = (ClipboardManager) this.act.getSystemService("clipboard");
                if (clipboardManager == null) {
                    return;
                }
                clipboardManager.setPrimaryClip(ClipData.newPlainText("magireco-cnv-log", CNCNDownloadUI.access$600()));
                CNCNDownloadUI.toast(this.act, "日志已复制到剪贴板（" + CNLog.size() + " 条）");
            } catch (Throwable th) {
                CNCNDownloadUI.toast(this.act, "复制失败：" + th.getMessage());
            }
        }
    }

    private static String composeLogText() {
        StringBuilder sb = new StringBuilder();
        sb.append(buildStatusText());
        sb.append("\n──────── 运行日志 ────────\n");
        String snapshot = CNLog.snapshot();
        if (snapshot.length() == 0) {
            snapshot = "（暂无日志）\n";
        }
        sb.append(snapshot);
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void renderLogModal() {
        FrameLayout frameLayout = logModal;
        if (frameLayout == null || tvLog == null || frameLayout.getVisibility() != 0) {
            return;
        }
        tvLog.setText(composeLogText());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void openLogModal() {
        FrameLayout frameLayout = logModal;
        if (frameLayout == null) {
            return;
        }
        frameLayout.setVisibility(0);
        renderLogModal();
        ScrollView scrollView = vLogScroll;
        if (scrollView != null) {
            scrollView.post(new ScrollToBottom());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void closeLogModal() {
        FrameLayout frameLayout = logModal;
        if (frameLayout != null) {
            frameLayout.setVisibility(8);
        }
    }

    /* loaded from: classes2.dex */
    private static final class LogChanged implements Runnable {
        private LogChanged() {
        }

        @Override // java.lang.Runnable
        public void run() {
            Handler handler = CNCNDownloadUI.uiHandler;
            if (handler == null || CNCNDownloadUI.logModal == null || CNCNDownloadUI.logModal.getVisibility() != 0) {
                return;
            }
            handler.post(new RenderLog());
        }
    }

    /* loaded from: classes2.dex */
    private static final class RenderLog implements Runnable {
        private RenderLog() {
        }

        @Override // java.lang.Runnable
        public void run() {
            boolean z = false;
            if (CNCNDownloadUI.vLogScroll != null && CNCNDownloadUI.tvLog != null && CNCNDownloadUI.vLogScroll.getScrollY() + CNCNDownloadUI.vLogScroll.getHeight() >= CNCNDownloadUI.tvLog.getHeight() - CNCNDownloadUI.dpStatic(24)) {
                z = true;
            }
            CNCNDownloadUI.renderLogModal();
            if (z && CNCNDownloadUI.vLogScroll != null) {
                CNCNDownloadUI.vLogScroll.post(new ScrollToBottom());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class ScrollToBottom implements Runnable {
        private ScrollToBottom() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (CNCNDownloadUI.vLogScroll != null) {
                CNCNDownloadUI.vLogScroll.fullScroll(130);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int dpStatic(int i) {
        return (int) ((i * Resources.getSystem().getDisplayMetrics().density) + 0.5f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void toggleTheme(Activity activity) {
        try {
            darkMode = !darkMode;
            activity.getSharedPreferences(PREFS_NAME, 0).edit().putBoolean(PREF_DARK_MODE, darkMode).apply();
            loadPalette(darkMode);
            ViewGroup viewGroup = decorView;
            FrameLayout frameLayout = overlayView;
            if (viewGroup == null) {
                return;
            }
            FrameLayout buildOverlay = buildOverlay(activity);
            if (frameLayout != null) {
                viewGroup.removeView(frameLayout);
            }
            viewGroup.addView(buildOverlay, new ViewGroup.LayoutParams(-1, -1));
            overlayView = buildOverlay;
            renderAll();
        } catch (Throwable th) {
            CNLog.e("界面", "主题切换失败: " + th);
        }
    }

    private static String formatMb(float f) {
        return f <= 0.0f ? "0 MB" : f < 1024.0f ? String.format(Locale.US, "%.1f MB", Float.valueOf(f)) : String.format(Locale.US, "%.2f GB", Float.valueOf(f / 1024.0f));
    }

    private static String formatMbps(float f) {
        return f <= 0.0f ? "" : String.format(Locale.US, "%.2f MB/s", Float.valueOf(f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void renderAll() {
        float f;
        int i;
        TextView textView = tvLog;
        if (textView != null) {
            textView.setText(buildStatusText());
        }
        int[] iArr = fileStatus;
        int[] iArr2 = fileProgress;
        float[] fArr = fileSize;
        float[] fArr2 = fileSpeed;
        float[] fArr3 = fileDownloaded;
        if (iArr2 != null) {
            int i2 = 0;
            for (int i3 = 0; i3 < 15; i3++) {
                i2 += iArr2[i3];
            }
            int i4 = i2 / 15;
            ProgressBar progressBar = progressBarOverall;
            if (progressBar != null) {
                progressBar.setProgress(i4);
            }
        }
        if (fArr2 != null && iArr != null) {
            f = 0.0f;
            for (int i5 = 0; i5 < 15; i5++) {
                if (iArr[i5] == 1) {
                    f += fArr2[i5];
                }
            }
        } else {
            f = 0.0f;
        }
        TextView textView2 = tvSpeed;
        if (textView2 != null) {
            textView2.setText(formatMbps(f));
        }
        TextView textView3 = vPhase;
        if (textView3 != null) {
            textView3.setText(phaseText);
        }
        TextView textView4 = vStatus;
        if (textView4 != null) {
            textView4.setText(detailText);
        }
        if (!slotList.isEmpty() && iArr != null && iArr2 != null) {
            int i6 = 0;
            for (int i7 = 15; i6 < i7; i7 = 15) {
                List<SlotViews> list = slotList;
                if (i6 < list.size()) {
                    SlotViews slotViews = list.get(i6);
                    int i8 = iArr[i6];
                    int i9 = iArr2[i6];
                    slotViews.bar.setProgress(i9);
                    switch (i8) {
                        case 1:
                            i = COLOR_ACCENT;
                            break;
                        case 2:
                            i = -10044566;
                            break;
                        case 3:
                            i = -1754827;
                            break;
                        default:
                            i = 1435011208;
                            break;
                    }
                    slotViews.bar.setProgressTintList(ColorStateList.valueOf(i));
                    if (i8 == 2) {
                        slotViews.infoView.setTextColor(-10044566);
                        slotViews.infoView.setText("✓");
                    } else if (i8 == 3) {
                        slotViews.infoView.setTextColor(-1754827);
                        slotViews.infoView.setText("✗");
                    } else if (i8 == 1) {
                        slotViews.infoView.setTextColor(COLOR_SUB);
                        StringBuilder sb = new StringBuilder();
                        sb.append(i9).append('%');
                        if (fArr3 != null && fArr != null && fArr[i6] > 0.0f) {
                            sb.append("  ").append(formatMb(fArr3[i6])).append(" / ").append(formatMb(fArr[i6]));
                        }
                        if (fArr2 != null && fArr2[i6] > 0.0f) {
                            sb.append("  ").append(formatMbps(fArr2[i6]));
                        }
                        slotViews.infoView.setText(sb.toString());
                    } else {
                        slotViews.infoView.setTextColor(COLOR_SUB);
                        slotViews.infoView.setText("");
                    }
                    i6++;
                }
            }
        }
        if (vAggregate != null && iArr != null) {
            int i10 = 0;
            for (int i11 = 0; i11 < 15; i11++) {
                if (iArr[i11] == 2) {
                    i10++;
                }
            }
            vAggregate.setText(i10 + " / 15 文件");
        }
        if (vOverallText != null) {
            String str = "总进度";
            if (fArr != null && fArr3 != null) {
                float f2 = 0.0f;
                float f3 = 0.0f;
                for (int i12 = 0; i12 < 15; i12++) {
                    f3 += fArr[i12];
                    f2 += fArr3[i12];
                }
                if (f3 > 0.0f) {
                    str = "总进度  " + formatMb(f2) + " / " + formatMb(f3);
                }
            }
            vOverallText.setText(str);
        }
    }

    /* loaded from: classes2.dex */
    public static class CreateUIRunnable implements Runnable {
        private final Activity context;

        public CreateUIRunnable(Activity activity) {
            this.context = activity;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                Activity activity = this.context;
                if (activity == null) {
                    return;
                }
                Activity unused = CNCNDownloadUI.hostActivity = activity;
                try {
                    CNLog.init(activity.getFilesDir());
                } catch (Throwable th) {
                }
                CNLog.setListener(new LogChanged());
                try {
                    boolean unused2 = CNCNDownloadUI.darkMode = activity.getSharedPreferences(CNCNDownloadUI.PREFS_NAME, 0).getBoolean(CNCNDownloadUI.PREF_DARK_MODE, false);
                } catch (Throwable th2) {
                    boolean unused3 = CNCNDownloadUI.darkMode = false;
                }
                CNCNDownloadUI.loadPalette(CNCNDownloadUI.darkMode);
                CNLog.i("界面", "下载浮层已创建，主题=" + (CNCNDownloadUI.darkMode ? "夜间" : "亮色"));
                CNCNDownloadUI.decorView = (ViewGroup) activity.getWindow().getDecorView();
                FrameLayout buildOverlay = CNCNDownloadUI.buildOverlay(activity);
                CNCNDownloadUI.decorView.addView(buildOverlay, new ViewGroup.LayoutParams(-1, -1));
                CNCNDownloadUI.overlayView = buildOverlay;
                CNCNDownloadUI.renderAll();
            } catch (Exception e) {
                CNLog.e("界面", "浮层操作失败: " + e.getMessage(), e);
            }
        }
    }

    /* loaded from: classes2.dex */
    public static class HideRunnable implements Runnable {
        @Override // java.lang.Runnable
        public void run() {
            try {
                CNLog.i("界面", "下载浮层关闭");
                CNLog.setListener(null);
                CNLog.close();
                ViewGroup viewGroup = CNCNDownloadUI.decorView;
                FrameLayout frameLayout = CNCNDownloadUI.overlayView;
                if (viewGroup != null && frameLayout != null) {
                    viewGroup.removeView(frameLayout);
                    CNCNDownloadUI.overlayView = null;
                    CNCNDownloadUI.tvLog = null;
                    CNCNDownloadUI.progressBarOverall = null;
                    CNCNDownloadUI.tvSpeed = null;
                    CNCNDownloadUI.decorView = null;
                    CNCNDownloadUI.uiHandler = null;
                    TextView unused = CNCNDownloadUI.vPhase = null;
                    TextView unused2 = CNCNDownloadUI.vStatus = null;
                    TextView unused3 = CNCNDownloadUI.vAggregate = null;
                    TextView unused4 = CNCNDownloadUI.vOverallText = null;
                    LinearLayout unused5 = CNCNDownloadUI.slotContainer = null;
                    LinearLayout unused6 = CNCNDownloadUI.vContribList = null;
                    TextView unused7 = CNCNDownloadUI.vThemeChip = null;
                    TextView unused8 = CNCNDownloadUI.vLogPill = null;
                    FrameLayout unused9 = CNCNDownloadUI.logModal = null;
                    ScrollView unused10 = CNCNDownloadUI.vLogScroll = null;
                    GradientDrawable unused11 = CNCNDownloadUI.themeChipBg = null;
                    GradientDrawable unused12 = CNCNDownloadUI.logPillBg = null;
                    Activity unused13 = CNCNDownloadUI.hostActivity = null;
                    CNCNDownloadUI.slotList.clear();
                }
            } catch (Exception e) {
            }
        }
    }

    /* loaded from: classes2.dex */
    public static class UpdateRunnable implements Runnable {
        @Override // java.lang.Runnable
        public void run() {
            try {
                CNCNDownloadUI.renderAll();
            } catch (Exception e) {
            }
        }
    }

    public static String buildStatusText() {
        String[] strArr = FILE_NAMES;
        int[] iArr = fileStatus;
        int[] iArr2 = fileProgress;
        float[] fArr = fileSize;
        float[] fArr2 = fileSpeed;
        float[] fArr3 = fileDownloaded;
        if (strArr == null || iArr == null || iArr2 == null) {
            return "=== MagiaCN Installer ===\n(initializing...)";
        }
        StringBuilder sb = new StringBuilder("=== MagiaCN Installer ===\n");
        int i = 0;
        while (i < 15) {
            int i2 = iArr[i];
            int i3 = i + 1;
            sb.append(i2 == 2 ? "[OK] " : i2 == 1 ? "[ > ] " : i2 == 3 ? "[ERR] " : "[  ] ").append(i3).append(TRouterMap.DOT).append(strArr[i]);
            if (i2 == 1) {
                sb.append("  ").append(iArr2[i]).append("%");
                if (fArr3 != null && fArr != null) {
                    String f = Float.toString(fArr3[i]);
                    if (f.length() > 6) {
                        f = f.substring(0, 6);
                    }
                    sb.append("  ").append(f).append("/");
                    String f2 = Float.toString(fArr[i]);
                    if (f2.length() > 6) {
                        f2 = f2.substring(0, 6);
                    }
                    sb.append(f2).append("MB");
                }
                if (fArr2 != null) {
                    String f3 = Float.toString(fArr2[i]);
                    if (f3.length() > 4) {
                        f3 = f3.substring(0, 4);
                    }
                    sb.append("  ").append(f3).append("MB/s");
                }
                if (iArr[i] != 0) {
                    int i4 = iArr2[i];
                    sb.append("\n  [");
                    for (int i5 = 0; i5 < 10; i5++) {
                        sb.append(i5 * 10 < i4 ? "█" : "░");
                    }
                    sb.append("]");
                }
            }
            sb.append("\n");
            i = i3;
        }
        return sb.toString();
    }

    public static void hide() {
        Handler handler;
        if (!isShowing || (handler = uiHandler) == null) {
            return;
        }
        handler.post(new HideRunnable());
        isShowing = false;
    }

    public static void markFileDone(int i) {
        int[] iArr = fileStatus;
        if (iArr != null) {
            iArr[i] = 2;
            int[] iArr2 = fileProgress;
            if (iArr2 != null) {
                iArr2[i] = 100;
            }
        }
        float[] fArr = fileSpeed;
        if (fArr != null) {
            fArr[i] = 0.0f;
        }
        Handler handler = uiHandler;
        if (handler != null) {
            handler.post(new UpdateRunnable());
        }
    }

    public static void setDownloadSpeed(int i, float f) {
        float[] fArr = fileSpeed;
        if (fArr != null) {
            fArr[i] = f;
        }
    }

    public static void setFileDownloaded(int i, float f) {
        float[] fArr = fileDownloaded;
        if (fArr != null) {
            fArr[i] = f;
        }
    }

    public static void setFileSize(int i, float f) {
        float[] fArr = fileSize;
        if (fArr != null) {
            fArr[i] = f;
        }
    }

    public static void show(Activity activity) {
        if (isShowing) {
            return;
        }
        try {
            uiHandler = new Handler(Looper.getMainLooper());
            activity.runOnUiThread(new CreateUIRunnable(activity));
            int i = 0;
            while (tvLog == null && i < 30) {
                i++;
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                }
            }
            isShowing = true;
        } catch (Exception e2) {
            CNLog.e("界面", "浮层操作失败: " + e2.getMessage(), e2);
        }
    }

    public static void throttledUpdate() {
        Handler handler = uiHandler;
        if (handler == null || System.currentTimeMillis() - lastUpdateTime < 500) {
            return;
        }
        lastUpdateTime = System.currentTimeMillis();
        handler.post(new UpdateRunnable());
    }

    public static void updateFileProgress(int i, int i2) {
        int[] iArr = fileProgress;
        if (iArr != null) {
            iArr[i] = i2;
            int[] iArr2 = fileStatus;
            if (iArr2 != null && iArr2[i] != 2) {
                iArr2[i] = 1;
            }
        }
        throttledUpdate();
    }

    public static void updateSimple(String str, String str2, int i) {
        if (str != null && str.length() > 0) {
            phaseText = str;
        }
        if (str2 != null && str2.length() > 0) {
            detailText = str2;
        }
        Handler handler = uiHandler;
        if (handler != null) {
            handler.post(new UpdateRunnable());
        }
    }
}
