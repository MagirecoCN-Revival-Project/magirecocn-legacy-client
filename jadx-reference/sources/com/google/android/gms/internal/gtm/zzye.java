package com.google.android.gms.internal.gtm;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public enum zzye {
    DOUBLE(zzyf.DOUBLE, 1),
    FLOAT(zzyf.FLOAT, 5),
    INT64(zzyf.LONG, 0),
    UINT64(zzyf.LONG, 0),
    INT32(zzyf.INT, 0),
    FIXED64(zzyf.LONG, 1),
    FIXED32(zzyf.INT, 5),
    BOOL(zzyf.BOOLEAN, 0),
    STRING(zzyf.STRING, 2),
    GROUP(zzyf.MESSAGE, 3),
    MESSAGE(zzyf.MESSAGE, 2),
    BYTES(zzyf.BYTE_STRING, 2),
    UINT32(zzyf.INT, 0),
    ENUM(zzyf.ENUM, 0),
    SFIXED32(zzyf.INT, 5),
    SFIXED64(zzyf.LONG, 1),
    SINT32(zzyf.INT, 0),
    SINT64(zzyf.LONG, 0);

    private final zzyf zzt;

    zzye(zzyf zzyfVar, int i) {
        this.zzt = zzyfVar;
    }

    public final zzyf zza() {
        return this.zzt;
    }
}
