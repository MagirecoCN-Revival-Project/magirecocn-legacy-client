package backtraceio.library.common;

/* loaded from: classes.dex */
public class BacktraceStringHelper {
    public static boolean isNullOrEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }

    public static boolean isObjectNotNullOrNotEmptyString(Object input) {
        return input instanceof String ? (input == null || input.toString().trim().isEmpty()) ? false : true : input != null;
    }
}
