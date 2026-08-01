package cn.thinkingdata.core.router;

import android.content.Context;
import android.text.TextUtils;
import cn.thinkingdata.core.router.plugin.IPlugin;
import cn.thinkingdata.core.router.plugin.MethodCall;
import cn.thinkingdata.core.router.provider.IProvider;
import cn.thinkingdata.core.utils.TDLog;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class _TRouter {
    private static final String TAG = "ThinkingAnalytics.TRouter";
    private static volatile boolean hasInit;
    private static volatile _TRouter instance;
    private static Context mContext;
    private Map<String, Object> objectMap = new HashMap();

    /* renamed from: cn.thinkingdata.core.router._TRouter$1, reason: invalid class name */
    /* loaded from: classes.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$cn$thinkingdata$core$router$RouteType;

        static {
            int[] iArr = new int[RouteType.values().length];
            $SwitchMap$cn$thinkingdata$core$router$RouteType = iArr;
            try {
                iArr[RouteType.PROVIDER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$cn$thinkingdata$core$router$RouteType[RouteType.PLUGIN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private _TRouter() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static _TRouter getInstance() {
        if (!hasInit) {
            throw new InitException("TRouterCore::Init::Invoke init(context) first!");
        }
        if (instance == null) {
            synchronized (_TRouter.class) {
                if (instance == null) {
                    instance = new _TRouter();
                }
            }
        }
        return instance;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static synchronized boolean init(Context context) {
        synchronized (_TRouter.class) {
            mContext = context;
            LogisticsCenter.init(context);
            TDLog.i(TAG, "[ThinkingData] Info: TRouter init success!");
            hasInit = true;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Postcard build(String str) {
        if (!TextUtils.isEmpty(str)) {
            return new Postcard(str);
        }
        TDLog.e(TAG, "TRouter build Parameter is invalid!");
        return new Postcard("");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Object navigation(Context context, Postcard postcard) {
        IPlugin iPlugin;
        if (!LogisticsCenter.completion(postcard)) {
            return null;
        }
        int i = AnonymousClass1.$SwitchMap$cn$thinkingdata$core$router$RouteType[postcard.getType().ordinal()];
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (i == 1) {
            if (postcard.isNeedCache() && this.objectMap.get(postcard.getClassName()) != null) {
                return (IProvider) this.objectMap.get(postcard.getClassName());
            }
            IProvider iProvider = (IProvider) Class.forName(postcard.getClassName()).getConstructor(new Class[0]).newInstance(new Object[0]);
            if (postcard.isNeedCache()) {
                this.objectMap.put(postcard.getClassName(), iProvider);
            }
            return iProvider;
        }
        if (i == 2) {
            MethodCall methodCall = new MethodCall();
            methodCall.method = postcard.getAction();
            methodCall.arguments = postcard.arguments;
            if (postcard.isNeedCache() && this.objectMap.get(postcard.getClassName()) != null && (iPlugin = (IPlugin) this.objectMap.get(postcard.getClassName())) != null) {
                iPlugin.onMethodCall(methodCall);
                return null;
            }
            IPlugin iPlugin2 = (IPlugin) Class.forName(postcard.getClassName()).getConstructor(new Class[0]).newInstance(new Object[0]);
            if (postcard.isNeedCache()) {
                this.objectMap.put(postcard.getClassName(), iPlugin2);
            }
            iPlugin2.onMethodCall(methodCall);
        }
        return null;
    }
}
