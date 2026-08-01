package com.google.firebase.components;

import com.google.firebase.inject.Provider;

/* loaded from: classes.dex */
final class zzh<T> implements Provider<T> {
    private static final Object zza = new Object();
    private volatile Object zzb = zza;
    private volatile Provider<T> zzc;

    /* JADX INFO: Access modifiers changed from: package-private */
    public zzh(final ComponentFactory<T> componentFactory, final ComponentContainer componentContainer) {
        this.zzc = new Provider(componentFactory, componentContainer) { // from class: com.google.firebase.components.zzi
            private final ComponentFactory zza;
            private final ComponentContainer zzb;

            /* JADX INFO: Access modifiers changed from: package-private */
            {
                this.zza = componentFactory;
                this.zzb = componentContainer;
            }

            @Override // com.google.firebase.inject.Provider
            public final Object get() {
                Object create;
                create = this.zza.create(this.zzb);
                return create;
            }
        };
    }

    @Override // com.google.firebase.inject.Provider
    public final T get() {
        T t = (T) this.zzb;
        Object obj = zza;
        if (t == obj) {
            synchronized (this) {
                t = (T) this.zzb;
                if (t == obj) {
                    t = this.zzc.get();
                    this.zzb = t;
                    this.zzc = null;
                }
            }
        }
        return t;
    }
}
