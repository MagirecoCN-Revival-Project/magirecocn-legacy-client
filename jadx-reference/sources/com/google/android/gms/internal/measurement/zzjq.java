package com.google.android.gms.internal.measurement;

import com.google.android.gms.common.internal.Preconditions;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class zzjq extends zzhh implements zzed {
    protected final zzjs zzajy;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzjq(zzjs zzjsVar) {
        super(zzjsVar.zzlj());
        Preconditions.checkNotNull(zzjsVar);
        this.zzajy = zzjsVar;
    }

    public zzjy zzjc() {
        return this.zzajy.zzjc();
    }

    public zzeb zzjd() {
        return this.zzajy.zzjd();
    }

    public zzej zzje() {
        return this.zzajy.zzje();
    }
}
