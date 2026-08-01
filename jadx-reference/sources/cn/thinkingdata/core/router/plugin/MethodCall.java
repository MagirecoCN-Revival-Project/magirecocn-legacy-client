package cn.thinkingdata.core.router.plugin;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class MethodCall {
    public Map<String, Object> arguments = new HashMap();
    public String method;

    public <T> T argument(String str) {
        return (T) this.arguments.get(str);
    }
}
