package com.google.android.gms.internal.measurement;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import android.util.Pair;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzeb extends zzjr {
    /* JADX INFO: Access modifiers changed from: package-private */
    public zzeb(zzjs zzjsVar) {
        super(zzjsVar);
    }

    private final Boolean zza(double d, zzki zzkiVar) {
        try {
            return zza(new BigDecimal(d), zzkiVar, Math.ulp(d));
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    private final Boolean zza(long j, zzki zzkiVar) {
        try {
            return zza(new BigDecimal(j), zzkiVar, 0.0d);
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    private static Boolean zza(Boolean bool, boolean z) {
        if (bool == null) {
            return null;
        }
        return Boolean.valueOf(bool.booleanValue() ^ z);
    }

    private final Boolean zza(String str, int i, boolean z, String str2, List<String> list, String str3) {
        boolean startsWith;
        if (str == null) {
            return null;
        }
        if (i == 6) {
            if (list == null || list.size() == 0) {
                return null;
            }
        } else if (str2 == null) {
            return null;
        }
        if (!z && i != 1) {
            str = str.toUpperCase(Locale.ENGLISH);
        }
        switch (i) {
            case 1:
                try {
                    return Boolean.valueOf(Pattern.compile(str3, z ? 0 : 66).matcher(str).matches());
                } catch (PatternSyntaxException unused) {
                    zzgf().zziv().zzg("Invalid regular expression in REGEXP audience filter. expression", str3);
                    return null;
                }
            case 2:
                startsWith = str.startsWith(str2);
                break;
            case 3:
                startsWith = str.endsWith(str2);
                break;
            case 4:
                startsWith = str.contains(str2);
                break;
            case 5:
                startsWith = str.equals(str2);
                break;
            case 6:
                startsWith = list.contains(str);
                break;
            default:
                return null;
        }
        return Boolean.valueOf(startsWith);
    }

    private final Boolean zza(String str, zzki zzkiVar) {
        if (!zzkc.zzcj(str)) {
            return null;
        }
        try {
            return zza(new BigDecimal(str), zzkiVar, 0.0d);
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    private final Boolean zza(String str, zzkk zzkkVar) {
        List<String> list;
        Preconditions.checkNotNull(zzkkVar);
        if (str == null || zzkkVar.zzast == null || zzkkVar.zzast.intValue() == 0) {
            return null;
        }
        if (zzkkVar.zzast.intValue() == 6) {
            if (zzkkVar.zzasw == null || zzkkVar.zzasw.length == 0) {
                return null;
            }
        } else if (zzkkVar.zzasu == null) {
            return null;
        }
        int intValue = zzkkVar.zzast.intValue();
        boolean z = zzkkVar.zzasv != null && zzkkVar.zzasv.booleanValue();
        String upperCase = (z || intValue == 1 || intValue == 6) ? zzkkVar.zzasu : zzkkVar.zzasu.toUpperCase(Locale.ENGLISH);
        if (zzkkVar.zzasw == null) {
            list = null;
        } else {
            String[] strArr = zzkkVar.zzasw;
            if (z) {
                list = Arrays.asList(strArr);
            } else {
                ArrayList arrayList = new ArrayList();
                for (String str2 : strArr) {
                    arrayList.add(str2.toUpperCase(Locale.ENGLISH));
                }
                list = arrayList;
            }
        }
        return zza(str, intValue, z, upperCase, list, intValue == 1 ? upperCase : null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:62:0x0071, code lost:
    
        if (r3 != null) goto L36;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static Boolean zza(BigDecimal bigDecimal, zzki zzkiVar, double d) {
        BigDecimal bigDecimal2;
        BigDecimal bigDecimal3;
        BigDecimal bigDecimal4;
        Preconditions.checkNotNull(zzkiVar);
        if (zzkiVar.zzasl != null && zzkiVar.zzasl.intValue() != 0) {
            if (zzkiVar.zzasl.intValue() == 4) {
                if (zzkiVar.zzaso == null || zzkiVar.zzasp == null) {
                    return null;
                }
            } else if (zzkiVar.zzasn == null) {
                return null;
            }
            int intValue = zzkiVar.zzasl.intValue();
            if (zzkiVar.zzasl.intValue() == 4) {
                if (zzkc.zzcj(zzkiVar.zzaso) && zzkc.zzcj(zzkiVar.zzasp)) {
                    try {
                        BigDecimal bigDecimal5 = new BigDecimal(zzkiVar.zzaso);
                        bigDecimal4 = new BigDecimal(zzkiVar.zzasp);
                        bigDecimal3 = bigDecimal5;
                        bigDecimal2 = null;
                    } catch (NumberFormatException unused) {
                    }
                }
                return null;
            }
            if (!zzkc.zzcj(zzkiVar.zzasn)) {
                return null;
            }
            try {
                bigDecimal2 = new BigDecimal(zzkiVar.zzasn);
                bigDecimal3 = null;
                bigDecimal4 = null;
            } catch (NumberFormatException unused2) {
            }
            if (intValue == 4) {
                if (bigDecimal3 == null) {
                    return null;
                }
            }
            if (intValue == 1) {
                return Boolean.valueOf(bigDecimal.compareTo(bigDecimal2) == -1);
            }
            if (intValue == 2) {
                return Boolean.valueOf(bigDecimal.compareTo(bigDecimal2) == 1);
            }
            if (intValue == 3) {
                if (d == 0.0d) {
                    return Boolean.valueOf(bigDecimal.compareTo(bigDecimal2) == 0);
                }
                if (bigDecimal.compareTo(bigDecimal2.subtract(new BigDecimal(d).multiply(new BigDecimal(2)))) == 1 && bigDecimal.compareTo(bigDecimal2.add(new BigDecimal(d).multiply(new BigDecimal(2)))) == -1) {
                    r6 = true;
                }
                return Boolean.valueOf(r6);
            }
            if (intValue == 4) {
                if (bigDecimal.compareTo(bigDecimal3) != -1 && bigDecimal.compareTo(bigDecimal4) != 1) {
                    r6 = true;
                }
                return Boolean.valueOf(r6);
            }
        }
        return null;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v28, resolved type: V */
    /* JADX DEBUG: Multi-variable search result rejected for r13v10, resolved type: V */
    /* JADX DEBUG: Multi-variable search result rejected for r13v38, resolved type: V */
    /* JADX DEBUG: Multi-variable search result rejected for r14v15, resolved type: V */
    /* JADX DEBUG: Multi-variable search result rejected for r14v5, resolved type: V */
    /* JADX DEBUG: Multi-variable search result rejected for r15v7, resolved type: V */
    /* JADX DEBUG: Multi-variable search result rejected for r4v3, resolved type: V */
    /* JADX DEBUG: Multi-variable search result rejected for r5v98, resolved type: V */
    /* JADX DEBUG: Multi-variable search result rejected for r6v29, resolved type: V */
    /* JADX DEBUG: Multi-variable search result rejected for r8v5, resolved type: V */
    /* JADX DEBUG: Multi-variable search result rejected for r8v54, resolved type: V */
    /* JADX DEBUG: Multi-variable search result rejected for r8v9, resolved type: V */
    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0734  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x073f  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0747  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0737  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x037e  */
    /* JADX WARN: Removed duplicated region for block: B:242:0x0281  */
    /* JADX WARN: Removed duplicated region for block: B:314:0x0a30  */
    /* JADX WARN: Removed duplicated region for block: B:317:0x0a3b  */
    /* JADX WARN: Removed duplicated region for block: B:321:0x0a50  */
    /* JADX WARN: Removed duplicated region for block: B:324:0x0a33  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0245  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0260 A[EDGE_INSN: B:72:0x0260->B:73:0x0260 BREAK  A[LOOP:3: B:64:0x0241->B:70:0x0259], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0262  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x032a  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x03aa  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x03ca  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final zzko[] zza(String str, zzkp[] zzkpVarArr, zzku[] zzkuVarArr) {
        zzfj zzis;
        Object zzbl;
        String str2;
        int zzvv;
        byte[] bArr;
        String str3;
        Iterator<zzkj> it;
        String str4;
        zzfj zziv;
        String zzbk;
        String str5;
        Boolean zza;
        Boolean zza2;
        zzfj zziv2;
        String zzbk2;
        String str6;
        zzkp zzkpVar;
        ArrayMap arrayMap;
        String str7;
        Boolean bool;
        String str8;
        boolean z;
        zzkq[] zzkqVarArr;
        String str9;
        zzkq[] zzkqVarArr2;
        zzes zzf;
        zzkq[] zzkqVarArr3;
        String str10;
        HashSet hashSet;
        ArrayMap arrayMap2;
        int i;
        String str11;
        ArrayMap arrayMap3;
        ArrayMap arrayMap4;
        String str12;
        zzes zzii;
        Map<Integer, List<zzkg>> map;
        Iterator<Integer> it2;
        zzeb zzebVar;
        String str13;
        ArrayMap arrayMap5;
        String str14;
        HashSet hashSet2;
        ArrayMap arrayMap6;
        Iterator<Integer> it3;
        Iterator<zzkg> it4;
        String str15;
        long j;
        ArrayMap arrayMap7;
        zzeb zzebVar2;
        Boolean valueOf;
        zzfj zziv3;
        String zzbi;
        String zzbj;
        String str16;
        Boolean zza3;
        String str17;
        Object obj;
        Long l;
        zzkp zzkpVar2;
        String str18;
        String str19;
        zzkp zzkpVar3;
        zzkq[] zzkqVarArr4;
        int length;
        int i2;
        int i3;
        zzkp zzkpVar4;
        zzkp zzkpVar5;
        String str20;
        SQLiteDatabase writableDatabase;
        String[] strArr;
        Iterator<Integer> it5;
        ArrayMap arrayMap8;
        ArrayMap arrayMap9;
        zzeb zzebVar3 = this;
        String str21 = str;
        zzkp[] zzkpVarArr2 = zzkpVarArr;
        Preconditions.checkNotEmpty(str);
        HashSet hashSet3 = new HashSet();
        ArrayMap arrayMap10 = new ArrayMap();
        ArrayMap arrayMap11 = new ArrayMap();
        ArrayMap arrayMap12 = new ArrayMap();
        Map<Integer, zzkt> zzbe = zzje().zzbe(str21);
        Boolean bool2 = false;
        if (zzbe != null) {
            Iterator<Integer> it6 = zzbe.keySet().iterator();
            while (it6.hasNext()) {
                int intValue = it6.next().intValue();
                zzkt zzktVar = zzbe.get(Integer.valueOf(intValue));
                BitSet bitSet = (BitSet) arrayMap11.get(Integer.valueOf(intValue));
                BitSet bitSet2 = (BitSet) arrayMap12.get(Integer.valueOf(intValue));
                if (bitSet == null) {
                    bitSet = new BitSet();
                    arrayMap11.put(Integer.valueOf(intValue), bitSet);
                    bitSet2 = new BitSet();
                    arrayMap12.put(Integer.valueOf(intValue), bitSet2);
                }
                Map<Integer, zzkt> map2 = zzbe;
                int i4 = 0;
                while (i4 < (zzktVar.zzauw.length << 6)) {
                    if (zzkc.zza(zzktVar.zzauw, i4)) {
                        it5 = it6;
                        arrayMap8 = arrayMap11;
                        arrayMap9 = arrayMap12;
                        zzgf().zziz().zze("Filter already evaluated. audience ID, filter ID", Integer.valueOf(intValue), Integer.valueOf(i4));
                        bitSet2.set(i4);
                        if (zzkc.zza(zzktVar.zzaux, i4)) {
                            bitSet.set(i4);
                        }
                    } else {
                        it5 = it6;
                        arrayMap8 = arrayMap11;
                        arrayMap9 = arrayMap12;
                    }
                    i4++;
                    it6 = it5;
                    arrayMap11 = arrayMap8;
                    arrayMap12 = arrayMap9;
                }
                zzko zzkoVar = new zzko();
                arrayMap10.put(Integer.valueOf(intValue), zzkoVar);
                zzkoVar.zzatk = bool2;
                zzkoVar.zzatj = zzktVar;
                zzkoVar.zzati = new zzkt();
                zzkoVar.zzati.zzaux = zzkc.zza(bitSet);
                zzkoVar.zzati.zzauw = zzkc.zza(bitSet2);
                zzbe = map2;
                it6 = it6;
            }
        }
        ArrayMap arrayMap13 = arrayMap11;
        ArrayMap arrayMap14 = arrayMap12;
        String str22 = "Filter definition";
        String str23 = "Skipping failed audience ID";
        if (zzkpVarArr2 != null) {
            ArrayMap arrayMap15 = new ArrayMap();
            int length2 = zzkpVarArr2.length;
            long j2 = 0;
            zzkp zzkpVar6 = null;
            int i5 = 0;
            Long l2 = null;
            while (i5 < length2) {
                zzkp zzkpVar7 = zzkpVarArr2[i5];
                String str24 = zzkpVar7.name;
                zzkq[] zzkqVarArr5 = zzkpVar7.zzatm;
                int i6 = length2;
                if (zzgh().zzd(str21, zzey.zzaic)) {
                    zzjc();
                    Long l3 = (Long) zzjy.zzb(zzkpVar7, "_eid");
                    boolean z2 = l3 != null;
                    ArrayMap arrayMap16 = arrayMap15;
                    if (z2 && str24.equals("_ep")) {
                        zzjc();
                        str24 = (String) zzjy.zzb(zzkpVar7, "_en");
                        if (TextUtils.isEmpty(str24)) {
                            zzgf().zzis().zzg("Extra parameter without an event name. eventId", l3);
                            zzkpVar2 = zzkpVar6;
                            str18 = str23;
                            bool = bool2;
                            str19 = str22;
                            arrayMap5 = arrayMap16;
                        } else {
                            if (zzkpVar6 == null || l2 == null || l3.longValue() != l2.longValue()) {
                                Pair<zzkp, Long> zza4 = zzje().zza(str21, l3);
                                zzkpVar2 = zzkpVar6;
                                if (zza4 == null || zza4.first == null) {
                                    str18 = str23;
                                    bool = bool2;
                                    str19 = str22;
                                    arrayMap5 = arrayMap16;
                                    zzgf().zzis().zze("Extra parameter without existing main event. eventName, eventId", str24, l3);
                                } else {
                                    zzkpVar6 = (zzkp) zza4.first;
                                    j2 = ((Long) zza4.second).longValue();
                                    zzjc();
                                    l2 = (Long) zzjy.zzb(zzkpVar6, "_eid");
                                }
                            }
                            zzkp zzkpVar8 = zzkpVar6;
                            j2--;
                            if (j2 <= 0) {
                                zzej zzje = zzje();
                                zzje.zzab();
                                zzje.zzgf().zziz().zzg("Clearing complex main event info. appId", str21);
                                try {
                                    writableDatabase = zzje.getWritableDatabase();
                                    zzkpVar5 = zzkpVar8;
                                    str20 = str23;
                                    try {
                                        strArr = new String[1];
                                    } catch (SQLiteException e) {
                                        e = e;
                                        zzje.zzgf().zzis().zzg("Error clearing complex main event", e);
                                        str8 = str22;
                                        zzkpVar3 = zzkpVar5;
                                        arrayMap = arrayMap16;
                                        str7 = str20;
                                        z = true;
                                        zzkqVarArr = zzkqVarArr5;
                                        bool = bool2;
                                        int length3 = zzkpVar3.zzatm.length + zzkqVarArr.length;
                                        zzkq[] zzkqVarArr6 = new zzkq[length3];
                                        zzkqVarArr4 = zzkpVar3.zzatm;
                                        length = zzkqVarArr4.length;
                                        i2 = 0;
                                        i3 = 0;
                                        while (true) {
                                            zzkpVar4 = zzkpVar3;
                                            if (i2 >= length) {
                                            }
                                            i2++;
                                            zzkpVar3 = zzkpVar4;
                                            zzkqVarArr4 = r30;
                                        }
                                        if (i3 > 0) {
                                        }
                                    }
                                } catch (SQLiteException e2) {
                                    e = e2;
                                    zzkpVar5 = zzkpVar8;
                                    str20 = str23;
                                }
                                try {
                                    strArr[0] = str21;
                                    writableDatabase.execSQL("delete from main_event_params where app_id=?", strArr);
                                } catch (SQLiteException e3) {
                                    e = e3;
                                    zzje.zzgf().zzis().zzg("Error clearing complex main event", e);
                                    str8 = str22;
                                    zzkpVar3 = zzkpVar5;
                                    arrayMap = arrayMap16;
                                    str7 = str20;
                                    z = true;
                                    zzkqVarArr = zzkqVarArr5;
                                    bool = bool2;
                                    int length32 = zzkpVar3.zzatm.length + zzkqVarArr.length;
                                    zzkq[] zzkqVarArr62 = new zzkq[length32];
                                    zzkqVarArr4 = zzkpVar3.zzatm;
                                    length = zzkqVarArr4.length;
                                    i2 = 0;
                                    i3 = 0;
                                    while (true) {
                                        zzkpVar4 = zzkpVar3;
                                        if (i2 >= length) {
                                        }
                                        i2++;
                                        zzkpVar3 = zzkpVar4;
                                        zzkqVarArr4 = r30;
                                    }
                                    if (i3 > 0) {
                                    }
                                }
                                str8 = str22;
                                zzkpVar3 = zzkpVar5;
                                arrayMap = arrayMap16;
                                str7 = str20;
                                z = true;
                                zzkqVarArr = zzkqVarArr5;
                                bool = bool2;
                            } else {
                                arrayMap = arrayMap16;
                                str8 = str22;
                                str7 = str23;
                                z = true;
                                zzkqVarArr = zzkqVarArr5;
                                bool = bool2;
                                zzje().zza(str, l3, j2, zzkpVar8);
                                zzkpVar3 = zzkpVar8;
                            }
                            int length322 = zzkpVar3.zzatm.length + zzkqVarArr.length;
                            zzkq[] zzkqVarArr622 = new zzkq[length322];
                            zzkqVarArr4 = zzkpVar3.zzatm;
                            length = zzkqVarArr4.length;
                            i2 = 0;
                            i3 = 0;
                            while (true) {
                                zzkpVar4 = zzkpVar3;
                                if (i2 >= length) {
                                    break;
                                }
                                zzkq zzkqVar = zzkqVarArr4[i2];
                                zzjc();
                                zzkq[] zzkqVarArr7 = zzkqVarArr4;
                                if (zzjy.zza(zzkpVar7, zzkqVar.name) == null) {
                                    zzkqVarArr622[i3] = zzkqVar;
                                    i3++;
                                }
                                i2++;
                                zzkpVar3 = zzkpVar4;
                                zzkqVarArr4 = zzkqVarArr7;
                            }
                            if (i3 > 0) {
                                int length4 = zzkqVarArr.length;
                                int i7 = 0;
                                while (i7 < length4) {
                                    zzkqVarArr622[i3] = zzkqVarArr[i7];
                                    i7++;
                                    i3++;
                                }
                                zzkq[] zzkqVarArr8 = i3 == length322 ? zzkqVarArr622 : (zzkq[]) Arrays.copyOf(zzkqVarArr622, i3);
                                str9 = str24;
                                zzkpVar6 = zzkpVar4;
                                zzkqVarArr2 = zzkqVarArr8;
                                zzf = zzje().zzf(str21, zzkpVar7.name);
                                if (zzf == null) {
                                    zzgf().zziv().zze("Event aggregate wasn't created during raw event logging. appId, event", zzfh.zzbl(str), zzgb().zzbi(str9));
                                    zzkqVarArr3 = zzkqVarArr2;
                                    str10 = str9;
                                    arrayMap3 = arrayMap13;
                                    arrayMap4 = arrayMap14;
                                    str12 = str8;
                                    hashSet = hashSet3;
                                    arrayMap2 = arrayMap10;
                                    i = i5;
                                    str11 = str21;
                                    zzii = new zzes(str, zzkpVar7.name, 1L, 1L, zzkpVar7.zzatn.longValue(), 0L, null, null, null);
                                } else {
                                    zzkqVarArr3 = zzkqVarArr2;
                                    str10 = str9;
                                    hashSet = hashSet3;
                                    arrayMap2 = arrayMap10;
                                    i = i5;
                                    str11 = str21;
                                    arrayMap3 = arrayMap13;
                                    arrayMap4 = arrayMap14;
                                    str12 = str8;
                                    zzii = zzf.zzii();
                                }
                                zzje().zza(zzii);
                                long j3 = zzii.zzafs;
                                ArrayMap arrayMap17 = arrayMap;
                                String str25 = str10;
                                map = (Map) arrayMap17.get(str25);
                                if (map == null) {
                                    map = zzje().zzk(str11, str25);
                                    if (map == null) {
                                        map = new ArrayMap<>();
                                    }
                                    arrayMap17.put(str25, map);
                                }
                                it2 = map.keySet().iterator();
                                while (it2.hasNext()) {
                                    int intValue2 = it2.next().intValue();
                                    HashSet hashSet4 = hashSet;
                                    if (hashSet4.contains(Integer.valueOf(intValue2))) {
                                        zzgf().zziz().zzg(str7, Integer.valueOf(intValue2));
                                        hashSet = hashSet4;
                                    } else {
                                        String str26 = str7;
                                        ArrayMap arrayMap18 = arrayMap2;
                                        zzko zzkoVar2 = (zzko) arrayMap18.get(Integer.valueOf(intValue2));
                                        ArrayMap arrayMap19 = arrayMap3;
                                        BitSet bitSet3 = (BitSet) arrayMap19.get(Integer.valueOf(intValue2));
                                        ArrayMap arrayMap20 = arrayMap4;
                                        BitSet bitSet4 = (BitSet) arrayMap20.get(Integer.valueOf(intValue2));
                                        if (zzkoVar2 == null) {
                                            zzko zzkoVar3 = new zzko();
                                            arrayMap18.put(Integer.valueOf(intValue2), zzkoVar3);
                                            zzkoVar3.zzatk = Boolean.valueOf(z);
                                            bitSet3 = new BitSet();
                                            arrayMap19.put(Integer.valueOf(intValue2), bitSet3);
                                            bitSet4 = new BitSet();
                                            arrayMap20.put(Integer.valueOf(intValue2), bitSet4);
                                        }
                                        Iterator<zzkg> it7 = map.get(Integer.valueOf(intValue2)).iterator();
                                        while (it7.hasNext()) {
                                            zzkp zzkpVar9 = zzkpVar6;
                                            zzkg next = it7.next();
                                            ArrayMap arrayMap21 = arrayMap17;
                                            Map<Integer, List<zzkg>> map3 = map;
                                            if (zzgf().isLoggable(2)) {
                                                it3 = it2;
                                                it4 = it7;
                                                arrayMap6 = arrayMap20;
                                                zzgf().zziz().zzd("Evaluating filter. audience, filter, event", Integer.valueOf(intValue2), next.zzasb, zzgb().zzbi(next.zzasc));
                                                str15 = str12;
                                                zzgf().zziz().zzg(str15, zzjc().zza(next));
                                            } else {
                                                arrayMap6 = arrayMap20;
                                                it3 = it2;
                                                it4 = it7;
                                                str15 = str12;
                                            }
                                            if (next.zzasb == null || next.zzasb.intValue() > 256) {
                                                j = j3;
                                                str12 = str15;
                                                arrayMap7 = arrayMap19;
                                                zzgf().zziv().zze("Invalid event filter ID. appId, id", zzfh.zzbl(str), String.valueOf(next.zzasb));
                                            } else if (bitSet3.get(next.zzasb.intValue())) {
                                                zzgf().zziz().zze("Event filter already evaluated true. audience ID, filter ID", Integer.valueOf(intValue2), next.zzasb);
                                                str12 = str15;
                                                map = map3;
                                                zzkpVar6 = zzkpVar9;
                                                it2 = it3;
                                                it7 = it4;
                                                arrayMap17 = arrayMap21;
                                                arrayMap20 = arrayMap6;
                                            } else {
                                                if (next.zzasf != null) {
                                                    zzebVar2 = this;
                                                    Boolean zza5 = zzebVar2.zza(j3, next.zzasf);
                                                    if (zza5 == null) {
                                                        j = j3;
                                                        str12 = str15;
                                                        arrayMap7 = arrayMap19;
                                                        valueOf = null;
                                                        zzgf().zziz().zzg("Event filter result", valueOf == null ? "null" : valueOf);
                                                        if (valueOf == null) {
                                                            hashSet4.add(Integer.valueOf(intValue2));
                                                        } else {
                                                            bitSet4.set(next.zzasb.intValue());
                                                            if (valueOf.booleanValue()) {
                                                                bitSet3.set(next.zzasb.intValue());
                                                            }
                                                        }
                                                    } else if (!zza5.booleanValue()) {
                                                        j = j3;
                                                        str12 = str15;
                                                        arrayMap7 = arrayMap19;
                                                        valueOf = bool;
                                                        zzgf().zziz().zzg("Event filter result", valueOf == null ? "null" : valueOf);
                                                        if (valueOf == null) {
                                                        }
                                                    }
                                                } else {
                                                    zzebVar2 = this;
                                                }
                                                HashSet hashSet5 = new HashSet();
                                                zzkh[] zzkhVarArr = next.zzasd;
                                                j = j3;
                                                int length5 = zzkhVarArr.length;
                                                int i8 = 0;
                                                while (i8 < length5) {
                                                    int i9 = length5;
                                                    zzkh zzkhVar = zzkhVarArr[i8];
                                                    zzkh[] zzkhVarArr2 = zzkhVarArr;
                                                    if (TextUtils.isEmpty(zzkhVar.zzask)) {
                                                        zzgf().zziv().zzg("null or empty param name in filter. event", zzgb().zzbi(str25));
                                                        str12 = str15;
                                                        break;
                                                    }
                                                    hashSet5.add(zzkhVar.zzask);
                                                    i8++;
                                                    length5 = i9;
                                                    zzkhVarArr = zzkhVarArr2;
                                                }
                                                ArrayMap arrayMap22 = new ArrayMap();
                                                zzkq[] zzkqVarArr9 = zzkqVarArr3;
                                                int length6 = zzkqVarArr9.length;
                                                str12 = str15;
                                                int i10 = 0;
                                                while (true) {
                                                    if (i10 < length6) {
                                                        int i11 = length6;
                                                        zzkq zzkqVar2 = zzkqVarArr9[i10];
                                                        zzkqVarArr3 = zzkqVarArr9;
                                                        if (hashSet5.contains(zzkqVar2.name)) {
                                                            if (zzkqVar2.zzatq == null) {
                                                                if (zzkqVar2.zzaro == null) {
                                                                    if (zzkqVar2.zzajo == null) {
                                                                        zzgf().zziv().zze("Unknown value for param. event, param", zzgb().zzbi(str25), zzgb().zzbj(zzkqVar2.name));
                                                                        break;
                                                                    }
                                                                    str17 = zzkqVar2.name;
                                                                    obj = zzkqVar2.zzajo;
                                                                } else {
                                                                    str17 = zzkqVar2.name;
                                                                    obj = zzkqVar2.zzaro;
                                                                }
                                                            } else {
                                                                str17 = zzkqVar2.name;
                                                                obj = zzkqVar2.zzatq;
                                                            }
                                                            arrayMap22.put(str17, obj);
                                                        }
                                                        i10++;
                                                        length6 = i11;
                                                        zzkqVarArr9 = zzkqVarArr3;
                                                    } else {
                                                        zzkqVarArr3 = zzkqVarArr9;
                                                        zzkh[] zzkhVarArr3 = next.zzasd;
                                                        int length7 = zzkhVarArr3.length;
                                                        int i12 = 0;
                                                        while (i12 < length7) {
                                                            zzkh zzkhVar2 = zzkhVarArr3[i12];
                                                            zzkh[] zzkhVarArr4 = zzkhVarArr3;
                                                            int i13 = length7;
                                                            boolean equals = Boolean.TRUE.equals(zzkhVar2.zzasj);
                                                            String str27 = zzkhVar2.zzask;
                                                            if (TextUtils.isEmpty(str27)) {
                                                                zzgf().zziv().zzg("Event has empty param name. event", zzgb().zzbi(str25));
                                                            } else {
                                                                arrayMap7 = arrayMap19;
                                                                V v = arrayMap22.get(str27);
                                                                ArrayMap arrayMap23 = arrayMap22;
                                                                if (v instanceof Long) {
                                                                    if (zzkhVar2.zzasi == null) {
                                                                        zziv3 = zzgf().zziv();
                                                                        zzbi = zzgb().zzbi(str25);
                                                                        zzbj = zzgb().zzbj(str27);
                                                                        str16 = "No number filter for long param. event, param";
                                                                        zziv3.zze(str16, zzbi, zzbj);
                                                                    } else {
                                                                        if (zzebVar2.zza(((Long) v).longValue(), zzkhVar2.zzasi) != null) {
                                                                            if (!(equals ^ (!r2.booleanValue()))) {
                                                                                i12++;
                                                                                zzkhVarArr3 = zzkhVarArr4;
                                                                                length7 = i13;
                                                                                arrayMap22 = arrayMap23;
                                                                                arrayMap19 = arrayMap7;
                                                                            }
                                                                        }
                                                                    }
                                                                } else if (v instanceof Double) {
                                                                    if (zzkhVar2.zzasi == null) {
                                                                        zziv3 = zzgf().zziv();
                                                                        zzbi = zzgb().zzbi(str25);
                                                                        zzbj = zzgb().zzbj(str27);
                                                                        str16 = "No number filter for double param. event, param";
                                                                        zziv3.zze(str16, zzbi, zzbj);
                                                                    } else {
                                                                        if (zzebVar2.zza(((Double) v).doubleValue(), zzkhVar2.zzasi) != null) {
                                                                            if (!(equals ^ (!r2.booleanValue()))) {
                                                                                i12++;
                                                                                zzkhVarArr3 = zzkhVarArr4;
                                                                                length7 = i13;
                                                                                arrayMap22 = arrayMap23;
                                                                                arrayMap19 = arrayMap7;
                                                                            }
                                                                        }
                                                                    }
                                                                } else if (v instanceof String) {
                                                                    if (zzkhVar2.zzash != null) {
                                                                        zza3 = zzebVar2.zza((String) v, zzkhVar2.zzash);
                                                                    } else {
                                                                        if (zzkhVar2.zzasi != null) {
                                                                            String str28 = (String) v;
                                                                            if (zzkc.zzcj(str28)) {
                                                                                zza3 = zzebVar2.zza(str28, zzkhVar2.zzasi);
                                                                            } else {
                                                                                zziv3 = zzgf().zziv();
                                                                                zzbi = zzgb().zzbi(str25);
                                                                                zzbj = zzgb().zzbj(str27);
                                                                                str16 = "Invalid param value for number filter. event, param";
                                                                            }
                                                                        } else {
                                                                            zziv3 = zzgf().zziv();
                                                                            zzbi = zzgb().zzbi(str25);
                                                                            zzbj = zzgb().zzbj(str27);
                                                                            str16 = "No filter for String param. event, param";
                                                                        }
                                                                        zziv3.zze(str16, zzbi, zzbj);
                                                                    }
                                                                    if (zza3 != null) {
                                                                        if (!(equals ^ (!zza3.booleanValue()))) {
                                                                            i12++;
                                                                            zzkhVarArr3 = zzkhVarArr4;
                                                                            length7 = i13;
                                                                            arrayMap22 = arrayMap23;
                                                                            arrayMap19 = arrayMap7;
                                                                        }
                                                                    }
                                                                } else {
                                                                    zzfh zzgf = zzgf();
                                                                    if (v == 0) {
                                                                        zzgf.zziz().zze("Missing param for filter. event, param", zzgb().zzbi(str25), zzgb().zzbj(str27));
                                                                    } else {
                                                                        zziv3 = zzgf.zziv();
                                                                        zzbi = zzgb().zzbi(str25);
                                                                        zzbj = zzgb().zzbj(str27);
                                                                        str16 = "Unknown param type. event, param";
                                                                        zziv3.zze(str16, zzbi, zzbj);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        arrayMap7 = arrayMap19;
                                                        valueOf = Boolean.valueOf(z);
                                                    }
                                                }
                                                arrayMap7 = arrayMap19;
                                                valueOf = null;
                                                zzgf().zziz().zzg("Event filter result", valueOf == null ? "null" : valueOf);
                                                if (valueOf == null) {
                                                }
                                            }
                                            map = map3;
                                            zzkpVar6 = zzkpVar9;
                                            it2 = it3;
                                            it7 = it4;
                                            j3 = j;
                                            arrayMap17 = arrayMap21;
                                            arrayMap19 = arrayMap7;
                                            arrayMap20 = arrayMap6;
                                        }
                                        arrayMap4 = arrayMap20;
                                        hashSet = hashSet4;
                                        str7 = str26;
                                        arrayMap2 = arrayMap18;
                                        arrayMap3 = arrayMap19;
                                        it2 = it2;
                                        str11 = str;
                                    }
                                }
                                zzebVar = this;
                                str13 = str11;
                                arrayMap5 = arrayMap17;
                                str14 = str7;
                                hashSet2 = hashSet;
                                arrayMap10 = arrayMap2;
                                i5 = i + 1;
                                zzkpVarArr2 = zzkpVarArr;
                                zzebVar3 = zzebVar;
                                str21 = str13;
                                str23 = str14;
                                length2 = i6;
                                bool2 = bool;
                                arrayMap15 = arrayMap5;
                                arrayMap13 = arrayMap3;
                                arrayMap14 = arrayMap4;
                                hashSet3 = hashSet2;
                                str22 = str12;
                            } else {
                                zzgf().zziv().zzg("No unique parameters in main event. eventName", str24);
                                zzkpVar6 = zzkpVar4;
                            }
                        }
                        zzebVar = zzebVar3;
                        hashSet2 = hashSet3;
                        i = i5;
                        str13 = str21;
                        arrayMap3 = arrayMap13;
                        arrayMap4 = arrayMap14;
                        str12 = str19;
                        zzkpVar6 = zzkpVar2;
                        str14 = str18;
                        i5 = i + 1;
                        zzkpVarArr2 = zzkpVarArr;
                        zzebVar3 = zzebVar;
                        str21 = str13;
                        str23 = str14;
                        length2 = i6;
                        bool2 = bool;
                        arrayMap15 = arrayMap5;
                        arrayMap13 = arrayMap3;
                        arrayMap14 = arrayMap4;
                        hashSet3 = hashSet2;
                        str22 = str12;
                    } else {
                        zzkpVar = zzkpVar6;
                        str7 = str23;
                        bool = bool2;
                        str8 = str22;
                        arrayMap = arrayMap16;
                        z = true;
                        zzkqVarArr = zzkqVarArr5;
                        if (z2) {
                            zzjc();
                            Object zzb = zzjy.zzb(zzkpVar7, "_epc");
                            j2 = ((Long) (zzb != null ? zzb : 0L)).longValue();
                            if (j2 <= 0) {
                                zzgf().zziv().zzg("Complex event with zero extra param count. eventName", str24);
                                l = l3;
                            } else {
                                l = l3;
                                zzje().zza(str, l3, j2, zzkpVar7);
                            }
                            l2 = l;
                            zzkpVar6 = zzkpVar7;
                        }
                    }
                    zzkq[] zzkqVarArr10 = zzkqVarArr;
                    str9 = str24;
                    zzkqVarArr2 = zzkqVarArr10;
                    zzf = zzje().zzf(str21, zzkpVar7.name);
                    if (zzf == null) {
                    }
                    zzje().zza(zzii);
                    long j32 = zzii.zzafs;
                    ArrayMap arrayMap172 = arrayMap;
                    String str252 = str10;
                    map = (Map) arrayMap172.get(str252);
                    if (map == null) {
                    }
                    it2 = map.keySet().iterator();
                    while (it2.hasNext()) {
                    }
                    zzebVar = this;
                    str13 = str11;
                    arrayMap5 = arrayMap172;
                    str14 = str7;
                    hashSet2 = hashSet;
                    arrayMap10 = arrayMap2;
                    i5 = i + 1;
                    zzkpVarArr2 = zzkpVarArr;
                    zzebVar3 = zzebVar;
                    str21 = str13;
                    str23 = str14;
                    length2 = i6;
                    bool2 = bool;
                    arrayMap15 = arrayMap5;
                    arrayMap13 = arrayMap3;
                    arrayMap14 = arrayMap4;
                    hashSet3 = hashSet2;
                    str22 = str12;
                } else {
                    zzkpVar = zzkpVar6;
                    arrayMap = arrayMap15;
                    str7 = str23;
                    bool = bool2;
                    str8 = str22;
                    z = true;
                    zzkqVarArr = zzkqVarArr5;
                }
                zzkpVar6 = zzkpVar;
                zzkq[] zzkqVarArr102 = zzkqVarArr;
                str9 = str24;
                zzkqVarArr2 = zzkqVarArr102;
                zzf = zzje().zzf(str21, zzkpVar7.name);
                if (zzf == null) {
                }
                zzje().zza(zzii);
                long j322 = zzii.zzafs;
                ArrayMap arrayMap1722 = arrayMap;
                String str2522 = str10;
                map = (Map) arrayMap1722.get(str2522);
                if (map == null) {
                }
                it2 = map.keySet().iterator();
                while (it2.hasNext()) {
                }
                zzebVar = this;
                str13 = str11;
                arrayMap5 = arrayMap1722;
                str14 = str7;
                hashSet2 = hashSet;
                arrayMap10 = arrayMap2;
                i5 = i + 1;
                zzkpVarArr2 = zzkpVarArr;
                zzebVar3 = zzebVar;
                str21 = str13;
                str23 = str14;
                length2 = i6;
                bool2 = bool;
                arrayMap15 = arrayMap5;
                arrayMap13 = arrayMap3;
                arrayMap14 = arrayMap4;
                hashSet3 = hashSet2;
                str22 = str12;
            }
        }
        zzeb zzebVar4 = zzebVar3;
        String str29 = str22;
        HashSet hashSet6 = hashSet3;
        String str30 = str21;
        ArrayMap arrayMap24 = arrayMap13;
        ArrayMap arrayMap25 = arrayMap14;
        String str31 = str23;
        zzku[] zzkuVarArr2 = zzkuVarArr;
        if (zzkuVarArr2 != null) {
            ArrayMap arrayMap26 = new ArrayMap();
            int length8 = zzkuVarArr2.length;
            int i14 = 0;
            while (i14 < length8) {
                zzku zzkuVar = zzkuVarArr2[i14];
                Map<Integer, List<zzkj>> map4 = (Map) arrayMap26.get(zzkuVar.name);
                if (map4 == null) {
                    map4 = zzje().zzl(str30, zzkuVar.name);
                    if (map4 == null) {
                        map4 = new ArrayMap<>();
                    }
                    arrayMap26.put(zzkuVar.name, map4);
                }
                Iterator<Integer> it8 = map4.keySet().iterator();
                while (it8.hasNext()) {
                    int intValue3 = it8.next().intValue();
                    if (hashSet6.contains(Integer.valueOf(intValue3))) {
                        zzgf().zziz().zzg(str31, Integer.valueOf(intValue3));
                    } else {
                        zzko zzkoVar4 = (zzko) arrayMap10.get(Integer.valueOf(intValue3));
                        ArrayMap arrayMap27 = arrayMap24;
                        BitSet bitSet5 = (BitSet) arrayMap27.get(Integer.valueOf(intValue3));
                        ArrayMap arrayMap28 = arrayMap26;
                        ArrayMap arrayMap29 = arrayMap25;
                        BitSet bitSet6 = (BitSet) arrayMap29.get(Integer.valueOf(intValue3));
                        if (zzkoVar4 == null) {
                            zzko zzkoVar5 = new zzko();
                            arrayMap10.put(Integer.valueOf(intValue3), zzkoVar5);
                            zzkoVar5.zzatk = true;
                            bitSet5 = new BitSet();
                            arrayMap27.put(Integer.valueOf(intValue3), bitSet5);
                            bitSet6 = new BitSet();
                            arrayMap29.put(Integer.valueOf(intValue3), bitSet6);
                        }
                        Iterator<zzkj> it9 = map4.get(Integer.valueOf(intValue3)).iterator();
                        while (it9.hasNext()) {
                            int i15 = length8;
                            zzkj next2 = it9.next();
                            Map<Integer, List<zzkj>> map5 = map4;
                            Iterator<Integer> it10 = it8;
                            if (zzgf().isLoggable(2)) {
                                str3 = str31;
                                it = it9;
                                zzgf().zziz().zzd("Evaluating filter. audience, filter, property", Integer.valueOf(intValue3), next2.zzasb, zzgb().zzbk(next2.zzasr));
                                str4 = str29;
                                zzgf().zziz().zzg(str4, zzjc().zza(next2));
                            } else {
                                str3 = str31;
                                it = it9;
                                str4 = str29;
                            }
                            if (next2.zzasb == null || next2.zzasb.intValue() > 256) {
                                str29 = str4;
                                zzgf().zziv().zze("Invalid property filter ID. appId, id", zzfh.zzbl(str), String.valueOf(next2.zzasb));
                                hashSet6.add(Integer.valueOf(intValue3));
                                arrayMap26 = arrayMap28;
                                arrayMap25 = arrayMap29;
                                arrayMap24 = arrayMap27;
                                map4 = map5;
                                length8 = i15;
                                it8 = it10;
                                str31 = str3;
                                break;
                            }
                            if (bitSet5.get(next2.zzasb.intValue())) {
                                zzgf().zziz().zze("Property filter already evaluated true. audience ID, filter ID", Integer.valueOf(intValue3), next2.zzasb);
                                str29 = str4;
                                map4 = map5;
                                length8 = i15;
                                it8 = it10;
                                it9 = it;
                                str31 = str3;
                            } else {
                                zzkh zzkhVar3 = next2.zzass;
                                if (zzkhVar3 == null) {
                                    zziv2 = zzgf().zziv();
                                    zzbk2 = zzgb().zzbk(zzkuVar.name);
                                    str6 = "Missing property filter. property";
                                } else {
                                    boolean equals2 = Boolean.TRUE.equals(zzkhVar3.zzasj);
                                    if (zzkuVar.zzatq == null) {
                                        str29 = str4;
                                        if (zzkuVar.zzaro == null) {
                                            if (zzkuVar.zzajo == null) {
                                                zziv = zzgf().zziv();
                                                zzbk = zzgb().zzbk(zzkuVar.name);
                                                str5 = "User property has no value, property";
                                            } else if (zzkhVar3.zzash != null) {
                                                zza = zzebVar4.zza(zzkuVar.zzajo, zzkhVar3.zzash);
                                            } else if (zzkhVar3.zzasi == null) {
                                                zziv = zzgf().zziv();
                                                zzbk = zzgb().zzbk(zzkuVar.name);
                                                str5 = "No string or number filter defined. property";
                                            } else if (zzkc.zzcj(zzkuVar.zzajo)) {
                                                zza = zzebVar4.zza(zzkuVar.zzajo, zzkhVar3.zzasi);
                                            } else {
                                                zzgf().zziv().zze("Invalid user property value for Numeric number filter. property, value", zzgb().zzbk(zzkuVar.name), zzkuVar.zzajo);
                                                zza2 = null;
                                                zzgf().zziz().zzg("Property filter result", zza2 == null ? "null" : zza2);
                                                if (zza2 == null) {
                                                }
                                                map4 = map5;
                                                length8 = i15;
                                                it8 = it10;
                                                it9 = it;
                                                str31 = str3;
                                            }
                                            zziv.zzg(str5, zzbk);
                                            zza2 = null;
                                            zzgf().zziz().zzg("Property filter result", zza2 == null ? "null" : zza2);
                                            if (zza2 == null) {
                                            }
                                            map4 = map5;
                                            length8 = i15;
                                            it8 = it10;
                                            it9 = it;
                                            str31 = str3;
                                        } else if (zzkhVar3.zzasi == null) {
                                            zziv = zzgf().zziv();
                                            zzbk = zzgb().zzbk(zzkuVar.name);
                                            str5 = "No number filter for double property. property";
                                            zziv.zzg(str5, zzbk);
                                            zza2 = null;
                                            zzgf().zziz().zzg("Property filter result", zza2 == null ? "null" : zza2);
                                            if (zza2 == null) {
                                                hashSet6.add(Integer.valueOf(intValue3));
                                            } else {
                                                bitSet6.set(next2.zzasb.intValue());
                                                if (zza2.booleanValue()) {
                                                    bitSet5.set(next2.zzasb.intValue());
                                                }
                                            }
                                            map4 = map5;
                                            length8 = i15;
                                            it8 = it10;
                                            it9 = it;
                                            str31 = str3;
                                        } else {
                                            zza = zzebVar4.zza(zzkuVar.zzaro.doubleValue(), zzkhVar3.zzasi);
                                        }
                                    } else if (zzkhVar3.zzasi == null) {
                                        zziv2 = zzgf().zziv();
                                        zzbk2 = zzgb().zzbk(zzkuVar.name);
                                        str6 = "No number filter for long property. property";
                                    } else {
                                        str29 = str4;
                                        zza = zzebVar4.zza(zzkuVar.zzatq.longValue(), zzkhVar3.zzasi);
                                    }
                                    zza2 = zza(zza, equals2);
                                    zzgf().zziz().zzg("Property filter result", zza2 == null ? "null" : zza2);
                                    if (zza2 == null) {
                                    }
                                    map4 = map5;
                                    length8 = i15;
                                    it8 = it10;
                                    it9 = it;
                                    str31 = str3;
                                }
                                zziv2.zzg(str6, zzbk2);
                                str29 = str4;
                                zza2 = null;
                                zzgf().zziz().zzg("Property filter result", zza2 == null ? "null" : zza2);
                                if (zza2 == null) {
                                }
                                map4 = map5;
                                length8 = i15;
                                it8 = it10;
                                it9 = it;
                                str31 = str3;
                            }
                        }
                        arrayMap26 = arrayMap28;
                        arrayMap25 = arrayMap29;
                        arrayMap24 = arrayMap27;
                    }
                }
                i14++;
                str30 = str;
                zzkuVarArr2 = zzkuVarArr;
            }
        }
        ArrayMap arrayMap30 = arrayMap24;
        ArrayMap arrayMap31 = arrayMap25;
        zzko[] zzkoVarArr = new zzko[arrayMap30.size()];
        Iterator it11 = arrayMap30.keySet().iterator();
        int i16 = 0;
        while (it11.hasNext()) {
            int intValue4 = ((Integer) it11.next()).intValue();
            if (!hashSet6.contains(Integer.valueOf(intValue4))) {
                zzko zzkoVar6 = (zzko) arrayMap10.get(Integer.valueOf(intValue4));
                if (zzkoVar6 == null) {
                    zzkoVar6 = new zzko();
                }
                int i17 = i16 + 1;
                zzkoVarArr[i16] = zzkoVar6;
                zzkoVar6.zzarx = Integer.valueOf(intValue4);
                zzkoVar6.zzati = new zzkt();
                zzkoVar6.zzati.zzaux = zzkc.zza((BitSet) arrayMap30.get(Integer.valueOf(intValue4)));
                zzkoVar6.zzati.zzauw = zzkc.zza((BitSet) arrayMap31.get(Integer.valueOf(intValue4)));
                zzej zzje2 = zzje();
                zzkt zzktVar2 = zzkoVar6.zzati;
                zzje2.zzch();
                zzje2.zzab();
                Preconditions.checkNotEmpty(str);
                Preconditions.checkNotNull(zzktVar2);
                try {
                    zzvv = zzktVar2.zzvv();
                    bArr = new byte[zzvv];
                } catch (IOException e4) {
                    e = e4;
                }
                try {
                    zzaby zzb2 = zzaby.zzb(bArr, 0, zzvv);
                    zzktVar2.zza(zzb2);
                    zzb2.zzvn();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("app_id", str);
                    contentValues.put("audience_id", Integer.valueOf(intValue4));
                    contentValues.put("current_results", bArr);
                    try {
                        try {
                            if (zzje2.getWritableDatabase().insertWithOnConflict("audience_filter_values", null, contentValues, 5) == -1) {
                                zzje2.zzgf().zzis().zzg("Failed to insert filter results (got -1). appId", zzfh.zzbl(str));
                            }
                        } catch (SQLiteException e5) {
                            e = e5;
                            zzis = zzje2.zzgf().zzis();
                            zzbl = zzfh.zzbl(str);
                            str2 = "Error storing filter results. appId";
                            zzis.zze(str2, zzbl, e);
                            i16 = i17;
                        }
                    } catch (SQLiteException e6) {
                        e = e6;
                    }
                } catch (IOException e7) {
                    e = e7;
                    zzis = zzje2.zzgf().zzis();
                    zzbl = zzfh.zzbl(str);
                    str2 = "Configuration loss. Failed to serialize filter results. appId";
                    zzis.zze(str2, zzbl, e);
                    i16 = i17;
                }
                i16 = i17;
            }
        }
        return (zzko[]) Arrays.copyOf(zzkoVarArr, i16);
    }

    @Override // com.google.android.gms.internal.measurement.zzjr
    protected final boolean zzhh() {
        return false;
    }
}
