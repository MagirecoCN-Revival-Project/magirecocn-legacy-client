package cn.thinkingdata.analytics.e;

import android.content.Context;
import android.content.res.Resources;
import android.os.Process;
import cn.thinkingdata.analytics.TDPresetProperties;
import cn.thinkingdata.analytics.ThinkingAnalyticsSDK;
import cn.thinkingdata.analytics.crash.CrashLogListener;
import cn.thinkingdata.analytics.utils.f;
import cn.thinkingdata.analytics.utils.k;
import cn.thinkingdata.core.utils.TDLog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.Arrays;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class a {
    private static a c;
    private final Context a;
    private boolean b;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: cn.thinkingdata.analytics.e.a$a, reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public class C0006a implements CrashLogListener {

        /* renamed from: cn.thinkingdata.analytics.e.a$a$a, reason: collision with other inner class name */
        /* loaded from: classes.dex */
        class C0007a implements ThinkingAnalyticsSDK.l {
            final /* synthetic */ String a;
            final /* synthetic */ File b;

            C0007a(C0006a c0006a, String str, File file) {
                this.a = str;
                this.b = file;
            }

            @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK.l
            public void process(ThinkingAnalyticsSDK thinkingAnalyticsSDK) {
                if (thinkingAnalyticsSDK.shouldTrackCrash()) {
                    try {
                        JSONObject jSONObject = new JSONObject();
                        try {
                            if (this.a.getBytes("UTF-8").length > 16384) {
                                if (!TDPresetProperties.disableList.contains("#app_crashed_reason")) {
                                    jSONObject.put("#app_crashed_reason", new String(f.a(this.a, 16384), "UTF-8"));
                                }
                            } else if (!TDPresetProperties.disableList.contains("#app_crashed_reason")) {
                                jSONObject.put("#app_crashed_reason", this.a);
                            }
                        } catch (UnsupportedEncodingException unused) {
                            if (this.a.length() > 8192 && !TDPresetProperties.disableList.contains("#app_crashed_reason")) {
                                jSONObject.put("#app_crashed_reason", this.a.substring(0, 8192));
                            }
                        }
                        thinkingAnalyticsSDK.trackAppCrashAndEndEvent(jSONObject);
                        this.b.delete();
                    } catch (JSONException unused2) {
                    }
                }
            }
        }

        C0006a(a aVar) {
        }

        @Override // cn.thinkingdata.analytics.crash.CrashLogListener
        public void onFile(File file) {
            ThinkingAnalyticsSDK.allInstances(new C0007a(this, a.a(file), file));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class b implements Runnable {
        b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            a aVar = a.this;
            aVar.a(aVar.a);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class c implements CrashLogListener {

        /* renamed from: cn.thinkingdata.analytics.e.a$c$a, reason: collision with other inner class name */
        /* loaded from: classes.dex */
        class C0008a implements ThinkingAnalyticsSDK.l {
            final /* synthetic */ String a;
            final /* synthetic */ File b;

            C0008a(c cVar, String str, File file) {
                this.a = str;
                this.b = file;
            }

            @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK.l
            public void process(ThinkingAnalyticsSDK thinkingAnalyticsSDK) {
                if (thinkingAnalyticsSDK.shouldTrackCrash()) {
                    try {
                        JSONObject jSONObject = new JSONObject();
                        try {
                            if (this.a.getBytes("UTF-8").length > 16384) {
                                if (!TDPresetProperties.disableList.contains("#app_crashed_reason")) {
                                    jSONObject.put("#app_crashed_reason", new String(f.a(this.a, 16384), "UTF-8"));
                                }
                            } else if (!TDPresetProperties.disableList.contains("#app_crashed_reason")) {
                                jSONObject.put("#app_crashed_reason", this.a);
                            }
                        } catch (UnsupportedEncodingException unused) {
                            if (this.a.length() > 8192 && !TDPresetProperties.disableList.contains("#app_crashed_reason")) {
                                jSONObject.put("#app_crashed_reason", this.a.substring(0, 8192));
                            }
                        }
                        thinkingAnalyticsSDK.autoTrack("ta_app_crash", jSONObject);
                        this.b.delete();
                    } catch (JSONException unused2) {
                    }
                }
            }
        }

        c(a aVar) {
        }

        @Override // cn.thinkingdata.analytics.crash.CrashLogListener
        public void onFile(File file) {
            ThinkingAnalyticsSDK.allInstances(new C0008a(this, a.a(file), file));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class d implements Thread.UncaughtExceptionHandler {
        private final Thread.UncaughtExceptionHandler a = Thread.getDefaultUncaughtExceptionHandler();

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: cn.thinkingdata.analytics.e.a$d$a, reason: collision with other inner class name */
        /* loaded from: classes.dex */
        public class C0009a implements ThinkingAnalyticsSDK.l {
            final /* synthetic */ String a;

            C0009a(d dVar, String str) {
                this.a = str;
            }

            @Override // cn.thinkingdata.analytics.ThinkingAnalyticsSDK.l
            public void process(ThinkingAnalyticsSDK thinkingAnalyticsSDK) {
                if (thinkingAnalyticsSDK.shouldTrackCrash()) {
                    try {
                        JSONObject jSONObject = new JSONObject();
                        try {
                            if (this.a.getBytes("UTF-8").length > 16384) {
                                if (!TDPresetProperties.disableList.contains("#app_crashed_reason")) {
                                    jSONObject.put("#app_crashed_reason", new String(f.a(this.a, 16384), "UTF-8"));
                                }
                            } else if (!TDPresetProperties.disableList.contains("#app_crashed_reason")) {
                                jSONObject.put("#app_crashed_reason", this.a);
                            }
                        } catch (UnsupportedEncodingException unused) {
                            TDLog.d("ThinkingAnalytics.ExceptionHandler", "Exception occurred in getBytes. ");
                            if (this.a.length() > 8192 && !TDPresetProperties.disableList.contains("#app_crashed_reason")) {
                                jSONObject.put("#app_crashed_reason", this.a.substring(0, 8192));
                            }
                        }
                        thinkingAnalyticsSDK.trackAppCrashAndEndEvent(jSONObject);
                    } catch (JSONException unused2) {
                    }
                }
            }
        }

        d() {
            Thread.setDefaultUncaughtExceptionHandler(this);
        }

        private void a() {
            Process.killProcess(Process.myPid());
            System.exit(10);
        }

        private void a(Throwable th) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            do {
                th.printStackTrace(printWriter);
                th = th.getCause();
            } while (th != null);
            printWriter.close();
            ThinkingAnalyticsSDK.allInstances(new C0009a(this, stringWriter.toString().replaceAll("(\r\n|\n\r|\n|\r)", "<br>")));
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public void uncaughtException(Thread thread, Throwable th) {
            boolean z;
            Throwable th2 = th;
            while (true) {
                if (th2 == null) {
                    z = true;
                    break;
                } else {
                    if (th2 instanceof k) {
                        z = false;
                        break;
                    }
                    th2 = th2.getCause();
                }
            }
            if (z) {
                a(th);
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.a;
            if (uncaughtExceptionHandler != null) {
                uncaughtExceptionHandler.uncaughtException(thread, th);
            } else {
                a();
            }
        }
    }

    private a(Context context) {
        this.a = context.getApplicationContext();
    }

    static String a(File file) {
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader = null;
        try {
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader(file));
                while (true) {
                    try {
                        String readLine = bufferedReader2.readLine();
                        if (readLine == null) {
                            break;
                        }
                        stringBuffer.append(readLine);
                        stringBuffer.append("\n");
                    } catch (IOException e) {
                        e = e;
                        bufferedReader = bufferedReader2;
                        e.printStackTrace();
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        return stringBuffer.toString();
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader = bufferedReader2;
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        throw th;
                    }
                }
                bufferedReader2.close();
                String stringBuffer2 = stringBuffer.toString();
                try {
                    bufferedReader2.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
                return stringBuffer2;
            } catch (IOException e5) {
                e = e5;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Context context) {
        File[] listFiles;
        String str = context.getCacheDir().getAbsolutePath() + File.separator + "tacrash";
        c cVar = new c(this);
        File file = new File(str);
        if (!file.exists() || (listFiles = file.listFiles()) == null) {
            return;
        }
        for (File file2 : listFiles) {
            cVar.onFile(file2);
        }
    }

    public static a b(Context context) {
        if (c == null) {
            if (context == null) {
                return null;
            }
            synchronized (d.class) {
                if (c == null) {
                    c = new a(context);
                }
            }
        }
        return c;
    }

    public synchronized void a() {
        if (!this.b) {
            ArrayList arrayList = new ArrayList();
            try {
                Resources resources = this.a.getResources();
                arrayList.addAll(Arrays.asList(resources.getStringArray(resources.getIdentifier("TACrashConfig", "array", this.a.getPackageName()))));
            } catch (Exception unused) {
            }
            if (arrayList.isEmpty()) {
                new d();
            } else {
                C0006a c0006a = new C0006a(this);
                new Thread(new b()).start();
                try {
                    Class<?> cls = Class.forName("cn.thinkingdata.android.crash.TACrash");
                    Object invoke = cls.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
                    cls.getMethod("init", Context.class).invoke(invoke, this.a);
                    cls.getMethod("enableLog", new Class[0]).invoke(invoke, new Object[0]);
                    if (arrayList.contains("java")) {
                        cls.getMethod("initJavaCrashHandler", Boolean.TYPE).invoke(invoke, true);
                    }
                    if (arrayList.contains("anr") || arrayList.contains("native")) {
                        cls.getMethod("initNativeCrashHandler", Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE).invoke(invoke, true, true, true, true);
                        if (arrayList.contains("anr")) {
                            cls.getMethod("initANRHandler", new Class[0]).invoke(invoke, new Object[0]);
                        }
                    }
                    cls.getMethod("initCrashLogListener", CrashLogListener.class).invoke(invoke, c0006a);
                } catch (Exception unused2) {
                }
            }
            this.b = true;
        }
    }
}
