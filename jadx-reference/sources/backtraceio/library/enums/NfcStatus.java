package backtraceio.library.enums;

/* loaded from: classes.dex */
public enum NfcStatus {
    NOT_AVAILABLE("NotAvailable"),
    DISABLED("Disabled"),
    ENABLED("Enabled");

    private final String text;

    NfcStatus(final String text) {
        this.text = text;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.text;
    }
}
