package com.google.firebase.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
final class zze {

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class zza {
        private final Component<?> zza;
        private final Set<zza> zzb = new HashSet();
        private final Set<zza> zzc = new HashSet();

        zza(Component<?> component) {
            this.zza = component;
        }

        final void zza(zza zzaVar) {
            this.zzb.add(zzaVar);
        }

        final void zzb(zza zzaVar) {
            this.zzc.add(zzaVar);
        }

        final Set<zza> zza() {
            return this.zzb;
        }

        final void zzc(zza zzaVar) {
            this.zzc.remove(zzaVar);
        }

        final Component<?> zzb() {
            return this.zza;
        }

        final boolean zzc() {
            return this.zzc.isEmpty();
        }

        final boolean zzd() {
            return this.zzb.isEmpty();
        }
    }

    private static Set<zza> zza(Set<zza> set) {
        HashSet hashSet = new HashSet();
        for (zza zzaVar : set) {
            if (zzaVar.zzc()) {
                hashSet.add(zzaVar);
            }
        }
        return hashSet;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static List<Component<?>> zza(List<Component<?>> list) {
        zza zzaVar;
        HashMap hashMap = new HashMap(list.size());
        for (Component<?> component : list) {
            zza zzaVar2 = new zza(component);
            for (Class<? super Object> cls : component.zza()) {
                if (hashMap.put(cls, zzaVar2) != null) {
                    throw new IllegalArgumentException(String.format("Multiple components provide %s.", cls));
                }
            }
        }
        for (zza zzaVar3 : hashMap.values()) {
            for (Dependency dependency : zzaVar3.zzb().zzb()) {
                if (dependency.zzc() && (zzaVar = (zza) hashMap.get(dependency.zza())) != null) {
                    zzaVar3.zza(zzaVar);
                    zzaVar.zzb(zzaVar3);
                }
            }
        }
        HashSet<zza> hashSet = new HashSet(hashMap.values());
        Set<zza> zza2 = zza(hashSet);
        ArrayList arrayList = new ArrayList();
        while (!zza2.isEmpty()) {
            zza next = zza2.iterator().next();
            zza2.remove(next);
            arrayList.add(next.zzb());
            for (zza zzaVar4 : next.zza()) {
                zzaVar4.zzc(next);
                if (zzaVar4.zzc()) {
                    zza2.add(zzaVar4);
                }
            }
        }
        if (arrayList.size() == list.size()) {
            Collections.reverse(arrayList);
            return arrayList;
        }
        ArrayList arrayList2 = new ArrayList();
        for (zza zzaVar5 : hashSet) {
            if (!zzaVar5.zzc() && !zzaVar5.zzd()) {
                arrayList2.add(zzaVar5.zzb());
            }
        }
        throw new DependencyCycleException(arrayList2);
    }
}
