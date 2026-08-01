package backtraceio.library.breadcrumbs;

import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import backtraceio.library.enums.BacktraceBreadcrumbLevel;
import backtraceio.library.enums.BacktraceBreadcrumbType;
import java.util.HashMap;

/* loaded from: classes.dex */
public class BacktraceComponentListener implements ComponentCallbacks2 {
    private final BacktraceBreadcrumbs backtraceBreadcrumbs;

    private String getMemoryWarningString(final int level) {
        return level != 5 ? level != 10 ? level != 15 ? level != 20 ? level != 40 ? level != 60 ? level != 80 ? "Generic memory warning" : "TRIM MEMORY COMPLETE" : "TRIM MEMORY MODERATE" : "TRIM MEMORY BACKGROUND" : "TRIM MEMORY UI HIDDEN" : "TRIM MEMORY RUNNING CRITICAL" : "TRIM MEMORY RUNNING LOW" : "TRIM MEMORY RUNNING MODERATE";
    }

    private String stringifyOrientation(final int orientation) {
        return orientation != 1 ? orientation != 2 ? "unknown orientation" : "landscape" : "portrait";
    }

    public BacktraceComponentListener(BacktraceBreadcrumbs backtraceBreadcrumbs) {
        this.backtraceBreadcrumbs = backtraceBreadcrumbs;
    }

    private BacktraceBreadcrumbLevel getMemoryWarningLevel(final int level) {
        if (level != 5 && level != 10) {
            if (level != 15) {
                if (level != 40) {
                    if (level != 60 && level != 80) {
                        return BacktraceBreadcrumbLevel.WARNING;
                    }
                }
            }
            return BacktraceBreadcrumbLevel.FATAL;
        }
        return BacktraceBreadcrumbLevel.ERROR;
    }

    @Override // android.content.ComponentCallbacks2
    public void onTrimMemory(int level) {
        this.backtraceBreadcrumbs.addBreadcrumb(getMemoryWarningString(level), BacktraceBreadcrumbType.SYSTEM, getMemoryWarningLevel(level));
    }

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        HashMap hashMap = new HashMap();
        hashMap.put("orientation", stringifyOrientation(newConfig.orientation));
        this.backtraceBreadcrumbs.addBreadcrumb("Configuration changed", hashMap, BacktraceBreadcrumbType.SYSTEM, BacktraceBreadcrumbLevel.INFO);
    }

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
        this.backtraceBreadcrumbs.addBreadcrumb("Critical low memory warning!", BacktraceBreadcrumbType.SYSTEM, BacktraceBreadcrumbLevel.FATAL);
    }
}
