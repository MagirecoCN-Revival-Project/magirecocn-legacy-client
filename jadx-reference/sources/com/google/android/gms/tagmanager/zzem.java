package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.res.Resources;
import com.google.android.gms.internal.gtm.zzfz;
import com.google.android.gms.internal.gtm.zzrl;
import com.google.android.gms.internal.gtm.zzro;
import com.google.android.gms.internal.gtm.zzrq;
import com.google.android.gms.internal.gtm.zzrs;
import com.google.android.gms.internal.gtm.zzru;
import com.google.android.gms.internal.gtm.zzrz;
import com.google.android.gms.internal.gtm.zzsa;
import com.google.android.gms.internal.gtm.zzuj;
import com.google.android.gms.internal.gtm.zzvk;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzem implements zzak {
    private final Context zza;
    private final String zzb;
    private final ExecutorService zzc = zzfz.zza().zza(2);
    private zzdg<zzrl> zzd;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzem(Context context, String str) {
        this.zza = context;
        this.zzb = str;
    }

    @Override // com.google.android.gms.common.api.Releasable
    public final synchronized void release() {
        this.zzc.shutdown();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x00b5 A[Catch: IOException -> 0x00e2, TryCatch #3 {IOException -> 0x00e2, blocks: (B:6:0x0042, B:8:0x004a, B:9:0x0062, B:11:0x0068, B:13:0x009f, B:15:0x00b5, B:18:0x00bd, B:20:0x00c1, B:24:0x00d6, B:26:0x00dc, B:31:0x00a4, B:29:0x00ab), top: B:5:0x0042, inners: #6, #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00bd A[Catch: IOException -> 0x00e2, TRY_LEAVE, TryCatch #3 {IOException -> 0x00e2, blocks: (B:6:0x0042, B:8:0x004a, B:9:0x0062, B:11:0x0068, B:13:0x009f, B:15:0x00b5, B:18:0x00bd, B:20:0x00c1, B:24:0x00d6, B:26:0x00dc, B:31:0x00a4, B:29:0x00ab), top: B:5:0x0042, inners: #6, #5 }] */
    @Override // com.google.android.gms.tagmanager.zzak
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final zzrs zza(int i) {
        zzrs zzrsVar;
        try {
            InputStream openRawResource = this.zza.getResources().openRawResource(i);
            String resourceName = this.zza.getResources().getResourceName(i);
            StringBuilder sb = new StringBuilder(String.valueOf(resourceName).length() + 66);
            sb.append("Attempting to load a container from the resource ID ");
            sb.append(i);
            sb.append(" (");
            sb.append(resourceName);
            sb.append(")");
            zzdh.zzb.zzd(sb.toString());
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                zzsa.zzc(openRawResource, byteArrayOutputStream);
                try {
                    com.google.android.gms.internal.gtm.zzak zzc = zzfv.zzc(zzcz.zza(new JSONObject(byteArrayOutputStream.toString("UTF-8"))));
                    zzru zza = zzrs.zza();
                    for (int i2 = 0; i2 < zzc.zzc(); i2++) {
                        zzrq zzb = zzro.zzb();
                        zzb.zzb(com.google.android.gms.internal.gtm.zzb.INSTANCE_NAME.toString(), zzc.zzk(i2));
                        zzb.zzb(com.google.android.gms.internal.gtm.zzb.FUNCTION.toString(), zzfv.zza(zzt.zzc()));
                        zzb.zzb(zzt.zzd(), zzc.zzl(i2));
                        zza.zzb(zzb.zza());
                    }
                    zzrsVar = zza.zza();
                } catch (UnsupportedEncodingException unused) {
                    zzdh.zzb.zza("Failed to convert binary resource to string for JSON parsing; the file format is not UTF-8 format.");
                    zzrsVar = null;
                    if (zzrsVar != null) {
                    }
                } catch (JSONException unused2) {
                    zzdh.zzc("Failed to extract the container from the resource file. Resource is a UTF-8 encoded string but doesn't contain a JSON container");
                    zzrsVar = null;
                    if (zzrsVar != null) {
                    }
                }
                if (zzrsVar != null) {
                    zzdh.zzb.zzd("The container was successfully loaded from the resource (using JSON file format)");
                    return zzrsVar;
                }
                try {
                    zzrs zzb2 = zzsa.zzb(com.google.android.gms.internal.gtm.zzaa.zzm(byteArrayOutputStream.toByteArray(), zzuj.zzb()));
                    zzdh.zzb.zzd("The container was successfully loaded from the resource (using binary file)");
                    return zzb2;
                } catch (zzrz unused3) {
                    zzdh.zzc("The resource file is invalid. The container from the binary file is invalid");
                    return null;
                } catch (zzvk unused4) {
                    zzdh.zza("The resource file is corrupted. The container cannot be extracted from the binary file");
                    return null;
                }
            } catch (IOException unused5) {
                String resourceName2 = this.zza.getResources().getResourceName(i);
                StringBuilder sb2 = new StringBuilder(String.valueOf(resourceName2).length() + 67);
                sb2.append("Error reading the default container with resource ID ");
                sb2.append(i);
                sb2.append(" (");
                sb2.append(resourceName2);
                sb2.append(")");
                zzdh.zzc(sb2.toString());
                return null;
            }
        } catch (Resources.NotFoundException unused6) {
            StringBuilder sb3 = new StringBuilder(98);
            sb3.append("Failed to load the container. No default container resource found with the resource ID ");
            sb3.append(i);
            zzdh.zzc(sb3.toString());
            return null;
        }
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public final void zzb() {
        this.zzc.execute(new zzek(this));
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public final void zzc(zzrl zzrlVar) {
        this.zzc.execute(new zzel(this, zzrlVar));
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public final void zzd(zzdg<zzrl> zzdgVar) {
        this.zzd = zzdgVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final File zze() {
        String valueOf = String.valueOf(this.zzb);
        return new File(this.zza.getDir("google_tagmanager", 0), valueOf.length() != 0 ? "resource_".concat(valueOf) : new String("resource_"));
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [18=4] */
    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzf() {
        com.google.android.gms.internal.gtm.zzah zzahVar;
        if (this.zzd == null) {
            throw new IllegalStateException("Callback must be set before execute");
        }
        zzdh.zzb.zzd("Attempting to load resource from disk");
        if ((zzea.zza().zze() == 2 || zzea.zza().zze() == 3) && this.zzb.equals(zzea.zza().zzc())) {
            this.zzd.zza(1);
            return;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(zze());
            try {
                try {
                    zzrl zzg = zzrl.zzg(fileInputStream, zzuj.zzb());
                    if (!zzg.zzk() && !zzg.zzl()) {
                        throw new IllegalArgumentException("Resource and SupplementedResource are NULL.");
                    }
                    zzdg<zzrl> zzdgVar = this.zzd;
                    if (zzg.zzl()) {
                        zzahVar = zzg.zzd().zzZ();
                    } else {
                        com.google.android.gms.internal.gtm.zzaa zzc = zzg.zzc();
                        com.google.android.gms.internal.gtm.zzah zzd = com.google.android.gms.internal.gtm.zzai.zzd();
                        zzd.zzc(zzc);
                        zzd.zza();
                        zzd.zzb(zzc.zzo());
                        zzahVar = zzd;
                    }
                    ((zzae) zzdgVar).zza.zzu(zzahVar.zzC(), zzg.zza(), true);
                } catch (Throwable th) {
                    try {
                        fileInputStream.close();
                    } catch (IOException unused) {
                        zzdh.zzc("Error closing stream for reading resource from disk");
                    }
                    throw th;
                }
            } catch (IOException unused2) {
                this.zzd.zza(2);
                zzdh.zzc("Failed to read the resource from disk");
            } catch (IllegalArgumentException unused3) {
                this.zzd.zza(2);
                zzdh.zzc("Failed to read the resource from disk. The resource is inconsistent");
            }
            try {
                fileInputStream.close();
            } catch (IOException unused4) {
                zzdh.zzc("Error closing stream for reading resource from disk");
            }
            zzdh.zzb.zzd("The Disk resource was successfully read.");
        } catch (FileNotFoundException unused5) {
            zzdh.zzb.zza("Failed to find the resource in the disk");
            this.zzd.zza(1);
        }
    }
}
