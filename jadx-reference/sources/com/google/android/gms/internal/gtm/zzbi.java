package com.google.android.gms.internal.gtm;

import android.text.TextUtils;
import com.adjust.sdk.Constants;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Locale;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzbi extends zzbs {
    public static boolean zza;
    private AdvertisingIdClient.Info zzb;
    private final zzfo zzc;
    private String zzd;
    private boolean zze;
    private final Object zzf;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzbi(zzbv zzbvVar) {
        super(zzbvVar);
        this.zze = false;
        this.zzf = new Object();
        this.zzc = new zzfo(zzbvVar.zzr());
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0159, code lost:
    
        if (r0 == false) goto L83;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0040 A[Catch: all -> 0x0174, TryCatch #1 {, blocks: (B:3:0x0001, B:5:0x000b, B:8:0x0012, B:9:0x002e, B:12:0x0038, B:15:0x015b, B:16:0x0040, B:17:0x004a, B:34:0x015e, B:83:0x016f, B:85:0x0034, B:88:0x001c, B:90:0x0020, B:92:0x0028, B:93:0x0170, B:19:0x004b, B:64:0x0050, B:66:0x0068, B:69:0x007c, B:70:0x0085, B:72:0x008a, B:77:0x0093, B:22:0x00a7, B:27:0x00b8, B:29:0x00c6, B:30:0x00d1, B:31:0x00d5, B:35:0x00cb, B:36:0x00d8, B:38:0x00e2, B:39:0x00ec, B:40:0x00e7, B:41:0x00b2, B:42:0x00f2, B:44:0x0100, B:45:0x010b, B:47:0x0115, B:49:0x0117, B:51:0x011f, B:53:0x0121, B:55:0x0129, B:56:0x013b, B:58:0x0149, B:59:0x0154, B:60:0x0158, B:61:0x014e, B:62:0x0105, B:67:0x00a2), top: B:2:0x0001, inners: #2, #4, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0034 A[Catch: all -> 0x0174, TryCatch #1 {, blocks: (B:3:0x0001, B:5:0x000b, B:8:0x0012, B:9:0x002e, B:12:0x0038, B:15:0x015b, B:16:0x0040, B:17:0x004a, B:34:0x015e, B:83:0x016f, B:85:0x0034, B:88:0x001c, B:90:0x0020, B:92:0x0028, B:93:0x0170, B:19:0x004b, B:64:0x0050, B:66:0x0068, B:69:0x007c, B:70:0x0085, B:72:0x008a, B:77:0x0093, B:22:0x00a7, B:27:0x00b8, B:29:0x00c6, B:30:0x00d1, B:31:0x00d5, B:35:0x00cb, B:36:0x00d8, B:38:0x00e2, B:39:0x00ec, B:40:0x00e7, B:41:0x00b2, B:42:0x00f2, B:44:0x0100, B:45:0x010b, B:47:0x0115, B:49:0x0117, B:51:0x011f, B:53:0x0121, B:55:0x0129, B:56:0x013b, B:58:0x0149, B:59:0x0154, B:60:0x0158, B:61:0x014e, B:62:0x0105, B:67:0x00a2), top: B:2:0x0001, inners: #2, #4, #7 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final synchronized AdvertisingIdClient.Info zzc() {
        AdvertisingIdClient.Info info;
        String id;
        String str;
        IOException e;
        FileInputStream openFileInput;
        byte[] bArr;
        int read;
        boolean zzf;
        if (this.zzc.zzc(1000L)) {
            this.zzc.zzb();
            String str2 = null;
            try {
                try {
                    info = AdvertisingIdClient.getAdvertisingIdInfo(zzo());
                } catch (Exception e2) {
                    if (!zza) {
                        zza = true;
                        zzS("Error getting advertiser id", e2);
                    }
                    info = null;
                    AdvertisingIdClient.Info info2 = this.zzb;
                    if (info == null) {
                    }
                    if (!TextUtils.isEmpty(id)) {
                    }
                    this.zzb = info;
                    return this.zzb;
                }
            } catch (IllegalStateException unused) {
                zzR("IllegalStateException getting Ad Id Info. If you would like to see Audience reports, please ensure that you have added '<meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />' to your application manifest file. See http://goo.gl/naFqQk for details.");
                info = null;
                AdvertisingIdClient.Info info22 = this.zzb;
                if (info == null) {
                }
                if (!TextUtils.isEmpty(id)) {
                }
                this.zzb = info;
                return this.zzb;
            }
            AdvertisingIdClient.Info info222 = this.zzb;
            id = info == null ? null : info.getId();
            if (!TextUtils.isEmpty(id)) {
                String zzb = zzv().zzb();
                synchronized (this.zzf) {
                    if (!this.zze) {
                        try {
                            openFileInput = zzo().openFileInput("gaClientIdData");
                            bArr = new byte[128];
                            read = openFileInput.read(bArr, 0, 128);
                        } catch (FileNotFoundException unused2) {
                        } catch (IOException e3) {
                            str = null;
                            e = e3;
                        }
                        if (openFileInput.available() > 0) {
                            zzR("Hash file seems corrupted, deleting it.");
                            openFileInput.close();
                            zzo().deleteFile("gaClientIdData");
                        } else if (read <= 0) {
                            zzO("Hash file is empty.");
                            openFileInput.close();
                        } else {
                            str = new String(bArr, 0, read);
                            try {
                                openFileInput.close();
                            } catch (FileNotFoundException unused3) {
                            } catch (IOException e4) {
                                e = e4;
                                zzS("Error reading Hash file, deleting it", e);
                                zzo().deleteFile("gaClientIdData");
                            }
                            str2 = str;
                        }
                        this.zzd = str2;
                        this.zze = true;
                    } else if (TextUtils.isEmpty(this.zzd)) {
                        if (info222 != null) {
                            str2 = info222.getId();
                        }
                        if (str2 != null) {
                            String valueOf = String.valueOf(zzb);
                            this.zzd = zze(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                        } else {
                            String valueOf2 = String.valueOf(id);
                            String valueOf3 = String.valueOf(zzb);
                            zzf = zzf(valueOf3.length() != 0 ? valueOf2.concat(valueOf3) : new String(valueOf2));
                        }
                    }
                    String valueOf4 = String.valueOf(id);
                    String valueOf5 = String.valueOf(zzb);
                    String zze = zze(valueOf5.length() != 0 ? valueOf4.concat(valueOf5) : new String(valueOf4));
                    if (!TextUtils.isEmpty(zze)) {
                        if (!zze.equals(this.zzd)) {
                            if (!TextUtils.isEmpty(this.zzd)) {
                                zzO("Resetting the client id because Advertising Id changed.");
                                zzb = zzv().zze();
                                zzP("New client Id", zzb);
                            }
                            String valueOf6 = String.valueOf(id);
                            String valueOf7 = String.valueOf(zzb);
                            zzf = zzf(valueOf7.length() != 0 ? valueOf6.concat(valueOf7) : new String(valueOf6));
                        }
                    } else {
                        zzJ("Failed to reset client id on adid change. Not using adid");
                        this.zzb = new AdvertisingIdClient.Info("", false);
                    }
                }
            }
            this.zzb = info;
        }
        return this.zzb;
    }

    private static String zze(String str) {
        MessageDigest zze = zzfs.zze(Constants.MD5);
        if (zze == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new BigInteger(1, zze.digest(str.getBytes())));
    }

    private final boolean zzf(String str) {
        try {
            String zze = zze(str);
            zzO("Storing hashed adid.");
            FileOutputStream openFileOutput = zzo().openFileOutput("gaClientIdData", 0);
            openFileOutput.write(zze.getBytes());
            openFileOutput.close();
            this.zzd = zze;
            return true;
        } catch (IOException e) {
            zzK("Error creating hash file", e);
            return false;
        }
    }

    public final String zza() {
        zzW();
        AdvertisingIdClient.Info zzc = zzc();
        String id = zzc != null ? zzc.getId() : null;
        if (TextUtils.isEmpty(id)) {
            return null;
        }
        return id;
    }

    public final boolean zzb() {
        zzW();
        AdvertisingIdClient.Info zzc = zzc();
        return (zzc == null || zzc.isLimitAdTrackingEnabled()) ? false : true;
    }

    @Override // com.google.android.gms.internal.gtm.zzbs
    protected final void zzd() {
    }
}
