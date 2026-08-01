package backtraceio.library.enums;

/* loaded from: classes.dex */
public enum GpsStatus {
    DISABLED("Disabled"),
    ENABLED("Enabled");

    private final String text;

    GpsStatus(final String text) {
        this.text = text;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.text;
    }
}
