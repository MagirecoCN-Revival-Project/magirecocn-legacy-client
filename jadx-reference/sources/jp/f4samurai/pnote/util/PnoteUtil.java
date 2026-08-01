package jp.f4samurai.pnote.util;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import androidx.core.app.NotificationCompat;
import cz.msebera.android.httpclient.HttpHeaders;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import jp.f4samurai.madomagi.BuildConfig;

/* loaded from: classes.dex */
public class PnoteUtil {
    protected static String API_VERSION = null;
    protected static String APP_ID = null;
    protected static String BASE_URL = null;
    protected static String NOAH_ID = null;
    protected static String SECRET_KEY = null;
    protected static String SENDER_ID = null;
    protected static final String TAG = "Pnote";
    private static boolean isRegisting;
    private static RegistDeviceCallback registDeviceCallback;

    /* loaded from: classes.dex */
    public interface RegistDeviceCallback {
        void registDeviceCallback();
    }

    /* renamed from: -$$Nest$smgetLanguage, reason: not valid java name */
    static /* bridge */ /* synthetic */ String m94$$Nest$smgetLanguage() {
        return getLanguage();
    }

    public static void initialize(Context context) {
        SENDER_ID = BuildConfig.PNOTE_SENDER_ID;
        NOAH_ID = "YOUR_NOAH_ID";
        BASE_URL = "https://api-pnote2.noahapps.jp/";
        APP_ID = BuildConfig.PNOTE_APP_ID;
        SECRET_KEY = BuildConfig.PNOTE_SECRET_KEY;
        API_VERSION = "1.7.0";
    }

    public static void setRegistDeviceCallback(RegistDeviceCallback registDeviceCallback2) {
        registDeviceCallback = registDeviceCallback2;
    }

    public static synchronized void registDevice(final Context context, final String str, final String str2) {
        synchronized (PnoteUtil.class) {
            if (isRegisting) {
                return;
            }
            isRegisting = true;
            new Thread(new Runnable() { // from class: jp.f4samurai.pnote.util.PnoteUtil.1
                @Override // java.lang.Runnable
                public void run() {
                    TreeMap treeMap = new TreeMap();
                    String deviceId = PnoteUtil.getDeviceId(context);
                    if (deviceId == null) {
                        return;
                    }
                    treeMap.put("device_id", deviceId);
                    treeMap.put("device_token", str);
                    treeMap.put("noah_id", context.getSharedPreferences(PnoteUtil.TAG, 0).getString("noah_id", PnoteUtil.NOAH_ID));
                    treeMap.put("guid", context.getSharedPreferences(PnoteUtil.TAG, 0).getString("sender", str2));
                    treeMap.put("app_id", PnoteUtil.APP_ID);
                    treeMap.put("app_version", PnoteUtil.getApplicationVersionName(context));
                    treeMap.put("os", "2");
                    treeMap.put("os_version", Build.VERSION.RELEASE);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("Z", Locale.US);
                    simpleDateFormat.setTimeZone(TimeZone.getDefault());
                    treeMap.put("timezone", simpleDateFormat.format(new Date()));
                    treeMap.put("language", PnoteUtil.m94$$Nest$smgetLanguage());
                    treeMap.put("api_version", PnoteUtil.API_VERSION);
                    try {
                        PnoteUtil.post("device/regist", treeMap);
                        if (PnoteUtil.registDeviceCallback != null) {
                            PnoteUtil.registDeviceCallback.registDeviceCallback();
                            PnoteUtil.registDeviceCallback = null;
                        }
                    } catch (Exception unused) {
                    }
                    PnoteUtil.isRegisting = false;
                }
            }).start();
        }
    }

    public static void unregistDevice(final Context context) {
        new Thread(new Runnable() { // from class: jp.f4samurai.pnote.util.PnoteUtil.2
            @Override // java.lang.Runnable
            public void run() {
                String deviceId = PnoteUtil.getDeviceId(context);
                if (deviceId == null) {
                    return;
                }
                TreeMap treeMap = new TreeMap();
                treeMap.put("device_id", deviceId);
                treeMap.put("app_id", PnoteUtil.APP_ID);
                treeMap.put("api_version", PnoteUtil.API_VERSION);
                try {
                    PnoteUtil.post("device/unregist", treeMap);
                } catch (IOException | InvalidKeyException | NoSuchAlgorithmException unused) {
                }
            }
        }).start();
    }

