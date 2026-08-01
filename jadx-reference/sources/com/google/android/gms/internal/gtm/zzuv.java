package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzuu;
import com.google.android.gms.internal.gtm.zzuv;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public abstract class zzuv<MessageType extends zzuv<MessageType, BuilderType>, BuilderType extends zzuu<MessageType, BuilderType>> extends zzuz<MessageType, BuilderType> implements zzwl {
    protected zzuo<zzuw> zza = zzuo.zzd();

    private final void zzc(zzux<MessageType, ?> zzuxVar) {
        if (zzuxVar.zza != ((zzuz) zzb(6, null, null))) {
            throw new IllegalArgumentException("This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings.");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final zzuo<zzuw> zzU() {
        if (this.zza.zzj()) {
            this.zza = this.zza.clone();
        }
        return this.zza;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v1, resolved type: Type */
    /* JADX WARN: Multi-variable type inference failed */
    public final <Type> Type zzV(zzuh<MessageType, Type> zzuhVar) {
        zzux<MessageType, ?> zzuxVar = (zzux) zzuhVar;
        zzc(zzuxVar);
        Type type = (Type) this.zza.zze(zzuxVar.zzd);
        if (type == 0) {
            return zzuxVar.zzb;
        }
        if (zzuxVar.zzd.zzc.zza() != zzyf.ENUM) {
            return type;
        }
        zzvc<?> zzvcVar = zzuxVar.zzd.zza;
        return (Type) zzyl.zzc(((Integer) type).intValue());
    }

    public final <Type> boolean zzW(zzuh<MessageType, Type> zzuhVar) {
        zzux<MessageType, ?> zzuxVar = (zzux) zzuhVar;
        zzc(zzuxVar);
        return this.zza.zza.get(zzuxVar.zzd) != null;
    }
}
