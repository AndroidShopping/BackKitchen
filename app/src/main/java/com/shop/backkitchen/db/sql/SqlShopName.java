package com.shop.backkitchen.db.sql;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.shop.backkitchen.db.table.ShopName;

import java.util.List;

/**
 * @author mengjie6
 * @date 2018/11/23
 */

public class SqlShopName {

    public static List<ShopName> getSyncShopName(){
        return SqlHelp.get(ShopName.class);
    }

    public static boolean addShopName(ShopName shopName){
        return SqlHelp.add(shopName);
    }

    public static boolean updateShopName(String jsonShopName){
        if (TextUtils.isEmpty(jsonShopName)){
            return false;
        }
        return SqlHelp.update(new Gson().fromJson(jsonShopName,ShopName.class));
    }

    public static boolean updateShopName(ShopName shopName){
        return SqlHelp.update(shopName);
    }

    public static boolean deleteShopName(ShopName shopName){
        return SqlHelp.delete(shopName);
    }

}
