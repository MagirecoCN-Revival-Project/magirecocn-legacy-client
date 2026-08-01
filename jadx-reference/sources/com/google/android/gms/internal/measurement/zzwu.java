package com.google.android.gms.internal.measurement;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.util.Log;
import androidx.core.content.PermissionChecker;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public abstract class zzwu<T> {
    private static final Object zzbno = new Object();
    private static boolean zzbnp;
    private static volatile Boolean zzbnq;
    private static Context zzqx;
    private final zzxe zzbnr;
    final String zzbns;
    private final String zzbnt;
    private final T zzbnu;
    private T zzbnv;
    private volatile zzwr zzbnw;
    private volatile SharedPreferences zzbnx;

    private zzwu(zzxe zzxeVar, String str, T t) {
        Uri uri;
        String str2;
        String str3;
        this.zzbnv = null;
        this.zzbnw = null;
        this.zzbnx = null;
        uri = zzxeVar.zzbod;
        if (uri == null) {
            throw new IllegalArgumentException("Must pass a valid SharedPreferences file name or ContentProvider URI");
        }
        this.zzbnr = zzxeVar;
        str2 = zzxeVar.zzboe;
        String valueOf = String.valueOf(str2);
        String valueOf2 = String.valueOf(str);
        this.zzbnt = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
        str3 = zzxeVar.zzbof;
        String valueOf3 = String.valueOf(str3);
        String valueOf4 = String.valueOf(str);
        this.zzbns = valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3);
        this.zzbnu = t;
    }

    public /* synthetic */ zzwu(zzxe zzxeVar, String str, Object obj, zzwy zzwyVar) {
        this(zzxeVar, str, obj);
    }

    public static void init(Context context) {
        Context applicationContext;
        synchronized (zzbno) {
            if ((Build.VERSION.SDK_INT < 24 || !context.isDeviceProtectedStorage()) && (applicationContext = context.getApplicationContext()) != null) {
                context = applicationContext;
            }
            if (zzqx != context) {
                zzbnq = null;
            }
            zzqx = context;
        }
        zzbnp = false;
    }

    public static zzwu<Double> zza(zzxe zzxeVar, String str, double d) {
        return new zzxb(zzxeVar, str, Double.valueOf(d));
    }

    public static zzwu<Integer> zza(zzxe zzxeVar, String str, int i) {
        return new zzwz(zzxeVar, str, Integer.valueOf(i));
    }

    public static zzwu<Long> zza(zzxe zzxeVar, String str, long j) {
        return new zzwy(zzxeVar, str, Long.valueOf(j));
    }

    public static zzwu<String> zza(zzxe zzxeVar, String str, String str2) {
        return new zzxc(zzxeVar, str, str2);
    }

    public static zzwu<Boolean> zza(zzxe zzxeVar, String str, boolean z) {
        return new zzxa(zzxeVar, str, Boolean.valueOf(z));
    }

    private static <V> V zza(zzxd<V> zzxdVar) {
        try {
            return zzxdVar.zzsl();
        } catch (SecurityException unused) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return zzxdVar.zzsl();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public static boolean zzd(String str, boolean z) {
        boolean z2 = false;
        try {
            if (zzsj()) {
                return ((Boolean) zza(new zzxd(str, z2) { // from class: com.google.android.gms.internal.measurement.zzwx
                    private final String zzboa;
                    private final boolean zzbob = false;

                    /* JADX INFO: Access modifiers changed from: package-private */
                    {
                        this.zzboa = str;
                    }

                    @Override // com.google.android.gms.internal.measurement.zzxd
                    public final Object zzsl() {
                        Boolean valueOf;
                        valueOf = Boolean.valueOf(zzwp.zza(zzwu.zzqx.getContentResolver(), this.zzboa, this.zzbob));
                        return valueOf;
                    }
                })).booleanValue();
            }
            return false;
        } catch (SecurityException e) {
            Log.e("PhenotypeFlag", "Unable to read GServices, returning default value.", e);
            return false;
        }
    }

    @Nullable
    private final T zzsh() {
        Uri uri;
        Uri uri2;
        if (zzd("gms:phenotype:phenotype_flag:debug_bypass_phenotype", false)) {
            String valueOf = String.valueOf(this.zzbns);
            Log.w("PhenotypeFlag", valueOf.length() != 0 ? "Bypass reading Phenotype values for flag: ".concat(valueOf) : new String("Bypass reading Phenotype values for flag: "));
            return null;
        }
        uri = this.zzbnr.zzbod;
        if (uri == null) {
            return null;
        }
        if (this.zzbnw == null) {
            ContentResolver contentResolver = zzqx.getContentResolver();
            uri2 = this.zzbnr.zzbod;
            this.zzbnw = zzwr.zza(contentResolver, uri2);
        }
        String str = (String) zza(new zzxd(this, this.zzbnw) { // from class: com.google.android.gms.internal.measurement.zzwv
            private final zzwu zzbny;
            private final zzwr zzbnz;

            /* JADX INFO: Access modifiers changed from: package-private */
            {
                this.zzbny = this;
                this.zzbnz = r2;
            }

            @Override // com.google.android.gms.internal.measurement.zzxd
            public final Object zzsl() {
                return this.zzbnz.zzsc().get(this.zzbny.zzbns);
            }
        });
        if (str != null) {
            return zzex(str);
        }
        return null;
    }

    @Nullable
    private final T zzsi() {
        if (!zzsj()) {
            return null;
        }
        try {
            String str = (String) zza(new zzxd(this) { // from class: com.google.android.gms.internal.measurement.zzww
                private final zzwu zzbny;

                /* JADX INFO: Access modifiers changed from: package-private */
                {
                    this.zzbny = this;
                }

                @Override // com.google.android.gms.internal.measurement.zzxd
                public final Object zzsl() {
                    return this.zzbny.zzsk();
                }
            });
            if (str != null) {
                return zzex(str);
            }
            return null;
        } catch (SecurityException e) {
            String valueOf = String.valueOf(this.zzbns);
            Log.e("PhenotypeFlag", valueOf.length() != 0 ? "Unable to read GServices for flag: ".concat(valueOf) : new String("Unable to read GServices for flag: "), e);
            return null;
        }
    }

    private static boolean zzsj() {
        if (zzbnq == null) {
            Context context = zzqx;
            if (context == null) {
                return false;
            }
            zzbnq = Boolean.valueOf(PermissionChecker.checkCallingOrSelfPermission(context, "com.google.android.providers.gsf.permission.READ_GSERVICES") == 0);
        }
        return zzbnq.booleanValue();
    }

    public final T get() {
        if (zzqx == null) {
            throw new IllegalStateException("Must call PhenotypeFlag.init() first");
        }
        T zzsh = zzsh();
        if (zzsh != null) {
            return zzsh;
        }
        T zzsi = zzsi();
        return zzsi != null ? zzsi : this.zzbnu;
    }

    protected abstract T zzex(String str);

    public final /* synthetic */ String zzsk() {
        return zzwp.zza(zzqx.getContentResolver(), this.zzbnt, (String) null);
    }
}
