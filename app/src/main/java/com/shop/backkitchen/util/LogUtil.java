package com.shop.backkitchen.util;

import android.util.Log;

import com.shop.backkitchen.BuildConfig;

public class LogUtil {

    public static final void v(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static final void v(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, msg, tr);
        }
    }

    public static final void d(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static final void d(String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(Constant.TAG, msg);
        }
    }

    public static final void d(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg, tr);
        }
    }

    public static final void i(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static final void i(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, msg, tr);
        }
    }

    public static final void w(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, msg);
        }
    }

    public static final void w(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, msg, tr);
        }
    }

    public static final void e(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static final void e(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg, tr);
        }
    }

}
