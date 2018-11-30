package com.shop.backkitchen.util;

import com.shop.backkitchen.application.BackKitchenApplication;
import com.shop.backkitchen.event.ServiceStatusEvent;

import org.greenrobot.eventbus.EventBus;

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
}
