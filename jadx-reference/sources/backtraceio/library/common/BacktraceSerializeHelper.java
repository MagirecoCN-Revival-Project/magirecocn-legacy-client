package backtraceio.library.common;

import backtraceio.library.models.BacktraceResult;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/* loaded from: classes.dex */
public class BacktraceSerializeHelper {
    public static BacktraceResult backtraceResultFromJson(String json) {
        return (BacktraceResult) new Gson().fromJson(json, BacktraceResult.class);
    }

    public static String toJson(Object object) {
        return buildGson().toJson(object);
    }

    public static <T> T fromJson(String str, Class<T> cls) {
        return (T) buildGson().fromJson(str, (Class) cls);
    }

    private static Gson buildGson() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES).create();
    }
}
