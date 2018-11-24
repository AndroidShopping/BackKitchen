package com.shop.backkitchen.application;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowLog;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.shop.backkitchen.util.SdkConfig;

/**
 * @author mengjie6
 * @date 2018/11/22
 */

public class BackKitchenApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(new FlowConfig.Builder(this).build());
        // add for verbose logging
         FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);
        SdkConfig.init(this);
    }
}
