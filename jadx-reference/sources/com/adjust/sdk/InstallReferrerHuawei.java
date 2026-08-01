package com.adjust.sdk;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class InstallReferrerHuawei {
    private static final String REFERRER_PROVIDER_AUTHORITY = "com.huawei.appmarket.commondata";
    private static final String REFERRER_PROVIDER_URI = "content://com.huawei.appmarket.commondata/item/5";
    private Context context;
    private final InstallReferrerReadListener referrerCallback;
    private ILogger logger = AdjustFactory.getLogger();
    private final AtomicBoolean shouldTryToRead = new AtomicBoolean(true);

    public InstallReferrerHuawei(Context context, InstallReferrerReadListener installReferrerReadListener) {
        this.context = context;
        this.referrerCallback = installReferrerReadListener;
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[IF]}, finally: {[IF, INVOKE] complete} */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0090, code lost:
    
        if (r3 != null) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x00aa, code lost:
    
        r17.shouldTryToRead.set(false);
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x00af, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00a7, code lost:
    
        r3.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00a5, code lost:
    
        if (0 == 0) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void readReferrer() {
        if (!this.shouldTryToRead.get()) {
            this.logger.debug("Should not try to read Install referrer Huawei", new Object[0]);
            return;
        }
        if (!Util.resolveContentProvider(this.context, REFERRER_PROVIDER_AUTHORITY)) {
            return;
        }
        Cursor cursor = null;
        Uri parse = Uri.parse(REFERRER_PROVIDER_URI);
        try {
            try {
                cursor = this.context.getContentResolver().query(parse, null, null, new String[]{this.context.getPackageName()}, null);
                if (cursor != null && cursor.moveToFirst()) {
                    String string = cursor.getString(0);
                    String string2 = cursor.getString(1);
                    String string3 = cursor.getString(2);
                    this.logger.debug("InstallReferrerHuawei reads referrer[%s] clickTime[%s] installTime[%s]", string, string2, string3);
                    this.referrerCallback.onInstallReferrerRead(new ReferrerDetails(string, Long.parseLong(string2), Long.parseLong(string3)));
                } else {
                    this.logger.debug("InstallReferrerHuawei fail to read referrer for package [%s] and content uri [%s]", this.context.getPackageName(), parse.toString());
                }
            } catch (Exception e) {
                this.logger.debug("InstallReferrerHuawei error [%s]", e.getMessage());
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }
}
