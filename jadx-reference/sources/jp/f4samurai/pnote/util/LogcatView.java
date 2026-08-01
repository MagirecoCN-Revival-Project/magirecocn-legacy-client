package jp.f4samurai.pnote.util;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* loaded from: classes.dex */
public class LogcatView extends LinearLayout {
    public static final int PARENT_FRAME_LAYOUT = 0;
    public static final int PARENT_RELATIVE_LAYOUT = 1;
    private static final String TAG = "Logcat";
    private LogcatTask mLogcatTask;
    private ScrollView mScrollView;
    private TextView mTextView;

    public LogcatView(Context context) {
        super(context);
        this.mTextView = null;
        this.mScrollView = null;
        this.mLogcatTask = null;
        init();
    }

    public LogcatView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTextView = null;
        this.mScrollView = null;
        this.mLogcatTask = null;
        init();
    }

    public void init() {
        setOrientation(1);
        setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        this.mScrollView = new ScrollView(getContext());
        TextView textView = new TextView(getContext());
        this.mTextView = textView;
        textView.setMovementMethod(new ScrollingMovementMethod());
        this.mTextView.setTextColor(-1);
        this.mTextView.setTypeface(Typeface.MONOSPACE);
        this.mScrollView.addView(this.mTextView, new LinearLayout.LayoutParams(-1, -1));
        addView(this.mScrollView, new LinearLayout.LayoutParams(-1, -1));
    }

    public String getLogLevelChar(int i) {
        return new String[]{"V", "V", "V", "D", "I", "W", "E"}[i];
    }

    public void clearText() {
        this.mTextView.setText("");
        this.mScrollView.fullScroll(130);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        LogcatTask logcatTask = new LogcatTask();
        this.mLogcatTask = logcatTask;
        logcatTask.execute(new Void[0]);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        LogcatTask logcatTask = this.mLogcatTask;
        if (logcatTask != null) {
            logcatTask.cancel(true);
        }
        super.onDetachedFromWindow();
    }

    /* loaded from: classes.dex */
    class LogcatTask extends AsyncTask<Void, String, Void> {
        LogcatTask() {
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                Runtime.getRuntime().exec("logcat -c");
            } catch (IOException unused) {
            }
            LogcatView.this.mTextView.setText("");
        }

        /* JADX DEBUG: Method merged with bridge method: doInBackground([Ljava/lang/Object;)Ljava/lang/Object; */
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(Void... voidArr) {
            String readLine;
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("logcat -v tag -s AndroidRuntime:E Logcat:E Pnote:V GCMBaseIntentService:V GCMBroadcastReceiver:V").getInputStream()), 2048);
                while (!isCancelled() && (readLine = bufferedReader.readLine()) != null) {
                    publishProgress(readLine);
                }
                return null;
            } catch (IOException unused) {
                return null;
            }
        }

        /* JADX DEBUG: Method merged with bridge method: onProgressUpdate([Ljava/lang/Object;)V */
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onProgressUpdate(String... strArr) {
            super.onProgressUpdate((Object[]) strArr);
            LogcatView.this.mTextView.append(strArr[0] + "\n");
            LogcatView.this.mScrollView.fullScroll(130);
        }

        /* JADX DEBUG: Method merged with bridge method: onPostExecute(Ljava/lang/Object;)V */
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Void r1) {
            super.onPostExecute((LogcatTask) r1);
        }
    }
}
