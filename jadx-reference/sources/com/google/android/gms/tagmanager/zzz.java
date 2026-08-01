package com.google.android.gms.tagmanager;

import android.os.Looper;
import android.os.Message;
import com.google.android.gms.internal.gtm.zzga;
import com.google.android.gms.tagmanager.ContainerHolder;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzz extends zzga {
    final /* synthetic */ zzaa zza;
    private final ContainerHolder.ContainerAvailableListener zzb;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzz(zzaa zzaaVar, ContainerHolder.ContainerAvailableListener containerAvailableListener, Looper looper) {
        super(looper);
        this.zza = zzaaVar;
        this.zzb = containerAvailableListener;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        if (message.what == 1) {
            this.zzb.onContainerAvailable(this.zza, (String) message.obj);
        } else {
            zzdh.zza("Don't know how to handle this message.");
        }
    }
}
