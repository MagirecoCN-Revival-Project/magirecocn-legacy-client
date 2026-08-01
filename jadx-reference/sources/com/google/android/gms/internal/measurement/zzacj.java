package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzacj {
    private static final int zzbxs = 11;
    private static final int zzbxt = 12;
    private static final int zzbxu = 16;
    private static final int zzbxv = 26;
    public static final int[] zzbts = new int[0];
    public static final long[] zzbxw = new long[0];
    private static final float[] zzbxx = new float[0];
    private static final double[] zzbxy = new double[0];
    private static final boolean[] zzbxz = new boolean[0];
    public static final String[] zzbya = new String[0];
    private static final byte[][] zzbyb = new byte[0];
    public static final byte[] zzbyc = new byte[0];

    public static final int zzb(zzabx zzabxVar, int i) throws IOException {
        int position = zzabxVar.getPosition();
        zzabxVar.zzak(i);
        int i2 = 1;
        while (zzabxVar.zzvf() == i) {
            zzabxVar.zzak(i);
            i2++;
        }
        zzabxVar.zzd(position, i);
        return i2;
    }
}
