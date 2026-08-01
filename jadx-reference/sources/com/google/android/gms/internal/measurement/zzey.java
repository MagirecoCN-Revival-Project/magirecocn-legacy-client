package com.google.android.gms.internal.measurement;

import android.net.Uri;
import androidx.work.WorkRequest;
import backtraceio.library.services.BacktraceMetrics;
import com.adjust.sdk.Constants;
import cz.msebera.android.httpclient.HttpStatus;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class zzey {
    static zzec zzagd;
    static List<zza<Integer>> zzage = new ArrayList();
    static List<zza<Long>> zzagf = new ArrayList();
    static List<zza<Boolean>> zzagg = new ArrayList();
    static List<zza<String>> zzagh = new ArrayList();
    static List<zza<Double>> zzagi = new ArrayList();
    private static final zzxe zzagj;
    private static zza<Boolean> zzagk;
    private static zza<Boolean> zzagl;
    private static zza<Boolean> zzagm;
    public static zza<Boolean> zzagn;
    public static zza<Boolean> zzago;
    public static zza<String> zzagp;
    public static zza<Long> zzagq;
    public static zza<Long> zzagr;
    public static zza<Long> zzags;
    public static zza<String> zzagt;
    public static zza<String> zzagu;
    public static zza<Integer> zzagv;
    public static zza<Integer> zzagw;
    public static zza<Integer> zzagx;
    public static zza<Integer> zzagy;
    public static zza<Integer> zzagz;
    public static zza<Integer> zzaha;
    public static zza<Integer> zzahb;
    public static zza<Integer> zzahc;
    public static zza<Integer> zzahd;
    public static zza<Integer> zzahe;
    public static zza<String> zzahf;
    public static zza<Long> zzahg;
    public static zza<Long> zzahh;
    public static zza<Long> zzahi;
    public static zza<Long> zzahj;
    public static zza<Long> zzahk;
    public static zza<Long> zzahl;
    public static zza<Long> zzahm;
    public static zza<Long> zzahn;
    public static zza<Long> zzaho;
    public static zza<Long> zzahp;
    public static zza<Long> zzahq;
    public static zza<Integer> zzahr;
    public static zza<Long> zzahs;
    public static zza<Integer> zzaht;
    public static zza<Integer> zzahu;
    public static zza<Long> zzahv;
    public static zza<Boolean> zzahw;
    public static zza<String> zzahx;
    public static zza<Long> zzahy;
    public static zza<Integer> zzahz;
    public static zza<Double> zzaia;
    public static zza<Boolean> zzaib;
    public static zza<Boolean> zzaic;
    public static zza<Boolean> zzaid;
    public static zza<Boolean> zzaie;
    public static zza<Boolean> zzaif;
    public static zza<Boolean> zzaig;
    public static zza<Boolean> zzaih;
    private static zza<Boolean> zzaii;
    public static zza<Boolean> zzaij;

    /* loaded from: classes.dex */
    public static final class zza<V> {
        private final V zzaab;
        private zzwu<V> zzaik;
        private final V zzail;
        private volatile V zzaim;
        private final String zzny;

        private zza(String str, V v, V v2) {
            this.zzny = str;
            this.zzaab = v;
            this.zzail = v2;
        }

        static zza<Double> zza(String str, double d, double d2) {
            Double valueOf = Double.valueOf(-3.0d);
            zza<Double> zzaVar = new zza<>(str, valueOf, valueOf);
            zzey.zzagi.add(zzaVar);
            return zzaVar;
        }

        static zza<Long> zzb(String str, long j, long j2) {
            zza<Long> zzaVar = new zza<>(str, Long.valueOf(j), Long.valueOf(j2));
            zzey.zzagf.add(zzaVar);
            return zzaVar;
        }

        static zza<Boolean> zzb(String str, boolean z, boolean z2) {
            zza<Boolean> zzaVar = new zza<>(str, Boolean.valueOf(z), Boolean.valueOf(z2));
            zzey.zzagg.add(zzaVar);
            return zzaVar;
        }

        static zza<Integer> zzc(String str, int i, int i2) {
            zza<Integer> zzaVar = new zza<>(str, Integer.valueOf(i), Integer.valueOf(i2));
            zzey.zzage.add(zzaVar);
            return zzaVar;
        }

        static zza<String> zzd(String str, String str2, String str3) {
            zza<String> zzaVar = new zza<>(str, str2, str3);
            zzey.zzagh.add(zzaVar);
            return zzaVar;
        }

        private static void zzil() {
            synchronized (zza.class) {
                if (zzec.isMainThread()) {
                    throw new IllegalStateException("Tried to refresh flag cache on main thread or on package side.");
                }
                zzec zzecVar = zzey.zzagd;
                for (zza<Boolean> zzaVar : zzey.zzagg) {
                    ((zza) zzaVar).zzaim = (V) ((zza) zzaVar).zzaik.get();
                }
                for (zza<String> zzaVar2 : zzey.zzagh) {
                    ((zza) zzaVar2).zzaim = (V) ((zza) zzaVar2).zzaik.get();
                }
                for (zza<Long> zzaVar3 : zzey.zzagf) {
                    ((zza) zzaVar3).zzaim = (V) ((zza) zzaVar3).zzaik.get();
                }
                for (zza<Integer> zzaVar4 : zzey.zzage) {
                    ((zza) zzaVar4).zzaim = (V) ((zza) zzaVar4).zzaik.get();
                }
                for (zza<Double> zzaVar5 : zzey.zzagi) {
                    ((zza) zzaVar5).zzaim = (V) ((zza) zzaVar5).zzaik.get();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void zzm() {
            synchronized (zza.class) {
                for (zza<Boolean> zzaVar : zzey.zzagg) {
                    zzxe zzxeVar = zzey.zzagj;
                    String str = ((zza) zzaVar).zzny;
                    zzec zzecVar = zzey.zzagd;
                    ((zza) zzaVar).zzaik = (zzwu<V>) zzxeVar.zzf(str, ((zza) zzaVar).zzaab.booleanValue());
                }
                for (zza<String> zzaVar2 : zzey.zzagh) {
                    zzxe zzxeVar2 = zzey.zzagj;
                    String str2 = ((zza) zzaVar2).zzny;
                    zzec zzecVar2 = zzey.zzagd;
                    ((zza) zzaVar2).zzaik = (zzwu<V>) zzxeVar2.zzv(str2, ((zza) zzaVar2).zzaab);
                }
                for (zza<Long> zzaVar3 : zzey.zzagf) {
                    zzxe zzxeVar3 = zzey.zzagj;
                    String str3 = ((zza) zzaVar3).zzny;
                    zzec zzecVar3 = zzey.zzagd;
                    ((zza) zzaVar3).zzaik = (zzwu<V>) zzxeVar3.zze(str3, ((zza) zzaVar3).zzaab.longValue());
                }
                for (zza<Integer> zzaVar4 : zzey.zzage) {
                    zzxe zzxeVar4 = zzey.zzagj;
                    String str4 = ((zza) zzaVar4).zzny;
                    zzec zzecVar4 = zzey.zzagd;
                    ((zza) zzaVar4).zzaik = (zzwu<V>) zzxeVar4.zzd(str4, ((zza) zzaVar4).zzaab.intValue());
                }
                for (zza<Double> zzaVar5 : zzey.zzagi) {
                    zzxe zzxeVar5 = zzey.zzagj;
                    String str5 = ((zza) zzaVar5).zzny;
                    zzec zzecVar5 = zzey.zzagd;
                    ((zza) zzaVar5).zzaik = (zzwu<V>) zzxeVar5.zzb(str5, ((zza) zzaVar5).zzaab.doubleValue());
                }
            }
        }

        public final V get() {
            if (zzey.zzagd == null) {
                return this.zzaab;
            }
            zzec zzecVar = zzey.zzagd;
            if (zzec.isMainThread()) {
                return this.zzaim == null ? this.zzaab : this.zzaim;
            }
            zzil();
            return this.zzaik.get();
        }

        public final V get(V v) {
            if (v != null) {
                return v;
            }
            if (zzey.zzagd == null) {
                return this.zzaab;
            }
            zzec zzecVar = zzey.zzagd;
            if (zzec.isMainThread()) {
                return this.zzaim == null ? this.zzaab : this.zzaim;
            }
            zzil();
            return this.zzaik.get();
        }

        public final String getKey() {
            return this.zzny;
        }
    }

    static {
        String valueOf = String.valueOf(Uri.encode("com.google.android.gms.measurement"));
        zzagj = new zzxe(Uri.parse(valueOf.length() != 0 ? "content://com.google.android.gms.phenotype/".concat(valueOf) : new String("content://com.google.android.gms.phenotype/")));
        zzagk = zza.zzb("measurement.log_third_party_store_events_enabled", false, false);
        zzagl = zza.zzb("measurement.log_installs_enabled", false, false);
        zzagm = zza.zzb("measurement.log_upgrades_enabled", false, false);
        zzagn = zza.zzb("measurement.log_androidId_enabled", false, false);
        zzago = zza.zzb("measurement.upload_dsid_enabled", false, false);
        zzagp = zza.zzd("measurement.log_tag", "FA", "FA-SVC");
        zzagq = zza.zzb("measurement.ad_id_cache_time", WorkRequest.MIN_BACKOFF_MILLIS, WorkRequest.MIN_BACKOFF_MILLIS);
        zzagr = zza.zzb("measurement.monitoring.sample_period_millis", 86400000L, 86400000L);
        zzags = zza.zzb("measurement.config.cache_time", 86400000L, 3600000L);
        zzagt = zza.zzd("measurement.config.url_scheme", Constants.SCHEME, Constants.SCHEME);
        zzagu = zza.zzd("measurement.config.url_authority", "app-measurement.com", "app-measurement.com");
        zzagv = zza.zzc("measurement.upload.max_bundles", 100, 100);
        zzagw = zza.zzc("measurement.upload.max_batch_size", 65536, 65536);
        zzagx = zza.zzc("measurement.upload.max_bundle_size", 65536, 65536);
        zzagy = zza.zzc("measurement.upload.max_events_per_bundle", 1000, 1000);
        zzagz = zza.zzc("measurement.upload.max_events_per_day", 100000, 100000);
        zzaha = zza.zzc("measurement.upload.max_error_events_per_day", 1000, 1000);
        zzahb = zza.zzc("measurement.upload.max_public_events_per_day", 50000, 50000);
        zzahc = zza.zzc("measurement.upload.max_conversions_per_day", HttpStatus.SC_INTERNAL_SERVER_ERROR, HttpStatus.SC_INTERNAL_SERVER_ERROR);
        zzahd = zza.zzc("measurement.upload.max_realtime_events_per_day", 10, 10);
        zzahe = zza.zzc("measurement.store.max_stored_events_per_app", 100000, 100000);
        zzahf = zza.zzd("measurement.upload.url", "https://app-measurement.com/a", "https://app-measurement.com/a");
        zzahg = zza.zzb("measurement.upload.backoff_period", 43200000L, 43200000L);
        zzahh = zza.zzb("measurement.upload.window_interval", 3600000L, 3600000L);
        zzahi = zza.zzb("measurement.upload.interval", 3600000L, 3600000L);
        zzahj = zza.zzb("measurement.upload.realtime_upload_interval", WorkRequest.MIN_BACKOFF_MILLIS, WorkRequest.MIN_BACKOFF_MILLIS);
        zzahk = zza.zzb("measurement.upload.debug_upload_interval", 1000L, 1000L);
        zzahl = zza.zzb("measurement.upload.minimum_delay", 500L, 500L);
        zzahm = zza.zzb("measurement.alarm_manager.minimum_interval", 60000L, 60000L);
        zzahn = zza.zzb("measurement.upload.stale_data_deletion_interval", 86400000L, 86400000L);
        zzaho = zza.zzb("measurement.upload.refresh_blacklisted_config_interval", 604800000L, 604800000L);
        zzahp = zza.zzb("measurement.upload.initial_upload_delay_time", 15000L, 15000L);
        zzahq = zza.zzb("measurement.upload.retry_time", BacktraceMetrics.defaultTimeIntervalMs, BacktraceMetrics.defaultTimeIntervalMs);
        zzahr = zza.zzc("measurement.upload.retry_count", 6, 6);
        zzahs = zza.zzb("measurement.upload.max_queue_time", 2419200000L, 2419200000L);
        zzaht = zza.zzc("measurement.lifetimevalue.max_currency_tracked", 4, 4);
        zzahu = zza.zzc("measurement.audience.filter_result_max_count", 200, 200);
        zzahv = zza.zzb("measurement.service_client.idle_disconnect_millis", 5000L, 5000L);
        zzahw = zza.zzb("measurement.test.boolean_flag", false, false);
        zzahx = zza.zzd("measurement.test.string_flag", "---", "---");
        zzahy = zza.zzb("measurement.test.long_flag", -1L, -1L);
        zzahz = zza.zzc("measurement.test.int_flag", -2, -2);
        zzaia = zza.zza("measurement.test.double_flag", -3.0d, -3.0d);
        zzaib = zza.zzb("measurement.lifetimevalue.user_engagement_tracking_enabled", false, false);
        zzaic = zza.zzb("measurement.audience.complex_param_evaluation", false, false);
        zzaid = zza.zzb("measurement.validation.internal_limits_internal_event_params", false, false);
        zzaie = zza.zzb("measurement.quality.unsuccessful_update_retry_counter", false, false);
        zzaif = zza.zzb("measurement.iid.disable_on_collection_disabled", true, true);
        zzaig = zza.zzb("measurement.app_launch.call_only_when_enabled", true, true);
        zzaih = zza.zzb("measurement.run_on_worker_inline", true, false);
        zzaii = zza.zzb("measurement.audience.dynamic_filters", false, false);
        zzaij = zza.zzb("measurement.reset_analytics.persist_time", false, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void zza(zzec zzecVar) {
        zzagd = zzecVar;
        zza.zzm();
    }
}
