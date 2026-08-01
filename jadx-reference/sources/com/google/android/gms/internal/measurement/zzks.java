package com.google.android.gms.internal.measurement;

import cz.msebera.android.httpclient.HttpStatus;
import java.io.IOException;

/* loaded from: classes.dex */
public final class zzks extends zzaca<zzks> {
    private static volatile zzks[] zzats;
    public Integer zzatt = null;
    public zzkp[] zzatu = zzkp.zzlu();
    public zzku[] zzatv = zzku.zzlx();
    public Long zzatw = null;
    public Long zzatx = null;
    public Long zzaty = null;
    public Long zzatz = null;
    public Long zzaua = null;
    public String zzaub = null;
    public String zzauc = null;
    public String zzaud = null;
    public String zzafo = null;
    public Integer zzaue = null;
    public String zzadt = null;
    public String zzti = null;
    public String zzth = null;
    public Long zzauf = null;
    public Long zzaug = null;
    public String zzauh = null;
    public Boolean zzaui = null;
    public String zzadl = null;
    public Long zzauj = null;
    public Integer zzauk = null;
    public String zzaek = null;
    public String zzadm = null;
    public Boolean zzaul = null;
    public zzko[] zzaum = zzko.zzlt();
    public String zzado = null;
    public Integer zzaun = null;
    private Integer zzauo = null;
    private Integer zzaup = null;
    public String zzauq = null;
    public Long zzaur = null;
    public Long zzaus = null;
    public String zzaut = null;
    private String zzauu = null;
    public Integer zzauv = null;

    public zzks() {
        this.zzbxg = null;
        this.zzbxr = -1;
    }

