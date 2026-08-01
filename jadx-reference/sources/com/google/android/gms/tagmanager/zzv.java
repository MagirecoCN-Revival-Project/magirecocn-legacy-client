package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.Container;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzv implements zzaq {
    final /* synthetic */ Container zza;

    @Override // com.google.android.gms.tagmanager.zzaq
    public final Object zza(String str, Map<String, Object> map) {
        Container.FunctionCallMacroCallback zza = this.zza.zza(str);
        if (zza == null) {
            return null;
        }
        return zza.getValue(str, map);
    }
}
