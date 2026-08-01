package jp.f4samurai.editbox;

import android.text.InputFilter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import jp.f4samurai.AppActivity;
import jp.f4samurai.editbox.KeyboardLayout;

/* loaded from: classes.dex */
public class EditBoxHelper {
    private static final String TAG = "EditBoxHelper";
    private static EditBox mEditBox;
    private static FrameLayout mFrameLayout;
    private static KeyboardLayout mKeyboardLayout;
    private static AppActivity sAppActivity;

    private static native void editBoxEditingDidEnd(String str);

    public static void _editBoxEditingDidEnd(String str) {
        editBoxEditingDidEnd(str);
    }

    public EditBoxHelper(FrameLayout frameLayout) {
        mFrameLayout = frameLayout;
        sAppActivity = (AppActivity) AppActivity.getContext();
        mEditBox = null;
        mKeyboardLayout = null;
    }

    public static void openEditBox(final String str, final int i, final int i2) {
        sAppActivity.runOnUiThread(new Runnable() { // from class: jp.f4samurai.editbox.EditBoxHelper.1
            @Override // java.lang.Runnable
            public void run() {
                if (EditBoxHelper.mEditBox != null) {
                    return;
                }
                EditBoxHelper.mKeyboardLayout = new KeyboardLayout(EditBoxHelper.sAppActivity);
                EditBoxHelper.mKeyboardLayout.setGravity(83);
                EditBoxHelper.mKeyboardLayout.setListener(new KeyboardLayout.OnKeyboardListener() { // from class: jp.f4samurai.editbox.EditBoxHelper.1.1
                    @Override // jp.f4samurai.editbox.KeyboardLayout.OnKeyboardListener
                    public void onResizeWindow(int i3) {
                        EditBoxHelper.mKeyboardLayout.setPadding(0, 0, 0, i3);
                    }
                });
                int i3 = i;
                EditBoxHelper.mEditBox = new EditBox(EditBoxHelper.sAppActivity, str, i3 != 0 ? i3 != 1 ? 0 : 145 : 1);
                if (i2 > 0) {
                    EditBoxHelper.mEditBox.setFilters(new InputFilter[]{new InputFilter.LengthFilter(i2)});
                }
                int i4 = (int) (EditBoxHelper.sAppActivity.getResources().getDisplayMetrics().density * 10.0f);
                EditBoxHelper.mEditBox.setPadding(i4, i4, i4, i4);
                EditBoxHelper.mKeyboardLayout.addView(EditBoxHelper.mEditBox, new LinearLayout.LayoutParams(-1, -2));
                EditBoxHelper.mFrameLayout.addView(EditBoxHelper.mKeyboardLayout);
                EditBoxHelper.mEditBox.requestFocus();
                EditBoxHelper.showKeyboard(EditBoxHelper.mEditBox);
            }
        });
    }

    public static void closeEditBox() {
        EditBox editBox = mEditBox;
        if (editBox != null) {
            hideKeyboard(editBox);
            mKeyboardLayout.removeView(mEditBox);
            mFrameLayout.removeView(mKeyboardLayout);
            mEditBox = null;
            mKeyboardLayout = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void showKeyboard(View view) {
        if (view != null) {
            ((InputMethodManager) sAppActivity.getSystemService("input_method")).showSoftInput(view, 2);
        }
    }

    private static void hideKeyboard(View view) {
        if (view != null) {
            ((InputMethodManager) sAppActivity.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
            sAppActivity.hideNavigation();
        }
    }

    public void onWindowFocusChanged(boolean z) {
        EditBox editBox;
        if (z || (editBox = mEditBox) == null) {
            return;
        }
        editBox.clearFocus();
    }
}
