package com.shop.backkitchen.db.typeconverter;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.raizlabs.android.dbflow.converter.TypeConverter;
import com.shop.backkitchen.db.table.ShopName;
import com.shop.backkitchen.util.GsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author houmengjie
 * @date 18/11/28
 */
public class ShopOrderConverter extends TypeConverter<String, ArrayList<ShopName>> {


    @Override
    public String getDBValue(ArrayList<ShopName> shopOrders) {
        if (null == shopOrders && shopOrders.isEmpty()) return null;
        return GsonUtils.toJson(shopOrders);
    }

    @Override
    public ArrayList<ShopName> getModelValue(String data) {
        if (TextUtils.isEmpty(data)) return null;
        return GsonUtils.fromJson(data,new TypeToken<List<ShopName>>(){}.getType());
    }
}
