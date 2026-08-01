package cn.thinkingdata.core.router;

/* loaded from: classes.dex */
public enum RouteType {
    PROVIDER,
    PLUGIN,
    UNKNOWN;

    public static RouteType parse(int i) {
        return i != 0 ? i != 1 ? UNKNOWN : PLUGIN : PROVIDER;
    }
}
