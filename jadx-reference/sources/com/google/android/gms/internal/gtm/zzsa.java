package com.google.android.gms.internal.gtm;

import cn.thinkingdata.core.router.TRouterMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzsa {
    /* JADX DEBUG: Multi-variable search result rejected for r2v2, resolved type: MessageType */
    /* JADX WARN: Multi-variable type inference failed */
    public static zzak zza(zzak zzakVar) {
        zzal zzg = zzak.zzg();
        zzg.zzt(1);
        zzg.zzt(zzakVar.zzO());
        zzg.zzi();
        zzg.zza(zzakVar.zzq());
        zzg.zzo(zzakVar.zzN());
        return (zzak) zzg.zzC();
    }

    public static zzrs zzb(zzaa zzaaVar) throws zzrz {
        zzak[] zzakVarArr = new zzak[zzaaVar.zzf()];
        for (int i = 0; i < zzaaVar.zzf(); i++) {
            zze(i, zzaaVar, zzakVarArr, new HashSet(0));
        }
        zzru zzruVar = new zzru(null);
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < zzaaVar.zze(); i2++) {
            arrayList.add(zzf(zzaaVar.zzi(i2), zzaaVar, zzakVarArr, i2));
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i3 = 0; i3 < zzaaVar.zzc(); i3++) {
            arrayList2.add(zzf(zzaaVar.zzh(i3), zzaaVar, zzakVarArr, i3));
        }
        ArrayList arrayList3 = new ArrayList();
        for (int i4 = 0; i4 < zzaaVar.zza(); i4++) {
            zzro zzf = zzf(zzaaVar.zzg(i4), zzaaVar, zzakVarArr, i4);
            zzruVar.zzb(zzf);
            arrayList3.add(zzf);
        }
        for (zzac zzacVar : zzaaVar.zzr()) {
            zzry zzryVar = new zzry(null);
            Iterator<Integer> it = zzacVar.zzh().iterator();
            while (it.hasNext()) {
                zzryVar.zzg((zzro) arrayList2.get(it.next().intValue()));
            }
            Iterator<Integer> it2 = zzacVar.zzg().iterator();
            while (it2.hasNext()) {
                zzryVar.zzf((zzro) arrayList2.get(it2.next().intValue()));
            }
            Iterator<Integer> it3 = zzacVar.zze().iterator();
            while (it3.hasNext()) {
                zzryVar.zzd((zzro) arrayList.get(it3.next().intValue()));
            }
            Iterator<Integer> it4 = zzacVar.zzf().iterator();
            while (it4.hasNext()) {
                zzryVar.zze(zzaaVar.zzn(it4.next().intValue()).zzp());
            }
            Iterator<Integer> it5 = zzacVar.zzk().iterator();
            while (it5.hasNext()) {
                zzryVar.zzj((zzro) arrayList.get(it5.next().intValue()));
            }
            Iterator<Integer> it6 = zzacVar.zzl().iterator();
            while (it6.hasNext()) {
                zzryVar.zzk(zzaaVar.zzn(it6.next().intValue()).zzp());
            }
            Iterator<Integer> it7 = zzacVar.zzc().iterator();
            while (it7.hasNext()) {
                zzryVar.zzb((zzro) arrayList3.get(it7.next().intValue()));
            }
            Iterator<Integer> it8 = zzacVar.zzd().iterator();
            while (it8.hasNext()) {
                zzryVar.zzc(zzaaVar.zzn(it8.next().intValue()).zzp());
            }
            Iterator<Integer> it9 = zzacVar.zzi().iterator();
            while (it9.hasNext()) {
                zzryVar.zzh((zzro) arrayList3.get(it9.next().intValue()));
            }
            Iterator<Integer> it10 = zzacVar.zzj().iterator();
            while (it10.hasNext()) {
                zzryVar.zzi(zzaaVar.zzn(it10.next().intValue()).zzp());
            }
            zzruVar.zzc(zzryVar.zza());
        }
        zzruVar.zze(zzaaVar.zzo());
        zzruVar.zzd(zzaaVar.zzd());
        return zzruVar.zza();
    }

    public static void zzc(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[1024];
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                return;
            } else {
                outputStream.write(bArr, 0, read);
            }
        }
    }

    private static zzae zzd(zzak zzakVar) throws zzrz {
        if (!zzakVar.zzW(zzae.zza)) {
            String valueOf = String.valueOf(zzakVar);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 54);
            sb.append("Expected a ServingValue and didn't get one. Value is: ");
            sb.append(valueOf);
            zzh(sb.toString());
        }
        return (zzae) zzakVar.zzV(zzae.zza);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v12, resolved type: BuilderType */
    /* JADX DEBUG: Multi-variable search result rejected for r0v14, resolved type: MessageType */
    /* JADX DEBUG: Multi-variable search result rejected for r0v17, resolved type: BuilderType */
    /* JADX DEBUG: Multi-variable search result rejected for r0v19, resolved type: MessageType */
    /* JADX DEBUG: Multi-variable search result rejected for r0v2, resolved type: MessageType */
    /* JADX DEBUG: Multi-variable search result rejected for r0v22, resolved type: BuilderType */
    /* JADX DEBUG: Multi-variable search result rejected for r0v9, resolved type: MessageType */
    /* JADX DEBUG: Multi-variable search result rejected for r1v4, resolved type: BuilderType */
    /* JADX DEBUG: Multi-variable search result rejected for r2v10, resolved type: MessageType */
    /* JADX DEBUG: Multi-variable search result rejected for r2v16, resolved type: MessageType */
    /* JADX DEBUG: Multi-variable search result rejected for r2v21, resolved type: MessageType */
    /* JADX DEBUG: Multi-variable search result rejected for r2v3, resolved type: MessageType */
    /* JADX DEBUG: Multi-variable search result rejected for r2v6, resolved type: BuilderType */
    /* JADX DEBUG: Multi-variable search result rejected for r7v3, resolved type: MessageType */
    /* JADX DEBUG: Multi-variable search result rejected for r8v1, resolved type: MessageType */
    /* JADX WARN: Multi-variable type inference failed */
    private static zzak zze(int i, zzaa zzaaVar, zzak[] zzakVarArr, Set<Integer> set) throws zzrz {
        zzal zzalVar;
        Integer valueOf = Integer.valueOf(i);
        if (set.contains(valueOf)) {
            String valueOf2 = String.valueOf(set);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf2).length() + 90);
            sb.append("Value cycle detected.  Current value reference: ");
            sb.append(i);
            sb.append(".  Previous value references: ");
            sb.append(valueOf2);
            sb.append(TRouterMap.DOT);
            zzh(sb.toString());
        }
        zzal zzalVar2 = (zzal) ((zzak) zzg(zzaaVar.zzs(), i, "values")).zzZ();
        zzak zzakVar = zzakVarArr[i];
        if (zzakVar != null) {
            return zzakVar;
        }
        set.add(valueOf);
        int zzu = zzalVar2.zzu();
        if (zzu == 2) {
            zzae zzd = zzd((zzak) zzalVar2.zzC());
            zzal zzalVar3 = (zzal) zza((zzak) zzalVar2.zzC()).zzZ();
            zzalVar3.zzj();
            Iterator<Integer> it = zzd.zzf().iterator();
            while (it.hasNext()) {
                zzalVar3.zze(zze(it.next().intValue(), zzaaVar, zzakVarArr, set));
            }
            zzalVar = zzalVar3;
        } else if (zzu == 3) {
            zzalVar = (zzal) zza((zzak) zzalVar2.zzC()).zzZ();
            zzae zzd2 = zzd((zzak) zzalVar2.zzC());
            if (zzd2.zzc() != zzd2.zzd()) {
                int zzc = zzd2.zzc();
                int zzd3 = zzd2.zzd();
                StringBuilder sb2 = new StringBuilder(58);
                sb2.append("Uneven map keys (");
                sb2.append(zzc);
                sb2.append(") and map values (");
                sb2.append(zzd3);
                sb2.append(")");
                zzh(sb2.toString());
            }
            zzalVar.zzk();
            zzalVar.zzl();
            Iterator<Integer> it2 = zzd2.zzg().iterator();
            while (it2.hasNext()) {
                zzalVar.zzf(zze(it2.next().intValue(), zzaaVar, zzakVarArr, set));
            }
            Iterator<Integer> it3 = zzd2.zzh().iterator();
            while (it3.hasNext()) {
                zzalVar.zzg(zze(it3.next().intValue(), zzaaVar, zzakVarArr, set));
            }
        } else if (zzu == 4) {
            zzalVar = (zzal) zza((zzak) zzalVar2.zzC()).zzZ();
            zzalVar.zzr(com.google.android.gms.tagmanager.zzfv.zzn(com.google.android.gms.tagmanager.zzfv.zzl(zze(zzd((zzak) zzalVar2.zzC()).zza(), zzaaVar, zzakVarArr, set))));
        } else if (zzu != 7) {
            zzalVar = zzalVar2;
        } else {
            zzalVar = (zzal) zza((zzak) zzalVar2.zzC()).zzZ();
            zzae zzd4 = zzd((zzak) zzalVar2.zzC());
            zzalVar.zzm();
            Iterator<Integer> it4 = zzd4.zzi().iterator();
            while (it4.hasNext()) {
                zzalVar.zzh(zze(it4.next().intValue(), zzaaVar, zzakVarArr, set));
            }
        }
        if (zzalVar == null) {
            String valueOf3 = String.valueOf(zzalVar2);
            StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf3).length() + 15);
            sb3.append("Invalid value: ");
            sb3.append(valueOf3);
            zzh(sb3.toString());
        }
        zzakVarArr[i] = (zzak) zzalVar.zzC();
        set.remove(Integer.valueOf(i));
        return (zzak) zzalVar.zzC();
    }

    private static zzro zzf(zzs zzsVar, zzaa zzaaVar, zzak[] zzakVarArr, int i) throws zzrz {
        zzrq zzrqVar = new zzrq(null);
        Iterator<Integer> it = zzsVar.zzc().iterator();
        while (it.hasNext()) {
            zzy zzyVar = (zzy) zzg(zzaaVar.zzq(), it.next().intValue(), "properties");
            String str = (String) zzg(zzaaVar.zzp(), zzyVar.zza(), "keys");
            int zzc = zzyVar.zzc();
            if (zzc < 0 || zzc >= zzakVarArr.length) {
                StringBuilder sb = new StringBuilder(51);
                sb.append("Index out of bounds detected: ");
                sb.append(zzc);
                sb.append(" in values");
                zzh(sb.toString());
            }
            zzak zzakVar = zzakVarArr[zzc];
            if (zzb.PUSH_AFTER_EVALUATE.toString().equals(str)) {
                zzrqVar.zzc(zzakVar);
            } else {
                zzrqVar.zzb(str, zzakVar);
            }
        }
        return zzrqVar.zza();
    }

    private static <T> T zzg(List<T> list, int i, String str) throws zzrz {
        if (i < 0 || i >= list.size()) {
            StringBuilder sb = new StringBuilder(str.length() + 45);
            sb.append("Index out of bounds detected: ");
            sb.append(i);
            sb.append(" in ");
            sb.append(str);
            zzh(sb.toString());
        }
        return list.get(i);
    }

    private static void zzh(String str) throws zzrz {
        com.google.android.gms.tagmanager.zzdh.zza(str);
        throw new zzrz(str);
    }
}
