package backtraceio.library.enums;

/* loaded from: classes.dex */
public enum BatteryState {
    CHARGING("Charging"),
    UNKNOWN("Unknown"),
    FULL("Full"),
    UNPLUGGED("Unplugged");

    private final String text;

    BatteryState(final String text) {
        this.text = text;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.text;
    }
}
