package com.shop.backkitchen.db.table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.shop.backkitchen.db.database.ShopDataBase;
import com.shop.backkitchen.db.typeconverter.ShopOrderConverter;

import java.util.ArrayList;

/**
 * @author mengjie6
 * @date 2018/11/22
 */
@Table(database = ShopDataBase.class, name = ShopDataBase.T_SHOP_ORDER)
public class ShopOrder extends BaseModel {

    @PrimaryKey(autoincrement = true)
    @Column
    @Expose
    @SerializedName("id")
    public long id;//订单id

    @Column
    @Expose
    @SerializedName("number")
    public String number;//订单编号

    @Column
    @Expose
    @SerializedName("price")
    public long price;//总价

    @Column
    @Expose
    @SerializedName("time")
    public long time;

    @Column
    @Expose
    @SerializedName("orderStatus")
    public int orderStatus = 1;//订单状态 1. 未付款  2. 已付款  3. 已取餐  4. 付款失败  5. 订单取消

    @Column
    @Expose
    @SerializedName("remarks")
    public String remarks;//订单备注

    @Column(typeConverter = ShopOrderConverter.class)
    @Expose
    @SerializedName("shopItem")
    public ArrayList<ShopName> shopItem;//商品id 格式【1，2，3】


}