    public static zzks[] zzlw() {
        if (zzats == null) {
            synchronized (zzace.zzbxq) {
                if (zzats == null) {
                    zzats = new zzks[0];
                }
            }
        }
        return zzats;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzks)) {
            return false;
        }
        zzks zzksVar = (zzks) obj;
        Integer num = this.zzatt;
        if (num == null) {
            if (zzksVar.zzatt != null) {
                return false;
            }
        } else if (!num.equals(zzksVar.zzatt)) {
            return false;
        }
        if (!zzace.equals(this.zzatu, zzksVar.zzatu) || !zzace.equals(this.zzatv, zzksVar.zzatv)) {
            return false;
        }
        Long l = this.zzatw;
        if (l == null) {
            if (zzksVar.zzatw != null) {
                return false;
            }
        } else if (!l.equals(zzksVar.zzatw)) {
            return false;
        }
        Long l2 = this.zzatx;
        if (l2 == null) {
            if (zzksVar.zzatx != null) {
                return false;
            }
        } else if (!l2.equals(zzksVar.zzatx)) {
            return false;
        }
        Long l3 = this.zzaty;
        if (l3 == null) {
            if (zzksVar.zzaty != null) {
                return false;
            }
        } else if (!l3.equals(zzksVar.zzaty)) {
            return false;
        }
        Long l4 = this.zzatz;
        if (l4 == null) {
            if (zzksVar.zzatz != null) {
                return false;
            }
        } else if (!l4.equals(zzksVar.zzatz)) {
            return false;
        }
        Long l5 = this.zzaua;
        if (l5 == null) {
            if (zzksVar.zzaua != null) {
                return false;
            }
        } else if (!l5.equals(zzksVar.zzaua)) {
            return false;
        }
        String str = this.zzaub;
        if (str == null) {
            if (zzksVar.zzaub != null) {
                return false;
            }
        } else if (!str.equals(zzksVar.zzaub)) {
            return false;
        }
        String str2 = this.zzauc;
        if (str2 == null) {
            if (zzksVar.zzauc != null) {
                return false;
            }
        } else if (!str2.equals(zzksVar.zzauc)) {
            return false;
        }
        String str3 = this.zzaud;
        if (str3 == null) {
            if (zzksVar.zzaud != null) {
                return false;
            }
        } else if (!str3.equals(zzksVar.zzaud)) {
            return false;
        }
        String str4 = this.zzafo;
        if (str4 == null) {
            if (zzksVar.zzafo != null) {
                return false;
            }
        } else if (!str4.equals(zzksVar.zzafo)) {
            return false;
        }
        Integer num2 = this.zzaue;
        if (num2 == null) {
            if (zzksVar.zzaue != null) {
                return false;
            }
        } else if (!num2.equals(zzksVar.zzaue)) {
            return false;
        }
        String str5 = this.zzadt;
        if (str5 == null) {
            if (zzksVar.zzadt != null) {
                return false;
            }
        } else if (!str5.equals(zzksVar.zzadt)) {
            return false;
        }
        String str6 = this.zzti;
        if (str6 == null) {
            if (zzksVar.zzti != null) {
                return false;
            }
        } else if (!str6.equals(zzksVar.zzti)) {
            return false;
        }
        String str7 = this.zzth;
        if (str7 == null) {
            if (zzksVar.zzth != null) {
                return false;
            }
        } else if (!str7.equals(zzksVar.zzth)) {
            return false;
        }
        Long l6 = this.zzauf;
        if (l6 == null) {
            if (zzksVar.zzauf != null) {
                return false;
            }
        } else if (!l6.equals(zzksVar.zzauf)) {
            return false;
        }
        Long l7 = this.zzaug;
        if (l7 == null) {
            if (zzksVar.zzaug != null) {
                return false;
            }
        } else if (!l7.equals(zzksVar.zzaug)) {
            return false;
        }
        String str8 = this.zzauh;
        if (str8 == null) {
            if (zzksVar.zzauh != null) {
                return false;
            }
        } else if (!str8.equals(zzksVar.zzauh)) {
            return false;
        }
        Boolean bool = this.zzaui;
        if (bool == null) {
            if (zzksVar.zzaui != null) {
                return false;
            }
        } else if (!bool.equals(zzksVar.zzaui)) {
            return false;
        }
        String str9 = this.zzadl;
        if (str9 == null) {
            if (zzksVar.zzadl != null) {
                return false;
            }
        } else if (!str9.equals(zzksVar.zzadl)) {
            return false;
        }
        Long l8 = this.zzauj;
        if (l8 == null) {
            if (zzksVar.zzauj != null) {
                return false;
            }
        } else if (!l8.equals(zzksVar.zzauj)) {
            return false;
        }
        Integer num3 = this.zzauk;
        if (num3 == null) {
            if (zzksVar.zzauk != null) {
                return false;
            }
        } else if (!num3.equals(zzksVar.zzauk)) {
            return false;
        }
        String str10 = this.zzaek;
        if (str10 == null) {
            if (zzksVar.zzaek != null) {
                return false;
            }
        } else if (!str10.equals(zzksVar.zzaek)) {
            return false;
        }
        String str11 = this.zzadm;
        if (str11 == null) {
            if (zzksVar.zzadm != null) {
                return false;
            }
        } else if (!str11.equals(zzksVar.zzadm)) {
            return false;
        }
        Boolean bool2 = this.zzaul;
        if (bool2 == null) {
            if (zzksVar.zzaul != null) {
                return false;
            }
        } else if (!bool2.equals(zzksVar.zzaul)) {
            return false;
        }
        if (!zzace.equals(this.zzaum, zzksVar.zzaum)) {
            return false;
        }
        String str12 = this.zzado;
        if (str12 == null) {
            if (zzksVar.zzado != null) {
                return false;
            }
        } else if (!str12.equals(zzksVar.zzado)) {
            return false;
        }
        Integer num4 = this.zzaun;
        if (num4 == null) {
            if (zzksVar.zzaun != null) {
                return false;
            }
        } else if (!num4.equals(zzksVar.zzaun)) {
            return false;
        }
        Integer num5 = this.zzauo;
        if (num5 == null) {
            if (zzksVar.zzauo != null) {
                return false;
            }
        } else if (!num5.equals(zzksVar.zzauo)) {
            return false;
        }
        Integer num6 = this.zzaup;
        if (num6 == null) {
            if (zzksVar.zzaup != null) {
                return false;
            }
        } else if (!num6.equals(zzksVar.zzaup)) {
            return false;
        }
        String str13 = this.zzauq;
        if (str13 == null) {
            if (zzksVar.zzauq != null) {
                return false;
            }
        } else if (!str13.equals(zzksVar.zzauq)) {
            return false;
        }
        Long l9 = this.zzaur;
        if (l9 == null) {
            if (zzksVar.zzaur != null) {
                return false;
            }
        } else if (!l9.equals(zzksVar.zzaur)) {
            return false;
        }
        Long l10 = this.zzaus;
        if (l10 == null) {
            if (zzksVar.zzaus != null) {
                return false;
            }
        } else if (!l10.equals(zzksVar.zzaus)) {
            return false;
        }
        String str14 = this.zzaut;
        if (str14 == null) {
            if (zzksVar.zzaut != null) {
                return false;
            }
        } else if (!str14.equals(zzksVar.zzaut)) {
            return false;
        }
        String str15 = this.zzauu;
        if (str15 == null) {
            if (zzksVar.zzauu != null) {
                return false;
            }
        } else if (!str15.equals(zzksVar.zzauu)) {
            return false;
        }
        Integer num7 = this.zzauv;
        if (num7 == null) {
            if (zzksVar.zzauv != null) {
                return false;
            }
        } else if (!num7.equals(zzksVar.zzauv)) {
            return false;
        }
        return (this.zzbxg == null || this.zzbxg.isEmpty()) ? zzksVar.zzbxg == null || zzksVar.zzbxg.isEmpty() : this.zzbxg.equals(zzksVar.zzbxg);
    }

    public final int hashCode() {
        int hashCode = (getClass().getName().hashCode() + 527) * 31;
        Integer num = this.zzatt;
        int i = 0;
        int hashCode2 = (((((hashCode + (num == null ? 0 : num.hashCode())) * 31) + zzace.hashCode(this.zzatu)) * 31) + zzace.hashCode(this.zzatv)) * 31;
        Long l = this.zzatw;
        int hashCode3 = (hashCode2 + (l == null ? 0 : l.hashCode())) * 31;
        Long l2 = this.zzatx;
        int hashCode4 = (hashCode3 + (l2 == null ? 0 : l2.hashCode())) * 31;
        Long l3 = this.zzaty;
        int hashCode5 = (hashCode4 + (l3 == null ? 0 : l3.hashCode())) * 31;
        Long l4 = this.zzatz;
        int hashCode6 = (hashCode5 + (l4 == null ? 0 : l4.hashCode())) * 31;
        Long l5 = this.zzaua;
        int hashCode7 = (hashCode6 + (l5 == null ? 0 : l5.hashCode())) * 31;
        String str = this.zzaub;
        int hashCode8 = (hashCode7 + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.zzauc;
        int hashCode9 = (hashCode8 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.zzaud;
        int hashCode10 = (hashCode9 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.zzafo;
        int hashCode11 = (hashCode10 + (str4 == null ? 0 : str4.hashCode())) * 31;
        Integer num2 = this.zzaue;
        int hashCode12 = (hashCode11 + (num2 == null ? 0 : num2.hashCode())) * 31;
        String str5 = this.zzadt;
        int hashCode13 = (hashCode12 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.zzti;
        int hashCode14 = (hashCode13 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.zzth;
        int hashCode15 = (hashCode14 + (str7 == null ? 0 : str7.hashCode())) * 31;
        Long l6 = this.zzauf;
        int hashCode16 = (hashCode15 + (l6 == null ? 0 : l6.hashCode())) * 31;
        Long l7 = this.zzaug;
        int hashCode17 = (hashCode16 + (l7 == null ? 0 : l7.hashCode())) * 31;
        String str8 = this.zzauh;
        int hashCode18 = (hashCode17 + (str8 == null ? 0 : str8.hashCode())) * 31;
        Boolean bool = this.zzaui;
        int hashCode19 = (hashCode18 + (bool == null ? 0 : bool.hashCode())) * 31;
        String str9 = this.zzadl;
        int hashCode20 = (hashCode19 + (str9 == null ? 0 : str9.hashCode())) * 31;
        Long l8 = this.zzauj;
        int hashCode21 = (hashCode20 + (l8 == null ? 0 : l8.hashCode())) * 31;
        Integer num3 = this.zzauk;
        int hashCode22 = (hashCode21 + (num3 == null ? 0 : num3.hashCode())) * 31;
        String str10 = this.zzaek;
        int hashCode23 = (hashCode22 + (str10 == null ? 0 : str10.hashCode())) * 31;
        String str11 = this.zzadm;
        int hashCode24 = (hashCode23 + (str11 == null ? 0 : str11.hashCode())) * 31;
        Boolean bool2 = this.zzaul;
        int hashCode25 = (((hashCode24 + (bool2 == null ? 0 : bool2.hashCode())) * 31) + zzace.hashCode(this.zzaum)) * 31;
        String str12 = this.zzado;
        int hashCode26 = (hashCode25 + (str12 == null ? 0 : str12.hashCode())) * 31;
        Integer num4 = this.zzaun;
        int hashCode27 = (hashCode26 + (num4 == null ? 0 : num4.hashCode())) * 31;
        Integer num5 = this.zzauo;
        int hashCode28 = (hashCode27 + (num5 == null ? 0 : num5.hashCode())) * 31;
        Integer num6 = this.zzaup;
        int hashCode29 = (hashCode28 + (num6 == null ? 0 : num6.hashCode())) * 31;
        String str13 = this.zzauq;
        int hashCode30 = (hashCode29 + (str13 == null ? 0 : str13.hashCode())) * 31;
        Long l9 = this.zzaur;
        int hashCode31 = (hashCode30 + (l9 == null ? 0 : l9.hashCode())) * 31;
        Long l10 = this.zzaus;
        int hashCode32 = (hashCode31 + (l10 == null ? 0 : l10.hashCode())) * 31;
        String str14 = this.zzaut;
        int hashCode33 = (hashCode32 + (str14 == null ? 0 : str14.hashCode())) * 31;
        String str15 = this.zzauu;
        int hashCode34 = (hashCode33 + (str15 == null ? 0 : str15.hashCode())) * 31;
        Integer num7 = this.zzauv;
        int hashCode35 = (hashCode34 + (num7 == null ? 0 : num7.hashCode())) * 31;
        if (this.zzbxg != null && !this.zzbxg.isEmpty()) {
            i = this.zzbxg.hashCode();
        }
        return hashCode35 + i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final int zza() {
        int zza = super.zza();
        Integer num = this.zzatt;
        if (num != null) {
            zza += zzaby.zzf(1, num.intValue());
        }
        zzkp[] zzkpVarArr = this.zzatu;
        int i = 0;
        if (zzkpVarArr != null && zzkpVarArr.length > 0) {
            int i2 = 0;
            while (true) {
                zzkp[] zzkpVarArr2 = this.zzatu;
                if (i2 >= zzkpVarArr2.length) {
                    break;
                }
                zzkp zzkpVar = zzkpVarArr2[i2];
                if (zzkpVar != null) {
                    zza += zzaby.zzb(2, zzkpVar);
                }
                i2++;
            }
        }
        zzku[] zzkuVarArr = this.zzatv;
        if (zzkuVarArr != null && zzkuVarArr.length > 0) {
            int i3 = 0;
            while (true) {
                zzku[] zzkuVarArr2 = this.zzatv;
                if (i3 >= zzkuVarArr2.length) {
                    break;
                }
                zzku zzkuVar = zzkuVarArr2[i3];
                if (zzkuVar != null) {
                    zza += zzaby.zzb(3, zzkuVar);
                }
                i3++;
            }
        }
        Long l = this.zzatw;
        if (l != null) {
            zza += zzaby.zzc(4, l.longValue());
        }
        Long l2 = this.zzatx;
        if (l2 != null) {
            zza += zzaby.zzc(5, l2.longValue());
        }
        Long l3 = this.zzaty;
        if (l3 != null) {
            zza += zzaby.zzc(6, l3.longValue());
        }
        Long l4 = this.zzaua;
        if (l4 != null) {
            zza += zzaby.zzc(7, l4.longValue());
        }
        String str = this.zzaub;
        if (str != null) {
            zza += zzaby.zzc(8, str);
        }
        String str2 = this.zzauc;
        if (str2 != null) {
            zza += zzaby.zzc(9, str2);
        }
        String str3 = this.zzaud;
        if (str3 != null) {
            zza += zzaby.zzc(10, str3);
        }
        String str4 = this.zzafo;
        if (str4 != null) {
            zza += zzaby.zzc(11, str4);
        }
        Integer num2 = this.zzaue;
        if (num2 != null) {
            zza += zzaby.zzf(12, num2.intValue());
        }
        String str5 = this.zzadt;
        if (str5 != null) {
            zza += zzaby.zzc(13, str5);
        }
        String str6 = this.zzti;
        if (str6 != null) {
            zza += zzaby.zzc(14, str6);
        }
        String str7 = this.zzth;
        if (str7 != null) {
            zza += zzaby.zzc(16, str7);
        }
        Long l5 = this.zzauf;
        if (l5 != null) {
            zza += zzaby.zzc(17, l5.longValue());
        }
        Long l6 = this.zzaug;
        if (l6 != null) {
            zza += zzaby.zzc(18, l6.longValue());
        }
        String str8 = this.zzauh;
        if (str8 != null) {
            zza += zzaby.zzc(19, str8);
        }
        Boolean bool = this.zzaui;
        if (bool != null) {
            bool.booleanValue();
            zza += zzaby.zzaq(20) + 1;
        }
        String str9 = this.zzadl;
        if (str9 != null) {
            zza += zzaby.zzc(21, str9);
        }
        Long l7 = this.zzauj;
        if (l7 != null) {
            zza += zzaby.zzc(22, l7.longValue());
        }
        Integer num3 = this.zzauk;
        if (num3 != null) {
            zza += zzaby.zzf(23, num3.intValue());
        }
        String str10 = this.zzaek;
        if (str10 != null) {
            zza += zzaby.zzc(24, str10);
        }
        String str11 = this.zzadm;
        if (str11 != null) {
            zza += zzaby.zzc(25, str11);
        }
        Long l8 = this.zzatz;
        if (l8 != null) {
            zza += zzaby.zzc(26, l8.longValue());
        }
        Boolean bool2 = this.zzaul;
        if (bool2 != null) {
            bool2.booleanValue();
            zza += zzaby.zzaq(28) + 1;
        }
        zzko[] zzkoVarArr = this.zzaum;
        if (zzkoVarArr != null && zzkoVarArr.length > 0) {
            while (true) {
                zzko[] zzkoVarArr2 = this.zzaum;
                if (i >= zzkoVarArr2.length) {
                    break;
                }
                zzko zzkoVar = zzkoVarArr2[i];
                if (zzkoVar != null) {
                    zza += zzaby.zzb(29, zzkoVar);
                }
                i++;
            }
        }
        String str12 = this.zzado;
        if (str12 != null) {
            zza += zzaby.zzc(30, str12);
        }
        Integer num4 = this.zzaun;
        if (num4 != null) {
            zza += zzaby.zzf(31, num4.intValue());
        }
        Integer num5 = this.zzauo;
        if (num5 != null) {
            zza += zzaby.zzf(32, num5.intValue());
        }
        Integer num6 = this.zzaup;
        if (num6 != null) {
            zza += zzaby.zzf(33, num6.intValue());
        }
        String str13 = this.zzauq;
        if (str13 != null) {
            zza += zzaby.zzc(34, str13);
        }
        Long l9 = this.zzaur;
        if (l9 != null) {
            zza += zzaby.zzc(35, l9.longValue());
        }
        Long l10 = this.zzaus;
        if (l10 != null) {
            zza += zzaby.zzc(36, l10.longValue());
        }
        String str14 = this.zzaut;
        if (str14 != null) {
            zza += zzaby.zzc(37, str14);
        }
        String str15 = this.zzauu;
        if (str15 != null) {
            zza += zzaby.zzc(38, str15);
        }
        Integer num7 = this.zzauv;
        return num7 != null ? zza + zzaby.zzf(39, num7.intValue()) : zza;
    }

    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final void zza(zzaby zzabyVar) throws IOException {
        Integer num = this.zzatt;
        if (num != null) {
            zzabyVar.zze(1, num.intValue());
        }
        zzkp[] zzkpVarArr = this.zzatu;
        int i = 0;
        if (zzkpVarArr != null && zzkpVarArr.length > 0) {
            int i2 = 0;
            while (true) {
                zzkp[] zzkpVarArr2 = this.zzatu;
                if (i2 >= zzkpVarArr2.length) {
                    break;
                }
                zzkp zzkpVar = zzkpVarArr2[i2];
                if (zzkpVar != null) {
                    zzabyVar.zza(2, zzkpVar);
                }
                i2++;
            }
        }
        zzku[] zzkuVarArr = this.zzatv;
        if (zzkuVarArr != null && zzkuVarArr.length > 0) {
            int i3 = 0;
            while (true) {
                zzku[] zzkuVarArr2 = this.zzatv;
                if (i3 >= zzkuVarArr2.length) {
                    break;
                }
                zzku zzkuVar = zzkuVarArr2[i3];
                if (zzkuVar != null) {
                    zzabyVar.zza(3, zzkuVar);
                }
                i3++;
            }
        }
        Long l = this.zzatw;
        if (l != null) {
            zzabyVar.zzb(4, l.longValue());
        }
        Long l2 = this.zzatx;
        if (l2 != null) {
            zzabyVar.zzb(5, l2.longValue());
        }
        Long l3 = this.zzaty;
        if (l3 != null) {
            zzabyVar.zzb(6, l3.longValue());
        }
        Long l4 = this.zzaua;
        if (l4 != null) {
            zzabyVar.zzb(7, l4.longValue());
        }
        String str = this.zzaub;
        if (str != null) {
            zzabyVar.zzb(8, str);
        }
        String str2 = this.zzauc;
        if (str2 != null) {
            zzabyVar.zzb(9, str2);
        }
        String str3 = this.zzaud;
        if (str3 != null) {
            zzabyVar.zzb(10, str3);
        }
        String str4 = this.zzafo;
        if (str4 != null) {
            zzabyVar.zzb(11, str4);
        }
        Integer num2 = this.zzaue;
        if (num2 != null) {
            zzabyVar.zze(12, num2.intValue());
        }
        String str5 = this.zzadt;
        if (str5 != null) {
            zzabyVar.zzb(13, str5);
        }
        String str6 = this.zzti;
        if (str6 != null) {
            zzabyVar.zzb(14, str6);
        }
        String str7 = this.zzth;
        if (str7 != null) {
            zzabyVar.zzb(16, str7);
        }
        Long l5 = this.zzauf;
        if (l5 != null) {
            zzabyVar.zzb(17, l5.longValue());
        }
        Long l6 = this.zzaug;
        if (l6 != null) {
            zzabyVar.zzb(18, l6.longValue());
        }
        String str8 = this.zzauh;
        if (str8 != null) {
            zzabyVar.zzb(19, str8);
        }
        Boolean bool = this.zzaui;
        if (bool != null) {
            zzabyVar.zza(20, bool.booleanValue());
        }
        String str9 = this.zzadl;
        if (str9 != null) {
            zzabyVar.zzb(21, str9);
        }
        Long l7 = this.zzauj;
        if (l7 != null) {
            zzabyVar.zzb(22, l7.longValue());
        }
        Integer num3 = this.zzauk;
        if (num3 != null) {
            zzabyVar.zze(23, num3.intValue());
        }
        String str10 = this.zzaek;
        if (str10 != null) {
            zzabyVar.zzb(24, str10);
        }
        String str11 = this.zzadm;
        if (str11 != null) {
            zzabyVar.zzb(25, str11);
        }
        Long l8 = this.zzatz;
        if (l8 != null) {
            zzabyVar.zzb(26, l8.longValue());
        }
        Boolean bool2 = this.zzaul;
        if (bool2 != null) {
            zzabyVar.zza(28, bool2.booleanValue());
        }
        zzko[] zzkoVarArr = this.zzaum;
        if (zzkoVarArr != null && zzkoVarArr.length > 0) {
            while (true) {
                zzko[] zzkoVarArr2 = this.zzaum;
                if (i >= zzkoVarArr2.length) {
                    break;
                }
                zzko zzkoVar = zzkoVarArr2[i];
                if (zzkoVar != null) {
                    zzabyVar.zza(29, zzkoVar);
                }
                i++;
            }
        }
        String str12 = this.zzado;
        if (str12 != null) {
            zzabyVar.zzb(30, str12);
        }
        Integer num4 = this.zzaun;
        if (num4 != null) {
            zzabyVar.zze(31, num4.intValue());
        }
        Integer num5 = this.zzauo;
        if (num5 != null) {
            zzabyVar.zze(32, num5.intValue());
        }
        Integer num6 = this.zzaup;
        if (num6 != null) {
            zzabyVar.zze(33, num6.intValue());
        }
        String str13 = this.zzauq;
        if (str13 != null) {
            zzabyVar.zzb(34, str13);
        }
        Long l9 = this.zzaur;
        if (l9 != null) {
            zzabyVar.zzb(35, l9.longValue());
        }
        Long l10 = this.zzaus;
        if (l10 != null) {
            zzabyVar.zzb(36, l10.longValue());
        }
        String str14 = this.zzaut;
        if (str14 != null) {
            zzabyVar.zzb(37, str14);
        }
        String str15 = this.zzauu;
        if (str15 != null) {
            zzabyVar.zzb(38, str15);
        }
        Integer num7 = this.zzauv;
        if (num7 != null) {
            zzabyVar.zze(39, num7.intValue());
        }
        super.zza(zzabyVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzacg
    public final /* synthetic */ zzacg zzb(zzabx zzabxVar) throws IOException {
        while (true) {
            int zzvf = zzabxVar.zzvf();
            switch (zzvf) {
                case 0:
                    return this;
                case 8:
                    this.zzatt = Integer.valueOf(zzabxVar.zzvh());
                    break;
                case 18:
                    int zzb = zzacj.zzb(zzabxVar, 18);
                    zzkp[] zzkpVarArr = this.zzatu;
                    int length = zzkpVarArr == null ? 0 : zzkpVarArr.length;
                    int i = zzb + length;
                    zzkp[] zzkpVarArr2 = new zzkp[i];
                    if (length != 0) {
                        System.arraycopy(zzkpVarArr, 0, zzkpVarArr2, 0, length);
                    }
                    while (length < i - 1) {
                        zzkpVarArr2[length] = new zzkp();
                        zzabxVar.zza(zzkpVarArr2[length]);
                        zzabxVar.zzvf();
                        length++;
                    }
                    zzkpVarArr2[length] = new zzkp();
                    zzabxVar.zza(zzkpVarArr2[length]);
                    this.zzatu = zzkpVarArr2;
                    break;
                case 26:
                    int zzb2 = zzacj.zzb(zzabxVar, 26);
                    zzku[] zzkuVarArr = this.zzatv;
                    int length2 = zzkuVarArr == null ? 0 : zzkuVarArr.length;
                    int i2 = zzb2 + length2;
                    zzku[] zzkuVarArr2 = new zzku[i2];
                    if (length2 != 0) {
                        System.arraycopy(zzkuVarArr, 0, zzkuVarArr2, 0, length2);
                    }
                    while (length2 < i2 - 1) {
                        zzkuVarArr2[length2] = new zzku();
                        zzabxVar.zza(zzkuVarArr2[length2]);
                        zzabxVar.zzvf();
                        length2++;
                    }
                    zzkuVarArr2[length2] = new zzku();
                    zzabxVar.zza(zzkuVarArr2[length2]);
                    this.zzatv = zzkuVarArr2;
                    break;
                case 32:
                    this.zzatw = Long.valueOf(zzabxVar.zzvi());
                    break;
                case 40:
                    this.zzatx = Long.valueOf(zzabxVar.zzvi());
                    break;
                case 48:
                    this.zzaty = Long.valueOf(zzabxVar.zzvi());
                    break;
                case 56:
                    this.zzaua = Long.valueOf(zzabxVar.zzvi());
                    break;
                case 66:
                    this.zzaub = zzabxVar.readString();
                    break;
                case 74:
                    this.zzauc = zzabxVar.readString();
                    break;
                case 82:
                    this.zzaud = zzabxVar.readString();
                    break;
                case 90:
                    this.zzafo = zzabxVar.readString();
                    break;
                case 96:
                    this.zzaue = Integer.valueOf(zzabxVar.zzvh());
                    break;
                case 106:
                    this.zzadt = zzabxVar.readString();
                    break;
                case 114:
                    this.zzti = zzabxVar.readString();
                    break;
                case 130:
                    this.zzth = zzabxVar.readString();
                    break;
                case 136:
                    this.zzauf = Long.valueOf(zzabxVar.zzvi());
                    break;
                case 144:
                    this.zzaug = Long.valueOf(zzabxVar.zzvi());
                    break;
                case 154:
                    this.zzauh = zzabxVar.readString();
                    break;
                case 160:
                    this.zzaui = Boolean.valueOf(zzabxVar.zzvg());
                    break;
                case 170:
                    this.zzadl = zzabxVar.readString();
                    break;
                case 176:
                    this.zzauj = Long.valueOf(zzabxVar.zzvi());
                    break;
                case 184:
                    this.zzauk = Integer.valueOf(zzabxVar.zzvh());
                    break;
                case 194:
                    this.zzaek = zzabxVar.readString();
                    break;
                case HttpStatus.SC_ACCEPTED /* 202 */:
                    this.zzadm = zzabxVar.readString();
                    break;
                case 208:
                    this.zzatz = Long.valueOf(zzabxVar.zzvi());
                    break;
                case 224:
                    this.zzaul = Boolean.valueOf(zzabxVar.zzvg());
                    break;
                case 234:
                    int zzb3 = zzacj.zzb(zzabxVar, 234);
                    zzko[] zzkoVarArr = this.zzaum;
                    int length3 = zzkoVarArr == null ? 0 : zzkoVarArr.length;
                    int i3 = zzb3 + length3;
                    zzko[] zzkoVarArr2 = new zzko[i3];
                    if (length3 != 0) {
                        System.arraycopy(zzkoVarArr, 0, zzkoVarArr2, 0, length3);
                    }
                    while (length3 < i3 - 1) {
                        zzkoVarArr2[length3] = new zzko();
                        zzabxVar.zza(zzkoVarArr2[length3]);
                        zzabxVar.zzvf();
                        length3++;
                    }
                    zzkoVarArr2[length3] = new zzko();
                    zzabxVar.zza(zzkoVarArr2[length3]);
                    this.zzaum = zzkoVarArr2;
                    break;
                case 242:
                    this.zzado = zzabxVar.readString();
                    break;
                case 248:
                    this.zzaun = Integer.valueOf(zzabxVar.zzvh());
                    break;
                case 256:
                    this.zzauo = Integer.valueOf(zzabxVar.zzvh());
                    break;
                case 264:
                    this.zzaup = Integer.valueOf(zzabxVar.zzvh());
                    break;
                case 274:
                    this.zzauq = zzabxVar.readString();
                    break;
                case 280:
                    this.zzaur = Long.valueOf(zzabxVar.zzvi());
                    break;
                case 288:
                    this.zzaus = Long.valueOf(zzabxVar.zzvi());
                    break;
                case 298:
                    this.zzaut = zzabxVar.readString();
                    break;
                case 306:
                    this.zzauu = zzabxVar.readString();
                    break;
                case 312:
                    this.zzauv = Integer.valueOf(zzabxVar.zzvh());
                    break;
                default:
                    if (!super.zza(zzabxVar, zzvf)) {
                        return this;
                    }
                    break;
            }
        }
    }
}
