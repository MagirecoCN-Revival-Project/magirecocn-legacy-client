package backtraceio.library.enums;

/* loaded from: classes.dex */
public enum BluetoothStatus {
    NOT_PERMITTED("NotPermitted"),
    DISABLED("Disabled"),
    ENABLED("Enabled");

    private final String text;

    BluetoothStatus(final String text) {
        this.text = text;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.text;
    }
}
