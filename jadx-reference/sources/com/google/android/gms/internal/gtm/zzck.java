package com.google.android.gms.internal.gtm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.analytics.CampaignTrackingReceiver;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.wrappers.Wrappers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzck extends zzbs {
    private boolean zza;
    private final zzce zzb;
    private final zzfe zzc;
    private final zzfc zzd;
    private final zzcc zze;
    private long zzf;
    private final zzcw zzg;
    private final zzcw zzh;
    private final zzfo zzi;
    private long zzj;
    private boolean zzk;

    /* JADX INFO: Access modifiers changed from: protected */
    public zzck(zzbv zzbvVar, zzbw zzbwVar) {
        super(zzbvVar);
        Preconditions.checkNotNull(zzbwVar);
        this.zzf = Long.MIN_VALUE;
        this.zzd = new zzfc(zzbvVar);
        this.zzb = new zzce(zzbvVar);
        this.zzc = new zzfe(zzbvVar);
        this.zze = new zzcc(zzbvVar);
        this.zzi = new zzfo(zzC());
        this.zzg = new zzcg(this, zzbvVar);
        this.zzh = new zzch(this, zzbvVar);
    }

    private final void zzag() {
        zzcy zzy = zzy();
        if (zzy.zze()) {
            zzy.zza();
        }
    }

    private final void zzah() {
        if (this.zzg.zzh()) {
            zzO("All hits dispatched or no network/service. Going to power save mode");
        }
        this.zzg.zzf();
    }

    private final void zzai() {
        long j;
        zzcy zzy = zzy();
        if (zzy.zzc() && !zzy.zze()) {
            com.google.android.gms.analytics.zzr.zzh();
            zzW();
            try {
                j = this.zzb.zzc();
            } catch (SQLiteException e) {
                zzK("Failed to get min/max hit times from local store", e);
                j = 0;
            }
            if (j != 0) {
                long abs = Math.abs(zzC().currentTimeMillis() - j);
                zzw();
                if (abs <= zzeu.zzn.zzb().longValue()) {
                    zzw();
                    zzP("Dispatch alarm scheduled (ms)", Long.valueOf(zzct.zzd()));
                    zzy.zzb();
                }
            }
        }
    }

    private final void zzaj(zzbx zzbxVar, zzaw zzawVar) {
        Preconditions.checkNotNull(zzbxVar);
        Preconditions.checkNotNull(zzawVar);
        com.google.android.gms.analytics.zza zzaVar = new com.google.android.gms.analytics.zza(zzt());
        zzaVar.zzc(zzbxVar.zzc());
        zzaVar.zzd(zzbxVar.zzf());
        com.google.android.gms.analytics.zzh zza = zzaVar.zza();
        zzbe zzbeVar = (zzbe) zza.zzb(zzbe.class);
        zzbeVar.zzk("data");
        zzbeVar.zzl(true);
        zza.zzg(zzawVar);
        zzaz zzazVar = (zzaz) zza.zzb(zzaz.class);
        zzav zzavVar = (zzav) zza.zzb(zzav.class);
        for (Map.Entry<String, String> entry : zzbxVar.zzd().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if ("an".equals(key)) {
                zzavVar.zzk(value);
            } else if ("av".equals(key)) {
                zzavVar.zzl(value);
            } else if ("aid".equals(key)) {
                zzavVar.zzi(value);
            } else if ("aiid".equals(key)) {
                zzavVar.zzj(value);
            } else if ("uid".equals(key)) {
                zzbeVar.zzm(value);
            } else {
                zzazVar.zze(key, value);
            }
        }
        zzH("Sending installation campaign to", zzbxVar.zzc(), zzawVar);
        zza.zzj(zzA().zza());
        zza.zzk();
    }

    private final boolean zzak(String str) {
        return Wrappers.packageManager(zzo()).checkCallingOrSelfPermission(str) == 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* bridge */ /* synthetic */ void zzc(zzck zzckVar) {
        try {
            zzckVar.zzb.zza();
            zzckVar.zzae();
        } catch (SQLiteException e) {
            zzckVar.zzS("Failed to delete stale hits", e);
        }
        zzcw zzcwVar = zzckVar.zzh;
        zzckVar.zzw();
        zzcwVar.zzg(86400000L);
    }

    public final void zzZ(long j) {
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        if (j < 0) {
            j = 0;
        }
        this.zzf = j;
        zzae();
    }

    public final long zza() {
        long j = this.zzf;
        if (j != Long.MIN_VALUE) {
            return j;
        }
        zzw();
        long longValue = zzeu.zzi.zzb().longValue();
        zzft zzB = zzB();
        zzB.zzW();
        if (!zzB.zzc) {
            return longValue;
        }
        zzB().zzW();
        return r0.zzd * 1000;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzaa() {
        zzW();
        Preconditions.checkState(!this.zza, "Analytics backend already started");
        this.zza = true;
        zzq().zzi(new zzci(this));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void zzab() {
        zzW();
        zzw();
        com.google.android.gms.analytics.zzr.zzh();
        Context zza = zzt().zza();
        if (!zzfi.zza(zza)) {
            zzR("AnalyticsReceiver is not registered or is disabled. Register the receiver for reliable dispatching on non-Google Play devices. See http://goo.gl/8Rd3yj for instructions.");
        } else if (!zzfn.zzh(zza)) {
            zzJ("AnalyticsService is not registered or is disabled. Analytics service at risk of not starting. See http://goo.gl/8Rd3yj for instructions.");
        }
        if (!CampaignTrackingReceiver.zzb(zza)) {
            zzR("CampaignTrackingReceiver is not registered, not exported or is disabled. Installation campaign tracking is not possible. See http://goo.gl/8Rd3yj for instructions.");
        }
        zzA().zza();
        if (!zzak("android.permission.ACCESS_NETWORK_STATE")) {
            zzJ("Missing required android.permission.ACCESS_NETWORK_STATE. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
            zzad();
        }
        if (!zzak("android.permission.INTERNET")) {
            zzJ("Missing required android.permission.INTERNET. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
            zzad();
        }
        if (zzfn.zzh(zzo())) {
            zzO("AnalyticsService registered in the app manifest and enabled");
        } else {
            zzw();
            zzR("AnalyticsService not registered in the app manifest. Hits might not be delivered reliably. See http://goo.gl/8Rd3yj for instructions.");
        }
        if (!this.zzk) {
            zzw();
            if (!this.zzb.zzac()) {
                zzi();
            }
        }
        zzae();
    }

    public final void zzac() {
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        zzF("Sync dispatching local hits");
        long j = this.zzj;
        zzw();
        zzi();
        try {
            zzaf();
            zzA().zzi();
            zzae();
            if (this.zzj != j) {
                this.zzd.zzb();
            }
        } catch (Exception e) {
            zzK("Sync local dispatch failed", e);
            zzae();
        }
    }

    public final void zzad() {
        zzW();
        com.google.android.gms.analytics.zzr.zzh();
        this.zzk = true;
        this.zze.zzc();
        zzae();
    }

    public final void zzae() {
        long min;
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        if (!this.zzk) {
            zzw();
            if (zza() > 0) {
                if (this.zzb.zzac()) {
                    this.zzd.zzc();
                    zzah();
                    zzag();
                    return;
                }
                if (!zzeu.zzJ.zzb().booleanValue()) {
                    this.zzd.zza();
                    if (!this.zzd.zzd()) {
                        zzah();
                        zzag();
                        zzai();
                        return;
                    }
                }
                zzai();
                long zza = zza();
                long zzb = zzA().zzb();
                if (zzb != 0) {
                    min = zza - Math.abs(zzC().currentTimeMillis() - zzb);
                    if (min <= 0) {
                        zzw();
                        min = Math.min(zzct.zze(), zza);
                    }
                } else {
                    zzw();
                    min = Math.min(zzct.zze(), zza);
                }
                zzP("Dispatch scheduled (ms)", Long.valueOf(min));
                if (this.zzg.zzh()) {
                    this.zzg.zze(Math.max(1L, min + this.zzg.zzb()));
                    return;
                } else {
                    this.zzg.zzg(min);
                    return;
                }
            }
        }
        this.zzd.zzc();
        zzah();
        zzag();
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [32=8, 33=10, 37=8, 38=8, 39=8] */
    protected final boolean zzaf() {
        boolean z;
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        zzO("Dispatching a batch of local hits");
        if (this.zze.zzg()) {
            z = false;
        } else {
            zzw();
            z = true;
        }
        boolean zze = true ^ this.zzc.zze();
        if (z && zze) {
            zzO("No network or service available. Will retry later");
            return false;
        }
        zzw();
        int zzh = zzct.zzh();
        zzw();
        long max = Math.max(zzh, zzct.zzg());
        ArrayList arrayList = new ArrayList();
        long j = 0;
        while (true) {
            try {
                this.zzb.zzm();
                arrayList.clear();
                try {
                    List<zzex> zzj = this.zzb.zzj(max);
                    if (zzj.isEmpty()) {
                        zzO("Store is empty, nothing to dispatch");
                        zzah();
                        zzag();
                        try {
                            this.zzb.zzab();
                            this.zzb.zzaa();
                            return false;
                        } catch (SQLiteException e) {
                            zzK("Failed to commit local dispatch transaction", e);
                            zzah();
                            zzag();
                            return false;
                        }
                    }
                    zzP("Hits loaded from store. count", Integer.valueOf(zzj.size()));
                    Iterator<zzex> it = zzj.iterator();
                    while (it.hasNext()) {
                        if (it.next().zzb() == j) {
                            zzL("Database contains successfully uploaded hit", Long.valueOf(j), Integer.valueOf(zzj.size()));
                            zzah();
                            zzag();
                            try {
                                this.zzb.zzab();
                                this.zzb.zzaa();
                                return false;
                            } catch (SQLiteException e2) {
                                zzK("Failed to commit local dispatch transaction", e2);
                                zzah();
                                zzag();
                                return false;
                            }
                        }
                    }
                    if (this.zze.zzg()) {
                        zzw();
                        zzO("Service connected, sending hits to the service");
                        while (!zzj.isEmpty()) {
                            zzex zzexVar = zzj.get(0);
                            if (!this.zze.zzh(zzexVar)) {
                                break;
                            }
                            j = Math.max(j, zzexVar.zzb());
                            zzj.remove(zzexVar);
                            zzG("Hit sent do device AnalyticsService for delivery", zzexVar);
                            try {
                                this.zzb.zzn(zzexVar.zzb());
                                arrayList.add(Long.valueOf(zzexVar.zzb()));
                            } catch (SQLiteException e3) {
                                zzK("Failed to remove hit that was send for delivery", e3);
                                zzah();
                                zzag();
                                try {
                                    this.zzb.zzab();
                                    this.zzb.zzaa();
                                    return false;
                                } catch (SQLiteException e4) {
                                    zzK("Failed to commit local dispatch transaction", e4);
                                    zzah();
                                    zzag();
                                    return false;
                                }
                            }
                        }
                    }
                    if (this.zzc.zze()) {
                        List<Long> zzc = this.zzc.zzc(zzj);
                        Iterator<Long> it2 = zzc.iterator();
                        while (it2.hasNext()) {
                            j = Math.max(j, it2.next().longValue());
                        }
                        try {
                            this.zzb.zzZ(zzc);
                            arrayList.addAll(zzc);
                        } catch (SQLiteException e5) {
                            zzK("Failed to remove successfully uploaded hits", e5);
                            zzah();
                            zzag();
                            try {
                                this.zzb.zzab();
                                this.zzb.zzaa();
                                return false;
                            } catch (SQLiteException e6) {
                                zzK("Failed to commit local dispatch transaction", e6);
                                zzah();
                                zzag();
                                return false;
                            }
                        }
                    }
                    if (arrayList.isEmpty()) {
                        try {
                            this.zzb.zzab();
                            this.zzb.zzaa();
                            return false;
                        } catch (SQLiteException e7) {
                            zzK("Failed to commit local dispatch transaction", e7);
                            zzah();
                            zzag();
                            return false;
                        }
                    }
                    try {
                        this.zzb.zzab();
                        this.zzb.zzaa();
                    } catch (SQLiteException e8) {
                        zzK("Failed to commit local dispatch transaction", e8);
                        zzah();
                        zzag();
                        return false;
                    }
                } catch (SQLiteException e9) {
                    zzS("Failed to read hits from persisted store", e9);
                    zzah();
                    zzag();
                    try {
                        this.zzb.zzab();
                        this.zzb.zzaa();
                        return false;
                    } catch (SQLiteException e10) {
                        zzK("Failed to commit local dispatch transaction", e10);
                        zzah();
                        zzag();
                        return false;
                    }
                }
            } catch (Throwable th) {
                this.zzb.zzab();
                this.zzb.zzaa();
                throw th;
            }
            try {
                this.zzb.zzab();
                this.zzb.zzaa();
                throw th;
            } catch (SQLiteException e11) {
                zzK("Failed to commit local dispatch transaction", e11);
                zzah();
                zzag();
                return false;
            }
        }
    }

    public final long zzb(zzbx zzbxVar, boolean z) {
        Preconditions.checkNotNull(zzbxVar);
        zzW();
        com.google.android.gms.analytics.zzr.zzh();
        try {
            try {
                this.zzb.zzm();
                zzce zzceVar = this.zzb;
                String zzb = zzbxVar.zzb();
                Preconditions.checkNotEmpty(zzb);
                zzceVar.zzW();
                com.google.android.gms.analytics.zzr.zzh();
                int delete = zzceVar.zzf().delete("properties", "app_uid=? AND cid<>?", new String[]{"0", zzb});
                if (delete > 0) {
                    zzceVar.zzP("Deleted property records", Integer.valueOf(delete));
                }
                long zze = this.zzb.zze(0L, zzbxVar.zzb(), zzbxVar.zzc());
                zzbxVar.zze(1 + zze);
                zzce zzceVar2 = this.zzb;
                Preconditions.checkNotNull(zzbxVar);
                zzceVar2.zzW();
                com.google.android.gms.analytics.zzr.zzh();
                SQLiteDatabase zzf = zzceVar2.zzf();
                Map<String, String> zzd = zzbxVar.zzd();
                Preconditions.checkNotNull(zzd);
                Uri.Builder builder = new Uri.Builder();
                for (Map.Entry<String, String> entry : zzd.entrySet()) {
                    builder.appendQueryParameter(entry.getKey(), entry.getValue());
                }
                String encodedQuery = builder.build().getEncodedQuery();
                if (encodedQuery == null) {
                    encodedQuery = "";
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put("app_uid", (Long) 0L);
                contentValues.put("cid", zzbxVar.zzb());
                contentValues.put("tid", zzbxVar.zzc());
                contentValues.put("adid", Integer.valueOf(zzbxVar.zzf() ? 1 : 0));
                contentValues.put("hits_count", Long.valueOf(zzbxVar.zza()));
                contentValues.put("params", encodedQuery);
                try {
                    if (zzf.insertWithOnConflict("properties", null, contentValues, 5) == -1) {
                        zzceVar2.zzJ("Failed to insert/update a property (got -1)");
                    }
                } catch (SQLiteException e) {
                    zzceVar2.zzK("Error storing a property", e);
                }
                this.zzb.zzab();
                try {
                    this.zzb.zzaa();
                } catch (SQLiteException e2) {
                    zzK("Failed to end transaction", e2);
                }
                return zze;
            } catch (Throwable th) {
                try {
                    this.zzb.zzaa();
                } catch (SQLiteException e3) {
                    zzK("Failed to end transaction", e3);
                }
                throw th;
            }
        } catch (SQLiteException e4) {
            zzK("Failed to update Analytics property", e4);
            try {
                this.zzb.zzaa();
            } catch (SQLiteException e5) {
                zzK("Failed to end transaction", e5);
            }
            return -1L;
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzbs
    protected final void zzd() {
        this.zzb.zzX();
        this.zzc.zzX();
        this.zze.zzX();
    }

    public final void zzf(zzcz zzczVar) {
        zzg(zzczVar, this.zzj);
    }

    public final void zzg(zzcz zzczVar, long j) {
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        long zzb = zzA().zzb();
        zzG("Dispatching local hits. Elapsed time since last dispatch (ms)", Long.valueOf(zzb != 0 ? Math.abs(zzC().currentTimeMillis() - zzb) : -1L));
        zzw();
        zzi();
        try {
            zzaf();
            zzA().zzi();
            zzae();
            if (zzczVar != null) {
                zzczVar.zza(null);
            }
            if (this.zzj != j) {
                this.zzd.zzb();
            }
        } catch (Exception e) {
            zzK("Local dispatch failed", e);
            zzA().zzi();
            zzae();
            if (zzczVar != null) {
                zzczVar.zza(e);
            }
        }
    }

    public final void zzh() {
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        zzw();
        zzO("Delete all hits from local store");
        try {
            zzce zzceVar = this.zzb;
            com.google.android.gms.analytics.zzr.zzh();
            zzceVar.zzW();
            zzceVar.zzf().delete("hits2", null, null);
            zzce zzceVar2 = this.zzb;
            com.google.android.gms.analytics.zzr.zzh();
            zzceVar2.zzW();
            zzceVar2.zzf().delete("properties", null, null);
            zzae();
        } catch (SQLiteException e) {
            zzS("Failed to delete hits from store", e);
        }
        zzi();
        if (this.zze.zze()) {
            zzO("Device service unavailable. Can't clear hits stored on the device service.");
        }
    }

    protected final void zzi() {
        if (this.zzk) {
            return;
        }
        zzw();
        if (zzct.zzl() && !this.zze.zzg()) {
            zzw();
            if (this.zzi.zzc(zzeu.zzO.zzb().longValue())) {
                this.zzi.zzb();
                zzO("Connecting to service");
                if (this.zze.zzf()) {
                    zzO("Connected to service");
                    this.zzi.zza();
                    zzm();
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x0161, code lost:
    
        if (r2.moveToFirst() != false) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0163, code lost:
    
        r9.add(java.lang.Long.valueOf(r2.getLong(0)));
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0172, code lost:
    
        if (r2.moveToNext() != false) goto L98;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0174, code lost:
    
        if (r2 == null) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0176, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0188, code lost:
    
        r0 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0185, code lost:
    
        if (r2 == null) goto L55;
     */
    /* JADX WARN: Removed duplicated region for block: B:70:0x019e A[Catch: SQLiteException -> 0x020a, TryCatch #1 {SQLiteException -> 0x020a, blocks: (B:16:0x0090, B:17:0x00af, B:19:0x00b5, B:22:0x00c9, B:25:0x00d1, B:28:0x00d9, B:35:0x00e3, B:38:0x00ef, B:40:0x00f8, B:41:0x0206, B:43:0x0103, B:45:0x011e, B:47:0x012f, B:48:0x0189, B:49:0x0134, B:61:0x0176, B:70:0x019e, B:71:0x01a1, B:76:0x01a2, B:78:0x01d0, B:79:0x01df, B:88:0x0201, B:89:0x01d8, B:81:0x01e4, B:83:0x01f0, B:86:0x01f6), top: B:15:0x0090, inners: #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void zzj(zzex zzexVar) {
        String zzk;
        Cursor cursor;
        List<Long> list;
        Pair<String, Long> zza;
        zzex zzexVar2 = zzexVar;
        Preconditions.checkNotNull(zzexVar);
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        if (this.zzk) {
            zzF("Hit delivery not possible. Missing network permissions. See http://goo.gl/8Rd3yj for instructions");
        } else {
            zzP("Delivering hit", zzexVar2);
        }
        if (TextUtils.isEmpty(zzexVar.zzf()) && (zza = zzA().zze().zza()) != null) {
            Long l = (Long) zza.second;
            String str = (String) zza.first;
            String valueOf = String.valueOf(l);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 1 + String.valueOf(str).length());
            sb.append(valueOf);
            sb.append(":");
            sb.append(str);
            String sb2 = sb.toString();
            HashMap hashMap = new HashMap(zzexVar.zzg());
            hashMap.put("_m", sb2);
            zzexVar2 = zzex.zze(this, zzexVar2, hashMap);
        }
        zzex zzexVar3 = zzexVar2;
        zzi();
        if (this.zze.zzh(zzexVar3)) {
            zzF("Hit sent to the device AnalyticsService for delivery");
            return;
        }
        zzw();
        try {
            zzce zzceVar = this.zzb;
            Preconditions.checkNotNull(zzexVar3);
            com.google.android.gms.analytics.zzr.zzh();
            zzceVar.zzW();
            Preconditions.checkNotNull(zzexVar3);
            Uri.Builder builder = new Uri.Builder();
            for (Map.Entry<String, String> entry : zzexVar3.zzg().entrySet()) {
                String key = entry.getKey();
                if (!"ht".equals(key) && !"qt".equals(key) && !"AppUID".equals(key)) {
                    builder.appendQueryParameter(key, entry.getValue());
                }
            }
            String encodedQuery = builder.build().getEncodedQuery();
            if (encodedQuery == null) {
                encodedQuery = "";
            }
            String str2 = encodedQuery;
            if (str2.length() <= 8192) {
                zzceVar.zzw();
                int intValue = zzeu.zzf.zzb().intValue();
                long zzb = zzceVar.zzb();
                Cursor cursor2 = null;
                if (zzb > intValue - 1) {
                    long j = (zzb - intValue) + 1;
                    com.google.android.gms.analytics.zzr.zzh();
                    zzceVar.zzW();
                    if (j <= 0) {
                        list = Collections.emptyList();
                    } else {
                        SQLiteDatabase zzf = zzceVar.zzf();
                        ArrayList arrayList = new ArrayList();
                        try {
                            cursor = zzf.query("hits2", new String[]{"hit_id"}, null, null, null, null, String.format("%s ASC", "hit_id"), Long.toString(j));
                            try {
                                try {
                                } catch (SQLiteException e) {
                                    e = e;
                                    zzceVar.zzS("Error selecting hit ids", e);
                                }
                            } catch (Throwable th) {
                                th = th;
                                cursor2 = cursor;
                                if (cursor2 != null) {
                                    cursor2.close();
                                }
                                throw th;
                            }
                        } catch (SQLiteException e2) {
                            e = e2;
                            cursor = null;
                        } catch (Throwable th2) {
                            th = th2;
                            if (cursor2 != null) {
                            }
                            throw th;
                        }
                    }
                    zzceVar.zzS("Store full, deleting hits to make room, count", Integer.valueOf(list.size()));
                    zzceVar.zzZ(list);
                }
                SQLiteDatabase zzf2 = zzceVar.zzf();
                ContentValues contentValues = new ContentValues();
                contentValues.put("hit_string", str2);
                contentValues.put("hit_time", Long.valueOf(zzexVar3.zzd()));
                contentValues.put("hit_app_id", Integer.valueOf(zzexVar3.zza()));
                if (zzexVar3.zzh()) {
                    zzceVar.zzw();
                    zzk = zzct.zzi();
                } else {
                    zzceVar.zzw();
                    zzk = zzct.zzk();
                }
                contentValues.put("hit_url", zzk);
                try {
                    long insert = zzf2.insert("hits2", null, contentValues);
                    if (insert == -1) {
                        zzceVar.zzJ("Failed to insert a hit (got -1)");
                    } else {
                        zzceVar.zzH("Hit saved to database. db-id, hit", Long.valueOf(insert), zzexVar3);
                    }
                } catch (SQLiteException e3) {
                    zzceVar.zzK("Error storing a hit", e3);
                }
            } else {
                zzceVar.zzz().zzb(zzexVar3, "Hit length exceeds the maximum allowed size");
            }
            zzae();
        } catch (SQLiteException e4) {
            zzK("Delivery failed to save hit to a database", e4);
            zzz().zzb(zzexVar3, "deliver: failed to insert hit to database");
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void zzk(zzbx zzbxVar) {
        com.google.android.gms.analytics.zzr.zzh();
        zzG("Sending first hit to property", zzbxVar.zzc());
        zzfo zzf = zzA().zzf();
        zzw();
        if (zzf.zzc(zzct.zzc())) {
            return;
        }
        String zzg = zzA().zzg();
        if (TextUtils.isEmpty(zzg)) {
            return;
        }
        zzaw zzb = zzfs.zzb(zzz(), zzg);
        zzG("Found relevant installation campaign", zzb);
        zzaj(zzbxVar, zzb);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzl() {
        com.google.android.gms.analytics.zzr.zzh();
        this.zzj = zzC().currentTimeMillis();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void zzm() {
        com.google.android.gms.analytics.zzr.zzh();
        zzw();
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        zzE();
        zzw();
        if (!zzct.zzl()) {
            zzR("Service client disabled. Can't dispatch local hits to device AnalyticsService");
        }
        if (!this.zze.zzg()) {
            zzO("Service not connected");
            return;
        }
        if (this.zzb.zzac()) {
            return;
        }
        zzO("Dispatching local hits to device AnalyticsService");
        while (true) {
            try {
                zzce zzceVar = this.zzb;
                zzw();
                List<zzex> zzj = zzceVar.zzj(zzct.zzh());
                if (zzj.isEmpty()) {
                    zzae();
                    return;
                }
                while (!zzj.isEmpty()) {
                    zzex zzexVar = zzj.get(0);
                    if (this.zze.zzh(zzexVar)) {
                        zzj.remove(zzexVar);
                        try {
                            this.zzb.zzn(zzexVar.zzb());
                        } catch (SQLiteException e) {
                            zzK("Failed to remove hit that was send for delivery", e);
                            zzah();
                            zzag();
                            return;
                        }
                    } else {
                        zzae();
                        return;
                    }
                }
            } catch (SQLiteException e2) {
                zzK("Failed to read hits from store", e2);
                zzah();
                zzag();
                return;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x00ac, code lost:
    
        if (r14.moveToFirst() != false) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00ae, code lost:
    
        r6 = r14.getString(0);
        r8 = r14.getString(1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00bd, code lost:
    
        if (r14.getInt(2) == 0) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00bf, code lost:
    
        r20 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00c4, code lost:
    
        r9 = r14.getInt(3);
        r23 = r4.zzl(r14.getString(4));
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00d7, code lost:
    
        if (android.text.TextUtils.isEmpty(r6) != false) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00dd, code lost:
    
        if (android.text.TextUtils.isEmpty(r8) == false) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00e0, code lost:
    
        r3.add(new com.google.android.gms.internal.gtm.zzbx(0, r6, r8, r20, r9, r23));
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00fb, code lost:
    
        if (r14.moveToNext() != false) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00f2, code lost:
    
        r4.zzT("Read property with empty client id or tracker id", r6, r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00c2, code lost:
    
        r20 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0101, code lost:
    
        if (r3.size() < r0) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0103, code lost:
    
        r4.zzR("Sending hits to too many properties. Campaign report might be incorrect");
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x010d, code lost:
    
        r0 = r3.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0115, code lost:
    
        if (r0.hasNext() == false) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0117, code lost:
    
        zzaj((com.google.android.gms.internal.gtm.zzbx) r0.next(), r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0121, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void zzn(String str) {
        Preconditions.checkNotEmpty(str);
        com.google.android.gms.analytics.zzr.zzh();
        zzE();
        zzaw zzb = zzfs.zzb(zzz(), str);
        if (zzb == null) {
            zzS("Parsing failed. Ignoring invalid campaign data", str);
            return;
        }
        String zzg = zzA().zzg();
        if (str.equals(zzg)) {
            zzR("Ignoring duplicate install campaign");
            return;
        }
        if (!TextUtils.isEmpty(zzg)) {
            zzL("Ignoring multiple install campaigns. original, new", zzg, str);
            return;
        }
        zzA().zzh(str);
        zzfo zzf = zzA().zzf();
        zzw();
        if (zzf.zzc(zzct.zzc())) {
            zzS("Campaign received too late, ignoring", zzb);
            return;
        }
        zzG("Received installation campaign", zzb);
        zzce zzceVar = this.zzb;
        zzceVar.zzW();
        com.google.android.gms.analytics.zzr.zzh();
        SQLiteDatabase zzf2 = zzceVar.zzf();
        Cursor cursor = null;
        try {
            try {
                zzceVar.zzw();
                int intValue = zzeu.zzh.zzb().intValue();
                cursor = zzf2.query("properties", new String[]{"cid", "tid", "adid", "hits_count", "params"}, "app_uid=?", new String[]{"0"}, null, null, null, String.valueOf(intValue));
                ArrayList arrayList = new ArrayList();
            } catch (SQLiteException e) {
                zzceVar.zzK("Error loading hits from the database", e);
                throw e;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
