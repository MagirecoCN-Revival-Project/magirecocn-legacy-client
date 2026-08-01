package com.google.firebase.iid;

import android.util.Log;
import android.util.Pair;
import androidx.collection.ArrayMap;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzak {
    private final Map<Pair<String, String>, Task<String>> zzch = new ArrayMap();

    /* JADX INFO: Access modifiers changed from: package-private */
    public final /* synthetic */ Task zza(Pair pair, Task task) throws Exception {
        synchronized (this) {
            this.zzch.remove(pair);
        }
        return task;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r5v3, resolved type: java.util.Map<android.util.Pair<java.lang.String, java.lang.String>, com.google.android.gms.tasks.Task<java.lang.String>> */
    /* JADX DEBUG: Type inference failed for r4v8. Raw type applied. Possible types: com.google.android.gms.tasks.Task<TContinuationResult>, com.google.android.gms.tasks.Task<java.lang.String> */
    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    public final synchronized Task<String> zza(String str, String str2, zzam zzamVar) {
        final Pair pair = new Pair(str, str2);
        Task<String> task = this.zzch.get(pair);
        if (task != null) {
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                String valueOf = String.valueOf(pair);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 29);
                sb.append("Joining ongoing request for: ");
                sb.append(valueOf);
                Log.d("FirebaseInstanceId", sb.toString());
            }
            return task;
        }
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            String valueOf2 = String.valueOf(pair);
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 24);
            sb2.append("Making new request for: ");
            sb2.append(valueOf2);
            Log.d("FirebaseInstanceId", sb2.toString());
        }
        Task continueWithTask = zzamVar.zzo().continueWithTask(FirebaseInstanceId.zzah, new Continuation(this, pair) { // from class: com.google.firebase.iid.zzal
            private final zzak zzci;
            private final Pair zzcj;

            /* JADX INFO: Access modifiers changed from: package-private */
            {
                this.zzci = this;
                this.zzcj = pair;
            }

            @Override // com.google.android.gms.tasks.Continuation
            public final Object then(Task task2) {
                return this.zzci.zza(this.zzcj, task2);
            }
        });
        this.zzch.put(pair, continueWithTask);
        return continueWithTask;
    }
}
