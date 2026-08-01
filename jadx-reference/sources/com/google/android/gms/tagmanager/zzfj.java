package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzfj implements zzbk {
    private final String zza;
    private final Context zzb;
    private final zzfi zzc;
    private final zzfh zzd = new zzfh();

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzfj(Context context, zzfi zzfiVar) {
        this.zzb = context.getApplicationContext();
        this.zzc = zzfiVar;
        String str = Build.VERSION.RELEASE;
        Locale locale = Locale.getDefault();
        String str2 = null;
        if (locale != null && locale.getLanguage() != null && locale.getLanguage().length() != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(locale.getLanguage().toLowerCase());
            if (locale.getCountry() != null && locale.getCountry().length() != 0) {
                sb.append("-");
                sb.append(locale.getCountry().toLowerCase());
            }
            str2 = sb.toString();
        }
        this.zza = String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", "GoogleTagManager", "4.00", str, str2, Build.MODEL, Build.ID);
    }

    static final URL zzc(zzca zzcaVar) {
        try {
            return new URL(zzcaVar.zzc());
        } catch (MalformedURLException unused) {
            zzdh.zza("Error trying to parse the GTM url.");
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00ab A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // com.google.android.gms.tagmanager.zzbk
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void zza(List<zzca> list) {
        boolean z;
        IOException e;
        int min = Math.min(list.size(), 40);
        boolean z2 = true;
        for (int i = 0; i < min; i++) {
            zzca zzcaVar = list.get(i);
            URL zzc = zzc(zzcaVar);
            if (zzc != null) {
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) zzc.openConnection();
                    InputStream inputStream = null;
                    if (z2) {
                        try {
                            zzdk.zza(this.zzb);
                        } catch (Throwable th) {
                            th = th;
                            z = true;
                            if (inputStream != null) {
                            }
                            httpURLConnection.disconnect();
                            throw th;
                            break;
                        }
                    }
                    try {
                        httpURLConnection.setRequestProperty("User-Agent", this.zza);
                        int responseCode = httpURLConnection.getResponseCode();
                        inputStream = httpURLConnection.getInputStream();
                        if (responseCode != 200) {
                            StringBuilder sb = new StringBuilder(25);
                            sb.append("Bad response: ");
                            sb.append(responseCode);
                            zzdh.zzc(sb.toString());
                            this.zzc.zza(zzcaVar);
                        } else {
                            ((zzdu) this.zzc).zza.zzl(zzcaVar.zzb());
                        }
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e2) {
                                e = e2;
                                z = false;
                                String valueOf = String.valueOf(e.getClass().getSimpleName());
                                zzdh.zzc(valueOf.length() == 0 ? "Exception sending hit: ".concat(valueOf) : new String("Exception sending hit: "));
                                zzdh.zzc(e.getMessage());
                                this.zzc.zza(zzcaVar);
                                z2 = z;
                            }
                        }
                        httpURLConnection.disconnect();
                        z2 = false;
                    } catch (Throwable th2) {
                        th = th2;
                        z = false;
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e3) {
                                e = e3;
                                String valueOf2 = String.valueOf(e.getClass().getSimpleName());
                                zzdh.zzc(valueOf2.length() == 0 ? "Exception sending hit: ".concat(valueOf2) : new String("Exception sending hit: "));
                                zzdh.zzc(e.getMessage());
                                this.zzc.zza(zzcaVar);
                                z2 = z;
                            }
                        }
                        httpURLConnection.disconnect();
                        throw th;
                        break;
                        break;
                    }
                } catch (IOException e4) {
                    z = z2;
                    e = e4;
                }
            } else {
                zzdh.zzc("No destination: discarding hit.");
                ((zzdu) this.zzc).zza.zzl(zzcaVar.zzb());
                long zzb = zzcaVar.zzb();
                StringBuilder sb2 = new StringBuilder(57);
                sb2.append("Permanent failure dispatching hitId: ");
                sb2.append(zzb);
                zzdh.zzb.zzd(sb2.toString());
            }
        }
    }

    @Override // com.google.android.gms.tagmanager.zzbk
    public final boolean zzb() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.zzb.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        zzdh.zzb.zzd("...no network connectivity");
        return false;
    }
}
