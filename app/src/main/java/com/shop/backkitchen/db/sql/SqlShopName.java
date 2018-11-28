package com.shop.backkitchen.db.sql;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.raizlabs.android.dbflow.sql.language.SQLOperator;
import com.shop.backkitchen.db.table.ShopName;
import com.shop.backkitchen.db.table.ShopName_Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author mengjie6
 * @date 2018/11/23
 */

public class SqlShopName {

    public static List<ShopName> getShopName(@NonNull SQLOperator... conditions) {
        return SqlHelp.get(ShopName.class, conditions);
    }

    public static ShopName getShopNameSingle(@NonNull SQLOperator... conditions) {
        return SqlHelp.getSingle(ShopName.class, conditions);
    }

    public static boolean addShopName(ShopName shopName) {
        return SqlHelp.add(shopName);
    }

    public static boolean updateShopName(String jsonShopName) {
        if (TextUtils.isEmpty(jsonShopName)) {
            return false;
        }
        return SqlHelp.update(new Gson().fromJson(jsonShopName, ShopName.class));
    }

    public static boolean updateShopName(ShopName shopName) {
        return SqlHelp.update(shopName);
    }

    public static long updateShopName(SQLOperator whereConditions, @NonNull SQLOperator... conditions) {
        if (whereConditions == null || conditions == null || conditions.length < 1) {
            return -1;
        }
        return SqlHelp.update(ShopName.class, whereConditions, conditions);
    }

    public static boolean deleteShopName(ShopName shopName) {
        return SqlHelp.delete(shopName);
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
                case "categoryId":
                    sqlOperators.add(getSQLOperatorCategoryId(param.get("categoryId")));
                    break;
                case "price":
                    try {
                        long price = Long.parseLong(param.get("price"));
                        sqlOperators.add(getSQLOperatorPrice(price));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    break;
                case "number":
                    try {
                        int number = Integer.parseInt(param.get("number"));
                        sqlOperators.add(getSQLOperatorNumber(number));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    break;
                case "name":
                    sqlOperators.add(getSQLOperatorName(param.get("name")));
                    break;
                case "description":
                    sqlOperators.add(getSQLOperatorDescription(param.get("description")));
                    break;
                case "picPath":
                    sqlOperators.add(getSQLOperatorPicPath(param.get("picPath")));
                    break;
                case "isShelf":
                    try {
                        Integer isShelf = Integer.parseInt(param.get("isShelf"));
                        sqlOperators.add(getSQLOperatorIsShelf(isShelf));
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
        return ShopName_Table.id.eq(id);
    }

    public static SQLOperator getSQLOperatorCategoryId(String categoryId) {
        try {
            long paramCategoryId = Long.parseLong(categoryId);
            return getSQLOperatorCategoryId(paramCategoryId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static SQLOperator getSQLOperatorCategoryId(long categoryId) {
        if (categoryId < 0) {
            return null;
        }
        return ShopName_Table.categoryId.eq(categoryId);
    }

    private static SQLOperator getSQLOperatorPrice(long price) {
        if (price < 0) {
            return null;
        }
        return ShopName_Table.price.eq(price);
    }
    private static SQLOperator getSQLOperatorNumber(int number) {
        return ShopName_Table.number.eq(number);
    }

    private static SQLOperator getSQLOperatorName(String name) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }
        return ShopName_Table.name.eq(name);
    }

    private static SQLOperator getSQLOperatorDescription(String description) {
        if (TextUtils.isEmpty(description)) {
            return null;
        }
        return ShopName_Table.description.eq(description);
    }

    private static SQLOperator getSQLOperatorPicPath(String picPath) {
        if (TextUtils.isEmpty(picPath)) {
            return null;
        }
        return ShopName_Table.picPath.eq(picPath);
    }

    private static SQLOperator getSQLOperatorIsShelf(int isShelf) {
        if (isShelf < 0) {
            return null;
        }
        return ShopName_Table.isShelf.eq(isShelf);
    }

}
