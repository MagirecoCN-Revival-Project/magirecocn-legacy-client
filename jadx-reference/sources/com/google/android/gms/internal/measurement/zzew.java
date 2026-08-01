package com.google.android.gms.internal.measurement;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* loaded from: classes.dex */
public final class zzew extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzew> CREATOR = new zzex();
    public final String name;
    public final String origin;
    public final zzet zzafr;
    public final long zzagc;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzew(zzew zzewVar, long j) {
        Preconditions.checkNotNull(zzewVar);
        this.name = zzewVar.name;
        this.zzafr = zzewVar.zzafr;
        this.origin = zzewVar.origin;
        this.zzagc = j;
    }

    public zzew(String str, zzet zzetVar, String str2, long j) {
        this.name = str;
        this.zzafr = zzetVar;
        this.origin = str2;
        this.zzagc = j;
    }

    public final String toString() {
        String str = this.origin;
        String str2 = this.name;
        String valueOf = String.valueOf(this.zzafr);
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 21 + String.valueOf(str2).length() + String.valueOf(valueOf).length());
        sb.append("origin=");
        sb.append(str);
        sb.append(",name=");
        sb.append(str2);
        sb.append(",params=");
        sb.append(valueOf);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.name, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzafr, i, false);
        SafeParcelWriter.writeString(parcel, 4, this.origin, false);
        SafeParcelWriter.writeLong(parcel, 5, this.zzagc);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
