package com.shop.backkitchen.ui;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.shop.backkitchen.R;
import com.shop.backkitchen.adapter.ShopOrderAdapter;
import com.shop.backkitchen.base.BaseActivity;
import com.shop.backkitchen.db.sql.SqlShopOrder;
import com.shop.backkitchen.db.table.ShopCategory;
import com.shop.backkitchen.db.table.ShopOrder;
import com.shop.backkitchen.event.ServiceStatusEvent;
import com.shop.backkitchen.service.HttpService;
import com.shop.backkitchen.service.ServiceReceiver;
import com.shop.backkitchen.util.ResourcesUtils;
import com.shop.backkitchen.util.SharedPreferencesUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class MainActivity extends BaseActivity {

    private ServiceReceiver mReceiver;
    private boolean serviceStatus = false;

    private TextView tv_service_status;
    private RecyclerView recyclerView;
    private ShopOrderAdapter recycleAdapter;

    private QueryTransaction.QueryResultListCallback<ShopOrder> listener = new QueryTransaction.QueryResultListCallback<ShopOrder>() {
        @Override
        public void onListQueryResult(QueryTransaction transaction, @NonNull List<ShopOrder> tResult) {
            if (recycleAdapter == null){
                return;
            }
//            boolean is = true;
//            for (ShopOrder order:tResult) {
//                if (is){
//                    order.orderStatus = 2;
//                    order.update();
//                }
//                is = !is;
//            }
            recycleAdapter.setShopOrders(tResult);
            recycleAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        setFitSystemWindows(false);
        setContentView(R.layout.activity_main);
        registered();
        tv_service_status = findViewById(R.id.tv_service_status);
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper. VERTICAL);
        recycleAdapter = new ShopOrderAdapter(thisContext);
        recyclerView.setAdapter(recycleAdapter);
        //设置分隔线
        //recyclerView.addItemDecoration( new DividerGridItemDecoration(this ));
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        setData();
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

    @Override
    protected void onResume() {
        super.onResume();
        SqlShopOrder.getShopOrderMain(listener);
//        SqlShopOrder.getShopOrder(listener);
    }

    @Subscribe
    public void event(ServiceStatusEvent event) {
        if (event == null || serviceStatus == event.serviceStatus) {
            return;
        }
        serviceStatus = event.serviceStatus;
        setData();
    }

    private void setData() {
        if (serviceStatus) {
            tv_service_status.setText(ResourcesUtils.getString(R.string.service_started));
        }else {
            tv_service_status.setText(ResourcesUtils.getString(R.string.service_not_started));
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
        EventBus.getDefault().unregister(this);
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
