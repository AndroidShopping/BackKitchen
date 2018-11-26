package com.shop.backkitchen.httpserver.dao;

import com.shop.backkitchen.db.sql.SqlShopName;
import com.shop.backkitchen.db.table.ShopName;

import java.util.List;
import java.util.Map;

/**
 * @author mengjie6
 * @date 2018/11/25
 */

public class ShopNameDao {

    public static boolean addShopName(Map<String,String> param){
        if (param == null || param.isEmpty()){
            return false;
        }
        ShopName shopName = new ShopName();
        shopName.categoryId = Long.parseLong(param.get("categoryId"));
        shopName.price = Long.parseLong(param.get("price"));
        shopName.name = param.get("name");
        shopName.description = param.get("description");
        shopName.picPath = param.get("picPath");
        int isShelf = 1;
        if (param.containsKey("isShelf")){
            isShelf = Integer.parseInt(param.get("isShelf"));
        }
        shopName.isShelf = isShelf;
        return SqlShopName.addShopName(shopName);
    }

    public static boolean deleteShopName(Map<String,String> param){
        if (param == null || param.isEmpty() || !param.containsKey("id")){
            return false;
        }
        ShopName shopNames =  SqlShopName.getShopNameSingle(SqlShopName.getSQLOperator(param));
        return shopNames == null ? false : shopNames.delete();
    }

    public static void updateShopName(Map<String,String> param){
        if (param == null || param.isEmpty() || !param.containsKey("id")){
             return ;
        }
        String id = param.remove("id");
        SqlShopName.updateShopName(SqlShopName.getSQLOperatorId(id),SqlShopName.getSQLOperator(param));
    }

    public static ShopName getShopNameSingle(Map<String,String> param){
        if (param == null || param.isEmpty() ){
            return null;
        }
        return SqlShopName.getShopNameSingle(SqlShopName.getSQLOperator(param));
    }

    public static List<ShopName> getShopName(Map<String,String> param){
        if (param == null || param.isEmpty() ){
            return getAllShopName();
        }
        return SqlShopName.getShopName(SqlShopName.getSQLOperator(param));
    }

    public static List<ShopName> getAllShopName(){
        List<ShopName> shopNames = SqlShopName.getShopName();
        return SqlShopName.getShopName();
    }

}
