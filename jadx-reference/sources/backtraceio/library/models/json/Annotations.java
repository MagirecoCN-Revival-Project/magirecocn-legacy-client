package backtraceio.library.models.json;

import backtraceio.library.models.BacktraceAttributeConsts;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class Annotations {
    public static Map<String, Object> getAnnotations(Object exceptionMessage, Map<String, Object> complexAttributes) {
        HashMap hashMap = new HashMap();
        hashMap.put("Environment Variables", System.getenv());
        if (complexAttributes != null) {
            hashMap.putAll(complexAttributes);
        }
        hashMap.put(BacktraceAttributeConsts.HandledExceptionAttributeType, new AnnotationException(exceptionMessage));
        return hashMap;
    }
}
