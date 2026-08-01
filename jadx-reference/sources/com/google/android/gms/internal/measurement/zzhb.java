package com.google.android.gms.internal.measurement;

import java.util.concurrent.Callable;

/* loaded from: classes.dex */
final class zzhb implements Callable<byte[]> {
    private final /* synthetic */ zzgo zzanp;
    private final /* synthetic */ String zzant;
    private final /* synthetic */ zzew zzanu;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzhb(zzgo zzgoVar, zzew zzewVar, String str) {
        this.zzanp = zzgoVar;
        this.zzanu = zzewVar;
        this.zzant = str;
    }

    /* JADX DEBUG: Return type fixed from 'java.lang.Object' to match base method */
    @Override // java.util.concurrent.Callable
    public final /* synthetic */ byte[] call() throws Exception {
        zzjs zzjsVar;
        zzjs zzjsVar2;
        zzjsVar = this.zzanp.zzajy;
        zzjsVar.zzlg();
        zzjsVar2 = this.zzanp.zzajy;
        return zzjsVar2.zza(this.zzanu, this.zzant);
    }
}
