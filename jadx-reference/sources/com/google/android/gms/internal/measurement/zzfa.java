package com.google.android.gms.internal.measurement;

import android.os.Parcel;
import android.os.RemoteException;
import java.util.Collection;

/* loaded from: classes.dex */
public abstract class zzfa extends zzo implements zzez {
    public zzfa() {
        super("com.google.android.gms.measurement.internal.IMeasurementService");
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:1:0x0000. Please report as an issue. */
    @Override // com.google.android.gms.internal.measurement.zzo
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        Collection zza;
        switch (i) {
            case 1:
                zza((zzew) zzp.zza(parcel, zzew.CREATOR), (zzdz) zzp.zza(parcel, zzdz.CREATOR));
                parcel2.writeNoException();
                return true;
            case 2:
                zza((zzjz) zzp.zza(parcel, zzjz.CREATOR), (zzdz) zzp.zza(parcel, zzdz.CREATOR));
                parcel2.writeNoException();
                return true;
            case 3:
            case 8:
            default:
                return false;
            case 4:
                zza((zzdz) zzp.zza(parcel, zzdz.CREATOR));
                parcel2.writeNoException();
                return true;
            case 5:
                zza((zzew) zzp.zza(parcel, zzew.CREATOR), parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 6:
                zzb((zzdz) zzp.zza(parcel, zzdz.CREATOR));
                parcel2.writeNoException();
                return true;
            case 7:
                zza = zza((zzdz) zzp.zza(parcel, zzdz.CREATOR), zzp.zza(parcel));
                parcel2.writeNoException();
                parcel2.writeTypedList(zza);
                return true;
            case 9:
                byte[] zza2 = zza((zzew) zzp.zza(parcel, zzew.CREATOR), parcel.readString());
                parcel2.writeNoException();
                parcel2.writeByteArray(zza2);
                return true;
            case 10:
                zza(parcel.readLong(), parcel.readString(), parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 11:
                String zzc = zzc((zzdz) zzp.zza(parcel, zzdz.CREATOR));
                parcel2.writeNoException();
                parcel2.writeString(zzc);
                return true;
            case 12:
                zza((zzee) zzp.zza(parcel, zzee.CREATOR), (zzdz) zzp.zza(parcel, zzdz.CREATOR));
                parcel2.writeNoException();
                return true;
            case 13:
                zzb((zzee) zzp.zza(parcel, zzee.CREATOR));
                parcel2.writeNoException();
                return true;
            case 14:
                zza = zza(parcel.readString(), parcel.readString(), zzp.zza(parcel), (zzdz) zzp.zza(parcel, zzdz.CREATOR));
                parcel2.writeNoException();
                parcel2.writeTypedList(zza);
                return true;
            case 15:
                zza = zza(parcel.readString(), parcel.readString(), parcel.readString(), zzp.zza(parcel));
                parcel2.writeNoException();
                parcel2.writeTypedList(zza);
                return true;
            case 16:
                zza = zza(parcel.readString(), parcel.readString(), (zzdz) zzp.zza(parcel, zzdz.CREATOR));
                parcel2.writeNoException();
                parcel2.writeTypedList(zza);
                return true;
            case 17:
                zza = zze(parcel.readString(), parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                parcel2.writeTypedList(zza);
                return true;
            case 18:
                zzd((zzdz) zzp.zza(parcel, zzdz.CREATOR));
                parcel2.writeNoException();
                return true;
        }
    }
}
