package jp.f4samurai.editbox;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: EditBoxHelper.java */
/* loaded from: classes.dex */
public class KeyboardLayout extends LinearLayout {
    private OnKeyboardListener listener;

    /* compiled from: EditBoxHelper.java */
    /* loaded from: classes.dex */
    public interface OnKeyboardListener {
        void onResizeWindow(int i);
    }

    public KeyboardLayout(Context context) {
        super(context);
    }

    public void setListener(OnKeyboardListener onKeyboardListener) {
        this.listener = onKeyboardListener;
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        if (this.listener != null) {
            int size = View.MeasureSpec.getSize(i2);
            Activity activity = (Activity) getContext();
            Rect rect = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            int i3 = size - (rect.top + rect.bottom);
            if (i3 > 100) {
                this.listener.onResizeWindow(i3);
            }
        }
        super.onMeasure(i, i2);
    }
}
