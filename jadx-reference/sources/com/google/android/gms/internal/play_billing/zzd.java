package com.google.android.gms.internal.play_billing;

import android.os.IBinder;
import android.os.IInterface;
import com.android.vending.billing.IInAppBillingService;

/* compiled from: com.android.billingclient:billing@@5.2.1 */
/* loaded from: classes.dex */
public abstract class zzd extends zzi implements zze {
    public static zze zzo(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface(IInAppBillingService.DESCRIPTOR);
        return queryLocalInterface instanceof zze ? (zze) queryLocalInterface : new zzc(iBinder);
    }
}
