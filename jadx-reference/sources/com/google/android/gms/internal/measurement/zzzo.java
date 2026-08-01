package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzzq;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
final class zzzo<FieldDescriptorType extends zzzq<FieldDescriptorType>> {
    private static final zzzo zzbse = new zzzo(true);
    private boolean zzbme;
    private final zzaba<FieldDescriptorType, Object> zzbsc;
    private boolean zzbsd;

    private zzzo() {
        this.zzbsd = false;
        this.zzbsc = zzaba.zzag(16);
    }

    private zzzo(boolean z) {
        this.zzbsd = false;
        zzaba<FieldDescriptorType, Object> zzag = zzaba.zzag(0);
        this.zzbsc = zzag;
        if (this.zzbme) {
            return;
        }
        zzag.zzrp();
        this.zzbme = true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0026, code lost:
    
        if ((r3 instanceof com.google.android.gms.internal.measurement.zzzu) == false) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x002f, code lost:
    
        if ((r3 instanceof byte[]) == false) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x001b, code lost:
    
        if ((r3 instanceof com.google.android.gms.internal.measurement.zzzw) == false) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x001e, code lost:
    
        r0 = false;
     */
    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0011. Please report as an issue. */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void zza(zzabr zzabrVar, Object obj) {
        zzzt.checkNotNull(obj);
        boolean z = true;
        boolean z2 = false;
        switch (zzzp.zzbsf[zzabrVar.zzve().ordinal()]) {
            case 1:
                z = obj instanceof Integer;
                z2 = z;
                break;
            case 2:
                z = obj instanceof Long;
                z2 = z;
                break;
            case 3:
                z = obj instanceof Float;
                z2 = z;
                break;
            case 4:
                z = obj instanceof Double;
                z2 = z;
                break;
            case 5:
                z = obj instanceof Boolean;
                z2 = z;
                break;
            case 6:
                z = obj instanceof String;
                z2 = z;
                break;
            case 7:
                if (!(obj instanceof zzyy)) {
                    break;
                }
                z2 = z;
                break;
            case 8:
                if (!(obj instanceof Integer)) {
                    break;
                }
                z2 = z;
                break;
            case 9:
                if (!(obj instanceof zzaan)) {
                    break;
                }
                z2 = z;
                break;
        }
        if (!z2) {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
    }

    private final void zza(FieldDescriptorType fielddescriptortype, Object obj) {
        if (!fielddescriptortype.zztt()) {
            zza(fielddescriptortype.zzts(), obj);
        } else {
            if (!(obj instanceof List)) {
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
            ArrayList arrayList = new ArrayList();
            arrayList.addAll((List) obj);
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList.get(i);
                i++;
                zza(fielddescriptortype.zzts(), obj2);
            }
            obj = arrayList;
        }
        if (obj instanceof zzzw) {
            this.zzbsd = true;
        }
        this.zzbsc.zza((zzaba<FieldDescriptorType, Object>) fielddescriptortype, (FieldDescriptorType) obj);
    }

    public static <T extends zzzq<T>> zzzo<T> zztr() {
        return zzbse;
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzzo zzzoVar = new zzzo();
        for (int i = 0; i < this.zzbsc.zzus(); i++) {
            Map.Entry<FieldDescriptorType, Object> zzah = this.zzbsc.zzah(i);
            zzzoVar.zza((zzzo) zzah.getKey(), zzah.getValue());
        }
        for (Map.Entry<FieldDescriptorType, Object> entry : this.zzbsc.zzut()) {
            zzzoVar.zza((zzzo) entry.getKey(), entry.getValue());
        }
        zzzoVar.zzbsd = this.zzbsd;
        return zzzoVar;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzzo) {
            return this.zzbsc.equals(((zzzo) obj).zzbsc);
        }
        return false;
    }

    public final int hashCode() {
        return this.zzbsc.hashCode();
    }

    public final Iterator<Map.Entry<FieldDescriptorType, Object>> iterator() {
        return this.zzbsd ? new zzzz(this.zzbsc.entrySet().iterator()) : this.zzbsc.entrySet().iterator();
    }
}
