package backtraceio.library.enums;

/* loaded from: classes.dex */
public enum ScreenOrientation {
    PORTRAIT("Portrait"),
    LANDSCAPE("Landscape"),
    UNDEFINED("Unknown");

    private final String text;

    ScreenOrientation(final String text) {
        this.text = text;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.text;
    }
}
