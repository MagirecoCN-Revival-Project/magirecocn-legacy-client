package cn.thinkingdata.analytics.f;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import cn.thinkingdata.analytics.TDConfig;
import cn.thinkingdata.analytics.TDPresetProperties;
import cn.thinkingdata.analytics.ThinkingAnalyticsSDK;
import cn.thinkingdata.analytics.f.c;
import cn.thinkingdata.analytics.utils.g;
import cn.thinkingdata.analytics.utils.k;
import cn.thinkingdata.analytics.utils.p;
import cn.thinkingdata.core.utils.TDLog;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class b {
    private static final Map<Context, b> g = new HashMap();
    private final C0012b a;
    private final a b;
    private final e c;
    private final c d;
    private final Context e;
    private final Map<String, Boolean> f = new ConcurrentHashMap();

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class a {
        private final Handler a;

        /* renamed from: cn.thinkingdata.analytics.f.b$a$a, reason: collision with other inner class name */
        /* loaded from: classes.dex */
        private class HandlerC0011a extends Handler {
            private final List<String> a;

            HandlerC0011a(Looper looper) {
                super(looper);
                this.a = new ArrayList();
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int a;
                int i = message.what;
                if (i != 0) {
                    if (i != 1) {
                        if (i == 2) {
                            b.this.a.c((String) message.obj);
                            return;
                        } else {
                            if (i == 3) {
                                this.a.remove((String) message.obj);
                                return;
                            }
                            return;
                        }
                    }
                    String str = (String) message.obj;
                    if (str == null) {
                        return;
                    }
                    b.this.a.a(str);
                    synchronized (a.this.a) {
                        a.this.a.removeMessages(2, str);
                        this.a.add(str);
                    }
                    synchronized (b.this.d) {
                        b.this.d.a(c.EnumC0013c.EVENTS, (String) message.obj);
                    }
                    return;
                }
                try {
                    cn.thinkingdata.analytics.f.a aVar = (cn.thinkingdata.analytics.f.a) message.obj;
                    if (aVar == null) {
                        return;
                    }
                    String str2 = aVar.j;
                    if (this.a.contains(str2)) {
                        return;
                    }
                    JSONObject a2 = aVar.a();
                    try {
                        a2.put("#uuid", UUID.randomUUID().toString());
                    } catch (JSONException unused) {
                    }
                    synchronized (b.this.d) {
                        a = b.this.d.a(a2, c.EnumC0013c.EVENTS, str2);
                    }
                    if (a < 0) {
                        TDLog.w("ThinkingAnalytics.DataHandle", "Saving data to database failed.");
                    } else {
                        TDLog.i("ThinkingAnalytics.DataHandle", "[ThinkingData] Info: Enqueue data(" + p.a(str2, 4) + "):\n" + a2.toString(4));
                    }
                    if (aVar.i) {
                        return;
                    }
                    a.this.a(str2, a);
                } catch (Exception e) {
                    TDLog.w("ThinkingAnalytics.DataHandle", "Exception occurred while saving data to database: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        a() {
            HandlerThread handlerThread = new HandlerThread("thinkingData.sdk.saveMessageWorker", 1);
            handlerThread.start();
            this.a = new HandlerC0011a(handlerThread.getLooper());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(String str, int i) {
            if (i >= b.this.e(str)) {
                b.this.a.c(str);
            } else {
                b.this.a.a(str, b.this.f(str));
            }
        }

        void a(cn.thinkingdata.analytics.f.a aVar) {
            Message obtain = Message.obtain();
            obtain.what = 0;
            obtain.obj = aVar;
            Handler handler = this.a;
            if (handler != null) {
                handler.sendMessage(obtain);
            }
        }

        void a(String str) {
            Message obtain = Message.obtain();
            obtain.what = 1;
            obtain.obj = str;
            Handler handler = this.a;
            if (handler != null) {
                handler.sendMessageAtFrontOfQueue(obtain);
            }
            Message obtain2 = Message.obtain();
            obtain2.what = 3;
            obtain2.obj = str;
            Handler handler2 = this.a;
            if (handler2 != null) {
                handler2.sendMessage(obtain2);
            }
        }

        void b(String str) {
            Message obtain = Message.obtain();
            obtain.what = 2;
            obtain.obj = str;
            this.a.sendMessage(obtain);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: cn.thinkingdata.analytics.f.b$b, reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public class C0012b {
        private final Handler b;
        private final cn.thinkingdata.analytics.utils.g c;
        private final Object a = new Object();
        private final Map<String, Boolean> d = new HashMap();

        /* renamed from: cn.thinkingdata.analytics.f.b$b$a */
        /* loaded from: classes.dex */
        private class a extends Handler {
            a(Looper looper) {
                super(looper);
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                C0012b c0012b;
                int i = message.what;
                if (i == 0) {
                    String str = (String) message.obj;
                    TDConfig d = b.this.d(str);
                    if (d != null) {
                        synchronized (C0012b.this.a) {
                            Message obtain = Message.obtain();
                            obtain.what = 1;
                            obtain.obj = str;
                            C0012b.this.b.sendMessage(obtain);
                            removeMessages(0, str);
                        }
                        try {
                            C0012b.this.a(d);
                        } catch (RuntimeException e) {
                            TDLog.w("ThinkingAnalytics.DataHandle", "Sending data to server failed due to unexpected exception: " + e.getMessage());
                            e.printStackTrace();
                        }
                        synchronized (C0012b.this.a) {
                            removeMessages(1, str);
                            C0012b.this.a(str, b.this.f(str));
                        }
                        return;
                    }
                } else {
                    if (i != 2) {
                        if (i == 3) {
                            if (((String) message.obj) == null) {
                                return;
                            }
                            synchronized (C0012b.this.a) {
                                removeMessages(0, message.obj);
                            }
                            return;
                        }
                        if (i == 4) {
                            try {
                                cn.thinkingdata.analytics.f.a aVar = (cn.thinkingdata.analytics.f.a) message.obj;
                                if (aVar == null) {
                                    return;
                                }
                                JSONObject a = aVar.a();
                                C0012b c0012b2 = C0012b.this;
                                c0012b2.a(b.this.d(aVar.j), a);
                                return;
                            } catch (Exception e2) {
                                TDLog.e("ThinkingAnalytics.DataHandle", "Exception occurred while sending message to Server: " + e2.getMessage());
                                return;
                            }
                        }
                        if (i != 5) {
                            if (i != 6) {
                                return;
                            }
                            f a2 = f.a(b.this.e);
                            synchronized (b.this.d) {
                                b.this.d.a(System.currentTimeMillis() - a2.a(), c.EnumC0013c.EVENTS);
                            }
                            return;
                        }
                        try {
                            cn.thinkingdata.analytics.f.a aVar2 = (cn.thinkingdata.analytics.f.a) message.obj;
                            if (aVar2 == null) {
                                return;
                            }
                            TDConfig d2 = b.this.d(aVar2.j);
                            if (d2.isNormal()) {
                                c0012b = C0012b.this;
                            } else {
                                try {
                                    C0012b.this.b(d2, aVar2.a());
                                    return;
                                } catch (Exception e3) {
                                    TDLog.e("ThinkingAnalytics.DataHandle", "Exception occurred while sending message to Server: " + e3.getMessage());
                                    if (d2.shouldThrowException()) {
                                        throw new k(e3);
                                    }
                                    if (d2.isDebugOnly()) {
                                        return;
                                    } else {
                                        c0012b = C0012b.this;
                                    }
                                }
                            }
                            b.this.c(aVar2);
                            return;
                        } catch (Exception e4) {
                            e4.printStackTrace();
                            return;
                        }
                    }
                    TDConfig d3 = b.this.d((String) message.obj);
                    if (d3 != null) {
                        try {
                            C0012b.this.a("", d3);
                            return;
                        } catch (RuntimeException e5) {
                            TDLog.w("ThinkingAnalytics.DataHandle", "Sending old data failed due to unexpected exception: " + e5.getMessage());
                            e5.printStackTrace();
                            return;
                        }
                    }
                }
                TDLog.w("ThinkingAnalytics.DataHandle", "Could found config object for token. Canceling...");
            }
        }

        C0012b() {
            HandlerThread handlerThread = new HandlerThread("thinkingData.sdk.sendMessageWorker", 1);
            handlerThread.start();
            this.b = new a(handlerThread.getLooper());
            this.c = b.this.a();
        }

        private Map<String, String> a(JSONArray jSONArray) {
            HashMap hashMap = new HashMap();
            hashMap.put("TA-Integration-Type", e.i());
            hashMap.put("TA-Integration-Version", e.j());
            hashMap.put("TA-Integration-Count", String.valueOf(jSONArray.length()));
            hashMap.put("TA-Integration-Extra", "Android");
            hashMap.put("TA-Datas-Type", cn.thinkingdata.analytics.encrypt.c.a(jSONArray) ? "1" : "0");
            return hashMap;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(TDConfig tDConfig) {
            a(tDConfig.getName(), tDConfig);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(TDConfig tDConfig, JSONObject jSONObject) {
            if (TextUtils.isEmpty(tDConfig.mToken)) {
                return;
            }
            JSONArray jSONArray = new JSONArray();
            jSONArray.put(jSONObject);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("data", jSONArray);
            jSONObject2.put("#app_id", tDConfig.mToken);
            jSONObject2.put("#flush_time", System.currentTimeMillis());
            TDLog.i("ThinkingAnalytics.DataHandle", "ret code: " + new JSONObject(this.c.a(tDConfig.getServerUrl(), jSONObject2.toString(), false, tDConfig.getSSLSocketFactory(), d("1"))).getString("code") + ", upload message:\n" + jSONObject2.toString(4));
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:92:0x0254  */
        /* JADX WARN: Removed duplicated region for block: B:94:0x025b  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void a(String str, TDConfig tDConfig) {
            Boolean bool;
            String[] a2;
            int i;
            String str2;
            boolean z;
            boolean z2;
            int a3;
            int a4;
            String format;
            JSONArray jSONArray;
            JSONObject jSONObject;
            if (tDConfig == null) {
                TDLog.w("ThinkingAnalytics.DataHandle", "Could found config object for sendToken. Canceling...");
                return;
            }
            if (TextUtils.isEmpty(tDConfig.mToken)) {
                return;
            }
            synchronized (b.this.f) {
                bool = (Boolean) b.this.f.get(str);
            }
            if (bool == null || !bool.booleanValue()) {
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (b.this.c.h()) {
                    if (!tDConfig.isShouldFlush(b.this.c.c())) {
                        return;
                    }
                    do {
                        synchronized (b.this.d) {
                            a2 = b.this.d.a(c.EnumC0013c.EVENTS, str, 50);
                        }
                        if (a2 == null) {
                            return;
                        }
                        i = 0;
                        String str3 = a2[0];
                        String str4 = a2[1];
                        try {
                            try {
                                try {
                                    try {
                                        try {
                                            jSONArray = new JSONArray(str4);
                                            try {
                                                jSONObject = new JSONObject();
                                                try {
                                                    jSONObject.put("data", jSONArray);
                                                    jSONObject.put("#app_id", tDConfig.mToken);
                                                    jSONObject.put("#flush_time", System.currentTimeMillis());
                                                } catch (JSONException e2) {
                                                    TDLog.w("ThinkingAnalytics.DataHandle", "Invalid data: " + jSONObject.toString());
                                                    throw e2;
                                                }
                                            } catch (JSONException unused) {
                                                if (!TextUtils.isEmpty("Cannot post message due to JSONException, the data will be deleted")) {
                                                    TDLog.e("ThinkingAnalytics.DataHandle", "Cannot post message due to JSONException, the data will be deleted");
                                                }
                                                synchronized (b.this.d) {
                                                    a4 = b.this.d.a(str3, c.EnumC0013c.EVENTS, str);
                                                    format = String.format(Locale.CHINA, "Events flushed. [left = %d]", Integer.valueOf(a4));
                                                }
                                            }
                                        } catch (MalformedInputException unused2) {
                                            z2 = false;
                                        }
                                    } catch (Throwable th) {
                                        th = th;
                                        z = false;
                                    }
                                } catch (g.a e3) {
                                    str2 = "Cannot post message to [" + tDConfig.getServerUrl() + "] due to " + e3.getMessage();
                                    if (TextUtils.isEmpty(str2)) {
                                    }
                                    TDLog.e("ThinkingAnalytics.DataHandle", str2);
                                }
                            } catch (IOException e4) {
                                str2 = "Cannot post message to [" + tDConfig.getServerUrl() + "] due to " + e4.getMessage();
                                if (TextUtils.isEmpty(str2)) {
                                }
                                TDLog.e("ThinkingAnalytics.DataHandle", str2);
                            }
                            try {
                                JSONObject jSONObject2 = new JSONObject(this.c.a(tDConfig.getServerUrl(), jSONObject.toString(), false, tDConfig.getSSLSocketFactory(), a(jSONArray)));
                                jSONObject2.getString("code");
                                TDLog.d("ThinkingAnalytics.DataHandle", "[ThinkingData] Debug: Send event, Request = " + jSONObject.toString(4));
                                TDLog.d("ThinkingAnalytics.DataHandle", "[ThinkingData] Debug: Send event, Response =" + jSONObject2.toString(4));
                                if (!TextUtils.isEmpty(null)) {
                                    TDLog.e("ThinkingAnalytics.DataHandle", null);
                                }
                                synchronized (b.this.d) {
                                    a4 = b.this.d.a(str3, c.EnumC0013c.EVENTS, str);
                                }
                                format = String.format(Locale.CHINA, "Events flushed. [left = %d]", Integer.valueOf(a4));
                            } catch (MalformedInputException unused3) {
                                z2 = true;
                                try {
                                    String str5 = "Cannot interpret " + tDConfig.getServerUrl() + " as a URL. The data will be deleted.";
                                    if (!TextUtils.isEmpty(str5)) {
                                        TDLog.e("ThinkingAnalytics.DataHandle", str5);
                                    }
                                    if (z2) {
                                        synchronized (b.this.d) {
                                            a4 = b.this.d.a(str3, c.EnumC0013c.EVENTS, str);
                                        }
                                        format = String.format(Locale.CHINA, "Events flushed. [left = %d]", Integer.valueOf(a4));
                                        TDLog.i("ThinkingAnalytics.DataHandle", format);
                                        i = a4;
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    z = z2;
                                    if (!TextUtils.isEmpty(null)) {
                                        TDLog.e("ThinkingAnalytics.DataHandle", null);
                                    }
                                    if (z) {
                                        synchronized (b.this.d) {
                                            a3 = b.this.d.a(str3, c.EnumC0013c.EVENTS, str);
                                        }
                                        TDLog.i("ThinkingAnalytics.DataHandle", String.format(Locale.CHINA, "Events flushed. [left = %d]", Integer.valueOf(a3)));
                                    }
                                    throw th;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                z = true;
                                if (!TextUtils.isEmpty(null)) {
                                }
                                if (z) {
                                }
                                throw th;
                            }
                            TDLog.i("ThinkingAnalytics.DataHandle", format);
                            i = a4;
                        } catch (JSONException e5) {
                            TDLog.w("ThinkingAnalytics.DataHandle", "The data is invalid: " + str4);
                            throw e5;
                        }
                    } while (i > 0);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(TDConfig tDConfig, JSONObject jSONObject) {
            StringBuilder sb = new StringBuilder();
            sb.append("appid=");
            sb.append(tDConfig.mToken);
            if (jSONObject.optJSONObject("properties") != null) {
                TDPresetProperties presetProperties = ThinkingAnalyticsSDK.sharedInstance(tDConfig).getPresetProperties();
                String str = (presetProperties == null || TDPresetProperties.disableList.contains("#device_id")) ? "" : presetProperties.deviceId;
                if (TextUtils.isEmpty(str) && !TDPresetProperties.disableList.contains("#device_id")) {
                    str = e.e(tDConfig.mContext).a(tDConfig.mContext);
                }
                if (!TextUtils.isEmpty(str)) {
                    sb.append("&deviceId=");
                    sb.append(str);
                }
            }
            sb.append("&source=client&data=");
            sb.append(URLEncoder.encode(jSONObject.toString()));
            if (tDConfig.isDebugOnly()) {
                sb.append("&dryRun=1");
            }
            String a2 = p.a(tDConfig.getName(), 4);
            TDLog.d("ThinkingAnalytics.DataHandle", "uploading message(" + a2 + "):\n" + jSONObject.toString(4));
            JSONObject jSONObject2 = new JSONObject(this.c.a(tDConfig.getDebugUrl(), sb.toString(), true, tDConfig.getSSLSocketFactory(), d("1")));
            int i = jSONObject2.getInt("errorLevel");
            if (i == -1) {
                if (tDConfig.isDebugOnly()) {
                    TDLog.w("ThinkingAnalytics.DataHandle", "The data will be discarded due to this device is not allowed to debug for: " + a2);
                    return;
                }
                tDConfig.setMode(TDConfig.TDMode.NORMAL);
                throw new k("Fallback to normal mode due to the device is not allowed to debug for: " + a2);
            }
            Boolean bool = this.d.get(tDConfig.getName());
            if (bool == null || !bool.booleanValue()) {
                this.d.put(tDConfig.getName(), true);
                tDConfig.setAllowDebug();
            }
            if (i == 0) {
                TDLog.d("ThinkingAnalytics.DataHandle", "Upload debug data successfully for " + a2);
                return;
            }
            try {
                if (jSONObject2.has("errorProperties")) {
                    TDLog.d("ThinkingAnalytics.DataHandle", " Error Properties: \n" + jSONObject2.getJSONArray("errorProperties").toString(4));
                }
                if (jSONObject2.has("errorReasons")) {
                    TDLog.d("ThinkingAnalytics.DataHandle", "Error Reasons: \n" + jSONObject2.getJSONArray("errorReasons").toString(4));
                }
            } catch (Exception unused) {
            }
            if (tDConfig.shouldThrowException()) {
                if (1 == i) {
                    throw new k("Invalid properties. Please refer to the logcat log for detail info.");
                }
                if (2 == i) {
                    throw new k("Invalid data format. Please refer to the logcat log for detail info.");
                }
                throw new k("Unknown error level: " + i);
            }
        }

        private Map<String, String> d(String str) {
            HashMap hashMap = new HashMap();
            hashMap.put("TA-Integration-Type", e.i());
            hashMap.put("TA-Integration-Version", e.j());
            hashMap.put("TA-Integration-Count", str);
            hashMap.put("TA-Integration-Extra", "Android");
            return hashMap;
        }

        void a() {
            Message obtain = Message.obtain();
            obtain.what = 6;
            this.b.sendMessage(obtain);
        }

        void a(cn.thinkingdata.analytics.f.a aVar) {
            if (aVar == null) {
                return;
            }
            Message obtain = Message.obtain();
            obtain.what = 5;
            obtain.obj = aVar;
            if (aVar.i) {
                return;
            }
            this.b.sendMessage(obtain);
        }

        void a(String str) {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            Message obtain = Message.obtain();
            obtain.what = 3;
            obtain.obj = str;
            this.b.sendMessageAtFrontOfQueue(obtain);
        }

        void a(String str, long j) {
            synchronized (this.a) {
                Handler handler = this.b;
                if (handler != null && !handler.hasMessages(0, str) && !this.b.hasMessages(1, str)) {
                    Message obtain = Message.obtain();
                    obtain.what = 0;
                    obtain.obj = str;
                    try {
                        this.b.sendMessageDelayed(obtain, j);
                    } catch (IllegalStateException e) {
                        TDLog.w("ThinkingAnalytics.DataHandle", "The app might be quiting: " + e.getMessage());
                    }
                }
            }
        }

        void b(cn.thinkingdata.analytics.f.a aVar) {
            if (aVar == null) {
                return;
            }
            Message obtain = Message.obtain();
            obtain.what = 4;
            obtain.obj = aVar;
            if (aVar.i) {
                return;
            }
            this.b.sendMessage(obtain);
        }

        void b(String str) {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            Message obtain = Message.obtain();
            obtain.what = 2;
            obtain.obj = str;
            this.b.sendMessage(obtain);
        }

        void c(String str) {
            synchronized (this.a) {
                Handler handler = this.b;
                if (handler != null && !handler.hasMessages(1, str)) {
                    Message obtain = Message.obtain();
                    obtain.what = 0;
                    obtain.obj = str;
                    this.b.sendMessage(obtain);
                }
            }
        }
    }

    b(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.e = applicationContext;
        this.c = e.e(applicationContext);
        this.d = a(applicationContext);
        C0012b c0012b = new C0012b();
        this.a = c0012b;
        this.b = new a();
        c0012b.a();
    }

    public static b b(Context context) {
        b bVar;
        Map<Context, b> map = g;
        synchronized (map) {
            Context applicationContext = context.getApplicationContext();
            if (map.containsKey(applicationContext)) {
                bVar = map.get(applicationContext);
            } else {
                bVar = new b(applicationContext);
                map.put(applicationContext, bVar);
            }
        }
        return bVar;
    }

    protected c a(Context context) {
        return c.a(context);
    }

    protected cn.thinkingdata.analytics.utils.g a() {
        return new cn.thinkingdata.analytics.utils.b();
    }

    public void a(cn.thinkingdata.analytics.f.a aVar) {
        if (aVar.i) {
            return;
        }
        this.a.b(aVar);
    }

    public void a(String str) {
        this.b.a(str);
    }

    public void a(String str, boolean z) {
        synchronized (this.f) {
            if (z) {
                this.f.put(str, true);
            } else {
                this.f.remove(str);
            }
        }
    }

    public void b(cn.thinkingdata.analytics.f.a aVar) {
        if (aVar.i) {
            return;
        }
        this.a.a(aVar);
    }

    public void b(String str) {
        this.b.b(str);
    }

    public void c(cn.thinkingdata.analytics.f.a aVar) {
        this.b.a(aVar);
    }

    public void c(String str) {
        this.a.b(str);
    }

    protected TDConfig d(String str) {
        return TDConfig.getInstance(this.e, str);
    }

    protected int e(String str) {
        TDConfig d = d(str);
        if (d == null) {
            return 20;
        }
        return d.getFlushBulkSize();
    }

    protected int f(String str) {
        TDConfig d = d(str);
        if (d == null) {
            return 15000;
        }
        return d.getFlushInterval();
    }
}
