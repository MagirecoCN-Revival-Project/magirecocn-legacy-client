package com.google.firebase.iid;

import android.text.TextUtils;
import android.util.Log;
import androidx.collection.ArrayMap;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.io.IOException;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzau {
    private final zzaq zzaj;
    private int zzdf = 0;
    private final Map<Integer, TaskCompletionSource<Void>> zzdg = new ArrayMap();

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzau(zzaq zzaqVar) {
        this.zzaj = zzaqVar;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0035, code lost:
    
        if (r4 == 1) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0038, code lost:
    
        r7.zzc(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x003f, code lost:
    
        if (com.google.firebase.iid.FirebaseInstanceId.zzi() == false) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0041, code lost:
    
        r7 = "unsubscribe operation succeeded";
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static boolean zza(FirebaseInstanceId firebaseInstanceId, String str) {
        String[] split = str.split("!");
        if (split.length == 2) {
            String str2 = split[0];
            String str3 = split[1];
            char c = 65535;
            try {
                int hashCode = str2.hashCode();
                if (hashCode != 83) {
                    if (hashCode == 85 && str2.equals("U")) {
                        c = 1;
                    }
                } else if (str2.equals("S")) {
                    c = 0;
                }
                firebaseInstanceId.zzb(str3);
                if (FirebaseInstanceId.zzi()) {
                    String str4 = "subscribe operation succeeded";
                    Log.d("FirebaseInstanceId", str4);
                }
            } catch (IOException e) {
                String valueOf = String.valueOf(e.getMessage());
                Log.e("FirebaseInstanceId", valueOf.length() != 0 ? "Topic sync failed: ".concat(valueOf) : new String("Topic sync failed: "));
                return false;
            }
        }
        return true;
    }

    private final String zzak() {
        String zzae;
        synchronized (this.zzaj) {
            zzae = this.zzaj.zzae();
        }
        if (TextUtils.isEmpty(zzae)) {
            return null;
        }
        String[] split = zzae.split(",");
        if (split.length <= 1 || TextUtils.isEmpty(split[1])) {
            return null;
        }
        return split[1];
    }

    private final synchronized boolean zzk(String str) {
        synchronized (this.zzaj) {
            String zzae = this.zzaj.zzae();
            String valueOf = String.valueOf(str);
            if (!zzae.startsWith(valueOf.length() != 0 ? ",".concat(valueOf) : new String(","))) {
                return false;
            }
            String valueOf2 = String.valueOf(str);
            this.zzaj.zzf(zzae.substring((valueOf2.length() != 0 ? ",".concat(valueOf2) : new String(",")).length()));
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final synchronized Task<Void> zza(String str) {
        String zzae;
        TaskCompletionSource<Void> taskCompletionSource;
        synchronized (this.zzaj) {
            zzae = this.zzaj.zzae();
            zzaq zzaqVar = this.zzaj;
            StringBuilder sb = new StringBuilder(String.valueOf(zzae).length() + 1 + String.valueOf(str).length());
            sb.append(zzae);
            sb.append(",");
            sb.append(str);
            zzaqVar.zzf(sb.toString());
        }
        taskCompletionSource = new TaskCompletionSource<>();
        this.zzdg.put(Integer.valueOf(this.zzdf + (TextUtils.isEmpty(zzae) ? 0 : zzae.split(",").length - 1)), taskCompletionSource);
        return taskCompletionSource.getTask();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zza(FirebaseInstanceId firebaseInstanceId) {
        TaskCompletionSource<Void> remove;
        while (true) {
            synchronized (this) {
                String zzak = zzak();
                if (zzak == null) {
                    Log.d("FirebaseInstanceId", "topic sync succeeded");
                    return true;
                }
                if (!zza(firebaseInstanceId, zzak)) {
                    return false;
                }
                synchronized (this) {
                    remove = this.zzdg.remove(Integer.valueOf(this.zzdf));
                    zzk(zzak);
                    this.zzdf++;
                }
                if (remove != null) {
                    remove.setResult(null);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final synchronized boolean zzaj() {
        return zzak() != null;
    }
}
