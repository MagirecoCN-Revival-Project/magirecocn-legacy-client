package com.google.android.gms.internal.measurement;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class zzwp {
    private static HashMap<String, String> zzbmx;
    private static Object zzbnc;
    private static boolean zzbnd;
    private static final Uri CONTENT_URI = Uri.parse("content://com.google.android.gsf.gservices");
    private static final Uri zzbmt = Uri.parse("content://com.google.android.gsf.gservices/prefix");
    public static final Pattern zzbmu = Pattern.compile("^(1|true|t|on|yes|y)$", 2);
    public static final Pattern zzbmv = Pattern.compile("^(0|false|f|off|no|n)$", 2);
    private static final AtomicBoolean zzbmw = new AtomicBoolean();
    private static final HashMap<String, Boolean> zzbmy = new HashMap<>();
    private static final HashMap<String, Integer> zzbmz = new HashMap<>();
    private static final HashMap<String, Long> zzbna = new HashMap<>();
    private static final HashMap<String, Float> zzbnb = new HashMap<>();
    private static String[] zzbne = new String[0];

    private static <T> T zza(HashMap<String, T> hashMap, String str, T t) {
        synchronized (zzwp.class) {
            if (!hashMap.containsKey(str)) {
                return null;
            }
            T t2 = hashMap.get(str);
            if (t2 != null) {
                t = t2;
            }
            return t;
        }
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[IF]}, finally: {[IF, INVOKE] complete} */
    public static String zza(ContentResolver contentResolver, String str, String str2) {
        synchronized (zzwp.class) {
            zza(contentResolver);
            Object obj = zzbnc;
            if (zzbmx.containsKey(str)) {
                String str3 = zzbmx.get(str);
                return str3 != null ? str3 : null;
            }
            for (String str4 : zzbne) {
                if (str.startsWith(str4)) {
                    if (!zzbnd || zzbmx.isEmpty()) {
                        zzbmx.putAll(zza(contentResolver, zzbne));
                        zzbnd = true;
                        if (zzbmx.containsKey(str)) {
                            String str5 = zzbmx.get(str);
                            return str5 != null ? str5 : null;
                        }
                    }
                    return null;
                }
            }
            Cursor query = contentResolver.query(CONTENT_URI, null, null, new String[]{str}, null);
            if (query == null) {
                return null;
            }
            try {
                if (!query.moveToFirst()) {
                    zza(obj, str, (String) null);
                    if (query != null) {
                        query.close();
                    }
                    return null;
                }
                String string = query.getString(1);
                if (string != null && string.equals(null)) {
                    string = null;
                }
                zza(obj, str, string);
                String str6 = string != null ? string : null;
                if (query != null) {
                    query.close();
                }
                return str6;
            } finally {
                if (query != null) {
                    query.close();
                }
            }
        }
    }

    private static Map<String, String> zza(ContentResolver contentResolver, String... strArr) {
        Cursor query = contentResolver.query(zzbmt, null, null, strArr, null);
        TreeMap treeMap = new TreeMap();
        if (query == null) {
            return treeMap;
        }
        while (query.moveToNext()) {
            try {
                treeMap.put(query.getString(0), query.getString(1));
            } finally {
                query.close();
            }
        }
        return treeMap;
    }

    private static void zza(ContentResolver contentResolver) {
        if (zzbmx == null) {
            zzbmw.set(false);
            zzbmx = new HashMap<>();
            zzbnc = new Object();
            zzbnd = false;
            contentResolver.registerContentObserver(CONTENT_URI, true, new zzwq(null));
            return;
        }
        if (zzbmw.getAndSet(false)) {
            zzbmx.clear();
            zzbmy.clear();
            zzbmz.clear();
            zzbna.clear();
            zzbnb.clear();
            zzbnc = new Object();
            zzbnd = false;
        }
    }

    private static void zza(Object obj, String str, String str2) {
        synchronized (zzwp.class) {
            if (obj == zzbnc) {
                zzbmx.put(str, str2);
            }
        }
    }

    private static <T> void zza(Object obj, HashMap<String, T> hashMap, String str, T t) {
        synchronized (zzwp.class) {
            if (obj == zzbnc) {
                hashMap.put(str, t);
                zzbmx.remove(str);
            }
        }
    }

    public static boolean zza(ContentResolver contentResolver, String str, boolean z) {
        Object zzb = zzb(contentResolver);
        HashMap<String, Boolean> hashMap = zzbmy;
        Boolean bool = (Boolean) zza(hashMap, str, Boolean.valueOf(z));
        if (bool != null) {
            return bool.booleanValue();
        }
        String zza = zza(contentResolver, str, (String) null);
        if (zza != null && !zza.equals("")) {
            if (zzbmu.matcher(zza).matches()) {
                bool = true;
                z = true;
            } else if (zzbmv.matcher(zza).matches()) {
                bool = false;
                z = false;
            } else {
                Log.w("Gservices", "attempt to read gservices key " + str + " (value \"" + zza + "\") as boolean");
            }
        }
        zza(zzb, hashMap, str, bool);
        return z;
    }

    private static Object zzb(ContentResolver contentResolver) {
        Object obj;
        synchronized (zzwp.class) {
            zza(contentResolver);
            obj = zzbnc;
        }
        return obj;
    }
}
