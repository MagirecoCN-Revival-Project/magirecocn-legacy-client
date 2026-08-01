package com.google.android.gms.dynamite;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import cn.thinkingdata.core.router.TRouterMap;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.CrashUtils;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.firebase.analytics.FirebaseAnalytics;
import dalvik.system.DelegateLastClassLoader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/* compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
/* loaded from: classes.dex */
public final class DynamiteModule {
    private static Boolean zzb = null;
    private static String zzc = null;
    private static boolean zzd = false;
    private static int zze = -1;
    private static zzq zzj;
    private static zzr zzk;
    private final Context zzi;
    private static final ThreadLocal<zzn> zzf = new ThreadLocal<>();
    private static final ThreadLocal<Long> zzg = new zzd();
    private static final VersionPolicy.IVersions zzh = new zze();
    public static final VersionPolicy PREFER_REMOTE = new zzf();
    public static final VersionPolicy PREFER_LOCAL = new zzg();
    public static final VersionPolicy PREFER_REMOTE_VERSION_NO_FORCE_STAGING = new zzh();
    public static final VersionPolicy PREFER_HIGHEST_OR_LOCAL_VERSION = new zzi();
    public static final VersionPolicy PREFER_HIGHEST_OR_LOCAL_VERSION_NO_FORCE_STAGING = new zzj();
    public static final VersionPolicy PREFER_HIGHEST_OR_REMOTE_VERSION = new zzk();
    public static final VersionPolicy zza = new zzl();

    /* compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
    /* loaded from: classes.dex */
    public static class DynamiteLoaderClassLoader {
        public static ClassLoader sClassLoader;
    }

    /* compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
    /* loaded from: classes.dex */
    public static class LoadingException extends Exception {
        /* synthetic */ LoadingException(String str, zzp zzpVar) {
            super(str);
        }

        /* synthetic */ LoadingException(String str, Throwable th, zzp zzpVar) {
            super(str, th);
        }
    }

    /* compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
    /* loaded from: classes.dex */
    public interface VersionPolicy {

        /* compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
        /* loaded from: classes.dex */
        public interface IVersions {
            int zza(Context context, String str);

            int zzb(Context context, String str, boolean z) throws LoadingException;
        }

        /* compiled from: com.google.android.gms:play-services-basement@@18.0.0 */
        /* loaded from: classes.dex */
        public static class SelectionResult {
            public int localVersion = 0;
            public int remoteVersion = 0;
            public int selection = 0;
        }

        SelectionResult selectModule(Context context, String str, IVersions iVersions) throws LoadingException;
    }

    private DynamiteModule(Context context) {
        Preconditions.checkNotNull(context);
        this.zzi = context;
    }

