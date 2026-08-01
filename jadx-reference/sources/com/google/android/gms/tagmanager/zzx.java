package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.Container;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzx implements zzaq {
    final /* synthetic */ Container zza;

    @Override // com.google.android.gms.tagmanager.zzaq
    public final Object zza(String str, Map<String, Object> map) {
        Container.FunctionCallTagCallback zzb = this.zza.zzb(str);
        if (zzb != null) {
            zzb.execute(str, map);
        }
        return zzfv.zzm();
    }
}
