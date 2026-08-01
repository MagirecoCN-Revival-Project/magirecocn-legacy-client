package jp.f4samurai.editbox;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.view.ViewCompat;
import jp.f4samurai.AppActivity;

/* loaded from: classes.dex */
public class EditBox extends AppCompatEditText {
    private static final String TAG = "EditBox";
    private AppActivity activity;
    private String mText;

    public EditBox(Context context, String str, int i) {
        super(context);
        this.activity = null;
        this.activity = (AppActivity) context;
        this.mText = str;
        setBackgroundColor(-1);
        setTextColor(ViewCompat.MEASURED_STATE_MASK);
        setText(str);
        setInputType(i);
        setImeOptions(33554432);
        setImeOptions(268435462);
        setTextIsSelectable(true);
        setFocusableInTouchMode(true);
        setFocusable(true);
        addTextChangedListener(new TextWatcher() { // from class: jp.f4samurai.editbox.EditBox.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                EditBox.this.mText = charSequence.toString();
            }
        });
        setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: jp.f4samurai.editbox.EditBox.2
            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view, boolean z) {
                if (z) {
                    return;
                }
                EditBoxHelper._editBoxEditingDidEnd(EditBox.this.mText);
                EditBoxHelper.closeEditBox();
            }
        });
        setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: jp.f4samurai.editbox.EditBox.3
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                if (i2 != 6) {
                    return false;
                }
                View currentFocus = EditBox.this.activity.getCurrentFocus();
                if (currentFocus == null) {
                    return true;
                }
                currentFocus.clearFocus();
                return true;
            }
        });
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onKeyPreIme(int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0 && i == 4) {
            View currentFocus = this.activity.getCurrentFocus();
            if (currentFocus == null) {
                return true;
            }
            currentFocus.clearFocus();
            return true;
        }
        return super.onKeyPreIme(i, keyEvent);
    }
}
