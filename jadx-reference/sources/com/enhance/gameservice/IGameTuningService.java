package com.enhance.gameservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IGameTuningService extends IInterface {

    /* loaded from: classes.dex */
    public static class Default implements IGameTuningService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.enhance.gameservice.IGameTuningService
        public int boostUp(int i) throws RemoteException {
            return 0;
        }

        @Override // com.enhance.gameservice.IGameTuningService
        public int getAbstractTemperature() throws RemoteException {
            return 0;
        }

        @Override // com.enhance.gameservice.IGameTuningService
        public int setFramePerSecond(int i) throws RemoteException {
            return 0;
        }

        @Override // com.enhance.gameservice.IGameTuningService
        public int setGamePowerSaving(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.enhance.gameservice.IGameTuningService
        public int setPreferredResolution(int i) throws RemoteException {
            return 0;
        }
    }

    int boostUp(int i) throws RemoteException;

    int getAbstractTemperature() throws RemoteException;

    int setFramePerSecond(int i) throws RemoteException;

    int setGamePowerSaving(boolean z) throws RemoteException;

    int setPreferredResolution(int i) throws RemoteException;

    /* loaded from: classes.dex */
    public static abstract class Stub extends Binder implements IGameTuningService {
        private static final String DESCRIPTOR = "com.enhance.gameservice.IGameTuningService";
        static final int TRANSACTION_boostUp = 3;
        static final int TRANSACTION_getAbstractTemperature = 4;
        static final int TRANSACTION_setFramePerSecond = 2;
        static final int TRANSACTION_setGamePowerSaving = 5;
        static final int TRANSACTION_setPreferredResolution = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IGameTuningService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IGameTuningService)) {
                return (IGameTuningService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                int preferredResolution = setPreferredResolution(parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(preferredResolution);
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                int framePerSecond = setFramePerSecond(parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(framePerSecond);
                return true;
            }
            if (i == 3) {
                parcel.enforceInterface(DESCRIPTOR);
                int boostUp = boostUp(parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(boostUp);
                return true;
            }
            if (i == 4) {
                parcel.enforceInterface(DESCRIPTOR);
                int abstractTemperature = getAbstractTemperature();
                parcel2.writeNoException();
                parcel2.writeInt(abstractTemperature);
                return true;
            }
            if (i != 5) {
                if (i == 1598968902) {
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel.enforceInterface(DESCRIPTOR);
            int gamePowerSaving = setGamePowerSaving(parcel.readInt() != 0);
            parcel2.writeNoException();
            parcel2.writeInt(gamePowerSaving);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes.dex */
        public static class Proxy implements IGameTuningService {
            public static IGameTuningService sDefaultImpl;
            private IBinder mRemote;

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.enhance.gameservice.IGameTuningService
            public int setPreferredResolution(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setPreferredResolution(i);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.enhance.gameservice.IGameTuningService
            public int setFramePerSecond(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setFramePerSecond(i);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.enhance.gameservice.IGameTuningService
            public int boostUp(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().boostUp(i);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.enhance.gameservice.IGameTuningService
            public int getAbstractTemperature() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAbstractTemperature();
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.enhance.gameservice.IGameTuningService
            public int setGamePowerSaving(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(z ? 1 : 0);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setGamePowerSaving(z);
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IGameTuningService iGameTuningService) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iGameTuningService == null) {
                return false;
            }
            Proxy.sDefaultImpl = iGameTuningService;
            return true;
        }

        public static IGameTuningService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
