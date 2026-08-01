package com.google.android.gms.internal.measurement;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.adjust.sdk.Constants;
import com.android.vending.expansion.zipfile.APEZProvider;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.CollectionUtils;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.security.auth.x500.X500Principal;
import kotlinx.coroutines.DebugKt;

/* loaded from: classes.dex */
public final class zzkc extends zzhi {
    private static final String[] zzarp = {"firebase_", "google_", "ga_"};
    private SecureRandom zzarq;
    private final AtomicLong zzarr;
    private int zzars;
    private Integer zzart;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzkc(zzgm zzgmVar) {
        super(zzgmVar);
        this.zzart = null;
        this.zzarr = new AtomicLong(0L);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static MessageDigest getMessageDigest(String str) {
        MessageDigest messageDigest;
        for (int i = 0; i < 2; i++) {
            try {
                messageDigest = MessageDigest.getInstance(str);
            } catch (NoSuchAlgorithmException unused) {
            }
            if (messageDigest != null) {
                return messageDigest;
            }
        }
        return null;
    }

    private static Object zza(int i, Object obj, boolean z) {
        if (obj == null) {
            return null;
        }
        if ((obj instanceof Long) || (obj instanceof Double)) {
            return obj;
        }
        if (obj instanceof Integer) {
            return Long.valueOf(((Integer) obj).intValue());
        }
        if (obj instanceof Byte) {
            return Long.valueOf(((Byte) obj).byteValue());
        }
        if (obj instanceof Short) {
            return Long.valueOf(((Short) obj).shortValue());
        }
        if (obj instanceof Boolean) {
            return Long.valueOf(((Boolean) obj).booleanValue() ? 1L : 0L);
        }
        if (obj instanceof Float) {
            return Double.valueOf(((Float) obj).doubleValue());
        }
        if ((obj instanceof String) || (obj instanceof Character) || (obj instanceof CharSequence)) {
            return zza(String.valueOf(obj), i, z);
        }
        return null;
    }

    public static String zza(String str, int i, boolean z) {
        if (str.codePointCount(0, str.length()) <= i) {
            return str;
        }
        if (z) {
            return String.valueOf(str.substring(0, str.offsetByCodePoints(0, i))).concat("...");
        }
        return null;
    }

    public static String zza(String str, String[] strArr, String[] strArr2) {
        Preconditions.checkNotNull(strArr);
        Preconditions.checkNotNull(strArr2);
        int min = Math.min(strArr.length, strArr2.length);
        for (int i = 0; i < min; i++) {
            if (zzs(str, strArr[i])) {
                return strArr2[i];
            }
        }
        return null;
    }

    private static void zza(Bundle bundle, Object obj) {
        Preconditions.checkNotNull(bundle);
        if (obj != null) {
            if ((obj instanceof String) || (obj instanceof CharSequence)) {
                bundle.putLong("_el", String.valueOf(obj).length());
            }
        }
    }

    public static boolean zza(Context context, boolean z) {
        Preconditions.checkNotNull(context);
        return zzc(context, Build.VERSION.SDK_INT >= 24 ? "com.google.android.gms.measurement.AppMeasurementJobService" : "com.google.android.gms.measurement.AppMeasurementService");
    }

    private static boolean zza(Bundle bundle, int i) {
        if (bundle.getLong("_err") != 0) {
            return false;
        }
        bundle.putLong("_err", i);
        return true;
    }

    private final boolean zza(String str, String str2, int i, Object obj, boolean z) {
        if (obj != null && !(obj instanceof Long) && !(obj instanceof Float) && !(obj instanceof Integer) && !(obj instanceof Byte) && !(obj instanceof Short) && !(obj instanceof Boolean) && !(obj instanceof Double)) {
            if (!(obj instanceof String) && !(obj instanceof Character) && !(obj instanceof CharSequence)) {
                if ((obj instanceof Bundle) && z) {
                    return true;
                }
                if ((obj instanceof Parcelable[]) && z) {
                    for (Parcelable parcelable : (Parcelable[]) obj) {
                        if (!(parcelable instanceof Bundle)) {
                            zzgf().zziv().zze("All Parcelable[] elements must be of type Bundle. Value type, name", parcelable.getClass(), str2);
                            return false;
                        }
                    }
                    return true;
                }
                if (!(obj instanceof ArrayList) || !z) {
                    return false;
                }
                ArrayList arrayList = (ArrayList) obj;
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj2 = arrayList.get(i2);
                    i2++;
                    if (!(obj2 instanceof Bundle)) {
                        zzgf().zziv().zze("All ArrayList elements must be of type Bundle. Value type, name", obj2.getClass(), str2);
                        return false;
                    }
                }
                return true;
            }
            String valueOf = String.valueOf(obj);
            if (valueOf.codePointCount(0, valueOf.length()) > i) {
                zzgf().zziv().zzd("Value is too long; discarded. Value kind, name, value length", str, str2, Integer.valueOf(valueOf.length()));
                return false;
            }
        }
        return true;
    }

