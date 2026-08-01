package com.google.android.gms.internal.measurement;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.util.Log;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public final class zzwr {
    private static final ConcurrentHashMap<Uri, zzwr> zzbnf = new ConcurrentHashMap<>();
    private static final String[] zzbnm = {"key", FirebaseAnalytics.Param.VALUE};
    private final Uri uri;
    private final ContentResolver zzbng;
    private volatile Map<String, String> zzbnj;
    private final Object zzbni = new Object();
    private final Object zzbnk = new Object();
    private final List<zzwt> zzbnl = new ArrayList();
    private final ContentObserver zzbnh = new zzws(this, null);

    private zzwr(ContentResolver contentResolver, Uri uri) {
        this.zzbng = contentResolver;
        this.uri = uri;
    }

    public static zzwr zza(ContentResolver contentResolver, Uri uri) {
        ConcurrentHashMap<Uri, zzwr> concurrentHashMap = zzbnf;
        zzwr zzwrVar = concurrentHashMap.get(uri);
        if (zzwrVar != null) {
            return zzwrVar;
        }
        zzwr zzwrVar2 = new zzwr(contentResolver, uri);
        zzwr putIfAbsent = concurrentHashMap.putIfAbsent(uri, zzwrVar2);
        if (putIfAbsent != null) {
            return putIfAbsent;
        }
        zzwrVar2.zzbng.registerContentObserver(zzwrVar2.uri, false, zzwrVar2.zzbnh);
        return zzwrVar2;
    }

    private final Map<String, String> zzse() {
        try {
            HashMap hashMap = new HashMap();
            Cursor query = this.zzbng.query(this.uri, zzbnm, null, null, null);
            if (query != null) {
                while (query.moveToNext()) {
                    try {
                        hashMap.put(query.getString(0), query.getString(1));
                    } catch (Throwable th) {
                        query.close();
                        throw th;
                    }
                }
                query.close();
            }
            return hashMap;
        } catch (SQLiteException | SecurityException unused) {
            Log.e("ConfigurationContentLoader", "PhenotypeFlag unable to load ContentProvider, using default values");
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzsf() {
        synchronized (this.zzbnk) {
            Iterator<zzwt> it = this.zzbnl.iterator();
            while (it.hasNext()) {
                it.next().zzsg();
            }
        }
    }

    public final Map<String, String> zzsc() {
        Map<String, String> zzse = zzwu.zzd("gms:phenotype:phenotype_flag:debug_disable_caching", false) ? zzse() : this.zzbnj;
        if (zzse == null) {
            synchronized (this.zzbni) {
                zzse = this.zzbnj;
                if (zzse == null) {
                    zzse = zzse();
                    this.zzbnj = zzse;
                }
            }
        }
        return zzse != null ? zzse : Collections.emptyMap();
    }

    public final void zzsd() {
        synchronized (this.zzbni) {
            this.zzbnj = null;
        }
    }
}
