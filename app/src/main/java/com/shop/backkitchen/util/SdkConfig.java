package com.shop.backkitchen.util;

import android.app.ActivityManager;
import android.content.Context;

import com.shop.backkitchen.application.BackKitchenApplication;
import com.shop.backkitchen.event.ServiceStatusEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by mengjie6 on 2018/11/24.
 */

public class SdkConfig {
    private static BackKitchenApplication context;


    public static void config(BackKitchenApplication application) {
        context = application;
    }

    public static BackKitchenApplication getContext() {
        return context;
    }

    public static void init(BackKitchenApplication application) {
        context = application;
    }

    public static void saveServiceStatus(boolean startup) {
        if (context == null) {
            return;
        }
        context.isServiceStartUp = startup;
        EventBus.getDefault().post(new ServiceStatusEvent(startup));
    }
    public static boolean getServiceStatus() {

        if (context == null){
            return false;
        }
        return context.isServiceStartUp;
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public static boolean isAppOnForeground(Context ctx) {
        ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = ctx.getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName) &&
                    appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

}
