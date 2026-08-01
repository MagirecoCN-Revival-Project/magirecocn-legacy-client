package com.google.android.gms.internal.measurement;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import android.util.Pair;
import androidx.collection.ArrayMap;
import com.adjust.sdk.Constants;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.time.DurationKt;
import kotlinx.coroutines.scheduling.WorkQueueKt;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzej extends zzjr {
    private static final String[] zzaew = {"last_bundled_timestamp", "ALTER TABLE events ADD COLUMN last_bundled_timestamp INTEGER;", "last_sampled_complex_event_id", "ALTER TABLE events ADD COLUMN last_sampled_complex_event_id INTEGER;", "last_sampling_rate", "ALTER TABLE events ADD COLUMN last_sampling_rate INTEGER;", "last_exempt_from_sampling", "ALTER TABLE events ADD COLUMN last_exempt_from_sampling INTEGER;"};
    private static final String[] zzaex = {FirebaseAnalytics.Param.ORIGIN, "ALTER TABLE user_attributes ADD COLUMN origin TEXT;"};
    private static final String[] zzaey = {"app_version", "ALTER TABLE apps ADD COLUMN app_version TEXT;", "app_store", "ALTER TABLE apps ADD COLUMN app_store TEXT;", "gmp_version", "ALTER TABLE apps ADD COLUMN gmp_version INTEGER;", "dev_cert_hash", "ALTER TABLE apps ADD COLUMN dev_cert_hash INTEGER;", "measurement_enabled", "ALTER TABLE apps ADD COLUMN measurement_enabled INTEGER;", "last_bundle_start_timestamp", "ALTER TABLE apps ADD COLUMN last_bundle_start_timestamp INTEGER;", "day", "ALTER TABLE apps ADD COLUMN day INTEGER;", "daily_public_events_count", "ALTER TABLE apps ADD COLUMN daily_public_events_count INTEGER;", "daily_events_count", "ALTER TABLE apps ADD COLUMN daily_events_count INTEGER;", "daily_conversions_count", "ALTER TABLE apps ADD COLUMN daily_conversions_count INTEGER;", "remote_config", "ALTER TABLE apps ADD COLUMN remote_config BLOB;", "config_fetched_time", "ALTER TABLE apps ADD COLUMN config_fetched_time INTEGER;", "failed_config_fetch_time", "ALTER TABLE apps ADD COLUMN failed_config_fetch_time INTEGER;", "app_version_int", "ALTER TABLE apps ADD COLUMN app_version_int INTEGER;", "firebase_instance_id", "ALTER TABLE apps ADD COLUMN firebase_instance_id TEXT;", "daily_error_events_count", "ALTER TABLE apps ADD COLUMN daily_error_events_count INTEGER;", "daily_realtime_events_count", "ALTER TABLE apps ADD COLUMN daily_realtime_events_count INTEGER;", "health_monitor_sample", "ALTER TABLE apps ADD COLUMN health_monitor_sample TEXT;", "android_id", "ALTER TABLE apps ADD COLUMN android_id INTEGER;", "adid_reporting_enabled", "ALTER TABLE apps ADD COLUMN adid_reporting_enabled INTEGER;", "ssaid_reporting_enabled", "ALTER TABLE apps ADD COLUMN ssaid_reporting_enabled INTEGER;"};
    private static final String[] zzaez = {"realtime", "ALTER TABLE raw_events ADD COLUMN realtime INTEGER;"};
    private static final String[] zzafa = {"has_realtime", "ALTER TABLE queue ADD COLUMN has_realtime INTEGER;", "retry_count", "ALTER TABLE queue ADD COLUMN retry_count INTEGER;"};
    private static final String[] zzafb = {"previous_install_count", "ALTER TABLE app2 ADD COLUMN previous_install_count INTEGER;"};
    private final zzem zzafc;
    private final zzjn zzafd;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzej(zzjs zzjsVar) {
        super(zzjsVar);
        this.zzafd = new zzjn(zzbt());
        this.zzafc = new zzem(this, getContext(), "google_app_measurement.db");
    }

    private final long zza(String str, String[] strArr) {
        Cursor cursor = null;
        try {
            try {
                Cursor rawQuery = getWritableDatabase().rawQuery(str, strArr);
                if (!rawQuery.moveToFirst()) {
                    throw new SQLiteException("Database returned empty set");
                }
                long j = rawQuery.getLong(0);
                if (rawQuery != null) {
                    rawQuery.close();
                }
                return j;
            } catch (SQLiteException e) {
                zzgf().zzis().zze("Database error", str, e);
                throw e;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[IF]}, finally: {[IF, INVOKE] complete} */
    private final long zza(String str, String[] strArr, long j) {
        Cursor cursor = null;
        try {
            try {
                cursor = getWritableDatabase().rawQuery(str, strArr);
                if (cursor.moveToFirst()) {
                    return cursor.getLong(0);
                }
                if (cursor != null) {
                    cursor.close();
                }
                return j;
            } catch (SQLiteException e) {
                zzgf().zzis().zze("Database error", str, e);
                throw e;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private final Object zza(Cursor cursor, int i) {
        int type = cursor.getType(i);
        if (type == 0) {
            zzgf().zzis().log("Loaded invalid null value from database");
            return null;
        }
        if (type == 1) {
            return Long.valueOf(cursor.getLong(i));
        }
        if (type == 2) {
            return Double.valueOf(cursor.getDouble(i));
        }
        if (type == 3) {
            return cursor.getString(i);
        }
        if (type != 4) {
            zzgf().zzis().zzg("Loaded invalid unknown value type, ignoring it", Integer.valueOf(type));
            return null;
        }
        zzgf().zzis().log("Loaded invalid blob type value, ignoring it");
        return null;
    }

    private static void zza(ContentValues contentValues, String str, Object obj) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(obj);
        if (obj instanceof String) {
            contentValues.put(str, (String) obj);
        } else if (obj instanceof Long) {
            contentValues.put(str, (Long) obj);
        } else {
            if (!(obj instanceof Double)) {
                throw new IllegalArgumentException("Invalid value type");
            }
            contentValues.put(str, (Double) obj);
        }
    }

    private final boolean zza(String str, int i, zzkg zzkgVar) {
        zzch();
        zzab();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzkgVar);
        if (TextUtils.isEmpty(zzkgVar.zzasc)) {
            zzgf().zziv().zzd("Event filter had no event name. Audience definition ignored. appId, audienceId, filterId", zzfh.zzbl(str), Integer.valueOf(i), String.valueOf(zzkgVar.zzasb));
            return false;
        }
        try {
            int zzvv = zzkgVar.zzvv();
            byte[] bArr = new byte[zzvv];
            zzaby zzb = zzaby.zzb(bArr, 0, zzvv);
            zzkgVar.zza(zzb);
            zzb.zzvn();
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", str);
            contentValues.put("audience_id", Integer.valueOf(i));
            contentValues.put("filter_id", zzkgVar.zzasb);
            contentValues.put("event_name", zzkgVar.zzasc);
            contentValues.put("data", bArr);
            try {
                if (getWritableDatabase().insertWithOnConflict("event_filters", null, contentValues, 5) != -1) {
                    return true;
                }
                zzgf().zzis().zzg("Failed to insert event filter (got -1). appId", zzfh.zzbl(str));
                return true;
            } catch (SQLiteException e) {
                zzgf().zzis().zze("Error storing event filter. appId", zzfh.zzbl(str), e);
                return false;
            }
        } catch (IOException e2) {
            zzgf().zzis().zze("Configuration loss. Failed to serialize event filter. appId", zzfh.zzbl(str), e2);
            return false;
        }
    }

    private final boolean zza(String str, int i, zzkj zzkjVar) {
        zzch();
        zzab();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzkjVar);
        if (TextUtils.isEmpty(zzkjVar.zzasr)) {
            zzgf().zziv().zzd("Property filter had no property name. Audience definition ignored. appId, audienceId, filterId", zzfh.zzbl(str), Integer.valueOf(i), String.valueOf(zzkjVar.zzasb));
            return false;
        }
        try {
            int zzvv = zzkjVar.zzvv();
            byte[] bArr = new byte[zzvv];
            zzaby zzb = zzaby.zzb(bArr, 0, zzvv);
            zzkjVar.zza(zzb);
            zzb.zzvn();
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", str);
            contentValues.put("audience_id", Integer.valueOf(i));
            contentValues.put("filter_id", zzkjVar.zzasb);
            contentValues.put("property_name", zzkjVar.zzasr);
            contentValues.put("data", bArr);
            try {
                if (getWritableDatabase().insertWithOnConflict("property_filters", null, contentValues, 5) != -1) {
                    return true;
                }
                zzgf().zzis().zzg("Failed to insert property filter (got -1). appId", zzfh.zzbl(str));
                return false;
            } catch (SQLiteException e) {
                zzgf().zzis().zze("Error storing property filter. appId", zzfh.zzbl(str), e);
                return false;
            }
        } catch (IOException e2) {
            zzgf().zzis().zze("Configuration loss. Failed to serialize property filter. appId", zzfh.zzbl(str), e2);
            return false;
        }
    }

    private final boolean zza(String str, List<Integer> list) {
        Preconditions.checkNotEmpty(str);
        zzch();
        zzab();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            long zza = zza("select count(1) from audience_filter_values where app_id=?", new String[]{str});
            int max = Math.max(0, Math.min(2000, zzgh().zzb(str, zzey.zzahu)));
            if (zza <= max) {
                return false;
            }
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                Integer num = list.get(i);
                if (num == null || !(num instanceof Integer)) {
                    return false;
                }
                arrayList.add(Integer.toString(num.intValue()));
            }
            String join = TextUtils.join(",", arrayList);
            StringBuilder sb = new StringBuilder(String.valueOf(join).length() + 2);
            sb.append("(");
            sb.append(join);
            sb.append(")");
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder(String.valueOf(sb2).length() + 140);
            sb3.append("audience_id in (select audience_id from audience_filter_values where app_id=? and audience_id not in ");
            sb3.append(sb2);
            sb3.append(" order by rowid desc limit -1 offset ?)");
            return writableDatabase.delete("audience_filter_values", sb3.toString(), new String[]{str, Integer.toString(max)}) > 0;
        } catch (SQLiteException e) {
            zzgf().zzis().zze("Database error querying filters. appId", zzfh.zzbl(str), e);
            return false;
        }
    }

    private final boolean zzhz() {
        return getContext().getDatabasePath("google_app_measurement.db").exists();
    }

    public final void beginTransaction() {
        zzch();
        getWritableDatabase().beginTransaction();
    }

    public final void endTransaction() {
        zzch();
        getWritableDatabase().endTransaction();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final SQLiteDatabase getWritableDatabase() {
        zzab();
        try {
            return this.zzafc.getWritableDatabase();
        } catch (SQLiteException e) {
            zzgf().zziv().zzg("Error opening database", e);
            throw e;
        }
    }

    public final void setTransactionSuccessful() {
        zzch();
        getWritableDatabase().setTransactionSuccessful();
    }

    public final long zza(zzks zzksVar) throws IOException {
        long zzc;
        zzab();
        zzch();
        Preconditions.checkNotNull(zzksVar);
        Preconditions.checkNotEmpty(zzksVar.zzti);
        try {
            int zzvv = zzksVar.zzvv();
            byte[] bArr = new byte[zzvv];
            zzaby zzb = zzaby.zzb(bArr, 0, zzvv);
            zzksVar.zza(zzb);
            zzb.zzvn();
            zzkc zzgc = zzgc();
            Preconditions.checkNotNull(bArr);
            zzgc.zzab();
            MessageDigest messageDigest = zzkc.getMessageDigest(Constants.MD5);
            if (messageDigest == null) {
                zzgc.zzgf().zzis().log("Failed to get MD5");
                zzc = 0;
            } else {
                zzc = zzkc.zzc(messageDigest.digest(bArr));
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", zzksVar.zzti);
            contentValues.put("metadata_fingerprint", Long.valueOf(zzc));
            contentValues.put("metadata", bArr);
            try {
                getWritableDatabase().insertWithOnConflict("raw_events_metadata", null, contentValues, 4);
                return zzc;
            } catch (SQLiteException e) {
                zzgf().zzis().zze("Error storing raw event metadata. appId", zzfh.zzbl(zzksVar.zzti), e);
                throw e;
            }
        } catch (IOException e2) {
            zzgf().zzis().zze("Data loss. Failed to serialize event metadata. appId", zzfh.zzbl(zzksVar.zzti), e2);
            throw e2;
        }
    }

    /* JADX WARN: Not initialized variable reg: 1, insn: 0x008c: MOVE (r0 I:??[OBJECT, ARRAY]) = (r1 I:??[OBJECT, ARRAY]), block:B:32:0x008c */
    /* JADX WARN: Removed duplicated region for block: B:34:0x008f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Pair<zzkp, Long> zza(String str, Long l) {
        Cursor cursor;
        Cursor cursor2;
        zzab();
        zzch();
        Cursor cursor3 = null;
        try {
            try {
                cursor = getWritableDatabase().rawQuery("select main_event, children_to_process from main_event_params where app_id=? and event_id=?", new String[]{str, String.valueOf(l)});
                try {
                    if (!cursor.moveToFirst()) {
                        zzgf().zziz().log("Main event not found");
                        if (cursor != null) {
                            cursor.close();
                        }
                        return null;
                    }
                    byte[] blob = cursor.getBlob(0);
                    Long valueOf = Long.valueOf(cursor.getLong(1));
                    zzabx zza = zzabx.zza(blob, 0, blob.length);
                    zzkp zzkpVar = new zzkp();
                    try {
                        zzkpVar.zzb(zza);
                        Pair<zzkp, Long> create = Pair.create(zzkpVar, valueOf);
                        if (cursor != null) {
                            cursor.close();
                        }
                        return create;
                    } catch (IOException e) {
                        zzgf().zzis().zzd("Failed to merge main event. appId, eventId", zzfh.zzbl(str), l, e);
                        if (cursor != null) {
                            cursor.close();
                        }
                        return null;
                    }
                } catch (SQLiteException e2) {
                    e = e2;
                    zzgf().zzis().zzg("Error selecting main event", e);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                }
            } catch (Throwable th) {
                th = th;
                cursor3 = cursor2;
                if (cursor3 != null) {
                    cursor3.close();
                }
                throw th;
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursor = null;
        } catch (Throwable th2) {
            th = th2;
            if (cursor3 != null) {
            }
            throw th;
        }
    }

    public final zzek zza(long j, String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        Preconditions.checkNotEmpty(str);
        zzab();
        zzch();
        String[] strArr = {str};
        zzek zzekVar = new zzek();
        Cursor cursor = null;
        try {
            try {
                SQLiteDatabase writableDatabase = getWritableDatabase();
                Cursor query = writableDatabase.query("apps", new String[]{"day", "daily_events_count", "daily_public_events_count", "daily_conversions_count", "daily_error_events_count", "daily_realtime_events_count"}, "app_id=?", new String[]{str}, null, null, null);
                if (!query.moveToFirst()) {
                    zzgf().zziv().zzg("Not updating daily counts, app is not known. appId", zzfh.zzbl(str));
                    if (query != null) {
                        query.close();
                    }
                    return zzekVar;
                }
                if (query.getLong(0) == j) {
                    zzekVar.zzaff = query.getLong(1);
                    zzekVar.zzafe = query.getLong(2);
                    zzekVar.zzafg = query.getLong(3);
                    zzekVar.zzafh = query.getLong(4);
                    zzekVar.zzafi = query.getLong(5);
                }
                if (z) {
                    zzekVar.zzaff++;
                }
                if (z2) {
                    zzekVar.zzafe++;
                }
                if (z3) {
                    zzekVar.zzafg++;
                }
                if (z4) {
                    zzekVar.zzafh++;
                }
                if (z5) {
                    zzekVar.zzafi++;
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put("day", Long.valueOf(j));
                contentValues.put("daily_public_events_count", Long.valueOf(zzekVar.zzafe));
                contentValues.put("daily_events_count", Long.valueOf(zzekVar.zzaff));
                contentValues.put("daily_conversions_count", Long.valueOf(zzekVar.zzafg));
                contentValues.put("daily_error_events_count", Long.valueOf(zzekVar.zzafh));
                contentValues.put("daily_realtime_events_count", Long.valueOf(zzekVar.zzafi));
                writableDatabase.update("apps", contentValues, "app_id=?", strArr);
                if (query != null) {
                    query.close();
                }
                return zzekVar;
            } catch (SQLiteException e) {
                zzgf().zzis().zze("Error updating daily counts. appId", zzfh.zzbl(str), e);
                if (0 != 0) {
                    cursor.close();
                }
                return zzekVar;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    public final void zza(zzdy zzdyVar) {
        Preconditions.checkNotNull(zzdyVar);
        zzab();
        zzch();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzdyVar.zzah());
        contentValues.put("app_instance_id", zzdyVar.getAppInstanceId());
        contentValues.put("gmp_app_id", zzdyVar.getGmpAppId());
        contentValues.put("resettable_device_id_hash", zzdyVar.zzgk());
        contentValues.put("last_bundle_index", Long.valueOf(zzdyVar.zzgs()));
        contentValues.put("last_bundle_start_timestamp", Long.valueOf(zzdyVar.zzgm()));
        contentValues.put("last_bundle_end_timestamp", Long.valueOf(zzdyVar.zzgn()));
        contentValues.put("app_version", zzdyVar.zzag());
        contentValues.put("app_store", zzdyVar.zzgp());
        contentValues.put("gmp_version", Long.valueOf(zzdyVar.zzgq()));
        contentValues.put("dev_cert_hash", Long.valueOf(zzdyVar.zzgr()));
        contentValues.put("measurement_enabled", Boolean.valueOf(zzdyVar.isMeasurementEnabled()));
        contentValues.put("day", Long.valueOf(zzdyVar.zzgw()));
        contentValues.put("daily_public_events_count", Long.valueOf(zzdyVar.zzgx()));
        contentValues.put("daily_events_count", Long.valueOf(zzdyVar.zzgy()));
        contentValues.put("daily_conversions_count", Long.valueOf(zzdyVar.zzgz()));
        contentValues.put("config_fetched_time", Long.valueOf(zzdyVar.zzgt()));
        contentValues.put("failed_config_fetch_time", Long.valueOf(zzdyVar.zzgu()));
        contentValues.put("app_version_int", Long.valueOf(zzdyVar.zzgo()));
        contentValues.put("firebase_instance_id", zzdyVar.zzgl());
        contentValues.put("daily_error_events_count", Long.valueOf(zzdyVar.zzhb()));
        contentValues.put("daily_realtime_events_count", Long.valueOf(zzdyVar.zzha()));
        contentValues.put("health_monitor_sample", zzdyVar.zzhc());
        contentValues.put("android_id", Long.valueOf(zzdyVar.zzhe()));
        contentValues.put("adid_reporting_enabled", Boolean.valueOf(zzdyVar.zzhf()));
        contentValues.put("ssaid_reporting_enabled", Boolean.valueOf(zzdyVar.zzhg()));
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (writableDatabase.update("apps", contentValues, "app_id = ?", new String[]{zzdyVar.zzah()}) == 0 && writableDatabase.insertWithOnConflict("apps", null, contentValues, 5) == -1) {
                zzgf().zzis().zzg("Failed to insert/update app (got -1). appId", zzfh.zzbl(zzdyVar.zzah()));
            }
        } catch (SQLiteException e) {
            zzgf().zzis().zze("Error storing app. appId", zzfh.zzbl(zzdyVar.zzah()), e);
        }
    }

    public final void zza(zzes zzesVar) {
        Preconditions.checkNotNull(zzesVar);
        zzab();
        zzch();
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzesVar.zzti);
        contentValues.put("name", zzesVar.name);
        contentValues.put("lifetime_count", Long.valueOf(zzesVar.zzafs));
        contentValues.put("current_bundle_count", Long.valueOf(zzesVar.zzaft));
        contentValues.put("last_fire_timestamp", Long.valueOf(zzesVar.zzafu));
        contentValues.put("last_bundled_timestamp", Long.valueOf(zzesVar.zzafv));
        contentValues.put("last_sampled_complex_event_id", zzesVar.zzafw);
        contentValues.put("last_sampling_rate", zzesVar.zzafx);
        contentValues.put("last_exempt_from_sampling", (zzesVar.zzafy == null || !zzesVar.zzafy.booleanValue()) ? null : 1L);
        try {
            if (getWritableDatabase().insertWithOnConflict("events", null, contentValues, 5) == -1) {
                zzgf().zzis().zzg("Failed to insert/update event aggregates (got -1). appId", zzfh.zzbl(zzesVar.zzti));
            }
        } catch (SQLiteException e) {
            zzgf().zzis().zze("Error storing event aggregates. appId", zzfh.zzbl(zzesVar.zzti), e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zza(String str, zzkf[] zzkfVarArr) {
        boolean z;
        zzfj zziv;
        String str2;
        Object zzbl;
        Integer num;
        zzch();
        zzab();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzkfVarArr);
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            zzch();
            zzab();
            Preconditions.checkNotEmpty(str);
            SQLiteDatabase writableDatabase2 = getWritableDatabase();
            writableDatabase2.delete("property_filters", "app_id=?", new String[]{str});
            writableDatabase2.delete("event_filters", "app_id=?", new String[]{str});
            for (zzkf zzkfVar : zzkfVarArr) {
                zzch();
                zzab();
                Preconditions.checkNotEmpty(str);
                Preconditions.checkNotNull(zzkfVar);
                Preconditions.checkNotNull(zzkfVar.zzarz);
                Preconditions.checkNotNull(zzkfVar.zzary);
                if (zzkfVar.zzarx == null) {
                    zzgf().zziv().zzg("Audience with no ID. appId", zzfh.zzbl(str));
                } else {
                    int intValue = zzkfVar.zzarx.intValue();
                    zzkg[] zzkgVarArr = zzkfVar.zzarz;
                    int length = zzkgVarArr.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            for (zzkj zzkjVar : zzkfVar.zzary) {
                                if (zzkjVar.zzasb == null) {
                                    zziv = zzgf().zziv();
                                    str2 = "Property filter with no ID. Audience definition ignored. appId, audienceId";
                                    zzbl = zzfh.zzbl(str);
                                    num = zzkfVar.zzarx;
                                }
                            }
                            zzkg[] zzkgVarArr2 = zzkfVar.zzarz;
                            int length2 = zzkgVarArr2.length;
                            int i2 = 0;
                            while (true) {
                                if (i2 >= length2) {
                                    z = true;
                                    break;
                                } else {
                                    if (!zza(str, intValue, zzkgVarArr2[i2])) {
                                        z = false;
                                        break;
                                    }
                                    i2++;
                                }
                            }
                            if (z) {
                                zzkj[] zzkjVarArr = zzkfVar.zzary;
                                int length3 = zzkjVarArr.length;
                                int i3 = 0;
                                while (true) {
                                    if (i3 >= length3) {
                                        break;
                                    }
                                    if (!zza(str, intValue, zzkjVarArr[i3])) {
                                        z = false;
                                        break;
                                    }
                                    i3++;
                                }
                            }
                            if (!z) {
                                zzch();
                                zzab();
                                Preconditions.checkNotEmpty(str);
                                SQLiteDatabase writableDatabase3 = getWritableDatabase();
                                writableDatabase3.delete("property_filters", "app_id=? and audience_id=?", new String[]{str, String.valueOf(intValue)});
                                writableDatabase3.delete("event_filters", "app_id=? and audience_id=?", new String[]{str, String.valueOf(intValue)});
                            }
                        } else {
                            if (zzkgVarArr[i].zzasb == null) {
                                zziv = zzgf().zziv();
                                str2 = "Event filter with no ID. Audience definition ignored. appId, audienceId";
                                zzbl = zzfh.zzbl(str);
                                num = zzkfVar.zzarx;
                                break;
                            }
                            i++;
                        }
                    }
                    zziv.zze(str2, zzbl, num);
                }
            }
            ArrayList arrayList = new ArrayList();
            for (zzkf zzkfVar2 : zzkfVarArr) {
                arrayList.add(zzkfVar2.zzarx);
            }
            zza(str, arrayList);
            writableDatabase.setTransactionSuccessful();
        } finally {
            writableDatabase.endTransaction();
        }
    }

    public final boolean zza(zzee zzeeVar) {
        Preconditions.checkNotNull(zzeeVar);
        zzab();
        zzch();
        if (zzh(zzeeVar.packageName, zzeeVar.zzaeq.name) == null && zza("SELECT COUNT(1) FROM conditional_properties WHERE app_id=?", new String[]{zzeeVar.packageName}) >= 1000) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzeeVar.packageName);
        contentValues.put(FirebaseAnalytics.Param.ORIGIN, zzeeVar.origin);
        contentValues.put("name", zzeeVar.zzaeq.name);
        zza(contentValues, FirebaseAnalytics.Param.VALUE, zzeeVar.zzaeq.getValue());
        contentValues.put("active", Boolean.valueOf(zzeeVar.active));
        contentValues.put("trigger_event_name", zzeeVar.triggerEventName);
        contentValues.put("trigger_timeout", Long.valueOf(zzeeVar.triggerTimeout));
        zzgc();
        contentValues.put("timed_out_event", zzkc.zza(zzeeVar.zzaer));
        contentValues.put("creation_timestamp", Long.valueOf(zzeeVar.creationTimestamp));
        zzgc();
        contentValues.put("triggered_event", zzkc.zza(zzeeVar.zzaes));
        contentValues.put("triggered_timestamp", Long.valueOf(zzeeVar.zzaeq.zzarl));
        contentValues.put("time_to_live", Long.valueOf(zzeeVar.timeToLive));
        zzgc();
        contentValues.put("expired_event", zzkc.zza(zzeeVar.zzaet));
        try {
            if (getWritableDatabase().insertWithOnConflict("conditional_properties", null, contentValues, 5) == -1) {
                zzgf().zzis().zzg("Failed to insert/update conditional user property (got -1)", zzfh.zzbl(zzeeVar.packageName));
            }
        } catch (SQLiteException e) {
            zzgf().zzis().zze("Error storing conditional user property", zzfh.zzbl(zzeeVar.packageName), e);
        }
        return true;
    }

    public final boolean zza(zzer zzerVar, long j, boolean z) {
        zzfj zzis;
        Object zzbl;
        String str;
        ContentValues contentValues;
        zzab();
        zzch();
        Preconditions.checkNotNull(zzerVar);
        Preconditions.checkNotEmpty(zzerVar.zzti);
        zzkp zzkpVar = new zzkp();
        zzkpVar.zzato = Long.valueOf(zzerVar.zzafq);
        zzkpVar.zzatm = new zzkq[zzerVar.zzafr.size()];
        Iterator<String> it = zzerVar.zzafr.iterator();
        int i = 0;
        while (it.hasNext()) {
            String next = it.next();
            zzkq zzkqVar = new zzkq();
            int i2 = i + 1;
            zzkpVar.zzatm[i] = zzkqVar;
            zzkqVar.name = next;
            zzjc().zza(zzkqVar, zzerVar.zzafr.get(next));
            i = i2;
        }
        try {
            int zzvv = zzkpVar.zzvv();
            byte[] bArr = new byte[zzvv];
            zzaby zzb = zzaby.zzb(bArr, 0, zzvv);
            zzkpVar.zza(zzb);
            zzb.zzvn();
            zzgf().zziz().zze("Saving event, name, data size", zzgb().zzbi(zzerVar.name), Integer.valueOf(zzvv));
            contentValues = new ContentValues();
            contentValues.put("app_id", zzerVar.zzti);
            contentValues.put("name", zzerVar.name);
            contentValues.put(AppMeasurement.Param.TIMESTAMP, Long.valueOf(zzerVar.timestamp));
            contentValues.put("metadata_fingerprint", Long.valueOf(j));
            contentValues.put("data", bArr);
            contentValues.put("realtime", Integer.valueOf(z ? 1 : 0));
        } catch (IOException e) {
            e = e;
            zzis = zzgf().zzis();
            zzbl = zzfh.zzbl(zzerVar.zzti);
            str = "Data loss. Failed to serialize event params/data. appId";
        }
        try {
            if (getWritableDatabase().insert("raw_events", null, contentValues) != -1) {
                return true;
            }
            zzgf().zzis().zzg("Failed to insert raw event (got -1). appId", zzfh.zzbl(zzerVar.zzti));
            return false;
        } catch (SQLiteException e2) {
            e = e2;
            zzis = zzgf().zzis();
            zzbl = zzfh.zzbl(zzerVar.zzti);
            str = "Error storing raw event. appId";
            zzis.zze(str, zzbl, e);
            return false;
        }
    }

    public final boolean zza(zzkb zzkbVar) {
        Preconditions.checkNotNull(zzkbVar);
        zzab();
        zzch();
        if (zzh(zzkbVar.zzti, zzkbVar.name) == null) {
            if (zzkc.zzcb(zzkbVar.name)) {
                if (zza("select count(1) from user_attributes where app_id=? and name not like '!_%' escape '!'", new String[]{zzkbVar.zzti}) >= 25) {
                    return false;
                }
            } else if (zza("select count(1) from user_attributes where app_id=? and origin=? AND name like '!_%' escape '!'", new String[]{zzkbVar.zzti, zzkbVar.origin}) >= 25) {
                return false;
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", zzkbVar.zzti);
        contentValues.put(FirebaseAnalytics.Param.ORIGIN, zzkbVar.origin);
        contentValues.put("name", zzkbVar.name);
        contentValues.put("set_timestamp", Long.valueOf(zzkbVar.zzarl));
        zza(contentValues, FirebaseAnalytics.Param.VALUE, zzkbVar.value);
        try {
            if (getWritableDatabase().insertWithOnConflict("user_attributes", null, contentValues, 5) == -1) {
                zzgf().zzis().zzg("Failed to insert/update user property (got -1). appId", zzfh.zzbl(zzkbVar.zzti));
            }
        } catch (SQLiteException e) {
            zzgf().zzis().zze("Error storing user property. appId", zzfh.zzbl(zzkbVar.zzti), e);
        }
        return true;
    }

    public final boolean zza(zzks zzksVar, boolean z) {
        zzfj zzis;
        Object zzbl;
        String str;
        ContentValues contentValues;
        zzab();
        zzch();
        Preconditions.checkNotNull(zzksVar);
        Preconditions.checkNotEmpty(zzksVar.zzti);
        Preconditions.checkNotNull(zzksVar.zzaty);
        zzht();
        long currentTimeMillis = zzbt().currentTimeMillis();
        if (zzksVar.zzaty.longValue() < currentTimeMillis - zzeg.zzhl() || zzksVar.zzaty.longValue() > zzeg.zzhl() + currentTimeMillis) {
            zzgf().zziv().zzd("Storing bundle outside of the max uploading time span. appId, now, timestamp", zzfh.zzbl(zzksVar.zzti), Long.valueOf(currentTimeMillis), zzksVar.zzaty);
        }
        try {
            int zzvv = zzksVar.zzvv();
            byte[] bArr = new byte[zzvv];
            zzaby zzb = zzaby.zzb(bArr, 0, zzvv);
            zzksVar.zza(zzb);
            zzb.zzvn();
            byte[] zza = zzgc().zza(bArr);
            zzgf().zziz().zzg("Saving bundle, size", Integer.valueOf(zza.length));
            contentValues = new ContentValues();
            contentValues.put("app_id", zzksVar.zzti);
            contentValues.put("bundle_end_timestamp", zzksVar.zzaty);
            contentValues.put("data", zza);
            contentValues.put("has_realtime", Integer.valueOf(z ? 1 : 0));
            if (zzksVar.zzauv != null) {
                contentValues.put("retry_count", zzksVar.zzauv);
            }
        } catch (IOException e) {
            e = e;
            zzis = zzgf().zzis();
            zzbl = zzfh.zzbl(zzksVar.zzti);
            str = "Data loss. Failed to serialize bundle. appId";
        }
        try {
            if (getWritableDatabase().insert("queue", null, contentValues) != -1) {
                return true;
            }
            zzgf().zzis().zzg("Failed to insert bundle (got -1). appId", zzfh.zzbl(zzksVar.zzti));
            return false;
        } catch (SQLiteException e2) {
            e = e2;
            zzis = zzgf().zzis();
            zzbl = zzfh.zzbl(zzksVar.zzti);
            str = "Error storing bundle. appId";
            zzis.zze(str, zzbl, e);
            return false;
        }
    }

    public final boolean zza(String str, Long l, long j, zzkp zzkpVar) {
        zzab();
        zzch();
        Preconditions.checkNotNull(zzkpVar);
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(l);
        try {
            int zzvv = zzkpVar.zzvv();
            byte[] bArr = new byte[zzvv];
            zzaby zzb = zzaby.zzb(bArr, 0, zzvv);
            zzkpVar.zza(zzb);
            zzb.zzvn();
            zzgf().zziz().zze("Saving complex main event, appId, data size", zzgb().zzbi(str), Integer.valueOf(zzvv));
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_id", str);
            contentValues.put("event_id", l);
            contentValues.put("children_to_process", Long.valueOf(j));
            contentValues.put("main_event", bArr);
            try {
                if (getWritableDatabase().insertWithOnConflict("main_event_params", null, contentValues, 5) != -1) {
                    return true;
                }
                zzgf().zzis().zzg("Failed to insert complex main event (got -1). appId", zzfh.zzbl(str));
                return false;
            } catch (SQLiteException e) {
                zzgf().zzis().zze("Error storing complex main event. appId", zzfh.zzbl(str), e);
                return false;
            }
        } catch (IOException e2) {
            zzgf().zzis().zzd("Data loss. Failed to serialize event params/data. appId, eventId", zzfh.zzbl(str), l, e2);
            return false;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: long */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:25:0x005b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String zzab(long j) {
        Cursor cursor;
        zzab();
        zzch();
        Cursor cursor2 = null;
        try {
            try {
                cursor = getWritableDatabase().rawQuery("select app_id from apps where app_id in (select distinct app_id from raw_events) and config_fetched_time < ? order by failed_config_fetch_time limit 1;", new String[]{String.valueOf(j)});
                try {
                    if (cursor.moveToFirst()) {
                        String string = cursor.getString(0);
                        if (cursor != null) {
                            cursor.close();
                        }
                        return string;
                    }
                    zzgf().zziz().log("No expired configs for apps with pending events");
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                } catch (SQLiteException e) {
                    e = e;
                    zzgf().zzis().zzg("Error selecting expired configs", e);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                }
            } catch (Throwable th) {
                th = th;
                cursor2 = j;
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

    /* JADX WARN: Removed duplicated region for block: B:26:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00c3 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00c5 A[EDGE_INSN: B:41:0x00c5->B:25:0x00c5 BREAK  A[LOOP:0: B:17:0x0054->B:40:?], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final List<Pair<zzks, Long>> zzb(String str, int i, int i2) {
        zzfj zzis;
        String str2;
        Object zzbl;
        byte[] zzb;
        zzab();
        zzch();
        Preconditions.checkArgument(i > 0);
        Preconditions.checkArgument(i2 > 0);
        Preconditions.checkNotEmpty(str);
        Cursor cursor = null;
        try {
            try {
                Cursor query = getWritableDatabase().query("queue", new String[]{"rowid", "data", "retry_count"}, "app_id=?", new String[]{str}, null, null, "rowid", String.valueOf(i));
                if (!query.moveToFirst()) {
                    List<Pair<zzks, Long>> emptyList = Collections.emptyList();
                    if (query != null) {
                        query.close();
                    }
                    return emptyList;
                }
                ArrayList arrayList = new ArrayList();
                int i3 = 0;
                do {
                    long j = query.getLong(0);
                    try {
                        zzb = zzgc().zzb(query.getBlob(1));
                    } catch (IOException e) {
                        e = e;
                        zzis = zzgf().zzis();
                        str2 = "Failed to unzip queued bundle. appId";
                        zzbl = zzfh.zzbl(str);
                    }
                    if (!arrayList.isEmpty() && zzb.length + i3 > i2) {
                        break;
                    }
                    zzabx zza = zzabx.zza(zzb, 0, zzb.length);
                    zzks zzksVar = new zzks();
                    try {
                        zzksVar.zzb(zza);
                        if (!query.isNull(2)) {
                            zzksVar.zzauv = Integer.valueOf(query.getInt(2));
                        }
                        i3 += zzb.length;
                        arrayList.add(Pair.create(zzksVar, Long.valueOf(j)));
                    } catch (IOException e2) {
                        e = e2;
                        zzis = zzgf().zzis();
                        str2 = "Failed to merge queued bundle. appId";
                        zzbl = zzfh.zzbl(str);
                        zzis.zze(str2, zzbl, e);
                        if (query.moveToNext()) {
                        }
                        if (query != null) {
                        }
                        return arrayList;
                    }
                    if (query.moveToNext()) {
                        break;
                    }
                } while (i3 <= i2);
                if (query != null) {
                    query.close();
                }
                return arrayList;
            } catch (Throwable th) {
                if (0 != 0) {
                    cursor.close();
                }
                throw th;
            }
        } catch (SQLiteException e3) {
            zzgf().zzis().zze("Error querying bundles. appId", zzfh.zzbl(str), e3);
            List<Pair<zzks, Long>> emptyList2 = Collections.emptyList();
            if (0 != 0) {
                cursor.close();
            }
            return emptyList2;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:51:0x0090, code lost:
    
        zzgf().zzis().zzg("Read more than the max allowed user properties, ignoring excess", 1000);
     */
    /* JADX WARN: Removed duplicated region for block: B:44:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0127  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final List<zzkb> zzb(String str, String str2, String str3) {
        String str4;
        Cursor cursor;
        ArrayList arrayList;
        Preconditions.checkNotEmpty(str);
        zzab();
        zzch();
        ArrayList arrayList2 = new ArrayList();
        Cursor cursor2 = null;
        try {
            try {
                try {
                    arrayList = new ArrayList(3);
                } catch (Throwable th) {
                    th = th;
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    throw th;
                }
            } catch (SQLiteException e) {
                e = e;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            arrayList.add(str);
            StringBuilder sb = new StringBuilder("app_id=?");
            if (TextUtils.isEmpty(str2)) {
                str4 = str2;
            } else {
                str4 = str2;
                try {
                    arrayList.add(str4);
                    sb.append(" and origin=?");
                } catch (SQLiteException e2) {
                    e = e2;
                    cursor = null;
                    zzgf().zzis().zzd("(2)Error querying user properties", zzfh.zzbl(str), str4, e);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                }
            }
            if (!TextUtils.isEmpty(str3)) {
                arrayList.add(String.valueOf(str3).concat("*"));
                sb.append(" and name glob ?");
            }
            cursor = getWritableDatabase().query("user_attributes", new String[]{"name", "set_timestamp", FirebaseAnalytics.Param.VALUE, FirebaseAnalytics.Param.ORIGIN}, sb.toString(), (String[]) arrayList.toArray(new String[arrayList.size()]), null, null, "rowid", "1001");
            try {
                if (!cursor.moveToFirst()) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    return arrayList2;
                }
                while (true) {
                    if (arrayList2.size() >= 1000) {
                        break;
                    }
                    String string = cursor.getString(0);
                    long j = cursor.getLong(1);
                    try {
                        Object zza = zza(cursor, 2);
                        String string2 = cursor.getString(3);
                        if (zza == null) {
                            try {
                                zzgf().zzis().zzd("(2)Read invalid user property value, ignoring it", zzfh.zzbl(str), string2, str3);
                            } catch (SQLiteException e3) {
                                e = e3;
                                str4 = string2;
                                zzgf().zzis().zzd("(2)Error querying user properties", zzfh.zzbl(str), str4, e);
                                if (cursor != null) {
                                }
                                return null;
                            }
                        } else {
                            arrayList2.add(new zzkb(str, string2, string, j, zza));
                        }
                        if (!cursor.moveToNext()) {
                            break;
                        }
                        str4 = string2;
                    } catch (SQLiteException e4) {
                        e = e4;
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }
                return arrayList2;
            } catch (SQLiteException e5) {
                e = e5;
            } catch (Throwable th3) {
                th = th3;
                cursor2 = cursor;
                if (cursor2 != null) {
                }
                throw th;
            }
        } catch (SQLiteException e6) {
            e = e6;
            str4 = str2;
            cursor = null;
            zzgf().zzis().zzd("(2)Error querying user properties", zzfh.zzbl(str), str4, e);
            if (cursor != null) {
            }
            return null;
        }
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[IF]}, finally: {[IF, INVOKE] complete} */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0052, code lost:
    
        zzgf().zzis().zzg("Read more than the max allowed conditional properties, ignoring extra", 1000);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final List<zzee> zzb(String str, String[] strArr) {
        zzab();
        zzch();
        ArrayList arrayList = new ArrayList();
        Cursor cursor = null;
        try {
            try {
                cursor = getWritableDatabase().query("conditional_properties", new String[]{"app_id", FirebaseAnalytics.Param.ORIGIN, "name", FirebaseAnalytics.Param.VALUE, "active", "trigger_event_name", "trigger_timeout", "timed_out_event", "creation_timestamp", "triggered_event", "triggered_timestamp", "time_to_live", "expired_event"}, str, strArr, null, null, "rowid", "1001");
                if (!cursor.moveToFirst()) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    return arrayList;
                }
                while (true) {
                    if (arrayList.size() >= 1000) {
                        break;
                    }
                    String string = cursor.getString(0);
                    String string2 = cursor.getString(1);
                    String string3 = cursor.getString(2);
                    Object zza = zza(cursor, 3);
                    boolean z = cursor.getInt(4) != 0;
                    String string4 = cursor.getString(5);
                    long j = cursor.getLong(6);
                    zzew zzewVar = (zzew) zzgc().zza(cursor.getBlob(7), zzew.CREATOR);
                    arrayList.add(new zzee(string, string2, new zzjz(string3, cursor.getLong(10), zza, string2), cursor.getLong(8), z, string4, zzewVar, j, (zzew) zzgc().zza(cursor.getBlob(9), zzew.CREATOR), cursor.getLong(11), (zzew) zzgc().zza(cursor.getBlob(12), zzew.CREATOR)));
                    if (!cursor.moveToNext()) {
                        break;
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }
                return arrayList;
            } catch (SQLiteException e) {
                zzgf().zzis().zzg("Error querying conditional user property value", e);
                List<zzee> emptyList = Collections.emptyList();
                if (cursor != null) {
                    cursor.close();
                }
                return emptyList;
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00a1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final List<zzkb> zzba(String str) {
        Cursor cursor;
        Preconditions.checkNotEmpty(str);
        zzab();
        zzch();
        ArrayList arrayList = new ArrayList();
        Cursor cursor2 = null;
        try {
            cursor = getWritableDatabase().query("user_attributes", new String[]{"name", FirebaseAnalytics.Param.ORIGIN, "set_timestamp", FirebaseAnalytics.Param.VALUE}, "app_id=?", new String[]{str}, null, null, "rowid", "1000");
            try {
                try {
                    if (!cursor.moveToFirst()) {
                        if (cursor != null) {
                            cursor.close();
                        }
                        return arrayList;
                    }
                    do {
                        String string = cursor.getString(0);
                        String string2 = cursor.getString(1);
                        if (string2 == null) {
                            string2 = "";
                        }
                        String str2 = string2;
                        long j = cursor.getLong(2);
                        Object zza = zza(cursor, 3);
                        if (zza == null) {
                            zzgf().zzis().zzg("Read invalid user property value, ignoring it. appId", zzfh.zzbl(str));
                        } else {
                            arrayList.add(new zzkb(str, str2, string, j, zza));
                        }
                    } while (cursor.moveToNext());
                    if (cursor != null) {
                        cursor.close();
                    }
                    return arrayList;
                } catch (SQLiteException e) {
                    e = e;
                    zzgf().zzis().zze("Error querying user properties. appId", zzfh.zzbl(str), e);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
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

    /* JADX WARN: Removed duplicated region for block: B:22:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0172 A[Catch: SQLiteException -> 0x019d, all -> 0x01c6, TryCatch #1 {all -> 0x01c6, blocks: (B:14:0x0067, B:16:0x00c8, B:20:0x00d2, B:23:0x011c, B:26:0x0152, B:28:0x015d, B:32:0x0167, B:34:0x0172, B:38:0x017a, B:40:0x0186, B:46:0x014e, B:47:0x0117, B:50:0x01af), top: B:2:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0186 A[Catch: SQLiteException -> 0x019d, all -> 0x01c6, TRY_LEAVE, TryCatch #1 {all -> 0x01c6, blocks: (B:14:0x0067, B:16:0x00c8, B:20:0x00d2, B:23:0x011c, B:26:0x0152, B:28:0x015d, B:32:0x0167, B:34:0x0172, B:38:0x017a, B:40:0x0186, B:46:0x014e, B:47:0x0117, B:50:0x01af), top: B:2:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0199  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x014e A[Catch: SQLiteException -> 0x019d, all -> 0x01c6, TryCatch #1 {all -> 0x01c6, blocks: (B:14:0x0067, B:16:0x00c8, B:20:0x00d2, B:23:0x011c, B:26:0x0152, B:28:0x015d, B:32:0x0167, B:34:0x0172, B:38:0x017a, B:40:0x0186, B:46:0x014e, B:47:0x0117, B:50:0x01af), top: B:2:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0117 A[Catch: SQLiteException -> 0x019d, all -> 0x01c6, TryCatch #1 {all -> 0x01c6, blocks: (B:14:0x0067, B:16:0x00c8, B:20:0x00d2, B:23:0x011c, B:26:0x0152, B:28:0x015d, B:32:0x0167, B:34:0x0172, B:38:0x017a, B:40:0x0186, B:46:0x014e, B:47:0x0117, B:50:0x01af), top: B:2:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x01ca  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final zzdy zzbb(String str) {
        Cursor cursor;
        boolean z;
        boolean z2;
        boolean z3;
        Preconditions.checkNotEmpty(str);
        zzab();
        zzch();
        Cursor cursor2 = null;
        try {
            try {
                z = true;
                cursor = getWritableDatabase().query("apps", new String[]{"app_instance_id", "gmp_app_id", "resettable_device_id_hash", "last_bundle_index", "last_bundle_start_timestamp", "last_bundle_end_timestamp", "app_version", "app_store", "gmp_version", "dev_cert_hash", "measurement_enabled", "day", "daily_public_events_count", "daily_events_count", "daily_conversions_count", "config_fetched_time", "failed_config_fetch_time", "app_version_int", "firebase_instance_id", "daily_error_events_count", "daily_realtime_events_count", "health_monitor_sample", "android_id", "adid_reporting_enabled", "ssaid_reporting_enabled"}, "app_id=?", new String[]{str}, null, null, null);
            } catch (Throwable th) {
                th = th;
            }
            try {
                if (!cursor.moveToFirst()) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                }
                try {
                    zzdy zzdyVar = new zzdy(this.zzajy.zzlj(), str);
                    zzdyVar.zzak(cursor.getString(0));
                    zzdyVar.zzal(cursor.getString(1));
                    zzdyVar.zzam(cursor.getString(2));
                    zzdyVar.zzr(cursor.getLong(3));
                    zzdyVar.zzm(cursor.getLong(4));
                    zzdyVar.zzn(cursor.getLong(5));
                    zzdyVar.setAppVersion(cursor.getString(6));
                    zzdyVar.zzao(cursor.getString(7));
                    zzdyVar.zzp(cursor.getLong(8));
                    zzdyVar.zzq(cursor.getLong(9));
                    if (!cursor.isNull(10) && cursor.getInt(10) == 0) {
                        z2 = false;
                        zzdyVar.setMeasurementEnabled(z2);
                        zzdyVar.zzu(cursor.getLong(11));
                        zzdyVar.zzv(cursor.getLong(12));
                        zzdyVar.zzw(cursor.getLong(13));
                        zzdyVar.zzx(cursor.getLong(14));
                        zzdyVar.zzs(cursor.getLong(15));
                        zzdyVar.zzt(cursor.getLong(16));
                        zzdyVar.zzo(!cursor.isNull(17) ? -2147483648L : cursor.getInt(17));
                        zzdyVar.zzan(cursor.getString(18));
                        zzdyVar.zzz(cursor.getLong(19));
                        zzdyVar.zzy(cursor.getLong(20));
                        zzdyVar.zzap(cursor.getString(21));
                        zzdyVar.zzaa(!cursor.isNull(22) ? 0L : cursor.getLong(22));
                        if (!cursor.isNull(23) && cursor.getInt(23) == 0) {
                            z3 = false;
                            zzdyVar.zzd(z3);
                            if (!cursor.isNull(24) && cursor.getInt(24) == 0) {
                                z = false;
                            }
                            zzdyVar.zze(z);
                            zzdyVar.zzgj();
                            if (cursor.moveToNext()) {
                                zzgf().zzis().zzg("Got multiple records for app, expected one. appId", zzfh.zzbl(str));
                            }
                            if (cursor != null) {
                                cursor.close();
                            }
                            return zzdyVar;
                        }
                        z3 = true;
                        zzdyVar.zzd(z3);
                        if (!cursor.isNull(24)) {
                            z = false;
                        }
                        zzdyVar.zze(z);
                        zzdyVar.zzgj();
                        if (cursor.moveToNext()) {
                        }
                        if (cursor != null) {
                        }
                        return zzdyVar;
                    }
                    z2 = true;
                    zzdyVar.setMeasurementEnabled(z2);
                    zzdyVar.zzu(cursor.getLong(11));
                    zzdyVar.zzv(cursor.getLong(12));
                    zzdyVar.zzw(cursor.getLong(13));
                    zzdyVar.zzx(cursor.getLong(14));
                    zzdyVar.zzs(cursor.getLong(15));
                    zzdyVar.zzt(cursor.getLong(16));
                    zzdyVar.zzo(!cursor.isNull(17) ? -2147483648L : cursor.getInt(17));
                    zzdyVar.zzan(cursor.getString(18));
                    zzdyVar.zzz(cursor.getLong(19));
                    zzdyVar.zzy(cursor.getLong(20));
                    zzdyVar.zzap(cursor.getString(21));
                    zzdyVar.zzaa(!cursor.isNull(22) ? 0L : cursor.getLong(22));
                    if (!cursor.isNull(23)) {
                        z3 = false;
                        zzdyVar.zzd(z3);
                        if (!cursor.isNull(24)) {
                        }
                        zzdyVar.zze(z);
                        zzdyVar.zzgj();
                        if (cursor.moveToNext()) {
                        }
                        if (cursor != null) {
                        }
                        return zzdyVar;
                    }
                    z3 = true;
                    zzdyVar.zzd(z3);
                    if (!cursor.isNull(24)) {
                    }
                    zzdyVar.zze(z);
                    zzdyVar.zzgj();
                    if (cursor.moveToNext()) {
                    }
                    if (cursor != null) {
                    }
                    return zzdyVar;
                } catch (SQLiteException e) {
                    e = e;
                    zzgf().zzis().zze("Error querying app. appId", zzfh.zzbl(str), e);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                }
            } catch (SQLiteException e2) {
                e = e2;
            } catch (Throwable th2) {
                th = th2;
                cursor2 = cursor;
                if (cursor2 != null) {
                    cursor2.close();
                }
                throw th;
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursor = null;
        } catch (Throwable th3) {
            th = th3;
            if (cursor2 != null) {
            }
            throw th;
        }
    }

    public final long zzbc(String str) {
        Preconditions.checkNotEmpty(str);
        zzab();
        zzch();
        try {
            return getWritableDatabase().delete("raw_events", "rowid in (select rowid from raw_events where app_id=? order by rowid desc limit -1 offset ?)", new String[]{str, String.valueOf(Math.max(0, Math.min(DurationKt.NANOS_IN_MILLIS, zzgh().zzb(str, zzey.zzahe))))});
        } catch (SQLiteException e) {
            zzgf().zzis().zze("Error deleting over the limit events. appId", zzfh.zzbl(str), e);
            return 0L;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0073  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final byte[] zzbd(String str) {
        Cursor cursor;
        Preconditions.checkNotEmpty(str);
        zzab();
        zzch();
        Cursor cursor2 = null;
        try {
            cursor = getWritableDatabase().query("apps", new String[]{"remote_config"}, "app_id=?", new String[]{str}, null, null, null);
            try {
                try {
                    if (!cursor.moveToFirst()) {
                        if (cursor != null) {
                            cursor.close();
                        }
                        return null;
                    }
                    byte[] blob = cursor.getBlob(0);
                    if (cursor.moveToNext()) {
                        zzgf().zzis().zzg("Got multiple records for app config, expected one. appId", zzfh.zzbl(str));
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                    return blob;
                } catch (SQLiteException e) {
                    e = e;
                    zzgf().zzis().zze("Error querying remote config. appId", zzfh.zzbl(str), e);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
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

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0098  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Map<Integer, zzkt> zzbe(String str) {
        Cursor cursor;
        zzch();
        zzab();
        Preconditions.checkNotEmpty(str);
        Cursor cursor2 = null;
        try {
            cursor = getWritableDatabase().query("audience_filter_values", new String[]{"audience_id", "current_results"}, "app_id=?", new String[]{str}, null, null, null);
            try {
                try {
                    if (!cursor.moveToFirst()) {
                        if (cursor != null) {
                            cursor.close();
                        }
                        return null;
                    }
                    ArrayMap arrayMap = new ArrayMap();
                    do {
                        int i = cursor.getInt(0);
                        byte[] blob = cursor.getBlob(1);
                        zzabx zza = zzabx.zza(blob, 0, blob.length);
                        zzkt zzktVar = new zzkt();
                        try {
                            zzktVar.zzb(zza);
                            arrayMap.put(Integer.valueOf(i), zzktVar);
                        } catch (IOException e) {
                            zzgf().zzis().zzd("Failed to merge filter results. appId, audienceId, error", zzfh.zzbl(str), Integer.valueOf(i), e);
                        }
                    } while (cursor.moveToNext());
                    if (cursor != null) {
                        cursor.close();
                    }
                    return arrayMap;
                } catch (SQLiteException e2) {
                    e = e2;
                    zzgf().zzis().zze("Database error querying filter results. appId", zzfh.zzbl(str), e);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                }
            } catch (Throwable th) {
                th = th;
                cursor2 = cursor;
                if (cursor2 != null) {
                    cursor2.close();
                }
                throw th;
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursor = null;
        } catch (Throwable th2) {
            th = th2;
            if (cursor2 != null) {
            }
            throw th;
        }
    }

    public final long zzbf(String str) {
        Preconditions.checkNotEmpty(str);
        return zza("select count(1) from events where app_id=? and name not like '!_%' escape '!'", new String[]{str}, 0L);
    }

    public final List<zzee> zzc(String str, String str2, String str3) {
        Preconditions.checkNotEmpty(str);
        zzab();
        zzch();
        ArrayList arrayList = new ArrayList(3);
        arrayList.add(str);
        StringBuilder sb = new StringBuilder("app_id=?");
        if (!TextUtils.isEmpty(str2)) {
            arrayList.add(str2);
            sb.append(" and origin=?");
        }
        if (!TextUtils.isEmpty(str3)) {
            arrayList.add(String.valueOf(str3).concat("*"));
            sb.append(" and name glob ?");
        }
        return zzb(sb.toString(), (String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzc(List<Long> list) {
        zzab();
        zzch();
        Preconditions.checkNotNull(list);
        Preconditions.checkNotZero(list.size());
        if (zzhz()) {
            String join = TextUtils.join(",", list);
            StringBuilder sb = new StringBuilder(String.valueOf(join).length() + 2);
            sb.append("(");
            sb.append(join);
            sb.append(")");
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder(String.valueOf(sb2).length() + 80);
            sb3.append("SELECT COUNT(1) FROM queue WHERE rowid IN ");
            sb3.append(sb2);
            sb3.append(" AND retry_count =  2147483647 LIMIT 1");
            if (zza(sb3.toString(), (String[]) null) > 0) {
                zzgf().zziv().log("The number of upload retries exceeds the limit. Will remain unchanged.");
            }
            try {
                SQLiteDatabase writableDatabase = getWritableDatabase();
                StringBuilder sb4 = new StringBuilder(String.valueOf(sb2).length() + WorkQueueKt.MASK);
                sb4.append("UPDATE queue SET retry_count = IFNULL(retry_count, 0) + 1 WHERE rowid IN ");
                sb4.append(sb2);
                sb4.append(" AND (retry_count IS NULL OR retry_count < 2147483647)");
                writableDatabase.execSQL(sb4.toString());
            } catch (SQLiteException e) {
                zzgf().zzis().zzg("Error incrementing retry count. error", e);
            }
        }
    }

    /* JADX WARN: Not initialized variable reg: 14, insn: 0x0109: MOVE (r16 I:??[OBJECT, ARRAY]) = (r14 I:??[OBJECT, ARRAY]), block:B:62:0x0109 */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0104  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final zzes zzf(String str, String str2) {
        Cursor cursor;
        Cursor cursor2;
        Cursor cursor3;
        Boolean valueOf;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzab();
        zzch();
        Cursor cursor4 = null;
        try {
            try {
                cursor = getWritableDatabase().query("events", new String[]{"lifetime_count", "current_bundle_count", "last_fire_timestamp", "last_bundled_timestamp", "last_sampled_complex_event_id", "last_sampling_rate", "last_exempt_from_sampling"}, "app_id=? and name=?", new String[]{str, str2}, null, null, null);
                try {
                    if (!cursor.moveToFirst()) {
                        if (cursor != null) {
                            cursor.close();
                        }
                        return null;
                    }
                    long j = cursor.getLong(0);
                    long j2 = cursor.getLong(1);
                    long j3 = cursor.getLong(2);
                    long j4 = cursor.isNull(3) ? 0L : cursor.getLong(3);
                    Long valueOf2 = cursor.isNull(4) ? null : Long.valueOf(cursor.getLong(4));
                    Long valueOf3 = cursor.isNull(5) ? null : Long.valueOf(cursor.getLong(5));
                    if (cursor.isNull(6)) {
                        valueOf = null;
                    } else {
                        try {
                            valueOf = Boolean.valueOf(cursor.getLong(6) == 1);
                        } catch (SQLiteException e) {
                            e = e;
                            zzgf().zzis().zzd("Error querying events. appId", zzfh.zzbl(str), zzgb().zzbi(str2), e);
                            if (cursor != null) {
                                cursor.close();
                            }
                            return null;
                        }
                    }
                    cursor3 = cursor;
                    try {
                        zzes zzesVar = new zzes(str, str2, j, j2, j3, j4, valueOf2, valueOf3, valueOf);
                        if (cursor3.moveToNext()) {
                            zzgf().zzis().zzg("Got multiple records for event aggregates, expected one. appId", zzfh.zzbl(str));
                        }
                        if (cursor3 != null) {
                            cursor3.close();
                        }
                        return zzesVar;
                    } catch (SQLiteException e2) {
                        e = e2;
                        cursor = cursor3;
                        zzgf().zzis().zzd("Error querying events. appId", zzfh.zzbl(str), zzgb().zzbi(str2), e);
                        if (cursor != null) {
                        }
                        return null;
                    } catch (Throwable th) {
                        th = th;
                        cursor4 = cursor3;
                        if (cursor4 != null) {
                            cursor4.close();
                        }
                        throw th;
                    }
                } catch (SQLiteException e3) {
                    e = e3;
                } catch (Throwable th2) {
                    th = th2;
                    cursor3 = cursor;
                }
            } catch (Throwable th3) {
                th = th3;
                cursor4 = cursor2;
            }
        } catch (SQLiteException e4) {
            e = e4;
            cursor = null;
        } catch (Throwable th4) {
            th = th4;
        }
    }

    public final void zzg(String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzab();
        zzch();
        try {
            zzgf().zziz().zzg("Deleted user attribute rows", Integer.valueOf(getWritableDatabase().delete("user_attributes", "app_id=? and name=?", new String[]{str, str2})));
        } catch (SQLiteException e) {
            zzgf().zzis().zzd("Error deleting user attribute. appId", zzfh.zzbl(str), zzgb().zzbk(str2), e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x00a9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final zzkb zzh(String str, String str2) {
        Cursor cursor;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzab();
        zzch();
        Cursor cursor2 = null;
        try {
            cursor = getWritableDatabase().query("user_attributes", new String[]{"set_timestamp", FirebaseAnalytics.Param.VALUE, FirebaseAnalytics.Param.ORIGIN}, "app_id=? and name=?", new String[]{str, str2}, null, null, null);
            try {
                if (!cursor.moveToFirst()) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                }
                try {
                    try {
                        zzkb zzkbVar = new zzkb(str, cursor.getString(2), str2, cursor.getLong(0), zza(cursor, 1));
                        if (cursor.moveToNext()) {
                            zzgf().zzis().zzg("Got multiple records for user property, expected one. appId", zzfh.zzbl(str));
                        }
                        if (cursor != null) {
                            cursor.close();
                        }
                        return zzkbVar;
                    } catch (SQLiteException e) {
                        e = e;
                        zzgf().zzis().zzd("Error querying user property. appId", zzfh.zzbl(str), zzgb().zzbk(str2), e);
                        if (cursor != null) {
                            cursor.close();
                        }
                        return null;
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
            } catch (Throwable th2) {
                th = th2;
                cursor2 = cursor;
                if (cursor2 != null) {
                }
                throw th;
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursor = null;
        } catch (Throwable th3) {
            th = th3;
            if (cursor2 != null) {
            }
            throw th;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzjr
    protected final boolean zzhh() {
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0041  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String zzhr() {
        Throwable th;
        Cursor cursor;
        try {
            cursor = getWritableDatabase().rawQuery("select app_id from queue order by has_realtime desc, rowid asc limit 1;", null);
            try {
                try {
                    if (!cursor.moveToFirst()) {
                        if (cursor != null) {
                            cursor.close();
                        }
                        return null;
                    }
                    String string = cursor.getString(0);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return string;
                } catch (SQLiteException e) {
                    e = e;
                    zzgf().zzis().zzg("Database error getting next bundle app id", e);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                }
            } catch (Throwable th2) {
                th = th2;
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        } catch (SQLiteException e2) {
            e = e2;
            cursor = null;
        } catch (Throwable th3) {
            th = th3;
            cursor = null;
            if (cursor != null) {
            }
            throw th;
        }
    }

    public final boolean zzhs() {
        return zza("select count(1) > 0 from queue where has_realtime = 1", (String[]) null) != 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzht() {
        int delete;
        zzab();
        zzch();
        if (zzhz()) {
            long j = zzgg().zzakg.get();
            long elapsedRealtime = zzbt().elapsedRealtime();
            if (Math.abs(elapsedRealtime - j) > zzey.zzahn.get().longValue()) {
                zzgg().zzakg.set(elapsedRealtime);
                zzab();
                zzch();
                if (!zzhz() || (delete = getWritableDatabase().delete("queue", "abs(bundle_end_timestamp - ?) > cast(? as integer)", new String[]{String.valueOf(zzbt().currentTimeMillis()), String.valueOf(zzeg.zzhl())})) <= 0) {
                    return;
                }
                zzgf().zziz().zzg("Deleted stale rows. rowsDeleted", Integer.valueOf(delete));
            }
        }
    }

    public final long zzhu() {
        return zza("select max(bundle_end_timestamp) from queue", (String[]) null, 0L);
    }

    public final long zzhv() {
        return zza("select max(timestamp) from raw_events", (String[]) null, 0L);
    }

    public final boolean zzhw() {
        return zza("select count(1) > 0 from raw_events", (String[]) null) != 0;
    }

    public final boolean zzhx() {
        return zza("select count(1) > 0 from raw_events where realtime = 1", (String[]) null) != 0;
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[IF]}, finally: {[IF, INVOKE] complete} */
    public final long zzhy() {
        Cursor cursor = null;
        try {
            try {
                cursor = getWritableDatabase().rawQuery("select rowid from raw_events order by rowid desc limit 1;", null);
                if (!cursor.moveToFirst()) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    return -1L;
                }
                long j = cursor.getLong(0);
                if (cursor != null) {
                    cursor.close();
                }
                return j;
            } catch (SQLiteException e) {
                zzgf().zzis().zzg("Error querying raw events", e);
                if (cursor != null) {
                    cursor.close();
                }
                return -1L;
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0125  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final zzee zzi(String str, String str2) {
        Cursor cursor;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzab();
        zzch();
        Cursor cursor2 = null;
        try {
            try {
                cursor = getWritableDatabase().query("conditional_properties", new String[]{FirebaseAnalytics.Param.ORIGIN, FirebaseAnalytics.Param.VALUE, "active", "trigger_event_name", "trigger_timeout", "timed_out_event", "creation_timestamp", "triggered_event", "triggered_timestamp", "time_to_live", "expired_event"}, "app_id=? and name=?", new String[]{str, str2}, null, null, null);
            } catch (Throwable th) {
                th = th;
            }
            try {
                if (!cursor.moveToFirst()) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                }
                String string = cursor.getString(0);
                try {
                    Object zza = zza(cursor, 1);
                    boolean z = cursor.getInt(2) != 0;
                    zzee zzeeVar = new zzee(str, string, new zzjz(str2, cursor.getLong(8), zza, string), cursor.getLong(6), z, cursor.getString(3), (zzew) zzgc().zza(cursor.getBlob(5), zzew.CREATOR), cursor.getLong(4), (zzew) zzgc().zza(cursor.getBlob(7), zzew.CREATOR), cursor.getLong(9), (zzew) zzgc().zza(cursor.getBlob(10), zzew.CREATOR));
                    if (cursor.moveToNext()) {
                        zzgf().zzis().zze("Got multiple records for conditional property, expected one", zzfh.zzbl(str), zzgb().zzbk(str2));
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                    return zzeeVar;
                } catch (SQLiteException e) {
                    e = e;
                    zzgf().zzis().zzd("Error querying conditional property", zzfh.zzbl(str), zzgb().zzbk(str2), e);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                }
            } catch (SQLiteException e2) {
                e = e2;
            } catch (Throwable th2) {
                th = th2;
                cursor2 = cursor;
                if (cursor2 != null) {
                    cursor2.close();
                }
                throw th;
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursor = null;
        } catch (Throwable th3) {
            th = th3;
            if (cursor2 != null) {
            }
            throw th;
        }
    }

    public final int zzj(String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzab();
        zzch();
        try {
            return getWritableDatabase().delete("conditional_properties", "app_id=? and name=?", new String[]{str, str2});
        } catch (SQLiteException e) {
            zzgf().zzis().zzd("Error deleting conditional property", zzfh.zzbl(str), zzgb().zzbk(str2), e);
            return 0;
        }
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:42:0x0093 */
    /* JADX DEBUG: Type inference failed for r3v4. Raw type applied. Possible types: V */
    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00b2  */
    /* JADX WARN: Type inference failed for: r9v0 */
    /* JADX WARN: Type inference failed for: r9v1, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r9v2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Map<Integer, List<zzkg>> zzk(String str, String str2) {
        Cursor cursor;
        zzch();
        zzab();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        ArrayMap arrayMap = new ArrayMap();
        ?? r9 = 0;
        try {
            try {
                cursor = getWritableDatabase().query("event_filters", new String[]{"audience_id", "data"}, "app_id=? AND event_name=?", new String[]{str, str2}, null, null, null);
                try {
                    if (!cursor.moveToFirst()) {
                        Map<Integer, List<zzkg>> emptyMap = Collections.emptyMap();
                        if (cursor != null) {
                            cursor.close();
                        }
                        return emptyMap;
                    }
                    do {
                        byte[] blob = cursor.getBlob(1);
                        zzabx zza = zzabx.zza(blob, 0, blob.length);
                        zzkg zzkgVar = new zzkg();
                        try {
                            zzkgVar.zzb(zza);
                            int i = cursor.getInt(0);
                            List list = (List) arrayMap.get(Integer.valueOf(i));
                            if (list == null) {
                                list = new ArrayList();
                                arrayMap.put(Integer.valueOf(i), list);
                            }
                            list.add(zzkgVar);
                        } catch (IOException e) {
                            zzgf().zzis().zze("Failed to merge filter. appId", zzfh.zzbl(str), e);
                        }
                    } while (cursor.moveToNext());
                    if (cursor != null) {
                        cursor.close();
                    }
                    return arrayMap;
                } catch (SQLiteException e2) {
                    e = e2;
                    zzgf().zzis().zze("Database error querying filters. appId", zzfh.zzbl(str), e);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                }
            } catch (Throwable th) {
                th = th;
                r9 = str2;
                if (r9 != 0) {
                    r9.close();
                }
                throw th;
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursor = null;
        } catch (Throwable th2) {
            th = th2;
            if (r9 != 0) {
            }
            throw th;
        }
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:42:0x0093 */
    /* JADX DEBUG: Type inference failed for r3v4. Raw type applied. Possible types: V */
    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00b2  */
    /* JADX WARN: Type inference failed for: r9v0 */
    /* JADX WARN: Type inference failed for: r9v1, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r9v2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Map<Integer, List<zzkj>> zzl(String str, String str2) {
        Cursor cursor;
        zzch();
        zzab();
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        ArrayMap arrayMap = new ArrayMap();
        ?? r9 = 0;
        try {
            try {
                cursor = getWritableDatabase().query("property_filters", new String[]{"audience_id", "data"}, "app_id=? AND property_name=?", new String[]{str, str2}, null, null, null);
                try {
                    if (!cursor.moveToFirst()) {
                        Map<Integer, List<zzkj>> emptyMap = Collections.emptyMap();
                        if (cursor != null) {
                            cursor.close();
                        }
                        return emptyMap;
                    }
                    do {
                        byte[] blob = cursor.getBlob(1);
                        zzabx zza = zzabx.zza(blob, 0, blob.length);
                        zzkj zzkjVar = new zzkj();
                        try {
                            zzkjVar.zzb(zza);
                            int i = cursor.getInt(0);
                            List list = (List) arrayMap.get(Integer.valueOf(i));
                            if (list == null) {
                                list = new ArrayList();
                                arrayMap.put(Integer.valueOf(i), list);
                            }
                            list.add(zzkjVar);
                        } catch (IOException e) {
                            zzgf().zzis().zze("Failed to merge filter", zzfh.zzbl(str), e);
                        }
                    } while (cursor.moveToNext());
                    if (cursor != null) {
                        cursor.close();
                    }
                    return arrayMap;
                } catch (SQLiteException e2) {
                    e = e2;
                    zzgf().zzis().zze("Database error querying filters. appId", zzfh.zzbl(str), e);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                }
            } catch (Throwable th) {
                th = th;
                r9 = str2;
                if (r9 != 0) {
                    r9.close();
                }
                throw th;
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursor = null;
        } catch (Throwable th2) {
            th = th2;
            if (r9 != 0) {
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final long zzm(String str, String str2) {
        long zza;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzab();
        zzch();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.beginTransaction();
        long j = 0;
        try {
            StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 32);
            sb.append("select ");
            sb.append(str2);
            sb.append(" from app2 where app_id=?");
            try {
                try {
                    zza = zza(sb.toString(), new String[]{str}, -1L);
                    if (zza == -1) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("app_id", str);
                        contentValues.put("first_open_count", (Integer) 0);
                        contentValues.put("previous_install_count", (Integer) 0);
                        if (writableDatabase.insertWithOnConflict("app2", null, contentValues, 5) == -1) {
                            zzgf().zzis().zze("Failed to insert column (got -1). appId", zzfh.zzbl(str), str2);
                            writableDatabase.endTransaction();
                            return -1L;
                        }
                        zza = 0;
                    }
                } catch (SQLiteException e) {
                    e = e;
                }
                try {
                    ContentValues contentValues2 = new ContentValues();
                    contentValues2.put("app_id", str);
                    contentValues2.put(str2, Long.valueOf(1 + zza));
                    if (writableDatabase.update("app2", contentValues2, "app_id = ?", new String[]{str}) == 0) {
                        zzgf().zzis().zze("Failed to update column (got 0). appId", zzfh.zzbl(str), str2);
                        writableDatabase.endTransaction();
                        return -1L;
                    }
                    writableDatabase.setTransactionSuccessful();
                    writableDatabase.endTransaction();
                    return zza;
                } catch (SQLiteException e2) {
                    e = e2;
                    j = zza;
                    zzgf().zzis().zzd("Error inserting column. appId", zzfh.zzbl(str), str2, e);
                    writableDatabase.endTransaction();
                    return j;
                }
            } catch (Throwable th) {
                th = th;
                writableDatabase.endTransaction();
                throw th;
            }
        } catch (SQLiteException e3) {
            e = e3;
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
