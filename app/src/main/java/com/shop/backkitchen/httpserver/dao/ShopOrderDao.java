package com.shop.backkitchen.httpserver.dao;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.shop.backkitchen.db.sql.SqlShopName;
import com.shop.backkitchen.db.sql.SqlShopOrder;
import com.shop.backkitchen.db.table.ShopName;
import com.shop.backkitchen.db.table.ShopOrder;
import com.shop.backkitchen.httpserver.ResponseResult;
import com.shop.backkitchen.util.GsonUtils;
import com.shop.backkitchen.util.OrderNumberUtil;

import java.util.List;
import java.util.Map;

/**
 * @author mengjie6
 * @date 2018/11/28
 */

public class ShopOrderDao {

    public static ResponseResult.ShopOrder addShopOrder(Map<String, String> param) {
        if (param == null || param.isEmpty() || !param.containsKey("order") || !param.containsKey("price")) {
            return null;
        }
        ShopOrder shopOrder = new ShopOrder();
        try {
            shopOrder.price = Integer.parseInt(param.get("price"));
        }catch (NumberFormatException e){
            return null;
        }

        shopOrder.shopItem= GsonUtils.fromJson(param.get("order"),new TypeToken<List<ShopName>>(){}.getType());
        if (shopOrder == null || shopOrder.shopItem == null || shopOrder.shopItem.isEmpty()){
            return null;
        }
        ShopName tempShopName;
        long tempPrice = 0l;
        for (ShopName shopName:shopOrder.shopItem) {
            tempShopName = ShopNameDao.getShopNameSingle(shopName.id);
            if (tempShopName == null || tempShopName.isShelf == 1 || tempShopName.price != shopName.price){
                return null;
            }
            tempPrice += shopName.price * shopName.number;
        }
        if (shopOrder.price != tempPrice){
            return null;
        }
        shopOrder.orderStatus = 1;
        shopOrder.time = System.currentTimeMillis();
        shopOrder.number = OrderNumberUtil.getOrderNumber();
        if (SqlShopOrder.addShopOrder(shopOrder)){
            ResponseResult.ShopOrder order = new ResponseResult.ShopOrder();
            order.id = shopOrder.id;
            order.number = shopOrder.number;
            return order;
        }
        return null;
    }

    public static boolean updateShopOrder(Map<String, String> param) {
        if (param == null || param.isEmpty() || !param.containsKey("id")) {
            return false;
        }
        String id = param.remove("id");
        boolean isSuccess =SqlShopOrder.updateShopOrder(SqlShopOrder.getSQLOperatorId(id), SqlShopOrder.getSQLOperator(param)) > 0;
        if (isSuccess){
            ShopOrder shopOrder = getShopOrderSingle(id);
            if (shopOrder != null && shopOrder.orderStatus == 2){
                // TODO: 2018/11/29 通知后厨主页有一个新的订单进来了 
            }
        }
        return isSuccess;
    }
    public static ShopOrder getShopOrderSingle(long id) {
        if (id < 0) {
            return null;
        }
        return SqlShopOrder.getShopOrderSingle(SqlShopName.getSQLOperatorId(id));
    }

    public static ShopOrder getShopOrderSingle(String id) {
        if (TextUtils.isEmpty(id)) {
            return null;
        }
        try {
            return getShopOrderSingle(Integer.parseInt(id));
        }catch (NumberFormatException e){
            return null;
        }

    }

    public static ShopOrder getShopOrderSingle(Map<String, String> param) {
        if (param == null || param.isEmpty()) {
            return null;
        }
        return SqlShopOrder.getShopOrderSingle(SqlShopName.getSQLOperator(param));
    }

    public static List<ShopOrder> getShopOrder(Map<String, String> param) {
        if (param == null || param.isEmpty()) {
            return getAllShopOrder();
        }
        return SqlShopOrder.getShopOrder(SqlShopOrder.getSQLOperator(param));
    }

    public static List<ShopOrder> getAllShopOrder() {
        return SqlShopOrder.getShopOrder();
    }

}
