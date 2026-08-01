package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Pair;
import androidx.collection.ArrayMap;
import androidx.work.WorkRequest;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.measurement.zzey;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.analytics.FirebaseAnalytics;
import cz.msebera.android.httpclient.HttpStatus;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import kotlin.time.DurationKt;
import kotlinx.coroutines.DebugKt;

/* loaded from: classes.dex */
public class zzjs implements zzed {
    private static volatile zzjs zzaqj;
    private final zzgm zzacw;
    private zzgg zzaqk;
    private zzfl zzaql;
    private zzej zzaqm;
    private zzfq zzaqn;
    private zzjo zzaqo;
    private zzeb zzaqp;
    private final zzjy zzaqq;
    private boolean zzaqr;
    private long zzaqs;
    private List<Runnable> zzaqt;
    private int zzaqu;
    private int zzaqv;
    private boolean zzaqw;
    private boolean zzaqx;
    private boolean zzaqy;
    private FileLock zzaqz;
    private FileChannel zzara;
    private List<Long> zzarb;
    private List<Long> zzarc;
    private long zzard;
    private boolean zzvo;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class zza implements zzel {
        zzks zzarh;
        List<Long> zzari;
        List<zzkp> zzarj;
        private long zzark;

        private zza() {
        }

        /* synthetic */ zza(zzjs zzjsVar, zzjt zzjtVar) {
            this();
        }

        private static long zza(zzkp zzkpVar) {
            return ((zzkpVar.zzatn.longValue() / 1000) / 60) / 60;
        }

        @Override // com.google.android.gms.internal.measurement.zzel
        public final boolean zza(long j, zzkp zzkpVar) {
            Preconditions.checkNotNull(zzkpVar);
            if (this.zzarj == null) {
                this.zzarj = new ArrayList();
            }
            if (this.zzari == null) {
                this.zzari = new ArrayList();
            }
            if (this.zzarj.size() > 0 && zza(this.zzarj.get(0)) != zza(zzkpVar)) {
                return false;
            }
            long zzvv = this.zzark + zzkpVar.zzvv();
            if (zzvv >= Math.max(0, zzey.zzagx.get().intValue())) {
                return false;
            }
            this.zzark = zzvv;
            this.zzarj.add(zzkpVar);
            this.zzari.add(Long.valueOf(j));
            return this.zzarj.size() < Math.max(1, zzey.zzagy.get().intValue());
        }

        @Override // com.google.android.gms.internal.measurement.zzel
        public final void zzb(zzks zzksVar) {
            Preconditions.checkNotNull(zzksVar);
            this.zzarh = zzksVar;
        }
    }

    private zzjs(zzjx zzjxVar) {
        this(zzjxVar, null);
    }

    private zzjs(zzjx zzjxVar, zzgm zzgmVar) {
        this.zzvo = false;
        Preconditions.checkNotNull(zzjxVar);
        zzgm zza2 = zzgm.zza(zzjxVar.zzqx, null, null);
        this.zzacw = zza2;
        this.zzard = -1L;
        zzjy zzjyVar = new zzjy(this);
        zzjyVar.zzm();
        this.zzaqq = zzjyVar;
        zzfl zzflVar = new zzfl(this);
        zzflVar.zzm();
        this.zzaql = zzflVar;
        zzgg zzggVar = new zzgg(this);
        zzggVar.zzm();
        this.zzaqk = zzggVar;
        zza2.zzge().zzc(new zzjt(this, zzjxVar));
    }

    private final int zza(FileChannel fileChannel) {
        zzab();
        if (fileChannel == null || !fileChannel.isOpen()) {
            this.zzacw.zzgf().zzis().log("Bad channel to read from");
            return 0;
        }
        ByteBuffer allocate = ByteBuffer.allocate(4);
        try {
            fileChannel.position(0L);
            int read = fileChannel.read(allocate);
            if (read == 4) {
                allocate.flip();
                return allocate.getInt();
            }
            if (read != -1) {
                this.zzacw.zzgf().zziv().zzg("Unexpected data length. Bytes read", Integer.valueOf(read));
            }
            return 0;
        } catch (IOException e) {
            this.zzacw.zzgf().zzis().zzg("Failed to read from channel", e);
            return 0;
        }
    }

