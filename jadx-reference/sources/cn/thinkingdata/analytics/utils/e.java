package cn.thinkingdata.analytics.utils;

/* loaded from: classes.dex */
public class e {
    public static boolean a(Object obj, String str) {
        for (Class<?> cls = obj.getClass(); cls.getCanonicalName() != null; cls = cls.getSuperclass()) {
            if (cls.getCanonicalName().equals(str)) {
                return true;
            }
            if (cls == Object.class) {
                return false;
            }
        }
        return false;
    }
}
