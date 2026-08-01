package cn.thinkingdata.core.router;

import android.content.Context;
import cn.thinkingdata.core.utils.TDLog;

/* loaded from: classes.dex */
public final class TRouter {
    private static final String TAG = "ThinkingAnalytics.TRouter";
    private static volatile boolean hasInit;
    private static volatile TRouter instance;

    private TRouter() {
    }

    public static TRouter getInstance() {
        if (!hasInit) {
            throw new InitException("TRouter::Init::Invoke init(context) first!");
        }
        if (instance == null) {
            synchronized (TRouter.class) {
                if (instance == null) {
                    instance = new TRouter();
                }
            }
        }
        return instance;
    }

    public static void init(Context context) {
        if (hasInit) {
            return;
        }
        TDLog.i(TAG, "[ThinkingData] Info: TRouter init start.");
        hasInit = _TRouter.init(context.getApplicationContext());
    }

    public Postcard build(String str) {
        return _TRouter.getInstance().build(str);
    }

    public Object navigation(Context context, Postcard postcard) {
        return _TRouter.getInstance().navigation(context, postcard);
    }
}
