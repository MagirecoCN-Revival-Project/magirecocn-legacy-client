package com.google.android.gms.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import javax.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* loaded from: classes.dex */
public class GoogleSignatureVerifier {

    @Nullable
    private static GoogleSignatureVerifier zza;
    private final Context zzb;
    private volatile String zzc;

    public GoogleSignatureVerifier(Context context) {
        this.zzb = context.getApplicationContext();
    }

    public static GoogleSignatureVerifier getInstance(Context context) {
        Preconditions.checkNotNull(context);
        synchronized (GoogleSignatureVerifier.class) {
            if (zza == null) {
                zzm.zzd(context);
                zza = new GoogleSignatureVerifier(context);
            }
        }
        return zza;
    }

    @Nullable
    static final zzi zza(PackageInfo packageInfo, zzi... zziVarArr) {
        if (packageInfo.signatures == null) {
            return null;
        }
        if (packageInfo.signatures.length != 1) {
            Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
            return null;
        }
        zzj zzjVar = new zzj(packageInfo.signatures[0].toByteArray());
        for (int i = 0; i < zziVarArr.length; i++) {
            if (zziVarArr[i].equals(zzjVar)) {
                return zziVarArr[i];
            }
        }
        return null;
    }

    public static final boolean zzb(PackageInfo packageInfo, boolean z) {
        if (packageInfo != null && packageInfo.signatures != null) {
            if ((z ? zza(packageInfo, zzl.zza) : zza(packageInfo, zzl.zza[0])) != null) {
                return true;
            }
        }
        return false;
    }

    private final zzw zzc(String str, boolean z, boolean z2) {
        zzw zzc;
        if (str == null) {
            return zzw.zzc("null pkg");
        }
        if (!str.equals(this.zzc)) {
            if (zzm.zze()) {
                zzc = zzm.zzb(str, GooglePlayServicesUtilLight.honorsDebugCertificates(this.zzb), false, false);
            } else {
                try {
                    PackageInfo packageInfo = this.zzb.getPackageManager().getPackageInfo(str, 64);
                    boolean honorsDebugCertificates = GooglePlayServicesUtilLight.honorsDebugCertificates(this.zzb);
                    if (packageInfo == null) {
                        zzc = zzw.zzc("null pkg");
                    } else if (packageInfo.signatures == null || packageInfo.signatures.length != 1) {
                        zzc = zzw.zzc("single cert required");
                    } else {
                        zzj zzjVar = new zzj(packageInfo.signatures[0].toByteArray());
                        String str2 = packageInfo.packageName;
                        zzw zza2 = zzm.zza(str2, zzjVar, honorsDebugCertificates, false);
                        zzc = (!zza2.zza || packageInfo.applicationInfo == null || (packageInfo.applicationInfo.flags & 2) == 0 || !zzm.zza(str2, zzjVar, false, true).zza) ? zza2 : zzw.zzc("debuggable release cert app rejected");
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    return zzw.zzd(str.length() != 0 ? "no pkg ".concat(str) : new String("no pkg "), e);
                }
            }
            if (zzc.zza) {
                this.zzc = str;
            }
            return zzc;
        }
        return zzw.zzb();
    }

    public boolean isGooglePublicSignedPackage(PackageInfo packageInfo) {
        if (packageInfo == null) {
            return false;
        }
        if (zzb(packageInfo, false)) {
            return true;
        }
        if (zzb(packageInfo, true)) {
            if (GooglePlayServicesUtilLight.honorsDebugCertificates(this.zzb)) {
                return true;
            }
            Log.w("GoogleSignatureVerifier", "Test-keys aren't accepted on this build.");
        }
        return false;
    }

    public boolean isPackageGoogleSigned(String str) {
        zzw zzc = zzc(str, false, false);
        zzc.zze();
        return zzc.zza;
    }

    public boolean isUidGoogleSigned(int i) {
        zzw zzc;
        int length;
        String[] packagesForUid = this.zzb.getPackageManager().getPackagesForUid(i);
        if (packagesForUid != null && (length = packagesForUid.length) != 0) {
            zzc = null;
            int i2 = 0;
            while (true) {
                if (i2 < length) {
                    zzc = zzc(packagesForUid[i2], false, false);
                    if (zzc.zza) {
                        break;
                    }
                    i2++;
                } else {
                    Preconditions.checkNotNull(zzc);
                    break;
                }
            }
        } else {
            zzc = zzw.zzc("no pkgs");
        }
        zzc.zze();
        return zzc.zza;
    }
}
