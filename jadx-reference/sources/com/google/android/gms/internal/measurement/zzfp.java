package com.google.android.gms.internal.measurement;

import com.google.android.gms.common.internal.Preconditions;
import java.net.URL;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzfp implements Runnable {
    private final String packageName;
    private final URL url;
    private final byte[] zzaju;
    private final zzfn zzajv;
    private final Map<String, String> zzajw;
    private final /* synthetic */ zzfl zzajx;

    public zzfp(zzfl zzflVar, String str, URL url, byte[] bArr, Map<String, String> map, zzfn zzfnVar) {
        this.zzajx = zzflVar;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(url);
        Preconditions.checkNotNull(zzfnVar);
        this.url = url;
        this.zzaju = bArr;
        this.zzajv = zzfnVar;
        this.packageName = str;
        this.zzajw = map;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: ProcessVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Method arg registers not loaded: com.google.android.gms.internal.measurement.zzfo.<init>(java.lang.String, com.google.android.gms.internal.measurement.zzfn, int, java.lang.Throwable, byte[], java.util.Map, com.google.android.gms.internal.measurement.zzfm):void, class status: GENERATED_AND_UNLOADED
        	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:289)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.isArgUnused(ProcessVariables.java:146)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.lambda$isVarUnused$0(ProcessVariables.java:131)
        	at jadx.core.utils.ListUtils.allMatch(ListUtils.java:172)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.isVarUnused(ProcessVariables.java:131)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.processBlock(ProcessVariables.java:82)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:64)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:19)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables.removeUnusedResults(ProcessVariables.java:73)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables.visit(ProcessVariables.java:48)
        */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x010a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00cf A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        /*
            r13 = this;
            java.lang.String r0 = "Error closing HTTP compressed POST connection output stream. appId"
            com.google.android.gms.internal.measurement.zzfl r1 = r13.zzajx
            r1.zzft()
            r1 = 0
            r2 = 0
            com.google.android.gms.internal.measurement.zzfl r3 = r13.zzajx     // Catch: java.lang.Throwable -> Lc8 java.io.IOException -> L103
            java.net.URL r4 = r13.url     // Catch: java.lang.Throwable -> Lc8 java.io.IOException -> L103
            java.net.HttpURLConnection r3 = r3.zzb(r4)     // Catch: java.lang.Throwable -> Lc8 java.io.IOException -> L103
            java.util.Map<java.lang.String, java.lang.String> r4 = r13.zzajw     // Catch: java.lang.Throwable -> Lc2 java.io.IOException -> Lc5
            if (r4 == 0) goto L39
            java.util.Set r4 = r4.entrySet()     // Catch: java.lang.Throwable -> Lc2 java.io.IOException -> Lc5
            java.util.Iterator r4 = r4.iterator()     // Catch: java.lang.Throwable -> Lc2 java.io.IOException -> Lc5
        L1d:
            boolean r5 = r4.hasNext()     // Catch: java.lang.Throwable -> Lc2 java.io.IOException -> Lc5
            if (r5 == 0) goto L39
            java.lang.Object r5 = r4.next()     // Catch: java.lang.Throwable -> Lc2 java.io.IOException -> Lc5
            java.util.Map$Entry r5 = (java.util.Map.Entry) r5     // Catch: java.lang.Throwable -> Lc2 java.io.IOException -> Lc5
            java.lang.Object r6 = r5.getKey()     // Catch: java.lang.Throwable -> Lc2 java.io.IOException -> Lc5
            java.lang.String r6 = (java.lang.String) r6     // Catch: java.lang.Throwable -> Lc2 java.io.IOException -> Lc5
            java.lang.Object r5 = r5.getValue()     // Catch: java.lang.Throwable -> Lc2 java.io.IOException -> Lc5
            java.lang.String r5 = (java.lang.String) r5     // Catch: java.lang.Throwable -> Lc2 java.io.IOException -> Lc5
            r3.addRequestProperty(r6, r5)     // Catch: java.lang.Throwable -> Lc2 java.io.IOException -> Lc5
            goto L1d
        L39:
            byte[] r4 = r13.zzaju     // Catch: java.lang.Throwable -> Lc2 java.io.IOException -> Lc5
            if (r4 == 0) goto L86
            com.google.android.gms.internal.measurement.zzfl r4 = r13.zzajx     // Catch: java.lang.Throwable -> Lc2 java.io.IOException -> Lc5
            com.google.android.gms.internal.measurement.zzkc r4 = r4.zzgc()     // Catch: java.lang.Throwable -> Lc2 java.io.IOException -> Lc5
            byte[] r5 = r13.zzaju     // Catch: java.lang.Throwable -> Lc2 java.io.IOException -> Lc5
            byte[] r4 = r4.zza(r5)     // Catch: java.lang.Throwable -> Lc2 java.io.IOException -> Lc5
            com.google.android.gms.internal.measurement.zzfl r5 = r13.zzajx     // Catch: java.lang.Throwable -> Lc2 java.io.IOException -> Lc5
            com.google.android.gms.internal.measurement.zzfh r5 = r5.zzgf()     // Catch: java.lang.Throwable -> Lc2 java.io.IOException -> Lc5
            com.google.android.gms.internal.measurement.zzfj r5 = r5.zziz()     // Catch: java.lang.Throwable -> Lc2 java.io.IOException -> Lc5
            java.lang.String r6 = "Uploading data. size"
            int r7 = r4.length     // Catch: java.lang.Throwable -> Lc2 java.io.IOException -> Lc5
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch: java.lang.Throwable -> Lc2 java.io.IOException -> Lc5
            r5.zzg(r6, r7)     // Catch: java.lang.Throwable -> Lc2 java.io.IOException -> Lc5
            r5 = 1
            r3.setDoOutput(r5)     // Catch: java.lang.Throwable -> Lc2 java.io.IOException -> Lc5
            java.lang.String r5 = "Content-Encoding"
            java.lang.String r6 = "gzip"
            r3.addRequestProperty(r5, r6)     // Catch: java.lang.Throwable -> Lc2 java.io.IOException -> Lc5
            int r5 = r4.length     // Catch: java.lang.Throwable -> Lc2 java.io.IOException -> Lc5
            r3.setFixedLengthStreamingMode(r5)     // Catch: java.lang.Throwable -> Lc2 java.io.IOException -> Lc5
            r3.connect()     // Catch: java.lang.Throwable -> Lc2 java.io.IOException -> Lc5
            java.io.OutputStream r5 = r3.getOutputStream()     // Catch: java.lang.Throwable -> Lc2 java.io.IOException -> Lc5
            r5.write(r4)     // Catch: java.lang.Throwable -> L7a java.io.IOException -> L80
            r5.close()     // Catch: java.lang.Throwable -> L7a java.io.IOException -> L80
            goto L86
        L7a:
            r4 = move-exception
            r10 = r1
            r2 = r4
            r1 = r5
            goto Lcc
        L80:
            r4 = move-exception
            r10 = r1
            r8 = r4
            r1 = r5
            goto L107
        L86:
            int r8 = r3.getResponseCode()     // Catch: java.lang.Throwable -> Lc2 java.io.IOException -> Lc5
            java.util.Map r11 = r3.getHeaderFields()     // Catch: java.lang.Throwable -> Lb8 java.io.IOException -> Lbd
            com.google.android.gms.internal.measurement.zzfl r2 = r13.zzajx     // Catch: java.lang.Throwable -> Laf java.io.IOException -> Lb4
            byte[] r10 = com.google.android.gms.internal.measurement.zzfl.zza(r2, r3)     // Catch: java.lang.Throwable -> Laf java.io.IOException -> Lb4
            if (r3 == 0) goto L99
            r3.disconnect()
        L99:
            com.google.android.gms.internal.measurement.zzfl r0 = r13.zzajx
            com.google.android.gms.internal.measurement.zzgh r0 = r0.zzge()
            com.google.android.gms.internal.measurement.zzfo r1 = new com.google.android.gms.internal.measurement.zzfo
            java.lang.String r6 = r13.packageName
            com.google.android.gms.internal.measurement.zzfn r7 = r13.zzajv
            r9 = 0
            r12 = 0
            r5 = r1
            r5.<init>(r6, r7, r8, r9, r10, r11)
        Lab:
            r0.zzc(r1)
            return
        Laf:
            r4 = move-exception
            r2 = r4
            r7 = r8
            r10 = r11
            goto Lcd
        Lb4:
            r4 = move-exception
            r7 = r8
            r10 = r11
            goto Lc0
        Lb8:
            r4 = move-exception
            r10 = r1
            r2 = r4
            r7 = r8
            goto Lcd
        Lbd:
            r4 = move-exception
            r10 = r1
            r7 = r8
        Lc0:
            r8 = r4
            goto L108
        Lc2:
            r4 = move-exception
            r10 = r1
            goto Lcb
        Lc5:
            r4 = move-exception
            r10 = r1
            goto L106
        Lc8:
            r4 = move-exception
            r3 = r1
            r10 = r3
        Lcb:
            r2 = r4
        Lcc:
            r7 = 0
        Lcd:
            if (r1 == 0) goto Le7
            r1.close()     // Catch: java.io.IOException -> Ld3
            goto Le7
        Ld3:
            r1 = move-exception
            com.google.android.gms.internal.measurement.zzfl r4 = r13.zzajx
            com.google.android.gms.internal.measurement.zzfh r4 = r4.zzgf()
            com.google.android.gms.internal.measurement.zzfj r4 = r4.zzis()
            java.lang.String r5 = r13.packageName
            java.lang.Object r5 = com.google.android.gms.internal.measurement.zzfh.zzbl(r5)
            r4.zze(r0, r5, r1)
        Le7:
            if (r3 == 0) goto Lec
            r3.disconnect()
        Lec:
            com.google.android.gms.internal.measurement.zzfl r0 = r13.zzajx
            com.google.android.gms.internal.measurement.zzgh r0 = r0.zzge()
            com.google.android.gms.internal.measurement.zzfo r1 = new com.google.android.gms.internal.measurement.zzfo
            java.lang.String r5 = r13.packageName
            com.google.android.gms.internal.measurement.zzfn r6 = r13.zzajv
            r8 = 0
            r9 = 0
            r11 = 0
            r4 = r1
            r4.<init>(r5, r6, r7, r8, r9, r10)
            r0.zzc(r1)
            throw r2
        L103:
            r4 = move-exception
            r3 = r1
            r10 = r3
        L106:
            r8 = r4
        L107:
            r7 = 0
        L108:
            if (r1 == 0) goto L122
            r1.close()     // Catch: java.io.IOException -> L10e
            goto L122
        L10e:
            r1 = move-exception
            com.google.android.gms.internal.measurement.zzfl r2 = r13.zzajx
            com.google.android.gms.internal.measurement.zzfh r2 = r2.zzgf()
            com.google.android.gms.internal.measurement.zzfj r2 = r2.zzis()
            java.lang.String r4 = r13.packageName
            java.lang.Object r4 = com.google.android.gms.internal.measurement.zzfh.zzbl(r4)
            r2.zze(r0, r4, r1)
        L122:
            if (r3 == 0) goto L127
            r3.disconnect()
        L127:
            com.google.android.gms.internal.measurement.zzfl r0 = r13.zzajx
            com.google.android.gms.internal.measurement.zzgh r0 = r0.zzge()
            com.google.android.gms.internal.measurement.zzfo r1 = new com.google.android.gms.internal.measurement.zzfo
            java.lang.String r5 = r13.packageName
            com.google.android.gms.internal.measurement.zzfn r6 = r13.zzajv
            r9 = 0
            r11 = 0
            r4 = r1
            r4.<init>(r5, r6, r7, r8, r9, r10)
            goto Lab
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzfp.run():void");
    }
}
