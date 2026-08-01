package cn.thinkingdata.analytics;

import android.content.Context;
import android.text.TextUtils;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import cn.thinkingdata.analytics.ThinkingAnalyticsSDK;
import cn.thinkingdata.analytics.encrypt.TDSecreteKey;
import cn.thinkingdata.analytics.f.f;
import cn.thinkingdata.analytics.g.g;
import cn.thinkingdata.analytics.utils.p;
import cn.thinkingdata.core.utils.TDLog;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class TDConfig {
    private static final String TAG = "ThinkingAnalytics.TDConfig";
    public static final String VERSION = "3.0.0-beta.1";
    private static final Map<Context, Map<String, TDConfig>> sInstances = new HashMap();
    private volatile boolean mAllowedDebug;
    private final cn.thinkingdata.analytics.g.d mConfigStoragePlugin;
    private final String mConfigUrl;
    public final Context mContext;
    private final f mContextConfig;
    private final String mDebugUrl;
    private TimeZone mDefaultTimeZone;
    private boolean mEnableMutiprocess;
    private SSLSocketFactory mSSLSocketFactory;
    private final String mServerUrl;
    public final String mToken;
    private volatile String name;
    private final Set<String> mDisabledEvents = new HashSet();
    private final ReadWriteLock mDisabledEventsLock = new ReentrantReadWriteLock();
    private volatile ModeEnum mMode = ModeEnum.NORMAL;
    private int mNetworkType = 255;
    private volatile boolean mTrackOldData = true;
    private TDSecreteKey secreteKey = null;
    boolean mEnableEncrypt = false;

    /* loaded from: classes.dex */
    public enum ModeEnum {
        NORMAL,
        DEBUG,
        DEBUG_ONLY
    }

    /* loaded from: classes.dex */
    public final class NetworkType {
        public static final int TYPE_2G = 1;
        public static final int TYPE_3G = 2;
        public static final int TYPE_4G = 4;
        public static final int TYPE_5G = 16;
        public static final int TYPE_ALL = 255;
        public static final int TYPE_WIFI = 8;

        public NetworkType() {
        }
    }

    /* loaded from: classes.dex */
    public enum TDMode {
        NORMAL,
        DEBUG,
        DEBUG_ONLY
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class a implements Runnable {
        a() {
        }

        /* JADX DEBUG: Multi-variable search result rejected for r8v3, resolved type: java.lang.Object[] */
        /* JADX DEBUG: Multi-variable search result rejected for r8v4, resolved type: java.lang.Object[] */
        /* JADX DEBUG: Multi-variable search result rejected for r8v5, resolved type: java.lang.Object[] */
        /* JADX DEBUG: Multi-variable search result rejected for r8v7, resolved type: java.lang.Object[] */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:108:0x027c  */
        /* JADX WARN: Removed duplicated region for block: B:110:? A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:111:0x0272 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void run() {
            HttpURLConnection httpURLConnection;
            System.currentTimeMillis();
            InputStream inputStream = null;
            Object[] objArr = 0;
            InputStream inputStream2 = null;
            Object[] objArr2 = 0;
            Object[] objArr3 = 0;
            Object[] objArr4 = 0;
            try {
                try {
                    httpURLConnection = (HttpURLConnection) new URL(TDConfig.this.mConfigUrl).openConnection();
                    try {
                        try {
                            SSLSocketFactory sSLSocketFactory = TDConfig.this.getSSLSocketFactory();
                            if (sSLSocketFactory != null && (httpURLConnection instanceof HttpsURLConnection)) {
                                ((HttpsURLConnection) httpURLConnection).setSSLSocketFactory(sSLSocketFactory);
                            }
                            httpURLConnection.setConnectTimeout(15000);
                            httpURLConnection.setReadTimeout(AccessibilityNodeInfoCompat.EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_MAX_LENGTH);
                            httpURLConnection.setRequestMethod("GET");
                            if (200 == httpURLConnection.getResponseCode()) {
                                inputStream2 = httpURLConnection.getInputStream();
                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream2));
                                StringBuffer stringBuffer = new StringBuffer();
                                while (true) {
                                    String readLine = bufferedReader.readLine();
                                    if (readLine == null) {
                                        break;
                                    } else {
                                        stringBuffer.append(readLine);
                                    }
                                }
                                JSONObject jSONObject = new JSONObject(stringBuffer.toString());
                                if (jSONObject.getString("code").equals("0")) {
                                    int intValue = ((Integer) TDConfig.this.mConfigStoragePlugin.a(g.FLUSH_INTERVAL)).intValue();
                                    int intValue2 = ((Integer) TDConfig.this.mConfigStoragePlugin.a(g.FLUSH_SIZE)).intValue();
                                    try {
                                        JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                                        intValue = jSONObject2.getInt("sync_interval") * 1000;
                                        intValue2 = jSONObject2.getInt("sync_batch_size");
                                        if (jSONObject2.has("secret_key")) {
                                            JSONObject jSONObject3 = jSONObject2.getJSONObject("secret_key");
                                            if (jSONObject3.has("key") && jSONObject3.has(ClientCookie.VERSION_ATTR) && jSONObject3.has("symmetric") && jSONObject3.has("asymmetric")) {
                                                String string = jSONObject3.getString("key");
                                                int i = jSONObject3.getInt(ClientCookie.VERSION_ATTR);
                                                String string2 = jSONObject3.getString("symmetric");
                                                String string3 = jSONObject3.getString("asymmetric");
                                                if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2) && !TextUtils.isEmpty(string3)) {
                                                    TDConfig.this.secreteKey = new TDSecreteKey(string, i, string2, string3);
                                                }
                                            }
                                        }
                                        TDLog.i(TDConfig.TAG, "[ThinkingData] Info: Get remote config success (" + p.a(TDConfig.this.mToken, 4) + "):\n" + jSONObject2.toString(4));
                                        if (jSONObject2.has("disable_event_list")) {
                                            TDConfig.this.mDisabledEventsLock.writeLock().lock();
                                            try {
                                                JSONArray jSONArray = jSONObject2.getJSONArray("disable_event_list");
                                                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                                                    TDConfig.this.mDisabledEvents.add(jSONArray.getString(i2));
                                                }
                                                TDConfig.this.mDisabledEventsLock.writeLock().unlock();
                                            } catch (Throwable th) {
                                                TDConfig.this.mDisabledEventsLock.writeLock().unlock();
                                                throw th;
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if (((Integer) TDConfig.this.mConfigStoragePlugin.a(g.FLUSH_SIZE)).intValue() != intValue2) {
                                        TDConfig.this.mConfigStoragePlugin.a(g.FLUSH_SIZE, Integer.valueOf(intValue2));
                                    }
                                    if (((Integer) TDConfig.this.mConfigStoragePlugin.a(g.FLUSH_INTERVAL)).intValue() != intValue) {
                                        TDConfig.this.mConfigStoragePlugin.a(g.FLUSH_INTERVAL, Integer.valueOf(intValue));
                                    }
                                }
                                inputStream2.close();
                                bufferedReader.close();
                            } else {
                                TDLog.d(TDConfig.TAG, "Getting remote config failed, responseCode is " + httpURLConnection.getResponseCode());
                            }
                            if (inputStream2 != null) {
                                try {
                                    inputStream2.close();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                            }
                            if (httpURLConnection == null) {
                                return;
                            }
                        } catch (JSONException e3) {
                            e = e3;
                            TDLog.d(TDConfig.TAG, "Getting remote config failed due to: " + e.getMessage());
                            if (0 != 0) {
                                try {
                                    (objArr2 == true ? 1 : 0).close();
                                } catch (IOException e4) {
                                    e4.printStackTrace();
                                }
                            }
                            if (httpURLConnection == null) {
                                return;
                            }
                            httpURLConnection.disconnect();
                        }
                    } catch (IOException e5) {
                        e = e5;
                        TDLog.d(TDConfig.TAG, "Getting remote config failed due to: " + e.getMessage());
                        if (0 != 0) {
                            try {
                                (objArr3 == true ? 1 : 0).close();
                            } catch (IOException e6) {
                                e6.printStackTrace();
                            }
                        }
                        if (httpURLConnection == null) {
                            return;
                        }
                        httpURLConnection.disconnect();
                    } catch (Exception e7) {
                        e = e7;
                        TDLog.d(TDConfig.TAG, "Getting remote config failed due to: " + e.getMessage());
                        if (0 != 0) {
                            try {
                                (objArr4 == true ? 1 : 0).close();
                            } catch (IOException e8) {
                                e8.printStackTrace();
                            }
                        }
                        if (httpURLConnection == null) {
                            return;
                        }
                        httpURLConnection.disconnect();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (0 != 0) {
                        try {
                            inputStream.close();
                        } catch (IOException e9) {
                            e9.printStackTrace();
                        }
                    }
                    if (0 != 0) {
                        throw th;
                    }
                    (objArr == true ? 1 : 0).disconnect();
                    throw th;
                }
            } catch (IOException e10) {
                e = e10;
                httpURLConnection = null;
            } catch (JSONException e11) {
                e = e11;
                httpURLConnection = null;
            } catch (Exception e12) {
                e = e12;
                httpURLConnection = null;
            } catch (Throwable th3) {
                th = th3;
                if (0 != 0) {
                }
                if (0 != 0) {
                }
            }
            httpURLConnection.disconnect();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static /* synthetic */ class b {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b;

        static {
            int[] iArr = new int[ThinkingAnalyticsSDK.ThinkingdataNetworkType.values().length];
            b = iArr;
            try {
                iArr[ThinkingAnalyticsSDK.ThinkingdataNetworkType.NETWORKTYPE_WIFI.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                b[ThinkingAnalyticsSDK.ThinkingdataNetworkType.NETWORKTYPE_DEFAULT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                b[ThinkingAnalyticsSDK.ThinkingdataNetworkType.NETWORKTYPE_ALL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[TDMode.values().length];
            a = iArr2;
            try {
                iArr2[TDMode.DEBUG.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[TDMode.DEBUG_ONLY.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[TDMode.NORMAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    private TDConfig(Context context, String str, String str2) {
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mContextConfig = f.a(applicationContext);
        this.mToken = str;
        this.mServerUrl = str2 + "/sync";
        this.mDebugUrl = str2 + "/data_debug";
        this.mConfigUrl = str2 + "/config?appid=" + str;
        this.mConfigStoragePlugin = new cn.thinkingdata.analytics.g.d(applicationContext, str);
        this.mEnableMutiprocess = false;
    }

    public static TDConfig getInstance(Context context, String str) {
        try {
            return getInstance(context, str, "");
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    public static TDConfig getInstance(Context context, String str, String str2) {
        return getInstance(context, str, str2, str);
    }

    public static TDConfig getInstance(Context context, String str, String str2, String str3) {
        TDConfig tDConfig;
        String str4;
        Context applicationContext = context.getApplicationContext();
        Map<Context, Map<String, TDConfig>> map = sInstances;
        synchronized (map) {
            Map<String, TDConfig> map2 = map.get(applicationContext);
            if (map2 == null) {
                map2 = new HashMap<>();
                map.put(applicationContext, map2);
            }
            String replace = str.replace(" ", "");
            String replace2 = str3.replace(" ", "");
            tDConfig = map2.get(replace2);
            if (tDConfig == null) {
                try {
                    URL url = new URL(str2);
                    StringBuilder sb = new StringBuilder();
                    sb.append(url.getProtocol());
                    sb.append("://");
                    sb.append(url.getHost());
                    if (url.getPort() > 0) {
                        str4 = ":" + url.getPort();
                    } else {
                        str4 = "";
                    }
                    sb.append(str4);
                    TDConfig tDConfig2 = new TDConfig(applicationContext, replace, sb.toString());
                    tDConfig2.setName(replace2);
                    map2.put(replace2, tDConfig2);
                    tDConfig2.getRemoteConfig();
                    tDConfig = tDConfig2;
                } catch (MalformedURLException e) {
                    TDLog.e(TAG, "Invalid server URL: " + str2);
                    throw new IllegalArgumentException(e);
                }
            }
        }
        return tDConfig;
    }

    private void getRemoteConfig() {
        new Thread(new a()).start();
    }

    private void setName(String str) {
        this.name = str;
    }

    public TDConfig enableEncrypt(int i, String str) {
        this.mEnableEncrypt = true;
        if (this.secreteKey == null) {
            TDSecreteKey tDSecreteKey = new TDSecreteKey();
            this.secreteKey = tDSecreteKey;
            tDSecreteKey.version = i;
            tDSecreteKey.publicKey = str;
            tDSecreteKey.asymmetricEncryption = "RSA";
            tDSecreteKey.symmetricEncryption = "AES";
        }
        return this;
    }

    public TDConfig enableEncrypt(boolean z) {
        this.mEnableEncrypt = z;
        return this;
    }

    public String getDebugUrl() {
        return this.mDebugUrl;
    }

    public synchronized TimeZone getDefaultTimeZone() {
        TimeZone timeZone;
        timeZone = this.mDefaultTimeZone;
        if (timeZone == null) {
            timeZone = TimeZone.getDefault();
        }
        return timeZone;
    }

    public int getFlushBulkSize() {
        return ((Integer) this.mConfigStoragePlugin.a(g.FLUSH_SIZE)).intValue();
    }

    public int getFlushInterval() {
        return ((Integer) this.mConfigStoragePlugin.a(g.FLUSH_INTERVAL)).intValue();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String getMainProcessName() {
        return this.mContextConfig.b();
    }

    public ModeEnum getMode() {
        return this.mMode;
    }

    public String getName() {
        return this.name;
    }

    public synchronized SSLSocketFactory getSSLSocketFactory() {
        return this.mSSLSocketFactory;
    }

    public TDSecreteKey getSecreteKey() {
        return this.secreteKey;
    }

    public String getServerUrl() {
        return this.mServerUrl;
    }

    Map<String, TDConfig> getTDConfigMap() {
        return sInstances.get(this.mContext);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isDebug() {
        return ModeEnum.DEBUG.equals(this.mMode);
    }

    public boolean isDebugOnly() {
        return ModeEnum.DEBUG_ONLY.equals(this.mMode);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isDisabledEvent(String str) {
        this.mDisabledEventsLock.readLock().lock();
        try {
            return this.mDisabledEvents.contains(str);
        } finally {
            this.mDisabledEventsLock.readLock().unlock();
        }
    }

    public boolean isEnableMutiprocess() {
        return this.mEnableMutiprocess;
    }

    public boolean isNormal() {
        return ModeEnum.NORMAL.equals(this.mMode);
    }

    public synchronized boolean isShouldFlush(String str) {
        return (p.a(str) & this.mNetworkType) != 0;
    }

    public void setAllowDebug() {
        this.mAllowedDebug = true;
    }

    public synchronized TDConfig setDefaultTimeZone(TimeZone timeZone) {
        this.mDefaultTimeZone = timeZone;
        return this;
    }

    public TDConfig setMode(ModeEnum modeEnum) {
        this.mMode = modeEnum;
        return this;
    }

    public TDConfig setMode(TDMode tDMode) {
        ModeEnum modeEnum;
        int i = b.a[tDMode.ordinal()];
        if (i == 1) {
            modeEnum = ModeEnum.DEBUG;
        } else {
            if (i != 2) {
                if (i == 3) {
                    modeEnum = ModeEnum.NORMAL;
                }
                return this;
            }
            modeEnum = ModeEnum.DEBUG_ONLY;
        }
        this.mMode = modeEnum;
        return this;
    }

    public TDConfig setMutiprocess(boolean z) {
        this.mEnableMutiprocess = z;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void setNetworkType(ThinkingAnalyticsSDK.ThinkingdataNetworkType thinkingdataNetworkType) {
        int i = b.b[thinkingdataNetworkType.ordinal()];
        if (i == 1) {
            this.mNetworkType = 8;
        } else if (i == 2 || i == 3) {
            this.mNetworkType = 31;
        }
    }

    public synchronized TDConfig setSSLSocketFactory(SSLSocketFactory sSLSocketFactory) {
        if (sSLSocketFactory != null) {
            this.mSSLSocketFactory = sSLSocketFactory;
            getRemoteConfig();
        }
        return this;
    }

    public TDConfig setSecretKey(TDSecreteKey tDSecreteKey) {
        if (this.secreteKey == null) {
            this.secreteKey = tDSecreteKey;
        }
        return this;
    }

    public TDConfig setTrackOldData(boolean z) {
        this.mTrackOldData = z;
        return this;
    }

    public boolean shouldThrowException() {
        return false;
    }

    public boolean trackOldData() {
        return this.mTrackOldData;
    }
}
