package com.shop.backkitchen.db.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * @author mengjie6
 * @date 2018/11/22
 */
@Database(name = ShopDataBase.NAME, version = ShopDataBase.VERSION)
public class ShopDataBase {
    public static final String NAME = "shopDataBase";//数据库

    public static final int VERSION = 1;

    public static final String T_SHOP_CATEGORY ="t_shop_category";//商品类别
    public static final String T_SHOP_NAME="t_shop_name";//商品列表
    public static final String T_SHOP_ORDER="t_shop_order";//订单
}
