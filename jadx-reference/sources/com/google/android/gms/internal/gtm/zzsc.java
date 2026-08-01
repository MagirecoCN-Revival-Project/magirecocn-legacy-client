package com.google.android.gms.internal.gtm;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/* compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* loaded from: classes.dex */
final class zzsc implements zzsd {
    private HttpURLConnection zza;
    private InputStream zzb = null;

    @Override // com.google.android.gms.internal.gtm.zzsd
    public final InputStream zza(String str) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setReadTimeout(AccessibilityNodeInfoCompat.EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_MAX_LENGTH);
        httpURLConnection.setConnectTimeout(AccessibilityNodeInfoCompat.EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_MAX_LENGTH);
        this.zza = httpURLConnection;
        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode == 200) {
            InputStream inputStream = httpURLConnection.getInputStream();
            this.zzb = inputStream;
            return inputStream;
        }
        StringBuilder sb = new StringBuilder(25);
        sb.append("Bad response: ");
        sb.append(responseCode);
        String sb2 = sb.toString();
        if (responseCode == 404) {
            throw new FileNotFoundException(sb2);
        }
        if (responseCode == 503) {
            throw new zzsf(sb2);
        }
        throw new IOException(sb2);
    }

    @Override // com.google.android.gms.internal.gtm.zzsd
    public final void zzb() {
        HttpURLConnection httpURLConnection = this.zza;
        try {
            InputStream inputStream = this.zzb;
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            String valueOf = String.valueOf(e.getMessage());
            com.google.android.gms.tagmanager.zzdh.zzb(valueOf.length() != 0 ? "HttpUrlConnectionNetworkClient: Error when closing http input stream: ".concat(valueOf) : new String("HttpUrlConnectionNetworkClient: Error when closing http input stream: "), e);
        }
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
    }
}
