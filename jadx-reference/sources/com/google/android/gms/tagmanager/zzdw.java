package com.google.android.gms.tagmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import java.util.ArrayList;
import java.util.Collections;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzdw implements zzcd {
    private static final String zza = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL,'%s' INTEGER NOT NULL);", "gtm_hits", "hit_id", "hit_time", "hit_url", "hit_first_send_time");
    private final zzdv zzb;
    private volatile zzbk zzc;
    private final Context zzd;
    private final String zze;
    private long zzf;
    private final Clock zzg;
    private final int zzh;
    private final zzez zzi;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzdw(zzez zzezVar, Context context, byte[] bArr) {
        Context applicationContext = context.getApplicationContext();
        this.zzd = applicationContext;
        this.zze = "gtm_urls.db";
        this.zzi = zzezVar;
        this.zzg = DefaultClock.getInstance();
        this.zzb = new zzdv(this, applicationContext, "gtm_urls.db");
        this.zzc = new zzfj(applicationContext, new zzdu(this));
        this.zzf = 0L;
        this.zzh = 2000;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* bridge */ /* synthetic */ void zzi(zzdw zzdwVar, long j, long j2) {
        SQLiteDatabase zzk = zzdwVar.zzk("Error opening database for getNumStoredHits.");
        if (zzk == null) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("hit_first_send_time", Long.valueOf(j2));
        try {
            zzk.update("gtm_hits", contentValues, "hit_id=?", new String[]{String.valueOf(j)});
        } catch (SQLiteException unused) {
            StringBuilder sb = new StringBuilder(69);
            sb.append("Error setting HIT_FIRST_DISPATCH_TIME for hitId: ");
            sb.append(j);
            zzdh.zzc(sb.toString());
            zzdwVar.zzl(j);
        }
    }

    private final SQLiteDatabase zzk(String str) {
        try {
            return this.zzb.getWritableDatabase();
        } catch (SQLiteException unused) {
            zzdh.zzc(str);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzl(long j) {
        zzj(new String[]{String.valueOf(j)});
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:12:0x01af */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:26:0x01d0 */
    /* JADX DEBUG: Multi-variable search result rejected for r14v7, resolved type: android.database.Cursor */
    /* JADX DEBUG: Multi-variable search result rejected for r14v8, resolved type: android.database.Cursor */
    /* JADX DEBUG: Multi-variable search result rejected for r14v9, resolved type: android.database.Cursor */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x0116, code lost:
    
        r0 = new java.lang.String("Error in peekHits fetching hit url: ");
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x00f3, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x00ff, code lost:
    
        r12 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x00f1, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x00f8, code lost:
    
        r12 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x014c, code lost:
    
        if (r12 != null) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x014e, code lost:
    
        r12.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x0151, code lost:
    
        throw r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x00ed, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x00fb, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x00fc, code lost:
    
        r3 = r11;
        r17 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x00f5, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x00f6, code lost:
    
        r17 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0057, code lost:
    
        if (r12.moveToFirst() != false) goto L13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0059, code lost:
    
        r11.add(new com.google.android.gms.tagmanager.zzca(r12.getLong(0), r12.getLong(1), r12.getLong(2)));
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0074, code lost:
    
        if (r12.moveToNext() != false) goto L143;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0076, code lost:
    
        if (r12 == null) goto L126;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0078, code lost:
    
        r12.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0094, code lost:
    
        r3 = r11;
        r17 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x009a, code lost:
    
        r12 = r3.query("gtm_hits", new java.lang.String[]{"hit_id", "hit_url"}, null, null, null, null, java.lang.String.format("%s ASC", "hit_id"), java.lang.Integer.toString(40));
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00a2, code lost:
    
        if (r12.moveToFirst() == false) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00a4, code lost:
    
        r0 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00b0, code lost:
    
        if (((android.database.sqlite.SQLiteCursor) r12).getWindow().getNumRows() <= 0) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00b2, code lost:
    
        ((com.google.android.gms.tagmanager.zzca) r3.get(r0)).zzd(r12.getString(1));
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x00dd, code lost:
    
        r0 = r0 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00e3, code lost:
    
        if (r12.moveToNext() != false) goto L145;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x00c1, code lost:
    
        com.google.android.gms.tagmanager.zzdh.zzc(java.lang.String.format("HitString for hitId %d too large.  Hit will be deleted.", java.lang.Long.valueOf(((com.google.android.gms.tagmanager.zzca) r3.get(r0)).zzb())));
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x00e5, code lost:
    
        if (r12 == null) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x00e7, code lost:
    
        r12.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x00ea, code lost:
    
        r2 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x00ef, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0101, code lost:
    
        r0 = java.lang.String.valueOf(r0.getMessage());
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x010f, code lost:
    
        if (r0.length() == 0) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0111, code lost:
    
        r0 = "Error in peekHits fetching hit url: ".concat(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x011b, code lost:
    
        com.google.android.gms.tagmanager.zzdh.zzc(r0);
        r2 = new java.util.ArrayList();
        r0 = r3.iterator();
        r3 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x012e, code lost:
    
        r4 = (com.google.android.gms.tagmanager.zzca) r0.next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x013c, code lost:
    
        if (android.text.TextUtils.isEmpty(r4.zzc()) == false) goto L149;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x013e, code lost:
    
        if (r3 != false) goto L147;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x0141, code lost:
    
        r3 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x0142, code lost:
    
        r2.add(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x0146, code lost:
    
        if (r12 != null) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x0148, code lost:
    
        r12.close();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0116 A[Catch: all -> 0x00ed, TryCatch #5 {all -> 0x00ed, blocks: (B:67:0x009e, B:70:0x00a5, B:72:0x00b2, B:73:0x00dd, B:77:0x00c1, B:83:0x0101, B:85:0x0111, B:86:0x011b, B:87:0x0128, B:89:0x012e, B:94:0x0142, B:101:0x0116), top: B:61:0x007b }] */
    /* JADX WARN: Removed duplicated region for block: B:108:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:11:0x01a4  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x017e A[Catch: all -> 0x01ee, TryCatch #7 {all -> 0x01ee, blocks: (B:120:0x016e, B:122:0x017e, B:123:0x0188, B:127:0x0183), top: B:119:0x016e }] */
    /* JADX WARN: Removed duplicated region for block: B:125:0x018d  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0183 A[Catch: all -> 0x01ee, TryCatch #7 {all -> 0x01ee, blocks: (B:120:0x016e, B:122:0x017e, B:123:0x0188, B:127:0x0183), top: B:119:0x016e }] */
    /* JADX WARN: Removed duplicated region for block: B:131:0x01f2  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x01da  */
    /* JADX WARN: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0111 A[Catch: all -> 0x00ed, TryCatch #5 {all -> 0x00ed, blocks: (B:67:0x009e, B:70:0x00a5, B:72:0x00b2, B:73:0x00dd, B:77:0x00c1, B:83:0x0101, B:85:0x0111, B:86:0x011b, B:87:0x0128, B:89:0x012e, B:94:0x0142, B:101:0x0116), top: B:61:0x007b }] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x012e A[Catch: all -> 0x00ed, TryCatch #5 {all -> 0x00ed, blocks: (B:67:0x009e, B:70:0x00a5, B:72:0x00b2, B:73:0x00dd, B:77:0x00c1, B:83:0x0101, B:85:0x0111, B:86:0x011b, B:87:0x0128, B:89:0x012e, B:94:0x0142, B:101:0x0116), top: B:61:0x007b }] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0148  */
    @Override // com.google.android.gms.tagmanager.zzcd
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void zza() {
        Cursor cursor;
        Cursor cursor2;
        Cursor query;
        Cursor cursor3;
        ArrayList arrayList;
        Cursor cursor4;
        Cursor cursor5;
        int i;
        zzdh.zzb.zzd("GTM Dispatch running...");
        if (!this.zzc.zzb()) {
            return;
        }
        ArrayList arrayList2 = new ArrayList();
        SQLiteDatabase zzk = zzk("Error opening database for peekHits");
        if (zzk != null) {
            try {
                try {
                    try {
                        query = zzk.query("gtm_hits", new String[]{"hit_id", "hit_time", "hit_first_send_time"}, null, null, null, null, String.format("%s ASC", "hit_id"), Integer.toString(40));
                    } catch (SQLiteException e) {
                        e = e;
                        cursor2 = null;
                        try {
                            String valueOf = String.valueOf(e.getMessage());
                            zzdh.zzc(valueOf.length() == 0 ? "Error in peekHits fetching hitIds: ".concat(valueOf) : new String("Error in peekHits fetching hitIds: "));
                            if (cursor2 != null) {
                                cursor2.close();
                            }
                            if (arrayList2.isEmpty()) {
                            }
                        } catch (Throwable th) {
                            th = th;
                            cursor = cursor2;
                            if (cursor != null) {
                                cursor.close();
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    cursor = null;
                }
                try {
                    try {
                        arrayList = new ArrayList();
                    } catch (SQLiteException e2) {
                        e = e2;
                        cursor3 = query;
                    }
                    try {
                    } catch (SQLiteException e3) {
                        e = e3;
                        cursor3 = query;
                        arrayList2 = arrayList;
                        cursor2 = cursor3;
                        String valueOf2 = String.valueOf(e.getMessage());
                        zzdh.zzc(valueOf2.length() == 0 ? "Error in peekHits fetching hitIds: ".concat(valueOf2) : new String("Error in peekHits fetching hitIds: "));
                        if (cursor2 != null) {
                        }
                        if (arrayList2.isEmpty()) {
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                    cursor = query;
                    if (cursor != null) {
                    }
                    throw th;
                }
            } catch (SQLiteException e4) {
                e = e4;
            }
        }
        if (arrayList2.isEmpty()) {
            zzdh.zzb.zzd("...nothing to dispatch");
            this.zzi.zza(true);
            return;
        }
        this.zzc.zza(arrayList2);
        SQLiteDatabase zzk2 = zzk("Error opening database for getNumStoredHits.");
        try {
            if (zzk2 == null) {
                return;
            }
            try {
                cursor5 = zzk2.query("gtm_hits", new String[]{"hit_id", "hit_first_send_time"}, "hit_first_send_time=0", null, null, null, null);
                try {
                    i = cursor5.getCount();
                    zzk2 = cursor5;
                    if (cursor5 != null) {
                        cursor5.close();
                        zzk2 = cursor5;
                    }
                } catch (SQLiteException unused) {
                    zzdh.zzc("Error getting num untried hits");
                    if (cursor5 != null) {
                        cursor5.close();
                        return;
                    } else {
                        i = 0;
                        zzk2 = cursor5;
                        if (i <= 0) {
                        }
                    }
                }
            } catch (SQLiteException unused2) {
                cursor5 = null;
            } catch (Throwable th4) {
                th = th4;
                cursor4 = null;
                if (cursor4 != null) {
                    cursor4.close();
                }
                throw th;
            }
            if (i <= 0) {
                zzff.zzg().zza();
            }
        } catch (Throwable th5) {
            th = th5;
            cursor4 = zzk2;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0083, code lost:
    
        if (r2.moveToFirst() != false) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0085, code lost:
    
        r4.add(java.lang.String.valueOf(r2.getLong(0)));
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0094, code lost:
    
        if (r2.moveToNext() != false) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0096, code lost:
    
        if (r2 == null) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00be, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00bc, code lost:
    
        if (r2 == null) goto L37;
     */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00f2  */
    @Override // com.google.android.gms.tagmanager.zzcd
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void zzb(long j, String str) {
        Cursor cursor;
        long currentTimeMillis = this.zzg.currentTimeMillis();
        if (currentTimeMillis > this.zzf + 86400000) {
            this.zzf = currentTimeMillis;
            SQLiteDatabase zzk = zzk("Error opening database for deleteStaleHits.");
            if (zzk != null) {
                zzk.delete("gtm_hits", "HIT_TIME < ?", new String[]{Long.toString(this.zzg.currentTimeMillis() - 2592000000L)});
                this.zzi.zza(zzc() == 0);
            }
        }
        int zzc = (zzc() - this.zzh) + 1;
        Cursor cursor2 = null;
        if (zzc > 0) {
            ArrayList arrayList = new ArrayList();
            SQLiteDatabase zzk2 = zzk("Error opening database for peekHitIds.");
            if (zzk2 != null) {
                try {
                    cursor = zzk2.query("gtm_hits", new String[]{"hit_id"}, null, null, null, null, String.format("%s ASC", "hit_id"), Integer.toString(zzc));
                    try {
                        try {
                        } catch (SQLiteException e) {
                            e = e;
                            String valueOf = String.valueOf(e.getMessage());
                            zzdh.zzc(valueOf.length() != 0 ? "Error in peekHits fetching hitIds: ".concat(valueOf) : new String("Error in peekHits fetching hitIds: "));
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
            int size = arrayList.size();
            StringBuilder sb = new StringBuilder(51);
            sb.append("Store full, deleting ");
            sb.append(size);
            sb.append(" hits to make room.");
            zzdh.zzb.zzd(sb.toString());
            zzj((String[]) arrayList.toArray(new String[0]));
        }
        SQLiteDatabase zzk3 = zzk("Error opening database for putHit");
        if (zzk3 == null) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("hit_time", Long.valueOf(j));
        contentValues.put("hit_url", str);
        contentValues.put("hit_first_send_time", (Integer) 0);
        try {
            zzk3.insert("gtm_hits", null, contentValues);
            this.zzi.zza(false);
        } catch (SQLiteException unused) {
            zzdh.zzc("Error storing hit");
        }
    }

    final int zzc() {
        SQLiteDatabase zzk = zzk("Error opening database for getNumStoredHits.");
        if (zzk == null) {
            return 0;
        }
        Cursor cursor = null;
        try {
            try {
                cursor = zzk.rawQuery("SELECT COUNT(*) from gtm_hits", null);
                r1 = cursor.moveToFirst() ? (int) cursor.getLong(0) : 0;
                if (cursor != null) {
                    return r1;
                }
            } catch (SQLiteException unused) {
                zzdh.zzc("Error getting numStoredHits");
                if (cursor != null) {
                    cursor.close();
                    return 0;
                }
            }
            return r1;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    final void zzj(String[] strArr) {
        int length;
        SQLiteDatabase zzk;
        if (strArr == null || (length = strArr.length) == 0 || (zzk = zzk("Error opening database for deleteHits.")) == null) {
            return;
        }
        boolean z = true;
        try {
            zzk.delete("gtm_hits", String.format("HIT_ID in (%s)", TextUtils.join(",", Collections.nCopies(length, "?"))), strArr);
            zzez zzezVar = this.zzi;
            if (zzc() != 0) {
                z = false;
            }
            zzezVar.zza(z);
        } catch (SQLiteException unused) {
            zzdh.zzc("Error deleting hits");
        }
    }
}
