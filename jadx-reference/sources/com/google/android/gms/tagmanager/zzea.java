package com.google.android.gms.tagmanager;

import android.net.Uri;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzea {
    private static zzea zza;
    private volatile int zze = 1;
    private volatile String zzc = null;
    private volatile String zzb = null;
    private volatile String zzd = null;

    zzea() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static zzea zza() {
        zzea zzeaVar;
        synchronized (zzea.class) {
            if (zza == null) {
                zza = new zzea();
            }
            zzeaVar = zza;
        }
        return zzeaVar;
    }

    private static final String zzf(String str) {
        return str.split("&")[0].split("=")[1];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final String zzb() {
        return this.zzc;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final String zzc() {
        return this.zzb;
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [7=4] */
    /* JADX INFO: Access modifiers changed from: package-private */
    public final synchronized boolean zzd(Uri uri) {
        try {
            String decode = URLDecoder.decode(uri.toString(), "UTF-8");
            if (!decode.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_auth=\\S+&gtm_preview=\\d+(&gtm_debug=x)?$")) {
                if (!decode.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_preview=$")) {
                    String valueOf = String.valueOf(decode);
                    zzdh.zzc(valueOf.length() != 0 ? "Invalid preview uri: ".concat(valueOf) : new String("Invalid preview uri: "));
                    return false;
                }
                if (!zzf(uri.getQuery()).equals(this.zzb)) {
                    return false;
                }
                String valueOf2 = String.valueOf(this.zzb);
                zzdh.zzb.zzd(valueOf2.length() != 0 ? "Exit preview mode for container: ".concat(valueOf2) : new String("Exit preview mode for container: "));
                this.zze = 1;
                this.zzc = null;
                return true;
            }
            String valueOf3 = String.valueOf(decode);
            zzdh.zzb.zzd(valueOf3.length() != 0 ? "Container preview url: ".concat(valueOf3) : new String("Container preview url: "));
            if (decode.matches(".*?&gtm_debug=x$")) {
                this.zze = 3;
            } else {
                this.zze = 2;
            }
            this.zzd = uri.getQuery().replace("&gtm_debug=x", "");
            if (this.zze == 2 || this.zze == 3) {
                String valueOf4 = String.valueOf(this.zzd);
                this.zzc = valueOf4.length() != 0 ? "/r?".concat(valueOf4) : new String("/r?");
            }
            this.zzb = zzf(this.zzd);
            return true;
        } catch (UnsupportedEncodingException unused) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final int zze() {
        return this.zze;
    }
}
