package cn.thinkingdata.core.utils;

import android.text.TextUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/* loaded from: classes.dex */
public class TimeUtil {
    public static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    private static Map<String, ThreadLocal<SimpleDateFormat>> formatMaps = new HashMap();

    public static String formatDate(Date date, String str, TimeZone timeZone) {
        if (TextUtils.isEmpty(str)) {
            str = TIME_PATTERN;
        }
        SimpleDateFormat dateFormat = getDateFormat(str, timeZone);
        if (dateFormat == null) {
            return "";
        }
        try {
            return dateFormat.format(date);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static synchronized SimpleDateFormat getDateFormat(final String str, final TimeZone timeZone) {
        SimpleDateFormat simpleDateFormat;
        synchronized (TimeUtil.class) {
            String str2 = str + "_" + (timeZone != null ? timeZone.getID() : "");
            ThreadLocal<SimpleDateFormat> threadLocal = formatMaps.get(str2);
            if (threadLocal == null) {
                threadLocal = new ThreadLocal<SimpleDateFormat>() { // from class: cn.thinkingdata.core.utils.TimeUtil.1
                    /* JADX DEBUG: Method merged with bridge method: initialValue()Ljava/lang/Object; */
                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // java.lang.ThreadLocal
                    public SimpleDateFormat initialValue() {
                        Exception e;
                        SimpleDateFormat simpleDateFormat2;
                        try {
                            simpleDateFormat2 = new SimpleDateFormat(str, Locale.CHINA);
                        } catch (Exception e2) {
                            e = e2;
                            simpleDateFormat2 = null;
                        }
                        try {
                            TimeZone timeZone2 = timeZone;
                            if (timeZone2 != null) {
                                simpleDateFormat2.setTimeZone(timeZone2);
                            }
                        } catch (Exception e3) {
                            e = e3;
                            e.printStackTrace();
                            return simpleDateFormat2;
                        }
                        return simpleDateFormat2;
                    }
                };
                if (threadLocal.get() != null) {
                    formatMaps.put(str2, threadLocal);
                }
            }
            simpleDateFormat = threadLocal.get();
        }
        return simpleDateFormat;
    }
}
