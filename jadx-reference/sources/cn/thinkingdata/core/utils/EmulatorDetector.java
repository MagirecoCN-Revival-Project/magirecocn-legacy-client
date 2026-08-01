package cn.thinkingdata.core.utils;

import android.text.TextUtils;

/* loaded from: classes.dex */
public class EmulatorDetector {
    private static String getProp(String str) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", String.class).invoke(cls, str);
        } catch (Exception unused) {
            return null;
        }
    }

    public static boolean isEmulator() {
        return mayOnEmulatorViaQEMU() || isEmulatorFromAbi();
    }

    private static boolean isEmulatorFromAbi() {
        String prop = getProp("ro.product.cpu.abi");
        return (prop == null || TextUtils.isEmpty(prop) || !prop.contains("x86")) ? false : true;
    }

    private static boolean mayOnEmulatorViaQEMU() {
        return "1".equals(getProp("ro.kernel.qemu"));
    }
}
