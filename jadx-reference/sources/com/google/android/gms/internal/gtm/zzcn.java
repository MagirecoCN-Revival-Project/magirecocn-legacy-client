package com.google.android.gms.internal.gtm;

import android.content.Context;
import com.google.android.gms.common.internal.Preconditions;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/* compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* loaded from: classes.dex */
public final class zzcn extends zzbs {
    private volatile String zza;
    private Future<String> zzb;

    /* JADX INFO: Access modifiers changed from: protected */
    public zzcn(zzbv zzbvVar) {
        super(zzbvVar);
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[IF]}, finally: {[IF, INVOKE, MOVE_EXCEPTION, INVOKE, MOVE_EXCEPTION] complete} */
    /* JADX INFO: Access modifiers changed from: private */
    public final String zzf() {
        String lowerCase = UUID.randomUUID().toString().toLowerCase(Locale.US);
        try {
            Context zza = zzq().zza();
            Preconditions.checkNotEmpty(lowerCase);
            Preconditions.checkNotMainThread("ClientId should be saved from worker thread");
            FileOutputStream fileOutputStream = null;
            try {
                try {
                    try {
                        zzP("Storing clientId", lowerCase);
                        fileOutputStream = zza.openFileOutput("gaClientId", 0);
                        fileOutputStream.write(lowerCase.getBytes());
                        return lowerCase;
                    } catch (FileNotFoundException e) {
                        zzK("Error creating clientId file", e);
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e2) {
                                e = e2;
                                zzK("Failed to close clientId writing stream", e);
                                return "0";
                            }
                        }
                        return "0";
                    }
                } catch (IOException e3) {
                    zzK("Error writing to clientId file", e3);
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e4) {
                            e = e4;
                            zzK("Failed to close clientId writing stream", e);
                            return "0";
                        }
                    }
                    return "0";
                }
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e5) {
                        zzK("Failed to close clientId writing stream", e5);
                    }
                }
            }
        } catch (Exception e6) {
            zzK("Error saving clientId file", e6);
            return "0";
        }
    }

    public final String zzb() {
        String str;
        zzW();
        synchronized (this) {
            if (this.zza == null) {
                this.zzb = zzq().zzg(new zzcl(this));
            }
            Future<String> future = this.zzb;
            if (future != null) {
                try {
                    this.zza = future.get();
                } catch (InterruptedException e) {
                    zzS("ClientId loading or generation was interrupted", e);
                    this.zza = "0";
                } catch (ExecutionException e2) {
                    zzK("Failed to load or generate client id", e2);
                    this.zza = "0";
                }
                if (this.zza == null) {
                    this.zza = "0";
                }
                zzP("Loaded clientId", this.zza);
                this.zzb = null;
            }
            str = this.zza;
        }
        return str;
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [9=5] */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:12:0x0092 */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:22:? */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:39:0x0064 */
    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0099 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x008a A[Catch: IOException -> 0x008e, TRY_ENTER, TRY_LEAVE, TryCatch #8 {IOException -> 0x008e, blocks: (B:11:0x0032, B:27:0x0047, B:42:0x0076, B:38:0x008a), top: B:2:0x0012 }] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0076 A[Catch: IOException -> 0x008e, TRY_ENTER, TRY_LEAVE, TryCatch #8 {IOException -> 0x008e, blocks: (B:11:0x0032, B:27:0x0047, B:42:0x0076, B:38:0x008a), top: B:2:0x0012 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x007e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:38:0x008a -> B:14:0x0092). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:41:0x0074 -> B:12:0x0092). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:42:0x0076 -> B:12:0x0092). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:58:0x008f -> B:12:0x0092). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String zzc() {
        Throwable th;
        IOException e;
        FileInputStream fileInputStream;
        String str = "gaClientId";
        Context zza = zzq().zza();
        Preconditions.checkNotMainThread("ClientId should be loaded from worker thread");
        String str2 = null;
        str2 = null;
        str2 = null;
        str2 = null;
        str2 = null;
        str2 = null;
        str2 = null;
        str2 = null;
        str2 = null;
        FileInputStream fileInputStream2 = null;
        try {
        } catch (IOException e2) {
            zzK("Failed to close client id reading stream", e2);
            str = e2;
        }
        try {
            try {
                fileInputStream = zza.openFileInput("gaClientId");
                try {
                    byte[] bArr = new byte[36];
                    int read = fileInputStream.read(bArr, 0, 36);
                    if (fileInputStream.available() > 0) {
                        zzR("clientId file seems corrupted, deleting it.");
                        fileInputStream.close();
                        zza.deleteFile("gaClientId");
                        str = str;
                        if (fileInputStream != null) {
                            fileInputStream.close();
                            str = str;
                        }
                    } else if (read < 14) {
                        zzR("clientId file is empty, deleting it.");
                        fileInputStream.close();
                        zza.deleteFile("gaClientId");
                        str = str;
                        if (fileInputStream != null) {
                            fileInputStream.close();
                            str = str;
                        }
                    } else {
                        fileInputStream.close();
                        String str3 = new String(bArr, 0, read);
                        zzP("Read client id from disk", str3);
                        String str4 = str;
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                                str4 = str;
                            } catch (IOException e3) {
                                zzK("Failed to close client id reading stream", e3);
                                str4 = e3;
                            }
                        }
                        str2 = str3;
                        str = str4;
                    }
                } catch (FileNotFoundException unused) {
                    str = str;
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    if (str2 == null) {
                    }
                } catch (IOException e4) {
                    e = e4;
                    zzK("Error reading client id file, deleting it", e);
                    zza.deleteFile(str);
                    str = str;
                    if (fileInputStream != null) {
                        fileInputStream.close();
                        str = str;
                    }
                    if (str2 == null) {
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                fileInputStream2 = fileInputStream;
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (IOException e5) {
                        zzK("Failed to close client id reading stream", e5);
                    }
                }
                throw th;
            }
        } catch (FileNotFoundException unused2) {
            fileInputStream = null;
            str = str;
            if (fileInputStream != null) {
            }
            if (str2 == null) {
            }
        } catch (IOException e6) {
            e = e6;
            fileInputStream = null;
            zzK("Error reading client id file, deleting it", e);
            zza.deleteFile(str);
            str = str;
            if (fileInputStream != null) {
            }
            if (str2 == null) {
            }
        } catch (Throwable th3) {
            th = th3;
            if (fileInputStream2 != null) {
            }
            throw th;
        }
        return str2 == null ? zzf() : str2;
    }

    @Override // com.google.android.gms.internal.gtm.zzbs
    protected final void zzd() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final String zze() {
        synchronized (this) {
            this.zza = null;
            this.zzb = zzq().zzg(new zzcm(this));
        }
        return zzb();
    }
}
