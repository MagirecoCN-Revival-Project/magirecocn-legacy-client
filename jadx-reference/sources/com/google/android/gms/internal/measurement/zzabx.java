package com.google.android.gms.internal.measurement;

import java.io.IOException;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;

/* loaded from: classes.dex */
public final class zzabx {
    private final byte[] buffer;
    private int zzbrs;
    private final int zzbwz;
    private final int zzbxa;
    private int zzbxb;
    private int zzbxc;
    private int zzbxd;
    private int zzbxe;
    private int zzbru = Integer.MAX_VALUE;
    private int zzbrn = 64;
    private int zzbro = 67108864;

    private zzabx(byte[] bArr, int i, int i2) {
        this.buffer = bArr;
        this.zzbwz = i;
        int i3 = i2 + i;
        this.zzbxb = i3;
        this.zzbxa = i3;
        this.zzbxc = i;
    }

    public static zzabx zza(byte[] bArr, int i, int i2) {
        return new zzabx(bArr, 0, i2);
    }

    private final void zzan(int i) throws IOException {
        if (i < 0) {
            throw zzacf.zzvr();
        }
        int i2 = this.zzbxc;
        int i3 = i2 + i;
        int i4 = this.zzbru;
        if (i3 > i4) {
            zzan(i4 - i2);
            throw zzacf.zzvq();
        }
        if (i > this.zzbxb - i2) {
            throw zzacf.zzvq();
        }
        this.zzbxc = i2 + i;
    }

    public static zzabx zzi(byte[] bArr) {
        return zza(bArr, 0, bArr.length);
    }

    private final void zztj() {
        int i = this.zzbxb + this.zzbrs;
        this.zzbxb = i;
        int i2 = this.zzbru;
        if (i <= i2) {
            this.zzbrs = 0;
            return;
        }
        int i3 = i - i2;
        this.zzbrs = i3;
        this.zzbxb = i - i3;
    }

    private final byte zzvm() throws IOException {
        int i = this.zzbxc;
        if (i == this.zzbxb) {
            throw zzacf.zzvq();
        }
        byte[] bArr = this.buffer;
        this.zzbxc = i + 1;
        return bArr[i];
    }

    public final int getPosition() {
        return this.zzbxc - this.zzbwz;
    }

    public final String readString() throws IOException {
        int zzvh = zzvh();
        if (zzvh < 0) {
            throw zzacf.zzvr();
        }
        int i = this.zzbxb;
        int i2 = this.zzbxc;
        if (zzvh > i - i2) {
            throw zzacf.zzvq();
        }
        String str = new String(this.buffer, i2, zzvh, zzace.UTF_8);
        this.zzbxc += zzvh;
        return str;
    }

    public final void zza(zzacg zzacgVar) throws IOException {
        int zzvh = zzvh();
        if (this.zzbxe >= this.zzbrn) {
            throw zzacf.zzvt();
        }
        int zzaf = zzaf(zzvh);
        this.zzbxe++;
        zzacgVar.zzb(this);
        zzaj(0);
        this.zzbxe--;
        zzal(zzaf);
    }

    public final void zza(zzacg zzacgVar, int i) throws IOException {
        int i2 = this.zzbxe;
        if (i2 >= this.zzbrn) {
            throw zzacf.zzvt();
        }
        this.zzbxe = i2 + 1;
        zzacgVar.zzb(this);
        zzaj((i << 3) | 4);
        this.zzbxe--;
    }

    public final int zzaf(int i) throws zzacf {
        if (i < 0) {
            throw zzacf.zzvr();
        }
        int i2 = i + this.zzbxc;
        int i3 = this.zzbru;
        if (i2 > i3) {
            throw zzacf.zzvq();
        }
        this.zzbru = i2;
        zztj();
        return i3;
    }

    public final void zzaj(int i) throws zzacf {
        if (this.zzbxd != i) {
            throw new zzacf("Protocol message end-group tag did not match expected tag.");
        }
    }

    public final boolean zzak(int i) throws IOException {
        int zzvf;
        int i2 = i & 7;
        if (i2 == 0) {
            zzvh();
            return true;
        }
        if (i2 == 1) {
            zzvk();
            return true;
        }
        if (i2 == 2) {
            zzan(zzvh());
            return true;
        }
        if (i2 != 3) {
            if (i2 == 4) {
                return false;
            }
            if (i2 != 5) {
                throw new zzacf("Protocol message tag had invalid wire type.");
            }
            zzvj();
            return true;
        }
        do {
            zzvf = zzvf();
            if (zzvf == 0) {
                break;
            }
        } while (zzak(zzvf));
        zzaj(((i >>> 3) << 3) | 4);
        return true;
    }

