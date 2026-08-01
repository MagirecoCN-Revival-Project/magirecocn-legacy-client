package com.google.android.gms.analytics;

import java.util.Comparator;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzf implements Comparator<zzj> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public zzf(zzg zzgVar) {
    }

    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, java.lang.Object] */
    @Override // java.util.Comparator
    public final /* bridge */ /* synthetic */ int compare(zzj zzjVar, zzj zzjVar2) {
        return zzjVar.getClass().getCanonicalName().compareTo(zzjVar2.getClass().getCanonicalName());
    }
}
