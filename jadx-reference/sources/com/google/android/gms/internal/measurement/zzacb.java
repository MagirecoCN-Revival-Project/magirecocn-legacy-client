package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzaca;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class zzacb<M extends zzaca<M>, T> {
    public final int tag;
    private final int type;
    protected final Class<T> zzbxh;
    protected final boolean zzbxi;
    private final zzzs<?, ?> zzbxj;

    private zzacb(int i, Class<T> cls, int i2, boolean z) {
        this(11, cls, null, 810, false);
    }

    private zzacb(int i, Class<T> cls, zzzs<?, ?> zzzsVar, int i2, boolean z) {
        this.type = i;
        this.zzbxh = cls;
        this.tag = i2;
        this.zzbxi = false;
        this.zzbxj = null;
    }

    public static <M extends zzaca<M>, T extends zzacg> zzacb<M, T> zza(int i, Class<T> cls, long j) {
        return new zzacb<>(11, cls, 810, false);
    }

    private final Object zzf(zzabx zzabxVar) {
        Class componentType = this.zzbxi ? this.zzbxh.getComponentType() : this.zzbxh;
        try {
            int i = this.type;
            if (i == 10) {
                zzacg zzacgVar = (zzacg) componentType.newInstance();
                zzabxVar.zza(zzacgVar, this.tag >>> 3);
                return zzacgVar;
            }
            if (i == 11) {
                zzacg zzacgVar2 = (zzacg) componentType.newInstance();
                zzabxVar.zza(zzacgVar2);
                return zzacgVar2;
            }
            int i2 = this.type;
            StringBuilder sb = new StringBuilder(24);
            sb.append("Unknown type ");
            sb.append(i2);
            throw new IllegalArgumentException(sb.toString());
        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading extension field", e);
        } catch (IllegalAccessException e2) {
            String valueOf = String.valueOf(componentType);
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 33);
            sb2.append("Error creating instance of class ");
            sb2.append(valueOf);
            throw new IllegalArgumentException(sb2.toString(), e2);
        } catch (InstantiationException e3) {
            String valueOf2 = String.valueOf(componentType);
            StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf2).length() + 33);
            sb3.append("Error creating instance of class ");
            sb3.append(valueOf2);
            throw new IllegalArgumentException(sb3.toString(), e3);
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzacb)) {
            return false;
        }
        zzacb zzacbVar = (zzacb) obj;
        return this.type == zzacbVar.type && this.zzbxh == zzacbVar.zzbxh && this.tag == zzacbVar.tag && this.zzbxi == zzacbVar.zzbxi;
    }

    public final int hashCode() {
        return ((((((this.type + 1147) * 31) + this.zzbxh.hashCode()) * 31) + this.tag) * 31) + (this.zzbxi ? 1 : 0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void zza(Object obj, zzaby zzabyVar) {
        try {
            zzabyVar.zzar(this.tag);
            int i = this.type;
            if (i == 10) {
                int i2 = this.tag >>> 3;
                ((zzacg) obj).zza(zzabyVar);
                zzabyVar.zzg(i2, 4);
            } else {
                if (i == 11) {
                    zzabyVar.zzb((zzacg) obj);
                    return;
                }
                int i3 = this.type;
                StringBuilder sb = new StringBuilder(24);
                sb.append("Unknown type ");
                sb.append(i3);
                throw new IllegalArgumentException(sb.toString());
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final T zzi(List<zzaci> list) {
        if (list == null) {
            return null;
        }
        if (!this.zzbxi) {
            if (list.isEmpty()) {
                return null;
            }
            return this.zzbxh.cast(zzf(zzabx.zzi(list.get(list.size() - 1).zzbrm)));
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            zzaci zzaciVar = list.get(i);
            if (zzaciVar.zzbrm.length != 0) {
                arrayList.add(zzf(zzabx.zzi(zzaciVar.zzbrm)));
            }
        }
        int size = arrayList.size();
        if (size == 0) {
            return null;
        }
        Class<T> cls = this.zzbxh;
        T cast = cls.cast(Array.newInstance(cls.getComponentType(), size));
        for (int i2 = 0; i2 < size; i2++) {
            Array.set(cast, i2, arrayList.get(i2));
        }
        return cast;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final int zzv(Object obj) {
        int i = this.tag >>> 3;
        int i2 = this.type;
        if (i2 == 10) {
            return (zzaby.zzaq(i) << 1) + ((zzacg) obj).zzvv();
        }
        if (i2 == 11) {
            return zzaby.zzb(i, (zzacg) obj);
        }
        int i3 = this.type;
        StringBuilder sb = new StringBuilder(24);
        sb.append("Unknown type ");
        sb.append(i3);
        throw new IllegalArgumentException(sb.toString());
    }
}
