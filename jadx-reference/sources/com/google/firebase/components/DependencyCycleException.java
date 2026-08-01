package com.google.firebase.components;

import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public class DependencyCycleException extends DependencyException {
    private final List<Component<?>> zza;

    public DependencyCycleException(List<Component<?>> list) {
        super("Dependency cycle detected: " + Arrays.toString(list.toArray()));
        this.zza = list;
    }

    public List<Component<?>> getComponentsInCycle() {
        return this.zza;
    }
}
