package cn.thinkingdata.analytics.utils;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public enum j {
    TRACK("track"),
    TRACK_UPDATE("track_update"),
    TRACK_OVERWRITE("track_overwrite"),
    USER_ADD("user_add"),
    USER_SET("user_set"),
    USER_SET_ONCE("user_setOnce"),
    USER_UNSET("user_unset"),
    USER_APPEND("user_append"),
    USER_DEL("user_del"),
    USER_UNIQ_APPEND("user_uniq_append");

    private static final Map<String, j> l = new HashMap();
    private final String a;

    static {
        for (j jVar : values()) {
            l.put(jVar.a(), jVar);
        }
    }

    j(String str) {
        this.a = str;
    }

    public static j a(String str) {
        return l.get(str);
    }

    public String a() {
        return this.a;
    }

    public boolean b() {
        return this == TRACK || this == TRACK_OVERWRITE || this == TRACK_UPDATE;
    }
}
