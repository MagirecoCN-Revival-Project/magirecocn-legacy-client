package com.google.android.gms.internal.play_billing;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.RandomAccess;
import sun.misc.Unsafe;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.android.billingclient:billing@@5.2.1 */
/* loaded from: classes.dex */
public final class zzdi<T> implements zzdp<T> {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zzeq.zzg();
    private final int[] zzc;
    private final Object[] zzd;
    private final int zze;
    private final int zzf;
    private final zzdf zzg;
    private final boolean zzh;
    private final boolean zzi;
    private final int[] zzj;
    private final int zzk;
    private final int zzl;
    private final zzct zzm;
    private final zzeg zzn;
    private final zzbo zzo;
    private final zzdk zzp;
    private final zzda zzq;

    private zzdi(int[] iArr, Object[] objArr, int i, int i2, zzdf zzdfVar, boolean z, boolean z2, int[] iArr2, int i3, int i4, zzdk zzdkVar, zzct zzctVar, zzeg zzegVar, zzbo zzboVar, zzda zzdaVar) {
        this.zzc = iArr;
        this.zzd = objArr;
        this.zze = i;
        this.zzf = i2;
        this.zzi = z;
        boolean z3 = false;
        if (zzboVar != null && zzboVar.zzc(zzdfVar)) {
            z3 = true;
        }
        this.zzh = z3;
        this.zzj = iArr2;
        this.zzk = i3;
        this.zzl = i4;
        this.zzp = zzdkVar;
        this.zzm = zzctVar;
        this.zzn = zzegVar;
        this.zzo = zzboVar;
        this.zzg = zzdfVar;
        this.zzq = zzdaVar;
    }

    private final zzce zzA(int i) {
        int i2 = i / 3;
        return (zzce) this.zzd[i2 + i2 + 1];
    }

    private final zzdp zzB(int i) {
        int i2 = i / 3;
        int i3 = i2 + i2;
        zzdp zzdpVar = (zzdp) this.zzd[i3];
        if (zzdpVar != null) {
            return zzdpVar;
        }
        zzdp zzb2 = zzdn.zza().zzb((Class) this.zzd[i3 + 1]);
        this.zzd[i3] = zzb2;
        return zzb2;
    }

    private final Object zzC(int i) {
        int i2 = i / 3;
        return this.zzd[i2 + i2];
    }

    private final Object zzD(Object obj, int i) {
        zzdp zzB = zzB(i);
        int zzy = zzy(i) & 1048575;
        if (!zzP(obj, i)) {
            return zzB.zze();
        }
        Object object = zzb.getObject(obj, zzy);
        if (zzS(object)) {
            return object;
        }
        Object zze = zzB.zze();
        if (object != null) {
            zzB.zzg(zze, object);
        }
        return zze;
    }

    private final Object zzE(Object obj, int i, int i2) {
        zzdp zzB = zzB(i2);
        if (!zzT(obj, i, i2)) {
            return zzB.zze();
        }
        Object object = zzb.getObject(obj, zzy(i2) & 1048575);
        if (zzS(object)) {
            return object;
        }
        Object zze = zzB.zze();
        if (object != null) {
            zzB.zzg(zze, object);
        }
        return zze;
    }

