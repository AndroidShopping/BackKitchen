package com.shop.backkitchen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.shop.backkitchen.db.table.ShopCategory;
import com.shop.backkitchen.service.HttpService;
import com.shop.backkitchen.util.IpGetUtil;
import com.shop.backkitchen.util.ResourcesUtils;
import com.shop.backkitchen.util.SharedPreferencesUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView)findViewById(R.id.tv_ip)).setText(IpGetUtil.getLocalIpAddress());
        startService(new Intent(this,HttpService.class));
        if (!SharedPreferencesUtils.getSetting().is_init_category.getVal()){
            SharedPreferencesUtils.getSetting().is_init_category.setVal(true).commit();
            String[] baseCategory =ResourcesUtils.getStringArray(R.array.shopCategory);
            for (String name:baseCategory) {
                ShopCategory shopCategory = new ShopCategory();
                shopCategory.name = name;
                shopCategory.save();
            }
        }
    }
}
