package com.shop.backkitchen.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.shop.backkitchen.httpserver.HttpServer;

/**
 * @author mengjie6
 * @date 2018/11/23
 */

public class HttpService extends Service {

    HttpServer mHttpServer = null;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mHttpServer == null){
            mHttpServer = new HttpServer();
        }
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        if (mHttpServer !=null){
            mHttpServer.stop();
            mHttpServer = null;
        }
        sendBroadcast(new Intent(ServiceReceiver.SERVICE_RECEIVER));
        super.onDestroy();
    }
}
