package backtraceio.library.models.json;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import backtraceio.library.BacktraceClient;
import backtraceio.library.common.BacktraceStringHelper;
import backtraceio.library.common.DeviceAttributesHelper;
import backtraceio.library.common.TypeHelper;
import backtraceio.library.enums.ScreenOrientation;
import backtraceio.library.logger.BacktraceLogger;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes.dex */
public class BacktraceAttributes {
    private static final transient String LOG_TAG = "BacktraceAttributes";
    private static boolean isMetricsEnabled;
    private static String sessionId;
    public Map<String, String> attributes;
    private final Map<String, Object> complexAttributes;
    private final Context context;

    public BacktraceAttributes(Context context, BacktraceReport report, Map<String, Object> clientAttributes) {
        this(context, report, clientAttributes, true);
    }

    public BacktraceAttributes(Context context, Map<String, Object> clientAttributes) {
        this(context, null, clientAttributes, false);
    }

    public BacktraceAttributes(Context context, BacktraceReport report, Map<String, Object> clientAttributes, Boolean includeDynamicAttributes) {
        this.attributes = new HashMap();
        this.complexAttributes = new HashMap();
        this.context = context;
        if (report != null) {
            convertReportAttributes(report);
            setExceptionAttributes(report);
        }
        if (clientAttributes != null) {
            convertClientAttributes(clientAttributes);
        }
        if (report != null && clientAttributes != null) {
            BacktraceReport.concatAttributes(report, clientAttributes);
        }
        setAppInformation();
        setDeviceInformation(includeDynamicAttributes);
        setScreenInformation(includeDynamicAttributes);
        if (isMetricsEnabled) {
            this.attributes.put("application.session", sessionId);
        }
    }

    public Map<String, Object> getComplexAttributes() {
        return this.complexAttributes;
    }

    private void setDeviceInformation(Boolean includeDynamicAttributes) {
        this.attributes.put("uname.version", Build.VERSION.RELEASE);
        this.attributes.put("culture", Locale.getDefault().getDisplayLanguage());
        this.attributes.put("build.type", "Release");
        this.attributes.put("device.model", Build.MODEL);
        this.attributes.put("device.brand", Build.BRAND);
        this.attributes.put("device.product", Build.PRODUCT);
        this.attributes.put("device.sdk", String.valueOf(Build.VERSION.SDK_INT));
        this.attributes.put("device.manufacturer", Build.MANUFACTURER);
        this.attributes.put("device.os_version", System.getProperty("os.version"));
        this.attributes.putAll(new DeviceAttributesHelper(this.context).getDeviceAttributes(includeDynamicAttributes));
    }

    private void setAppInformation() {
        this.attributes.put("application.package", this.context.getApplicationContext().getPackageName());
        this.attributes.put("application", getApplicationName());
        String applicationVersionOrEmpty = getApplicationVersionOrEmpty();
        if (!BacktraceStringHelper.isNullOrEmpty(applicationVersionOrEmpty)) {
            this.attributes.put("application.version", applicationVersionOrEmpty);
            this.attributes.put(ClientCookie.VERSION_ATTR, applicationVersionOrEmpty);
        }
        this.attributes.put("backtrace.version", BacktraceClient.version);
    }

    private void setScreenInformation(Boolean includeDynamicAttributes) {
        Display defaultDisplay = ((WindowManager) this.context.getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        this.attributes.put("screen.width", String.valueOf(displayMetrics.widthPixels));
        this.attributes.put("screen.height", String.valueOf(displayMetrics.heightPixels));
        this.attributes.put("screen.dpi", String.valueOf(displayMetrics.densityDpi));
        if (includeDynamicAttributes.booleanValue()) {
            this.attributes.put("screen.orientation", getScreenOrientation().toString());
            this.attributes.put("screen.brightness", String.valueOf(getScreenBrightness()));
        }
    }

    private void setExceptionAttributes(BacktraceReport report) {
        if (report == null) {
            return;
        }
        if (!report.exceptionTypeReport.booleanValue()) {
            this.attributes.put("error.message", report.message);
        } else {
            this.attributes.put("classifier", report.exception.getClass().getName());
            this.attributes.put("error.message", report.exception.getMessage());
        }
    }

    private ScreenOrientation getScreenOrientation() {
        int i = this.context.getResources().getConfiguration().orientation;
        if (i == 1) {
            return ScreenOrientation.PORTRAIT;
        }
        if (i == 2) {
            return ScreenOrientation.LANDSCAPE;
        }
        return ScreenOrientation.UNDEFINED;
    }

    private int getScreenBrightness() {
        return Settings.System.getInt(this.context.getContentResolver(), "screen_brightness", 0);
    }

    private void convertClientAttributes(Map<String, Object> clientAttributes) {
        convertAttributes(clientAttributes);
    }

    private void convertReportAttributes(BacktraceReport report) {
        convertAttributes(BacktraceReport.concatAttributes(report, null));
        if (report.exceptionTypeReport.booleanValue()) {
            this.complexAttributes.put("Exception properties", report.exception);
        }
    }

    private void convertAttributes(Map<String, Object> attributes) {
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            Object value = entry.getValue();
            if (value != null) {
                if (TypeHelper.isPrimitiveOrPrimitiveWrapperOrString(value.getClass())) {
                    this.attributes.put(entry.getKey(), value.toString());
                } else {
                    this.complexAttributes.put(entry.getKey(), value);
                }
            }
        }
    }

    public String getApplicationName() {
        return this.context.getApplicationInfo().loadLabel(this.context.getPackageManager()).toString();
    }

    public String getApplicationVersionOrEmpty() {
        try {
            return this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            BacktraceLogger.e(LOG_TAG, "Could not resolve application version");
            e.printStackTrace();
            return "";
        }
    }

    public Map<String, Object> getAllAttributes() {
        HashMap hashMap = new HashMap();
        hashMap.putAll(this.attributes);
        hashMap.putAll(this.complexAttributes);
        return hashMap;
    }

    public static void enableMetrics() {
        isMetricsEnabled = true;
        sessionId = UUID.randomUUID().toString();
    }
}
