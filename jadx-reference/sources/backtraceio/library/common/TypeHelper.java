package backtraceio.library.common;

/* loaded from: classes.dex */
public class TypeHelper {
    public static boolean isPrimitiveOrPrimitiveWrapperOrString(Class type) {
        return (type.isPrimitive() && type != Void.TYPE) || type == Double.class || type == Float.class || type == Long.class || type == Integer.class || type == Short.class || type == Character.class || type == Byte.class || type == Boolean.class || type == String.class || type.isEnum();
    }
}