    private static Field zzF(Class cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            throw new RuntimeException("Field " + str + " for " + cls.getName() + " not found. Known fields are " + Arrays.toString(declaredFields));
        }
    }

    private static void zzG(Object obj) {
        if (!zzS(obj)) {
            throw new IllegalArgumentException("Mutating immutable message: ".concat(String.valueOf(String.valueOf(obj))));
        }
    }

    private final void zzH(Object obj, Object obj2, int i) {
        if (zzP(obj2, i)) {
            int zzy = zzy(i) & 1048575;
            Unsafe unsafe = zzb;
            long j = zzy;
            Object object = unsafe.getObject(obj2, j);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + this.zzc[i] + " is present but null: " + obj2.toString());
            }
            zzdp zzB = zzB(i);
            if (!zzP(obj, i)) {
                if (!zzS(object)) {
                    unsafe.putObject(obj, j, object);
                } else {
                    Object zze = zzB.zze();
                    zzB.zzg(zze, object);
                    unsafe.putObject(obj, j, zze);
                }
                zzJ(obj, i);
                return;
            }
            Object object2 = unsafe.getObject(obj, j);
            if (!zzS(object2)) {
                Object zze2 = zzB.zze();
                zzB.zzg(zze2, object2);
                unsafe.putObject(obj, j, zze2);
                object2 = zze2;
            }
            zzB.zzg(object2, object);
        }
    }

    private final void zzI(Object obj, Object obj2, int i) {
        int i2 = this.zzc[i];
        if (zzT(obj2, i2, i)) {
            int zzy = zzy(i) & 1048575;
            Unsafe unsafe = zzb;
            long j = zzy;
            Object object = unsafe.getObject(obj2, j);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + this.zzc[i] + " is present but null: " + obj2.toString());
            }
            zzdp zzB = zzB(i);
            if (!zzT(obj, i2, i)) {
                if (!zzS(object)) {
                    unsafe.putObject(obj, j, object);
                } else {
                    Object zze = zzB.zze();
                    zzB.zzg(zze, object);
                    unsafe.putObject(obj, j, zze);
                }
                zzK(obj, i2, i);
                return;
            }
            Object object2 = unsafe.getObject(obj, j);
            if (!zzS(object2)) {
                Object zze2 = zzB.zze();
                zzB.zzg(zze2, object2);
                unsafe.putObject(obj, j, zze2);
                object2 = zze2;
            }
            zzB.zzg(object2, object);
        }
    }

    private final void zzJ(Object obj, int i) {
        int zzv = zzv(i);
        long j = 1048575 & zzv;
        if (j == 1048575) {
            return;
        }
        zzeq.zzq(obj, j, (1 << (zzv >>> 20)) | zzeq.zzc(obj, j));
    }

    private final void zzK(Object obj, int i, int i2) {
        zzeq.zzq(obj, zzv(i2) & 1048575, i);
    }

    private final void zzL(Object obj, int i, Object obj2) {
        zzb.putObject(obj, zzy(i) & 1048575, obj2);
        zzJ(obj, i);
    }

    private final void zzM(Object obj, int i, int i2, Object obj2) {
        zzb.putObject(obj, zzy(i2) & 1048575, obj2);
        zzK(obj, i, i2);
    }

    private final void zzN(zzey zzeyVar, int i, Object obj, int i2) throws IOException {
        if (obj == null) {
            return;
        }
        throw null;
    }

    private final boolean zzO(Object obj, Object obj2, int i) {
        return zzP(obj, i) == zzP(obj2, i);
    }

    private final boolean zzP(Object obj, int i) {
        int zzv = zzv(i);
        long j = zzv & 1048575;
        if (j != 1048575) {
            return (zzeq.zzc(obj, j) & (1 << (zzv >>> 20))) != 0;
        }
        int zzy = zzy(i);
        long j2 = zzy & 1048575;
        switch (zzx(zzy)) {
            case 0:
                return Double.doubleToRawLongBits(zzeq.zza(obj, j2)) != 0;
            case 1:
                return Float.floatToRawIntBits(zzeq.zzb(obj, j2)) != 0;
            case 2:
                return zzeq.zzd(obj, j2) != 0;
            case 3:
                return zzeq.zzd(obj, j2) != 0;
            case 4:
                return zzeq.zzc(obj, j2) != 0;
            case 5:
                return zzeq.zzd(obj, j2) != 0;
            case 6:
                return zzeq.zzc(obj, j2) != 0;
            case 7:
                return zzeq.zzw(obj, j2);
            case 8:
                Object zzf = zzeq.zzf(obj, j2);
                if (zzf instanceof String) {
                    return !((String) zzf).isEmpty();
                }
                if (zzf instanceof zzba) {
                    return !zzba.zzb.equals(zzf);
                }
                throw new IllegalArgumentException();
            case 9:
                return zzeq.zzf(obj, j2) != null;
            case 10:
                return !zzba.zzb.equals(zzeq.zzf(obj, j2));
            case 11:
                return zzeq.zzc(obj, j2) != 0;
            case 12:
                return zzeq.zzc(obj, j2) != 0;
            case 13:
                return zzeq.zzc(obj, j2) != 0;
            case 14:
                return zzeq.zzd(obj, j2) != 0;
            case 15:
                return zzeq.zzc(obj, j2) != 0;
            case 16:
                return zzeq.zzd(obj, j2) != 0;
            case 17:
                return zzeq.zzf(obj, j2) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    private final boolean zzQ(Object obj, int i, int i2, int i3, int i4) {
        if (i2 == 1048575) {
            return zzP(obj, i);
        }
        return (i3 & i4) != 0;
    }

    private static boolean zzR(Object obj, int i, zzdp zzdpVar) {
        return zzdpVar.zzk(zzeq.zzf(obj, i & 1048575));
    }

    private static boolean zzS(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof zzcb) {
            return ((zzcb) obj).zzs();
        }
        return true;
    }

    private final boolean zzT(Object obj, int i, int i2) {
        return zzeq.zzc(obj, (long) (zzv(i2) & 1048575)) == i;
    }

    private static boolean zzU(Object obj, long j) {
        return ((Boolean) zzeq.zzf(obj, j)).booleanValue();
    }

    private static final void zzV(int i, Object obj, zzey zzeyVar) throws IOException {
        if (obj instanceof String) {
            zzeyVar.zzF(i, (String) obj);
        } else {
            zzeyVar.zzd(i, (zzba) obj);
        }
    }

    static zzeh zzd(Object obj) {
        zzcb zzcbVar = (zzcb) obj;
        zzeh zzehVar = zzcbVar.zzc;
        if (zzehVar != zzeh.zzc()) {
            return zzehVar;
        }
        zzeh zzf = zzeh.zzf();
        zzcbVar.zzc = zzf;
        return zzf;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:65:0x026c  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0284  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0287  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x026f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static zzdi zzl(Class cls, zzdc zzdcVar, zzdk zzdkVar, zzct zzctVar, zzeg zzegVar, zzbo zzboVar, zzda zzdaVar) {
        int i;
        int charAt;
        int charAt2;
        int[] iArr;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        char charAt3;
        int i7;
        char charAt4;
        int i8;
        char charAt5;
        int i9;
        char charAt6;
        int i10;
        char charAt7;
        int i11;
        char charAt8;
        int i12;
        char charAt9;
        int i13;
        char charAt10;
        int i14;
        int i15;
        int i16;
        Object[] objArr;
        int objectFieldOffset;
        int i17;
        int i18;
        int i19;
        int i20;
        Field zzF;
        char charAt11;
        int i21;
        int i22;
        int i23;
        Object obj;
        Field zzF2;
        Object obj2;
        Field zzF3;
        int i24;
        char charAt12;
        int i25;
        char charAt13;
        int i26;
        char charAt14;
        int i27;
        char charAt15;
        if (zzdcVar instanceof zzdo) {
            zzdo zzdoVar = (zzdo) zzdcVar;
            int zzc = zzdoVar.zzc();
            String zzd = zzdoVar.zzd();
            int length = zzd.length();
            int i28 = 0;
            int i29 = 55296;
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
            int charAt16 = zzd.charAt(i);
            if (charAt16 >= 55296) {
                int i32 = charAt16 & 8191;
                int i33 = 13;
                while (true) {
                    i27 = i31 + 1;
                    charAt15 = zzd.charAt(i31);
                    if (charAt15 < 55296) {
                        break;
                    }
                    i32 |= (charAt15 & 8191) << i33;
                    i33 += 13;
                    i31 = i27;
                }
                charAt16 = i32 | (charAt15 << i33);
                i31 = i27;
            }
            if (charAt16 == 0) {
                iArr = zza;
                i2 = 0;
                i3 = 0;
                charAt = 0;
                charAt2 = 0;
                i5 = 0;
                i4 = 0;
            } else {
                int i34 = i31 + 1;
                int charAt17 = zzd.charAt(i31);
                if (charAt17 >= 55296) {
                    int i35 = charAt17 & 8191;
                    int i36 = 13;
                    while (true) {
                        i13 = i34 + 1;
                        charAt10 = zzd.charAt(i34);
                        if (charAt10 < 55296) {
                            break;
                        }
                        i35 |= (charAt10 & 8191) << i36;
                        i36 += 13;
                        i34 = i13;
                    }
                    charAt17 = i35 | (charAt10 << i36);
                    i34 = i13;
                }
                int i37 = i34 + 1;
                int charAt18 = zzd.charAt(i34);
                if (charAt18 >= 55296) {
                    int i38 = charAt18 & 8191;
                    int i39 = 13;
                    while (true) {
                        i12 = i37 + 1;
                        charAt9 = zzd.charAt(i37);
                        if (charAt9 < 55296) {
                            break;
                        }
                        i38 |= (charAt9 & 8191) << i39;
                        i39 += 13;
                        i37 = i12;
                    }
                    charAt18 = i38 | (charAt9 << i39);
                    i37 = i12;
                }
                int i40 = i37 + 1;
                int charAt19 = zzd.charAt(i37);
                if (charAt19 >= 55296) {
                    int i41 = charAt19 & 8191;
                    int i42 = 13;
                    while (true) {
                        i11 = i40 + 1;
                        charAt8 = zzd.charAt(i40);
                        if (charAt8 < 55296) {
                            break;
                        }
                        i41 |= (charAt8 & 8191) << i42;
                        i42 += 13;
                        i40 = i11;
                    }
                    charAt19 = i41 | (charAt8 << i42);
                    i40 = i11;
                }
                int i43 = i40 + 1;
                int charAt20 = zzd.charAt(i40);
                if (charAt20 >= 55296) {
                    int i44 = charAt20 & 8191;
                    int i45 = 13;
                    while (true) {
                        i10 = i43 + 1;
                        charAt7 = zzd.charAt(i43);
                        if (charAt7 < 55296) {
                            break;
                        }
                        i44 |= (charAt7 & 8191) << i45;
                        i45 += 13;
                        i43 = i10;
                    }
                    charAt20 = i44 | (charAt7 << i45);
                    i43 = i10;
                }
                int i46 = i43 + 1;
                charAt = zzd.charAt(i43);
                if (charAt >= 55296) {
                    int i47 = charAt & 8191;
                    int i48 = 13;
                    while (true) {
                        i9 = i46 + 1;
                        charAt6 = zzd.charAt(i46);
                        if (charAt6 < 55296) {
                            break;
                        }
                        i47 |= (charAt6 & 8191) << i48;
                        i48 += 13;
                        i46 = i9;
                    }
                    charAt = i47 | (charAt6 << i48);
                    i46 = i9;
                }
                int i49 = i46 + 1;
                charAt2 = zzd.charAt(i46);
                if (charAt2 >= 55296) {
                    int i50 = charAt2 & 8191;
                    int i51 = 13;
                    while (true) {
                        i8 = i49 + 1;
                        charAt5 = zzd.charAt(i49);
                        if (charAt5 < 55296) {
                            break;
                        }
                        i50 |= (charAt5 & 8191) << i51;
                        i51 += 13;
                        i49 = i8;
                    }
                    charAt2 = i50 | (charAt5 << i51);
                    i49 = i8;
                }
                int i52 = i49 + 1;
                int charAt21 = zzd.charAt(i49);
                if (charAt21 >= 55296) {
                    int i53 = charAt21 & 8191;
                    int i54 = 13;
                    while (true) {
                        i7 = i52 + 1;
                        charAt4 = zzd.charAt(i52);
                        if (charAt4 < 55296) {
                            break;
                        }
                        i53 |= (charAt4 & 8191) << i54;
                        i54 += 13;
                        i52 = i7;
                    }
                    charAt21 = i53 | (charAt4 << i54);
                    i52 = i7;
                }
                int i55 = i52 + 1;
                int charAt22 = zzd.charAt(i52);
                if (charAt22 >= 55296) {
                    int i56 = charAt22 & 8191;
                    int i57 = i55;
                    int i58 = 13;
                    while (true) {
                        i6 = i57 + 1;
                        charAt3 = zzd.charAt(i57);
                        if (charAt3 < 55296) {
                            break;
                        }
                        i56 |= (charAt3 & 8191) << i58;
                        i58 += 13;
                        i57 = i6;
                    }
                    charAt22 = i56 | (charAt3 << i58);
                    i55 = i6;
                }
                int i59 = charAt22 + charAt2 + charAt21;
                int i60 = charAt17 + charAt17 + charAt18;
                int[] iArr2 = new int[i59];
                i28 = charAt17;
                iArr = iArr2;
                i2 = charAt19;
                i3 = i60;
                i4 = charAt22;
                i31 = i55;
                i5 = charAt20;
            }
            Unsafe unsafe = zzb;
            Object[] zze = zzdoVar.zze();
            Class<?> cls2 = zzdoVar.zza().getClass();
            int i61 = i4 + charAt2;
            int i62 = charAt + charAt;
            int[] iArr3 = new int[charAt * 3];
            Object[] objArr2 = new Object[i62];
            int i63 = i4;
            int i64 = i61;
            int i65 = 0;
            int i66 = 0;
            while (true) {
                boolean z = zzc == 2;
                if (i31 < length) {
                    int i67 = i31 + 1;
                    int charAt23 = zzd.charAt(i31);
                    if (charAt23 >= i29) {
                        int i68 = charAt23 & 8191;
                        int i69 = i67;
                        int i70 = 13;
                        while (true) {
                            i26 = i69 + 1;
                            charAt14 = zzd.charAt(i69);
                            i14 = zzc;
                            if (charAt14 < 55296) {
                                break;
                            }
                            i68 |= (charAt14 & 8191) << i70;
                            i70 += 13;
                            i69 = i26;
                            zzc = i14;
                        }
                        charAt23 = i68 | (charAt14 << i70);
                        i15 = i26;
                    } else {
                        i14 = zzc;
                        i15 = i67;
                    }
                    int i71 = i15 + 1;
                    int charAt24 = zzd.charAt(i15);
                    int i72 = length;
                    char c = 55296;
                    if (charAt24 >= 55296) {
                        int i73 = charAt24 & 8191;
                        int i74 = 13;
                        while (true) {
                            i25 = i71 + 1;
                            charAt13 = zzd.charAt(i71);
                            if (charAt13 < c) {
                                break;
                            }
                            i73 |= (charAt13 & 8191) << i74;
                            i74 += 13;
                            i71 = i25;
                            c = 55296;
                        }
                        charAt24 = i73 | (charAt13 << i74);
                        i71 = i25;
                    }
                    if ((charAt24 & 1024) != 0) {
                        iArr[i65] = i66;
                        i65++;
                    }
                    int i75 = charAt24 & 255;
                    int i76 = i5;
                    if (i75 >= 51) {
                        int i77 = i71 + 1;
                        int charAt25 = zzd.charAt(i71);
                        if (charAt25 >= 55296) {
                            int i78 = charAt25 & 8191;
                            int i79 = i77;
                            int i80 = 13;
                            while (true) {
                                i24 = i79 + 1;
                                charAt12 = zzd.charAt(i79);
                                i16 = i2;
                                if (charAt12 < 55296) {
                                    break;
                                }
                                i78 |= (charAt12 & 8191) << i80;
                                i80 += 13;
                                i79 = i24;
                                i2 = i16;
                            }
                            charAt25 = i78 | (charAt12 << i80);
                            i22 = i24;
                        } else {
                            i16 = i2;
                            i22 = i77;
                        }
                        int i81 = i75 - 51;
                        int i82 = i22;
                        if (i81 == 9 || i81 == 17) {
                            int i83 = i66 / 3;
                            i23 = i3 + 1;
                            objArr2[i83 + i83 + 1] = zze[i3];
                        } else {
                            if (i81 == 12 && !z) {
                                int i84 = i66 / 3;
                                i23 = i3 + 1;
                                objArr2[i84 + i84 + 1] = zze[i3];
                            }
                            int i85 = charAt25 + charAt25;
                            obj = zze[i85];
                            if (!(obj instanceof Field)) {
                                zzF2 = (Field) obj;
                            } else {
                                zzF2 = zzF(cls2, (String) obj);
                                zze[i85] = zzF2;
                            }
                            i19 = (int) unsafe.objectFieldOffset(zzF2);
                            int i86 = i85 + 1;
                            obj2 = zze[i86];
                            if (!(obj2 instanceof Field)) {
                                zzF3 = (Field) obj2;
                            } else {
                                zzF3 = zzF(cls2, (String) obj2);
                                zze[i86] = zzF3;
                            }
                            i20 = (int) unsafe.objectFieldOffset(zzF3);
                            objArr = zze;
                            i17 = i82;
                            i18 = 0;
                        }
                        i3 = i23;
                        int i852 = charAt25 + charAt25;
                        obj = zze[i852];
                        if (!(obj instanceof Field)) {
                        }
                        i19 = (int) unsafe.objectFieldOffset(zzF2);
                        int i862 = i852 + 1;
                        obj2 = zze[i862];
                        if (!(obj2 instanceof Field)) {
                        }
                        i20 = (int) unsafe.objectFieldOffset(zzF3);
                        objArr = zze;
                        i17 = i82;
                        i18 = 0;
                    } else {
                        i16 = i2;
                        int i87 = i3 + 1;
                        Field zzF4 = zzF(cls2, (String) zze[i3]);
                        if (i75 == 9 || i75 == 17) {
                            int i88 = i66 / 3;
                            objArr2[i88 + i88 + 1] = zzF4.getType();
                        } else {
                            if (i75 == 27 || i75 == 49) {
                                int i89 = i66 / 3;
                                i21 = i87 + 1;
                                objArr2[i89 + i89 + 1] = zze[i87];
                            } else if (i75 == 12 || i75 == 30 || i75 == 44) {
                                if (!z) {
                                    int i90 = i66 / 3;
                                    i21 = i87 + 1;
                                    objArr2[i90 + i90 + 1] = zze[i87];
                                }
                            } else if (i75 == 50) {
                                int i91 = i63 + 1;
                                iArr[i63] = i66;
                                int i92 = i66 / 3;
                                int i93 = i87 + 1;
                                int i94 = i92 + i92;
                                objArr2[i94] = zze[i87];
                                if ((charAt24 & 2048) != 0) {
                                    i87 = i93 + 1;
                                    objArr2[i94 + 1] = zze[i93];
                                    i63 = i91;
                                } else {
                                    objArr = zze;
                                    i87 = i93;
                                    i63 = i91;
                                    objectFieldOffset = (int) unsafe.objectFieldOffset(zzF4);
                                    int i95 = 1048575;
                                    if ((charAt24 & 4096) == 4096 || i75 > 17) {
                                        i17 = i71;
                                        i18 = 0;
                                    } else {
                                        int i96 = i71 + 1;
                                        int charAt26 = zzd.charAt(i71);
                                        if (charAt26 >= 55296) {
                                            int i97 = charAt26 & 8191;
                                            int i98 = 13;
                                            while (true) {
                                                i17 = i96 + 1;
                                                charAt11 = zzd.charAt(i96);
                                                if (charAt11 < 55296) {
                                                    break;
                                                }
                                                i97 |= (charAt11 & 8191) << i98;
                                                i98 += 13;
                                                i96 = i17;
                                            }
                                            charAt26 = i97 | (charAt11 << i98);
                                        } else {
                                            i17 = i96;
                                        }
                                        int i99 = i28 + i28 + (charAt26 / 32);
                                        Object obj3 = objArr[i99];
                                        if (obj3 instanceof Field) {
                                            zzF = (Field) obj3;
                                        } else {
                                            zzF = zzF(cls2, (String) obj3);
                                            objArr[i99] = zzF;
                                        }
                                        i18 = charAt26 % 32;
                                        i95 = (int) unsafe.objectFieldOffset(zzF);
                                    }
                                    if (i75 >= 18 && i75 <= 49) {
                                        iArr[i64] = objectFieldOffset;
                                        i64++;
                                    }
                                    i3 = i87;
                                    i19 = objectFieldOffset;
                                    i20 = i95;
                                }
                            }
                            i87 = i21;
                        }
                        objArr = zze;
                        objectFieldOffset = (int) unsafe.objectFieldOffset(zzF4);
                        int i952 = 1048575;
                        if ((charAt24 & 4096) == 4096) {
                        }
                        i17 = i71;
                        i18 = 0;
                        if (i75 >= 18) {
                            iArr[i64] = objectFieldOffset;
                            i64++;
                        }
                        i3 = i87;
                        i19 = objectFieldOffset;
                        i20 = i952;
                    }
                    int i100 = i66 + 1;
                    iArr3[i66] = charAt23;
                    int i101 = i100 + 1;
                    iArr3[i100] = ((charAt24 & 256) != 0 ? 268435456 : 0) | ((charAt24 & 512) != 0 ? 536870912 : 0) | (i75 << 20) | i19;
                    i66 = i101 + 1;
                    iArr3[i101] = (i18 << 20) | i20;
                    zze = objArr;
                    length = i72;
                    i31 = i17;
                    i5 = i76;
                    zzc = i14;
                    i2 = i16;
                    i29 = 55296;
                } else {
                    return new zzdi(iArr3, objArr2, i2, i5, zzdoVar.zza(), z, false, iArr, i4, i61, zzdkVar, zzctVar, zzegVar, zzboVar, zzdaVar);
                }
            }
        } else {
            throw null;
        }
    }

    private static double zzm(Object obj, long j) {
        return ((Double) zzeq.zzf(obj, j)).doubleValue();
    }

    private static float zzn(Object obj, long j) {
        return ((Float) zzeq.zzf(obj, j)).floatValue();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:11:0x003d. Please report as an issue. */
    private final int zzo(Object obj) {
        int i;
        int zzx;
        int zzx2;
        int zzy;
        int zzx3;
        int zzx4;
        int zzx5;
        int zzx6;
        int zzt;
        int zzh;
        int zzx7;
        int zzx8;
        int i2;
        int zzx9;
        int zzx10;
        int zzx11;
        int zzx12;
        Unsafe unsafe = zzb;
        int i3 = 1048575;
        int i4 = 0;
        int i5 = 0;
        int i6 = 1048575;
        int i7 = 0;
        while (i4 < this.zzc.length) {
            int zzy2 = zzy(i4);
            int[] iArr = this.zzc;
            int i8 = iArr[i4];
            int zzx13 = zzx(zzy2);
            if (zzx13 <= 17) {
                int i9 = iArr[i4 + 2];
                int i10 = i9 & i3;
                int i11 = i9 >>> 20;
                if (i10 != i6) {
                    i7 = unsafe.getInt(obj, i10);
                    i6 = i10;
                }
                i = 1 << i11;
            } else {
                i = 0;
            }
            long j = zzy2 & i3;
            switch (zzx13) {
                case 0:
                    if ((i7 & i) == 0) {
                        break;
                    } else {
                        zzx = zzbi.zzx(i8 << 3);
                        zzx4 = zzx + 8;
                        i5 += zzx4;
                        break;
                    }
                case 1:
                    if ((i7 & i) == 0) {
                        break;
                    } else {
                        zzx2 = zzbi.zzx(i8 << 3);
                        zzx4 = zzx2 + 4;
                        i5 += zzx4;
                        break;
                    }
                case 2:
                    if ((i7 & i) == 0) {
                        break;
                    } else {
                        zzy = zzbi.zzy(unsafe.getLong(obj, j));
                        zzx3 = zzbi.zzx(i8 << 3);
                        i5 += zzx3 + zzy;
                        break;
                    }
                case 3:
                    if ((i7 & i) == 0) {
                        break;
                    } else {
                        zzy = zzbi.zzy(unsafe.getLong(obj, j));
                        zzx3 = zzbi.zzx(i8 << 3);
                        i5 += zzx3 + zzy;
                        break;
                    }
                case 4:
                    if ((i7 & i) == 0) {
                        break;
                    } else {
                        zzy = zzbi.zzu(unsafe.getInt(obj, j));
                        zzx3 = zzbi.zzx(i8 << 3);
                        i5 += zzx3 + zzy;
                        break;
                    }
                case 5:
                    if ((i7 & i) == 0) {
                        break;
                    } else {
                        zzx = zzbi.zzx(i8 << 3);
                        zzx4 = zzx + 8;
                        i5 += zzx4;
                        break;
                    }
                case 6:
                    if ((i7 & i) == 0) {
                        break;
                    } else {
                        zzx2 = zzbi.zzx(i8 << 3);
                        zzx4 = zzx2 + 4;
                        i5 += zzx4;
                        break;
                    }
                case 7:
                    if ((i7 & i) == 0) {
                        break;
                    } else {
                        zzx4 = zzbi.zzx(i8 << 3) + 1;
                        i5 += zzx4;
                        break;
                    }
                case 8:
                    if ((i7 & i) == 0) {
                        break;
                    } else {
                        Object object = unsafe.getObject(obj, j);
                        if (object instanceof zzba) {
                            int i12 = zzbi.zzb;
                            int zzd = ((zzba) object).zzd();
                            zzx5 = zzbi.zzx(zzd) + zzd;
                            zzx6 = zzbi.zzx(i8 << 3);
                            zzx4 = zzx6 + zzx5;
                            i5 += zzx4;
                            break;
                        } else {
                            zzy = zzbi.zzw((String) object);
                            zzx3 = zzbi.zzx(i8 << 3);
                            i5 += zzx3 + zzy;
                            break;
                        }
                    }
                case 9:
                    if ((i7 & i) == 0) {
                        break;
                    } else {
                        zzx4 = zzdr.zzn(i8, unsafe.getObject(obj, j), zzB(i4));
                        i5 += zzx4;
                        break;
                    }
                case 10:
                    if ((i7 & i) == 0) {
                        break;
                    } else {
                        zzba zzbaVar = (zzba) unsafe.getObject(obj, j);
                        int i13 = zzbi.zzb;
                        int zzd2 = zzbaVar.zzd();
                        zzx5 = zzbi.zzx(zzd2) + zzd2;
                        zzx6 = zzbi.zzx(i8 << 3);
                        zzx4 = zzx6 + zzx5;
                        i5 += zzx4;
                        break;
                    }
                case 11:
                    if ((i7 & i) == 0) {
                        break;
                    } else {
                        zzy = zzbi.zzx(unsafe.getInt(obj, j));
                        zzx3 = zzbi.zzx(i8 << 3);
                        i5 += zzx3 + zzy;
                        break;
                    }
                case 12:
                    if ((i7 & i) == 0) {
                        break;
                    } else {
                        zzy = zzbi.zzu(unsafe.getInt(obj, j));
                        zzx3 = zzbi.zzx(i8 << 3);
                        i5 += zzx3 + zzy;
                        break;
                    }
                case 13:
                    if ((i7 & i) == 0) {
                        break;
                    } else {
                        zzx2 = zzbi.zzx(i8 << 3);
                        zzx4 = zzx2 + 4;
                        i5 += zzx4;
                        break;
                    }
                case 14:
                    if ((i7 & i) == 0) {
                        break;
                    } else {
                        zzx = zzbi.zzx(i8 << 3);
                        zzx4 = zzx + 8;
                        i5 += zzx4;
                        break;
                    }
                case 15:
                    if ((i7 & i) == 0) {
                        break;
                    } else {
                        int i14 = unsafe.getInt(obj, j);
                        zzx3 = zzbi.zzx(i8 << 3);
                        zzy = zzbi.zzx((i14 >> 31) ^ (i14 + i14));
                        i5 += zzx3 + zzy;
                        break;
                    }
                case 16:
                    if ((i & i7) == 0) {
                        break;
                    } else {
                        long j2 = unsafe.getLong(obj, j);
                        i5 += zzbi.zzx(i8 << 3) + zzbi.zzy((j2 >> 63) ^ (j2 + j2));
                        break;
                    }
                case 17:
                    if ((i7 & i) == 0) {
                        break;
                    } else {
                        zzx4 = zzbi.zzt(i8, (zzdf) unsafe.getObject(obj, j), zzB(i4));
                        i5 += zzx4;
                        break;
                    }
                case 18:
                    zzx4 = zzdr.zzg(i8, (List) unsafe.getObject(obj, j), false);
                    i5 += zzx4;
                    break;
                case 19:
                    zzx4 = zzdr.zze(i8, (List) unsafe.getObject(obj, j), false);
                    i5 += zzx4;
                    break;
                case 20:
                    zzx4 = zzdr.zzl(i8, (List) unsafe.getObject(obj, j), false);
                    i5 += zzx4;
                    break;
                case 21:
                    zzx4 = zzdr.zzw(i8, (List) unsafe.getObject(obj, j), false);
                    i5 += zzx4;
                    break;
                case 22:
                    zzx4 = zzdr.zzj(i8, (List) unsafe.getObject(obj, j), false);
                    i5 += zzx4;
                    break;
                case 23:
                    zzx4 = zzdr.zzg(i8, (List) unsafe.getObject(obj, j), false);
                    i5 += zzx4;
                    break;
                case 24:
                    zzx4 = zzdr.zze(i8, (List) unsafe.getObject(obj, j), false);
                    i5 += zzx4;
                    break;
                case 25:
                    zzx4 = zzdr.zza(i8, (List) unsafe.getObject(obj, j), false);
                    i5 += zzx4;
                    break;
                case 26:
                    zzt = zzdr.zzt(i8, (List) unsafe.getObject(obj, j));
                    i5 += zzt;
                    break;
                case 27:
                    zzt = zzdr.zzo(i8, (List) unsafe.getObject(obj, j), zzB(i4));
                    i5 += zzt;
                    break;
                case 28:
                    zzt = zzdr.zzb(i8, (List) unsafe.getObject(obj, j));
                    i5 += zzt;
                    break;
                case 29:
                    zzt = zzdr.zzu(i8, (List) unsafe.getObject(obj, j), false);
                    i5 += zzt;
                    break;
                case 30:
                    zzt = zzdr.zzc(i8, (List) unsafe.getObject(obj, j), false);
                    i5 += zzt;
                    break;
                case 31:
                    zzt = zzdr.zze(i8, (List) unsafe.getObject(obj, j), false);
                    i5 += zzt;
                    break;
                case 32:
                    zzt = zzdr.zzg(i8, (List) unsafe.getObject(obj, j), false);
                    i5 += zzt;
                    break;
                case 33:
                    zzt = zzdr.zzp(i8, (List) unsafe.getObject(obj, j), false);
                    i5 += zzt;
                    break;
                case 34:
                    zzt = zzdr.zzr(i8, (List) unsafe.getObject(obj, j), false);
                    i5 += zzt;
                    break;
                case 35:
                    zzh = zzdr.zzh((List) unsafe.getObject(obj, j));
                    if (zzh > 0) {
                        zzx7 = zzbi.zzx(zzh);
                        zzx8 = zzbi.zzx(i8 << 3);
                        i2 = zzx8 + zzx7;
                        i5 += i2 + zzh;
                    }
                    break;
                case 36:
                    zzh = zzdr.zzf((List) unsafe.getObject(obj, j));
                    if (zzh > 0) {
                        zzx7 = zzbi.zzx(zzh);
                        zzx8 = zzbi.zzx(i8 << 3);
                        i2 = zzx8 + zzx7;
                        i5 += i2 + zzh;
                    }
                    break;
                case 37:
                    zzh = zzdr.zzm((List) unsafe.getObject(obj, j));
                    if (zzh > 0) {
                        zzx7 = zzbi.zzx(zzh);
                        zzx8 = zzbi.zzx(i8 << 3);
                        i2 = zzx8 + zzx7;
                        i5 += i2 + zzh;
                    }
                    break;
                case 38:
                    zzh = zzdr.zzx((List) unsafe.getObject(obj, j));
                    if (zzh > 0) {
                        zzx7 = zzbi.zzx(zzh);
                        zzx8 = zzbi.zzx(i8 << 3);
                        i2 = zzx8 + zzx7;
                        i5 += i2 + zzh;
                    }
                    break;
                case 39:
                    zzh = zzdr.zzk((List) unsafe.getObject(obj, j));
                    if (zzh > 0) {
                        zzx7 = zzbi.zzx(zzh);
                        zzx8 = zzbi.zzx(i8 << 3);
                        i2 = zzx8 + zzx7;
                        i5 += i2 + zzh;
                    }
                    break;
                case 40:
                    zzh = zzdr.zzh((List) unsafe.getObject(obj, j));
                    if (zzh > 0) {
                        zzx7 = zzbi.zzx(zzh);
                        zzx8 = zzbi.zzx(i8 << 3);
                        i2 = zzx8 + zzx7;
                        i5 += i2 + zzh;
                    }
                    break;
                case 41:
                    zzh = zzdr.zzf((List) unsafe.getObject(obj, j));
                    if (zzh > 0) {
                        zzx7 = zzbi.zzx(zzh);
                        zzx8 = zzbi.zzx(i8 << 3);
                        i2 = zzx8 + zzx7;
                        i5 += i2 + zzh;
                    }
                    break;
                case 42:
                    List list = (List) unsafe.getObject(obj, j);
                    int i15 = zzdr.zza;
                    zzh = list.size();
                    if (zzh > 0) {
                        zzx7 = zzbi.zzx(zzh);
                        zzx8 = zzbi.zzx(i8 << 3);
                        i2 = zzx8 + zzx7;
                        i5 += i2 + zzh;
                    }
                    break;
                case 43:
                    zzh = zzdr.zzv((List) unsafe.getObject(obj, j));
                    if (zzh > 0) {
                        zzx7 = zzbi.zzx(zzh);
                        zzx8 = zzbi.zzx(i8 << 3);
                        i2 = zzx8 + zzx7;
                        i5 += i2 + zzh;
                    }
                    break;
                case 44:
                    zzh = zzdr.zzd((List) unsafe.getObject(obj, j));
                    if (zzh > 0) {
                        zzx7 = zzbi.zzx(zzh);
                        zzx8 = zzbi.zzx(i8 << 3);
                        i2 = zzx8 + zzx7;
                        i5 += i2 + zzh;
                    }
                    break;
                case 45:
                    zzh = zzdr.zzf((List) unsafe.getObject(obj, j));
                    if (zzh > 0) {
                        zzx7 = zzbi.zzx(zzh);
                        zzx8 = zzbi.zzx(i8 << 3);
                        i2 = zzx8 + zzx7;
                        i5 += i2 + zzh;
                    }
                    break;
                case 46:
                    zzh = zzdr.zzh((List) unsafe.getObject(obj, j));
                    if (zzh > 0) {
                        zzx7 = zzbi.zzx(zzh);
                        zzx8 = zzbi.zzx(i8 << 3);
                        i2 = zzx8 + zzx7;
                        i5 += i2 + zzh;
                    }
                    break;
                case 47:
                    zzh = zzdr.zzq((List) unsafe.getObject(obj, j));
                    if (zzh > 0) {
                        zzx7 = zzbi.zzx(zzh);
                        zzx8 = zzbi.zzx(i8 << 3);
                        i2 = zzx8 + zzx7;
                        i5 += i2 + zzh;
                    }
                    break;
                case 48:
                    zzh = zzdr.zzs((List) unsafe.getObject(obj, j));
                    if (zzh > 0) {
                        zzx7 = zzbi.zzx(zzh);
                        zzx8 = zzbi.zzx(i8 << 3);
                        i2 = zzx8 + zzx7;
                        i5 += i2 + zzh;
                    }
                    break;
                case 49:
                    zzt = zzdr.zzi(i8, (List) unsafe.getObject(obj, j), zzB(i4));
                    i5 += zzt;
                    break;
                case 50:
                    zzda.zza(i8, unsafe.getObject(obj, j), zzC(i4));
                    break;
                case 51:
                    if (zzT(obj, i8, i4)) {
                        zzx9 = zzbi.zzx(i8 << 3);
                        zzt = zzx9 + 8;
                        i5 += zzt;
                    }
                    break;
                case 52:
                    if (zzT(obj, i8, i4)) {
                        zzx10 = zzbi.zzx(i8 << 3);
                        zzt = zzx10 + 4;
                        i5 += zzt;
                    }
                    break;
                case 53:
                    if (zzT(obj, i8, i4)) {
                        zzh = zzbi.zzy(zzz(obj, j));
                        i2 = zzbi.zzx(i8 << 3);
                        i5 += i2 + zzh;
                    }
                    break;
                case 54:
                    if (zzT(obj, i8, i4)) {
                        zzh = zzbi.zzy(zzz(obj, j));
                        i2 = zzbi.zzx(i8 << 3);
                        i5 += i2 + zzh;
                    }
                    break;
                case 55:
                    if (zzT(obj, i8, i4)) {
                        zzh = zzbi.zzu(zzp(obj, j));
                        i2 = zzbi.zzx(i8 << 3);
                        i5 += i2 + zzh;
                    }
                    break;
                case 56:
                    if (zzT(obj, i8, i4)) {
                        zzx9 = zzbi.zzx(i8 << 3);
                        zzt = zzx9 + 8;
                        i5 += zzt;
                    }
                    break;
                case 57:
                    if (zzT(obj, i8, i4)) {
                        zzx10 = zzbi.zzx(i8 << 3);
                        zzt = zzx10 + 4;
                        i5 += zzt;
                    }
                    break;
                case 58:
                    if (zzT(obj, i8, i4)) {
                        zzt = zzbi.zzx(i8 << 3) + 1;
                        i5 += zzt;
                    }
                    break;
                case 59:
                    if (zzT(obj, i8, i4)) {
                        Object object2 = unsafe.getObject(obj, j);
                        if (object2 instanceof zzba) {
                            int i16 = zzbi.zzb;
                            int zzd3 = ((zzba) object2).zzd();
                            zzx11 = zzbi.zzx(zzd3) + zzd3;
                            zzx12 = zzbi.zzx(i8 << 3);
                            zzt = zzx12 + zzx11;
                            i5 += zzt;
                        } else {
                            zzh = zzbi.zzw((String) object2);
                            i2 = zzbi.zzx(i8 << 3);
                            i5 += i2 + zzh;
                        }
                    }
                    break;
                case 60:
                    if (zzT(obj, i8, i4)) {
                        zzt = zzdr.zzn(i8, unsafe.getObject(obj, j), zzB(i4));
                        i5 += zzt;
                    }
                    break;
                case 61:
                    if (zzT(obj, i8, i4)) {
                        zzba zzbaVar2 = (zzba) unsafe.getObject(obj, j);
                        int i17 = zzbi.zzb;
                        int zzd4 = zzbaVar2.zzd();
                        zzx11 = zzbi.zzx(zzd4) + zzd4;
                        zzx12 = zzbi.zzx(i8 << 3);
                        zzt = zzx12 + zzx11;
                        i5 += zzt;
                    }
                    break;
                case 62:
                    if (zzT(obj, i8, i4)) {
                        zzh = zzbi.zzx(zzp(obj, j));
                        i2 = zzbi.zzx(i8 << 3);
                        i5 += i2 + zzh;
                    }
                    break;
                case 63:
                    if (zzT(obj, i8, i4)) {
                        zzh = zzbi.zzu(zzp(obj, j));
                        i2 = zzbi.zzx(i8 << 3);
                        i5 += i2 + zzh;
                    }
                    break;
                case 64:
                    if (zzT(obj, i8, i4)) {
                        zzx10 = zzbi.zzx(i8 << 3);
                        zzt = zzx10 + 4;
                        i5 += zzt;
                    }
                    break;
                case 65:
                    if (zzT(obj, i8, i4)) {
                        zzx9 = zzbi.zzx(i8 << 3);
                        zzt = zzx9 + 8;
                        i5 += zzt;
                    }
                    break;
                case 66:
                    if (zzT(obj, i8, i4)) {
                        int zzp = zzp(obj, j);
                        i2 = zzbi.zzx(i8 << 3);
                        zzh = zzbi.zzx((zzp >> 31) ^ (zzp + zzp));
                        i5 += i2 + zzh;
                    }
                    break;
                case 67:
                    if (zzT(obj, i8, i4)) {
                        long zzz = zzz(obj, j);
                        i5 += zzbi.zzx(i8 << 3) + zzbi.zzy((zzz >> 63) ^ (zzz + zzz));
                    }
                    break;
                case 68:
                    if (zzT(obj, i8, i4)) {
                        zzt = zzbi.zzt(i8, (zzdf) unsafe.getObject(obj, j), zzB(i4));
                        i5 += zzt;
                    }
                    break;
            }
            i4 += 3;
            i3 = 1048575;
        }
        zzeg zzegVar = this.zzn;
        int zza2 = i5 + zzegVar.zza(zzegVar.zzd(obj));
        if (!this.zzh) {
            return zza2;
        }
        this.zzo.zza(obj);
        throw null;
    }

    private static int zzp(Object obj, long j) {
        return ((Integer) zzeq.zzf(obj, j)).intValue();
    }

    private final int zzq(Object obj, byte[] bArr, int i, int i2, int i3, long j, zzan zzanVar) throws IOException {
        Unsafe unsafe = zzb;
        Object zzC = zzC(i3);
        Object object = unsafe.getObject(obj, j);
        if (!((zzcz) object).zze()) {
            zzcz zzb2 = zzcz.zza().zzb();
            zzda.zzb(zzb2, object);
            unsafe.putObject(obj, j, zzb2);
        }
        throw null;
    }

    private final int zzr(Object obj, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, int i8, zzan zzanVar) throws IOException {
        Unsafe unsafe = zzb;
        long j2 = this.zzc[i8 + 2] & 1048575;
        switch (i7) {
            case 51:
                if (i5 == 1) {
                    unsafe.putObject(obj, j, Double.valueOf(Double.longBitsToDouble(zzao.zzp(bArr, i))));
                    int i9 = i + 8;
                    unsafe.putInt(obj, j2, i4);
                    return i9;
                }
                break;
            case 52:
                if (i5 == 5) {
                    unsafe.putObject(obj, j, Float.valueOf(Float.intBitsToFloat(zzao.zzb(bArr, i))));
                    int i10 = i + 4;
                    unsafe.putInt(obj, j2, i4);
                    return i10;
                }
                break;
            case 53:
            case 54:
                if (i5 == 0) {
                    int zzm = zzao.zzm(bArr, i, zzanVar);
                    unsafe.putObject(obj, j, Long.valueOf(zzanVar.zzb));
                    unsafe.putInt(obj, j2, i4);
                    return zzm;
                }
                break;
            case 55:
            case 62:
                if (i5 == 0) {
                    int zzj = zzao.zzj(bArr, i, zzanVar);
                    unsafe.putObject(obj, j, Integer.valueOf(zzanVar.zza));
                    unsafe.putInt(obj, j2, i4);
                    return zzj;
                }
                break;
            case 56:
            case 65:
                if (i5 == 1) {
                    unsafe.putObject(obj, j, Long.valueOf(zzao.zzp(bArr, i)));
                    int i11 = i + 8;
                    unsafe.putInt(obj, j2, i4);
                    return i11;
                }
                break;
            case 57:
            case 64:
                if (i5 == 5) {
                    unsafe.putObject(obj, j, Integer.valueOf(zzao.zzb(bArr, i)));
                    int i12 = i + 4;
                    unsafe.putInt(obj, j2, i4);
                    return i12;
                }
                break;
            case 58:
                if (i5 == 0) {
                    int zzm2 = zzao.zzm(bArr, i, zzanVar);
                    unsafe.putObject(obj, j, Boolean.valueOf(zzanVar.zzb != 0));
                    unsafe.putInt(obj, j2, i4);
                    return zzm2;
                }
                break;
            case 59:
                if (i5 == 2) {
                    int zzj2 = zzao.zzj(bArr, i, zzanVar);
                    int i13 = zzanVar.zza;
                    if (i13 == 0) {
                        unsafe.putObject(obj, j, "");
                    } else if ((i6 & 536870912) == 0 || zzev.zze(bArr, zzj2, zzj2 + i13)) {
                        unsafe.putObject(obj, j, new String(bArr, zzj2, i13, zzcg.zzb));
                        zzj2 += i13;
                    } else {
                        throw zzci.zzc();
                    }
                    unsafe.putInt(obj, j2, i4);
                    return zzj2;
                }
                break;
            case 60:
                if (i5 == 2) {
                    Object zzE = zzE(obj, i4, i8);
                    int zzo = zzao.zzo(zzE, zzB(i8), bArr, i, i2, zzanVar);
                    zzM(obj, i4, i8, zzE);
                    return zzo;
                }
                break;
            case 61:
                if (i5 == 2) {
                    int zza2 = zzao.zza(bArr, i, zzanVar);
                    unsafe.putObject(obj, j, zzanVar.zzc);
                    unsafe.putInt(obj, j2, i4);
                    return zza2;
                }
                break;
            case 63:
                if (i5 == 0) {
                    int zzj3 = zzao.zzj(bArr, i, zzanVar);
                    int i14 = zzanVar.zza;
                    zzce zzA = zzA(i8);
                    if (zzA == null || zzA.zza(i14)) {
                        unsafe.putObject(obj, j, Integer.valueOf(i14));
                        unsafe.putInt(obj, j2, i4);
                    } else {
                        zzd(obj).zzj(i3, Long.valueOf(i14));
                    }
                    return zzj3;
                }
                break;
            case 66:
                if (i5 == 0) {
                    int zzj4 = zzao.zzj(bArr, i, zzanVar);
                    unsafe.putObject(obj, j, Integer.valueOf(zzbe.zzb(zzanVar.zza)));
                    unsafe.putInt(obj, j2, i4);
                    return zzj4;
                }
                break;
            case 67:
                if (i5 == 0) {
                    int zzm3 = zzao.zzm(bArr, i, zzanVar);
                    unsafe.putObject(obj, j, Long.valueOf(zzbe.zzc(zzanVar.zzb)));
                    unsafe.putInt(obj, j2, i4);
                    return zzm3;
                }
                break;
            case 68:
                if (i5 == 3) {
                    Object zzE2 = zzE(obj, i4, i8);
                    int zzn = zzao.zzn(zzE2, zzB(i8), bArr, i, i2, (i3 & (-8)) | 4, zzanVar);
                    zzM(obj, i4, i8, zzE2);
                    return zzn;
                }
                break;
        }
        return i;
    }

    /* JADX DEBUG: Duplicate block (B:122:0x0216) to fix multi-entry loop: BACK_EDGE: B:122:0x0216 -> B:114:0x0217 */
    /* JADX DEBUG: Duplicate block (B:142:0x0264) to fix multi-entry loop: BACK_EDGE: B:142:0x0264 -> B:132:0x0265 */
    /* JADX DEBUG: Duplicate block (B:93:0x019b) to fix multi-entry loop: BACK_EDGE: B:93:0x019b -> B:83:0x019c */
    private final int zzs(Object obj, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, long j, int i7, long j2, zzan zzanVar) throws IOException {
        int i8;
        int i9;
        int i10;
        int i11;
        int zzl;
        int i12 = i;
        Unsafe unsafe = zzb;
        zzcf zzcfVar = (zzcf) unsafe.getObject(obj, j2);
        if (!zzcfVar.zzc()) {
            int size = zzcfVar.size();
            zzcfVar = zzcfVar.zzd(size == 0 ? 10 : size + size);
            unsafe.putObject(obj, j2, zzcfVar);
        }
        switch (i7) {
            case 18:
            case 35:
                if (i5 == 2) {
                    zzbk zzbkVar = (zzbk) zzcfVar;
                    int zzj = zzao.zzj(bArr, i12, zzanVar);
                    int i13 = zzanVar.zza + zzj;
                    while (zzj < i13) {
                        zzbkVar.zze(Double.longBitsToDouble(zzao.zzp(bArr, zzj)));
                        zzj += 8;
                    }
                    if (zzj == i13) {
                        return zzj;
                    }
                    throw zzci.zzg();
                }
                if (i5 == 1) {
                    zzbk zzbkVar2 = (zzbk) zzcfVar;
                    zzbkVar2.zze(Double.longBitsToDouble(zzao.zzp(bArr, i)));
                    while (true) {
                        i8 = i12 + 8;
                        if (i8 < i2) {
                            i12 = zzao.zzj(bArr, i8, zzanVar);
                            if (i3 == zzanVar.zza) {
                                zzbkVar2.zze(Double.longBitsToDouble(zzao.zzp(bArr, i12)));
                            }
                        }
                    }
                    return i8;
                }
                break;
            case 19:
            case 36:
                if (i5 == 2) {
                    zzbu zzbuVar = (zzbu) zzcfVar;
                    int zzj2 = zzao.zzj(bArr, i12, zzanVar);
                    int i14 = zzanVar.zza + zzj2;
                    while (zzj2 < i14) {
                        zzbuVar.zze(Float.intBitsToFloat(zzao.zzb(bArr, zzj2)));
                        zzj2 += 4;
                    }
                    if (zzj2 == i14) {
                        return zzj2;
                    }
                    throw zzci.zzg();
                }
                if (i5 == 5) {
                    zzbu zzbuVar2 = (zzbu) zzcfVar;
                    zzbuVar2.zze(Float.intBitsToFloat(zzao.zzb(bArr, i)));
                    while (true) {
                        i9 = i12 + 4;
                        if (i9 < i2) {
                            i12 = zzao.zzj(bArr, i9, zzanVar);
                            if (i3 == zzanVar.zza) {
                                zzbuVar2.zze(Float.intBitsToFloat(zzao.zzb(bArr, i12)));
                            }
                        }
                    }
                    return i9;
                }
                break;
            case 20:
            case 21:
            case 37:
            case 38:
                if (i5 == 2) {
                    zzcu zzcuVar = (zzcu) zzcfVar;
                    int zzj3 = zzao.zzj(bArr, i12, zzanVar);
                    int i15 = zzanVar.zza + zzj3;
                    while (zzj3 < i15) {
                        zzj3 = zzao.zzm(bArr, zzj3, zzanVar);
                        zzcuVar.zzf(zzanVar.zzb);
                    }
                    if (zzj3 == i15) {
                        return zzj3;
                    }
                    throw zzci.zzg();
                }
                if (i5 == 0) {
                    zzcu zzcuVar2 = (zzcu) zzcfVar;
                    int zzm = zzao.zzm(bArr, i12, zzanVar);
                    zzcuVar2.zzf(zzanVar.zzb);
                    while (zzm < i2) {
                        int zzj4 = zzao.zzj(bArr, zzm, zzanVar);
                        if (i3 != zzanVar.zza) {
                            return zzm;
                        }
                        zzm = zzao.zzm(bArr, zzj4, zzanVar);
                        zzcuVar2.zzf(zzanVar.zzb);
                    }
                    return zzm;
                }
                break;
            case 22:
            case 29:
            case 39:
            case 43:
                if (i5 == 2) {
                    return zzao.zzf(bArr, i12, zzcfVar, zzanVar);
                }
                if (i5 == 0) {
                    return zzao.zzl(i3, bArr, i, i2, zzcfVar, zzanVar);
                }
                break;
            case 23:
            case 32:
            case 40:
            case 46:
                if (i5 == 2) {
                    zzcu zzcuVar3 = (zzcu) zzcfVar;
                    int zzj5 = zzao.zzj(bArr, i12, zzanVar);
                    int i16 = zzanVar.zza + zzj5;
                    while (zzj5 < i16) {
                        zzcuVar3.zzf(zzao.zzp(bArr, zzj5));
                        zzj5 += 8;
                    }
                    if (zzj5 == i16) {
                        return zzj5;
                    }
                    throw zzci.zzg();
                }
                if (i5 == 1) {
                    zzcu zzcuVar4 = (zzcu) zzcfVar;
                    zzcuVar4.zzf(zzao.zzp(bArr, i));
                    while (true) {
                        i10 = i12 + 8;
                        if (i10 < i2) {
                            i12 = zzao.zzj(bArr, i10, zzanVar);
                            if (i3 == zzanVar.zza) {
                                zzcuVar4.zzf(zzao.zzp(bArr, i12));
                            }
                        }
                    }
                    return i10;
                }
                break;
            case 24:
            case 31:
            case 41:
            case 45:
                if (i5 == 2) {
                    zzcc zzccVar = (zzcc) zzcfVar;
                    int zzj6 = zzao.zzj(bArr, i12, zzanVar);
                    int i17 = zzanVar.zza + zzj6;
                    while (zzj6 < i17) {
                        zzccVar.zzf(zzao.zzb(bArr, zzj6));
                        zzj6 += 4;
                    }
                    if (zzj6 == i17) {
                        return zzj6;
                    }
                    throw zzci.zzg();
                }
                if (i5 == 5) {
                    zzcc zzccVar2 = (zzcc) zzcfVar;
                    zzccVar2.zzf(zzao.zzb(bArr, i));
                    while (true) {
                        i11 = i12 + 4;
                        if (i11 < i2) {
                            i12 = zzao.zzj(bArr, i11, zzanVar);
                            if (i3 == zzanVar.zza) {
                                zzccVar2.zzf(zzao.zzb(bArr, i12));
                            }
                        }
                    }
                    return i11;
                }
                break;
            case 25:
            case 42:
                if (i5 == 2) {
                    zzap zzapVar = (zzap) zzcfVar;
                    int zzj7 = zzao.zzj(bArr, i12, zzanVar);
                    int i18 = zzanVar.zza + zzj7;
                    while (zzj7 < i18) {
                        zzj7 = zzao.zzm(bArr, zzj7, zzanVar);
                        zzapVar.zze(zzanVar.zzb != 0);
                    }
                    if (zzj7 == i18) {
                        return zzj7;
                    }
                    throw zzci.zzg();
                }
                if (i5 == 0) {
                    zzap zzapVar2 = (zzap) zzcfVar;
                    int zzm2 = zzao.zzm(bArr, i12, zzanVar);
                    zzapVar2.zze(zzanVar.zzb != 0);
                    while (zzm2 < i2) {
                        int zzj8 = zzao.zzj(bArr, zzm2, zzanVar);
                        if (i3 != zzanVar.zza) {
                            return zzm2;
                        }
                        zzm2 = zzao.zzm(bArr, zzj8, zzanVar);
                        zzapVar2.zze(zzanVar.zzb != 0);
                    }
                    return zzm2;
                }
                break;
            case 26:
                if (i5 == 2) {
                    if ((j & 536870912) != 0) {
                        i12 = zzao.zzj(bArr, i12, zzanVar);
                        int i19 = zzanVar.zza;
                        if (i19 < 0) {
                            throw zzci.zzd();
                        }
                        if (i19 == 0) {
                            zzcfVar.add("");
                        } else {
                            int i20 = i12 + i19;
                            if (!zzev.zze(bArr, i12, i20)) {
                                throw zzci.zzc();
                            }
                            zzcfVar.add(new String(bArr, i12, i19, zzcg.zzb));
                            i12 = i20;
                        }
                        while (i12 < i2) {
                            int zzj9 = zzao.zzj(bArr, i12, zzanVar);
                            if (i3 != zzanVar.zza) {
                                break;
                            } else {
                                i12 = zzao.zzj(bArr, zzj9, zzanVar);
                                int i21 = zzanVar.zza;
                                if (i21 < 0) {
                                    throw zzci.zzd();
                                }
                                if (i21 == 0) {
                                    zzcfVar.add("");
                                } else {
                                    int i22 = i12 + i21;
                                    if (zzev.zze(bArr, i12, i22)) {
                                        zzcfVar.add(new String(bArr, i12, i21, zzcg.zzb));
                                        i12 = i22;
                                    } else {
                                        throw zzci.zzc();
                                    }
                                }
                            }
                        }
                        break;
                    } else {
                        i12 = zzao.zzj(bArr, i12, zzanVar);
                        int i23 = zzanVar.zza;
                        if (i23 < 0) {
                            throw zzci.zzd();
                        }
                        if (i23 == 0) {
                            zzcfVar.add("");
                        } else {
                            zzcfVar.add(new String(bArr, i12, i23, zzcg.zzb));
                            i12 += i23;
                        }
                        while (i12 < i2) {
                            int zzj10 = zzao.zzj(bArr, i12, zzanVar);
                            if (i3 != zzanVar.zza) {
                                break;
                            } else {
                                i12 = zzao.zzj(bArr, zzj10, zzanVar);
                                int i24 = zzanVar.zza;
                                if (i24 < 0) {
                                    throw zzci.zzd();
                                }
                                if (i24 == 0) {
                                    zzcfVar.add("");
                                } else {
                                    zzcfVar.add(new String(bArr, i12, i24, zzcg.zzb));
                                    i12 += i24;
                                }
                            }
                        }
                        break;
                    }
                }
                break;
            case 27:
                if (i5 == 2) {
                    return zzao.zze(zzB(i6), i3, bArr, i, i2, zzcfVar, zzanVar);
                }
                break;
            case 28:
                if (i5 == 2) {
                    int zzj11 = zzao.zzj(bArr, i12, zzanVar);
                    int i25 = zzanVar.zza;
                    if (i25 < 0) {
                        throw zzci.zzd();
                    }
                    if (i25 > bArr.length - zzj11) {
                        throw zzci.zzg();
                    }
                    if (i25 == 0) {
                        zzcfVar.add(zzba.zzb);
                    } else {
                        zzcfVar.add(zzba.zzl(bArr, zzj11, i25));
                        zzj11 += i25;
                    }
                    while (zzj11 < i2) {
                        int zzj12 = zzao.zzj(bArr, zzj11, zzanVar);
                        if (i3 != zzanVar.zza) {
                            return zzj11;
                        }
                        zzj11 = zzao.zzj(bArr, zzj12, zzanVar);
                        int i26 = zzanVar.zza;
                        if (i26 >= 0) {
                            if (i26 > bArr.length - zzj11) {
                                throw zzci.zzg();
                            }
                            if (i26 == 0) {
                                zzcfVar.add(zzba.zzb);
                            } else {
                                zzcfVar.add(zzba.zzl(bArr, zzj11, i26));
                                zzj11 += i26;
                            }
                        } else {
                            throw zzci.zzd();
                        }
                    }
                    return zzj11;
                }
                break;
            case 30:
            case 44:
                if (i5 == 2) {
                    zzl = zzao.zzf(bArr, i12, zzcfVar, zzanVar);
                } else if (i5 == 0) {
                    zzl = zzao.zzl(i3, bArr, i, i2, zzcfVar, zzanVar);
                }
                zzce zzA = zzA(i6);
                zzeg zzegVar = this.zzn;
                int i27 = zzdr.zza;
                if (zzA != null) {
                    Object obj2 = null;
                    if (zzcfVar instanceof RandomAccess) {
                        int size2 = zzcfVar.size();
                        int i28 = 0;
                        for (int i29 = 0; i29 < size2; i29++) {
                            int intValue = ((Integer) zzcfVar.get(i29)).intValue();
                            if (zzA.zza(intValue)) {
                                if (i29 != i28) {
                                    zzcfVar.set(i28, Integer.valueOf(intValue));
                                }
                                i28++;
                            } else {
                                obj2 = zzdr.zzB(obj, i4, intValue, obj2, zzegVar);
                            }
                        }
                        if (i28 != size2) {
                            zzcfVar.subList(i28, size2).clear();
                            return zzl;
                        }
                    } else {
                        Iterator it = zzcfVar.iterator();
                        while (it.hasNext()) {
                            int intValue2 = ((Integer) it.next()).intValue();
                            if (!zzA.zza(intValue2)) {
                                obj2 = zzdr.zzB(obj, i4, intValue2, obj2, zzegVar);
                                it.remove();
                            }
                        }
                    }
                }
                return zzl;
            case 33:
            case 47:
                if (i5 == 2) {
                    zzcc zzccVar3 = (zzcc) zzcfVar;
                    int zzj13 = zzao.zzj(bArr, i12, zzanVar);
                    int i30 = zzanVar.zza + zzj13;
                    while (zzj13 < i30) {
                        zzj13 = zzao.zzj(bArr, zzj13, zzanVar);
                        zzccVar3.zzf(zzbe.zzb(zzanVar.zza));
                    }
                    if (zzj13 == i30) {
                        return zzj13;
                    }
                    throw zzci.zzg();
                }
                if (i5 == 0) {
                    zzcc zzccVar4 = (zzcc) zzcfVar;
                    int zzj14 = zzao.zzj(bArr, i12, zzanVar);
                    zzccVar4.zzf(zzbe.zzb(zzanVar.zza));
                    while (zzj14 < i2) {
                        int zzj15 = zzao.zzj(bArr, zzj14, zzanVar);
                        if (i3 != zzanVar.zza) {
                            return zzj14;
                        }
                        zzj14 = zzao.zzj(bArr, zzj15, zzanVar);
                        zzccVar4.zzf(zzbe.zzb(zzanVar.zza));
                    }
                    return zzj14;
                }
                break;
            case 34:
            case 48:
                if (i5 == 2) {
                    zzcu zzcuVar5 = (zzcu) zzcfVar;
                    int zzj16 = zzao.zzj(bArr, i12, zzanVar);
                    int i31 = zzanVar.zza + zzj16;
                    while (zzj16 < i31) {
                        zzj16 = zzao.zzm(bArr, zzj16, zzanVar);
                        zzcuVar5.zzf(zzbe.zzc(zzanVar.zzb));
                    }
                    if (zzj16 == i31) {
                        return zzj16;
                    }
                    throw zzci.zzg();
                }
                if (i5 == 0) {
                    zzcu zzcuVar6 = (zzcu) zzcfVar;
                    int zzm3 = zzao.zzm(bArr, i12, zzanVar);
                    zzcuVar6.zzf(zzbe.zzc(zzanVar.zzb));
                    while (zzm3 < i2) {
                        int zzj17 = zzao.zzj(bArr, zzm3, zzanVar);
                        if (i3 != zzanVar.zza) {
                            return zzm3;
                        }
                        zzm3 = zzao.zzm(bArr, zzj17, zzanVar);
                        zzcuVar6.zzf(zzbe.zzc(zzanVar.zzb));
                    }
                    return zzm3;
                }
                break;
            default:
                if (i5 == 3) {
                    zzdp zzB = zzB(i6);
                    int i32 = (i3 & (-8)) | 4;
                    int zzc = zzao.zzc(zzB, bArr, i, i2, i32, zzanVar);
                    zzcfVar.add(zzanVar.zzc);
                    while (zzc < i2) {
                        int zzj18 = zzao.zzj(bArr, zzc, zzanVar);
                        if (i3 != zzanVar.zza) {
                            return zzc;
                        }
                        zzc = zzao.zzc(zzB, bArr, zzj18, i2, i32, zzanVar);
                        zzcfVar.add(zzanVar.zzc);
                    }
                    return zzc;
                }
                break;
        }
        return i12;
    }

    private final int zzt(int i) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zzw(i, 0);
    }

    private final int zzu(int i, int i2) {
        if (i < this.zze || i > this.zzf) {
            return -1;
        }
        return zzw(i, i2);
    }

    private final int zzv(int i) {
        return this.zzc[i + 2];
    }

    private final int zzw(int i, int i2) {
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

    private static int zzx(int i) {
        return (i >>> 20) & 255;
    }

    private final int zzy(int i) {
        return this.zzc[i + 1];
    }

    private static long zzz(Object obj, long j) {
        return ((Long) zzeq.zzf(obj, j)).longValue();
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:12:0x0037. Please report as an issue. */
    @Override // com.google.android.gms.internal.play_billing.zzdp
    public final int zza(Object obj) {
        int zzx;
        int zzx2;
        int zzy;
        int zzx3;
        int zzx4;
        int zzx5;
        int zzx6;
        int zzn;
        int zzx7;
        int zzy2;
        int zzx8;
        int zzx9;
        if (this.zzi) {
            Unsafe unsafe = zzb;
            int i = 0;
            for (int i2 = 0; i2 < this.zzc.length; i2 += 3) {
                int zzy3 = zzy(i2);
                int zzx10 = zzx(zzy3);
                int i3 = this.zzc[i2];
                int i4 = zzy3 & 1048575;
                if (zzx10 >= zzbt.DOUBLE_LIST_PACKED.zza() && zzx10 <= zzbt.SINT64_LIST_PACKED.zza()) {
                    int i5 = this.zzc[i2 + 2];
                }
                long j = i4;
                switch (zzx10) {
                    case 0:
                        if (zzP(obj, i2)) {
                            zzx = zzbi.zzx(i3 << 3);
                            zzn = zzx + 8;
                            i += zzn;
                            break;
                        } else {
                            break;
                        }
                    case 1:
                        if (zzP(obj, i2)) {
                            zzx2 = zzbi.zzx(i3 << 3);
                            zzn = zzx2 + 4;
                            i += zzn;
                            break;
                        } else {
                            break;
                        }
                    case 2:
                        if (zzP(obj, i2)) {
                            zzy = zzbi.zzy(zzeq.zzd(obj, j));
                            zzx3 = zzbi.zzx(i3 << 3);
                            i += zzx3 + zzy;
                            break;
                        } else {
                            break;
                        }
                    case 3:
                        if (zzP(obj, i2)) {
                            zzy = zzbi.zzy(zzeq.zzd(obj, j));
                            zzx3 = zzbi.zzx(i3 << 3);
                            i += zzx3 + zzy;
                            break;
                        } else {
                            break;
                        }
                    case 4:
                        if (zzP(obj, i2)) {
                            zzy = zzbi.zzu(zzeq.zzc(obj, j));
                            zzx3 = zzbi.zzx(i3 << 3);
                            i += zzx3 + zzy;
                            break;
                        } else {
                            break;
                        }
                    case 5:
                        if (zzP(obj, i2)) {
                            zzx = zzbi.zzx(i3 << 3);
                            zzn = zzx + 8;
                            i += zzn;
                            break;
                        } else {
                            break;
                        }
                    case 6:
                        if (zzP(obj, i2)) {
                            zzx2 = zzbi.zzx(i3 << 3);
                            zzn = zzx2 + 4;
                            i += zzn;
                            break;
                        } else {
                            break;
                        }
                    case 7:
                        if (zzP(obj, i2)) {
                            zzx4 = zzbi.zzx(i3 << 3);
                            zzn = zzx4 + 1;
                            i += zzn;
                            break;
                        } else {
                            break;
                        }
                    case 8:
                        if (zzP(obj, i2)) {
                            Object zzf = zzeq.zzf(obj, j);
                            if (zzf instanceof zzba) {
                                int i6 = i3 << 3;
                                int i7 = zzbi.zzb;
                                int zzd = ((zzba) zzf).zzd();
                                zzx5 = zzbi.zzx(zzd) + zzd;
                                zzx6 = zzbi.zzx(i6);
                                zzn = zzx6 + zzx5;
                                i += zzn;
                                break;
                            } else {
                                zzy = zzbi.zzw((String) zzf);
                                zzx3 = zzbi.zzx(i3 << 3);
                                i += zzx3 + zzy;
                                break;
                            }
                        } else {
                            break;
                        }
                    case 9:
                        if (zzP(obj, i2)) {
                            zzn = zzdr.zzn(i3, zzeq.zzf(obj, j), zzB(i2));
                            i += zzn;
                            break;
                        } else {
                            break;
                        }
                    case 10:
                        if (zzP(obj, i2)) {
                            zzba zzbaVar = (zzba) zzeq.zzf(obj, j);
                            int i8 = i3 << 3;
                            int i9 = zzbi.zzb;
                            int zzd2 = zzbaVar.zzd();
                            zzx5 = zzbi.zzx(zzd2) + zzd2;
                            zzx6 = zzbi.zzx(i8);
                            zzn = zzx6 + zzx5;
                            i += zzn;
                            break;
                        } else {
                            break;
                        }
                    case 11:
                        if (zzP(obj, i2)) {
                            zzy = zzbi.zzx(zzeq.zzc(obj, j));
                            zzx3 = zzbi.zzx(i3 << 3);
                            i += zzx3 + zzy;
                            break;
                        } else {
                            break;
                        }
                    case 12:
                        if (zzP(obj, i2)) {
                            zzy = zzbi.zzu(zzeq.zzc(obj, j));
                            zzx3 = zzbi.zzx(i3 << 3);
                            i += zzx3 + zzy;
                            break;
                        } else {
                            break;
                        }
                    case 13:
                        if (zzP(obj, i2)) {
                            zzx2 = zzbi.zzx(i3 << 3);
                            zzn = zzx2 + 4;
                            i += zzn;
                            break;
                        } else {
                            break;
                        }
                    case 14:
                        if (zzP(obj, i2)) {
                            zzx = zzbi.zzx(i3 << 3);
                            zzn = zzx + 8;
                            i += zzn;
                            break;
                        } else {
                            break;
                        }
                    case 15:
                        if (zzP(obj, i2)) {
                            int zzc = zzeq.zzc(obj, j);
                            zzx3 = zzbi.zzx(i3 << 3);
                            zzy = zzbi.zzx((zzc >> 31) ^ (zzc + zzc));
                            i += zzx3 + zzy;
                            break;
                        } else {
                            break;
                        }
                    case 16:
                        if (zzP(obj, i2)) {
                            long zzd3 = zzeq.zzd(obj, j);
                            zzx7 = zzbi.zzx(i3 << 3);
                            zzy2 = zzbi.zzy((zzd3 + zzd3) ^ (zzd3 >> 63));
                            zzn = zzx7 + zzy2;
                            i += zzn;
                            break;
                        } else {
                            break;
                        }
                    case 17:
                        if (zzP(obj, i2)) {
                            zzn = zzbi.zzt(i3, (zzdf) zzeq.zzf(obj, j), zzB(i2));
                            i += zzn;
                            break;
                        } else {
                            break;
                        }
                    case 18:
                        zzn = zzdr.zzg(i3, (List) zzeq.zzf(obj, j), false);
                        i += zzn;
                        break;
                    case 19:
                        zzn = zzdr.zze(i3, (List) zzeq.zzf(obj, j), false);
                        i += zzn;
                        break;
                    case 20:
                        zzn = zzdr.zzl(i3, (List) zzeq.zzf(obj, j), false);
                        i += zzn;
                        break;
                    case 21:
                        zzn = zzdr.zzw(i3, (List) zzeq.zzf(obj, j), false);
                        i += zzn;
                        break;
                    case 22:
                        zzn = zzdr.zzj(i3, (List) zzeq.zzf(obj, j), false);
                        i += zzn;
                        break;
                    case 23:
                        zzn = zzdr.zzg(i3, (List) zzeq.zzf(obj, j), false);
                        i += zzn;
                        break;
                    case 24:
                        zzn = zzdr.zze(i3, (List) zzeq.zzf(obj, j), false);
                        i += zzn;
                        break;
                    case 25:
                        zzn = zzdr.zza(i3, (List) zzeq.zzf(obj, j), false);
                        i += zzn;
                        break;
                    case 26:
                        zzn = zzdr.zzt(i3, (List) zzeq.zzf(obj, j));
                        i += zzn;
                        break;
                    case 27:
                        zzn = zzdr.zzo(i3, (List) zzeq.zzf(obj, j), zzB(i2));
                        i += zzn;
                        break;
                    case 28:
                        zzn = zzdr.zzb(i3, (List) zzeq.zzf(obj, j));
                        i += zzn;
                        break;
                    case 29:
                        zzn = zzdr.zzu(i3, (List) zzeq.zzf(obj, j), false);
                        i += zzn;
                        break;
                    case 30:
                        zzn = zzdr.zzc(i3, (List) zzeq.zzf(obj, j), false);
                        i += zzn;
                        break;
                    case 31:
                        zzn = zzdr.zze(i3, (List) zzeq.zzf(obj, j), false);
                        i += zzn;
                        break;
                    case 32:
                        zzn = zzdr.zzg(i3, (List) zzeq.zzf(obj, j), false);
                        i += zzn;
                        break;
                    case 33:
                        zzn = zzdr.zzp(i3, (List) zzeq.zzf(obj, j), false);
                        i += zzn;
                        break;
                    case 34:
                        zzn = zzdr.zzr(i3, (List) zzeq.zzf(obj, j), false);
                        i += zzn;
                        break;
                    case 35:
                        zzy = zzdr.zzh((List) unsafe.getObject(obj, j));
                        if (zzy > 0) {
                            int i10 = i3 << 3;
                            zzx8 = zzbi.zzx(zzy);
                            zzx9 = zzbi.zzx(i10);
                            zzx3 = zzx9 + zzx8;
                            i += zzx3 + zzy;
                            break;
                        } else {
                            break;
                        }
                    case 36:
                        zzy = zzdr.zzf((List) unsafe.getObject(obj, j));
                        if (zzy > 0) {
                            int i11 = i3 << 3;
                            zzx8 = zzbi.zzx(zzy);
                            zzx9 = zzbi.zzx(i11);
                            zzx3 = zzx9 + zzx8;
                            i += zzx3 + zzy;
                            break;
                        } else {
                            break;
                        }
                    case 37:
                        zzy = zzdr.zzm((List) unsafe.getObject(obj, j));
                        if (zzy > 0) {
                            int i12 = i3 << 3;
                            zzx8 = zzbi.zzx(zzy);
                            zzx9 = zzbi.zzx(i12);
                            zzx3 = zzx9 + zzx8;
                            i += zzx3 + zzy;
                            break;
                        } else {
                            break;
                        }
                    case 38:
                        zzy = zzdr.zzx((List) unsafe.getObject(obj, j));
                        if (zzy > 0) {
                            int i13 = i3 << 3;
                            zzx8 = zzbi.zzx(zzy);
                            zzx9 = zzbi.zzx(i13);
                            zzx3 = zzx9 + zzx8;
                            i += zzx3 + zzy;
                            break;
                        } else {
                            break;
                        }
                    case 39:
                        zzy = zzdr.zzk((List) unsafe.getObject(obj, j));
                        if (zzy > 0) {
                            int i14 = i3 << 3;
                            zzx8 = zzbi.zzx(zzy);
                            zzx9 = zzbi.zzx(i14);
                            zzx3 = zzx9 + zzx8;
                            i += zzx3 + zzy;
                            break;
                        } else {
                            break;
                        }
                    case 40:
                        zzy = zzdr.zzh((List) unsafe.getObject(obj, j));
                        if (zzy > 0) {
                            int i15 = i3 << 3;
                            zzx8 = zzbi.zzx(zzy);
                            zzx9 = zzbi.zzx(i15);
                            zzx3 = zzx9 + zzx8;
                            i += zzx3 + zzy;
                            break;
                        } else {
                            break;
                        }
                    case 41:
                        zzy = zzdr.zzf((List) unsafe.getObject(obj, j));
                        if (zzy > 0) {
                            int i16 = i3 << 3;
                            zzx8 = zzbi.zzx(zzy);
                            zzx9 = zzbi.zzx(i16);
                            zzx3 = zzx9 + zzx8;
                            i += zzx3 + zzy;
                            break;
                        } else {
                            break;
                        }
                    case 42:
                        List list = (List) unsafe.getObject(obj, j);
                        int i17 = zzdr.zza;
                        zzy = list.size();
                        if (zzy > 0) {
                            int i18 = i3 << 3;
                            zzx8 = zzbi.zzx(zzy);
                            zzx9 = zzbi.zzx(i18);
                            zzx3 = zzx9 + zzx8;
                            i += zzx3 + zzy;
                            break;
                        } else {
                            break;
                        }
                    case 43:
                        zzy = zzdr.zzv((List) unsafe.getObject(obj, j));
                        if (zzy > 0) {
                            int i19 = i3 << 3;
                            zzx8 = zzbi.zzx(zzy);
                            zzx9 = zzbi.zzx(i19);
                            zzx3 = zzx9 + zzx8;
                            i += zzx3 + zzy;
                            break;
                        } else {
                            break;
                        }
                    case 44:
                        zzy = zzdr.zzd((List) unsafe.getObject(obj, j));
                        if (zzy > 0) {
                            int i20 = i3 << 3;
                            zzx8 = zzbi.zzx(zzy);
                            zzx9 = zzbi.zzx(i20);
                            zzx3 = zzx9 + zzx8;
                            i += zzx3 + zzy;
                            break;
                        } else {
                            break;
                        }
                    case 45:
                        zzy = zzdr.zzf((List) unsafe.getObject(obj, j));
                        if (zzy > 0) {
                            int i21 = i3 << 3;
                            zzx8 = zzbi.zzx(zzy);
                            zzx9 = zzbi.zzx(i21);
                            zzx3 = zzx9 + zzx8;
                            i += zzx3 + zzy;
                            break;
                        } else {
                            break;
                        }
                    case 46:
                        zzy = zzdr.zzh((List) unsafe.getObject(obj, j));
                        if (zzy > 0) {
                            int i22 = i3 << 3;
                            zzx8 = zzbi.zzx(zzy);
                            zzx9 = zzbi.zzx(i22);
                            zzx3 = zzx9 + zzx8;
                            i += zzx3 + zzy;
                            break;
                        } else {
                            break;
                        }
                    case 47:
                        zzy = zzdr.zzq((List) unsafe.getObject(obj, j));
                        if (zzy > 0) {
                            int i23 = i3 << 3;
                            zzx8 = zzbi.zzx(zzy);
                            zzx9 = zzbi.zzx(i23);
                            zzx3 = zzx9 + zzx8;
                            i += zzx3 + zzy;
                            break;
                        } else {
                            break;
                        }
                    case 48:
                        zzy = zzdr.zzs((List) unsafe.getObject(obj, j));
                        if (zzy > 0) {
                            int i24 = i3 << 3;
                            zzx8 = zzbi.zzx(zzy);
                            zzx9 = zzbi.zzx(i24);
                            zzx3 = zzx9 + zzx8;
                            i += zzx3 + zzy;
                            break;
                        } else {
                            break;
                        }
                    case 49:
                        zzn = zzdr.zzi(i3, (List) zzeq.zzf(obj, j), zzB(i2));
                        i += zzn;
                        break;
                    case 50:
                        zzda.zza(i3, zzeq.zzf(obj, j), zzC(i2));
                        break;
                    case 51:
                        if (zzT(obj, i3, i2)) {
                            zzx = zzbi.zzx(i3 << 3);
                            zzn = zzx + 8;
                            i += zzn;
                            break;
                        } else {
                            break;
                        }
                    case 52:
                        if (zzT(obj, i3, i2)) {
                            zzx2 = zzbi.zzx(i3 << 3);
                            zzn = zzx2 + 4;
                            i += zzn;
                            break;
                        } else {
                            break;
                        }
                    case 53:
                        if (zzT(obj, i3, i2)) {
                            zzy = zzbi.zzy(zzz(obj, j));
                            zzx3 = zzbi.zzx(i3 << 3);
                            i += zzx3 + zzy;
                            break;
                        } else {
                            break;
                        }
                    case 54:
                        if (zzT(obj, i3, i2)) {
                            zzy = zzbi.zzy(zzz(obj, j));
                            zzx3 = zzbi.zzx(i3 << 3);
                            i += zzx3 + zzy;
                            break;
                        } else {
                            break;
                        }
                    case 55:
                        if (zzT(obj, i3, i2)) {
                            zzy = zzbi.zzu(zzp(obj, j));
                            zzx3 = zzbi.zzx(i3 << 3);
                            i += zzx3 + zzy;
                            break;
                        } else {
                            break;
                        }
                    case 56:
                        if (zzT(obj, i3, i2)) {
                            zzx = zzbi.zzx(i3 << 3);
                            zzn = zzx + 8;
                            i += zzn;
                            break;
                        } else {
                            break;
                        }
                    case 57:
                        if (zzT(obj, i3, i2)) {
                            zzx2 = zzbi.zzx(i3 << 3);
                            zzn = zzx2 + 4;
                            i += zzn;
                            break;
                        } else {
                            break;
                        }
                    case 58:
                        if (zzT(obj, i3, i2)) {
                            zzx4 = zzbi.zzx(i3 << 3);
                            zzn = zzx4 + 1;
                            i += zzn;
                            break;
                        } else {
                            break;
                        }
                    case 59:
                        if (zzT(obj, i3, i2)) {
                            Object zzf2 = zzeq.zzf(obj, j);
                            if (zzf2 instanceof zzba) {
                                int i25 = i3 << 3;
                                int i26 = zzbi.zzb;
                                int zzd4 = ((zzba) zzf2).zzd();
                                zzx5 = zzbi.zzx(zzd4) + zzd4;
                                zzx6 = zzbi.zzx(i25);
                                zzn = zzx6 + zzx5;
                                i += zzn;
                                break;
                            } else {
                                zzy = zzbi.zzw((String) zzf2);
                                zzx3 = zzbi.zzx(i3 << 3);
                                i += zzx3 + zzy;
                                break;
                            }
                        } else {
                            break;
                        }
                    case 60:
                        if (zzT(obj, i3, i2)) {
                            zzn = zzdr.zzn(i3, zzeq.zzf(obj, j), zzB(i2));
                            i += zzn;
                            break;
                        } else {
                            break;
                        }
                    case 61:
                        if (zzT(obj, i3, i2)) {
                            zzba zzbaVar2 = (zzba) zzeq.zzf(obj, j);
                            int i27 = i3 << 3;
                            int i28 = zzbi.zzb;
                            int zzd5 = zzbaVar2.zzd();
                            zzx5 = zzbi.zzx(zzd5) + zzd5;
                            zzx6 = zzbi.zzx(i27);
                            zzn = zzx6 + zzx5;
                            i += zzn;
                            break;
                        } else {
                            break;
                        }
                    case 62:
                        if (zzT(obj, i3, i2)) {
                            zzy = zzbi.zzx(zzp(obj, j));
                            zzx3 = zzbi.zzx(i3 << 3);
                            i += zzx3 + zzy;
                            break;
                        } else {
                            break;
                        }
                    case 63:
                        if (zzT(obj, i3, i2)) {
                            zzy = zzbi.zzu(zzp(obj, j));
                            zzx3 = zzbi.zzx(i3 << 3);
                            i += zzx3 + zzy;
                            break;
                        } else {
                            break;
                        }
                    case 64:
                        if (zzT(obj, i3, i2)) {
                            zzx2 = zzbi.zzx(i3 << 3);
                            zzn = zzx2 + 4;
                            i += zzn;
                            break;
                        } else {
                            break;
                        }
                    case 65:
                        if (zzT(obj, i3, i2)) {
                            zzx = zzbi.zzx(i3 << 3);
                            zzn = zzx + 8;
                            i += zzn;
                            break;
                        } else {
                            break;
                        }
                    case 66:
                        if (zzT(obj, i3, i2)) {
                            int zzp = zzp(obj, j);
                            zzx3 = zzbi.zzx(i3 << 3);
                            zzy = zzbi.zzx((zzp >> 31) ^ (zzp + zzp));
                            i += zzx3 + zzy;
                            break;
                        } else {
                            break;
                        }
                    case 67:
                        if (zzT(obj, i3, i2)) {
                            long zzz = zzz(obj, j);
                            zzx7 = zzbi.zzx(i3 << 3);
                            zzy2 = zzbi.zzy((zzz + zzz) ^ (zzz >> 63));
                            zzn = zzx7 + zzy2;
                            i += zzn;
                            break;
                        } else {
                            break;
                        }
                    case 68:
                        if (zzT(obj, i3, i2)) {
                            zzn = zzbi.zzt(i3, (zzdf) zzeq.zzf(obj, j), zzB(i2));
                            i += zzn;
                            break;
                        } else {
                            break;
                        }
                }
            }
            zzeg zzegVar = this.zzn;
            return i + zzegVar.zza(zzegVar.zzd(obj));
        }
        return zzo(obj);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:4:0x001c. Please report as an issue. */
    @Override // com.google.android.gms.internal.play_billing.zzdp
    public final int zzb(Object obj) {
        int i;
        long doubleToLongBits;
        int i2;
        int floatToIntBits;
        int length = this.zzc.length;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4 += 3) {
            int zzy = zzy(i4);
            int i5 = this.zzc[i4];
            long j = 1048575 & zzy;
            int i6 = 37;
            switch (zzx(zzy)) {
                case 0:
                    i = i3 * 53;
                    doubleToLongBits = Double.doubleToLongBits(zzeq.zza(obj, j));
                    byte[] bArr = zzcg.zzd;
                    i3 = i + ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32)));
                    break;
                case 1:
                    i2 = i3 * 53;
                    floatToIntBits = Float.floatToIntBits(zzeq.zzb(obj, j));
                    i3 = i2 + floatToIntBits;
                    break;
                case 2:
                    i = i3 * 53;
                    doubleToLongBits = zzeq.zzd(obj, j);
                    byte[] bArr2 = zzcg.zzd;
                    i3 = i + ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32)));
                    break;
                case 3:
                    i = i3 * 53;
                    doubleToLongBits = zzeq.zzd(obj, j);
                    byte[] bArr3 = zzcg.zzd;
                    i3 = i + ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32)));
                    break;
                case 4:
                    i2 = i3 * 53;
                    floatToIntBits = zzeq.zzc(obj, j);
                    i3 = i2 + floatToIntBits;
                    break;
                case 5:
                    i = i3 * 53;
                    doubleToLongBits = zzeq.zzd(obj, j);
                    byte[] bArr4 = zzcg.zzd;
                    i3 = i + ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32)));
                    break;
                case 6:
                    i2 = i3 * 53;
                    floatToIntBits = zzeq.zzc(obj, j);
                    i3 = i2 + floatToIntBits;
                    break;
                case 7:
                    i2 = i3 * 53;
                    floatToIntBits = zzcg.zza(zzeq.zzw(obj, j));
                    i3 = i2 + floatToIntBits;
                    break;
                case 8:
                    i2 = i3 * 53;
                    floatToIntBits = ((String) zzeq.zzf(obj, j)).hashCode();
                    i3 = i2 + floatToIntBits;
                    break;
                case 9:
                    Object zzf = zzeq.zzf(obj, j);
                    if (zzf != null) {
                        i6 = zzf.hashCode();
                    }
                    i3 = (i3 * 53) + i6;
                    break;
                case 10:
                    i2 = i3 * 53;
                    floatToIntBits = zzeq.zzf(obj, j).hashCode();
                    i3 = i2 + floatToIntBits;
                    break;
                case 11:
                    i2 = i3 * 53;
                    floatToIntBits = zzeq.zzc(obj, j);
                    i3 = i2 + floatToIntBits;
                    break;
                case 12:
                    i2 = i3 * 53;
                    floatToIntBits = zzeq.zzc(obj, j);
                    i3 = i2 + floatToIntBits;
                    break;
                case 13:
                    i2 = i3 * 53;
                    floatToIntBits = zzeq.zzc(obj, j);
                    i3 = i2 + floatToIntBits;
                    break;
                case 14:
                    i = i3 * 53;
                    doubleToLongBits = zzeq.zzd(obj, j);
                    byte[] bArr5 = zzcg.zzd;
                    i3 = i + ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32)));
                    break;
                case 15:
                    i2 = i3 * 53;
                    floatToIntBits = zzeq.zzc(obj, j);
                    i3 = i2 + floatToIntBits;
                    break;
                case 16:
                    i = i3 * 53;
                    doubleToLongBits = zzeq.zzd(obj, j);
                    byte[] bArr6 = zzcg.zzd;
                    i3 = i + ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32)));
                    break;
                case 17:
                    Object zzf2 = zzeq.zzf(obj, j);
                    if (zzf2 != null) {
                        i6 = zzf2.hashCode();
                    }
                    i3 = (i3 * 53) + i6;
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
                    i2 = i3 * 53;
                    floatToIntBits = zzeq.zzf(obj, j).hashCode();
                    i3 = i2 + floatToIntBits;
                    break;
                case 50:
                    i2 = i3 * 53;
                    floatToIntBits = zzeq.zzf(obj, j).hashCode();
                    i3 = i2 + floatToIntBits;
                    break;
                case 51:
                    if (zzT(obj, i5, i4)) {
                        i = i3 * 53;
                        doubleToLongBits = Double.doubleToLongBits(zzm(obj, j));
                        byte[] bArr7 = zzcg.zzd;
                        i3 = i + ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32)));
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zzT(obj, i5, i4)) {
                        i2 = i3 * 53;
                        floatToIntBits = Float.floatToIntBits(zzn(obj, j));
                        i3 = i2 + floatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zzT(obj, i5, i4)) {
                        i = i3 * 53;
                        doubleToLongBits = zzz(obj, j);
                        byte[] bArr8 = zzcg.zzd;
                        i3 = i + ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32)));
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zzT(obj, i5, i4)) {
                        i = i3 * 53;
                        doubleToLongBits = zzz(obj, j);
                        byte[] bArr9 = zzcg.zzd;
                        i3 = i + ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32)));
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zzT(obj, i5, i4)) {
                        i2 = i3 * 53;
                        floatToIntBits = zzp(obj, j);
                        i3 = i2 + floatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zzT(obj, i5, i4)) {
                        i = i3 * 53;
                        doubleToLongBits = zzz(obj, j);
                        byte[] bArr10 = zzcg.zzd;
                        i3 = i + ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32)));
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zzT(obj, i5, i4)) {
                        i2 = i3 * 53;
                        floatToIntBits = zzp(obj, j);
                        i3 = i2 + floatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zzT(obj, i5, i4)) {
                        i2 = i3 * 53;
                        floatToIntBits = zzcg.zza(zzU(obj, j));
                        i3 = i2 + floatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (zzT(obj, i5, i4)) {
                        i2 = i3 * 53;
                        floatToIntBits = ((String) zzeq.zzf(obj, j)).hashCode();
                        i3 = i2 + floatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (zzT(obj, i5, i4)) {
                        i2 = i3 * 53;
                        floatToIntBits = zzeq.zzf(obj, j).hashCode();
                        i3 = i2 + floatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zzT(obj, i5, i4)) {
                        i2 = i3 * 53;
                        floatToIntBits = zzeq.zzf(obj, j).hashCode();
                        i3 = i2 + floatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zzT(obj, i5, i4)) {
                        i2 = i3 * 53;
                        floatToIntBits = zzp(obj, j);
                        i3 = i2 + floatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zzT(obj, i5, i4)) {
                        i2 = i3 * 53;
                        floatToIntBits = zzp(obj, j);
                        i3 = i2 + floatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zzT(obj, i5, i4)) {
                        i2 = i3 * 53;
                        floatToIntBits = zzp(obj, j);
                        i3 = i2 + floatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zzT(obj, i5, i4)) {
                        i = i3 * 53;
                        doubleToLongBits = zzz(obj, j);
                        byte[] bArr11 = zzcg.zzd;
                        i3 = i + ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32)));
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (zzT(obj, i5, i4)) {
                        i2 = i3 * 53;
                        floatToIntBits = zzp(obj, j);
                        i3 = i2 + floatToIntBits;
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (zzT(obj, i5, i4)) {
                        i = i3 * 53;
                        doubleToLongBits = zzz(obj, j);
                        byte[] bArr12 = zzcg.zzd;
                        i3 = i + ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32)));
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (zzT(obj, i5, i4)) {
                        i2 = i3 * 53;
                        floatToIntBits = zzeq.zzf(obj, j).hashCode();
                        i3 = i2 + floatToIntBits;
                        break;
                    } else {
                        break;
                    }
            }
        }
        int hashCode = (i3 * 53) + this.zzn.zzd(obj).hashCode();
        if (!this.zzh) {
            return hashCode;
        }
        this.zzo.zza(obj);
        throw null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0401, code lost:
    
        if (r6 == 1048575) goto L143;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0403, code lost:
    
        r27.putInt(r12, r6, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0409, code lost:
    
        r2 = r8.zzk;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x040d, code lost:
    
        if (r2 >= r8.zzl) goto L217;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x040f, code lost:
    
        r4 = r8.zzj[r2];
        r5 = r8.zzc[r4];
        r5 = com.google.android.gms.internal.play_billing.zzeq.zzf(r12, r8.zzy(r4) & 1048575);
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0421, code lost:
    
        if (r5 != null) goto L149;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0428, code lost:
    
        if (r8.zzA(r4) != null) goto L216;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x042d, code lost:
    
        r5 = (com.google.android.gms.internal.play_billing.zzcz) r5;
        r0 = (com.google.android.gms.internal.play_billing.zzcy) r8.zzC(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0435, code lost:
    
        throw null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x042a, code lost:
    
        r2 = r2 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0436, code lost:
    
        if (r9 != 0) goto L160;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x043a, code lost:
    
        if (r0 != r32) goto L158;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0441, code lost:
    
        throw com.google.android.gms.internal.play_billing.zzci.zze();
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0448, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0444, code lost:
    
        if (r0 > r32) goto L164;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0446, code lost:
    
        if (r3 != r9) goto L164;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x044d, code lost:
    
        throw com.google.android.gms.internal.play_billing.zzci.zze();
     */
    /* JADX WARN: Failed to find 'out' block for switch in B:112:0x0092. Please report as an issue. */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int zzc(Object obj, byte[] bArr, int i, int i2, int i3, zzan zzanVar) throws IOException {
        Unsafe unsafe;
        int i4;
        Object obj2;
        zzdi<T> zzdiVar;
        int i5;
        int zzt;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        Object obj3;
        int i12;
        zzan zzanVar2;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        int i20;
        int i21;
        int i22;
        int i23;
        int i24;
        zzdi<T> zzdiVar2 = this;
        Object obj4 = obj;
        byte[] bArr2 = bArr;
        int i25 = i2;
        int i26 = i3;
        zzan zzanVar3 = zzanVar;
        zzG(obj);
        Unsafe unsafe2 = zzb;
        int i27 = -1;
        int i28 = i;
        int i29 = -1;
        int i30 = 0;
        int i31 = 0;
        int i32 = 0;
        int i33 = 1048575;
        while (true) {
            if (i28 < i25) {
                int i34 = i28 + 1;
                byte b = bArr2[i28];
                if (b < 0) {
                    int zzk = zzao.zzk(b, bArr2, i34, zzanVar3);
                    i5 = zzanVar3.zza;
                    i34 = zzk;
                } else {
                    i5 = b;
                }
                int i35 = i5 >>> 3;
                if (i35 > i29) {
                    zzt = zzdiVar2.zzu(i35, i30 / 3);
                } else {
                    zzt = zzdiVar2.zzt(i35);
                }
                int i36 = zzt;
                if (i36 == i27) {
                    i6 = i35;
                    i7 = i34;
                    i8 = i5;
                    i9 = i32;
                    unsafe = unsafe2;
                    i10 = i26;
                    i11 = 0;
                } else {
                    int i37 = i5 & 7;
                    int[] iArr = zzdiVar2.zzc;
                    int i38 = iArr[i36 + 1];
                    int zzx = zzx(i38);
                    int i39 = i34;
                    int i40 = i5;
                    long j = i38 & 1048575;
                    if (zzx <= 17) {
                        int i41 = iArr[i36 + 2];
                        int i42 = 1 << (i41 >>> 20);
                        int i43 = i41 & 1048575;
                        if (i43 != i33) {
                            if (i33 != 1048575) {
                                unsafe2.putInt(obj4, i33, i32);
                            }
                            i14 = i43;
                            i13 = unsafe2.getInt(obj4, i43);
                        } else {
                            i13 = i32;
                            i14 = i33;
                        }
                        switch (zzx) {
                            case 0:
                                i15 = i36;
                                i16 = i35;
                                i17 = i39;
                                if (i37 != 1) {
                                    i22 = i15;
                                    i10 = i3;
                                    i9 = i13;
                                    unsafe = unsafe2;
                                    i6 = i16;
                                    i11 = i22;
                                    i7 = i17;
                                    i8 = i40;
                                    i33 = i14;
                                    break;
                                } else {
                                    zzeq.zzo(obj4, j, Double.longBitsToDouble(zzao.zzp(bArr2, i17)));
                                    i28 = i17 + 8;
                                    i32 = i13 | i42;
                                    i25 = i2;
                                    i30 = i15;
                                    i29 = i16;
                                    i31 = i40;
                                    i33 = i14;
                                    i27 = -1;
                                    i26 = i3;
                                }
                            case 1:
                                i15 = i36;
                                i16 = i35;
                                i17 = i39;
                                if (i37 != 5) {
                                    i22 = i15;
                                    i10 = i3;
                                    i9 = i13;
                                    unsafe = unsafe2;
                                    i6 = i16;
                                    i11 = i22;
                                    i7 = i17;
                                    i8 = i40;
                                    i33 = i14;
                                    break;
                                } else {
                                    zzeq.zzp(obj4, j, Float.intBitsToFloat(zzao.zzb(bArr2, i17)));
                                    i28 = i17 + 4;
                                    i32 = i13 | i42;
                                    i25 = i2;
                                    i30 = i15;
                                    i29 = i16;
                                    i31 = i40;
                                    i33 = i14;
                                    i27 = -1;
                                    i26 = i3;
                                }
                            case 2:
                            case 3:
                                i15 = i36;
                                i16 = i35;
                                i17 = i39;
                                if (i37 != 0) {
                                    i22 = i15;
                                    i10 = i3;
                                    i9 = i13;
                                    unsafe = unsafe2;
                                    i6 = i16;
                                    i11 = i22;
                                    i7 = i17;
                                    i8 = i40;
                                    i33 = i14;
                                    break;
                                } else {
                                    int zzm = zzao.zzm(bArr2, i17, zzanVar3);
                                    unsafe2.putLong(obj, j, zzanVar3.zzb);
                                    i32 = i13 | i42;
                                    i30 = i15;
                                    i29 = i16;
                                    i28 = zzm;
                                    i31 = i40;
                                    i33 = i14;
                                    i27 = -1;
                                    i25 = i2;
                                    i26 = i3;
                                }
                            case 4:
                            case 11:
                                i15 = i36;
                                i16 = i35;
                                i17 = i39;
                                if (i37 != 0) {
                                    i22 = i15;
                                    i10 = i3;
                                    i9 = i13;
                                    unsafe = unsafe2;
                                    i6 = i16;
                                    i11 = i22;
                                    i7 = i17;
                                    i8 = i40;
                                    i33 = i14;
                                    break;
                                } else {
                                    i28 = zzao.zzj(bArr2, i17, zzanVar3);
                                    unsafe2.putInt(obj4, j, zzanVar3.zza);
                                    i32 = i13 | i42;
                                    i25 = i2;
                                    i30 = i15;
                                    i29 = i16;
                                    i31 = i40;
                                    i33 = i14;
                                    i27 = -1;
                                    i26 = i3;
                                }
                            case 5:
                            case 14:
                                i15 = i36;
                                i16 = i35;
                                i18 = i39;
                                if (i37 != 1) {
                                    i17 = i18;
                                    i22 = i15;
                                    i10 = i3;
                                    i9 = i13;
                                    unsafe = unsafe2;
                                    i6 = i16;
                                    i11 = i22;
                                    i7 = i17;
                                    i8 = i40;
                                    i33 = i14;
                                    break;
                                } else {
                                    i17 = i18;
                                    unsafe2.putLong(obj, j, zzao.zzp(bArr2, i18));
                                    i28 = i17 + 8;
                                    i32 = i13 | i42;
                                    i25 = i2;
                                    i30 = i15;
                                    i29 = i16;
                                    i31 = i40;
                                    i33 = i14;
                                    i27 = -1;
                                    i26 = i3;
                                }
                            case 6:
                            case 13:
                                i15 = i36;
                                i16 = i35;
                                i18 = i39;
                                if (i37 != 5) {
                                    i17 = i18;
                                    i22 = i15;
                                    i10 = i3;
                                    i9 = i13;
                                    unsafe = unsafe2;
                                    i6 = i16;
                                    i11 = i22;
                                    i7 = i17;
                                    i8 = i40;
                                    i33 = i14;
                                    break;
                                } else {
                                    unsafe2.putInt(obj4, j, zzao.zzb(bArr2, i18));
                                    i28 = i18 + 4;
                                    i32 = i13 | i42;
                                    i25 = i2;
                                    i30 = i15;
                                    i29 = i16;
                                    i31 = i40;
                                    i33 = i14;
                                    i27 = -1;
                                    i26 = i3;
                                }
                            case 7:
                                i15 = i36;
                                i16 = i35;
                                i18 = i39;
                                if (i37 != 0) {
                                    i17 = i18;
                                    i22 = i15;
                                    i10 = i3;
                                    i9 = i13;
                                    unsafe = unsafe2;
                                    i6 = i16;
                                    i11 = i22;
                                    i7 = i17;
                                    i8 = i40;
                                    i33 = i14;
                                    break;
                                } else {
                                    i28 = zzao.zzm(bArr2, i18, zzanVar3);
                                    zzeq.zzm(obj4, j, zzanVar3.zzb != 0);
                                    i32 = i13 | i42;
                                    i25 = i2;
                                    i30 = i15;
                                    i29 = i16;
                                    i31 = i40;
                                    i33 = i14;
                                    i27 = -1;
                                    i26 = i3;
                                }
                            case 8:
                                i15 = i36;
                                i16 = i35;
                                i18 = i39;
                                if (i37 != 2) {
                                    i17 = i18;
                                    i22 = i15;
                                    i10 = i3;
                                    i9 = i13;
                                    unsafe = unsafe2;
                                    i6 = i16;
                                    i11 = i22;
                                    i7 = i17;
                                    i8 = i40;
                                    i33 = i14;
                                    break;
                                } else {
                                    if ((i38 & 536870912) == 0) {
                                        i28 = zzao.zzg(bArr2, i18, zzanVar3);
                                    } else {
                                        i28 = zzao.zzh(bArr2, i18, zzanVar3);
                                    }
                                    unsafe2.putObject(obj4, j, zzanVar3.zzc);
                                    i32 = i13 | i42;
                                    i25 = i2;
                                    i30 = i15;
                                    i29 = i16;
                                    i31 = i40;
                                    i33 = i14;
                                    i27 = -1;
                                    i26 = i3;
                                }
                            case 9:
                                i15 = i36;
                                i16 = i35;
                                i18 = i39;
                                if (i37 != 2) {
                                    i40 = i40;
                                    i17 = i18;
                                    i22 = i15;
                                    i10 = i3;
                                    i9 = i13;
                                    unsafe = unsafe2;
                                    i6 = i16;
                                    i11 = i22;
                                    i7 = i17;
                                    i8 = i40;
                                    i33 = i14;
                                    break;
                                } else {
                                    Object zzD = zzdiVar2.zzD(obj4, i15);
                                    i40 = i40;
                                    i28 = zzao.zzo(zzD, zzdiVar2.zzB(i15), bArr, i18, i2, zzanVar);
                                    zzdiVar2.zzL(obj4, i15, zzD);
                                    i32 = i13 | i42;
                                    i25 = i2;
                                    i30 = i15;
                                    i29 = i16;
                                    i31 = i40;
                                    i33 = i14;
                                    i27 = -1;
                                    i26 = i3;
                                }
                            case 10:
                                i19 = i36;
                                i16 = i35;
                                i20 = i40;
                                i21 = i39;
                                if (i37 != 2) {
                                    i22 = i19;
                                    i40 = i20;
                                    i17 = i21;
                                    i10 = i3;
                                    i9 = i13;
                                    unsafe = unsafe2;
                                    i6 = i16;
                                    i11 = i22;
                                    i7 = i17;
                                    i8 = i40;
                                    i33 = i14;
                                    break;
                                } else {
                                    i28 = zzao.zza(bArr2, i21, zzanVar3);
                                    unsafe2.putObject(obj4, j, zzanVar3.zzc);
                                    i32 = i13 | i42;
                                    i30 = i19;
                                    i29 = i16;
                                    i31 = i20;
                                    i33 = i14;
                                    i27 = -1;
                                    i25 = i2;
                                    i26 = i3;
                                }
                            case 12:
                                i19 = i36;
                                i16 = i35;
                                i20 = i40;
                                i21 = i39;
                                if (i37 != 0) {
                                    i22 = i19;
                                    i40 = i20;
                                    i17 = i21;
                                    i10 = i3;
                                    i9 = i13;
                                    unsafe = unsafe2;
                                    i6 = i16;
                                    i11 = i22;
                                    i7 = i17;
                                    i8 = i40;
                                    i33 = i14;
                                    break;
                                } else {
                                    i28 = zzao.zzj(bArr2, i21, zzanVar3);
                                    int i44 = zzanVar3.zza;
                                    zzce zzA = zzdiVar2.zzA(i19);
                                    if (zzA == null || zzA.zza(i44)) {
                                        unsafe2.putInt(obj4, j, i44);
                                        i32 = i13 | i42;
                                        i30 = i19;
                                        i29 = i16;
                                        i31 = i20;
                                        i33 = i14;
                                        i27 = -1;
                                        i25 = i2;
                                        i26 = i3;
                                    } else {
                                        zzd(obj).zzj(i20, Long.valueOf(i44));
                                        i30 = i19;
                                        i32 = i13;
                                        i29 = i16;
                                        i31 = i20;
                                        i33 = i14;
                                        i27 = -1;
                                        i25 = i2;
                                        i26 = i3;
                                    }
                                }
                                break;
                            case 15:
                                i19 = i36;
                                i16 = i35;
                                i20 = i40;
                                i21 = i39;
                                if (i37 != 0) {
                                    i22 = i19;
                                    i40 = i20;
                                    i17 = i21;
                                    i10 = i3;
                                    i9 = i13;
                                    unsafe = unsafe2;
                                    i6 = i16;
                                    i11 = i22;
                                    i7 = i17;
                                    i8 = i40;
                                    i33 = i14;
                                    break;
                                } else {
                                    i28 = zzao.zzj(bArr2, i21, zzanVar3);
                                    unsafe2.putInt(obj4, j, zzbe.zzb(zzanVar3.zza));
                                    i32 = i13 | i42;
                                    i30 = i19;
                                    i29 = i16;
                                    i31 = i20;
                                    i33 = i14;
                                    i27 = -1;
                                    i25 = i2;
                                    i26 = i3;
                                }
                            case 16:
                                if (i37 != 0) {
                                    i16 = i35;
                                    i22 = i36;
                                    i17 = i39;
                                    i10 = i3;
                                    i9 = i13;
                                    unsafe = unsafe2;
                                    i6 = i16;
                                    i11 = i22;
                                    i7 = i17;
                                    i8 = i40;
                                    i33 = i14;
                                    break;
                                } else {
                                    int zzm2 = zzao.zzm(bArr2, i39, zzanVar3);
                                    unsafe2.putLong(obj, j, zzbe.zzc(zzanVar3.zzb));
                                    i32 = i13 | i42;
                                    i30 = i36;
                                    i29 = i35;
                                    i31 = i40;
                                    i28 = zzm2;
                                    i33 = i14;
                                    i27 = -1;
                                    i25 = i2;
                                    i26 = i3;
                                }
                            default:
                                i15 = i36;
                                i16 = i35;
                                i17 = i39;
                                if (i37 != 3) {
                                    i22 = i15;
                                    i10 = i3;
                                    i9 = i13;
                                    unsafe = unsafe2;
                                    i6 = i16;
                                    i11 = i22;
                                    i7 = i17;
                                    i8 = i40;
                                    i33 = i14;
                                    break;
                                } else {
                                    Object zzD2 = zzdiVar2.zzD(obj4, i15);
                                    i28 = zzao.zzn(zzD2, zzdiVar2.zzB(i15), bArr, i17, i2, (i16 << 3) | 4, zzanVar);
                                    zzdiVar2.zzL(obj4, i15, zzD2);
                                    i32 = i13 | i42;
                                    i25 = i2;
                                    i29 = i16;
                                    i30 = i15;
                                    i31 = i40;
                                    i33 = i14;
                                    i27 = -1;
                                    bArr2 = bArr;
                                    i26 = i3;
                                }
                        }
                    } else if (zzx != 27) {
                        i9 = i32;
                        i23 = i33;
                        if (zzx <= 49) {
                            i6 = i35;
                            unsafe = unsafe2;
                            i11 = i36;
                            i28 = zzs(obj, bArr, i39, i2, i40, i35, i37, i36, i38, zzx, j, zzanVar);
                            if (i28 != i39) {
                                zzdiVar2 = this;
                                obj4 = obj;
                                bArr2 = bArr;
                                i29 = i6;
                                i25 = i2;
                                i26 = i3;
                                zzanVar3 = zzanVar;
                                i32 = i9;
                                i31 = i40;
                                i30 = i11;
                                i33 = i23;
                                unsafe2 = unsafe;
                                i27 = -1;
                            } else {
                                i7 = i28;
                                i8 = i40;
                                i33 = i23;
                                i10 = i3;
                            }
                        } else {
                            i6 = i35;
                            unsafe = unsafe2;
                            i11 = i36;
                            i24 = i39;
                            if (zzx != 50) {
                                i28 = zzr(obj, bArr, i24, i2, i40, i6, i37, i38, zzx, j, i11, zzanVar);
                                if (i28 != i24) {
                                    zzdiVar2 = this;
                                    obj4 = obj;
                                    bArr2 = bArr;
                                    i29 = i6;
                                    i25 = i2;
                                    i26 = i3;
                                    zzanVar3 = zzanVar;
                                    i32 = i9;
                                    i31 = i40;
                                    i30 = i11;
                                    i33 = i23;
                                    unsafe2 = unsafe;
                                    i27 = -1;
                                } else {
                                    i7 = i28;
                                    i8 = i40;
                                    i33 = i23;
                                    i10 = i3;
                                }
                            } else if (i37 == 2) {
                                i28 = zzq(obj, bArr, i24, i2, i11, j, zzanVar);
                                if (i28 != i24) {
                                    zzdiVar2 = this;
                                    obj4 = obj;
                                    bArr2 = bArr;
                                    i29 = i6;
                                    i25 = i2;
                                    i26 = i3;
                                    zzanVar3 = zzanVar;
                                    i32 = i9;
                                    i31 = i40;
                                    i30 = i11;
                                    i33 = i23;
                                    unsafe2 = unsafe;
                                    i27 = -1;
                                } else {
                                    i7 = i28;
                                    i8 = i40;
                                    i33 = i23;
                                    i10 = i3;
                                }
                            } else {
                                i10 = i3;
                                i7 = i24;
                                i8 = i40;
                                i33 = i23;
                            }
                        }
                    } else if (i37 == 2) {
                        zzcf zzcfVar = (zzcf) unsafe2.getObject(obj4, j);
                        if (!zzcfVar.zzc()) {
                            int size = zzcfVar.size();
                            zzcfVar = zzcfVar.zzd(size == 0 ? 10 : size + size);
                            unsafe2.putObject(obj4, j, zzcfVar);
                        }
                        i28 = zzao.zze(zzdiVar2.zzB(i36), i40, bArr, i39, i2, zzcfVar, zzanVar);
                        i25 = i2;
                        i26 = i3;
                        i29 = i35;
                        i30 = i36;
                        i32 = i32;
                        i31 = i40;
                        i33 = i33;
                        i27 = -1;
                        bArr2 = bArr;
                    } else {
                        i9 = i32;
                        i23 = i33;
                        i6 = i35;
                        unsafe = unsafe2;
                        i11 = i36;
                        i24 = i39;
                        i10 = i3;
                        i7 = i24;
                        i8 = i40;
                        i33 = i23;
                    }
                }
                if (i8 != i10 || i10 == 0) {
                    int i45 = i10;
                    if (this.zzh) {
                        zzanVar2 = zzanVar;
                        zzbn zzbnVar = zzanVar2.zzd;
                        if (zzbnVar != zzbn.zza) {
                            i12 = i6;
                            if (zzbnVar.zzb(this.zzg, i12) == null) {
                                i28 = zzao.zzi(i8, bArr, i7, i2, zzd(obj), zzanVar);
                                obj3 = obj;
                                i25 = i2;
                                i31 = i8;
                                zzdiVar2 = this;
                                i29 = i12;
                                obj4 = obj3;
                                i32 = i9;
                                i30 = i11;
                                i27 = -1;
                                bArr2 = bArr;
                                i26 = i45;
                                zzanVar3 = zzanVar2;
                                unsafe2 = unsafe;
                            } else {
                                throw null;
                            }
                        } else {
                            obj3 = obj;
                            i12 = i6;
                        }
                    } else {
                        obj3 = obj;
                        i12 = i6;
                        zzanVar2 = zzanVar;
                    }
                    i28 = zzao.zzi(i8, bArr, i7, i2, zzd(obj), zzanVar);
                    i25 = i2;
                    i31 = i8;
                    zzdiVar2 = this;
                    i29 = i12;
                    obj4 = obj3;
                    i32 = i9;
                    i30 = i11;
                    i27 = -1;
                    bArr2 = bArr;
                    i26 = i45;
                    zzanVar3 = zzanVar2;
                    unsafe2 = unsafe;
                } else {
                    zzdiVar = this;
                    obj2 = obj;
                    i4 = i10;
                    i28 = i7;
                    i31 = i8;
                    i32 = i9;
                }
            } else {
                unsafe = unsafe2;
                i4 = i26;
                obj2 = obj4;
                zzdiVar = zzdiVar2;
            }
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzdp
    public final Object zze() {
        return ((zzcb) this.zzg).zzh();
    }

    @Override // com.google.android.gms.internal.play_billing.zzdp
    public final void zzf(Object obj) {
        if (zzS(obj)) {
            if (obj instanceof zzcb) {
                zzcb zzcbVar = (zzcb) obj;
                zzcbVar.zzp(Integer.MAX_VALUE);
                zzcbVar.zza = 0;
                zzcbVar.zzn();
            }
            int length = this.zzc.length;
            for (int i = 0; i < length; i += 3) {
                int zzy = zzy(i);
                int i2 = 1048575 & zzy;
                int zzx = zzx(zzy);
                long j = i2;
                if (zzx != 9) {
                    if (zzx == 60 || zzx == 68) {
                        if (zzT(obj, this.zzc[i], i)) {
                            zzB(i).zzf(zzb.getObject(obj, j));
                        }
                    } else {
                        switch (zzx) {
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
                                this.zzm.zza(obj, j);
                                break;
                            case 50:
                                Unsafe unsafe = zzb;
                                Object object = unsafe.getObject(obj, j);
                                if (object != null) {
                                    ((zzcz) object).zzc();
                                    unsafe.putObject(obj, j, object);
                                    break;
                                } else {
                                    break;
                                }
                        }
                    }
                }
                if (zzP(obj, i)) {
                    zzB(i).zzf(zzb.getObject(obj, j));
                }
            }
            this.zzn.zzg(obj);
            if (this.zzh) {
                this.zzo.zzb(obj);
            }
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzdp
    public final void zzg(Object obj, Object obj2) {
        zzG(obj);
        Objects.requireNonNull(obj2);
        for (int i = 0; i < this.zzc.length; i += 3) {
            int zzy = zzy(i);
            int i2 = this.zzc[i];
            long j = 1048575 & zzy;
            switch (zzx(zzy)) {
                case 0:
                    if (zzP(obj2, i)) {
                        zzeq.zzo(obj, j, zzeq.zza(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (zzP(obj2, i)) {
                        zzeq.zzp(obj, j, zzeq.zzb(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (zzP(obj2, i)) {
                        zzeq.zzr(obj, j, zzeq.zzd(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (zzP(obj2, i)) {
                        zzeq.zzr(obj, j, zzeq.zzd(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (zzP(obj2, i)) {
                        zzeq.zzq(obj, j, zzeq.zzc(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (zzP(obj2, i)) {
                        zzeq.zzr(obj, j, zzeq.zzd(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (zzP(obj2, i)) {
                        zzeq.zzq(obj, j, zzeq.zzc(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (zzP(obj2, i)) {
                        zzeq.zzm(obj, j, zzeq.zzw(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (zzP(obj2, i)) {
                        zzeq.zzs(obj, j, zzeq.zzf(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 9:
                    zzH(obj, obj2, i);
                    break;
                case 10:
                    if (zzP(obj2, i)) {
                        zzeq.zzs(obj, j, zzeq.zzf(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (zzP(obj2, i)) {
                        zzeq.zzq(obj, j, zzeq.zzc(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (zzP(obj2, i)) {
                        zzeq.zzq(obj, j, zzeq.zzc(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (zzP(obj2, i)) {
                        zzeq.zzq(obj, j, zzeq.zzc(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (zzP(obj2, i)) {
                        zzeq.zzr(obj, j, zzeq.zzd(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (zzP(obj2, i)) {
                        zzeq.zzq(obj, j, zzeq.zzc(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (zzP(obj2, i)) {
                        zzeq.zzr(obj, j, zzeq.zzd(obj2, j));
                        zzJ(obj, i);
                        break;
                    } else {
                        break;
                    }
                case 17:
                    zzH(obj, obj2, i);
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
                    this.zzm.zzb(obj, obj2, j);
                    break;
                case 50:
                    int i3 = zzdr.zza;
                    zzeq.zzs(obj, j, zzda.zzb(zzeq.zzf(obj, j), zzeq.zzf(obj2, j)));
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
                    if (zzT(obj2, i2, i)) {
                        zzeq.zzs(obj, j, zzeq.zzf(obj2, j));
                        zzK(obj, i2, i);
                        break;
                    } else {
                        break;
                    }
                case 60:
                    zzI(obj, obj2, i);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                    if (zzT(obj2, i2, i)) {
                        zzeq.zzs(obj, j, zzeq.zzf(obj2, j));
                        zzK(obj, i2, i);
                        break;
                    } else {
                        break;
                    }
                case 68:
                    zzI(obj, obj2, i);
                    break;
            }
        }
        zzdr.zzC(this.zzn, obj, obj2);
        if (this.zzh) {
            this.zzo.zza(obj2);
            throw null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x02ed, code lost:
    
        if (r0 != r24) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x02ef, code lost:
    
        r14 = r31;
        r12 = r32;
        r13 = r34;
        r11 = r35;
        r2 = r15;
        r1 = r23;
        r6 = r25;
        r7 = r26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0300, code lost:
    
        r2 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x032c, code lost:
    
        if (r0 != r14) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x034f, code lost:
    
        if (r0 != r14) goto L103;
     */
    /* JADX WARN: Failed to find 'out' block for switch in B:63:0x0095. Please report as an issue. */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v12, types: [int] */
    @Override // com.google.android.gms.internal.play_billing.zzdp
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void zzh(Object obj, byte[] bArr, int i, int i2, zzan zzanVar) throws IOException {
        byte b;
        int i3;
        int zzt;
        int i4;
        int i5;
        Unsafe unsafe;
        int i6;
        Unsafe unsafe2;
        int i7;
        Unsafe unsafe3;
        zzdi<T> zzdiVar;
        Unsafe unsafe4;
        int i8;
        int i9;
        int i10;
        zzdi<T> zzdiVar2 = this;
        Object obj2 = obj;
        byte[] bArr2 = bArr;
        int i11 = i2;
        zzan zzanVar2 = zzanVar;
        if (!zzdiVar2.zzi) {
            zzc(obj, bArr, i, i2, 0, zzanVar);
            return;
        }
        zzG(obj);
        Unsafe unsafe5 = zzb;
        int i12 = -1;
        int i13 = 1048575;
        int i14 = i;
        int i15 = -1;
        int i16 = 0;
        int i17 = 0;
        int i18 = 1048575;
        while (i14 < i11) {
            int i19 = i14 + 1;
            byte b2 = bArr2[i14];
            if (b2 < 0) {
                i3 = zzao.zzk(b2, bArr2, i19, zzanVar2);
                b = zzanVar2.zza;
            } else {
                b = b2;
                i3 = i19;
            }
            int i20 = b >>> 3;
            if (i20 > i15) {
                zzt = zzdiVar2.zzu(i20, i16 / 3);
            } else {
                zzt = zzdiVar2.zzt(i20);
            }
            int i21 = zzt;
            if (i21 == i12) {
                i4 = i3;
                i5 = i20;
                unsafe = unsafe5;
                i6 = 0;
            } else {
                int i22 = b & 7;
                int[] iArr = zzdiVar2.zzc;
                int i23 = iArr[i21 + 1];
                int zzx = zzx(i23);
                Unsafe unsafe6 = unsafe5;
                long j = i23 & i13;
                if (zzx <= 17) {
                    int i24 = iArr[i21 + 2];
                    int i25 = 1 << (i24 >>> 20);
                    int i26 = i24 & 1048575;
                    if (i26 != i18) {
                        if (i18 != 1048575) {
                            long j2 = i18;
                            unsafe4 = unsafe6;
                            unsafe4.putInt(obj2, j2, i17);
                        } else {
                            unsafe4 = unsafe6;
                        }
                        if (i26 != 1048575) {
                            i17 = unsafe4.getInt(obj2, i26);
                        }
                        unsafe2 = unsafe4;
                        i18 = i26;
                    } else {
                        unsafe2 = unsafe6;
                    }
                    switch (zzx) {
                        case 0:
                            zzdiVar = this;
                            i5 = i20;
                            i6 = i21;
                            i7 = i18;
                            unsafe3 = unsafe2;
                            if (i22 != 1) {
                                i4 = i3;
                                unsafe = unsafe3;
                                i18 = i7;
                                break;
                            } else {
                                zzeq.zzo(obj2, j, Double.longBitsToDouble(zzao.zzp(bArr2, i3)));
                                i14 = i3 + 8;
                                i17 |= i25;
                                unsafe5 = unsafe3;
                                i16 = i6;
                                i18 = i7;
                                i15 = i5;
                                i13 = 1048575;
                                i12 = -1;
                                zzdiVar2 = zzdiVar;
                                i11 = i2;
                                break;
                            }
                        case 1:
                            zzdiVar = this;
                            i5 = i20;
                            i6 = i21;
                            i7 = i18;
                            unsafe3 = unsafe2;
                            if (i22 != 5) {
                                i4 = i3;
                                unsafe = unsafe3;
                                i18 = i7;
                                break;
                            } else {
                                zzeq.zzp(obj2, j, Float.intBitsToFloat(zzao.zzb(bArr2, i3)));
                                i14 = i3 + 4;
                                i17 |= i25;
                                unsafe5 = unsafe3;
                                i16 = i6;
                                i18 = i7;
                                i15 = i5;
                                i13 = 1048575;
                                i12 = -1;
                                zzdiVar2 = zzdiVar;
                                i11 = i2;
                                break;
                            }
                        case 2:
                        case 3:
                            zzdiVar = this;
                            i5 = i20;
                            i6 = i21;
                            i7 = i18;
                            unsafe3 = unsafe2;
                            if (i22 != 0) {
                                i4 = i3;
                                unsafe = unsafe3;
                                i18 = i7;
                                break;
                            } else {
                                int zzm = zzao.zzm(bArr2, i3, zzanVar2);
                                unsafe3.putLong(obj, j, zzanVar2.zzb);
                                i17 |= i25;
                                unsafe5 = unsafe3;
                                i16 = i6;
                                i14 = zzm;
                                i18 = i7;
                                i15 = i5;
                                i13 = 1048575;
                                i12 = -1;
                                zzdiVar2 = zzdiVar;
                                i11 = i2;
                                break;
                            }
                        case 4:
                        case 11:
                            zzdiVar = this;
                            i5 = i20;
                            i6 = i21;
                            i7 = i18;
                            unsafe3 = unsafe2;
                            if (i22 != 0) {
                                i4 = i3;
                                unsafe = unsafe3;
                                i18 = i7;
                                break;
                            } else {
                                i14 = zzao.zzj(bArr2, i3, zzanVar2);
                                unsafe3.putInt(obj2, j, zzanVar2.zza);
                                i17 |= i25;
                                unsafe5 = unsafe3;
                                i16 = i6;
                                i18 = i7;
                                i15 = i5;
                                i13 = 1048575;
                                i12 = -1;
                                zzdiVar2 = zzdiVar;
                                i11 = i2;
                                break;
                            }
                        case 5:
                        case 14:
                            zzdiVar = this;
                            i5 = i20;
                            i6 = i21;
                            i7 = i18;
                            unsafe3 = unsafe2;
                            if (i22 != 1) {
                                i4 = i3;
                                unsafe = unsafe3;
                                i18 = i7;
                                break;
                            } else {
                                unsafe3.putLong(obj, j, zzao.zzp(bArr2, i3));
                                i14 = i3 + 8;
                                i17 |= i25;
                                unsafe5 = unsafe3;
                                i16 = i6;
                                i18 = i7;
                                i15 = i5;
                                i13 = 1048575;
                                i12 = -1;
                                zzdiVar2 = zzdiVar;
                                i11 = i2;
                                break;
                            }
                        case 6:
                        case 13:
                            zzdiVar = this;
                            i5 = i20;
                            i6 = i21;
                            i7 = i18;
                            unsafe3 = unsafe2;
                            if (i22 != 5) {
                                i4 = i3;
                                unsafe = unsafe3;
                                i18 = i7;
                                break;
                            } else {
                                unsafe3.putInt(obj2, j, zzao.zzb(bArr2, i3));
                                i14 = i3 + 4;
                                i17 |= i25;
                                unsafe5 = unsafe3;
                                i16 = i6;
                                i18 = i7;
                                i15 = i5;
                                i13 = 1048575;
                                i12 = -1;
                                zzdiVar2 = zzdiVar;
                                i11 = i2;
                                break;
                            }
                        case 7:
                            zzdiVar = this;
                            i5 = i20;
                            i6 = i21;
                            i7 = i18;
                            unsafe3 = unsafe2;
                            if (i22 != 0) {
                                i4 = i3;
                                unsafe = unsafe3;
                                i18 = i7;
                                break;
                            } else {
                                i14 = zzao.zzm(bArr2, i3, zzanVar2);
                                zzeq.zzm(obj2, j, zzanVar2.zzb != 0);
                                i17 |= i25;
                                unsafe5 = unsafe3;
                                i16 = i6;
                                i18 = i7;
                                i15 = i5;
                                i13 = 1048575;
                                i12 = -1;
                                zzdiVar2 = zzdiVar;
                                i11 = i2;
                                break;
                            }
                        case 8:
                            zzdiVar = this;
                            i5 = i20;
                            i6 = i21;
                            i7 = i18;
                            unsafe3 = unsafe2;
                            if (i22 != 2) {
                                i4 = i3;
                                unsafe = unsafe3;
                                i18 = i7;
                                break;
                            } else {
                                if ((i23 & 536870912) == 0) {
                                    i14 = zzao.zzg(bArr2, i3, zzanVar2);
                                } else {
                                    i14 = zzao.zzh(bArr2, i3, zzanVar2);
                                }
                                unsafe3.putObject(obj2, j, zzanVar2.zzc);
                                i17 |= i25;
                                unsafe5 = unsafe3;
                                i16 = i6;
                                i18 = i7;
                                i15 = i5;
                                i13 = 1048575;
                                i12 = -1;
                                zzdiVar2 = zzdiVar;
                                i11 = i2;
                                break;
                            }
                        case 9:
                            i5 = i20;
                            i6 = i21;
                            i7 = i18;
                            unsafe3 = unsafe2;
                            if (i22 != 2) {
                                i4 = i3;
                                unsafe = unsafe3;
                                i18 = i7;
                                break;
                            } else {
                                zzdiVar = this;
                                Object zzD = zzdiVar.zzD(obj2, i6);
                                i14 = zzao.zzo(zzD, zzdiVar.zzB(i6), bArr, i3, i2, zzanVar);
                                zzdiVar.zzL(obj2, i6, zzD);
                                i17 |= i25;
                                unsafe5 = unsafe3;
                                i16 = i6;
                                i18 = i7;
                                i15 = i5;
                                i13 = 1048575;
                                i12 = -1;
                                zzdiVar2 = zzdiVar;
                                i11 = i2;
                                break;
                            }
                        case 10:
                            i5 = i20;
                            i6 = i21;
                            i7 = i18;
                            unsafe3 = unsafe2;
                            if (i22 == 2) {
                                i14 = zzao.zza(bArr2, i3, zzanVar2);
                                unsafe3.putObject(obj2, j, zzanVar2.zzc);
                                i17 |= i25;
                                i11 = i2;
                                unsafe5 = unsafe3;
                                i16 = i6;
                                i18 = i7;
                                i15 = i5;
                                i13 = 1048575;
                                i12 = -1;
                                zzdiVar2 = this;
                                break;
                            }
                            i4 = i3;
                            unsafe = unsafe3;
                            i18 = i7;
                            break;
                        case 12:
                            i5 = i20;
                            i6 = i21;
                            i7 = i18;
                            unsafe3 = unsafe2;
                            if (i22 == 0) {
                                i14 = zzao.zzj(bArr2, i3, zzanVar2);
                                unsafe3.putInt(obj2, j, zzanVar2.zza);
                                i17 |= i25;
                                i11 = i2;
                                unsafe5 = unsafe3;
                                i16 = i6;
                                i18 = i7;
                                i15 = i5;
                                i13 = 1048575;
                                i12 = -1;
                                zzdiVar2 = this;
                                break;
                            }
                            i4 = i3;
                            unsafe = unsafe3;
                            i18 = i7;
                            break;
                        case 15:
                            i5 = i20;
                            i6 = i21;
                            i7 = i18;
                            unsafe3 = unsafe2;
                            if (i22 == 0) {
                                i14 = zzao.zzj(bArr2, i3, zzanVar2);
                                unsafe3.putInt(obj2, j, zzbe.zzb(zzanVar2.zza));
                                i17 |= i25;
                                i11 = i2;
                                unsafe5 = unsafe3;
                                i16 = i6;
                                i18 = i7;
                                i15 = i5;
                                i13 = 1048575;
                                i12 = -1;
                                zzdiVar2 = this;
                                break;
                            }
                            i4 = i3;
                            unsafe = unsafe3;
                            i18 = i7;
                            break;
                        case 16:
                            if (i22 != 0) {
                                i5 = i20;
                                i6 = i21;
                                i7 = i18;
                                unsafe3 = unsafe2;
                                i4 = i3;
                                unsafe = unsafe3;
                                i18 = i7;
                                break;
                            } else {
                                int zzm2 = zzao.zzm(bArr2, i3, zzanVar2);
                                unsafe2.putLong(obj, j, zzbe.zzc(zzanVar2.zzb));
                                i17 |= i25;
                                unsafe5 = unsafe2;
                                i14 = zzm2;
                                i16 = i21;
                                i18 = i18;
                                i15 = i20;
                                i13 = 1048575;
                                i12 = -1;
                                zzdiVar2 = this;
                                i11 = i2;
                                break;
                            }
                        default:
                            i5 = i20;
                            i6 = i21;
                            i7 = i18;
                            unsafe3 = unsafe2;
                            i4 = i3;
                            unsafe = unsafe3;
                            i18 = i7;
                            break;
                    }
                } else {
                    i5 = i20;
                    int i27 = i18;
                    zzdi<T> zzdiVar3 = zzdiVar2;
                    i6 = i21;
                    if (zzx != 27) {
                        if (zzx <= 49) {
                            int i28 = i3;
                            i9 = i17;
                            i10 = i27;
                            unsafe = unsafe6;
                            i14 = zzs(obj, bArr, i3, i2, b, i5, i22, i6, i23, zzx, j, zzanVar);
                        } else {
                            i8 = i3;
                            i9 = i17;
                            unsafe = unsafe6;
                            i10 = i27;
                            if (zzx != 50) {
                                i14 = zzr(obj, bArr, i8, i2, b, i5, i22, i23, zzx, j, i6, zzanVar);
                            } else if (i22 == 2) {
                                i14 = zzq(obj, bArr, i8, i2, i6, j, zzanVar);
                            }
                        }
                        i13 = 1048575;
                        i12 = -1;
                        zzdiVar2 = this;
                    } else if (i22 == 2) {
                        zzcf zzcfVar = (zzcf) unsafe6.getObject(obj2, j);
                        if (!zzcfVar.zzc()) {
                            int size = zzcfVar.size();
                            zzcfVar = zzcfVar.zzd(size == 0 ? 10 : size + size);
                            unsafe6.putObject(obj2, j, zzcfVar);
                        }
                        i14 = zzao.zze(zzdiVar3.zzB(i6), b, bArr, i3, i2, zzcfVar, zzanVar);
                        i11 = i2;
                        unsafe5 = unsafe6;
                        i17 = i17;
                        i16 = i6;
                        i18 = i27;
                        i15 = i5;
                        i13 = 1048575;
                        zzdiVar2 = zzdiVar3;
                        i12 = -1;
                    } else {
                        i8 = i3;
                        i9 = i17;
                        unsafe = unsafe6;
                        i10 = i27;
                    }
                    i4 = i8;
                    i17 = i9;
                    i18 = i10;
                    i14 = zzao.zzi(b, bArr, i4, i2, zzd(obj), zzanVar);
                    obj2 = obj;
                    bArr2 = bArr;
                    i11 = i2;
                    zzanVar2 = zzanVar;
                    i16 = i6;
                    i15 = i5;
                    unsafe5 = unsafe;
                    i13 = 1048575;
                    i12 = -1;
                    zzdiVar2 = this;
                }
            }
            i14 = zzao.zzi(b, bArr, i4, i2, zzd(obj), zzanVar);
            obj2 = obj;
            bArr2 = bArr;
            i11 = i2;
            zzanVar2 = zzanVar;
            i16 = i6;
            i15 = i5;
            unsafe5 = unsafe;
            i13 = 1048575;
            i12 = -1;
            zzdiVar2 = this;
        }
        int i29 = i17;
        Unsafe unsafe7 = unsafe5;
        if (i18 != 1048575) {
            unsafe7.putInt(obj, i18, i29);
        }
        if (i14 != i2) {
            throw zzci.zze();
        }
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:4:0x0015. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:17:0x01c2 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x01c3 A[SYNTHETIC] */
    @Override // com.google.android.gms.internal.play_billing.zzdp
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean zzj(Object obj, Object obj2) {
        int i;
        boolean zzW;
        int length = this.zzc.length;
        while (i < length) {
            int zzy = zzy(i);
            long j = zzy & 1048575;
            switch (zzx(zzy)) {
                case 0:
                    i = (zzO(obj, obj2, i) && Double.doubleToLongBits(zzeq.zza(obj, j)) == Double.doubleToLongBits(zzeq.zza(obj2, j))) ? i + 3 : 0;
                    return false;
                case 1:
                    if (zzO(obj, obj2, i) && Float.floatToIntBits(zzeq.zzb(obj, j)) == Float.floatToIntBits(zzeq.zzb(obj2, j))) {
                    }
                    return false;
                case 2:
                    if (zzO(obj, obj2, i) && zzeq.zzd(obj, j) == zzeq.zzd(obj2, j)) {
                    }
                    return false;
                case 3:
                    if (zzO(obj, obj2, i) && zzeq.zzd(obj, j) == zzeq.zzd(obj2, j)) {
                    }
                    return false;
                case 4:
                    if (zzO(obj, obj2, i) && zzeq.zzc(obj, j) == zzeq.zzc(obj2, j)) {
                    }
                    return false;
                case 5:
                    if (zzO(obj, obj2, i) && zzeq.zzd(obj, j) == zzeq.zzd(obj2, j)) {
                    }
                    return false;
                case 6:
                    if (zzO(obj, obj2, i) && zzeq.zzc(obj, j) == zzeq.zzc(obj2, j)) {
                    }
                    return false;
                case 7:
                    if (zzO(obj, obj2, i) && zzeq.zzw(obj, j) == zzeq.zzw(obj2, j)) {
                    }
                    return false;
                case 8:
                    if (zzO(obj, obj2, i) && zzdr.zzW(zzeq.zzf(obj, j), zzeq.zzf(obj2, j))) {
                    }
                    return false;
                case 9:
                    if (zzO(obj, obj2, i) && zzdr.zzW(zzeq.zzf(obj, j), zzeq.zzf(obj2, j))) {
                    }
                    return false;
                case 10:
                    if (zzO(obj, obj2, i) && zzdr.zzW(zzeq.zzf(obj, j), zzeq.zzf(obj2, j))) {
                    }
                    return false;
                case 11:
                    if (zzO(obj, obj2, i) && zzeq.zzc(obj, j) == zzeq.zzc(obj2, j)) {
                    }
                    return false;
                case 12:
                    if (zzO(obj, obj2, i) && zzeq.zzc(obj, j) == zzeq.zzc(obj2, j)) {
                    }
                    return false;
                case 13:
                    if (zzO(obj, obj2, i) && zzeq.zzc(obj, j) == zzeq.zzc(obj2, j)) {
                    }
                    return false;
                case 14:
                    if (zzO(obj, obj2, i) && zzeq.zzd(obj, j) == zzeq.zzd(obj2, j)) {
                    }
                    return false;
                case 15:
                    if (zzO(obj, obj2, i) && zzeq.zzc(obj, j) == zzeq.zzc(obj2, j)) {
                    }
                    return false;
                case 16:
                    if (zzO(obj, obj2, i) && zzeq.zzd(obj, j) == zzeq.zzd(obj2, j)) {
                    }
                    return false;
                case 17:
                    if (zzO(obj, obj2, i) && zzdr.zzW(zzeq.zzf(obj, j), zzeq.zzf(obj2, j))) {
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
                    zzW = zzdr.zzW(zzeq.zzf(obj, j), zzeq.zzf(obj2, j));
                    if (zzW) {
                        return false;
                    }
                case 50:
                    zzW = zzdr.zzW(zzeq.zzf(obj, j), zzeq.zzf(obj2, j));
                    if (zzW) {
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
                    long zzv = zzv(i) & 1048575;
                    if (zzeq.zzc(obj, zzv) == zzeq.zzc(obj2, zzv) && zzdr.zzW(zzeq.zzf(obj, j), zzeq.zzf(obj2, j))) {
                    }
                    return false;
                default:
            }
        }
        if (!this.zzn.zzd(obj).equals(this.zzn.zzd(obj2))) {
            return false;
        }
        if (!this.zzh) {
            return true;
        }
        this.zzo.zza(obj);
        this.zzo.zza(obj2);
        throw null;
    }

    @Override // com.google.android.gms.internal.play_billing.zzdp
    public final boolean zzk(Object obj) {
        int i;
        int i2;
        int i3 = 1048575;
        int i4 = 0;
        int i5 = 0;
        while (i5 < this.zzk) {
            int i6 = this.zzj[i5];
            int i7 = this.zzc[i6];
            int zzy = zzy(i6);
            int i8 = this.zzc[i6 + 2];
            int i9 = i8 & 1048575;
            int i10 = 1 << (i8 >>> 20);
            if (i9 != i3) {
                if (i9 != 1048575) {
                    i4 = zzb.getInt(obj, i9);
                }
                i2 = i4;
                i = i9;
            } else {
                i = i3;
                i2 = i4;
            }
            if ((268435456 & zzy) != 0 && !zzQ(obj, i6, i, i2, i10)) {
                return false;
            }
            int zzx = zzx(zzy);
            if (zzx != 9 && zzx != 17) {
                if (zzx != 27) {
                    if (zzx == 60 || zzx == 68) {
                        if (zzT(obj, i7, i6) && !zzR(obj, zzy, zzB(i6))) {
                            return false;
                        }
                    } else if (zzx != 49) {
                        if (zzx == 50 && !((zzcz) zzeq.zzf(obj, zzy & 1048575)).isEmpty()) {
                            throw null;
                        }
                    }
                }
                List list = (List) zzeq.zzf(obj, zzy & 1048575);
                if (list.isEmpty()) {
                    continue;
                } else {
                    zzdp zzB = zzB(i6);
                    for (int i11 = 0; i11 < list.size(); i11++) {
                        if (!zzB.zzk(list.get(i11))) {
                            return false;
                        }
                    }
                }
            } else if (zzQ(obj, i6, i, i2, i10) && !zzR(obj, zzy, zzB(i6))) {
                return false;
            }
            i5++;
            i3 = i;
            i4 = i2;
        }
        if (!this.zzh) {
            return true;
        }
        this.zzo.zza(obj);
        throw null;
    }

    @Override // com.google.android.gms.internal.play_billing.zzdp
    public final void zzi(Object obj, zzey zzeyVar) throws IOException {
        int i;
        int i2 = 1048575;
        if (this.zzi) {
            if (!this.zzh) {
                int length = this.zzc.length;
                for (int i3 = 0; i3 < length; i3 += 3) {
                    int zzy = zzy(i3);
                    int i4 = this.zzc[i3];
                    switch (zzx(zzy)) {
                        case 0:
                            if (zzP(obj, i3)) {
                                zzeyVar.zzf(i4, zzeq.zza(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 1:
                            if (zzP(obj, i3)) {
                                zzeyVar.zzo(i4, zzeq.zzb(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 2:
                            if (zzP(obj, i3)) {
                                zzeyVar.zzt(i4, zzeq.zzd(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 3:
                            if (zzP(obj, i3)) {
                                zzeyVar.zzJ(i4, zzeq.zzd(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 4:
                            if (zzP(obj, i3)) {
                                zzeyVar.zzr(i4, zzeq.zzc(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 5:
                            if (zzP(obj, i3)) {
                                zzeyVar.zzm(i4, zzeq.zzd(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 6:
                            if (zzP(obj, i3)) {
                                zzeyVar.zzk(i4, zzeq.zzc(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 7:
                            if (zzP(obj, i3)) {
                                zzeyVar.zzb(i4, zzeq.zzw(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 8:
                            if (zzP(obj, i3)) {
                                zzV(i4, zzeq.zzf(obj, zzy & 1048575), zzeyVar);
                                break;
                            } else {
                                break;
                            }
                        case 9:
                            if (zzP(obj, i3)) {
                                zzeyVar.zzv(i4, zzeq.zzf(obj, zzy & 1048575), zzB(i3));
                                break;
                            } else {
                                break;
                            }
                        case 10:
                            if (zzP(obj, i3)) {
                                zzeyVar.zzd(i4, (zzba) zzeq.zzf(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 11:
                            if (zzP(obj, i3)) {
                                zzeyVar.zzH(i4, zzeq.zzc(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 12:
                            if (zzP(obj, i3)) {
                                zzeyVar.zzi(i4, zzeq.zzc(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 13:
                            if (zzP(obj, i3)) {
                                zzeyVar.zzw(i4, zzeq.zzc(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 14:
                            if (zzP(obj, i3)) {
                                zzeyVar.zzy(i4, zzeq.zzd(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 15:
                            if (zzP(obj, i3)) {
                                zzeyVar.zzA(i4, zzeq.zzc(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 16:
                            if (zzP(obj, i3)) {
                                zzeyVar.zzC(i4, zzeq.zzd(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 17:
                            if (zzP(obj, i3)) {
                                zzeyVar.zzq(i4, zzeq.zzf(obj, zzy & 1048575), zzB(i3));
                                break;
                            } else {
                                break;
                            }
                        case 18:
                            zzdr.zzG(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, false);
                            break;
                        case 19:
                            zzdr.zzK(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, false);
                            break;
                        case 20:
                            zzdr.zzN(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, false);
                            break;
                        case 21:
                            zzdr.zzV(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, false);
                            break;
                        case 22:
                            zzdr.zzM(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, false);
                            break;
                        case 23:
                            zzdr.zzJ(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, false);
                            break;
                        case 24:
                            zzdr.zzI(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, false);
                            break;
                        case 25:
                            zzdr.zzE(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, false);
                            break;
                        case 26:
                            zzdr.zzT(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar);
                            break;
                        case 27:
                            zzdr.zzO(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, zzB(i3));
                            break;
                        case 28:
                            zzdr.zzF(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar);
                            break;
                        case 29:
                            zzdr.zzU(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, false);
                            break;
                        case 30:
                            zzdr.zzH(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, false);
                            break;
                        case 31:
                            zzdr.zzP(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, false);
                            break;
                        case 32:
                            zzdr.zzQ(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, false);
                            break;
                        case 33:
                            zzdr.zzR(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, false);
                            break;
                        case 34:
                            zzdr.zzS(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, false);
                            break;
                        case 35:
                            zzdr.zzG(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, true);
                            break;
                        case 36:
                            zzdr.zzK(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, true);
                            break;
                        case 37:
                            zzdr.zzN(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, true);
                            break;
                        case 38:
                            zzdr.zzV(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, true);
                            break;
                        case 39:
                            zzdr.zzM(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, true);
                            break;
                        case 40:
                            zzdr.zzJ(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, true);
                            break;
                        case 41:
                            zzdr.zzI(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, true);
                            break;
                        case 42:
                            zzdr.zzE(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, true);
                            break;
                        case 43:
                            zzdr.zzU(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, true);
                            break;
                        case 44:
                            zzdr.zzH(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, true);
                            break;
                        case 45:
                            zzdr.zzP(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, true);
                            break;
                        case 46:
                            zzdr.zzQ(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, true);
                            break;
                        case 47:
                            zzdr.zzR(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, true);
                            break;
                        case 48:
                            zzdr.zzS(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, true);
                            break;
                        case 49:
                            zzdr.zzL(i4, (List) zzeq.zzf(obj, zzy & 1048575), zzeyVar, zzB(i3));
                            break;
                        case 50:
                            zzN(zzeyVar, i4, zzeq.zzf(obj, zzy & 1048575), i3);
                            break;
                        case 51:
                            if (zzT(obj, i4, i3)) {
                                zzeyVar.zzf(i4, zzm(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 52:
                            if (zzT(obj, i4, i3)) {
                                zzeyVar.zzo(i4, zzn(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 53:
                            if (zzT(obj, i4, i3)) {
                                zzeyVar.zzt(i4, zzz(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 54:
                            if (zzT(obj, i4, i3)) {
                                zzeyVar.zzJ(i4, zzz(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 55:
                            if (zzT(obj, i4, i3)) {
                                zzeyVar.zzr(i4, zzp(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 56:
                            if (zzT(obj, i4, i3)) {
                                zzeyVar.zzm(i4, zzz(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 57:
                            if (zzT(obj, i4, i3)) {
                                zzeyVar.zzk(i4, zzp(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 58:
                            if (zzT(obj, i4, i3)) {
                                zzeyVar.zzb(i4, zzU(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 59:
                            if (zzT(obj, i4, i3)) {
                                zzV(i4, zzeq.zzf(obj, zzy & 1048575), zzeyVar);
                                break;
                            } else {
                                break;
                            }
                        case 60:
                            if (zzT(obj, i4, i3)) {
                                zzeyVar.zzv(i4, zzeq.zzf(obj, zzy & 1048575), zzB(i3));
                                break;
                            } else {
                                break;
                            }
                        case 61:
                            if (zzT(obj, i4, i3)) {
                                zzeyVar.zzd(i4, (zzba) zzeq.zzf(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 62:
                            if (zzT(obj, i4, i3)) {
                                zzeyVar.zzH(i4, zzp(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 63:
                            if (zzT(obj, i4, i3)) {
                                zzeyVar.zzi(i4, zzp(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 64:
                            if (zzT(obj, i4, i3)) {
                                zzeyVar.zzw(i4, zzp(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 65:
                            if (zzT(obj, i4, i3)) {
                                zzeyVar.zzy(i4, zzz(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 66:
                            if (zzT(obj, i4, i3)) {
                                zzeyVar.zzA(i4, zzp(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 67:
                            if (zzT(obj, i4, i3)) {
                                zzeyVar.zzC(i4, zzz(obj, zzy & 1048575));
                                break;
                            } else {
                                break;
                            }
                        case 68:
                            if (zzT(obj, i4, i3)) {
                                zzeyVar.zzq(i4, zzeq.zzf(obj, zzy & 1048575), zzB(i3));
                                break;
                            } else {
                                break;
                            }
                    }
                }
                zzeg zzegVar = this.zzn;
                zzegVar.zzi(zzegVar.zzd(obj), zzeyVar);
                return;
            }
            this.zzo.zza(obj);
            throw null;
        }
        if (!this.zzh) {
            int length2 = this.zzc.length;
            Unsafe unsafe = zzb;
            int i5 = 0;
            int i6 = 1048575;
            int i7 = 0;
            while (i5 < length2) {
                int zzy2 = zzy(i5);
                int[] iArr = this.zzc;
                int i8 = iArr[i5];
                int zzx = zzx(zzy2);
                if (zzx <= 17) {
                    int i9 = iArr[i5 + 2];
                    int i10 = i9 & i2;
                    if (i10 != i6) {
                        i7 = unsafe.getInt(obj, i10);
                        i6 = i10;
                    }
                    i = 1 << (i9 >>> 20);
                } else {
                    i = 0;
                }
                long j = zzy2 & i2;
                switch (zzx) {
                    case 0:
                        if ((i7 & i) == 0) {
                            break;
                        } else {
                            zzeyVar.zzf(i8, zzeq.zza(obj, j));
                            continue;
                        }
                    case 1:
                        if ((i7 & i) != 0) {
                            zzeyVar.zzo(i8, zzeq.zzb(obj, j));
                            break;
                        } else {
                            continue;
                        }
                    case 2:
                        if ((i7 & i) != 0) {
                            zzeyVar.zzt(i8, unsafe.getLong(obj, j));
                            break;
                        } else {
                            continue;
                        }
                    case 3:
                        if ((i7 & i) != 0) {
                            zzeyVar.zzJ(i8, unsafe.getLong(obj, j));
                            break;
                        } else {
                            continue;
                        }
                    case 4:
                        if ((i7 & i) != 0) {
                            zzeyVar.zzr(i8, unsafe.getInt(obj, j));
                            break;
                        } else {
                            continue;
                        }
                    case 5:
                        if ((i7 & i) != 0) {
                            zzeyVar.zzm(i8, unsafe.getLong(obj, j));
                            break;
                        } else {
                            continue;
                        }
                    case 6:
                        if ((i7 & i) != 0) {
                            zzeyVar.zzk(i8, unsafe.getInt(obj, j));
                            break;
                        } else {
                            continue;
                        }
                    case 7:
                        if ((i7 & i) != 0) {
                            zzeyVar.zzb(i8, zzeq.zzw(obj, j));
                            break;
                        } else {
                            continue;
                        }
                    case 8:
                        if ((i7 & i) != 0) {
                            zzV(i8, unsafe.getObject(obj, j), zzeyVar);
                            break;
                        } else {
                            continue;
                        }
                    case 9:
                        if ((i7 & i) != 0) {
                            zzeyVar.zzv(i8, unsafe.getObject(obj, j), zzB(i5));
                            break;
                        } else {
                            continue;
                        }
                    case 10:
                        if ((i7 & i) != 0) {
                            zzeyVar.zzd(i8, (zzba) unsafe.getObject(obj, j));
                            break;
                        } else {
                            continue;
                        }
                    case 11:
                        if ((i7 & i) != 0) {
                            zzeyVar.zzH(i8, unsafe.getInt(obj, j));
                            break;
                        } else {
                            continue;
                        }
                    case 12:
                        if ((i7 & i) != 0) {
                            zzeyVar.zzi(i8, unsafe.getInt(obj, j));
                            break;
                        } else {
                            continue;
                        }
                    case 13:
                        if ((i7 & i) != 0) {
                            zzeyVar.zzw(i8, unsafe.getInt(obj, j));
                            break;
                        } else {
                            continue;
                        }
                    case 14:
                        if ((i7 & i) != 0) {
                            zzeyVar.zzy(i8, unsafe.getLong(obj, j));
                            break;
                        } else {
                            continue;
                        }
                    case 15:
                        if ((i7 & i) != 0) {
                            zzeyVar.zzA(i8, unsafe.getInt(obj, j));
                            break;
                        } else {
                            continue;
                        }
                    case 16:
                        if ((i7 & i) != 0) {
                            zzeyVar.zzC(i8, unsafe.getLong(obj, j));
                            break;
                        } else {
                            continue;
                        }
                    case 17:
                        if ((i7 & i) != 0) {
                            zzeyVar.zzq(i8, unsafe.getObject(obj, j), zzB(i5));
                            break;
                        } else {
                            continue;
                        }
                    case 18:
                        zzdr.zzG(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, false);
                        continue;
                    case 19:
                        zzdr.zzK(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, false);
                        continue;
                    case 20:
                        zzdr.zzN(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, false);
                        continue;
                    case 21:
                        zzdr.zzV(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, false);
                        continue;
                    case 22:
                        zzdr.zzM(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, false);
                        continue;
                    case 23:
                        zzdr.zzJ(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, false);
                        continue;
                    case 24:
                        zzdr.zzI(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, false);
                        continue;
                    case 25:
                        zzdr.zzE(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, false);
                        continue;
                    case 26:
                        zzdr.zzT(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar);
                        break;
                    case 27:
                        zzdr.zzO(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, zzB(i5));
                        break;
                    case 28:
                        zzdr.zzF(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar);
                        break;
                    case 29:
                        zzdr.zzU(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, false);
                        break;
                    case 30:
                        zzdr.zzH(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, false);
                        break;
                    case 31:
                        zzdr.zzP(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, false);
                        break;
                    case 32:
                        zzdr.zzQ(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, false);
                        break;
                    case 33:
                        zzdr.zzR(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, false);
                        break;
                    case 34:
                        zzdr.zzS(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, false);
                        break;
                    case 35:
                        zzdr.zzG(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, true);
                        break;
                    case 36:
                        zzdr.zzK(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, true);
                        break;
                    case 37:
                        zzdr.zzN(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, true);
                        break;
                    case 38:
                        zzdr.zzV(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, true);
                        break;
                    case 39:
                        zzdr.zzM(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, true);
                        break;
                    case 40:
                        zzdr.zzJ(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, true);
                        break;
                    case 41:
                        zzdr.zzI(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, true);
                        break;
                    case 42:
                        zzdr.zzE(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, true);
                        break;
                    case 43:
                        zzdr.zzU(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, true);
                        break;
                    case 44:
                        zzdr.zzH(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, true);
                        break;
                    case 45:
                        zzdr.zzP(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, true);
                        break;
                    case 46:
                        zzdr.zzQ(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, true);
                        break;
                    case 47:
                        zzdr.zzR(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, true);
                        break;
                    case 48:
                        zzdr.zzS(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, true);
                        break;
                    case 49:
                        zzdr.zzL(this.zzc[i5], (List) unsafe.getObject(obj, j), zzeyVar, zzB(i5));
                        break;
                    case 50:
                        zzN(zzeyVar, i8, unsafe.getObject(obj, j), i5);
                        break;
                    case 51:
                        if (zzT(obj, i8, i5)) {
                            zzeyVar.zzf(i8, zzm(obj, j));
                            break;
                        }
                        break;
                    case 52:
                        if (zzT(obj, i8, i5)) {
                            zzeyVar.zzo(i8, zzn(obj, j));
                            break;
                        }
                        break;
                    case 53:
                        if (zzT(obj, i8, i5)) {
                            zzeyVar.zzt(i8, zzz(obj, j));
                            break;
                        }
                        break;
                    case 54:
                        if (zzT(obj, i8, i5)) {
                            zzeyVar.zzJ(i8, zzz(obj, j));
                            break;
                        }
                        break;
                    case 55:
                        if (zzT(obj, i8, i5)) {
                            zzeyVar.zzr(i8, zzp(obj, j));
                            break;
                        }
                        break;
                    case 56:
                        if (zzT(obj, i8, i5)) {
                            zzeyVar.zzm(i8, zzz(obj, j));
                            break;
                        }
                        break;
                    case 57:
                        if (zzT(obj, i8, i5)) {
                            zzeyVar.zzk(i8, zzp(obj, j));
                            break;
                        }
                        break;
                    case 58:
                        if (zzT(obj, i8, i5)) {
                            zzeyVar.zzb(i8, zzU(obj, j));
                            break;
                        }
                        break;
                    case 59:
                        if (zzT(obj, i8, i5)) {
                            zzV(i8, unsafe.getObject(obj, j), zzeyVar);
                            break;
                        }
                        break;
                    case 60:
                        if (zzT(obj, i8, i5)) {
                            zzeyVar.zzv(i8, unsafe.getObject(obj, j), zzB(i5));
                            break;
                        }
                        break;
                    case 61:
                        if (zzT(obj, i8, i5)) {
                            zzeyVar.zzd(i8, (zzba) unsafe.getObject(obj, j));
                            break;
                        }
                        break;
                    case 62:
                        if (zzT(obj, i8, i5)) {
                            zzeyVar.zzH(i8, zzp(obj, j));
                            break;
                        }
                        break;
                    case 63:
                        if (zzT(obj, i8, i5)) {
                            zzeyVar.zzi(i8, zzp(obj, j));
                            break;
                        }
                        break;
                    case 64:
                        if (zzT(obj, i8, i5)) {
                            zzeyVar.zzw(i8, zzp(obj, j));
                            break;
                        }
                        break;
                    case 65:
                        if (zzT(obj, i8, i5)) {
                            zzeyVar.zzy(i8, zzz(obj, j));
                            break;
                        }
                        break;
                    case 66:
                        if (zzT(obj, i8, i5)) {
                            zzeyVar.zzA(i8, zzp(obj, j));
                            break;
                        }
                        break;
                    case 67:
                        if (zzT(obj, i8, i5)) {
                            zzeyVar.zzC(i8, zzz(obj, j));
                            break;
                        }
                        break;
                    case 68:
                        if (zzT(obj, i8, i5)) {
                            zzeyVar.zzq(i8, unsafe.getObject(obj, j), zzB(i5));
                            break;
                        }
                        break;
                }
                i5 += 3;
                i2 = 1048575;
            }
            zzeg zzegVar2 = this.zzn;
            zzegVar2.zzi(zzegVar2.zzd(obj), zzeyVar);
            return;
        }
        this.zzo.zza(obj);
        throw null;
    }
}
