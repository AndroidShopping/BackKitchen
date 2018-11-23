package com.shop.backkitchen.db.sql;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.shop.backkitchen.db.table.ShopCategory;

import java.util.List;

/**
 * @author mengjie6
 * @date 2018/11/23
 */

public class SqlCategory {

    public static List<ShopCategory> getSyncCategory(){
        return SqlHelp.get(ShopCategory.class);
    }

    public static boolean addCategory(ShopCategory shopCategory){
        return SqlHelp.add(shopCategory);
    }

    public static boolean updateCategory(String jsonCategory){
        if (TextUtils.isEmpty(jsonCategory)){
            return false;
        }
        return SqlHelp.update(new Gson().fromJson(jsonCategory,ShopCategory.class));
    }

    public static boolean updateCategory(ShopCategory shopCategory){
        return SqlHelp.update(shopCategory);
    }

    public static boolean deleteCategory(ShopCategory shopCategory){
        return SqlHelp.delete(shopCategory);
    }

}