    public final void zzal(int i) {
        this.zzbru = i;
        zztj();
    }

    public final void zzam(int i) {
        zzd(i, this.zzbxd);
    }

    public final byte[] zzc(int i, int i2) {
        if (i2 == 0) {
            return zzacj.zzbyc;
        }
        byte[] bArr = new byte[i2];
        System.arraycopy(this.buffer, this.zzbwz + i, bArr, 0, i2);
        return bArr;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void zzd(int i, int i2) {
        int i3 = this.zzbxc;
        int i4 = this.zzbwz;
        if (i > i3 - i4) {
            int i5 = this.zzbxc - this.zzbwz;
            StringBuilder sb = new StringBuilder(50);
            sb.append("Position ");
            sb.append(i);
            sb.append(" is beyond current ");
            sb.append(i5);
            throw new IllegalArgumentException(sb.toString());
        }
        if (i >= 0) {
            this.zzbxc = i4 + i;
            this.zzbxd = i2;
        } else {
            StringBuilder sb2 = new StringBuilder(24);
            sb2.append("Bad position ");
            sb2.append(i);
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    public final int zzvf() throws IOException {
        if (this.zzbxc == this.zzbxb) {
            this.zzbxd = 0;
            return 0;
        }
        int zzvh = zzvh();
        this.zzbxd = zzvh;
        if (zzvh != 0) {
            return zzvh;
        }
        throw new zzacf("Protocol message contained an invalid tag (zero).");
    }

    public final boolean zzvg() throws IOException {
        return zzvh() != 0;
    }

    public final int zzvh() throws IOException {
        int i;
        byte zzvm = zzvm();
        if (zzvm >= 0) {
            return zzvm;
        }
        int i2 = zzvm & ByteCompanionObject.MAX_VALUE;
        byte zzvm2 = zzvm();
        if (zzvm2 >= 0) {
            i = zzvm2 << 7;
        } else {
            i2 |= (zzvm2 & ByteCompanionObject.MAX_VALUE) << 7;
            byte zzvm3 = zzvm();
            if (zzvm3 >= 0) {
                i = zzvm3 << 14;
            } else {
                i2 |= (zzvm3 & ByteCompanionObject.MAX_VALUE) << 14;
                byte zzvm4 = zzvm();
                if (zzvm4 < 0) {
                    int i3 = i2 | ((zzvm4 & ByteCompanionObject.MAX_VALUE) << 21);
                    byte zzvm5 = zzvm();
                    int i4 = i3 | (zzvm5 << 28);
                    if (zzvm5 >= 0) {
                        return i4;
                    }
                    for (int i5 = 0; i5 < 5; i5++) {
                        if (zzvm() >= 0) {
                            return i4;
                        }
                    }
                    throw zzacf.zzvs();
                }
                i = zzvm4 << 21;
            }
        }
        return i2 | i;
    }

    public final long zzvi() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            j |= (r3 & ByteCompanionObject.MAX_VALUE) << i;
            if ((zzvm() & ByteCompanionObject.MIN_VALUE) == 0) {
                return j;
            }
        }
        throw zzacf.zzvs();
    }

    public final int zzvj() throws IOException {
        return (zzvm() & UByte.MAX_VALUE) | ((zzvm() & UByte.MAX_VALUE) << 8) | ((zzvm() & UByte.MAX_VALUE) << 16) | ((zzvm() & UByte.MAX_VALUE) << 24);
    }

    public final long zzvk() throws IOException {
        return ((zzvm() & 255) << 8) | (zzvm() & 255) | ((zzvm() & 255) << 16) | ((zzvm() & 255) << 24) | ((zzvm() & 255) << 32) | ((zzvm() & 255) << 40) | ((zzvm() & 255) << 48) | ((zzvm() & 255) << 56);
    }

    public final int zzvl() {
        int i = this.zzbru;
        if (i == Integer.MAX_VALUE) {
            return -1;
        }
        return i - this.zzbxc;
    }
}