    public static int getLocalVersion(Context context, String str) {
        try {
            ClassLoader classLoader = context.getApplicationContext().getClassLoader();
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 61);
            sb.append("com.google.android.gms.dynamite.descriptors.");
            sb.append(str);
            sb.append(TRouterMap.DOT);
            sb.append("ModuleDescriptor");
            Class<?> loadClass = classLoader.loadClass(sb.toString());
            Field declaredField = loadClass.getDeclaredField("MODULE_ID");
            Field declaredField2 = loadClass.getDeclaredField("MODULE_VERSION");
            if (!Objects.equal(declaredField.get(null), str)) {
                String valueOf = String.valueOf(declaredField.get(null));
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 51 + String.valueOf(str).length());
                sb2.append("Module descriptor id '");
                sb2.append(valueOf);
                sb2.append("' didn't match expected id '");
                sb2.append(str);
                sb2.append("'");
                Log.e("DynamiteModule", sb2.toString());
                return 0;
            }
            return declaredField2.getInt(null);
        } catch (ClassNotFoundException unused) {
            StringBuilder sb3 = new StringBuilder(String.valueOf(str).length() + 45);
            sb3.append("Local module descriptor class for ");
            sb3.append(str);
            sb3.append(" not found.");
            Log.w("DynamiteModule", sb3.toString());
            return 0;
        } catch (Exception e) {
            String valueOf2 = String.valueOf(e.getMessage());
            Log.e("DynamiteModule", valueOf2.length() != 0 ? "Failed to load module descriptor class: ".concat(valueOf2) : new String("Failed to load module descriptor class: "));
            return 0;
        }
    }

    public static int getRemoteVersion(Context context, String str) {
        return zza(context, str, false);
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[CONST, CMP_L]}, finally: {[CONST, CMP_L, SGET, INVOKE, INVOKE, SGET, INVOKE, IF, IGET, SGET, INVOKE, IGET, SGET, INVOKE, INVOKE, IF] complete} */
    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [60=8, 61=5, 62=4, 63=5] */
    public static DynamiteModule load(Context context, VersionPolicy versionPolicy, String str) throws LoadingException {
        Boolean bool;
        IObjectWrapper zzh2;
        DynamiteModule dynamiteModule;
        zzr zzrVar;
        Boolean valueOf;
        IObjectWrapper zze2;
        ThreadLocal<zzn> threadLocal = zzf;
        zzn zznVar = threadLocal.get();
        zzn zznVar2 = new zzn(null);
        threadLocal.set(zznVar2);
        ThreadLocal<Long> threadLocal2 = zzg;
        long longValue = threadLocal2.get().longValue();
        try {
            threadLocal2.set(Long.valueOf(SystemClock.elapsedRealtime()));
            VersionPolicy.SelectionResult selectModule = versionPolicy.selectModule(context, str, zzh);
            int i = selectModule.localVersion;
            int i2 = selectModule.remoteVersion;
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 68 + String.valueOf(str).length());
            sb.append("Considering local module ");
            sb.append(str);
            sb.append(":");
            sb.append(i);
            sb.append(" and remote module ");
            sb.append(str);
            sb.append(":");
            sb.append(i2);
            Log.i("DynamiteModule", sb.toString());
            int i3 = selectModule.selection;
            if (i3 != 0) {
                if (i3 == -1) {
                    if (selectModule.localVersion != 0) {
                        i3 = -1;
                    }
                }
                if (i3 != 1 || selectModule.remoteVersion != 0) {
                    if (i3 == -1) {
                        DynamiteModule zzc2 = zzc(context, str);
                        if (longValue == 0) {
                            threadLocal2.remove();
                        } else {
                            threadLocal2.set(Long.valueOf(longValue));
                        }
                        Cursor cursor = zznVar2.zza;
                        if (cursor != null) {
                            cursor.close();
                        }
                        threadLocal.set(zznVar);
                        return zzc2;
                    }
                    if (i3 != 1) {
                        StringBuilder sb2 = new StringBuilder(47);
                        sb2.append("VersionPolicy returned invalid code:");
                        sb2.append(i3);
                        throw new LoadingException(sb2.toString(), null);
                    }
                    try {
                        int i4 = selectModule.remoteVersion;
                        try {
                            synchronized (DynamiteModule.class) {
                                bool = zzb;
                            }
                            if (bool == null) {
                                throw new LoadingException("Failed to determine which loading route to use.", null);
                            }
                            if (bool.booleanValue()) {
                                StringBuilder sb3 = new StringBuilder(String.valueOf(str).length() + 51);
                                sb3.append("Selected remote version of ");
                                sb3.append(str);
                                sb3.append(", version >= ");
                                sb3.append(i4);
                                Log.i("DynamiteModule", sb3.toString());
                                synchronized (DynamiteModule.class) {
                                    zzrVar = zzk;
                                }
                                if (zzrVar == null) {
                                    throw new LoadingException("DynamiteLoaderV2 was not cached.", null);
                                }
                                zzn zznVar3 = threadLocal.get();
                                if (zznVar3 == null || zznVar3.zza == null) {
                                    throw new LoadingException("No result cursor", null);
                                }
                                Context applicationContext = context.getApplicationContext();
                                Cursor cursor2 = zznVar3.zza;
                                ObjectWrapper.wrap(null);
                                synchronized (DynamiteModule.class) {
                                    valueOf = Boolean.valueOf(zze >= 2);
                                }
                                if (valueOf.booleanValue()) {
                                    Log.v("DynamiteModule", "Dynamite loader version >= 2, using loadModule2NoCrashUtils");
                                    zze2 = zzrVar.zzf(ObjectWrapper.wrap(applicationContext), str, i4, ObjectWrapper.wrap(cursor2));
                                } else {
                                    Log.w("DynamiteModule", "Dynamite loader version < 2, falling back to loadModule2");
                                    zze2 = zzrVar.zze(ObjectWrapper.wrap(applicationContext), str, i4, ObjectWrapper.wrap(cursor2));
                                }
                                Context context2 = (Context) ObjectWrapper.unwrap(zze2);
                                if (context2 == null) {
                                    throw new LoadingException("Failed to get module context", null);
                                }
                                dynamiteModule = new DynamiteModule(context2);
                            } else {
                                StringBuilder sb4 = new StringBuilder(String.valueOf(str).length() + 51);
                                sb4.append("Selected remote version of ");
                                sb4.append(str);
                                sb4.append(", version >= ");
                                sb4.append(i4);
                                Log.i("DynamiteModule", sb4.toString());
                                zzq zzf2 = zzf(context);
                                if (zzf2 == null) {
                                    throw new LoadingException("Failed to create IDynamiteLoader.", null);
                                }
                                int zze3 = zzf2.zze();
                                if (zze3 >= 3) {
                                    zzn zznVar4 = threadLocal.get();
                                    if (zznVar4 == null) {
                                        throw new LoadingException("No cached result cursor holder", null);
                                    }
                                    zzh2 = zzf2.zzi(ObjectWrapper.wrap(context), str, i4, ObjectWrapper.wrap(zznVar4.zza));
                                } else if (zze3 == 2) {
                                    Log.w("DynamiteModule", "IDynamite loader version = 2");
                                    zzh2 = zzf2.zzj(ObjectWrapper.wrap(context), str, i4);
                                } else {
                                    Log.w("DynamiteModule", "Dynamite loader version < 2, falling back to createModuleContext");
                                    zzh2 = zzf2.zzh(ObjectWrapper.wrap(context), str, i4);
                                }
                                if (ObjectWrapper.unwrap(zzh2) == null) {
                                    throw new LoadingException("Failed to load remote module.", null);
                                }
                                dynamiteModule = new DynamiteModule((Context) ObjectWrapper.unwrap(zzh2));
                            }
                            if (longValue == 0) {
                                threadLocal2.remove();
                            } else {
                                threadLocal2.set(Long.valueOf(longValue));
                            }
                            Cursor cursor3 = zznVar2.zza;
                            if (cursor3 != null) {
                                cursor3.close();
                            }
                            threadLocal.set(zznVar);
                            return dynamiteModule;
                        } catch (RemoteException e) {
                            throw new LoadingException("Failed to load remote module.", e, null);
                        } catch (LoadingException e2) {
                            throw e2;
                        } catch (Throwable th) {
                            CrashUtils.addDynamiteErrorToDropBox(context, th);
                            throw new LoadingException("Failed to load remote module.", th, null);
                        }
                    } catch (LoadingException e3) {
                        String valueOf2 = String.valueOf(e3.getMessage());
                        Log.w("DynamiteModule", valueOf2.length() != 0 ? "Failed to load remote module: ".concat(valueOf2) : new String("Failed to load remote module: "));
                        int i5 = selectModule.localVersion;
                        if (i5 == 0 || versionPolicy.selectModule(context, str, new zzo(i5, 0)).selection != -1) {
                            throw new LoadingException("Remote load failed. No local fallback found.", e3, null);
                        }
                        DynamiteModule zzc3 = zzc(context, str);
                        if (longValue == 0) {
                            zzg.remove();
                        } else {
                            zzg.set(Long.valueOf(longValue));
                        }
                        Cursor cursor4 = zznVar2.zza;
                        if (cursor4 != null) {
                            cursor4.close();
                        }
                        zzf.set(zznVar);
                        return zzc3;
                    }
                }
            }
            int i6 = selectModule.localVersion;
            int i7 = selectModule.remoteVersion;
            StringBuilder sb5 = new StringBuilder(String.valueOf(str).length() + 92);
            sb5.append("No acceptable module ");
            sb5.append(str);
            sb5.append(" found. Local version is ");
            sb5.append(i6);
            sb5.append(" and remote version is ");
            sb5.append(i7);
            sb5.append(TRouterMap.DOT);
            throw new LoadingException(sb5.toString(), null);
        } catch (Throwable th2) {
            if (longValue == 0) {
                zzg.remove();
            } else {
                zzg.set(Long.valueOf(longValue));
            }
            Cursor cursor5 = zznVar2.zza;
            if (cursor5 != null) {
                cursor5.close();
            }
            zzf.set(zznVar);
            throw th2;
        }
    }

    public static int zza(Context context, String str, boolean z) {
        Field declaredField;
        Throwable th;
        RemoteException e;
        Cursor cursor;
        try {
            synchronized (DynamiteModule.class) {
                Boolean bool = zzb;
                if (bool == null) {
                    try {
                        declaredField = context.getApplicationContext().getClassLoader().loadClass(DynamiteLoaderClassLoader.class.getName()).getDeclaredField("sClassLoader");
                    } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e2) {
                        String obj = e2.toString();
                        StringBuilder sb = new StringBuilder(obj.length() + 30);
                        sb.append("Failed to load module via V2: ");
                        sb.append(obj);
                        Log.w("DynamiteModule", sb.toString());
                        bool = Boolean.FALSE;
                    }
                    synchronized (declaredField.getDeclaringClass()) {
                        ClassLoader classLoader = (ClassLoader) declaredField.get(null);
                        if (classLoader != null) {
                            if (classLoader == ClassLoader.getSystemClassLoader()) {
                                bool = Boolean.FALSE;
                            } else {
                                try {
                                    zzd(classLoader);
                                } catch (LoadingException unused) {
                                }
                                bool = Boolean.TRUE;
                            }
                        } else if (zzd || Boolean.TRUE.equals(null)) {
                            declaredField.set(null, ClassLoader.getSystemClassLoader());
                            bool = Boolean.FALSE;
                        } else {
                            try {
                                int zzb2 = zzb(context, str, z);
                                String str2 = zzc;
                                if (str2 != null && !str2.isEmpty()) {
                                    ClassLoader zza2 = zzb.zza();
                                    if (zza2 == null) {
                                        if (Build.VERSION.SDK_INT >= 29) {
                                            String str3 = zzc;
                                            Preconditions.checkNotNull(str3);
                                            zza2 = new DelegateLastClassLoader(str3, ClassLoader.getSystemClassLoader());
                                        } else {
                                            String str4 = zzc;
                                            Preconditions.checkNotNull(str4);
                                            zza2 = new zzc(str4, ClassLoader.getSystemClassLoader());
                                        }
                                    }
                                    zzd(zza2);
                                    declaredField.set(null, zza2);
                                    zzb = Boolean.TRUE;
                                    return zzb2;
                                }
                                return zzb2;
                            } catch (LoadingException unused2) {
                                declaredField.set(null, ClassLoader.getSystemClassLoader());
                                bool = Boolean.FALSE;
                            }
                        }
                        zzb = bool;
                    }
                }
                boolean booleanValue = bool.booleanValue();
                int i = 0;
                if (!booleanValue) {
                    zzq zzf2 = zzf(context);
                    try {
                        if (zzf2 != null) {
                            try {
                                int zze2 = zzf2.zze();
                                if (zze2 >= 3) {
                                    zzn zznVar = zzf.get();
                                    if (zznVar == null || (cursor = zznVar.zza) == null) {
                                        Cursor cursor2 = (Cursor) ObjectWrapper.unwrap(zzf2.zzk(ObjectWrapper.wrap(context), str, z, zzg.get().longValue()));
                                        if (cursor2 != null) {
                                            try {
                                                if (cursor2.moveToFirst()) {
                                                    int i2 = cursor2.getInt(0);
                                                    r2 = (i2 <= 0 || !zze(cursor2)) ? cursor2 : null;
                                                    if (r2 != null) {
                                                        r2.close();
                                                    }
                                                    i = i2;
                                                }
                                            } catch (RemoteException e3) {
                                                e = e3;
                                                r2 = cursor2;
                                                String valueOf = String.valueOf(e.getMessage());
                                                Log.w("DynamiteModule", valueOf.length() != 0 ? "Failed to retrieve remote module version: ".concat(valueOf) : new String("Failed to retrieve remote module version: "));
                                                if (r2 != null) {
                                                    r2.close();
                                                }
                                                return i;
                                            } catch (Throwable th2) {
                                                th = th2;
                                                r2 = cursor2;
                                                if (r2 != null) {
                                                    r2.close();
                                                }
                                                throw th;
                                            }
                                        }
                                        Log.w("DynamiteModule", "Failed to retrieve remote module version.");
                                        if (cursor2 != null) {
                                            cursor2.close();
                                        }
                                    } else {
                                        i = cursor.getInt(0);
                                    }
                                } else if (zze2 == 2) {
                                    Log.w("DynamiteModule", "IDynamite loader version = 2, no high precision latency measurement.");
                                    i = zzf2.zzg(ObjectWrapper.wrap(context), str, z);
                                } else {
                                    Log.w("DynamiteModule", "IDynamite loader version < 2, falling back to getModuleVersion2");
                                    i = zzf2.zzf(ObjectWrapper.wrap(context), str, z);
                                }
                            } catch (RemoteException e4) {
                                e = e4;
                            }
                        }
                        return i;
                    } catch (Throwable th3) {
                        th = th3;
                    }
                } else {
                    try {
                        return zzb(context, str, z);
                    } catch (LoadingException e5) {
                        String valueOf2 = String.valueOf(e5.getMessage());
                        Log.w("DynamiteModule", valueOf2.length() != 0 ? "Failed to retrieve remote module version: ".concat(valueOf2) : new String("Failed to retrieve remote module version: "));
                        return 0;
                    }
                }
            }
        } catch (Throwable th4) {
            CrashUtils.addDynamiteErrorToDropBox(context, th4);
            throw th4;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00c6  */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static int zzb(Context context, String str, boolean z) throws LoadingException {
        Throwable th;
        Exception e;
        ?? r0 = 0;
        ?? r02 = 0;
        ?? r03 = 0;
        ?? r04 = 0;
        try {
            try {
                boolean z2 = true;
                Cursor query = context.getContentResolver().query(new Uri.Builder().scheme(FirebaseAnalytics.Param.CONTENT).authority("com.google.android.gms.chimera").path(true != z ? "api" : "api_force_staging").appendPath(str).appendQueryParameter("requestStartTime", String.valueOf(zzg.get().longValue())).build(), null, null, null, null);
                if (query != null) {
                    try {
                        if (query.moveToFirst()) {
                            boolean z3 = false;
                            int i = query.getInt(0);
                            if (i > 0) {
                                synchronized (DynamiteModule.class) {
                                    zzc = query.getString(2);
                                    int columnIndex = query.getColumnIndex("loaderVersion");
                                    if (columnIndex >= 0) {
                                        zze = query.getInt(columnIndex);
                                    }
                                    int columnIndex2 = query.getColumnIndex("disableStandaloneDynamiteLoader");
                                    if (columnIndex2 >= 0) {
                                        if (query.getInt(columnIndex2) == 0) {
                                            z2 = false;
                                        }
                                        zzd = z2;
                                        z3 = z2;
                                    }
                                }
                                if (zze(query)) {
                                    query = null;
                                }
                            }
                            if (z3) {
                                throw new LoadingException("forcing fallback to container DynamiteLoader impl", r03 == true ? 1 : 0);
                            }
                            if (query != null) {
                                query.close();
                            }
                            return i;
                        }
                    } catch (Exception e2) {
                        e = e2;
                        if (e instanceof LoadingException) {
                            throw e;
                        }
                        throw new LoadingException("V2 version check failed", e, r02 == true ? 1 : 0);
                    }
                }
                Log.w("DynamiteModule", "Failed to retrieve remote module version.");
                throw new LoadingException("Failed to connect to dynamite module ContentResolver.", r04 == true ? 1 : 0);
            } catch (Throwable th2) {
                th = th2;
                r0 = context;
                if (r0 != 0) {
                    r0.close();
                }
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
        } catch (Throwable th3) {
            th = th3;
            if (r0 != 0) {
            }
            throw th;
        }
    }

    private static DynamiteModule zzc(Context context, String str) {
        String valueOf = String.valueOf(str);
        Log.i("DynamiteModule", valueOf.length() != 0 ? "Selected local version of ".concat(valueOf) : new String("Selected local version of "));
        return new DynamiteModule(context.getApplicationContext());
    }

    private static void zzd(ClassLoader classLoader) throws LoadingException {
        zzr zzrVar;
        zzp zzpVar = null;
        try {
            IBinder iBinder = (IBinder) classLoader.loadClass("com.google.android.gms.dynamiteloader.DynamiteLoaderV2").getConstructor(new Class[0]).newInstance(new Object[0]);
            if (iBinder == null) {
                zzrVar = null;
            } else {
                IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoaderV2");
                if (queryLocalInterface instanceof zzr) {
                    zzrVar = (zzr) queryLocalInterface;
                } else {
                    zzrVar = new zzr(iBinder);
                }
            }
            zzk = zzrVar;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new LoadingException("Failed to instantiate dynamite loader", e, zzpVar);
        }
    }

    private static boolean zze(Cursor cursor) {
        zzn zznVar = zzf.get();
        if (zznVar == null || zznVar.zza != null) {
            return false;
        }
        zznVar.zza = cursor;
        return true;
    }

    private static zzq zzf(Context context) {
        zzq zzqVar;
        synchronized (DynamiteModule.class) {
            zzq zzqVar2 = zzj;
            if (zzqVar2 != null) {
                return zzqVar2;
            }
            try {
                IBinder iBinder = (IBinder) context.createPackageContext("com.google.android.gms", 3).getClassLoader().loadClass("com.google.android.gms.chimera.container.DynamiteLoaderImpl").newInstance();
                if (iBinder == null) {
                    zzqVar = null;
                } else {
                    IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoader");
                    zzqVar = queryLocalInterface instanceof zzq ? (zzq) queryLocalInterface : new zzq(iBinder);
                }
                if (zzqVar != null) {
                    zzj = zzqVar;
                    return zzqVar;
                }
            } catch (Exception e) {
                String valueOf = String.valueOf(e.getMessage());
                Log.e("DynamiteModule", valueOf.length() != 0 ? "Failed to load IDynamiteLoader from GmsCore: ".concat(valueOf) : new String("Failed to load IDynamiteLoader from GmsCore: "));
            }
            return null;
        }
    }

    public Context getModuleContext() {
        return this.zzi;
    }

    public IBinder instantiate(String str) throws LoadingException {
        try {
            return (IBinder) this.zzi.getClassLoader().loadClass(str).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            String valueOf = String.valueOf(str);
            throw new LoadingException(valueOf.length() != 0 ? "Failed to instantiate module class: ".concat(valueOf) : new String("Failed to instantiate module class: "), e, null);
        }
    }
}
