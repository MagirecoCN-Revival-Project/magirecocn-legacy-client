package cn.thinkingdata.analytics.utils.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* loaded from: classes.dex */
public class a extends BroadcastReceiver {
    private InterfaceC0015a a;

    /* renamed from: cn.thinkingdata.analytics.utils.broadcast.a$a, reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public interface InterfaceC0015a {
        void a();
    }

    public a(InterfaceC0015a interfaceC0015a) {
        a(interfaceC0015a);
    }

    public void a(InterfaceC0015a interfaceC0015a) {
        this.a = interfaceC0015a;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        InterfaceC0015a interfaceC0015a;
        if (!intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE") || (interfaceC0015a = this.a) == null) {
            return;
        }
        interfaceC0015a.a();
    }
}
