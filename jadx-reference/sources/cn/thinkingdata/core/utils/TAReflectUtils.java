package cn.thinkingdata.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public class TAReflectUtils {
    static String TAG = "ThinkingAnalytics.TAReflectUtils";

    public static Object createObject(String str) {
        Class<?> cls;
        try {
            cls = Class.forName(str);
        } catch (Exception e) {
            e = e;
            cls = null;
        }
        try {
            return cls.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e2) {
            e = e2;
            e.printStackTrace();
            return cls;
        }
    }

    public static Field getAccessibleField(Object obj, String str) {
        for (Class<?> cls = obj.getClass(); cls != Object.class; cls = cls.getSuperclass()) {
            if (cls != null) {
                try {
                    Field declaredField = cls.getDeclaredField(str);
                    declaredField.setAccessible(true);
                    return declaredField;
                } catch (NoSuchFieldException unused) {
                    continue;
                }
            }
        }
        return null;
    }

    public static Method getAccessibleMethod(Object obj, String str, Class<?>... clsArr) {
        if (obj == null) {
            TDLog.i(TAG, "obj is null!");
            return null;
        }
        for (Class<?> cls = obj.getClass(); cls != Object.class; cls = cls.getSuperclass()) {
            try {
                Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
                declaredMethod.setAccessible(true);
                return declaredMethod;
            } catch (NoSuchMethodException unused) {
            }
        }
        return null;
    }

    public static Object getFieldValue(Object obj, String str) {
        Field accessibleField = getAccessibleField(obj, str);
        if (accessibleField != null) {
            try {
                return accessibleField.get(obj);
            } catch (IllegalAccessException e) {
                TDLog.e(TAG, e.getMessage());
                return null;
            }
        }
        throw new IllegalArgumentException("Could not find field [" + str + "] on target [" + obj + "]");
    }

    public static Object getObjectInstance(String str) {
        Class<?> cls;
        try {
            cls = Class.forName(str);
        } catch (Exception e) {
            e = e;
            cls = null;
        }
        try {
            return cls.getMethod("getInstance", new Class[0]).invoke(cls, new Object[0]);
        } catch (Exception e2) {
            e = e2;
            e.printStackTrace();
            return cls;
        }
    }

    public static <T> Class<T> getSuperClassGenericType(Class cls) {
        return getSuperClassGenericType(cls, 0);
    }

    public static Class getSuperClassGenericType(Class cls, int i) {
        Type genericSuperclass = cls.getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType)) {
            TDLog.w(TAG, cls.getSimpleName() + "'s superclass not ParameterizedType");
            return Object.class;
        }
        Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
        if (i < actualTypeArguments.length && i >= 0) {
            if (actualTypeArguments[i] instanceof Class) {
                return (Class) actualTypeArguments[i];
            }
            TDLog.w(TAG, cls.getSimpleName() + " not set the actual class on superclass generic parameter");
            return Object.class;
        }
        TDLog.w(TAG, "Index: " + i + ", Size of " + cls.getSimpleName() + "'s Parameterized Type: " + actualTypeArguments.length);
        return Object.class;
    }

    public static Object invokeGetterMethod(Object obj, String str) {
        return invokeMethod(obj, "get" + str.trim(), new Object[0], new Class[0]);
    }

    public static Object invokeMethod(Object obj, String str, Object[] objArr, Class<?>... clsArr) {
        Method accessibleMethod = getAccessibleMethod(obj, str, clsArr);
        if (accessibleMethod != null) {
            try {
                return accessibleMethod.invoke(obj, objArr);
            } catch (Exception e) {
                TDLog.e(TAG, e.getMessage());
                return null;
            }
        }
        TDLog.i(TAG, "Could not find method [" + str + "] on target [" + obj + "]");
        return null;
    }

    public static void invokeSetterMethod(Object obj, String str, Object obj2) {
        invokeSetterMethod(obj, str, obj2, null);
    }

    public static void invokeSetterMethod(Object obj, String str, Object obj2, Class<?> cls) {
        if (cls == null) {
            cls = obj2.getClass();
        }
        invokeMethod(obj, "set" + str.trim(), new Object[]{obj2}, cls);
    }

    public static void invokeStaticMethod(String str, String str2, Object[] objArr, Class<?>... clsArr) {
        Class.forName(str).getDeclaredMethod(str2, clsArr).invoke(null, objArr);
    }

    public static void setFieldValue(Object obj, String str, Object obj2) {
        Field accessibleField = getAccessibleField(obj, str);
        if (accessibleField != null) {
            try {
                accessibleField.set(obj, obj2);
                return;
            } catch (IllegalAccessException e) {
                TDLog.e(TAG, e.getMessage());
                return;
            }
        }
        throw new IllegalArgumentException("Could not find field [" + str + "] on target [" + obj + "]");
    }
}
