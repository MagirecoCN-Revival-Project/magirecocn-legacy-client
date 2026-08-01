package com.google.firebase.components;

import com.google.firebase.inject.Provider;

/* loaded from: classes.dex */
public interface ComponentContainer {
    <T> T get(Class<T> cls);

    <T> Provider<T> getProvider(Class<T> cls);
}
