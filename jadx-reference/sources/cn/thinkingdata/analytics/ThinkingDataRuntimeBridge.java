package cn.thinkingdata.analytics;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;
import cn.thinkingdata.analytics.ThinkingAnalyticsSDK;
import cn.thinkingdata.analytics.utils.p;
import cn.thinkingdata.core.utils.TDLog;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class ThinkingDataRuntimeBridge {
    private static final String TAG = "ThinkingAnalytics.ThinkingDataRuntimeBridge";

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class a implements ThinkingAnalyticsSDK.l {
        final /* synthetic */ Object a;

        a(Object obj) {
            this.a = obj;
        }

        /* JADX WARN: Code restructure failed: missing block: B:63:0x0087, code lost:
        
            if (cn.thinkingdata.analytics.TDPresetProperties.disableList.contains("#title") == false) goto L28;
         */
        /* JADX WARN: Removed duplicated region for block: B:29:0x0090 A[Catch: JSONException -> 0x011d, TRY_ENTER, TryCatch #1 {JSONException -> 0x011d, blocks: (B:20:0x005a, B:23:0x006c, B:26:0x0089, B:29:0x0090, B:31:0x0098, B:33:0x009d, B:34:0x00bf, B:36:0x00c5, B:38:0x00d1, B:39:0x010d, B:41:0x00db, B:43:0x00e9, B:45:0x00f3, B:47:0x0101, B:51:0x0111, B:53:0x0117, B:56:0x00b4, B:58:0x00bc, B:60:0x0077, B:62:0x0081), top: B:19:0x005a }] */
        /* JADX WARN: Removed duplicated region for block: B:36:0x00c5 A[Catch: JSONException -> 0x011d, TryCatch #1 {JSONException -> 0x011d, blocks: (B:20:0x005a, B:23:0x006c, B:26:0x0089, B:29:0x0090, B:31:0x0098, B:33:0x009d, B:34:0x00bf, B:36:0x00c5, B:38:0x00d1, B:39:0x010d, B:41:0x00db, B:43:0x00e9, B:45:0x00f3, B:47:0x0101, B:51:0x0111, B:53:0x0117, B:56:0x00b4, B:58:0x00bc, B:60:0x0077, B:62:0x0081), top: B:19:0x005a }] */
        /* JADX WARN: Removed duplicated region for block: B:41:0x00db A[Catch: JSONException -> 0x011d, TryCatch #1 {JSONException -> 0x011d, blocks: (B:20:0x005a, B:23:0x006c, B:26:0x0089, B:29:0x0090, B:31:0x0098, B:33:0x009d, B:34:0x00bf, B:36:0x00c5, B:38:0x00d1, B:39:0x010d, B:41:0x00db, B:43:0x00e9, B:45:0x00f3, B:47:0x0101, B:51:0x0111, B:53:0x0117, B:56:0x00b4, B:58:0x00bc, B:60:0x0077, B:62:0x0081), top: B:19:0x005a }] */
        /* JADX WARN: Removed duplicated region for block: B:56:0x00b4 A[Catch: JSONException -> 0x011d, TryCatch #1 {JSONException -> 0x011d, blocks: (B:20:0x005a, B:23:0x006c, B:26:0x0089, B:29:0x0090, B:31:0x0098, B:33:0x009d, B:34:0x00bf, B:36:0x00c5, B:38:0x00d1, B:39:0x010d, B:41:0x00db, B:43:0x00e9, B:45:0x00f3, B:47:0x0101, B:51:0x0111, B:53:0x0117, B:56:0x00b4, B:58:0x00bc, B:60:0x0077, B:62:0x0081), top: B:19:0x005a }] */
        @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK.l
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void process(ThinkingAnalyticsSDK thinkingAnalyticsSDK) {
            Object obj;
            if (thinkingAnalyticsSDK.isTrackFragmentAppViewScreenEnabled()) {
                ThinkingDataIgnoreTrackAppViewScreen thinkingDataIgnoreTrackAppViewScreen = (ThinkingDataIgnoreTrackAppViewScreen) this.a.getClass().getAnnotation(ThinkingDataIgnoreTrackAppViewScreen.class);
                if (thinkingDataIgnoreTrackAppViewScreen == null || !(TextUtils.isEmpty(thinkingDataIgnoreTrackAppViewScreen.appId()) || thinkingAnalyticsSDK.getToken().equals(thinkingDataIgnoreTrackAppViewScreen.appId()))) {
                    JSONObject jSONObject = new JSONObject();
                    String canonicalName = this.a.getClass().getCanonicalName();
                    Activity activity = null;
                    try {
                        activity = (Activity) this.a.getClass().getMethod("getActivity", new Class[0]).invoke(this.a, new Object[0]);
                    } catch (Exception unused) {
                    }
                    try {
                        String a = p.a(this.a, thinkingAnalyticsSDK.getToken());
                        if (TextUtils.isEmpty(a) || TDPresetProperties.disableList.contains("#title")) {
                            if (activity != null) {
                                a = p.a(activity);
                                if (!TextUtils.isEmpty(a)) {
                                }
                            }
                            if (activity == null) {
                                if (!TDPresetProperties.disableList.contains("#screen_name")) {
                                    jSONObject.put("#screen_name", String.format(Locale.CHINA, "%s|%s", activity.getClass().getCanonicalName(), canonicalName));
                                }
                            } else if (!TDPresetProperties.disableList.contains("#screen_name")) {
                                jSONObject.put("#screen_name", canonicalName);
                            }
                            obj = this.a;
                            if (obj instanceof ScreenAutoTracker) {
                                ThinkingDataAutoTrackAppViewScreenUrl thinkingDataAutoTrackAppViewScreenUrl = (ThinkingDataAutoTrackAppViewScreenUrl) obj.getClass().getAnnotation(ThinkingDataAutoTrackAppViewScreenUrl.class);
                                if (thinkingDataAutoTrackAppViewScreenUrl == null || !(TextUtils.isEmpty(thinkingDataAutoTrackAppViewScreenUrl.appId()) || thinkingAnalyticsSDK.getToken().equals(thinkingDataAutoTrackAppViewScreenUrl.appId()))) {
                                    if (thinkingAnalyticsSDK.isIgnoreAppViewInExtPackage()) {
                                        return;
                                    }
                                    thinkingAnalyticsSDK.autoTrack("ta_app_view", jSONObject);
                                    return;
                                } else {
                                    String url = thinkingDataAutoTrackAppViewScreenUrl.url();
                                    if (!TextUtils.isEmpty(url)) {
                                        canonicalName = url;
                                    }
                                }
                            } else {
                                ScreenAutoTracker screenAutoTracker = (ScreenAutoTracker) obj;
                                canonicalName = screenAutoTracker.getScreenUrl();
                                JSONObject trackProperties = screenAutoTracker.getTrackProperties();
                                if (trackProperties != null) {
                                    p.a(trackProperties, jSONObject, thinkingAnalyticsSDK.mConfig.getDefaultTimeZone());
                                }
                            }
                            thinkingAnalyticsSDK.trackViewScreenInternal(canonicalName, jSONObject);
                        }
                        jSONObject.put("#title", a);
                        if (activity == null) {
                        }
                        obj = this.a;
                        if (obj instanceof ScreenAutoTracker) {
                        }
                        thinkingAnalyticsSDK.trackViewScreenInternal(canonicalName, jSONObject);
                    } catch (JSONException unused2) {
                        TDLog.d(ThinkingDataRuntimeBridge.TAG, "JSONException occurred when track fragment events");
                    }
                }
            }
        }
    }

    /* loaded from: classes.dex */
    static class b implements ThinkingAnalyticsSDK.l {
        final /* synthetic */ String a;
        final /* synthetic */ String b;
        final /* synthetic */ JSONObject c;

        b(String str, String str2, JSONObject jSONObject) {
            this.a = str;
            this.b = str2;
            this.c = jSONObject;
        }

        @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK.l
        public void process(ThinkingAnalyticsSDK thinkingAnalyticsSDK) {
            if (thinkingAnalyticsSDK.isAutoTrackEnabled()) {
                if (TextUtils.isEmpty(this.a) || thinkingAnalyticsSDK.getToken().equals(this.a)) {
                    thinkingAnalyticsSDK.track(this.b, this.c);
                }
            }
        }
    }

    /* loaded from: classes.dex */
    static class c implements ThinkingAnalyticsSDK.l {
        final /* synthetic */ String a;
        final /* synthetic */ String b;
        final /* synthetic */ JSONObject c;

        c(String str, String str2, JSONObject jSONObject) {
            this.a = str;
            this.b = str2;
            this.c = jSONObject;
        }

        @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK.l
        public void process(ThinkingAnalyticsSDK thinkingAnalyticsSDK) {
            if (thinkingAnalyticsSDK.isAutoTrackEnabled()) {
                if (TextUtils.isEmpty(this.a) || thinkingAnalyticsSDK.getToken().equals(this.a)) {
                    thinkingAnalyticsSDK.track(this.b, this.c);
                }
            }
        }
    }

    /* loaded from: classes.dex */
    static class d implements ThinkingAnalyticsSDK.l {
        final /* synthetic */ Object a;
        final /* synthetic */ View b;

        d(Object obj, View view) {
            this.a = obj;
            this.b = view;
        }

        /* JADX WARN: Removed duplicated region for block: B:86:0x0417 A[Catch: Exception -> 0x0455, TryCatch #8 {Exception -> 0x0455, blocks: (B:3:0x0016, B:7:0x001d, B:10:0x0026, B:12:0x002a, B:14:0x002e, B:16:0x003a, B:20:0x0049, B:22:0x004d, B:24:0x0059, B:27:0x0068, B:29:0x006c, B:31:0x0074, B:33:0x007b, B:35:0x0087, B:37:0x008d, B:41:0x0098, B:43:0x00a0, B:45:0x00a6, B:48:0x00b1, B:50:0x00df, B:52:0x00fa, B:55:0x0105, B:58:0x010e, B:60:0x0128, B:62:0x0130, B:64:0x0135, B:66:0x013d, B:68:0x0152, B:70:0x015a, B:78:0x0176, B:81:0x0188, B:84:0x0411, B:86:0x0417, B:88:0x041f, B:89:0x0426, B:91:0x042e, B:92:0x0431, B:94:0x0446, B:95:0x044f, B:100:0x0197, B:102:0x019d, B:104:0x01a7, B:106:0x01af, B:107:0x01c4, B:109:0x01b6, B:111:0x01be, B:114:0x01d6, B:137:0x0251, B:138:0x0256, B:140:0x025c, B:142:0x0264, B:143:0x026d, B:145:0x0273, B:147:0x0269, B:148:0x027c, B:151:0x0282, B:161:0x02a9, B:163:0x02ad, B:165:0x02b7, B:168:0x02bd, B:170:0x02c5, B:172:0x02ca, B:173:0x02d2, B:175:0x02d6, B:177:0x02e0, B:179:0x02e4, B:181:0x02ee, B:183:0x02f2, B:185:0x02fc, B:188:0x0302, B:190:0x030e, B:191:0x0329, B:192:0x0313, B:195:0x0319, B:197:0x0325, B:198:0x032f, B:200:0x0333, B:202:0x0341, B:204:0x0345, B:206:0x0353, B:208:0x0393, B:210:0x0397, B:212:0x03e2, B:213:0x03ba, B:215:0x03be, B:217:0x03e8, B:235:0x038d, B:255:0x00dc, B:226:0x0357, B:228:0x036a, B:229:0x0378, B:231:0x0380, B:249:0x00c9, B:251:0x00d5), top: B:2:0x0016, inners: #3, #4 }] */
        /* JADX WARN: Removed duplicated region for block: B:91:0x042e A[Catch: Exception -> 0x0455, TryCatch #8 {Exception -> 0x0455, blocks: (B:3:0x0016, B:7:0x001d, B:10:0x0026, B:12:0x002a, B:14:0x002e, B:16:0x003a, B:20:0x0049, B:22:0x004d, B:24:0x0059, B:27:0x0068, B:29:0x006c, B:31:0x0074, B:33:0x007b, B:35:0x0087, B:37:0x008d, B:41:0x0098, B:43:0x00a0, B:45:0x00a6, B:48:0x00b1, B:50:0x00df, B:52:0x00fa, B:55:0x0105, B:58:0x010e, B:60:0x0128, B:62:0x0130, B:64:0x0135, B:66:0x013d, B:68:0x0152, B:70:0x015a, B:78:0x0176, B:81:0x0188, B:84:0x0411, B:86:0x0417, B:88:0x041f, B:89:0x0426, B:91:0x042e, B:92:0x0431, B:94:0x0446, B:95:0x044f, B:100:0x0197, B:102:0x019d, B:104:0x01a7, B:106:0x01af, B:107:0x01c4, B:109:0x01b6, B:111:0x01be, B:114:0x01d6, B:137:0x0251, B:138:0x0256, B:140:0x025c, B:142:0x0264, B:143:0x026d, B:145:0x0273, B:147:0x0269, B:148:0x027c, B:151:0x0282, B:161:0x02a9, B:163:0x02ad, B:165:0x02b7, B:168:0x02bd, B:170:0x02c5, B:172:0x02ca, B:173:0x02d2, B:175:0x02d6, B:177:0x02e0, B:179:0x02e4, B:181:0x02ee, B:183:0x02f2, B:185:0x02fc, B:188:0x0302, B:190:0x030e, B:191:0x0329, B:192:0x0313, B:195:0x0319, B:197:0x0325, B:198:0x032f, B:200:0x0333, B:202:0x0341, B:204:0x0345, B:206:0x0353, B:208:0x0393, B:210:0x0397, B:212:0x03e2, B:213:0x03ba, B:215:0x03be, B:217:0x03e8, B:235:0x038d, B:255:0x00dc, B:226:0x0357, B:228:0x036a, B:229:0x0378, B:231:0x0380, B:249:0x00c9, B:251:0x00d5), top: B:2:0x0016, inners: #3, #4 }] */
        /* JADX WARN: Removed duplicated region for block: B:94:0x0446 A[Catch: Exception -> 0x0455, TryCatch #8 {Exception -> 0x0455, blocks: (B:3:0x0016, B:7:0x001d, B:10:0x0026, B:12:0x002a, B:14:0x002e, B:16:0x003a, B:20:0x0049, B:22:0x004d, B:24:0x0059, B:27:0x0068, B:29:0x006c, B:31:0x0074, B:33:0x007b, B:35:0x0087, B:37:0x008d, B:41:0x0098, B:43:0x00a0, B:45:0x00a6, B:48:0x00b1, B:50:0x00df, B:52:0x00fa, B:55:0x0105, B:58:0x010e, B:60:0x0128, B:62:0x0130, B:64:0x0135, B:66:0x013d, B:68:0x0152, B:70:0x015a, B:78:0x0176, B:81:0x0188, B:84:0x0411, B:86:0x0417, B:88:0x041f, B:89:0x0426, B:91:0x042e, B:92:0x0431, B:94:0x0446, B:95:0x044f, B:100:0x0197, B:102:0x019d, B:104:0x01a7, B:106:0x01af, B:107:0x01c4, B:109:0x01b6, B:111:0x01be, B:114:0x01d6, B:137:0x0251, B:138:0x0256, B:140:0x025c, B:142:0x0264, B:143:0x026d, B:145:0x0273, B:147:0x0269, B:148:0x027c, B:151:0x0282, B:161:0x02a9, B:163:0x02ad, B:165:0x02b7, B:168:0x02bd, B:170:0x02c5, B:172:0x02ca, B:173:0x02d2, B:175:0x02d6, B:177:0x02e0, B:179:0x02e4, B:181:0x02ee, B:183:0x02f2, B:185:0x02fc, B:188:0x0302, B:190:0x030e, B:191:0x0329, B:192:0x0313, B:195:0x0319, B:197:0x0325, B:198:0x032f, B:200:0x0333, B:202:0x0341, B:204:0x0345, B:206:0x0353, B:208:0x0393, B:210:0x0397, B:212:0x03e2, B:213:0x03ba, B:215:0x03be, B:217:0x03e8, B:235:0x038d, B:255:0x00dc, B:226:0x0357, B:228:0x036a, B:229:0x0378, B:231:0x0380, B:249:0x00c9, B:251:0x00d5), top: B:2:0x0016, inners: #3, #4 }] */
        @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK.l
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void process(ThinkingAnalyticsSDK thinkingAnalyticsSDK) {
            Class<?> cls;
            Class<?> cls2;
            CharSequence substring;
            StringBuilder sb;
            Object obj;
            CharSequence sb2;
            CharSequence contentDescription;
            JSONObject jSONObject;
            try {
                if (thinkingAnalyticsSDK.isAutoTrackEnabled() && !thinkingAnalyticsSDK.isAutoTrackEventTypeIgnored(ThinkingAnalyticsSDK.AutoTrackEventType.APP_CLICK)) {
                    Object obj2 = this.a;
                    if (obj2 != null) {
                        if (obj2 instanceof ThinkingDataIgnoreTrackOnClick) {
                            ThinkingDataIgnoreTrackOnClick thinkingDataIgnoreTrackOnClick = (ThinkingDataIgnoreTrackOnClick) obj2;
                            if (TextUtils.isEmpty(thinkingDataIgnoreTrackOnClick.appId()) || thinkingAnalyticsSDK.getToken().equals(thinkingDataIgnoreTrackOnClick.appId())) {
                                return;
                            }
                        } else if (obj2 instanceof ThinkingDataTrackViewOnClick) {
                            ThinkingDataTrackViewOnClick thinkingDataTrackViewOnClick = (ThinkingDataTrackViewOnClick) obj2;
                            if (!TextUtils.isEmpty(thinkingDataTrackViewOnClick.appId()) && !thinkingAnalyticsSDK.getToken().equals(thinkingDataTrackViewOnClick.appId())) {
                                return;
                            }
                        } else if (obj2 instanceof String) {
                            String str = (String) obj2;
                            if (!TextUtils.isEmpty(str) && str.length() >= 2) {
                                String substring2 = str.substring(2);
                                if (str.startsWith("1_")) {
                                    if (TextUtils.isEmpty(substring2) || thinkingAnalyticsSDK.getToken().equals(substring2)) {
                                        return;
                                    }
                                } else if (str.startsWith("2_") && !TextUtils.isEmpty(substring2) && !thinkingAnalyticsSDK.getToken().equals(substring2)) {
                                    return;
                                }
                            }
                        }
                    }
                    long currentTimeMillis = System.currentTimeMillis();
                    String str2 = (String) p.a(thinkingAnalyticsSDK.getToken(), this.b, R.id.thinking_analytics_tag_view_onclick_timestamp);
                    if (!TextUtils.isEmpty(str2)) {
                        try {
                            if (currentTimeMillis - Long.parseLong(str2) < 500) {
                                TDLog.i(ThinkingDataRuntimeBridge.TAG, "This onClick maybe extends from super, IGNORE");
                                return;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    p.a(thinkingAnalyticsSDK.getToken(), this.b, R.id.thinking_analytics_tag_view_onclick_timestamp, String.valueOf(currentTimeMillis));
                    Activity a = p.a(this.b.getContext());
                    if ((a == null || !thinkingAnalyticsSDK.isActivityAutoTrackAppClickIgnored(a.getClass())) && !ThinkingDataRuntimeBridge.isViewIgnored(thinkingAnalyticsSDK, this.b)) {
                        JSONObject jSONObject2 = new JSONObject();
                        p.a(a, this.b, jSONObject2);
                        String a2 = p.a(this.b, thinkingAnalyticsSDK.getToken());
                        if (!TextUtils.isEmpty(a2) && !TDPresetProperties.disableList.contains("#element_id")) {
                            jSONObject2.put("#element_id", a2);
                        }
                        if (a != null && !TDPresetProperties.disableList.contains("#screen_name")) {
                            jSONObject2.put("#screen_name", a.getClass().getCanonicalName());
                            String a3 = p.a(a);
                            if (!TextUtils.isEmpty(a3) && !TDPresetProperties.disableList.contains("#title")) {
                                jSONObject2.put("#title", a3);
                            }
                        }
                        CharSequence charSequence = null;
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
                        try {
                            cls2 = Class.forName("androidx.viewpager.widget.ViewPager");
                        } catch (Exception unused3) {
                            cls2 = null;
                        }
                        if (cls2 == null) {
                            try {
                                cls2 = Class.forName("androidx.viewpager.widget.ViewPager");
                            } catch (Exception unused4) {
                            }
                        }
                        Object canonicalName = this.b.getClass().getCanonicalName();
                        View view = this.b;
                        if (view instanceof CheckBox) {
                            sb2 = ((CheckBox) view).getText();
                            obj = "CheckBox";
                        } else {
                            if (cls == null || !cls.isInstance(view)) {
                                if (cls2 != null && cls2.isInstance(this.b)) {
                                    canonicalName = "ViewPager";
                                    try {
                                        Object invoke = this.b.getClass().getMethod("getAdapter", new Class[0]).invoke(this.b, new Object[0]);
                                        Method method = this.b.getClass().getMethod("getCurrentItem", new Class[0]);
                                        if (method != null) {
                                            int intValue = ((Integer) method.invoke(this.b, new Object[0])).intValue();
                                            if (!TDPresetProperties.disableList.contains("#element_position")) {
                                                jSONObject2.put("#element_position", String.format(Locale.CHINA, "%d", Integer.valueOf(intValue)));
                                            }
                                            Method method2 = invoke.getClass().getMethod("getPageTitle", Integer.TYPE);
                                            if (method2 != null) {
                                                substring = (String) method2.invoke(invoke, Integer.valueOf(intValue));
                                                charSequence = substring;
                                            }
                                        }
                                    } catch (Exception e2) {
                                        e = e2;
                                        e.printStackTrace();
                                        if (!TextUtils.isEmpty(charSequence)) {
                                        }
                                        if (!TDPresetProperties.disableList.contains("#element_type")) {
                                        }
                                        p.a(this.b, jSONObject2);
                                        jSONObject = (JSONObject) p.a(thinkingAnalyticsSDK.getToken(), this.b, R.id.thinking_analytics_tag_view_properties);
                                        if (jSONObject != null) {
                                        }
                                        thinkingAnalyticsSDK.autoTrack("ta_app_click", jSONObject2);
                                    }
                                    if (!TextUtils.isEmpty(charSequence)) {
                                        jSONObject2.put("#element_content", charSequence.toString());
                                    }
                                    if (!TDPresetProperties.disableList.contains("#element_type")) {
                                    }
                                    p.a(this.b, jSONObject2);
                                    jSONObject = (JSONObject) p.a(thinkingAnalyticsSDK.getToken(), this.b, R.id.thinking_analytics_tag_view_properties);
                                    if (jSONObject != null) {
                                    }
                                    thinkingAnalyticsSDK.autoTrack("ta_app_click", jSONObject2);
                                }
                                View view2 = this.b;
                                if (view2 instanceof Switch) {
                                    Switch r9 = (Switch) view2;
                                    CharSequence textOn = r9.isChecked() ? r9.getTextOn() : r9.getTextOff();
                                    if (TextUtils.isEmpty(textOn)) {
                                        textOn = r9.getText();
                                    }
                                    charSequence = textOn;
                                    canonicalName = "SwitchButton";
                                } else if (view2 instanceof RadioGroup) {
                                    canonicalName = "RadioGroup";
                                    int checkedRadioButtonId = ((RadioGroup) view2).getCheckedRadioButtonId();
                                    if (a != null) {
                                        try {
                                            RadioButton radioButton = (RadioButton) a.findViewById(checkedRadioButtonId);
                                            if (radioButton != null && !TextUtils.isEmpty(radioButton.getText())) {
                                                substring = radioButton.getText().toString();
                                                charSequence = substring;
                                            }
                                        } catch (Exception e3) {
                                            e = e3;
                                            e.printStackTrace();
                                            if (!TextUtils.isEmpty(charSequence)) {
                                            }
                                            if (!TDPresetProperties.disableList.contains("#element_type")) {
                                            }
                                            p.a(this.b, jSONObject2);
                                            jSONObject = (JSONObject) p.a(thinkingAnalyticsSDK.getToken(), this.b, R.id.thinking_analytics_tag_view_properties);
                                            if (jSONObject != null) {
                                            }
                                            thinkingAnalyticsSDK.autoTrack("ta_app_click", jSONObject2);
                                        }
                                    }
                                } else if (view2 instanceof RadioButton) {
                                    sb2 = ((RadioButton) view2).getText();
                                    obj = "RadioButton";
                                } else if (view2 instanceof ToggleButton) {
                                    ToggleButton toggleButton = (ToggleButton) view2;
                                    charSequence = toggleButton.isChecked() ? toggleButton.getTextOn() : toggleButton.getTextOff();
                                    canonicalName = "ToggleButton";
                                } else if (view2 instanceof Button) {
                                    sb2 = ((Button) view2).getText();
                                    obj = "Button";
                                } else if (view2 instanceof CheckedTextView) {
                                    sb2 = ((CheckedTextView) view2).getText();
                                    obj = "CheckedTextView";
                                } else if (view2 instanceof TextView) {
                                    sb2 = ((TextView) view2).getText();
                                    obj = "TextView";
                                } else if (view2 instanceof ImageButton) {
                                    canonicalName = "ImageButton";
                                    ImageButton imageButton = (ImageButton) view2;
                                    if (!TextUtils.isEmpty(imageButton.getContentDescription())) {
                                        contentDescription = imageButton.getContentDescription();
                                        substring = contentDescription.toString();
                                        charSequence = substring;
                                    }
                                } else if (view2 instanceof ImageView) {
                                    canonicalName = "ImageView";
                                    ImageView imageView = (ImageView) view2;
                                    if (!TextUtils.isEmpty(imageView.getContentDescription())) {
                                        contentDescription = imageView.getContentDescription();
                                        substring = contentDescription.toString();
                                        charSequence = substring;
                                    }
                                } else if (view2 instanceof RatingBar) {
                                    sb2 = String.valueOf(((RatingBar) view2).getRating());
                                    obj = "RatingBar";
                                } else if (view2 instanceof SeekBar) {
                                    sb2 = String.valueOf(((SeekBar) view2).getProgress());
                                    obj = "SeekBar";
                                } else if (view2 instanceof Spinner) {
                                    try {
                                        charSequence = p.a(new StringBuilder(), (ViewGroup) this.b);
                                        if (!TextUtils.isEmpty(charSequence)) {
                                            charSequence = charSequence.toString().substring(0, charSequence.length() - 1);
                                        }
                                        if (!TDPresetProperties.disableList.contains("#element_position")) {
                                            jSONObject2.put("#element_position", ((Spinner) this.b).getSelectedItemPosition());
                                        }
                                    } catch (Exception e4) {
                                        e4.printStackTrace();
                                    }
                                    canonicalName = "Spinner";
                                } else {
                                    if (view2 instanceof TimePicker) {
                                        sb = new StringBuilder();
                                        sb.append(((TimePicker) this.b).getCurrentHour());
                                        sb.append(":");
                                        sb.append(((TimePicker) this.b).getCurrentMinute());
                                        obj = "TimePicker";
                                    } else if (view2 instanceof DatePicker) {
                                        DatePicker datePicker = (DatePicker) view2;
                                        sb = new StringBuilder();
                                        sb.append(datePicker.getYear());
                                        sb.append("-");
                                        sb.append(datePicker.getMonth());
                                        sb.append("-");
                                        sb.append(datePicker.getDayOfMonth());
                                        obj = "DatePicker";
                                    } else if (view2 instanceof ViewGroup) {
                                        try {
                                            charSequence = p.a(new StringBuilder(), (ViewGroup) this.b);
                                            if (!TextUtils.isEmpty(charSequence)) {
                                                substring = charSequence.toString().substring(0, charSequence.length() - 1);
                                                charSequence = substring;
                                            }
                                        } catch (Exception e5) {
                                            e = e5;
                                            e.printStackTrace();
                                            if (!TextUtils.isEmpty(charSequence)) {
                                            }
                                            if (!TDPresetProperties.disableList.contains("#element_type")) {
                                            }
                                            p.a(this.b, jSONObject2);
                                            jSONObject = (JSONObject) p.a(thinkingAnalyticsSDK.getToken(), this.b, R.id.thinking_analytics_tag_view_properties);
                                            if (jSONObject != null) {
                                            }
                                            thinkingAnalyticsSDK.autoTrack("ta_app_click", jSONObject2);
                                        }
                                    }
                                    sb2 = sb.toString();
                                }
                                if (!TextUtils.isEmpty(charSequence) && !TDPresetProperties.disableList.contains("#element_content")) {
                                    jSONObject2.put("#element_content", charSequence.toString());
                                }
                                if (!TDPresetProperties.disableList.contains("#element_type")) {
                                    jSONObject2.put("#element_type", canonicalName);
                                }
                                p.a(this.b, jSONObject2);
                                jSONObject = (JSONObject) p.a(thinkingAnalyticsSDK.getToken(), this.b, R.id.thinking_analytics_tag_view_properties);
                                if (jSONObject != null) {
                                    p.a(jSONObject, jSONObject2, thinkingAnalyticsSDK.mConfig.getDefaultTimeZone());
                                }
                                thinkingAnalyticsSDK.autoTrack("ta_app_click", jSONObject2);
                            }
                            sb2 = (String) (((CompoundButton) this.b).isChecked() ? this.b.getClass().getMethod("getTextOn", new Class[0]) : this.b.getClass().getMethod("getTextOff", new Class[0])).invoke(this.b, new Object[0]);
                            obj = "SwitchCompat";
                        }
                        charSequence = sb2;
                        canonicalName = obj;
                        if (!TextUtils.isEmpty(charSequence)) {
                        }
                        if (!TDPresetProperties.disableList.contains("#element_type")) {
                        }
                        p.a(this.b, jSONObject2);
                        jSONObject = (JSONObject) p.a(thinkingAnalyticsSDK.getToken(), this.b, R.id.thinking_analytics_tag_view_properties);
                        if (jSONObject != null) {
                        }
                        thinkingAnalyticsSDK.autoTrack("ta_app_click", jSONObject2);
                    }
                }
            } catch (Exception e6) {
                TDLog.e(ThinkingDataRuntimeBridge.TAG, "onViewClickMethod error: " + e6.toString());
                e6.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class e implements ThinkingAnalyticsSDK.l {
        final /* synthetic */ Context a;
        final /* synthetic */ View b;
        final /* synthetic */ View c;
        final /* synthetic */ int d;
        final /* synthetic */ int e;

        e(Context context, View view, View view2, int i, int i2) {
            this.a = context;
            this.b = view;
            this.c = view2;
            this.d = i;
            this.e = i2;
        }

        /* JADX WARN: Removed duplicated region for block: B:49:0x00e4 A[Catch: Exception -> 0x018a, TryCatch #1 {Exception -> 0x018a, blocks: (B:3:0x000a, B:7:0x0011, B:10:0x001a, B:12:0x0022, B:15:0x002d, B:18:0x0036, B:21:0x003f, B:24:0x0048, B:26:0x0054, B:28:0x005c, B:30:0x0071, B:32:0x0079, B:33:0x007c, B:35:0x0088, B:37:0x0090, B:38:0x0093, B:41:0x009b, B:43:0x00a3, B:45:0x00a7, B:46:0x00d9, B:47:0x00dc, B:49:0x00e4, B:50:0x00e9, B:55:0x011e, B:57:0x0124, B:59:0x012c, B:60:0x012f, B:62:0x0144, B:63:0x014d, B:65:0x0157, B:68:0x015b, B:70:0x0161, B:72:0x0170, B:74:0x0176, B:76:0x0168, B:78:0x0181, B:79:0x0184, B:52:0x0112, B:54:0x0116, B:88:0x010e, B:89:0x00b6, B:91:0x00be, B:93:0x00c3, B:83:0x00f0, B:85:0x0103), top: B:2:0x000a, inners: #0, #2 }] */
        /* JADX WARN: Removed duplicated region for block: B:52:0x0112 A[Catch: Exception -> 0x018a, TryCatch #1 {Exception -> 0x018a, blocks: (B:3:0x000a, B:7:0x0011, B:10:0x001a, B:12:0x0022, B:15:0x002d, B:18:0x0036, B:21:0x003f, B:24:0x0048, B:26:0x0054, B:28:0x005c, B:30:0x0071, B:32:0x0079, B:33:0x007c, B:35:0x0088, B:37:0x0090, B:38:0x0093, B:41:0x009b, B:43:0x00a3, B:45:0x00a7, B:46:0x00d9, B:47:0x00dc, B:49:0x00e4, B:50:0x00e9, B:55:0x011e, B:57:0x0124, B:59:0x012c, B:60:0x012f, B:62:0x0144, B:63:0x014d, B:65:0x0157, B:68:0x015b, B:70:0x0161, B:72:0x0170, B:74:0x0176, B:76:0x0168, B:78:0x0181, B:79:0x0184, B:52:0x0112, B:54:0x0116, B:88:0x010e, B:89:0x00b6, B:91:0x00be, B:93:0x00c3, B:83:0x00f0, B:85:0x0103), top: B:2:0x000a, inners: #0, #2 }] */
        /* JADX WARN: Removed duplicated region for block: B:62:0x0144 A[Catch: Exception -> 0x018a, TryCatch #1 {Exception -> 0x018a, blocks: (B:3:0x000a, B:7:0x0011, B:10:0x001a, B:12:0x0022, B:15:0x002d, B:18:0x0036, B:21:0x003f, B:24:0x0048, B:26:0x0054, B:28:0x005c, B:30:0x0071, B:32:0x0079, B:33:0x007c, B:35:0x0088, B:37:0x0090, B:38:0x0093, B:41:0x009b, B:43:0x00a3, B:45:0x00a7, B:46:0x00d9, B:47:0x00dc, B:49:0x00e4, B:50:0x00e9, B:55:0x011e, B:57:0x0124, B:59:0x012c, B:60:0x012f, B:62:0x0144, B:63:0x014d, B:65:0x0157, B:68:0x015b, B:70:0x0161, B:72:0x0170, B:74:0x0176, B:76:0x0168, B:78:0x0181, B:79:0x0184, B:52:0x0112, B:54:0x0116, B:88:0x010e, B:89:0x00b6, B:91:0x00be, B:93:0x00c3, B:83:0x00f0, B:85:0x0103), top: B:2:0x000a, inners: #0, #2 }] */
        /* JADX WARN: Removed duplicated region for block: B:70:0x0161 A[Catch: JSONException -> 0x0180, Exception -> 0x018a, TryCatch #2 {JSONException -> 0x0180, blocks: (B:68:0x015b, B:70:0x0161, B:72:0x0170, B:74:0x0176, B:76:0x0168), top: B:67:0x015b, outer: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:76:0x0168 A[Catch: JSONException -> 0x0180, Exception -> 0x018a, TryCatch #2 {JSONException -> 0x0180, blocks: (B:68:0x015b, B:70:0x0161, B:72:0x0170, B:74:0x0176, B:76:0x0168), top: B:67:0x015b, outer: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:82:0x00f0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK.l
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void process(ThinkingAnalyticsSDK thinkingAnalyticsSDK) {
            Object format;
            String str;
            View view;
            JSONObject jSONObject;
            ExpandableListAdapter expandableListAdapter;
            JSONObject thinkingGroupItemTrackProperties;
            try {
            } catch (Exception e) {
                e.printStackTrace();
                TDLog.i(ThinkingDataRuntimeBridge.TAG, " ExpandableListView.OnChildClickListener.onGroupClick AOP ERROR: " + e.getMessage());
            }
            if (thinkingAnalyticsSDK.isAutoTrackEnabled() && !thinkingAnalyticsSDK.isAutoTrackEventTypeIgnored(ThinkingAnalyticsSDK.AutoTrackEventType.APP_CLICK)) {
                Activity a = p.a(this.a);
                if ((a != null && thinkingAnalyticsSDK.isActivityAutoTrackAppClickIgnored(a.getClass())) || ThinkingDataRuntimeBridge.isViewIgnored(thinkingAnalyticsSDK, ExpandableListView.class) || ThinkingDataRuntimeBridge.isViewIgnored(thinkingAnalyticsSDK, this.b) || ThinkingDataRuntimeBridge.isViewIgnored(thinkingAnalyticsSDK, this.c)) {
                    return;
                }
                JSONObject jSONObject2 = new JSONObject();
                p.a(a, this.c, jSONObject2);
                if (a != null && !TDPresetProperties.disableList.contains("#screen_name")) {
                    jSONObject2.put("#screen_name", a.getClass().getCanonicalName());
                    String a2 = p.a(a);
                    if (!TextUtils.isEmpty(a2) && !TDPresetProperties.disableList.contains("#title")) {
                        jSONObject2.put("#title", a2);
                    }
                }
                String a3 = p.a(this.b);
                if (!TextUtils.isEmpty(a3) && !TDPresetProperties.disableList.contains("#element_id")) {
                    jSONObject2.put("#element_id", a3);
                }
                if (this.d >= 0) {
                    if (!TDPresetProperties.disableList.contains("#element_position")) {
                        format = String.format(Locale.CHINA, "%d:%d", Integer.valueOf(this.e), Integer.valueOf(this.d));
                        jSONObject2.put("#element_position", format);
                    }
                    if (!TDPresetProperties.disableList.contains("#element_type")) {
                    }
                    str = null;
                    view = this.c;
                    if (!(view instanceof ViewGroup)) {
                    }
                    if (!TextUtils.isEmpty(str)) {
                        jSONObject2.put("#element_content", str);
                    }
                    p.a(this.b, jSONObject2);
                    jSONObject = (JSONObject) p.a(thinkingAnalyticsSDK.getToken(), this.c, R.id.thinking_analytics_tag_view_properties);
                    if (jSONObject != null) {
                    }
                    expandableListAdapter = ((ExpandableListView) this.b).getExpandableListAdapter();
                    if (expandableListAdapter != null) {
                        ThinkingExpandableListViewItemTrackProperties thinkingExpandableListViewItemTrackProperties = (ThinkingExpandableListViewItemTrackProperties) expandableListAdapter;
                        int i = this.d;
                        if (i >= 0) {
                        }
                        if (thinkingGroupItemTrackProperties != null) {
                            p.a(thinkingGroupItemTrackProperties, jSONObject2, thinkingAnalyticsSDK.mConfig.getDefaultTimeZone());
                        }
                    }
                    thinkingAnalyticsSDK.autoTrack("ta_app_click", jSONObject2);
                    return;
                }
                if (!TDPresetProperties.disableList.contains("#element_position")) {
                    format = String.format(Locale.CHINA, "%d", Integer.valueOf(this.e));
                    jSONObject2.put("#element_position", format);
                }
                if (!TDPresetProperties.disableList.contains("#element_type")) {
                    jSONObject2.put("#element_type", "ExpandableListView");
                }
                str = null;
                view = this.c;
                if (!(view instanceof ViewGroup)) {
                    try {
                        str = p.a(new StringBuilder(), (ViewGroup) this.c);
                        if (!TextUtils.isEmpty(str)) {
                            str = str.substring(0, str.length() - 1);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else if (view instanceof TextView) {
                    str = (String) ((TextView) view).getText();
                }
                if (!TextUtils.isEmpty(str) && !TDPresetProperties.disableList.contains("#element_content")) {
                    jSONObject2.put("#element_content", str);
                }
                p.a(this.b, jSONObject2);
                jSONObject = (JSONObject) p.a(thinkingAnalyticsSDK.getToken(), this.c, R.id.thinking_analytics_tag_view_properties);
                if (jSONObject != null) {
                    p.a(jSONObject, jSONObject2, thinkingAnalyticsSDK.mConfig.getDefaultTimeZone());
                }
                expandableListAdapter = ((ExpandableListView) this.b).getExpandableListAdapter();
                if (expandableListAdapter != null && (expandableListAdapter instanceof ThinkingExpandableListViewItemTrackProperties)) {
                    try {
                        ThinkingExpandableListViewItemTrackProperties thinkingExpandableListViewItemTrackProperties2 = (ThinkingExpandableListViewItemTrackProperties) expandableListAdapter;
                        int i2 = this.d;
                        thinkingGroupItemTrackProperties = i2 >= 0 ? thinkingExpandableListViewItemTrackProperties2.getThinkingGroupItemTrackProperties(this.e) : thinkingExpandableListViewItemTrackProperties2.getThinkingChildItemTrackProperties(this.e, i2);
                        if (thinkingGroupItemTrackProperties != null && cn.thinkingdata.analytics.utils.f.a(thinkingGroupItemTrackProperties)) {
                            p.a(thinkingGroupItemTrackProperties, jSONObject2, thinkingAnalyticsSDK.mConfig.getDefaultTimeZone());
                        }
                    } catch (JSONException e3) {
                        e3.printStackTrace();
                    }
                }
                thinkingAnalyticsSDK.autoTrack("ta_app_click", jSONObject2);
                return;
                e.printStackTrace();
                TDLog.i(ThinkingDataRuntimeBridge.TAG, " ExpandableListView.OnChildClickListener.onGroupClick AOP ERROR: " + e.getMessage());
            }
        }
    }

    /* loaded from: classes.dex */
    static class f implements ThinkingAnalyticsSDK.l {
        final /* synthetic */ Dialog a;
        final /* synthetic */ int b;

        f(Dialog dialog, int i) {
            this.a = dialog;
            this.b = i;
        }

        /* JADX WARN: Code restructure failed: missing block: B:53:0x00e3, code lost:
        
            if (cn.thinkingdata.analytics.TDPresetProperties.disableList.contains("#element_content") == false) goto L56;
         */
        /* JADX WARN: Code restructure failed: missing block: B:66:0x0106, code lost:
        
            if (cn.thinkingdata.analytics.TDPresetProperties.disableList.contains("#element_content") == false) goto L65;
         */
        /* JADX WARN: Code restructure failed: missing block: B:80:0x0150, code lost:
        
            if (cn.thinkingdata.analytics.TDPresetProperties.disableList.contains("#element_content") == false) goto L56;
         */
        @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK.l
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void process(ThinkingAnalyticsSDK thinkingAnalyticsSDK) {
            Class<?> cls;
            Object item;
            Object obj;
            try {
                if (thinkingAnalyticsSDK.isAutoTrackEnabled() && !thinkingAnalyticsSDK.isAutoTrackEventTypeIgnored(ThinkingAnalyticsSDK.AutoTrackEventType.APP_CLICK)) {
                    Activity a = p.a(this.a.getContext());
                    if (a == null) {
                        a = this.a.getOwnerActivity();
                    }
                    if ((a == null || !thinkingAnalyticsSDK.isActivityAutoTrackAppClickIgnored(a.getClass())) && !ThinkingDataRuntimeBridge.isViewIgnored(thinkingAnalyticsSDK, Dialog.class)) {
                        JSONObject jSONObject = new JSONObject();
                        try {
                            if (this.a.getWindow() != null) {
                                String str = (String) p.a(thinkingAnalyticsSDK.getToken(), this.a.getWindow().getDecorView(), R.id.thinking_analytics_tag_view_id);
                                if (!TextUtils.isEmpty(str) && !TDPresetProperties.disableList.contains("#element_id")) {
                                    jSONObject.put("#element_id", str);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (a != null && !TDPresetProperties.disableList.contains("#screen_name")) {
                            jSONObject.put("#screen_name", a.getClass().getCanonicalName());
                            String a2 = p.a(a);
                            if (!TextUtils.isEmpty(a2) && !TDPresetProperties.disableList.contains("#title")) {
                                jSONObject.put("#title", a2);
                            }
                        }
                        if (!TDPresetProperties.disableList.contains("#element_type")) {
                            jSONObject.put("#element_type", "Dialog");
                        }
                        Button button = null;
                        try {
                            cls = Class.forName("androidx.appcompat.app.AlertDialog)");
                        } catch (Exception unused) {
                            cls = null;
                        }
                        if (cls == null) {
                            try {
                                cls = Class.forName("androidx.appcompat.app.AlertDialog");
                            } catch (Exception unused2) {
                            }
                        }
                        Dialog dialog = this.a;
                        if (dialog instanceof AlertDialog) {
                            AlertDialog alertDialog = (AlertDialog) dialog;
                            button = alertDialog.getButton(this.b);
                            if (button == null) {
                                ListView listView = alertDialog.getListView();
                                if (listView != null) {
                                    obj = listView.getAdapter().getItem(this.b);
                                    if (obj != null) {
                                        if (obj instanceof String) {
                                        }
                                    }
                                }
                            } else if (!TextUtils.isEmpty(button.getText())) {
                            }
                            thinkingAnalyticsSDK.autoTrack("ta_app_click", jSONObject);
                        }
                        if (cls != null && cls.isInstance(dialog)) {
                            try {
                                button = (Button) this.a.getClass().getMethod("getButton", Integer.TYPE).invoke(this.a, Integer.valueOf(this.b));
                            } catch (Exception unused3) {
                            }
                            if (button == null) {
                                try {
                                    ListView listView2 = (ListView) this.a.getClass().getMethod("getListView", new Class[0]).invoke(this.a, new Object[0]);
                                    if (listView2 != null && (item = listView2.getAdapter().getItem(this.b)) != null && (item instanceof String) && !TDPresetProperties.disableList.contains("#element_content")) {
                                        jSONObject.put("#element_content", item);
                                    }
                                } catch (Exception unused4) {
                                }
                            } else if (!TextUtils.isEmpty(button.getText())) {
                            }
                        }
                        thinkingAnalyticsSDK.autoTrack("ta_app_click", jSONObject);
                        obj = button.getText();
                        jSONObject.put("#element_content", obj);
                        thinkingAnalyticsSDK.autoTrack("ta_app_click", jSONObject);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                TDLog.i(ThinkingDataRuntimeBridge.TAG, " DialogInterface.OnClickListener.onClick AOP ERROR: " + e2.getMessage());
            }
        }
    }

    /* loaded from: classes.dex */
    static class g implements ThinkingAnalyticsSDK.l {
        final /* synthetic */ View a;
        final /* synthetic */ View b;
        final /* synthetic */ int c;

        g(View view, View view2, int i) {
            this.a = view;
            this.b = view2;
            this.c = i;
        }

        @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK.l
        public void process(ThinkingAnalyticsSDK thinkingAnalyticsSDK) {
            Context context;
            try {
                if (!thinkingAnalyticsSDK.isAutoTrackEnabled() || thinkingAnalyticsSDK.isAutoTrackEventTypeIgnored(ThinkingAnalyticsSDK.AutoTrackEventType.APP_CLICK) || (context = this.a.getContext()) == null) {
                    return;
                }
                Activity a = p.a(context);
                if ((a == null || !thinkingAnalyticsSDK.isActivityAutoTrackAppClickIgnored(a.getClass())) && !ThinkingDataRuntimeBridge.isViewIgnored(thinkingAnalyticsSDK, this.b.getClass())) {
                    JSONObject jSONObject = new JSONObject();
                    if (thinkingAnalyticsSDK.getIgnoredViewTypeList() != null) {
                        if ((this.b instanceof ListView) && !TDPresetProperties.disableList.contains("#element_type")) {
                            jSONObject.put("#element_type", "ListView");
                            if (ThinkingDataRuntimeBridge.isViewIgnored(thinkingAnalyticsSDK, ListView.class)) {
                                return;
                            }
                        } else if ((this.b instanceof GridView) && !TDPresetProperties.disableList.contains("#element_type")) {
                            jSONObject.put("#element_type", "GridView");
                            if (ThinkingDataRuntimeBridge.isViewIgnored(thinkingAnalyticsSDK, GridView.class)) {
                                return;
                            }
                        } else if ((this.b instanceof Spinner) && !TDPresetProperties.disableList.contains("#element_type")) {
                            jSONObject.put("#element_type", "Spinner");
                            if (ThinkingDataRuntimeBridge.isViewIgnored(thinkingAnalyticsSDK, Spinner.class)) {
                                return;
                            }
                        }
                    }
                    Adapter adapter = ((AdapterView) this.b).getAdapter();
                    if (adapter instanceof ThinkingAdapterViewItemTrackProperties) {
                        try {
                            JSONObject thinkingItemTrackProperties = ((ThinkingAdapterViewItemTrackProperties) adapter).getThinkingItemTrackProperties(this.c);
                            if (thinkingItemTrackProperties != null && cn.thinkingdata.analytics.utils.f.a(thinkingItemTrackProperties)) {
                                p.a(thinkingItemTrackProperties, jSONObject, thinkingAnalyticsSDK.mConfig.getDefaultTimeZone());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    p.a(a, this.a, jSONObject);
                    String a2 = p.a(this.b, thinkingAnalyticsSDK.getToken());
                    if (!TextUtils.isEmpty(a2) && !TDPresetProperties.disableList.contains("#element_id")) {
                        jSONObject.put("#element_id", a2);
                    }
                    if (a != null && !TDPresetProperties.disableList.contains("#screen_name")) {
                        jSONObject.put("#screen_name", a.getClass().getCanonicalName());
                        String a3 = p.a(a);
                        if (!TextUtils.isEmpty(a3) && !TDPresetProperties.disableList.contains("#title")) {
                            jSONObject.put("#title", a3);
                        }
                    }
                    if (!TDPresetProperties.disableList.contains("#element_position")) {
                        jSONObject.put("#element_position", String.valueOf(this.c));
                    }
                    String str = null;
                    View view = this.a;
                    if (view instanceof ViewGroup) {
                        try {
                            str = p.a(new StringBuilder(), (ViewGroup) this.a);
                            if (!TextUtils.isEmpty(str)) {
                                str = str.substring(0, str.length() - 1);
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    } else if (view instanceof TextView) {
                        str = ((TextView) view).getText().toString();
                    }
                    if (!TextUtils.isEmpty(str) && !TDPresetProperties.disableList.contains("#element_content")) {
                        jSONObject.put("#element_content", str);
                    }
                    p.a(this.b, jSONObject);
                    JSONObject jSONObject2 = (JSONObject) p.a(thinkingAnalyticsSDK.getToken(), this.a, R.id.thinking_analytics_tag_view_properties);
                    if (jSONObject2 != null) {
                        p.a(jSONObject2, jSONObject, thinkingAnalyticsSDK.mConfig.getDefaultTimeZone());
                    }
                    thinkingAnalyticsSDK.autoTrack("ta_app_click", jSONObject);
                }
            } catch (Exception e3) {
                e3.printStackTrace();
                TDLog.i(ThinkingDataRuntimeBridge.TAG, " AdapterView.OnItemClickListener.onItemClick AOP ERROR: " + e3.getMessage());
            }
        }
    }

    /* loaded from: classes.dex */
    static class h implements ThinkingAnalyticsSDK.l {
        final /* synthetic */ Object a;
        final /* synthetic */ MenuItem b;

        h(Object obj, MenuItem menuItem) {
            this.a = obj;
            this.b = menuItem;
        }

        @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK.l
        public void process(ThinkingAnalyticsSDK thinkingAnalyticsSDK) {
            Object obj;
            try {
                if (!thinkingAnalyticsSDK.isAutoTrackEnabled() || thinkingAnalyticsSDK.isAutoTrackEventTypeIgnored(ThinkingAnalyticsSDK.AutoTrackEventType.APP_CLICK) || ThinkingDataRuntimeBridge.isViewIgnored(thinkingAnalyticsSDK, MenuItem.class) || (obj = this.a) == null) {
                    return;
                }
                String str = null;
                Context context = obj instanceof Context ? (Context) obj : null;
                if (context == null) {
                    return;
                }
                Activity a = p.a(context);
                if (a == null || !thinkingAnalyticsSDK.isActivityAutoTrackAppClickIgnored(a.getClass())) {
                    try {
                        str = context.getResources().getResourceEntryName(this.b.getItemId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    JSONObject jSONObject = new JSONObject();
                    if (a != null && !TDPresetProperties.disableList.contains("#screen_name")) {
                        jSONObject.put("#screen_name", a.getClass().getCanonicalName());
                        String a2 = p.a(a);
                        if (!TextUtils.isEmpty(a2) && !TDPresetProperties.disableList.contains("#title")) {
                            jSONObject.put("#title", a2);
                        }
                    }
                    if (!TextUtils.isEmpty(str) && !TDPresetProperties.disableList.contains("#element_id")) {
                        jSONObject.put("#element_id", str);
                    }
                    if (!TextUtils.isEmpty(this.b.getTitle()) && !TDPresetProperties.disableList.contains("#element_content")) {
                        jSONObject.put("#element_content", this.b.getTitle());
                    }
                    if (!TDPresetProperties.disableList.contains("#element_type")) {
                        jSONObject.put("#element_type", "MenuItem");
                    }
                    thinkingAnalyticsSDK.autoTrack("ta_app_click", jSONObject);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                TDLog.i(ThinkingDataRuntimeBridge.TAG, "track MenuItem click error: " + e2.getMessage());
            }
        }
    }

    /* loaded from: classes.dex */
    static class i implements ThinkingAnalyticsSDK.l {
        final /* synthetic */ String a;

        i(String str) {
            this.a = str;
        }

        @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK.l
        public void process(ThinkingAnalyticsSDK thinkingAnalyticsSDK) {
            try {
                if (!thinkingAnalyticsSDK.isAutoTrackEnabled() || thinkingAnalyticsSDK.isAutoTrackEventTypeIgnored(ThinkingAnalyticsSDK.AutoTrackEventType.APP_CLICK) || ThinkingDataRuntimeBridge.isViewIgnored(thinkingAnalyticsSDK, TabHost.class)) {
                    return;
                }
                JSONObject jSONObject = new JSONObject();
                if (!TDPresetProperties.disableList.contains("#element_content")) {
                    jSONObject.put("#element_content", this.a);
                }
                if (!TDPresetProperties.disableList.contains("#element_type")) {
                    jSONObject.put("#element_type", "TabHost");
                }
                thinkingAnalyticsSDK.autoTrack("ta_app_click", jSONObject);
            } catch (Exception e) {
                e.printStackTrace();
                TDLog.i(ThinkingDataRuntimeBridge.TAG, " onTabChanged AOP ERROR: " + e.getMessage());
            }
        }
    }

    private static boolean fragmentGetUserVisibleHint(Object obj) {
        try {
            return ((Boolean) obj.getClass().getMethod("getUserVisibleHint", new Class[0]).invoke(obj, new Object[0])).booleanValue();
        } catch (Exception unused) {
            return false;
        }
    }

    private static boolean fragmentIsNotHidden(Object obj) {
        try {
            return !((Boolean) obj.getClass().getMethod("isHidden", new Class[0]).invoke(obj, new Object[0])).booleanValue();
        } catch (Exception unused) {
            return true;
        }
    }

    private static boolean fragmentIsResumed(Object obj) {
        try {
            return ((Boolean) obj.getClass().getMethod("isResumed", new Class[0]).invoke(obj, new Object[0])).booleanValue();
        } catch (Exception unused) {
            return false;
        }
    }

    private static boolean isNotFragment(Object obj) {
        Class<?> cls;
        Class<?> cls2 = null;
        try {
            cls = Class.forName("androidx.fragment.app.Fragment");
        } catch (Exception unused) {
            cls = null;
        }
        try {
            cls2 = Class.forName("androidx.fragment.app.Fragment");
        } catch (Exception unused2) {
        }
        if (cls == null && cls2 == null) {
            return true;
        }
        if (cls != null) {
            try {
                if (cls.isInstance(obj)) {
                    return false;
                }
            } catch (Exception unused3) {
            }
        }
        if (cls2 != null) {
            if (cls2.isInstance(obj)) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isViewIgnored(ThinkingAnalyticsSDK thinkingAnalyticsSDK, View view) {
        if (view == null) {
            return true;
        }
        try {
            List<Class> ignoredViewTypeList = thinkingAnalyticsSDK.getIgnoredViewTypeList();
            if (ignoredViewTypeList != null) {
                Iterator<Class> it = ignoredViewTypeList.iterator();
                while (it.hasNext()) {
                    if (it.next().isAssignableFrom(view.getClass())) {
                        return true;
                    }
                }
            }
            return "1".equals(p.a(thinkingAnalyticsSDK.getToken(), view, R.id.thinking_analytics_tag_view_ignored));
        } catch (Exception e2) {
            e2.printStackTrace();
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isViewIgnored(ThinkingAnalyticsSDK thinkingAnalyticsSDK, Class cls) {
        if (cls == null) {
            return true;
        }
        try {
            List<Class> ignoredViewTypeList = thinkingAnalyticsSDK.getIgnoredViewTypeList();
            if (ignoredViewTypeList == null) {
                return false;
            }
            Iterator<Class> it = ignoredViewTypeList.iterator();
            while (it.hasNext()) {
                if (it.next().isAssignableFrom(cls)) {
                    return true;
                }
            }
            return false;
        } catch (Exception unused) {
            return true;
        }
    }

    public static void onAdapterViewItemClick(View view, View view2, int i2) {
        if (view == null || view2 == null || !(view instanceof AdapterView)) {
            return;
        }
        ThinkingAnalyticsSDK.allInstances(new g(view2, view, i2));
    }

    public static void onAppPushClickEvent(ThinkingAnalyticsSDK thinkingAnalyticsSDK, String str, JSONObject jSONObject) {
        if (thinkingAnalyticsSDK != null) {
            thinkingAnalyticsSDK.autoTrack(str, jSONObject);
            thinkingAnalyticsSDK.flush();
        }
    }

    public static void onDialogClick(Object obj, int i2) {
        if (obj instanceof Dialog) {
            ThinkingAnalyticsSDK.allInstances(new f((Dialog) obj, i2));
        }
    }

    public static void onExpandableListViewOnChildClick(View view, View view2, int i2, int i3) {
        Context context;
        if (view == null || (context = view.getContext()) == null) {
            return;
        }
        ThinkingAnalyticsSDK.allInstances(new e(context, view, view2, i3, i2));
    }

    public static void onExpandableListViewOnGroupClick(View view, View view2, int i2) {
        onExpandableListViewOnChildClick(view, view2, i2, -1);
    }

    public static void onFragmentCreateView(Object obj, View view) {
        try {
            if (isNotFragment(obj)) {
                return;
            }
            String name = obj.getClass().getName();
            view.setTag(R.id.thinking_analytics_tag_view_fragment_name, name);
            if (view instanceof ViewGroup) {
                traverseView(name, (ViewGroup) view);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void onFragmentHiddenChanged(Object obj, boolean z) {
        if (isNotFragment(obj)) {
            return;
        }
        Object obj2 = null;
        try {
            obj2 = obj.getClass().getMethod("getParentFragment", new Class[0]).invoke(obj, new Object[0]);
        } catch (Exception unused) {
        }
        if (z) {
            return;
        }
        if ((obj2 == null && fragmentIsResumed(obj) && fragmentIsNotHidden(obj)) || (fragmentIsResumed(obj) && fragmentIsNotHidden(obj) && fragmentGetUserVisibleHint(obj))) {
            trackFragmentViewScreen(obj);
        }
    }

    public static void onFragmentOnResume(Object obj) {
        if (isNotFragment(obj)) {
            return;
        }
        Object obj2 = null;
        try {
            obj2 = obj.getClass().getMethod("getParentFragment", new Class[0]).invoke(obj, new Object[0]);
        } catch (Exception unused) {
        }
        if (obj2 == null) {
            if (!fragmentIsNotHidden(obj) || !fragmentGetUserVisibleHint(obj)) {
                return;
            }
        } else if (!fragmentIsNotHidden(obj) || !fragmentGetUserVisibleHint(obj) || !fragmentIsNotHidden(obj2) || !fragmentGetUserVisibleHint(obj2)) {
            return;
        }
        trackFragmentViewScreen(obj);
    }

    public static void onFragmentSetUserVisibleHint(Object obj, boolean z) {
        if (isNotFragment(obj)) {
            return;
        }
        Object obj2 = null;
        try {
            obj2 = obj.getClass().getMethod("getParentFragment", new Class[0]).invoke(obj, new Object[0]);
        } catch (Exception unused) {
        }
        if (z) {
            if ((obj2 == null && fragmentIsResumed(obj) && fragmentIsNotHidden(obj)) || (fragmentIsResumed(obj) && fragmentIsNotHidden(obj) && fragmentGetUserVisibleHint(obj))) {
                trackFragmentViewScreen(obj);
            }
        }
    }

    public static void onMenuItemSelected(Object obj, MenuItem menuItem) {
        if (menuItem == null) {
            return;
        }
        ThinkingAnalyticsSDK.allInstances(new h(obj, menuItem));
    }

    public static void onTabHostChanged(String str) {
        ThinkingAnalyticsSDK.allInstances(new i(str));
    }

    public static void onViewOnClick(View view, Object obj) {
        if (view == null) {
            return;
        }
        ThinkingAnalyticsSDK.allInstances(new d(obj, view));
    }

    public static void trackEvent(Object obj) {
        if (obj instanceof ThinkingDataTrackEvent) {
            ThinkingDataTrackEvent thinkingDataTrackEvent = (ThinkingDataTrackEvent) obj;
            String eventName = thinkingDataTrackEvent.eventName();
            String properties = thinkingDataTrackEvent.properties();
            String appId = thinkingDataTrackEvent.appId();
            if (TextUtils.isEmpty(eventName)) {
                return;
            }
            JSONObject jSONObject = new JSONObject();
            if (!TextUtils.isEmpty(properties)) {
                try {
                    p.a(new JSONObject(properties), jSONObject, (TimeZone) null);
                } catch (JSONException e2) {
                    TDLog.e(TAG, "Exception occurred in trackEvent");
                    e2.printStackTrace();
                }
            }
            ThinkingAnalyticsSDK.allInstances(new b(appId, eventName, jSONObject));
        }
    }

    public static void trackEvent(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(str2)) {
            try {
                p.a(new JSONObject(str2), jSONObject, (TimeZone) null);
            } catch (JSONException e2) {
                TDLog.e(TAG, "Exception occurred in trackEvent");
                e2.printStackTrace();
            }
        }
        ThinkingAnalyticsSDK.allInstances(new c(str3, str, jSONObject));
    }

    private static void trackFragmentViewScreen(Object obj) {
        ThinkingAnalyticsSDK.allInstances(new a(obj));
    }

    private static void traverseView(String str, ViewGroup viewGroup) {
        try {
            if (TextUtils.isEmpty(str) || viewGroup == null) {
                return;
            }
            int childCount = viewGroup.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = viewGroup.getChildAt(i2);
                childAt.setTag(R.id.thinking_analytics_tag_view_fragment_name, str);
                if (childAt instanceof ViewGroup) {
                    traverseView(str, (ViewGroup) childAt);
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
