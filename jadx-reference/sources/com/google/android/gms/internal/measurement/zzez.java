package com.google.android.gms.internal.measurement;

import android.os.IInterface;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes.dex */
public interface zzez extends IInterface {
    List<zzjz> zza(zzdz zzdzVar, boolean z) throws RemoteException;

    List<zzee> zza(String str, String str2, zzdz zzdzVar) throws RemoteException;

    List<zzjz> zza(String str, String str2, String str3, boolean z) throws RemoteException;

    List<zzjz> zza(String str, String str2, boolean z, zzdz zzdzVar) throws RemoteException;

    void zza(long j, String str, String str2, String str3) throws RemoteException;

    void zza(zzdz zzdzVar) throws RemoteException;

    void zza(zzee zzeeVar, zzdz zzdzVar) throws RemoteException;

    void zza(zzew zzewVar, zzdz zzdzVar) throws RemoteException;

    void zza(zzew zzewVar, String str, String str2) throws RemoteException;

    void zza(zzjz zzjzVar, zzdz zzdzVar) throws RemoteException;

    byte[] zza(zzew zzewVar, String str) throws RemoteException;

    void zzb(zzdz zzdzVar) throws RemoteException;

    void zzb(zzee zzeeVar) throws RemoteException;

    String zzc(zzdz zzdzVar) throws RemoteException;

    void zzd(zzdz zzdzVar) throws RemoteException;

    List<zzee> zze(String str, String str2, String str3) throws RemoteException;
}