    private final zzdz zza(Context context, String str, String str2, boolean z, boolean z2, boolean z3, long j) {
        String str3;
        int i;
        String str4;
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            this.zzacw.zzgf().zzis().log("PackageManager is null, can not log app install information");
            return null;
        }
        try {
            str3 = packageManager.getInstallerPackageName(str);
        } catch (IllegalArgumentException unused) {
            this.zzacw.zzgf().zzis().zzg("Error retrieving installer package name. appId", zzfh.zzbl(str));
            str3 = "Unknown";
        }
        if (str3 == null) {
            str3 = "manual_install";
        } else if ("com.android.vending".equals(str3)) {
            str3 = "";
        }
        String str5 = str3;
        try {
            PackageInfo packageInfo = Wrappers.packageManager(context).getPackageInfo(str, 0);
            if (packageInfo != null) {
                CharSequence applicationLabel = Wrappers.packageManager(context).getApplicationLabel(str);
                if (!TextUtils.isEmpty(applicationLabel)) {
                    applicationLabel.toString();
                }
                String str6 = packageInfo.versionName;
                i = packageInfo.versionCode;
                str4 = str6;
            } else {
                i = Integer.MIN_VALUE;
                str4 = "Unknown";
            }
            this.zzacw.zzgi();
            return new zzdz(str, str2, str4, i, str5, 12451L, this.zzacw.zzgc().zzd(context, str), (String) null, z, false, "", 0L, this.zzacw.zzgh().zzaz(str) ? j : 0L, 0, z2, z3, false);
        } catch (PackageManager.NameNotFoundException unused2) {
            this.zzacw.zzgf().zzis().zze("Error retrieving newly installed package info. appId, appName", zzfh.zzbl(str), "Unknown");
            return null;
        }
    }

    private static void zza(zzjr zzjrVar) {
        if (zzjrVar == null) {
            throw new IllegalStateException("Upload Component not created");
        }
        if (zzjrVar.isInitialized()) {
            return;
        }
        String valueOf = String.valueOf(zzjrVar.getClass());
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 27);
        sb.append("Component not initialized: ");
        sb.append(valueOf);
        throw new IllegalStateException(sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(zzjx zzjxVar) {
        this.zzacw.zzge().zzab();
        zzej zzejVar = new zzej(this);
        zzejVar.zzm();
        this.zzaqm = zzejVar;
        this.zzacw.zzgh().zza(this.zzaqk);
        zzeb zzebVar = new zzeb(this);
        zzebVar.zzm();
        this.zzaqp = zzebVar;
        zzjo zzjoVar = new zzjo(this);
        zzjoVar.zzm();
        this.zzaqo = zzjoVar;
        this.zzaqn = new zzfq(this);
        if (this.zzaqu != this.zzaqv) {
            this.zzacw.zzgf().zzis().zze("Not all upload components initialized", Integer.valueOf(this.zzaqu), Integer.valueOf(this.zzaqv));
        }
        this.zzvo = true;
    }

    private final boolean zza(int i, FileChannel fileChannel) {
        zzab();
        if (fileChannel == null || !fileChannel.isOpen()) {
            this.zzacw.zzgf().zzis().log("Bad channel to read from");
            return false;
        }
        ByteBuffer allocate = ByteBuffer.allocate(4);
        allocate.putInt(i);
        allocate.flip();
        try {
            fileChannel.truncate(0L);
            fileChannel.write(allocate);
            fileChannel.force(true);
            if (fileChannel.size() != 4) {
                this.zzacw.zzgf().zzis().zzg("Error writing to channel. Bytes written", Long.valueOf(fileChannel.size()));
            }
            return true;
        } catch (IOException e) {
            this.zzacw.zzgf().zzis().zzg("Failed to write to channel", e);
            return false;
        }
    }

    private final boolean zza(String str, zzew zzewVar) {
        long longValue;
        zzkb zzkbVar;
        String string = zzewVar.zzafr.getString(FirebaseAnalytics.Param.CURRENCY);
        if (FirebaseAnalytics.Event.ECOMMERCE_PURCHASE.equals(zzewVar.name)) {
            double doubleValue = zzewVar.zzafr.zzbg(FirebaseAnalytics.Param.VALUE).doubleValue() * 1000000.0d;
            if (doubleValue == 0.0d) {
                doubleValue = zzewVar.zzafr.getLong(FirebaseAnalytics.Param.VALUE).longValue() * 1000000.0d;
            }
            if (doubleValue > 9.223372036854776E18d || doubleValue < -9.223372036854776E18d) {
                this.zzacw.zzgf().zziv().zze("Data lost. Currency value is too big. appId", zzfh.zzbl(str), Double.valueOf(doubleValue));
                return false;
            }
            longValue = Math.round(doubleValue);
        } else {
            longValue = zzewVar.zzafr.getLong(FirebaseAnalytics.Param.VALUE).longValue();
        }
        if (!TextUtils.isEmpty(string)) {
            String upperCase = string.toUpperCase(Locale.US);
            if (upperCase.matches("[A-Z]{3}")) {
                String valueOf = String.valueOf(upperCase);
                String concat = valueOf.length() != 0 ? "_ltv_".concat(valueOf) : new String("_ltv_");
                zzkb zzh = zzje().zzh(str, concat);
                if (zzh == null || !(zzh.value instanceof Long)) {
                    zzej zzje = zzje();
                    int zzb = this.zzacw.zzgh().zzb(str, zzey.zzaht) - 1;
                    Preconditions.checkNotEmpty(str);
                    zzje.zzab();
                    zzje.zzch();
                    try {
                        zzje.getWritableDatabase().execSQL("delete from user_attributes where app_id=? and name in (select name from user_attributes where app_id=? and name like '_ltv_%' order by set_timestamp desc limit ?,10);", new String[]{str, str, String.valueOf(zzb)});
                    } catch (SQLiteException e) {
                        zzje.zzgf().zzis().zze("Error pruning currencies. appId", zzfh.zzbl(str), e);
                    }
                    zzkbVar = new zzkb(str, zzewVar.origin, concat, this.zzacw.zzbt().currentTimeMillis(), Long.valueOf(longValue));
                } else {
                    zzkbVar = new zzkb(str, zzewVar.origin, concat, this.zzacw.zzbt().currentTimeMillis(), Long.valueOf(((Long) zzh.value).longValue() + longValue));
                }
                if (!zzje().zza(zzkbVar)) {
                    this.zzacw.zzgf().zzis().zzd("Too many unique user properties are set. Ignoring user property. appId", zzfh.zzbl(str), this.zzacw.zzgb().zzbk(zzkbVar.name), zzkbVar.value);
                    this.zzacw.zzgc().zza(str, 9, (String) null, (String) null, 0);
                }
            }
        }
        return true;
    }

    private final zzko[] zza(String str, zzku[] zzkuVarArr, zzkp[] zzkpVarArr) {
        Preconditions.checkNotEmpty(str);
        return zzjd().zza(str, zzkpVarArr, zzkuVarArr);
    }

    private final void zzab() {
        this.zzacw.zzge().zzab();
    }

    private final void zzb(zzdy zzdyVar) {
        ArrayMap arrayMap;
        zzab();
        if (TextUtils.isEmpty(zzdyVar.getGmpAppId())) {
            zzb(zzdyVar.zzah(), HttpStatus.SC_NO_CONTENT, null, null, null);
            return;
        }
        String gmpAppId = zzdyVar.getGmpAppId();
        String appInstanceId = zzdyVar.getAppInstanceId();
        Uri.Builder builder = new Uri.Builder();
        Uri.Builder encodedAuthority = builder.scheme(zzey.zzagt.get()).encodedAuthority(zzey.zzagu.get());
        String valueOf = String.valueOf(gmpAppId);
        encodedAuthority.path(valueOf.length() != 0 ? "config/app/".concat(valueOf) : new String("config/app/")).appendQueryParameter("app_instance_id", appInstanceId).appendQueryParameter("platform", "android").appendQueryParameter("gmp_version", "12451");
        String uri = builder.build().toString();
        try {
            URL url = new URL(uri);
            this.zzacw.zzgf().zziz().zzg("Fetching remote configuration", zzdyVar.zzah());
            zzkm zzbt = zzkv().zzbt(zzdyVar.zzah());
            String zzbu = zzkv().zzbu(zzdyVar.zzah());
            if (zzbt == null || TextUtils.isEmpty(zzbu)) {
                arrayMap = null;
            } else {
                ArrayMap arrayMap2 = new ArrayMap();
                arrayMap2.put("If-Modified-Since", zzbu);
                arrayMap = arrayMap2;
            }
            this.zzaqw = true;
            zzfl zzkw = zzkw();
            String zzah = zzdyVar.zzah();
            zzjv zzjvVar = new zzjv(this);
            zzkw.zzab();
            zzkw.zzch();
            Preconditions.checkNotNull(url);
            Preconditions.checkNotNull(zzjvVar);
            zzkw.zzge().zzd(new zzfp(zzkw, zzah, url, null, arrayMap, zzjvVar));
        } catch (MalformedURLException unused) {
            this.zzacw.zzgf().zzis().zze("Failed to parse config URL. Not fetching. appId", zzfh.zzbl(zzdyVar.zzah()), uri);
        }
    }

    private final Boolean zzc(zzdy zzdyVar) {
        try {
            if (zzdyVar.zzgo() != -2147483648L) {
                if (zzdyVar.zzgo() == Wrappers.packageManager(this.zzacw.getContext()).getPackageInfo(zzdyVar.zzah(), 0).versionCode) {
                    return true;
                }
            } else {
                String str = Wrappers.packageManager(this.zzacw.getContext()).getPackageInfo(zzdyVar.zzah(), 0).versionName;
                if (zzdyVar.zzag() != null && zzdyVar.zzag().equals(str)) {
                    return true;
                }
            }
            return false;
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:118:0x058a, code lost:
    
        r14 = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void zzc(zzew zzewVar, zzdz zzdzVar) {
        zzer zza2;
        zzes zzac;
        zzdy zzbb;
        Preconditions.checkNotNull(zzdzVar);
        Preconditions.checkNotEmpty(zzdzVar.packageName);
        long nanoTime = System.nanoTime();
        zzab();
        zzkz();
        String str = zzdzVar.packageName;
        if (this.zzacw.zzgc().zzd(zzewVar, zzdzVar)) {
            if (!zzdzVar.zzadw) {
                zzg(zzdzVar);
                return;
            }
            if (zzkv().zzn(str, zzewVar.name)) {
                this.zzacw.zzgf().zziv().zze("Dropping blacklisted event. appId", zzfh.zzbl(str), this.zzacw.zzgb().zzbi(zzewVar.name));
                boolean z = zzkv().zzbx(str) || zzkv().zzby(str);
                if (!z && !"_err".equals(zzewVar.name)) {
                    this.zzacw.zzgc().zza(str, 11, "_ev", zzewVar.name, 0);
                }
                if (!z || (zzbb = zzje().zzbb(str)) == null) {
                    return;
                }
                if (Math.abs(this.zzacw.zzbt().currentTimeMillis() - Math.max(zzbb.zzgu(), zzbb.zzgt())) > zzey.zzaho.get().longValue()) {
                    this.zzacw.zzgf().zziy().log("Fetching config for blacklisted app");
                    zzb(zzbb);
                    return;
                }
                return;
            }
            if (this.zzacw.zzgf().isLoggable(2)) {
                this.zzacw.zzgf().zziz().zzg("Logging event", this.zzacw.zzgb().zzb(zzewVar));
            }
            zzje().beginTransaction();
            try {
                zzg(zzdzVar);
                if (("_iap".equals(zzewVar.name) || FirebaseAnalytics.Event.ECOMMERCE_PURCHASE.equals(zzewVar.name)) && !zza(str, zzewVar)) {
                    zzje().setTransactionSuccessful();
                    return;
                }
                boolean zzcb = zzkc.zzcb(zzewVar.name);
                boolean equals = "_err".equals(zzewVar.name);
                zzek zza3 = zzje().zza(zzla(), str, true, zzcb, false, equals, false);
                long intValue = zza3.zzaff - zzey.zzagz.get().intValue();
                if (intValue > 0) {
                    if (intValue % 1000 == 1) {
                        this.zzacw.zzgf().zzis().zze("Data loss. Too many events logged. appId, count", zzfh.zzbl(str), Long.valueOf(zza3.zzaff));
                    }
                    zzje().setTransactionSuccessful();
                    return;
                }
                if (zzcb) {
                    long intValue2 = zza3.zzafe - zzey.zzahb.get().intValue();
                    if (intValue2 > 0) {
                        if (intValue2 % 1000 == 1) {
                            this.zzacw.zzgf().zzis().zze("Data loss. Too many public events logged. appId, count", zzfh.zzbl(str), Long.valueOf(zza3.zzafe));
                        }
                        this.zzacw.zzgc().zza(str, 16, "_ev", zzewVar.name, 0);
                        zzje().setTransactionSuccessful();
                        return;
                    }
                }
                if (equals) {
                    long max = zza3.zzafh - Math.max(0, Math.min(DurationKt.NANOS_IN_MILLIS, this.zzacw.zzgh().zzb(zzdzVar.packageName, zzey.zzaha)));
                    if (max > 0) {
                        if (max == 1) {
                            this.zzacw.zzgf().zzis().zze("Too many error events logged. appId, count", zzfh.zzbl(str), Long.valueOf(zza3.zzafh));
                        }
                        zzje().setTransactionSuccessful();
                        return;
                    }
                }
                Bundle zzij = zzewVar.zzafr.zzij();
                this.zzacw.zzgc().zza(zzij, "_o", zzewVar.origin);
                if (this.zzacw.zzgc().zzci(str)) {
                    this.zzacw.zzgc().zza(zzij, "_dbg", (Object) 1L);
                    this.zzacw.zzgc().zza(zzij, "_r", (Object) 1L);
                }
                long zzbc = zzje().zzbc(str);
                if (zzbc > 0) {
                    this.zzacw.zzgf().zziv().zze("Data lost. Too many events stored on disk, deleted. appId", zzfh.zzbl(str), Long.valueOf(zzbc));
                }
                zzer zzerVar = new zzer(this.zzacw, zzewVar.origin, str, zzewVar.name, zzewVar.zzagc, 0L, zzij);
                zzes zzf = zzje().zzf(str, zzerVar.name);
                if (zzf != null) {
                    zza2 = zzerVar.zza(this.zzacw, zzf.zzafu);
                    zzac = zzf.zzac(zza2.timestamp);
                } else if (zzje().zzbf(str) >= 500 && zzcb) {
                    this.zzacw.zzgf().zzis().zzd("Too many event names used, ignoring event. appId, name, supported count", zzfh.zzbl(str), this.zzacw.zzgb().zzbi(zzerVar.name), Integer.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR));
                    this.zzacw.zzgc().zza(str, 8, (String) null, (String) null, 0);
                    return;
                } else {
                    zzac = new zzes(str, zzerVar.name, 0L, 0L, zzerVar.timestamp, 0L, null, null, null);
                    zza2 = zzerVar;
                }
                zzje().zza(zzac);
                zzab();
                zzkz();
                Preconditions.checkNotNull(zza2);
                Preconditions.checkNotNull(zzdzVar);
                Preconditions.checkNotEmpty(zza2.zzti);
                Preconditions.checkArgument(zza2.zzti.equals(zzdzVar.packageName));
                zzks zzksVar = new zzks();
                zzksVar.zzatt = 1;
                zzksVar.zzaub = "android";
                zzksVar.zzti = zzdzVar.packageName;
                zzksVar.zzadt = zzdzVar.zzadt;
                zzksVar.zzth = zzdzVar.zzth;
                zzksVar.zzaun = zzdzVar.zzads == -2147483648L ? null : Integer.valueOf((int) zzdzVar.zzads);
                zzksVar.zzauf = Long.valueOf(zzdzVar.zzadu);
                zzksVar.zzadm = zzdzVar.zzadm;
                zzksVar.zzauj = zzdzVar.zzadv == 0 ? null : Long.valueOf(zzdzVar.zzadv);
                Pair<String, Boolean> zzbn = this.zzacw.zzgg().zzbn(zzdzVar.packageName);
                if (zzbn == null || TextUtils.isEmpty((CharSequence) zzbn.first)) {
                    if (!this.zzacw.zzfx().zzf(this.zzacw.getContext()) && zzdzVar.zzadz) {
                        String string = Settings.Secure.getString(this.zzacw.getContext().getContentResolver(), "android_id");
                        if (string == null) {
                            this.zzacw.zzgf().zziv().zzg("null secure ID. appId", zzfh.zzbl(zzksVar.zzti));
                            string = "null";
                        } else if (string.isEmpty()) {
                            this.zzacw.zzgf().zziv().zzg("empty secure ID. appId", zzfh.zzbl(zzksVar.zzti));
                        }
                        zzksVar.zzauq = string;
                    }
                } else if (zzdzVar.zzady) {
                    zzksVar.zzauh = (String) zzbn.first;
                    zzksVar.zzaui = (Boolean) zzbn.second;
                }
                this.zzacw.zzfx().zzch();
                zzksVar.zzaud = Build.MODEL;
                this.zzacw.zzfx().zzch();
                zzksVar.zzauc = Build.VERSION.RELEASE;
                zzksVar.zzaue = Integer.valueOf((int) this.zzacw.zzfx().zzig());
                zzksVar.zzafo = this.zzacw.zzfx().zzih();
                zzksVar.zzaug = null;
                zzksVar.zzatw = null;
                zzksVar.zzatx = null;
                zzksVar.zzaty = null;
                zzksVar.zzaus = Long.valueOf(zzdzVar.zzadx);
                if (this.zzacw.isEnabled() && zzeg.zzho()) {
                    zzksVar.zzaut = null;
                }
                zzdy zzbb2 = zzje().zzbb(zzdzVar.packageName);
                if (zzbb2 == null) {
                    zzbb2 = new zzdy(this.zzacw, zzdzVar.packageName);
                    zzbb2.zzak(this.zzacw.zzfw().zzio());
                    zzbb2.zzan(zzdzVar.zzado);
                    zzbb2.zzal(zzdzVar.zzadm);
                    zzbb2.zzam(this.zzacw.zzgg().zzbo(zzdzVar.packageName));
                    zzbb2.zzr(0L);
                    zzbb2.zzm(0L);
                    zzbb2.zzn(0L);
                    zzbb2.setAppVersion(zzdzVar.zzth);
                    zzbb2.zzo(zzdzVar.zzads);
                    zzbb2.zzao(zzdzVar.zzadt);
                    zzbb2.zzp(zzdzVar.zzadu);
                    zzbb2.zzq(zzdzVar.zzadv);
                    zzbb2.setMeasurementEnabled(zzdzVar.zzadw);
                    zzbb2.zzaa(zzdzVar.zzadx);
                    zzje().zza(zzbb2);
                }
                zzksVar.zzadl = zzbb2.getAppInstanceId();
                zzksVar.zzado = zzbb2.zzgl();
                List<zzkb> zzba = zzje().zzba(zzdzVar.packageName);
                zzksVar.zzatv = new zzku[zzba.size()];
                for (int i = 0; i < zzba.size(); i++) {
                    zzku zzkuVar = new zzku();
                    zzksVar.zzatv[i] = zzkuVar;
                    zzkuVar.name = zzba.get(i).name;
                    zzkuVar.zzauz = Long.valueOf(zzba.get(i).zzarl);
                    zzjc().zza(zzkuVar, zzba.get(i).value);
                }
                try {
                    long zza4 = zzje().zza(zzksVar);
                    zzej zzje = zzje();
                    if (zza2.zzafr != null) {
                        Iterator<String> it = zza2.zzafr.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                if ("_r".equals(it.next())) {
                                    break;
                                }
                            } else {
                                boolean zzo = zzkv().zzo(zza2.zzti, zza2.name);
                                zzek zza5 = zzje().zza(zzla(), zza2.zzti, false, false, false, false, false);
                                if (zzo && zza5.zzafi < this.zzacw.zzgh().zzaq(zza2.zzti)) {
                                }
                            }
                        }
                    }
                    boolean z2 = false;
                    if (zzje.zza(zza2, zza4, z2)) {
                        this.zzaqs = 0L;
                    }
                } catch (IOException e) {
                    this.zzacw.zzgf().zzis().zze("Data loss. Failed to insert raw event metadata. appId", zzfh.zzbl(zzksVar.zzti), e);
                }
                zzje().setTransactionSuccessful();
                if (this.zzacw.zzgf().isLoggable(2)) {
                    this.zzacw.zzgf().zziz().zzg("Event recorded", this.zzacw.zzgb().zza(zza2));
                }
                zzje().endTransaction();
                zzld();
                this.zzacw.zzgf().zziz().zzg("Background event processing time, ms", Long.valueOf(((System.nanoTime() - nanoTime) + 500000) / 1000000));
            } finally {
                zzje().endTransaction();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0076, code lost:
    
        if (r4 != null) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:386:0x0204, code lost:
    
        if (r4 == null) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:391:0x0228, code lost:
    
        if (r4 != null) goto L18;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:150:0x05f4 A[Catch: all -> 0x0ae0, TryCatch #1 {all -> 0x0ae0, blocks: (B:3:0x000b, B:19:0x0078, B:20:0x022c, B:22:0x0230, B:27:0x023e, B:28:0x025b, B:30:0x0265, B:33:0x027f, B:35:0x02b4, B:40:0x02c8, B:42:0x02d0, B:45:0x0556, B:47:0x02ef, B:49:0x0303, B:54:0x04f9, B:56:0x0503, B:58:0x0507, B:61:0x050d, B:63:0x051a, B:64:0x052e, B:65:0x0532, B:66:0x0550, B:68:0x0539, B:70:0x031b, B:72:0x031f, B:73:0x0324, B:78:0x0337, B:80:0x0343, B:82:0x035b, B:83:0x034b, B:85:0x0353, B:91:0x0368, B:93:0x03a6, B:94:0x03e2, B:97:0x0416, B:99:0x041b, B:103:0x0427, B:105:0x0430, B:107:0x0438, B:108:0x0440, B:101:0x0443, B:110:0x044a, B:113:0x0454, B:115:0x0487, B:117:0x04a6, B:121:0x04bb, B:122:0x04b2, B:130:0x04c2, B:132:0x04d5, B:133:0x04e0, B:137:0x055e, B:139:0x0570, B:141:0x057c, B:143:0x058a, B:146:0x058f, B:147:0x05d1, B:148:0x05ef, B:150:0x05f4, B:154:0x0600, B:156:0x060c, B:159:0x062c, B:152:0x0606, B:162:0x05b4, B:163:0x0644, B:165:0x0660, B:167:0x067b, B:170:0x068b, B:172:0x069e, B:173:0x06b2, B:175:0x06b6, B:177:0x06c0, B:178:0x06cd, B:180:0x06d1, B:182:0x06d9, B:183:0x06e8, B:187:0x08c9, B:190:0x06fc, B:194:0x070d, B:196:0x0717, B:200:0x0725, B:202:0x0729, B:206:0x0759, B:208:0x076b, B:210:0x078b, B:212:0x0795, B:214:0x07a5, B:215:0x07dd, B:218:0x07ed, B:220:0x07f4, B:222:0x07fe, B:224:0x0802, B:226:0x0806, B:228:0x080a, B:229:0x0816, B:231:0x081c, B:233:0x0837, B:234:0x0840, B:235:0x0854, B:237:0x086f, B:239:0x0898, B:240:0x08a6, B:242:0x08b7, B:244:0x08bd, B:249:0x0731, B:251:0x0735, B:253:0x073d, B:255:0x0741, B:198:0x074b, B:261:0x08d6, B:263:0x08dd, B:264:0x08e5, B:265:0x08ed, B:267:0x08f3, B:269:0x0909, B:270:0x091d, B:272:0x0922, B:274:0x0936, B:275:0x093a, B:277:0x094a, B:279:0x094e, B:282:0x0951, B:284:0x0960, B:285:0x09d4, B:287:0x09d9, B:289:0x09ec, B:292:0x09f1, B:293:0x09f3, B:294:0x0a1e, B:295:0x09f6, B:297:0x0a00, B:298:0x0a07, B:299:0x0a27, B:300:0x0a3e, B:303:0x0a46, B:305:0x0a4b, B:308:0x0a5b, B:310:0x0a75, B:311:0x0a8e, B:313:0x0a96, B:314:0x0ab8, B:321:0x0aa7, B:322:0x0978, B:324:0x097d, B:326:0x0987, B:327:0x098d, B:332:0x099f, B:333:0x09a5, B:338:0x0ac8, B:413:0x0adc, B:414:0x0adf), top: B:2:0x000b, inners: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:156:0x060c A[Catch: all -> 0x0ae0, TryCatch #1 {all -> 0x0ae0, blocks: (B:3:0x000b, B:19:0x0078, B:20:0x022c, B:22:0x0230, B:27:0x023e, B:28:0x025b, B:30:0x0265, B:33:0x027f, B:35:0x02b4, B:40:0x02c8, B:42:0x02d0, B:45:0x0556, B:47:0x02ef, B:49:0x0303, B:54:0x04f9, B:56:0x0503, B:58:0x0507, B:61:0x050d, B:63:0x051a, B:64:0x052e, B:65:0x0532, B:66:0x0550, B:68:0x0539, B:70:0x031b, B:72:0x031f, B:73:0x0324, B:78:0x0337, B:80:0x0343, B:82:0x035b, B:83:0x034b, B:85:0x0353, B:91:0x0368, B:93:0x03a6, B:94:0x03e2, B:97:0x0416, B:99:0x041b, B:103:0x0427, B:105:0x0430, B:107:0x0438, B:108:0x0440, B:101:0x0443, B:110:0x044a, B:113:0x0454, B:115:0x0487, B:117:0x04a6, B:121:0x04bb, B:122:0x04b2, B:130:0x04c2, B:132:0x04d5, B:133:0x04e0, B:137:0x055e, B:139:0x0570, B:141:0x057c, B:143:0x058a, B:146:0x058f, B:147:0x05d1, B:148:0x05ef, B:150:0x05f4, B:154:0x0600, B:156:0x060c, B:159:0x062c, B:152:0x0606, B:162:0x05b4, B:163:0x0644, B:165:0x0660, B:167:0x067b, B:170:0x068b, B:172:0x069e, B:173:0x06b2, B:175:0x06b6, B:177:0x06c0, B:178:0x06cd, B:180:0x06d1, B:182:0x06d9, B:183:0x06e8, B:187:0x08c9, B:190:0x06fc, B:194:0x070d, B:196:0x0717, B:200:0x0725, B:202:0x0729, B:206:0x0759, B:208:0x076b, B:210:0x078b, B:212:0x0795, B:214:0x07a5, B:215:0x07dd, B:218:0x07ed, B:220:0x07f4, B:222:0x07fe, B:224:0x0802, B:226:0x0806, B:228:0x080a, B:229:0x0816, B:231:0x081c, B:233:0x0837, B:234:0x0840, B:235:0x0854, B:237:0x086f, B:239:0x0898, B:240:0x08a6, B:242:0x08b7, B:244:0x08bd, B:249:0x0731, B:251:0x0735, B:253:0x073d, B:255:0x0741, B:198:0x074b, B:261:0x08d6, B:263:0x08dd, B:264:0x08e5, B:265:0x08ed, B:267:0x08f3, B:269:0x0909, B:270:0x091d, B:272:0x0922, B:274:0x0936, B:275:0x093a, B:277:0x094a, B:279:0x094e, B:282:0x0951, B:284:0x0960, B:285:0x09d4, B:287:0x09d9, B:289:0x09ec, B:292:0x09f1, B:293:0x09f3, B:294:0x0a1e, B:295:0x09f6, B:297:0x0a00, B:298:0x0a07, B:299:0x0a27, B:300:0x0a3e, B:303:0x0a46, B:305:0x0a4b, B:308:0x0a5b, B:310:0x0a75, B:311:0x0a8e, B:313:0x0a96, B:314:0x0ab8, B:321:0x0aa7, B:322:0x0978, B:324:0x097d, B:326:0x0987, B:327:0x098d, B:332:0x099f, B:333:0x09a5, B:338:0x0ac8, B:413:0x0adc, B:414:0x0adf), top: B:2:0x000b, inners: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:159:0x062c A[Catch: all -> 0x0ae0, TryCatch #1 {all -> 0x0ae0, blocks: (B:3:0x000b, B:19:0x0078, B:20:0x022c, B:22:0x0230, B:27:0x023e, B:28:0x025b, B:30:0x0265, B:33:0x027f, B:35:0x02b4, B:40:0x02c8, B:42:0x02d0, B:45:0x0556, B:47:0x02ef, B:49:0x0303, B:54:0x04f9, B:56:0x0503, B:58:0x0507, B:61:0x050d, B:63:0x051a, B:64:0x052e, B:65:0x0532, B:66:0x0550, B:68:0x0539, B:70:0x031b, B:72:0x031f, B:73:0x0324, B:78:0x0337, B:80:0x0343, B:82:0x035b, B:83:0x034b, B:85:0x0353, B:91:0x0368, B:93:0x03a6, B:94:0x03e2, B:97:0x0416, B:99:0x041b, B:103:0x0427, B:105:0x0430, B:107:0x0438, B:108:0x0440, B:101:0x0443, B:110:0x044a, B:113:0x0454, B:115:0x0487, B:117:0x04a6, B:121:0x04bb, B:122:0x04b2, B:130:0x04c2, B:132:0x04d5, B:133:0x04e0, B:137:0x055e, B:139:0x0570, B:141:0x057c, B:143:0x058a, B:146:0x058f, B:147:0x05d1, B:148:0x05ef, B:150:0x05f4, B:154:0x0600, B:156:0x060c, B:159:0x062c, B:152:0x0606, B:162:0x05b4, B:163:0x0644, B:165:0x0660, B:167:0x067b, B:170:0x068b, B:172:0x069e, B:173:0x06b2, B:175:0x06b6, B:177:0x06c0, B:178:0x06cd, B:180:0x06d1, B:182:0x06d9, B:183:0x06e8, B:187:0x08c9, B:190:0x06fc, B:194:0x070d, B:196:0x0717, B:200:0x0725, B:202:0x0729, B:206:0x0759, B:208:0x076b, B:210:0x078b, B:212:0x0795, B:214:0x07a5, B:215:0x07dd, B:218:0x07ed, B:220:0x07f4, B:222:0x07fe, B:224:0x0802, B:226:0x0806, B:228:0x080a, B:229:0x0816, B:231:0x081c, B:233:0x0837, B:234:0x0840, B:235:0x0854, B:237:0x086f, B:239:0x0898, B:240:0x08a6, B:242:0x08b7, B:244:0x08bd, B:249:0x0731, B:251:0x0735, B:253:0x073d, B:255:0x0741, B:198:0x074b, B:261:0x08d6, B:263:0x08dd, B:264:0x08e5, B:265:0x08ed, B:267:0x08f3, B:269:0x0909, B:270:0x091d, B:272:0x0922, B:274:0x0936, B:275:0x093a, B:277:0x094a, B:279:0x094e, B:282:0x0951, B:284:0x0960, B:285:0x09d4, B:287:0x09d9, B:289:0x09ec, B:292:0x09f1, B:293:0x09f3, B:294:0x0a1e, B:295:0x09f6, B:297:0x0a00, B:298:0x0a07, B:299:0x0a27, B:300:0x0a3e, B:303:0x0a46, B:305:0x0a4b, B:308:0x0a5b, B:310:0x0a75, B:311:0x0a8e, B:313:0x0a96, B:314:0x0ab8, B:321:0x0aa7, B:322:0x0978, B:324:0x097d, B:326:0x0987, B:327:0x098d, B:332:0x099f, B:333:0x09a5, B:338:0x0ac8, B:413:0x0adc, B:414:0x0adf), top: B:2:0x000b, inners: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:160:0x0609 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:206:0x0759 A[Catch: all -> 0x0ae0, TryCatch #1 {all -> 0x0ae0, blocks: (B:3:0x000b, B:19:0x0078, B:20:0x022c, B:22:0x0230, B:27:0x023e, B:28:0x025b, B:30:0x0265, B:33:0x027f, B:35:0x02b4, B:40:0x02c8, B:42:0x02d0, B:45:0x0556, B:47:0x02ef, B:49:0x0303, B:54:0x04f9, B:56:0x0503, B:58:0x0507, B:61:0x050d, B:63:0x051a, B:64:0x052e, B:65:0x0532, B:66:0x0550, B:68:0x0539, B:70:0x031b, B:72:0x031f, B:73:0x0324, B:78:0x0337, B:80:0x0343, B:82:0x035b, B:83:0x034b, B:85:0x0353, B:91:0x0368, B:93:0x03a6, B:94:0x03e2, B:97:0x0416, B:99:0x041b, B:103:0x0427, B:105:0x0430, B:107:0x0438, B:108:0x0440, B:101:0x0443, B:110:0x044a, B:113:0x0454, B:115:0x0487, B:117:0x04a6, B:121:0x04bb, B:122:0x04b2, B:130:0x04c2, B:132:0x04d5, B:133:0x04e0, B:137:0x055e, B:139:0x0570, B:141:0x057c, B:143:0x058a, B:146:0x058f, B:147:0x05d1, B:148:0x05ef, B:150:0x05f4, B:154:0x0600, B:156:0x060c, B:159:0x062c, B:152:0x0606, B:162:0x05b4, B:163:0x0644, B:165:0x0660, B:167:0x067b, B:170:0x068b, B:172:0x069e, B:173:0x06b2, B:175:0x06b6, B:177:0x06c0, B:178:0x06cd, B:180:0x06d1, B:182:0x06d9, B:183:0x06e8, B:187:0x08c9, B:190:0x06fc, B:194:0x070d, B:196:0x0717, B:200:0x0725, B:202:0x0729, B:206:0x0759, B:208:0x076b, B:210:0x078b, B:212:0x0795, B:214:0x07a5, B:215:0x07dd, B:218:0x07ed, B:220:0x07f4, B:222:0x07fe, B:224:0x0802, B:226:0x0806, B:228:0x080a, B:229:0x0816, B:231:0x081c, B:233:0x0837, B:234:0x0840, B:235:0x0854, B:237:0x086f, B:239:0x0898, B:240:0x08a6, B:242:0x08b7, B:244:0x08bd, B:249:0x0731, B:251:0x0735, B:253:0x073d, B:255:0x0741, B:198:0x074b, B:261:0x08d6, B:263:0x08dd, B:264:0x08e5, B:265:0x08ed, B:267:0x08f3, B:269:0x0909, B:270:0x091d, B:272:0x0922, B:274:0x0936, B:275:0x093a, B:277:0x094a, B:279:0x094e, B:282:0x0951, B:284:0x0960, B:285:0x09d4, B:287:0x09d9, B:289:0x09ec, B:292:0x09f1, B:293:0x09f3, B:294:0x0a1e, B:295:0x09f6, B:297:0x0a00, B:298:0x0a07, B:299:0x0a27, B:300:0x0a3e, B:303:0x0a46, B:305:0x0a4b, B:308:0x0a5b, B:310:0x0a75, B:311:0x0a8e, B:313:0x0a96, B:314:0x0ab8, B:321:0x0aa7, B:322:0x0978, B:324:0x097d, B:326:0x0987, B:327:0x098d, B:332:0x099f, B:333:0x09a5, B:338:0x0ac8, B:413:0x0adc, B:414:0x0adf), top: B:2:0x000b, inners: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:208:0x076b A[Catch: all -> 0x0ae0, TryCatch #1 {all -> 0x0ae0, blocks: (B:3:0x000b, B:19:0x0078, B:20:0x022c, B:22:0x0230, B:27:0x023e, B:28:0x025b, B:30:0x0265, B:33:0x027f, B:35:0x02b4, B:40:0x02c8, B:42:0x02d0, B:45:0x0556, B:47:0x02ef, B:49:0x0303, B:54:0x04f9, B:56:0x0503, B:58:0x0507, B:61:0x050d, B:63:0x051a, B:64:0x052e, B:65:0x0532, B:66:0x0550, B:68:0x0539, B:70:0x031b, B:72:0x031f, B:73:0x0324, B:78:0x0337, B:80:0x0343, B:82:0x035b, B:83:0x034b, B:85:0x0353, B:91:0x0368, B:93:0x03a6, B:94:0x03e2, B:97:0x0416, B:99:0x041b, B:103:0x0427, B:105:0x0430, B:107:0x0438, B:108:0x0440, B:101:0x0443, B:110:0x044a, B:113:0x0454, B:115:0x0487, B:117:0x04a6, B:121:0x04bb, B:122:0x04b2, B:130:0x04c2, B:132:0x04d5, B:133:0x04e0, B:137:0x055e, B:139:0x0570, B:141:0x057c, B:143:0x058a, B:146:0x058f, B:147:0x05d1, B:148:0x05ef, B:150:0x05f4, B:154:0x0600, B:156:0x060c, B:159:0x062c, B:152:0x0606, B:162:0x05b4, B:163:0x0644, B:165:0x0660, B:167:0x067b, B:170:0x068b, B:172:0x069e, B:173:0x06b2, B:175:0x06b6, B:177:0x06c0, B:178:0x06cd, B:180:0x06d1, B:182:0x06d9, B:183:0x06e8, B:187:0x08c9, B:190:0x06fc, B:194:0x070d, B:196:0x0717, B:200:0x0725, B:202:0x0729, B:206:0x0759, B:208:0x076b, B:210:0x078b, B:212:0x0795, B:214:0x07a5, B:215:0x07dd, B:218:0x07ed, B:220:0x07f4, B:222:0x07fe, B:224:0x0802, B:226:0x0806, B:228:0x080a, B:229:0x0816, B:231:0x081c, B:233:0x0837, B:234:0x0840, B:235:0x0854, B:237:0x086f, B:239:0x0898, B:240:0x08a6, B:242:0x08b7, B:244:0x08bd, B:249:0x0731, B:251:0x0735, B:253:0x073d, B:255:0x0741, B:198:0x074b, B:261:0x08d6, B:263:0x08dd, B:264:0x08e5, B:265:0x08ed, B:267:0x08f3, B:269:0x0909, B:270:0x091d, B:272:0x0922, B:274:0x0936, B:275:0x093a, B:277:0x094a, B:279:0x094e, B:282:0x0951, B:284:0x0960, B:285:0x09d4, B:287:0x09d9, B:289:0x09ec, B:292:0x09f1, B:293:0x09f3, B:294:0x0a1e, B:295:0x09f6, B:297:0x0a00, B:298:0x0a07, B:299:0x0a27, B:300:0x0a3e, B:303:0x0a46, B:305:0x0a4b, B:308:0x0a5b, B:310:0x0a75, B:311:0x0a8e, B:313:0x0a96, B:314:0x0ab8, B:321:0x0aa7, B:322:0x0978, B:324:0x097d, B:326:0x0987, B:327:0x098d, B:332:0x099f, B:333:0x09a5, B:338:0x0ac8, B:413:0x0adc, B:414:0x0adf), top: B:2:0x000b, inners: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:210:0x078b A[Catch: all -> 0x0ae0, TryCatch #1 {all -> 0x0ae0, blocks: (B:3:0x000b, B:19:0x0078, B:20:0x022c, B:22:0x0230, B:27:0x023e, B:28:0x025b, B:30:0x0265, B:33:0x027f, B:35:0x02b4, B:40:0x02c8, B:42:0x02d0, B:45:0x0556, B:47:0x02ef, B:49:0x0303, B:54:0x04f9, B:56:0x0503, B:58:0x0507, B:61:0x050d, B:63:0x051a, B:64:0x052e, B:65:0x0532, B:66:0x0550, B:68:0x0539, B:70:0x031b, B:72:0x031f, B:73:0x0324, B:78:0x0337, B:80:0x0343, B:82:0x035b, B:83:0x034b, B:85:0x0353, B:91:0x0368, B:93:0x03a6, B:94:0x03e2, B:97:0x0416, B:99:0x041b, B:103:0x0427, B:105:0x0430, B:107:0x0438, B:108:0x0440, B:101:0x0443, B:110:0x044a, B:113:0x0454, B:115:0x0487, B:117:0x04a6, B:121:0x04bb, B:122:0x04b2, B:130:0x04c2, B:132:0x04d5, B:133:0x04e0, B:137:0x055e, B:139:0x0570, B:141:0x057c, B:143:0x058a, B:146:0x058f, B:147:0x05d1, B:148:0x05ef, B:150:0x05f4, B:154:0x0600, B:156:0x060c, B:159:0x062c, B:152:0x0606, B:162:0x05b4, B:163:0x0644, B:165:0x0660, B:167:0x067b, B:170:0x068b, B:172:0x069e, B:173:0x06b2, B:175:0x06b6, B:177:0x06c0, B:178:0x06cd, B:180:0x06d1, B:182:0x06d9, B:183:0x06e8, B:187:0x08c9, B:190:0x06fc, B:194:0x070d, B:196:0x0717, B:200:0x0725, B:202:0x0729, B:206:0x0759, B:208:0x076b, B:210:0x078b, B:212:0x0795, B:214:0x07a5, B:215:0x07dd, B:218:0x07ed, B:220:0x07f4, B:222:0x07fe, B:224:0x0802, B:226:0x0806, B:228:0x080a, B:229:0x0816, B:231:0x081c, B:233:0x0837, B:234:0x0840, B:235:0x0854, B:237:0x086f, B:239:0x0898, B:240:0x08a6, B:242:0x08b7, B:244:0x08bd, B:249:0x0731, B:251:0x0735, B:253:0x073d, B:255:0x0741, B:198:0x074b, B:261:0x08d6, B:263:0x08dd, B:264:0x08e5, B:265:0x08ed, B:267:0x08f3, B:269:0x0909, B:270:0x091d, B:272:0x0922, B:274:0x0936, B:275:0x093a, B:277:0x094a, B:279:0x094e, B:282:0x0951, B:284:0x0960, B:285:0x09d4, B:287:0x09d9, B:289:0x09ec, B:292:0x09f1, B:293:0x09f3, B:294:0x0a1e, B:295:0x09f6, B:297:0x0a00, B:298:0x0a07, B:299:0x0a27, B:300:0x0a3e, B:303:0x0a46, B:305:0x0a4b, B:308:0x0a5b, B:310:0x0a75, B:311:0x0a8e, B:313:0x0a96, B:314:0x0ab8, B:321:0x0aa7, B:322:0x0978, B:324:0x097d, B:326:0x0987, B:327:0x098d, B:332:0x099f, B:333:0x09a5, B:338:0x0ac8, B:413:0x0adc, B:414:0x0adf), top: B:2:0x000b, inners: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0230 A[Catch: all -> 0x0ae0, TryCatch #1 {all -> 0x0ae0, blocks: (B:3:0x000b, B:19:0x0078, B:20:0x022c, B:22:0x0230, B:27:0x023e, B:28:0x025b, B:30:0x0265, B:33:0x027f, B:35:0x02b4, B:40:0x02c8, B:42:0x02d0, B:45:0x0556, B:47:0x02ef, B:49:0x0303, B:54:0x04f9, B:56:0x0503, B:58:0x0507, B:61:0x050d, B:63:0x051a, B:64:0x052e, B:65:0x0532, B:66:0x0550, B:68:0x0539, B:70:0x031b, B:72:0x031f, B:73:0x0324, B:78:0x0337, B:80:0x0343, B:82:0x035b, B:83:0x034b, B:85:0x0353, B:91:0x0368, B:93:0x03a6, B:94:0x03e2, B:97:0x0416, B:99:0x041b, B:103:0x0427, B:105:0x0430, B:107:0x0438, B:108:0x0440, B:101:0x0443, B:110:0x044a, B:113:0x0454, B:115:0x0487, B:117:0x04a6, B:121:0x04bb, B:122:0x04b2, B:130:0x04c2, B:132:0x04d5, B:133:0x04e0, B:137:0x055e, B:139:0x0570, B:141:0x057c, B:143:0x058a, B:146:0x058f, B:147:0x05d1, B:148:0x05ef, B:150:0x05f4, B:154:0x0600, B:156:0x060c, B:159:0x062c, B:152:0x0606, B:162:0x05b4, B:163:0x0644, B:165:0x0660, B:167:0x067b, B:170:0x068b, B:172:0x069e, B:173:0x06b2, B:175:0x06b6, B:177:0x06c0, B:178:0x06cd, B:180:0x06d1, B:182:0x06d9, B:183:0x06e8, B:187:0x08c9, B:190:0x06fc, B:194:0x070d, B:196:0x0717, B:200:0x0725, B:202:0x0729, B:206:0x0759, B:208:0x076b, B:210:0x078b, B:212:0x0795, B:214:0x07a5, B:215:0x07dd, B:218:0x07ed, B:220:0x07f4, B:222:0x07fe, B:224:0x0802, B:226:0x0806, B:228:0x080a, B:229:0x0816, B:231:0x081c, B:233:0x0837, B:234:0x0840, B:235:0x0854, B:237:0x086f, B:239:0x0898, B:240:0x08a6, B:242:0x08b7, B:244:0x08bd, B:249:0x0731, B:251:0x0735, B:253:0x073d, B:255:0x0741, B:198:0x074b, B:261:0x08d6, B:263:0x08dd, B:264:0x08e5, B:265:0x08ed, B:267:0x08f3, B:269:0x0909, B:270:0x091d, B:272:0x0922, B:274:0x0936, B:275:0x093a, B:277:0x094a, B:279:0x094e, B:282:0x0951, B:284:0x0960, B:285:0x09d4, B:287:0x09d9, B:289:0x09ec, B:292:0x09f1, B:293:0x09f3, B:294:0x0a1e, B:295:0x09f6, B:297:0x0a00, B:298:0x0a07, B:299:0x0a27, B:300:0x0a3e, B:303:0x0a46, B:305:0x0a4b, B:308:0x0a5b, B:310:0x0a75, B:311:0x0a8e, B:313:0x0a96, B:314:0x0ab8, B:321:0x0aa7, B:322:0x0978, B:324:0x097d, B:326:0x0987, B:327:0x098d, B:332:0x099f, B:333:0x09a5, B:338:0x0ac8, B:413:0x0adc, B:414:0x0adf), top: B:2:0x000b, inners: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:248:0x0768  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x023e A[Catch: all -> 0x0ae0, TryCatch #1 {all -> 0x0ae0, blocks: (B:3:0x000b, B:19:0x0078, B:20:0x022c, B:22:0x0230, B:27:0x023e, B:28:0x025b, B:30:0x0265, B:33:0x027f, B:35:0x02b4, B:40:0x02c8, B:42:0x02d0, B:45:0x0556, B:47:0x02ef, B:49:0x0303, B:54:0x04f9, B:56:0x0503, B:58:0x0507, B:61:0x050d, B:63:0x051a, B:64:0x052e, B:65:0x0532, B:66:0x0550, B:68:0x0539, B:70:0x031b, B:72:0x031f, B:73:0x0324, B:78:0x0337, B:80:0x0343, B:82:0x035b, B:83:0x034b, B:85:0x0353, B:91:0x0368, B:93:0x03a6, B:94:0x03e2, B:97:0x0416, B:99:0x041b, B:103:0x0427, B:105:0x0430, B:107:0x0438, B:108:0x0440, B:101:0x0443, B:110:0x044a, B:113:0x0454, B:115:0x0487, B:117:0x04a6, B:121:0x04bb, B:122:0x04b2, B:130:0x04c2, B:132:0x04d5, B:133:0x04e0, B:137:0x055e, B:139:0x0570, B:141:0x057c, B:143:0x058a, B:146:0x058f, B:147:0x05d1, B:148:0x05ef, B:150:0x05f4, B:154:0x0600, B:156:0x060c, B:159:0x062c, B:152:0x0606, B:162:0x05b4, B:163:0x0644, B:165:0x0660, B:167:0x067b, B:170:0x068b, B:172:0x069e, B:173:0x06b2, B:175:0x06b6, B:177:0x06c0, B:178:0x06cd, B:180:0x06d1, B:182:0x06d9, B:183:0x06e8, B:187:0x08c9, B:190:0x06fc, B:194:0x070d, B:196:0x0717, B:200:0x0725, B:202:0x0729, B:206:0x0759, B:208:0x076b, B:210:0x078b, B:212:0x0795, B:214:0x07a5, B:215:0x07dd, B:218:0x07ed, B:220:0x07f4, B:222:0x07fe, B:224:0x0802, B:226:0x0806, B:228:0x080a, B:229:0x0816, B:231:0x081c, B:233:0x0837, B:234:0x0840, B:235:0x0854, B:237:0x086f, B:239:0x0898, B:240:0x08a6, B:242:0x08b7, B:244:0x08bd, B:249:0x0731, B:251:0x0735, B:253:0x073d, B:255:0x0741, B:198:0x074b, B:261:0x08d6, B:263:0x08dd, B:264:0x08e5, B:265:0x08ed, B:267:0x08f3, B:269:0x0909, B:270:0x091d, B:272:0x0922, B:274:0x0936, B:275:0x093a, B:277:0x094a, B:279:0x094e, B:282:0x0951, B:284:0x0960, B:285:0x09d4, B:287:0x09d9, B:289:0x09ec, B:292:0x09f1, B:293:0x09f3, B:294:0x0a1e, B:295:0x09f6, B:297:0x0a00, B:298:0x0a07, B:299:0x0a27, B:300:0x0a3e, B:303:0x0a46, B:305:0x0a4b, B:308:0x0a5b, B:310:0x0a75, B:311:0x0a8e, B:313:0x0a96, B:314:0x0ab8, B:321:0x0aa7, B:322:0x0978, B:324:0x097d, B:326:0x0987, B:327:0x098d, B:332:0x099f, B:333:0x09a5, B:338:0x0ac8, B:413:0x0adc, B:414:0x0adf), top: B:2:0x000b, inners: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:338:0x0ac8 A[Catch: all -> 0x0ae0, TRY_ENTER, TRY_LEAVE, TryCatch #1 {all -> 0x0ae0, blocks: (B:3:0x000b, B:19:0x0078, B:20:0x022c, B:22:0x0230, B:27:0x023e, B:28:0x025b, B:30:0x0265, B:33:0x027f, B:35:0x02b4, B:40:0x02c8, B:42:0x02d0, B:45:0x0556, B:47:0x02ef, B:49:0x0303, B:54:0x04f9, B:56:0x0503, B:58:0x0507, B:61:0x050d, B:63:0x051a, B:64:0x052e, B:65:0x0532, B:66:0x0550, B:68:0x0539, B:70:0x031b, B:72:0x031f, B:73:0x0324, B:78:0x0337, B:80:0x0343, B:82:0x035b, B:83:0x034b, B:85:0x0353, B:91:0x0368, B:93:0x03a6, B:94:0x03e2, B:97:0x0416, B:99:0x041b, B:103:0x0427, B:105:0x0430, B:107:0x0438, B:108:0x0440, B:101:0x0443, B:110:0x044a, B:113:0x0454, B:115:0x0487, B:117:0x04a6, B:121:0x04bb, B:122:0x04b2, B:130:0x04c2, B:132:0x04d5, B:133:0x04e0, B:137:0x055e, B:139:0x0570, B:141:0x057c, B:143:0x058a, B:146:0x058f, B:147:0x05d1, B:148:0x05ef, B:150:0x05f4, B:154:0x0600, B:156:0x060c, B:159:0x062c, B:152:0x0606, B:162:0x05b4, B:163:0x0644, B:165:0x0660, B:167:0x067b, B:170:0x068b, B:172:0x069e, B:173:0x06b2, B:175:0x06b6, B:177:0x06c0, B:178:0x06cd, B:180:0x06d1, B:182:0x06d9, B:183:0x06e8, B:187:0x08c9, B:190:0x06fc, B:194:0x070d, B:196:0x0717, B:200:0x0725, B:202:0x0729, B:206:0x0759, B:208:0x076b, B:210:0x078b, B:212:0x0795, B:214:0x07a5, B:215:0x07dd, B:218:0x07ed, B:220:0x07f4, B:222:0x07fe, B:224:0x0802, B:226:0x0806, B:228:0x080a, B:229:0x0816, B:231:0x081c, B:233:0x0837, B:234:0x0840, B:235:0x0854, B:237:0x086f, B:239:0x0898, B:240:0x08a6, B:242:0x08b7, B:244:0x08bd, B:249:0x0731, B:251:0x0735, B:253:0x073d, B:255:0x0741, B:198:0x074b, B:261:0x08d6, B:263:0x08dd, B:264:0x08e5, B:265:0x08ed, B:267:0x08f3, B:269:0x0909, B:270:0x091d, B:272:0x0922, B:274:0x0936, B:275:0x093a, B:277:0x094a, B:279:0x094e, B:282:0x0951, B:284:0x0960, B:285:0x09d4, B:287:0x09d9, B:289:0x09ec, B:292:0x09f1, B:293:0x09f3, B:294:0x0a1e, B:295:0x09f6, B:297:0x0a00, B:298:0x0a07, B:299:0x0a27, B:300:0x0a3e, B:303:0x0a46, B:305:0x0a4b, B:308:0x0a5b, B:310:0x0a75, B:311:0x0a8e, B:313:0x0a96, B:314:0x0ab8, B:321:0x0aa7, B:322:0x0978, B:324:0x097d, B:326:0x0987, B:327:0x098d, B:332:0x099f, B:333:0x09a5, B:338:0x0ac8, B:413:0x0adc, B:414:0x0adf), top: B:2:0x000b, inners: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:413:0x0adc A[Catch: all -> 0x0ae0, TRY_ENTER, TryCatch #1 {all -> 0x0ae0, blocks: (B:3:0x000b, B:19:0x0078, B:20:0x022c, B:22:0x0230, B:27:0x023e, B:28:0x025b, B:30:0x0265, B:33:0x027f, B:35:0x02b4, B:40:0x02c8, B:42:0x02d0, B:45:0x0556, B:47:0x02ef, B:49:0x0303, B:54:0x04f9, B:56:0x0503, B:58:0x0507, B:61:0x050d, B:63:0x051a, B:64:0x052e, B:65:0x0532, B:66:0x0550, B:68:0x0539, B:70:0x031b, B:72:0x031f, B:73:0x0324, B:78:0x0337, B:80:0x0343, B:82:0x035b, B:83:0x034b, B:85:0x0353, B:91:0x0368, B:93:0x03a6, B:94:0x03e2, B:97:0x0416, B:99:0x041b, B:103:0x0427, B:105:0x0430, B:107:0x0438, B:108:0x0440, B:101:0x0443, B:110:0x044a, B:113:0x0454, B:115:0x0487, B:117:0x04a6, B:121:0x04bb, B:122:0x04b2, B:130:0x04c2, B:132:0x04d5, B:133:0x04e0, B:137:0x055e, B:139:0x0570, B:141:0x057c, B:143:0x058a, B:146:0x058f, B:147:0x05d1, B:148:0x05ef, B:150:0x05f4, B:154:0x0600, B:156:0x060c, B:159:0x062c, B:152:0x0606, B:162:0x05b4, B:163:0x0644, B:165:0x0660, B:167:0x067b, B:170:0x068b, B:172:0x069e, B:173:0x06b2, B:175:0x06b6, B:177:0x06c0, B:178:0x06cd, B:180:0x06d1, B:182:0x06d9, B:183:0x06e8, B:187:0x08c9, B:190:0x06fc, B:194:0x070d, B:196:0x0717, B:200:0x0725, B:202:0x0729, B:206:0x0759, B:208:0x076b, B:210:0x078b, B:212:0x0795, B:214:0x07a5, B:215:0x07dd, B:218:0x07ed, B:220:0x07f4, B:222:0x07fe, B:224:0x0802, B:226:0x0806, B:228:0x080a, B:229:0x0816, B:231:0x081c, B:233:0x0837, B:234:0x0840, B:235:0x0854, B:237:0x086f, B:239:0x0898, B:240:0x08a6, B:242:0x08b7, B:244:0x08bd, B:249:0x0731, B:251:0x0735, B:253:0x073d, B:255:0x0741, B:198:0x074b, B:261:0x08d6, B:263:0x08dd, B:264:0x08e5, B:265:0x08ed, B:267:0x08f3, B:269:0x0909, B:270:0x091d, B:272:0x0922, B:274:0x0936, B:275:0x093a, B:277:0x094a, B:279:0x094e, B:282:0x0951, B:284:0x0960, B:285:0x09d4, B:287:0x09d9, B:289:0x09ec, B:292:0x09f1, B:293:0x09f3, B:294:0x0a1e, B:295:0x09f6, B:297:0x0a00, B:298:0x0a07, B:299:0x0a27, B:300:0x0a3e, B:303:0x0a46, B:305:0x0a4b, B:308:0x0a5b, B:310:0x0a75, B:311:0x0a8e, B:313:0x0a96, B:314:0x0ab8, B:321:0x0aa7, B:322:0x0978, B:324:0x097d, B:326:0x0987, B:327:0x098d, B:332:0x099f, B:333:0x09a5, B:338:0x0ac8, B:413:0x0adc, B:414:0x0adf), top: B:2:0x000b, inners: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:415:? A[Catch: all -> 0x0ae0, SYNTHETIC, TRY_LEAVE, TryCatch #1 {all -> 0x0ae0, blocks: (B:3:0x000b, B:19:0x0078, B:20:0x022c, B:22:0x0230, B:27:0x023e, B:28:0x025b, B:30:0x0265, B:33:0x027f, B:35:0x02b4, B:40:0x02c8, B:42:0x02d0, B:45:0x0556, B:47:0x02ef, B:49:0x0303, B:54:0x04f9, B:56:0x0503, B:58:0x0507, B:61:0x050d, B:63:0x051a, B:64:0x052e, B:65:0x0532, B:66:0x0550, B:68:0x0539, B:70:0x031b, B:72:0x031f, B:73:0x0324, B:78:0x0337, B:80:0x0343, B:82:0x035b, B:83:0x034b, B:85:0x0353, B:91:0x0368, B:93:0x03a6, B:94:0x03e2, B:97:0x0416, B:99:0x041b, B:103:0x0427, B:105:0x0430, B:107:0x0438, B:108:0x0440, B:101:0x0443, B:110:0x044a, B:113:0x0454, B:115:0x0487, B:117:0x04a6, B:121:0x04bb, B:122:0x04b2, B:130:0x04c2, B:132:0x04d5, B:133:0x04e0, B:137:0x055e, B:139:0x0570, B:141:0x057c, B:143:0x058a, B:146:0x058f, B:147:0x05d1, B:148:0x05ef, B:150:0x05f4, B:154:0x0600, B:156:0x060c, B:159:0x062c, B:152:0x0606, B:162:0x05b4, B:163:0x0644, B:165:0x0660, B:167:0x067b, B:170:0x068b, B:172:0x069e, B:173:0x06b2, B:175:0x06b6, B:177:0x06c0, B:178:0x06cd, B:180:0x06d1, B:182:0x06d9, B:183:0x06e8, B:187:0x08c9, B:190:0x06fc, B:194:0x070d, B:196:0x0717, B:200:0x0725, B:202:0x0729, B:206:0x0759, B:208:0x076b, B:210:0x078b, B:212:0x0795, B:214:0x07a5, B:215:0x07dd, B:218:0x07ed, B:220:0x07f4, B:222:0x07fe, B:224:0x0802, B:226:0x0806, B:228:0x080a, B:229:0x0816, B:231:0x081c, B:233:0x0837, B:234:0x0840, B:235:0x0854, B:237:0x086f, B:239:0x0898, B:240:0x08a6, B:242:0x08b7, B:244:0x08bd, B:249:0x0731, B:251:0x0735, B:253:0x073d, B:255:0x0741, B:198:0x074b, B:261:0x08d6, B:263:0x08dd, B:264:0x08e5, B:265:0x08ed, B:267:0x08f3, B:269:0x0909, B:270:0x091d, B:272:0x0922, B:274:0x0936, B:275:0x093a, B:277:0x094a, B:279:0x094e, B:282:0x0951, B:284:0x0960, B:285:0x09d4, B:287:0x09d9, B:289:0x09ec, B:292:0x09f1, B:293:0x09f3, B:294:0x0a1e, B:295:0x09f6, B:297:0x0a00, B:298:0x0a07, B:299:0x0a27, B:300:0x0a3e, B:303:0x0a46, B:305:0x0a4b, B:308:0x0a5b, B:310:0x0a75, B:311:0x0a8e, B:313:0x0a96, B:314:0x0ab8, B:321:0x0aa7, B:322:0x0978, B:324:0x097d, B:326:0x0987, B:327:0x098d, B:332:0x099f, B:333:0x09a5, B:338:0x0ac8, B:413:0x0adc, B:414:0x0adf), top: B:2:0x000b, inners: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x051a A[Catch: all -> 0x0ae0, TryCatch #1 {all -> 0x0ae0, blocks: (B:3:0x000b, B:19:0x0078, B:20:0x022c, B:22:0x0230, B:27:0x023e, B:28:0x025b, B:30:0x0265, B:33:0x027f, B:35:0x02b4, B:40:0x02c8, B:42:0x02d0, B:45:0x0556, B:47:0x02ef, B:49:0x0303, B:54:0x04f9, B:56:0x0503, B:58:0x0507, B:61:0x050d, B:63:0x051a, B:64:0x052e, B:65:0x0532, B:66:0x0550, B:68:0x0539, B:70:0x031b, B:72:0x031f, B:73:0x0324, B:78:0x0337, B:80:0x0343, B:82:0x035b, B:83:0x034b, B:85:0x0353, B:91:0x0368, B:93:0x03a6, B:94:0x03e2, B:97:0x0416, B:99:0x041b, B:103:0x0427, B:105:0x0430, B:107:0x0438, B:108:0x0440, B:101:0x0443, B:110:0x044a, B:113:0x0454, B:115:0x0487, B:117:0x04a6, B:121:0x04bb, B:122:0x04b2, B:130:0x04c2, B:132:0x04d5, B:133:0x04e0, B:137:0x055e, B:139:0x0570, B:141:0x057c, B:143:0x058a, B:146:0x058f, B:147:0x05d1, B:148:0x05ef, B:150:0x05f4, B:154:0x0600, B:156:0x060c, B:159:0x062c, B:152:0x0606, B:162:0x05b4, B:163:0x0644, B:165:0x0660, B:167:0x067b, B:170:0x068b, B:172:0x069e, B:173:0x06b2, B:175:0x06b6, B:177:0x06c0, B:178:0x06cd, B:180:0x06d1, B:182:0x06d9, B:183:0x06e8, B:187:0x08c9, B:190:0x06fc, B:194:0x070d, B:196:0x0717, B:200:0x0725, B:202:0x0729, B:206:0x0759, B:208:0x076b, B:210:0x078b, B:212:0x0795, B:214:0x07a5, B:215:0x07dd, B:218:0x07ed, B:220:0x07f4, B:222:0x07fe, B:224:0x0802, B:226:0x0806, B:228:0x080a, B:229:0x0816, B:231:0x081c, B:233:0x0837, B:234:0x0840, B:235:0x0854, B:237:0x086f, B:239:0x0898, B:240:0x08a6, B:242:0x08b7, B:244:0x08bd, B:249:0x0731, B:251:0x0735, B:253:0x073d, B:255:0x0741, B:198:0x074b, B:261:0x08d6, B:263:0x08dd, B:264:0x08e5, B:265:0x08ed, B:267:0x08f3, B:269:0x0909, B:270:0x091d, B:272:0x0922, B:274:0x0936, B:275:0x093a, B:277:0x094a, B:279:0x094e, B:282:0x0951, B:284:0x0960, B:285:0x09d4, B:287:0x09d9, B:289:0x09ec, B:292:0x09f1, B:293:0x09f3, B:294:0x0a1e, B:295:0x09f6, B:297:0x0a00, B:298:0x0a07, B:299:0x0a27, B:300:0x0a3e, B:303:0x0a46, B:305:0x0a4b, B:308:0x0a5b, B:310:0x0a75, B:311:0x0a8e, B:313:0x0a96, B:314:0x0ab8, B:321:0x0aa7, B:322:0x0978, B:324:0x097d, B:326:0x0987, B:327:0x098d, B:332:0x099f, B:333:0x09a5, B:338:0x0ac8, B:413:0x0adc, B:414:0x0adf), top: B:2:0x000b, inners: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0532 A[Catch: all -> 0x0ae0, TryCatch #1 {all -> 0x0ae0, blocks: (B:3:0x000b, B:19:0x0078, B:20:0x022c, B:22:0x0230, B:27:0x023e, B:28:0x025b, B:30:0x0265, B:33:0x027f, B:35:0x02b4, B:40:0x02c8, B:42:0x02d0, B:45:0x0556, B:47:0x02ef, B:49:0x0303, B:54:0x04f9, B:56:0x0503, B:58:0x0507, B:61:0x050d, B:63:0x051a, B:64:0x052e, B:65:0x0532, B:66:0x0550, B:68:0x0539, B:70:0x031b, B:72:0x031f, B:73:0x0324, B:78:0x0337, B:80:0x0343, B:82:0x035b, B:83:0x034b, B:85:0x0353, B:91:0x0368, B:93:0x03a6, B:94:0x03e2, B:97:0x0416, B:99:0x041b, B:103:0x0427, B:105:0x0430, B:107:0x0438, B:108:0x0440, B:101:0x0443, B:110:0x044a, B:113:0x0454, B:115:0x0487, B:117:0x04a6, B:121:0x04bb, B:122:0x04b2, B:130:0x04c2, B:132:0x04d5, B:133:0x04e0, B:137:0x055e, B:139:0x0570, B:141:0x057c, B:143:0x058a, B:146:0x058f, B:147:0x05d1, B:148:0x05ef, B:150:0x05f4, B:154:0x0600, B:156:0x060c, B:159:0x062c, B:152:0x0606, B:162:0x05b4, B:163:0x0644, B:165:0x0660, B:167:0x067b, B:170:0x068b, B:172:0x069e, B:173:0x06b2, B:175:0x06b6, B:177:0x06c0, B:178:0x06cd, B:180:0x06d1, B:182:0x06d9, B:183:0x06e8, B:187:0x08c9, B:190:0x06fc, B:194:0x070d, B:196:0x0717, B:200:0x0725, B:202:0x0729, B:206:0x0759, B:208:0x076b, B:210:0x078b, B:212:0x0795, B:214:0x07a5, B:215:0x07dd, B:218:0x07ed, B:220:0x07f4, B:222:0x07fe, B:224:0x0802, B:226:0x0806, B:228:0x080a, B:229:0x0816, B:231:0x081c, B:233:0x0837, B:234:0x0840, B:235:0x0854, B:237:0x086f, B:239:0x0898, B:240:0x08a6, B:242:0x08b7, B:244:0x08bd, B:249:0x0731, B:251:0x0735, B:253:0x073d, B:255:0x0741, B:198:0x074b, B:261:0x08d6, B:263:0x08dd, B:264:0x08e5, B:265:0x08ed, B:267:0x08f3, B:269:0x0909, B:270:0x091d, B:272:0x0922, B:274:0x0936, B:275:0x093a, B:277:0x094a, B:279:0x094e, B:282:0x0951, B:284:0x0960, B:285:0x09d4, B:287:0x09d9, B:289:0x09ec, B:292:0x09f1, B:293:0x09f3, B:294:0x0a1e, B:295:0x09f6, B:297:0x0a00, B:298:0x0a07, B:299:0x0a27, B:300:0x0a3e, B:303:0x0a46, B:305:0x0a4b, B:308:0x0a5b, B:310:0x0a75, B:311:0x0a8e, B:313:0x0a96, B:314:0x0ab8, B:321:0x0aa7, B:322:0x0978, B:324:0x097d, B:326:0x0987, B:327:0x098d, B:332:0x099f, B:333:0x09a5, B:338:0x0ac8, B:413:0x0adc, B:414:0x0adf), top: B:2:0x000b, inners: #4 }] */
    /* JADX WARN: Type inference failed for: r4v0, types: [com.google.android.gms.internal.measurement.zzjt] */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v3, types: [android.database.Cursor] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final boolean zzd(String str, long j) {
        Throwable th;
        SQLiteException sQLiteException;
        String str2;
        boolean z;
        zza zzaVar;
        zzks zzksVar;
        long j2;
        zzkp[] zzkpVarArr;
        zzks zzksVar2;
        int i;
        boolean z2;
        int zzp;
        zza zzaVar2;
        int i2;
        SecureRandom secureRandom;
        int i3;
        zzkb zzkbVar;
        int i4;
        boolean z3;
        String str3;
        int i5;
        int i6;
        long j3;
        zzfj zziv;
        String str4;
        Object zzbl;
        Long l;
        boolean z4;
        SQLiteDatabase writableDatabase;
        String str5;
        String str6;
        String str7;
        String[] strArr;
        String str8 = "_lte";
        zzje().beginTransaction();
        try {
            ?? r4 = 0;
            Cursor cursor = null;
            zza zzaVar3 = new zza(this, r4);
            zzej zzje = zzje();
            long j4 = this.zzard;
            Preconditions.checkNotNull(zzaVar3);
            zzje.zzab();
            zzje.zzch();
            try {
                try {
                    writableDatabase = zzje.getWritableDatabase();
                    try {
                    } catch (SQLiteException e) {
                        sQLiteException = e;
                        str2 = null;
                        zzje.zzgf().zzis().zze("Data loss. Error selecting raw event. appId", zzfh.zzbl(str2), sQLiteException);
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (r4 != 0) {
                        throw th;
                    }
                    r4.close();
                    throw th;
                }
            } catch (SQLiteException e2) {
                sQLiteException = e2;
                cursor = null;
            } catch (Throwable th3) {
                th = th3;
                r4 = 0;
                if (r4 != 0) {
                }
            }
            if (TextUtils.isEmpty(null)) {
                String[] strArr2 = j4 != -1 ? new String[]{String.valueOf(j4), String.valueOf(j)} : new String[]{String.valueOf(j)};
                str5 = j4 != -1 ? "rowid <= ? and " : "";
                StringBuilder sb = new StringBuilder(str5.length() + 148);
                sb.append("select app_id, metadata_fingerprint from raw_events where ");
                sb.append(str5);
                sb.append("app_id in (select app_id from apps where config_fetched_time >= ?) order by rowid limit 1;");
                cursor = writableDatabase.rawQuery(sb.toString(), strArr2);
                if (cursor.moveToFirst()) {
                    str2 = cursor.getString(0);
                    try {
                        String string = cursor.getString(1);
                        cursor.close();
                        str6 = string;
                    } catch (SQLiteException e3) {
                        sQLiteException = e3;
                        zzje.zzgf().zzis().zze("Data loss. Error selecting raw event. appId", zzfh.zzbl(str2), sQLiteException);
                    }
                }
            } else {
                String[] strArr3 = j4 != -1 ? new String[]{null, String.valueOf(j4)} : new String[]{null};
                str5 = j4 != -1 ? " and rowid <= ?" : "";
                StringBuilder sb2 = new StringBuilder(str5.length() + 84);
                sb2.append("select metadata_fingerprint from raw_events where app_id = ?");
                sb2.append(str5);
                sb2.append(" order by rowid limit 1;");
                cursor = writableDatabase.rawQuery(sb2.toString(), strArr3);
                if (cursor.moveToFirst()) {
                    String string2 = cursor.getString(0);
                    cursor.close();
                    str6 = string2;
                    str2 = null;
                } else {
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (zzaVar3.zzarj != null && !zzaVar3.zzarj.isEmpty()) {
                        z = false;
                        if (z) {
                            zzje().setTransactionSuccessful();
                            zzje().endTransaction();
                            return false;
                        }
                        zzks zzksVar3 = zzaVar3.zzarh;
                        zzksVar3.zzatu = new zzkp[zzaVar3.zzarj.size()];
                        boolean zzau = this.zzacw.zzgh().zzau(zzksVar3.zzti);
                        int i7 = 0;
                        int i8 = 0;
                        boolean z5 = false;
                        long j5 = 0;
                        while (i7 < zzaVar3.zzarj.size()) {
                            zzkp zzkpVar = zzaVar3.zzarj.get(i7);
                            if (zzkv().zzn(zzaVar3.zzarh.zzti, zzkpVar.name)) {
                                boolean z6 = z5;
                                str3 = str8;
                                this.zzacw.zzgf().zziv().zze("Dropping blacklisted raw event. appId", zzfh.zzbl(zzaVar3.zzarh.zzti), this.zzacw.zzgb().zzbi(zzkpVar.name));
                                if (!zzkv().zzbx(zzaVar3.zzarh.zzti) && !zzkv().zzby(zzaVar3.zzarh.zzti)) {
                                    z4 = false;
                                    if (!z4 && !"_err".equals(zzkpVar.name)) {
                                        this.zzacw.zzgc().zza(zzaVar3.zzarh.zzti, 11, "_ev", zzkpVar.name, 0);
                                    }
                                    i5 = i7;
                                    z5 = z6;
                                }
                                z4 = true;
                                if (!z4) {
                                    this.zzacw.zzgc().zza(zzaVar3.zzarh.zzti, 11, "_ev", zzkpVar.name, 0);
                                }
                                i5 = i7;
                                z5 = z6;
                            } else {
                                str3 = str8;
                                boolean z7 = z5;
                                boolean zzo = zzkv().zzo(zzaVar3.zzarh.zzti, zzkpVar.name);
                                if (!zzo) {
                                    this.zzacw.zzgc();
                                    if (!zzkc.zzck(zzkpVar.name)) {
                                        i5 = i7;
                                        i6 = i8;
                                        j3 = j5;
                                        z5 = z7;
                                        if (zzau && "_e".equals(zzkpVar.name)) {
                                            if (zzkpVar.zzatm != null && zzkpVar.zzatm.length != 0) {
                                                zzjc();
                                                l = (Long) zzjy.zzb(zzkpVar, "_et");
                                                if (l != null) {
                                                    zziv = this.zzacw.zzgf().zziv();
                                                    str4 = "Engagement event does not include duration. appId";
                                                    zzbl = zzfh.zzbl(zzaVar3.zzarh.zzti);
                                                    zziv.zzg(str4, zzbl);
                                                } else {
                                                    j5 = j3 + l.longValue();
                                                    i8 = i6 + 1;
                                                    zzksVar3.zzatu[i6] = zzkpVar;
                                                }
                                            }
                                            zziv = this.zzacw.zzgf().zziv();
                                            str4 = "Engagement event does not contain any parameters. appId";
                                            zzbl = zzfh.zzbl(zzaVar3.zzarh.zzti);
                                            zziv.zzg(str4, zzbl);
                                        }
                                        j5 = j3;
                                        i8 = i6 + 1;
                                        zzksVar3.zzatu[i6] = zzkpVar;
                                    }
                                }
                                if (zzkpVar.zzatm == null) {
                                    zzkpVar.zzatm = new zzkq[0];
                                }
                                zzkq[] zzkqVarArr = zzkpVar.zzatm;
                                int length = zzkqVarArr.length;
                                i5 = i7;
                                boolean z8 = false;
                                int i9 = 0;
                                boolean z9 = false;
                                while (true) {
                                    i6 = i8;
                                    if (i9 >= length) {
                                        break;
                                    }
                                    int i10 = length;
                                    zzkq zzkqVar = zzkqVarArr[i9];
                                    zzkq[] zzkqVarArr2 = zzkqVarArr;
                                    if ("_c".equals(zzkqVar.name)) {
                                        zzkqVar.zzatq = 1L;
                                        z8 = true;
                                    } else if ("_r".equals(zzkqVar.name)) {
                                        zzkqVar.zzatq = 1L;
                                        z9 = true;
                                    }
                                    i9++;
                                    i8 = i6;
                                    length = i10;
                                    zzkqVarArr = zzkqVarArr2;
                                }
                                if (!z8 && zzo) {
                                    this.zzacw.zzgf().zziz().zzg("Marking event as conversion", this.zzacw.zzgb().zzbi(zzkpVar.name));
                                    zzkq[] zzkqVarArr3 = (zzkq[]) Arrays.copyOf(zzkpVar.zzatm, zzkpVar.zzatm.length + 1);
                                    zzkq zzkqVar2 = new zzkq();
                                    zzkqVar2.name = "_c";
                                    zzkqVar2.zzatq = 1L;
                                    zzkqVarArr3[zzkqVarArr3.length - 1] = zzkqVar2;
                                    zzkpVar.zzatm = zzkqVarArr3;
                                }
                                if (!z9) {
                                    this.zzacw.zzgf().zziz().zzg("Marking event as real-time", this.zzacw.zzgb().zzbi(zzkpVar.name));
                                    zzkq[] zzkqVarArr4 = (zzkq[]) Arrays.copyOf(zzkpVar.zzatm, zzkpVar.zzatm.length + 1);
                                    zzkq zzkqVar3 = new zzkq();
                                    zzkqVar3.name = "_r";
                                    zzkqVar3.zzatq = 1L;
                                    zzkqVarArr4[zzkqVarArr4.length - 1] = zzkqVar3;
                                    zzkpVar.zzatm = zzkqVarArr4;
                                }
                                j3 = j5;
                                if (zzje().zza(zzla(), zzaVar3.zzarh.zzti, false, false, false, false, true).zzafi > this.zzacw.zzgh().zzaq(zzaVar3.zzarh.zzti)) {
                                    int i11 = 0;
                                    while (true) {
                                        if (i11 >= zzkpVar.zzatm.length) {
                                            break;
                                        }
                                        if ("_r".equals(zzkpVar.zzatm[i11].name)) {
                                            int length2 = zzkpVar.zzatm.length - 1;
                                            zzkq[] zzkqVarArr5 = new zzkq[length2];
                                            if (i11 > 0) {
                                                System.arraycopy(zzkpVar.zzatm, 0, zzkqVarArr5, 0, i11);
                                            }
                                            if (i11 < length2) {
                                                System.arraycopy(zzkpVar.zzatm, i11 + 1, zzkqVarArr5, i11, length2 - i11);
                                            }
                                            zzkpVar.zzatm = zzkqVarArr5;
                                        } else {
                                            i11++;
                                        }
                                    }
                                    z5 = z7;
                                } else {
                                    z5 = true;
                                }
                                if (zzkc.zzcb(zzkpVar.name) && zzo && zzje().zza(zzla(), zzaVar3.zzarh.zzti, false, false, true, false, false).zzafg > this.zzacw.zzgh().zzb(zzaVar3.zzarh.zzti, zzey.zzahc)) {
                                    this.zzacw.zzgf().zziv().zzg("Too many conversions. Not logging as conversion. appId", zzfh.zzbl(zzaVar3.zzarh.zzti));
                                    zzkq zzkqVar4 = null;
                                    boolean z10 = false;
                                    for (zzkq zzkqVar5 : zzkpVar.zzatm) {
                                        if ("_c".equals(zzkqVar5.name)) {
                                            zzkqVar4 = zzkqVar5;
                                        } else if ("_err".equals(zzkqVar5.name)) {
                                            z10 = true;
                                        }
                                    }
                                    if (z10 && zzkqVar4 != null) {
                                        zzkpVar.zzatm = (zzkq[]) ArrayUtils.removeAll(zzkpVar.zzatm, zzkqVar4);
                                    } else if (zzkqVar4 != null) {
                                        zzkqVar4.name = "_err";
                                        zzkqVar4.zzatq = 10L;
                                    } else {
                                        this.zzacw.zzgf().zzis().zzg("Did not find conversion parameter. appId", zzfh.zzbl(zzaVar3.zzarh.zzti));
                                    }
                                }
                                if (zzau) {
                                    if (zzkpVar.zzatm != null) {
                                        zzjc();
                                        l = (Long) zzjy.zzb(zzkpVar, "_et");
                                        if (l != null) {
                                        }
                                    }
                                    zziv = this.zzacw.zzgf().zziv();
                                    str4 = "Engagement event does not contain any parameters. appId";
                                    zzbl = zzfh.zzbl(zzaVar3.zzarh.zzti);
                                    zziv.zzg(str4, zzbl);
                                }
                                j5 = j3;
                                i8 = i6 + 1;
                                zzksVar3.zzatu[i6] = zzkpVar;
                            }
                            i7 = i5 + 1;
                            str8 = str3;
                        }
                        String str9 = str8;
                        int i12 = i8;
                        boolean z11 = z5;
                        long j6 = j5;
                        if (i12 < zzaVar3.zzarj.size()) {
                            zzksVar3.zzatu = (zzkp[]) Arrays.copyOf(zzksVar3.zzatu, i12);
                        }
                        if (zzau) {
                            zzkb zzh = zzje().zzh(zzksVar3.zzti, str9);
                            if (zzh != null && zzh.value != null) {
                                zzkbVar = new zzkb(zzksVar3.zzti, DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_lte", this.zzacw.zzbt().currentTimeMillis(), Long.valueOf(((Long) zzh.value).longValue() + j6));
                                zzku zzkuVar = new zzku();
                                zzkuVar.name = str9;
                                zzkuVar.zzauz = Long.valueOf(this.zzacw.zzbt().currentTimeMillis());
                                zzkuVar.zzatq = (Long) zzkbVar.value;
                                i4 = 0;
                                while (true) {
                                    if (i4 < zzksVar3.zzatv.length) {
                                        z3 = false;
                                        break;
                                    }
                                    if (str9.equals(zzksVar3.zzatv[i4].name)) {
                                        zzksVar3.zzatv[i4] = zzkuVar;
                                        z3 = true;
                                        break;
                                    }
                                    i4++;
                                }
                                if (!z3) {
                                    zzksVar3.zzatv = (zzku[]) Arrays.copyOf(zzksVar3.zzatv, zzksVar3.zzatv.length + 1);
                                    zzksVar3.zzatv[zzaVar3.zzarh.zzatv.length - 1] = zzkuVar;
                                }
                                if (j6 > 0) {
                                    zzje().zza(zzkbVar);
                                    this.zzacw.zzgf().zziy().zzg("Updated lifetime engagement user property with value. Value", zzkbVar.value);
                                }
                            }
                            zzkbVar = new zzkb(zzksVar3.zzti, DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_lte", this.zzacw.zzbt().currentTimeMillis(), Long.valueOf(j6));
                            zzku zzkuVar2 = new zzku();
                            zzkuVar2.name = str9;
                            zzkuVar2.zzauz = Long.valueOf(this.zzacw.zzbt().currentTimeMillis());
                            zzkuVar2.zzatq = (Long) zzkbVar.value;
                            i4 = 0;
                            while (true) {
                                if (i4 < zzksVar3.zzatv.length) {
                                }
                                i4++;
                            }
                            if (!z3) {
                            }
                            if (j6 > 0) {
                            }
                        }
                        zzksVar3.zzaum = zza(zzksVar3.zzti, zzksVar3.zzatv, zzksVar3.zzatu);
                        if (this.zzacw.zzgh().zzat(zzaVar3.zzarh.zzti)) {
                            HashMap hashMap = new HashMap();
                            zzkp[] zzkpVarArr2 = new zzkp[zzksVar3.zzatu.length];
                            SecureRandom zzll = this.zzacw.zzgc().zzll();
                            zzkp[] zzkpVarArr3 = zzksVar3.zzatu;
                            int length3 = zzkpVarArr3.length;
                            int i13 = 0;
                            int i14 = 0;
                            while (i13 < length3) {
                                zzkp zzkpVar2 = zzkpVarArr3[i13];
                                if (zzkpVar2.name.equals("_ep")) {
                                    zzjc();
                                    String str10 = (String) zzjy.zzb(zzkpVar2, "_en");
                                    zzes zzesVar = (zzes) hashMap.get(str10);
                                    if (zzesVar == null) {
                                        zzkpVarArr = zzkpVarArr3;
                                        zzesVar = zzje().zzf(zzaVar3.zzarh.zzti, str10);
                                        hashMap.put(str10, zzesVar);
                                    } else {
                                        zzkpVarArr = zzkpVarArr3;
                                    }
                                    if (zzesVar.zzafw == null) {
                                        if (zzesVar.zzafx.longValue() > 1) {
                                            zzjc();
                                            zzkpVar2.zzatm = zzjy.zza(zzkpVar2.zzatm, "_sr", zzesVar.zzafx);
                                        }
                                        if (zzesVar.zzafy != null && zzesVar.zzafy.booleanValue()) {
                                            zzjc();
                                            zzkpVar2.zzatm = zzjy.zza(zzkpVar2.zzatm, "_efs", (Object) 1L);
                                        }
                                        zzkpVarArr2[i14] = zzkpVar2;
                                        zzaVar2 = zzaVar3;
                                        zzksVar2 = zzksVar3;
                                        secureRandom = zzll;
                                        i14++;
                                    } else {
                                        zzaVar2 = zzaVar3;
                                        zzksVar2 = zzksVar3;
                                        secureRandom = zzll;
                                    }
                                    i = length3;
                                } else {
                                    zzkpVarArr = zzkpVarArr3;
                                    Long l2 = 1L;
                                    if (!TextUtils.isEmpty("_dbg") && l2 != null) {
                                        zzkq[] zzkqVarArr6 = zzkpVar2.zzatm;
                                        i = length3;
                                        int length4 = zzkqVarArr6.length;
                                        zzksVar2 = zzksVar3;
                                        int i15 = 0;
                                        while (true) {
                                            if (i15 >= length4) {
                                                break;
                                            }
                                            int i16 = length4;
                                            zzkq zzkqVar6 = zzkqVarArr6[i15];
                                            zzkq[] zzkqVarArr7 = zzkqVarArr6;
                                            if (!"_dbg".equals(zzkqVar6.name)) {
                                                i15++;
                                                length4 = i16;
                                                zzkqVarArr6 = zzkqVarArr7;
                                            } else if (((l2 instanceof Long) && l2.equals(zzkqVar6.zzatq)) || (((l2 instanceof String) && l2.equals(zzkqVar6.zzajo)) || ((l2 instanceof Double) && l2.equals(zzkqVar6.zzaro)))) {
                                                z2 = true;
                                            }
                                        }
                                        z2 = false;
                                        zzp = z2 ? zzkv().zzp(zzaVar3.zzarh.zzti, zzkpVar2.name) : 1;
                                        if (zzp > 0) {
                                            this.zzacw.zzgf().zziv().zze("Sample rate must be positive. event, rate", zzkpVar2.name, Integer.valueOf(zzp));
                                            i3 = i14 + 1;
                                            zzkpVarArr2[i14] = zzkpVar2;
                                        } else {
                                            zzes zzesVar2 = (zzes) hashMap.get(zzkpVar2.name);
                                            if (zzesVar2 == null && (zzesVar2 = zzje().zzf(zzaVar3.zzarh.zzti, zzkpVar2.name)) == null) {
                                                this.zzacw.zzgf().zziv().zze("Event being bundled has no eventAggregate. appId, eventName", zzaVar3.zzarh.zzti, zzkpVar2.name);
                                                zzesVar2 = new zzes(zzaVar3.zzarh.zzti, zzkpVar2.name, 1L, 1L, zzkpVar2.zzatn.longValue(), 0L, null, null, null);
                                            }
                                            zzjc();
                                            Long l3 = (Long) zzjy.zzb(zzkpVar2, "_eid");
                                            Boolean valueOf = Boolean.valueOf(l3 != null);
                                            if (zzp == 1) {
                                                i3 = i14 + 1;
                                                zzkpVarArr2[i14] = zzkpVar2;
                                                if (valueOf.booleanValue() && (zzesVar2.zzafw != null || zzesVar2.zzafx != null || zzesVar2.zzafy != null)) {
                                                    hashMap.put(zzkpVar2.name, zzesVar2.zza(null, null, null));
                                                }
                                            } else if (zzll.nextInt(zzp) == 0) {
                                                zzjc();
                                                zzaVar2 = zzaVar3;
                                                long j7 = zzp;
                                                zzkpVar2.zzatm = zzjy.zza(zzkpVar2.zzatm, "_sr", Long.valueOf(j7));
                                                int i17 = i14 + 1;
                                                zzkpVarArr2[i14] = zzkpVar2;
                                                if (valueOf.booleanValue()) {
                                                    zzesVar2 = zzesVar2.zza(null, Long.valueOf(j7), null);
                                                }
                                                hashMap.put(zzkpVar2.name, zzesVar2.zzad(zzkpVar2.zzatn.longValue()));
                                                secureRandom = zzll;
                                                i14 = i17;
                                            } else {
                                                zzaVar2 = zzaVar3;
                                                i2 = i13;
                                                secureRandom = zzll;
                                                if (Math.abs(zzkpVar2.zzatn.longValue() - zzesVar2.zzafv) >= 86400000) {
                                                    zzjc();
                                                    zzkpVar2.zzatm = zzjy.zza(zzkpVar2.zzatm, "_efs", (Object) 1L);
                                                    zzjc();
                                                    long j8 = zzp;
                                                    zzkpVar2.zzatm = zzjy.zza(zzkpVar2.zzatm, "_sr", Long.valueOf(j8));
                                                    int i18 = i14 + 1;
                                                    zzkpVarArr2[i14] = zzkpVar2;
                                                    if (valueOf.booleanValue()) {
                                                        zzesVar2 = zzesVar2.zza(null, Long.valueOf(j8), true);
                                                    }
                                                    hashMap.put(zzkpVar2.name, zzesVar2.zzad(zzkpVar2.zzatn.longValue()));
                                                    i14 = i18;
                                                } else if (valueOf.booleanValue()) {
                                                    hashMap.put(zzkpVar2.name, zzesVar2.zza(l3, null, null));
                                                }
                                                i13 = i2 + 1;
                                                zzaVar3 = zzaVar2;
                                                zzkpVarArr3 = zzkpVarArr;
                                                length3 = i;
                                                zzksVar3 = zzksVar2;
                                                zzll = secureRandom;
                                            }
                                        }
                                        zzaVar2 = zzaVar3;
                                        i14 = i3;
                                        secureRandom = zzll;
                                    }
                                    zzksVar2 = zzksVar3;
                                    i = length3;
                                    z2 = false;
                                    if (z2) {
                                    }
                                    if (zzp > 0) {
                                    }
                                    zzaVar2 = zzaVar3;
                                    i14 = i3;
                                    secureRandom = zzll;
                                }
                                i2 = i13;
                                i13 = i2 + 1;
                                zzaVar3 = zzaVar2;
                                zzkpVarArr3 = zzkpVarArr;
                                length3 = i;
                                zzksVar3 = zzksVar2;
                                zzll = secureRandom;
                            }
                            zzaVar = zzaVar3;
                            zzksVar = zzksVar3;
                            if (i14 < zzksVar.zzatu.length) {
                                zzksVar.zzatu = (zzkp[]) Arrays.copyOf(zzkpVarArr2, i14);
                            }
                            Iterator it = hashMap.entrySet().iterator();
                            while (it.hasNext()) {
                                zzje().zza((zzes) ((Map.Entry) it.next()).getValue());
                            }
                        } else {
                            zzaVar = zzaVar3;
                            zzksVar = zzksVar3;
                        }
                        zzksVar.zzatx = Long.MAX_VALUE;
                        zzksVar.zzaty = Long.MIN_VALUE;
                        for (int i19 = 0; i19 < zzksVar.zzatu.length; i19++) {
                            zzkp zzkpVar3 = zzksVar.zzatu[i19];
                            if (zzkpVar3.zzatn.longValue() < zzksVar.zzatx.longValue()) {
                                zzksVar.zzatx = zzkpVar3.zzatn;
                            }
                            if (zzkpVar3.zzatn.longValue() > zzksVar.zzaty.longValue()) {
                                zzksVar.zzaty = zzkpVar3.zzatn;
                            }
                        }
                        zza zzaVar4 = zzaVar;
                        String str11 = zzaVar4.zzarh.zzti;
                        zzdy zzbb = zzje().zzbb(str11);
                        if (zzbb == null) {
                            this.zzacw.zzgf().zzis().zzg("Bundling raw events w/o app info. appId", zzfh.zzbl(zzaVar4.zzarh.zzti));
                        } else if (zzksVar.zzatu.length > 0) {
                            long zzgn = zzbb.zzgn();
                            zzksVar.zzaua = zzgn != 0 ? Long.valueOf(zzgn) : null;
                            long zzgm = zzbb.zzgm();
                            if (zzgm != 0) {
                                zzgn = zzgm;
                            }
                            zzksVar.zzatz = zzgn != 0 ? Long.valueOf(zzgn) : null;
                            zzbb.zzgv();
                            zzksVar.zzauk = Integer.valueOf((int) zzbb.zzgs());
                            zzbb.zzm(zzksVar.zzatx.longValue());
                            zzbb.zzn(zzksVar.zzaty.longValue());
                            zzksVar.zzaek = zzbb.zzhd();
                            zzje().zza(zzbb);
                        }
                        if (zzksVar.zzatu.length > 0) {
                            this.zzacw.zzgi();
                            zzkm zzbt = zzkv().zzbt(zzaVar4.zzarh.zzti);
                            if (zzbt != null && zzbt.zzatb != null) {
                                j2 = zzbt.zzatb;
                                zzksVar.zzaur = j2;
                                zzje().zza(zzksVar, z11);
                            }
                            if (TextUtils.isEmpty(zzaVar4.zzarh.zzadm)) {
                                j2 = -1L;
                                zzksVar.zzaur = j2;
                                zzje().zza(zzksVar, z11);
                            } else {
                                this.zzacw.zzgf().zziv().zzg("Did not find measurement config or missing version info. appId", zzfh.zzbl(zzaVar4.zzarh.zzti));
                                zzje().zza(zzksVar, z11);
                            }
                        }
                        zzej zzje2 = zzje();
                        List<Long> list = zzaVar4.zzari;
                        Preconditions.checkNotNull(list);
                        zzje2.zzab();
                        zzje2.zzch();
                        StringBuilder sb3 = new StringBuilder("rowid in (");
                        for (int i20 = 0; i20 < list.size(); i20++) {
                            if (i20 != 0) {
                                sb3.append(",");
                            }
                            sb3.append(list.get(i20).longValue());
                        }
                        sb3.append(")");
                        int delete = zzje2.getWritableDatabase().delete("raw_events", sb3.toString(), null);
                        if (delete != list.size()) {
                            zzje2.zzgf().zzis().zze("Deleted fewer rows from raw events table than expected", Integer.valueOf(delete), Integer.valueOf(list.size()));
                        }
                        zzej zzje3 = zzje();
                        try {
                            zzje3.getWritableDatabase().execSQL("delete from raw_events_metadata where app_id=? and metadata_fingerprint not in (select distinct metadata_fingerprint from raw_events where app_id=?)", new String[]{str11, str11});
                        } catch (SQLiteException e4) {
                            zzje3.zzgf().zzis().zze("Failed to remove unused event metadata. appId", zzfh.zzbl(str11), e4);
                        }
                        zzje().setTransactionSuccessful();
                        zzje().endTransaction();
                        return true;
                    }
                    z = true;
                    if (z) {
                    }
                }
            }
            cursor = writableDatabase.query("raw_events_metadata", new String[]{"metadata"}, "app_id = ? and metadata_fingerprint = ?", new String[]{str2, str6}, null, null, "rowid", "2");
            if (cursor.moveToFirst()) {
                byte[] blob = cursor.getBlob(0);
                zzabx zza2 = zzabx.zza(blob, 0, blob.length);
                zzks zzksVar4 = new zzks();
                try {
                    zzksVar4.zzb(zza2);
                    if (cursor.moveToNext()) {
                        zzje.zzgf().zziv().zzg("Get multiple raw event metadata records, expected one. appId", zzfh.zzbl(str2));
                    }
                    cursor.close();
                    zzaVar3.zzb(zzksVar4);
                    if (j4 != -1) {
                        str7 = "app_id = ? and metadata_fingerprint = ? and rowid <= ?";
                        strArr = new String[]{str2, str6, String.valueOf(j4)};
                    } else {
                        str7 = "app_id = ? and metadata_fingerprint = ?";
                        strArr = new String[]{str2, str6};
                    }
                    cursor = writableDatabase.query("raw_events", new String[]{"rowid", "name", AppMeasurement.Param.TIMESTAMP, "data"}, str7, strArr, null, null, "rowid", null);
                } catch (IOException e5) {
                    zzje.zzgf().zzis().zze("Data loss. Failed to merge raw event metadata. appId", zzfh.zzbl(str2), e5);
                }
                if (!cursor.moveToFirst()) {
                    zzje.zzgf().zziv().zzg("Raw event data disappeared while in transaction. appId", zzfh.zzbl(str2));
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (zzaVar3.zzarj != null) {
                    }
                    z = true;
                    if (z) {
                    }
                }
                do {
                    long j9 = cursor.getLong(0);
                    byte[] blob2 = cursor.getBlob(3);
                    zzabx zza3 = zzabx.zza(blob2, 0, blob2.length);
                    zzkp zzkpVar4 = new zzkp();
                    try {
                        zzkpVar4.zzb(zza3);
                        zzkpVar4.name = cursor.getString(1);
                        zzkpVar4.zzatn = Long.valueOf(cursor.getLong(2));
                    } catch (IOException e6) {
                        zzje.zzgf().zzis().zze("Data loss. Failed to merge raw event. appId", zzfh.zzbl(str2), e6);
                    }
                    if (!zzaVar3.zza(j9, zzkpVar4)) {
                        if (cursor != null) {
                            cursor.close();
                        }
                        if (zzaVar3.zzarj != null) {
                        }
                        z = true;
                        if (z) {
                        }
                    }
                } while (cursor.moveToNext());
                if (cursor != null) {
                    cursor.close();
                }
                if (zzaVar3.zzarj != null) {
                }
                z = true;
                if (z) {
                }
            } else {
                zzje.zzgf().zzis().zzg("Raw event metadata record is missing. appId", zzfh.zzbl(str2));
                if (cursor != null) {
                    cursor.close();
                }
                if (zzaVar3.zzarj != null) {
                    z = false;
                    if (z) {
                    }
                }
                z = true;
                if (z) {
                }
            }
        } catch (Throwable th4) {
            zzje().endTransaction();
            throw th4;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0151  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x014e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final zzdy zzg(zzdz zzdzVar) {
        boolean z;
        zzab();
        zzkz();
        Preconditions.checkNotNull(zzdzVar);
        Preconditions.checkNotEmpty(zzdzVar.packageName);
        zzdy zzbb = zzje().zzbb(zzdzVar.packageName);
        String zzbo = this.zzacw.zzgg().zzbo(zzdzVar.packageName);
        boolean z2 = true;
        if (zzbb == null) {
            zzbb = new zzdy(this.zzacw, zzdzVar.packageName);
            zzbb.zzak(this.zzacw.zzfw().zzio());
            zzbb.zzam(zzbo);
        } else {
            if (zzbo.equals(zzbb.zzgk())) {
                z = false;
                if (!TextUtils.isEmpty(zzdzVar.zzadm) && !zzdzVar.zzadm.equals(zzbb.getGmpAppId())) {
                    zzbb.zzal(zzdzVar.zzadm);
                    z = true;
                }
                if (!TextUtils.isEmpty(zzdzVar.zzado) && !zzdzVar.zzado.equals(zzbb.zzgl())) {
                    zzbb.zzan(zzdzVar.zzado);
                    z = true;
                }
                if (zzdzVar.zzadu != 0 && zzdzVar.zzadu != zzbb.zzgq()) {
                    zzbb.zzp(zzdzVar.zzadu);
                    z = true;
                }
                if (!TextUtils.isEmpty(zzdzVar.zzth) && !zzdzVar.zzth.equals(zzbb.zzag())) {
                    zzbb.setAppVersion(zzdzVar.zzth);
                    z = true;
                }
                if (zzdzVar.zzads != zzbb.zzgo()) {
                    zzbb.zzo(zzdzVar.zzads);
                    z = true;
                }
                if (zzdzVar.zzadt != null && !zzdzVar.zzadt.equals(zzbb.zzgp())) {
                    zzbb.zzao(zzdzVar.zzadt);
                    z = true;
                }
                if (zzdzVar.zzadv != zzbb.zzgr()) {
                    zzbb.zzq(zzdzVar.zzadv);
                    z = true;
                }
                if (zzdzVar.zzadw != zzbb.isMeasurementEnabled()) {
                    zzbb.setMeasurementEnabled(zzdzVar.zzadw);
                    z = true;
                }
                if (!TextUtils.isEmpty(zzdzVar.zzaek) && !zzdzVar.zzaek.equals(zzbb.zzhc())) {
                    zzbb.zzap(zzdzVar.zzaek);
                    z = true;
                }
                if (zzdzVar.zzadx != zzbb.zzhe()) {
                    zzbb.zzaa(zzdzVar.zzadx);
                    z = true;
                }
                if (zzdzVar.zzady != zzbb.zzhf()) {
                    zzbb.zzd(zzdzVar.zzady);
                    z = true;
                }
                if (zzdzVar.zzadz == zzbb.zzhg()) {
                    zzbb.zze(zzdzVar.zzadz);
                } else {
                    z2 = z;
                }
                if (z2) {
                    zzje().zza(zzbb);
                }
                return zzbb;
            }
            zzbb.zzam(zzbo);
            zzbb.zzak(this.zzacw.zzfw().zzio());
        }
        z = true;
        if (!TextUtils.isEmpty(zzdzVar.zzadm)) {
            zzbb.zzal(zzdzVar.zzadm);
            z = true;
        }
        if (!TextUtils.isEmpty(zzdzVar.zzado)) {
            zzbb.zzan(zzdzVar.zzado);
            z = true;
        }
        if (zzdzVar.zzadu != 0) {
            zzbb.zzp(zzdzVar.zzadu);
            z = true;
        }
        if (!TextUtils.isEmpty(zzdzVar.zzth)) {
            zzbb.setAppVersion(zzdzVar.zzth);
            z = true;
        }
        if (zzdzVar.zzads != zzbb.zzgo()) {
        }
        if (zzdzVar.zzadt != null) {
            zzbb.zzao(zzdzVar.zzadt);
            z = true;
        }
        if (zzdzVar.zzadv != zzbb.zzgr()) {
        }
        if (zzdzVar.zzadw != zzbb.isMeasurementEnabled()) {
        }
        if (!TextUtils.isEmpty(zzdzVar.zzaek)) {
            zzbb.zzap(zzdzVar.zzaek);
            z = true;
        }
        if (zzdzVar.zzadx != zzbb.zzhe()) {
        }
        if (zzdzVar.zzady != zzbb.zzhf()) {
        }
        if (zzdzVar.zzadz == zzbb.zzhg()) {
        }
        if (z2) {
        }
        return zzbb;
    }

    public static zzjs zzg(Context context) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(context.getApplicationContext());
        if (zzaqj == null) {
            synchronized (zzjs.class) {
                if (zzaqj == null) {
                    zzaqj = new zzjs(new zzjx(context));
                }
            }
        }
        return zzaqj;
    }

    private final zzgg zzkv() {
        zza(this.zzaqk);
        return this.zzaqk;
    }

    private final zzfq zzkx() {
        zzfq zzfqVar = this.zzaqn;
        if (zzfqVar != null) {
            return zzfqVar;
        }
        throw new IllegalStateException("Network broadcast receiver not created");
    }

    private final zzjo zzky() {
        zza(this.zzaqo);
        return this.zzaqo;
    }

    private final long zzla() {
        long currentTimeMillis = this.zzacw.zzbt().currentTimeMillis();
        zzfs zzgg = this.zzacw.zzgg();
        zzgg.zzch();
        zzgg.zzab();
        long j = zzgg.zzakh.get();
        if (j == 0) {
            j = 1 + zzgg.zzgc().zzll().nextInt(86400000);
            zzgg.zzakh.set(j);
        }
        return ((((currentTimeMillis + j) / 1000) / 60) / 60) / 24;
    }

    private final boolean zzlc() {
        zzab();
        zzkz();
        return zzje().zzhw() || !TextUtils.isEmpty(zzje().zzhr());
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0195  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void zzld() {
        zzey.zza<Long> zzaVar;
        long j;
        zzab();
        zzkz();
        if (!zzlh()) {
            return;
        }
        if (this.zzaqs > 0) {
            long abs = 3600000 - Math.abs(this.zzacw.zzbt().elapsedRealtime() - this.zzaqs);
            if (abs > 0) {
                this.zzacw.zzgf().zziz().zzg("Upload has been suspended. Will update scheduling later in approximately ms", Long.valueOf(abs));
                zzkx().unregister();
                zzky().cancel();
                return;
            }
            this.zzaqs = 0L;
        }
        if (!this.zzacw.zzkd() || !zzlc()) {
            this.zzacw.zzgf().zziz().log("Nothing to upload or uploading impossible");
            zzkx().unregister();
            zzky().cancel();
            return;
        }
        long currentTimeMillis = this.zzacw.zzbt().currentTimeMillis();
        long max = Math.max(0L, zzey.zzahp.get().longValue());
        boolean z = zzje().zzhx() || zzje().zzhs();
        if (z) {
            String zzhn = this.zzacw.zzgh().zzhn();
            zzaVar = (TextUtils.isEmpty(zzhn) || ".none.".equals(zzhn)) ? zzey.zzahj : zzey.zzahk;
        } else {
            zzaVar = zzey.zzahi;
        }
        long max2 = Math.max(0L, zzaVar.get().longValue());
        long j2 = this.zzacw.zzgg().zzakd.get();
        long j3 = this.zzacw.zzgg().zzake.get();
        long max3 = Math.max(zzje().zzhu(), zzje().zzhv());
        if (max3 != 0) {
            long abs2 = currentTimeMillis - Math.abs(max3 - currentTimeMillis);
            long abs3 = currentTimeMillis - Math.abs(j2 - currentTimeMillis);
            long abs4 = currentTimeMillis - Math.abs(j3 - currentTimeMillis);
            long max4 = Math.max(abs3, abs4);
            j = abs2 + max;
            if (z && max4 > 0) {
                j = Math.min(abs2, max4) + max2;
            }
            if (!this.zzacw.zzgc().zza(max4, max2)) {
                j = max4 + max2;
            }
            if (abs4 != 0 && abs4 >= abs2) {
                for (int i = 0; i < Math.min(20, Math.max(0, zzey.zzahr.get().intValue())); i++) {
                    j += Math.max(0L, zzey.zzahq.get().longValue()) * (1 << i);
                    if (j > abs4) {
                        break;
                    }
                }
            }
            if (j != 0) {
                this.zzacw.zzgf().zziz().log("Next upload time is 0");
                zzkx().unregister();
                zzky().cancel();
                return;
            }
            if (!zzkw().zzex()) {
                this.zzacw.zzgf().zziz().log("No network");
                zzkx().zzeu();
                zzky().cancel();
                return;
            }
            long j4 = this.zzacw.zzgg().zzakf.get();
            long max5 = Math.max(0L, zzey.zzahg.get().longValue());
            if (!this.zzacw.zzgc().zza(j4, max5)) {
                j = Math.max(j, j4 + max5);
            }
            zzkx().unregister();
            long currentTimeMillis2 = j - this.zzacw.zzbt().currentTimeMillis();
            if (currentTimeMillis2 <= 0) {
                currentTimeMillis2 = Math.max(0L, zzey.zzahl.get().longValue());
                this.zzacw.zzgg().zzakd.set(this.zzacw.zzbt().currentTimeMillis());
            }
            this.zzacw.zzgf().zziz().zzg("Upload scheduled in approximately ms", Long.valueOf(currentTimeMillis2));
            zzky().zzh(currentTimeMillis2);
            return;
        }
        j = 0;
        if (j != 0) {
        }
    }

    private final void zzle() {
        zzab();
        if (this.zzaqw || this.zzaqx || this.zzaqy) {
            this.zzacw.zzgf().zziz().zzd("Not stopping services. fetch, network, upload", Boolean.valueOf(this.zzaqw), Boolean.valueOf(this.zzaqx), Boolean.valueOf(this.zzaqy));
            return;
        }
        this.zzacw.zzgf().zziz().log("Stopping uploading service(s)");
        List<Runnable> list = this.zzaqt;
        if (list == null) {
            return;
        }
        Iterator<Runnable> it = list.iterator();
        while (it.hasNext()) {
            it.next().run();
        }
        this.zzaqt.clear();
    }

    private final boolean zzlf() {
        zzfj zzis;
        String str;
        zzab();
        try {
            FileChannel channel = new RandomAccessFile(new File(this.zzacw.getContext().getFilesDir(), "google_app_measurement.db"), "rw").getChannel();
            this.zzara = channel;
            FileLock tryLock = channel.tryLock();
            this.zzaqz = tryLock;
            if (tryLock != null) {
                this.zzacw.zzgf().zziz().log("Storage concurrent access okay");
                return true;
            }
            this.zzacw.zzgf().zzis().log("Storage concurrent data access panic");
            return false;
        } catch (FileNotFoundException e) {
            e = e;
            zzis = this.zzacw.zzgf().zzis();
            str = "Failed to acquire storage lock";
            zzis.zzg(str, e);
            return false;
        } catch (IOException e2) {
            e = e2;
            zzis = this.zzacw.zzgf().zzis();
            str = "Failed to access storage lock file";
            zzis.zzg(str, e);
            return false;
        }
    }

    private final boolean zzlh() {
        zzab();
        zzkz();
        return this.zzaqr;
    }

    @Override // com.google.android.gms.internal.measurement.zzed
    public final Context getContext() {
        return this.zzacw.getContext();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void start() {
        this.zzacw.zzge().zzab();
        zzje().zzht();
        if (this.zzacw.zzgg().zzakd.get() == 0) {
            this.zzacw.zzgg().zzakd.set(this.zzacw.zzbt().currentTimeMillis());
        }
        zzld();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0156, code lost:
    
        r9.zzacw.zzgg().zzakf.set(r9.zzacw.zzbt().currentTimeMillis());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void zza(int i, Throwable th, byte[] bArr, String str) {
        zzej zzje;
        long longValue;
        zzab();
        zzkz();
        if (bArr == null) {
            try {
                bArr = new byte[0];
            } finally {
                this.zzaqx = false;
                zzle();
            }
        }
        List<Long> list = this.zzarb;
        this.zzarb = null;
        boolean z = true;
        if ((i == 200 || i == 204) && th == null) {
            try {
                this.zzacw.zzgg().zzakd.set(this.zzacw.zzbt().currentTimeMillis());
                this.zzacw.zzgg().zzake.set(0L);
                zzld();
                this.zzacw.zzgf().zziz().zze("Successful upload. Got network response. code, size", Integer.valueOf(i), Integer.valueOf(bArr.length));
                zzje().beginTransaction();
                try {
                    for (Long l : list) {
                        try {
                            zzje = zzje();
                            longValue = l.longValue();
                            zzje.zzab();
                            zzje.zzch();
                            try {
                            } catch (SQLiteException e) {
                                zzje.zzgf().zzis().zzg("Failed to delete a bundle in a queue table", e);
                                throw e;
                                break;
                            }
                        } catch (SQLiteException e2) {
                            List<Long> list2 = this.zzarc;
                            if (list2 == null || !list2.contains(l)) {
                                throw e2;
                            }
                        }
                        if (zzje.getWritableDatabase().delete("queue", "rowid=?", new String[]{String.valueOf(longValue)}) != 1) {
                            throw new SQLiteException("Deleted fewer rows from queue than expected");
                            break;
                        }
                    }
                    zzje().setTransactionSuccessful();
                    zzje().endTransaction();
                    this.zzarc = null;
                    if (zzkw().zzex() && zzlc()) {
                        zzlb();
                    } else {
                        this.zzard = -1L;
                        zzld();
                    }
                    this.zzaqs = 0L;
                } catch (Throwable th2) {
                    zzje().endTransaction();
                    throw th2;
                }
            } catch (SQLiteException e3) {
                this.zzacw.zzgf().zzis().zzg("Database error while trying to delete uploaded bundles", e3);
                this.zzaqs = this.zzacw.zzbt().elapsedRealtime();
                this.zzacw.zzgf().zziz().zzg("Disable upload, time", Long.valueOf(this.zzaqs));
            }
        } else {
            this.zzacw.zzgf().zziz().zze("Network upload failed. Will retry later. code, error", Integer.valueOf(i), th);
            this.zzacw.zzgg().zzake.set(this.zzacw.zzbt().currentTimeMillis());
            if (i != 503 && i != 429) {
                z = false;
            }
            if (this.zzacw.zzgh().zzaw(str)) {
                zzje().zzc(list);
            }
            zzld();
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r10v15, resolved type: java.lang.Long */
    /* JADX DEBUG: Multi-variable search result rejected for r10v16, resolved type: java.lang.Long */
    /* JADX DEBUG: Multi-variable search result rejected for r10v3, resolved type: java.lang.Long */
    /* JADX DEBUG: Multi-variable search result rejected for r10v4, resolved type: java.lang.Long */
    /* JADX DEBUG: Multi-variable search result rejected for r10v5, resolved type: java.lang.Long */
    /* JADX DEBUG: Multi-variable search result rejected for r10v6, resolved type: java.lang.Long */
    /* JADX WARN: Multi-variable type inference failed */
    public final byte[] zza(zzew zzewVar, String str) {
        zzkb zzkbVar;
        zzks zzksVar;
        zzkr zzkrVar;
        zzdy zzdyVar;
        byte[] bArr;
        Bundle bundle;
        long j;
        zzfj zziv;
        String str2;
        Object zzbl;
        zzkz();
        zzab();
        this.zzacw.zzfr();
        Preconditions.checkNotNull(zzewVar);
        Preconditions.checkNotEmpty(str);
        zzkr zzkrVar2 = new zzkr();
        zzje().beginTransaction();
        try {
            zzdy zzbb = zzje().zzbb(str);
            if (zzbb == null) {
                this.zzacw.zzgf().zziy().zzg("Log and bundle not available. package_name", str);
            } else {
                if (zzbb.isMeasurementEnabled()) {
                    if (("_iap".equals(zzewVar.name) || FirebaseAnalytics.Event.ECOMMERCE_PURCHASE.equals(zzewVar.name)) && !zza(str, zzewVar)) {
                        this.zzacw.zzgf().zziv().zzg("Failed to handle purchase event at single event bundle creation. appId", zzfh.zzbl(str));
                    }
                    boolean zzau = this.zzacw.zzgh().zzau(str);
                    Long l = 0L;
                    if (zzau && "_e".equals(zzewVar.name)) {
                        if (zzewVar.zzafr != null && zzewVar.zzafr.size() != 0) {
                            if (zzewVar.zzafr.getLong("_et") == null) {
                                zziv = this.zzacw.zzgf().zziv();
                                str2 = "The engagement event does not include duration. appId";
                                zzbl = zzfh.zzbl(str);
                                zziv.zzg(str2, zzbl);
                            } else {
                                l = zzewVar.zzafr.getLong("_et");
                            }
                        }
                        zziv = this.zzacw.zzgf().zziv();
                        str2 = "The engagement event does not contain any parameters. appId";
                        zzbl = zzfh.zzbl(str);
                        zziv.zzg(str2, zzbl);
                    }
                    zzks zzksVar2 = new zzks();
                    zzkrVar2.zzatr = new zzks[]{zzksVar2};
                    zzksVar2.zzatt = 1;
                    zzksVar2.zzaub = "android";
                    zzksVar2.zzti = zzbb.zzah();
                    zzksVar2.zzadt = zzbb.zzgp();
                    zzksVar2.zzth = zzbb.zzag();
                    long zzgo = zzbb.zzgo();
                    zzksVar2.zzaun = zzgo == -2147483648L ? null : Integer.valueOf((int) zzgo);
                    zzksVar2.zzauf = Long.valueOf(zzbb.zzgq());
                    zzksVar2.zzadm = zzbb.getGmpAppId();
                    zzksVar2.zzauj = Long.valueOf(zzbb.zzgr());
                    if (this.zzacw.isEnabled() && zzeg.zzho() && this.zzacw.zzgh().zzas(zzksVar2.zzti)) {
                        zzksVar2.zzaut = null;
                    }
                    Pair<String, Boolean> zzbn = this.zzacw.zzgg().zzbn(zzbb.zzah());
                    if (zzbb.zzhf() && zzbn != null && !TextUtils.isEmpty((CharSequence) zzbn.first)) {
                        zzksVar2.zzauh = (String) zzbn.first;
                        zzksVar2.zzaui = (Boolean) zzbn.second;
                    }
                    this.zzacw.zzfx().zzch();
                    zzksVar2.zzaud = Build.MODEL;
                    this.zzacw.zzfx().zzch();
                    zzksVar2.zzauc = Build.VERSION.RELEASE;
                    zzksVar2.zzaue = Integer.valueOf((int) this.zzacw.zzfx().zzig());
                    zzksVar2.zzafo = this.zzacw.zzfx().zzih();
                    zzksVar2.zzadl = zzbb.getAppInstanceId();
                    zzksVar2.zzado = zzbb.zzgl();
                    List<zzkb> zzba = zzje().zzba(zzbb.zzah());
                    zzksVar2.zzatv = new zzku[zzba.size()];
                    if (zzau) {
                        zzkbVar = zzje().zzh(zzksVar2.zzti, "_lte");
                        if (zzkbVar != null && zzkbVar.value != null) {
                            if (l.longValue() > 0) {
                                zzkbVar = new zzkb(zzksVar2.zzti, DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_lte", this.zzacw.zzbt().currentTimeMillis(), Long.valueOf(((Long) zzkbVar.value).longValue() + l.longValue()));
                            }
                        }
                        zzkbVar = new zzkb(zzksVar2.zzti, DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_lte", this.zzacw.zzbt().currentTimeMillis(), l);
                    } else {
                        zzkbVar = null;
                    }
                    int i = 0;
                    zzku zzkuVar = null;
                    while (i < zzba.size()) {
                        zzku zzkuVar2 = new zzku();
                        zzksVar2.zzatv[i] = zzkuVar2;
                        zzkuVar2.name = zzba.get(i).name;
                        zzdy zzdyVar2 = zzbb;
                        zzkr zzkrVar3 = zzkrVar2;
                        zzkuVar2.zzauz = Long.valueOf(zzba.get(i).zzarl);
                        zzjc().zza(zzkuVar2, zzba.get(i).value);
                        if (zzau && "_lte".equals(zzkuVar2.name)) {
                            zzkuVar2.zzatq = (Long) zzkbVar.value;
                            zzkuVar2.zzauz = Long.valueOf(this.zzacw.zzbt().currentTimeMillis());
                            zzkuVar = zzkuVar2;
                        }
                        i++;
                        zzkrVar2 = zzkrVar3;
                        zzbb = zzdyVar2;
                    }
                    zzdy zzdyVar3 = zzbb;
                    zzkr zzkrVar4 = zzkrVar2;
                    if (zzau && zzkuVar == null) {
                        zzku zzkuVar3 = new zzku();
                        zzkuVar3.name = "_lte";
                        zzkuVar3.zzauz = Long.valueOf(this.zzacw.zzbt().currentTimeMillis());
                        zzkuVar3.zzatq = (Long) zzkbVar.value;
                        zzksVar2.zzatv = (zzku[]) Arrays.copyOf(zzksVar2.zzatv, zzksVar2.zzatv.length + 1);
                        zzksVar2.zzatv[zzksVar2.zzatv.length - 1] = zzkuVar3;
                    }
                    if (l.longValue() > 0) {
                        zzje().zza(zzkbVar);
                    }
                    Bundle zzij = zzewVar.zzafr.zzij();
                    if ("_iap".equals(zzewVar.name)) {
                        zzij.putLong("_c", 1L);
                        this.zzacw.zzgf().zziy().log("Marking in-app purchase as real-time");
                        zzij.putLong("_r", 1L);
                    }
                    zzij.putString("_o", zzewVar.origin);
                    if (this.zzacw.zzgc().zzci(zzksVar2.zzti)) {
                        this.zzacw.zzgc().zza(zzij, "_dbg", (Object) 1L);
                        this.zzacw.zzgc().zza(zzij, "_r", (Object) 1L);
                    }
                    zzes zzf = zzje().zzf(str, zzewVar.name);
                    if (zzf == null) {
                        bArr = null;
                        zzksVar = zzksVar2;
                        zzdyVar = zzdyVar3;
                        zzkrVar = zzkrVar4;
                        bundle = zzij;
                        zzje().zza(new zzes(str, zzewVar.name, 1L, 0L, zzewVar.zzagc, 0L, null, null, null));
                        j = 0;
                    } else {
                        zzksVar = zzksVar2;
                        zzkrVar = zzkrVar4;
                        zzdyVar = zzdyVar3;
                        bArr = null;
                        bundle = zzij;
                        long j2 = zzf.zzafu;
                        zzje().zza(zzf.zzac(zzewVar.zzagc).zzii());
                        j = j2;
                    }
                    zzer zzerVar = new zzer(this.zzacw, zzewVar.origin, str, zzewVar.name, zzewVar.zzagc, j, bundle);
                    zzkp zzkpVar = new zzkp();
                    zzks zzksVar3 = zzksVar;
                    zzksVar3.zzatu = new zzkp[]{zzkpVar};
                    zzkpVar.zzatn = Long.valueOf(zzerVar.timestamp);
                    zzkpVar.name = zzerVar.name;
                    zzkpVar.zzato = Long.valueOf(zzerVar.zzafq);
                    zzkpVar.zzatm = new zzkq[zzerVar.zzafr.size()];
                    Iterator<String> it = zzerVar.zzafr.iterator();
                    int i2 = 0;
                    while (it.hasNext()) {
                        String next = it.next();
                        zzkq zzkqVar = new zzkq();
                        zzkpVar.zzatm[i2] = zzkqVar;
                        zzkqVar.name = next;
                        zzjc().zza(zzkqVar, zzerVar.zzafr.get(next));
                        i2++;
                    }
                    zzksVar3.zzaum = zza(zzdyVar.zzah(), zzksVar3.zzatv, zzksVar3.zzatu);
                    zzksVar3.zzatx = zzkpVar.zzatn;
                    zzksVar3.zzaty = zzkpVar.zzatn;
                    long zzgn = zzdyVar.zzgn();
                    zzksVar3.zzaua = zzgn != 0 ? Long.valueOf(zzgn) : bArr;
                    long zzgm = zzdyVar.zzgm();
                    if (zzgm != 0) {
                        zzgn = zzgm;
                    }
                    zzksVar3.zzatz = zzgn != 0 ? Long.valueOf(zzgn) : bArr;
                    zzdyVar.zzgv();
                    zzksVar3.zzauk = Integer.valueOf((int) zzdyVar.zzgs());
                    zzksVar3.zzaug = 12451L;
                    zzksVar3.zzatw = Long.valueOf(this.zzacw.zzbt().currentTimeMillis());
                    zzksVar3.zzaul = Boolean.TRUE;
                    zzdy zzdyVar4 = zzdyVar;
                    zzdyVar4.zzm(zzksVar3.zzatx.longValue());
                    zzdyVar4.zzn(zzksVar3.zzaty.longValue());
                    zzje().zza(zzdyVar4);
                    zzje().setTransactionSuccessful();
                    try {
                        int zzvv = zzkrVar.zzvv();
                        byte[] bArr2 = new byte[zzvv];
                        zzaby zzb = zzaby.zzb(bArr2, 0, zzvv);
                        zzkrVar.zza(zzb);
                        zzb.zzvn();
                        return this.zzacw.zzgc().zza(bArr2);
                    } catch (IOException e) {
                        this.zzacw.zzgf().zzis().zze("Data loss. Failed to bundle and serialize. appId", zzfh.zzbl(str), e);
                        return bArr;
                    }
                }
                this.zzacw.zzgf().zziy().zzg("Log and bundle disabled. package_name", str);
            }
            return new byte[0];
        } finally {
            zzje().endTransaction();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzb(zzee zzeeVar, zzdz zzdzVar) {
        zzfj zzis;
        String str;
        Object zzbl;
        String zzbk;
        Object value;
        zzfj zzis2;
        String str2;
        Object zzbl2;
        String zzbk2;
        Object obj;
        Preconditions.checkNotNull(zzeeVar);
        Preconditions.checkNotEmpty(zzeeVar.packageName);
        Preconditions.checkNotNull(zzeeVar.origin);
        Preconditions.checkNotNull(zzeeVar.zzaeq);
        Preconditions.checkNotEmpty(zzeeVar.zzaeq.name);
        zzab();
        zzkz();
        if (TextUtils.isEmpty(zzdzVar.zzadm)) {
            return;
        }
        if (!zzdzVar.zzadw) {
            zzg(zzdzVar);
            return;
        }
        zzee zzeeVar2 = new zzee(zzeeVar);
        boolean z = false;
        zzeeVar2.active = false;
        zzje().beginTransaction();
        try {
            zzee zzi = zzje().zzi(zzeeVar2.packageName, zzeeVar2.zzaeq.name);
            if (zzi != null && !zzi.origin.equals(zzeeVar2.origin)) {
                this.zzacw.zzgf().zziv().zzd("Updating a conditional user property with different origin. name, origin, origin (from DB)", this.zzacw.zzgb().zzbk(zzeeVar2.zzaeq.name), zzeeVar2.origin, zzi.origin);
            }
            if (zzi != null && zzi.active) {
                zzeeVar2.origin = zzi.origin;
                zzeeVar2.creationTimestamp = zzi.creationTimestamp;
                zzeeVar2.triggerTimeout = zzi.triggerTimeout;
                zzeeVar2.triggerEventName = zzi.triggerEventName;
                zzeeVar2.zzaes = zzi.zzaes;
                zzeeVar2.active = zzi.active;
                zzeeVar2.zzaeq = new zzjz(zzeeVar2.zzaeq.name, zzi.zzaeq.zzarl, zzeeVar2.zzaeq.getValue(), zzi.zzaeq.origin);
            } else if (TextUtils.isEmpty(zzeeVar2.triggerEventName)) {
                zzeeVar2.zzaeq = new zzjz(zzeeVar2.zzaeq.name, zzeeVar2.creationTimestamp, zzeeVar2.zzaeq.getValue(), zzeeVar2.zzaeq.origin);
                zzeeVar2.active = true;
                z = true;
            }
            if (zzeeVar2.active) {
                zzjz zzjzVar = zzeeVar2.zzaeq;
                zzkb zzkbVar = new zzkb(zzeeVar2.packageName, zzeeVar2.origin, zzjzVar.name, zzjzVar.zzarl, zzjzVar.getValue());
                if (zzje().zza(zzkbVar)) {
                    zzis2 = this.zzacw.zzgf().zziy();
                    str2 = "User property updated immediately";
                    zzbl2 = zzeeVar2.packageName;
                    zzbk2 = this.zzacw.zzgb().zzbk(zzkbVar.name);
                    obj = zzkbVar.value;
                } else {
                    zzis2 = this.zzacw.zzgf().zzis();
                    str2 = "(2)Too many active user properties, ignoring";
                    zzbl2 = zzfh.zzbl(zzeeVar2.packageName);
                    zzbk2 = this.zzacw.zzgb().zzbk(zzkbVar.name);
                    obj = zzkbVar.value;
                }
                zzis2.zzd(str2, zzbl2, zzbk2, obj);
                if (z && zzeeVar2.zzaes != null) {
                    zzc(new zzew(zzeeVar2.zzaes, zzeeVar2.creationTimestamp), zzdzVar);
                }
            }
            if (zzje().zza(zzeeVar2)) {
                zzis = this.zzacw.zzgf().zziy();
                str = "Conditional property added";
                zzbl = zzeeVar2.packageName;
                zzbk = this.zzacw.zzgb().zzbk(zzeeVar2.zzaeq.name);
                value = zzeeVar2.zzaeq.getValue();
            } else {
                zzis = this.zzacw.zzgf().zzis();
                str = "Too many conditional properties, ignoring";
                zzbl = zzfh.zzbl(zzeeVar2.packageName);
                zzbk = this.zzacw.zzgb().zzbk(zzeeVar2.zzaeq.name);
                value = zzeeVar2.zzaeq.getValue();
            }
            zzis.zzd(str, zzbl, zzbk, value);
            zzje().setTransactionSuccessful();
        } finally {
            zzje().endTransaction();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzb(zzew zzewVar, zzdz zzdzVar) {
        List<zzee> zzb;
        List<zzee> zzb2;
        List<zzee> zzb3;
        zzfj zzis;
        String str;
        Object zzbl;
        String zzbk;
        Object obj;
        Preconditions.checkNotNull(zzdzVar);
        Preconditions.checkNotEmpty(zzdzVar.packageName);
        zzab();
        zzkz();
        String str2 = zzdzVar.packageName;
        long j = zzewVar.zzagc;
        if (this.zzacw.zzgc().zzd(zzewVar, zzdzVar)) {
            if (!zzdzVar.zzadw) {
                zzg(zzdzVar);
                return;
            }
            zzje().beginTransaction();
            try {
                zzej zzje = zzje();
                Preconditions.checkNotEmpty(str2);
                zzje.zzab();
                zzje.zzch();
                if (j < 0) {
                    zzje.zzgf().zziv().zze("Invalid time querying timed out conditional properties", zzfh.zzbl(str2), Long.valueOf(j));
                    zzb = Collections.emptyList();
                } else {
                    zzb = zzje.zzb("active=0 and app_id=? and abs(? - creation_timestamp) > trigger_timeout", new String[]{str2, String.valueOf(j)});
                }
                for (zzee zzeeVar : zzb) {
                    if (zzeeVar != null) {
                        this.zzacw.zzgf().zziy().zzd("User property timed out", zzeeVar.packageName, this.zzacw.zzgb().zzbk(zzeeVar.zzaeq.name), zzeeVar.zzaeq.getValue());
                        if (zzeeVar.zzaer != null) {
                            zzc(new zzew(zzeeVar.zzaer, j), zzdzVar);
                        }
                        zzje().zzj(str2, zzeeVar.zzaeq.name);
                    }
                }
                zzej zzje2 = zzje();
                Preconditions.checkNotEmpty(str2);
                zzje2.zzab();
                zzje2.zzch();
                if (j < 0) {
                    zzje2.zzgf().zziv().zze("Invalid time querying expired conditional properties", zzfh.zzbl(str2), Long.valueOf(j));
                    zzb2 = Collections.emptyList();
                } else {
                    zzb2 = zzje2.zzb("active<>0 and app_id=? and abs(? - triggered_timestamp) > time_to_live", new String[]{str2, String.valueOf(j)});
                }
                ArrayList arrayList = new ArrayList(zzb2.size());
                for (zzee zzeeVar2 : zzb2) {
                    if (zzeeVar2 != null) {
                        this.zzacw.zzgf().zziy().zzd("User property expired", zzeeVar2.packageName, this.zzacw.zzgb().zzbk(zzeeVar2.zzaeq.name), zzeeVar2.zzaeq.getValue());
                        zzje().zzg(str2, zzeeVar2.zzaeq.name);
                        if (zzeeVar2.zzaet != null) {
                            arrayList.add(zzeeVar2.zzaet);
                        }
                        zzje().zzj(str2, zzeeVar2.zzaeq.name);
                    }
                }
                ArrayList arrayList2 = arrayList;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj2 = arrayList.get(i);
                    i++;
                    zzc(new zzew((zzew) obj2, j), zzdzVar);
                }
                zzej zzje3 = zzje();
                String str3 = zzewVar.name;
                Preconditions.checkNotEmpty(str2);
                Preconditions.checkNotEmpty(str3);
                zzje3.zzab();
                zzje3.zzch();
                if (j < 0) {
                    zzje3.zzgf().zziv().zzd("Invalid time querying triggered conditional properties", zzfh.zzbl(str2), zzje3.zzgb().zzbi(str3), Long.valueOf(j));
                    zzb3 = Collections.emptyList();
                } else {
                    zzb3 = zzje3.zzb("active=0 and app_id=? and trigger_event_name=? and abs(? - creation_timestamp) <= trigger_timeout", new String[]{str2, str3, String.valueOf(j)});
                }
                ArrayList arrayList3 = new ArrayList(zzb3.size());
                for (zzee zzeeVar3 : zzb3) {
                    if (zzeeVar3 != null) {
                        zzjz zzjzVar = zzeeVar3.zzaeq;
                        zzkb zzkbVar = new zzkb(zzeeVar3.packageName, zzeeVar3.origin, zzjzVar.name, j, zzjzVar.getValue());
                        if (zzje().zza(zzkbVar)) {
                            zzis = this.zzacw.zzgf().zziy();
                            str = "User property triggered";
                            zzbl = zzeeVar3.packageName;
                            zzbk = this.zzacw.zzgb().zzbk(zzkbVar.name);
                            obj = zzkbVar.value;
                        } else {
                            zzis = this.zzacw.zzgf().zzis();
                            str = "Too many active user properties, ignoring";
                            zzbl = zzfh.zzbl(zzeeVar3.packageName);
                            zzbk = this.zzacw.zzgb().zzbk(zzkbVar.name);
                            obj = zzkbVar.value;
                        }
                        zzis.zzd(str, zzbl, zzbk, obj);
                        if (zzeeVar3.zzaes != null) {
                            arrayList3.add(zzeeVar3.zzaes);
                        }
                        zzeeVar3.zzaeq = new zzjz(zzkbVar);
                        zzeeVar3.active = true;
                        zzje().zza(zzeeVar3);
                    }
                }
                zzc(zzewVar, zzdzVar);
                ArrayList arrayList4 = arrayList3;
                int size2 = arrayList3.size();
                int i2 = 0;
                while (i2 < size2) {
                    Object obj3 = arrayList3.get(i2);
                    i2++;
                    zzc(new zzew((zzew) obj3, j), zzdzVar);
                }
                zzje().setTransactionSuccessful();
            } finally {
                zzje().endTransaction();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzb(zzjr zzjrVar) {
        this.zzaqu++;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzb(zzjz zzjzVar, zzdz zzdzVar) {
        zzab();
        zzkz();
        if (TextUtils.isEmpty(zzdzVar.zzadm)) {
            return;
        }
        if (!zzdzVar.zzadw) {
            zzg(zzdzVar);
            return;
        }
        int zzce = this.zzacw.zzgc().zzce(zzjzVar.name);
        zzgm zzgmVar = this.zzacw;
        if (zzce != 0) {
            zzgmVar.zzgc();
            this.zzacw.zzgc().zza(zzdzVar.packageName, zzce, "_ev", zzkc.zza(zzjzVar.name, 24, true), zzjzVar.name != null ? zzjzVar.name.length() : 0);
            return;
        }
        int zzi = zzgmVar.zzgc().zzi(zzjzVar.name, zzjzVar.getValue());
        if (zzi != 0) {
            this.zzacw.zzgc();
            String zza2 = zzkc.zza(zzjzVar.name, 24, true);
            Object value = zzjzVar.getValue();
            this.zzacw.zzgc().zza(zzdzVar.packageName, zzi, "_ev", zza2, (value == null || !((value instanceof String) || (value instanceof CharSequence))) ? 0 : String.valueOf(value).length());
            return;
        }
        Object zzj = this.zzacw.zzgc().zzj(zzjzVar.name, zzjzVar.getValue());
        if (zzj == null) {
            return;
        }
        zzkb zzkbVar = new zzkb(zzdzVar.packageName, zzjzVar.origin, zzjzVar.name, zzjzVar.zzarl, zzj);
        this.zzacw.zzgf().zziy().zze("Setting user property", this.zzacw.zzgb().zzbk(zzkbVar.name), zzj);
        zzje().beginTransaction();
        try {
            zzg(zzdzVar);
            boolean zza3 = zzje().zza(zzkbVar);
            zzje().setTransactionSuccessful();
            if (zza3) {
                this.zzacw.zzgf().zziy().zze("User property set", this.zzacw.zzgb().zzbk(zzkbVar.name), zzkbVar.value);
            } else {
                this.zzacw.zzgf().zzis().zze("Too many unique user properties are set. Ignoring user property", this.zzacw.zzgb().zzbk(zzkbVar.name), zzkbVar.value);
                this.zzacw.zzgc().zza(zzdzVar.packageName, 9, (String) null, (String) null, 0);
            }
        } finally {
            zzje().endTransaction();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00b0, code lost:
    
        r6.zzacw.zzgg().zzakf.set(r6.zzacw.zzbt().currentTimeMillis());
     */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0132 A[Catch: all -> 0x0179, TryCatch #1 {all -> 0x0179, blocks: (B:5:0x0029, B:12:0x0045, B:13:0x016d, B:24:0x0061, B:31:0x00b0, B:32:0x00c5, B:35:0x00cd, B:37:0x00d9, B:39:0x00df, B:43:0x00ec, B:46:0x011c, B:48:0x0132, B:49:0x015a, B:51:0x0164, B:53:0x016a, B:54:0x0142, B:55:0x0103, B:57:0x010d), top: B:4:0x0029, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0142 A[Catch: all -> 0x0179, TryCatch #1 {all -> 0x0179, blocks: (B:5:0x0029, B:12:0x0045, B:13:0x016d, B:24:0x0061, B:31:0x00b0, B:32:0x00c5, B:35:0x00cd, B:37:0x00d9, B:39:0x00df, B:43:0x00ec, B:46:0x011c, B:48:0x0132, B:49:0x015a, B:51:0x0164, B:53:0x016a, B:54:0x0142, B:55:0x0103, B:57:0x010d), top: B:4:0x0029, outer: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void zzb(String str, int i, Throwable th, byte[] bArr, Map<String, List<String>> map) {
        zzej zzje;
        zzab();
        zzkz();
        Preconditions.checkNotEmpty(str);
        if (bArr == null) {
            try {
                bArr = new byte[0];
            } finally {
                this.zzaqw = false;
                zzle();
            }
        }
        this.zzacw.zzgf().zziz().zzg("onConfigFetched. Response size", Integer.valueOf(bArr.length));
        zzje().beginTransaction();
        try {
            zzdy zzbb = zzje().zzbb(str);
            boolean z = true;
            boolean z2 = (i == 200 || i == 204 || i == 304) && th == null;
            if (zzbb == null) {
                this.zzacw.zzgf().zziv().zzg("App does not exist in onConfigFetched. appId", zzfh.zzbl(str));
            } else {
                if (!z2 && i != 404) {
                    zzbb.zzt(this.zzacw.zzbt().currentTimeMillis());
                    zzje().zza(zzbb);
                    this.zzacw.zzgf().zziz().zze("Fetching config failed. code, error", Integer.valueOf(i), th);
                    zzkv().zzbv(str);
                    this.zzacw.zzgg().zzake.set(this.zzacw.zzbt().currentTimeMillis());
                    if (i != 503 && i != 429) {
                        z = false;
                    }
                    zzld();
                }
                List<String> list = map != null ? map.get("Last-Modified") : null;
                String str2 = (list == null || list.size() <= 0) ? null : list.get(0);
                if (i != 404 && i != 304) {
                    if (!zzkv().zza(str, bArr, str2)) {
                        zzje = zzje();
                        zzje.endTransaction();
                    }
                    zzbb.zzs(this.zzacw.zzbt().currentTimeMillis());
                    zzje().zza(zzbb);
                    if (i != 404) {
                        this.zzacw.zzgf().zziw().zzg("Config not found. Using empty config. appId", str);
                    } else {
                        this.zzacw.zzgf().zziz().zze("Successfully fetched config. Got network response. code, size", Integer.valueOf(i), Integer.valueOf(bArr.length));
                    }
                    if (zzkw().zzex() && zzlc()) {
                        zzlb();
                    }
                    zzld();
                }
                if (zzkv().zzbt(str) == null && !zzkv().zza(str, null, null)) {
                    zzje = zzje();
                    zzje.endTransaction();
                }
                zzbb.zzs(this.zzacw.zzbt().currentTimeMillis());
                zzje().zza(zzbb);
                if (i != 404) {
                }
                if (zzkw().zzex()) {
                    zzlb();
                }
                zzld();
            }
            zzje().setTransactionSuccessful();
            zzje = zzje();
            zzje.endTransaction();
        } catch (Throwable th2) {
            zzje().endTransaction();
            throw th2;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzed
    public final Clock zzbt() {
        return this.zzacw.zzbt();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzc(zzee zzeeVar, zzdz zzdzVar) {
        Preconditions.checkNotNull(zzeeVar);
        Preconditions.checkNotEmpty(zzeeVar.packageName);
        Preconditions.checkNotNull(zzeeVar.zzaeq);
        Preconditions.checkNotEmpty(zzeeVar.zzaeq.name);
        zzab();
        zzkz();
        if (TextUtils.isEmpty(zzdzVar.zzadm)) {
            return;
        }
        if (!zzdzVar.zzadw) {
            zzg(zzdzVar);
            return;
        }
        zzje().beginTransaction();
        try {
            zzg(zzdzVar);
            zzee zzi = zzje().zzi(zzeeVar.packageName, zzeeVar.zzaeq.name);
            if (zzi != null) {
                this.zzacw.zzgf().zziy().zze("Removing conditional user property", zzeeVar.packageName, this.zzacw.zzgb().zzbk(zzeeVar.zzaeq.name));
                zzje().zzj(zzeeVar.packageName, zzeeVar.zzaeq.name);
                if (zzi.active) {
                    zzje().zzg(zzeeVar.packageName, zzeeVar.zzaeq.name);
                }
                if (zzeeVar.zzaet != null) {
                    zzc(this.zzacw.zzgc().zza(zzeeVar.zzaet.name, zzeeVar.zzaet.zzafr != null ? zzeeVar.zzaet.zzafr.zzij() : null, zzi.origin, zzeeVar.zzaet.zzagc, true, false), zzdzVar);
                }
            } else {
                this.zzacw.zzgf().zziv().zze("Conditional user property doesn't exist", zzfh.zzbl(zzeeVar.packageName), this.zzacw.zzgb().zzbk(zzeeVar.zzaeq.name));
            }
            zzje().setTransactionSuccessful();
        } finally {
            zzje().endTransaction();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzc(zzew zzewVar, String str) {
        zzdy zzbb = zzje().zzbb(str);
        if (zzbb == null || TextUtils.isEmpty(zzbb.zzag())) {
            this.zzacw.zzgf().zziy().zzg("No app data available; dropping event", str);
            return;
        }
        Boolean zzc = zzc(zzbb);
        if (zzc == null) {
            if (!"_ui".equals(zzewVar.name)) {
                this.zzacw.zzgf().zziv().zzg("Could not find package. appId", zzfh.zzbl(str));
            }
        } else if (!zzc.booleanValue()) {
            this.zzacw.zzgf().zzis().zzg("App version does not match; dropping event. appId", zzfh.zzbl(str));
            return;
        }
        zzb(zzewVar, new zzdz(str, zzbb.getGmpAppId(), zzbb.zzag(), zzbb.zzgo(), zzbb.zzgp(), zzbb.zzgq(), zzbb.zzgr(), (String) null, zzbb.isMeasurementEnabled(), false, zzbb.zzgl(), zzbb.zzhe(), 0L, 0, zzbb.zzhf(), zzbb.zzhg(), false));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzc(zzjz zzjzVar, zzdz zzdzVar) {
        zzab();
        zzkz();
        if (TextUtils.isEmpty(zzdzVar.zzadm)) {
            return;
        }
        if (!zzdzVar.zzadw) {
            zzg(zzdzVar);
            return;
        }
        this.zzacw.zzgf().zziy().zzg("Removing user property", this.zzacw.zzgb().zzbk(zzjzVar.name));
        zzje().beginTransaction();
        try {
            zzg(zzdzVar);
            zzje().zzg(zzdzVar.packageName, zzjzVar.name);
            zzje().setTransactionSuccessful();
            this.zzacw.zzgf().zziy().zzg("User property removed", this.zzacw.zzgb().zzbk(zzjzVar.name));
        } finally {
            zzje().endTransaction();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final zzdz zzca(String str) {
        String str2;
        zzfj zzfjVar;
        Object obj;
        String str3 = str;
        zzdy zzbb = zzje().zzbb(str3);
        if (zzbb == null || TextUtils.isEmpty(zzbb.zzag())) {
            str2 = "No app data available; dropping";
            obj = str3;
            zzfjVar = this.zzacw.zzgf().zziy();
        } else {
            Boolean zzc = zzc(zzbb);
            if (zzc == null || zzc.booleanValue()) {
                return new zzdz(str, zzbb.getGmpAppId(), zzbb.zzag(), zzbb.zzgo(), zzbb.zzgp(), zzbb.zzgq(), zzbb.zzgr(), (String) null, zzbb.isMeasurementEnabled(), false, zzbb.zzgl(), zzbb.zzhe(), 0L, 0, zzbb.zzhf(), zzbb.zzhg(), false);
            }
            zzfj zzis = this.zzacw.zzgf().zzis();
            str2 = "App version does not match; dropping. appId";
            obj = zzfh.zzbl(str);
            zzfjVar = zzis;
        }
        zzfjVar.zzg(str2, obj);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzd(zzdz zzdzVar) {
        if (this.zzarb != null) {
            ArrayList arrayList = new ArrayList();
            this.zzarc = arrayList;
            arrayList.addAll(this.zzarb);
        }
        zzej zzje = zzje();
        String str = zzdzVar.packageName;
        Preconditions.checkNotEmpty(str);
        zzje.zzab();
        zzje.zzch();
        try {
            SQLiteDatabase writableDatabase = zzje.getWritableDatabase();
            String[] strArr = {str};
            int delete = writableDatabase.delete("apps", "app_id=?", strArr) + 0 + writableDatabase.delete("events", "app_id=?", strArr) + writableDatabase.delete("user_attributes", "app_id=?", strArr) + writableDatabase.delete("conditional_properties", "app_id=?", strArr) + writableDatabase.delete("raw_events", "app_id=?", strArr) + writableDatabase.delete("raw_events_metadata", "app_id=?", strArr) + writableDatabase.delete("queue", "app_id=?", strArr) + writableDatabase.delete("audience_filter_values", "app_id=?", strArr) + writableDatabase.delete("main_event_params", "app_id=?", strArr);
            if (delete > 0) {
                zzje.zzgf().zziz().zze("Reset analytics data. app, records", str, Integer.valueOf(delete));
            }
        } catch (SQLiteException e) {
            zzje.zzgf().zzis().zze("Error resetting analytics data. appId, error", zzfh.zzbl(str), e);
        }
        zzdz zza2 = zza(this.zzacw.getContext(), zzdzVar.packageName, zzdzVar.zzadm, zzdzVar.zzadw, zzdzVar.zzady, zzdzVar.zzadz, zzdzVar.zzaem);
        if (!this.zzacw.zzgh().zzay(zzdzVar.packageName) || zzdzVar.zzadw) {
            zzf(zza2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zze(zzdz zzdzVar) {
        zzab();
        zzkz();
        Preconditions.checkNotEmpty(zzdzVar.packageName);
        zzg(zzdzVar);
    }

    /* JADX WARN: Removed duplicated region for block: B:118:0x039d A[Catch: all -> 0x03c9, TryCatch #4 {all -> 0x03c9, blocks: (B:25:0x0099, B:27:0x00a5, B:29:0x00ab, B:31:0x00b7, B:33:0x00df, B:35:0x0128, B:39:0x013b, B:41:0x014f, B:44:0x015c, B:46:0x0166, B:47:0x0185, B:48:0x01bd, B:50:0x01c2, B:51:0x01ca, B:53:0x01dd, B:56:0x01f1, B:58:0x0239, B:60:0x023d, B:61:0x0240, B:63:0x024c, B:64:0x02fc, B:66:0x0317, B:67:0x031a, B:68:0x032b, B:69:0x037e, B:70:0x0399, B:71:0x03ba, B:76:0x0263, B:79:0x0270, B:81:0x0291, B:83:0x0299, B:85:0x02a1, B:86:0x02a7, B:89:0x02b1, B:93:0x02c0, B:103:0x02d2, B:95:0x02ea, B:97:0x02f0, B:98:0x02f3, B:100:0x02f9, B:106:0x0279, B:112:0x0333, B:114:0x0365, B:116:0x0369, B:117:0x036c, B:118:0x039d, B:120:0x03a3, B:122:0x01d1, B:124:0x0189, B:126:0x0191, B:128:0x019d), top: B:24:0x0099, inners: #0, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:121:0x01cf  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x01c2 A[Catch: all -> 0x03c9, TryCatch #4 {all -> 0x03c9, blocks: (B:25:0x0099, B:27:0x00a5, B:29:0x00ab, B:31:0x00b7, B:33:0x00df, B:35:0x0128, B:39:0x013b, B:41:0x014f, B:44:0x015c, B:46:0x0166, B:47:0x0185, B:48:0x01bd, B:50:0x01c2, B:51:0x01ca, B:53:0x01dd, B:56:0x01f1, B:58:0x0239, B:60:0x023d, B:61:0x0240, B:63:0x024c, B:64:0x02fc, B:66:0x0317, B:67:0x031a, B:68:0x032b, B:69:0x037e, B:70:0x0399, B:71:0x03ba, B:76:0x0263, B:79:0x0270, B:81:0x0291, B:83:0x0299, B:85:0x02a1, B:86:0x02a7, B:89:0x02b1, B:93:0x02c0, B:103:0x02d2, B:95:0x02ea, B:97:0x02f0, B:98:0x02f3, B:100:0x02f9, B:106:0x0279, B:112:0x0333, B:114:0x0365, B:116:0x0369, B:117:0x036c, B:118:0x039d, B:120:0x03a3, B:122:0x01d1, B:124:0x0189, B:126:0x0191, B:128:0x019d), top: B:24:0x0099, inners: #0, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x01dd A[Catch: all -> 0x03c9, TRY_LEAVE, TryCatch #4 {all -> 0x03c9, blocks: (B:25:0x0099, B:27:0x00a5, B:29:0x00ab, B:31:0x00b7, B:33:0x00df, B:35:0x0128, B:39:0x013b, B:41:0x014f, B:44:0x015c, B:46:0x0166, B:47:0x0185, B:48:0x01bd, B:50:0x01c2, B:51:0x01ca, B:53:0x01dd, B:56:0x01f1, B:58:0x0239, B:60:0x023d, B:61:0x0240, B:63:0x024c, B:64:0x02fc, B:66:0x0317, B:67:0x031a, B:68:0x032b, B:69:0x037e, B:70:0x0399, B:71:0x03ba, B:76:0x0263, B:79:0x0270, B:81:0x0291, B:83:0x0299, B:85:0x02a1, B:86:0x02a7, B:89:0x02b1, B:93:0x02c0, B:103:0x02d2, B:95:0x02ea, B:97:0x02f0, B:98:0x02f3, B:100:0x02f9, B:106:0x0279, B:112:0x0333, B:114:0x0365, B:116:0x0369, B:117:0x036c, B:118:0x039d, B:120:0x03a3, B:122:0x01d1, B:124:0x0189, B:126:0x0191, B:128:0x019d), top: B:24:0x0099, inners: #0, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0317 A[Catch: all -> 0x03c9, TryCatch #4 {all -> 0x03c9, blocks: (B:25:0x0099, B:27:0x00a5, B:29:0x00ab, B:31:0x00b7, B:33:0x00df, B:35:0x0128, B:39:0x013b, B:41:0x014f, B:44:0x015c, B:46:0x0166, B:47:0x0185, B:48:0x01bd, B:50:0x01c2, B:51:0x01ca, B:53:0x01dd, B:56:0x01f1, B:58:0x0239, B:60:0x023d, B:61:0x0240, B:63:0x024c, B:64:0x02fc, B:66:0x0317, B:67:0x031a, B:68:0x032b, B:69:0x037e, B:70:0x0399, B:71:0x03ba, B:76:0x0263, B:79:0x0270, B:81:0x0291, B:83:0x0299, B:85:0x02a1, B:86:0x02a7, B:89:0x02b1, B:93:0x02c0, B:103:0x02d2, B:95:0x02ea, B:97:0x02f0, B:98:0x02f3, B:100:0x02f9, B:106:0x0279, B:112:0x0333, B:114:0x0365, B:116:0x0369, B:117:0x036c, B:118:0x039d, B:120:0x03a3, B:122:0x01d1, B:124:0x0189, B:126:0x0191, B:128:0x019d), top: B:24:0x0099, inners: #0, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x02a1 A[Catch: all -> 0x03c9, TryCatch #4 {all -> 0x03c9, blocks: (B:25:0x0099, B:27:0x00a5, B:29:0x00ab, B:31:0x00b7, B:33:0x00df, B:35:0x0128, B:39:0x013b, B:41:0x014f, B:44:0x015c, B:46:0x0166, B:47:0x0185, B:48:0x01bd, B:50:0x01c2, B:51:0x01ca, B:53:0x01dd, B:56:0x01f1, B:58:0x0239, B:60:0x023d, B:61:0x0240, B:63:0x024c, B:64:0x02fc, B:66:0x0317, B:67:0x031a, B:68:0x032b, B:69:0x037e, B:70:0x0399, B:71:0x03ba, B:76:0x0263, B:79:0x0270, B:81:0x0291, B:83:0x0299, B:85:0x02a1, B:86:0x02a7, B:89:0x02b1, B:93:0x02c0, B:103:0x02d2, B:95:0x02ea, B:97:0x02f0, B:98:0x02f3, B:100:0x02f9, B:106:0x0279, B:112:0x0333, B:114:0x0365, B:116:0x0369, B:117:0x036c, B:118:0x039d, B:120:0x03a3, B:122:0x01d1, B:124:0x0189, B:126:0x0191, B:128:0x019d), top: B:24:0x0099, inners: #0, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x02ad  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x02af  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x02a6  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x02ea A[Catch: all -> 0x03c9, TryCatch #4 {all -> 0x03c9, blocks: (B:25:0x0099, B:27:0x00a5, B:29:0x00ab, B:31:0x00b7, B:33:0x00df, B:35:0x0128, B:39:0x013b, B:41:0x014f, B:44:0x015c, B:46:0x0166, B:47:0x0185, B:48:0x01bd, B:50:0x01c2, B:51:0x01ca, B:53:0x01dd, B:56:0x01f1, B:58:0x0239, B:60:0x023d, B:61:0x0240, B:63:0x024c, B:64:0x02fc, B:66:0x0317, B:67:0x031a, B:68:0x032b, B:69:0x037e, B:70:0x0399, B:71:0x03ba, B:76:0x0263, B:79:0x0270, B:81:0x0291, B:83:0x0299, B:85:0x02a1, B:86:0x02a7, B:89:0x02b1, B:93:0x02c0, B:103:0x02d2, B:95:0x02ea, B:97:0x02f0, B:98:0x02f3, B:100:0x02f9, B:106:0x0279, B:112:0x0333, B:114:0x0365, B:116:0x0369, B:117:0x036c, B:118:0x039d, B:120:0x03a3, B:122:0x01d1, B:124:0x0189, B:126:0x0191, B:128:0x019d), top: B:24:0x0099, inners: #0, #3 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void zzf(zzdz zzdzVar) {
        int i;
        zzes zzesVar;
        zzej zzje;
        String str;
        String str2;
        zzew zzewVar;
        zzew zzewVar2;
        int i2;
        PackageInfo packageInfo;
        ApplicationInfo applicationInfo;
        boolean z;
        long zzm;
        zzew zzewVar3;
        zzab();
        zzkz();
        Preconditions.checkNotNull(zzdzVar);
        Preconditions.checkNotEmpty(zzdzVar.packageName);
        if (TextUtils.isEmpty(zzdzVar.zzadm)) {
            return;
        }
        zzdy zzbb = zzje().zzbb(zzdzVar.packageName);
        if (zzbb != null && TextUtils.isEmpty(zzbb.getGmpAppId()) && !TextUtils.isEmpty(zzdzVar.zzadm)) {
            zzbb.zzs(0L);
            zzje().zza(zzbb);
            zzkv().zzbw(zzdzVar.packageName);
        }
        if (!zzdzVar.zzadw) {
            zzg(zzdzVar);
            return;
        }
        long j = zzdzVar.zzaem;
        if (j == 0) {
            j = this.zzacw.zzbt().currentTimeMillis();
        }
        int i3 = zzdzVar.zzaen;
        if (i3 != 0 && i3 != 1) {
            this.zzacw.zzgf().zziv().zze("Incorrect app type, assuming installed app. appId, appType", zzfh.zzbl(zzdzVar.packageName), Integer.valueOf(i3));
            i3 = 0;
        }
        zzje().beginTransaction();
        try {
            zzdy zzbb2 = zzje().zzbb(zzdzVar.packageName);
            if (zzbb2 != null && zzbb2.getGmpAppId() != null && !zzbb2.getGmpAppId().equals(zzdzVar.zzadm)) {
                this.zzacw.zzgf().zziv().zzg("New GMP App Id passed in. Removing cached database data. appId", zzfh.zzbl(zzbb2.zzah()));
                zzej zzje2 = zzje();
                String zzah = zzbb2.zzah();
                zzje2.zzch();
                zzje2.zzab();
                Preconditions.checkNotEmpty(zzah);
                try {
                    SQLiteDatabase writableDatabase = zzje2.getWritableDatabase();
                    String[] strArr = {zzah};
                    int delete = writableDatabase.delete("events", "app_id=?", strArr) + 0 + writableDatabase.delete("user_attributes", "app_id=?", strArr) + writableDatabase.delete("conditional_properties", "app_id=?", strArr) + writableDatabase.delete("apps", "app_id=?", strArr) + writableDatabase.delete("raw_events", "app_id=?", strArr) + writableDatabase.delete("raw_events_metadata", "app_id=?", strArr) + writableDatabase.delete("event_filters", "app_id=?", strArr) + writableDatabase.delete("property_filters", "app_id=?", strArr) + writableDatabase.delete("audience_filter_values", "app_id=?", strArr);
                    if (delete > 0) {
                        zzje2.zzgf().zziz().zze("Deleted application data. app, records", zzah, Integer.valueOf(delete));
                    }
                } catch (SQLiteException e) {
                    zzje2.zzgf().zzis().zze("Error deleting application data. appId, error", zzfh.zzbl(zzah), e);
                }
                zzbb2 = null;
            }
            if (zzbb2 != null) {
                if (zzbb2.zzgo() == -2147483648L) {
                    i = 1;
                    if (zzbb2.zzag() != null && !zzbb2.zzag().equals(zzdzVar.zzth)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("_pv", zzbb2.zzag());
                        zzewVar3 = new zzew("_au", new zzet(bundle), DebugKt.DEBUG_PROPERTY_VALUE_AUTO, j);
                    }
                    zzg(zzdzVar);
                    if (i3 == 0) {
                        zzje = zzje();
                        str = zzdzVar.packageName;
                        str2 = "_f";
                    } else {
                        if (i3 != i) {
                            zzesVar = null;
                            if (zzesVar == null) {
                                long j2 = j;
                                if (zzdzVar.zzael) {
                                    zzewVar = new zzew("_cd", new zzet(new Bundle()), DebugKt.DEBUG_PROPERTY_VALUE_AUTO, j2);
                                }
                                zzje().setTransactionSuccessful();
                            }
                            long j3 = j;
                            long j4 = ((j / 3600000) + 1) * 3600000;
                            if (i3 == 0) {
                                zzb(new zzjz("_fot", j3, Long.valueOf(j4), DebugKt.DEBUG_PROPERTY_VALUE_AUTO), zzdzVar);
                                zzab();
                                zzkz();
                                Bundle bundle2 = new Bundle();
                                bundle2.putLong("_c", 1L);
                                bundle2.putLong("_r", 1L);
                                bundle2.putLong("_uwa", 0L);
                                bundle2.putLong("_pfo", 0L);
                                bundle2.putLong("_sys", 0L);
                                bundle2.putLong("_sysu", 0L);
                                if (this.zzacw.zzgh().zzay(zzdzVar.packageName) && zzdzVar.zzaeo) {
                                    bundle2.putLong("_dac", 1L);
                                }
                                if (this.zzacw.getContext().getPackageManager() == null) {
                                    this.zzacw.zzgf().zzis().zzg("PackageManager is null, first open report might be inaccurate. appId", zzfh.zzbl(zzdzVar.packageName));
                                } else {
                                    try {
                                        i2 = 0;
                                    } catch (PackageManager.NameNotFoundException e2) {
                                        e = e2;
                                        i2 = 0;
                                    }
                                    try {
                                        packageInfo = Wrappers.packageManager(this.zzacw.getContext()).getPackageInfo(zzdzVar.packageName, 0);
                                    } catch (PackageManager.NameNotFoundException e3) {
                                        e = e3;
                                        this.zzacw.zzgf().zzis().zze("Package info is null, first open report might be inaccurate. appId", zzfh.zzbl(zzdzVar.packageName), e);
                                        packageInfo = null;
                                        if (packageInfo != null) {
                                            if (packageInfo.firstInstallTime == packageInfo.lastUpdateTime) {
                                            }
                                            zzb(new zzjz("_fi", j3, Long.valueOf(!z ? 1L : 0L), DebugKt.DEBUG_PROPERTY_VALUE_AUTO), zzdzVar);
                                        }
                                        applicationInfo = Wrappers.packageManager(this.zzacw.getContext()).getApplicationInfo(zzdzVar.packageName, i2);
                                        if (applicationInfo != null) {
                                        }
                                        zzej zzje3 = zzje();
                                        String str3 = zzdzVar.packageName;
                                        Preconditions.checkNotEmpty(str3);
                                        zzje3.zzab();
                                        zzje3.zzch();
                                        zzm = zzje3.zzm(str3, "first_open_count");
                                        if (zzm >= 0) {
                                        }
                                        zzewVar2 = new zzew("_f", new zzet(bundle2), DebugKt.DEBUG_PROPERTY_VALUE_AUTO, j3);
                                        zzb(zzewVar2, zzdzVar);
                                        Bundle bundle3 = new Bundle();
                                        bundle3.putLong("_et", 1L);
                                        zzewVar = new zzew("_e", new zzet(bundle3), DebugKt.DEBUG_PROPERTY_VALUE_AUTO, j3);
                                        zzb(zzewVar, zzdzVar);
                                        zzje().setTransactionSuccessful();
                                    }
                                    if (packageInfo != null && packageInfo.firstInstallTime != 0) {
                                        if (packageInfo.firstInstallTime == packageInfo.lastUpdateTime) {
                                            bundle2.putLong("_uwa", 1L);
                                            z = false;
                                        } else {
                                            z = true;
                                        }
                                        zzb(new zzjz("_fi", j3, Long.valueOf(!z ? 1L : 0L), DebugKt.DEBUG_PROPERTY_VALUE_AUTO), zzdzVar);
                                    }
                                    try {
                                        applicationInfo = Wrappers.packageManager(this.zzacw.getContext()).getApplicationInfo(zzdzVar.packageName, i2);
                                    } catch (PackageManager.NameNotFoundException e4) {
                                        this.zzacw.zzgf().zzis().zze("Application info is null, first open report might be inaccurate. appId", zzfh.zzbl(zzdzVar.packageName), e4);
                                        applicationInfo = null;
                                    }
                                    if (applicationInfo != null) {
                                        if ((applicationInfo.flags & 1) != 0) {
                                            bundle2.putLong("_sys", 1L);
                                        }
                                        if ((applicationInfo.flags & 128) != 0) {
                                            bundle2.putLong("_sysu", 1L);
                                        }
                                    }
                                }
                                zzej zzje32 = zzje();
                                String str32 = zzdzVar.packageName;
                                Preconditions.checkNotEmpty(str32);
                                zzje32.zzab();
                                zzje32.zzch();
                                zzm = zzje32.zzm(str32, "first_open_count");
                                if (zzm >= 0) {
                                    bundle2.putLong("_pfo", zzm);
                                }
                                zzewVar2 = new zzew("_f", new zzet(bundle2), DebugKt.DEBUG_PROPERTY_VALUE_AUTO, j3);
                            } else {
                                if (i3 == 1) {
                                    zzb(new zzjz("_fvt", j3, Long.valueOf(j4), DebugKt.DEBUG_PROPERTY_VALUE_AUTO), zzdzVar);
                                    zzab();
                                    zzkz();
                                    Bundle bundle4 = new Bundle();
                                    bundle4.putLong("_c", 1L);
                                    bundle4.putLong("_r", 1L);
                                    if (this.zzacw.zzgh().zzay(zzdzVar.packageName) && zzdzVar.zzaeo) {
                                        bundle4.putLong("_dac", 1L);
                                    }
                                    zzewVar2 = new zzew("_v", new zzet(bundle4), DebugKt.DEBUG_PROPERTY_VALUE_AUTO, j3);
                                }
                                Bundle bundle32 = new Bundle();
                                bundle32.putLong("_et", 1L);
                                zzewVar = new zzew("_e", new zzet(bundle32), DebugKt.DEBUG_PROPERTY_VALUE_AUTO, j3);
                            }
                            zzb(zzewVar2, zzdzVar);
                            Bundle bundle322 = new Bundle();
                            bundle322.putLong("_et", 1L);
                            zzewVar = new zzew("_e", new zzet(bundle322), DebugKt.DEBUG_PROPERTY_VALUE_AUTO, j3);
                            zzb(zzewVar, zzdzVar);
                            zzje().setTransactionSuccessful();
                        }
                        zzje = zzje();
                        str = zzdzVar.packageName;
                        str2 = "_v";
                    }
                    zzesVar = zzje.zzf(str, str2);
                    if (zzesVar == null) {
                    }
                    zzb(zzewVar, zzdzVar);
                    zzje().setTransactionSuccessful();
                }
                if (zzbb2.zzgo() != zzdzVar.zzads) {
                    Bundle bundle5 = new Bundle();
                    bundle5.putString("_pv", zzbb2.zzag());
                    zzet zzetVar = new zzet(bundle5);
                    i = 1;
                    zzewVar3 = new zzew("_au", zzetVar, DebugKt.DEBUG_PROPERTY_VALUE_AUTO, j);
                }
                zzb(zzewVar3, zzdzVar);
                zzg(zzdzVar);
                if (i3 == 0) {
                }
                zzesVar = zzje.zzf(str, str2);
                if (zzesVar == null) {
                }
                zzb(zzewVar, zzdzVar);
                zzje().setTransactionSuccessful();
            }
            i = 1;
            zzg(zzdzVar);
            if (i3 == 0) {
            }
            zzesVar = zzje.zzf(str, str2);
            if (zzesVar == null) {
            }
            zzb(zzewVar, zzdzVar);
            zzje().setTransactionSuccessful();
        } finally {
            zzje().endTransaction();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzg(Runnable runnable) {
        zzab();
        if (this.zzaqt == null) {
            this.zzaqt = new ArrayList();
        }
        this.zzaqt.add(runnable);
    }

    public final zzff zzgb() {
        return this.zzacw.zzgb();
    }

    public final zzkc zzgc() {
        return this.zzacw.zzgc();
    }

    @Override // com.google.android.gms.internal.measurement.zzed
    public final zzgh zzge() {
        return this.zzacw.zzge();
    }

    @Override // com.google.android.gms.internal.measurement.zzed
    public final zzfh zzgf() {
        return this.zzacw.zzgf();
    }

    public final zzeg zzgh() {
        return this.zzacw.zzgh();
    }

    @Override // com.google.android.gms.internal.measurement.zzed
    public final zzec zzgi() {
        return this.zzacw.zzgi();
    }

    public final String zzh(zzdz zzdzVar) {
        try {
            return (String) this.zzacw.zzge().zzb(new zzjw(this, zzdzVar)).get(WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            this.zzacw.zzgf().zzis().zze("Failed to get app instance id. appId", zzfh.zzbl(zzdzVar.packageName), e);
            return null;
        }
    }

    public final zzjy zzjc() {
        zza(this.zzaqq);
        return this.zzaqq;
    }

    public final zzeb zzjd() {
        zza(this.zzaqp);
        return this.zzaqp;
    }

    public final zzej zzje() {
        zza(this.zzaqm);
        return this.zzaqm;
    }

    public final zzfl zzkw() {
        zza(this.zzaql);
        return this.zzaql;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzkz() {
        if (!this.zzvo) {
            throw new IllegalStateException("UploadController is not initialized");
        }
    }

    public final void zzlb() {
        zzdy zzbb;
        String str;
        zzfj zziz;
        String str2;
        zzab();
        zzkz();
        this.zzaqy = true;
        try {
            this.zzacw.zzgi();
            Boolean zzko = this.zzacw.zzfy().zzko();
            if (zzko == null) {
                zziz = this.zzacw.zzgf().zziv();
                str2 = "Upload data called on the client side before use of service was decided";
            } else {
                if (!zzko.booleanValue()) {
                    if (this.zzaqs <= 0) {
                        zzab();
                        if (this.zzarb != null) {
                            zziz = this.zzacw.zzgf().zziz();
                            str2 = "Uploading requested multiple times";
                        } else {
                            if (zzkw().zzex()) {
                                long currentTimeMillis = this.zzacw.zzbt().currentTimeMillis();
                                zzd(null, currentTimeMillis - zzeg.zzhm());
                                long j = this.zzacw.zzgg().zzakd.get();
                                if (j != 0) {
                                    this.zzacw.zzgf().zziy().zzg("Uploading events. Elapsed time since last upload attempt (ms)", Long.valueOf(Math.abs(currentTimeMillis - j)));
                                }
                                String zzhr = zzje().zzhr();
                                if (TextUtils.isEmpty(zzhr)) {
                                    this.zzard = -1L;
                                    String zzab = zzje().zzab(currentTimeMillis - zzeg.zzhm());
                                    if (!TextUtils.isEmpty(zzab) && (zzbb = zzje().zzbb(zzab)) != null) {
                                        zzb(zzbb);
                                    }
                                } else {
                                    if (this.zzard == -1) {
                                        this.zzard = zzje().zzhy();
                                    }
                                    List<Pair<zzks, Long>> zzb = zzje().zzb(zzhr, this.zzacw.zzgh().zzb(zzhr, zzey.zzagv), Math.max(0, this.zzacw.zzgh().zzb(zzhr, zzey.zzagw)));
                                    if (!zzb.isEmpty()) {
                                        Iterator<Pair<zzks, Long>> it = zzb.iterator();
                                        while (true) {
                                            if (!it.hasNext()) {
                                                str = null;
                                                break;
                                            }
                                            zzks zzksVar = (zzks) it.next().first;
                                            if (!TextUtils.isEmpty(zzksVar.zzauh)) {
                                                str = zzksVar.zzauh;
                                                break;
                                            }
                                        }
                                        if (str != null) {
                                            int i = 0;
                                            while (true) {
                                                if (i >= zzb.size()) {
                                                    break;
                                                }
                                                zzks zzksVar2 = (zzks) zzb.get(i).first;
                                                if (!TextUtils.isEmpty(zzksVar2.zzauh) && !zzksVar2.zzauh.equals(str)) {
                                                    zzb = zzb.subList(0, i);
                                                    break;
                                                }
                                                i++;
                                            }
                                        }
                                        zzkr zzkrVar = new zzkr();
                                        zzkrVar.zzatr = new zzks[zzb.size()];
                                        ArrayList arrayList = new ArrayList(zzb.size());
                                        boolean z = zzeg.zzho() && this.zzacw.zzgh().zzas(zzhr);
                                        for (int i2 = 0; i2 < zzkrVar.zzatr.length; i2++) {
                                            zzkrVar.zzatr[i2] = (zzks) zzb.get(i2).first;
                                            arrayList.add((Long) zzb.get(i2).second);
                                            zzkrVar.zzatr[i2].zzaug = 12451L;
                                            zzkrVar.zzatr[i2].zzatw = Long.valueOf(currentTimeMillis);
                                            zzks zzksVar3 = zzkrVar.zzatr[i2];
                                            this.zzacw.zzgi();
                                            zzksVar3.zzaul = false;
                                            if (!z) {
                                                zzkrVar.zzatr[i2].zzaut = null;
                                            }
                                        }
                                        String zzb2 = this.zzacw.zzgf().isLoggable(2) ? zzjc().zzb(zzkrVar) : null;
                                        byte[] zza2 = zzjc().zza(zzkrVar);
                                        String str3 = zzey.zzahf.get();
                                        try {
                                            URL url = new URL(str3);
                                            Preconditions.checkArgument(!arrayList.isEmpty());
                                            if (this.zzarb != null) {
                                                this.zzacw.zzgf().zzis().log("Set uploading progress before finishing the previous upload");
                                            } else {
                                                this.zzarb = new ArrayList(arrayList);
                                            }
                                            this.zzacw.zzgg().zzake.set(currentTimeMillis);
                                            this.zzacw.zzgf().zziz().zzd("Uploading data. app, uncompressed size, data", zzkrVar.zzatr.length > 0 ? zzkrVar.zzatr[0].zzti : "?", Integer.valueOf(zza2.length), zzb2);
                                            this.zzaqx = true;
                                            zzfl zzkw = zzkw();
                                            zzju zzjuVar = new zzju(this, zzhr);
                                            zzkw.zzab();
                                            zzkw.zzch();
                                            Preconditions.checkNotNull(url);
                                            Preconditions.checkNotNull(zza2);
                                            Preconditions.checkNotNull(zzjuVar);
                                            zzkw.zzge().zzd(new zzfp(zzkw, zzhr, url, zza2, null, zzjuVar));
                                        } catch (MalformedURLException unused) {
                                            this.zzacw.zzgf().zzis().zze("Failed to parse upload URL. Not uploading. appId", zzfh.zzbl(zzhr), str3);
                                        }
                                    }
                                }
                            }
                            this.zzacw.zzgf().zziz().log("Network not connected, ignoring upload request");
                        }
                    }
                    zzld();
                }
                zziz = this.zzacw.zzgf().zzis();
                str2 = "Upload called in the client side when service should be used";
            }
            zziz.log(str2);
        } finally {
            this.zzaqy = false;
            zzle();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzlg() {
        zzfj zzis;
        Integer valueOf;
        Integer valueOf2;
        String str;
        zzab();
        zzkz();
        if (this.zzaqr) {
            return;
        }
        this.zzacw.zzgf().zzix().log("This instance being marked as an uploader");
        zzab();
        zzkz();
        if (zzlh() && zzlf()) {
            int zza2 = zza(this.zzara);
            int zzip = this.zzacw.zzfw().zzip();
            zzab();
            if (zza2 > zzip) {
                zzis = this.zzacw.zzgf().zzis();
                valueOf = Integer.valueOf(zza2);
                valueOf2 = Integer.valueOf(zzip);
                str = "Panic: can't downgrade version. Previous, current version";
            } else if (zza2 < zzip) {
                if (zza(zzip, this.zzara)) {
                    zzis = this.zzacw.zzgf().zziz();
                    valueOf = Integer.valueOf(zza2);
                    valueOf2 = Integer.valueOf(zzip);
                    str = "Storage version upgraded. Previous, current version";
                } else {
                    zzis = this.zzacw.zzgf().zzis();
                    valueOf = Integer.valueOf(zza2);
                    valueOf2 = Integer.valueOf(zzip);
                    str = "Storage version upgrade failed. Previous, current version";
                }
            }
            zzis.zze(str, valueOf, valueOf2);
        }
        this.zzaqr = true;
        zzld();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzli() {
        this.zzaqv++;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final zzgm zzlj() {
        return this.zzacw;
    }

    public final void zzm(boolean z) {
        zzld();
    }
}
