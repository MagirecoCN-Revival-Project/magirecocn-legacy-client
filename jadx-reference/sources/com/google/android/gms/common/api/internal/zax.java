package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import java.util.concurrent.locks.Lock;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* loaded from: classes.dex */
public final class zax implements zabz {
    final /* synthetic */ zaaa zaa;

    /* JADX DEBUG: Marked for inline */
    /* JADX DEBUG: Method not inlined, still used in: [com.google.android.gms.common.api.internal.zaaa.<init>(android.content.Context, com.google.android.gms.common.api.internal.zabe, java.util.concurrent.locks.Lock, android.os.Looper, com.google.android.gms.common.GoogleApiAvailabilityLight, java.util.Map<com.google.android.gms.common.api.Api$AnyClientKey<?>, com.google.android.gms.common.api.Api$Client>, java.util.Map<com.google.android.gms.common.api.Api$AnyClientKey<?>, com.google.android.gms.common.api.Api$Client>, com.google.android.gms.common.internal.ClientSettings, com.google.android.gms.common.api.Api$AbstractClientBuilder<? extends com.google.android.gms.signin.zae, com.google.android.gms.signin.SignInOptions>, com.google.android.gms.common.api.Api$Client, java.util.ArrayList<com.google.android.gms.common.api.internal.zat>, java.util.ArrayList<com.google.android.gms.common.api.internal.zat>, java.util.Map<com.google.android.gms.common.api.Api<?>, java.lang.Boolean>, java.util.Map<com.google.android.gms.common.api.Api<?>, java.lang.Boolean>):void] */
    /* JADX INFO: Access modifiers changed from: package-private */
    public /* synthetic */ zax(zaaa zaaaVar, zaw zawVar) {
        this.zaa = zaaaVar;
    }

    @Override // com.google.android.gms.common.api.internal.zabz
    public final void zaa(ConnectionResult connectionResult) {
        Lock lock;
        Lock lock2;
        lock = this.zaa.zam;
        lock.lock();
        try {
            this.zaa.zaj = connectionResult;
            zaaa.zap(this.zaa);
        } finally {
            lock2 = this.zaa.zam;
            lock2.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabz
    public final void zab(Bundle bundle) {
        Lock lock;
        Lock lock2;
        lock = this.zaa.zam;
        lock.lock();
        try {
            zaaa.zao(this.zaa, bundle);
            this.zaa.zaj = ConnectionResult.RESULT_SUCCESS;
            zaaa.zap(this.zaa);
        } finally {
            lock2 = this.zaa.zam;
            lock2.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabz
    public final void zac(int i, boolean z) {
        Lock lock;
        Lock lock2;
        boolean z2;
        Lock lock3;
        ConnectionResult connectionResult;
        ConnectionResult connectionResult2;
        zabi zabiVar;
        lock = this.zaa.zam;
        lock.lock();
        try {
            zaaa zaaaVar = this.zaa;
            z2 = zaaaVar.zal;
            if (!z2) {
                connectionResult = zaaaVar.zak;
                if (connectionResult != null) {
                    connectionResult2 = zaaaVar.zak;
                    if (connectionResult2.isSuccess()) {
                        this.zaa.zal = true;
                        zabiVar = this.zaa.zae;
                        zabiVar.onConnectionSuspended(i);
                        lock3 = this.zaa.zam;
                        lock3.unlock();
                    }
                }
            }
            this.zaa.zal = false;
            zaaa.zan(this.zaa, i, z);
            lock3 = this.zaa.zam;
            lock3.unlock();
        } catch (Throwable th) {
            lock2 = this.zaa.zam;
            lock2.unlock();
            throw th;
        }
    }
}
