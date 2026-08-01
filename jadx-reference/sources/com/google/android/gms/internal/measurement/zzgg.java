package com.google.android.gms.internal.measurement;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes.dex */
public final class zzgg extends zzjr implements zzei {
    private static int zzalo = 65535;
    private static int zzalp = 2;
    private final Map<String, Map<String, String>> zzalq;
    private final Map<String, Map<String, Boolean>> zzalr;
    private final Map<String, Map<String, Boolean>> zzals;
    private final Map<String, zzkm> zzalt;
    private final Map<String, Map<String, Integer>> zzalu;
    private final Map<String, String> zzalv;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzgg(zzjs zzjsVar) {
        super(zzjsVar);
        this.zzalq = new ArrayMap();
        this.zzalr = new ArrayMap();
        this.zzals = new ArrayMap();
        this.zzalt = new ArrayMap();
        this.zzalv = new ArrayMap();
        this.zzalu = new ArrayMap();
    }

    private final zzkm zza(String str, byte[] bArr) {
        if (bArr == null) {
            return new zzkm();
        }
        zzabx zza = zzabx.zza(bArr, 0, bArr.length);
        zzkm zzkmVar = new zzkm();
        try {
            zzkmVar.zzb(zza);
            zzgf().zziz().zze("Parsed config. version, gmp_app_id", zzkmVar.zzatb, zzkmVar.zzadm);
            return zzkmVar;
        } catch (IOException e) {
            zzgf().zziv().zze("Unable to merge remote config. appId", zzfh.zzbl(str), e);
            return new zzkm();
        }
    }

    private static Map<String, String> zza(zzkm zzkmVar) {
        ArrayMap arrayMap = new ArrayMap();
        if (zzkmVar != null && zzkmVar.zzatd != null) {
            for (zzkn zzknVar : zzkmVar.zzatd) {
                if (zzknVar != null) {
                    arrayMap.put(zzknVar.zzny, zzknVar.value);
                }
            }
        }
        return arrayMap;
    }

    private final void zza(String str, zzkm zzkmVar) {
        ArrayMap arrayMap = new ArrayMap();
        ArrayMap arrayMap2 = new ArrayMap();
        ArrayMap arrayMap3 = new ArrayMap();
        if (zzkmVar != null && zzkmVar.zzate != null) {
            for (zzkl zzklVar : zzkmVar.zzate) {
                if (TextUtils.isEmpty(zzklVar.name)) {
                    zzgf().zziv().log("EventConfig contained null event name");
                } else {
                    String zzaj = AppMeasurement.Event.zzaj(zzklVar.name);
                    if (!TextUtils.isEmpty(zzaj)) {
                        zzklVar.name = zzaj;
                    }
                    arrayMap.put(zzklVar.name, zzklVar.zzasy);
                    arrayMap2.put(zzklVar.name, zzklVar.zzasz);
                    if (zzklVar.zzata != null) {
                        if (zzklVar.zzata.intValue() < zzalp || zzklVar.zzata.intValue() > zzalo) {
                            zzgf().zziv().zze("Invalid sampling rate. Event name, sample rate", zzklVar.name, zzklVar.zzata);
                        } else {
                            arrayMap3.put(zzklVar.name, zzklVar.zzata);
                        }
                    }
                }
            }
        }
        this.zzalr.put(str, arrayMap);
        this.zzals.put(str, arrayMap2);
        this.zzalu.put(str, arrayMap3);
    }

