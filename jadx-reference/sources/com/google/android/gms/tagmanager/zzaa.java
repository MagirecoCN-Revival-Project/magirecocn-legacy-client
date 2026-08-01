package com.google.android.gms.tagmanager;

import android.os.Looper;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tagmanager.ContainerHolder;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzaa implements ContainerHolder {
    private final Looper zza;
    private Container zzb;
    private Container zzc;
    private final Status zzd;
    private zzz zze;
    private zzy zzf;
    private boolean zzg;
    private TagManager zzh;

    public zzaa(Status status) {
        this.zzd = status;
        this.zza = null;
    }

    public zzaa(TagManager tagManager, Looper looper, Container container, zzy zzyVar) {
        this.zzh = tagManager;
        this.zza = looper == null ? Looper.getMainLooper() : looper;
        this.zzb = container;
        this.zzf = zzyVar;
        this.zzd = Status.RESULT_SUCCESS;
        tagManager.zza(this);
    }

    private final void zzf() {
        zzz zzzVar = this.zze;
        if (zzzVar != null) {
            zzzVar.sendMessage(zzzVar.obtainMessage(1, this.zzc.zzc()));
        }
    }

    @Override // com.google.android.gms.tagmanager.ContainerHolder
    public final synchronized Container getContainer() {
        if (this.zzg) {
            zzdh.zza("ContainerHolder is released.");
            return null;
        }
        Container container = this.zzc;
        if (container != null) {
            this.zzb = container;
            this.zzc = null;
        }
        return this.zzb;
    }

    @Override // com.google.android.gms.common.api.Result
    public final Status getStatus() {
        return this.zzd;
    }

    @Override // com.google.android.gms.tagmanager.ContainerHolder
    public final synchronized void refresh() {
        if (this.zzg) {
            zzdh.zza("Refreshing a released ContainerHolder.");
        } else {
            this.zzf.zzb();
        }
    }

    @Override // com.google.android.gms.common.api.Releasable
    public final synchronized void release() {
        if (this.zzg) {
            zzdh.zza("Releasing a released ContainerHolder.");
            return;
        }
        this.zzg = true;
        this.zzh.zzc(this);
        this.zzb.zze();
        this.zzb = null;
        this.zzc = null;
        this.zzf = null;
        this.zze = null;
    }

    @Override // com.google.android.gms.tagmanager.ContainerHolder
    public final synchronized void setContainerAvailableListener(ContainerHolder.ContainerAvailableListener containerAvailableListener) {
        if (this.zzg) {
            zzdh.zza("ContainerHolder is released.");
            return;
        }
        if (containerAvailableListener == null) {
            this.zze = null;
            return;
        }
        this.zze = new zzz(this, containerAvailableListener, this.zza);
        if (this.zzc != null) {
            zzf();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final String zza() {
        if (this.zzg) {
            zzdh.zza("getContainerId called on a released ContainerHolder.");
            return "";
        }
        return this.zzb.getContainerId();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final String zzb() {
        if (this.zzg) {
            zzdh.zza("setCtfeUrlPathAndQuery called on a released ContainerHolder.");
            return "";
        }
        return this.zzf.zza();
    }

    public final synchronized void zzc(Container container) {
        if (this.zzg) {
            return;
        }
        this.zzc = container;
        zzf();
    }

    public final synchronized void zzd(String str) {
        if (this.zzg) {
            return;
        }
        this.zzb.zzd(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zze(String str) {
        if (this.zzg) {
            zzdh.zza("setCtfeUrlPathAndQuery called on a released ContainerHolder.");
        } else {
            this.zzf.zzc(str);
        }
    }
}
