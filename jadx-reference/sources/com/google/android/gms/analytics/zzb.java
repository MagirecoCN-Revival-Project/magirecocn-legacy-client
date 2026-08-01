package com.google.android.gms.analytics;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.gtm.zzav;
import com.google.android.gms.internal.gtm.zzaw;
import com.google.android.gms.internal.gtm.zzax;
import com.google.android.gms.internal.gtm.zzay;
import com.google.android.gms.internal.gtm.zzaz;
import com.google.android.gms.internal.gtm.zzba;
import com.google.android.gms.internal.gtm.zzbb;
import com.google.android.gms.internal.gtm.zzbc;
import com.google.android.gms.internal.gtm.zzbd;
import com.google.android.gms.internal.gtm.zzbe;
import com.google.android.gms.internal.gtm.zzbf;
import com.google.android.gms.internal.gtm.zzbg;
import com.google.android.gms.internal.gtm.zzbh;
import com.google.android.gms.internal.gtm.zzbr;
import com.google.android.gms.internal.gtm.zzbt;
import com.google.android.gms.internal.gtm.zzbv;
import com.google.android.gms.internal.gtm.zzbx;
import com.google.android.gms.internal.gtm.zzex;
import com.google.android.gms.internal.gtm.zzfs;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzb extends zzbr implements zzt {
    private static DecimalFormat zza;
    private final zzbv zzb;
    private final String zzc;
    private final Uri zzd;

    public zzb(zzbv zzbvVar, String str) {
        super(zzbvVar);
        Preconditions.checkNotEmpty(str);
        this.zzb = zzbvVar;
        this.zzc = str;
        this.zzd = zza(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Uri zza(String str) {
        Preconditions.checkNotEmpty(str);
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("uri");
        builder.authority("google-analytics.com");
        builder.path(str);
        return builder.build();
    }

    static String zzc(double d) {
        if (zza == null) {
            zza = new DecimalFormat("0.######");
        }
        return zza.format(d);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x003c, code lost:
    
        if (android.text.TextUtils.isEmpty(r6) != false) goto L9;
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0067 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x001e A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Map<String, String> zzd(zzh zzhVar) {
        String valueOf;
        HashMap hashMap = new HashMap();
        zzaz zzazVar = (zzaz) zzhVar.zzc(zzaz.class);
        if (zzazVar != null) {
            for (Map.Entry<String, Object> entry : zzazVar.zzd().entrySet()) {
                Object value = entry.getValue();
                if (value != null) {
                    if (value instanceof String) {
                        valueOf = (String) value;
                    } else if (value instanceof Double) {
                        Double d = (Double) value;
                        if (d.doubleValue() != 0.0d) {
                            valueOf = zzc(d.doubleValue());
                            if (valueOf != null) {
                                hashMap.put(entry.getKey(), valueOf);
                            }
                        }
                    } else {
                        if (!(value instanceof Boolean)) {
                            valueOf = String.valueOf(value);
                        } else if (value != Boolean.FALSE) {
                            valueOf = "1";
                        }
                        if (valueOf != null) {
                        }
                    }
                }
                valueOf = null;
                if (valueOf != null) {
                }
            }
        }
        zzbe zzbeVar = (zzbe) zzhVar.zzc(zzbe.class);
        if (zzbeVar != null) {
            zzf(hashMap, "t", zzbeVar.zzf());
            zzf(hashMap, "cid", zzbeVar.zze());
            zzf(hashMap, "uid", zzbeVar.zzg());
            zzf(hashMap, "sc", null);
            zzg(hashMap, "ni", zzbeVar.zzo());
            zzf(hashMap, "adid", zzbeVar.zzd());
            zzg(hashMap, "ate", zzbeVar.zzn());
        }
        zzbf zzbfVar = (zzbf) zzhVar.zzc(zzbf.class);
        if (zzbfVar != null) {
            zzf(hashMap, "cd", null);
            double zzd = zzbfVar.zzd();
            if (zzd != 0.0d) {
                hashMap.put("a", zzc(zzd));
            }
            zzf(hashMap, "dr", null);
        }
        if (((zzbc) zzhVar.zzc(zzbc.class)) != null) {
            zzf(hashMap, "ec", null);
            zzf(hashMap, "ea", null);
            zzf(hashMap, "el", null);
        }
        zzaw zzawVar = (zzaw) zzhVar.zzc(zzaw.class);
        if (zzawVar != null) {
            zzf(hashMap, "cn", zzawVar.zzl());
            zzf(hashMap, "cs", zzawVar.zzm());
            zzf(hashMap, "cm", zzawVar.zzk());
            zzf(hashMap, "ck", zzawVar.zzj());
            zzf(hashMap, "cc", zzawVar.zzf());
            zzf(hashMap, "ci", zzawVar.zzi());
            zzf(hashMap, "anid", zzawVar.zze());
            zzf(hashMap, "gclid", zzawVar.zzh());
            zzf(hashMap, "dclid", zzawVar.zzg());
            zzf(hashMap, FirebaseAnalytics.Param.ACLID, zzawVar.zzd());
        }
        if (((zzbd) zzhVar.zzc(zzbd.class)) != null) {
            zzf(hashMap, "exd", null);
        }
        if (((zzbg) zzhVar.zzc(zzbg.class)) != null) {
            zzf(hashMap, "sn", null);
            zzf(hashMap, "sa", null);
            zzf(hashMap, "st", null);
        }
        if (((zzbh) zzhVar.zzc(zzbh.class)) != null) {
            zzf(hashMap, "utv", null);
            zzf(hashMap, "utc", null);
            zzf(hashMap, "utl", null);
        }
        zzax zzaxVar = (zzax) zzhVar.zzc(zzax.class);
        if (zzaxVar != null) {
            for (Map.Entry<Integer, String> entry2 : zzaxVar.zzd().entrySet()) {
                String zzb = zzd.zzb(entry2.getKey().intValue());
                if (!TextUtils.isEmpty(zzb)) {
                    hashMap.put(zzb, entry2.getValue());
                }
            }
        }
        zzay zzayVar = (zzay) zzhVar.zzc(zzay.class);
        if (zzayVar != null) {
            for (Map.Entry<Integer, Double> entry3 : zzayVar.zzd().entrySet()) {
                String zze = zzd.zze(entry3.getKey().intValue());
                if (!TextUtils.isEmpty(zze)) {
                    hashMap.put(zze, zzc(entry3.getValue().doubleValue()));
                }
            }
        }
        zzbb zzbbVar = (zzbb) zzhVar.zzc(zzbb.class);
        if (zzbbVar != null) {
            Iterator<Promotion> it = zzbbVar.zze().iterator();
            int i = 1;
            while (it.hasNext()) {
                hashMap.putAll(it.next().zza(zzd.zzm(i)));
                i++;
            }
            Iterator<Product> it2 = zzbbVar.zzd().iterator();
            int i2 = 1;
            while (it2.hasNext()) {
                hashMap.putAll(it2.next().zza(zzd.zzk(i2)));
                i2++;
            }
            int i3 = 1;
            for (Map.Entry<String, List<Product>> entry4 : zzbbVar.zzf().entrySet()) {
                List<Product> value2 = entry4.getValue();
                String zzh = zzd.zzh(i3);
                int i4 = 1;
                for (Product product : value2) {
                    String valueOf2 = String.valueOf(zzh);
                    String valueOf3 = String.valueOf(zzd.zzi(i4));
                    hashMap.putAll(product.zza(valueOf3.length() != 0 ? valueOf2.concat(valueOf3) : new String(valueOf2)));
                    i4++;
                }
                if (!TextUtils.isEmpty(entry4.getKey())) {
                    hashMap.put(String.valueOf(zzh).concat("nm"), entry4.getKey());
                }
                i3++;
            }
        }
        zzba zzbaVar = (zzba) zzhVar.zzc(zzba.class);
        if (zzbaVar != null) {
            zzf(hashMap, "ul", zzbaVar.zzd());
            int i5 = zzbaVar.zza;
            int i6 = zzbaVar.zzb;
            if (i5 > 0 && i6 > 0) {
                StringBuilder sb = new StringBuilder(23);
                sb.append(i5);
                sb.append("x");
                sb.append(i6);
                hashMap.put("sr", sb.toString());
            }
        }
        zzav zzavVar = (zzav) zzhVar.zzc(zzav.class);
        if (zzavVar != null) {
            zzf(hashMap, "an", zzavVar.zzf());
            zzf(hashMap, "aid", zzavVar.zzd());
            zzf(hashMap, "aiid", zzavVar.zze());
            zzf(hashMap, "av", zzavVar.zzg());
        }
        return hashMap;
    }

    private static void zzf(Map<String, String> map, String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        map.put(str, str2);
    }

    private static void zzg(Map<String, String> map, String str, boolean z) {
        if (z) {
            map.put(str, "1");
        }
    }

    @Override // com.google.android.gms.analytics.zzt
    public final Uri zzb() {
        return this.zzd;
    }

    @Override // com.google.android.gms.analytics.zzt
    public final void zze(zzh zzhVar) {
        Preconditions.checkNotNull(zzhVar);
        Preconditions.checkArgument(zzhVar.zzm(), "Can't deliver not submitted measurement");
        Preconditions.checkNotMainThread("deliver should be called on worker thread");
        zzh zzhVar2 = new zzh(zzhVar);
        zzbe zzbeVar = (zzbe) zzhVar2.zzb(zzbe.class);
        if (TextUtils.isEmpty(zzbeVar.zzf())) {
            zzz().zzc(zzd(zzhVar2), "Ignoring measurement without type");
            return;
        }
        if (TextUtils.isEmpty(zzbeVar.zze())) {
            zzz().zzc(zzd(zzhVar2), "Ignoring measurement without client id");
            return;
        }
        if (this.zzb.zzc().getAppOptOut()) {
            return;
        }
        if (zzfs.zzj(0.0d, zzbeVar.zze())) {
            zzG("Sampling enabled. Hit sampled out. sampling rate", Double.valueOf(0.0d));
            return;
        }
        Map<String, String> zzd = zzd(zzhVar2);
        zzd.put("v", "1");
        zzd.put("_v", zzbt.zzb);
        zzd.put("tid", this.zzc);
        if (this.zzb.zzc().isDryRunEnabled()) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : zzd.entrySet()) {
                if (sb.length() != 0) {
                    sb.append(", ");
                }
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
            }
            zzN("Dry run is enabled. GoogleAnalytics would have sent", sb.toString());
            return;
        }
        HashMap hashMap = new HashMap();
        zzfs.zzg(hashMap, "uid", zzbeVar.zzg());
        zzav zzavVar = (zzav) zzhVar.zzc(zzav.class);
        if (zzavVar != null) {
            zzfs.zzg(hashMap, "an", zzavVar.zzf());
            zzfs.zzg(hashMap, "aid", zzavVar.zzd());
            zzfs.zzg(hashMap, "av", zzavVar.zzg());
            zzfs.zzg(hashMap, "aiid", zzavVar.zze());
        }
        zzd.put("_s", String.valueOf(zzs().zza(new zzbx(0L, zzbeVar.zze(), this.zzc, !TextUtils.isEmpty(zzbeVar.zzd()), 0L, hashMap))));
        zzs().zzh(new zzex(zzz(), zzd, zzhVar.zza(), true));
    }
}
