package com.google.android.gms.tagmanager;

import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
public interface ContainerHolder extends Result, Releasable {

    /* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
    /* loaded from: classes.dex */
    public interface ContainerAvailableListener {
        void onContainerAvailable(ContainerHolder containerHolder, String str);
    }

    Container getContainer();

    void refresh();

    void setContainerAvailableListener(ContainerAvailableListener containerAvailableListener);
}
