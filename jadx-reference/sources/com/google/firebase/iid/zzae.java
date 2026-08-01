package com.google.firebase.iid;

import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes.dex */
abstract class zzae<T> {
    final int what;
    final int zzby;
    final TaskCompletionSource<T> zzbz = new TaskCompletionSource<>();
    final Bundle zzca;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzae(int i, int i2, Bundle bundle) {
        this.zzby = i;
        this.what = i2;
        this.zzca = bundle;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void finish(T t) {
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            String valueOf = String.valueOf(this);
            String valueOf2 = String.valueOf(t);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 16 + String.valueOf(valueOf2).length());
            sb.append("Finishing ");
            sb.append(valueOf);
            sb.append(" with ");
            sb.append(valueOf2);
            Log.d("MessengerIpcClient", sb.toString());
        }
        this.zzbz.setResult(t);
    }

    public String toString() {
        int i = this.what;
        int i2 = this.zzby;
        boolean zzv = zzv();
        StringBuilder sb = new StringBuilder(55);
        sb.append("Request { what=");
        sb.append(i);
        sb.append(" id=");
        sb.append(i2);
        sb.append(" oneWay=");
        sb.append(zzv);
        sb.append("}");
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zza(zzaf zzafVar) {
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            String valueOf = String.valueOf(this);
            String valueOf2 = String.valueOf(zzafVar);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 14 + String.valueOf(valueOf2).length());
            sb.append("Failing ");
            sb.append(valueOf);
            sb.append(" with ");
            sb.append(valueOf2);
            Log.d("MessengerIpcClient", sb.toString());
        }
        this.zzbz.setException(zzafVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void zzb(Bundle bundle);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract boolean zzv();
}
