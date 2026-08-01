package com.google.android.gms.common.internal.service;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* loaded from: classes.dex */
public final class zac extends zaf {
    /* JADX INFO: Access modifiers changed from: package-private */
    public zac(zae zaeVar, GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [com.google.android.gms.common.api.Api$AnyClient] */
    /* JADX DEBUG: Multi-variable search result rejected for r2v2, resolved type: T */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    protected final /* bridge */ /* synthetic */ void doExecute(zah zahVar) throws RemoteException {
        ((zal) zahVar.getService()).zae(new zad(this));
    }
}
