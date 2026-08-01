package com.google.android.gms.internal.gtm;

import java.util.Iterator;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzxs implements Iterator<String> {
    final Iterator<String> zza;
    final /* synthetic */ zzxt zzb;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzxs(zzxt zzxtVar) {
        zzvs zzvsVar;
        this.zzb = zzxtVar;
        zzvsVar = zzxtVar.zza;
        this.zza = zzvsVar.iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zza.hasNext();
    }

    /* JADX DEBUG: Return type fixed from 'java.lang.Object' to match base method */
    @Override // java.util.Iterator
    public final /* bridge */ /* synthetic */ String next() {
        return this.zza.next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
