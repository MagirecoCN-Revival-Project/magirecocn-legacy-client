package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.internal.gtm.zzsa;
import com.google.android.gms.internal.gtm.zzsd;
import com.google.android.gms.internal.gtm.zzse;
import com.google.android.gms.internal.gtm.zzsf;
import com.google.android.gms.internal.gtm.zzuj;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzeg implements Runnable {
    private final Context zza;
    private final zzse zzb;
    private final String zzc;
    private final String zzd;
    private zzdg<com.google.android.gms.internal.gtm.zzai> zze;
    private volatile zzap zzf;
    private volatile String zzg;
    private volatile String zzh;

    public zzeg(Context context, String str, zzap zzapVar) {
        zzse zzseVar = new zzse();
        this.zza = context;
        this.zzb = zzseVar;
        this.zzc = str;
        this.zzf = zzapVar;
        String valueOf = String.valueOf(str);
        String concat = valueOf.length() != 0 ? "/r?id=".concat(valueOf) : new String("/r?id=");
        this.zzd = concat;
        this.zzg = concat;
        this.zzh = null;
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [17=4, 46=4] */
    @Override // java.lang.Runnable
    public final void run() {
        zzam zzamVar;
        Clock clock;
        long j;
        boolean zzv;
        com.google.android.gms.internal.gtm.zzai zzaiVar;
        com.google.android.gms.internal.gtm.zzai zzaiVar2;
        zzam zzamVar2;
        if (this.zze == null) {
            throw new IllegalStateException("callback must be set before execute");
        }
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.zza.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            zzdh.zzb.zzd("...no network connectivity");
            this.zze.zza(1);
            return;
        }
        zzdh.zzb.zzd("Start loading resource from network ...");
        String zza = this.zzf.zza();
        String str = this.zzg;
        StringBuilder sb = new StringBuilder(zza.length() + 12 + String.valueOf(str).length());
        sb.append(zza);
        sb.append(str);
        sb.append("&v=a65833898");
        String sb2 = sb.toString();
        if (this.zzh != null && !this.zzh.trim().equals("")) {
            String valueOf = String.valueOf(sb2);
            String str2 = this.zzh;
            StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf).length() + 4 + String.valueOf(str2).length());
            sb3.append(valueOf);
            sb3.append("&pv=");
            sb3.append(str2);
            sb2 = sb3.toString();
        }
        int zze = zzea.zza().zze();
        InputStream inputStream = null;
        if (zze == 0) {
            throw null;
        }
        if (zze == 3) {
            sb2 = String.valueOf(sb2).concat("&gtm_debug=x");
        }
        zzsd zza2 = zzse.zza();
        try {
            try {
                inputStream = zza2.zza(sb2);
            } catch (zzsf unused) {
                String valueOf2 = String.valueOf(sb2);
                zzdh.zzc(valueOf2.length() != 0 ? "Error when loading resource for url: ".concat(valueOf2) : new String("Error when loading resource for url: "));
                this.zze.zza(4);
            } catch (FileNotFoundException unused2) {
                String str3 = this.zzc;
                StringBuilder sb4 = new StringBuilder(String.valueOf(sb2).length() + 79 + String.valueOf(str3).length());
                sb4.append("No data is retrieved from the given url: ");
                sb4.append(sb2);
                sb4.append(". Make sure container_id: ");
                sb4.append(str3);
                sb4.append(" is correct.");
                zzdh.zzc(sb4.toString());
                this.zze.zza(3);
                zza2.zzb();
                return;
            } catch (IOException e) {
                String message = e.getMessage();
                StringBuilder sb5 = new StringBuilder(String.valueOf(sb2).length() + 40 + String.valueOf(message).length());
                sb5.append("Error when loading resources from url: ");
                sb5.append(sb2);
                sb5.append(" ");
                sb5.append(message);
                zzdh.zzd(sb5.toString(), e);
                this.zze.zza(2);
                zza2.zzb();
                return;
            }
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                zzsa.zzc(inputStream, byteArrayOutputStream);
                com.google.android.gms.internal.gtm.zzai zzg = com.google.android.gms.internal.gtm.zzai.zzg(byteArrayOutputStream.toByteArray(), zzuj.zzb());
                String valueOf3 = String.valueOf(zzg);
                StringBuilder sb6 = new StringBuilder(String.valueOf(valueOf3).length() + 43);
                sb6.append("Successfully loaded supplemented resource: ");
                sb6.append(valueOf3);
                zzdh.zzb.zzd(sb6.toString());
                if (!zzg.zzm() && zzg.zza() == 0) {
                    String valueOf4 = String.valueOf(this.zzc);
                    zzdh.zzb.zzd(valueOf4.length() != 0 ? "No change for container: ".concat(valueOf4) : new String("No change for container: "));
                }
                zzdg<com.google.android.gms.internal.gtm.zzai> zzdgVar = this.zze;
                zzamVar = ((zzag) zzdgVar).zza.zzi;
                zzamVar.zzd();
                synchronized (((zzag) zzdgVar).zza) {
                    if (!zzg.zzm()) {
                        zzaiVar = ((zzag) zzdgVar).zza.zzn;
                        if (zzaiVar.zzm()) {
                            com.google.android.gms.internal.gtm.zzah zzZ = zzg.zzZ();
                            zzaiVar2 = ((zzag) zzdgVar).zza.zzn;
                            zzZ.zzc(zzaiVar2.zzc());
                            zzg = zzZ.zzC();
                        } else {
                            zzdh.zza("Current resource is null; network resource is also null");
                            zzamVar2 = ((zzag) zzdgVar).zza.zzi;
                            ((zzag) zzdgVar).zza.zzr(zzamVar2.zzb());
                        }
                    }
                    zzal zzalVar = ((zzag) zzdgVar).zza;
                    clock = zzalVar.zza;
                    zzalVar.zzu(zzg, clock.currentTimeMillis(), false);
                    j = ((zzag) zzdgVar).zza.zzo;
                    StringBuilder sb7 = new StringBuilder(58);
                    sb7.append("setting refresh time to current time: ");
                    sb7.append(j);
                    zzdh.zzb.zzd(sb7.toString());
                    zzv = ((zzag) zzdgVar).zza.zzv();
                    if (!zzv) {
                        ((zzag) zzdgVar).zza.zzt(zzg);
                    }
                }
                zza2.zzb();
                zzdh.zzb.zzd("Load resource from network finished.");
            } catch (IOException e2) {
                String message2 = e2.getMessage();
                StringBuilder sb8 = new StringBuilder(String.valueOf(sb2).length() + 51 + String.valueOf(message2).length());
                sb8.append("Error when parsing downloaded resources from url: ");
                sb8.append(sb2);
                sb8.append(" ");
                sb8.append(message2);
                zzdh.zzd(sb8.toString(), e2);
                this.zze.zza(3);
                zza2.zzb();
            }
        } catch (Throwable th) {
            zza2.zzb();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zza(String str) {
        if (str == null) {
            this.zzg = this.zzd;
        } else {
            zzdh.zzb.zza(str.length() != 0 ? "Setting CTFE URL path: ".concat(str) : new String("Setting CTFE URL path: "));
            this.zzg = str;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzb(zzdg<com.google.android.gms.internal.gtm.zzai> zzdgVar) {
        this.zze = zzdgVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzc(String str) {
        String valueOf = String.valueOf(str);
        zzdh.zzb.zza(valueOf.length() != 0 ? "Setting previous container version: ".concat(valueOf) : new String("Setting previous container version: "));
        this.zzh = str;
    }
}
