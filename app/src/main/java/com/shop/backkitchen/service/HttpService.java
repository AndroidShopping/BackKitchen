package com.shop.backkitchen.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.shop.backkitchen.R;
import com.shop.backkitchen.httpserver.HttpServer;
import com.shop.backkitchen.util.ResourcesUtils;
import com.shop.backkitchen.util.SdkConfig;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;

/**
 * @author mengjie6
 * @date 2018/11/23
 */

public class HttpService extends Service {

    HttpServer mHttpServer = null;
    private boolean isFailure = false;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mHttpServer != null){
            mHttpServer.stop();
            mHttpServer = null;
        }
        try {
//            mHttpServer = new HttpServer("www.backkitchen.com",Constant.PORT);
            mHttpServer = new HttpServer();
            mHttpServer.start(NanoHTTPD.SOCKET_READ_TIMEOUT, true);
            SdkConfig.saveServiceStatus(true);
            Toast.makeText(this,ResourcesUtils.getString(R.string.service_startup_success),Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,ResourcesUtils.getString(R.string.service_startup_failure),Toast.LENGTH_SHORT).show();
            SdkConfig.saveServiceStatus(false);
            isFailure = true;
            stopSelf();
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
        if (!isFailure) {
            sendBroadcast(new Intent(ServiceReceiver.SERVICE_RECEIVER));
        }
        super.onDestroy();
    }
}
