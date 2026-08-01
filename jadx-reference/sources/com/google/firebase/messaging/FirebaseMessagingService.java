package com.google.firebase.messaging;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.iid.zzap;
import com.google.firebase.iid.zzv;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes.dex */
public class FirebaseMessagingService extends com.google.firebase.iid.zzb {
    private static final Queue<String> zzdo = new ArrayDeque(10);

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void zzj(Bundle bundle) {
        Iterator<String> it = bundle.keySet().iterator();
        while (it.hasNext()) {
            String next = it.next();
            if (next != null && next.startsWith("google.c.")) {
                it.remove();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean zzk(Bundle bundle) {
        if (bundle == null) {
            return false;
        }
        return "1".equals(bundle.getString("google.c.a.e"));
    }

    public void onDeletedMessages() {
    }

    public void onMessageReceived(RemoteMessage remoteMessage) {
    }

    public void onMessageSent(String str) {
    }

    public void onSendError(String str, Exception exc) {
    }

    @Override // com.google.firebase.iid.zzb
    protected final Intent zzb(Intent intent) {
        return zzap.zzac().zzad();
    }

    @Override // com.google.firebase.iid.zzb
    public final boolean zzc(Intent intent) {
        if (!"com.google.firebase.messaging.NOTIFICATION_OPEN".equals(intent.getAction())) {
            return false;
        }
        PendingIntent pendingIntent = (PendingIntent) intent.getParcelableExtra("pending_intent");
        if (pendingIntent != null) {
            try {
                pendingIntent.send();
            } catch (PendingIntent.CanceledException unused) {
                Log.e("FirebaseMessaging", "Notification pending intent canceled");
            }
        }
        if (!zzk(intent.getExtras())) {
            return true;
        }
        zzb.zzd(this, intent);
        return true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00cd, code lost:
    
        if (r2.equals("send_error") == false) goto L41;
     */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00a4  */
    @Override // com.google.firebase.iid.zzb
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void zzd(Intent intent) {
        Task<Void> zza;
        boolean z;
        String action = intent.getAction();
        if (action == null) {
            action = "";
        }
        action.hashCode();
        if (action.equals("com.google.firebase.messaging.NOTIFICATION_DISMISS")) {
            if (zzk(intent.getExtras())) {
                zzb.zze(this, intent);
                return;
            }
            return;
        }
        if (!action.equals("com.google.android.c2dm.intent.RECEIVE")) {
            String valueOf = String.valueOf(intent.getAction());
            Log.d("FirebaseMessaging", valueOf.length() != 0 ? "Unknown intent action: ".concat(valueOf) : new String("Unknown intent action: "));
            return;
        }
        String stringExtra = intent.getStringExtra("google.message_id");
        char c = 2;
        if (TextUtils.isEmpty(stringExtra)) {
            zza = Tasks.forResult(null);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("google.message_id", stringExtra);
            zza = zzv.zzc(this).zza(2, bundle);
        }
        try {
            if (!TextUtils.isEmpty(stringExtra)) {
                Queue<String> queue = zzdo;
                if (queue.contains(stringExtra)) {
                    if (Log.isLoggable("FirebaseMessaging", 3)) {
                        String valueOf2 = String.valueOf(stringExtra);
                        Log.d("FirebaseMessaging", valueOf2.length() != 0 ? "Received duplicate message: ".concat(valueOf2) : new String("Received duplicate message: "));
                    }
                    z = true;
                    if (!z) {
                        String stringExtra2 = intent.getStringExtra("message_type");
                        if (stringExtra2 == null) {
                            stringExtra2 = "gcm";
                        }
                        stringExtra2.hashCode();
                        switch (stringExtra2.hashCode()) {
                            case -2062414158:
                                if (stringExtra2.equals("deleted_messages")) {
                                    c = 0;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 102161:
                                if (stringExtra2.equals("gcm")) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 814694033:
                                break;
                            case 814800675:
                                if (stringExtra2.equals("send_event")) {
                                    c = 3;
                                    break;
                                }
                                c = 65535;
                                break;
                            default:
                                c = 65535;
                                break;
                        }
                        switch (c) {
                            case 0:
                                onDeletedMessages();
                                break;
                            case 1:
                                if (zzk(intent.getExtras())) {
                                    zzb.zzc(this, intent);
                                }
                                Bundle extras = intent.getExtras();
                                if (extras == null) {
                                    extras = new Bundle();
                                }
                                extras.remove("androidx.contentpager.content.wakelockid");
                                if (zza.zzf(extras)) {
                                    if (!zza.zzd(this).zzh(extras)) {
                                        if (zzk(extras)) {
                                            zzb.zzf(this, intent);
                                        }
                                    }
                                }
                                onMessageReceived(new RemoteMessage(extras));
                                break;
                            case 2:
                                String stringExtra3 = intent.getStringExtra("google.message_id");
                                if (stringExtra3 == null) {
                                    stringExtra3 = intent.getStringExtra("message_id");
                                }
                                onSendError(stringExtra3, new SendException(intent.getStringExtra("error")));
                                break;
                            case 3:
                                onMessageSent(intent.getStringExtra("google.message_id"));
                                break;
                            default:
                                String valueOf3 = String.valueOf(stringExtra2);
                                Log.w("FirebaseMessaging", valueOf3.length() != 0 ? "Received message with unknown type: ".concat(valueOf3) : new String("Received message with unknown type: "));
                                break;
                        }
                    }
                    Tasks.await(zza, 1L, TimeUnit.SECONDS);
                    return;
                }
                if (queue.size() >= 10) {
                    queue.remove();
                }
                queue.add(stringExtra);
            }
            Tasks.await(zza, 1L, TimeUnit.SECONDS);
            return;
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            String valueOf4 = String.valueOf(e);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf4).length() + 20);
            sb.append("Message ack failed: ");
            sb.append(valueOf4);
            Log.w("FirebaseMessaging", sb.toString());
            return;
        }
        z = false;
        if (!z) {
        }
    }
}
