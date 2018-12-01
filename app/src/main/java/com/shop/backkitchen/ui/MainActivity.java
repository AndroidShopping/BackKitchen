package com.shop.backkitchen.ui;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.shop.backkitchen.R;
import com.shop.backkitchen.base.BaseActivity;
import com.shop.backkitchen.db.table.ShopCategory;
import com.shop.backkitchen.service.HttpService;
import com.shop.backkitchen.service.ServiceReceiver;
import com.shop.backkitchen.util.ResourcesUtils;
import com.shop.backkitchen.util.SharedPreferencesUtils;

public class MainActivity extends BaseActivity {

    private ServiceReceiver mReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFitSystemWindows(false);
        setContentView(R.layout.activity_main);
        registered();
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
    
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_setup:
                startActivity(new Intent(this,SettingsActivity.class));
                break;
            case R.id.iv_history:
                // TODO: 2018/11/29 历史订单页面
                break;
            case R.id.iv_administration:
                // TODO: 2018/11/29  商品管理页面
                startActivity(new Intent(this,ShopListActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
            mReceiver = null;
        }
    }

    private void registered(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(ServiceReceiver.SERVICE_RECEIVER);
        mReceiver = new ServiceReceiver();
        this.registerReceiver(mReceiver, filter);
    }
}
