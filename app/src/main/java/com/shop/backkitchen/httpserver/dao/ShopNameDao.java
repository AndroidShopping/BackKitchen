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

    public static boolean addShopName(Map<String, String> param) {
        if (param == null || param.isEmpty()) {
            return false;
        }
        ShopName shopName = new ShopName();
        try {
            if (param.containsKey("categoryId")) {
                shopName.categoryId = Long.parseLong(param.get("categoryId"));
            }
            if (param.containsKey("price")) {
                shopName.price = Long.parseLong(param.get("price"));
            }
            if (param.containsKey("name")) {
                shopName.name = param.get("name");
            }
            if (param.containsKey("description")) {
                shopName.description = param.get("description");
            }
            if (param.containsKey("picPath")) {
                shopName.picPath = param.get("picPath");
            }
            int isShelf = 0;
            if (param.containsKey("isShelf")) {
                isShelf = Integer.parseInt(param.get("isShelf"));
            }
            shopName.isShelf = isShelf;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
        return SqlShopName.addShopName(shopName);
    }

    public static boolean deleteShopName(Map<String, String> param) {
        if (param == null || param.isEmpty() || !param.containsKey("id")) {
            return false;
        }
        ShopName shopNames = SqlShopName.getShopNameSingle(SqlShopName.getSQLOperator(param));
        return shopNames == null ? false : shopNames.delete();
    }

    public static boolean updateShopName(Map<String, String> param) {
        if (param == null || param.isEmpty() || !param.containsKey("id")) {
            return false;
        }
        String id = param.remove("id");
        return SqlShopName.updateShopName(SqlShopName.getSQLOperatorId(id), SqlShopName.getSQLOperator(param)) > 0;
    }

    public static ShopName getShopNameSingle(long id) {
        if (id < 0) {
            return null;
        }
        return SqlShopName.getShopNameSingle(SqlShopName.getSQLOperatorId(id));
    }

    public static List<ShopName> getShopName(Map<String, String> param) {
        if (param == null || param.isEmpty()) {
            return getAllShopName();
        }
        return SqlShopName.getShopName(SqlShopName.getSQLOperator(param));
    }

    public static List<ShopName> getAllShopName() {
        return SqlShopName.getShopName();
    }

}
