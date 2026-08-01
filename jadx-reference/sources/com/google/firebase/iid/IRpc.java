package com.google.firebase.iid;

import com.google.android.gms.tasks.Task;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public interface IRpc {
    Task<Void> ackMessage(String str);

    Task<String> buildChannel(String str);

    Task<Void> deleteInstanceId(String str);

    Task<Void> deleteToken(String str, String str2, String str3);

    Task<String> getToken(String str, String str2, String str3);

    Task<Void> subscribeToTopic(String str, String str2, String str3);

    Task<Void> unsubscribeFromTopic(String str, String str2, String str3);
}
