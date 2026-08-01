package com.google.android.gms.internal.gtm;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public class zzvq {
    private static final zzuj zzb = zzuj.zza();
    protected volatile zzwk zza;
    private volatile zztd zzc;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzvq)) {
            return false;
        }
        zzvq zzvqVar = (zzvq) obj;
        zzwk zzwkVar = this.zza;
        zzwk zzwkVar2 = zzvqVar.zza;
        if (zzwkVar == null && zzwkVar2 == null) {
            return zzb().equals(zzvqVar.zzb());
        }
        if (zzwkVar != null && zzwkVar2 != null) {
            return zzwkVar.equals(zzwkVar2);
        }
        if (zzwkVar != null) {
            zzvqVar.zzd(zzwkVar.zzar());
            return zzwkVar.equals(zzvqVar.zza);
        }
        zzd(zzwkVar2.zzar());
        return this.zza.equals(zzwkVar2);
    }

    public int hashCode() {
        return 1;
    }

    public final int zza() {
        if (this.zzc != null) {
            return ((zzta) this.zzc).zza.length;
        }
        if (this.zza != null) {
            return this.zza.zzX();
        }
        return 0;
    }

    public final zztd zzb() {
        if (this.zzc != null) {
            return this.zzc;
        }
        synchronized (this) {
            if (this.zzc != null) {
                return this.zzc;
            }
            if (this.zza == null) {
                this.zzc = zztd.zzb;
            } else {
                this.zzc = this.zza.zzR();
            }
            return this.zzc;
        }
    }

    public final zzwk zzc(zzwk zzwkVar) {
        zzwk zzwkVar2 = this.zza;
        this.zzc = null;
        this.zza = zzwkVar;
        return zzwkVar2;
    }

    protected final void zzd(zzwk zzwkVar) {
        if (this.zza != null) {
            return;
        }
        synchronized (this) {
            if (this.zza == null) {
                try {
                    this.zza = zzwkVar;
                    this.zzc = zztd.zzb;
                } catch (zzvk unused) {
                    this.zza = zzwkVar;
                    this.zzc = zztd.zzb;
                }
            }
        }
    }
}
