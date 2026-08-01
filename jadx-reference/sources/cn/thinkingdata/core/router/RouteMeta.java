package cn.thinkingdata.core.router;

/* loaded from: classes.dex */
public class RouteMeta {
    private String className;
    private boolean needCache;
    private String path;
    private RouteType type;

    public RouteMeta() {
    }

    public RouteMeta(RouteType routeType, String str, String str2, boolean z) {
        this.type = routeType;
        this.path = str;
        this.className = str2;
        this.needCache = z;
    }

    public static RouteMeta build(RouteType routeType, String str, String str2, boolean z) {
        return new RouteMeta(routeType, str, str2, z);
    }

    public String getClassName() {
        return this.className;
    }

    public String getPath() {
        return this.path;
    }

    public RouteType getType() {
        return this.type;
    }

    public boolean isNeedCache() {
        return this.needCache;
    }

    public void setClassName(String str) {
        this.className = str;
    }

    public void setNeedCache(boolean z) {
        this.needCache = z;
    }

    public void setType(RouteType routeType) {
        this.type = routeType;
    }
}
