package com.google.firebase;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import androidx.collection.ArrayMap;
import androidx.core.content.ContextCompat;
import com.google.android.gms.common.api.internal.BackgroundDetector;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.common.util.ProcessUtils;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.components.Component;
import com.google.firebase.components.zzc;
import com.google.firebase.components.zzd;
import com.google.firebase.events.Event;
import com.google.firebase.events.Publisher;
import com.google.firebase.internal.InternalTokenProvider;
import com.google.firebase.internal.InternalTokenResult;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public class FirebaseApp {
    public static final String DEFAULT_APP_NAME = "[DEFAULT]";
    private final Context zzi;
    private final String zzj;
    private final FirebaseOptions zzk;
    private final zzd zzl;
    private final SharedPreferences zzm;
    private final Publisher zzn;
    private InternalTokenProvider zzu;
    private static final List<String> zzb = Arrays.asList("com.google.firebase.auth.FirebaseAuth", "com.google.firebase.iid.FirebaseInstanceId");
    private static final List<String> zzc = Collections.singletonList("com.google.firebase.crash.FirebaseCrash");
    private static final List<String> zzd = Arrays.asList("com.google.android.gms.measurement.AppMeasurement");
    private static final List<String> zze = Arrays.asList(new String[0]);
    private static final Set<String> zzf = Collections.emptySet();
    private static final Object zzg = new Object();
    private static final Executor zzh = new zza(0);
    static final Map<String, FirebaseApp> zza = new ArrayMap();
    private final AtomicBoolean zzo = new AtomicBoolean(false);
    private final AtomicBoolean zzp = new AtomicBoolean();
    private final List<IdTokenListener> zzr = new CopyOnWriteArrayList();
    private final List<BackgroundStateChangeListener> zzs = new CopyOnWriteArrayList();
    private final List<com.google.firebase.zza> zzt = new CopyOnWriteArrayList();
    private IdTokenListenersCountChangedListener zzv = new com.google.firebase.internal.zza();
    private final AtomicBoolean zzq = new AtomicBoolean(zzb());

    /* loaded from: classes.dex */
    public interface BackgroundStateChangeListener {
        void onBackgroundStateChanged(boolean z);
    }

    /* loaded from: classes.dex */
    public interface IdTokenListener {
        void onIdTokenChanged(InternalTokenResult internalTokenResult);
    }

    /* loaded from: classes.dex */
    public interface IdTokenListenersCountChangedListener {
        void onListenerCountChanged(int i);
    }

    public Context getApplicationContext() {
        zzc();
        return this.zzi;
    }

    public String getName() {
        zzc();
        return this.zzj;
    }

    public FirebaseOptions getOptions() {
        zzc();
        return this.zzk;
    }

    public boolean equals(Object obj) {
        if (obj instanceof FirebaseApp) {
            return this.zzj.equals(((FirebaseApp) obj).getName());
        }
        return false;
    }

    public int hashCode() {
        return this.zzj.hashCode();
    }

    public String toString() {
        return Objects.toStringHelper(this).add("name", this.zzj).add("options", this.zzk).toString();
    }

    public static List<FirebaseApp> getApps(Context context) {
        ArrayList arrayList;
        synchronized (zzg) {
            arrayList = new ArrayList(zza.values());
        }
        return arrayList;
    }

    public static FirebaseApp getInstance() {
        FirebaseApp firebaseApp;
        synchronized (zzg) {
            firebaseApp = zza.get(DEFAULT_APP_NAME);
            if (firebaseApp == null) {
                throw new IllegalStateException("Default FirebaseApp is not initialized in this process " + ProcessUtils.getMyProcessName() + ". Make sure to call FirebaseApp.initializeApp(Context) first.");
            }
        }
        return firebaseApp;
    }

    public static FirebaseApp getInstance(String str) {
        FirebaseApp firebaseApp;
        String str2;
        synchronized (zzg) {
            firebaseApp = zza.get(str.trim());
            if (firebaseApp == null) {
                List<String> zzd2 = zzd();
                if (zzd2.isEmpty()) {
                    str2 = "";
                } else {
                    str2 = "Available app names: " + TextUtils.join(", ", zzd2);
                }
                throw new IllegalStateException(String.format("FirebaseApp with name %s doesn't exist. %s", str, str2));
            }
        }
        return firebaseApp;
    }

    public static FirebaseApp initializeApp(Context context) {
        synchronized (zzg) {
            if (zza.containsKey(DEFAULT_APP_NAME)) {
                return getInstance();
            }
            FirebaseOptions fromResource = FirebaseOptions.fromResource(context);
            if (fromResource == null) {
                return null;
            }
            return initializeApp(context, fromResource);
        }
    }

    public static FirebaseApp initializeApp(Context context, FirebaseOptions firebaseOptions) {
        return initializeApp(context, firebaseOptions, DEFAULT_APP_NAME);
    }

    public static void onBackgroundStateChanged(boolean z) {
        synchronized (zzg) {
            Iterator it = new ArrayList(zza.values()).iterator();
            while (it.hasNext()) {
                FirebaseApp firebaseApp = (FirebaseApp) it.next();
                if (firebaseApp.zzo.get()) {
                    firebaseApp.zza(z);
                }
            }
        }
    }

    public void setTokenProvider(InternalTokenProvider internalTokenProvider) {
        this.zzu = (InternalTokenProvider) Preconditions.checkNotNull(internalTokenProvider);
    }

    public void setIdTokenListenersCountChangedListener(IdTokenListenersCountChangedListener idTokenListenersCountChangedListener) {
        IdTokenListenersCountChangedListener idTokenListenersCountChangedListener2 = (IdTokenListenersCountChangedListener) Preconditions.checkNotNull(idTokenListenersCountChangedListener);
        this.zzv = idTokenListenersCountChangedListener2;
        idTokenListenersCountChangedListener2.onListenerCountChanged(this.zzr.size());
    }

    public Task<GetTokenResult> getToken(boolean z) {
        zzc();
        InternalTokenProvider internalTokenProvider = this.zzu;
        if (internalTokenProvider == null) {
            return Tasks.forException(new FirebaseApiNotAvailableException("firebase-auth is not linked, please fall back to unauthenticated mode."));
        }
        return internalTokenProvider.getAccessToken(z);
    }

    public String getUid() throws FirebaseApiNotAvailableException {
        zzc();
        InternalTokenProvider internalTokenProvider = this.zzu;
        if (internalTokenProvider == null) {
            throw new FirebaseApiNotAvailableException("firebase-auth is not linked, please fall back to unauthenticated mode.");
        }
        return internalTokenProvider.getUid();
    }

    public void delete() {
        if (this.zzp.compareAndSet(false, true)) {
            synchronized (zzg) {
                zza.remove(this.zzj);
            }
            Iterator<com.google.firebase.zza> it = this.zzt.iterator();
            while (it.hasNext()) {
                it.next();
            }
        }
    }

    public <T> T get(Class<T> cls) {
        zzc();
        return (T) this.zzl.get(cls);
    }

    public void setAutomaticResourceManagementEnabled(boolean z) {
        zzc();
        if (this.zzo.compareAndSet(!z, z)) {
            boolean isInBackground = BackgroundDetector.getInstance().isInBackground();
            if (z && isInBackground) {
                zza(true);
            } else {
                if (z || !isInBackground) {
                    return;
                }
                zza(false);
            }
        }
    }

    public boolean isAutomaticDataCollectionEnabled() {
        zzc();
        return this.zzq.get();
    }

    public void setAutomaticDataCollectionEnabled(boolean z) {
        zzc();
        if (this.zzq.compareAndSet(!z, z)) {
            this.zzm.edit().putBoolean("firebase_automatic_data_collection_enabled", z).commit();
            this.zzn.publish(new Event<>(AutomaticDataCollectionChange.class, new AutomaticDataCollectionChange(z)));
        }
    }

    protected FirebaseApp(Context context, String str, FirebaseOptions firebaseOptions) {
        this.zzi = (Context) Preconditions.checkNotNull(context);
        this.zzj = Preconditions.checkNotEmpty(str);
        this.zzk = (FirebaseOptions) Preconditions.checkNotNull(firebaseOptions);
        this.zzm = context.getSharedPreferences("com.google.firebase.common.prefs", 0);
        zzd zzdVar = new zzd(zzh, new zzc(context).zza(), Component.of(context, Context.class, new Class[0]), Component.of(this, FirebaseApp.class, new Class[0]), Component.of(firebaseOptions, FirebaseOptions.class, new Class[0]));
        this.zzl = zzdVar;
        this.zzn = (Publisher) zzdVar.get(Publisher.class);
    }

    private boolean zzb() {
        ApplicationInfo applicationInfo;
        if (this.zzm.contains("firebase_automatic_data_collection_enabled")) {
            return this.zzm.getBoolean("firebase_automatic_data_collection_enabled", true);
        }
        try {
            PackageManager packageManager = this.zzi.getPackageManager();
            if (packageManager != null && (applicationInfo = packageManager.getApplicationInfo(this.zzi.getPackageName(), 128)) != null && applicationInfo.metaData != null && applicationInfo.metaData.containsKey("firebase_automatic_data_collection_enabled")) {
                return applicationInfo.metaData.getBoolean("firebase_automatic_data_collection_enabled");
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return true;
    }

    private void zzc() {
        Preconditions.checkState(!this.zzp.get(), "FirebaseApp was deleted");
    }

    public List<IdTokenListener> getListeners() {
        zzc();
        return this.zzr;
    }

    public boolean isDefaultApp() {
        return DEFAULT_APP_NAME.equals(getName());
    }

    public void notifyIdTokenListeners(InternalTokenResult internalTokenResult) {
        Log.d("FirebaseApp", "Notifying auth state listeners.");
        Iterator<IdTokenListener> it = this.zzr.iterator();
        int i = 0;
        while (it.hasNext()) {
            it.next().onIdTokenChanged(internalTokenResult);
            i++;
        }
        Log.d("FirebaseApp", String.format("Notified %d auth state listeners.", Integer.valueOf(i)));
    }

    private void zza(boolean z) {
        Log.d("FirebaseApp", "Notifying background state change listeners.");
        Iterator<BackgroundStateChangeListener> it = this.zzs.iterator();
        while (it.hasNext()) {
            it.next().onBackgroundStateChanged(z);
        }
    }

    public void addIdTokenListener(IdTokenListener idTokenListener) {
        zzc();
        Preconditions.checkNotNull(idTokenListener);
        this.zzr.add(idTokenListener);
        this.zzv.onListenerCountChanged(this.zzr.size());
    }

    public void removeIdTokenListener(IdTokenListener idTokenListener) {
        zzc();
        Preconditions.checkNotNull(idTokenListener);
        this.zzr.remove(idTokenListener);
        this.zzv.onListenerCountChanged(this.zzr.size());
    }

    public void addBackgroundStateChangeListener(BackgroundStateChangeListener backgroundStateChangeListener) {
        zzc();
        if (this.zzo.get() && BackgroundDetector.getInstance().isInBackground()) {
            backgroundStateChangeListener.onBackgroundStateChanged(true);
        }
        this.zzs.add(backgroundStateChangeListener);
    }

    public void removeBackgroundStateChangeListener(BackgroundStateChangeListener backgroundStateChangeListener) {
        zzc();
        this.zzs.remove(backgroundStateChangeListener);
    }

    public String getPersistenceKey() {
        return Base64Utils.encodeUrlSafeNoPadding(getName().getBytes()) + "+" + Base64Utils.encodeUrlSafeNoPadding(getOptions().getApplicationId().getBytes());
    }

    public void addLifecycleEventListener(com.google.firebase.zza zzaVar) {
        zzc();
        Preconditions.checkNotNull(zzaVar);
        this.zzt.add(zzaVar);
    }

    public void removeLifecycleEventListener(com.google.firebase.zza zzaVar) {
        zzc();
        Preconditions.checkNotNull(zzaVar);
        this.zzt.remove(zzaVar);
    }

    public static void clearInstancesForTest() {
        synchronized (zzg) {
            zza.clear();
        }
    }

    public static String getPersistenceKey(String str, FirebaseOptions firebaseOptions) {
        return Base64Utils.encodeUrlSafeNoPadding(str.getBytes()) + "+" + Base64Utils.encodeUrlSafeNoPadding(firebaseOptions.getApplicationId().getBytes());
    }

    private static List<String> zzd() {
        ArrayList arrayList = new ArrayList();
        synchronized (zzg) {
            Iterator<FirebaseApp> it = zza.values().iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getName());
            }
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void zze() {
        boolean isDeviceProtectedStorage = ContextCompat.isDeviceProtectedStorage(this.zzi);
        if (isDeviceProtectedStorage) {
            zzb.zza(this.zzi);
        } else {
            this.zzl.zza(isDefaultApp());
        }
        zza(FirebaseApp.class, this, zzb, isDeviceProtectedStorage);
        if (isDefaultApp()) {
            zza(FirebaseApp.class, this, zzc, isDeviceProtectedStorage);
            zza(Context.class, this.zzi, zzd, isDeviceProtectedStorage);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: java.lang.Class<?> */
    /* JADX WARN: Multi-variable type inference failed */
    private static <T> void zza(Class<T> cls, T t, Iterable<String> iterable, boolean z) {
        for (String str : iterable) {
            if (z) {
                try {
                } catch (ClassNotFoundException unused) {
                    if (zzf.contains(str)) {
                        throw new IllegalStateException(str + " is missing, but is required. Check if it has been removed by Proguard.");
                    }
                    Log.d("FirebaseApp", str + " is not linked. Skipping initialization.");
                } catch (IllegalAccessException e) {
                    Log.wtf("FirebaseApp", "Failed to initialize " + str, e);
                } catch (NoSuchMethodException unused2) {
                    throw new IllegalStateException(str + "#getInstance has been removed by Proguard. Add keep rule to prevent it.");
                } catch (InvocationTargetException e2) {
                    Log.wtf("FirebaseApp", "Firebase API initialization failure.", e2);
                }
                if (zze.contains(str)) {
                }
            }
            Method method = Class.forName(str).getMethod("getInstance", cls);
            int modifiers = method.getModifiers();
            if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers)) {
                method.invoke(null, t);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class zzb extends BroadcastReceiver {
        private static AtomicReference<zzb> zza = new AtomicReference<>();
        private final Context zzb;

        private zzb(Context context) {
            this.zzb = context;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            synchronized (FirebaseApp.zzg) {
                Iterator<FirebaseApp> it = FirebaseApp.zza.values().iterator();
                while (it.hasNext()) {
                    it.next().zze();
                }
            }
            this.zzb.unregisterReceiver(this);
        }

        static /* synthetic */ void zza(Context context) {
            if (zza.get() == null) {
                zzb zzbVar = new zzb(context);
                if (zza.compareAndSet(null, zzbVar)) {
                    context.registerReceiver(zzbVar, new IntentFilter("android.intent.action.USER_UNLOCKED"));
                }
            }
        }
    }

    /* loaded from: classes.dex */
    static class zza implements Executor {
        private static final Handler zza = new Handler(Looper.getMainLooper());

        private zza() {
        }

        /* synthetic */ zza(byte b) {
            this();
        }

        @Override // java.util.concurrent.Executor
        public final void execute(Runnable runnable) {
            zza.post(runnable);
        }
    }

    public static FirebaseApp initializeApp(Context context, FirebaseOptions firebaseOptions, String str) {
        FirebaseApp firebaseApp;
        if (PlatformVersion.isAtLeastIceCreamSandwich() && (context.getApplicationContext() instanceof Application)) {
            BackgroundDetector.initialize((Application) context.getApplicationContext());
            BackgroundDetector.getInstance().addListener(new BackgroundDetector.BackgroundStateChangeListener() { // from class: com.google.firebase.FirebaseApp.1
                @Override // com.google.android.gms.common.api.internal.BackgroundDetector.BackgroundStateChangeListener
                public final void onBackgroundStateChanged(boolean z) {
                    FirebaseApp.onBackgroundStateChanged(z);
                }
            });
        }
        String trim = str.trim();
        if (context.getApplicationContext() != null) {
            context = context.getApplicationContext();
        }
        synchronized (zzg) {
            Map<String, FirebaseApp> map = zza;
            Preconditions.checkState(!map.containsKey(trim), "FirebaseApp name " + trim + " already exists!");
            Preconditions.checkNotNull(context, "Application context cannot be null.");
            firebaseApp = new FirebaseApp(context, trim, firebaseOptions);
            map.put(trim, firebaseApp);
        }
        firebaseApp.zze();
        return firebaseApp;
    }
}
