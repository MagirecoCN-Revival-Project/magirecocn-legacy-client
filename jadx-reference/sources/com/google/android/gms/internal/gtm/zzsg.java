package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzsg;
import com.google.android.gms.internal.gtm.zzsh;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public abstract class zzsg<MessageType extends zzsh<MessageType, BuilderType>, BuilderType extends zzsg<MessageType, BuilderType>> implements zzwj {
    /* JADX DEBUG: Method merged with bridge method: clone()Ljava/lang/Object; */
    @Override // 
    public abstract BuilderType zzv();

    protected abstract BuilderType zzw(MessageType messagetype);

    /* JADX DEBUG: Multi-variable search result rejected for r1v0, resolved type: com.google.android.gms.internal.gtm.zzsg<MessageType extends com.google.android.gms.internal.gtm.zzsh<MessageType, BuilderType>, BuilderType extends com.google.android.gms.internal.gtm.zzsg<MessageType, BuilderType>> */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.gtm.zzwj
    public final /* bridge */ /* synthetic */ zzwj zzx(zzwk zzwkVar) {
        if (!zzar().getClass().isInstance(zzwkVar)) {
            throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
        }
        return zzw((zzsh) zzwkVar);
    }
}