    public static void sendMessage(Context context, final String str, final String str2, final String str3, final String str4) {
        new Thread(new Runnable() { // from class: jp.f4samurai.pnote.util.PnoteUtil.3
            @Override // java.lang.Runnable
            public void run() {
                TreeMap treeMap = new TreeMap();
                treeMap.put("app_id", PnoteUtil.APP_ID);
                treeMap.put("sender_guid", str);
                treeMap.put("receiver_guids", str2);
                treeMap.put("launch", str4);
                try {
                    treeMap.put("message", PnoteUtil.urlEncode(str3));
                } catch (UnsupportedEncodingException unused) {
                }
                treeMap.put("language", PnoteUtil.m94$$Nest$smgetLanguage());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("Z", Locale.getDefault());
                simpleDateFormat.setTimeZone(TimeZone.getDefault());
                treeMap.put("timezone", simpleDateFormat.format(new Date()));
                treeMap.put("api_version", PnoteUtil.API_VERSION);
                try {
                    PnoteUtil.post("message/regist", treeMap);
                } catch (IOException | InvalidKeyException | NoSuchAlgorithmException unused2) {
                }
            }
        }).start();
    }

    public static void sendMessageToNoahIds(Context context, final String str, final String str2, final String str3, final String str4) {
        new Thread(new Runnable() { // from class: jp.f4samurai.pnote.util.PnoteUtil.4
            @Override // java.lang.Runnable
            public void run() {
                TreeMap treeMap = new TreeMap();
                treeMap.put("app_id", PnoteUtil.APP_ID);
                treeMap.put("sender_guid", str);
                treeMap.put("receiver_noahids", str2);
                treeMap.put("launch", str4);
                try {
                    treeMap.put("message", PnoteUtil.urlEncode(str3));
                } catch (UnsupportedEncodingException unused) {
                }
                treeMap.put("language", PnoteUtil.m94$$Nest$smgetLanguage());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("Z", Locale.getDefault());
                simpleDateFormat.setTimeZone(TimeZone.getDefault());
                treeMap.put("timezone", simpleDateFormat.format(new Date()));
                treeMap.put("api_version", PnoteUtil.API_VERSION);
                try {
                    PnoteUtil.post("message/regist", treeMap);
                } catch (IOException | InvalidKeyException | NoSuchAlgorithmException unused2) {
                }
            }
        }).start();
    }

    public static void sendMessageCounter(final Context context, final String str) {
        new Thread(new Runnable() { // from class: jp.f4samurai.pnote.util.PnoteUtil.5
            @Override // java.lang.Runnable
            public void run() {
                TreeMap treeMap = new TreeMap();
                treeMap.put("app_id", PnoteUtil.APP_ID);
                treeMap.put("mid", str);
                treeMap.put("os", "2");
                treeMap.put("api_version", PnoteUtil.API_VERSION);
                String deviceId = PnoteUtil.getDeviceId(context);
                if (deviceId == null || deviceId.equals("")) {
                    return;
                }
                treeMap.put("device_id", deviceId);
                try {
                    PnoteUtil.post("message/counter", treeMap);
                } catch (IOException | InvalidKeyException | NoSuchAlgorithmException unused) {
                }
            }
        }).start();
    }

    public static void registTags(final Context context, final String str, final String str2) {
        new Thread(new Runnable() { // from class: jp.f4samurai.pnote.util.PnoteUtil.6
            @Override // java.lang.Runnable
            public void run() {
                TreeMap treeMap = new TreeMap();
                treeMap.put("app_id", PnoteUtil.APP_ID);
                treeMap.put("tags", str);
                String deviceId = PnoteUtil.getDeviceId(context);
                if (deviceId == null || deviceId.equals("")) {
                    return;
                }
                treeMap.put("device_id", deviceId);
                String str3 = str2;
                if (str3 != null && !str3.equals("")) {
                    treeMap.put("guid", str2);
                }
                treeMap.put("api_version", PnoteUtil.API_VERSION);
                try {
                    PnoteUtil.post("tag/regist", treeMap);
                } catch (IOException | InvalidKeyException | NoSuchAlgorithmException unused) {
                }
            }
        }).start();
    }

