package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* loaded from: classes.dex */
public final class zzkt extends zzaca<zzkt> {
    public long[] zzauw = zzacj.zzbxw;
    public long[] zzaux = zzacj.zzbxw;

    public zzkt() {
        this.zzbxg = null;
        this.zzbxr = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzkt)) {
            return false;
        }
        zzkt zzktVar = (zzkt) obj;
        if (zzace.equals(this.zzauw, zzktVar.zzauw) && zzace.equals(this.zzaux, zzktVar.zzaux)) {
            return (this.zzbxg == null || this.zzbxg.isEmpty()) ? zzktVar.zzbxg == null || zzktVar.zzbxg.isEmpty() : this.zzbxg.equals(zzktVar.zzbxg);
        }
        return false;
    }

    public final int hashCode() {
        return ((((((getClass().getName().hashCode() + 527) * 31) + zzace.hashCode(this.zzauw)) * 31) + zzace.hashCode(this.zzaux)) * 31) + ((this.zzbxg == null || this.zzbxg.isEmpty()) ? 0 : this.zzbxg.hashCode());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final int zza() {
        long[] jArr;
        int zza = super.zza();
        long[] jArr2 = this.zzauw;
        int i = 0;
        if (jArr2 != null && jArr2.length > 0) {
            int i2 = 0;
            int i3 = 0;
            while (true) {
                jArr = this.zzauw;
                if (i2 >= jArr.length) {
                    break;
                }
                i3 += zzaby.zzao(jArr[i2]);
                i2++;
            }
            zza = zza + i3 + (jArr.length * 1);
        }
        long[] jArr3 = this.zzaux;
        if (jArr3 == null || jArr3.length <= 0) {
            return zza;
        }
        int i4 = 0;
        while (true) {
            long[] jArr4 = this.zzaux;
            if (i >= jArr4.length) {
                return zza + i4 + (jArr4.length * 1);
            }
            i4 += zzaby.zzao(jArr4[i]);
            i++;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzaca, com.google.android.gms.internal.measurement.zzacg
    public final void zza(zzaby zzabyVar) throws IOException {
        long[] jArr = this.zzauw;
        int i = 0;
        if (jArr != null && jArr.length > 0) {
            int i2 = 0;
            while (true) {
                long[] jArr2 = this.zzauw;
                if (i2 >= jArr2.length) {
                    break;
                }
                zzabyVar.zza(1, jArr2[i2]);
                i2++;
            }
        }
        long[] jArr3 = this.zzaux;
        if (jArr3 != null && jArr3.length > 0) {
            while (true) {
                long[] jArr4 = this.zzaux;
                if (i >= jArr4.length) {
                    break;
                }
                zzabyVar.zza(2, jArr4[i]);
                i++;
            }
        }
        super.zza(zzabyVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzacg
    public final /* synthetic */ zzacg zzb(zzabx zzabxVar) throws IOException {
        int zzaf;
        while (true) {
            int zzvf = zzabxVar.zzvf();
            if (zzvf == 0) {
                return this;
            }
            if (zzvf != 8) {
                if (zzvf == 10) {
                    zzaf = zzabxVar.zzaf(zzabxVar.zzvh());
                    int position = zzabxVar.getPosition();
                    int i = 0;
                    while (zzabxVar.zzvl() > 0) {
                        zzabxVar.zzvi();
                        i++;
                    }
                    zzabxVar.zzam(position);
                    long[] jArr = this.zzauw;
                    int length = jArr == null ? 0 : jArr.length;
                    int i2 = i + length;
                    long[] jArr2 = new long[i2];
                    if (length != 0) {
                        System.arraycopy(jArr, 0, jArr2, 0, length);
                    }
                    while (length < i2) {
                        jArr2[length] = zzabxVar.zzvi();
                        length++;
                    }
                    this.zzauw = jArr2;
                } else if (zzvf == 16) {
                    int zzb = zzacj.zzb(zzabxVar, 16);
                    long[] jArr3 = this.zzaux;
                    int length2 = jArr3 == null ? 0 : jArr3.length;
                    int i3 = zzb + length2;
                    long[] jArr4 = new long[i3];
                    if (length2 != 0) {
                        System.arraycopy(jArr3, 0, jArr4, 0, length2);
                    }
                    while (length2 < i3 - 1) {
                        jArr4[length2] = zzabxVar.zzvi();
                        zzabxVar.zzvf();
                        length2++;
                    }
                    jArr4[length2] = zzabxVar.zzvi();
                    this.zzaux = jArr4;
                } else if (zzvf == 18) {
                    zzaf = zzabxVar.zzaf(zzabxVar.zzvh());
                    int position2 = zzabxVar.getPosition();
                    int i4 = 0;
                    while (zzabxVar.zzvl() > 0) {
                        zzabxVar.zzvi();
                        i4++;
                    }
                    zzabxVar.zzam(position2);
                    long[] jArr5 = this.zzaux;
                    int length3 = jArr5 == null ? 0 : jArr5.length;
                    int i5 = i4 + length3;
                    long[] jArr6 = new long[i5];
                    if (length3 != 0) {
                        System.arraycopy(jArr5, 0, jArr6, 0, length3);
                    }
                    while (length3 < i5) {
                        jArr6[length3] = zzabxVar.zzvi();
                        length3++;
                    }
                    this.zzaux = jArr6;
                } else if (!super.zza(zzabxVar, zzvf)) {
                    return this;
                }
                zzabxVar.zzal(zzaf);
            } else {
                int zzb2 = zzacj.zzb(zzabxVar, 8);
                long[] jArr7 = this.zzauw;
                int length4 = jArr7 == null ? 0 : jArr7.length;
                int i6 = zzb2 + length4;
                long[] jArr8 = new long[i6];
                if (length4 != 0) {
                    System.arraycopy(jArr7, 0, jArr8, 0, length4);
                }
                while (length4 < i6 - 1) {
                    jArr8[length4] = zzabxVar.zzvi();
                    zzabxVar.zzvf();
                    length4++;
                }
                jArr8[length4] = zzabxVar.zzvi();
                this.zzauw = jArr8;
            }
        }
    }
}
