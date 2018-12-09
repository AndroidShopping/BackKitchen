package com.shop.backkitchen.application;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowLog;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.shop.backkitchen.util.Constant;
import com.shop.backkitchen.util.SdkConfig;
import com.shop.backkitchen.util.SharedPreferencesUtils;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * @author mengjie6
 * @date 2018/11/22
 */

public class BackKitchenApplication extends Application {
    public static boolean isServiceStartUp = false;
    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "701338ac35", false);

        FlowManager.init(new FlowConfig.Builder(this).build());
        // add for verbose logging
         FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);
        SdkConfig.init(this);
        Constant.PORT = SharedPreferencesUtils.getSetting().service_port.getVal();
        Constant.IMAGE_PATH = SharedPreferencesUtils.getSetting().image_sd_path.getVal();
    }
}
