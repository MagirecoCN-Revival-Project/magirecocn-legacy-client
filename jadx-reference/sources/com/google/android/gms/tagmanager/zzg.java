package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.Uri;
import com.adjust.sdk.Constants;
import com.google.android.gms.common.internal.ImagesContract;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzg implements zzav {
    private final Context zza;

    public zzg(Context context) {
        this.zza = context;
    }

    @Override // com.google.android.gms.tagmanager.zzav
    public final void zza(Map<String, Object> map) {
        String queryParameter;
        Object obj;
        Object obj2 = map.get("gtm.url");
        if (obj2 == null && (obj = map.get("gtm")) != null && (obj instanceof Map)) {
            obj2 = ((Map) obj).get(ImagesContract.URL);
        }
        if (obj2 == null || !(obj2 instanceof String) || (queryParameter = Uri.parse((String) obj2).getQueryParameter(Constants.REFERRER)) == null) {
            return;
        }
        zzcx.zzc(this.zza, queryParameter);
    }
}
