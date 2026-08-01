package com.google.android.gms.internal.measurement;

import android.util.Log;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzxa extends zzwu<Boolean> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public zzxa(zzxe zzxeVar, String str, Boolean bool) {
        super(zzxeVar, str, bool, null);
    }

    /* JADX DEBUG: Return type fixed from 'java.lang.Object' to match base method */
    @Override // com.google.android.gms.internal.measurement.zzwu
    protected final /* synthetic */ Boolean zzex(String str) {
        if (zzwp.zzbmu.matcher(str).matches()) {
            return true;
        }
        if (zzwp.zzbmv.matcher(str).matches()) {
            return false;
        }
        String str2 = this.zzbns;
        StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 28 + String.valueOf(str).length());
        sb.append("Invalid boolean value for ");
        sb.append(str2);
        sb.append(": ");
        sb.append(str);
        Log.e("PhenotypeFlag", sb.toString());
        return null;
    }
}
