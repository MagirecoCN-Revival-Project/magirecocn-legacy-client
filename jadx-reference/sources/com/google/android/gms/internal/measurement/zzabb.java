package com.google.android.gms.internal.measurement;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: Add missing generic type declarations: [FieldDescriptorType] */
/* loaded from: classes.dex */
public final class zzabb<FieldDescriptorType> extends zzaba<FieldDescriptorType, Object> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public zzabb(int i) {
        super(i, null);
    }

    @Override // com.google.android.gms.internal.measurement.zzaba
    public final void zzrp() {
        if (!isImmutable()) {
            for (int i = 0; i < zzus(); i++) {
                Map.Entry<FieldDescriptorType, Object> zzah = zzah(i);
                if (((zzzq) zzah.getKey()).zztt()) {
                    zzah.setValue(Collections.unmodifiableList((List) zzah.getValue()));
                }
            }
            for (Map.Entry<FieldDescriptorType, Object> entry : zzut()) {
                if (((zzzq) entry.getKey()).zztt()) {
                    entry.setValue(Collections.unmodifiableList((List) entry.getValue()));
                }
            }
        }
        super.zzrp();
    }
}
