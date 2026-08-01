package com.google.android.gms.internal.measurement;

import java.lang.Comparable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class zzaba<K extends Comparable<K>, V> extends AbstractMap<K, V> {
    private boolean zzbme;
    private final int zzbuh;
    private List<zzabf> zzbui;
    private Map<K, V> zzbuj;
    private volatile zzabh zzbuk;
    private Map<K, V> zzbul;

    private zzaba(int i) {
        this.zzbuh = i;
        this.zzbui = Collections.emptyList();
        this.zzbuj = Collections.emptyMap();
        this.zzbul = Collections.emptyMap();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public /* synthetic */ zzaba(int i, zzabb zzabbVar) {
        this(i);
    }

    private final int zza(K k) {
        int size = this.zzbui.size() - 1;
        if (size >= 0) {
            int compareTo = k.compareTo((Comparable) this.zzbui.get(size).getKey());
            if (compareTo > 0) {
                return -(size + 2);
            }
            if (compareTo == 0) {
                return size;
            }
        }
        int i = 0;
        while (i <= size) {
            int i2 = (i + size) / 2;
            int compareTo2 = k.compareTo((Comparable) this.zzbui.get(i2).getKey());
            if (compareTo2 < 0) {
                size = i2 - 1;
            } else {
                if (compareTo2 <= 0) {
                    return i2;
                }
                i = i2 + 1;
            }
        }
        return -(i + 1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <FieldDescriptorType extends zzzq<FieldDescriptorType>> zzaba<FieldDescriptorType, Object> zzag(int i) {
        return new zzabb(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final V zzai(int i) {
        zzuu();
        V v = (V) this.zzbui.remove(i).getValue();
        if (!this.zzbuj.isEmpty()) {
            Iterator<Map.Entry<K, V>> it = zzuv().entrySet().iterator();
            this.zzbui.add(new zzabf(this, it.next()));
            it.remove();
        }
        return v;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzuu() {
        if (this.zzbme) {
            throw new UnsupportedOperationException();
        }
    }

    private final SortedMap<K, V> zzuv() {
        zzuu();
        if (this.zzbuj.isEmpty() && !(this.zzbuj instanceof TreeMap)) {
            TreeMap treeMap = new TreeMap();
            this.zzbuj = treeMap;
            this.zzbul = treeMap.descendingMap();
        }
        return (SortedMap) this.zzbuj;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        zzuu();
        if (!this.zzbui.isEmpty()) {
            this.zzbui.clear();
        }
        if (this.zzbuj.isEmpty()) {
            return;
        }
        this.zzbuj.clear();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r1v0, resolved type: com.google.android.gms.internal.measurement.zzaba<K extends java.lang.Comparable<K>, V> */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        Comparable comparable = (Comparable) obj;
        return zza((zzaba<K, V>) comparable) >= 0 || this.zzbuj.containsKey(comparable);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        if (this.zzbuk == null) {
            this.zzbuk = new zzabh(this, null);
        }
        return this.zzbuk;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzaba)) {
            return super.equals(obj);
        }
        zzaba zzabaVar = (zzaba) obj;
        int size = size();
        if (size != zzabaVar.size()) {
            return false;
        }
        int zzus = zzus();
        if (zzus != zzabaVar.zzus()) {
            return entrySet().equals(zzabaVar.entrySet());
        }
        for (int i = 0; i < zzus; i++) {
            if (!zzah(i).equals(zzabaVar.zzah(i))) {
                return false;
            }
        }
        if (zzus != size) {
            return this.zzbuj.equals(zzabaVar.zzbuj);
        }
        return true;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r1v0, resolved type: com.google.android.gms.internal.measurement.zzaba<K extends java.lang.Comparable<K>, V> */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    public V get(Object obj) {
        Comparable comparable = (Comparable) obj;
        int zza = zza((zzaba<K, V>) comparable);
        return zza >= 0 ? (V) this.zzbui.get(zza).getValue() : this.zzbuj.get(comparable);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int hashCode() {
        int zzus = zzus();
        int i = 0;
        for (int i2 = 0; i2 < zzus; i2++) {
            i += this.zzbui.get(i2).hashCode();
        }
        return this.zzbuj.size() > 0 ? i + this.zzbuj.hashCode() : i;
    }

    public final boolean isImmutable() {
        return this.zzbme;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v0, resolved type: com.google.android.gms.internal.measurement.zzaba<K extends java.lang.Comparable<K>, V> */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    public /* synthetic */ Object put(Object obj, Object obj2) {
        return zza((zzaba<K, V>) obj, (Comparable) obj2);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r1v0, resolved type: com.google.android.gms.internal.measurement.zzaba<K extends java.lang.Comparable<K>, V> */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    public V remove(Object obj) {
        zzuu();
        Comparable comparable = (Comparable) obj;
        int zza = zza((zzaba<K, V>) comparable);
        if (zza >= 0) {
            return (V) zzai(zza);
        }
        if (this.zzbuj.isEmpty()) {
            return null;
        }
        return this.zzbuj.remove(comparable);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.zzbui.size() + this.zzbuj.size();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v3, resolved type: java.util.concurrent.ConcurrentSkipListMap */
    /* JADX WARN: Multi-variable type inference failed */
    public final V zza(K k, V v) {
        zzuu();
        int zza = zza((zzaba<K, V>) k);
        if (zza >= 0) {
            return (V) this.zzbui.get(zza).setValue(v);
        }
        zzuu();
        if (this.zzbui.isEmpty() && !(this.zzbui instanceof ArrayList)) {
            this.zzbui = new ArrayList(this.zzbuh);
        }
        int i = -(zza + 1);
        if (i >= this.zzbuh) {
            return zzuv().put(k, v);
        }
        int size = this.zzbui.size();
        int i2 = this.zzbuh;
        if (size == i2) {
            zzabf remove = this.zzbui.remove(i2 - 1);
            zzuv().put((Comparable) remove.getKey(), remove.getValue());
        }
        this.zzbui.add(i, new zzabf(this, k, v));
        return null;
    }

    public final Map.Entry<K, V> zzah(int i) {
        return this.zzbui.get(i);
    }

    public void zzrp() {
        if (this.zzbme) {
            return;
        }
        this.zzbuj = this.zzbuj.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.zzbuj);
        this.zzbul = this.zzbul.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.zzbul);
        this.zzbme = true;
    }

    public final int zzus() {
        return this.zzbui.size();
    }

    public final Iterable<Map.Entry<K, V>> zzut() {
        return this.zzbuj.isEmpty() ? zzabc.zzuw() : this.zzbuj.entrySet();
    }
}
