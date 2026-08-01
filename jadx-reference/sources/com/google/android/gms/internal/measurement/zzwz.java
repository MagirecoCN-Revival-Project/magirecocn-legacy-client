package com.google.android.gms.internal.measurement;

import android.util.Log;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzwz extends zzwu<Integer> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public zzwz(zzxe zzxeVar, String str, Integer num) {
        super(zzxeVar, str, num, null);
    }

    /* JADX DEBUG: Method merged with bridge method: zzex(Ljava/lang/String;)Ljava/lang/Object; */
    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.measurement.zzwu
    /* renamed from: zzez, reason: merged with bridge method [inline-methods] */
    public final Integer zzex(String str) {
        try {
            return Integer.valueOf(Integer.parseInt(str));
        } catch (NumberFormatException unused) {
            String str2 = this.zzbns;
            StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 28 + String.valueOf(str).length());
            sb.append("Invalid integer value for ");
            sb.append(str2);
            sb.append(": ");
            sb.append(str);
            Log.e("PhenotypeFlag", sb.toString());
            return null;
        }
    }
}
