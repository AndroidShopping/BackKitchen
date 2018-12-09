package com.shop.backkitchen.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.shop.backkitchen.R;
import com.shop.backkitchen.adapter.ShopAdapter;
import com.shop.backkitchen.base.BaseActivity;
import com.shop.backkitchen.db.sql.SqlShopName;
import com.shop.backkitchen.db.table.ShopName;

import java.util.List;

/**
 * 商品列表页面
 *
 * @author mengjie6
 * @date 2018/12/01
 */
public class ShopListActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private ShopAdapter recycleAdapter;
    private QueryTransaction.QueryResultListCallback<ShopName> listener = new QueryTransaction.QueryResultListCallback<ShopName>() {
        @Override
        public void onListQueryResult(QueryTransaction transaction, @NonNull List<ShopName> tResult) {
            if (recycleAdapter == null) {
                return;
            }
            recycleAdapter.setShopNames(tResult);
            recycleAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        findViewById(R.id.iv_add).setVisibility(View.VISIBLE);
        findViewById(R.id.iv_add).setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        findViewById(R.id.iv_back).setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recycleAdapter = new ShopAdapter(thisContext);
        recyclerView.setAdapter(recycleAdapter);
        //设置分隔线
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    protected void onResume() {
        super.onResume();
        SqlShopName.getShopName(listener);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_add:
                startActivity(new Intent(thisContext, ShopAddActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
