package com.google.android.gms.common.data;

import cn.thinkingdata.analytics.utils.f;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: com.google.android.gms:play-services-base@@18.0.1 */
/* loaded from: classes.dex */
public final class FreezableUtils {
    public static <T, E extends Freezable<T>> ArrayList<T> freeze(ArrayList<E> arrayList) {
        f.a aVar = (ArrayList<T>) new ArrayList(arrayList.size());
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            aVar.add(arrayList.get(i).freeze());
        }
        return aVar;
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freezeIterable(Iterable<E> iterable) {
        f.a aVar = (ArrayList<T>) new ArrayList();
        Iterator<E> it = iterable.iterator();
        while (it.hasNext()) {
            aVar.add(it.next().freeze());
        }
        return aVar;
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freeze(E[] eArr) {
        f.a aVar = (ArrayList<T>) new ArrayList(eArr.length);
        for (E e : eArr) {
            aVar.add(e.freeze());
        }
        return aVar;
    }
}
