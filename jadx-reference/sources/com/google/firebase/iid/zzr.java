package com.google.firebase.iid;

import android.os.Bundle;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import java.io.IOException;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzr implements Continuation<Bundle, String> {
    private final /* synthetic */ zzo zzbj;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzr(zzo zzoVar) {
        this.zzbj = zzoVar;
    }

    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [com.google.android.gms.tasks.Task] */
    /* JADX DEBUG: Return type fixed from 'java.lang.Object' to match base method */
    @Override // com.google.android.gms.tasks.Continuation
    public final /* synthetic */ String then(Task<Bundle> task) throws Exception {
        String zza;
        Bundle result = task.getResult(IOException.class);
        zzo zzoVar = this.zzbj;
        zza = zzo.zza(result);
        return zza;
    }
}
