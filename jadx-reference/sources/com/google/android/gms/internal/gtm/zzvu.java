package com.google.android.gms.internal.gtm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzvu extends zzvy {
    private static final Class<?> zza = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zzvu() {
        super(null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public /* synthetic */ zzvu(zzvt zzvtVar) {
        super(null);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r1v10, resolved type: java.util.ArrayList */
    /* JADX WARN: Multi-variable type inference failed */
    private static <L> List<L> zzf(Object obj, long j, int i) {
        zzvr zzvrVar;
        List<L> arrayList;
        List<L> list = (List) zzxy.zzf(obj, j);
        if (list.isEmpty()) {
            if (list instanceof zzvs) {
                arrayList = new zzvr(i);
            } else if (!(list instanceof zzws) || !(list instanceof zzvh)) {
                arrayList = new ArrayList<>(i);
            } else {
                arrayList = ((zzvh) list).zzd(i);
            }
            zzxy.zzs(obj, j, arrayList);
            return arrayList;
        }
        if (zza.isAssignableFrom(list.getClass())) {
            ArrayList arrayList2 = new ArrayList(list.size() + i);
            arrayList2.addAll(list);
            zzxy.zzs(obj, j, arrayList2);
            zzvrVar = arrayList2;
        } else if (list instanceof zzxt) {
            zzvr zzvrVar2 = new zzvr(list.size() + i);
            zzvrVar2.addAll(zzvrVar2.size(), (zzxt) list);
            zzxy.zzs(obj, j, zzvrVar2);
            zzvrVar = zzvrVar2;
        } else {
            if (!(list instanceof zzws) || !(list instanceof zzvh)) {
                return list;
            }
            zzvh zzvhVar = (zzvh) list;
            if (zzvhVar.zzc()) {
                return list;
            }
            zzvh zzd = zzvhVar.zzd(list.size() + i);
            zzxy.zzs(obj, j, zzd);
            return zzd;
        }
        return zzvrVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.gtm.zzvy
    public final <L> List<L> zza(Object obj, long j) {
        return zzf(obj, j, 10);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.gtm.zzvy
    public final void zzb(Object obj, long j) {
        Object unmodifiableList;
        List list = (List) zzxy.zzf(obj, j);
        if (list instanceof zzvs) {
            unmodifiableList = ((zzvs) list).zze();
        } else {
            if (zza.isAssignableFrom(list.getClass())) {
                return;
            }
            if (!(list instanceof zzws) || !(list instanceof zzvh)) {
                unmodifiableList = Collections.unmodifiableList(list);
            } else {
                zzvh zzvhVar = (zzvh) list;
                if (zzvhVar.zzc()) {
                    zzvhVar.zzb();
                    return;
                }
                return;
            }
        }
        zzxy.zzs(obj, j, unmodifiableList);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.gtm.zzvy
    public final <E> void zzc(Object obj, Object obj2, long j) {
        List list = (List) zzxy.zzf(obj2, j);
        List zzf = zzf(obj, j, list.size());
        int size = zzf.size();
        int size2 = list.size();
        if (size > 0 && size2 > 0) {
            zzf.addAll(list);
        }
        if (size > 0) {
            list = zzf;
        }
        zzxy.zzs(obj, j, list);
    }
}
