package jp.f4samurai;

import android.app.Application;
import android.util.Log;
import com.loadLib.libLoader;

/* loaded from: classes.dex */
public class MyApplication extends Application {
    public MyApplication() {
        libLoader.loadLib();
    }

    @Override // android.app.Application
    public void onCreate() {
        Log.i("MagiaDump", "=== [JAVA] MyApplication.onCreate standard load start ===");
        try {
            System.loadLibrary("cn_hook");
            Log.i("MagiaDump", "=== [JAVA] libcn_hook.so standard load SUCCESS! ===");
        } catch (Throwable th) {
            Log.e("MagiaDump", "=== [JAVA] libcn_hook.so standard load FAILED! ===", th);
        }
        super.onCreate();
    }
}
