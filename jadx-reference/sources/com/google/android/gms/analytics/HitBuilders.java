package com.google.android.gms.analytics;

import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.internal.gtm.zzfa;
import com.google.android.gms.internal.gtm.zzfs;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public class HitBuilders {

    /* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
    @Deprecated
    /* loaded from: classes.dex */
    public static class AppViewBuilder extends HitBuilder<AppViewBuilder> {
        public AppViewBuilder() {
            set("&t", "screenview");
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ AppViewBuilder addImpression(Product product, String str) {
            super.addImpression(product, str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ AppViewBuilder addProduct(Product product) {
            super.addProduct(product);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ AppViewBuilder addPromotion(Promotion promotion) {
            super.addPromotion(promotion);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ AppViewBuilder setCampaignParamsFromUrl(String str) {
            super.setCampaignParamsFromUrl(str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ AppViewBuilder setCustomDimension(int i, String str) {
            super.setCustomDimension(i, str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ AppViewBuilder setCustomMetric(int i, float f) {
            super.setCustomMetric(i, f);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ AppViewBuilder setNewSession() {
            super.setNewSession();
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ AppViewBuilder setNonInteraction(boolean z) {
            super.setNonInteraction(z);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ AppViewBuilder setProductAction(ProductAction productAction) {
            super.setProductAction(productAction);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ AppViewBuilder setPromotionAction(String str) {
            super.setPromotionAction(str);
            return this;
        }
    }

    /* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
    /* loaded from: classes.dex */
    public static class ExceptionBuilder extends HitBuilder<ExceptionBuilder> {
        public ExceptionBuilder() {
            set("&t", "exception");
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ExceptionBuilder addImpression(Product product, String str) {
            super.addImpression(product, str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ExceptionBuilder addProduct(Product product) {
            super.addProduct(product);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ExceptionBuilder addPromotion(Promotion promotion) {
            super.addPromotion(promotion);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ExceptionBuilder setCampaignParamsFromUrl(String str) {
            super.setCampaignParamsFromUrl(str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ExceptionBuilder setCustomDimension(int i, String str) {
            super.setCustomDimension(i, str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ExceptionBuilder setCustomMetric(int i, float f) {
            super.setCustomMetric(i, f);
            return this;
        }

        public ExceptionBuilder setDescription(String str) {
            set("&exd", str);
            return this;
        }

        public ExceptionBuilder setFatal(boolean z) {
            set("&exf", zzfs.zzc(z));
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ExceptionBuilder setNewSession() {
            super.setNewSession();
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ExceptionBuilder setNonInteraction(boolean z) {
            super.setNonInteraction(z);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ExceptionBuilder setProductAction(ProductAction productAction) {
            super.setProductAction(productAction);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ExceptionBuilder setPromotionAction(String str) {
            super.setPromotionAction(str);
            return this;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
    /* loaded from: classes.dex */
    public static class HitBuilder<T extends HitBuilder> {
        ProductAction zza;
        private Map<String, String> zze = new HashMap();
        Map<String, List<Product>> zzb = new HashMap();
        List<Promotion> zzc = new ArrayList();
        List<Product> zzd = new ArrayList();

        protected HitBuilder() {
        }

        private final T zza(String str, String str2) {
            if (str2 != null) {
                this.zze.put(str, str2);
            }
            return this;
        }

        public T addImpression(Product product, String str) {
            if (product == null) {
                zzfa.zze("product should be non-null");
                return this;
            }
            if (str == null) {
                str = "";
            }
            if (!this.zzb.containsKey(str)) {
                this.zzb.put(str, new ArrayList());
            }
            this.zzb.get(str).add(product);
            return this;
        }

        public T addProduct(Product product) {
            if (product == null) {
                zzfa.zze("product should be non-null");
                return this;
            }
            this.zzd.add(product);
            return this;
        }

        public T addPromotion(Promotion promotion) {
            if (promotion == null) {
                zzfa.zze("promotion should be non-null");
                return this;
            }
            this.zzc.add(promotion);
            return this;
        }

        public Map<String, String> build() {
            HashMap hashMap = new HashMap(this.zze);
            ProductAction productAction = this.zza;
            if (productAction != null) {
                hashMap.putAll(productAction.zza());
            }
            Iterator<Promotion> it = this.zzc.iterator();
            int i = 1;
            while (it.hasNext()) {
                hashMap.putAll(it.next().zza(zzd.zzl(i)));
                i++;
            }
            Iterator<Product> it2 = this.zzd.iterator();
            int i2 = 1;
            while (it2.hasNext()) {
                hashMap.putAll(it2.next().zza(zzd.zzj(i2)));
                i2++;
            }
            int i3 = 1;
            for (Map.Entry<String, List<Product>> entry : this.zzb.entrySet()) {
                List<Product> value = entry.getValue();
                String zzg = zzd.zzg(i3);
                int i4 = 1;
                for (Product product : value) {
                    String valueOf = String.valueOf(zzg);
                    String valueOf2 = String.valueOf(zzd.zzi(i4));
                    hashMap.putAll(product.zza(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf)));
                    i4++;
                }
                if (!TextUtils.isEmpty(entry.getKey())) {
                    hashMap.put(String.valueOf(zzg).concat("nm"), entry.getKey());
                }
                i3++;
            }
            return hashMap;
        }

        protected String get(String str) {
            return this.zze.get(str);
        }

        public final T set(String str, String str2) {
            if (str != null) {
                this.zze.put(str, str2);
            } else {
                zzfa.zze("HitBuilder.set() called with a null paramName.");
            }
            return this;
        }

        public final T setAll(Map<String, String> map) {
            if (map == null) {
                return this;
            }
            this.zze.putAll(new HashMap(map));
            return this;
        }

        /* JADX WARN: Code restructure failed: missing block: B:11:0x0034, code lost:
        
            if (r15.contains("=") == false) goto L29;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public T setCampaignParamsFromUrl(String str) {
            String str2 = null;
            if (!TextUtils.isEmpty(str)) {
                if (str.contains("?")) {
                    String[] split = str.split("[\\?]");
                    if (split.length > 1) {
                        str = split[1];
                    }
                }
                if (str.contains("%3D")) {
                    try {
                        str = URLDecoder.decode(str, "UTF-8");
                    } catch (UnsupportedEncodingException unused) {
                    }
                }
                Map<String, String> zzf = zzfs.zzf(str);
                String[] strArr = {"dclid", "utm_source", "gclid", FirebaseAnalytics.Param.ACLID, "utm_campaign", "utm_medium", "utm_term", "utm_content", "utm_id", "anid", "gmob_t"};
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 11; i++) {
                    if (!TextUtils.isEmpty(zzf.get(strArr[i]))) {
                        if (sb.length() > 0) {
                            sb.append("&");
                        }
                        sb.append(strArr[i]);
                        sb.append("=");
                        sb.append(zzf.get(strArr[i]));
                    }
                }
                str2 = sb.toString();
            }
            if (TextUtils.isEmpty(str2)) {
                return this;
            }
            Map<String, String> zzf2 = zzfs.zzf(str2);
            zza("&cc", zzf2.get("utm_content"));
            zza("&cm", zzf2.get("utm_medium"));
            zza("&cn", zzf2.get("utm_campaign"));
            zza("&cs", zzf2.get("utm_source"));
            zza("&ck", zzf2.get("utm_term"));
            zza("&ci", zzf2.get("utm_id"));
            zza("&anid", zzf2.get("anid"));
            zza("&gclid", zzf2.get("gclid"));
            zza("&dclid", zzf2.get("dclid"));
            zza("&aclid", zzf2.get(FirebaseAnalytics.Param.ACLID));
            zza("&gmob_t", zzf2.get("gmob_t"));
            return this;
        }

        public T setCustomDimension(int i, String str) {
            set(zzd.zza(i), str);
            return this;
        }

        public T setCustomMetric(int i, float f) {
            set(zzd.zzd(i), Float.toString(f));
            return this;
        }

        protected T setHitType(String str) {
            set("&t", str);
            return this;
        }

        public T setNewSession() {
            set("&sc", "start");
            return this;
        }

        public T setNonInteraction(boolean z) {
            set("&ni", zzfs.zzc(z));
            return this;
        }

        public T setProductAction(ProductAction productAction) {
            this.zza = productAction;
            return this;
        }

        public T setPromotionAction(String str) {
            this.zze.put("&promoa", str);
            return this;
        }
    }

    /* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
    @Deprecated
    /* loaded from: classes.dex */
    public static class ItemBuilder extends HitBuilder<ItemBuilder> {
        public ItemBuilder() {
            set("&t", "item");
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ItemBuilder addImpression(Product product, String str) {
            super.addImpression(product, str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ItemBuilder addProduct(Product product) {
            super.addProduct(product);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ItemBuilder addPromotion(Promotion promotion) {
            super.addPromotion(promotion);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ItemBuilder setCampaignParamsFromUrl(String str) {
            super.setCampaignParamsFromUrl(str);
            return this;
        }

        public ItemBuilder setCategory(String str) {
            set("&iv", str);
            return this;
        }

        public ItemBuilder setCurrencyCode(String str) {
            set("&cu", str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ItemBuilder setCustomDimension(int i, String str) {
            super.setCustomDimension(i, str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ItemBuilder setCustomMetric(int i, float f) {
            super.setCustomMetric(i, f);
            return this;
        }

        public ItemBuilder setName(String str) {
            set("&in", str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ItemBuilder setNewSession() {
            super.setNewSession();
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ItemBuilder setNonInteraction(boolean z) {
            super.setNonInteraction(z);
            return this;
        }

        public ItemBuilder setPrice(double d) {
            set("&ip", Double.toString(d));
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ItemBuilder setProductAction(ProductAction productAction) {
            super.setProductAction(productAction);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ItemBuilder setPromotionAction(String str) {
            super.setPromotionAction(str);
            return this;
        }

        public ItemBuilder setQuantity(long j) {
            set("&iq", Long.toString(j));
            return this;
        }

        public ItemBuilder setSku(String str) {
            set("&ic", str);
            return this;
        }

        public ItemBuilder setTransactionId(String str) {
            set("&ti", str);
            return this;
        }
    }

    /* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
    /* loaded from: classes.dex */
    public static class ScreenViewBuilder extends HitBuilder<ScreenViewBuilder> {
        public ScreenViewBuilder() {
            set("&t", "screenview");
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ScreenViewBuilder addImpression(Product product, String str) {
            super.addImpression(product, str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ScreenViewBuilder addProduct(Product product) {
            super.addProduct(product);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ScreenViewBuilder addPromotion(Promotion promotion) {
            super.addPromotion(promotion);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ScreenViewBuilder setCampaignParamsFromUrl(String str) {
            super.setCampaignParamsFromUrl(str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ScreenViewBuilder setCustomDimension(int i, String str) {
            super.setCustomDimension(i, str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ScreenViewBuilder setCustomMetric(int i, float f) {
            super.setCustomMetric(i, f);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ScreenViewBuilder setNewSession() {
            super.setNewSession();
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ScreenViewBuilder setNonInteraction(boolean z) {
            super.setNonInteraction(z);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ScreenViewBuilder setProductAction(ProductAction productAction) {
            super.setProductAction(productAction);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ ScreenViewBuilder setPromotionAction(String str) {
            super.setPromotionAction(str);
            return this;
        }
    }

    /* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
    /* loaded from: classes.dex */
    public static class SocialBuilder extends HitBuilder<SocialBuilder> {
        public SocialBuilder() {
            set("&t", NotificationCompat.CATEGORY_SOCIAL);
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ SocialBuilder addImpression(Product product, String str) {
            super.addImpression(product, str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ SocialBuilder addProduct(Product product) {
            super.addProduct(product);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ SocialBuilder addPromotion(Promotion promotion) {
            super.addPromotion(promotion);
            return this;
        }

        public SocialBuilder setAction(String str) {
            set("&sa", str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ SocialBuilder setCampaignParamsFromUrl(String str) {
            super.setCampaignParamsFromUrl(str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ SocialBuilder setCustomDimension(int i, String str) {
            super.setCustomDimension(i, str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ SocialBuilder setCustomMetric(int i, float f) {
            super.setCustomMetric(i, f);
            return this;
        }

        public SocialBuilder setNetwork(String str) {
            set("&sn", str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ SocialBuilder setNewSession() {
            super.setNewSession();
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ SocialBuilder setNonInteraction(boolean z) {
            super.setNonInteraction(z);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ SocialBuilder setProductAction(ProductAction productAction) {
            super.setProductAction(productAction);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ SocialBuilder setPromotionAction(String str) {
            super.setPromotionAction(str);
            return this;
        }

        public SocialBuilder setTarget(String str) {
            set("&st", str);
            return this;
        }
    }

    /* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
    @Deprecated
    /* loaded from: classes.dex */
    public static class TransactionBuilder extends HitBuilder<TransactionBuilder> {
        public TransactionBuilder() {
            set("&t", "transaction");
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ TransactionBuilder addImpression(Product product, String str) {
            super.addImpression(product, str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ TransactionBuilder addProduct(Product product) {
            super.addProduct(product);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ TransactionBuilder addPromotion(Promotion promotion) {
            super.addPromotion(promotion);
            return this;
        }

        public TransactionBuilder setAffiliation(String str) {
            set("&ta", str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ TransactionBuilder setCampaignParamsFromUrl(String str) {
            super.setCampaignParamsFromUrl(str);
            return this;
        }

        public TransactionBuilder setCurrencyCode(String str) {
            set("&cu", str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ TransactionBuilder setCustomDimension(int i, String str) {
            super.setCustomDimension(i, str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ TransactionBuilder setCustomMetric(int i, float f) {
            super.setCustomMetric(i, f);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ TransactionBuilder setNewSession() {
            super.setNewSession();
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ TransactionBuilder setNonInteraction(boolean z) {
            super.setNonInteraction(z);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ TransactionBuilder setProductAction(ProductAction productAction) {
            super.setProductAction(productAction);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ TransactionBuilder setPromotionAction(String str) {
            super.setPromotionAction(str);
            return this;
        }

        public TransactionBuilder setRevenue(double d) {
            set("&tr", Double.toString(d));
            return this;
        }

        public TransactionBuilder setShipping(double d) {
            set("&ts", Double.toString(d));
            return this;
        }

        public TransactionBuilder setTax(double d) {
            set("&tt", Double.toString(d));
            return this;
        }

        public TransactionBuilder setTransactionId(String str) {
            set("&ti", str);
            return this;
        }
    }

    /* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
    /* loaded from: classes.dex */
    public static class EventBuilder extends HitBuilder<EventBuilder> {
        public EventBuilder() {
            set("&t", "event");
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ EventBuilder addImpression(Product product, String str) {
            super.addImpression(product, str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ EventBuilder addProduct(Product product) {
            super.addProduct(product);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ EventBuilder addPromotion(Promotion promotion) {
            super.addPromotion(promotion);
            return this;
        }

        public EventBuilder setAction(String str) {
            set("&ea", str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ EventBuilder setCampaignParamsFromUrl(String str) {
            super.setCampaignParamsFromUrl(str);
            return this;
        }

        public EventBuilder setCategory(String str) {
            set("&ec", str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ EventBuilder setCustomDimension(int i, String str) {
            super.setCustomDimension(i, str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ EventBuilder setCustomMetric(int i, float f) {
            super.setCustomMetric(i, f);
            return this;
        }

        public EventBuilder setLabel(String str) {
            set("&el", str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ EventBuilder setNewSession() {
            super.setNewSession();
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ EventBuilder setNonInteraction(boolean z) {
            super.setNonInteraction(z);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ EventBuilder setProductAction(ProductAction productAction) {
            super.setProductAction(productAction);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ EventBuilder setPromotionAction(String str) {
            super.setPromotionAction(str);
            return this;
        }

        public EventBuilder setValue(long j) {
            set("&ev", Long.toString(j));
            return this;
        }

        public EventBuilder(String str, String str2) {
            this();
            setCategory(str);
            setAction(str2);
        }
    }

    /* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
    /* loaded from: classes.dex */
    public static class TimingBuilder extends HitBuilder<TimingBuilder> {
        public TimingBuilder() {
            set("&t", "timing");
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ TimingBuilder addImpression(Product product, String str) {
            super.addImpression(product, str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ TimingBuilder addProduct(Product product) {
            super.addProduct(product);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ TimingBuilder addPromotion(Promotion promotion) {
            super.addPromotion(promotion);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ TimingBuilder setCampaignParamsFromUrl(String str) {
            super.setCampaignParamsFromUrl(str);
            return this;
        }

        public TimingBuilder setCategory(String str) {
            set("&utc", str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ TimingBuilder setCustomDimension(int i, String str) {
            super.setCustomDimension(i, str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ TimingBuilder setCustomMetric(int i, float f) {
            super.setCustomMetric(i, f);
            return this;
        }

        public TimingBuilder setLabel(String str) {
            set("&utl", str);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ TimingBuilder setNewSession() {
            super.setNewSession();
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ TimingBuilder setNonInteraction(boolean z) {
            super.setNonInteraction(z);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ TimingBuilder setProductAction(ProductAction productAction) {
            super.setProductAction(productAction);
            return this;
        }

        /* JADX DEBUG: Return type fixed from 'com.google.android.gms.analytics.HitBuilders$HitBuilder' to match base method */
        @Override // com.google.android.gms.analytics.HitBuilders.HitBuilder
        public final /* bridge */ /* synthetic */ TimingBuilder setPromotionAction(String str) {
            super.setPromotionAction(str);
            return this;
        }

        public TimingBuilder setValue(long j) {
            set("&utt", Long.toString(j));
            return this;
        }

        public TimingBuilder setVariable(String str) {
            set("&utv", str);
            return this;
        }

        public TimingBuilder(String str, String str2, long j) {
            this();
            setVariable(str2);
            setValue(j);
            setCategory(str);
        }
    }
}
