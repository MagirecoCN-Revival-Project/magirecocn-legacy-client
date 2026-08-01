package cn.thinkingdata.core.network;

import android.os.Handler;
import android.os.Looper;

/* loaded from: classes.dex */
public abstract class TEHttpCallback {
    static Handler sMainHandler = new Handler(Looper.getMainLooper());
    public boolean callBackOnMainThread = false;

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onError(final String str) {
        if (this.callBackOnMainThread) {
            sMainHandler.post(new Runnable() { // from class: cn.thinkingdata.core.network.TEHttpCallback.1
                @Override // java.lang.Runnable
                public void run() {
                    TEHttpCallback.this.onFailure(str);
                }
            });
        } else {
            onFailure(str);
        }
    }

    public abstract void onFailure(String str);

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onResponse(final String str) {
        if (this.callBackOnMainThread) {
            sMainHandler.post(new Runnable() { // from class: cn.thinkingdata.core.network.TEHttpCallback.2
                @Override // java.lang.Runnable
                public void run() {
                    TEHttpCallback.this.onSuccess(str);
                }
            });
        } else {
            onSuccess(str);
        }
    }

    public abstract void onSuccess(String str);
}
