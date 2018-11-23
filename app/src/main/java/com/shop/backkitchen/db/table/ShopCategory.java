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
@Table(database = ShopDataBase.class, name = ShopDataBase.T_SHOP_CATEGORY)
public class ShopCategory extends BaseModel {

    @PrimaryKey(autoincrement = true)
    @Column
    public long id;//类别id

    @Column
    public String name;// 类别名称

    @Column
    public String description;//类别的描述

    @Column
    public String picPath;//图片路径
}
