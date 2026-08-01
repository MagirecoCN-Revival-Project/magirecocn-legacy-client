package backtraceio.library.enums;

/* loaded from: classes.dex */
public enum WifiStatus {
    NOT_PERMITTED("NotPermitted"),
    DISABLED("Disabled"),
    ENABLED("Enabled");

    private final String text;

    WifiStatus(final String text) {
        this.text = text;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.text;
    }
}
