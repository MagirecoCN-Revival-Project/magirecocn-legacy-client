package com.google.android.gms.internal.measurement;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

/* loaded from: classes.dex */
final class zzabo {
    private static final Logger logger = Logger.getLogger(zzabo.class.getName());
    private static final Class<?> zzbrf;
    private static final Unsafe zzbtt;
    private static final boolean zzbux;
    private static final boolean zzbuy;
    private static final zzd zzbuz;
    private static final boolean zzbva;
    private static final boolean zzbvb;
    private static final long zzbvc;
    private static final long zzbvd;
    private static final long zzbve;
    private static final long zzbvf;
    private static final long zzbvg;
    private static final long zzbvh;
    private static final long zzbvi;
    private static final long zzbvj;
    private static final long zzbvk;
    private static final long zzbvl;
    private static final long zzbvm;
    private static final long zzbvn;
    private static final long zzbvo;
    private static final long zzbvp;
    private static final long zzbvq;
    private static final boolean zzbvr;

    /* loaded from: classes.dex */
    static final class zza extends zzd {
        zza(Unsafe unsafe) {
            super(unsafe);
        }
    }

    /* loaded from: classes.dex */
    static final class zzb extends zzd {
        zzb(Unsafe unsafe) {
            super(unsafe);
        }
    }

