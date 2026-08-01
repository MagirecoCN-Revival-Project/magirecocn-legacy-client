package com.google.android.gms.internal.measurement;

import android.os.Binder;
import android.text.TextUtils;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.GoogleSignatureVerifier;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.UidVerifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

/* loaded from: classes.dex */
public final class zzgo extends zzfa {
    private final zzjs zzajy;
    private Boolean zzanm;
    private String zzann;

    public zzgo(zzjs zzjsVar) {
        this(zzjsVar, null);
    }

    private zzgo(zzjs zzjsVar, String str) {
        Preconditions.checkNotNull(zzjsVar);
        this.zzajy = zzjsVar;
        this.zzann = null;
    }

    private final void zzb(zzdz zzdzVar, boolean z) {
        Preconditions.checkNotNull(zzdzVar);
        zzc(zzdzVar.packageName, false);
        this.zzajy.zzgc().zzcf(zzdzVar.zzadm);
    }

    private final void zzc(String str, boolean z) {
        boolean z2;
        if (TextUtils.isEmpty(str)) {
            this.zzajy.zzgf().zzis().log("Measurement Service called without app package");
            throw new SecurityException("Measurement Service called without app package");
        }
        if (z) {
            try {
                if (this.zzanm == null) {
                    if (!"com.google.android.gms".equals(this.zzann) && !UidVerifier.isGooglePlayServicesUid(this.zzajy.getContext(), Binder.getCallingUid()) && !GoogleSignatureVerifier.getInstance(this.zzajy.getContext()).isUidGoogleSigned(Binder.getCallingUid())) {
                        z2 = false;
                        this.zzanm = Boolean.valueOf(z2);
                    }
                    z2 = true;
                    this.zzanm = Boolean.valueOf(z2);
                }
                if (this.zzanm.booleanValue()) {
                    return;
                }
            } catch (SecurityException e) {
                this.zzajy.zzgf().zzis().zzg("Measurement Service called with invalid calling package. appId", zzfh.zzbl(str));
                throw e;
            }
        }
        if (this.zzann == null && GooglePlayServicesUtilLight.uidHasPackageName(this.zzajy.getContext(), Binder.getCallingUid(), str)) {
            this.zzann = str;
        }
        if (str.equals(this.zzann)) {
        } else {
            throw new SecurityException(String.format("Unknown calling package name '%s'.", str));
        }
    }

