package com.shop.backkitchen.httpserver.dao;

import android.text.TextUtils;

import com.raizlabs.android.dbflow.sql.language.SQLOperator;
import com.shop.backkitchen.db.sql.SqlCategory;
import com.shop.backkitchen.db.table.ShopCategory;
import com.shop.backkitchen.db.table.ShopName;
import com.shop.backkitchen.util.IpGetUtil;
import com.shop.backkitchen.util.ServerImageUtil;

import java.util.List;
import java.util.Map;

/**
 * @author mengjie6
 * @date 2018/11/23
 */

public class CategoryDao {
    public static List<ShopCategory> getCategory(){
        return getCategory(null);
    }

    public static List<ShopCategory> getCategory(Map<String,String> param){
        SQLOperator[] sqlOperators = SqlCategory.getSQLOperator(param);
        List<ShopCategory> list;
        if (sqlOperators == null || sqlOperators.length <=0) {
            list = getAllCategory();
        }else {
            list = SqlCategory.getSyncCategory(sqlOperators);
        }
        if (list !=null && !list.isEmpty()){
            for (ShopCategory category:list) {
                if (category == null){
                    continue;
                }
                if (!TextUtils.isEmpty(category.picPath)){
                    category.picPath = IpGetUtil.getHost(ServerImageUtil.service2client(category.picPath));
                }
                if (category.shopNames != null && !category.shopNames.isEmpty()){
                    for (ShopName shopName:category.shopNames) {
                        if (shopName == null){
                            continue;
                        }
                        shopName.picPath = IpGetUtil.getHost(ServerImageUtil.service2client(shopName.picPath));
                    }
                }
            }
        }
        return list;
    }

    private static List<ShopCategory> getAllCategory(){
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
