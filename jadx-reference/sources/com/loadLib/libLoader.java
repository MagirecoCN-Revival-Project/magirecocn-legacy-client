package com.loadLib;

import android.app.Activity;
import android.os.Handler;

/* loaded from: classes2.dex */
public class libLoader extends Activity {
    public static void loadLib() {
        new Handler().postDelayed(new Runnable() { // from class: com.loadLib.libLoader.1
            @Override // java.lang.Runnable
            public void run() {
                System.loadLibrary("uwasa");
            }
        }, 1L);
    }
}
