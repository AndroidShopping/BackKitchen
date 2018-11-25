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
@Table(database = ShopDataBase.class, name = ShopDataBase.T_SHOP_NAME)
public class ShopName extends BaseModel {

    @PrimaryKey(autoincrement = true)
    @Column
    public long id;//商品id

    @Column
    public long categoryId;//品类id

    @Column
    public long price;//价格

    @Column
    public String name;//菜名

    @Column
    public String description;//描述

    @Column
    public int number = -1;//数量，-1表示不限量

    @Column
    public String picPath;//图片路径

    @Column
    public int isShelf = 0;//表示是否上架  0 上架  1  不上架
}
