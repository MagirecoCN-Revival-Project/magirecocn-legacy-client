package io.kamihama.magianative;

import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.HttpHeaders;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.message.TokenParser;
import cz.msebera.android.httpclient.protocol.HTTP;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.time.DurationKt;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public final class CNMirrors {
    private static final int CONNECT_TIMEOUT_MS = 15000;
    public static final String DEFAULT_BASE = "https://assets.magireco.top/";
    private static final int MAX_JSON_BYTES = 262144;
    public static final String MIRRORS_URL = "https://assets.magireco.top/mirrors.json";
    private static final int READ_TIMEOUT_MS = 30000;
    private static final String TAG = "MagiaCNMirrors";
    private static volatile int cfgChunks = 4;
    private static volatile long cfgMinChunkBytes = 8388608;
    private static volatile int cfgSwitchAfterFail = 1;
    private static volatile int cfgStallSeconds = 25;
    private static volatile int cfgMinSpeedKbps = 32;
    private static volatile long cfgCooldownMs = 60000;
    private static volatile List<Mirror> mirrors = defaultList();
    private static volatile boolean loaded = false;

    public static int chunks() {
        return cfgChunks;
    }

    public static long minChunkBytes() {
        return cfgMinChunkBytes;
    }

    public static int switchAfterFail() {
        return cfgSwitchAfterFail;
    }

    public static int stallSeconds() {
        return cfgStallSeconds;
    }

    public static int minSpeedKbps() {
        return cfgMinSpeedKbps;
    }

    /* loaded from: classes3.dex */
    public static final class Mirror {
        public final String base;
        public final int chunks;
        public final boolean enabled;
        public final String name;
        public final int weight;
        final AtomicInteger failures = new AtomicInteger(0);
        volatile long cooldownUntilNs = 0;

        Mirror(String str, String str2, int i, int i2, boolean z) {
            this.name = str;
            this.base = str2;
            this.weight = i;
            this.chunks = i2;
            this.enabled = z;
        }

        public String urlFor(String str) {
            return this.base + str;
        }

        public int effectiveChunks() {
            int i = this.chunks;
            return i > 0 ? i : CNMirrors.cfgChunks;
        }

        public String toString() {
            return this.name + " <" + this.base + ">";
        }
    }

    private CNMirrors() {
    }

    private static List<Mirror> defaultList() {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(new Mirror("默认线路", DEFAULT_BASE, 100, 0, true));
        return arrayList;
    }

    public static synchronized void refresh(boolean z) {
        List<Mirror> parse;
        synchronized (CNMirrors.class) {
            try {
                parse = parse(fetch(MIRRORS_URL, z));
            } finally {
            }
            if (parse.isEmpty()) {
                CNLog.w(TAG, "mirrors.json 未包含任何可用线路，沿用默认线路");
                return;
            }
            mirrors = parse;
            loaded = true;
            StringBuilder sb = new StringBuilder();
            for (Mirror mirror : parse) {
                sb.append(TokenParser.SP).append(mirror.name).append('=').append(mirror.base);
            }
            CNLog.i(TAG, "线路列表已加载 count=" + parse.size() + ((Object) sb));
        }
    }

    public static boolean isLoaded() {
        return loaded;
    }

    private static String fetch(String str, boolean z) throws IOException {
        URL url = new URL(str);
        HttpURLConnection httpURLConnection = (HttpURLConnection) (z ? url.openConnection(Proxy.NO_PROXY) : url.openConnection());
        httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT_MS);
        httpURLConnection.setReadTimeout(READ_TIMEOUT_MS);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setInstanceFollowRedirects(true);
        httpURLConnection.setRequestProperty(HttpHeaders.ACCEPT, RequestParams.APPLICATION_JSON);
        httpURLConnection.setRequestProperty("Accept-Encoding", HTTP.IDENTITY_CODING);
        httpURLConnection.setRequestProperty("Connection", "close");
        InputStream inputStream = null;
        try {
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode < 200 || responseCode >= 300) {
                throw new IOException("mirrors.json HTTP " + responseCode);
            }
            InputStream inputStream2 = httpURLConnection.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[8192];
            int i = 0;
            while (true) {
                int read = inputStream2.read(bArr);
                if (read >= 0) {
                    i += read;
                    if (i > 262144) {
                        throw new IOException("mirrors.json 过大");
                    }
                    byteArrayOutputStream.write(bArr, 0, read);
                } else {
                    String str2 = new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
                    if (inputStream2 != null) {
                        try {
                            inputStream2.close();
                        } catch (IOException e) {
                        }
                    }
                    httpURLConnection.disconnect();
                    return str2;
                }
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                }
            }
            httpURLConnection.disconnect();
            throw th;
        }
    }

    private static List<Mirror> parse(String str) throws Exception {
        JSONObject jSONObject = new JSONObject(str);
        JSONObject optJSONObject = jSONObject.optJSONObject("settings");
        if (optJSONObject != null) {
            cfgChunks = clampInt(optJSONObject.optInt("chunks", cfgChunks), 1, 16);
            cfgMinChunkBytes = Math.max(1048576L, optJSONObject.optLong("min_chunk_bytes", cfgMinChunkBytes));
            cfgSwitchAfterFail = clampInt(optJSONObject.optInt("switch_after_failures", cfgSwitchAfterFail), 1, 10);
            cfgStallSeconds = clampInt(optJSONObject.optInt("stall_seconds", cfgStallSeconds), 5, HttpStatus.SC_MULTIPLE_CHOICES);
            cfgMinSpeedKbps = clampInt(optJSONObject.optInt("min_speed_kbps", cfgMinSpeedKbps), 0, DurationKt.NANOS_IN_MILLIS);
            cfgCooldownMs = Math.max(1000L, optJSONObject.optLong("cooldown_ms", cfgCooldownMs));
        }
        JSONArray optJSONArray = jSONObject.optJSONArray("mirrors");
        ArrayList arrayList = new ArrayList();
        if (optJSONArray == null) {
            return arrayList;
        }
        for (int i = 0; i < optJSONArray.length(); i++) {
            JSONObject optJSONObject2 = optJSONArray.optJSONObject(i);
            if (optJSONObject2 != null) {
                String trim = optJSONObject2.optString("base", "").trim();
                if (!trim.isEmpty()) {
                    String lowerCase = trim.toLowerCase(Locale.US);
                    if (!lowerCase.startsWith("http://") && !lowerCase.startsWith("https://")) {
                        CNLog.w(TAG, "忽略非 http(s) 线路: " + trim);
                    } else {
                        if (!trim.endsWith("/")) {
                            trim = trim + "/";
                        }
                        String str2 = trim;
                        arrayList.add(new Mirror(optJSONObject2.optString("name", str2), str2, optJSONObject2.optInt("weight", 0), optJSONObject2.optInt("chunks", 0), optJSONObject2.optBoolean("enabled", true)));
                    }
                }
            }
        }
        sortByWeightDesc(arrayList);
        return arrayList;
    }

    private static void sortByWeightDesc(List<Mirror> list) {
        for (int i = 1; i < list.size(); i++) {
            Mirror mirror = list.get(i);
            int i2 = i - 1;
            while (i2 >= 0 && list.get(i2).weight < mirror.weight) {
                list.set(i2 + 1, list.get(i2));
                i2--;
            }
            list.set(i2 + 1, mirror);
        }
    }

    private static int clampInt(int i, int i2, int i3) {
        return i < i2 ? i2 : i > i3 ? i3 : i;
    }

    public static List<Mirror> healthy() {
        List<Mirror> list = mirrors;
        long nanoTime = System.nanoTime();
        ArrayList arrayList = new ArrayList(list.size());
        for (Mirror mirror : list) {
            if (mirror.enabled && mirror.cooldownUntilNs <= nanoTime) {
                arrayList.add(mirror);
            }
        }
        if (!arrayList.isEmpty()) {
            return arrayList;
        }
        ArrayList arrayList2 = new ArrayList(list.size());
        for (Mirror mirror2 : list) {
            if (mirror2.enabled) {
                arrayList2.add(mirror2);
            }
        }
        if (arrayList2.isEmpty()) {
            arrayList2.add(new Mirror("默认线路", DEFAULT_BASE, 0, 0, true));
        }
        return arrayList2;
    }

    public static Mirror pick(int i) {
        List<Mirror> healthy = healthy();
        int size = (i - 1) % healthy.size();
        if (size < 0) {
            size = 0;
        }
        return healthy.get(size);
    }

    public static void reportFailure(Mirror mirror, String str) {
        if (mirror == null) {
            return;
        }
        int incrementAndGet = mirror.failures.incrementAndGet();
        if (incrementAndGet >= cfgSwitchAfterFail) {
            mirror.cooldownUntilNs = System.nanoTime() + (cfgCooldownMs * 1000000);
            mirror.failures.set(0);
            CNLog.w(TAG, "线路进入冷却 mirror=" + mirror.name + " reason=" + str + " cooldown_ms=" + cfgCooldownMs);
            return;
        }
        CNLog.w(TAG, "线路失败 mirror=" + mirror.name + " reason=" + str + " count=" + incrementAndGet);
    }

    public static void reportSuccess(Mirror mirror) {
        if (mirror == null) {
            return;
        }
        mirror.failures.set(0);
        mirror.cooldownUntilNs = 0L;
    }
}
