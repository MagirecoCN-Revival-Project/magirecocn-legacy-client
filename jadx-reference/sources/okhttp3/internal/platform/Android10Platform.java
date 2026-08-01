package okhttp3.internal.platform;

import android.annotation.SuppressLint;
import android.net.ssl.SSLSockets;
import java.util.List;
import javax.annotation.Nullable;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import okhttp3.Protocol;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

/* JADX INFO: Access modifiers changed from: package-private */
@SuppressLint({"NewApi"})
/* loaded from: classes2.dex */
public class Android10Platform extends AndroidPlatform {
    Android10Platform(Class<?> sslParametersClass) {
        super(sslParametersClass, null, null, null, null);
    }

    @Override // okhttp3.internal.platform.AndroidPlatform, okhttp3.internal.platform.Platform
    @SuppressLint({"NewApi"})
    @IgnoreJRERequirement
    public void configureTlsExtensions(SSLSocket sslSocket, String hostname, List<Protocol> protocols) {
        enableSessionTickets(sslSocket);
        SSLParameters sslParameters = sslSocket.getSSLParameters();
        String[] protocolsArray = (String[]) Platform.alpnProtocolNames(protocols).toArray(new String[0]);
        sslParameters.setApplicationProtocols(protocolsArray);
        sslSocket.setSSLParameters(sslParameters);
    }

    private void enableSessionTickets(SSLSocket sslSocket) {
        if (SSLSockets.isSupportedSocket(sslSocket)) {
            SSLSockets.setUseSessionTickets(sslSocket, true);
        }
    }

    @Override // okhttp3.internal.platform.AndroidPlatform, okhttp3.internal.platform.Platform
    @Nullable
    @IgnoreJRERequirement
    public String getSelectedProtocol(SSLSocket socket) {
        String alpnResult = socket.getApplicationProtocol();
        if (alpnResult == null || alpnResult.isEmpty()) {
            return null;
        }
        return alpnResult;
    }

    @Nullable
    public static Platform buildIfSupported() {
        try {
            if (getSdkInt() >= 29) {
                Class<?> sslParametersClass = Class.forName("com.android.org.conscrypt.SSLParametersImpl");
                return new Android10Platform(sslParametersClass);
            }
            return null;
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }
}
