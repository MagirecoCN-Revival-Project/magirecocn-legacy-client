package com.google.android.gms.tagmanager;

import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzfm implements zzav {
    final /* synthetic */ TagManager zza;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzfm(TagManager tagManager) {
        this.zza = tagManager;
    }

    @Override // com.google.android.gms.tagmanager.zzav
    public final void zza(Map<String, Object> map) {
        Object obj = map.get("event");
        if (obj != null) {
            TagManager.zzb(this.zza, obj.toString());
        }
    }
}
