package com.shop.backkitchen.db.sql;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.sql.language.SQLOperator;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * @author mengjie6
 * @date 2018/11/23
 */

public class SqlHelp {
    static <T extends BaseModel> List<T> get(Class clazz){
        return SQLite.select().from(clazz).queryList();
    }

    static <T extends BaseModel> List<T> get(Class clazz,@NonNull SQLOperator... conditions){
        return SQLite.select().from(clazz).where(conditions).queryList();
    }

    static <T extends BaseModel> boolean add(T category){
        if (category == null){
            return false;
        }
        return category.save();
    }

    static <T extends BaseModel> boolean update(T category){
        if (category == null){
            return false;
        }
        return category.update();
    }

    static <T extends BaseModel> boolean delete(T category){
        if (category == null){
            return false;
        }
        return category.delete();
    }

}
