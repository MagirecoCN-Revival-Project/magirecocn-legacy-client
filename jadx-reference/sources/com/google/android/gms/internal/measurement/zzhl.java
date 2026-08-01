package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.CollectionUtils;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;
import kotlinx.coroutines.DebugKt;

/* loaded from: classes.dex */
public final class zzhl extends zzhi {
    protected zzie zzanz;
    private AppMeasurement.EventInterceptor zzaoa;
    private final Set<AppMeasurement.OnEventListener> zzaob;
    private boolean zzaoc;
    private final AtomicReference<String> zzaod;
    protected boolean zzaoe;

    /* JADX INFO: Access modifiers changed from: protected */
    public zzhl(zzgm zzgmVar) {
        super(zzgmVar);
        this.zzaob = new CopyOnWriteArraySet();
        this.zzaoe = true;
        this.zzaod = new AtomicReference<>();
    }

    private final void zza(AppMeasurement.ConditionalUserProperty conditionalUserProperty) {
        long currentTimeMillis = zzbt().currentTimeMillis();
        Preconditions.checkNotNull(conditionalUserProperty);
        Preconditions.checkNotEmpty(conditionalUserProperty.mName);
        Preconditions.checkNotEmpty(conditionalUserProperty.mOrigin);
        Preconditions.checkNotNull(conditionalUserProperty.mValue);
        conditionalUserProperty.mCreationTimestamp = currentTimeMillis;
        String str = conditionalUserProperty.mName;
        Object obj = conditionalUserProperty.mValue;
        if (zzgc().zzce(str) != 0) {
            zzgf().zzis().zzg("Invalid conditional user property name", zzgb().zzbk(str));
            return;
        }
        if (zzgc().zzi(str, obj) != 0) {
            zzgf().zzis().zze("Invalid conditional user property value", zzgb().zzbk(str), obj);
            return;
        }
        Object zzj = zzgc().zzj(str, obj);
        if (zzj == null) {
            zzgf().zzis().zze("Unable to normalize conditional user property value", zzgb().zzbk(str), obj);
            return;
        }
        conditionalUserProperty.mValue = zzj;
        long j = conditionalUserProperty.mTriggerTimeout;
        if (!TextUtils.isEmpty(conditionalUserProperty.mTriggerEventName) && (j > 15552000000L || j < 1)) {
            zzgf().zzis().zze("Invalid conditional user property timeout", zzgb().zzbk(str), Long.valueOf(j));
            return;
        }
        long j2 = conditionalUserProperty.mTimeToLive;
        if (j2 > 15552000000L || j2 < 1) {
            zzgf().zzis().zze("Invalid conditional user property time to live", zzgb().zzbk(str), Long.valueOf(j2));
        } else {
            zzge().zzc(new zzhs(this, conditionalUserProperty));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        String[] strArr;
        Bundle bundle2;
        int i;
        long j2;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkNotNull(bundle);
        zzab();
        zzch();
        if (!this.zzacw.isEnabled()) {
            zzgf().zziy().log("Event not sent since app measurement is disabled");
            return;
        }
        if (!this.zzaoc) {
            this.zzaoc = true;
            try {
                try {
                    Class.forName("com.google.android.gms.tagmanager.TagManagerService").getDeclaredMethod("initialize", Context.class).invoke(null, getContext());
                } catch (Exception e) {
                    zzgf().zziv().zzg("Failed to invoke Tag Manager's initialize() method", e);
                }
            } catch (ClassNotFoundException unused) {
                zzgf().zzix().log("Tag Manager is not found and thus will not be used");
            }
        }
        if (z3) {
            zzgi();
            if (!"_iap".equals(str2)) {
                zzkc zzgc = this.zzacw.zzgc();
                int i2 = 2;
                if (zzgc.zzq("event", str2)) {
                    if (!zzgc.zza("event", AppMeasurement.Event.zzacx, str2)) {
                        i2 = 13;
                    } else if (zzgc.zza("event", 40, str2)) {
                        i2 = 0;
                    }
                }
                if (i2 != 0) {
                    zzgf().zziu().zzg("Invalid public event name. Event will not be logged (FE)", zzgb().zzbi(str2));
                    this.zzacw.zzgc();
                    this.zzacw.zzgc().zza(i2, "_ev", zzkc.zza(str2, 40, true), str2 != null ? str2.length() : 0);
                    return;
                }
            }
        }
        zzgi();
        zzif zzkk = zzfz().zzkk();
        if (zzkk != null && !bundle.containsKey("_sc")) {
            zzkk.zzaou = true;
        }
        zzig.zza(zzkk, bundle, z && z3);
        boolean equals = "am".equals(str);
        boolean zzch = zzkc.zzch(str2);
        if (z && this.zzaoa != null && !zzch && !equals) {
            zzgf().zziy().zze("Passing event to registered event handler (FE)", zzgb().zzbi(str2), zzgb().zzb(bundle));
            this.zzaoa.interceptEvent(str, str2, bundle, j);
            return;
        }
        if (this.zzacw.zzkd()) {
            int zzcc = zzgc().zzcc(str2);
            if (zzcc != 0) {
                zzgf().zziu().zzg("Invalid event name. Event will not be logged (FE)", zzgb().zzbi(str2));
                zzgc();
                this.zzacw.zzgc().zza(str3, zzcc, "_ev", zzkc.zza(str2, 40, true), str2 != null ? str2.length() : 0);
                return;
            }
            List<String> listOf = CollectionUtils.listOf((Object[]) new String[]{"_o", "_sn", "_sc", "_si"});
            Bundle zza = zzgc().zza(str2, bundle, listOf, z3, true);
            zzif zzifVar = (zza != null && zza.containsKey("_sc") && zza.containsKey("_si")) ? new zzif(zza.getString("_sn"), zza.getString("_sc"), Long.valueOf(zza.getLong("_si")).longValue()) : null;
            if (zzifVar != null) {
                zzkk = zzifVar;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(zza);
            long nextLong = zzgc().zzll().nextLong();
            String[] strArr2 = (String[]) zza.keySet().toArray(new String[bundle.size()]);
            Arrays.sort(strArr2);
            int length = strArr2.length;
            int i3 = 0;
            int i4 = 0;
            while (i4 < length) {
                String str4 = strArr2[i4];
                Object obj = zza.get(str4);
                zzgc();
                Bundle[] zze = zzkc.zze(obj);
                if (zze != null) {
                    String str5 = "_eid";
                    zza.putInt(str4, zze.length);
                    int i5 = 0;
                    while (i5 < zze.length) {
                        Bundle bundle3 = zze[i5];
                        int i6 = i5;
                        zzig.zza(zzkk, bundle3, true);
                        String str6 = str5;
                        long j3 = nextLong;
                        Bundle zza2 = zzgc().zza("_ep", bundle3, listOf, z3, false);
                        zza2.putString("_en", str2);
                        zza2.putLong(str6, j3);
                        zza2.putString("_gn", str4);
                        zza2.putInt("_ll", zze.length);
                        zza2.putInt("_i", i6);
                        arrayList.add(zza2);
                        zza = zza;
                        strArr2 = strArr2;
                        str5 = str6;
                        nextLong = j3;
                        i3 = i3;
                        i5 = i6 + 1;
                    }
                    j2 = nextLong;
                    strArr = strArr2;
                    bundle2 = zza;
                    i = zze.length + i3;
                } else {
                    strArr = strArr2;
                    bundle2 = zza;
                    i = i3;
                    j2 = nextLong;
                }
                i4++;
                zza = bundle2;
                strArr2 = strArr;
                i3 = i;
                nextLong = j2;
            }
            Bundle bundle4 = zza;
            long j4 = nextLong;
            int i7 = i3;
            if (i7 != 0) {
                bundle4.putLong("_eid", j4);
                bundle4.putInt("_epc", i7);
            }
            int i8 = 0;
            while (i8 < arrayList.size()) {
                Bundle bundle5 = (Bundle) arrayList.get(i8);
                String str7 = i8 != 0 ? "_ep" : str2;
                bundle5.putString("_o", str);
                if (z2) {
                    bundle5 = zzgc().zzd(bundle5);
                }
                Bundle bundle6 = bundle5;
                zzgf().zziy().zze("Logging event (FE)", zzgb().zzbi(str2), zzgb().zzb(bundle6));
                zzfy().zzb(new zzew(str7, new zzet(bundle6), str, j), str3);
                if (!equals) {
                    Iterator<AppMeasurement.OnEventListener> it = this.zzaob.iterator();
                    while (it.hasNext()) {
                        it.next().onEvent(str, str2, new Bundle(bundle6), j);
                    }
                }
                i8++;
            }
            zzgi();
            if (zzfz().zzkk() == null || !AppMeasurement.Event.APP_EXCEPTION.equals(str2)) {
                return;
            }
            zzgd().zzl(true);
        }
    }

    private final void zza(String str, String str2, long j, Object obj) {
        zzge().zzc(new zzhn(this, str, str2, obj, j));
    }

    private final void zza(String str, String str2, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        zzb(str, str2, zzbt().currentTimeMillis(), bundle, z, z2, z3, str3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(String str, String str2, Object obj, long j) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotEmpty(str2);
        zzab();
        zzfs();
        zzch();
        if (!this.zzacw.isEnabled()) {
            zzgf().zziy().log("User property not set since app measurement is disabled");
        } else if (this.zzacw.zzkd()) {
            zzgf().zziy().zze("Setting user property (FE)", zzgb().zzbi(str2), obj);
            zzfy().zzb(new zzjz(str2, j, obj, str));
        }
    }

    private final void zza(String str, String str2, String str3, Bundle bundle) {
        long currentTimeMillis = zzbt().currentTimeMillis();
        Preconditions.checkNotEmpty(str2);
        AppMeasurement.ConditionalUserProperty conditionalUserProperty = new AppMeasurement.ConditionalUserProperty();
        conditionalUserProperty.mAppId = str;
        conditionalUserProperty.mName = str2;
        conditionalUserProperty.mCreationTimestamp = currentTimeMillis;
        if (str3 != null) {
            conditionalUserProperty.mExpiredEventName = str3;
            conditionalUserProperty.mExpiredEventParams = bundle;
        }
        zzge().zzc(new zzht(this, conditionalUserProperty));
    }

    private final Map<String, Object> zzb(String str, String str2, String str3, boolean z) {
        zzfj zziv;
        String str4;
        if (zzge().zzjr()) {
            zziv = zzgf().zzis();
            str4 = "Cannot get user properties from analytics worker thread";
        } else if (zzec.isMainThread()) {
            zziv = zzgf().zzis();
            str4 = "Cannot get user properties from main thread";
        } else {
            AtomicReference atomicReference = new AtomicReference();
            synchronized (atomicReference) {
                this.zzacw.zzge().zzc(new zzhv(this, atomicReference, str, str2, str3, z));
                try {
                    atomicReference.wait(5000L);
                } catch (InterruptedException e) {
                    zzgf().zziv().zzg("Interrupted waiting for get user properties", e);
                }
            }
            List<zzjz> list = (List) atomicReference.get();
            if (list != null) {
                ArrayMap arrayMap = new ArrayMap(list.size());
                for (zzjz zzjzVar : list) {
                    arrayMap.put(zzjzVar.name, zzjzVar.getValue());
                }
                return arrayMap;
            }
            zziv = zzgf().zziv();
            str4 = "Timed out waiting for get user properties";
        }
        zziv.log(str4);
        return Collections.emptyMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzb(AppMeasurement.ConditionalUserProperty conditionalUserProperty) {
        zzab();
        zzch();
        Preconditions.checkNotNull(conditionalUserProperty);
        Preconditions.checkNotEmpty(conditionalUserProperty.mName);
        Preconditions.checkNotEmpty(conditionalUserProperty.mOrigin);
        Preconditions.checkNotNull(conditionalUserProperty.mValue);
        if (!this.zzacw.isEnabled()) {
            zzgf().zziy().log("Conditional property not sent since Firebase Analytics is disabled");
            return;
        }
        zzjz zzjzVar = new zzjz(conditionalUserProperty.mName, conditionalUserProperty.mTriggeredTimestamp, conditionalUserProperty.mValue, conditionalUserProperty.mOrigin);
        try {
            zzew zza = zzgc().zza(conditionalUserProperty.mTriggeredEventName, conditionalUserProperty.mTriggeredEventParams, conditionalUserProperty.mOrigin, 0L, true, false);
            zzfy().zzd(new zzee(conditionalUserProperty.mAppId, conditionalUserProperty.mOrigin, zzjzVar, conditionalUserProperty.mCreationTimestamp, false, conditionalUserProperty.mTriggerEventName, zzgc().zza(conditionalUserProperty.mTimedOutEventName, conditionalUserProperty.mTimedOutEventParams, conditionalUserProperty.mOrigin, 0L, true, false), conditionalUserProperty.mTriggerTimeout, zza, conditionalUserProperty.mTimeToLive, zzgc().zza(conditionalUserProperty.mExpiredEventName, conditionalUserProperty.mExpiredEventParams, conditionalUserProperty.mOrigin, 0L, true, false)));
        } catch (IllegalArgumentException unused) {
        }
    }

    private final void zzb(String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        Bundle bundle2;
        if (bundle == null) {
            bundle2 = new Bundle();
        } else {
            Bundle bundle3 = new Bundle(bundle);
            for (String str4 : bundle3.keySet()) {
                Object obj = bundle3.get(str4);
                if (obj instanceof Bundle) {
                    bundle3.putBundle(str4, new Bundle((Bundle) obj));
                } else {
                    int i = 0;
                    if (obj instanceof Parcelable[]) {
                        Parcelable[] parcelableArr = (Parcelable[]) obj;
                        while (i < parcelableArr.length) {
                            if (parcelableArr[i] instanceof Bundle) {
                                parcelableArr[i] = new Bundle((Bundle) parcelableArr[i]);
                            }
                            i++;
                        }
                    } else if (obj instanceof ArrayList) {
                        ArrayList arrayList = (ArrayList) obj;
                        while (i < arrayList.size()) {
                            Object obj2 = arrayList.get(i);
                            if (obj2 instanceof Bundle) {
                                arrayList.set(i, new Bundle((Bundle) obj2));
                            }
                            i++;
                        }
                    }
                }
            }
            bundle2 = bundle3;
        }
        zzge().zzc(new zzid(this, str, str2, j, bundle2, z, z2, z3, str3));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzc(AppMeasurement.ConditionalUserProperty conditionalUserProperty) {
        zzab();
        zzch();
        Preconditions.checkNotNull(conditionalUserProperty);
        Preconditions.checkNotEmpty(conditionalUserProperty.mName);
        if (!this.zzacw.isEnabled()) {
            zzgf().zziy().log("Conditional property not cleared since Firebase Analytics is disabled");
            return;
        }
        try {
            zzfy().zzd(new zzee(conditionalUserProperty.mAppId, conditionalUserProperty.mOrigin, new zzjz(conditionalUserProperty.mName, 0L, null, null), conditionalUserProperty.mCreationTimestamp, conditionalUserProperty.mActive, conditionalUserProperty.mTriggerEventName, null, conditionalUserProperty.mTriggerTimeout, null, conditionalUserProperty.mTimeToLive, zzgc().zza(conditionalUserProperty.mExpiredEventName, conditionalUserProperty.mExpiredEventParams, conditionalUserProperty.mOrigin, conditionalUserProperty.mCreationTimestamp, true, false)));
        } catch (IllegalArgumentException unused) {
        }
    }

    private final List<AppMeasurement.ConditionalUserProperty> zzf(String str, String str2, String str3) {
        zzfj zzis;
        String str4;
        if (zzge().zzjr()) {
            zzis = zzgf().zzis();
            str4 = "Cannot get conditional user properties from analytics worker thread";
        } else {
            if (!zzec.isMainThread()) {
                AtomicReference atomicReference = new AtomicReference();
                synchronized (atomicReference) {
                    this.zzacw.zzge().zzc(new zzhu(this, atomicReference, str, str2, str3));
                    try {
                        atomicReference.wait(5000L);
                    } catch (InterruptedException e) {
                        zzgf().zziv().zze("Interrupted waiting for get conditional user properties", str, e);
                    }
                }
                List<zzee> list = (List) atomicReference.get();
                if (list == null) {
                    zzgf().zziv().zzg("Timed out waiting for get conditional user properties", str);
                    return Collections.emptyList();
                }
                ArrayList arrayList = new ArrayList(list.size());
                for (zzee zzeeVar : list) {
                    AppMeasurement.ConditionalUserProperty conditionalUserProperty = new AppMeasurement.ConditionalUserProperty();
                    conditionalUserProperty.mAppId = zzeeVar.packageName;
                    conditionalUserProperty.mOrigin = zzeeVar.origin;
                    conditionalUserProperty.mCreationTimestamp = zzeeVar.creationTimestamp;
                    conditionalUserProperty.mName = zzeeVar.zzaeq.name;
                    conditionalUserProperty.mValue = zzeeVar.zzaeq.getValue();
                    conditionalUserProperty.mActive = zzeeVar.active;
                    conditionalUserProperty.mTriggerEventName = zzeeVar.triggerEventName;
                    if (zzeeVar.zzaer != null) {
                        conditionalUserProperty.mTimedOutEventName = zzeeVar.zzaer.name;
                        if (zzeeVar.zzaer.zzafr != null) {
                            conditionalUserProperty.mTimedOutEventParams = zzeeVar.zzaer.zzafr.zzij();
                        }
                    }
                    conditionalUserProperty.mTriggerTimeout = zzeeVar.triggerTimeout;
                    if (zzeeVar.zzaes != null) {
                        conditionalUserProperty.mTriggeredEventName = zzeeVar.zzaes.name;
                        if (zzeeVar.zzaes.zzafr != null) {
                            conditionalUserProperty.mTriggeredEventParams = zzeeVar.zzaes.zzafr.zzij();
                        }
                    }
                    conditionalUserProperty.mTriggeredTimestamp = zzeeVar.zzaeq.zzarl;
                    conditionalUserProperty.mTimeToLive = zzeeVar.timeToLive;
                    if (zzeeVar.zzaet != null) {
                        conditionalUserProperty.mExpiredEventName = zzeeVar.zzaet.name;
                        if (zzeeVar.zzaet.zzafr != null) {
                            conditionalUserProperty.mExpiredEventParams = zzeeVar.zzaet.zzafr.zzij();
                        }
                    }
                    arrayList.add(conditionalUserProperty);
                }
                return arrayList;
            }
            zzis = zzgf().zzis();
            str4 = "Cannot get conditional user properties from main thread";
        }
        zzis.log(str4);
        return Collections.emptyList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzi(boolean z) {
        zzab();
        zzfs();
        zzch();
        zzgf().zziy().zzg("Setting app measurement enabled (FE)", Boolean.valueOf(z));
        zzgg().setMeasurementEnabled(z);
        if (!zzgh().zzay(zzfw().zzah())) {
            zzfy().zzkm();
        } else if (!this.zzacw.isEnabled() || !this.zzaoe) {
            zzfy().zzkm();
        } else {
            zzgf().zziy().log("Recording app launch after enabling measurement for the first time (FE)");
            zzkj();
        }
    }

    public final void clearConditionalUserProperty(String str, String str2, Bundle bundle) {
        zzfs();
        zza((String) null, str, str2, bundle);
    }

    public final void clearConditionalUserPropertyAs(String str, String str2, String str3, Bundle bundle) {
        Preconditions.checkNotEmpty(str);
        zzfr();
        zza(str, str2, str3, bundle);
    }

    public final Task<String> getAppInstanceId() {
        try {
            String zzjh = zzgg().zzjh();
            return zzjh != null ? Tasks.forResult(zzjh) : Tasks.call(zzge().zzjs(), new zzhp(this));
        } catch (Exception e) {
            zzgf().zziv().log("Failed to schedule task for getAppInstanceId");
            return Tasks.forException(e);
        }
    }

    public final List<AppMeasurement.ConditionalUserProperty> getConditionalUserProperties(String str, String str2) {
        zzfs();
        return zzf(null, str, str2);
    }

    public final List<AppMeasurement.ConditionalUserProperty> getConditionalUserPropertiesAs(String str, String str2, String str3) {
        Preconditions.checkNotEmpty(str);
        zzfr();
        return zzf(str, str2, str3);
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final Map<String, Object> getUserProperties(String str, String str2, boolean z) {
        zzfs();
        return zzb(null, str, str2, z);
    }

    public final Map<String, Object> getUserPropertiesAs(String str, String str2, String str3, boolean z) {
        Preconditions.checkNotEmpty(str);
        zzfr();
        return zzb(str, str2, str3, z);
    }

    public final void logEvent(String str, String str2, Bundle bundle) {
        zzfs();
        zza(str, str2, bundle, true, this.zzaoa == null || zzkc.zzch(str2), false, null);
    }

    public final void registerOnMeasurementEventListener(AppMeasurement.OnEventListener onEventListener) {
        zzfs();
        zzch();
        Preconditions.checkNotNull(onEventListener);
        if (this.zzaob.add(onEventListener)) {
            return;
        }
        zzgf().zziv().log("OnEventListener already registered");
    }

    public final void resetAnalyticsData() {
        zzge().zzc(new zzhr(this, zzbt().currentTimeMillis()));
    }

    public final void setConditionalUserProperty(AppMeasurement.ConditionalUserProperty conditionalUserProperty) {
        Preconditions.checkNotNull(conditionalUserProperty);
        zzfs();
        AppMeasurement.ConditionalUserProperty conditionalUserProperty2 = new AppMeasurement.ConditionalUserProperty(conditionalUserProperty);
        if (!TextUtils.isEmpty(conditionalUserProperty2.mAppId)) {
            zzgf().zziv().log("Package name should be null when calling setConditionalUserProperty");
        }
        conditionalUserProperty2.mAppId = null;
        zza(conditionalUserProperty2);
    }

    public final void setConditionalUserPropertyAs(AppMeasurement.ConditionalUserProperty conditionalUserProperty) {
        Preconditions.checkNotNull(conditionalUserProperty);
        Preconditions.checkNotEmpty(conditionalUserProperty.mAppId);
        zzfr();
        zza(new AppMeasurement.ConditionalUserProperty(conditionalUserProperty));
    }

    public final void setEventInterceptor(AppMeasurement.EventInterceptor eventInterceptor) {
        AppMeasurement.EventInterceptor eventInterceptor2;
        zzab();
        zzfs();
        zzch();
        if (eventInterceptor != null && eventInterceptor != (eventInterceptor2 = this.zzaoa)) {
            Preconditions.checkState(eventInterceptor2 == null, "EventInterceptor already set.");
        }
        this.zzaoa = eventInterceptor;
    }

    public final void setMeasurementEnabled(boolean z) {
        zzch();
        zzfs();
        zzge().zzc(new zzia(this, z));
    }

    public final void setMinimumSessionDuration(long j) {
        zzfs();
        zzge().zzc(new zzib(this, j));
    }

    public final void setSessionTimeoutDuration(long j) {
        zzfs();
        zzge().zzc(new zzic(this, j));
    }

    public final void setUserProperty(String str, String str2, Object obj) {
        Preconditions.checkNotEmpty(str);
        long currentTimeMillis = zzbt().currentTimeMillis();
        int zzce = zzgc().zzce(str2);
        if (zzce != 0) {
            zzgc();
            this.zzacw.zzgc().zza(zzce, "_ev", zzkc.zza(str2, 24, true), str2 != null ? str2.length() : 0);
            return;
        }
        if (obj == null) {
            zza(str, str2, currentTimeMillis, (Object) null);
            return;
        }
        int zzi = zzgc().zzi(str2, obj);
        if (zzi != 0) {
            zzgc();
            this.zzacw.zzgc().zza(zzi, "_ev", zzkc.zza(str2, 24, true), ((obj instanceof String) || (obj instanceof CharSequence)) ? String.valueOf(obj).length() : 0);
        } else {
            Object zzj = zzgc().zzj(str2, obj);
            if (zzj != null) {
                zza(str, str2, currentTimeMillis, zzj);
            }
        }
    }

    public final void unregisterOnMeasurementEventListener(AppMeasurement.OnEventListener onEventListener) {
        zzfs();
        zzch();
        Preconditions.checkNotNull(onEventListener);
        if (this.zzaob.remove(onEventListener)) {
            return;
        }
        zzgf().zziv().log("OnEventListener had not been registered");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zza(String str, String str2, Bundle bundle) {
        zzfs();
        zzab();
        zza(str, str2, zzbt().currentTimeMillis(), bundle, true, this.zzaoa == null || zzkc.zzch(str2), false, null);
    }

    public final void zza(String str, String str2, Bundle bundle, long j) {
        zzfs();
        zzb(str, str2, j, bundle, false, true, true, null);
    }

    public final void zza(String str, String str2, Bundle bundle, boolean z) {
        zzfs();
        zza(str, str2, bundle, true, this.zzaoa == null || zzkc.zzch(str2), true, null);
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final String zzae(long j) {
        AtomicReference atomicReference = new AtomicReference();
        synchronized (atomicReference) {
            zzge().zzc(new zzhq(this, atomicReference));
            try {
                atomicReference.wait(j);
            } catch (InterruptedException unused) {
                zzgf().zziv().log("Interrupted waiting for app instance id");
                return null;
            }
        }
        return (String) atomicReference.get();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzbq(String str) {
        this.zzaod.set(str);
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ Clock zzbt() {
        return super.zzbt();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ void zzfr() {
        super.zzfr();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ void zzfs() {
        super.zzfs();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ void zzft() {
        super.zzft();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzdu zzfu() {
        return super.zzfu();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzhl zzfv() {
        return super.zzfv();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzfc zzfw() {
        return super.zzfw();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzeq zzfx() {
        return super.zzfx();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzij zzfy() {
        return super.zzfy();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzig zzfz() {
        return super.zzfz();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzfd zzga() {
        return super.zzga();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzff zzgb() {
        return super.zzgb();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzkc zzgc() {
        return super.zzgc();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzji zzgd() {
        return super.zzgd();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ zzgh zzge() {
        return super.zzge();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ zzfh zzgf() {
        return super.zzgf();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzfs zzgg() {
        return super.zzgg();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzeg zzgh() {
        return super.zzgh();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ zzec zzgi() {
        return super.zzgi();
    }

    @Override // com.google.android.gms.internal.measurement.zzhi
    protected final boolean zzhh() {
        return false;
    }

    public final String zzhq() {
        AtomicReference atomicReference = new AtomicReference();
        return (String) zzge().zza(atomicReference, 15000L, "String test flag value", new zzhw(this, atomicReference));
    }

    public final List<zzjz> zzj(boolean z) {
        zzfj zziv;
        String str;
        zzfs();
        zzch();
        zzgf().zziy().log("Fetching user attributes (FE)");
        if (zzge().zzjr()) {
            zziv = zzgf().zzis();
            str = "Cannot get all user properties from analytics worker thread";
        } else if (zzec.isMainThread()) {
            zziv = zzgf().zzis();
            str = "Cannot get all user properties from main thread";
        } else {
            AtomicReference atomicReference = new AtomicReference();
            synchronized (atomicReference) {
                this.zzacw.zzge().zzc(new zzho(this, atomicReference, z));
                try {
                    atomicReference.wait(5000L);
                } catch (InterruptedException e) {
                    zzgf().zziv().zzg("Interrupted waiting for get user properties", e);
                }
            }
            List<zzjz> list = (List) atomicReference.get();
            if (list != null) {
                return list;
            }
            zziv = zzgf().zziv();
            str = "Timed out waiting for get user properties";
        }
        zziv.log(str);
        return Collections.emptyList();
    }

    public final String zzjh() {
        zzfs();
        return this.zzaod.get();
    }

    public final Boolean zzkf() {
        AtomicReference atomicReference = new AtomicReference();
        return (Boolean) zzge().zza(atomicReference, 15000L, "boolean test flag value", new zzhm(this, atomicReference));
    }

    public final Long zzkg() {
        AtomicReference atomicReference = new AtomicReference();
        return (Long) zzge().zza(atomicReference, 15000L, "long test flag value", new zzhx(this, atomicReference));
    }

    public final Integer zzkh() {
        AtomicReference atomicReference = new AtomicReference();
        return (Integer) zzge().zza(atomicReference, 15000L, "int test flag value", new zzhy(this, atomicReference));
    }

    public final Double zzki() {
        AtomicReference atomicReference = new AtomicReference();
        return (Double) zzge().zza(atomicReference, 15000L, "double test flag value", new zzhz(this, atomicReference));
    }

    public final void zzkj() {
        zzab();
        zzfs();
        zzch();
        if (this.zzacw.zzkd()) {
            zzfy().zzkj();
            this.zzaoe = false;
            String zzjk = zzgg().zzjk();
            if (TextUtils.isEmpty(zzjk)) {
                return;
            }
            zzfx().zzch();
            if (zzjk.equals(Build.VERSION.RELEASE)) {
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("_po", zzjk);
            logEvent(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_ou", bundle);
        }
    }
}
