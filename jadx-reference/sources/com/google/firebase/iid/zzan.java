package com.google.firebase.iid;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import androidx.collection.SimpleArrayMap;
import androidx.work.WorkRequest;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.iid.zzi;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
final class zzan {
    private static int zzby;
    private static PendingIntent zzck;
    private final zzah zzao;
    private final SimpleArrayMap<String, TaskCompletionSource<Bundle>> zzcl = new SimpleArrayMap<>();
    private Messenger zzcm = new Messenger(new zzao(this, Looper.getMainLooper()));
    private Messenger zzcn;
    private zzi zzco;
    private final Context zzz;

    public zzan(Context context, zzah zzahVar) {
        this.zzz = context;
        this.zzao = zzahVar;
    }

    private static synchronized void zza(Context context, Intent intent) {
        synchronized (zzan.class) {
            if (zzck == null) {
                Intent intent2 = new Intent();
                intent2.setPackage("com.google.example.invalidpackage");
                zzck = PendingIntent.getBroadcast(context, 0, intent2, 0);
            }
            intent.putExtra("app", zzck);
        }
    }

    private final void zza(String str, Bundle bundle) {
        synchronized (this.zzcl) {
            TaskCompletionSource<Bundle> remove = this.zzcl.remove(str);
            if (remove != null) {
                remove.setResult(bundle);
            } else {
                String valueOf = String.valueOf(str);
                Log.w("FirebaseInstanceId", valueOf.length() != 0 ? "Missing callback for ".concat(valueOf) : new String("Missing callback for "));
            }
        }
    }

