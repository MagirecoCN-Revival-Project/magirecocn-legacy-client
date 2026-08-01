package jp.f4samurai.pnote.util;

import com.google.firebase.iid.FirebaseInstanceIdService;
import jp.f4samurai.pnote.PnoteHelper;

/* loaded from: classes.dex */
public class MyInstanceIDListenerService extends FirebaseInstanceIdService {
    private static final String TAG = "Pnote";

    @Override // com.google.firebase.iid.FirebaseInstanceIdService
    public void onTokenRefresh() {
        if (PnoteHelper.getIsRegistDevice().booleanValue()) {
            PnoteHelper._callStartRegist();
        }
    }
}
