package com.shop.backkitchen.db.table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.shop.backkitchen.db.database.ShopDataBase;

import java.util.List;

/**
 * @author mengjie6
 * @date 2018/11/22
 */
@Table(database = ShopDataBase.class, name = ShopDataBase.T_SHOP_CATEGORY)
public class ShopCategory extends BaseModel {

    @PrimaryKey(autoincrement = true)
    @Column
    @Expose
    @SerializedName("id")
    public long id;//类别id

    @Column
    @Expose
    @SerializedName("name")
    public String name;// 类别名称

    @Column
    @Expose
    @SerializedName("description")
    public String description;//类别的描述

    @Column
    @Expose
    @SerializedName("picPath")
    public String picPath;//图片路径

    public List<ShopName> shopNames;

    // needs to be accessible for DELETE

    @OneToMany(methods = {OneToMany.Method.SAVE, OneToMany.Method.DELETE}, variableName = "shopNames")
    public List<ShopName> getshopNames() {
//        if (shopNames == null || shopNames.isEmpty()) {
//            shopNames = SQLite.select()
//                    .from(shopNames.class)
//                    .where(ShopName_Table.queenForeignKeyContainer_id.eq(id))
//                    .queryList();
//        }
        return shopNames;
    }
}
