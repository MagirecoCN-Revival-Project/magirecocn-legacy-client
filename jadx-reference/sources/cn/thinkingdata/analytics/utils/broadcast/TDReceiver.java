package cn.thinkingdata.analytics.utils.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import cn.thinkingdata.analytics.TDFirstEvent;
import cn.thinkingdata.analytics.TDOverWritableEvent;
import cn.thinkingdata.analytics.TDUpdatableEvent;
import cn.thinkingdata.analytics.ThinkingAnalyticsEvent;
import cn.thinkingdata.analytics.ThinkingAnalyticsSDK;
import cn.thinkingdata.analytics.utils.j;
import cn.thinkingdata.analytics.utils.p;
import cn.thinkingdata.core.router.TRouterMap;
import java.util.Date;
import java.util.TimeZone;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class TDReceiver extends BroadcastReceiver {
    private static volatile TDReceiver a;

    public static synchronized TDReceiver a() {
        TDReceiver tDReceiver;
        synchronized (TDReceiver.class) {
            if (a == null) {
                synchronized (TDReceiver.class) {
                    if (a == null) {
                        a = new TDReceiver();
                    }
                }
            }
            tDReceiver = a;
        }
        return tDReceiver;
    }

    public static void a(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        String d = p.d(context);
        String str = "cn.thinkingdata.receiver";
        if (d.length() != 0) {
            str = d + TRouterMap.DOT + "cn.thinkingdata.receiver";
        }
        intentFilter.addAction(str);
        if (Build.VERSION.SDK_INT >= 33) {
            context.registerReceiver(a(), intentFilter, 2);
        } else {
            context.registerReceiver(a(), intentFilter);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:75:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x00dd  */
    @Override // android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onReceive(Context context, Intent intent) {
        ThinkingAnalyticsSDK sharedInstance;
        JSONObject jSONObject;
        Date date;
        JSONObject jSONObject2;
        JSONObject jSONObject3;
        int intExtra = intent.getIntExtra("TD_ACTION", 0);
        String stringExtra = intent.getStringExtra("#app_id");
        if (stringExtra == null || stringExtra.length() <= 0 || (sharedInstance = ThinkingAnalyticsSDK.sharedInstance(context, stringExtra)) == null) {
            return;
        }
        r4 = null;
        JSONObject jSONObject4 = null;
        r4 = null;
        JSONObject jSONObject5 = null;
        ThinkingAnalyticsEvent thinkingAnalyticsEvent = null;
        switch (intExtra) {
            case 1048578:
                String stringExtra2 = intent.getStringExtra("properties");
                long longExtra = intent.getLongExtra("TD_DATE", 0L);
                String stringExtra3 = intent.getStringExtra("TD_KEY_TIMEZONE");
                if (stringExtra2 != null) {
                    try {
                        jSONObject = new JSONObject(stringExtra2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    date = longExtra != 0 ? new Date(longExtra) : null;
                    TimeZone defaultTimeZone = sharedInstance.mConfig.getDefaultTimeZone();
                    if (stringExtra3 != null) {
                        defaultTimeZone = TimeZone.getTimeZone(stringExtra3);
                    }
                    String stringExtra4 = intent.getStringExtra("#event_name");
                    if (date != null) {
                        sharedInstance.track(stringExtra4, jSONObject);
                        return;
                    } else {
                        sharedInstance.track(stringExtra4, jSONObject, date, defaultTimeZone);
                        return;
                    }
                }
                jSONObject = null;
                if (longExtra != 0) {
                }
                TimeZone defaultTimeZone2 = sharedInstance.mConfig.getDefaultTimeZone();
                if (stringExtra3 != null) {
                }
                String stringExtra42 = intent.getStringExtra("#event_name");
                if (date != null) {
                }
            case 1048579:
            case 1048580:
            case 1048581:
                String stringExtra5 = intent.getStringExtra("#event_name");
                String stringExtra6 = intent.getStringExtra("properties");
                long longExtra2 = intent.getLongExtra("TD_DATE", 0L);
                String stringExtra7 = intent.getStringExtra("TD_KEY_TIMEZONE");
                if (stringExtra6 != null) {
                    try {
                        jSONObject2 = new JSONObject(stringExtra6);
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                    Date date2 = longExtra2 == 0 ? new Date(longExtra2) : null;
                    TimeZone timeZone = stringExtra7 == null ? TimeZone.getTimeZone(stringExtra7) : null;
                    String stringExtra8 = intent.getStringExtra("TD_KEY_EXTRA_FIELD");
                    if (intExtra != 1048579) {
                        TDFirstEvent tDFirstEvent = new TDFirstEvent(stringExtra5, jSONObject2);
                        thinkingAnalyticsEvent = tDFirstEvent;
                        if (stringExtra8 != null) {
                            thinkingAnalyticsEvent = tDFirstEvent;
                            if (stringExtra8.length() > 0) {
                                tDFirstEvent.setFirstCheckId(stringExtra8);
                                thinkingAnalyticsEvent = tDFirstEvent;
                            }
                        }
                    } else if (intExtra == 1048581) {
                        thinkingAnalyticsEvent = new TDOverWritableEvent(stringExtra5, jSONObject2, stringExtra8);
                    } else if (intExtra == 1048580) {
                        thinkingAnalyticsEvent = new TDUpdatableEvent(stringExtra5, jSONObject2, stringExtra8);
                    }
                    if (thinkingAnalyticsEvent == null) {
                        thinkingAnalyticsEvent.setEventTime(date2, timeZone);
                        sharedInstance.track(thinkingAnalyticsEvent);
                        return;
                    }
                    return;
                }
                jSONObject2 = null;
                if (longExtra2 == 0) {
                }
                if (stringExtra7 == null) {
                }
                String stringExtra82 = intent.getStringExtra("TD_KEY_EXTRA_FIELD");
                if (intExtra != 1048579) {
                }
                if (thinkingAnalyticsEvent == null) {
                }
            case 1048582:
                String stringExtra9 = intent.getStringExtra("properties");
                if (stringExtra9 != null) {
                    try {
                        jSONObject5 = new JSONObject(stringExtra9);
                    } catch (JSONException e3) {
                        e3.printStackTrace();
                    }
                }
                sharedInstance.autoTrack(intent.getStringExtra("#event_name"), jSONObject5);
                return;
            default:
                switch (intExtra) {
                    case 2097152:
                        String stringExtra10 = intent.getStringExtra("properties");
                        long longExtra3 = intent.getLongExtra("TD_DATE", 0L);
                        if (stringExtra10 != null) {
                            try {
                                jSONObject3 = new JSONObject(stringExtra10);
                            } catch (JSONException e4) {
                                e4.printStackTrace();
                            }
                            sharedInstance.user_operations(j.a(intent.getStringExtra("TD_KEY_USER_PROPERTY_SET_TYPE")), jSONObject3, longExtra3 != 0 ? new Date(longExtra3) : null);
                            return;
                        }
                        jSONObject3 = null;
                        sharedInstance.user_operations(j.a(intent.getStringExtra("TD_KEY_USER_PROPERTY_SET_TYPE")), jSONObject3, longExtra3 != 0 ? new Date(longExtra3) : null);
                        return;
                    case 2097153:
                        String stringExtra11 = intent.getStringExtra("properties");
                        if (stringExtra11 != null) {
                            try {
                                jSONObject4 = new JSONObject(stringExtra11);
                            } catch (JSONException e5) {
                                e5.printStackTrace();
                            }
                        }
                        sharedInstance.setSuperProperties(jSONObject4);
                        return;
                    case 2097154:
                        sharedInstance.login(intent.getStringExtra("#account_id"));
                        return;
                    case 2097155:
                        sharedInstance.logout();
                        return;
                    case 2097156:
                        sharedInstance.identify(intent.getStringExtra("#distinct_id"));
                        return;
                    case 2097157:
                        sharedInstance.flush();
                        return;
                    case 2097158:
                        sharedInstance.unsetSuperProperty(intent.getStringExtra("properties"));
                        return;
                    case 2097159:
                        sharedInstance.clearSuperProperties();
                        return;
                    default:
                        return;
                }
        }
    }
}
