package com.google.android.gms.internal.measurement;

import android.util.Log;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzwy extends zzwu<Long> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public zzwy(zzxe zzxeVar, String str, Long l) {
        super(zzxeVar, str, l, null);
    }

    /* JADX DEBUG: Method merged with bridge method: zzex(Ljava/lang/String;)Ljava/lang/Object; */
    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.measurement.zzwu
    /* renamed from: zzey, reason: merged with bridge method [inline-methods] */
    public final Long zzex(String str) {
        try {
            return Long.valueOf(Long.parseLong(str));
        } catch (NumberFormatException unused) {
            String str2 = this.zzbns;
            StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 25 + String.valueOf(str).length());
            sb.append("Invalid long value for ");
            sb.append(str2);
            sb.append(": ");
            sb.append(str);
            Log.e("PhenotypeFlag", sb.toString());
            return null;
        }
    }
}
