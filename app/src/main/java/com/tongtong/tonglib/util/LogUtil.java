package com.tongtong.tonglib.util;

import android.util.Log;

import java.util.Locale;

/**
 * Created by KOHeeJin on 2016-06-21.
 */
public class LogUtil {
    public static String TAG = "LogUtil";

    public static final int useNoLog = -1;
    public static final int useVerifyLog = 1;
    public static final int useDebugLog = 2;
    public static final int useErrorLog = 3;
    public static final int useALLLog = 9;

    private static final int DEFAULT_LOG_LEVEL = useALLLog;
    private static int mLogLevel = DEFAULT_LOG_LEVEL;

    public static void setLogLevel(int logLevel) { mLogLevel = logLevel; }
    // public static boolean DEBUG = Log.isLoggable(TAG, Log.VERBOSE);

    /**
     * Customize the log tag for your application, so that other apps
     * using Volley don't mix their logs with yours.
     * <br />
     * Enable the log property for your tag before starting your app:
     * <br />
     * {@code adb shell setprop log.tag.&lt;tag&gt;}
     */

    public static void setTag(String tag) {
        d("Changing log tag to %s", tag);
        TAG = tag;

        // Reinitialize the DEBUG "constant"
        // DEBUG = Log.isLoggable(TAG, Log.VERBOSE);
    }

    public static void v(String format, Object... args) {
        if (mLogLevel >= useVerifyLog) {
            Log.v(TAG, buildMessage(format, args));
        }
    }

    public static void d(String format, Object... args) {
        if (mLogLevel >= useDebugLog) {
            Log.d(TAG, buildMessage(format, args));
        }
    }

    public static void e(String format, Object... args) {
        if (mLogLevel >= useErrorLog) {
            Log.e(TAG, buildMessage(format, args));
        }
    }

    public static void e(Throwable tr, String format, Object... args) {
        if (mLogLevel >= useErrorLog) {
            Log.e(TAG, buildMessage(format, args), tr);
        }
    }

    public static void wtf(String format, Object... args) {
        Log.wtf(TAG, buildMessage(format, args));
    }

    public static void wtf(Throwable tr, String format, Object... args) {
        Log.wtf(TAG, buildMessage(format, args), tr);
    }

    /**
     * Formats the caller's provided message and prepends useful info like
     * calling thread ID and method name.
     */
    private static String buildMessage(String format, Object... args) {
        String msg = (args == null) ? format : String.format(Locale.KOREAN, format, args);
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();

        String caller = "<unknown>";
        // Walk up the stack looking for the first caller outside of VolleyLog.
        // It will be at least two frames up, so start there.
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(LogUtil.class)) {
                String callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
                callingClass = callingClass.substring(callingClass.lastIndexOf('$') + 1);

                caller = callingClass + "." + trace[i].getMethodName();
                break;
            }
        }

        return String.format(Locale.KOREAN, "[%d] %s: %s", Thread.currentThread().getId(), caller, msg);
    }
}
