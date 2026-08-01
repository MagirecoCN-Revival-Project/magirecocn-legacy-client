package com.google.android.gms.internal.measurement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteFullException;
import android.os.Parcel;
import android.os.SystemClock;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.measurement.AppMeasurement;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class zzfd extends zzhi {
    private final zzfe zzaip;
    private boolean zzaiq;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzfd(zzgm zzgmVar) {
        super(zzgmVar);
        this.zzaip = new zzfe(this, getContext(), "google_app_measurement_local.db");
    }

    private final SQLiteDatabase getWritableDatabase() throws SQLiteException {
        if (this.zzaiq) {
            return null;
        }
        SQLiteDatabase writableDatabase = this.zzaip.getWritableDatabase();
        if (writableDatabase != null) {
            return writableDatabase;
        }
        this.zzaiq = true;
        return null;
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:74:0x005c */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:75:0x0059 */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x012c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x012c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00da A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r13v0 */
    /* JADX WARN: Type inference failed for: r13v1 */
    /* JADX WARN: Type inference failed for: r13v2, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r13v3 */
    /* JADX WARN: Type inference failed for: r13v4, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r13v5 */
    /* JADX WARN: Type inference failed for: r13v6 */
    /* JADX WARN: Type inference failed for: r13v7 */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1, types: [int, boolean] */
    /* JADX WARN: Type inference failed for: r3v10 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final boolean zza(int i, byte[] bArr) {
        SQLiteDatabase sQLiteDatabase;
        ?? r13;
        zzfs();
        zzab();
        ?? r3 = 0;
        if (this.zzaiq) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(AppMeasurement.Param.TYPE, Integer.valueOf(i));
        contentValues.put("entry", bArr);
        int i2 = 0;
        int i3 = 5;
        for (int i4 = 5; i2 < i4; i4 = 5) {
            Cursor cursor = null;
            cursor = null;
            cursor = null;
            cursor = null;
            cursor = null;
            Cursor cursor2 = null;
            cursor = null;
            SQLiteDatabase sQLiteDatabase2 = null;
            try {
                sQLiteDatabase = getWritableDatabase();
                try {
                    if (sQLiteDatabase == null) {
                        try {
                            this.zzaiq = true;
                            if (sQLiteDatabase != null) {
                                sQLiteDatabase.close();
                            }
                            return r3;
                        } catch (SQLiteFullException e) {
                            e = e;
                            try {
                                zzgf().zzis().zzg("Error writing entry to local database", e);
                                this.zzaiq = true;
                                if (cursor != null) {
                                    cursor.close();
                                }
                                if (sQLiteDatabase == null) {
                                    i2++;
                                    r3 = 0;
                                }
                                sQLiteDatabase.close();
                                i2++;
                                r3 = 0;
                            } catch (Throwable th) {
                                th = th;
                                if (cursor != null) {
                                    cursor.close();
                                }
                                if (sQLiteDatabase != null) {
                                    sQLiteDatabase.close();
                                }
                                throw th;
                            }
                        } catch (SQLiteException e2) {
                            e = e2;
                            r13 = 0;
                            sQLiteDatabase2 = sQLiteDatabase;
                            r13 = r13;
                            if (sQLiteDatabase2 != null) {
                                try {
                                    if (sQLiteDatabase2.inTransaction()) {
                                        sQLiteDatabase2.endTransaction();
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    sQLiteDatabase = sQLiteDatabase2;
                                    cursor = r13;
                                    if (cursor != null) {
                                    }
                                    if (sQLiteDatabase != null) {
                                    }
                                    throw th;
                                }
                            }
                            zzgf().zzis().zzg("Error writing entry to local database", e);
                            this.zzaiq = true;
                            if (r13 != 0) {
                                r13.close();
                            }
                            if (sQLiteDatabase2 == null) {
                                sQLiteDatabase2.close();
                            }
                            i2++;
                            r3 = 0;
                        }
                    } else {
                        try {
                            sQLiteDatabase.beginTransaction();
                            long j = 0;
                            r13 = sQLiteDatabase.rawQuery("select count(1) from messages", null);
                            if (r13 != 0) {
                                try {
                                    if (r13.moveToFirst()) {
                                        j = r13.getLong(r3);
                                    }
                                } catch (SQLiteDatabaseLockedException unused) {
                                    cursor2 = r13;
                                    SystemClock.sleep(i3);
                                    i3 += 20;
                                    if (cursor2 != null) {
                                        cursor2.close();
                                    }
                                    if (sQLiteDatabase == null) {
                                        i2++;
                                        r3 = 0;
                                    }
                                    sQLiteDatabase.close();
                                    i2++;
                                    r3 = 0;
                                } catch (SQLiteFullException e3) {
                                    e = e3;
                                    cursor = r13;
                                    zzgf().zzis().zzg("Error writing entry to local database", e);
                                    this.zzaiq = true;
                                    if (cursor != null) {
                                    }
                                    if (sQLiteDatabase == null) {
                                    }
                                    sQLiteDatabase.close();
                                    i2++;
                                    r3 = 0;
                                } catch (SQLiteException e4) {
                                    e = e4;
                                    sQLiteDatabase2 = sQLiteDatabase;
                                    r13 = r13;
                                    if (sQLiteDatabase2 != null) {
                                    }
                                    zzgf().zzis().zzg("Error writing entry to local database", e);
                                    this.zzaiq = true;
                                    if (r13 != 0) {
                                    }
                                    if (sQLiteDatabase2 == null) {
                                    }
                                    i2++;
                                    r3 = 0;
                                } catch (Throwable th3) {
                                    th = th3;
                                    cursor = r13;
                                    if (cursor != null) {
                                    }
                                    if (sQLiteDatabase != null) {
                                    }
                                    throw th;
                                }
                            }
                            if (j >= 100000) {
                                zzgf().zzis().log("Data loss, local db full");
                                long j2 = (100000 - j) + 1;
                                String[] strArr = new String[1];
                                strArr[r3] = Long.toString(j2);
                                long delete = sQLiteDatabase.delete("messages", "rowid in (select rowid from messages order by rowid asc limit ?)", strArr);
                                if (delete != j2) {
                                    zzgf().zzis().zzd("Different delete count than expected in local db. expected, received, difference", Long.valueOf(j2), Long.valueOf(delete), Long.valueOf(j2 - delete));
                                }
                            }
                            sQLiteDatabase.insertOrThrow("messages", null, contentValues);
                            sQLiteDatabase.setTransactionSuccessful();
                            sQLiteDatabase.endTransaction();
                            if (r13 != 0) {
                                r13.close();
                            }
                            if (sQLiteDatabase == null) {
                                return true;
                            }
                            sQLiteDatabase.close();
                            return true;
                        } catch (SQLiteFullException e5) {
                            e = e5;
                        } catch (SQLiteException e6) {
                            e = e6;
                            r13 = 0;
                        } catch (Throwable th4) {
                            th = th4;
                            if (cursor != null) {
                            }
                            if (sQLiteDatabase != null) {
                            }
                            throw th;
                        }
                    }
                } catch (SQLiteDatabaseLockedException unused2) {
                    cursor2 = null;
                }
            } catch (SQLiteDatabaseLockedException unused3) {
                sQLiteDatabase = null;
            } catch (SQLiteFullException e7) {
                e = e7;
                sQLiteDatabase = null;
            } catch (SQLiteException e8) {
                e = e8;
                r13 = 0;
            } catch (Throwable th5) {
                th = th5;
                sQLiteDatabase = null;
            }
        }
        zzgf().zziv().log("Failed to write entry to local database");
        return false;
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final void resetAnalyticsData() {
        zzfs();
        zzab();
        try {
            int delete = getWritableDatabase().delete("messages", null, null) + 0;
            if (delete > 0) {
                zzgf().zziz().zzg("Reset local analytics data. records", Integer.valueOf(delete));
            }
        } catch (SQLiteException e) {
            zzgf().zzis().zzg("Error resetting local analytics data. error", e);
        }
    }

    public final boolean zza(zzew zzewVar) {
        Parcel obtain = Parcel.obtain();
        zzewVar.writeToParcel(obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        if (marshall.length <= 131072) {
            return zza(0, marshall);
        }
        zzgf().zziv().log("Event is too long for local database. Sending event directly to service");
        return false;
    }

    public final boolean zza(zzjz zzjzVar) {
        Parcel obtain = Parcel.obtain();
        zzjzVar.writeToParcel(obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        if (marshall.length <= 131072) {
            return zza(1, marshall);
        }
        zzgf().zziv().log("User property too long for local database. Sending directly to service");
        return false;
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ Clock zzbt() {
        return super.zzbt();
    }

    public final boolean zzc(zzee zzeeVar) {
        zzgc();
        byte[] zza = zzkc.zza(zzeeVar);
        if (zza.length <= 131072) {
            return zza(2, zza);
        }
        zzgf().zziv().log("Conditional user property too long for local database. Sending directly to service");
        return false;
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

    @Override // com.google.android.gms.internal.measurement.zzhi
    protected final boolean zzhh() {
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x01c8 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:109:0x01c2  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x01c8 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x01c8 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:120:0x017f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:129:0x01d2  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x01d7  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x01a7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final List<AbstractSafeParcelable> zzp(int i) {
        SQLiteDatabase sQLiteDatabase;
        Cursor cursor;
        SQLiteDatabase sQLiteDatabase2;
        Parcel obtain;
        SafeParcelable safeParcelable;
        zzab();
        zzfs();
        Cursor cursor2 = null;
        if (this.zzaiq) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        if (!getContext().getDatabasePath("google_app_measurement_local.db").exists()) {
            return arrayList;
        }
        int i2 = 0;
        int i3 = 5;
        for (int i4 = 5; i2 < i4; i4 = 5) {
            try {
                sQLiteDatabase2 = getWritableDatabase();
                try {
                    if (sQLiteDatabase2 == null) {
                        try {
                            this.zzaiq = true;
                            if (sQLiteDatabase2 != null) {
                                sQLiteDatabase2.close();
                            }
                            return null;
                        } catch (SQLiteFullException e) {
                            e = e;
                            cursor = null;
                            zzgf().zzis().zzg("Error reading entries from local database", e);
                            this.zzaiq = true;
                            if (cursor != null) {
                            }
                            if (sQLiteDatabase2 == null) {
                            }
                            sQLiteDatabase2.close();
                            i2++;
                        } catch (SQLiteException e2) {
                            e = e2;
                            cursor = null;
                            if (sQLiteDatabase2 != null) {
                            }
                            zzgf().zzis().zzg("Error reading entries from local database", e);
                            this.zzaiq = true;
                            if (cursor != null) {
                            }
                            if (sQLiteDatabase2 == null) {
                            }
                            sQLiteDatabase2.close();
                            i2++;
                        }
                    } else {
                        try {
                            sQLiteDatabase2.beginTransaction();
                            sQLiteDatabase = sQLiteDatabase2;
                        } catch (SQLiteFullException e3) {
                            e = e3;
                            cursor = null;
                            zzgf().zzis().zzg("Error reading entries from local database", e);
                            this.zzaiq = true;
                            if (cursor != null) {
                                cursor.close();
                            }
                            if (sQLiteDatabase2 == null) {
                                i2++;
                            }
                            sQLiteDatabase2.close();
                            i2++;
                        } catch (SQLiteException e4) {
                            e = e4;
                            cursor = null;
                            if (sQLiteDatabase2 != null) {
                                try {
                                    if (sQLiteDatabase2.inTransaction()) {
                                        sQLiteDatabase2.endTransaction();
                                    }
                                } catch (Throwable th) {
                                    th = th;
                                    cursor2 = cursor;
                                    sQLiteDatabase = sQLiteDatabase2;
                                    if (cursor2 != null) {
                                        cursor2.close();
                                    }
                                    if (sQLiteDatabase != null) {
                                        sQLiteDatabase.close();
                                    }
                                    throw th;
                                }
                            }
                            zzgf().zzis().zzg("Error reading entries from local database", e);
                            this.zzaiq = true;
                            if (cursor != null) {
                                cursor.close();
                            }
                            if (sQLiteDatabase2 == null) {
                                i2++;
                            }
                            sQLiteDatabase2.close();
                            i2++;
                        }
                        try {
                            cursor = sQLiteDatabase2.query("messages", new String[]{"rowid", AppMeasurement.Param.TYPE, "entry"}, null, null, null, null, "rowid asc", Integer.toString(100));
                            long j = -1;
                            while (cursor.moveToNext()) {
                                try {
                                    j = cursor.getLong(0);
                                    int i5 = cursor.getInt(1);
                                    byte[] blob = cursor.getBlob(2);
                                    if (i5 == 0) {
                                        obtain = Parcel.obtain();
                                        try {
                                            try {
                                                obtain.unmarshall(blob, 0, blob.length);
                                                obtain.setDataPosition(0);
                                                safeParcelable = (zzew) zzew.CREATOR.createFromParcel(obtain);
                                            } catch (SafeParcelReader.ParseException unused) {
                                                zzgf().zzis().log("Failed to load event from local database");
                                                obtain.recycle();
                                            }
                                            if (safeParcelable != null) {
                                            }
                                        } finally {
                                        }
                                    } else if (i5 == 1) {
                                        obtain = Parcel.obtain();
                                        try {
                                            try {
                                                obtain.unmarshall(blob, 0, blob.length);
                                                obtain.setDataPosition(0);
                                                safeParcelable = (zzjz) zzjz.CREATOR.createFromParcel(obtain);
                                            } catch (SafeParcelReader.ParseException unused2) {
                                                zzgf().zzis().log("Failed to load user property from local database");
                                                obtain.recycle();
                                                safeParcelable = null;
                                            }
                                            if (safeParcelable != null) {
                                            }
                                        } finally {
                                        }
                                    } else if (i5 == 2) {
                                        obtain = Parcel.obtain();
                                        try {
                                            try {
                                                obtain.unmarshall(blob, 0, blob.length);
                                                obtain.setDataPosition(0);
                                                safeParcelable = (zzee) zzee.CREATOR.createFromParcel(obtain);
                                            } finally {
                                            }
                                        } catch (SafeParcelReader.ParseException unused3) {
                                            zzgf().zzis().log("Failed to load user property from local database");
                                            obtain.recycle();
                                            safeParcelable = null;
                                        }
                                        if (safeParcelable != null) {
                                        }
                                    } else {
                                        zzgf().zzis().log("Unknown record type in local database");
                                    }
                                    arrayList.add(safeParcelable);
                                } catch (SQLiteDatabaseLockedException unused4) {
                                    sQLiteDatabase2 = sQLiteDatabase;
                                    SystemClock.sleep(i3);
                                    i3 += 20;
                                    if (cursor != null) {
                                        cursor.close();
                                    }
                                    if (sQLiteDatabase2 == null) {
                                        i2++;
                                    }
                                    sQLiteDatabase2.close();
                                    i2++;
                                } catch (SQLiteFullException e5) {
                                    e = e5;
                                    sQLiteDatabase2 = sQLiteDatabase;
                                    zzgf().zzis().zzg("Error reading entries from local database", e);
                                    this.zzaiq = true;
                                    if (cursor != null) {
                                    }
                                    if (sQLiteDatabase2 == null) {
                                    }
                                    sQLiteDatabase2.close();
                                    i2++;
                                } catch (SQLiteException e6) {
                                    e = e6;
                                    sQLiteDatabase2 = sQLiteDatabase;
                                    if (sQLiteDatabase2 != null) {
                                    }
                                    zzgf().zzis().zzg("Error reading entries from local database", e);
                                    this.zzaiq = true;
                                    if (cursor != null) {
                                    }
                                    if (sQLiteDatabase2 == null) {
                                    }
                                    sQLiteDatabase2.close();
                                    i2++;
                                } catch (Throwable th2) {
                                    th = th2;
                                    cursor2 = cursor;
                                    if (cursor2 != null) {
                                    }
                                    if (sQLiteDatabase != null) {
                                    }
                                    throw th;
                                }
                            }
                            if (sQLiteDatabase.delete("messages", "rowid <= ?", new String[]{Long.toString(j)}) < arrayList.size()) {
                                zzgf().zzis().log("Fewer entries removed from local database than expected");
                            }
                            sQLiteDatabase.setTransactionSuccessful();
                            sQLiteDatabase.endTransaction();
                            if (cursor != null) {
                                cursor.close();
                            }
                            if (sQLiteDatabase != null) {
                                sQLiteDatabase.close();
                            }
                            return arrayList;
                        } catch (SQLiteDatabaseLockedException unused5) {
                            cursor = null;
                            sQLiteDatabase2 = sQLiteDatabase;
                            SystemClock.sleep(i3);
                            i3 += 20;
                            if (cursor != null) {
                            }
                            if (sQLiteDatabase2 == null) {
                            }
                            sQLiteDatabase2.close();
                            i2++;
                        } catch (SQLiteFullException e7) {
                            e = e7;
                            cursor = null;
                        } catch (SQLiteException e8) {
                            e = e8;
                            cursor = null;
                        } catch (Throwable th3) {
                            th = th3;
                        }
                    }
                } catch (SQLiteDatabaseLockedException unused6) {
                    sQLiteDatabase = sQLiteDatabase2;
                } catch (Throwable th4) {
                    th = th4;
                    sQLiteDatabase = sQLiteDatabase2;
                    if (cursor2 != null) {
                    }
                    if (sQLiteDatabase != null) {
                    }
                    throw th;
                }
            } catch (SQLiteDatabaseLockedException unused7) {
                cursor = null;
                sQLiteDatabase2 = null;
            } catch (SQLiteFullException e9) {
                e = e9;
                cursor = null;
                sQLiteDatabase2 = null;
            } catch (SQLiteException e10) {
                e = e10;
                cursor = null;
                sQLiteDatabase2 = null;
            } catch (Throwable th5) {
                th = th5;
                sQLiteDatabase = null;
            }
        }
        zzgf().zziv().log("Failed to read events from database in reasonable time");
        return null;
    }
}
