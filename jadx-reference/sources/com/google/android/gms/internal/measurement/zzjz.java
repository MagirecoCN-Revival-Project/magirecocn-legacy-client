package com.google.android.gms.internal.measurement;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* loaded from: classes.dex */
public final class zzjz extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzjz> CREATOR = new zzka();
    public final String name;
    public final String origin;
    private final int versionCode;
    private final String zzajo;
    public final long zzarl;
    private final Long zzarm;
    private final Float zzarn;
    private final Double zzaro;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzjz(int i, String str, long j, Long l, Float f, String str2, String str3, Double d) {
        this.versionCode = i;
        this.name = str;
        this.zzarl = j;
        this.zzarm = l;
        this.zzarn = null;
        if (i == 1) {
            this.zzaro = f != null ? Double.valueOf(f.doubleValue()) : null;
        } else {
            this.zzaro = d;
        }
        this.zzajo = str2;
        this.origin = str3;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzjz(zzkb zzkbVar) {
        this(zzkbVar.name, zzkbVar.zzarl, zzkbVar.value, zzkbVar.origin);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzjz(String str, long j, Object obj, String str2) {
        Preconditions.checkNotEmpty(str);
        this.versionCode = 2;
        this.name = str;
        this.zzarl = j;
        this.origin = str2;
        if (obj == null) {
            this.zzarm = null;
            this.zzarn = null;
            this.zzaro = null;
            this.zzajo = null;
            return;
        }
        if (obj instanceof Long) {
            this.zzarm = (Long) obj;
            this.zzarn = null;
            this.zzaro = null;
            this.zzajo = null;
            return;
        }
        if (obj instanceof String) {
            this.zzarm = null;
            this.zzarn = null;
            this.zzaro = null;
            this.zzajo = (String) obj;
            return;
        }
        if (!(obj instanceof Double)) {
            throw new IllegalArgumentException("User attribute given of un-supported type");
        }
        this.zzarm = null;
        this.zzarn = null;
        this.zzaro = (Double) obj;
        this.zzajo = null;
    }

    public final Object getValue() {
        Long l = this.zzarm;
        if (l != null) {
            return l;
        }
        Double d = this.zzaro;
        if (d != null) {
            return d;
        }
        String str = this.zzajo;
        if (str != null) {
            return str;
        }
        return null;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.versionCode);
        SafeParcelWriter.writeString(parcel, 2, this.name, false);
        SafeParcelWriter.writeLong(parcel, 3, this.zzarl);
        SafeParcelWriter.writeLongObject(parcel, 4, this.zzarm, false);
        SafeParcelWriter.writeFloatObject(parcel, 5, null, false);
        SafeParcelWriter.writeString(parcel, 6, this.zzajo, false);
        SafeParcelWriter.writeString(parcel, 7, this.origin, false);
        SafeParcelWriter.writeDoubleObject(parcel, 8, this.zzaro, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
