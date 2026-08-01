package cn.thinkingdata.analytics.utils;

import cn.thinkingdata.core.utils.TimeUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class m implements d {
    private final TimeZone a;
    private final Date b;
    private boolean c = true;

    public m(Date date, TimeZone timeZone) {
        this.b = date == null ? new Date() : date;
        this.a = timeZone;
    }

    @Override // cn.thinkingdata.analytics.utils.d
    public Double a() {
        if (!this.c || this.a == null) {
            return null;
        }
        return Double.valueOf(p.a(this.b.getTime(), this.a));
    }

    public void a(boolean z) {
    }

    @Override // cn.thinkingdata.analytics.utils.d
    public String b() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TimeUtil.TIME_PATTERN, Locale.CHINA);
            TimeZone timeZone = this.a;
            if (timeZone != null) {
                simpleDateFormat.setTimeZone(timeZone);
            }
            String format = simpleDateFormat.format(this.b);
            return !Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3}").matcher(format).find() ? p.a(this.b, this.a) : format;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void c() {
        this.c = false;
    }
}
