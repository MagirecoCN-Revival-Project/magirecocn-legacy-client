package cn.thinkingdata.analytics.utils;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Choreographer;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import cn.thinkingdata.analytics.R;
import cn.thinkingdata.analytics.ScreenAutoTracker;
import cn.thinkingdata.analytics.TDPresetProperties;
import cn.thinkingdata.analytics.ThinkingDataFragmentTitle;
import cn.thinkingdata.core.utils.ProcessUtil;
import cn.thinkingdata.core.utils.TDLog;
import cn.thinkingdata.core.utils.TimeUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class p {
    static long a;
    static long b;
    static volatile int c;
    static final Object d = new Object();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class a implements Choreographer.FrameCallback {
        a() {
        }

        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long j) {
            synchronized (p.d) {
                p.b = j;
                if (j > p.a) {
                    try {
                        long j2 = 1000000000 / (p.b - p.a);
                        if (j2 > 70) {
                            p.c = 60;
                        } else {
                            p.c = (int) j2;
                        }
                    } catch (Exception unused) {
                    }
                }
                p.c = 60;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class b implements Choreographer.FrameCallback {
        final /* synthetic */ Choreographer.FrameCallback a;

        b(Choreographer.FrameCallback frameCallback) {
            this.a = frameCallback;
        }

        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long j) {
            synchronized (p.d) {
                p.a = j;
                Choreographer.getInstance().postFrameCallback(this.a);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class c implements Runnable {
        final /* synthetic */ Handler a;
        final /* synthetic */ Choreographer.FrameCallback b;

        c(Handler handler, Choreographer.FrameCallback frameCallback) {
            this.a = handler;
            this.b = frameCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.a.postDelayed(this, 500L);
            Choreographer.getInstance().postFrameCallback(this.b);
        }
    }

    public static double a(double d2) {
        return Math.round(d2 * 10.0d) / 10.0d;
    }

    public static double a(long j, TimeZone timeZone) {
        if (timeZone == null) {
            timeZone = TimeZone.getDefault();
        }
        return timeZone.getOffset(j) / 3600000.0d;
    }

    public static float a(float f, int i) {
        int i2 = 1;
        for (int i3 = 0; i3 < i; i3++) {
            i2 *= 10;
        }
        return Math.round(f * r3) / i2;
    }

    public static int a() {
        if (c == 0) {
            c = 60;
        }
        return c;
    }

    private static int a(ViewParent viewParent, View view) {
        try {
            if (!(viewParent instanceof ViewGroup)) {
                return -1;
            }
            ViewGroup viewGroup = (ViewGroup) viewParent;
            String a2 = a(view);
            String canonicalName = view.getClass().getCanonicalName();
            int i = 0;
            for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
                View childAt = viewGroup.getChildAt(i2);
                if (e.a(childAt, canonicalName)) {
                    String a3 = a(childAt);
                    if ((a2 == null || a2.equals(a3)) && childAt == view) {
                        return i;
                    }
                    i++;
                }
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int a(String str) {
        if ("NULL".equals(str)) {
            return 255;
        }
        if ("WIFI".equals(str)) {
            return 8;
        }
        if ("2G".equals(str)) {
            return 1;
        }
        if ("3G".equals(str)) {
            return 2;
        }
        if ("4G".equals(str)) {
            return 4;
        }
        return "5G".equals(str) ? 16 : 255;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x001c, code lost:
    
        if ((r1 instanceof android.app.Activity) != false) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Activity a(Context context) {
        if (context != null) {
            try {
                if (!(context instanceof Activity)) {
                    if (context instanceof ContextWrapper) {
                        while (!(context instanceof Activity) && (context instanceof ContextWrapper)) {
                            context = ((ContextWrapper) context).getBaseContext();
                        }
                    }
                }
                return (Activity) context;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static synchronized Object a(String str, View view, int i) {
        synchronized (p.class) {
            HashMap hashMap = (HashMap) view.getTag(i);
            if (hashMap == null) {
                return null;
            }
            return hashMap.get(str);
        }
    }

    public static String a(int i) {
        double random;
        double d2;
        char c2;
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            int random2 = (int) (Math.random() * 2.0d);
            if (random2 == 0) {
                random = Math.random() * 10.0d;
                d2 = 48.0d;
            } else if (random2 != 1) {
                c2 = 0;
                sb.append(c2);
            } else {
                random = Math.random() * 6.0d;
                d2 = 97.0d;
            }
            c2 = (char) (random + d2);
            sb.append(c2);
        }
        return sb.toString();
    }

    public static String a(Activity activity) {
        PackageManager packageManager;
        if (activity != null) {
            try {
                String charSequence = !TextUtils.isEmpty(activity.getTitle()) ? activity.getTitle().toString() : null;
                if (Build.VERSION.SDK_INT >= 11) {
                    String b2 = b(activity);
                    if (!TextUtils.isEmpty(b2)) {
                        charSequence = b2;
                    }
                }
                if (!TextUtils.isEmpty(charSequence) || (packageManager = activity.getPackageManager()) == null) {
                    return charSequence;
                }
                ActivityInfo activityInfo = packageManager.getActivityInfo(activity.getComponentName(), 0);
                return !TextUtils.isEmpty(activityInfo.loadLabel(packageManager)) ? activityInfo.loadLabel(packageManager).toString() : charSequence;
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public static String a(View view) {
        return a(view, (String) null);
    }

    public static String a(View view, String str) {
        try {
            String str2 = (String) a(str, view, R.id.thinking_analytics_tag_view_id);
            try {
                return (!TextUtils.isEmpty(str2) || view.getId() == -1) ? str2 : view.getContext().getResources().getResourceEntryName(view.getId());
            } catch (Exception unused) {
                return str2;
            }
        } catch (Exception unused2) {
            return null;
        }
    }

    public static String a(Object obj, String str) {
        ThinkingDataFragmentTitle thinkingDataFragmentTitle;
        JSONObject trackProperties;
        String str2 = null;
        try {
            if ((obj instanceof ScreenAutoTracker) && (trackProperties = ((ScreenAutoTracker) obj).getTrackProperties()) != null && trackProperties.has("#title")) {
                str2 = trackProperties.optString("#title");
            }
            return (TextUtils.isEmpty(str2) && obj.getClass().isAnnotationPresent(ThinkingDataFragmentTitle.class) && (thinkingDataFragmentTitle = (ThinkingDataFragmentTitle) obj.getClass().getAnnotation(ThinkingDataFragmentTitle.class)) != null) ? (TextUtils.isEmpty(thinkingDataFragmentTitle.appId()) || str.equals(thinkingDataFragmentTitle.appId())) ? thinkingDataFragmentTitle.title() : str2 : str2;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String a(String str, int i) {
        return (!TextUtils.isEmpty(str) && str.length() > i) ? str.substring(str.length() - 4) : str;
    }

    private static String a(String str, String str2) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            String str3 = (String) cls.getDeclaredMethod("get", String.class).invoke(cls, str);
            return TextUtils.isEmpty(str3) ? str2 : str3;
        } catch (Throwable th) {
            TDLog.i("TA.SystemProperties", th.getMessage());
            return str2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00d8 A[Catch: Exception -> 0x00ed, TryCatch #1 {Exception -> 0x00ed, blocks: (B:4:0x0004, B:7:0x0009, B:9:0x0011, B:13:0x00e4, B:14:0x001d, B:16:0x0021, B:22:0x0035, B:24:0x0039, B:27:0x00d2, B:29:0x00d8, B:33:0x0044, B:35:0x004a, B:37:0x0053, B:39:0x0059, B:40:0x006c, B:41:0x0060, B:43:0x0066, B:44:0x0076, B:46:0x007a, B:47:0x0081, B:49:0x0085, B:51:0x008d, B:52:0x0092, B:53:0x0097, B:55:0x009b, B:56:0x00a2, B:58:0x00a6, B:59:0x00ad, B:61:0x00b1, B:62:0x00b8, B:64:0x00bc, B:66:0x00c8, B:74:0x00e8), top: B:2:0x0002 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00e4 A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r4v14, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r4v22, types: [java.lang.CharSequence] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String a(StringBuilder sb, ViewGroup viewGroup) {
        Class<?> cls;
        CharSequence charSequence;
        String str;
        try {
            if (viewGroup == null) {
                return sb.toString();
            }
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt.getVisibility() == 0) {
                    if (childAt instanceof ViewGroup) {
                        a(sb, (ViewGroup) childAt);
                    } else {
                        String str2 = null;
                        try {
                            cls = Class.forName("androidx.appcompat.widget.SwitchCompat");
                        } catch (Exception unused) {
                            cls = null;
                        }
                        if (cls == null) {
                            try {
                                cls = Class.forName("androidx.appcompat.widget.SwitchCompat");
                            } catch (Exception unused2) {
                            }
                        }
                        if (childAt instanceof CheckBox) {
                            str = ((CheckBox) childAt).getText();
                        } else {
                            if (cls != null && cls.isInstance(childAt)) {
                                str2 = (String) (((CompoundButton) childAt).isChecked() ? childAt.getClass().getMethod("getTextOn", new Class[0]) : childAt.getClass().getMethod("getTextOff", new Class[0])).invoke(childAt, new Object[0]);
                            } else if (childAt instanceof RadioButton) {
                                str = ((RadioButton) childAt).getText();
                            } else {
                                if (childAt instanceof ToggleButton) {
                                    ToggleButton toggleButton = (ToggleButton) childAt;
                                    charSequence = toggleButton.isChecked() ? toggleButton.getTextOn() : toggleButton.getTextOff();
                                } else if (childAt instanceof Button) {
                                    charSequence = ((Button) childAt).getText();
                                } else if (childAt instanceof CheckedTextView) {
                                    charSequence = ((CheckedTextView) childAt).getText();
                                } else if (childAt instanceof TextView) {
                                    charSequence = ((TextView) childAt).getText();
                                } else if (childAt instanceof ImageView) {
                                    ImageView imageView = (ImageView) childAt;
                                    if (!TextUtils.isEmpty(imageView.getContentDescription())) {
                                        charSequence = imageView.getContentDescription().toString();
                                    }
                                }
                                if (!TextUtils.isEmpty(charSequence)) {
                                    sb.append(charSequence.toString());
                                    sb.append("-");
                                }
                            }
                            charSequence = str2;
                            if (!TextUtils.isEmpty(charSequence)) {
                            }
                        }
                        str2 = str;
                        charSequence = str2;
                        if (!TextUtils.isEmpty(charSequence)) {
                        }
                    }
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return sb.toString();
        }
    }

    public static String a(Date date, TimeZone timeZone) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeZone(timeZone);
        calendar.setTime(date);
        Locale locale = Locale.CHINA;
        Object[] objArr = new Object[7];
        objArr[0] = Integer.valueOf(calendar.get(1));
        objArr[1] = Integer.valueOf(calendar.get(2) + 1);
        objArr[2] = Integer.valueOf(calendar.get(5));
        objArr[3] = Integer.valueOf(calendar.get(9) == 0 ? calendar.get(10) : calendar.get(10) + 12);
        objArr[4] = Integer.valueOf(calendar.get(12));
        objArr[5] = Integer.valueOf(calendar.get(13));
        objArr[6] = Integer.valueOf(calendar.get(14));
        return String.format(locale, "%04d-%02d-%02d %02d:%02d:%02d.%3d", objArr);
    }

    public static JSONArray a(JSONArray jSONArray, TimeZone timeZone) {
        JSONArray jSONArray2 = new JSONArray();
        for (int i = 0; i < jSONArray.length(); i++) {
            Object opt = jSONArray.opt(i);
            if (opt != null) {
                if (opt instanceof Date) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TimeUtil.TIME_PATTERN, Locale.CHINA);
                    if (timeZone != null) {
                        simpleDateFormat.setTimeZone(timeZone);
                    }
                    Date date = (Date) opt;
                    String format = simpleDateFormat.format(date);
                    if (!Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3}").matcher(format).find()) {
                        format = a(date, timeZone);
                    }
                    jSONArray2.put(format);
                } else {
                    if (opt instanceof JSONArray) {
                        opt = a((JSONArray) opt, timeZone);
                    } else if (opt instanceof JSONObject) {
                        opt = a((JSONObject) opt, timeZone);
                    }
                    jSONArray2.put(opt);
                }
            }
        }
        return jSONArray2;
    }

    public static JSONObject a(JSONObject jSONObject, TimeZone timeZone) {
        JSONObject jSONObject2 = new JSONObject();
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            try {
                Object obj = jSONObject.get(next);
                if (obj instanceof Date) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TimeUtil.TIME_PATTERN, Locale.CHINA);
                    if (timeZone != null) {
                        simpleDateFormat.setTimeZone(timeZone);
                    }
                    String format = simpleDateFormat.format((Date) obj);
                    if (!Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3}").matcher(format).find()) {
                        format = a((Date) obj, timeZone);
                    }
                    jSONObject2.put(next, format);
                } else {
                    if (obj instanceof JSONArray) {
                        obj = a((JSONArray) obj, timeZone);
                    } else if (obj instanceof JSONObject) {
                        obj = a((JSONObject) obj, timeZone);
                    }
                    jSONObject2.put(next, obj);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jSONObject2;
    }

    public static void a(Activity activity, View view, JSONObject jSONObject) {
        ViewParent parent;
        if (view == null) {
            return;
        }
        if (jSONObject == null) {
            try {
                jSONObject = new JSONObject();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        ArrayList arrayList = new ArrayList();
        do {
            parent = view.getParent();
            arrayList.add(view.getClass().getCanonicalName() + "[" + a(parent, view) + "]");
            if (parent instanceof ViewGroup) {
                view = (ViewGroup) parent;
            }
        } while (parent instanceof ViewGroup);
        Collections.reverse(arrayList);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < arrayList.size(); i++) {
            sb.append((String) arrayList.get(i));
            if (i != arrayList.size() - 1) {
                sb.append("/");
            }
        }
        if (TDPresetProperties.disableList.contains("#element_selector")) {
            return;
        }
        jSONObject.put("#element_selector", sb.toString());
    }

    public static void a(View view, JSONObject jSONObject) {
        if (view != null) {
            try {
                String str = (String) view.getTag(R.id.thinking_analytics_tag_view_fragment_name);
                if (TextUtils.isEmpty(str) && view.getParent() != null && (view.getParent() instanceof View)) {
                    str = (String) ((View) view.getParent()).getTag(R.id.thinking_analytics_tag_view_fragment_name);
                }
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                String optString = jSONObject.optString("#screen_name");
                if (TextUtils.isEmpty(str)) {
                    if (TDPresetProperties.disableList.contains("#screen_name")) {
                        return;
                    }
                    jSONObject.put("#screen_name", str);
                } else {
                    if (TDPresetProperties.disableList.contains("#screen_name")) {
                        return;
                    }
                    jSONObject.put("#screen_name", String.format(Locale.CHINA, "%s|%s", optString, str));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized void a(String str, View view, int i, Object obj) {
        synchronized (p.class) {
            if (str == null) {
                return;
            }
            HashMap hashMap = (HashMap) view.getTag(i);
            if (hashMap == null) {
                hashMap = new HashMap();
            }
            hashMap.put(str, obj);
            view.setTag(i, hashMap);
        }
    }

    public static void a(JSONObject jSONObject, Activity activity) {
        PackageManager packageManager;
        if (activity == null || jSONObject == null) {
            return;
        }
        try {
            if (!TDPresetProperties.disableList.contains("#screen_name")) {
                jSONObject.put("#screen_name", activity.getClass().getCanonicalName());
            }
            String charSequence = activity.getTitle().toString();
            if (Build.VERSION.SDK_INT >= 11) {
                String b2 = b(activity);
                if (!TextUtils.isEmpty(b2)) {
                    charSequence = b2;
                }
            }
            if (TextUtils.isEmpty(charSequence) && (packageManager = activity.getPackageManager()) != null) {
                charSequence = packageManager.getActivityInfo(activity.getComponentName(), 0).loadLabel(packageManager).toString();
            }
            if (TextUtils.isEmpty(charSequence) || TDPresetProperties.disableList.contains("#title")) {
                return;
            }
            jSONObject.put("#title", charSequence);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void a(JSONObject jSONObject, JSONObject jSONObject2, TimeZone timeZone) {
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            Object obj = jSONObject.get(next);
            if (obj instanceof Date) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TimeUtil.TIME_PATTERN, Locale.CHINA);
                if (timeZone != null) {
                    simpleDateFormat.setTimeZone(timeZone);
                }
                Date date = (Date) obj;
                String format = simpleDateFormat.format(date);
                if (!Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3}").matcher(format).find()) {
                    format = a(date, timeZone);
                }
                jSONObject2.put(next, format);
            } else {
                if (obj instanceof JSONArray) {
                    obj = a((JSONArray) obj, timeZone);
                } else if (obj instanceof JSONObject) {
                    obj = a((JSONObject) obj, timeZone);
                }
                jSONObject2.put(next, obj);
            }
        }
    }

    public static String b() {
        if (!c()) {
            return null;
        }
        String a2 = a("hw_sc.build.platform.version", "");
        return TextUtils.isEmpty(a2) ? b("getprop hw_sc.build.platform.version") : a2;
    }

    public static String b(Activity activity) {
        Class<?> cls;
        Object invoke;
        CharSequence charSequence;
        ActionBar actionBar = activity.getActionBar();
        if (actionBar == null) {
            try {
                cls = Class.forName("androidx.appcompat.app.AppCompatActivity");
            } catch (Throwable unused) {
                cls = null;
            }
            if (cls == null) {
                try {
                    cls = Class.forName("androidx.appcompat.app.AppCompatActivity");
                } catch (Throwable unused2) {
                }
            }
            if (cls != null) {
                try {
                    if (cls.isInstance(activity) && (invoke = activity.getClass().getMethod("getSupportActionBar", new Class[0]).invoke(activity, new Object[0])) != null && (charSequence = (CharSequence) invoke.getClass().getMethod("getTitle", new Class[0]).invoke(invoke, new Object[0])) != null) {
                        return charSequence.toString();
                    }
                } catch (Throwable unused3) {
                }
            }
        } else if (!TextUtils.isEmpty(actionBar.getTitle())) {
            return actionBar.getTitle().toString();
        }
        return null;
    }

    public static String b(Context context) {
        try {
            return ProcessUtil.getCurrentProcessName(context);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 3, expect 1 */
    public static String b(String str) {
        Throwable th;
        BufferedReader bufferedReader;
        InputStreamReader inputStreamReader;
        try {
            inputStreamReader = new InputStreamReader(Runtime.getRuntime().exec(str).getInputStream());
            try {
                bufferedReader = new BufferedReader(inputStreamReader);
                try {
                    StringBuilder sb = new StringBuilder();
                    while (true) {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        sb.append(readLine);
                    }
                    String sb2 = sb.toString();
                    try {
                        bufferedReader.close();
                    } catch (Throwable th2) {
                        TDLog.i("TDExec", th2.getMessage());
                    }
                    try {
                        inputStreamReader.close();
                    } catch (IOException e) {
                        TDLog.i("TDExec", e.getMessage());
                    }
                    return sb2;
                } catch (Throwable th3) {
                    th = th3;
                    try {
                        TDLog.i("TDExec", th.getMessage());
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (Throwable th4) {
                                TDLog.i("TDExec", th4.getMessage());
                            }
                        }
                        if (inputStreamReader != null) {
                            try {
                                inputStreamReader.close();
                            } catch (IOException e2) {
                                TDLog.i("TDExec", e2.getMessage());
                            }
                        }
                        return null;
                    } finally {
                    }
                }
            } catch (Throwable th5) {
                th = th5;
                bufferedReader = null;
            }
        } catch (Throwable th6) {
            th = th6;
            bufferedReader = null;
            inputStreamReader = null;
        }
    }

    public static void b(JSONObject jSONObject, JSONObject jSONObject2, TimeZone timeZone) {
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            JSONObject optJSONObject = jSONObject.optJSONObject(next);
            JSONObject optJSONObject2 = jSONObject2.optJSONObject(next);
            if (optJSONObject != null) {
                if (optJSONObject2 == null) {
                    JSONObject jSONObject3 = new JSONObject();
                    a(optJSONObject, jSONObject3, timeZone);
                    jSONObject2.put(next, jSONObject3);
                } else {
                    a(optJSONObject, optJSONObject2, timeZone);
                }
            }
        }
    }

    public static String c(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 15) < 3 ? "Phone" : "Tablet";
    }

    public static boolean c() {
        try {
            Class<?> cls = Class.forName("com.huawei.system.BuildEx");
            Object invoke = cls.getMethod("getOsBrand", new Class[0]).invoke(cls, new Object[0]);
            if (invoke == null) {
                return false;
            }
            return "harmony".equalsIgnoreCase(invoke.toString());
        } catch (Throwable th) {
            TDLog.i("HasHarmonyOS", th.getMessage());
            return false;
        }
    }

    public static String d(Context context) {
        if (context == null) {
            return "";
        }
        String b2 = cn.thinkingdata.analytics.f.f.a(context).b();
        if (b2.length() != 0) {
            return b2;
        }
        try {
            return context.getApplicationInfo().processName;
        } catch (Exception unused) {
            return b2;
        }
    }

    public static boolean d() {
        try {
            return new File("/storage/emulated/0/Download/ta_log_controller").exists();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void e() {
        if (Build.VERSION.SDK_INT >= 16) {
            b bVar = new b(new a());
            Handler handler = new Handler();
            handler.postDelayed(new c(handler, bVar), 500L);
        }
    }

    public static boolean e(Context context) {
        return true;
    }

    public static boolean f(Context context) {
        if (context == null) {
            return true;
        }
        String b2 = b(context.getApplicationContext());
        return !TextUtils.isEmpty(b2) && d(context).equals(b2);
    }
}
