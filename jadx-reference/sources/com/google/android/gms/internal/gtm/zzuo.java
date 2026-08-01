package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzun;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzuo<T extends zzun<T>> {
    private static final zzuo zzb = new zzuo(true);
    final zzxk<T, Object> zza = new zzxa(16);
    private boolean zzc;
    private boolean zzd;

    private zzuo() {
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:5:0x0023. Please report as an issue. */
    public static int zza(zzun<?> zzunVar, Object obj) {
        zzye zzd = zzunVar.zzd();
        int zza = zzunVar.zza();
        zzunVar.zzg();
        int zzC = zzto.zzC(zza);
        if (zzd == zzye.GROUP) {
            zzvi.zzi((zzwk) obj);
            zzC += zzC;
        }
        zzyf zzyfVar = zzyf.INT;
        int i = 4;
        switch (zzd) {
            case DOUBLE:
                ((Double) obj).doubleValue();
                i = 8;
                return zzC + i;
            case FLOAT:
                ((Float) obj).floatValue();
                return zzC + i;
            case INT64:
                i = zzto.zzE(((Long) obj).longValue());
                return zzC + i;
            case UINT64:
                i = zzto.zzE(((Long) obj).longValue());
                return zzC + i;
            case INT32:
                i = zzto.zzx(((Integer) obj).intValue());
                return zzC + i;
            case FIXED64:
                ((Long) obj).longValue();
                i = 8;
                return zzC + i;
            case FIXED32:
                ((Integer) obj).intValue();
                return zzC + i;
            case BOOL:
                ((Boolean) obj).booleanValue();
                i = 1;
                return zzC + i;
            case STRING:
                if (obj instanceof zztd) {
                    i = zzto.zzu((zztd) obj);
                } else {
                    i = zzto.zzB((String) obj);
                }
                return zzC + i;
            case GROUP:
                i = zzto.zzw((zzwk) obj);
                return zzC + i;
            case MESSAGE:
                if (obj instanceof zzvp) {
                    i = zzto.zzy((zzvp) obj);
                } else {
                    i = zzto.zzz((zzwk) obj);
                }
                return zzC + i;
            case BYTES:
                if (obj instanceof zztd) {
                    i = zzto.zzu((zztd) obj);
                } else {
                    i = zzto.zzt((byte[]) obj);
                }
                return zzC + i;
            case UINT32:
                i = zzto.zzD(((Integer) obj).intValue());
                return zzC + i;
            case ENUM:
                if (obj instanceof zzvb) {
                    i = zzto.zzx(((zzvb) obj).zza());
                } else {
                    i = zzto.zzx(((Integer) obj).intValue());
                }
                return zzC + i;
            case SFIXED32:
                ((Integer) obj).intValue();
                return zzC + i;
            case SFIXED64:
                ((Long) obj).longValue();
                i = 8;
                return zzC + i;
            case SINT32:
                int intValue = ((Integer) obj).intValue();
                i = zzto.zzD((intValue >> 31) ^ (intValue + intValue));
                return zzC + i;
            case SINT64:
                long longValue = ((Long) obj).longValue();
                i = zzto.zzE((longValue >> 63) ^ (longValue + longValue));
                return zzC + i;
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    public static <T extends zzun<T>> zzuo<T> zzd() {
        return zzb;
    }

    private static Object zzl(Object obj) {
        if (obj instanceof zzwp) {
            return ((zzwp) obj).zzc();
        }
        if (!(obj instanceof byte[])) {
            return obj;
        }
        byte[] bArr = (byte[]) obj;
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        return bArr2;
    }

    private final void zzm(Map.Entry<T, Object> entry) {
        zzwk zzC;
        T key = entry.getKey();
        Object value = entry.getValue();
        if (!(value instanceof zzvp)) {
            key.zzg();
            if (key.zze() == zzyf.MESSAGE) {
                Object zze = zze(key);
                if (zze == null) {
                    this.zza.put(key, zzl(value));
                    return;
                }
                if (zze instanceof zzwp) {
                    zzC = key.zzc((zzwp) zze, (zzwp) value);
                } else {
                    zzwj zzap = ((zzwk) zze).zzap();
                    key.zzb(zzap, (zzwk) value);
                    zzC = zzap.zzC();
                }
                this.zza.put(key, zzC);
                return;
            }
            this.zza.put(key, zzl(value));
            return;
        }
        throw null;
    }

    private static <T extends zzun<T>> boolean zzn(Map.Entry<T, Object> entry) {
        T key = entry.getKey();
        if (key.zze() == zzyf.MESSAGE) {
            key.zzg();
            Object value = entry.getValue();
            if (value instanceof zzwk) {
                if (!((zzwk) value).zzas()) {
                    return false;
                }
            } else {
                if (value instanceof zzvp) {
                    return true;
                }
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
        }
        return true;
    }

    private static final int zzo(Map.Entry<T, Object> entry) {
        int zzD;
        int zzD2;
        T key = entry.getKey();
        Object value = entry.getValue();
        if (key.zze() != zzyf.MESSAGE) {
            return zza(key, value);
        }
        key.zzg();
        key.zzf();
        if (value instanceof zzvp) {
            int zza = entry.getKey().zza();
            int zzD3 = zzto.zzD(8);
            int zza2 = ((zzvp) value).zza();
            zzD = zzD3 + zzD3 + zzto.zzD(16) + zzto.zzD(zza);
            zzD2 = zzto.zzD(24) + zzto.zzD(zza2) + zza2;
        } else {
            int zza3 = entry.getKey().zza();
            int zzD4 = zzto.zzD(8);
            zzD = zzD4 + zzD4 + zzto.zzD(16) + zzto.zzD(zza3);
            zzD2 = zzto.zzD(24) + zzto.zzz((zzwk) value);
        }
        return zzD + zzD2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzuo) {
            return this.zza.equals(((zzuo) obj).zza);
        }
        return false;
    }

    public final int hashCode() {
        return this.zza.hashCode();
    }

    public final int zzb() {
        int i = 0;
        for (int i2 = 0; i2 < this.zza.zzb(); i2++) {
            i += zzo(this.zza.zzg(i2));
        }
        Iterator<Map.Entry<T, Object>> it = this.zza.zzc().iterator();
        while (it.hasNext()) {
            i += zzo(it.next());
        }
        return i;
    }

    /* JADX DEBUG: Method merged with bridge method: clone()Ljava/lang/Object; */
    /* renamed from: zzc, reason: merged with bridge method [inline-methods] */
    public final zzuo<T> clone() {
        zzuo<T> zzuoVar = new zzuo<>();
        for (int i = 0; i < this.zza.zzb(); i++) {
            Map.Entry<T, Object> zzg = this.zza.zzg(i);
            zzuoVar.zzi(zzg.getKey(), zzg.getValue());
        }
        for (Map.Entry<T, Object> entry : this.zza.zzc()) {
            zzuoVar.zzi(entry.getKey(), entry.getValue());
        }
        zzuoVar.zzd = this.zzd;
        return zzuoVar;
    }

    public final Object zze(T t) {
        Object obj = this.zza.get(t);
        if (!(obj instanceof zzvp)) {
            return obj;
        }
        throw null;
    }

    public final Iterator<Map.Entry<T, Object>> zzf() {
        if (this.zzd) {
            return new zzvo(this.zza.entrySet().iterator());
        }
        return this.zza.entrySet().iterator();
    }

    public final void zzg() {
        if (this.zzc) {
            return;
        }
        this.zza.zza();
        this.zzc = true;
    }

    public final void zzh(zzuo<T> zzuoVar) {
        for (int i = 0; i < zzuoVar.zza.zzb(); i++) {
            zzm(zzuoVar.zza.zzg(i));
        }
        Iterator<Map.Entry<T, Object>> it = zzuoVar.zza.zzc().iterator();
        while (it.hasNext()) {
            zzm(it.next());
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x002a, code lost:
    
        if ((r7 instanceof com.google.android.gms.internal.gtm.zzvb) == false) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0033, code lost:
    
        if ((r7 instanceof byte[]) == false) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0047, code lost:
    
        if (r0 == false) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0021, code lost:
    
        if ((r7 instanceof com.google.android.gms.internal.gtm.zzvp) == false) goto L32;
     */
    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0017. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:9:0x004d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void zzi(T t, Object obj) {
        boolean z;
        t.zzg();
        t.zzd();
        zzvi.zze(obj);
        zzye zzyeVar = zzye.DOUBLE;
        zzyf zzyfVar = zzyf.INT;
        switch (r0.zza()) {
            case INT:
                z = obj instanceof Integer;
                break;
            case LONG:
                z = obj instanceof Long;
                break;
            case FLOAT:
                z = obj instanceof Float;
                break;
            case DOUBLE:
                z = obj instanceof Double;
                break;
            case BOOLEAN:
                z = obj instanceof Boolean;
                break;
            case STRING:
                z = obj instanceof String;
                break;
            case BYTE_STRING:
                if (!(obj instanceof zztd)) {
                    break;
                }
                if (obj instanceof zzvp) {
                    this.zzd = true;
                }
                this.zza.put(t, obj);
                return;
            case ENUM:
                if (!(obj instanceof Integer)) {
                    break;
                }
                if (obj instanceof zzvp) {
                }
                this.zza.put(t, obj);
                return;
            case MESSAGE:
                if (!(obj instanceof zzwk)) {
                    break;
                }
                if (obj instanceof zzvp) {
                }
                this.zza.put(t, obj);
                return;
            default:
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(t.zza()), t.zzd().zza(), obj.getClass().getName()));
        }
    }

    public final boolean zzj() {
        return this.zzc;
    }

    public final boolean zzk() {
        for (int i = 0; i < this.zza.zzb(); i++) {
            if (!zzn(this.zza.zzg(i))) {
                return false;
            }
        }
        Iterator<Map.Entry<T, Object>> it = this.zza.zzc().iterator();
        while (it.hasNext()) {
            if (!zzn(it.next())) {
                return false;
            }
        }
        return true;
    }

    private zzuo(boolean z) {
        zzg();
        zzg();
    }
}
