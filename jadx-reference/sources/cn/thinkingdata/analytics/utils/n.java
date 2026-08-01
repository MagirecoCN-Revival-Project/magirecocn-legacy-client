package cn.thinkingdata.analytics.utils;

import android.os.SystemClock;
import cn.thinkingdata.core.utils.TimeUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class n implements d {
    private final long a = SystemClock.elapsedRealtime();
    private final TimeZone b;
    private final c c;
    private Date d;

    public n(c cVar, TimeZone timeZone) {
        this.c = cVar;
        this.b = timeZone;
    }

    private synchronized Date c() {
        if (this.d == null) {
            this.d = this.c.a(this.a);
        }
        return this.d;
    }

    @Override // cn.thinkingdata.analytics.utils.d
    public Double a() {
        return Double.valueOf(p.a(c().getTime(), this.b));
    }

    @Override // cn.thinkingdata.analytics.utils.d
    public String b() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TimeUtil.TIME_PATTERN, Locale.CHINA);
            simpleDateFormat.setTimeZone(this.b);
            String format = simpleDateFormat.format(c());
            return !Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3}").matcher(format).find() ? p.a(c(), this.b) : format;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
