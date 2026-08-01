package com.google.firebase.iid;

import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

/* loaded from: classes.dex */
final class zzac {
    private final Messenger zzaf;
    private final zzi zzbx;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzac(IBinder iBinder) throws RemoteException {
        String interfaceDescriptor = iBinder.getInterfaceDescriptor();
        if ("android.os.IMessenger".equals(interfaceDescriptor)) {
            this.zzaf = new Messenger(iBinder);
            this.zzbx = null;
        } else if ("com.google.android.gms.iid.IMessengerCompat".equals(interfaceDescriptor)) {
            this.zzbx = new zzi(iBinder);
            this.zzaf = null;
        } else {
            String valueOf = String.valueOf(interfaceDescriptor);
            Log.w("MessengerIpcClient", valueOf.length() != 0 ? "Invalid interface descriptor: ".concat(valueOf) : new String("Invalid interface descriptor: "));
            throw new RemoteException();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void send(Message message) throws RemoteException {
        Messenger messenger = this.zzaf;
        if (messenger != null) {
            messenger.send(message);
            return;
        }
        zzi zziVar = this.zzbx;
        if (zziVar == null) {
            throw new IllegalStateException("Both messengers are null");
        }
        zziVar.send(message);
    }
}
