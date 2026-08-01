package com.google.android.gms.internal.measurement;

import android.content.Context;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import java.io.IOException;

/* loaded from: classes.dex */
public final class zzjy extends zzjr {
    /* JADX INFO: Access modifiers changed from: package-private */
    public zzjy(zzjs zzjsVar) {
        super(zzjsVar);
    }

    public static zzkq zza(zzkp zzkpVar, String str) {
        for (zzkq zzkqVar : zzkpVar.zzatm) {
            if (zzkqVar.name.equals(str)) {
                return zzkqVar;
            }
        }
        return null;
    }

    private static void zza(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            sb.append("  ");
        }
    }

    private final void zza(StringBuilder sb, int i, zzkh zzkhVar) {
        String str;
        if (zzkhVar == null) {
            return;
        }
        zza(sb, i);
        sb.append("filter {\n");
        zza(sb, i, "complement", zzkhVar.zzasj);
        zza(sb, i, "param_name", zzgb().zzbj(zzkhVar.zzask));
        int i2 = i + 1;
        zzkk zzkkVar = zzkhVar.zzash;
        if (zzkkVar != null) {
            zza(sb, i2);
            sb.append("string_filter");
            sb.append(" {\n");
            if (zzkkVar.zzast != null) {
                switch (zzkkVar.zzast.intValue()) {
                    case 1:
                        str = "REGEXP";
                        break;
                    case 2:
                        str = "BEGINS_WITH";
                        break;
                    case 3:
                        str = "ENDS_WITH";
                        break;
                    case 4:
                        str = "PARTIAL";
                        break;
                    case 5:
                        str = "EXACT";
                        break;
                    case 6:
                        str = "IN_LIST";
                        break;
                    default:
                        str = "UNKNOWN_MATCH_TYPE";
                        break;
                }
                zza(sb, i2, "match_type", str);
            }
            zza(sb, i2, "expression", zzkkVar.zzasu);
            zza(sb, i2, "case_sensitive", zzkkVar.zzasv);
            if (zzkkVar.zzasw.length > 0) {
                zza(sb, i2 + 1);
                sb.append("expression_list {\n");
                for (String str2 : zzkkVar.zzasw) {
                    zza(sb, i2 + 2);
                    sb.append(str2);
                    sb.append("\n");
                }
                sb.append("}\n");
            }
            zza(sb, i2);
            sb.append("}\n");
        }
        zza(sb, i2, "number_filter", zzkhVar.zzasi);
        zza(sb, i);
        sb.append("}\n");
    }

    private final void zza(StringBuilder sb, int i, String str, zzki zzkiVar) {
        if (zzkiVar == null) {
            return;
        }
        zza(sb, i);
        sb.append(str);
        sb.append(" {\n");
        if (zzkiVar.zzasl != null) {
            int intValue = zzkiVar.zzasl.intValue();
            zza(sb, i, "comparison_type", intValue != 1 ? intValue != 2 ? intValue != 3 ? intValue != 4 ? "UNKNOWN_COMPARISON_TYPE" : "BETWEEN" : "EQUAL" : "GREATER_THAN" : "LESS_THAN");
        }
        zza(sb, i, "match_as_float", zzkiVar.zzasm);
        zza(sb, i, "comparison_value", zzkiVar.zzasn);
        zza(sb, i, "min_comparison_value", zzkiVar.zzaso);
        zza(sb, i, "max_comparison_value", zzkiVar.zzasp);
        zza(sb, i);
        sb.append("}\n");
    }

    private static void zza(StringBuilder sb, int i, String str, zzkt zzktVar) {
        if (zzktVar == null) {
            return;
        }
        zza(sb, 3);
        sb.append(str);
        sb.append(" {\n");
        int i2 = 0;
        if (zzktVar.zzaux != null) {
            zza(sb, 4);
            sb.append("results: ");
            long[] jArr = zzktVar.zzaux;
            int length = jArr.length;
            int i3 = 0;
            int i4 = 0;
            while (i3 < length) {
                Long valueOf = Long.valueOf(jArr[i3]);
                int i5 = i4 + 1;
                if (i4 != 0) {
                    sb.append(", ");
                }
                sb.append(valueOf);
                i3++;
                i4 = i5;
            }
            sb.append('\n');
        }
        if (zzktVar.zzauw != null) {
            zza(sb, 4);
            sb.append("status: ");
            long[] jArr2 = zzktVar.zzauw;
            int length2 = jArr2.length;
            int i6 = 0;
            while (i2 < length2) {
                Long valueOf2 = Long.valueOf(jArr2[i2]);
                int i7 = i6 + 1;
                if (i6 != 0) {
                    sb.append(", ");
                }
                sb.append(valueOf2);
                i2++;
                i6 = i7;
            }
            sb.append('\n');
        }
        zza(sb, 3);
        sb.append("}\n");
    }

    private static void zza(StringBuilder sb, int i, String str, Object obj) {
        if (obj == null) {
            return;
        }
        zza(sb, i + 1);
        sb.append(str);
        sb.append(": ");
        sb.append(obj);
        sb.append('\n');
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static zzkq[] zza(zzkq[] zzkqVarArr, String str, Object obj) {
        for (zzkq zzkqVar : zzkqVarArr) {
            if (str.equals(zzkqVar.name)) {
                zzkqVar.zzatq = null;
                zzkqVar.zzajo = null;
                zzkqVar.zzaro = null;
                if (obj instanceof Long) {
                    zzkqVar.zzatq = (Long) obj;
                } else if (obj instanceof String) {
                    zzkqVar.zzajo = (String) obj;
                } else if (obj instanceof Double) {
                    zzkqVar.zzaro = (Double) obj;
                }
                return zzkqVarArr;
            }
        }
        zzkq[] zzkqVarArr2 = new zzkq[zzkqVarArr.length + 1];
        System.arraycopy(zzkqVarArr, 0, zzkqVarArr2, 0, zzkqVarArr.length);
        zzkq zzkqVar2 = new zzkq();
        zzkqVar2.name = str;
        if (obj instanceof Long) {
            zzkqVar2.zzatq = (Long) obj;
        } else if (obj instanceof String) {
            zzkqVar2.zzajo = (String) obj;
        } else if (obj instanceof Double) {
            zzkqVar2.zzaro = (Double) obj;
        }
        zzkqVarArr2[zzkqVarArr.length] = zzkqVar2;
        return zzkqVarArr2;
    }

    public static Object zzb(zzkp zzkpVar, String str) {
        zzkq zza = zza(zzkpVar, str);
        if (zza == null) {
            return null;
        }
        if (zza.zzajo != null) {
            return zza.zzajo;
        }
        if (zza.zzatq != null) {
            return zza.zzatq;
        }
        if (zza.zzaro != null) {
            return zza.zzaro;
        }
        return null;
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final String zza(zzkg zzkgVar) {
        if (zzkgVar == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\nevent_filter {\n");
        zza(sb, 0, "filter_id", zzkgVar.zzasb);
        zza(sb, 0, "event_name", zzgb().zzbi(zzkgVar.zzasc));
        zza(sb, 1, "event_count_filter", zzkgVar.zzasf);
        sb.append("  filters {\n");
        for (zzkh zzkhVar : zzkgVar.zzasd) {
            zza(sb, 2, zzkhVar);
        }
        zza(sb, 1);
        sb.append("}\n}\n");
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final String zza(zzkj zzkjVar) {
        if (zzkjVar == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\nproperty_filter {\n");
        zza(sb, 0, "filter_id", zzkjVar.zzasb);
        zza(sb, 0, "property_name", zzgb().zzbk(zzkjVar.zzasr));
        zza(sb, 1, zzkjVar.zzass);
        sb.append("}\n");
        return sb.toString();
    }

    public final void zza(zzkq zzkqVar, Object obj) {
        Preconditions.checkNotNull(obj);
        zzkqVar.zzajo = null;
        zzkqVar.zzatq = null;
        zzkqVar.zzaro = null;
        if (obj instanceof String) {
            zzkqVar.zzajo = (String) obj;
            return;
        }
        if (obj instanceof Long) {
            zzkqVar.zzatq = (Long) obj;
        } else if (obj instanceof Double) {
            zzkqVar.zzaro = (Double) obj;
        } else {
            zzgf().zzis().zzg("Ignoring invalid (type) event param value", obj);
        }
    }

    public final void zza(zzku zzkuVar, Object obj) {
        Preconditions.checkNotNull(obj);
        zzkuVar.zzajo = null;
        zzkuVar.zzatq = null;
        zzkuVar.zzaro = null;
        if (obj instanceof String) {
            zzkuVar.zzajo = (String) obj;
            return;
        }
        if (obj instanceof Long) {
            zzkuVar.zzatq = (Long) obj;
        } else if (obj instanceof Double) {
            zzkuVar.zzaro = (Double) obj;
        } else {
            zzgf().zzis().zzg("Ignoring invalid (type) user attribute value", obj);
        }
    }

    public final byte[] zza(zzkr zzkrVar) {
        try {
            int zzvv = zzkrVar.zzvv();
            byte[] bArr = new byte[zzvv];
            zzaby zzb = zzaby.zzb(bArr, 0, zzvv);
            zzkrVar.zza(zzb);
            zzb.zzvn();
            return bArr;
        } catch (IOException e) {
            zzgf().zzis().zzg("Data loss. Failed to serialize batch", e);
            return null;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final String zzb(zzkr zzkrVar) {
        zzks[] zzksVarArr;
        zzkp[] zzkpVarArr;
        zzkp[] zzkpVarArr2;
        zzks[] zzksVarArr2;
        StringBuilder sb = new StringBuilder();
        sb.append("\nbatch {\n");
        if (zzkrVar.zzatr != null) {
            zzks[] zzksVarArr3 = zzkrVar.zzatr;
            int length = zzksVarArr3.length;
            int i = 0;
            while (i < length) {
                zzks zzksVar = zzksVarArr3[i];
                if (zzksVar == null || zzksVar == null) {
                    zzksVarArr = zzksVarArr3;
                } else {
                    zza(sb, 1);
                    sb.append("bundle {\n");
                    zza(sb, 1, "protocol_version", zzksVar.zzatt);
                    zza(sb, 1, "platform", zzksVar.zzaub);
                    zza(sb, 1, "gmp_version", zzksVar.zzauf);
                    zza(sb, 1, "uploading_gmp_version", zzksVar.zzaug);
                    zza(sb, 1, "config_version", zzksVar.zzaur);
                    zza(sb, 1, "gmp_app_id", zzksVar.zzadm);
                    zza(sb, 1, "app_id", zzksVar.zzti);
                    zza(sb, 1, "app_version", zzksVar.zzth);
                    zza(sb, 1, "app_version_major", zzksVar.zzaun);
                    zza(sb, 1, "firebase_instance_id", zzksVar.zzado);
                    zza(sb, 1, "dev_cert_hash", zzksVar.zzauj);
                    zza(sb, 1, "app_store", zzksVar.zzadt);
                    zza(sb, 1, "upload_timestamp_millis", zzksVar.zzatw);
                    zza(sb, 1, "start_timestamp_millis", zzksVar.zzatx);
                    zza(sb, 1, "end_timestamp_millis", zzksVar.zzaty);
                    zza(sb, 1, "previous_bundle_start_timestamp_millis", zzksVar.zzatz);
                    zza(sb, 1, "previous_bundle_end_timestamp_millis", zzksVar.zzaua);
                    zza(sb, 1, "app_instance_id", zzksVar.zzadl);
                    zza(sb, 1, "resettable_device_id", zzksVar.zzauh);
                    zza(sb, 1, "device_id", zzksVar.zzauq);
                    zza(sb, 1, "ds_id", zzksVar.zzaut);
                    zza(sb, 1, "limited_ad_tracking", zzksVar.zzaui);
                    zza(sb, 1, "os_version", zzksVar.zzauc);
                    zza(sb, 1, "device_model", zzksVar.zzaud);
                    zza(sb, 1, "user_default_language", zzksVar.zzafo);
                    zza(sb, 1, "time_zone_offset_minutes", zzksVar.zzaue);
                    zza(sb, 1, "bundle_sequential_index", zzksVar.zzauk);
                    zza(sb, 1, "service_upload", zzksVar.zzaul);
                    zza(sb, 1, "health_monitor", zzksVar.zzaek);
                    if (zzksVar.zzaus != null && zzksVar.zzaus.longValue() != 0) {
                        zza(sb, 1, "android_id", zzksVar.zzaus);
                    }
                    if (zzksVar.zzauv != null) {
                        zza(sb, 1, "retry_counter", zzksVar.zzauv);
                    }
                    zzku[] zzkuVarArr = zzksVar.zzatv;
                    int i2 = 2;
                    if (zzkuVarArr != null) {
                        int length2 = zzkuVarArr.length;
                        int i3 = 0;
                        while (i3 < length2) {
                            zzku zzkuVar = zzkuVarArr[i3];
                            if (zzkuVar != null) {
                                zza(sb, 2);
                                sb.append("user_property {\n");
                                zzksVarArr2 = zzksVarArr3;
                                zza(sb, 2, "set_timestamp_millis", zzkuVar.zzauz);
                                zza(sb, 2, "name", zzgb().zzbk(zzkuVar.name));
                                zza(sb, 2, "string_value", zzkuVar.zzajo);
                                zza(sb, 2, "int_value", zzkuVar.zzatq);
                                zza(sb, 2, "double_value", zzkuVar.zzaro);
                                zza(sb, 2);
                                sb.append("}\n");
                            } else {
                                zzksVarArr2 = zzksVarArr3;
                            }
                            i3++;
                            zzksVarArr3 = zzksVarArr2;
                        }
                    }
                    zzksVarArr = zzksVarArr3;
                    zzko[] zzkoVarArr = zzksVar.zzaum;
                    if (zzkoVarArr != null) {
                        for (zzko zzkoVar : zzkoVarArr) {
                            if (zzkoVar != null) {
                                zza(sb, 2);
                                sb.append("audience_membership {\n");
                                zza(sb, 2, "audience_id", zzkoVar.zzarx);
                                zza(sb, 2, "new_audience", zzkoVar.zzatk);
                                zza(sb, 2, "current_data", zzkoVar.zzati);
                                zza(sb, 2, "previous_data", zzkoVar.zzatj);
                                zza(sb, 2);
                                sb.append("}\n");
                            }
                        }
                    }
                    zzkp[] zzkpVarArr3 = zzksVar.zzatu;
                    if (zzkpVarArr3 != null) {
                        int length3 = zzkpVarArr3.length;
                        int i4 = 0;
                        while (i4 < length3) {
                            zzkp zzkpVar = zzkpVarArr3[i4];
                            if (zzkpVar != null) {
                                zza(sb, i2);
                                sb.append("event {\n");
                                zza(sb, i2, "name", zzgb().zzbi(zzkpVar.name));
                                zza(sb, i2, "timestamp_millis", zzkpVar.zzatn);
                                zza(sb, i2, "previous_timestamp_millis", zzkpVar.zzato);
                                zza(sb, i2, "count", zzkpVar.count);
                                zzkq[] zzkqVarArr = zzkpVar.zzatm;
                                if (zzkqVarArr != null) {
                                    int length4 = zzkqVarArr.length;
                                    int i5 = 0;
                                    while (i5 < length4) {
                                        zzkq zzkqVar = zzkqVarArr[i5];
                                        if (zzkqVar != null) {
                                            zza(sb, 3);
                                            sb.append("param {\n");
                                            zzkpVarArr2 = zzkpVarArr3;
                                            zza(sb, 3, "name", zzgb().zzbj(zzkqVar.name));
                                            zza(sb, 3, "string_value", zzkqVar.zzajo);
                                            zza(sb, 3, "int_value", zzkqVar.zzatq);
                                            zza(sb, 3, "double_value", zzkqVar.zzaro);
                                            zza(sb, 3);
                                            sb.append("}\n");
                                        } else {
                                            zzkpVarArr2 = zzkpVarArr3;
                                        }
                                        i5++;
                                        zzkpVarArr3 = zzkpVarArr2;
                                    }
                                }
                                zzkpVarArr = zzkpVarArr3;
                                zza(sb, 2);
                                sb.append("}\n");
                            } else {
                                zzkpVarArr = zzkpVarArr3;
                            }
                            i4++;
                            zzkpVarArr3 = zzkpVarArr;
                            i2 = 2;
                        }
                    }
                    zza(sb, 1);
                    sb.append("}\n");
                }
                i++;
                zzksVarArr3 = zzksVarArr;
            }
        }
        sb.append("}\n");
        return sb.toString();
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

    @Override // com.google.android.gms.internal.measurement.zzjr
    protected final boolean zzhh() {
        return false;
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final /* bridge */ /* synthetic */ zzjy zzjc() {
        return super.zzjc();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final /* bridge */ /* synthetic */ zzeb zzjd() {
        return super.zzjd();
    }

    @Override // com.google.android.gms.internal.measurement.zzjq
    public final /* bridge */ /* synthetic */ zzej zzje() {
        return super.zzje();
    }
}
