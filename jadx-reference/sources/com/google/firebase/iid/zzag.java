package com.google.firebase.iid;

import android.os.Bundle;

/* loaded from: classes.dex */
final class zzag extends zzae<Bundle> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public zzag(int i, int i2, Bundle bundle) {
        super(i, 1, bundle);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.firebase.iid.zzae
    public final void zzb(Bundle bundle) {
        Bundle bundle2 = bundle.getBundle("data");
        if (bundle2 == null) {
            bundle2 = Bundle.EMPTY;
        }
        finish(bundle2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.firebase.iid.zzae
    public final boolean zzv() {
        return false;
    }
}
