package com.google.android.gms.internal.measurement;

import android.content.SharedPreferences;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes.dex */
public final class zzfu {
    private boolean value;
    private final boolean zzakx;
    private boolean zzaky;
    private final /* synthetic */ zzfs zzakz;
    private final String zzny;

    public zzfu(zzfs zzfsVar, String str, boolean z) {
        this.zzakz = zzfsVar;
        Preconditions.checkNotEmpty(str);
        this.zzny = str;
        this.zzakx = true;
    }

    public final boolean get() {
        SharedPreferences zzjf;
        if (!this.zzaky) {
            this.zzaky = true;
            zzjf = this.zzakz.zzjf();
            this.value = zzjf.getBoolean(this.zzny, this.zzakx);
        }
        return this.value;
    }

    public final void set(boolean z) {
        SharedPreferences zzjf;
        zzjf = this.zzakz.zzjf();
        SharedPreferences.Editor edit = zzjf.edit();
        edit.putBoolean(this.zzny, z);
        edit.apply();
        this.value = z;
    }
}
