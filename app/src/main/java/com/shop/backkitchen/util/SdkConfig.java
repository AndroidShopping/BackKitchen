package com.shop.backkitchen.util;

import android.app.Application;

import com.shop.backkitchen.application.BackKitchenApplication;

/**
 * Created by mengjie6 on 2018/11/24.
 */

public class SdkConfig {
    private static Application context;
    private static BackKitchenApplication activityLifecycleCallbacks;


    public static void config(Application application){
        context = application;
    }

    public static Application getContext() {
        return context;
    }

    public static void init(Application application){
        context = application;
        activityLifecycleCallbacks = new BackKitchenApplication();
    }
}
