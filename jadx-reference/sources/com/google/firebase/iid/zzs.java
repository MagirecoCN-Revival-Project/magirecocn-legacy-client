package com.google.firebase.iid;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Properties;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class zzs {
    private static zzt zza(SharedPreferences sharedPreferences, String str) throws zzu {
        String string = sharedPreferences.getString(zzaq.zzb(str, "|P|"), null);
        String string2 = sharedPreferences.getString(zzaq.zzb(str, "|K|"), null);
        if (string == null || string2 == null) {
            return null;
        }
        return new zzt(zza(string, string2), zzb(sharedPreferences, str));
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    private static zzt zza(File file) throws zzu, IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            String property = properties.getProperty("pub");
            String property2 = properties.getProperty("pri");
            if (property != null && property2 != null) {
                try {
                    zzt zztVar = new zzt(zza(property, property2), Long.parseLong(properties.getProperty("cre")));
                    zza((Throwable) null, fileInputStream);
                    return zztVar;
                } catch (NumberFormatException e) {
                    throw new zzu(e);
                }
            }
            zza((Throwable) null, fileInputStream);
            return null;
        } finally {
        }
    }

    private static KeyPair zza(String str, String str2) throws zzu {
        try {
            byte[] decode = Base64.decode(str, 8);
            byte[] decode2 = Base64.decode(str2, 8);
            try {
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                return new KeyPair(keyFactory.generatePublic(new X509EncodedKeySpec(decode)), keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decode2)));
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                String valueOf = String.valueOf(e);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 19);
                sb.append("Invalid key stored ");
                sb.append(valueOf);
                Log.w("FirebaseInstanceId", sb.toString());
                throw new zzu(e);
            }
        } catch (IllegalArgumentException e2) {
            throw new zzu(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void zza(Context context) {
        for (File file : zzb(context).listFiles()) {
            if (file.getName().startsWith("com.google.InstanceId")) {
                file.delete();
            }
        }
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[]}, finally: {[THROW, INVOKE, MOVE_EXCEPTION, THROW, MOVE_EXCEPTION] complete} */
    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    private static void zza(Context context, String str, zzt zztVar) {
        String zzp;
        String zzq;
        long j;
        try {
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                Log.d("FirebaseInstanceId", "Writing key to properties file");
            }
            File zzf = zzf(context, str);
            zzf.createNewFile();
            Properties properties = new Properties();
            zzp = zztVar.zzp();
            properties.setProperty("pub", zzp);
            zzq = zztVar.zzq();
            properties.setProperty("pri", zzq);
            j = zztVar.zzbl;
            properties.setProperty("cre", String.valueOf(j));
            FileOutputStream fileOutputStream = new FileOutputStream(zzf);
            try {
                properties.store(fileOutputStream, (String) null);
                zza((Throwable) null, fileOutputStream);
            } finally {
            }
        } catch (IOException e) {
            String valueOf = String.valueOf(e);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 21);
            sb.append("Failed to write key: ");
            sb.append(valueOf);
            Log.w("FirebaseInstanceId", sb.toString());
        }
    }

    private static /* synthetic */ void zza(Throwable th, FileInputStream fileInputStream) {
        if (th == null) {
            fileInputStream.close();
            return;
        }
        try {
            fileInputStream.close();
        } catch (Throwable th2) {
            com.google.android.gms.internal.firebase_messaging.zzh.zza(th, th2);
        }
    }

    private static /* synthetic */ void zza(Throwable th, FileOutputStream fileOutputStream) {
        if (th == null) {
            fileOutputStream.close();
            return;
        }
        try {
            fileOutputStream.close();
        } catch (Throwable th2) {
            com.google.android.gms.internal.firebase_messaging.zzh.zza(th, th2);
        }
    }

    private static long zzb(SharedPreferences sharedPreferences, String str) {
        String string = sharedPreferences.getString(zzaq.zzb(str, "cre"), null);
        if (string == null) {
            return 0L;
        }
        try {
            return Long.parseLong(string);
        } catch (NumberFormatException unused) {
            return 0L;
        }
    }

    private static File zzb(Context context) {
        File noBackupFilesDir = ContextCompat.getNoBackupFilesDir(context);
        if (noBackupFilesDir != null && noBackupFilesDir.isDirectory()) {
            return noBackupFilesDir;
        }
        Log.w("FirebaseInstanceId", "noBackupFilesDir doesn't exist, using regular files directory instead");
        return context.getFilesDir();
    }

    private final void zzb(Context context, String str, zzt zztVar) {
        String zzp;
        String zzq;
        long j;
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.google.android.gms.appid", 0);
        try {
            if (zztVar.equals(zza(sharedPreferences, str))) {
                return;
            }
        } catch (zzu unused) {
        }
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            Log.d("FirebaseInstanceId", "Writing key to shared preferences");
        }
        SharedPreferences.Editor edit = sharedPreferences.edit();
        String zzb = zzaq.zzb(str, "|P|");
        zzp = zztVar.zzp();
        edit.putString(zzb, zzp);
        String zzb2 = zzaq.zzb(str, "|K|");
        zzq = zztVar.zzq();
        edit.putString(zzb2, zzq);
        String zzb3 = zzaq.zzb(str, "cre");
        j = zztVar.zzbl;
        edit.putString(zzb3, String.valueOf(j));
        edit.commit();
    }

    private final zzt zzd(Context context, String str) throws zzu {
        zzt zze;
        try {
            zze = zze(context, str);
        } catch (zzu e) {
            e = e;
        }
        if (zze != null) {
            zzb(context, str, zze);
            return zze;
        }
        e = null;
        try {
            zzt zza = zza(context.getSharedPreferences("com.google.android.gms.appid", 0), str);
            if (zza != null) {
                zza(context, str, zza);
                return zza;
            }
        } catch (zzu e2) {
            e = e2;
        }
        if (e == null) {
            return null;
        }
        throw e;
    }

    private final zzt zze(Context context, String str) throws zzu {
        File zzf = zzf(context, str);
        if (!zzf.exists()) {
            return null;
        }
        try {
            return zza(zzf);
        } catch (IOException e) {
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                String valueOf = String.valueOf(e);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 40);
                sb.append("Failed to read key from file, retrying: ");
                sb.append(valueOf);
                Log.d("FirebaseInstanceId", sb.toString());
            }
            try {
                return zza(zzf);
            } catch (IOException e2) {
                String valueOf2 = String.valueOf(e2);
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 45);
                sb2.append("IID file exists, but failed to read from it: ");
                sb2.append(valueOf2);
                Log.w("FirebaseInstanceId", sb2.toString());
                throw new zzu(e2);
            }
        }
    }

    private static File zzf(Context context, String str) {
        String sb;
        if (TextUtils.isEmpty(str)) {
            sb = "com.google.InstanceId.properties";
        } else {
            try {
                String encodeToString = Base64.encodeToString(str.getBytes("UTF-8"), 11);
                StringBuilder sb2 = new StringBuilder(String.valueOf(encodeToString).length() + 33);
                sb2.append("com.google.InstanceId_");
                sb2.append(encodeToString);
                sb2.append(".properties");
                sb = sb2.toString();
            } catch (UnsupportedEncodingException e) {
                throw new AssertionError(e);
            }
        }
        return new File(zzb(context), sb);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final zzt zzb(Context context, String str) throws zzu {
        zzt zzd = zzd(context, str);
        return zzd != null ? zzd : zzc(context, str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final zzt zzc(Context context, String str) {
        zzt zztVar = new zzt(zza.zzb(), System.currentTimeMillis());
        try {
            zzt zzd = zzd(context, str);
            if (zzd != null) {
                if (Log.isLoggable("FirebaseInstanceId", 3)) {
                    Log.d("FirebaseInstanceId", "Loaded key after generating new one, using loaded one");
                }
                return zzd;
            }
        } catch (zzu unused) {
        }
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            Log.d("FirebaseInstanceId", "Generated new key");
        }
        zza(context, str, zztVar);
        zzb(context, str, zztVar);
        return zztVar;
    }
}
