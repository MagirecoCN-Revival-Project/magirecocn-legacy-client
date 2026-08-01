package com.google.android.gms.tagmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.internal.gtm.zzfz;
import com.google.firebase.analytics.FirebaseAnalytics;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzbe implements zzax {
    private static final String zza = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' STRING NOT NULL, '%s' BLOB NOT NULL, '%s' INTEGER NOT NULL);", "datalayer", "ID", "key", FirebaseAnalytics.Param.VALUE, ClientCookie.EXPIRES_ATTR);
    private final Executor zzb;
    private final Context zzc;
    private final zzbc zzd;
    private final Clock zze;

    public zzbe(Context context) {
        Clock defaultClock = DefaultClock.getInstance();
        ExecutorService zza2 = zzfz.zza().zza(2);
        this.zzc = context;
        this.zze = defaultClock;
        this.zzb = zza2;
        this.zzd = new zzbc(this, context, "google_tagmanager.db");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* bridge */ /* synthetic */ List zzf(zzbe zzbeVar) {
        ObjectInputStream objectInputStream;
        try {
            zzbeVar.zzk(zzbeVar.zze.currentTimeMillis());
            SQLiteDatabase zzi = zzbeVar.zzi("Error opening database for loadSerialized.");
            ArrayList<zzbd> arrayList = new ArrayList();
            if (zzi != null) {
                Cursor query = zzi.query("datalayer", new String[]{"key", FirebaseAnalytics.Param.VALUE}, null, null, null, null, "ID", null);
                while (query.moveToNext()) {
                    try {
                        arrayList.add(new zzbd(query.getString(0), query.getBlob(1)));
                    } finally {
                        query.close();
                    }
                }
            }
            ArrayList arrayList2 = new ArrayList();
            for (zzbd zzbdVar : arrayList) {
                String str = zzbdVar.zza;
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(zzbdVar.zzb);
                ObjectInputStream objectInputStream2 = null;
                r2 = null;
                r2 = null;
                r2 = null;
                Object obj = null;
                try {
                    objectInputStream = new ObjectInputStream(byteArrayInputStream);
                } catch (IOException unused) {
                    objectInputStream = null;
                } catch (ClassNotFoundException unused2) {
                    objectInputStream = null;
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    obj = objectInputStream.readObject();
                    try {
                        objectInputStream.close();
                    } catch (IOException unused3) {
                    }
                } catch (IOException unused4) {
                    if (objectInputStream != null) {
                        objectInputStream.close();
                    }
                    byteArrayInputStream.close();
                    arrayList2.add(new zzau(str, obj));
                } catch (ClassNotFoundException unused5) {
                    if (objectInputStream != null) {
                        objectInputStream.close();
                    }
                    byteArrayInputStream.close();
                    arrayList2.add(new zzau(str, obj));
                } catch (Throwable th2) {
                    th = th2;
                    objectInputStream2 = objectInputStream;
                    if (objectInputStream2 != null) {
                        try {
                            objectInputStream2.close();
                        } catch (IOException unused6) {
                            throw th;
                        }
                    }
                    byteArrayInputStream.close();
                    throw th;
                }
                byteArrayInputStream.close();
                arrayList2.add(new zzau(str, obj));
            }
            return arrayList2;
        } finally {
            zzbeVar.zzj();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* bridge */ /* synthetic */ void zzg(zzbe zzbeVar, String str) {
        SQLiteDatabase zzi = zzbeVar.zzi("Error opening database for clearKeysWithPrefix.");
        if (zzi == null) {
            return;
        }
        try {
            int delete = zzi.delete("datalayer", "key = ? OR key LIKE ?", new String[]{str, String.valueOf(str).concat(".%")});
            StringBuilder sb = new StringBuilder(25);
            sb.append("Cleared ");
            sb.append(delete);
            sb.append(" items");
            zzdh.zzb.zzd(sb.toString());
        } catch (SQLiteException e) {
            String valueOf = String.valueOf(e);
            StringBuilder sb2 = new StringBuilder(String.valueOf(str).length() + 44 + String.valueOf(valueOf).length());
            sb2.append("Error deleting entries with key prefix: ");
            sb2.append(str);
            sb2.append(" (");
            sb2.append(valueOf);
            sb2.append(").");
            zzdh.zzc(sb2.toString());
        } finally {
            zzbeVar.zzj();
        }
    }

    private final SQLiteDatabase zzi(String str) {
        try {
            return this.zzd.getWritableDatabase();
        } catch (SQLiteException unused) {
            zzdh.zzc(str);
            return null;
        }
    }

    private final void zzj() {
        try {
            this.zzd.close();
        } catch (SQLiteException unused) {
        }
    }

    private final void zzk(long j) {
        SQLiteDatabase zzi = zzi("Error opening database for deleteOlderThan.");
        if (zzi == null) {
            return;
        }
        try {
            int delete = zzi.delete("datalayer", "expires <= ?", new String[]{Long.toString(j)});
            StringBuilder sb = new StringBuilder(33);
            sb.append("Deleted ");
            sb.append(delete);
            sb.append(" expired items");
            zzdh.zzb.zzd(sb.toString());
        } catch (SQLiteException unused) {
            zzdh.zzc("Error deleting old entries.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x007c, code lost:
    
        if (r5.moveToFirst() != false) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x007e, code lost:
    
        r4.add(java.lang.String.valueOf(r5.getLong(0)));
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x008d, code lost:
    
        if (r5.moveToNext() != false) goto L107;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x008f, code lost:
    
        if (r5 == null) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0091, code lost:
    
        r5.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00bd, code lost:
    
        if (r5 == null) goto L52;
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0049 A[Catch: all -> 0x0187, TRY_LEAVE, TryCatch #6 {all -> 0x0187, blocks: (B:4:0x0003, B:8:0x0044, B:10:0x0049, B:40:0x0091, B:51:0x0138, B:52:0x013b, B:13:0x00c0, B:15:0x00eb, B:18:0x00ef, B:20:0x00f7, B:22:0x0112, B:24:0x0118, B:26:0x0128, B:27:0x0132, B:28:0x012d, B:59:0x013c, B:65:0x0147, B:66:0x014b, B:68:0x0151, B:78:0x0031, B:84:0x0040, B:89:0x0183, B:90:0x0186), top: B:3:0x0003, outer: #8, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0138 A[Catch: all -> 0x0187, TryCatch #6 {all -> 0x0187, blocks: (B:4:0x0003, B:8:0x0044, B:10:0x0049, B:40:0x0091, B:51:0x0138, B:52:0x013b, B:13:0x00c0, B:15:0x00eb, B:18:0x00ef, B:20:0x00f7, B:22:0x0112, B:24:0x0118, B:26:0x0128, B:27:0x0132, B:28:0x012d, B:59:0x013c, B:65:0x0147, B:66:0x014b, B:68:0x0151, B:78:0x0031, B:84:0x0040, B:89:0x0183, B:90:0x0186), top: B:3:0x0003, outer: #8, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0147 A[Catch: all -> 0x0187, TryCatch #6 {all -> 0x0187, blocks: (B:4:0x0003, B:8:0x0044, B:10:0x0049, B:40:0x0091, B:51:0x0138, B:52:0x013b, B:13:0x00c0, B:15:0x00eb, B:18:0x00ef, B:20:0x00f7, B:22:0x0112, B:24:0x0118, B:26:0x0128, B:27:0x0132, B:28:0x012d, B:59:0x013c, B:65:0x0147, B:66:0x014b, B:68:0x0151, B:78:0x0031, B:84:0x0040, B:89:0x0183, B:90:0x0186), top: B:3:0x0003, outer: #8, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0183 A[Catch: all -> 0x0187, TRY_ENTER, TryCatch #6 {all -> 0x0187, blocks: (B:4:0x0003, B:8:0x0044, B:10:0x0049, B:40:0x0091, B:51:0x0138, B:52:0x013b, B:13:0x00c0, B:15:0x00eb, B:18:0x00ef, B:20:0x00f7, B:22:0x0112, B:24:0x0118, B:26:0x0128, B:27:0x0132, B:28:0x012d, B:59:0x013c, B:65:0x0147, B:66:0x014b, B:68:0x0151, B:78:0x0031, B:84:0x0040, B:89:0x0183, B:90:0x0186), top: B:3:0x0003, outer: #8, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final synchronized void zzl(List<zzbd> list, long j) {
        Cursor cursor;
        int i;
        int i2;
        SQLiteDatabase zzi;
        Cursor cursor2;
        int length;
        SQLiteDatabase zzi2;
        try {
            long currentTimeMillis = this.zze.currentTimeMillis();
            zzk(currentTimeMillis);
            int size = list.size();
            SQLiteDatabase zzi3 = zzi("Error opening database for getNumStoredEntries.");
            Cursor cursor3 = null;
            if (zzi3 != null) {
                try {
                    cursor = zzi3.rawQuery("SELECT COUNT(*) from datalayer", null);
                    try {
                        try {
                            i = cursor.moveToFirst() ? (int) cursor.getLong(0) : 0;
                            if (cursor != null) {
                                cursor.close();
                            }
                        } catch (SQLiteException unused) {
                            zzdh.zzc("Error getting numStoredEntries");
                            if (cursor != null) {
                                cursor.close();
                            }
                            i = 0;
                            i2 = (i - 2000) + size;
                            if (i2 > 0) {
                            }
                            long j2 = currentTimeMillis + j;
                            zzi = zzi("Error opening database for writeEntryToDatabase.");
                            if (zzi != null) {
                            }
                        }
                    } catch (Throwable th) {
                        th = th;
                        cursor3 = cursor;
                        if (cursor3 != null) {
                            cursor3.close();
                        }
                        throw th;
                    }
                } catch (SQLiteException unused2) {
                    cursor = null;
                } catch (Throwable th2) {
                    th = th2;
                    if (cursor3 != null) {
                    }
                    throw th;
                }
                i2 = (i - 2000) + size;
                if (i2 > 0) {
                    ArrayList arrayList = new ArrayList();
                    SQLiteDatabase zzi4 = zzi("Error opening database for peekEntryIds.");
                    if (zzi4 != null) {
                        try {
                            cursor2 = zzi4.query("datalayer", new String[]{"ID"}, null, null, null, null, String.format("%s ASC", "ID"), Integer.toString(i2));
                            try {
                                try {
                                } catch (SQLiteException e) {
                                    e = e;
                                    String valueOf = String.valueOf(e.getMessage());
                                    zzdh.zzc(valueOf.length() != 0 ? "Error in peekEntries fetching entryIds: ".concat(valueOf) : new String("Error in peekEntries fetching entryIds: "));
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                if (cursor2 != null) {
                                    cursor2.close();
                                }
                                throw th;
                            }
                        } catch (SQLiteException e2) {
                            e = e2;
                            cursor2 = null;
                        } catch (Throwable th4) {
                            th = th4;
                            cursor2 = null;
                            if (cursor2 != null) {
                            }
                            throw th;
                        }
                    }
                    int size2 = arrayList.size();
                    StringBuilder sb = new StringBuilder(64);
                    sb.append("DataLayer store full, deleting ");
                    sb.append(size2);
                    sb.append(" entries to make room.");
                    zzdh.zzb.zzb(sb.toString());
                    String[] strArr = (String[]) arrayList.toArray(new String[0]);
                    if (strArr != null && (length = strArr.length) != 0 && (zzi2 = zzi("Error opening database for deleteEntries.")) != null) {
                        try {
                            zzi2.delete("datalayer", String.format("%s in (%s)", "ID", TextUtils.join(",", Collections.nCopies(length, "?"))), strArr);
                        } catch (SQLiteException unused3) {
                            String valueOf2 = String.valueOf(Arrays.toString(strArr));
                            zzdh.zzc(valueOf2.length() != 0 ? "Error deleting entries ".concat(valueOf2) : new String("Error deleting entries "));
                        }
                    }
                }
                long j22 = currentTimeMillis + j;
                zzi = zzi("Error opening database for writeEntryToDatabase.");
                if (zzi != null) {
                    for (zzbd zzbdVar : list) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(ClientCookie.EXPIRES_ATTR, Long.valueOf(j22));
                        contentValues.put("key", zzbdVar.zza);
                        contentValues.put(FirebaseAnalytics.Param.VALUE, zzbdVar.zzb);
                        zzi.insert("datalayer", null, contentValues);
                    }
                }
            }
            i = 0;
            i2 = (i - 2000) + size;
            if (i2 > 0) {
            }
            long j222 = currentTimeMillis + j;
            zzi = zzi("Error opening database for writeEntryToDatabase.");
            if (zzi != null) {
            }
        } finally {
            zzj();
        }
    }

    @Override // com.google.android.gms.tagmanager.zzax
    public final void zza(String str) {
        this.zzb.execute(new zzbb(this, str));
    }

    @Override // com.google.android.gms.tagmanager.zzax
    public final void zzb(zzaw zzawVar) {
        this.zzb.execute(new zzba(this, zzawVar));
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0044, code lost:
    
        if (r6 == null) goto L9;
     */
    @Override // com.google.android.gms.tagmanager.zzax
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void zzc(List<zzau> list, long j) {
        ObjectOutputStream objectOutputStream;
        ArrayList arrayList = new ArrayList();
        for (zzau zzauVar : list) {
            String str = zzauVar.zza;
            Object obj = zzauVar.zzb;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream2 = null;
            r5 = null;
            byte[] bArr = null;
            try {
                objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                try {
                    objectOutputStream.writeObject(obj);
                    bArr = byteArrayOutputStream.toByteArray();
                } catch (IOException unused) {
                } catch (Throwable th) {
                    th = th;
                    objectOutputStream2 = objectOutputStream;
                    if (objectOutputStream2 != null) {
                        try {
                            objectOutputStream2.close();
                        } catch (IOException unused2) {
                            throw th;
                        }
                    }
                    byteArrayOutputStream.close();
                    throw th;
                }
            } catch (IOException unused3) {
                objectOutputStream = null;
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                objectOutputStream.close();
                byteArrayOutputStream.close();
            } catch (IOException unused4) {
            }
            arrayList.add(new zzbd(str, bArr));
        }
        this.zzb.execute(new zzaz(this, arrayList, j));
    }
}
