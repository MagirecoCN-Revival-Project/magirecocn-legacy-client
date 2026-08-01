package com.google.android.gms.internal.measurement;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzhn implements Runnable {
    private final /* synthetic */ String val$name;
    private final /* synthetic */ String zzanr;
    private final /* synthetic */ zzhl zzaog;
    private final /* synthetic */ Object zzaoh;
    private final /* synthetic */ long zzaoi;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzhn(zzhl zzhlVar, String str, String str2, Object obj, long j) {
        this.zzaog = zzhlVar;
        this.zzanr = str;
        this.val$name = str2;
        this.zzaoh = obj;
        this.zzaoi = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzaog.zza(this.zzanr, this.val$name, this.zzaoh, this.zzaoi);
    }
}
