package com.google.android.gms.internal.measurement;

import android.content.SharedPreferences;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes.dex */
public final class zzfx {
    private String value;
    private boolean zzaky;
    private final /* synthetic */ zzfs zzakz;
    private final String zzale;
    private final String zzny;

    public zzfx(zzfs zzfsVar, String str, String str2) {
        this.zzakz = zzfsVar;
        Preconditions.checkNotEmpty(str);
        this.zzny = str;
        this.zzale = null;
    }

    public final void zzbr(String str) {
        SharedPreferences zzjf;
        if (zzkc.zzs(str, this.value)) {
            return;
        }
        zzjf = this.zzakz.zzjf();
        SharedPreferences.Editor edit = zzjf.edit();
        edit.putString(this.zzny, str);
        edit.apply();
        this.value = str;
    }

    public final String zzjn() {
        SharedPreferences zzjf;
        if (!this.zzaky) {
            this.zzaky = true;
            zzjf = this.zzakz.zzjf();
            this.value = zzjf.getString(this.zzny, null);
        }
        return this.value;
    }
}
