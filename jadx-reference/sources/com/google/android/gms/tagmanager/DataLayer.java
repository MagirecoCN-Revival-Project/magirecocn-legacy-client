package com.google.android.gms.tagmanager;

import cn.thinkingdata.core.router.TRouterMap;
import com.google.android.gms.common.internal.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.checkerframework.dataflow.qual.SideEffectFree;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
public class DataLayer {
    public static final String EVENT_KEY = "event";
    public static final Object OBJECT_NOT_PRESENT = new Object();
    static final String[] zza = "gtm.lifetime".split("\\.");
    private static final Pattern zzb = Pattern.compile("(\\d+)\\s*([smhd]?)");
    private final ConcurrentHashMap<zzav, Integer> zzc;
    private final Map<String, Object> zzd;
    private final ReentrantLock zze;
    private final LinkedList<Map<String, Object>> zzf;
    private final zzax zzg;
    private final CountDownLatch zzh;

    DataLayer() {
        this(new zzas());
    }

    public static List<Object> listOf(Object... objArr) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : objArr) {
            arrayList.add(obj);
        }
        return arrayList;
    }

    public static Map<String, Object> mapOf(Object... objArr) {
        if ((objArr.length & 1) != 0) {
            throw new IllegalArgumentException("expected even number of key-value pairs");
        }
        HashMap hashMap = new HashMap();
        for (int i = 0; i < objArr.length; i += 2) {
            Object obj = objArr[i];
            if (obj instanceof String) {
                hashMap.put((String) obj, objArr[i + 1]);
            } else {
                String valueOf = String.valueOf(obj);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 21);
                sb.append("key is not a string: ");
                sb.append(valueOf);
                throw new IllegalArgumentException(sb.toString());
            }
        }
        return hashMap;
    }

    private final void zzh(Map<String, Object> map, String str, Collection<zzau> collection) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String str2 = str.length() == 0 ? "" : TRouterMap.DOT;
            String key = entry.getKey();
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + str2.length() + String.valueOf(key).length());
            sb.append(str);
            sb.append(str2);
            sb.append(key);
            String sb2 = sb.toString();
            if (entry.getValue() instanceof Map) {
                zzh((Map) entry.getValue(), sb2, collection);
            } else if (!sb2.equals("gtm.lifetime")) {
                collection.add(new zzau(sb2, entry.getValue()));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzi(Map<String, Object> map) {
        Long l;
        long j;
        this.zze.lock();
        try {
            this.zzf.offer(map);
            if (this.zze.getHoldCount() == 1) {
                int i = 0;
                do {
                    Map<String, Object> poll = this.zzf.poll();
                    if (poll != null) {
                        synchronized (this.zzd) {
                            for (String str : poll.keySet()) {
                                zzf(zza(str, poll.get(str)), this.zzd);
                            }
                        }
                        Iterator<zzav> it = this.zzc.keySet().iterator();
                        while (it.hasNext()) {
                            it.next().zza(poll);
                        }
                        i++;
                    }
                } while (i <= 500);
                this.zzf.clear();
                throw new RuntimeException("Seems like an infinite loop of pushing to the data layer");
            }
            String[] strArr = zza;
            int length = strArr.length;
            Object obj = map;
            int i2 = 0;
            while (true) {
                l = null;
                if (i2 >= length) {
                    break;
                }
                String str2 = strArr[i2];
                if (!(obj instanceof Map)) {
                    obj = null;
                    break;
                } else {
                    obj = ((Map) obj).get(str2);
                    i2++;
                }
            }
            if (obj != null) {
                String obj2 = obj.toString();
                Matcher matcher = zzb.matcher(obj2);
                if (matcher.matches()) {
                    try {
                        String group = matcher.group(1);
                        Preconditions.checkNotNull(group);
                        j = Long.parseLong(group);
                    } catch (NumberFormatException unused) {
                        String valueOf = String.valueOf(obj2);
                        zzdh.zzc(valueOf.length() != 0 ? "illegal number in _lifetime value: ".concat(valueOf) : new String("illegal number in _lifetime value: "));
                        j = 0;
                    }
                    if (j <= 0) {
                        String valueOf2 = String.valueOf(obj2);
                        zzdh.zzb.zzb(valueOf2.length() != 0 ? "non-positive _lifetime: ".concat(valueOf2) : new String("non-positive _lifetime: "));
                    } else {
                        String group2 = matcher.group(2);
                        Preconditions.checkNotNull(group2);
                        if (group2.length() == 0) {
                            l = Long.valueOf(j);
                        } else {
                            char charAt = group2.charAt(0);
                            if (charAt == 'd') {
                                l = Long.valueOf(j * 86400000);
                            } else if (charAt == 'h') {
                                l = Long.valueOf(j * 3600000);
                            } else if (charAt == 'm') {
                                l = Long.valueOf(j * 60000);
                            } else if (charAt == 's') {
                                l = Long.valueOf(j * 1000);
                            } else {
                                String valueOf3 = String.valueOf(obj2);
                                zzdh.zzc(valueOf3.length() != 0 ? "unknown units in _lifetime: ".concat(valueOf3) : new String("unknown units in _lifetime: "));
                            }
                        }
                    }
                } else {
                    String valueOf4 = String.valueOf(obj2);
                    zzdh.zzb.zzb(valueOf4.length() != 0 ? "unknown _lifetime: ".concat(valueOf4) : new String("unknown _lifetime: "));
                }
            }
            if (l != null) {
                ArrayList arrayList = new ArrayList();
                zzh(map, "", arrayList);
                this.zzg.zzc(arrayList, l.longValue());
            }
        } finally {
            this.zze.unlock();
        }
    }

    public Object get(String str) {
        synchronized (this.zzd) {
            Object obj = this.zzd;
            for (String str2 : str.split("\\.")) {
                if (!(obj instanceof Map)) {
                    return null;
                }
                obj = ((Map) obj).get(str2);
                if (obj == null) {
                    return null;
                }
            }
            return obj;
        }
    }

    public void push(String str, Object obj) {
        push(zza(str, obj));
    }

    public void pushEvent(String str, Map<String, Object> map) {
        HashMap hashMap = new HashMap(map);
        hashMap.put("event", str);
        push(hashMap);
    }

    public String toString() {
        String sb;
        synchronized (this.zzd) {
            StringBuilder sb2 = new StringBuilder();
            for (Map.Entry<String, Object> entry : this.zzd.entrySet()) {
                sb2.append(String.format("{\n\tKey: %s\n\tValue: %s\n}\n", entry.getKey(), entry.getValue()));
            }
            sb = sb2.toString();
        }
        return sb;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final Map<String, Object> zza(String str, Object obj) {
        HashMap hashMap = new HashMap();
        String[] split = str.toString().split("\\.");
        int i = 0;
        HashMap hashMap2 = hashMap;
        while (true) {
            int length = split.length - 1;
            if (i < length) {
                HashMap hashMap3 = new HashMap();
                hashMap2.put(split[i], hashMap3);
                i++;
                hashMap2 = hashMap3;
            } else {
                hashMap2.put(split[length], obj);
                return hashMap;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzd(String str) {
        push(str, null);
        this.zzg.zza(str);
    }

    final void zze(List<Object> list, List<Object> list2) {
        while (list2.size() < list.size()) {
            list2.add(null);
        }
        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            if (obj instanceof List) {
                if (!(list2.get(i) instanceof List)) {
                    list2.set(i, new ArrayList());
                }
                Object obj2 = list2.get(i);
                Preconditions.checkNotNull(obj2);
                zze((List) obj, (List) obj2);
            } else if (obj instanceof Map) {
                if (!(list2.get(i) instanceof Map)) {
                    list2.set(i, new HashMap());
                }
                Object obj3 = list2.get(i);
                Preconditions.checkNotNull(obj3);
                zzf((Map) obj, (Map) obj3);
            } else if (obj != OBJECT_NOT_PRESENT) {
                list2.set(i, obj);
            }
        }
    }

    final void zzf(Map<String, Object> map, Map<String, Object> map2) {
        for (String str : map.keySet()) {
            Object obj = map.get(str);
            if (obj instanceof List) {
                if (!(map2.get(str) instanceof List)) {
                    map2.put(str, new ArrayList());
                }
                Object obj2 = map2.get(str);
                Preconditions.checkNotNull(obj2);
                zze((List) obj, (List) obj2);
            } else if (obj instanceof Map) {
                if (!(map2.get(str) instanceof Map)) {
                    map2.put(str, new HashMap());
                }
                Object obj3 = map2.get(str);
                Preconditions.checkNotNull(obj3);
                zzf((Map) obj, (Map) obj3);
            } else {
                map2.put(str, obj);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzg(zzav zzavVar) {
        this.zzc.put(zzavVar, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public DataLayer(zzax zzaxVar) {
        this.zzg = zzaxVar;
        this.zzc = new ConcurrentHashMap<>();
        this.zzd = new HashMap();
        this.zze = new ReentrantLock();
        this.zzf = new LinkedList<>();
        this.zzh = new CountDownLatch(1);
        zzaxVar.zzb(new zzat(this));
    }

    @SideEffectFree
    public void push(Map<String, Object> map) {
        try {
            this.zzh.await();
        } catch (InterruptedException unused) {
            zzdh.zzc("DataLayer.push: unexpected InterruptedException");
        }
        zzi(map);
    }
}