    /* loaded from: classes.dex */
    static final class zzc extends zzd {
        zzc(Unsafe unsafe) {
            super(unsafe);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static abstract class zzd {
        Unsafe zzbvs;

        zzd(Unsafe unsafe) {
            this.zzbvs = unsafe;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x00ee  */
    static {
        zzd zzcVar;
        Field zza2;
        Unsafe zzva = zzva();
        zzbtt = zzva;
        zzbrf = zzyx.zztf();
        boolean zzk = zzk(Long.TYPE);
        zzbux = zzk;
        boolean zzk2 = zzk(Integer.TYPE);
        zzbuy = zzk2;
        Field field = null;
        if (zzva != null) {
            if (!zzyx.zzte()) {
                zzcVar = new zzc(zzva);
            } else if (zzk) {
                zzcVar = new zzb(zzva);
            } else if (zzk2) {
                zzcVar = new zza(zzva);
            }
            zzbuz = zzcVar;
            zzbva = zzvc();
            zzbvb = zzvb();
            zzbvc = zzi(byte[].class);
            zzbvd = zzi(boolean[].class);
            zzbve = zzj(boolean[].class);
            zzbvf = zzi(int[].class);
            zzbvg = zzj(int[].class);
            zzbvh = zzi(long[].class);
            zzbvi = zzj(long[].class);
            zzbvj = zzi(float[].class);
            zzbvk = zzj(float[].class);
            zzbvl = zzi(double[].class);
            zzbvm = zzj(double[].class);
            zzbvn = zzi(Object[].class);
            zzbvo = zzj(Object[].class);
            zzbvp = zza(zzvd());
            zza2 = zza(String.class, FirebaseAnalytics.Param.VALUE);
            if (zza2 != null && zza2.getType() == char[].class) {
                field = zza2;
            }
            zzbvq = zza(field);
            zzbvr = ByteOrder.nativeOrder() != ByteOrder.BIG_ENDIAN;
        }
        zzcVar = null;
        zzbuz = zzcVar;
        zzbva = zzvc();
        zzbvb = zzvb();
        zzbvc = zzi(byte[].class);
        zzbvd = zzi(boolean[].class);
        zzbve = zzj(boolean[].class);
        zzbvf = zzi(int[].class);
        zzbvg = zzj(int[].class);
        zzbvh = zzi(long[].class);
        zzbvi = zzj(long[].class);
        zzbvj = zzi(float[].class);
        zzbvk = zzj(float[].class);
        zzbvl = zzi(double[].class);
        zzbvm = zzj(double[].class);
        zzbvn = zzi(Object[].class);
        zzbvo = zzj(Object[].class);
        zzbvp = zza(zzvd());
        zza2 = zza(String.class, FirebaseAnalytics.Param.VALUE);
        if (zza2 != null) {
            field = zza2;
        }
        zzbvq = zza(field);
        zzbvr = ByteOrder.nativeOrder() != ByteOrder.BIG_ENDIAN;
    }

    private zzabo() {
    }

    private static long zza(Field field) {
        zzd zzdVar;
        if (field == null || (zzdVar = zzbuz) == null) {
            return -1L;
        }
        return zzdVar.zzbvs.objectFieldOffset(field);
    }

    private static Field zza(Class<?> cls, String str) {
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            return declaredField;
        } catch (Throwable unused) {
            return null;
        }
    }

    private static int zzi(Class<?> cls) {
        if (zzbvb) {
            return zzbuz.zzbvs.arrayBaseOffset(cls);
        }
        return -1;
    }

    private static int zzj(Class<?> cls) {
        if (zzbvb) {
            return zzbuz.zzbvs.arrayIndexScale(cls);
        }
        return -1;
    }

    private static boolean zzk(Class<?> cls) {
        if (!zzyx.zzte()) {
            return false;
        }
        try {
            Class<?> cls2 = zzbrf;
            cls2.getMethod("peekLong", cls, Boolean.TYPE);
            cls2.getMethod("pokeLong", cls, Long.TYPE, Boolean.TYPE);
            cls2.getMethod("pokeInt", cls, Integer.TYPE, Boolean.TYPE);
            cls2.getMethod("peekInt", cls, Boolean.TYPE);
            cls2.getMethod("pokeByte", cls, Byte.TYPE);
            cls2.getMethod("peekByte", cls);
            cls2.getMethod("pokeByteArray", cls, byte[].class, Integer.TYPE, Integer.TYPE);
            cls2.getMethod("peekByteArray", cls, byte[].class, Integer.TYPE, Integer.TYPE);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Unsafe zzva() {
        try {
            return (Unsafe) AccessController.doPrivileged(new zzabp());
        } catch (Throwable unused) {
            return null;
        }
    }

    private static boolean zzvb() {
        Unsafe unsafe = zzbtt;
        if (unsafe == null) {
            return false;
        }
        try {
            Class<?> cls = unsafe.getClass();
            cls.getMethod("objectFieldOffset", Field.class);
            cls.getMethod("arrayBaseOffset", Class.class);
            cls.getMethod("arrayIndexScale", Class.class);
            cls.getMethod("getInt", Object.class, Long.TYPE);
            cls.getMethod("putInt", Object.class, Long.TYPE, Integer.TYPE);
            cls.getMethod("getLong", Object.class, Long.TYPE);
            cls.getMethod("putLong", Object.class, Long.TYPE, Long.TYPE);
            cls.getMethod("getObject", Object.class, Long.TYPE);
            cls.getMethod("putObject", Object.class, Long.TYPE, Object.class);
            if (zzyx.zzte()) {
                return true;
            }
            cls.getMethod("getByte", Object.class, Long.TYPE);
            cls.getMethod("putByte", Object.class, Long.TYPE, Byte.TYPE);
            cls.getMethod("getBoolean", Object.class, Long.TYPE);
            cls.getMethod("putBoolean", Object.class, Long.TYPE, Boolean.TYPE);
            cls.getMethod("getFloat", Object.class, Long.TYPE);
            cls.getMethod("putFloat", Object.class, Long.TYPE, Float.TYPE);
            cls.getMethod("getDouble", Object.class, Long.TYPE);
            cls.getMethod("putDouble", Object.class, Long.TYPE, Double.TYPE);
            return true;
        } catch (Throwable th) {
            Logger logger2 = logger;
            Level level = Level.WARNING;
            String valueOf = String.valueOf(th);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 71);
            sb.append("platform method missing - proto runtime falling back to safer methods: ");
            sb.append(valueOf);
            logger2.logp(level, "com.google.protobuf.UnsafeUtil", "supportsUnsafeArrayOperations", sb.toString());
            return false;
        }
    }

    private static boolean zzvc() {
        Unsafe unsafe = zzbtt;
        if (unsafe == null) {
            return false;
        }
        try {
            Class<?> cls = unsafe.getClass();
            cls.getMethod("objectFieldOffset", Field.class);
            cls.getMethod("getLong", Object.class, Long.TYPE);
            if (zzvd() == null) {
                return false;
            }
            if (zzyx.zzte()) {
                return true;
            }
            cls.getMethod("getByte", Long.TYPE);
            cls.getMethod("putByte", Long.TYPE, Byte.TYPE);
            cls.getMethod("getInt", Long.TYPE);
            cls.getMethod("putInt", Long.TYPE, Integer.TYPE);
            cls.getMethod("getLong", Long.TYPE);
            cls.getMethod("putLong", Long.TYPE, Long.TYPE);
            cls.getMethod("copyMemory", Long.TYPE, Long.TYPE, Long.TYPE);
            cls.getMethod("copyMemory", Object.class, Long.TYPE, Object.class, Long.TYPE, Long.TYPE);
            return true;
        } catch (Throwable th) {
            Logger logger2 = logger;
            Level level = Level.WARNING;
            String valueOf = String.valueOf(th);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 71);
            sb.append("platform method missing - proto runtime falling back to safer methods: ");
            sb.append(valueOf);
            logger2.logp(level, "com.google.protobuf.UnsafeUtil", "supportsUnsafeByteBufferOperations", sb.toString());
            return false;
        }
    }

    private static Field zzvd() {
        Field zza2;
        if (zzyx.zzte() && (zza2 = zza(Buffer.class, "effectiveDirectAddress")) != null) {
            return zza2;
        }
        Field zza3 = zza(Buffer.class, "address");
        if (zza3 == null || zza3.getType() != Long.TYPE) {
            return null;
        }
        return zza3;
    }
}
