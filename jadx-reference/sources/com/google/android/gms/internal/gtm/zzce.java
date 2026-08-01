package com.google.android.gms.internal.gtm;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.HttpUtils;
import java.io.Closeable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzce extends zzbs implements Closeable {
    private static final String zza = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL, '%s' TEXT NOT NULL, '%s' INTEGER);", "hits2", "hit_id", "hit_time", "hit_url", "hit_string", "hit_app_id");
    private static final String zzb = String.format("SELECT MAX(%s) FROM %s WHERE 1;", "hit_time", "hits2");
    private final zzcd zzc;
    private final zzfo zzd;
    private final zzfo zze;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzce(zzbv zzbvVar) {
        super(zzbvVar);
        this.zzd = new zzfo(zzC());
        this.zze = new zzfo(zzC());
        this.zzc = new zzcd(this, zzbvVar.zza(), zzae());
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[IF]}, finally: {[IF, INVOKE] complete} */
    private final long zzad(String str, String[] strArr, long j) {
        Cursor cursor = null;
        try {
            try {
                cursor = zzf().rawQuery(str, strArr);
                if (cursor.moveToFirst()) {
                    return cursor.getLong(0);
                }
                if (cursor == null) {
                    return 0L;
                }
                cursor.close();
                return 0L;
            } catch (SQLiteException e) {
                zzL("Database error", str, e);
                throw e;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String zzae() {
        zzw();
        zzw();
        return "google_analytics_v4.db";
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        try {
            this.zzc.close();
        } catch (SQLiteException e) {
            zzK("Sql error closing database", e);
        } catch (IllegalStateException e2) {
            zzK("Error closing database", e2);
        }
    }

    public final void zzZ(List<Long> list) {
        Preconditions.checkNotNull(list);
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        if (list.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder("hit_id");
        sb.append(" in (");
        for (int i = 0; i < list.size(); i++) {
            Long l = list.get(i);
            if (l != null && l.longValue() != 0) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(l);
            } else {
                throw new SQLiteException("Invalid hit id");
            }
        }
        sb.append(")");
        String sb2 = sb.toString();
        try {
            SQLiteDatabase zzf = zzf();
            zzP("Deleting dispatched hits. count", Integer.valueOf(list.size()));
            int delete = zzf.delete("hits2", sb2, null);
            if (delete != list.size()) {
                zzU("Deleted fewer hits then expected", Integer.valueOf(list.size()), Integer.valueOf(delete), sb2);
            }
        } catch (SQLiteException e) {
            zzK("Error deleting hits", e);
            throw e;
        }
    }

    public final int zza() {
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        if (!this.zzd.zzc(86400000L)) {
            return 0;
        }
        this.zzd.zzb();
        zzO("Deleting stale hits (if any)");
        int delete = zzf().delete("hits2", "hit_time < ?", new String[]{Long.toString(zzC().currentTimeMillis() - 2592000000L)});
        zzP("Deleted stale hits, count", Integer.valueOf(delete));
        return delete;
    }

    public final void zzaa() {
        zzW();
        zzf().endTransaction();
    }

    public final void zzab() {
        zzW();
        zzf().setTransactionSuccessful();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzac() {
        return zzb() == 0;
    }

    public final long zzb() {
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        Cursor cursor = null;
        try {
            try {
                Cursor rawQuery = zzf().rawQuery("SELECT COUNT(*) FROM hits2", null);
                if (rawQuery.moveToFirst()) {
                    long j = rawQuery.getLong(0);
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    return j;
                }
                throw new SQLiteException("Database returned empty set");
            } catch (SQLiteException e) {
                zzL("Database error", "SELECT COUNT(*) FROM hits2", e);
                throw e;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    public final long zzc() {
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        return zzad(zzb, null, 0L);
    }

    @Override // com.google.android.gms.internal.gtm.zzbs
    protected final void zzd() {
    }

    public final long zze(long j, String str, String str2) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzW();
        com.google.android.gms.analytics.zzr.zzh();
        return zzad("SELECT hits_count FROM properties WHERE app_uid=? AND cid=? AND tid=?", new String[]{"0", str, str2}, 0L);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final SQLiteDatabase zzf() {
        try {
            return this.zzc.getWritableDatabase();
        } catch (SQLiteException e) {
            zzS("Error opening database", e);
            throw e;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x004b, code lost:
    
        if (r14.moveToFirst() != false) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x004d, code lost:
    
        r7 = r14.getLong(0);
        r4 = r14.getLong(1);
        r1 = r14.getString(2);
        r2 = r14.getString(3);
        r9 = r14.getInt(4);
        r3 = zzk(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x006c, code lost:
    
        if (android.text.TextUtils.isEmpty(r2) == false) goto L13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x006e, code lost:
    
        r6 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x007a, code lost:
    
        r0.add(new com.google.android.gms.internal.gtm.zzex(r16, r3, r4, r6, r7, r9, null));
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x008a, code lost:
    
        if (r14.moveToNext() != false) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0076, code lost:
    
        if (r2.startsWith("http:") != false) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0079, code lost:
    
        r6 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x008c, code lost:
    
        if (r14 == null) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x008e, code lost:
    
        r14.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0091, code lost:
    
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final List<zzex> zzj(long j) {
        Cursor query;
        Preconditions.checkArgument(j >= 0);
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        Cursor cursor = null;
        try {
            try {
                query = zzf().query("hits2", new String[]{"hit_id", "hit_time", "hit_string", "hit_url", "hit_app_id"}, null, null, null, null, String.format("%s ASC", "hit_id"), Long.toString(j));
            } catch (SQLiteException e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            ArrayList arrayList = new ArrayList();
        } catch (SQLiteException e2) {
            e = e2;
            cursor = query;
            zzK("Error loading hits from the database", e);
            throw e;
        } catch (Throwable th2) {
            th = th2;
            cursor = query;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    final Map<String, String> zzk(String str) {
        if (TextUtils.isEmpty(str)) {
            return new HashMap(0);
        }
        try {
            if (!str.startsWith("?")) {
                String valueOf = String.valueOf(str);
                str = valueOf.length() != 0 ? "?".concat(valueOf) : new String("?");
            }
            return HttpUtils.parse(new URI(str), "UTF-8");
        } catch (URISyntaxException e) {
            zzK("Error parsing hit parameters", e);
            return new HashMap(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final Map<String, String> zzl(String str) {
        if (TextUtils.isEmpty(str)) {
            return new HashMap(0);
        }
        try {
            String valueOf = String.valueOf(str);
            return HttpUtils.parse(new URI(valueOf.length() != 0 ? "?".concat(valueOf) : new String("?")), "UTF-8");
        } catch (URISyntaxException e) {
            zzK("Error parsing property parameters", e);
            return new HashMap(0);
        }
    }

    public final void zzm() {
        zzW();
        zzf().beginTransaction();
    }

    public final void zzn(long j) {
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        ArrayList arrayList = new ArrayList(1);
        Long valueOf = Long.valueOf(j);
        arrayList.add(valueOf);
        zzP("Deleting hit, id", valueOf);
        zzZ(arrayList);
    }
}