    private static synchronized String zzab() {
        String num;
        synchronized (zzan.class) {
            int i = zzby;
            zzby = i + 1;
            num = Integer.toString(i);
        }
        return num;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzb(Message message) {
        String str;
        String str2;
        if (message == null || !(message.obj instanceof Intent)) {
            str = "FirebaseInstanceId";
            str2 = "Dropping invalid message";
        } else {
            Intent intent = (Intent) message.obj;
            intent.setExtrasClassLoader(new zzi.zza());
            if (intent.hasExtra("google.messenger")) {
                Parcelable parcelableExtra = intent.getParcelableExtra("google.messenger");
                if (parcelableExtra instanceof zzi) {
                    this.zzco = (zzi) parcelableExtra;
                }
                if (parcelableExtra instanceof Messenger) {
                    this.zzcn = (Messenger) parcelableExtra;
                }
            }
            Intent intent2 = (Intent) message.obj;
            String action = intent2.getAction();
            if (!"com.google.android.c2dm.intent.REGISTRATION".equals(action)) {
                if (Log.isLoggable("FirebaseInstanceId", 3)) {
                    String valueOf = String.valueOf(action);
                    Log.d("FirebaseInstanceId", valueOf.length() != 0 ? "Unexpected response action: ".concat(valueOf) : new String("Unexpected response action: "));
                    return;
                }
                return;
            }
            String stringExtra = intent2.getStringExtra("registration_id");
            if (stringExtra == null) {
                stringExtra = intent2.getStringExtra("unregistered");
            }
            if (stringExtra != null) {
                Matcher matcher = Pattern.compile("\\|ID\\|([^|]+)\\|:?+(.*)").matcher(stringExtra);
                if (!matcher.matches()) {
                    if (Log.isLoggable("FirebaseInstanceId", 3)) {
                        String valueOf2 = String.valueOf(stringExtra);
                        Log.d("FirebaseInstanceId", valueOf2.length() != 0 ? "Unexpected response string: ".concat(valueOf2) : new String("Unexpected response string: "));
                        return;
                    }
                    return;
                }
                String group = matcher.group(1);
                String group2 = matcher.group(2);
                Bundle extras = intent2.getExtras();
                extras.putString("registration_id", group2);
                zza(group, extras);
                return;
            }
            String stringExtra2 = intent2.getStringExtra("error");
            if (stringExtra2 == null) {
                String valueOf3 = String.valueOf(intent2.getExtras());
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf3).length() + 49);
                sb.append("Unexpected response, no error or registration id ");
                sb.append(valueOf3);
                Log.w("FirebaseInstanceId", sb.toString());
                return;
            }
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                String valueOf4 = String.valueOf(stringExtra2);
                Log.d("FirebaseInstanceId", valueOf4.length() != 0 ? "Received InstanceID error ".concat(valueOf4) : new String("Received InstanceID error "));
            }
            if (!stringExtra2.startsWith("|")) {
                synchronized (this.zzcl) {
                    for (int i = 0; i < this.zzcl.size(); i++) {
                        zza(this.zzcl.keyAt(i), intent2.getExtras());
                    }
                }
                return;
            }
            String[] split = stringExtra2.split("\\|");
            if (split.length > 2 && "ID".equals(split[1])) {
                String str3 = split[2];
                String str4 = split[3];
                if (str4.startsWith(":")) {
                    str4 = str4.substring(1);
                }
                zza(str3, intent2.putExtra("error", str4).getExtras());
                return;
            }
            str = "FirebaseInstanceId";
            String valueOf5 = String.valueOf(stringExtra2);
            str2 = valueOf5.length() != 0 ? "Unexpected structured response ".concat(valueOf5) : new String("Unexpected structured response ");
        }
        Log.w(str, str2);
    }

    private final Bundle zzd(Bundle bundle) throws IOException {
        Bundle zze = zze(bundle);
        if (zze == null || !zze.containsKey("google.messenger")) {
            return zze;
        }
        Bundle zze2 = zze(bundle);
        if (zze2 == null || !zze2.containsKey("google.messenger")) {
            return zze2;
        }
        return null;
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:56:0x00a8 */
    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: androidx.collection.SimpleArrayMap<java.lang.String, com.google.android.gms.tasks.TaskCompletionSource<android.os.Bundle>> */
    /* JADX DEBUG: Multi-variable search result rejected for r3v13, resolved type: int */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x00a0, code lost:
    
        if (r8.zzco != null) goto L19;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00ea A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.google.android.gms.tasks.TaskCompletionSource, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v15, types: [java.util.concurrent.TimeUnit] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:20:0x00ce -> B:21:0x00d9). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:54:0x00d4 -> B:21:0x00d9). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final Bundle zze(Bundle bundle) throws IOException {
        TaskCompletionSource taskCompletionSource;
        TaskCompletionSource taskCompletionSource2;
        String zzab = zzab();
        ?? taskCompletionSource3 = new TaskCompletionSource();
        synchronized (this.zzcl) {
            this.zzcl.put(zzab, taskCompletionSource3);
        }
        if (this.zzao.zzw() == 0) {
            throw new IOException("MISSING_INSTANCEID_SERVICE");
        }
        Intent intent = new Intent();
        intent.setPackage("com.google.android.gms");
        intent.setAction(this.zzao.zzw() == 2 ? "com.google.iid.TOKEN_REQUEST" : "com.google.android.c2dm.intent.REGISTER");
        intent.putExtras(bundle);
        zza(this.zzz, intent);
        StringBuilder sb = new StringBuilder(String.valueOf(zzab).length() + 5);
        sb.append("|ID|");
        sb.append(zzab);
        sb.append("|");
        intent.putExtra("kid", sb.toString());
        int i = 3;
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            String valueOf = String.valueOf(intent.getExtras());
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 8);
            sb2.append("Sending ");
            sb2.append(valueOf);
            Log.d("FirebaseInstanceId", sb2.toString());
        }
        intent.putExtra("google.messenger", this.zzcm);
        if (this.zzcn == null) {
            taskCompletionSource2 = taskCompletionSource3;
        }
        Message obtain = Message.obtain();
        obtain.obj = intent;
        try {
            Messenger messenger = this.zzcn;
            if (messenger != null) {
                messenger.send(obtain);
                taskCompletionSource = taskCompletionSource3;
            } else {
                this.zzco.send(obtain);
                taskCompletionSource = taskCompletionSource3;
            }
        } catch (RemoteException unused) {
            taskCompletionSource2 = taskCompletionSource3;
            if (Log.isLoggable("FirebaseInstanceId", i)) {
                Log.d("FirebaseInstanceId", "Messenger failed, fallback to startService");
                taskCompletionSource2 = taskCompletionSource3;
            }
            if (this.zzao.zzw() == 2) {
                this.zzz.sendBroadcast(intent);
                taskCompletionSource = taskCompletionSource2;
            } else {
                this.zzz.startService(intent);
                taskCompletionSource = taskCompletionSource2;
            }
            try {
                Task task = taskCompletionSource.getTask();
                taskCompletionSource3 = 30000;
                i = TimeUnit.MILLISECONDS;
                Bundle bundle2 = (Bundle) Tasks.await(task, WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS, i);
                synchronized (this.zzcl) {
                }
            } catch (Throwable th) {
                synchronized (this.zzcl) {
                    this.zzcl.remove(zzab);
                    throw th;
                }
            }
        }
        try {
            Task task2 = taskCompletionSource.getTask();
            taskCompletionSource3 = 30000;
            i = TimeUnit.MILLISECONDS;
            Bundle bundle22 = (Bundle) Tasks.await(task2, WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS, i);
            synchronized (this.zzcl) {
                this.zzcl.remove(zzab);
            }
            return bundle22;
        } catch (InterruptedException | TimeoutException unused2) {
            Log.w("FirebaseInstanceId", "No response");
            throw new IOException("TIMEOUT");
        } catch (ExecutionException e) {
            throw new IOException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final Bundle zzc(Bundle bundle) throws IOException {
        if (this.zzao.zzz() < 12000000) {
            return zzd(bundle);
        }
        try {
            return (Bundle) Tasks.await(zzv.zzc(this.zzz).zzb(1, bundle));
        } catch (InterruptedException | ExecutionException e) {
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                String valueOf = String.valueOf(e);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 22);
                sb.append("Error making request: ");
                sb.append(valueOf);
                Log.d("FirebaseInstanceId", sb.toString());
            }
            if ((e.getCause() instanceof zzaf) && ((zzaf) e.getCause()).getErrorCode() == 4) {
                return zzd(bundle);
            }
            return null;
        }
    }
}
