package com.google.firebase.iid;

import com.google.firebase.FirebaseApp;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentFactory;

/* loaded from: classes.dex */
final /* synthetic */ class zzai implements ComponentFactory {
    static final ComponentFactory zzcf = new zzai();

    private zzai() {
    }

    @Override // com.google.firebase.components.ComponentFactory
    public final Object create(ComponentContainer componentContainer) {
        return new FirebaseInstanceId((FirebaseApp) componentContainer.get(FirebaseApp.class));
    }
}
