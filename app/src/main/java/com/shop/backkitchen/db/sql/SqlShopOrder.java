package com.shop.backkitchen.db.sql;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.raizlabs.android.dbflow.sql.language.SQLOperator;
import com.shop.backkitchen.db.table.ShopOrder;
import com.shop.backkitchen.db.table.ShopOrder_Table;
import com.shop.backkitchen.util.GsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author mengjie6
 * @date 2018/11/28
 */

public class SqlShopOrder {


    public static List<ShopOrder> getShopOrder(@NonNull SQLOperator... conditions) {
        return SqlHelp.get(ShopOrder.class, conditions);
    }

    public static ShopOrder getShopOrderSingle(@NonNull SQLOperator... conditions) {
        return SqlHelp.getSingle(ShopOrder.class, conditions);
    }

    public static boolean addShopOrder(ShopOrder shopOrder) {
        return SqlHelp.add(shopOrder);
    }

    public static boolean updateShopOrder(String jsonShopOrder) {
        if (TextUtils.isEmpty(jsonShopOrder)) {
            return false;
        }
        return SqlHelp.update(GsonUtils.fromJson(jsonShopOrder, ShopOrder.class));
    }

    public static boolean updateShopOrder(ShopOrder shopOrder) {
        return SqlHelp.update(shopOrder);
    }

    public static long updateShopOrder(SQLOperator whereConditions, @NonNull SQLOperator... conditions) {
        if (whereConditions == null || conditions == null || conditions.length < 1) {
            return -1;
        }
        return SqlHelp.update(ShopOrder.class, whereConditions, conditions);
    }


    public static boolean deleteShopOrder(ShopOrder shopOrder) {
        return SqlHelp.delete(shopOrder);
    }

    public static SQLOperator[] getSQLOperator(Map<String, String> param) {
        if (param == null) {
            return null;
        }
        ArrayList<SQLOperator> sqlOperators = new ArrayList<>();
        for (String key : param.keySet()) {
            switch (key) {
                case "id":
                    sqlOperators.add(getSQLOperatorId(param.get("id")));
                    break;
                case "number":
                    sqlOperators.add(getSQLOperatorName(param.get("number")));
                    break;
                case "price":
                    try {
                        long price = Long.parseLong(param.get("price"));
                        sqlOperators.add(getSQLOperatorPrice(price));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    break;
                case "time":
                    try {
                        long price = Long.parseLong(param.get("time"));
                        sqlOperators.add(getSQLOperatorTime(price));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                case "orderStatus":
                    try {
                        int price = Integer.parseInt(param.get("orderStatus"));
                        sqlOperators.add(getSQLOperatorOrderStatus(price));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        return SqlUtil.operatorLite2Array(sqlOperators);
    }

    public static SQLOperator getSQLOperatorId(String id) {
        try {
            long paramId = Long.parseLong(id);
            return getSQLOperatorId(paramId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static SQLOperator getSQLOperatorId(long id) {
        if (id < 0) {
            return null;
        }
        return ShopOrder_Table.id.eq(id);
    }

    public static SQLOperator getSQLOperatorName(String name) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }
        return ShopOrder_Table.number.eq(name);
    }

    private static SQLOperator getSQLOperatorPrice(long price) {
        if (price < 0) {
            return null;
        }
        return ShopOrder_Table.price.eq(price);
    }

    private static SQLOperator getSQLOperatorTime(long time) {
        if (time < 0) {
            return null;
        }
        return ShopOrder_Table.time.eq(time);
    }

    private static SQLOperator getSQLOperatorOrderStatus(int orderStatus) {
        if (orderStatus < 0) {
            return null;
        }
        return ShopOrder_Table.orderStatus.eq(orderStatus);
    }
}
