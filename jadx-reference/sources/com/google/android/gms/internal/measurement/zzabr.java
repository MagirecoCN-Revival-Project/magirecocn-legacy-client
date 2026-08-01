package com.google.android.gms.internal.measurement;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'zzbwb' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* loaded from: classes.dex */
public class zzabr {
    public static final zzabr zzbvt;
    public static final zzabr zzbvu;
    public static final zzabr zzbvv;
    public static final zzabr zzbvw;
    public static final zzabr zzbvx;
    public static final zzabr zzbvy;
    public static final zzabr zzbvz;
    public static final zzabr zzbwa;
    public static final zzabr zzbwb;
    public static final zzabr zzbwc;
    public static final zzabr zzbwd;
    public static final zzabr zzbwe;
    public static final zzabr zzbwf;
    public static final zzabr zzbwg;
    public static final zzabr zzbwh;
    public static final zzabr zzbwi;
    public static final zzabr zzbwj;
    public static final zzabr zzbwk;
    private static final /* synthetic */ zzabr[] zzbwn;
    private final zzabw zzbwl;
    private final int zzbwm;

    static {
        zzabr zzabrVar = new zzabr("DOUBLE", 0, zzabw.DOUBLE, 1);
        zzbvt = zzabrVar;
        zzabr zzabrVar2 = new zzabr("FLOAT", 1, zzabw.FLOAT, 5);
        zzbvu = zzabrVar2;
        final int i = 2;
        zzabr zzabrVar3 = new zzabr("INT64", 2, zzabw.LONG, 0);
        zzbvv = zzabrVar3;
        final int i2 = 3;
        zzabr zzabrVar4 = new zzabr("UINT64", 3, zzabw.LONG, 0);
        zzbvw = zzabrVar4;
        zzabr zzabrVar5 = new zzabr("INT32", 4, zzabw.INT, 0);
        zzbvx = zzabrVar5;
        zzabr zzabrVar6 = new zzabr("FIXED64", 5, zzabw.LONG, 1);
        zzbvy = zzabrVar6;
        zzabr zzabrVar7 = new zzabr("FIXED32", 6, zzabw.INT, 5);
        zzbvz = zzabrVar7;
        zzabr zzabrVar8 = new zzabr("BOOL", 7, zzabw.BOOLEAN, 0);
        zzbwa = zzabrVar8;
        final zzabw zzabwVar = zzabw.STRING;
        final String str = "STRING";
        final int i3 = 8;
        zzabr zzabrVar9 = new zzabr(str, i3, zzabwVar, i) { // from class: com.google.android.gms.internal.measurement.zzabs
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                int i4 = 8;
                int i5 = 2;
                zzabq zzabqVar = null;
            }
        };
        zzbwb = zzabrVar9;
        final zzabw zzabwVar2 = zzabw.MESSAGE;
        final String str2 = "GROUP";
        final int i4 = 9;
        zzabr zzabrVar10 = new zzabr(str2, i4, zzabwVar2, i2) { // from class: com.google.android.gms.internal.measurement.zzabt
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                int i5 = 9;
                int i6 = 3;
                zzabq zzabqVar = null;
            }
        };
        zzbwc = zzabrVar10;
        final zzabw zzabwVar3 = zzabw.MESSAGE;
        final String str3 = "MESSAGE";
        final int i5 = 10;
        zzabr zzabrVar11 = new zzabr(str3, i5, zzabwVar3, i) { // from class: com.google.android.gms.internal.measurement.zzabu
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                int i6 = 10;
                int i7 = 2;
                zzabq zzabqVar = null;
            }
        };
        zzbwd = zzabrVar11;
        final zzabw zzabwVar4 = zzabw.BYTE_STRING;
        final String str4 = "BYTES";
        final int i6 = 11;
        zzabr zzabrVar12 = new zzabr(str4, i6, zzabwVar4, i) { // from class: com.google.android.gms.internal.measurement.zzabv
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                int i7 = 11;
                int i8 = 2;
                zzabq zzabqVar = null;
            }
        };
        zzbwe = zzabrVar12;
        zzabr zzabrVar13 = new zzabr("UINT32", 12, zzabw.INT, 0);
        zzbwf = zzabrVar13;
        zzabr zzabrVar14 = new zzabr("ENUM", 13, zzabw.ENUM, 0);
        zzbwg = zzabrVar14;
        zzabr zzabrVar15 = new zzabr("SFIXED32", 14, zzabw.INT, 5);
        zzbwh = zzabrVar15;
        zzabr zzabrVar16 = new zzabr("SFIXED64", 15, zzabw.LONG, 1);
        zzbwi = zzabrVar16;
        zzabr zzabrVar17 = new zzabr("SINT32", 16, zzabw.INT, 0);
        zzbwj = zzabrVar17;
        zzabr zzabrVar18 = new zzabr("SINT64", 17, zzabw.LONG, 0);
        zzbwk = zzabrVar18;
        zzbwn = new zzabr[]{zzabrVar, zzabrVar2, zzabrVar3, zzabrVar4, zzabrVar5, zzabrVar6, zzabrVar7, zzabrVar8, zzabrVar9, zzabrVar10, zzabrVar11, zzabrVar12, zzabrVar13, zzabrVar14, zzabrVar15, zzabrVar16, zzabrVar17, zzabrVar18};
    }

    private zzabr(String str, int i, zzabw zzabwVar, int i2) {
        this.zzbwl = zzabwVar;
        this.zzbwm = i2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public /* synthetic */ zzabr(String str, int i, zzabw zzabwVar, int i2, zzabq zzabqVar) {
        this(str, i, zzabwVar, i2);
    }

    public static zzabr[] values() {
        return (zzabr[]) zzbwn.clone();
    }

    public final zzabw zzve() {
        return this.zzbwl;
    }
}