    private final void zzbs(String str) {
        zzch();
        zzab();
        Preconditions.checkNotEmpty(str);
        if (this.zzalt.get(str) == null) {
            byte[] zzbd = zzje().zzbd(str);
            if (zzbd != null) {
                zzkm zza = zza(str, zzbd);
                this.zzalq.put(str, zza(zza));
                zza(str, zza);
                this.zzalt.put(str, zza);
                this.zzalv.put(str, null);
                return;
            }
            this.zzalq.put(str, null);
            this.zzalr.put(str, null);
            this.zzals.put(str, null);
            this.zzalt.put(str, null);
            this.zzalv.put(str, null);
            this.zzalu.put(str, null);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final boolean zza(String str, byte[] bArr, String str2) {
        byte[] bArr2;
        zzch();
        zzab();
        Preconditions.checkNotEmpty(str);
        zzkm zza = zza(str, bArr);
        if (zza == null) {
            return false;
        }
        zza(str, zza);
        this.zzalt.put(str, zza);
        this.zzalv.put(str, str2);
        this.zzalq.put(str, zza(zza));
        zzeb zzjd = zzjd();
        zzkf[] zzkfVarArr = zza.zzatf;
        Preconditions.checkNotNull(zzkfVarArr);
        for (zzkf zzkfVar : zzkfVarArr) {
            for (zzkg zzkgVar : zzkfVar.zzarz) {
                String zzaj = AppMeasurement.Event.zzaj(zzkgVar.zzasc);
                if (zzaj != null) {
                    zzkgVar.zzasc = zzaj;
                }
                for (zzkh zzkhVar : zzkgVar.zzasd) {
                    String zzaj2 = AppMeasurement.Param.zzaj(zzkhVar.zzask);
                    if (zzaj2 != null) {
                        zzkhVar.zzask = zzaj2;
                    }
                }
            }
            for (zzkj zzkjVar : zzkfVar.zzary) {
                String zzaj3 = AppMeasurement.UserProperty.zzaj(zzkjVar.zzasr);
                if (zzaj3 != null) {
                    zzkjVar.zzasr = zzaj3;
                }
            }
        }
        zzjd.zzje().zza(str, zzkfVarArr);
        try {
            zza.zzatf = null;
            int zzvv = zza.zzvv();
            bArr2 = new byte[zzvv];
            zza.zza(zzaby.zzb(bArr2, 0, zzvv));
        } catch (IOException e) {
            zzgf().zziv().zze("Unable to serialize reduced-size config. Storing full config instead. appId", zzfh.zzbl(str), e);
            bArr2 = bArr;
        }
        zzej zzje = zzje();
        Preconditions.checkNotEmpty(str);
        zzje.zzab();
        zzje.zzch();
        new ContentValues().put("remote_config", bArr2);
        try {
            if (zzje.getWritableDatabase().update("apps", r0, "app_id = ?", new String[]{str}) == 0) {
                zzje.zzgf().zzis().zzg("Failed to update remote config (got 0). appId", zzfh.zzbl(str));
            }
        } catch (SQLiteException e2) {
            zzje.zzgf().zzis().zze("Error storing remote config. appId", zzfh.zzbl(str), e2);
        }
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ Clock zzbt() {
        return super.zzbt();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final zzkm zzbt(String str) {
        zzch();
        zzab();
        Preconditions.checkNotEmpty(str);
        zzbs(str);
        return this.zzalt.get(str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final String zzbu(String str) {
        zzab();
        return this.zzalv.get(str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void zzbv(String str) {
        zzab();
        this.zzalv.put(str, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzbw(String str) {
        zzab();
        this.zzalt.remove(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzbx(String str) {
        return "1".equals(zze(str, "measurement.upload.blacklist_internal"));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzby(String str) {
        return "1".equals(zze(str, "measurement.upload.blacklist_public"));
    }

    @Override // com.google.android.gms.internal.measurement.zzei
    public final String zze(String str, String str2) {
        zzab();
        zzbs(str);
        Map<String, String> map = this.zzalq.get(str);
        if (map != null) {
            return map.get(str2);
        }
        return null;
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ void zzfr() {
        super.zzfr();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ void zzfs() {
        super.zzfs();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ void zzft() {
        super.zzft();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzdu zzfu() {
        return super.zzfu();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzhl zzfv() {
        return super.zzfv();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzfc zzfw() {
        return super.zzfw();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzeq zzfx() {
        return super.zzfx();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzij zzfy() {
        return super.zzfy();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzig zzfz() {
        return super.zzfz();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzfd zzga() {
        return super.zzga();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzff zzgb() {
        return super.zzgb();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzkc zzgc() {
        return super.zzgc();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzji zzgd() {
        return super.zzgd();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ zzgh zzge() {
        return super.zzge();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ zzfh zzgf() {
        return super.zzgf();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzfs zzgg() {
        return super.zzgg();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzeg zzgh() {
        return super.zzgh();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ zzec zzgi() {
        return super.zzgi();
    }

    @Override // com.google.android.gms.internal.measurement.zzjr
    protected final boolean zzhh() {
        return false;
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final /* bridge */ /* synthetic */ zzjy zzjc() {
        return super.zzjc();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final /* bridge */ /* synthetic */ zzeb zzjd() {
        return super.zzjd();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final /* bridge */ /* synthetic */ zzej zzje() {
        return super.zzje();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzn(String str, String str2) {
        Boolean bool;
        zzab();
        zzbs(str);
        if (zzbx(str) && zzkc.zzch(str2)) {
            return true;
        }
        if (zzby(str) && zzkc.zzcb(str2)) {
            return true;
        }
        Map<String, Boolean> map = this.zzalr.get(str);
        if (map == null || (bool = map.get(str2)) == null) {
            return false;
        }
        return bool.booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzo(String str, String str2) {
        Boolean bool;
        zzab();
        zzbs(str);
        if (FirebaseAnalytics.Event.ECOMMERCE_PURCHASE.equals(str2)) {
            return true;
        }
        Map<String, Boolean> map = this.zzals.get(str);
        if (map == null || (bool = map.get(str2)) == null) {
            return false;
        }
        return bool.booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final int zzp(String str, String str2) {
        Integer num;
        zzab();
        zzbs(str);
        Map<String, Integer> map = this.zzalu.get(str);
        if (map == null || (num = map.get(str2)) == null) {
            return 1;
        }
        return num.intValue();
    }
}
