package com.google.android.gms.tagmanager;

import android.os.Build;
import java.io.File;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzbv {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean zza(String str) {
        try {
            if (Integer.parseInt(Build.VERSION.SDK) >= 9) {
                File file = new File(str);
                file.setReadable(false, false);
                file.setWritable(false, false);
                file.setReadable(true, true);
                file.setWritable(true, true);
                return true;
            }
        } catch (NumberFormatException unused) {
            String valueOf = String.valueOf(Build.VERSION.SDK);
            zzdh.zza(valueOf.length() != 0 ? "Invalid version number: ".concat(valueOf) : new String("Invalid version number: "));
        }
        return false;
    }
}
