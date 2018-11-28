package com.shop.backkitchen.db.sql;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.raizlabs.android.dbflow.sql.language.SQLOperator;
import com.shop.backkitchen.db.table.ShopCategory;
import com.shop.backkitchen.db.table.ShopCategory_Table;
import com.shop.backkitchen.util.GsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author mengjie6
 * @date 2018/11/23
 */

public class SqlCategory {

//    public static List<ShopCategory> getSyncCategory(){
//        return SqlHelp.get(ShopCategory.class);
//    }

    public static List<ShopCategory> getSyncCategory(@NonNull SQLOperator... conditions) {
        return SqlHelp.get(ShopCategory.class, conditions);
    }

    public static boolean addCategory(ShopCategory shopCategory) {
        return SqlHelp.add(shopCategory);
    }

    public static boolean updateCategory(String jsonCategory) {
        if (TextUtils.isEmpty(jsonCategory)) {
            return false;
        }
        return SqlHelp.update(GsonUtils.fromJson(jsonCategory, ShopCategory.class));
    }

    public static boolean updateCategory(ShopCategory shopCategory) {
        return SqlHelp.update(shopCategory);
    }

    public static boolean deleteCategory(ShopCategory shopCategory) {
        return SqlHelp.delete(shopCategory);
    }

    public static SQLOperator[] getSQLOperator(Map<String, String> param) {
        if (param == null) {
            return null;
        }
        ArrayList<SQLOperator> sqlOperators = new ArrayList<>();
        for (String key : param.keySet()) {
            switch (key) {
                case "id":
                    try {
                        long id = Long.parseLong(param.get("id"));
                        sqlOperators.add(getSQLOperatorId(id));
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
            }
        }
        return SqlUtil.operatorLite2Array(sqlOperators);
    }

    private static SQLOperator getSQLOperatorId(long id) {
        if (id < 0) {
            return null;
        }
        return ShopCategory_Table.id.eq(id);
    }

    private static SQLOperator getSQLOperatorName(String name) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }
        return ShopCategory_Table.name.eq(name);
    }

    private static SQLOperator getSQLOperatorDescription(String description) {
        if (TextUtils.isEmpty(description)) {
            return null;
        }
        return ShopCategory_Table.description.eq(description);
    }

    private static SQLOperator getSQLOperatorPicPath(String picPath) {
        if (TextUtils.isEmpty(picPath)) {
            return null;
        }
        return ShopCategory_Table.picPath.eq(picPath);
    }

}
