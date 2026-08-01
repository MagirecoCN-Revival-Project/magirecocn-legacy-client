package com.google.android.gms.internal.measurement;

import android.util.Log;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzxb extends zzwu<Double> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public zzxb(zzxe zzxeVar, String str, Double d) {
        super(zzxeVar, str, d, null);
    }

    /* JADX DEBUG: Method merged with bridge method: zzex(Ljava/lang/String;)Ljava/lang/Object; */
    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.measurement.zzwu
    /* renamed from: zzfa, reason: merged with bridge method [inline-methods] */
    public final Double zzex(String str) {
        try {
            return Double.valueOf(Double.parseDouble(str));
        } catch (NumberFormatException unused) {
            String str2 = this.zzbns;
            StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 27 + String.valueOf(str).length());
            sb.append("Invalid double value for ");
            sb.append(str2);
            sb.append(": ");
            sb.append(str);
            Log.e("PhenotypeFlag", sb.toString());
            return null;
        }
    }
}
