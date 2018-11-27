package com.shop.backkitchen.httpserver.dao;

import com.raizlabs.android.dbflow.sql.language.SQLOperator;
import com.shop.backkitchen.db.sql.SqlCategory;
import com.shop.backkitchen.db.table.ShopCategory;

import java.util.List;
import java.util.Map;

/**
 * @author mengjie6
 * @date 2018/11/23
 */

public class CategoryDao {

    public static List<ShopCategory> getCategory(Map<String,String> param){
        SQLOperator[] sqlOperators = SqlCategory.getSQLOperator(param);
        if (sqlOperators == null || sqlOperators.length <=0) {
            return getAllCategory();
        }else {
            return SqlCategory.getSyncCategory(sqlOperators);
        }
    }

    public static List<ShopCategory> getAllCategory(){
        return SqlCategory.getSyncCategory();
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
