package com.google.android.gms.internal.gtm;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import sun.misc.Unsafe;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzwn<T> implements zzwx<T> {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zzxy.zzg();
    private final int[] zzc;
    private final Object[] zzd;
    private final int zze;
    private final int zzf;
    private final zzwk zzg;
    private final boolean zzh;
    private final boolean zzi;
    private final boolean zzj;
    private final int[] zzk;
    private final int zzl;
    private final int zzm;
    private final zzvy zzn;
    private final zzxo<?, ?> zzo;
    private final zzuk<?> zzp;
    private final zzwq zzq;
    private final zzwf zzr;

    /* JADX DEBUG: Multi-variable search result rejected for r10v0, resolved type: int */
    /* JADX DEBUG: Multi-variable search result rejected for r11v0, resolved type: com.google.android.gms.internal.gtm.zzwk */
    /* JADX DEBUG: Multi-variable search result rejected for r13v0, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r14v0, resolved type: int[] */
    /* JADX DEBUG: Multi-variable search result rejected for r16v0, resolved type: int */
    /* JADX DEBUG: Multi-variable search result rejected for r17v0, resolved type: com.google.android.gms.internal.gtm.zzwq */
    /* JADX DEBUG: Multi-variable search result rejected for r18v0, resolved type: com.google.android.gms.internal.gtm.zzvy */
    /* JADX DEBUG: Multi-variable search result rejected for r19v0, resolved type: com.google.android.gms.internal.gtm.zzxo<?, ?> */
    /* JADX DEBUG: Multi-variable search result rejected for r20v0, resolved type: com.google.android.gms.internal.gtm.zzuk<?> */
    /* JADX DEBUG: Multi-variable search result rejected for r7v0, resolved type: int[] */
    /* JADX DEBUG: Multi-variable search result rejected for r8v0, resolved type: java.lang.Object[] */
    /* JADX WARN: Multi-variable type inference failed */
    private zzwn(int[] iArr, int[] iArr2, Object[] objArr, int i, int i2, zzwk zzwkVar, boolean z, boolean z2, int[] iArr3, int i3, int i4, zzwq zzwqVar, zzvy zzvyVar, zzxo<?, ?> zzxoVar, zzuk<?> zzukVar, zzwf zzwfVar) {
        this.zzc = iArr;
        this.zzd = iArr2;
        this.zze = objArr;
        this.zzf = i;
        this.zzi = i2 instanceof zzuz;
        this.zzj = zzwkVar;
        boolean z3 = false;
        if (zzxoVar != 0 && zzxoVar.zzi(i2)) {
            z3 = true;
        }
        this.zzh = z3;
        this.zzk = z2;
        this.zzl = iArr3;
        this.zzm = i3;
        this.zzq = i4;
        this.zzn = zzwqVar;
        this.zzo = zzvyVar;
        this.zzp = zzxoVar;
        this.zzg = i2;
        this.zzr = zzukVar;
    }

    private final int zzA(int i, int i2) {
        int length = (this.zzc.length / 3) - 1;
        while (i2 <= length) {
            int i3 = (length + i2) >>> 1;
            int i4 = i3 * 3;
            int i5 = this.zzc[i4];
            if (i == i5) {
                return i4;
            }
            if (i < i5) {
                length = i3 - 1;
            } else {
                i2 = i3 + 1;
            }
        }
        return -1;
    }

    private static int zzB(int i) {
        return (i >>> 20) & 255;
    }

    private final int zzC(int i) {
        return this.zzc[i + 1];
    }

    private static <T> long zzD(T t, long j) {
        return ((Long) zzxy.zzf(t, j)).longValue();
    }

    private final zzvd zzE(int i) {
        int i2 = i / 3;
        return (zzvd) this.zzd[i2 + i2 + 1];
    }

    private final zzwx zzF(int i) {
        int i2 = i / 3;
        int i3 = i2 + i2;
        zzwx zzwxVar = (zzwx) this.zzd[i3];
        if (zzwxVar != null) {
            return zzwxVar;
        }
        zzwx<T> zzb2 = zzwt.zza().zzb((Class) this.zzd[i3 + 1]);
        this.zzd[i3] = zzb2;
        return zzb2;
    }

    private final <UT, UB> UB zzG(Object obj, int i, UB ub, zzxo<UT, UB> zzxoVar) {
        int i2 = this.zzc[i];
        Object zzf = zzxy.zzf(obj, zzC(i) & 1048575);
        if (zzf == null || zzE(i) == null) {
            return ub;
        }
        throw null;
    }

    private final Object zzH(int i) {
        int i2 = i / 3;
        return this.zzd[i2 + i2];
    }

    private static Field zzI(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            String name = cls.getName();
            String arrays = Arrays.toString(declaredFields);
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 40 + String.valueOf(name).length() + String.valueOf(arrays).length());
            sb.append("Field ");
            sb.append(str);
            sb.append(" for ");
            sb.append(name);
            sb.append(" not found. Known fields are ");
            sb.append(arrays);
            throw new RuntimeException(sb.toString());
        }
    }

    private final void zzJ(T t, T t2, int i) {
        long zzC = zzC(i) & 1048575;
        if (zzQ(t2, i)) {
            Object zzf = zzxy.zzf(t, zzC);
            Object zzf2 = zzxy.zzf(t2, zzC);
            if (zzf != null && zzf2 != null) {
                zzxy.zzs(t, zzC, zzvi.zzg(zzf, zzf2));
                zzM(t, i);
            } else if (zzf2 != null) {
                zzxy.zzs(t, zzC, zzf2);
                zzM(t, i);
            }
        }
    }

    private final void zzK(T t, T t2, int i) {
        int zzC = zzC(i);
        int i2 = this.zzc[i];
        long j = zzC & 1048575;
        if (zzT(t2, i2, i)) {
            Object zzf = zzT(t, i2, i) ? zzxy.zzf(t, j) : null;
            Object zzf2 = zzxy.zzf(t2, j);
            if (zzf != null && zzf2 != null) {
                zzxy.zzs(t, j, zzvi.zzg(zzf, zzf2));
                zzN(t, i2, i);
            } else if (zzf2 != null) {
                zzxy.zzs(t, j, zzf2);
                zzN(t, i2, i);
            }
        }
    }

    private final void zzL(Object obj, int i, zzww zzwwVar) throws IOException {
        if (zzP(i)) {
            zzxy.zzs(obj, i & 1048575, zzwwVar.zzx());
        } else if (!this.zzi) {
            zzxy.zzs(obj, i & 1048575, zzwwVar.zzq());
        } else {
            zzxy.zzs(obj, i & 1048575, zzwwVar.zzv());
        }
    }

    private final void zzM(T t, int i) {
        int zzz = zzz(i);
        long j = 1048575 & zzz;
        if (j == 1048575) {
            return;
        }
        zzxy.zzq(t, j, (1 << (zzz >>> 20)) | zzxy.zzc(t, j));
    }

    private final void zzN(T t, int i, int i2) {
        zzxy.zzq(t, zzz(i2) & 1048575, i);
    }

    private final boolean zzO(T t, T t2, int i) {
        return zzQ(t, i) == zzQ(t2, i);
    }

    private static boolean zzP(int i) {
        return (i & 536870912) != 0;
    }

    private final boolean zzQ(T t, int i) {
        int zzz = zzz(i);
        long j = zzz & 1048575;
        if (j != 1048575) {
            return (zzxy.zzc(t, j) & (1 << (zzz >>> 20))) != 0;
        }
        int zzC = zzC(i);
        long j2 = zzC & 1048575;
        switch (zzB(zzC)) {
            case 0:
                return zzxy.zza(t, j2) != 0.0d;
            case 1:
                return zzxy.zzb(t, j2) != 0.0f;
            case 2:
                return zzxy.zzd(t, j2) != 0;
            case 3:
                return zzxy.zzd(t, j2) != 0;
            case 4:
                return zzxy.zzc(t, j2) != 0;
            case 5:
                return zzxy.zzd(t, j2) != 0;
            case 6:
                return zzxy.zzc(t, j2) != 0;
            case 7:
                return zzxy.zzw(t, j2);
            case 8:
                Object zzf = zzxy.zzf(t, j2);
                if (zzf instanceof String) {
                    return !((String) zzf).isEmpty();
                }
                if (zzf instanceof zztd) {
                    return !zztd.zzb.equals(zzf);
                }
                throw new IllegalArgumentException();
            case 9:
                return zzxy.zzf(t, j2) != null;
            case 10:
                return !zztd.zzb.equals(zzxy.zzf(t, j2));
            case 11:
                return zzxy.zzc(t, j2) != 0;
            case 12:
                return zzxy.zzc(t, j2) != 0;
            case 13:
                return zzxy.zzc(t, j2) != 0;
            case 14:
                return zzxy.zzd(t, j2) != 0;
            case 15:
                return zzxy.zzc(t, j2) != 0;
            case 16:
                return zzxy.zzd(t, j2) != 0;
            case 17:
                return zzxy.zzf(t, j2) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    private final boolean zzR(T t, int i, int i2, int i3, int i4) {
        if (i2 == 1048575) {
            return zzQ(t, i);
        }
        return (i3 & i4) != 0;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: com.google.android.gms.internal.gtm.zzwx */
    /* JADX WARN: Multi-variable type inference failed */
    private static boolean zzS(Object obj, int i, zzwx zzwxVar) {
        return zzwxVar.zzk(zzxy.zzf(obj, i & 1048575));
    }

    private final boolean zzT(T t, int i, int i2) {
        return zzxy.zzc(t, (long) (zzz(i2) & 1048575)) == i;
    }

    private static <T> boolean zzU(T t, long j) {
        return ((Boolean) zzxy.zzf(t, j)).booleanValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:222:0x0492  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0034  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void zzV(T t, zztp zztpVar) throws IOException {
        Iterator<Map.Entry<?, Object>> it;
        Map.Entry<?, ?> entry;
        int length;
        int i;
        int i2;
        if (this.zzh) {
            zzuo<?> zzb2 = this.zzp.zzb(t);
            if (!zzb2.zza.isEmpty()) {
                it = zzb2.zzf();
                entry = (Map.Entry) it.next();
                length = this.zzc.length;
                Unsafe unsafe = zzb;
                int i3 = 1048575;
                i = 0;
                int i4 = 1048575;
                int i5 = 0;
                while (i < length) {
                    int zzC = zzC(i);
                    int i6 = this.zzc[i];
                    int zzB = zzB(zzC);
                    if (zzB <= 17) {
                        int i7 = this.zzc[i + 2];
                        int i8 = i7 & i3;
                        if (i8 != i4) {
                            i5 = unsafe.getInt(t, i8);
                            i4 = i8;
                        }
                        i2 = 1 << (i7 >>> 20);
                    } else {
                        i2 = 0;
                    }
                    while (entry != null && this.zzp.zza(entry) <= i6) {
                        this.zzp.zzj(zztpVar, entry);
                        entry = it.hasNext() ? (Map.Entry) it.next() : null;
                    }
                    long j = zzC & i3;
                    switch (zzB) {
                        case 0:
                            if ((i2 & i5) == 0) {
                                break;
                            } else {
                                zztpVar.zzf(i6, zzxy.zza(t, j));
                                continue;
                            }
                        case 1:
                            if ((i2 & i5) != 0) {
                                zztpVar.zzo(i6, zzxy.zzb(t, j));
                                break;
                            } else {
                                continue;
                            }
                        case 2:
                            if ((i2 & i5) != 0) {
                                zztpVar.zzt(i6, unsafe.getLong(t, j));
                                break;
                            } else {
                                continue;
                            }
                        case 3:
                            if ((i2 & i5) != 0) {
                                zztpVar.zzK(i6, unsafe.getLong(t, j));
                                break;
                            } else {
                                continue;
                            }
                        case 4:
                            if ((i2 & i5) != 0) {
                                zztpVar.zzr(i6, unsafe.getInt(t, j));
                                break;
                            } else {
                                continue;
                            }
                        case 5:
                            if ((i2 & i5) != 0) {
                                zztpVar.zzm(i6, unsafe.getLong(t, j));
                                break;
                            } else {
                                continue;
                            }
                        case 6:
                            if ((i2 & i5) != 0) {
                                zztpVar.zzk(i6, unsafe.getInt(t, j));
                                break;
                            } else {
                                continue;
                            }
                        case 7:
                            if ((i2 & i5) != 0) {
                                zztpVar.zzb(i6, zzxy.zzw(t, j));
                                break;
                            } else {
                                continue;
                            }
                        case 8:
                            if ((i2 & i5) != 0) {
                                zzX(i6, unsafe.getObject(t, j), zztpVar);
                                break;
                            } else {
                                continue;
                            }
                        case 9:
                            if ((i2 & i5) != 0) {
                                zztpVar.zzv(i6, unsafe.getObject(t, j), zzF(i));
                                break;
                            } else {
                                continue;
                            }
                        case 10:
                            if ((i2 & i5) != 0) {
                                zztpVar.zzd(i6, (zztd) unsafe.getObject(t, j));
                                break;
                            } else {
                                continue;
                            }
                        case 11:
                            if ((i2 & i5) != 0) {
                                zztpVar.zzI(i6, unsafe.getInt(t, j));
                                break;
                            } else {
                                continue;
                            }
                        case 12:
                            if ((i2 & i5) != 0) {
                                zztpVar.zzi(i6, unsafe.getInt(t, j));
                                break;
                            } else {
                                continue;
                            }
                        case 13:
                            if ((i2 & i5) != 0) {
                                zztpVar.zzx(i6, unsafe.getInt(t, j));
                                break;
                            } else {
                                continue;
                            }
                        case 14:
                            if ((i2 & i5) != 0) {
                                zztpVar.zzz(i6, unsafe.getLong(t, j));
                                break;
                            } else {
                                continue;
                            }
                        case 15:
                            if ((i2 & i5) != 0) {
                                zztpVar.zzB(i6, unsafe.getInt(t, j));
                                break;
                            } else {
                                continue;
                            }
                        case 16:
                            if ((i2 & i5) != 0) {
                                zztpVar.zzD(i6, unsafe.getLong(t, j));
                                break;
                            } else {
                                continue;
                            }
                        case 17:
                            if ((i2 & i5) != 0) {
                                zztpVar.zzq(i6, unsafe.getObject(t, j), zzF(i));
                                break;
                            } else {
                                continue;
                            }
                        case 18:
                            zzwz.zzL(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, false);
                            continue;
                        case 19:
                            zzwz.zzP(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, false);
                            continue;
                        case 20:
                            zzwz.zzS(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, false);
                            continue;
                        case 21:
                            zzwz.zzaa(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, false);
                            continue;
                        case 22:
                            zzwz.zzR(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, false);
                            continue;
                        case 23:
                            zzwz.zzO(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, false);
                            continue;
                        case 24:
                            zzwz.zzN(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, false);
                            continue;
                        case 25:
                            zzwz.zzJ(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, false);
                            continue;
                        case 26:
                            zzwz.zzY(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar);
                            break;
                        case 27:
                            zzwz.zzT(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, zzF(i));
                            break;
                        case 28:
                            zzwz.zzK(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar);
                            break;
                        case 29:
                            zzwz.zzZ(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, false);
                            break;
                        case 30:
                            zzwz.zzM(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, false);
                            break;
                        case 31:
                            zzwz.zzU(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, false);
                            break;
                        case 32:
                            zzwz.zzV(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, false);
                            break;
                        case 33:
                            zzwz.zzW(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, false);
                            break;
                        case 34:
                            zzwz.zzX(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, false);
                            break;
                        case 35:
                            zzwz.zzL(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, true);
                            break;
                        case 36:
                            zzwz.zzP(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, true);
                            break;
                        case 37:
                            zzwz.zzS(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, true);
                            break;
                        case 38:
                            zzwz.zzaa(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, true);
                            break;
                        case 39:
                            zzwz.zzR(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, true);
                            break;
                        case 40:
                            zzwz.zzO(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, true);
                            break;
                        case 41:
                            zzwz.zzN(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, true);
                            break;
                        case 42:
                            zzwz.zzJ(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, true);
                            break;
                        case 43:
                            zzwz.zzZ(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, true);
                            break;
                        case 44:
                            zzwz.zzM(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, true);
                            break;
                        case 45:
                            zzwz.zzU(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, true);
                            break;
                        case 46:
                            zzwz.zzV(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, true);
                            break;
                        case 47:
                            zzwz.zzW(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, true);
                            break;
                        case 48:
                            zzwz.zzX(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, true);
                            break;
                        case 49:
                            zzwz.zzQ(this.zzc[i], (List) unsafe.getObject(t, j), zztpVar, zzF(i));
                            break;
                        case 50:
                            zzW(zztpVar, i6, unsafe.getObject(t, j), i);
                            break;
                        case 51:
                            if (zzT(t, i6, i)) {
                                zztpVar.zzf(i6, zzo(t, j));
                                break;
                            }
                            break;
                        case 52:
                            if (zzT(t, i6, i)) {
                                zztpVar.zzo(i6, zzp(t, j));
                                break;
                            }
                            break;
                        case 53:
                            if (zzT(t, i6, i)) {
                                zztpVar.zzt(i6, zzD(t, j));
                                break;
                            }
                            break;
                        case 54:
                            if (zzT(t, i6, i)) {
                                zztpVar.zzK(i6, zzD(t, j));
                                break;
                            }
                            break;
                        case 55:
                            if (zzT(t, i6, i)) {
                                zztpVar.zzr(i6, zzs(t, j));
                                break;
                            }
                            break;
                        case 56:
                            if (zzT(t, i6, i)) {
                                zztpVar.zzm(i6, zzD(t, j));
                                break;
                            }
                            break;
                        case 57:
                            if (zzT(t, i6, i)) {
                                zztpVar.zzk(i6, zzs(t, j));
                                break;
                            }
                            break;
                        case 58:
                            if (zzT(t, i6, i)) {
                                zztpVar.zzb(i6, zzU(t, j));
                                break;
                            }
                            break;
                        case 59:
                            if (zzT(t, i6, i)) {
                                zzX(i6, unsafe.getObject(t, j), zztpVar);
                                break;
                            }
                            break;
                        case 60:
                            if (zzT(t, i6, i)) {
                                zztpVar.zzv(i6, unsafe.getObject(t, j), zzF(i));
                                break;
                            }
                            break;
                        case 61:
                            if (zzT(t, i6, i)) {
                                zztpVar.zzd(i6, (zztd) unsafe.getObject(t, j));
                                break;
                            }
                            break;
                        case 62:
                            if (zzT(t, i6, i)) {
                                zztpVar.zzI(i6, zzs(t, j));
                                break;
                            }
                            break;
                        case 63:
                            if (zzT(t, i6, i)) {
                                zztpVar.zzi(i6, zzs(t, j));
                                break;
                            }
                            break;
                        case 64:
                            if (zzT(t, i6, i)) {
                                zztpVar.zzx(i6, zzs(t, j));
                                break;
                            }
                            break;
                        case 65:
                            if (zzT(t, i6, i)) {
                                zztpVar.zzz(i6, zzD(t, j));
                                break;
                            }
                            break;
                        case 66:
                            if (zzT(t, i6, i)) {
                                zztpVar.zzB(i6, zzs(t, j));
                                break;
                            }
                            break;
                        case 67:
                            if (zzT(t, i6, i)) {
                                zztpVar.zzD(i6, zzD(t, j));
                                break;
                            }
                            break;
                        case 68:
                            if (zzT(t, i6, i)) {
                                zztpVar.zzq(i6, unsafe.getObject(t, j), zzF(i));
                                break;
                            }
                            break;
                    }
                    i += 3;
                    i3 = 1048575;
                }
                while (entry != null) {
                    this.zzp.zzj(zztpVar, entry);
                    entry = it.hasNext() ? (Map.Entry) it.next() : null;
                }
                zzxo<?, ?> zzxoVar = this.zzo;
                zzxoVar.zzs(zzxoVar.zzd(t), zztpVar);
            }
        }
        it = null;
        entry = null;
        length = this.zzc.length;
        Unsafe unsafe2 = zzb;
        int i32 = 1048575;
        i = 0;
        int i42 = 1048575;
        int i52 = 0;
        while (i < length) {
        }
        while (entry != null) {
        }
        zzxo<?, ?> zzxoVar2 = this.zzo;
        zzxoVar2.zzs(zzxoVar2.zzd(t), zztpVar);
    }

    private final <K, V> void zzW(zztp zztpVar, int i, Object obj, int i2) throws IOException {
        if (obj == null) {
            return;
        }
        throw null;
    }

    private static final void zzX(int i, Object obj, zztp zztpVar) throws IOException {
        if (obj instanceof String) {
            zztpVar.zzG(i, (String) obj);
        } else {
            zztpVar.zzd(i, (zztd) obj);
        }
    }

    static zzxp zzd(Object obj) {
        zzuz zzuzVar = (zzuz) obj;
        zzxp zzxpVar = zzuzVar.zzc;
        if (zzxpVar != zzxp.zzc()) {
            return zzxpVar;
        }
        zzxp zze = zzxp.zze();
        zzuzVar.zzc = zze;
        return zze;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> zzwn<T> zzl(Class<T> cls, zzwh zzwhVar, zzwq zzwqVar, zzvy zzvyVar, zzxo<?, ?> zzxoVar, zzuk<?> zzukVar, zzwf zzwfVar) {
        if (zzwhVar instanceof zzwv) {
            return zzm((zzwv) zzwhVar, zzwqVar, zzvyVar, zzxoVar, zzukVar, zzwfVar);
        }
        throw null;
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x032b  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0385  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x025d  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0278  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x027b  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0260  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static <T> zzwn<T> zzm(zzwv zzwvVar, zzwq zzwqVar, zzvy zzvyVar, zzxo<?, ?> zzxoVar, zzuk<?> zzukVar, zzwf zzwfVar) {
        int i;
        int charAt;
        int charAt2;
        int charAt3;
        int[] iArr;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        char charAt4;
        int i7;
        char charAt5;
        int i8;
        char charAt6;
        int i9;
        char charAt7;
        int i10;
        char charAt8;
        int i11;
        char charAt9;
        int i12;
        char charAt10;
        int i13;
        char charAt11;
        int i14;
        int i15;
        int i16;
        int[] iArr2;
        int i17;
        int i18;
        int i19;
        int objectFieldOffset;
        Object[] objArr;
        String str;
        Class<?> cls;
        int i20;
        int i21;
        int i22;
        Field zzI;
        char charAt12;
        int i23;
        int i24;
        int i25;
        Object obj;
        Field zzI2;
        Object obj2;
        Field zzI3;
        int i26;
        char charAt13;
        int i27;
        char charAt14;
        int i28;
        char charAt15;
        int i29;
        char charAt16;
        boolean z = zzwvVar.zzc() == 2;
        String zzd = zzwvVar.zzd();
        int length = zzd.length();
        char c = 55296;
        if (zzd.charAt(0) >= 55296) {
            int i30 = 1;
            while (true) {
                i = i30 + 1;
                if (zzd.charAt(i30) < 55296) {
                    break;
                }
                i30 = i;
            }
        } else {
            i = 1;
        }
        int i31 = i + 1;
        int charAt17 = zzd.charAt(i);
        if (charAt17 >= 55296) {
            int i32 = charAt17 & 8191;
            int i33 = 13;
            while (true) {
                i29 = i31 + 1;
                charAt16 = zzd.charAt(i31);
                if (charAt16 < 55296) {
                    break;
                }
                i32 |= (charAt16 & 8191) << i33;
                i33 += 13;
                i31 = i29;
            }
            charAt17 = i32 | (charAt16 << i33);
            i31 = i29;
        }
        if (charAt17 == 0) {
            iArr = zza;
            i3 = 0;
            charAt = 0;
            i5 = 0;
            charAt2 = 0;
            i4 = 0;
            charAt3 = 0;
            i2 = 0;
        } else {
            int i34 = i31 + 1;
            int charAt18 = zzd.charAt(i31);
            if (charAt18 >= 55296) {
                int i35 = charAt18 & 8191;
                int i36 = 13;
                while (true) {
                    i13 = i34 + 1;
                    charAt11 = zzd.charAt(i34);
                    if (charAt11 < 55296) {
                        break;
                    }
                    i35 |= (charAt11 & 8191) << i36;
                    i36 += 13;
                    i34 = i13;
                }
                charAt18 = i35 | (charAt11 << i36);
                i34 = i13;
            }
            int i37 = i34 + 1;
            int charAt19 = zzd.charAt(i34);
            if (charAt19 >= 55296) {
                int i38 = charAt19 & 8191;
                int i39 = 13;
                while (true) {
                    i12 = i37 + 1;
                    charAt10 = zzd.charAt(i37);
                    if (charAt10 < 55296) {
                        break;
                    }
                    i38 |= (charAt10 & 8191) << i39;
                    i39 += 13;
                    i37 = i12;
                }
                charAt19 = i38 | (charAt10 << i39);
                i37 = i12;
            }
            int i40 = i37 + 1;
            charAt = zzd.charAt(i37);
            if (charAt >= 55296) {
                int i41 = charAt & 8191;
                int i42 = 13;
                while (true) {
                    i11 = i40 + 1;
                    charAt9 = zzd.charAt(i40);
                    if (charAt9 < 55296) {
                        break;
                    }
                    i41 |= (charAt9 & 8191) << i42;
                    i42 += 13;
                    i40 = i11;
                }
                charAt = i41 | (charAt9 << i42);
                i40 = i11;
            }
            int i43 = i40 + 1;
            int charAt20 = zzd.charAt(i40);
            if (charAt20 >= 55296) {
                int i44 = charAt20 & 8191;
                int i45 = 13;
                while (true) {
                    i10 = i43 + 1;
                    charAt8 = zzd.charAt(i43);
                    if (charAt8 < 55296) {
                        break;
                    }
                    i44 |= (charAt8 & 8191) << i45;
                    i45 += 13;
                    i43 = i10;
                }
                charAt20 = i44 | (charAt8 << i45);
                i43 = i10;
            }
            int i46 = i43 + 1;
            charAt2 = zzd.charAt(i43);
            if (charAt2 >= 55296) {
                int i47 = charAt2 & 8191;
                int i48 = 13;
                while (true) {
                    i9 = i46 + 1;
                    charAt7 = zzd.charAt(i46);
                    if (charAt7 < 55296) {
                        break;
                    }
                    i47 |= (charAt7 & 8191) << i48;
                    i48 += 13;
                    i46 = i9;
                }
                charAt2 = i47 | (charAt7 << i48);
                i46 = i9;
            }
            int i49 = i46 + 1;
            int charAt21 = zzd.charAt(i46);
            if (charAt21 >= 55296) {
                int i50 = charAt21 & 8191;
                int i51 = 13;
                while (true) {
                    i8 = i49 + 1;
                    charAt6 = zzd.charAt(i49);
                    if (charAt6 < 55296) {
                        break;
                    }
                    i50 |= (charAt6 & 8191) << i51;
                    i51 += 13;
                    i49 = i8;
                }
                charAt21 = i50 | (charAt6 << i51);
                i49 = i8;
            }
            int i52 = i49 + 1;
            int charAt22 = zzd.charAt(i49);
            if (charAt22 >= 55296) {
                int i53 = charAt22 & 8191;
                int i54 = 13;
                while (true) {
                    i7 = i52 + 1;
                    charAt5 = zzd.charAt(i52);
                    if (charAt5 < 55296) {
                        break;
                    }
                    i53 |= (charAt5 & 8191) << i54;
                    i54 += 13;
                    i52 = i7;
                }
                charAt22 = i53 | (charAt5 << i54);
                i52 = i7;
            }
            int i55 = i52 + 1;
            charAt3 = zzd.charAt(i52);
            if (charAt3 >= 55296) {
                int i56 = charAt3 & 8191;
                int i57 = 13;
                while (true) {
                    i6 = i55 + 1;
                    charAt4 = zzd.charAt(i55);
                    if (charAt4 < 55296) {
                        break;
                    }
                    i56 |= (charAt4 & 8191) << i57;
                    i57 += 13;
                    i55 = i6;
                }
                charAt3 = i56 | (charAt4 << i57);
                i55 = i6;
            }
            iArr = new int[charAt3 + charAt21 + charAt22];
            i2 = charAt18 + charAt18 + charAt19;
            i3 = charAt18;
            i31 = i55;
            int i58 = charAt21;
            i4 = charAt20;
            i5 = i58;
        }
        Unsafe unsafe = zzb;
        Object[] zze = zzwvVar.zze();
        Class<?> cls2 = zzwvVar.zza().getClass();
        int[] iArr3 = new int[charAt2 * 3];
        Object[] objArr2 = new Object[charAt2 + charAt2];
        int i59 = charAt3 + i5;
        int i60 = charAt3;
        int i61 = i59;
        int i62 = 0;
        int i63 = 0;
        while (i31 < length) {
            int i64 = i31 + 1;
            int charAt23 = zzd.charAt(i31);
            if (charAt23 >= c) {
                int i65 = charAt23 & 8191;
                int i66 = i64;
                int i67 = 13;
                while (true) {
                    i28 = i66 + 1;
                    charAt15 = zzd.charAt(i66);
                    if (charAt15 < c) {
                        break;
                    }
                    i65 |= (charAt15 & 8191) << i67;
                    i67 += 13;
                    i66 = i28;
                }
                charAt23 = i65 | (charAt15 << i67);
                i14 = i28;
            } else {
                i14 = i64;
            }
            int i68 = i14 + 1;
            int charAt24 = zzd.charAt(i14);
            if (charAt24 >= c) {
                int i69 = charAt24 & 8191;
                int i70 = i68;
                int i71 = 13;
                while (true) {
                    i27 = i70 + 1;
                    charAt14 = zzd.charAt(i70);
                    i15 = length;
                    if (charAt14 < 55296) {
                        break;
                    }
                    i69 |= (charAt14 & 8191) << i71;
                    i71 += 13;
                    i70 = i27;
                    length = i15;
                }
                charAt24 = i69 | (charAt14 << i71);
                i16 = i27;
            } else {
                i15 = length;
                i16 = i68;
            }
            int i72 = charAt24 & 255;
            int i73 = charAt3;
            if ((charAt24 & 1024) != 0) {
                iArr[i63] = i62;
                i63++;
            }
            if (i72 >= 51) {
                int i74 = i16 + 1;
                int charAt25 = zzd.charAt(i16);
                if (charAt25 >= 55296) {
                    int i75 = charAt25 & 8191;
                    int i76 = i74;
                    int i77 = 13;
                    while (true) {
                        i26 = i76 + 1;
                        charAt13 = zzd.charAt(i76);
                        i18 = i4;
                        if (charAt13 < 55296) {
                            break;
                        }
                        i75 |= (charAt13 & 8191) << i77;
                        i77 += 13;
                        i76 = i26;
                        i4 = i18;
                    }
                    charAt25 = i75 | (charAt13 << i77);
                    i24 = i26;
                } else {
                    i18 = i4;
                    i24 = i74;
                }
                int i78 = i72 - 51;
                i20 = i24;
                if (i78 == 9 || i78 == 17) {
                    int i79 = i62 / 3;
                    i25 = i2 + 1;
                    objArr2[i79 + i79 + 1] = zze[i2];
                } else {
                    if (i78 == 12 && !z) {
                        int i80 = i62 / 3;
                        i25 = i2 + 1;
                        objArr2[i80 + i80 + 1] = zze[i2];
                    }
                    int i81 = charAt25 + charAt25;
                    obj = zze[i81];
                    if (!(obj instanceof Field)) {
                        zzI2 = (Field) obj;
                    } else {
                        zzI2 = zzI(cls2, (String) obj);
                        zze[i81] = zzI2;
                    }
                    iArr2 = iArr3;
                    i17 = charAt;
                    int objectFieldOffset2 = (int) unsafe.objectFieldOffset(zzI2);
                    int i82 = i81 + 1;
                    obj2 = zze[i82];
                    if (!(obj2 instanceof Field)) {
                        zzI3 = (Field) obj2;
                    } else {
                        zzI3 = zzI(cls2, (String) obj2);
                        zze[i82] = zzI3;
                    }
                    int objectFieldOffset3 = (int) unsafe.objectFieldOffset(zzI3);
                    str = zzd;
                    cls = cls2;
                    i21 = objectFieldOffset3;
                    objArr = objArr2;
                    objectFieldOffset = objectFieldOffset2;
                    i22 = 0;
                }
                i2 = i25;
                int i812 = charAt25 + charAt25;
                obj = zze[i812];
                if (!(obj instanceof Field)) {
                }
                iArr2 = iArr3;
                i17 = charAt;
                int objectFieldOffset22 = (int) unsafe.objectFieldOffset(zzI2);
                int i822 = i812 + 1;
                obj2 = zze[i822];
                if (!(obj2 instanceof Field)) {
                }
                int objectFieldOffset32 = (int) unsafe.objectFieldOffset(zzI3);
                str = zzd;
                cls = cls2;
                i21 = objectFieldOffset32;
                objArr = objArr2;
                objectFieldOffset = objectFieldOffset22;
                i22 = 0;
            } else {
                iArr2 = iArr3;
                i17 = charAt;
                i18 = i4;
                int i83 = i2 + 1;
                Field zzI4 = zzI(cls2, (String) zze[i2]);
                if (i72 == 9 || i72 == 17) {
                    int i84 = i62 / 3;
                    objArr2[i84 + i84 + 1] = zzI4.getType();
                } else {
                    if (i72 == 27 || i72 == 49) {
                        int i85 = i62 / 3;
                        i23 = i83 + 1;
                        objArr2[i85 + i85 + 1] = zze[i83];
                    } else if (i72 == 12 || i72 == 30 || i72 == 44) {
                        if (!z) {
                            int i86 = i62 / 3;
                            i23 = i83 + 1;
                            objArr2[i86 + i86 + 1] = zze[i83];
                        }
                    } else if (i72 == 50) {
                        int i87 = i60 + 1;
                        iArr[i60] = i62;
                        int i88 = i62 / 3;
                        int i89 = i88 + i88;
                        int i90 = i83 + 1;
                        objArr2[i89] = zze[i83];
                        if ((charAt24 & 2048) != 0) {
                            i83 = i90 + 1;
                            objArr2[i89 + 1] = zze[i90];
                            i60 = i87;
                        } else {
                            i60 = i87;
                            i19 = i90;
                            objectFieldOffset = (int) unsafe.objectFieldOffset(zzI4);
                            objArr = objArr2;
                            if ((charAt24 & 4096) == 4096 || i72 > 17) {
                                str = zzd;
                                cls = cls2;
                                i20 = i16;
                                i21 = 1048575;
                                i22 = 0;
                            } else {
                                int i91 = i16 + 1;
                                int charAt26 = zzd.charAt(i16);
                                if (charAt26 >= 55296) {
                                    int i92 = charAt26 & 8191;
                                    int i93 = 13;
                                    while (true) {
                                        i20 = i91 + 1;
                                        charAt12 = zzd.charAt(i91);
                                        if (charAt12 < 55296) {
                                            break;
                                        }
                                        i92 |= (charAt12 & 8191) << i93;
                                        i93 += 13;
                                        i91 = i20;
                                    }
                                    charAt26 = i92 | (charAt12 << i93);
                                } else {
                                    i20 = i91;
                                }
                                int i94 = i3 + i3 + (charAt26 / 32);
                                Object obj3 = zze[i94];
                                str = zzd;
                                if (obj3 instanceof Field) {
                                    zzI = (Field) obj3;
                                } else {
                                    zzI = zzI(cls2, (String) obj3);
                                    zze[i94] = zzI;
                                }
                                cls = cls2;
                                i21 = (int) unsafe.objectFieldOffset(zzI);
                                i22 = charAt26 % 32;
                            }
                            if (i72 >= 18 && i72 <= 49) {
                                iArr[i61] = objectFieldOffset;
                                i61++;
                            }
                            i2 = i19;
                        }
                    }
                    i19 = i23;
                    objectFieldOffset = (int) unsafe.objectFieldOffset(zzI4);
                    objArr = objArr2;
                    if ((charAt24 & 4096) == 4096) {
                    }
                    str = zzd;
                    cls = cls2;
                    i20 = i16;
                    i21 = 1048575;
                    i22 = 0;
                    if (i72 >= 18) {
                        iArr[i61] = objectFieldOffset;
                        i61++;
                    }
                    i2 = i19;
                }
                i19 = i83;
                objectFieldOffset = (int) unsafe.objectFieldOffset(zzI4);
                objArr = objArr2;
                if ((charAt24 & 4096) == 4096) {
                }
                str = zzd;
                cls = cls2;
                i20 = i16;
                i21 = 1048575;
                i22 = 0;
                if (i72 >= 18) {
                }
                i2 = i19;
            }
            int i95 = i62 + 1;
            iArr2[i62] = charAt23;
            int i96 = i95 + 1;
            iArr2[i95] = ((charAt24 & 256) != 0 ? 268435456 : 0) | ((charAt24 & 512) != 0 ? 536870912 : 0) | (i72 << 20) | objectFieldOffset;
            i62 = i96 + 1;
            iArr2[i96] = (i22 << 20) | i21;
            cls2 = cls;
            charAt = i17;
            charAt3 = i73;
            i31 = i20;
            length = i15;
            objArr2 = objArr;
            zzd = str;
            iArr3 = iArr2;
            i4 = i18;
            c = 55296;
        }
        return new zzwn<>(iArr3, objArr2, charAt, i4, zzwvVar.zza(), z, false, iArr, charAt3, i59, zzwqVar, zzvyVar, zzxoVar, zzukVar, zzwfVar, null);
    }

    private static <T> double zzo(T t, long j) {
        return ((Double) zzxy.zzf(t, j)).doubleValue();
    }

    private static <T> float zzp(T t, long j) {
        return ((Float) zzxy.zzf(t, j)).floatValue();
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:10:0x003c. Please report as an issue. */
    private final int zzq(T t) {
        int i;
        int zzD;
        int zzD2;
        int zzD3;
        int zzE;
        int zzD4;
        int zzx;
        int zzD5;
        int zzD6;
        int zzd;
        int zzD7;
        int zzo;
        int zzC;
        int zzD8;
        int i2;
        Unsafe unsafe = zzb;
        int i3 = 0;
        int i4 = 0;
        int i5 = 1048575;
        for (int i6 = 0; i6 < this.zzc.length; i6 += 3) {
            int zzC2 = zzC(i6);
            int i7 = this.zzc[i6];
            int zzB = zzB(zzC2);
            if (zzB <= 17) {
                int i8 = this.zzc[i6 + 2];
                int i9 = i8 & 1048575;
                i = 1 << (i8 >>> 20);
                if (i9 != i5) {
                    i4 = unsafe.getInt(t, i9);
                    i5 = i9;
                }
            } else {
                i = 0;
            }
            long j = zzC2 & 1048575;
            switch (zzB) {
                case 0:
                    if ((i4 & i) != 0) {
                        zzD = zzto.zzD(i7 << 3);
                        zzo = zzD + 8;
                        i3 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if ((i4 & i) != 0) {
                        zzD2 = zzto.zzD(i7 << 3);
                        zzo = zzD2 + 4;
                        i3 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if ((i4 & i) != 0) {
                        long j2 = unsafe.getLong(t, j);
                        zzD3 = zzto.zzD(i7 << 3);
                        zzE = zzto.zzE(j2);
                        zzo = zzD3 + zzE;
                        i3 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if ((i4 & i) != 0) {
                        long j3 = unsafe.getLong(t, j);
                        zzD3 = zzto.zzD(i7 << 3);
                        zzE = zzto.zzE(j3);
                        zzo = zzD3 + zzE;
                        i3 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if ((i4 & i) != 0) {
                        int i10 = unsafe.getInt(t, j);
                        zzD4 = zzto.zzD(i7 << 3);
                        zzx = zzto.zzx(i10);
                        i2 = zzD4 + zzx;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if ((i4 & i) != 0) {
                        zzD = zzto.zzD(i7 << 3);
                        zzo = zzD + 8;
                        i3 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if ((i4 & i) != 0) {
                        zzD2 = zzto.zzD(i7 << 3);
                        zzo = zzD2 + 4;
                        i3 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if ((i4 & i) != 0) {
                        zzD5 = zzto.zzD(i7 << 3);
                        zzo = zzD5 + 1;
                        i3 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if ((i4 & i) == 0) {
                        break;
                    } else {
                        Object object = unsafe.getObject(t, j);
                        if (object instanceof zztd) {
                            zzD6 = zzto.zzD(i7 << 3);
                            zzd = ((zztd) object).zzd();
                            zzD7 = zzto.zzD(zzd);
                            i2 = zzD6 + zzD7 + zzd;
                            i3 += i2;
                            break;
                        } else {
                            zzD4 = zzto.zzD(i7 << 3);
                            zzx = zzto.zzB((String) object);
                            i2 = zzD4 + zzx;
                            i3 += i2;
                        }
                    }
                case 9:
                    if ((i4 & i) != 0) {
                        zzo = zzwz.zzo(i7, unsafe.getObject(t, j), zzF(i6));
                        i3 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 10:
                    if ((i4 & i) != 0) {
                        zztd zztdVar = (zztd) unsafe.getObject(t, j);
                        zzD6 = zzto.zzD(i7 << 3);
                        zzd = zztdVar.zzd();
                        zzD7 = zzto.zzD(zzd);
                        i2 = zzD6 + zzD7 + zzd;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if ((i4 & i) != 0) {
                        int i11 = unsafe.getInt(t, j);
                        zzD4 = zzto.zzD(i7 << 3);
                        zzx = zzto.zzD(i11);
                        i2 = zzD4 + zzx;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if ((i4 & i) != 0) {
                        int i12 = unsafe.getInt(t, j);
                        zzD4 = zzto.zzD(i7 << 3);
                        zzx = zzto.zzx(i12);
                        i2 = zzD4 + zzx;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if ((i4 & i) != 0) {
                        zzD2 = zzto.zzD(i7 << 3);
                        zzo = zzD2 + 4;
                        i3 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if ((i4 & i) != 0) {
                        zzD = zzto.zzD(i7 << 3);
                        zzo = zzD + 8;
                        i3 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if ((i4 & i) != 0) {
                        int i13 = unsafe.getInt(t, j);
                        zzD4 = zzto.zzD(i7 << 3);
                        zzx = zzto.zzD((i13 >> 31) ^ (i13 + i13));
                        i2 = zzD4 + zzx;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if ((i4 & i) != 0) {
                        long j4 = unsafe.getLong(t, j);
                        zzD4 = zzto.zzD(i7 << 3);
                        zzx = zzto.zzE((j4 >> 63) ^ (j4 + j4));
                        i2 = zzD4 + zzx;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 17:
                    if ((i4 & i) != 0) {
                        zzo = zzto.zzv(i7, (zzwk) unsafe.getObject(t, j), zzF(i6));
                        i3 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 18:
                    zzo = zzwz.zzh(i7, (List) unsafe.getObject(t, j), false);
                    i3 += zzo;
                    break;
                case 19:
                    zzo = zzwz.zzf(i7, (List) unsafe.getObject(t, j), false);
                    i3 += zzo;
                    break;
                case 20:
                    zzo = zzwz.zzm(i7, (List) unsafe.getObject(t, j), false);
                    i3 += zzo;
                    break;
                case 21:
                    zzo = zzwz.zzx(i7, (List) unsafe.getObject(t, j), false);
                    i3 += zzo;
                    break;
                case 22:
                    zzo = zzwz.zzk(i7, (List) unsafe.getObject(t, j), false);
                    i3 += zzo;
                    break;
                case 23:
                    zzo = zzwz.zzh(i7, (List) unsafe.getObject(t, j), false);
                    i3 += zzo;
                    break;
                case 24:
                    zzo = zzwz.zzf(i7, (List) unsafe.getObject(t, j), false);
                    i3 += zzo;
                    break;
                case 25:
                    zzo = zzwz.zza(i7, (List) unsafe.getObject(t, j), false);
                    i3 += zzo;
                    break;
                case 26:
                    zzo = zzwz.zzu(i7, (List) unsafe.getObject(t, j));
                    i3 += zzo;
                    break;
                case 27:
                    zzo = zzwz.zzp(i7, (List) unsafe.getObject(t, j), zzF(i6));
                    i3 += zzo;
                    break;
                case 28:
                    zzo = zzwz.zzc(i7, (List) unsafe.getObject(t, j));
                    i3 += zzo;
                    break;
                case 29:
                    zzo = zzwz.zzv(i7, (List) unsafe.getObject(t, j), false);
                    i3 += zzo;
                    break;
                case 30:
                    zzo = zzwz.zzd(i7, (List) unsafe.getObject(t, j), false);
                    i3 += zzo;
                    break;
                case 31:
                    zzo = zzwz.zzf(i7, (List) unsafe.getObject(t, j), false);
                    i3 += zzo;
                    break;
                case 32:
                    zzo = zzwz.zzh(i7, (List) unsafe.getObject(t, j), false);
                    i3 += zzo;
                    break;
                case 33:
                    zzo = zzwz.zzq(i7, (List) unsafe.getObject(t, j), false);
                    i3 += zzo;
                    break;
                case 34:
                    zzo = zzwz.zzs(i7, (List) unsafe.getObject(t, j), false);
                    i3 += zzo;
                    break;
                case 35:
                    zzx = zzwz.zzi((List) unsafe.getObject(t, j));
                    if (zzx > 0) {
                        zzC = zzto.zzC(i7);
                        zzD8 = zzto.zzD(zzx);
                        zzD4 = zzC + zzD8;
                        i2 = zzD4 + zzx;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 36:
                    zzx = zzwz.zzg((List) unsafe.getObject(t, j));
                    if (zzx > 0) {
                        zzC = zzto.zzC(i7);
                        zzD8 = zzto.zzD(zzx);
                        zzD4 = zzC + zzD8;
                        i2 = zzD4 + zzx;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 37:
                    zzx = zzwz.zzn((List) unsafe.getObject(t, j));
                    if (zzx > 0) {
                        zzC = zzto.zzC(i7);
                        zzD8 = zzto.zzD(zzx);
                        zzD4 = zzC + zzD8;
                        i2 = zzD4 + zzx;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 38:
                    zzx = zzwz.zzy((List) unsafe.getObject(t, j));
                    if (zzx > 0) {
                        zzC = zzto.zzC(i7);
                        zzD8 = zzto.zzD(zzx);
                        zzD4 = zzC + zzD8;
                        i2 = zzD4 + zzx;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 39:
                    zzx = zzwz.zzl((List) unsafe.getObject(t, j));
                    if (zzx > 0) {
                        zzC = zzto.zzC(i7);
                        zzD8 = zzto.zzD(zzx);
                        zzD4 = zzC + zzD8;
                        i2 = zzD4 + zzx;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 40:
                    zzx = zzwz.zzi((List) unsafe.getObject(t, j));
                    if (zzx > 0) {
                        zzC = zzto.zzC(i7);
                        zzD8 = zzto.zzD(zzx);
                        zzD4 = zzC + zzD8;
                        i2 = zzD4 + zzx;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 41:
                    zzx = zzwz.zzg((List) unsafe.getObject(t, j));
                    if (zzx > 0) {
                        zzC = zzto.zzC(i7);
                        zzD8 = zzto.zzD(zzx);
                        zzD4 = zzC + zzD8;
                        i2 = zzD4 + zzx;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 42:
                    zzx = zzwz.zzb((List) unsafe.getObject(t, j));
                    if (zzx > 0) {
                        zzC = zzto.zzC(i7);
                        zzD8 = zzto.zzD(zzx);
                        zzD4 = zzC + zzD8;
                        i2 = zzD4 + zzx;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 43:
                    zzx = zzwz.zzw((List) unsafe.getObject(t, j));
                    if (zzx > 0) {
                        zzC = zzto.zzC(i7);
                        zzD8 = zzto.zzD(zzx);
                        zzD4 = zzC + zzD8;
                        i2 = zzD4 + zzx;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 44:
                    zzx = zzwz.zze((List) unsafe.getObject(t, j));
                    if (zzx > 0) {
                        zzC = zzto.zzC(i7);
                        zzD8 = zzto.zzD(zzx);
                        zzD4 = zzC + zzD8;
                        i2 = zzD4 + zzx;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 45:
                    zzx = zzwz.zzg((List) unsafe.getObject(t, j));
                    if (zzx > 0) {
                        zzC = zzto.zzC(i7);
                        zzD8 = zzto.zzD(zzx);
                        zzD4 = zzC + zzD8;
                        i2 = zzD4 + zzx;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 46:
                    zzx = zzwz.zzi((List) unsafe.getObject(t, j));
                    if (zzx > 0) {
                        zzC = zzto.zzC(i7);
                        zzD8 = zzto.zzD(zzx);
                        zzD4 = zzC + zzD8;
                        i2 = zzD4 + zzx;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 47:
                    zzx = zzwz.zzr((List) unsafe.getObject(t, j));
                    if (zzx > 0) {
                        zzC = zzto.zzC(i7);
                        zzD8 = zzto.zzD(zzx);
                        zzD4 = zzC + zzD8;
                        i2 = zzD4 + zzx;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 48:
                    zzx = zzwz.zzt((List) unsafe.getObject(t, j));
                    if (zzx > 0) {
                        zzC = zzto.zzC(i7);
                        zzD8 = zzto.zzD(zzx);
                        zzD4 = zzC + zzD8;
                        i2 = zzD4 + zzx;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 49:
                    zzo = zzwz.zzj(i7, (List) unsafe.getObject(t, j), zzF(i6));
                    i3 += zzo;
                    break;
                case 50:
                    zzwf.zza(i7, unsafe.getObject(t, j), zzH(i6));
                    break;
                case 51:
                    if (zzT(t, i7, i6)) {
                        zzD = zzto.zzD(i7 << 3);
                        zzo = zzD + 8;
                        i3 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zzT(t, i7, i6)) {
                        zzD2 = zzto.zzD(i7 << 3);
                        zzo = zzD2 + 4;
                        i3 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zzT(t, i7, i6)) {
                        long zzD9 = zzD(t, j);
                        zzD3 = zzto.zzD(i7 << 3);
                        zzE = zzto.zzE(zzD9);
                        zzo = zzD3 + zzE;
                        i3 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zzT(t, i7, i6)) {
                        long zzD10 = zzD(t, j);
                        zzD3 = zzto.zzD(i7 << 3);
                        zzE = zzto.zzE(zzD10);
                        zzo = zzD3 + zzE;
                        i3 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zzT(t, i7, i6)) {
                        int zzs = zzs(t, j);
                        zzD4 = zzto.zzD(i7 << 3);
                        zzx = zzto.zzx(zzs);
                        i2 = zzD4 + zzx;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zzT(t, i7, i6)) {
                        zzD = zzto.zzD(i7 << 3);
                        zzo = zzD + 8;
                        i3 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zzT(t, i7, i6)) {
                        zzD2 = zzto.zzD(i7 << 3);
                        zzo = zzD2 + 4;
                        i3 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zzT(t, i7, i6)) {
                        zzD5 = zzto.zzD(i7 << 3);
                        zzo = zzD5 + 1;
                        i3 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (!zzT(t, i7, i6)) {
                        break;
                    } else {
                        Object object2 = unsafe.getObject(t, j);
                        if (object2 instanceof zztd) {
                            zzD6 = zzto.zzD(i7 << 3);
                            zzd = ((zztd) object2).zzd();
                            zzD7 = zzto.zzD(zzd);
                            i2 = zzD6 + zzD7 + zzd;
                            i3 += i2;
                            break;
                        } else {
                            zzD4 = zzto.zzD(i7 << 3);
                            zzx = zzto.zzB((String) object2);
                            i2 = zzD4 + zzx;
                            i3 += i2;
                        }
                    }
                case 60:
                    if (zzT(t, i7, i6)) {
                        zzo = zzwz.zzo(i7, unsafe.getObject(t, j), zzF(i6));
                        i3 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zzT(t, i7, i6)) {
                        zztd zztdVar2 = (zztd) unsafe.getObject(t, j);
                        zzD6 = zzto.zzD(i7 << 3);
                        zzd = zztdVar2.zzd();
                        zzD7 = zzto.zzD(zzd);
                        i2 = zzD6 + zzD7 + zzd;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zzT(t, i7, i6)) {
                        int zzs2 = zzs(t, j);
                        zzD4 = zzto.zzD(i7 << 3);
                        zzx = zzto.zzD(zzs2);
                        i2 = zzD4 + zzx;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zzT(t, i7, i6)) {
                        int zzs3 = zzs(t, j);
                        zzD4 = zzto.zzD(i7 << 3);
                        zzx = zzto.zzx(zzs3);
                        i2 = zzD4 + zzx;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zzT(t, i7, i6)) {
                        zzD2 = zzto.zzD(i7 << 3);
                        zzo = zzD2 + 4;
                        i3 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zzT(t, i7, i6)) {
                        zzD = zzto.zzD(i7 << 3);
                        zzo = zzD + 8;
                        i3 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (zzT(t, i7, i6)) {
                        int zzs4 = zzs(t, j);
                        zzD4 = zzto.zzD(i7 << 3);
                        zzx = zzto.zzD((zzs4 >> 31) ^ (zzs4 + zzs4));
                        i2 = zzD4 + zzx;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (zzT(t, i7, i6)) {
                        long zzD11 = zzD(t, j);
                        zzD4 = zzto.zzD(i7 << 3);
                        zzx = zzto.zzE((zzD11 >> 63) ^ (zzD11 + zzD11));
                        i2 = zzD4 + zzx;
                        i3 += i2;
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (zzT(t, i7, i6)) {
                        zzo = zzto.zzv(i7, (zzwk) unsafe.getObject(t, j), zzF(i6));
                        i3 += zzo;
                        break;
                    } else {
                        break;
                    }
            }
        }
        zzxo<?, ?> zzxoVar = this.zzo;
        int zza2 = i3 + zzxoVar.zza(zzxoVar.zzd(t));
        if (!this.zzh) {
            return zza2;
        }
        zzuo<?> zzb2 = this.zzp.zzb(t);
        int i14 = 0;
        for (int i15 = 0; i15 < zzb2.zza.zzb(); i15++) {
            Map.Entry<?, Object> zzg = zzb2.zza.zzg(i15);
            i14 += zzuo.zza((zzun) zzg.getKey(), zzg.getValue());
        }
        for (Map.Entry<?, Object> entry : zzb2.zza.zzc()) {
            i14 += zzuo.zza((zzun) entry.getKey(), entry.getValue());
        }
        return zza2 + i14;
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:10:0x0033. Please report as an issue. */
    private final int zzr(T t) {
        int zzD;
        int zzD2;
        int zzD3;
        int zzE;
        int zzD4;
        int zzx;
        int zzD5;
        int zzD6;
        int zzd;
        int zzD7;
        int zzo;
        int zzC;
        int zzD8;
        int i;
        Unsafe unsafe = zzb;
        int i2 = 0;
        for (int i3 = 0; i3 < this.zzc.length; i3 += 3) {
            int zzC2 = zzC(i3);
            int zzB = zzB(zzC2);
            int i4 = this.zzc[i3];
            long j = zzC2 & 1048575;
            if (zzB >= zzup.DOUBLE_LIST_PACKED.zza() && zzB <= zzup.SINT64_LIST_PACKED.zza()) {
                int i5 = this.zzc[i3 + 2];
            }
            switch (zzB) {
                case 0:
                    if (zzQ(t, i3)) {
                        zzD = zzto.zzD(i4 << 3);
                        zzo = zzD + 8;
                        i2 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (zzQ(t, i3)) {
                        zzD2 = zzto.zzD(i4 << 3);
                        zzo = zzD2 + 4;
                        i2 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (zzQ(t, i3)) {
                        long zzd2 = zzxy.zzd(t, j);
                        zzD3 = zzto.zzD(i4 << 3);
                        zzE = zzto.zzE(zzd2);
                        i2 += zzD3 + zzE;
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (zzQ(t, i3)) {
                        long zzd3 = zzxy.zzd(t, j);
                        zzD3 = zzto.zzD(i4 << 3);
                        zzE = zzto.zzE(zzd3);
                        i2 += zzD3 + zzE;
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (zzQ(t, i3)) {
                        int zzc = zzxy.zzc(t, j);
                        zzD4 = zzto.zzD(i4 << 3);
                        zzx = zzto.zzx(zzc);
                        i = zzD4 + zzx;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (zzQ(t, i3)) {
                        zzD = zzto.zzD(i4 << 3);
                        zzo = zzD + 8;
                        i2 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (zzQ(t, i3)) {
                        zzD2 = zzto.zzD(i4 << 3);
                        zzo = zzD2 + 4;
                        i2 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (zzQ(t, i3)) {
                        zzD5 = zzto.zzD(i4 << 3);
                        zzo = zzD5 + 1;
                        i2 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (!zzQ(t, i3)) {
                        break;
                    } else {
                        Object zzf = zzxy.zzf(t, j);
                        if (zzf instanceof zztd) {
                            zzD6 = zzto.zzD(i4 << 3);
                            zzd = ((zztd) zzf).zzd();
                            zzD7 = zzto.zzD(zzd);
                            i = zzD6 + zzD7 + zzd;
                            i2 += i;
                            break;
                        } else {
                            zzD4 = zzto.zzD(i4 << 3);
                            zzx = zzto.zzB((String) zzf);
                            i = zzD4 + zzx;
                            i2 += i;
                        }
                    }
                case 9:
                    if (zzQ(t, i3)) {
                        zzo = zzwz.zzo(i4, zzxy.zzf(t, j), zzF(i3));
                        i2 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 10:
                    if (zzQ(t, i3)) {
                        zztd zztdVar = (zztd) zzxy.zzf(t, j);
                        zzD6 = zzto.zzD(i4 << 3);
                        zzd = zztdVar.zzd();
                        zzD7 = zzto.zzD(zzd);
                        i = zzD6 + zzD7 + zzd;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (zzQ(t, i3)) {
                        int zzc2 = zzxy.zzc(t, j);
                        zzD4 = zzto.zzD(i4 << 3);
                        zzx = zzto.zzD(zzc2);
                        i = zzD4 + zzx;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (zzQ(t, i3)) {
                        int zzc3 = zzxy.zzc(t, j);
                        zzD4 = zzto.zzD(i4 << 3);
                        zzx = zzto.zzx(zzc3);
                        i = zzD4 + zzx;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (zzQ(t, i3)) {
                        zzD2 = zzto.zzD(i4 << 3);
                        zzo = zzD2 + 4;
                        i2 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (zzQ(t, i3)) {
                        zzD = zzto.zzD(i4 << 3);
                        zzo = zzD + 8;
                        i2 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (zzQ(t, i3)) {
                        int zzc4 = zzxy.zzc(t, j);
                        zzD4 = zzto.zzD(i4 << 3);
                        zzx = zzto.zzD((zzc4 >> 31) ^ (zzc4 + zzc4));
                        i = zzD4 + zzx;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (zzQ(t, i3)) {
                        long zzd4 = zzxy.zzd(t, j);
                        zzD4 = zzto.zzD(i4 << 3);
                        zzx = zzto.zzE((zzd4 >> 63) ^ (zzd4 + zzd4));
                        i = zzD4 + zzx;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 17:
                    if (zzQ(t, i3)) {
                        zzo = zzto.zzv(i4, (zzwk) zzxy.zzf(t, j), zzF(i3));
                        i2 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 18:
                    zzo = zzwz.zzh(i4, (List) zzxy.zzf(t, j), false);
                    i2 += zzo;
                    break;
                case 19:
                    zzo = zzwz.zzf(i4, (List) zzxy.zzf(t, j), false);
                    i2 += zzo;
                    break;
                case 20:
                    zzo = zzwz.zzm(i4, (List) zzxy.zzf(t, j), false);
                    i2 += zzo;
                    break;
                case 21:
                    zzo = zzwz.zzx(i4, (List) zzxy.zzf(t, j), false);
                    i2 += zzo;
                    break;
                case 22:
                    zzo = zzwz.zzk(i4, (List) zzxy.zzf(t, j), false);
                    i2 += zzo;
                    break;
                case 23:
                    zzo = zzwz.zzh(i4, (List) zzxy.zzf(t, j), false);
                    i2 += zzo;
                    break;
                case 24:
                    zzo = zzwz.zzf(i4, (List) zzxy.zzf(t, j), false);
                    i2 += zzo;
                    break;
                case 25:
                    zzo = zzwz.zza(i4, (List) zzxy.zzf(t, j), false);
                    i2 += zzo;
                    break;
                case 26:
                    zzo = zzwz.zzu(i4, (List) zzxy.zzf(t, j));
                    i2 += zzo;
                    break;
                case 27:
                    zzo = zzwz.zzp(i4, (List) zzxy.zzf(t, j), zzF(i3));
                    i2 += zzo;
                    break;
                case 28:
                    zzo = zzwz.zzc(i4, (List) zzxy.zzf(t, j));
                    i2 += zzo;
                    break;
                case 29:
                    zzo = zzwz.zzv(i4, (List) zzxy.zzf(t, j), false);
                    i2 += zzo;
                    break;
                case 30:
                    zzo = zzwz.zzd(i4, (List) zzxy.zzf(t, j), false);
                    i2 += zzo;
                    break;
                case 31:
                    zzo = zzwz.zzf(i4, (List) zzxy.zzf(t, j), false);
                    i2 += zzo;
                    break;
                case 32:
                    zzo = zzwz.zzh(i4, (List) zzxy.zzf(t, j), false);
                    i2 += zzo;
                    break;
                case 33:
                    zzo = zzwz.zzq(i4, (List) zzxy.zzf(t, j), false);
                    i2 += zzo;
                    break;
                case 34:
                    zzo = zzwz.zzs(i4, (List) zzxy.zzf(t, j), false);
                    i2 += zzo;
                    break;
                case 35:
                    zzx = zzwz.zzi((List) unsafe.getObject(t, j));
                    if (zzx > 0) {
                        zzC = zzto.zzC(i4);
                        zzD8 = zzto.zzD(zzx);
                        zzD4 = zzC + zzD8;
                        i = zzD4 + zzx;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 36:
                    zzx = zzwz.zzg((List) unsafe.getObject(t, j));
                    if (zzx > 0) {
                        zzC = zzto.zzC(i4);
                        zzD8 = zzto.zzD(zzx);
                        zzD4 = zzC + zzD8;
                        i = zzD4 + zzx;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 37:
                    zzx = zzwz.zzn((List) unsafe.getObject(t, j));
                    if (zzx > 0) {
                        zzC = zzto.zzC(i4);
                        zzD8 = zzto.zzD(zzx);
                        zzD4 = zzC + zzD8;
                        i = zzD4 + zzx;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 38:
                    zzx = zzwz.zzy((List) unsafe.getObject(t, j));
                    if (zzx > 0) {
                        zzC = zzto.zzC(i4);
                        zzD8 = zzto.zzD(zzx);
                        zzD4 = zzC + zzD8;
                        i = zzD4 + zzx;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 39:
                    zzx = zzwz.zzl((List) unsafe.getObject(t, j));
                    if (zzx > 0) {
                        zzC = zzto.zzC(i4);
                        zzD8 = zzto.zzD(zzx);
                        zzD4 = zzC + zzD8;
                        i = zzD4 + zzx;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 40:
                    zzx = zzwz.zzi((List) unsafe.getObject(t, j));
                    if (zzx > 0) {
                        zzC = zzto.zzC(i4);
                        zzD8 = zzto.zzD(zzx);
                        zzD4 = zzC + zzD8;
                        i = zzD4 + zzx;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 41:
                    zzx = zzwz.zzg((List) unsafe.getObject(t, j));
                    if (zzx > 0) {
                        zzC = zzto.zzC(i4);
                        zzD8 = zzto.zzD(zzx);
                        zzD4 = zzC + zzD8;
                        i = zzD4 + zzx;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 42:
                    zzx = zzwz.zzb((List) unsafe.getObject(t, j));
                    if (zzx > 0) {
                        zzC = zzto.zzC(i4);
                        zzD8 = zzto.zzD(zzx);
                        zzD4 = zzC + zzD8;
                        i = zzD4 + zzx;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 43:
                    zzx = zzwz.zzw((List) unsafe.getObject(t, j));
                    if (zzx > 0) {
                        zzC = zzto.zzC(i4);
                        zzD8 = zzto.zzD(zzx);
                        zzD4 = zzC + zzD8;
                        i = zzD4 + zzx;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 44:
                    zzx = zzwz.zze((List) unsafe.getObject(t, j));
                    if (zzx > 0) {
                        zzC = zzto.zzC(i4);
                        zzD8 = zzto.zzD(zzx);
                        zzD4 = zzC + zzD8;
                        i = zzD4 + zzx;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 45:
                    zzx = zzwz.zzg((List) unsafe.getObject(t, j));
                    if (zzx > 0) {
                        zzC = zzto.zzC(i4);
                        zzD8 = zzto.zzD(zzx);
                        zzD4 = zzC + zzD8;
                        i = zzD4 + zzx;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 46:
                    zzx = zzwz.zzi((List) unsafe.getObject(t, j));
                    if (zzx > 0) {
                        zzC = zzto.zzC(i4);
                        zzD8 = zzto.zzD(zzx);
                        zzD4 = zzC + zzD8;
                        i = zzD4 + zzx;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 47:
                    zzx = zzwz.zzr((List) unsafe.getObject(t, j));
                    if (zzx > 0) {
                        zzC = zzto.zzC(i4);
                        zzD8 = zzto.zzD(zzx);
                        zzD4 = zzC + zzD8;
                        i = zzD4 + zzx;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 48:
                    zzx = zzwz.zzt((List) unsafe.getObject(t, j));
                    if (zzx > 0) {
                        zzC = zzto.zzC(i4);
                        zzD8 = zzto.zzD(zzx);
                        zzD4 = zzC + zzD8;
                        i = zzD4 + zzx;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 49:
                    zzo = zzwz.zzj(i4, (List) zzxy.zzf(t, j), zzF(i3));
                    i2 += zzo;
                    break;
                case 50:
                    zzwf.zza(i4, zzxy.zzf(t, j), zzH(i3));
                    break;
                case 51:
                    if (zzT(t, i4, i3)) {
                        zzD = zzto.zzD(i4 << 3);
                        zzo = zzD + 8;
                        i2 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zzT(t, i4, i3)) {
                        zzD2 = zzto.zzD(i4 << 3);
                        zzo = zzD2 + 4;
                        i2 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zzT(t, i4, i3)) {
                        long zzD9 = zzD(t, j);
                        zzD3 = zzto.zzD(i4 << 3);
                        zzE = zzto.zzE(zzD9);
                        i2 += zzD3 + zzE;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zzT(t, i4, i3)) {
                        long zzD10 = zzD(t, j);
                        zzD3 = zzto.zzD(i4 << 3);
                        zzE = zzto.zzE(zzD10);
                        i2 += zzD3 + zzE;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zzT(t, i4, i3)) {
                        int zzs = zzs(t, j);
                        zzD4 = zzto.zzD(i4 << 3);
                        zzx = zzto.zzx(zzs);
                        i = zzD4 + zzx;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zzT(t, i4, i3)) {
                        zzD = zzto.zzD(i4 << 3);
                        zzo = zzD + 8;
                        i2 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zzT(t, i4, i3)) {
                        zzD2 = zzto.zzD(i4 << 3);
                        zzo = zzD2 + 4;
                        i2 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zzT(t, i4, i3)) {
                        zzD5 = zzto.zzD(i4 << 3);
                        zzo = zzD5 + 1;
                        i2 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (!zzT(t, i4, i3)) {
                        break;
                    } else {
                        Object zzf2 = zzxy.zzf(t, j);
                        if (zzf2 instanceof zztd) {
                            zzD6 = zzto.zzD(i4 << 3);
                            zzd = ((zztd) zzf2).zzd();
                            zzD7 = zzto.zzD(zzd);
                            i = zzD6 + zzD7 + zzd;
                            i2 += i;
                            break;
                        } else {
                            zzD4 = zzto.zzD(i4 << 3);
                            zzx = zzto.zzB((String) zzf2);
                            i = zzD4 + zzx;
                            i2 += i;
                        }
                    }
                case 60:
                    if (zzT(t, i4, i3)) {
                        zzo = zzwz.zzo(i4, zzxy.zzf(t, j), zzF(i3));
                        i2 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zzT(t, i4, i3)) {
                        zztd zztdVar2 = (zztd) zzxy.zzf(t, j);
                        zzD6 = zzto.zzD(i4 << 3);
                        zzd = zztdVar2.zzd();
                        zzD7 = zzto.zzD(zzd);
                        i = zzD6 + zzD7 + zzd;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zzT(t, i4, i3)) {
                        int zzs2 = zzs(t, j);
                        zzD4 = zzto.zzD(i4 << 3);
                        zzx = zzto.zzD(zzs2);
                        i = zzD4 + zzx;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zzT(t, i4, i3)) {
                        int zzs3 = zzs(t, j);
                        zzD4 = zzto.zzD(i4 << 3);
                        zzx = zzto.zzx(zzs3);
                        i = zzD4 + zzx;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zzT(t, i4, i3)) {
                        zzD2 = zzto.zzD(i4 << 3);
                        zzo = zzD2 + 4;
                        i2 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zzT(t, i4, i3)) {
                        zzD = zzto.zzD(i4 << 3);
                        zzo = zzD + 8;
                        i2 += zzo;
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (zzT(t, i4, i3)) {
                        int zzs4 = zzs(t, j);
                        zzD4 = zzto.zzD(i4 << 3);
                        zzx = zzto.zzD((zzs4 >> 31) ^ (zzs4 + zzs4));
                        i = zzD4 + zzx;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (zzT(t, i4, i3)) {
                        long zzD11 = zzD(t, j);
                        zzD4 = zzto.zzD(i4 << 3);
                        zzx = zzto.zzE((zzD11 >> 63) ^ (zzD11 + zzD11));
                        i = zzD4 + zzx;
                        i2 += i;
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (zzT(t, i4, i3)) {
                        zzo = zzto.zzv(i4, (zzwk) zzxy.zzf(t, j), zzF(i3));
                        i2 += zzo;
                        break;
                    } else {
                        break;
                    }
            }
        }
        zzxo<?, ?> zzxoVar = this.zzo;
        return i2 + zzxoVar.zza(zzxoVar.zzd(t));
    }

    private static <T> int zzs(T t, long j) {
        return ((Integer) zzxy.zzf(t, j)).intValue();
    }

    private final <K, V> int zzt(T t, byte[] bArr, int i, int i2, int i3, long j, zzsl zzslVar) throws IOException {
        Unsafe unsafe = zzb;
        Object zzH = zzH(i3);
        Object object = unsafe.getObject(t, j);
        if (zzwf.zzb(object)) {
            zzwe<K, V> zzb2 = zzwe.zza().zzb();
            zzwf.zzc(zzb2, object);
            unsafe.putObject(t, j, zzb2);
        }
        throw null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0023. Please report as an issue. */
    private final int zzu(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, int i8, zzsl zzslVar) throws IOException {
        Unsafe unsafe = zzb;
        long j2 = this.zzc[i8 + 2] & 1048575;
        switch (i7) {
            case 51:
                if (i5 == 1) {
                    unsafe.putObject(t, j, Double.valueOf(Double.longBitsToDouble(zzsm.zzo(bArr, i))));
                    unsafe.putInt(t, j2, i4);
                    return i + 8;
                }
                return i;
            case 52:
                if (i5 == 5) {
                    unsafe.putObject(t, j, Float.valueOf(Float.intBitsToFloat(zzsm.zzb(bArr, i))));
                    unsafe.putInt(t, j2, i4);
                    return i + 4;
                }
                return i;
            case 53:
            case 54:
                if (i5 == 0) {
                    int zzm = zzsm.zzm(bArr, i, zzslVar);
                    unsafe.putObject(t, j, Long.valueOf(zzslVar.zzb));
                    unsafe.putInt(t, j2, i4);
                    return zzm;
                }
                return i;
            case 55:
            case 62:
                if (i5 == 0) {
                    int zzj = zzsm.zzj(bArr, i, zzslVar);
                    unsafe.putObject(t, j, Integer.valueOf(zzslVar.zza));
                    unsafe.putInt(t, j2, i4);
                    return zzj;
                }
                return i;
            case 56:
            case 65:
                if (i5 == 1) {
                    unsafe.putObject(t, j, Long.valueOf(zzsm.zzo(bArr, i)));
                    unsafe.putInt(t, j2, i4);
                    return i + 8;
                }
                return i;
            case 57:
            case 64:
                if (i5 == 5) {
                    unsafe.putObject(t, j, Integer.valueOf(zzsm.zzb(bArr, i)));
                    unsafe.putInt(t, j2, i4);
                    return i + 4;
                }
                return i;
            case 58:
                if (i5 == 0) {
                    int zzm2 = zzsm.zzm(bArr, i, zzslVar);
                    unsafe.putObject(t, j, Boolean.valueOf(zzslVar.zzb != 0));
                    unsafe.putInt(t, j2, i4);
                    return zzm2;
                }
                return i;
            case 59:
                if (i5 == 2) {
                    int zzj2 = zzsm.zzj(bArr, i, zzslVar);
                    int i9 = zzslVar.zza;
                    if (i9 == 0) {
                        unsafe.putObject(t, j, "");
                    } else if ((i6 & 536870912) == 0 || zzyd.zzf(bArr, zzj2, zzj2 + i9)) {
                        unsafe.putObject(t, j, new String(bArr, zzj2, i9, zzvi.zza));
                        zzj2 += i9;
                    } else {
                        throw zzvk.zzd();
                    }
                    unsafe.putInt(t, j2, i4);
                    return zzj2;
                }
                return i;
            case 60:
                if (i5 == 2) {
                    int zzd = zzsm.zzd(zzF(i8), bArr, i, i2, zzslVar);
                    Object object = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                    if (object == null) {
                        unsafe.putObject(t, j, zzslVar.zzc);
                    } else {
                        unsafe.putObject(t, j, zzvi.zzg(object, zzslVar.zzc));
                    }
                    unsafe.putInt(t, j2, i4);
                    return zzd;
                }
                return i;
            case 61:
                if (i5 == 2) {
                    int zza2 = zzsm.zza(bArr, i, zzslVar);
                    unsafe.putObject(t, j, zzslVar.zzc);
                    unsafe.putInt(t, j2, i4);
                    return zza2;
                }
                return i;
            case 63:
                if (i5 == 0) {
                    int zzj3 = zzsm.zzj(bArr, i, zzslVar);
                    int i10 = zzslVar.zza;
                    zzvd zzE = zzE(i8);
                    if (zzE == null || zzE.zza(i10)) {
                        unsafe.putObject(t, j, Integer.valueOf(i10));
                        unsafe.putInt(t, j2, i4);
                    } else {
                        zzd(t).zzh(i3, Long.valueOf(i10));
                    }
                    return zzj3;
                }
                return i;
            case 66:
                if (i5 == 0) {
                    int zzj4 = zzsm.zzj(bArr, i, zzslVar);
                    unsafe.putObject(t, j, Integer.valueOf(zztj.zzs(zzslVar.zza)));
                    unsafe.putInt(t, j2, i4);
                    return zzj4;
                }
                return i;
            case 67:
                if (i5 == 0) {
                    int zzm3 = zzsm.zzm(bArr, i, zzslVar);
                    unsafe.putObject(t, j, Long.valueOf(zztj.zzt(zzslVar.zzb)));
                    unsafe.putInt(t, j2, i4);
                    return zzm3;
                }
                return i;
            case 68:
                if (i5 == 3) {
                    int zzc = zzsm.zzc(zzF(i8), bArr, i, i2, (i3 & (-8)) | 4, zzslVar);
                    Object object2 = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                    if (object2 == null) {
                        unsafe.putObject(t, j, zzslVar.zzc);
                    } else {
                        unsafe.putObject(t, j, zzvi.zzg(object2, zzslVar.zzc));
                    }
                    unsafe.putInt(t, j2, i4);
                    return zzc;
                }
                return i;
            default:
                return i;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:151:0x02a8, code lost:
    
        if (r0 != r15) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x02aa, code lost:
    
        r15 = r30;
        r14 = r31;
        r12 = r32;
        r13 = r34;
        r11 = r35;
        r2 = r19;
        r1 = r20;
        r6 = r24;
        r7 = r25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x02be, code lost:
    
        r2 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x02f1, code lost:
    
        if (r0 != r15) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x0314, code lost:
    
        if (r0 != r15) goto L105;
     */
    /* JADX WARN: Failed to find 'out' block for switch in B:22:0x0097. Please report as an issue. */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v10, types: [int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final int zzv(T t, byte[] bArr, int i, int i2, zzsl zzslVar) throws IOException {
        byte b;
        int i3;
        int zzx;
        int i4;
        int i5;
        Unsafe unsafe;
        int i6;
        int i7;
        Unsafe unsafe2;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int zzm;
        int i13;
        Unsafe unsafe3;
        zzwn<T> zzwnVar = this;
        T t2 = t;
        byte[] bArr2 = bArr;
        int i14 = i2;
        zzsl zzslVar2 = zzslVar;
        Unsafe unsafe4 = zzb;
        int i15 = -1;
        int i16 = 1048575;
        int i17 = i;
        int i18 = -1;
        int i19 = 0;
        int i20 = 0;
        int i21 = 1048575;
        while (i17 < i14) {
            int i22 = i17 + 1;
            byte b2 = bArr2[i17];
            if (b2 < 0) {
                i3 = zzsm.zzk(b2, bArr2, i22, zzslVar2);
                b = zzslVar2.zza;
            } else {
                b = b2;
                i3 = i22;
            }
            int i23 = b >>> 3;
            int i24 = b & 7;
            if (i23 > i18) {
                zzx = zzwnVar.zzy(i23, i19 / 3);
            } else {
                zzx = zzwnVar.zzx(i23);
            }
            int i25 = zzx;
            if (i25 == i15) {
                i4 = i3;
                i5 = i23;
                unsafe = unsafe4;
                i6 = 0;
            } else {
                int i26 = zzwnVar.zzc[i25 + 1];
                int zzB = zzB(i26);
                Unsafe unsafe5 = unsafe4;
                long j = i26 & i16;
                if (zzB <= 17) {
                    int i27 = zzwnVar.zzc[i25 + 2];
                    int i28 = 1 << (i27 >>> 20);
                    int i29 = i27 & 1048575;
                    if (i29 != i21) {
                        i11 = i26;
                        i12 = i25;
                        if (i21 != 1048575) {
                            long j2 = i21;
                            unsafe3 = unsafe5;
                            unsafe3.putInt(t2, j2, i20);
                        } else {
                            unsafe3 = unsafe5;
                        }
                        if (i29 != 1048575) {
                            i20 = unsafe3.getInt(t2, i29);
                        }
                        unsafe2 = unsafe3;
                        i21 = i29;
                    } else {
                        i11 = i26;
                        i12 = i25;
                        unsafe2 = unsafe5;
                    }
                    switch (zzB) {
                        case 0:
                            i7 = i12;
                            i5 = i23;
                            if (i24 != 1) {
                                i4 = i3;
                                unsafe = unsafe2;
                                i6 = i7;
                                break;
                            } else {
                                zzxy.zzo(t2, j, Double.longBitsToDouble(zzsm.zzo(bArr2, i3)));
                                i17 = i3 + 8;
                                i20 |= i28;
                                unsafe4 = unsafe2;
                                i19 = i7;
                                i18 = i5;
                                i16 = 1048575;
                                i15 = -1;
                                i14 = i2;
                                break;
                            }
                        case 1:
                            i7 = i12;
                            i5 = i23;
                            if (i24 != 5) {
                                i4 = i3;
                                unsafe = unsafe2;
                                i6 = i7;
                                break;
                            } else {
                                zzxy.zzp(t2, j, Float.intBitsToFloat(zzsm.zzb(bArr2, i3)));
                                i17 = i3 + 4;
                                i20 |= i28;
                                unsafe4 = unsafe2;
                                i19 = i7;
                                i18 = i5;
                                i16 = 1048575;
                                i15 = -1;
                                i14 = i2;
                                break;
                            }
                        case 2:
                        case 3:
                            i7 = i12;
                            i5 = i23;
                            if (i24 != 0) {
                                i4 = i3;
                                unsafe = unsafe2;
                                i6 = i7;
                                break;
                            } else {
                                zzm = zzsm.zzm(bArr2, i3, zzslVar2);
                                unsafe2.putLong(t, j, zzslVar2.zzb);
                                i20 |= i28;
                                unsafe4 = unsafe2;
                                i19 = i7;
                                i17 = zzm;
                                i18 = i5;
                                i16 = 1048575;
                                i15 = -1;
                                i14 = i2;
                                break;
                            }
                        case 4:
                        case 11:
                            i7 = i12;
                            i5 = i23;
                            if (i24 != 0) {
                                i4 = i3;
                                unsafe = unsafe2;
                                i6 = i7;
                                break;
                            } else {
                                i17 = zzsm.zzj(bArr2, i3, zzslVar2);
                                unsafe2.putInt(t2, j, zzslVar2.zza);
                                i20 |= i28;
                                unsafe4 = unsafe2;
                                i19 = i7;
                                i18 = i5;
                                i16 = 1048575;
                                i15 = -1;
                                i14 = i2;
                                break;
                            }
                        case 5:
                        case 14:
                            i7 = i12;
                            i5 = i23;
                            if (i24 != 1) {
                                i4 = i3;
                                unsafe = unsafe2;
                                i6 = i7;
                                break;
                            } else {
                                unsafe2.putLong(t, j, zzsm.zzo(bArr2, i3));
                                i17 = i3 + 8;
                                i20 |= i28;
                                unsafe4 = unsafe2;
                                i19 = i7;
                                i18 = i5;
                                i16 = 1048575;
                                i15 = -1;
                                i14 = i2;
                                break;
                            }
                        case 6:
                        case 13:
                            i13 = i2;
                            i7 = i12;
                            i5 = i23;
                            if (i24 != 5) {
                                i4 = i3;
                                unsafe = unsafe2;
                                i6 = i7;
                                break;
                            } else {
                                unsafe2.putInt(t2, j, zzsm.zzb(bArr2, i3));
                                i17 = i3 + 4;
                                i20 |= i28;
                                unsafe4 = unsafe2;
                                i18 = i5;
                                i16 = 1048575;
                                i15 = -1;
                                int i30 = i7;
                                i14 = i13;
                                i19 = i30;
                                break;
                            }
                        case 7:
                            i13 = i2;
                            i7 = i12;
                            i5 = i23;
                            if (i24 != 0) {
                                i4 = i3;
                                unsafe = unsafe2;
                                i6 = i7;
                                break;
                            } else {
                                i17 = zzsm.zzm(bArr2, i3, zzslVar2);
                                zzxy.zzm(t2, j, zzslVar2.zzb != 0);
                                i20 |= i28;
                                unsafe4 = unsafe2;
                                i18 = i5;
                                i16 = 1048575;
                                i15 = -1;
                                int i302 = i7;
                                i14 = i13;
                                i19 = i302;
                                break;
                            }
                        case 8:
                            i13 = i2;
                            i7 = i12;
                            i5 = i23;
                            if (i24 != 2) {
                                i4 = i3;
                                unsafe = unsafe2;
                                i6 = i7;
                                break;
                            } else {
                                if ((i11 & 536870912) == 0) {
                                    i17 = zzsm.zzg(bArr2, i3, zzslVar2);
                                } else {
                                    i17 = zzsm.zzh(bArr2, i3, zzslVar2);
                                }
                                unsafe2.putObject(t2, j, zzslVar2.zzc);
                                i20 |= i28;
                                unsafe4 = unsafe2;
                                i18 = i5;
                                i16 = 1048575;
                                i15 = -1;
                                int i3022 = i7;
                                i14 = i13;
                                i19 = i3022;
                                break;
                            }
                        case 9:
                            i7 = i12;
                            i5 = i23;
                            if (i24 != 2) {
                                i4 = i3;
                                unsafe = unsafe2;
                                i6 = i7;
                                break;
                            } else {
                                i13 = i2;
                                i17 = zzsm.zzd(zzwnVar.zzF(i7), bArr2, i3, i13, zzslVar2);
                                Object object = unsafe2.getObject(t2, j);
                                if (object == null) {
                                    unsafe2.putObject(t2, j, zzslVar2.zzc);
                                } else {
                                    unsafe2.putObject(t2, j, zzvi.zzg(object, zzslVar2.zzc));
                                }
                                i20 |= i28;
                                unsafe4 = unsafe2;
                                i18 = i5;
                                i16 = 1048575;
                                i15 = -1;
                                int i30222 = i7;
                                i14 = i13;
                                i19 = i30222;
                                break;
                            }
                        case 10:
                            i7 = i12;
                            i5 = i23;
                            if (i24 != 2) {
                                i4 = i3;
                                unsafe = unsafe2;
                                i6 = i7;
                                break;
                            } else {
                                i17 = zzsm.zza(bArr2, i3, zzslVar2);
                                unsafe2.putObject(t2, j, zzslVar2.zzc);
                                i20 |= i28;
                                unsafe4 = unsafe2;
                                i19 = i7;
                                i18 = i5;
                                i16 = 1048575;
                                i15 = -1;
                                i14 = i2;
                                break;
                            }
                        case 12:
                            i7 = i12;
                            i5 = i23;
                            if (i24 != 0) {
                                i4 = i3;
                                unsafe = unsafe2;
                                i6 = i7;
                                break;
                            } else {
                                i17 = zzsm.zzj(bArr2, i3, zzslVar2);
                                unsafe2.putInt(t2, j, zzslVar2.zza);
                                i20 |= i28;
                                unsafe4 = unsafe2;
                                i19 = i7;
                                i18 = i5;
                                i16 = 1048575;
                                i15 = -1;
                                i14 = i2;
                                break;
                            }
                        case 15:
                            i7 = i12;
                            i5 = i23;
                            if (i24 != 0) {
                                i4 = i3;
                                unsafe = unsafe2;
                                i6 = i7;
                                break;
                            } else {
                                i17 = zzsm.zzj(bArr2, i3, zzslVar2);
                                unsafe2.putInt(t2, j, zztj.zzs(zzslVar2.zza));
                                i20 |= i28;
                                unsafe4 = unsafe2;
                                i19 = i7;
                                i18 = i5;
                                i16 = 1048575;
                                i15 = -1;
                                i14 = i2;
                                break;
                            }
                        case 16:
                            if (i24 != 0) {
                                i7 = i12;
                                i5 = i23;
                                i4 = i3;
                                unsafe = unsafe2;
                                i6 = i7;
                                break;
                            } else {
                                zzm = zzsm.zzm(bArr2, i3, zzslVar2);
                                i7 = i12;
                                i5 = i23;
                                unsafe2.putLong(t, j, zztj.zzt(zzslVar2.zzb));
                                i20 |= i28;
                                unsafe4 = unsafe2;
                                i19 = i7;
                                i17 = zzm;
                                i18 = i5;
                                i16 = 1048575;
                                i15 = -1;
                                i14 = i2;
                                break;
                            }
                        default:
                            i7 = i12;
                            i5 = i23;
                            i4 = i3;
                            unsafe = unsafe2;
                            i6 = i7;
                            break;
                    }
                } else {
                    i5 = i23;
                    i7 = i25;
                    unsafe2 = unsafe5;
                    if (zzB != 27) {
                        if (zzB <= 49) {
                            int i31 = i3;
                            i9 = i20;
                            i10 = i21;
                            unsafe = unsafe2;
                            i6 = i7;
                            i17 = zzw(t, bArr, i3, i2, b, i5, i24, i7, i26, zzB, j, zzslVar);
                        } else {
                            i8 = i3;
                            i9 = i20;
                            i10 = i21;
                            unsafe = unsafe2;
                            i6 = i7;
                            if (zzB != 50) {
                                i17 = zzu(t, bArr, i8, i2, b, i5, i24, i26, zzB, j, i6, zzslVar);
                            } else if (i24 == 2) {
                                i17 = zzt(t, bArr, i8, i2, i6, j, zzslVar);
                            }
                        }
                        unsafe4 = unsafe;
                        i16 = 1048575;
                        i15 = -1;
                    } else if (i24 == 2) {
                        zzvh zzvhVar = (zzvh) unsafe2.getObject(t2, j);
                        if (!zzvhVar.zzc()) {
                            int size = zzvhVar.size();
                            zzvhVar = zzvhVar.zzd(size == 0 ? 10 : size + size);
                            unsafe2.putObject(t2, j, zzvhVar);
                        }
                        i17 = zzsm.zze(zzwnVar.zzF(i7), b, bArr, i3, i2, zzvhVar, zzslVar);
                        i20 = i20;
                        unsafe4 = unsafe2;
                        i19 = i7;
                        i18 = i5;
                        i16 = 1048575;
                        i15 = -1;
                        i14 = i2;
                    } else {
                        i8 = i3;
                        i9 = i20;
                        i10 = i21;
                        unsafe = unsafe2;
                        i6 = i7;
                    }
                    i4 = i8;
                    i20 = i9;
                    i21 = i10;
                }
            }
            i17 = zzsm.zzi(b, bArr, i4, i2, zzd(t), zzslVar);
            zzwnVar = this;
            t2 = t;
            bArr2 = bArr;
            i14 = i2;
            zzslVar2 = zzslVar;
            i19 = i6;
            i18 = i5;
            unsafe4 = unsafe;
            i16 = 1048575;
            i15 = -1;
        }
        int i32 = i20;
        Unsafe unsafe6 = unsafe4;
        if (i21 != 1048575) {
            unsafe6.putInt(t, i21, i32);
        }
        if (i17 == i2) {
            return i17;
        }
        throw zzvk.zzg();
    }

    /* JADX DEBUG: Duplicate block (B:107:0x01cc) to fix multi-entry loop: BACK_EDGE: B:107:0x01cc -> B:99:0x01cd */
    /* JADX DEBUG: Duplicate block (B:127:0x021a) to fix multi-entry loop: BACK_EDGE: B:127:0x021a -> B:117:0x021b */
    /* JADX DEBUG: Duplicate block (B:78:0x014f) to fix multi-entry loop: BACK_EDGE: B:78:0x014f -> B:68:0x0150 */
    /* JADX DEBUG: Multi-variable search result rejected for r16v0, resolved type: T */
    /* JADX WARN: Failed to find 'out' block for switch in B:9:0x0037. Please report as an issue. */
    /* JADX WARN: Multi-variable type inference failed */
    private final int zzw(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, long j, int i7, long j2, zzsl zzslVar) throws IOException {
        int i8;
        int i9;
        int i10;
        int i11;
        int zzj;
        int i12 = i;
        Unsafe unsafe = zzb;
        zzvh zzvhVar = (zzvh) unsafe.getObject(t, j2);
        if (!zzvhVar.zzc()) {
            int size = zzvhVar.size();
            zzvhVar = zzvhVar.zzd(size == 0 ? 10 : size + size);
            unsafe.putObject(t, j2, zzvhVar);
        }
        switch (i7) {
            case 18:
            case 35:
                if (i5 == 2) {
                    zzug zzugVar = (zzug) zzvhVar;
                    int zzj2 = zzsm.zzj(bArr, i12, zzslVar);
                    int i13 = zzslVar.zza + zzj2;
                    while (zzj2 < i13) {
                        zzugVar.zze(Double.longBitsToDouble(zzsm.zzo(bArr, zzj2)));
                        zzj2 += 8;
                    }
                    if (zzj2 == i13) {
                        return zzj2;
                    }
                    throw zzvk.zzj();
                }
                if (i5 == 1) {
                    zzug zzugVar2 = (zzug) zzvhVar;
                    zzugVar2.zze(Double.longBitsToDouble(zzsm.zzo(bArr, i)));
                    while (true) {
                        i8 = i12 + 8;
                        if (i8 < i2) {
                            i12 = zzsm.zzj(bArr, i8, zzslVar);
                            if (i3 == zzslVar.zza) {
                                zzugVar2.zze(Double.longBitsToDouble(zzsm.zzo(bArr, i12)));
                            }
                        }
                    }
                    return i8;
                }
                return i12;
            case 19:
            case 36:
                if (i5 == 2) {
                    zzuq zzuqVar = (zzuq) zzvhVar;
                    int zzj3 = zzsm.zzj(bArr, i12, zzslVar);
                    int i14 = zzslVar.zza + zzj3;
                    while (zzj3 < i14) {
                        zzuqVar.zze(Float.intBitsToFloat(zzsm.zzb(bArr, zzj3)));
                        zzj3 += 4;
                    }
                    if (zzj3 == i14) {
                        return zzj3;
                    }
                    throw zzvk.zzj();
                }
                if (i5 == 5) {
                    zzuq zzuqVar2 = (zzuq) zzvhVar;
                    zzuqVar2.zze(Float.intBitsToFloat(zzsm.zzb(bArr, i)));
                    while (true) {
                        i9 = i12 + 4;
                        if (i9 < i2) {
                            i12 = zzsm.zzj(bArr, i9, zzslVar);
                            if (i3 == zzslVar.zza) {
                                zzuqVar2.zze(Float.intBitsToFloat(zzsm.zzb(bArr, i12)));
                            }
                        }
                    }
                    return i9;
                }
                return i12;
            case 20:
            case 21:
            case 37:
            case 38:
                if (i5 == 2) {
                    zzvz zzvzVar = (zzvz) zzvhVar;
                    int zzj4 = zzsm.zzj(bArr, i12, zzslVar);
                    int i15 = zzslVar.zza + zzj4;
                    while (zzj4 < i15) {
                        zzj4 = zzsm.zzm(bArr, zzj4, zzslVar);
                        zzvzVar.zzf(zzslVar.zzb);
                    }
                    if (zzj4 == i15) {
                        return zzj4;
                    }
                    throw zzvk.zzj();
                }
                if (i5 == 0) {
                    zzvz zzvzVar2 = (zzvz) zzvhVar;
                    int zzm = zzsm.zzm(bArr, i12, zzslVar);
                    zzvzVar2.zzf(zzslVar.zzb);
                    while (zzm < i2) {
                        int zzj5 = zzsm.zzj(bArr, zzm, zzslVar);
                        if (i3 != zzslVar.zza) {
                            return zzm;
                        }
                        zzm = zzsm.zzm(bArr, zzj5, zzslVar);
                        zzvzVar2.zzf(zzslVar.zzb);
                    }
                    return zzm;
                }
                return i12;
            case 22:
            case 29:
            case 39:
            case 43:
                if (i5 == 2) {
                    return zzsm.zzf(bArr, i12, zzvhVar, zzslVar);
                }
                if (i5 == 0) {
                    return zzsm.zzl(i3, bArr, i, i2, zzvhVar, zzslVar);
                }
                return i12;
            case 23:
            case 32:
            case 40:
            case 46:
                if (i5 == 2) {
                    zzvz zzvzVar3 = (zzvz) zzvhVar;
                    int zzj6 = zzsm.zzj(bArr, i12, zzslVar);
                    int i16 = zzslVar.zza + zzj6;
                    while (zzj6 < i16) {
                        zzvzVar3.zzf(zzsm.zzo(bArr, zzj6));
                        zzj6 += 8;
                    }
                    if (zzj6 == i16) {
                        return zzj6;
                    }
                    throw zzvk.zzj();
                }
                if (i5 == 1) {
                    zzvz zzvzVar4 = (zzvz) zzvhVar;
                    zzvzVar4.zzf(zzsm.zzo(bArr, i));
                    while (true) {
                        i10 = i12 + 8;
                        if (i10 < i2) {
                            i12 = zzsm.zzj(bArr, i10, zzslVar);
                            if (i3 == zzslVar.zza) {
                                zzvzVar4.zzf(zzsm.zzo(bArr, i12));
                            }
                        }
                    }
                    return i10;
                }
                return i12;
            case 24:
            case 31:
            case 41:
            case 45:
                if (i5 == 2) {
                    zzva zzvaVar = (zzva) zzvhVar;
                    int zzj7 = zzsm.zzj(bArr, i12, zzslVar);
                    int i17 = zzslVar.zza + zzj7;
                    while (zzj7 < i17) {
                        zzvaVar.zzh(zzsm.zzb(bArr, zzj7));
                        zzj7 += 4;
                    }
                    if (zzj7 == i17) {
                        return zzj7;
                    }
                    throw zzvk.zzj();
                }
                if (i5 == 5) {
                    zzva zzvaVar2 = (zzva) zzvhVar;
                    zzvaVar2.zzh(zzsm.zzb(bArr, i));
                    while (true) {
                        i11 = i12 + 4;
                        if (i11 < i2) {
                            i12 = zzsm.zzj(bArr, i11, zzslVar);
                            if (i3 == zzslVar.zza) {
                                zzvaVar2.zzh(zzsm.zzb(bArr, i12));
                            }
                        }
                    }
                    return i11;
                }
                return i12;
            case 25:
            case 42:
                if (i5 == 2) {
                    zzsr zzsrVar = (zzsr) zzvhVar;
                    zzj = zzsm.zzj(bArr, i12, zzslVar);
                    int i18 = zzslVar.zza + zzj;
                    while (zzj < i18) {
                        zzj = zzsm.zzm(bArr, zzj, zzslVar);
                        zzsrVar.zze(zzslVar.zzb != 0);
                    }
                    if (zzj != i18) {
                        throw zzvk.zzj();
                    }
                    return zzj;
                }
                if (i5 == 0) {
                    zzsr zzsrVar2 = (zzsr) zzvhVar;
                    int zzm2 = zzsm.zzm(bArr, i12, zzslVar);
                    zzsrVar2.zze(zzslVar.zzb != 0);
                    while (zzm2 < i2) {
                        int zzj8 = zzsm.zzj(bArr, zzm2, zzslVar);
                        if (i3 != zzslVar.zza) {
                            return zzm2;
                        }
                        zzm2 = zzsm.zzm(bArr, zzj8, zzslVar);
                        zzsrVar2.zze(zzslVar.zzb != 0);
                    }
                    return zzm2;
                }
                return i12;
            case 26:
                if (i5 == 2) {
                    if ((j & 536870912) != 0) {
                        i12 = zzsm.zzj(bArr, i12, zzslVar);
                        int i19 = zzslVar.zza;
                        if (i19 < 0) {
                            throw zzvk.zzf();
                        }
                        if (i19 == 0) {
                            zzvhVar.add("");
                        } else {
                            int i20 = i12 + i19;
                            if (!zzyd.zzf(bArr, i12, i20)) {
                                throw zzvk.zzd();
                            }
                            zzvhVar.add(new String(bArr, i12, i19, zzvi.zza));
                            i12 = i20;
                        }
                        while (i12 < i2) {
                            int zzj9 = zzsm.zzj(bArr, i12, zzslVar);
                            if (i3 == zzslVar.zza) {
                                i12 = zzsm.zzj(bArr, zzj9, zzslVar);
                                int i21 = zzslVar.zza;
                                if (i21 < 0) {
                                    throw zzvk.zzf();
                                }
                                if (i21 == 0) {
                                    zzvhVar.add("");
                                } else {
                                    int i22 = i12 + i21;
                                    if (zzyd.zzf(bArr, i12, i22)) {
                                        zzvhVar.add(new String(bArr, i12, i21, zzvi.zza));
                                        i12 = i22;
                                    } else {
                                        throw zzvk.zzd();
                                    }
                                }
                            }
                        }
                    } else {
                        i12 = zzsm.zzj(bArr, i12, zzslVar);
                        int i23 = zzslVar.zza;
                        if (i23 < 0) {
                            throw zzvk.zzf();
                        }
                        if (i23 == 0) {
                            zzvhVar.add("");
                        } else {
                            zzvhVar.add(new String(bArr, i12, i23, zzvi.zza));
                            i12 += i23;
                        }
                        while (i12 < i2) {
                            int zzj10 = zzsm.zzj(bArr, i12, zzslVar);
                            if (i3 == zzslVar.zza) {
                                i12 = zzsm.zzj(bArr, zzj10, zzslVar);
                                int i24 = zzslVar.zza;
                                if (i24 < 0) {
                                    throw zzvk.zzf();
                                }
                                if (i24 == 0) {
                                    zzvhVar.add("");
                                } else {
                                    zzvhVar.add(new String(bArr, i12, i24, zzvi.zza));
                                    i12 += i24;
                                }
                            }
                        }
                    }
                }
                return i12;
            case 27:
                if (i5 == 2) {
                    return zzsm.zze(zzF(i6), i3, bArr, i, i2, zzvhVar, zzslVar);
                }
                return i12;
            case 28:
                if (i5 == 2) {
                    int zzj11 = zzsm.zzj(bArr, i12, zzslVar);
                    int i25 = zzslVar.zza;
                    if (i25 < 0) {
                        throw zzvk.zzf();
                    }
                    if (i25 > bArr.length - zzj11) {
                        throw zzvk.zzj();
                    }
                    if (i25 == 0) {
                        zzvhVar.add(zztd.zzb);
                    } else {
                        zzvhVar.add(zztd.zzn(bArr, zzj11, i25));
                        zzj11 += i25;
                    }
                    while (zzj11 < i2) {
                        int zzj12 = zzsm.zzj(bArr, zzj11, zzslVar);
                        if (i3 != zzslVar.zza) {
                            return zzj11;
                        }
                        zzj11 = zzsm.zzj(bArr, zzj12, zzslVar);
                        int i26 = zzslVar.zza;
                        if (i26 >= 0) {
                            if (i26 > bArr.length - zzj11) {
                                throw zzvk.zzj();
                            }
                            if (i26 == 0) {
                                zzvhVar.add(zztd.zzb);
                            } else {
                                zzvhVar.add(zztd.zzn(bArr, zzj11, i26));
                                zzj11 += i26;
                            }
                        } else {
                            throw zzvk.zzf();
                        }
                    }
                    return zzj11;
                }
                return i12;
            case 30:
            case 44:
                if (i5 != 2) {
                    if (i5 == 0) {
                        zzj = zzsm.zzl(i3, bArr, i, i2, zzvhVar, zzslVar);
                    }
                    return i12;
                }
                zzj = zzsm.zzf(bArr, i12, zzvhVar, zzslVar);
                zzuz zzuzVar = (zzuz) t;
                zzxp zzxpVar = zzuzVar.zzc;
                if (zzxpVar == zzxp.zzc()) {
                    zzxpVar = null;
                }
                Object zzC = zzwz.zzC(i4, zzvhVar, zzE(i6), zzxpVar, this.zzo);
                if (zzC != null) {
                    zzuzVar.zzc = (zzxp) zzC;
                    return zzj;
                }
                return zzj;
            case 33:
            case 47:
                if (i5 == 2) {
                    zzva zzvaVar3 = (zzva) zzvhVar;
                    int zzj13 = zzsm.zzj(bArr, i12, zzslVar);
                    int i27 = zzslVar.zza + zzj13;
                    while (zzj13 < i27) {
                        zzj13 = zzsm.zzj(bArr, zzj13, zzslVar);
                        zzvaVar3.zzh(zztj.zzs(zzslVar.zza));
                    }
                    if (zzj13 == i27) {
                        return zzj13;
                    }
                    throw zzvk.zzj();
                }
                if (i5 == 0) {
                    zzva zzvaVar4 = (zzva) zzvhVar;
                    int zzj14 = zzsm.zzj(bArr, i12, zzslVar);
                    zzvaVar4.zzh(zztj.zzs(zzslVar.zza));
                    while (zzj14 < i2) {
                        int zzj15 = zzsm.zzj(bArr, zzj14, zzslVar);
                        if (i3 != zzslVar.zza) {
                            return zzj14;
                        }
                        zzj14 = zzsm.zzj(bArr, zzj15, zzslVar);
                        zzvaVar4.zzh(zztj.zzs(zzslVar.zza));
                    }
                    return zzj14;
                }
                return i12;
            case 34:
            case 48:
                if (i5 == 2) {
                    zzvz zzvzVar5 = (zzvz) zzvhVar;
                    int zzj16 = zzsm.zzj(bArr, i12, zzslVar);
                    int i28 = zzslVar.zza + zzj16;
                    while (zzj16 < i28) {
                        zzj16 = zzsm.zzm(bArr, zzj16, zzslVar);
                        zzvzVar5.zzf(zztj.zzt(zzslVar.zzb));
                    }
                    if (zzj16 == i28) {
                        return zzj16;
                    }
                    throw zzvk.zzj();
                }
                if (i5 == 0) {
                    zzvz zzvzVar6 = (zzvz) zzvhVar;
                    int zzm3 = zzsm.zzm(bArr, i12, zzslVar);
                    zzvzVar6.zzf(zztj.zzt(zzslVar.zzb));
                    while (zzm3 < i2) {
                        int zzj17 = zzsm.zzj(bArr, zzm3, zzslVar);
                        if (i3 != zzslVar.zza) {
                            return zzm3;
                        }
                        zzm3 = zzsm.zzm(bArr, zzj17, zzslVar);
                        zzvzVar6.zzf(zztj.zzt(zzslVar.zzb));
                    }
                    return zzm3;
                }
                return i12;
            default:
                if (i5 == 3) {
                    zzwx zzF = zzF(i6);
                    int i29 = (i3 & (-8)) | 4;
                    int zzc = zzsm.zzc(zzF, bArr, i, i2, i29, zzslVar);
                    zzvhVar.add(zzslVar.zzc);
                    while (zzc < i2) {
                        int zzj18 = zzsm.zzj(bArr, zzc, zzslVar);
                        if (i3 != zzslVar.zza) {
                            return zzc;
                        }
                        zzc = zzsm.zzc(zzF, bArr, zzj18, i2, i29, zzslVar);
                        zzvhVar.add(zzslVar.zzc);
                    }
                    return zzc;
                }
                return i12;
        }
    }

    private final int zzx(int i) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zzA(i, 0);
    }

    private final int zzy(int i, int i2) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zzA(i, i2);
    }

    private final int zzz(int i) {
        return this.zzc[i + 2];
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final int zza(T t) {
        return this.zzj ? zzr(t) : zzq(t);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:4:0x001a. Please report as an issue. */
    @Override // com.google.android.gms.internal.gtm.zzwx
    public final int zzb(T t) {
        int i;
        int zzc;
        int length = this.zzc.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3 += 3) {
            int zzC = zzC(i3);
            int i4 = this.zzc[i3];
            long j = 1048575 & zzC;
            int i5 = 37;
            switch (zzB(zzC)) {
                case 0:
                    i = i2 * 53;
                    zzc = zzvi.zzc(Double.doubleToLongBits(zzxy.zza(t, j)));
                    i2 = i + zzc;
                    break;
                case 1:
                    i = i2 * 53;
                    zzc = Float.floatToIntBits(zzxy.zzb(t, j));
                    i2 = i + zzc;
                    break;
                case 2:
                    i = i2 * 53;
                    zzc = zzvi.zzc(zzxy.zzd(t, j));
                    i2 = i + zzc;
                    break;
                case 3:
                    i = i2 * 53;
                    zzc = zzvi.zzc(zzxy.zzd(t, j));
                    i2 = i + zzc;
                    break;
                case 4:
                    i = i2 * 53;
                    zzc = zzxy.zzc(t, j);
                    i2 = i + zzc;
                    break;
                case 5:
                    i = i2 * 53;
                    zzc = zzvi.zzc(zzxy.zzd(t, j));
                    i2 = i + zzc;
                    break;
                case 6:
                    i = i2 * 53;
                    zzc = zzxy.zzc(t, j);
                    i2 = i + zzc;
                    break;
                case 7:
                    i = i2 * 53;
                    zzc = zzvi.zza(zzxy.zzw(t, j));
                    i2 = i + zzc;
                    break;
                case 8:
                    i = i2 * 53;
                    zzc = ((String) zzxy.zzf(t, j)).hashCode();
                    i2 = i + zzc;
                    break;
                case 9:
                    Object zzf = zzxy.zzf(t, j);
                    if (zzf != null) {
                        i5 = zzf.hashCode();
                    }
                    i2 = (i2 * 53) + i5;
                    break;
                case 10:
                    i = i2 * 53;
                    zzc = zzxy.zzf(t, j).hashCode();
                    i2 = i + zzc;
                    break;
                case 11:
                    i = i2 * 53;
                    zzc = zzxy.zzc(t, j);
                    i2 = i + zzc;
                    break;
                case 12:
                    i = i2 * 53;
                    zzc = zzxy.zzc(t, j);
                    i2 = i + zzc;
                    break;
                case 13:
                    i = i2 * 53;
                    zzc = zzxy.zzc(t, j);
                    i2 = i + zzc;
                    break;
                case 14:
                    i = i2 * 53;
                    zzc = zzvi.zzc(zzxy.zzd(t, j));
                    i2 = i + zzc;
                    break;
                case 15:
                    i = i2 * 53;
                    zzc = zzxy.zzc(t, j);
                    i2 = i + zzc;
                    break;
                case 16:
                    i = i2 * 53;
                    zzc = zzvi.zzc(zzxy.zzd(t, j));
                    i2 = i + zzc;
                    break;
                case 17:
                    Object zzf2 = zzxy.zzf(t, j);
                    if (zzf2 != null) {
                        i5 = zzf2.hashCode();
                    }
                    i2 = (i2 * 53) + i5;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    i = i2 * 53;
                    zzc = zzxy.zzf(t, j).hashCode();
                    i2 = i + zzc;
                    break;
                case 50:
                    i = i2 * 53;
                    zzc = zzxy.zzf(t, j).hashCode();
                    i2 = i + zzc;
                    break;
                case 51:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        zzc = zzvi.zzc(Double.doubleToLongBits(zzo(t, j)));
                        i2 = i + zzc;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        zzc = Float.floatToIntBits(zzp(t, j));
                        i2 = i + zzc;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        zzc = zzvi.zzc(zzD(t, j));
                        i2 = i + zzc;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        zzc = zzvi.zzc(zzD(t, j));
                        i2 = i + zzc;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        zzc = zzs(t, j);
                        i2 = i + zzc;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        zzc = zzvi.zzc(zzD(t, j));
                        i2 = i + zzc;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        zzc = zzs(t, j);
                        i2 = i + zzc;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        zzc = zzvi.zza(zzU(t, j));
                        i2 = i + zzc;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        zzc = ((String) zzxy.zzf(t, j)).hashCode();
                        i2 = i + zzc;
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        zzc = zzxy.zzf(t, j).hashCode();
                        i2 = i + zzc;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        zzc = zzxy.zzf(t, j).hashCode();
                        i2 = i + zzc;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        zzc = zzs(t, j);
                        i2 = i + zzc;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        zzc = zzs(t, j);
                        i2 = i + zzc;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        zzc = zzs(t, j);
                        i2 = i + zzc;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        zzc = zzvi.zzc(zzD(t, j));
                        i2 = i + zzc;
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        zzc = zzs(t, j);
                        i2 = i + zzc;
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        zzc = zzvi.zzc(zzD(t, j));
                        i2 = i + zzc;
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (zzT(t, i4, i3)) {
                        i = i2 * 53;
                        zzc = zzxy.zzf(t, j).hashCode();
                        i2 = i + zzc;
                        break;
                    } else {
                        break;
                    }
            }
        }
        int hashCode = (i2 * 53) + this.zzo.zzd(t).hashCode();
        return this.zzh ? (hashCode * 53) + this.zzp.zzb(t).zza.hashCode() : hashCode;
    }

    /* JADX DEBUG: Type inference failed for r4v0. Raw type applied. Possible types: com.google.android.gms.internal.gtm.zzxo<?, ?>, com.google.android.gms.internal.gtm.zzxo<UT, UB> */
    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Failed to find 'out' block for switch in B:141:0x0094. Please report as an issue. */
    /* JADX WARN: Failed to find 'out' block for switch in B:77:0x043c. Please report as an issue. */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0546  */
    /* JADX WARN: Type inference failed for: r13v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int zzc(T t, byte[] bArr, int i, int i2, int i3, zzsl zzslVar) throws IOException {
        Unsafe unsafe;
        int i4;
        int i5;
        T t2;
        zzwn<T> zzwnVar;
        int i6;
        int i7;
        int i8;
        int i9;
        int zzx;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        T t3;
        byte[] bArr2;
        int i16;
        zzsl zzslVar2;
        int i17;
        int i18;
        T t4;
        Object valueOf;
        int ordinal;
        Object zze;
        T t5;
        int i19;
        int i20;
        int i21;
        int i22;
        int i23;
        int i24;
        int i25;
        int i26;
        int i27;
        int i28;
        zzwn<T> zzwnVar2 = this;
        T t6 = t;
        byte[] bArr3 = bArr;
        int i29 = i2;
        int i30 = i3;
        zzsl zzslVar3 = zzslVar;
        Unsafe unsafe2 = zzb;
        int i31 = i;
        int i32 = 0;
        int i33 = -1;
        int i34 = 0;
        int i35 = 0;
        int i36 = 1048575;
        while (true) {
            if (i31 < i29) {
                int i37 = i31 + 1;
                byte b = bArr3[i31];
                if (b < 0) {
                    i9 = zzsm.zzk(b, bArr3, i37, zzslVar3);
                    i8 = zzslVar3.zza;
                } else {
                    i8 = b;
                    i9 = i37;
                }
                int i38 = i8 >>> 3;
                int i39 = i8 & 7;
                if (i38 > i33) {
                    zzx = zzwnVar2.zzy(i38, i34 / 3);
                } else {
                    zzx = zzwnVar2.zzx(i38);
                }
                if (zzx == -1) {
                    i10 = i9;
                    i11 = i35;
                    i12 = i38;
                    unsafe = unsafe2;
                    i13 = i30;
                    i14 = 0;
                    i15 = i8;
                } else {
                    int i40 = zzwnVar2.zzc[zzx + 1];
                    int zzB = zzB(i40);
                    long j = i40 & 1048575;
                    int i41 = i8;
                    if (zzB <= 17) {
                        int i42 = zzwnVar2.zzc[zzx + 2];
                        int i43 = 1 << (i42 >>> 20);
                        int i44 = i42 & 1048575;
                        if (i44 != i36) {
                            i19 = i39;
                            if (i36 != 1048575) {
                                unsafe2.putInt(t6, i36, i35);
                            }
                            i35 = unsafe2.getInt(t6, i44);
                            i20 = i44;
                        } else {
                            i19 = i39;
                            i20 = i36;
                        }
                        int i45 = i35;
                        switch (zzB) {
                            case 0:
                                i21 = i9;
                                i22 = zzx;
                                i23 = i41;
                                if (i19 != 1) {
                                    i25 = i21;
                                    i13 = i3;
                                    i11 = i45;
                                    i36 = i20;
                                    i14 = i22;
                                    unsafe = unsafe2;
                                    i15 = i23;
                                    i10 = i25;
                                    i12 = i38;
                                    break;
                                } else {
                                    zzxy.zzo(t6, j, Double.longBitsToDouble(zzsm.zzo(bArr3, i21)));
                                    i31 = i21 + 8;
                                    i35 = i45 | i43;
                                    i29 = i2;
                                    i36 = i20;
                                    i34 = i22;
                                    i32 = i23;
                                    i33 = i38;
                                    i30 = i3;
                                }
                            case 1:
                                i24 = i9;
                                i22 = zzx;
                                i23 = i41;
                                if (i19 != 5) {
                                    i25 = i24;
                                    i13 = i3;
                                    i11 = i45;
                                    i36 = i20;
                                    i14 = i22;
                                    unsafe = unsafe2;
                                    i15 = i23;
                                    i10 = i25;
                                    i12 = i38;
                                    break;
                                } else {
                                    zzxy.zzp(t6, j, Float.intBitsToFloat(zzsm.zzb(bArr3, i24)));
                                    i31 = i24 + 4;
                                    i35 = i45 | i43;
                                    i29 = i2;
                                    i36 = i20;
                                    i34 = i22;
                                    i32 = i23;
                                    i33 = i38;
                                    i30 = i3;
                                }
                            case 2:
                            case 3:
                                i24 = i9;
                                i22 = zzx;
                                i23 = i41;
                                if (i19 != 0) {
                                    i25 = i24;
                                    i13 = i3;
                                    i11 = i45;
                                    i36 = i20;
                                    i14 = i22;
                                    unsafe = unsafe2;
                                    i15 = i23;
                                    i10 = i25;
                                    i12 = i38;
                                    break;
                                } else {
                                    int zzm = zzsm.zzm(bArr3, i24, zzslVar3);
                                    unsafe2.putLong(t, j, zzslVar3.zzb);
                                    i35 = i45 | i43;
                                    i36 = i20;
                                    i34 = i22;
                                    i32 = i23;
                                    i31 = zzm;
                                    i33 = i38;
                                    i29 = i2;
                                    i30 = i3;
                                }
                            case 4:
                            case 11:
                                i24 = i9;
                                i22 = zzx;
                                i23 = i41;
                                if (i19 != 0) {
                                    i25 = i24;
                                    i13 = i3;
                                    i11 = i45;
                                    i36 = i20;
                                    i14 = i22;
                                    unsafe = unsafe2;
                                    i15 = i23;
                                    i10 = i25;
                                    i12 = i38;
                                    break;
                                } else {
                                    i31 = zzsm.zzj(bArr3, i24, zzslVar3);
                                    unsafe2.putInt(t6, j, zzslVar3.zza);
                                    i35 = i45 | i43;
                                    i29 = i2;
                                    i36 = i20;
                                    i34 = i22;
                                    i32 = i23;
                                    i33 = i38;
                                    i30 = i3;
                                }
                            case 5:
                            case 14:
                                i22 = zzx;
                                i23 = i41;
                                if (i19 != 1) {
                                    i25 = i9;
                                    i13 = i3;
                                    i11 = i45;
                                    i36 = i20;
                                    i14 = i22;
                                    unsafe = unsafe2;
                                    i15 = i23;
                                    i10 = i25;
                                    i12 = i38;
                                    break;
                                } else {
                                    i21 = i9;
                                    unsafe2.putLong(t, j, zzsm.zzo(bArr3, i9));
                                    i31 = i21 + 8;
                                    i35 = i45 | i43;
                                    i29 = i2;
                                    i36 = i20;
                                    i34 = i22;
                                    i32 = i23;
                                    i33 = i38;
                                    i30 = i3;
                                }
                            case 6:
                            case 13:
                                i22 = zzx;
                                i23 = i41;
                                if (i19 != 5) {
                                    i25 = i9;
                                    i13 = i3;
                                    i11 = i45;
                                    i36 = i20;
                                    i14 = i22;
                                    unsafe = unsafe2;
                                    i15 = i23;
                                    i10 = i25;
                                    i12 = i38;
                                    break;
                                } else {
                                    unsafe2.putInt(t6, j, zzsm.zzb(bArr3, i9));
                                    i31 = i9 + 4;
                                    i35 = i45 | i43;
                                    i36 = i20;
                                    i34 = i22;
                                    i32 = i23;
                                    i33 = i38;
                                    i30 = i3;
                                }
                            case 7:
                                i22 = zzx;
                                i23 = i41;
                                if (i19 != 0) {
                                    i25 = i9;
                                    i13 = i3;
                                    i11 = i45;
                                    i36 = i20;
                                    i14 = i22;
                                    unsafe = unsafe2;
                                    i15 = i23;
                                    i10 = i25;
                                    i12 = i38;
                                    break;
                                } else {
                                    i31 = zzsm.zzm(bArr3, i9, zzslVar3);
                                    zzxy.zzm(t6, j, zzslVar3.zzb != 0);
                                    i35 = i45 | i43;
                                    i36 = i20;
                                    i34 = i22;
                                    i32 = i23;
                                    i33 = i38;
                                    i30 = i3;
                                }
                            case 8:
                                i22 = zzx;
                                i23 = i41;
                                if (i19 != 2) {
                                    i25 = i9;
                                    i13 = i3;
                                    i11 = i45;
                                    i36 = i20;
                                    i14 = i22;
                                    unsafe = unsafe2;
                                    i15 = i23;
                                    i10 = i25;
                                    i12 = i38;
                                    break;
                                } else {
                                    if ((i40 & 536870912) == 0) {
                                        i31 = zzsm.zzg(bArr3, i9, zzslVar3);
                                    } else {
                                        i31 = zzsm.zzh(bArr3, i9, zzslVar3);
                                    }
                                    unsafe2.putObject(t6, j, zzslVar3.zzc);
                                    i35 = i45 | i43;
                                    i36 = i20;
                                    i34 = i22;
                                    i32 = i23;
                                    i33 = i38;
                                    i30 = i3;
                                }
                            case 9:
                                i22 = zzx;
                                i23 = i41;
                                if (i19 != 2) {
                                    i25 = i9;
                                    i13 = i3;
                                    i11 = i45;
                                    i36 = i20;
                                    i14 = i22;
                                    unsafe = unsafe2;
                                    i15 = i23;
                                    i10 = i25;
                                    i12 = i38;
                                    break;
                                } else {
                                    i31 = zzsm.zzd(zzwnVar2.zzF(i22), bArr3, i9, i29, zzslVar3);
                                    if ((i45 & i43) == 0) {
                                        unsafe2.putObject(t6, j, zzslVar3.zzc);
                                    } else {
                                        unsafe2.putObject(t6, j, zzvi.zzg(unsafe2.getObject(t6, j), zzslVar3.zzc));
                                    }
                                    i35 = i45 | i43;
                                    i36 = i20;
                                    i34 = i22;
                                    i32 = i23;
                                    i33 = i38;
                                    i30 = i3;
                                }
                            case 10:
                                i22 = zzx;
                                i23 = i41;
                                if (i19 != 2) {
                                    i25 = i9;
                                    i13 = i3;
                                    i11 = i45;
                                    i36 = i20;
                                    i14 = i22;
                                    unsafe = unsafe2;
                                    i15 = i23;
                                    i10 = i25;
                                    i12 = i38;
                                    break;
                                } else {
                                    i31 = zzsm.zza(bArr3, i9, zzslVar3);
                                    unsafe2.putObject(t6, j, zzslVar3.zzc);
                                    i35 = i45 | i43;
                                    i36 = i20;
                                    i34 = i22;
                                    i32 = i23;
                                    i33 = i38;
                                    i30 = i3;
                                }
                            case 12:
                                i22 = zzx;
                                i23 = i41;
                                if (i19 != 0) {
                                    i25 = i9;
                                    i13 = i3;
                                    i11 = i45;
                                    i36 = i20;
                                    i14 = i22;
                                    unsafe = unsafe2;
                                    i15 = i23;
                                    i10 = i25;
                                    i12 = i38;
                                    break;
                                } else {
                                    i31 = zzsm.zzj(bArr3, i9, zzslVar3);
                                    int i46 = zzslVar3.zza;
                                    zzvd zzE = zzwnVar2.zzE(i22);
                                    if (zzE == null || zzE.zza(i46)) {
                                        unsafe2.putInt(t6, j, i46);
                                        i35 = i45 | i43;
                                        i36 = i20;
                                        i34 = i22;
                                        i32 = i23;
                                        i33 = i38;
                                        i30 = i3;
                                    } else {
                                        zzd(t).zzh(i23, Long.valueOf(i46));
                                        i35 = i45;
                                        i36 = i20;
                                        i34 = i22;
                                        i32 = i23;
                                        i33 = i38;
                                        i30 = i3;
                                    }
                                }
                                break;
                            case 15:
                                i22 = zzx;
                                i23 = i41;
                                if (i19 != 0) {
                                    i25 = i9;
                                    i13 = i3;
                                    i11 = i45;
                                    i36 = i20;
                                    i14 = i22;
                                    unsafe = unsafe2;
                                    i15 = i23;
                                    i10 = i25;
                                    i12 = i38;
                                    break;
                                } else {
                                    i31 = zzsm.zzj(bArr3, i9, zzslVar3);
                                    unsafe2.putInt(t6, j, zztj.zzs(zzslVar3.zza));
                                    i35 = i45 | i43;
                                    i36 = i20;
                                    i34 = i22;
                                    i32 = i23;
                                    i33 = i38;
                                    i30 = i3;
                                }
                            case 16:
                                if (i19 != 0) {
                                    i22 = zzx;
                                    i23 = i41;
                                    i25 = i9;
                                    i13 = i3;
                                    i11 = i45;
                                    i36 = i20;
                                    i14 = i22;
                                    unsafe = unsafe2;
                                    i15 = i23;
                                    i10 = i25;
                                    i12 = i38;
                                    break;
                                } else {
                                    int zzm2 = zzsm.zzm(bArr3, i9, zzslVar3);
                                    i23 = i41;
                                    i22 = zzx;
                                    unsafe2.putLong(t, j, zztj.zzt(zzslVar3.zzb));
                                    i35 = i45 | i43;
                                    i31 = zzm2;
                                    i36 = i20;
                                    i34 = i22;
                                    i32 = i23;
                                    i33 = i38;
                                    i30 = i3;
                                }
                            default:
                                i21 = i9;
                                i22 = zzx;
                                i23 = i41;
                                if (i19 != 3) {
                                    i25 = i21;
                                    i13 = i3;
                                    i11 = i45;
                                    i36 = i20;
                                    i14 = i22;
                                    unsafe = unsafe2;
                                    i15 = i23;
                                    i10 = i25;
                                    i12 = i38;
                                    break;
                                } else {
                                    i31 = zzsm.zzc(zzwnVar2.zzF(i22), bArr, i21, i2, (i38 << 3) | 4, zzslVar);
                                    if ((i45 & i43) == 0) {
                                        unsafe2.putObject(t6, j, zzslVar3.zzc);
                                    } else {
                                        unsafe2.putObject(t6, j, zzvi.zzg(unsafe2.getObject(t6, j), zzslVar3.zzc));
                                    }
                                    i35 = i45 | i43;
                                    bArr3 = bArr;
                                    i29 = i2;
                                    i36 = i20;
                                    i34 = i22;
                                    i32 = i23;
                                    i33 = i38;
                                    i30 = i3;
                                }
                        }
                    } else {
                        int i47 = i9;
                        int i48 = zzx;
                        if (zzB != 27) {
                            i11 = i35;
                            i26 = i36;
                            if (zzB <= 49) {
                                i27 = i41;
                                i12 = i38;
                                i14 = i48;
                                unsafe = unsafe2;
                                i31 = zzw(t, bArr, i47, i2, i41, i38, i39, i48, i40, zzB, j, zzslVar);
                                if (i31 != i47) {
                                    zzwnVar2 = this;
                                    t6 = t;
                                    bArr3 = bArr;
                                    i33 = i12;
                                    i29 = i2;
                                    i30 = i3;
                                    zzslVar3 = zzslVar;
                                    i34 = i14;
                                    i35 = i11;
                                    i36 = i26;
                                    i32 = i27;
                                    unsafe2 = unsafe;
                                } else {
                                    i10 = i31;
                                    i36 = i26;
                                    i15 = i27;
                                    i13 = i3;
                                }
                            } else {
                                i27 = i41;
                                i14 = i48;
                                unsafe = unsafe2;
                                i28 = i47;
                                i12 = i38;
                                if (zzB != 50) {
                                    i31 = zzu(t, bArr, i28, i2, i27, i12, i39, i40, zzB, j, i14, zzslVar);
                                    if (i31 != i28) {
                                        zzwnVar2 = this;
                                        t6 = t;
                                        bArr3 = bArr;
                                        i33 = i12;
                                        i29 = i2;
                                        i30 = i3;
                                        zzslVar3 = zzslVar;
                                        i34 = i14;
                                        i35 = i11;
                                        i36 = i26;
                                        i32 = i27;
                                        unsafe2 = unsafe;
                                    } else {
                                        i10 = i31;
                                        i36 = i26;
                                        i15 = i27;
                                        i13 = i3;
                                    }
                                } else if (i39 == 2) {
                                    i31 = zzt(t, bArr, i28, i2, i14, j, zzslVar);
                                    if (i31 != i28) {
                                        zzwnVar2 = this;
                                        t6 = t;
                                        bArr3 = bArr;
                                        i33 = i12;
                                        i29 = i2;
                                        i30 = i3;
                                        zzslVar3 = zzslVar;
                                        i34 = i14;
                                        i35 = i11;
                                        i36 = i26;
                                        i32 = i27;
                                        unsafe2 = unsafe;
                                    } else {
                                        i10 = i31;
                                        i36 = i26;
                                        i15 = i27;
                                        i13 = i3;
                                    }
                                } else {
                                    i13 = i3;
                                    i10 = i28;
                                    i36 = i26;
                                    i15 = i27;
                                }
                            }
                        } else if (i39 == 2) {
                            zzvh zzvhVar = (zzvh) unsafe2.getObject(t6, j);
                            if (!zzvhVar.zzc()) {
                                int size = zzvhVar.size();
                                zzvhVar = zzvhVar.zzd(size == 0 ? 10 : size + size);
                                unsafe2.putObject(t6, j, zzvhVar);
                            }
                            i32 = i41;
                            i31 = zzsm.zze(zzwnVar2.zzF(i48), i32, bArr, i47, i2, zzvhVar, zzslVar);
                            bArr3 = bArr;
                            i29 = i2;
                            i30 = i3;
                            i34 = i48;
                            i35 = i35;
                            i36 = i36;
                            i33 = i38;
                        } else {
                            i11 = i35;
                            i26 = i36;
                            i27 = i41;
                            i14 = i48;
                            unsafe = unsafe2;
                            i28 = i47;
                            i12 = i38;
                            i13 = i3;
                            i10 = i28;
                            i36 = i26;
                            i15 = i27;
                        }
                    }
                }
                if (i15 != i13 || i13 == 0) {
                    int i49 = i13;
                    if (this.zzh) {
                        zzslVar2 = zzslVar;
                        if (zzslVar2.zzd != zzuj.zza()) {
                            zzwk zzwkVar = this.zzg;
                            zzxo<?, ?> zzxoVar = this.zzo;
                            i16 = i12;
                            zzux zzc = zzslVar2.zzd.zzc(zzwkVar, i16);
                            if (zzc == null) {
                                i31 = zzsm.zzi(i15, bArr, i10, i2, zzd(t), zzslVar);
                                t5 = t;
                                bArr2 = bArr;
                            } else {
                                ?? r13 = t;
                                zzuv zzuvVar = (zzuv) r13;
                                zzuvVar.zzU();
                                zzuo<zzuw> zzuoVar = zzuvVar.zza;
                                if (zzc.zzd.zzc == zzye.ENUM) {
                                    bArr2 = bArr;
                                    i10 = zzsm.zzj(bArr2, i10, zzslVar2);
                                    zzvc<?> zzvcVar = zzc.zzd.zza;
                                    if (zzyl.zzc(zzslVar2.zza) == null) {
                                        zzxp zzxpVar = zzuvVar.zzc;
                                        if (zzxpVar == zzxp.zzc()) {
                                            zzxpVar = zzxp.zze();
                                            zzuvVar.zzc = zzxpVar;
                                        }
                                        zzwz.zzD(i16, zzslVar2.zza, zzxpVar, zzxoVar);
                                        i31 = i10;
                                        t5 = r13;
                                    } else {
                                        valueOf = Integer.valueOf(zzslVar2.zza);
                                    }
                                } else {
                                    bArr2 = bArr;
                                    switch (zzc.zzd.zzc) {
                                        case DOUBLE:
                                            i17 = i36;
                                            i18 = i2;
                                            valueOf = Double.valueOf(Double.longBitsToDouble(zzsm.zzo(bArr2, i10)));
                                            i10 += 8;
                                            zzc.zza();
                                            ordinal = zzc.zzd.zzc.ordinal();
                                            if ((ordinal != 9 || ordinal == 10) && (zze = zzuoVar.zze(zzc.zzd)) != null) {
                                                valueOf = zzvi.zzg(zze, valueOf);
                                            }
                                            zzuoVar.zzi(zzc.zzd, valueOf);
                                            i31 = i10;
                                            t4 = r13;
                                            i32 = i15;
                                            i33 = i16;
                                            t6 = t4;
                                            bArr3 = bArr2;
                                            i34 = i14;
                                            i35 = i11;
                                            i29 = i18;
                                            zzwnVar2 = this;
                                            i30 = i49;
                                            zzslVar3 = zzslVar2;
                                            unsafe2 = unsafe;
                                            i36 = i17;
                                            break;
                                        case FLOAT:
                                            i17 = i36;
                                            i18 = i2;
                                            valueOf = Float.valueOf(Float.intBitsToFloat(zzsm.zzb(bArr2, i10)));
                                            i10 += 4;
                                            zzc.zza();
                                            ordinal = zzc.zzd.zzc.ordinal();
                                            if (ordinal != 9) {
                                                break;
                                            }
                                            valueOf = zzvi.zzg(zze, valueOf);
                                            zzuoVar.zzi(zzc.zzd, valueOf);
                                            i31 = i10;
                                            t4 = r13;
                                            i32 = i15;
                                            i33 = i16;
                                            t6 = t4;
                                            bArr3 = bArr2;
                                            i34 = i14;
                                            i35 = i11;
                                            i29 = i18;
                                            zzwnVar2 = this;
                                            i30 = i49;
                                            zzslVar3 = zzslVar2;
                                            unsafe2 = unsafe;
                                            i36 = i17;
                                            break;
                                        case INT64:
                                        case UINT64:
                                            i17 = i36;
                                            i18 = i2;
                                            i10 = zzsm.zzm(bArr2, i10, zzslVar2);
                                            valueOf = Long.valueOf(zzslVar2.zzb);
                                            zzc.zza();
                                            ordinal = zzc.zzd.zzc.ordinal();
                                            if (ordinal != 9) {
                                            }
                                            valueOf = zzvi.zzg(zze, valueOf);
                                            zzuoVar.zzi(zzc.zzd, valueOf);
                                            i31 = i10;
                                            t4 = r13;
                                            i32 = i15;
                                            i33 = i16;
                                            t6 = t4;
                                            bArr3 = bArr2;
                                            i34 = i14;
                                            i35 = i11;
                                            i29 = i18;
                                            zzwnVar2 = this;
                                            i30 = i49;
                                            zzslVar3 = zzslVar2;
                                            unsafe2 = unsafe;
                                            i36 = i17;
                                            break;
                                        case INT32:
                                        case UINT32:
                                            i17 = i36;
                                            i18 = i2;
                                            i10 = zzsm.zzj(bArr2, i10, zzslVar2);
                                            valueOf = Integer.valueOf(zzslVar2.zza);
                                            zzc.zza();
                                            ordinal = zzc.zzd.zzc.ordinal();
                                            if (ordinal != 9) {
                                            }
                                            valueOf = zzvi.zzg(zze, valueOf);
                                            zzuoVar.zzi(zzc.zzd, valueOf);
                                            i31 = i10;
                                            t4 = r13;
                                            i32 = i15;
                                            i33 = i16;
                                            t6 = t4;
                                            bArr3 = bArr2;
                                            i34 = i14;
                                            i35 = i11;
                                            i29 = i18;
                                            zzwnVar2 = this;
                                            i30 = i49;
                                            zzslVar3 = zzslVar2;
                                            unsafe2 = unsafe;
                                            i36 = i17;
                                            break;
                                        case FIXED64:
                                        case SFIXED64:
                                            i17 = i36;
                                            i18 = i2;
                                            valueOf = Long.valueOf(zzsm.zzo(bArr2, i10));
                                            i10 += 8;
                                            zzc.zza();
                                            ordinal = zzc.zzd.zzc.ordinal();
                                            if (ordinal != 9) {
                                            }
                                            valueOf = zzvi.zzg(zze, valueOf);
                                            zzuoVar.zzi(zzc.zzd, valueOf);
                                            i31 = i10;
                                            t4 = r13;
                                            i32 = i15;
                                            i33 = i16;
                                            t6 = t4;
                                            bArr3 = bArr2;
                                            i34 = i14;
                                            i35 = i11;
                                            i29 = i18;
                                            zzwnVar2 = this;
                                            i30 = i49;
                                            zzslVar3 = zzslVar2;
                                            unsafe2 = unsafe;
                                            i36 = i17;
                                            break;
                                        case FIXED32:
                                        case SFIXED32:
                                            i17 = i36;
                                            i18 = i2;
                                            valueOf = Integer.valueOf(zzsm.zzb(bArr2, i10));
                                            i10 += 4;
                                            zzc.zza();
                                            ordinal = zzc.zzd.zzc.ordinal();
                                            if (ordinal != 9) {
                                            }
                                            valueOf = zzvi.zzg(zze, valueOf);
                                            zzuoVar.zzi(zzc.zzd, valueOf);
                                            i31 = i10;
                                            t4 = r13;
                                            i32 = i15;
                                            i33 = i16;
                                            t6 = t4;
                                            bArr3 = bArr2;
                                            i34 = i14;
                                            i35 = i11;
                                            i29 = i18;
                                            zzwnVar2 = this;
                                            i30 = i49;
                                            zzslVar3 = zzslVar2;
                                            unsafe2 = unsafe;
                                            i36 = i17;
                                            break;
                                        case BOOL:
                                            i17 = i36;
                                            i18 = i2;
                                            i10 = zzsm.zzm(bArr2, i10, zzslVar2);
                                            valueOf = Boolean.valueOf(zzslVar2.zzb != 0);
                                            zzc.zza();
                                            ordinal = zzc.zzd.zzc.ordinal();
                                            if (ordinal != 9) {
                                            }
                                            valueOf = zzvi.zzg(zze, valueOf);
                                            zzuoVar.zzi(zzc.zzd, valueOf);
                                            i31 = i10;
                                            t4 = r13;
                                            i32 = i15;
                                            i33 = i16;
                                            t6 = t4;
                                            bArr3 = bArr2;
                                            i34 = i14;
                                            i35 = i11;
                                            i29 = i18;
                                            zzwnVar2 = this;
                                            i30 = i49;
                                            zzslVar3 = zzslVar2;
                                            unsafe2 = unsafe;
                                            i36 = i17;
                                            break;
                                        case STRING:
                                            i17 = i36;
                                            i18 = i2;
                                            i10 = zzsm.zzg(bArr2, i10, zzslVar2);
                                            valueOf = zzslVar2.zzc;
                                            zzc.zza();
                                            ordinal = zzc.zzd.zzc.ordinal();
                                            if (ordinal != 9) {
                                            }
                                            valueOf = zzvi.zzg(zze, valueOf);
                                            zzuoVar.zzi(zzc.zzd, valueOf);
                                            i31 = i10;
                                            t4 = r13;
                                            i32 = i15;
                                            i33 = i16;
                                            t6 = t4;
                                            bArr3 = bArr2;
                                            i34 = i14;
                                            i35 = i11;
                                            i29 = i18;
                                            zzwnVar2 = this;
                                            i30 = i49;
                                            zzslVar3 = zzslVar2;
                                            unsafe2 = unsafe;
                                            i36 = i17;
                                            break;
                                        case GROUP:
                                            i17 = i36;
                                            i18 = i2;
                                            i10 = zzsm.zzc(zzwt.zza().zzb(zzc.zzc.getClass()), bArr, i10, i2, (i16 << 3) | 4, zzslVar);
                                            valueOf = zzslVar2.zzc;
                                            zzc.zza();
                                            ordinal = zzc.zzd.zzc.ordinal();
                                            if (ordinal != 9) {
                                            }
                                            valueOf = zzvi.zzg(zze, valueOf);
                                            zzuoVar.zzi(zzc.zzd, valueOf);
                                            i31 = i10;
                                            t4 = r13;
                                            i32 = i15;
                                            i33 = i16;
                                            t6 = t4;
                                            bArr3 = bArr2;
                                            i34 = i14;
                                            i35 = i11;
                                            i29 = i18;
                                            zzwnVar2 = this;
                                            i30 = i49;
                                            zzslVar3 = zzslVar2;
                                            unsafe2 = unsafe;
                                            i36 = i17;
                                            break;
                                        case MESSAGE:
                                            i10 = zzsm.zzd(zzwt.zza().zzb(zzc.zzc.getClass()), bArr2, i10, i2, zzslVar2);
                                            valueOf = zzslVar2.zzc;
                                            i17 = i36;
                                            i18 = i2;
                                            zzc.zza();
                                            ordinal = zzc.zzd.zzc.ordinal();
                                            if (ordinal != 9) {
                                            }
                                            valueOf = zzvi.zzg(zze, valueOf);
                                            zzuoVar.zzi(zzc.zzd, valueOf);
                                            i31 = i10;
                                            t4 = r13;
                                            i32 = i15;
                                            i33 = i16;
                                            t6 = t4;
                                            bArr3 = bArr2;
                                            i34 = i14;
                                            i35 = i11;
                                            i29 = i18;
                                            zzwnVar2 = this;
                                            i30 = i49;
                                            zzslVar3 = zzslVar2;
                                            unsafe2 = unsafe;
                                            i36 = i17;
                                            break;
                                        case BYTES:
                                            i10 = zzsm.zza(bArr2, i10, zzslVar2);
                                            valueOf = zzslVar2.zzc;
                                            break;
                                        case ENUM:
                                            throw new IllegalStateException("Shouldn't reach here.");
                                        case SINT32:
                                            i10 = zzsm.zzj(bArr2, i10, zzslVar2);
                                            valueOf = Integer.valueOf(zztj.zzs(zzslVar2.zza));
                                            break;
                                        case SINT64:
                                            i10 = zzsm.zzm(bArr2, i10, zzslVar2);
                                            valueOf = Long.valueOf(zztj.zzt(zzslVar2.zzb));
                                            break;
                                        default:
                                            i17 = i36;
                                            i18 = i2;
                                            valueOf = null;
                                            zzc.zza();
                                            ordinal = zzc.zzd.zzc.ordinal();
                                            if (ordinal != 9) {
                                            }
                                            valueOf = zzvi.zzg(zze, valueOf);
                                            zzuoVar.zzi(zzc.zzd, valueOf);
                                            i31 = i10;
                                            t4 = r13;
                                            i32 = i15;
                                            i33 = i16;
                                            t6 = t4;
                                            bArr3 = bArr2;
                                            i34 = i14;
                                            i35 = i11;
                                            i29 = i18;
                                            zzwnVar2 = this;
                                            i30 = i49;
                                            zzslVar3 = zzslVar2;
                                            unsafe2 = unsafe;
                                            i36 = i17;
                                            break;
                                    }
                                }
                                i17 = i36;
                                i18 = i2;
                                zzc.zza();
                                ordinal = zzc.zzd.zzc.ordinal();
                                if (ordinal != 9) {
                                }
                                valueOf = zzvi.zzg(zze, valueOf);
                                zzuoVar.zzi(zzc.zzd, valueOf);
                                i31 = i10;
                                t4 = r13;
                                i32 = i15;
                                i33 = i16;
                                t6 = t4;
                                bArr3 = bArr2;
                                i34 = i14;
                                i35 = i11;
                                i29 = i18;
                                zzwnVar2 = this;
                                i30 = i49;
                                zzslVar3 = zzslVar2;
                                unsafe2 = unsafe;
                                i36 = i17;
                            }
                            i17 = i36;
                            i18 = i2;
                            t4 = t5;
                            i32 = i15;
                            i33 = i16;
                            t6 = t4;
                            bArr3 = bArr2;
                            i34 = i14;
                            i35 = i11;
                            i29 = i18;
                            zzwnVar2 = this;
                            i30 = i49;
                            zzslVar3 = zzslVar2;
                            unsafe2 = unsafe;
                            i36 = i17;
                        } else {
                            t3 = t;
                            bArr2 = bArr;
                            i16 = i12;
                        }
                    } else {
                        t3 = t;
                        bArr2 = bArr;
                        i16 = i12;
                        zzslVar2 = zzslVar;
                    }
                    i17 = i36;
                    i18 = i2;
                    i31 = zzsm.zzi(i15, bArr, i10, i2, zzd(t), zzslVar);
                    t4 = t3;
                    i32 = i15;
                    i33 = i16;
                    t6 = t4;
                    bArr3 = bArr2;
                    i34 = i14;
                    i35 = i11;
                    i29 = i18;
                    zzwnVar2 = this;
                    i30 = i49;
                    zzslVar3 = zzslVar2;
                    unsafe2 = unsafe;
                    i36 = i17;
                } else {
                    zzwnVar = this;
                    t2 = t;
                    i4 = i13;
                    i31 = i10;
                    i6 = i36;
                    i32 = i15;
                    i35 = i11;
                    i7 = 1048575;
                    i5 = i2;
                }
            } else {
                int i50 = i36;
                unsafe = unsafe2;
                i4 = i30;
                i5 = i29;
                t2 = t6;
                zzwnVar = zzwnVar2;
                i6 = i50;
                i7 = 1048575;
            }
        }
        if (i6 != i7) {
            unsafe.putInt(t2, i6, i35);
        }
        for (int i51 = zzwnVar.zzl; i51 < zzwnVar.zzm; i51++) {
            zzwnVar.zzG(t2, zzwnVar.zzk[i51], null, zzwnVar.zzo);
        }
        if (i4 == 0) {
            if (i31 != i5) {
                throw zzvk.zzg();
            }
        } else if (i31 > i5 || i32 != i4) {
            throw zzvk.zzg();
        }
        return i31;
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final T zze() {
        return (T) ((zzuz) this.zzg).zzb(4, null, null);
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final void zzf(T t) {
        int i;
        int i2 = this.zzl;
        while (true) {
            i = this.zzm;
            if (i2 >= i) {
                break;
            }
            long zzC = zzC(this.zzk[i2]) & 1048575;
            Object zzf = zzxy.zzf(t, zzC);
            if (zzf != null) {
                ((zzwe) zzf).zzc();
                zzxy.zzs(t, zzC, zzf);
            }
            i2++;
        }
        int length = this.zzk.length;
        while (i < length) {
            this.zzn.zzb(t, this.zzk[i]);
            i++;
        }
        this.zzo.zzm(t);
        if (this.zzh) {
            this.zzp.zzf(t);
        }
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[IGET]}, finally: {[IGET, IGET, AGET, INVOKE, ARITH, INVOKE, IF, IF, IGET] complete} */
    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [218=5, 219=5, 220=5] */
    /* JADX WARN: Failed to find 'out' block for switch in B:9:0x0084. Please report as an issue. */
    @Override // com.google.android.gms.internal.gtm.zzwx
    public final void zzh(T t, zzww zzwwVar, zzuj zzujVar) throws IOException {
        Objects.requireNonNull(zzujVar);
        zzxo zzxoVar = this.zzo;
        zzuk<?> zzukVar = this.zzp;
        zzuo<?> zzuoVar = null;
        Object obj = null;
        while (true) {
            try {
                int zzc = zzwwVar.zzc();
                int zzx = zzx(zzc);
                if (zzx >= 0) {
                    int zzC = zzC(zzx);
                    try {
                        switch (zzB(zzC)) {
                            case 0:
                                zzxy.zzo(t, zzC & 1048575, zzwwVar.zza());
                                zzM(t, zzx);
                                break;
                            case 1:
                                zzxy.zzp(t, zzC & 1048575, zzwwVar.zzb());
                                zzM(t, zzx);
                                break;
                            case 2:
                                zzxy.zzr(t, zzC & 1048575, zzwwVar.zzl());
                                zzM(t, zzx);
                                break;
                            case 3:
                                zzxy.zzr(t, zzC & 1048575, zzwwVar.zzo());
                                zzM(t, zzx);
                                break;
                            case 4:
                                zzxy.zzq(t, zzC & 1048575, zzwwVar.zzg());
                                zzM(t, zzx);
                                break;
                            case 5:
                                zzxy.zzr(t, zzC & 1048575, zzwwVar.zzk());
                                zzM(t, zzx);
                                break;
                            case 6:
                                zzxy.zzq(t, zzC & 1048575, zzwwVar.zzf());
                                zzM(t, zzx);
                                break;
                            case 7:
                                zzxy.zzm(t, zzC & 1048575, zzwwVar.zzS());
                                zzM(t, zzx);
                                break;
                            case 8:
                                zzL(t, zzC, zzwwVar);
                                zzM(t, zzx);
                                break;
                            case 9:
                                if (zzQ(t, zzx)) {
                                    long j = zzC & 1048575;
                                    zzxy.zzs(t, j, zzvi.zzg(zzxy.zzf(t, j), zzwwVar.zzu(zzF(zzx), zzujVar)));
                                    break;
                                } else {
                                    zzxy.zzs(t, zzC & 1048575, zzwwVar.zzu(zzF(zzx), zzujVar));
                                    zzM(t, zzx);
                                    break;
                                }
                            case 10:
                                zzxy.zzs(t, zzC & 1048575, zzwwVar.zzq());
                                zzM(t, zzx);
                                break;
                            case 11:
                                zzxy.zzq(t, zzC & 1048575, zzwwVar.zzj());
                                zzM(t, zzx);
                                break;
                            case 12:
                                int zze = zzwwVar.zze();
                                zzvd zzE = zzE(zzx);
                                if (zzE != null && !zzE.zza(zze)) {
                                    obj = zzwz.zzD(zzc, zze, obj, zzxoVar);
                                    break;
                                }
                                zzxy.zzq(t, zzC & 1048575, zze);
                                zzM(t, zzx);
                                break;
                            case 13:
                                zzxy.zzq(t, zzC & 1048575, zzwwVar.zzh());
                                zzM(t, zzx);
                                break;
                            case 14:
                                zzxy.zzr(t, zzC & 1048575, zzwwVar.zzm());
                                zzM(t, zzx);
                                break;
                            case 15:
                                zzxy.zzq(t, zzC & 1048575, zzwwVar.zzi());
                                zzM(t, zzx);
                                break;
                            case 16:
                                zzxy.zzr(t, zzC & 1048575, zzwwVar.zzn());
                                zzM(t, zzx);
                                break;
                            case 17:
                                if (zzQ(t, zzx)) {
                                    long j2 = zzC & 1048575;
                                    zzxy.zzs(t, j2, zzvi.zzg(zzxy.zzf(t, j2), zzwwVar.zzs(zzF(zzx), zzujVar)));
                                    break;
                                } else {
                                    zzxy.zzs(t, zzC & 1048575, zzwwVar.zzs(zzF(zzx), zzujVar));
                                    zzM(t, zzx);
                                    break;
                                }
                            case 18:
                                zzwwVar.zzA(this.zzn.zza(t, zzC & 1048575));
                                break;
                            case 19:
                                zzwwVar.zzE(this.zzn.zza(t, zzC & 1048575));
                                break;
                            case 20:
                                zzwwVar.zzH(this.zzn.zza(t, zzC & 1048575));
                                break;
                            case 21:
                                zzwwVar.zzR(this.zzn.zza(t, zzC & 1048575));
                                break;
                            case 22:
                                zzwwVar.zzG(this.zzn.zza(t, zzC & 1048575));
                                break;
                            case 23:
                                zzwwVar.zzD(this.zzn.zza(t, zzC & 1048575));
                                break;
                            case 24:
                                zzwwVar.zzC(this.zzn.zza(t, zzC & 1048575));
                                break;
                            case 25:
                                zzwwVar.zzy(this.zzn.zza(t, zzC & 1048575));
                                break;
                            case 26:
                                if (zzP(zzC)) {
                                    zzwwVar.zzP(this.zzn.zza(t, zzC & 1048575));
                                    break;
                                } else {
                                    zzwwVar.zzN(this.zzn.zza(t, zzC & 1048575));
                                    break;
                                }
                            case 27:
                                zzwwVar.zzI(this.zzn.zza(t, zzC & 1048575), zzF(zzx), zzujVar);
                                break;
                            case 28:
                                zzwwVar.zzz(this.zzn.zza(t, zzC & 1048575));
                                break;
                            case 29:
                                zzwwVar.zzQ(this.zzn.zza(t, zzC & 1048575));
                                break;
                            case 30:
                                List<Integer> zza2 = this.zzn.zza(t, zzC & 1048575);
                                zzwwVar.zzB(zza2);
                                obj = zzwz.zzC(zzc, zza2, zzE(zzx), obj, zzxoVar);
                                break;
                            case 31:
                                zzwwVar.zzJ(this.zzn.zza(t, zzC & 1048575));
                                break;
                            case 32:
                                zzwwVar.zzK(this.zzn.zza(t, zzC & 1048575));
                                break;
                            case 33:
                                zzwwVar.zzL(this.zzn.zza(t, zzC & 1048575));
                                break;
                            case 34:
                                zzwwVar.zzM(this.zzn.zza(t, zzC & 1048575));
                                break;
                            case 35:
                                zzwwVar.zzA(this.zzn.zza(t, zzC & 1048575));
                                break;
                            case 36:
                                zzwwVar.zzE(this.zzn.zza(t, zzC & 1048575));
                                break;
                            case 37:
                                zzwwVar.zzH(this.zzn.zza(t, zzC & 1048575));
                                break;
                            case 38:
                                zzwwVar.zzR(this.zzn.zza(t, zzC & 1048575));
                                break;
                            case 39:
                                zzwwVar.zzG(this.zzn.zza(t, zzC & 1048575));
                                break;
                            case 40:
                                zzwwVar.zzD(this.zzn.zza(t, zzC & 1048575));
                                break;
                            case 41:
                                zzwwVar.zzC(this.zzn.zza(t, zzC & 1048575));
                                break;
                            case 42:
                                zzwwVar.zzy(this.zzn.zza(t, zzC & 1048575));
                                break;
                            case 43:
                                zzwwVar.zzQ(this.zzn.zza(t, zzC & 1048575));
                                break;
                            case 44:
                                List<Integer> zza3 = this.zzn.zza(t, zzC & 1048575);
                                zzwwVar.zzB(zza3);
                                obj = zzwz.zzC(zzc, zza3, zzE(zzx), obj, zzxoVar);
                                break;
                            case 45:
                                zzwwVar.zzJ(this.zzn.zza(t, zzC & 1048575));
                                break;
                            case 46:
                                zzwwVar.zzK(this.zzn.zza(t, zzC & 1048575));
                                break;
                            case 47:
                                zzwwVar.zzL(this.zzn.zza(t, zzC & 1048575));
                                break;
                            case 48:
                                zzwwVar.zzM(this.zzn.zza(t, zzC & 1048575));
                                break;
                            case 49:
                                zzwwVar.zzF(this.zzn.zza(t, zzC & 1048575), zzF(zzx), zzujVar);
                                break;
                            case 50:
                                Object zzH = zzH(zzx);
                                long zzC2 = zzC(zzx) & 1048575;
                                Object zzf = zzxy.zzf(t, zzC2);
                                if (zzf == null) {
                                    zzf = zzwe.zza().zzb();
                                    zzxy.zzs(t, zzC2, zzf);
                                } else if (zzwf.zzb(zzf)) {
                                    Object zzb2 = zzwe.zza().zzb();
                                    zzwf.zzc(zzb2, zzf);
                                    zzxy.zzs(t, zzC2, zzb2);
                                    zzf = zzb2;
                                }
                                throw null;
                                break;
                            case 51:
                                zzxy.zzs(t, zzC & 1048575, Double.valueOf(zzwwVar.zza()));
                                zzN(t, zzc, zzx);
                                break;
                            case 52:
                                zzxy.zzs(t, zzC & 1048575, Float.valueOf(zzwwVar.zzb()));
                                zzN(t, zzc, zzx);
                                break;
                            case 53:
                                zzxy.zzs(t, zzC & 1048575, Long.valueOf(zzwwVar.zzl()));
                                zzN(t, zzc, zzx);
                                break;
                            case 54:
                                zzxy.zzs(t, zzC & 1048575, Long.valueOf(zzwwVar.zzo()));
                                zzN(t, zzc, zzx);
                                break;
                            case 55:
                                zzxy.zzs(t, zzC & 1048575, Integer.valueOf(zzwwVar.zzg()));
                                zzN(t, zzc, zzx);
                                break;
                            case 56:
                                zzxy.zzs(t, zzC & 1048575, Long.valueOf(zzwwVar.zzk()));
                                zzN(t, zzc, zzx);
                                break;
                            case 57:
                                zzxy.zzs(t, zzC & 1048575, Integer.valueOf(zzwwVar.zzf()));
                                zzN(t, zzc, zzx);
                                break;
                            case 58:
                                zzxy.zzs(t, zzC & 1048575, Boolean.valueOf(zzwwVar.zzS()));
                                zzN(t, zzc, zzx);
                                break;
                            case 59:
                                zzL(t, zzC, zzwwVar);
                                zzN(t, zzc, zzx);
                                break;
                            case 60:
                                if (zzT(t, zzc, zzx)) {
                                    long j3 = zzC & 1048575;
                                    zzxy.zzs(t, j3, zzvi.zzg(zzxy.zzf(t, j3), zzwwVar.zzu(zzF(zzx), zzujVar)));
                                } else {
                                    zzxy.zzs(t, zzC & 1048575, zzwwVar.zzu(zzF(zzx), zzujVar));
                                    zzM(t, zzx);
                                }
                                zzN(t, zzc, zzx);
                                break;
                            case 61:
                                zzxy.zzs(t, zzC & 1048575, zzwwVar.zzq());
                                zzN(t, zzc, zzx);
                                break;
                            case 62:
                                zzxy.zzs(t, zzC & 1048575, Integer.valueOf(zzwwVar.zzj()));
                                zzN(t, zzc, zzx);
                                break;
                            case 63:
                                int zze2 = zzwwVar.zze();
                                zzvd zzE2 = zzE(zzx);
                                if (zzE2 != null && !zzE2.zza(zze2)) {
                                    obj = zzwz.zzD(zzc, zze2, obj, zzxoVar);
                                    break;
                                }
                                zzxy.zzs(t, zzC & 1048575, Integer.valueOf(zze2));
                                zzN(t, zzc, zzx);
                                break;
                            case 64:
                                zzxy.zzs(t, zzC & 1048575, Integer.valueOf(zzwwVar.zzh()));
                                zzN(t, zzc, zzx);
                                break;
                            case 65:
                                zzxy.zzs(t, zzC & 1048575, Long.valueOf(zzwwVar.zzm()));
                                zzN(t, zzc, zzx);
                                break;
                            case 66:
                                zzxy.zzs(t, zzC & 1048575, Integer.valueOf(zzwwVar.zzi()));
                                zzN(t, zzc, zzx);
                                break;
                            case 67:
                                zzxy.zzs(t, zzC & 1048575, Long.valueOf(zzwwVar.zzn()));
                                zzN(t, zzc, zzx);
                                break;
                            case 68:
                                zzxy.zzs(t, zzC & 1048575, zzwwVar.zzs(zzF(zzx), zzujVar));
                                zzN(t, zzc, zzx);
                                break;
                            default:
                                if (obj == null) {
                                    obj = zzxoVar.zzf();
                                }
                                if (!zzxoVar.zzp(obj, zzwwVar)) {
                                    for (int i = this.zzl; i < this.zzm; i++) {
                                        zzG(t, this.zzk[i], obj, zzxoVar);
                                    }
                                    zzxoVar.zzn(t, obj);
                                    return;
                                }
                                break;
                        }
                    } catch (zzvj unused) {
                        zzxoVar.zzq(zzwwVar);
                        if (obj == null) {
                            obj = zzxoVar.zzc(t);
                        }
                        if (!zzxoVar.zzp(obj, zzwwVar)) {
                            for (int i2 = this.zzl; i2 < this.zzm; i2++) {
                                zzG(t, this.zzk[i2], obj, zzxoVar);
                            }
                            if (obj != null) {
                                zzxoVar.zzn(t, obj);
                                return;
                            }
                            return;
                        }
                    }
                } else {
                    if (zzc == Integer.MAX_VALUE) {
                        for (int i3 = this.zzl; i3 < this.zzm; i3++) {
                            zzG(t, this.zzk[i3], obj, zzxoVar);
                        }
                        if (obj != null) {
                            zzxoVar.zzn(t, obj);
                            return;
                        }
                        return;
                    }
                    Object zzd = !this.zzh ? null : zzukVar.zzd(zzujVar, this.zzg, zzc);
                    if (zzd != null) {
                        if (zzuoVar == null) {
                            zzuoVar = zzukVar.zzc(t);
                        }
                        zzuo<?> zzuoVar2 = zzuoVar;
                        obj = zzukVar.zze(zzwwVar, zzd, zzujVar, zzuoVar2, obj, zzxoVar);
                        zzuoVar = zzuoVar2;
                    } else {
                        zzxoVar.zzq(zzwwVar);
                        if (obj == null) {
                            obj = zzxoVar.zzc(t);
                        }
                        if (!zzxoVar.zzp(obj, zzwwVar)) {
                            for (int i4 = this.zzl; i4 < this.zzm; i4++) {
                                zzG(t, this.zzk[i4], obj, zzxoVar);
                            }
                            if (obj != null) {
                                zzxoVar.zzn(t, obj);
                                return;
                            }
                            return;
                        }
                    }
                }
            } catch (Throwable th) {
                for (int i5 = this.zzl; i5 < this.zzm; i5++) {
                    zzG(t, this.zzk[i5], obj, zzxoVar);
                }
                if (obj != null) {
                    zzxoVar.zzn(t, obj);
                }
                throw th;
            }
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final void zzi(T t, byte[] bArr, int i, int i2, zzsl zzslVar) throws IOException {
        if (this.zzj) {
            zzv(t, bArr, i, i2, zzslVar);
        } else {
            zzc(t, bArr, i, i2, 0, zzslVar);
        }
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:4:0x0015. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:17:0x01c2 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x01c3 A[SYNTHETIC] */
    @Override // com.google.android.gms.internal.gtm.zzwx
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean zzj(T t, T t2) {
        int i;
        boolean zzH;
        int length = this.zzc.length;
        while (i < length) {
            int zzC = zzC(i);
            long j = zzC & 1048575;
            switch (zzB(zzC)) {
                case 0:
                    i = (zzO(t, t2, i) && Double.doubleToLongBits(zzxy.zza(t, j)) == Double.doubleToLongBits(zzxy.zza(t2, j))) ? i + 3 : 0;
                    return false;
                case 1:
                    if (zzO(t, t2, i) && Float.floatToIntBits(zzxy.zzb(t, j)) == Float.floatToIntBits(zzxy.zzb(t2, j))) {
                    }
                    return false;
                case 2:
                    if (zzO(t, t2, i) && zzxy.zzd(t, j) == zzxy.zzd(t2, j)) {
                    }
                    return false;
                case 3:
                    if (zzO(t, t2, i) && zzxy.zzd(t, j) == zzxy.zzd(t2, j)) {
                    }
                    return false;
                case 4:
                    if (zzO(t, t2, i) && zzxy.zzc(t, j) == zzxy.zzc(t2, j)) {
                    }
                    return false;
                case 5:
                    if (zzO(t, t2, i) && zzxy.zzd(t, j) == zzxy.zzd(t2, j)) {
                    }
                    return false;
                case 6:
                    if (zzO(t, t2, i) && zzxy.zzc(t, j) == zzxy.zzc(t2, j)) {
                    }
                    return false;
                case 7:
                    if (zzO(t, t2, i) && zzxy.zzw(t, j) == zzxy.zzw(t2, j)) {
                    }
                    return false;
                case 8:
                    if (zzO(t, t2, i) && zzwz.zzH(zzxy.zzf(t, j), zzxy.zzf(t2, j))) {
                    }
                    return false;
                case 9:
                    if (zzO(t, t2, i) && zzwz.zzH(zzxy.zzf(t, j), zzxy.zzf(t2, j))) {
                    }
                    return false;
                case 10:
                    if (zzO(t, t2, i) && zzwz.zzH(zzxy.zzf(t, j), zzxy.zzf(t2, j))) {
                    }
                    return false;
                case 11:
                    if (zzO(t, t2, i) && zzxy.zzc(t, j) == zzxy.zzc(t2, j)) {
                    }
                    return false;
                case 12:
                    if (zzO(t, t2, i) && zzxy.zzc(t, j) == zzxy.zzc(t2, j)) {
                    }
                    return false;
                case 13:
                    if (zzO(t, t2, i) && zzxy.zzc(t, j) == zzxy.zzc(t2, j)) {
                    }
                    return false;
                case 14:
                    if (zzO(t, t2, i) && zzxy.zzd(t, j) == zzxy.zzd(t2, j)) {
                    }
                    return false;
                case 15:
                    if (zzO(t, t2, i) && zzxy.zzc(t, j) == zzxy.zzc(t2, j)) {
                    }
                    return false;
                case 16:
                    if (zzO(t, t2, i) && zzxy.zzd(t, j) == zzxy.zzd(t2, j)) {
                    }
                    return false;
                case 17:
                    if (zzO(t, t2, i) && zzwz.zzH(zzxy.zzf(t, j), zzxy.zzf(t2, j))) {
                    }
                    return false;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    zzH = zzwz.zzH(zzxy.zzf(t, j), zzxy.zzf(t2, j));
                    if (zzH) {
                        return false;
                    }
                case 50:
                    zzH = zzwz.zzH(zzxy.zzf(t, j), zzxy.zzf(t2, j));
                    if (zzH) {
                    }
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                case 60:
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                case 68:
                    long zzz = zzz(i) & 1048575;
                    if (zzxy.zzc(t, zzz) == zzxy.zzc(t2, zzz) && zzwz.zzH(zzxy.zzf(t, j), zzxy.zzf(t2, j))) {
                    }
                    return false;
                default:
            }
        }
        if (!this.zzo.zzd(t).equals(this.zzo.zzd(t2))) {
            return false;
        }
        if (this.zzh) {
            return this.zzp.zzb(t).equals(this.zzp.zzb(t2));
        }
        return true;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r1v8, resolved type: com.google.android.gms.internal.gtm.zzwx */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.gtm.zzwx
    public final boolean zzk(T t) {
        int i;
        int i2;
        int i3 = 1048575;
        int i4 = 0;
        int i5 = 0;
        while (i5 < this.zzl) {
            int i6 = this.zzk[i5];
            int i7 = this.zzc[i6];
            int zzC = zzC(i6);
            int i8 = this.zzc[i6 + 2];
            int i9 = i8 & 1048575;
            int i10 = 1 << (i8 >>> 20);
            if (i9 != i3) {
                if (i9 != 1048575) {
                    i4 = zzb.getInt(t, i9);
                }
                i2 = i4;
                i = i9;
            } else {
                i = i3;
                i2 = i4;
            }
            if ((268435456 & zzC) != 0 && !zzR(t, i6, i, i2, i10)) {
                return false;
            }
            int zzB = zzB(zzC);
            if (zzB != 9 && zzB != 17) {
                if (zzB != 27) {
                    if (zzB == 60 || zzB == 68) {
                        if (zzT(t, i7, i6) && !zzS(t, zzC, zzF(i6))) {
                            return false;
                        }
                    } else if (zzB != 49) {
                        if (zzB == 50 && !((zzwe) zzxy.zzf(t, zzC & 1048575)).isEmpty()) {
                            throw null;
                        }
                    }
                }
                List list = (List) zzxy.zzf(t, zzC & 1048575);
                if (list.isEmpty()) {
                    continue;
                } else {
                    zzwx zzF = zzF(i6);
                    for (int i11 = 0; i11 < list.size(); i11++) {
                        if (!zzF.zzk(list.get(i11))) {
                            return false;
                        }
                    }
                }
            } else if (zzR(t, i6, i, i2, i10) && !zzS(t, zzC, zzF(i6))) {
                return false;
            }
            i5++;
            i3 = i;
            i4 = i2;
        }
        return !this.zzh || this.zzp.zzb(t).zzk();
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARN: Removed duplicated region for block: B:277:0x0507  */
    @Override // com.google.android.gms.internal.gtm.zzwx
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void zzn(T t, zztp zztpVar) throws IOException {
        Iterator<Map.Entry<?, Object>> it;
        Map.Entry<?, ?> entry;
        int length;
        int i;
        if (this.zzj) {
            if (this.zzh) {
                zzuo<?> zzb2 = this.zzp.zzb(t);
                if (!zzb2.zza.isEmpty()) {
                    it = zzb2.zzf();
                    entry = (Map.Entry) it.next();
                    length = this.zzc.length;
                    for (i = 0; i < length; i += 3) {
                        int zzC = zzC(i);
                        int i2 = this.zzc[i];
                        while (entry != null && this.zzp.zza(entry) <= i2) {
                            this.zzp.zzj(zztpVar, entry);
                            entry = it.hasNext() ? (Map.Entry) it.next() : null;
                        }
                        switch (zzB(zzC)) {
                            case 0:
                                if (zzQ(t, i)) {
                                    zztpVar.zzf(i2, zzxy.zza(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 1:
                                if (zzQ(t, i)) {
                                    zztpVar.zzo(i2, zzxy.zzb(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 2:
                                if (zzQ(t, i)) {
                                    zztpVar.zzt(i2, zzxy.zzd(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 3:
                                if (zzQ(t, i)) {
                                    zztpVar.zzK(i2, zzxy.zzd(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 4:
                                if (zzQ(t, i)) {
                                    zztpVar.zzr(i2, zzxy.zzc(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 5:
                                if (zzQ(t, i)) {
                                    zztpVar.zzm(i2, zzxy.zzd(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 6:
                                if (zzQ(t, i)) {
                                    zztpVar.zzk(i2, zzxy.zzc(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 7:
                                if (zzQ(t, i)) {
                                    zztpVar.zzb(i2, zzxy.zzw(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 8:
                                if (zzQ(t, i)) {
                                    zzX(i2, zzxy.zzf(t, zzC & 1048575), zztpVar);
                                    break;
                                } else {
                                    break;
                                }
                            case 9:
                                if (zzQ(t, i)) {
                                    zztpVar.zzv(i2, zzxy.zzf(t, zzC & 1048575), zzF(i));
                                    break;
                                } else {
                                    break;
                                }
                            case 10:
                                if (zzQ(t, i)) {
                                    zztpVar.zzd(i2, (zztd) zzxy.zzf(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 11:
                                if (zzQ(t, i)) {
                                    zztpVar.zzI(i2, zzxy.zzc(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 12:
                                if (zzQ(t, i)) {
                                    zztpVar.zzi(i2, zzxy.zzc(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 13:
                                if (zzQ(t, i)) {
                                    zztpVar.zzx(i2, zzxy.zzc(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 14:
                                if (zzQ(t, i)) {
                                    zztpVar.zzz(i2, zzxy.zzd(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 15:
                                if (zzQ(t, i)) {
                                    zztpVar.zzB(i2, zzxy.zzc(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 16:
                                if (zzQ(t, i)) {
                                    zztpVar.zzD(i2, zzxy.zzd(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 17:
                                if (zzQ(t, i)) {
                                    zztpVar.zzq(i2, zzxy.zzf(t, zzC & 1048575), zzF(i));
                                    break;
                                } else {
                                    break;
                                }
                            case 18:
                                zzwz.zzL(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, false);
                                break;
                            case 19:
                                zzwz.zzP(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, false);
                                break;
                            case 20:
                                zzwz.zzS(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, false);
                                break;
                            case 21:
                                zzwz.zzaa(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, false);
                                break;
                            case 22:
                                zzwz.zzR(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, false);
                                break;
                            case 23:
                                zzwz.zzO(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, false);
                                break;
                            case 24:
                                zzwz.zzN(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, false);
                                break;
                            case 25:
                                zzwz.zzJ(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, false);
                                break;
                            case 26:
                                zzwz.zzY(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar);
                                break;
                            case 27:
                                zzwz.zzT(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, zzF(i));
                                break;
                            case 28:
                                zzwz.zzK(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar);
                                break;
                            case 29:
                                zzwz.zzZ(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, false);
                                break;
                            case 30:
                                zzwz.zzM(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, false);
                                break;
                            case 31:
                                zzwz.zzU(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, false);
                                break;
                            case 32:
                                zzwz.zzV(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, false);
                                break;
                            case 33:
                                zzwz.zzW(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, false);
                                break;
                            case 34:
                                zzwz.zzX(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, false);
                                break;
                            case 35:
                                zzwz.zzL(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, true);
                                break;
                            case 36:
                                zzwz.zzP(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, true);
                                break;
                            case 37:
                                zzwz.zzS(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, true);
                                break;
                            case 38:
                                zzwz.zzaa(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, true);
                                break;
                            case 39:
                                zzwz.zzR(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, true);
                                break;
                            case 40:
                                zzwz.zzO(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, true);
                                break;
                            case 41:
                                zzwz.zzN(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, true);
                                break;
                            case 42:
                                zzwz.zzJ(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, true);
                                break;
                            case 43:
                                zzwz.zzZ(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, true);
                                break;
                            case 44:
                                zzwz.zzM(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, true);
                                break;
                            case 45:
                                zzwz.zzU(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, true);
                                break;
                            case 46:
                                zzwz.zzV(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, true);
                                break;
                            case 47:
                                zzwz.zzW(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, true);
                                break;
                            case 48:
                                zzwz.zzX(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, true);
                                break;
                            case 49:
                                zzwz.zzQ(this.zzc[i], (List) zzxy.zzf(t, zzC & 1048575), zztpVar, zzF(i));
                                break;
                            case 50:
                                zzW(zztpVar, i2, zzxy.zzf(t, zzC & 1048575), i);
                                break;
                            case 51:
                                if (zzT(t, i2, i)) {
                                    zztpVar.zzf(i2, zzo(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 52:
                                if (zzT(t, i2, i)) {
                                    zztpVar.zzo(i2, zzp(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 53:
                                if (zzT(t, i2, i)) {
                                    zztpVar.zzt(i2, zzD(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 54:
                                if (zzT(t, i2, i)) {
                                    zztpVar.zzK(i2, zzD(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 55:
                                if (zzT(t, i2, i)) {
                                    zztpVar.zzr(i2, zzs(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 56:
                                if (zzT(t, i2, i)) {
                                    zztpVar.zzm(i2, zzD(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 57:
                                if (zzT(t, i2, i)) {
                                    zztpVar.zzk(i2, zzs(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 58:
                                if (zzT(t, i2, i)) {
                                    zztpVar.zzb(i2, zzU(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 59:
                                if (zzT(t, i2, i)) {
                                    zzX(i2, zzxy.zzf(t, zzC & 1048575), zztpVar);
                                    break;
                                } else {
                                    break;
                                }
                            case 60:
                                if (zzT(t, i2, i)) {
                                    zztpVar.zzv(i2, zzxy.zzf(t, zzC & 1048575), zzF(i));
                                    break;
                                } else {
                                    break;
                                }
                            case 61:
                                if (zzT(t, i2, i)) {
                                    zztpVar.zzd(i2, (zztd) zzxy.zzf(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 62:
                                if (zzT(t, i2, i)) {
                                    zztpVar.zzI(i2, zzs(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 63:
                                if (zzT(t, i2, i)) {
                                    zztpVar.zzi(i2, zzs(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 64:
                                if (zzT(t, i2, i)) {
                                    zztpVar.zzx(i2, zzs(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 65:
                                if (zzT(t, i2, i)) {
                                    zztpVar.zzz(i2, zzD(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 66:
                                if (zzT(t, i2, i)) {
                                    zztpVar.zzB(i2, zzs(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 67:
                                if (zzT(t, i2, i)) {
                                    zztpVar.zzD(i2, zzD(t, zzC & 1048575));
                                    break;
                                } else {
                                    break;
                                }
                            case 68:
                                if (zzT(t, i2, i)) {
                                    zztpVar.zzq(i2, zzxy.zzf(t, zzC & 1048575), zzF(i));
                                    break;
                                } else {
                                    break;
                                }
                        }
                    }
                    while (entry != null) {
                        this.zzp.zzj(zztpVar, entry);
                        entry = it.hasNext() ? (Map.Entry) it.next() : null;
                    }
                    zzxo<?, ?> zzxoVar = this.zzo;
                    zzxoVar.zzs(zzxoVar.zzd(t), zztpVar);
                    return;
                }
            }
            it = null;
            entry = null;
            length = this.zzc.length;
            while (i < length) {
            }
            while (entry != null) {
            }
            zzxo<?, ?> zzxoVar2 = this.zzo;
            zzxoVar2.zzs(zzxoVar2.zzd(t), zztpVar);
            return;
        }
        zzV(t, zztpVar);
    }

    @Override // com.google.android.gms.internal.gtm.zzwx
    public final void zzg(T t, T t2) {
        Objects.requireNonNull(t2);
        for (int i = 0; i < this.zzc.length; i += 3) {
            int zzC = zzC(i);
            long j = 1048575 & zzC;
            int i2 = this.zzc[i];
            switch (zzB(zzC)) {
                case 0:
                    if (zzQ(t2, i)) {
                        zzxy.zzo(t, j, zzxy.zza(t2, j));
                        zzM(t, i);
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (zzQ(t2, i)) {
                        zzxy.zzp(t, j, zzxy.zzb(t2, j));
                        zzM(t, i);
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (zzQ(t2, i)) {
                        zzxy.zzr(t, j, zzxy.zzd(t2, j));
                        zzM(t, i);
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (zzQ(t2, i)) {
                        zzxy.zzr(t, j, zzxy.zzd(t2, j));
                        zzM(t, i);
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (zzQ(t2, i)) {
                        zzxy.zzq(t, j, zzxy.zzc(t2, j));
                        zzM(t, i);
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (zzQ(t2, i)) {
                        zzxy.zzr(t, j, zzxy.zzd(t2, j));
                        zzM(t, i);
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (zzQ(t2, i)) {
                        zzxy.zzq(t, j, zzxy.zzc(t2, j));
                        zzM(t, i);
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (zzQ(t2, i)) {
                        zzxy.zzm(t, j, zzxy.zzw(t2, j));
                        zzM(t, i);
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (zzQ(t2, i)) {
                        zzxy.zzs(t, j, zzxy.zzf(t2, j));
                        zzM(t, i);
                        break;
                    } else {
                        break;
                    }
                case 9:
                    zzJ(t, t2, i);
                    break;
                case 10:
                    if (zzQ(t2, i)) {
                        zzxy.zzs(t, j, zzxy.zzf(t2, j));
                        zzM(t, i);
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (zzQ(t2, i)) {
                        zzxy.zzq(t, j, zzxy.zzc(t2, j));
                        zzM(t, i);
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (zzQ(t2, i)) {
                        zzxy.zzq(t, j, zzxy.zzc(t2, j));
                        zzM(t, i);
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (zzQ(t2, i)) {
                        zzxy.zzq(t, j, zzxy.zzc(t2, j));
                        zzM(t, i);
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (zzQ(t2, i)) {
                        zzxy.zzr(t, j, zzxy.zzd(t2, j));
                        zzM(t, i);
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (zzQ(t2, i)) {
                        zzxy.zzq(t, j, zzxy.zzc(t2, j));
                        zzM(t, i);
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (zzQ(t2, i)) {
                        zzxy.zzr(t, j, zzxy.zzd(t2, j));
                        zzM(t, i);
                        break;
                    } else {
                        break;
                    }
                case 17:
                    zzJ(t, t2, i);
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    this.zzn.zzc(t, t2, j);
                    break;
                case 50:
                    zzwz.zzI(this.zzr, t, t2, j);
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                    if (zzT(t2, i2, i)) {
                        zzxy.zzs(t, j, zzxy.zzf(t2, j));
                        zzN(t, i2, i);
                        break;
                    } else {
                        break;
                    }
                case 60:
                    zzK(t, t2, i);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                    if (zzT(t2, i2, i)) {
                        zzxy.zzs(t, j, zzxy.zzf(t2, j));
                        zzN(t, i2, i);
                        break;
                    } else {
                        break;
                    }
                case 68:
                    zzK(t, t2, i);
                    break;
            }
        }
        zzwz.zzF(this.zzo, t, t2);
        if (this.zzh) {
            zzwz.zzE(this.zzp, t, t2);
        }
    }
}
