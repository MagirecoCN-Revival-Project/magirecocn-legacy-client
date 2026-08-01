package com.google.firebase.iid;

import android.os.Bundle;

/* loaded from: classes.dex */
final class zzad extends zzae<Void> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public zzad(int i, int i2, Bundle bundle) {
        super(i, 2, bundle);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.firebase.iid.zzae
    public final void zzb(Bundle bundle) {
        if (bundle.getBoolean("ack", false)) {
            finish(null);
        } else {
            zza(new zzaf(4, "Invalid response to one way request"));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.firebase.iid.zzae
    public final boolean zzv() {
        return true;
    }
}
