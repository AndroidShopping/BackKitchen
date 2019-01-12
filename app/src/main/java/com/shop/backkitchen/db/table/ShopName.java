package com.shop.backkitchen.db.table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.shop.backkitchen.db.database.ShopDataBase;

/**
 * @author mengjie6
 * @date 2018/11/22
 */
@Table(database = ShopDataBase.class, name = ShopDataBase.T_SHOP_NAME)
public class ShopName extends BaseModel {

    @PrimaryKey(autoincrement = true)
    @Column
    @Expose
    @SerializedName("id")
    public long id;//商品id

    @Column
    @Expose
    @SerializedName("categoryId")
    public long categoryId;//品类id

    @Column
    @Expose
    @SerializedName("price")
    public long price;//价格

    @Column
    @Expose
    @SerializedName("name")
    public String name;//菜名

    @Column
    @Expose
    @SerializedName("description")
    public String description;//描述

    @Column
    @Expose
    @SerializedName("number")
    public int number = 0;//数量，-1表示不限量

    @Column
    @Expose
    @SerializedName("picPath")
    public String picPath;//图片路径

    @Column
    @Expose
    @SerializedName("isShelf")
    public int isShelf = 0;//表示是否上架  0 上架  1  不上架

    public boolean isFinish =false;
}
