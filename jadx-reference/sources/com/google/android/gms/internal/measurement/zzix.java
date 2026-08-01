package com.google.android.gms.internal.measurement;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.stats.ConnectionTracker;

/* loaded from: classes.dex */
public final class zzix implements ServiceConnection, BaseGmsClient.BaseConnectionCallbacks, BaseGmsClient.BaseOnConnectionFailedListener {
    final /* synthetic */ zzij zzapn;
    private volatile boolean zzapt;
    private volatile zzfg zzapu;

    /* JADX INFO: Access modifiers changed from: protected */
    public zzix(zzij zzijVar) {
        this.zzapn = zzijVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean zza(zzix zzixVar, boolean z) {
        zzixVar.zzapt = false;
        return false;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.BaseConnectionCallbacks
    public final void onConnected(Bundle bundle) {
        Preconditions.checkMainThread("MeasurementServiceConnection.onConnected");
        synchronized (this) {
            try {
                zzez service = this.zzapu.getService();
                this.zzapu = null;
                this.zzapn.zzge().zzc(new zzja(this, service));
            } catch (DeadObjectException | IllegalStateException unused) {
                this.zzapu = null;
                this.zzapt = false;
            }
        }
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.BaseOnConnectionFailedListener
    public final void onConnectionFailed(ConnectionResult connectionResult) {
        Preconditions.checkMainThread("MeasurementServiceConnection.onConnectionFailed");
        zzfh zzjv = this.zzapn.zzacw.zzjv();
        if (zzjv != null) {
            zzjv.zziv().zzg("Service connection failed", connectionResult);
        }
        synchronized (this) {
            this.zzapt = false;
            this.zzapu = null;
        }
        this.zzapn.zzge().zzc(new zzjc(this));
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.BaseConnectionCallbacks
    public final void onConnectionSuspended(int i) {
        Preconditions.checkMainThread("MeasurementServiceConnection.onConnectionSuspended");
        this.zzapn.zzgf().zziy().log("Service connection suspended");
        this.zzapn.zzge().zzc(new zzjb(this));
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        zzix zzixVar;
        Preconditions.checkMainThread("MeasurementServiceConnection.onServiceConnected");
        synchronized (this) {
            if (iBinder == null) {
                this.zzapt = false;
                this.zzapn.zzgf().zzis().log("Service connected with null binder");
                return;
            }
            zzez zzezVar = null;
            try {
                String interfaceDescriptor = iBinder.getInterfaceDescriptor();
                if ("com.google.android.gms.measurement.internal.IMeasurementService".equals(interfaceDescriptor)) {
                    if (iBinder != null) {
                        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.measurement.internal.IMeasurementService");
                        zzezVar = queryLocalInterface instanceof zzez ? (zzez) queryLocalInterface : new zzfb(iBinder);
                    }
                    this.zzapn.zzgf().zziz().log("Bound to IMeasurementService interface");
                } else {
                    this.zzapn.zzgf().zzis().zzg("Got binder with a wrong descriptor", interfaceDescriptor);
                }
            } catch (RemoteException unused) {
                this.zzapn.zzgf().zzis().log("Service connect failed to get IMeasurementService");
            }
            if (zzezVar == null) {
                this.zzapt = false;
                try {
                    ConnectionTracker connectionTracker = ConnectionTracker.getInstance();
                    Context context = this.zzapn.getContext();
                    zzixVar = this.zzapn.zzapg;
                    connectionTracker.unbindService(context, zzixVar);
                } catch (IllegalArgumentException unused2) {
                }
            } else {
                this.zzapn.zzge().zzc(new zziy(this, zzezVar));
            }
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        Preconditions.checkMainThread("MeasurementServiceConnection.onServiceDisconnected");
        this.zzapn.zzgf().zziy().log("Service disconnected");
        this.zzapn.zzge().zzc(new zziz(this, componentName));
    }

    public final void zzc(Intent intent) {
        zzix zzixVar;
        this.zzapn.zzab();
        Context context = this.zzapn.getContext();
        ConnectionTracker connectionTracker = ConnectionTracker.getInstance();
        synchronized (this) {
            if (this.zzapt) {
                this.zzapn.zzgf().zziz().log("Connection attempt already in progress");
                return;
            }
            this.zzapn.zzgf().zziz().log("Using local app measurement service");
            this.zzapt = true;
            zzixVar = this.zzapn.zzapg;
            connectionTracker.bindService(context, intent, zzixVar, 129);
        }
    }

    public final void zzkq() {
        this.zzapn.zzab();
        Context context = this.zzapn.getContext();
        synchronized (this) {
            if (this.zzapt) {
                this.zzapn.zzgf().zziz().log("Connection attempt already in progress");
                return;
            }
            if (this.zzapu != null) {
                this.zzapn.zzgf().zziz().log("Already awaiting connection attempt");
                return;
            }
            this.zzapu = new zzfg(context, Looper.getMainLooper(), this, this);
            this.zzapn.zzgf().zziz().log("Connecting to remote service");
            this.zzapt = true;
            this.zzapu.checkAvailabilityAndConnect();
        }
    }
}
