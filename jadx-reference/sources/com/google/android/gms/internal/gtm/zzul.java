package com.google.android.gms.internal.gtm;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzul extends zzuk<zzuw> {
    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.gtm.zzuk
    public final int zza(Map.Entry<?, ?> entry) {
        return ((zzuw) entry.getKey()).zzb;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.gtm.zzuk
    public final zzuo<zzuw> zzb(Object obj) {
        return ((zzuv) obj).zza;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.gtm.zzuk
    public final zzuo<zzuw> zzc(Object obj) {
        return ((zzuv) obj).zzU();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.gtm.zzuk
    public final Object zzd(zzuj zzujVar, zzwk zzwkVar, int i) {
        return zzujVar.zzc(zzwkVar, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.gtm.zzuk
    public final <UT, UB> UB zze(zzww zzwwVar, Object obj, zzuj zzujVar, zzuo<zzuw> zzuoVar, UB ub, zzxo<UT, UB> zzxoVar) throws IOException {
        Object valueOf;
        Object zze;
        zzux zzuxVar = (zzux) obj;
        zzuw zzuwVar = zzuxVar.zzd;
        int i = zzuwVar.zzb;
        if (zzuwVar.zzc == zzye.ENUM) {
            int zzg = zzwwVar.zzg();
            zzvc<?> zzvcVar = zzuxVar.zzd.zza;
            if (zzyl.zzc(zzg) == null) {
                return (UB) zzwz.zzD(i, zzg, ub, zzxoVar);
            }
            valueOf = Integer.valueOf(zzg);
        } else {
            switch (zzuxVar.zzd.zzc) {
                case DOUBLE:
                    valueOf = Double.valueOf(zzwwVar.zza());
                    break;
                case FLOAT:
                    valueOf = Float.valueOf(zzwwVar.zzb());
                    break;
                case INT64:
                    valueOf = Long.valueOf(zzwwVar.zzl());
                    break;
                case UINT64:
                    valueOf = Long.valueOf(zzwwVar.zzo());
                    break;
                case INT32:
                    valueOf = Integer.valueOf(zzwwVar.zzg());
                    break;
                case FIXED64:
                    valueOf = Long.valueOf(zzwwVar.zzk());
                    break;
                case FIXED32:
                    valueOf = Integer.valueOf(zzwwVar.zzf());
                    break;
                case BOOL:
                    valueOf = Boolean.valueOf(zzwwVar.zzS());
                    break;
                case STRING:
                    valueOf = zzwwVar.zzv();
                    break;
                case GROUP:
                    valueOf = zzwwVar.zzr(zzuxVar.zzc.getClass(), zzujVar);
                    break;
                case MESSAGE:
                    valueOf = zzwwVar.zzt(zzuxVar.zzc.getClass(), zzujVar);
                    break;
                case BYTES:
                    valueOf = zzwwVar.zzq();
                    break;
                case UINT32:
                    valueOf = Integer.valueOf(zzwwVar.zzj());
                    break;
                case ENUM:
                    throw new IllegalStateException("Shouldn't reach here.");
                case SFIXED32:
                    valueOf = Integer.valueOf(zzwwVar.zzh());
                    break;
                case SFIXED64:
                    valueOf = Long.valueOf(zzwwVar.zzm());
                    break;
                case SINT32:
                    valueOf = Integer.valueOf(zzwwVar.zzi());
                    break;
                case SINT64:
                    valueOf = Long.valueOf(zzwwVar.zzn());
                    break;
                default:
                    valueOf = null;
                    break;
            }
        }
        zzuxVar.zza();
        int ordinal = zzuxVar.zzd.zzc.ordinal();
        if ((ordinal == 9 || ordinal == 10) && (zze = zzuoVar.zze(zzuxVar.zzd)) != null) {
            valueOf = zzvi.zzg(zze, valueOf);
        }
        zzuoVar.zzi(zzuxVar.zzd, valueOf);
        return ub;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.gtm.zzuk
    public final void zzf(Object obj) {
        ((zzuv) obj).zza.zzg();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.gtm.zzuk
    public final void zzg(zzww zzwwVar, Object obj, zzuj zzujVar, zzuo<zzuw> zzuoVar) throws IOException {
        zzux zzuxVar = (zzux) obj;
        zzuoVar.zzi(zzuxVar.zzd, zzwwVar.zzt(zzuxVar.zzc.getClass(), zzujVar));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.gtm.zzuk
    public final void zzh(zztd zztdVar, Object obj, zzuj zzujVar, zzuo<zzuw> zzuoVar) throws IOException {
        byte[] bArr;
        zzux zzuxVar = (zzux) obj;
        zzwk zzD = zzuxVar.zzc.zzao().zzD();
        int zzd = zztdVar.zzd();
        if (zzd == 0) {
            bArr = zzvi.zzc;
        } else {
            byte[] bArr2 = new byte[zzd];
            zztdVar.zze(bArr2, 0, 0, zzd);
            bArr = bArr2;
        }
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        if (wrap.hasArray()) {
            zzsn zzsnVar = new zzsn(wrap, true);
            zzwt.zza().zzb(zzD.getClass()).zzh(zzD, zzsnVar, zzujVar);
            zzuoVar.zzi(zzuxVar.zzd, zzD);
            if (zzsnVar.zzc() != Integer.MAX_VALUE) {
                throw zzvk.zzb();
            }
            return;
        }
        throw new IllegalArgumentException("Direct buffers not yet supported");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.gtm.zzuk
    public final boolean zzi(zzwk zzwkVar) {
        return zzwkVar instanceof zzuv;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.gms.internal.gtm.zzuk
    public final void zzj(zztp zztpVar, Map.Entry<?, ?> entry) throws IOException {
        zzuw zzuwVar = (zzuw) entry.getKey();
        zzye zzyeVar = zzye.DOUBLE;
        switch (zzuwVar.zzc) {
            case DOUBLE:
                zztpVar.zzf(zzuwVar.zzb, ((Double) entry.getValue()).doubleValue());
                return;
            case FLOAT:
                zztpVar.zzo(zzuwVar.zzb, ((Float) entry.getValue()).floatValue());
                return;
            case INT64:
                zztpVar.zzt(zzuwVar.zzb, ((Long) entry.getValue()).longValue());
                return;
            case UINT64:
                zztpVar.zzK(zzuwVar.zzb, ((Long) entry.getValue()).longValue());
                return;
            case INT32:
                zztpVar.zzr(zzuwVar.zzb, ((Integer) entry.getValue()).intValue());
                return;
            case FIXED64:
                zztpVar.zzm(zzuwVar.zzb, ((Long) entry.getValue()).longValue());
                return;
            case FIXED32:
                zztpVar.zzk(zzuwVar.zzb, ((Integer) entry.getValue()).intValue());
                return;
            case BOOL:
                zztpVar.zzb(zzuwVar.zzb, ((Boolean) entry.getValue()).booleanValue());
                return;
            case STRING:
                zztpVar.zzG(zzuwVar.zzb, (String) entry.getValue());
                return;
            case GROUP:
                zztpVar.zzq(zzuwVar.zzb, entry.getValue(), zzwt.zza().zzb(entry.getValue().getClass()));
                return;
            case MESSAGE:
                zztpVar.zzv(zzuwVar.zzb, entry.getValue(), zzwt.zza().zzb(entry.getValue().getClass()));
                return;
            case BYTES:
                zztpVar.zzd(zzuwVar.zzb, (zztd) entry.getValue());
                return;
            case UINT32:
                zztpVar.zzI(zzuwVar.zzb, ((Integer) entry.getValue()).intValue());
                return;
            case ENUM:
                zztpVar.zzr(zzuwVar.zzb, ((Integer) entry.getValue()).intValue());
                return;
            case SFIXED32:
                zztpVar.zzx(zzuwVar.zzb, ((Integer) entry.getValue()).intValue());
                return;
            case SFIXED64:
                zztpVar.zzz(zzuwVar.zzb, ((Long) entry.getValue()).longValue());
                return;
            case SINT32:
                zztpVar.zzB(zzuwVar.zzb, ((Integer) entry.getValue()).intValue());
                return;
            case SINT64:
                zztpVar.zzD(zzuwVar.zzb, ((Long) entry.getValue()).longValue());
                return;
            default:
                return;
        }
    }
}
