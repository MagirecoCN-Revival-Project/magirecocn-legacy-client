package com.google.android.gms.internal.gtm;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzco implements Parcelable.Creator<zzcp> {
    /* JADX DEBUG: Return type fixed from 'java.lang.Object' to match base method */
    @Override // android.os.Parcelable.Creator
    @Deprecated
    public final /* bridge */ /* synthetic */ zzcp createFromParcel(Parcel parcel) {
        return new zzcp(parcel);
    }

    /* JADX DEBUG: Return type fixed from 'java.lang.Object[]' to match base method */
    @Override // android.os.Parcelable.Creator
    @Deprecated
    public final /* bridge */ /* synthetic */ zzcp[] newArray(int i) {
        return new zzcp[i];
    }
}
