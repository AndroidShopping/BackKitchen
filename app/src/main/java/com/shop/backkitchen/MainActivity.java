package com.shop.backkitchen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.shop.backkitchen.service.HttpService;
import com.shop.backkitchen.util.IpGetUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView)findViewById(R.id.tv_ip)).setText(IpGetUtil.getLocalIpAddress());
        startService(new Intent(this,HttpService.class));
    }
}
