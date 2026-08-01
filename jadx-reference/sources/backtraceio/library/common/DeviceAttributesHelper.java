package backtraceio.library.common;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import backtraceio.library.enums.BatteryState;
import backtraceio.library.enums.BluetoothStatus;
import backtraceio.library.enums.GpsStatus;
import backtraceio.library.enums.LocationStatus;
import backtraceio.library.enums.NfcStatus;
import backtraceio.library.enums.WifiStatus;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.UUID;

/* loaded from: classes.dex */
public class DeviceAttributesHelper {
    private final Context context;

    public DeviceAttributesHelper(Context context) {
        this.context = context;
    }

    public HashMap<String, String> getDeviceAttributes(Boolean includeDynamicAttributes) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("guid", generateDeviceId());
        hashMap.put("uname.sysname", "Android");
        hashMap.put("uname.machine", System.getProperty("os.arch"));
        if (!includeDynamicAttributes.booleanValue()) {
            return hashMap;
        }
        hashMap.put("device.airplane_mode", String.valueOf(isAirplaneModeOn()));
        hashMap.put("device.location", getLocationServiceStatus().toString());
        hashMap.put("device.nfc.status", getNfcStatus().toString());
        hashMap.put("device.gps.enabled", getGpsStatus().toString());
        hashMap.put("device.bluetooth_status", isBluetoothEnabled().toString());
        hashMap.put("device.cpu.temperature", String.valueOf(getCpuTemperature()));
        hashMap.put("device.is_power_saving_mode", String.valueOf(isPowerSavingMode()));
        hashMap.put("device.wifi.status", getWifiStatus().toString());
        hashMap.put("system.memory.total", getMaxRamSize());
        hashMap.put("system.memory.free", getDeviceFreeRam());
        hashMap.put("system.memory.active", getDeviceActiveRam());
        hashMap.put("app.storage_used", getAppUsedStorageSize());
        hashMap.put("battery.level", String.valueOf(getBatteryLevel()));
        hashMap.put("battery.state", getBatteryState().toString());
        hashMap.put("cpu.boottime", String.valueOf(System.currentTimeMillis() - SystemClock.elapsedRealtime()));
        return hashMap;
    }

    private boolean isAirplaneModeOn() {
        return Settings.Global.getInt(this.context.getContentResolver(), "airplane_mode_on", 0) != 0;
    }

    private LocationStatus getLocationServiceStatus() {
        if (Settings.Secure.getInt(this.context.getContentResolver(), "location_mode", 0) != 0) {
            return LocationStatus.ENABLED;
        }
        return LocationStatus.DISABLED;
    }

    private NfcStatus getNfcStatus() {
        NfcAdapter defaultAdapter = NfcAdapter.getDefaultAdapter(this.context);
        if (defaultAdapter == null) {
            return NfcStatus.NOT_AVAILABLE;
        }
        if (!defaultAdapter.isEnabled()) {
            return NfcStatus.DISABLED;
        }
        return NfcStatus.ENABLED;
    }

    private BluetoothStatus isBluetoothEnabled() {
        if (!PermissionHelper.isPermissionForBluetoothGranted(this.context)) {
            return BluetoothStatus.NOT_PERMITTED;
        }
        if (BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            return BluetoothStatus.ENABLED;
        }
        return BluetoothStatus.DISABLED;
    }

    private float getCpuTemperature() {
        try {
            Process exec = Runtime.getRuntime().exec("cat sys/class/thermal/thermal_zone0/temp");
            exec.waitFor();
            String readLine = new BufferedReader(new InputStreamReader(exec.getInputStream())).readLine();
            if (readLine == null) {
                return 0.0f;
            }
            return Float.parseFloat(readLine) / 1000.0f;
        } catch (Exception unused) {
            return 0.0f;
        }
    }

    private GpsStatus getGpsStatus() {
        return ((LocationManager) this.context.getSystemService(FirebaseAnalytics.Param.LOCATION)).isProviderEnabled("gps") ? GpsStatus.ENABLED : GpsStatus.DISABLED;
    }

    private WifiStatus getWifiStatus() {
        if (!PermissionHelper.isPermissionForAccessWifiStateGranted(this.context)) {
            return WifiStatus.NOT_PERMITTED;
        }
        if (((WifiManager) this.context.getApplicationContext().getSystemService("wifi")).isWifiEnabled()) {
            return WifiStatus.ENABLED;
        }
        return WifiStatus.DISABLED;
    }

    private boolean isPowerSavingMode() {
        if (Build.VERSION.SDK_INT < 21) {
            return false;
        }
        return ((PowerManager) this.context.getSystemService("power")).isPowerSaveMode();
    }

    private float getBatteryLevel() {
        if (this.context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED")) == null) {
            return -1.0f;
        }
        return r0.getIntExtra(FirebaseAnalytics.Param.LEVEL, -1) / r0.getIntExtra("scale", -1);
    }

    private BatteryState getBatteryState() {
        Intent registerReceiver = this.context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (registerReceiver == null) {
            return BatteryState.UNKNOWN;
        }
        int intExtra = registerReceiver.getIntExtra(NotificationCompat.CATEGORY_STATUS, -1);
        if (intExtra == 2) {
            return BatteryState.CHARGING;
        }
        if (intExtra == 4) {
            return BatteryState.UNPLUGGED;
        }
        if (intExtra == 5) {
            return BatteryState.FULL;
        }
        return BatteryState.UNKNOWN;
    }

    private String generateDeviceId() {
        String string = Settings.Secure.getString(this.context.getContentResolver(), "android_id");
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        return UUID.nameUUIDFromBytes(string.getBytes()).toString();
    }

    private String getMaxRamSize() {
        return Long.toString(getMemoryInformation().totalMem);
    }

    private String getDeviceFreeRam() {
        return Long.toString(getMemoryInformation().availMem);
    }

    private String getDeviceActiveRam() {
        ActivityManager.MemoryInfo memoryInformation = getMemoryInformation();
        return Long.toString(memoryInformation.totalMem - memoryInformation.availMem);
    }

    private ActivityManager.MemoryInfo getMemoryInformation() {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ((ActivityManager) this.context.getSystemService("activity")).getMemoryInfo(memoryInfo);
        return memoryInfo;
    }

    private String getAppUsedStorageSize() {
        long j;
        try {
            Runtime runtime = Runtime.getRuntime();
            j = runtime.totalMemory() - runtime.freeMemory();
        } catch (Exception e) {
            e.printStackTrace();
            j = -1;
        }
        return Long.toString(j);
    }
}
