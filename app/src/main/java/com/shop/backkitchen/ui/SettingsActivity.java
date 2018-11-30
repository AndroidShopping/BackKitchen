package com.shop.backkitchen.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shop.backkitchen.R;
import com.shop.backkitchen.base.BaseActivity;
import com.shop.backkitchen.event.ServiceStatusEvent;
import com.shop.backkitchen.service.HttpService;
import com.shop.backkitchen.util.Constant;
import com.shop.backkitchen.util.IpGetUtil;
import com.shop.backkitchen.util.ResourcesUtils;
import com.shop.backkitchen.util.SdkConfig;
import com.shop.backkitchen.util.SharedPreferencesUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 *
 */
public class SettingsActivity extends BaseActivity {

    private boolean serviceStatus;


    private TextView tvIp;
    private TextView tvStartService;
    private EditText etPort;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        tvIp = findViewById(R.id.tv_ip);
        tvStartService = findViewById(R.id.tv_start_service);
        etPort = findViewById(R.id.et_port);
        serviceStatus = SdkConfig.getServiceStatus();

        setData();
    }

    private void setData() {
        tvIp.setText(String.valueOf(IpGetUtil.getLocalIpAddress()));
        etPort.setEnabled(serviceStatus);
        etPort.setText(String.valueOf(Constant.PORT));
        if (serviceStatus){
            tvStartService.setText(ResourcesUtils.getString(R.string.service_started_button));
        }else{
            tvStartService.setText(ResourcesUtils.getString(R.string.service_not_started_button));
        }
    }

    @Subscribe
    public void event(ServiceStatusEvent event){
        if (event == null || serviceStatus == event.serviceStatus){
            return;
        }
        serviceStatus = event.serviceStatus;
    }

    protected void onClick(View view) {
       switch (view.getId()){
           case R.id.tv_start_service:
               startService();
               break;
       }
    }

    private void startService() {
        try {
            int port = Integer.parseInt(etPort.getText().toString());
            Constant.PORT = port;
            SharedPreferencesUtils.getSetting().service_port.setVal(Constant.PORT).commit();
            startService(new Intent(this,HttpService.class));
        }catch (NumberFormatException e){
            Toast.makeText(thisContext,ResourcesUtils.getString(R.string.port_error),Toast.LENGTH_SHORT).show();
            return;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
