package com.shop.backkitchen.httpserver.dao;

import com.shop.backkitchen.db.sql.SqlCategory;
import com.shop.backkitchen.db.table.ShopCategory;
import com.shop.backkitchen.util.GsonUtils;

import java.util.List;
import java.util.Map;

/**
 * @author mengjie6
 * @date 2018/11/23
 */

public class CategoryDao {
    public static String getCategory(Map<String,String> param){
        List<ShopCategory> list = null;
        if (param == null || param.isEmpty()){
             list = SqlCategory.getSyncCategory();

        }
        return GsonUtils.toJson(list);
    }

    public static boolean addCategory(Map<String,String> param){
        if (param == null || param.isEmpty()){
            return false;

        }
        ShopCategory category = new ShopCategory();
        category.name = param.get("name");
        category.description = param.get("description");
        category.picPath = param.get("picPath");
        return SqlCategory.addCategory(category);
    }
}
