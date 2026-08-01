package com.google.android.gms.internal.measurement;

import android.content.SharedPreferences;
import android.util.Pair;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes.dex */
public final class zzfw {
    private final long zzabj;
    private final /* synthetic */ zzfs zzakz;
    private final String zzalb;
    private final String zzalc;
    private final String zzald;

    private zzfw(zzfs zzfsVar, String str, long j) {
        this.zzakz = zzfsVar;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkArgument(j > 0);
        this.zzalb = String.valueOf(str).concat(":start");
        this.zzalc = String.valueOf(str).concat(":count");
        this.zzald = String.valueOf(str).concat(":value");
        this.zzabj = j;
    }

    private final void zzfh() {
        SharedPreferences zzjf;
        this.zzakz.zzab();
        long currentTimeMillis = this.zzakz.zzbt().currentTimeMillis();
        zzjf = this.zzakz.zzjf();
        SharedPreferences.Editor edit = zzjf.edit();
        edit.remove(this.zzalc);
        edit.remove(this.zzald);
        edit.putLong(this.zzalb, currentTimeMillis);
        edit.apply();
    }

    private final long zzfj() {
        SharedPreferences zzjf;
        zzjf = this.zzakz.zzjf();
        return zzjf.getLong(this.zzalb, 0L);
    }

    public final void zzc(String str, long j) {
        SharedPreferences zzjf;
        SharedPreferences zzjf2;
        SharedPreferences zzjf3;
        this.zzakz.zzab();
        if (zzfj() == 0) {
            zzfh();
        }
        if (str == null) {
            str = "";
        }
        zzjf = this.zzakz.zzjf();
        long j2 = zzjf.getLong(this.zzalc, 0L);
        if (j2 <= 0) {
            zzjf3 = this.zzakz.zzjf();
            SharedPreferences.Editor edit = zzjf3.edit();
            edit.putString(this.zzald, str);
            edit.putLong(this.zzalc, 1L);
            edit.apply();
            return;
        }
        long j3 = j2 + 1;
        boolean z = (this.zzakz.zzgc().zzll().nextLong() & Long.MAX_VALUE) < Long.MAX_VALUE / j3;
        zzjf2 = this.zzakz.zzjf();
        SharedPreferences.Editor edit2 = zzjf2.edit();
        if (z) {
            edit2.putString(this.zzald, str);
        }
        edit2.putLong(this.zzalc, j3);
        edit2.apply();
    }

    public final Pair<String, Long> zzfi() {
        long abs;
        SharedPreferences zzjf;
        SharedPreferences zzjf2;
        this.zzakz.zzab();
        this.zzakz.zzab();
        long zzfj = zzfj();
        if (zzfj == 0) {
            zzfh();
            abs = 0;
        } else {
            abs = Math.abs(zzfj - this.zzakz.zzbt().currentTimeMillis());
        }
        long j = this.zzabj;
        if (abs < j) {
            return null;
        }
        if (abs > (j << 1)) {
            zzfh();
            return null;
        }
        zzjf = this.zzakz.zzjf();
        String string = zzjf.getString(this.zzald, null);
        zzjf2 = this.zzakz.zzjf();
        long j2 = zzjf2.getLong(this.zzalc, 0L);
        zzfh();
        return (string == null || j2 <= 0) ? zzfs.zzakb : new Pair<>(string, Long.valueOf(j2));
    }
}
