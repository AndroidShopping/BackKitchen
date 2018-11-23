package com.shop.backkitchen.db.table;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.shop.backkitchen.db.database.ShopDataBase;

/**
 * @author mengjie6
 * @date 2018/11/22
 */
@Table(database = ShopDataBase.class, name = ShopDataBase.T_SHOP_ORDER)
public class ShopOrder extends BaseModel {

    @PrimaryKey(autoincrement = true)
    @Column
    public long id;//订单id

    @Column
    public long number;//订单编号

    @Column
    public String shopId;//商品id 格式【1，2，3】

    @Column
    public long price;//总价

    @Column
    public long payment;//付款

    @Column
    public int paymentWay;//付款方式

    @Column
    public int orderStatus;//订单状态 1. 未付款  2. 已付款  3. 已取餐  4. 付款失败  5. 订单取消

    @Column
    public String remarks;//订单备注
}