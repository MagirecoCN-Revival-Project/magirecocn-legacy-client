package com.google.firebase.components;

import com.google.firebase.inject.Provider;

/* loaded from: classes.dex */
public abstract /* synthetic */ class ComponentContainer$$CC {
    public static Object get(ComponentContainer componentContainer, Class cls) {
        Provider provider = componentContainer.getProvider(cls);
        if (provider == null) {
            return null;
        }
        return provider.get();
    }
}