    public static void registTags(final Context context, final String str, final ArrayList<Integer> arrayList, final String str2) {
        new Thread(new Runnable() { // from class: jp.f4samurai.pnote.util.PnoteUtil.7
            @Override // java.lang.Runnable
            public void run() {
                TreeMap treeMap = new TreeMap();
                treeMap.put("app_id", PnoteUtil.APP_ID);
                treeMap.put("tags", str);
                int i = 0;
                while (true) {
                    String str3 = "";
                    if (i >= arrayList.size()) {
                        break;
                    }
                    int intValue = ((Integer) arrayList.get(i)).intValue();
                    String valueOf = String.valueOf(intValue);
                    if (intValue >= 0) {
                        str3 = valueOf;
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append("tag");
                    i++;
                    sb.append(i);
                    treeMap.put(sb.toString(), str3);
                }
                String deviceId = PnoteUtil.getDeviceId(context);
                if (deviceId == null || deviceId.equals("")) {
                    return;
                }
                treeMap.put("device_id", deviceId);
                String str4 = str2;
                if (str4 != null && !str4.equals("")) {
                    treeMap.put("guid", str2);
                }
                treeMap.put("api_version", PnoteUtil.API_VERSION);
                try {
                    PnoteUtil.post("tag/regist", treeMap);
                } catch (IOException | InvalidKeyException | NoSuchAlgorithmException unused) {
                }
            }
        }).start();
    }

    public static void registSingleTag(final Context context, final int i, final int i2, final String str) {
        new Thread(new Runnable() { // from class: jp.f4samurai.pnote.util.PnoteUtil.8
            @Override // java.lang.Runnable
            public void run() {
                TreeMap treeMap = new TreeMap();
                treeMap.put("app_id", PnoteUtil.APP_ID);
                String valueOf = String.valueOf(i2);
                if (i2 < 0) {
                    valueOf = "";
                }
                treeMap.put("tag" + i, valueOf);
                String deviceId = PnoteUtil.getDeviceId(context);
                if (deviceId == null || deviceId.equals("")) {
                    return;
                }
                treeMap.put("device_id", deviceId);
                String str2 = str;
                if (str2 != null && !str2.equals("")) {
                    treeMap.put("guid", str);
                }
                treeMap.put("api_version", PnoteUtil.API_VERSION);
                try {
                    PnoteUtil.post("tag/regist", treeMap);
                } catch (IOException | InvalidKeyException | NoSuchAlgorithmException unused) {
                }
            }
        }).start();
    }

    public static void registNotification(Context context, final String str, final String str2, final Map<String, String> map) {
        new Thread(new Runnable() { // from class: jp.f4samurai.pnote.util.PnoteUtil.9
            @Override // java.lang.Runnable
            public void run() {
                TreeMap treeMap = new TreeMap();
                treeMap.put("app_id", PnoteUtil.APP_ID);
                treeMap.put("api_version", PnoteUtil.API_VERSION);
                treeMap.put("send_os", str2);
                treeMap.put("message", str);
                String[] strArr = {"badge", "launch", "reservation", "sending_at", "sound", "debug"};
                for (int i = 0; i < 6; i++) {
                    if (map.containsKey(strArr[i]) && map.get(strArr[i]) != "") {
                        treeMap.put(strArr[i], (String) map.get(strArr[i]));
                    }
                }
                for (Map.Entry entry : treeMap.entrySet()) {
                }
                try {
                    PnoteUtil.post("notification/regist", treeMap);
                } catch (IOException | InvalidKeyException | NoSuchAlgorithmException unused) {
                }
            }
        }).start();
    }

    public static void listNotification(Context context, final String str, final String str2, final Map<String, String> map) {
        new Thread(new Runnable() { // from class: jp.f4samurai.pnote.util.PnoteUtil.10
            @Override // java.lang.Runnable
            public void run() {
                TreeMap treeMap = new TreeMap();
                treeMap.put("app_id", PnoteUtil.APP_ID);
                treeMap.put("api_version", PnoteUtil.API_VERSION);
                treeMap.put("by", str);
                treeMap.put(NotificationCompat.CATEGORY_STATUS, str2);
                String[] strArr = {"start", "end", "page"};
                for (int i = 0; i < 3; i++) {
                    if (map.containsKey(strArr[i]) && map.get(strArr[i]) != "") {
                        treeMap.put(strArr[i], (String) map.get(strArr[i]));
                    }
                }
                for (Map.Entry entry : treeMap.entrySet()) {
                }
                try {
                    PnoteUtil.post("notification/list", treeMap);
                } catch (IOException | InvalidKeyException | NoSuchAlgorithmException unused) {
                }
            }
        }).start();
    }

    public static String post(String str, Map<String, String> map) throws InvalidKeyException, NoSuchAlgorithmException, IOException {
        URL url = new URL(BASE_URL + str);
        Map<String, String> addOAuthParams = addOAuthParams(new TreeMap());
        TreeMap treeMap = new TreeMap();
        treeMap.putAll(addOAuthParams);
        treeMap.putAll(map);
        addOAuthParams.put("oauth_signature", Base64.encodeToString(encryptHmacSha1(SECRET_KEY, createSignatureBaseString(HttpPost.METHOD_NAME, url.toString(), treeMap)), 2));
        String createAuthorizationHeader = createAuthorizationHeader(addOAuthParams);
        String retrieveParams = retrieveParams(map);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod(HttpPost.METHOD_NAME);
        httpURLConnection.addRequestProperty("Authorization", createAuthorizationHeader);
        httpURLConnection.addRequestProperty(HttpHeaders.ACCEPT, "*/*");
        PrintStream printStream = new PrintStream(httpURLConnection.getOutputStream());
        printStream.print(retrieveParams);
        printStream.close();
        httpURLConnection.connect();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String str2 = "";
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    return str2;
                }
                str2 = str2 + readLine;
            }
        } catch (IOException unused) {
            try {
                httpURLConnection.getResponseCode();
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
                String str3 = null;
                while (true) {
                    String readLine2 = bufferedReader2.readLine();
                    if (readLine2 == null) {
                        break;
                    }
                    str3 = str3 + readLine2;
                }
            } catch (IOException unused2) {
            }
            return null;
        }
    }

    private static Map<String, String> addOAuthParams(Map<String, String> map) {
        map.put("oauth_consumer_key", APP_ID);
        map.put("oauth_signature_method", "HMAC-SHA1");
        map.put("oauth_timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        map.put("oauth_nonce", String.valueOf(UUID.randomUUID()));
        map.put("oauth_version", "1.0");
        return map;
    }

    private static byte[] encryptHmacSha1(String str, String str2) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec((str + "&").getBytes(), "HmacSHA1");
        Mac mac = Mac.getInstance(secretKeySpec.getAlgorithm());
        mac.init(secretKeySpec);
        return mac.doFinal(str2.getBytes());
    }

    public static String retrieveParams(Map<String, String> map) throws UnsupportedEncodingException {
        String str = "";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String value = entry.getValue();
            if (value == null) {
                value = "";
            }
            str = str.equals("") ? str + entry.getKey() + "=" + urlEncode(value) : str + "&" + entry.getKey() + "=" + urlEncode(value);
        }
        return str;
    }

    public static String urlEncode(String str) throws UnsupportedEncodingException {
        String encode = URLEncoder.encode(str, "UTF-8");
        return encode.indexOf("+") != -1 ? encode.replaceAll("\\+", "%20") : encode;
    }

    public static String getDeviceId(Context context) {
        String string = Settings.Secure.getString(context.getContentResolver(), "android_id");
        if (string == null || string.equals("")) {
            string = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
        }
        if (string == null || string.equals("")) {
            return null;
        }
        return string;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getApplicationVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 128).versionName;
        } catch (Exception unused) {
            return "";
        }
    }

    private static String createSignatureBaseString(String str, String str2, Map<String, String> map) {
        try {
            return String.format("%s&%s&%s", str, urlEncode(str2), urlEncode(retrieveParams(map)));
        } catch (UnsupportedEncodingException unused) {
            return "";
        }
    }

    private static String createAuthorizationHeader(Map<String, String> map) {
        StringBuilder sb = new StringBuilder("OAuth ");
        try {
            for (String str : map.keySet()) {
                sb.append(String.format("%s=\"%s\", ", urlEncode(str), urlEncode(map.get(str))));
            }
            return sb.substring(0, sb.length() - 2);
        } catch (UnsupportedEncodingException unused) {
            return "";
        }
    }

    public static String getNowDateString(String str) {
        return new SimpleDateFormat(str).format(new Date());
    }

    private static String getLanguage() {
        String substring = Locale.getDefault().getLanguage().substring(0, 2);
        if (!substring.equals("zh")) {
            return substring;
        }
        String country = Locale.getDefault().getCountry();
        if (country.equals("CN")) {
            return substring + "-Hant";
        }
        if (!country.equals("TW")) {
            return substring;
        }
        return substring + "-Hans";
    }
}
