package androidx.lifecycle;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

/* loaded from: classes.dex */
public interface HasDefaultViewModelProviderFactory {
    CreationExtras getDefaultViewModelCreationExtras();

    ViewModelProvider.Factory getDefaultViewModelProviderFactory();

    /* renamed from: androidx.lifecycle.HasDefaultViewModelProviderFactory$-CC */
    /* loaded from: classes.dex */
    public final /* synthetic */ class CC {
        /* JADX DEBUG: Method not inlined, still used in: [androidx.fragment.app.Fragment.getDefaultViewModelCreationExtras():androidx.lifecycle.viewmodel.CreationExtras] */
        public static CreationExtras $default$getDefaultViewModelCreationExtras(HasDefaultViewModelProviderFactory _this) {
            return CreationExtras.Empty.INSTANCE;
        }
    }
}
