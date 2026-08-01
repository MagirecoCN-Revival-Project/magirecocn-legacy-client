package com.google.android.gms.internal.measurement;

import android.content.SharedPreferences;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes.dex */
public final class zzfv {
    private long value;
    private boolean zzaky;
    private final /* synthetic */ zzfs zzakz;
    private final long zzala;
    private final String zzny;

    public zzfv(zzfs zzfsVar, String str, long j) {
        this.zzakz = zzfsVar;
        Preconditions.checkNotEmpty(str);
        this.zzny = str;
        this.zzala = j;
    }

    public final long get() {
        SharedPreferences zzjf;
        if (!this.zzaky) {
            this.zzaky = true;
            zzjf = this.zzakz.zzjf();
            this.value = zzjf.getLong(this.zzny, this.zzala);
        }
        return this.value;
    }

    public final void set(long j) {
        SharedPreferences zzjf;
        zzjf = this.zzakz.zzjf();
        SharedPreferences.Editor edit = zzjf.edit();
        edit.putLong(this.zzny, j);
        edit.apply();
        this.value = j;
    }
}