    private final void zze(Runnable runnable) {
        Preconditions.checkNotNull(runnable);
        if (zzey.zzaih.get().booleanValue() && this.zzajy.zzge().zzjr()) {
            runnable.run();
        } else {
            this.zzajy.zzge().zzc(runnable);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzez
    public final List<zzjz> zza(zzdz zzdzVar, boolean z) {
        zzb(zzdzVar, false);
        try {
            List<zzkb> list = (List) this.zzajy.zzge().zzb(new zzhe(this, zzdzVar)).get();
            ArrayList arrayList = new ArrayList(list.size());
            for (zzkb zzkbVar : list) {
                if (z || !zzkc.zzch(zzkbVar.name)) {
                    arrayList.add(new zzjz(zzkbVar));
                }
            }
            return arrayList;
        } catch (InterruptedException | ExecutionException e) {
            this.zzajy.zzgf().zzis().zze("Failed to get user attributes. appId", zzfh.zzbl(zzdzVar.packageName), e);
            return null;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzez
    public final List<zzee> zza(String str, String str2, zzdz zzdzVar) {
        zzb(zzdzVar, false);
        try {
            return (List) this.zzajy.zzge().zzb(new zzgw(this, zzdzVar, str, str2)).get();
        } catch (InterruptedException | ExecutionException e) {
            this.zzajy.zzgf().zzis().zzg("Failed to get conditional user properties", e);
            return Collections.emptyList();
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzez
    public final List<zzjz> zza(String str, String str2, String str3, boolean z) {
        zzc(str, true);
        try {
            List<zzkb> list = (List) this.zzajy.zzge().zzb(new zzgv(this, str, str2, str3)).get();
            ArrayList arrayList = new ArrayList(list.size());
            for (zzkb zzkbVar : list) {
                if (z || !zzkc.zzch(zzkbVar.name)) {
                    arrayList.add(new zzjz(zzkbVar));
                }
            }
            return arrayList;
        } catch (InterruptedException | ExecutionException e) {
            this.zzajy.zzgf().zzis().zze("Failed to get user attributes. appId", zzfh.zzbl(str), e);
            return Collections.emptyList();
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzez
    public final List<zzjz> zza(String str, String str2, boolean z, zzdz zzdzVar) {
        zzb(zzdzVar, false);
        try {
            List<zzkb> list = (List) this.zzajy.zzge().zzb(new zzgu(this, zzdzVar, str, str2)).get();
            ArrayList arrayList = new ArrayList(list.size());
            for (zzkb zzkbVar : list) {
                if (z || !zzkc.zzch(zzkbVar.name)) {
                    arrayList.add(new zzjz(zzkbVar));
                }
            }
            return arrayList;
        } catch (InterruptedException | ExecutionException e) {
            this.zzajy.zzgf().zzis().zze("Failed to get user attributes. appId", zzfh.zzbl(zzdzVar.packageName), e);
            return Collections.emptyList();
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzez
    public final void zza(long j, String str, String str2, String str3) {
        zze(new zzhg(this, str2, str3, str, j));
    }

    @Override // com.google.android.gms.internal.measurement.zzez
    public final void zza(zzdz zzdzVar) {
        zzb(zzdzVar, false);
        zze(new zzhf(this, zzdzVar));
    }

    @Override // com.google.android.gms.internal.measurement.zzez
    public final void zza(zzee zzeeVar, zzdz zzdzVar) {
        Preconditions.checkNotNull(zzeeVar);
        Preconditions.checkNotNull(zzeeVar.zzaeq);
        zzb(zzdzVar, false);
        zzee zzeeVar2 = new zzee(zzeeVar);
        zzeeVar2.packageName = zzdzVar.packageName;
        zze(zzeeVar.zzaeq.getValue() == null ? new zzgq(this, zzeeVar2, zzdzVar) : new zzgr(this, zzeeVar2, zzdzVar));
    }

    @Override // com.google.android.gms.internal.measurement.zzez
    public final void zza(zzew zzewVar, zzdz zzdzVar) {
        Preconditions.checkNotNull(zzewVar);
        zzb(zzdzVar, false);
        zze(new zzgz(this, zzewVar, zzdzVar));
    }

    @Override // com.google.android.gms.internal.measurement.zzez
    public final void zza(zzew zzewVar, String str, String str2) {
        Preconditions.checkNotNull(zzewVar);
        Preconditions.checkNotEmpty(str);
        zzc(str, true);
        zze(new zzha(this, zzewVar, str));
    }

    @Override // com.google.android.gms.internal.measurement.zzez
    public final void zza(zzjz zzjzVar, zzdz zzdzVar) {
        Preconditions.checkNotNull(zzjzVar);
        zzb(zzdzVar, false);
        zze(zzjzVar.getValue() == null ? new zzhc(this, zzjzVar, zzdzVar) : new zzhd(this, zzjzVar, zzdzVar));
    }

    @Override // com.google.android.gms.internal.measurement.zzez
    public final byte[] zza(zzew zzewVar, String str) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzewVar);
        zzc(str, true);
        this.zzajy.zzgf().zziy().zzg("Log and bundle. event", this.zzajy.zzgb().zzbi(zzewVar.name));
        long nanoTime = this.zzajy.zzbt().nanoTime() / 1000000;
        try {
            byte[] bArr = (byte[]) this.zzajy.zzge().zzc(new zzhb(this, zzewVar, str)).get();
            if (bArr == null) {
                this.zzajy.zzgf().zzis().zzg("Log and bundle returned null. appId", zzfh.zzbl(str));
                bArr = new byte[0];
            }
            this.zzajy.zzgf().zziy().zzd("Log and bundle processed. event, size, time_ms", this.zzajy.zzgb().zzbi(zzewVar.name), Integer.valueOf(bArr.length), Long.valueOf((this.zzajy.zzbt().nanoTime() / 1000000) - nanoTime));
            return bArr;
        } catch (InterruptedException | ExecutionException e) {
            this.zzajy.zzgf().zzis().zzd("Failed to log and bundle. appId, event, error", zzfh.zzbl(str), this.zzajy.zzgb().zzbi(zzewVar.name), e);
            return null;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzez
    public final void zzb(zzdz zzdzVar) {
        zzb(zzdzVar, false);
        zze(new zzgp(this, zzdzVar));
    }

    @Override // com.google.android.gms.internal.measurement.zzez
    public final void zzb(zzee zzeeVar) {
        Preconditions.checkNotNull(zzeeVar);
        Preconditions.checkNotNull(zzeeVar.zzaeq);
        zzc(zzeeVar.packageName, true);
        zzee zzeeVar2 = new zzee(zzeeVar);
        zze(zzeeVar.zzaeq.getValue() == null ? new zzgs(this, zzeeVar2) : new zzgt(this, zzeeVar2));
    }

    @Override // com.google.android.gms.internal.measurement.zzez
    public final String zzc(zzdz zzdzVar) {
        zzb(zzdzVar, false);
        return this.zzajy.zzh(zzdzVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzez
    public final void zzd(zzdz zzdzVar) {
        zzc(zzdzVar.packageName, false);
        zze(new zzgy(this, zzdzVar));
    }

    @Override // com.google.android.gms.internal.measurement.zzez
    public final List<zzee> zze(String str, String str2, String str3) {
        zzc(str, true);
        try {
            return (List) this.zzajy.zzge().zzb(new zzgx(this, str, str2, str3)).get();
        } catch (InterruptedException | ExecutionException e) {
            this.zzajy.zzgf().zzis().zzg("Failed to get conditional user properties", e);
            return Collections.emptyList();
        }
    }
}
