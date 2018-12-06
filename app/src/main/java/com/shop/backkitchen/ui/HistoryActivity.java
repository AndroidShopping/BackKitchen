package com.shop.backkitchen.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.shop.backkitchen.R;
import com.shop.backkitchen.adapter.ShopOrderHistoryAdapter;
import com.shop.backkitchen.base.BaseActivity;
import com.shop.backkitchen.db.sql.SqlShopOrder;
import com.shop.backkitchen.db.table.ShopOrder;

import java.util.List;


public class HistoryActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private ShopOrderHistoryAdapter recycleAdapter;
    private QueryTransaction.QueryResultListCallback<ShopOrder> listener = new QueryTransaction.QueryResultListCallback<ShopOrder>() {
        @Override
        public void onListQueryResult(QueryTransaction transaction, @NonNull List<ShopOrder> tResult) {
            if (recycleAdapter == null){
                return;
            }
            recycleAdapter.setShopOrders(tResult);
            recycleAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFitSystemWindows(false);
        setContentView(R.layout.activity_history);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper. VERTICAL);
        recycleAdapter = new ShopOrderHistoryAdapter(thisContext);
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setItemAnimator( new DefaultItemAnimator());
    }

    @Override
    protected void onResume() {
        super.onResume();
        SqlShopOrder.getShopOrderHistory(listener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
