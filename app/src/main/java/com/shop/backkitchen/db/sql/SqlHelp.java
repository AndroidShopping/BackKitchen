package com.shop.backkitchen.db.sql;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.sql.language.SQLOperator;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.util.List;

/**
 * @author mengjie6
 * @date 2018/11/23
 */

public class SqlHelp {

    static  <T extends BaseModel> void getAsync(Class clazz, @NonNull QueryTransaction.QueryResultListCallback<T> queryResultListCallback){
        SQLite.select()
                .from(clazz)
                .async()
                .queryListResultCallback(queryResultListCallback)
                .execute();
    }

    static  <T extends BaseModel> void getAsync(Class clazz, @NonNull QueryTransaction.QueryResultListCallback<T> queryResultListCallback,@NonNull SQLOperator... conditions){
        SQLite.select()
                .from(clazz)
                .where(conditions)
                .async()
                .queryListResultCallback(queryResultListCallback)
                .execute();
    }

    static <T extends BaseModel> List<T> get(Class clazz,@NonNull SQLOperator... conditions){
        return SQLite.select().from(clazz).where(conditions).queryList();
    }

    static <T extends BaseModel> T getSingle(Class clazz,@NonNull SQLOperator... conditions){
        return  (T)SQLite.select().from(clazz).where(conditions).querySingle();
    }

    static <T extends BaseModel> boolean add(T category){
        if (category == null){
            return false;
        }
        return category.save();
    }

    static long update(Class clazz,SQLOperator whereConditions ,@NonNull SQLOperator... conditions){
        return SQLite.update(clazz).set(conditions).where(whereConditions).executeUpdateDelete();
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
