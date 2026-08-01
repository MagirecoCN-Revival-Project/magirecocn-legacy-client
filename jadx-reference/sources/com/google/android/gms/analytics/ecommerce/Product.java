package com.google.android.gms.analytics.ecommerce;

import com.google.android.gms.analytics.zzd;
import com.google.android.gms.analytics.zzj;
import com.google.android.gms.common.internal.Preconditions;
import java.util.HashMap;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public class Product {
    Map<String, String> zza = new HashMap();

    public Product setBrand(String str) {
        zzb("br", str);
        return this;
    }

    public Product setCategory(String str) {
        zzb("ca", str);
        return this;
    }

    public Product setCouponCode(String str) {
        zzb("cc", str);
        return this;
    }

    public Product setCustomDimension(int i, String str) {
        zzb(zzd.zzc(i), str);
        return this;
    }

    public Product setCustomMetric(int i, int i2) {
        zzb(zzd.zzf(i), Integer.toString(i2));
        return this;
    }

    public Product setId(String str) {
        zzb("id", str);
        return this;
    }

    public Product setName(String str) {
        zzb("nm", str);
        return this;
    }

    public Product setPosition(int i) {
        zzb("ps", Integer.toString(i));
        return this;
    }

    public Product setPrice(double d) {
        zzb("pr", Double.toString(d));
        return this;
    }

    public Product setQuantity(int i) {
        zzb("qt", Integer.toString(i));
        return this;
    }

    public Product setVariant(String str) {
        zzb("va", str);
        return this;
    }

    public String toString() {
        return zzj.zzb(this.zza);
    }

    public final Map<String, String> zza(String str) {
        HashMap hashMap = new HashMap();
        for (Map.Entry<String, String> entry : this.zza.entrySet()) {
            String valueOf = String.valueOf(str);
            String valueOf2 = String.valueOf(entry.getKey());
            hashMap.put(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf), entry.getValue());
        }
        return hashMap;
    }

    final void zzb(String str, String str2) {
        Preconditions.checkNotNull(str, "Name should be non-null");
        this.zza.put(str, str2);
    }
}
