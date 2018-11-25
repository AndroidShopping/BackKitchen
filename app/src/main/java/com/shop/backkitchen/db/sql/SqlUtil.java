package com.shop.backkitchen.db.sql;

import com.raizlabs.android.dbflow.sql.language.SQLOperator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mengjie6
 * @date 2018/11/25
 */

public class SqlUtil {
    public static SQLOperator[] operatorLite2Array(ArrayList<SQLOperator> sqlOperators){
        if (sqlOperators == null || sqlOperators.isEmpty()){
            return null;
        }
        List<SQLOperator> list2 = new ArrayList<>();
        list2.add(null);
        sqlOperators.removeAll(list2);
        return sqlOperators.toArray(new SQLOperator[]{});
    }
}
