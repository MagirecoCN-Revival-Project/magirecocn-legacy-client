package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzacd implements Cloneable {
    private Object value;
    private zzacb<?, ?> zzbxo;
    private List<zzaci> zzbxp = new ArrayList();

    private final byte[] toByteArray() throws IOException {
        byte[] bArr = new byte[zza()];
        zza(zzaby.zzj(bArr));
        return bArr;
    }

    /* JADX DEBUG: Method merged with bridge method: clone()Ljava/lang/Object; */
    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: zzvp, reason: merged with bridge method [inline-methods] */
    public final zzacd clone() {
        Object clone;
        zzacd zzacdVar = new zzacd();
        try {
            zzacdVar.zzbxo = this.zzbxo;
            List<zzaci> list = this.zzbxp;
            if (list == null) {
                zzacdVar.zzbxp = null;
            } else {
                zzacdVar.zzbxp.addAll(list);
            }
            Object obj = this.value;
            if (obj != null) {
                if (obj instanceof zzacg) {
                    clone = (zzacg) ((zzacg) obj).clone();
                } else if (obj instanceof byte[]) {
                    clone = ((byte[]) obj).clone();
                } else {
                    int i = 0;
                    if (obj instanceof byte[][]) {
                        byte[][] bArr = (byte[][]) obj;
                        byte[][] bArr2 = new byte[bArr.length];
                        zzacdVar.value = bArr2;
                        while (i < bArr.length) {
                            bArr2[i] = (byte[]) bArr[i].clone();
                            i++;
                        }
                    } else if (obj instanceof boolean[]) {
                        clone = ((boolean[]) obj).clone();
                    } else if (obj instanceof int[]) {
                        clone = ((int[]) obj).clone();
                    } else if (obj instanceof long[]) {
                        clone = ((long[]) obj).clone();
                    } else if (obj instanceof float[]) {
                        clone = ((float[]) obj).clone();
                    } else if (obj instanceof double[]) {
                        clone = ((double[]) obj).clone();
                    } else if (obj instanceof zzacg[]) {
                        zzacg[] zzacgVarArr = (zzacg[]) obj;
                        zzacg[] zzacgVarArr2 = new zzacg[zzacgVarArr.length];
                        zzacdVar.value = zzacgVarArr2;
                        while (i < zzacgVarArr.length) {
                            zzacgVarArr2[i] = (zzacg) zzacgVarArr[i].clone();
                            i++;
                        }
                    }
                }
                zzacdVar.value = clone;
            }
            return zzacdVar;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object obj) {
        List<zzaci> list;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzacd)) {
            return false;
        }
        zzacd zzacdVar = (zzacd) obj;
        if (this.value == null || zzacdVar.value == null) {
            List<zzaci> list2 = this.zzbxp;
            if (list2 != null && (list = zzacdVar.zzbxp) != null) {
                return list2.equals(list);
            }
            try {
                return Arrays.equals(toByteArray(), zzacdVar.toByteArray());
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        zzacb<?, ?> zzacbVar = this.zzbxo;
        if (zzacbVar != zzacdVar.zzbxo) {
            return false;
        }
        if (!zzacbVar.zzbxh.isArray()) {
            return this.value.equals(zzacdVar.value);
        }
        Object obj2 = this.value;
        return obj2 instanceof byte[] ? Arrays.equals((byte[]) obj2, (byte[]) zzacdVar.value) : obj2 instanceof int[] ? Arrays.equals((int[]) obj2, (int[]) zzacdVar.value) : obj2 instanceof long[] ? Arrays.equals((long[]) obj2, (long[]) zzacdVar.value) : obj2 instanceof float[] ? Arrays.equals((float[]) obj2, (float[]) zzacdVar.value) : obj2 instanceof double[] ? Arrays.equals((double[]) obj2, (double[]) zzacdVar.value) : obj2 instanceof boolean[] ? Arrays.equals((boolean[]) obj2, (boolean[]) zzacdVar.value) : Arrays.deepEquals((Object[]) obj2, (Object[]) zzacdVar.value);
    }

    public final int hashCode() {
        try {
            return Arrays.hashCode(toByteArray()) + 527;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final int zza() {
        Object obj = this.value;
        if (obj == null) {
            int i = 0;
            for (zzaci zzaciVar : this.zzbxp) {
                i += zzaby.zzas(zzaciVar.tag) + 0 + zzaciVar.zzbrm.length;
            }
            return i;
        }
        zzacb<?, ?> zzacbVar = this.zzbxo;
        if (!zzacbVar.zzbxi) {
            return zzacbVar.zzv(obj);
        }
        int length = Array.getLength(obj);
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            if (Array.get(obj, i3) != null) {
                i2 += zzacbVar.zzv(Array.get(obj, i3));
            }
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zza(zzaby zzabyVar) throws IOException {
        Object obj = this.value;
        if (obj == null) {
            for (zzaci zzaciVar : this.zzbxp) {
                zzabyVar.zzar(zzaciVar.tag);
                zzabyVar.zzk(zzaciVar.zzbrm);
            }
            return;
        }
        zzacb<?, ?> zzacbVar = this.zzbxo;
        if (!zzacbVar.zzbxi) {
            zzacbVar.zza(obj, zzabyVar);
            return;
        }
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            Object obj2 = Array.get(obj, i);
            if (obj2 != null) {
                zzacbVar.zza(obj2, zzabyVar);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zza(zzaci zzaciVar) throws IOException {
        Object zzi;
        List<zzaci> list = this.zzbxp;
        if (list != null) {
            list.add(zzaciVar);
            return;
        }
        Object obj = this.value;
        if (obj instanceof zzacg) {
            byte[] bArr = zzaciVar.zzbrm;
            zzabx zza = zzabx.zza(bArr, 0, bArr.length);
            int zzvh = zza.zzvh();
            if (zzvh != bArr.length - zzaby.zzao(zzvh)) {
                throw zzacf.zzvq();
            }
            zzi = ((zzacg) this.value).zzb(zza);
        } else if (obj instanceof zzacg[]) {
            zzacg[] zzacgVarArr = (zzacg[]) this.zzbxo.zzi(Collections.singletonList(zzaciVar));
            zzacg[] zzacgVarArr2 = (zzacg[]) this.value;
            zzacg[] zzacgVarArr3 = (zzacg[]) Arrays.copyOf(zzacgVarArr2, zzacgVarArr2.length + zzacgVarArr.length);
            System.arraycopy(zzacgVarArr, 0, zzacgVarArr3, zzacgVarArr2.length, zzacgVarArr.length);
            zzi = zzacgVarArr3;
        } else {
            zzi = this.zzbxo.zzi(Collections.singletonList(zzaciVar));
        }
        this.zzbxo = this.zzbxo;
        this.value = zzi;
        this.zzbxp = null;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: com.google.android.gms.internal.measurement.zzacb<?, T> */
    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    public final <T> T zzb(zzacb<?, T> zzacbVar) {
        if (this.value == null) {
            this.zzbxo = zzacbVar;
            this.value = zzacbVar.zzi(this.zzbxp);
            this.zzbxp = null;
        } else if (!this.zzbxo.equals(zzacbVar)) {
            throw new IllegalStateException("Tried to getExtension with a different Extension.");
        }
        return (T) this.value;
    }
}
