package com.squareup.tape;

import com.squareup.tape.Task;

/* loaded from: classes.dex */
public interface TaskInjector<T extends Task> {
    void injectMembers(T t);
}
