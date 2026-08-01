package com.google.firebase.components;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class zzc {
    private final Context zza;
    private final zzb zzb;

    /* loaded from: classes.dex */
    interface zzb {
        List<String> zza(Context context);
    }

    public zzc(Context context) {
        this(context, new zza((byte) 0));
    }

    private zzc(Context context, zzb zzbVar) {
        this.zza = context;
        this.zzb = zzbVar;
    }

    public final List<ComponentRegistrar> zza() {
        return zza(this.zzb.zza(this.zza));
    }

    private static List<ComponentRegistrar> zza(List<String> list) {
        ArrayList arrayList = new ArrayList();
        for (String str : list) {
            try {
                Class<?> cls = Class.forName(str);
                if (!ComponentRegistrar.class.isAssignableFrom(cls)) {
                    Log.w("ComponentDiscovery", String.format("Class %s is not an instance of %s", str, "com.google.firebase.components.ComponentRegistrar"));
                } else {
                    arrayList.add((ComponentRegistrar) cls.newInstance());
                }
            } catch (ClassNotFoundException e) {
                Log.w("ComponentDiscovery", String.format("Class %s is not an found.", str), e);
            } catch (IllegalAccessException e2) {
                Log.w("ComponentDiscovery", String.format("Could not instantiate %s.", str), e2);
            } catch (InstantiationException e3) {
                Log.w("ComponentDiscovery", String.format("Could not instantiate %s.", str), e3);
            }
        }
        return arrayList;
    }

    /* loaded from: classes.dex */
    static class zza implements zzb {
        private zza() {
        }

        /* synthetic */ zza(byte b) {
            this();
        }

        @Override // com.google.firebase.components.zzc.zzb
        public final List<String> zza(Context context) {
            Bundle zzb = zzb(context);
            if (zzb == null) {
                Log.w("ComponentDiscovery", "Could not retrieve metadata, returning empty list of registrars.");
                return Collections.emptyList();
            }
            ArrayList arrayList = new ArrayList();
            for (String str : zzb.keySet()) {
                if ("com.google.firebase.components.ComponentRegistrar".equals(zzb.get(str)) && str.startsWith("com.google.firebase.components:")) {
                    arrayList.add(str.substring(31));
                }
            }
            return arrayList;
        }

        private static Bundle zzb(Context context) {
            try {
                PackageManager packageManager = context.getPackageManager();
                if (packageManager == null) {
                    Log.w("ComponentDiscovery", "Context has no PackageManager.");
                    return null;
                }
                ServiceInfo serviceInfo = packageManager.getServiceInfo(new ComponentName(context, (Class<?>) ComponentDiscoveryService.class), 128);
                if (serviceInfo == null) {
                    Log.w("ComponentDiscovery", "ComponentDiscoveryService has no service info.");
                    return null;
                }
                return serviceInfo.metaData;
            } catch (PackageManager.NameNotFoundException unused) {
                Log.w("ComponentDiscovery", "Application info not found.");
                return null;
            }
        }
    }
}
