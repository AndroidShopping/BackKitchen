package com.shop.backkitchen.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.shop.backkitchen.R;
import com.shop.backkitchen.event.StartupServiceEvent;
import com.shop.backkitchen.httpserver.HttpServer;
import com.shop.backkitchen.util.Constant;
import com.shop.backkitchen.util.IpGetUtil;
import com.shop.backkitchen.util.ResourcesUtils;
import com.shop.backkitchen.util.SdkConfig;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;

/**
 * @author mengjie6
 * @date 2018/11/23
 */

public class HttpService extends Service {

    private static final int NOTIFICATION_DOWNLOAD_ID = 0x1000;
    HttpServer mHttpServer = null;
    private boolean isFailure = false;
    private NotificationManager mNotifyManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!IpGetUtil.checkIp(IpGetUtil.getLocalIpAddress()) || !IpGetUtil.checkPort(String.valueOf(Constant.PORT))){
            isFailure = true;
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }
        if (mHttpServer != null) {
            mHttpServer.stop();
            mHttpServer = null;
        }
        try {
            mHttpServer = new HttpServer();
            mHttpServer.start(NanoHTTPD.SOCKET_READ_TIMEOUT, true);
            SdkConfig.saveServiceStatus(true);
            Toast.makeText(this, ResourcesUtils.getString(R.string.service_startup_success), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, ResourcesUtils.getString(R.string.service_startup_failure), Toast.LENGTH_SHORT).show();
            SdkConfig.saveServiceStatus(false);
            isFailure = true;
            stopSelf();
        }
        flags = START_STICKY;
        showNotify();
        return super.onStartCommand(intent, flags, startId);
    }

    private void showNotify() {
        mNotifyManager = (NotificationManager) SdkConfig.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(SdkConfig.getContext());
        builder.setSmallIcon(R.mipmap.ic_launcher_round)//可以换成你的app的logo
                .setTicker(ResourcesUtils.getString(R.string.service_live))
                .setContentTitle(ResourcesUtils.getString(R.string.service_notification))
                .setOngoing(true)
                .setDefaults(Notification.FLAG_SHOW_LIGHTS)
                .setAutoCancel(false)
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .build();
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_NO_CLEAR;
        //设置 Notification 的 flags = FLAG_NO_CLEAR
        //FLAG_AUTO_CANCEL 表示该通知能被状态栏的清除按钮给清除掉
        //等价于 builder.setAutoCancel(true);
        mNotifyManager.notify(NOTIFICATION_DOWNLOAD_ID, notification);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        if (mNotifyManager != null) {
            mNotifyManager.cancel(NOTIFICATION_DOWNLOAD_ID);
            mNotifyManager = null;
        }
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        if (mHttpServer != null) {
            mHttpServer.stop();
            mHttpServer = null;
        }
        if (mNotifyManager != null) {
            mNotifyManager.cancel(NOTIFICATION_DOWNLOAD_ID);
            mNotifyManager = null;
        }
        if (!isFailure) {
            EventBus.getDefault().post(new StartupServiceEvent());
            sendBroadcast(new Intent(ServiceReceiver.SERVICE_RECEIVER));
        }

        super.onDestroy();
    }
}
