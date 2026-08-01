package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
public class TagManager {
    private static TagManager zza;
    private final zzfp zzb;
    private final Context zzc;
    private final DataLayer zzd;
    private final zzey zze;
    private final ConcurrentMap<String, zzaa> zzf;
    private final zzap zzg;

    TagManager(Context context, zzfp zzfpVar, DataLayer dataLayer, zzey zzeyVar) {
        Context applicationContext = context.getApplicationContext();
        this.zzc = applicationContext;
        this.zze = zzeyVar;
        this.zzb = zzfpVar;
        this.zzf = new ConcurrentHashMap();
        this.zzd = dataLayer;
        dataLayer.zzg(new zzfm(this));
        dataLayer.zzg(new zzg(applicationContext));
        this.zzg = new zzap();
        Preconditions.checkNotNull(applicationContext);
        applicationContext.registerComponentCallbacks(new zzfo(this));
        Preconditions.checkNotNull(applicationContext);
        zzd.zzb(applicationContext);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* bridge */ /* synthetic */ void zzb(TagManager tagManager, String str) {
        Preconditions.checkNotNull(tagManager.zzf);
        Iterator<zzaa> it = tagManager.zzf.values().iterator();
        while (it.hasNext()) {
            it.next().zzd(str);
        }
    }

    public void dispatch() {
        this.zze.zza();
    }

    public DataLayer getDataLayer() {
        return this.zzd;
    }

    public PendingResult<ContainerHolder> loadContainerDefaultOnly(String str, int i) {
        zzal zzalVar = new zzal(this.zzc, this, null, str, i, this.zzg);
        zzalVar.zzl();
        return zzalVar;
    }

    public PendingResult<ContainerHolder> loadContainerPreferFresh(String str, int i) {
        zzal zzalVar = new zzal(this.zzc, this, null, str, i, this.zzg);
        zzalVar.zzm();
        return zzalVar;
    }

    public PendingResult<ContainerHolder> loadContainerPreferNonDefault(String str, int i) {
        zzal zzalVar = new zzal(this.zzc, this, null, str, i, this.zzg);
        zzalVar.zzn();
        return zzalVar;
    }

    public void setVerboseLoggingEnabled(boolean z) {
        int i = true != z ? 5 : 2;
        zzdh.zza = i;
        zzdh.zzb.zzc(i);
    }

    public final int zza(zzaa zzaaVar) {
        this.zzf.put(zzaaVar.zza(), zzaaVar);
        return this.zzf.size();
    }

    public final boolean zzc(zzaa zzaaVar) {
        return this.zzf.remove(zzaaVar.zza()) != null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final synchronized boolean zzd(Uri uri) {
        zzea zza2 = zzea.zza();
        if (!zza2.zzd(uri)) {
            return false;
        }
        String zzc = zza2.zzc();
        int zze = zza2.zze();
        int i = zze - 1;
        if (zze == 0) {
            throw null;
        }
        if (i == 0) {
            zzaa zzaaVar = this.zzf.get(zzc);
            if (zzaaVar != null) {
                zzaaVar.zze(null);
                zzaaVar.refresh();
            }
        } else if (i == 1 || i == 2) {
            for (String str : this.zzf.keySet()) {
                zzaa zzaaVar2 = this.zzf.get(str);
                if (str.equals(zzc)) {
                    zzaaVar2.zze(zza2.zzb());
                    zzaaVar2.refresh();
                } else if (zzaaVar2.zzb() != null) {
                    zzaaVar2.zze(null);
                    zzaaVar2.refresh();
                }
            }
        }
        return true;
    }

    public static TagManager getInstance(Context context) {
        TagManager tagManager;
        synchronized (TagManager.class) {
            if (zza == null) {
                if (context == null) {
                    zzdh.zza("TagManager.getInstance requires non-null context.");
                    throw null;
                }
                zza = new TagManager(context, new zzfn(), new DataLayer(new zzbe(context)), zzff.zzg());
            }
            tagManager = zza;
        }
        return tagManager;
    }

    public PendingResult<ContainerHolder> loadContainerDefaultOnly(String str, int i, Handler handler) {
        zzal zzalVar = new zzal(this.zzc, this, handler.getLooper(), str, i, this.zzg);
        zzalVar.zzl();
        return zzalVar;
    }

    public PendingResult<ContainerHolder> loadContainerPreferFresh(String str, int i, Handler handler) {
        zzal zzalVar = new zzal(this.zzc, this, handler.getLooper(), str, i, this.zzg);
        zzalVar.zzm();
        return zzalVar;
    }

    public PendingResult<ContainerHolder> loadContainerPreferNonDefault(String str, int i, Handler handler) {
        zzal zzalVar = new zzal(this.zzc, this, handler.getLooper(), str, i, this.zzg);
        zzalVar.zzn();
        return zzalVar;
    }
}
