package com.shop.backkitchen.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 服务保活
 * @author mengjie6
 * @date 2018/11/29
 */

public class ServiceReceiver extends BroadcastReceiver {
    public static final String SERVICE_RECEIVER="com.shop.backkitchen.service.servicereceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(SERVICE_RECEIVER)){
            context.startService(new Intent(context, HttpService.class));
        }
    }
}
