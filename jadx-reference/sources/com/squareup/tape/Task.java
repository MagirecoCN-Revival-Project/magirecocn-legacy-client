package com.squareup.tape;

import java.io.Serializable;

/* loaded from: classes.dex */
public interface Task<T> extends Serializable {
    void execute(T t);
}
