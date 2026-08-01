package backtraceio.library.interfaces;

import android.content.Context;
import backtraceio.library.enums.BacktraceBreadcrumbLevel;
import backtraceio.library.enums.BacktraceBreadcrumbType;
import backtraceio.library.models.json.BacktraceReport;
import java.util.EnumSet;
import java.util.Map;

/* loaded from: classes.dex */
public interface Breadcrumbs {
    boolean addBreadcrumb(String message);

    boolean addBreadcrumb(String message, BacktraceBreadcrumbLevel level);

    boolean addBreadcrumb(String message, BacktraceBreadcrumbType type);

    boolean addBreadcrumb(String message, BacktraceBreadcrumbType type, BacktraceBreadcrumbLevel level);

    boolean addBreadcrumb(String message, Map<String, Object> attributes);

    boolean addBreadcrumb(String message, Map<String, Object> attributes, BacktraceBreadcrumbLevel level);

    boolean addBreadcrumb(String message, Map<String, Object> attributes, BacktraceBreadcrumbType type);

    boolean addBreadcrumb(String message, Map<String, Object> attributes, BacktraceBreadcrumbType type, BacktraceBreadcrumbLevel level);

    boolean clearBreadcrumbs();

    boolean enableBreadcrumbs(Context context);

    boolean enableBreadcrumbs(Context context, int maxBreadcrumbLogSizeBytes);

    boolean enableBreadcrumbs(Context context, EnumSet<BacktraceBreadcrumbType> breadcrumbTypesToEnable);

    boolean enableBreadcrumbs(Context context, EnumSet<BacktraceBreadcrumbType> breadcrumbTypesToEnable, int maxBreadcrumbLogSizeBytes);

    String getBreadcrumbLogPath();

    EnumSet<BacktraceBreadcrumbType> getEnabledBreadcrumbTypes();

    void processReportBreadcrumbs(BacktraceReport report);

    void setCurrentBreadcrumbId(long breadcrumbId);
}
