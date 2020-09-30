package com.mx.finger.utils;

import android.util.Log;
import android.widget.Button;
import com.mx.finger.BuildConfig;

/**
 * @author zhangyw
 * @date 2019-06-27 13:50
 * @email zyawei@live.com
 */
public class LogUtils {
    private static boolean sEnableLog = BuildConfig.DEBUG;

    public static void logAble(boolean enable) {
        sEnableLog = enable;
    }

    public static void v(String tag, String format, Object... args) {
        println(Log.VERBOSE, tag, String.format(format, args));
    }

    public static void d(String tag, String format, Object... args) {
        println(Log.DEBUG, tag, String.format(format, args));
    }

    public static void i(String tag, String format, Object... args) {
        println(Log.INFO, tag, String.format(format, args));
    }

    public static void w(String tag, String format, Object... args) {
        println(Log.WARN, tag, String.format(format, args));
    }

    public static void e(String tag, String format, Object... args) {
        println(Log.ERROR, tag, String.format(format, args));
    }

    public static void println(int level, String tag, String format, Object... args) {
        if (sEnableLog) {
            Log.println(level, tag, String.format(format, args));
        }
    }
}