    public static boolean zza(long[] jArr, int i) {
        if (i >= (jArr.length << 6)) {
            return false;
        }
        return ((1 << (i % 64)) & jArr[i / 64]) != 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static byte[] zza(Parcelable parcelable) {
        if (parcelable == null) {
            return null;
        }
        Parcel obtain = Parcel.obtain();
        try {
            parcelable.writeToParcel(obtain, 0);
            return obtain.marshall();
        } finally {
            obtain.recycle();
        }
    }

    public static long[] zza(BitSet bitSet) {
        int length = (bitSet.length() + 63) / 64;
        long[] jArr = new long[length];
        for (int i = 0; i < length; i++) {
            jArr[i] = 0;
            for (int i2 = 0; i2 < 64; i2++) {
                int i3 = (i << 6) + i2;
                if (i3 < bitSet.length()) {
                    if (bitSet.get(i3)) {
                        jArr[i] = jArr[i] | (1 << i2);
                    }
                }
            }
        }
        return jArr;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static long zzc(byte[] bArr) {
        Preconditions.checkNotNull(bArr);
        int i = 0;
        Preconditions.checkState(bArr.length > 0);
        long j = 0;
        for (int length = bArr.length - 1; length >= 0 && length >= bArr.length - 8; length--) {
            j += (bArr[length] & 255) << i;
            i += 8;
        }
        return j;
    }

    private static boolean zzc(Context context, String str) {
        ServiceInfo serviceInfo;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null && (serviceInfo = packageManager.getServiceInfo(new ComponentName(context, str), 0)) != null) {
                if (serviceInfo.enabled) {
                    return true;
                }
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean zzcb(String str) {
        Preconditions.checkNotEmpty(str);
        return str.charAt(0) != '_' || str.equals("_ep");
    }

    private static int zzcg(String str) {
        if ("_ldl".equals(str)) {
            return 2048;
        }
        return APEZProvider.FILEID.equals(str) ? 256 : 36;
    }

    public static boolean zzch(String str) {
        return !TextUtils.isEmpty(str) && str.startsWith("_");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean zzcj(String str) {
        return str != null && str.matches("(\\+|-)?([0-9]+\\.?[0-9]*|[0-9]*\\.?[0-9]+)") && str.length() <= 310;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean zzck(String str) {
        Preconditions.checkNotEmpty(str);
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case 94660:
                if (str.equals("_in")) {
                    c = 0;
                    break;
                }
                break;
            case 95025:
                if (str.equals("_ug")) {
                    c = 1;
                    break;
                }
                break;
            case 95027:
                if (str.equals("_ui")) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
                return true;
            default:
                return false;
        }
    }

    public static boolean zzd(Intent intent) {
        String stringExtra = intent.getStringExtra("android.intent.extra.REFERRER_NAME");
        return "android-app://com.google.android.googlequicksearchbox/https/www.google.com".equals(stringExtra) || "https://www.google.com".equals(stringExtra) || "android-app://com.google.appcrawler".equals(stringExtra);
    }

    private final boolean zze(Context context, String str) {
        zzfj zzis;
        String str2;
        X500Principal x500Principal = new X500Principal("CN=Android Debug,O=Android,C=US");
        try {
            PackageInfo packageInfo = Wrappers.packageManager(context).getPackageInfo(str, 64);
            if (packageInfo == null || packageInfo.signatures == null || packageInfo.signatures.length <= 0) {
                return true;
            }
            return ((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(packageInfo.signatures[0].toByteArray()))).getSubjectX500Principal().equals(x500Principal);
        } catch (PackageManager.NameNotFoundException e) {
            e = e;
            zzis = zzgf().zzis();
            str2 = "Package name not found";
            zzis.zzg(str2, e);
            return true;
        } catch (CertificateException e2) {
            e = e2;
            zzis = zzgf().zzis();
            str2 = "Error obtaining certificate";
            zzis.zzg(str2, e);
            return true;
        }
    }

    public static Bundle[] zze(Object obj) {
        Object[] array;
        if (obj instanceof Bundle) {
            return new Bundle[]{(Bundle) obj};
        }
        if (obj instanceof Parcelable[]) {
            Parcelable[] parcelableArr = (Parcelable[]) obj;
            array = Arrays.copyOf(parcelableArr, parcelableArr.length, Bundle[].class);
        } else {
            if (!(obj instanceof ArrayList)) {
                return null;
            }
            ArrayList arrayList = (ArrayList) obj;
            array = arrayList.toArray(new Bundle[arrayList.size()]);
        }
        return (Bundle[]) array;
    }

    public static Object zzf(Object obj) {
        ObjectInputStream objectInputStream;
        ObjectOutputStream objectOutputStream;
        try {
            if (obj == null) {
                return null;
            }
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                try {
                    objectOutputStream.writeObject(obj);
                    objectOutputStream.flush();
                    objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
                    try {
                        Object readObject = objectInputStream.readObject();
                        objectOutputStream.close();
                        objectInputStream.close();
                        return readObject;
                    } catch (Throwable th) {
                        th = th;
                        if (objectOutputStream != null) {
                            objectOutputStream.close();
                        }
                        if (objectInputStream != null) {
                            objectInputStream.close();
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    objectInputStream = null;
                }
            } catch (Throwable th3) {
                th = th3;
                objectInputStream = null;
                objectOutputStream = null;
            }
        } catch (IOException | ClassNotFoundException unused) {
            return null;
        }
    }

    private final boolean zzr(String str, String str2) {
        if (str2 == null) {
            zzgf().zzis().zzg("Name is required and can't be null. Type", str);
            return false;
        }
        if (str2.length() == 0) {
            zzgf().zzis().zzg("Name is required and can't be empty. Type", str);
            return false;
        }
        int codePointAt = str2.codePointAt(0);
        if (!Character.isLetter(codePointAt) && codePointAt != 95) {
            zzgf().zzis().zze("Name must start with a letter or _ (underscore). Type, name", str, str2);
            return false;
        }
        int length = str2.length();
        int charCount = Character.charCount(codePointAt);
        while (charCount < length) {
            int codePointAt2 = str2.codePointAt(charCount);
            if (codePointAt2 != 95 && !Character.isLetterOrDigit(codePointAt2)) {
                zzgf().zzis().zze("Name must consist of letters, digits or _ (underscores). Type, name", str, str2);
                return false;
            }
            charCount += Character.charCount(codePointAt2);
        }
        return true;
    }

    public static boolean zzs(String str, String str2) {
        if (str == null && str2 == null) {
            return true;
        }
        if (str == null) {
            return false;
        }
        return str.equals(str2);
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final Bundle zza(Uri uri) {
        String str;
        String str2;
        String str3;
        String str4;
        if (uri == null) {
            return null;
        }
        try {
            if (uri.isHierarchical()) {
                str = uri.getQueryParameter("utm_campaign");
                str2 = uri.getQueryParameter("utm_source");
                str3 = uri.getQueryParameter("utm_medium");
                str4 = uri.getQueryParameter("gclid");
            } else {
                str = null;
                str2 = null;
                str3 = null;
                str4 = null;
            }
            if (TextUtils.isEmpty(str) && TextUtils.isEmpty(str2) && TextUtils.isEmpty(str3) && TextUtils.isEmpty(str4)) {
                return null;
            }
            Bundle bundle = new Bundle();
            if (!TextUtils.isEmpty(str)) {
                bundle.putString(FirebaseAnalytics.Param.CAMPAIGN, str);
            }
            if (!TextUtils.isEmpty(str2)) {
                bundle.putString(FirebaseAnalytics.Param.SOURCE, str2);
            }
            if (!TextUtils.isEmpty(str3)) {
                bundle.putString("medium", str3);
            }
            if (!TextUtils.isEmpty(str4)) {
                bundle.putString("gclid", str4);
            }
            String queryParameter = uri.getQueryParameter("utm_term");
            if (!TextUtils.isEmpty(queryParameter)) {
                bundle.putString(FirebaseAnalytics.Param.TERM, queryParameter);
            }
            String queryParameter2 = uri.getQueryParameter("utm_content");
            if (!TextUtils.isEmpty(queryParameter2)) {
                bundle.putString(FirebaseAnalytics.Param.CONTENT, queryParameter2);
            }
            String queryParameter3 = uri.getQueryParameter(FirebaseAnalytics.Param.ACLID);
            if (!TextUtils.isEmpty(queryParameter3)) {
                bundle.putString(FirebaseAnalytics.Param.ACLID, queryParameter3);
            }
            String queryParameter4 = uri.getQueryParameter(FirebaseAnalytics.Param.CP1);
            if (!TextUtils.isEmpty(queryParameter4)) {
                bundle.putString(FirebaseAnalytics.Param.CP1, queryParameter4);
            }
            String queryParameter5 = uri.getQueryParameter("anid");
            if (!TextUtils.isEmpty(queryParameter5)) {
                bundle.putString("anid", queryParameter5);
            }
            return bundle;
        } catch (UnsupportedOperationException e) {
            zzgf().zziv().zzg("Install referrer url isn't a hierarchical URI", e);
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:79:0x004d, code lost:
    
        if (zza("event param", 40, r14) == false) goto L17;
     */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0053  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Bundle zza(String str, Bundle bundle, List<String> list, boolean z, boolean z2) {
        int i;
        String str2;
        boolean z3;
        int i2;
        String str3;
        zzkc zzkcVar;
        String str4;
        Object obj;
        int i3;
        int size;
        boolean z4;
        String[] strArr = null;
        if (bundle == null) {
            return null;
        }
        Bundle bundle2 = new Bundle(bundle);
        Iterator<String> it = bundle.keySet().iterator();
        int i4 = 0;
        while (it.hasNext()) {
            String next = it.next();
            if (list == null || !list.contains(next)) {
                int i5 = 14;
                if (z) {
                    if (zzq("event param", next)) {
                        if (!zza("event param", strArr, next)) {
                            i = 14;
                            if (i == 0) {
                                if (zzr("event param", next)) {
                                    if (zza("event param", strArr, next)) {
                                        if (zza("event param", 40, next)) {
                                            i5 = 0;
                                        }
                                    }
                                    i = i5;
                                }
                                i5 = 3;
                                i = i5;
                            }
                        }
                    }
                    i = 3;
                    if (i == 0) {
                    }
                }
                i = 0;
                if (i == 0) {
                }
            } else {
                i = 0;
            }
            if (i == 0) {
                Object obj2 = bundle.get(next);
                zzab();
                if (z2) {
                    if (obj2 instanceof Parcelable[]) {
                        size = ((Parcelable[]) obj2).length;
                    } else {
                        if (obj2 instanceof ArrayList) {
                            size = ((ArrayList) obj2).size();
                        }
                        z4 = true;
                        if (!z4) {
                            i3 = 17;
                            str2 = "_ev";
                            z3 = true;
                            if (i3 != 0 || str2.equals(next)) {
                                if (zzcb(next) || (i4 = i4 + 1) <= 25) {
                                    strArr = null;
                                } else {
                                    StringBuilder sb = new StringBuilder(48);
                                    sb.append("Event can't contain more than 25 params");
                                    zzgf().zzis().zze(sb.toString(), zzgb().zzbi(str), zzgb().zzb(bundle));
                                    zza(bundle2, 5);
                                }
                            } else if (zza(bundle2, i3)) {
                                bundle2.putString(str2, zza(next, 40, z3));
                                zza(bundle2, bundle.get(next));
                            }
                        }
                    }
                    if (size > 1000) {
                        zzgf().zziv().zzd("Parameter array is too long; discarded. Value kind, name, array length", "param", next, Integer.valueOf(size));
                        z4 = false;
                        if (!z4) {
                        }
                    }
                    z4 = true;
                    if (!z4) {
                    }
                }
                if ((zzgh().zzav(zzfw().zzah()) && zzch(str)) || zzch(next)) {
                    str3 = "param";
                    zzkcVar = this;
                    str4 = next;
                    i2 = 256;
                    z3 = true;
                    obj = obj2;
                    str2 = "_ev";
                } else {
                    str2 = "_ev";
                    z3 = true;
                    i2 = 100;
                    str3 = "param";
                    zzkcVar = this;
                    str4 = next;
                    obj = obj2;
                }
                i3 = zzkcVar.zza(str3, str4, i2, obj, z2) ? 0 : 4;
                if (i3 != 0) {
                }
                if (zzcb(next)) {
                }
                strArr = null;
            } else if (zza(bundle2, i)) {
                bundle2.putString("_ev", zza(next, 40, true));
                if (i == 3) {
                    zza(bundle2, next);
                }
            }
            bundle2.remove(next);
            strArr = null;
        }
        return bundle2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final <T extends Parcelable> T zza(byte[] bArr, Parcelable.Creator<T> creator) {
        if (bArr == null) {
            return null;
        }
        Parcel obtain = Parcel.obtain();
        try {
            obtain.unmarshall(bArr, 0, bArr.length);
            obtain.setDataPosition(0);
            return creator.createFromParcel(obtain);
        } catch (SafeParcelReader.ParseException unused) {
            zzgf().zzis().log("Failed to load parcelable from buffer");
            return null;
        } finally {
            obtain.recycle();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final zzew zza(String str, Bundle bundle, String str2, long j, boolean z, boolean z2) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (zzcc(str) != 0) {
            zzgf().zzis().zzg("Invalid conditional property event name", zzgb().zzbk(str));
            throw new IllegalArgumentException();
        }
        Bundle bundle2 = bundle != null ? new Bundle(bundle) : new Bundle();
        bundle2.putString("_o", str2);
        return new zzew(str, new zzet(zzd(zza(str, bundle2, CollectionUtils.listOf("_o"), false, false))), str2, j);
    }

    public final void zza(int i, String str, String str2, int i2) {
        zza((String) null, i, str, str2, i2);
    }

    public final void zza(Bundle bundle, String str, Object obj) {
        if (bundle == null) {
            return;
        }
        if (obj instanceof Long) {
            bundle.putLong(str, ((Long) obj).longValue());
            return;
        }
        if (obj instanceof String) {
            bundle.putString(str, String.valueOf(obj));
        } else if (obj instanceof Double) {
            bundle.putDouble(str, ((Double) obj).doubleValue());
        } else if (str != null) {
            zzgf().zziw().zze("Not putting event parameter. Invalid value type. name, type", zzgb().zzbj(str), obj != null ? obj.getClass().getSimpleName() : null);
        }
    }

    public final void zza(String str, int i, String str2, String str3, int i2) {
        Bundle bundle = new Bundle();
        zza(bundle, i);
        if (!TextUtils.isEmpty(str2)) {
            bundle.putString(str2, str3);
        }
        if (i == 6 || i == 7 || i == 2) {
            bundle.putLong("_el", i2);
        }
        this.zzacw.zzgi();
        this.zzacw.zzfv().logEvent(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_err", bundle);
    }

    public final boolean zza(long j, long j2) {
        return j == 0 || j2 <= 0 || Math.abs(zzbt().currentTimeMillis() - j) > j2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zza(String str, int i, String str2) {
        if (str2 == null) {
            zzgf().zzis().zzg("Name is required and can't be null. Type", str);
            return false;
        }
        if (str2.codePointCount(0, str2.length()) <= i) {
            return true;
        }
        zzgf().zzis().zzd("Name is too long. Type, maximum supported length, name", str, Integer.valueOf(i), str2);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zza(String str, String[] strArr, String str2) {
        boolean z;
        boolean z2;
        if (str2 == null) {
            zzgf().zzis().zzg("Name is required and can't be null. Type", str);
            return false;
        }
        Preconditions.checkNotNull(str2);
        int i = 0;
        while (true) {
            String[] strArr2 = zzarp;
            if (i >= strArr2.length) {
                z = false;
                break;
            }
            if (str2.startsWith(strArr2[i])) {
                z = true;
                break;
            }
            i++;
        }
        if (z) {
            zzgf().zzis().zze("Name starts with reserved prefix. Type, name", str, str2);
            return false;
        }
        if (strArr != null) {
            Preconditions.checkNotNull(strArr);
            int i2 = 0;
            while (true) {
                if (i2 >= strArr.length) {
                    z2 = false;
                    break;
                }
                if (zzs(str2, strArr[i2])) {
                    z2 = true;
                    break;
                }
                i2++;
            }
            if (z2) {
                zzgf().zzis().zze("Name is reserved. Type, name", str, str2);
                return false;
            }
        }
        return true;
    }

    public final byte[] zza(byte[] bArr) throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.close();
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            zzgf().zzis().zzg("Failed to gzip content", e);
            throw e;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    public final byte[] zzb(byte[] bArr) throws IOException {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            GZIPInputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr2 = new byte[1024];
            while (true) {
                int read = gZIPInputStream.read(bArr2);
                if (read <= 0) {
                    gZIPInputStream.close();
                    byteArrayInputStream.close();
                    return byteArrayOutputStream.toByteArray();
                }
                byteArrayOutputStream.write(bArr2, 0, read);
            }
        } catch (IOException e) {
            zzgf().zzis().zzg("Failed to ungzip content", e);
            throw e;
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ Clock zzbt() {
        return super.zzbt();
    }

    public final int zzcc(String str) {
        if (!zzr("event", str)) {
            return 2;
        }
        if (zza("event", AppMeasurement.Event.zzacx, str)) {
            return !zza("event", 40, str) ? 2 : 0;
        }
        return 13;
    }

    public final int zzcd(String str) {
        if (!zzq("user property", str)) {
            return 6;
        }
        if (zza("user property", AppMeasurement.UserProperty.zzadb, str)) {
            return !zza("user property", 24, str) ? 6 : 0;
        }
        return 15;
    }

    public final int zzce(String str) {
        if (!zzr("user property", str)) {
            return 6;
        }
        if (zza("user property", AppMeasurement.UserProperty.zzadb, str)) {
            return !zza("user property", 24, str) ? 6 : 0;
        }
        return 15;
    }

    public final boolean zzcf(String str) {
        if (TextUtils.isEmpty(str)) {
            zzgf().zzis().log("Missing google_app_id. Firebase Analytics disabled. See https://goo.gl/NAOOOI");
            return false;
        }
        Preconditions.checkNotNull(str);
        if (str.matches("^(1:\\d+:android:[a-f0-9]+|ca-app-pub-.*)$")) {
            return true;
        }
        zzgf().zzis().zzg("Invalid google_app_id. Firebase Analytics disabled. See https://goo.gl/NAOOOI. provided id", str);
        return false;
    }

    public final boolean zzci(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String zzhn = zzgh().zzhn();
        zzgi();
        return zzhn.equals(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:11:0x006b -> B:7:0x0078). Please report as a decompilation issue!!! */
    public final long zzd(Context context, String str) {
        zzab();
        Preconditions.checkNotNull(context);
        Preconditions.checkNotEmpty(str);
        PackageManager packageManager = context.getPackageManager();
        MessageDigest messageDigest = getMessageDigest(Constants.MD5);
        long j = -1;
        if (messageDigest == null) {
            zzgf().zzis().log("Could not get MD5 instance");
        } else {
            if (packageManager != null) {
                try {
                } catch (PackageManager.NameNotFoundException e) {
                    zzgf().zzis().zzg("Package name not found", e);
                }
                if (!zze(context, str)) {
                    PackageInfo packageInfo = Wrappers.packageManager(context).getPackageInfo(getContext().getPackageName(), 64);
                    if (packageInfo.signatures == null || packageInfo.signatures.length <= 0) {
                        zzgf().zziv().log("Could not get signatures");
                    } else {
                        j = zzc(messageDigest.digest(packageInfo.signatures[0].toByteArray()));
                    }
                }
            }
            j = 0;
        }
        return j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final Bundle zzd(Bundle bundle) {
        Bundle bundle2 = new Bundle();
        if (bundle != null) {
            for (String str : bundle.keySet()) {
                Object zzh = zzh(str, bundle.get(str));
                if (zzh == null) {
                    zzgf().zziv().zzg("Param value can't be null", zzgb().zzbj(str));
                } else {
                    zza(bundle2, str, zzh);
                }
            }
        }
        return bundle2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzd(zzew zzewVar, zzdz zzdzVar) {
        Preconditions.checkNotNull(zzewVar);
        Preconditions.checkNotNull(zzdzVar);
        if (!TextUtils.isEmpty(zzdzVar.zzadm)) {
            return true;
        }
        zzgi();
        return false;
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ void zzfr() {
        super.zzfr();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ void zzfs() {
        super.zzfs();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ void zzft() {
        super.zzft();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzdu zzfu() {
        return super.zzfu();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzhl zzfv() {
        return super.zzfv();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzfc zzfw() {
        return super.zzfw();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzeq zzfx() {
        return super.zzfx();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzij zzfy() {
        return super.zzfy();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzig zzfz() {
        return super.zzfz();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzfd zzga() {
        return super.zzga();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzff zzgb() {
        return super.zzgb();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzkc zzgc() {
        return super.zzgc();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzji zzgd() {
        return super.zzgd();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ zzgh zzge() {
        return super.zzge();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ zzfh zzgf() {
        return super.zzgf();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzfs zzgg() {
        return super.zzgg();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh
    public final /* bridge */ /* synthetic */ zzeg zzgh() {
        return super.zzgh();
    }

    @Override // com.google.android.gms.internal.measurement.zzhh, com.google.android.gms.internal.measurement.zzed
    public final /* bridge */ /* synthetic */ zzec zzgi() {
        return super.zzgi();
    }

    public final Object zzh(String str, Object obj) {
        boolean z;
        if ("_ev".equals(str)) {
            z = true;
        } else {
            r1 = zzch(str) ? 256 : 100;
            z = false;
        }
        return zza(r1, obj, z);
    }

    @Override // com.google.android.gms.internal.measurement.zzhi
    protected final boolean zzhh() {
        return true;
    }

    public final int zzi(String str, Object obj) {
        return "_ldl".equals(str) ? zza("user property referrer", str, zzcg(str), obj, false) : zza("user property", str, zzcg(str), obj, false) ? 0 : 7;
    }

    @Override // com.google.android.gms.internal.measurement.zzhi
    protected final void zzin() {
        zzab();
        SecureRandom secureRandom = new SecureRandom();
        long nextLong = secureRandom.nextLong();
        if (nextLong == 0) {
            nextLong = secureRandom.nextLong();
            if (nextLong == 0) {
                zzgf().zziv().log("Utils falling back to Random for random id");
            }
        }
        this.zzarr.set(nextLong);
    }

    public final Object zzj(String str, Object obj) {
        return zza(zzcg(str), obj, "_ldl".equals(str));
    }

    public final long zzlk() {
        long j = this.zzarr.get();
        synchronized (this.zzarr) {
            if (j != 0) {
                this.zzarr.compareAndSet(-1L, 1L);
                return this.zzarr.getAndIncrement();
            }
            long nextLong = new Random(System.nanoTime() ^ zzbt().currentTimeMillis()).nextLong();
            int i = this.zzars + 1;
            this.zzars = i;
            return nextLong + i;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final SecureRandom zzll() {
        zzab();
        if (this.zzarq == null) {
            this.zzarq = new SecureRandom();
        }
        return this.zzarq;
    }

    public final int zzlm() {
        if (this.zzart == null) {
            this.zzart = Integer.valueOf(GoogleApiAvailabilityLight.getInstance().getApkVersion(getContext()) / 1000);
        }
        return this.zzart.intValue();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean zzq(String str, String str2) {
        if (str2 == null) {
            zzgf().zzis().zzg("Name is required and can't be null. Type", str);
            return false;
        }
        if (str2.length() == 0) {
            zzgf().zzis().zzg("Name is required and can't be empty. Type", str);
            return false;
        }
        int codePointAt = str2.codePointAt(0);
        if (!Character.isLetter(codePointAt)) {
            zzgf().zzis().zze("Name must start with a letter. Type, name", str, str2);
            return false;
        }
        int length = str2.length();
        int charCount = Character.charCount(codePointAt);
        while (charCount < length) {
            int codePointAt2 = str2.codePointAt(charCount);
            if (codePointAt2 != 95 && !Character.isLetterOrDigit(codePointAt2)) {
                zzgf().zzis().zze("Name must consist of letters, digits or _ (underscores). Type, name", str, str2);
                return false;
            }
            charCount += Character.charCount(codePointAt2);
        }
        return true;
    }

    public final boolean zzw(String str) {
        zzab();
        if (Wrappers.packageManager(getContext()).checkCallingOrSelfPermission(str) == 0) {
            return true;
        }
        zzgf().zziy().zzg("Permission not granted", str);
        return false;
    }
}
