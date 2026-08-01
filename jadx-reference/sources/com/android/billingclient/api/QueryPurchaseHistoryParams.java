package com.android.billingclient.api;

/* compiled from: com.android.billingclient:billing@@5.2.1 */
/* loaded from: classes.dex */
public final class QueryPurchaseHistoryParams {
    private final String zza;

    /* compiled from: com.android.billingclient:billing@@5.2.1 */
    /* loaded from: classes.dex */
    public static class Builder {
        private String zza;

        private Builder() {
        }

        /* synthetic */ Builder(zzbq zzbqVar) {
        }

        public QueryPurchaseHistoryParams build() {
            if (this.zza != null) {
                return new QueryPurchaseHistoryParams(this, null);
            }
            throw new IllegalArgumentException("Product type must be set");
        }

        public Builder setProductType(String str) {
            this.zza = str;
            return this;
        }
    }

    /* JADX DEBUG: Marked for inline */
    /* JADX DEBUG: Method not inlined, still used in: [com.android.billingclient.api.QueryPurchaseHistoryParams.Builder.build():com.android.billingclient.api.QueryPurchaseHistoryParams] */
    /* synthetic */ QueryPurchaseHistoryParams(Builder builder, zzbr zzbrVar) {
        this.zza = builder.zza;
    }

    public static Builder newBuilder() {
        return new Builder(null);
    }

    public final String zza() {
        return this.zza;
    }
}
