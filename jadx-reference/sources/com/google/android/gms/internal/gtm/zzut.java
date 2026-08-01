package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzut;
import com.google.android.gms.internal.gtm.zzuz;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public class zzut<MessageType extends zzuz<MessageType, BuilderType>, BuilderType extends zzut<MessageType, BuilderType>> extends zzsg<MessageType, BuilderType> {
    protected MessageType zza;
    protected boolean zzb = false;
    private final MessageType zzc;

    /* JADX INFO: Access modifiers changed from: protected */
    public zzut(MessageType messagetype) {
        this.zzc = messagetype;
        this.zza = (MessageType) messagetype.zzb(4, null, null);
    }

    private static final void zza(MessageType messagetype, MessageType messagetype2) {
        zzwt.zza().zzb(messagetype.getClass()).zzg(messagetype, messagetype2);
    }

    /* JADX DEBUG: Method merged with bridge method: zzC()Lcom/google/android/gms/internal/gtm/zzwk; */
    @Override // com.google.android.gms.internal.gtm.zzwj
    /* renamed from: zzA, reason: merged with bridge method [inline-methods] */
    public final MessageType zzC() {
        MessageType zzD = zzD();
        if (zzD.zzas()) {
            return zzD;
        }
        throw new zzxn(zzD);
    }

    /* JADX DEBUG: Method merged with bridge method: zzD()Lcom/google/android/gms/internal/gtm/zzwk; */
    @Override // com.google.android.gms.internal.gtm.zzwj
    /* renamed from: zzB, reason: merged with bridge method [inline-methods] */
    public MessageType zzD() {
        if (this.zzb) {
            return this.zza;
        }
        MessageType messagetype = this.zza;
        zzwt.zza().zzb(messagetype.getClass()).zzf(messagetype);
        this.zzb = true;
        return this.zza;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void zzF() {
        MessageType messagetype = (MessageType) this.zza.zzb(4, null, null);
        zza(messagetype, this.zza);
        this.zza = messagetype;
    }

    @Override // com.google.android.gms.internal.gtm.zzwl
    public final /* bridge */ /* synthetic */ zzwk zzar() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.gtm.zzwl
    public final boolean zzas() {
        throw null;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v0, resolved type: com.google.android.gms.internal.gtm.zzut<MessageType extends com.google.android.gms.internal.gtm.zzuz<MessageType, BuilderType>, BuilderType extends com.google.android.gms.internal.gtm.zzut<MessageType, BuilderType>> */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.gtm.zzsg
    protected final /* bridge */ /* synthetic */ zzsg zzw(zzsh zzshVar) {
        zzz((zzuz) zzshVar);
        return this;
    }

    /* JADX DEBUG: Method merged with bridge method: clone()Ljava/lang/Object; */
    /* JADX DEBUG: Method merged with bridge method: zzv()Lcom/google/android/gms/internal/gtm/zzsg; */
    @Override // com.google.android.gms.internal.gtm.zzsg
    /* renamed from: zzy, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public final BuilderType zzv() {
        BuilderType buildertype = (BuilderType) this.zzc.zzb(5, null, null);
        buildertype.zzz(zzD());
        return buildertype;
    }

    public final BuilderType zzz(MessageType messagetype) {
        if (this.zzb) {
            zzF();
            this.zzb = false;
        }
        zza(this.zza, messagetype);
        return this;
    }
}
