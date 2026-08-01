package com.google.android.gms.internal.measurement;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* loaded from: classes.dex */
public final class zzee extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzee> CREATOR = new zzef();
    public boolean active;
    public long creationTimestamp;
    public String origin;
    public String packageName;
    public long timeToLive;
    public String triggerEventName;
    public long triggerTimeout;
    public zzjz zzaeq;
    public zzew zzaer;
    public zzew zzaes;
    public zzew zzaet;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzee(zzee zzeeVar) {
        Preconditions.checkNotNull(zzeeVar);
        this.packageName = zzeeVar.packageName;
        this.origin = zzeeVar.origin;
        this.zzaeq = zzeeVar.zzaeq;
        this.creationTimestamp = zzeeVar.creationTimestamp;
        this.active = zzeeVar.active;
        this.triggerEventName = zzeeVar.triggerEventName;
        this.zzaer = zzeeVar.zzaer;
        this.triggerTimeout = zzeeVar.triggerTimeout;
        this.zzaes = zzeeVar.zzaes;
        this.timeToLive = zzeeVar.timeToLive;
        this.zzaet = zzeeVar.zzaet;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzee(String str, String str2, zzjz zzjzVar, long j, boolean z, String str3, zzew zzewVar, long j2, zzew zzewVar2, long j3, zzew zzewVar3) {
        this.packageName = str;
        this.origin = str2;
        this.zzaeq = zzjzVar;
        this.creationTimestamp = j;
        this.active = z;
        this.triggerEventName = str3;
        this.zzaer = zzewVar;
        this.triggerTimeout = j2;
        this.zzaes = zzewVar2;
        this.timeToLive = j3;
        this.zzaet = zzewVar3;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.packageName, false);
        SafeParcelWriter.writeString(parcel, 3, this.origin, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzaeq, i, false);
        SafeParcelWriter.writeLong(parcel, 5, this.creationTimestamp);
        SafeParcelWriter.writeBoolean(parcel, 6, this.active);
        SafeParcelWriter.writeString(parcel, 7, this.triggerEventName, false);
        SafeParcelWriter.writeParcelable(parcel, 8, this.zzaer, i, false);
        SafeParcelWriter.writeLong(parcel, 9, this.triggerTimeout);
        SafeParcelWriter.writeParcelable(parcel, 10, this.zzaes, i, false);
        SafeParcelWriter.writeLong(parcel, 11, this.timeToLive);
        SafeParcelWriter.writeParcelable(parcel, 12, this.zzaet, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
