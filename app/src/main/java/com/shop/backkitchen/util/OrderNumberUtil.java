package com.shop.backkitchen.util;

import java.text.DecimalFormat;

/**
 * @author mengjie6
 * @date 2018/11/28
 */

public class OrderNumberUtil {
    public static String getOrderNumber(){
        int number = SharedPreferencesUtils.getSetting().every_day_order_number.getVal();
        if (number >= 999){
            number = 1;
        }else {
            number ++;
        }
        SharedPreferencesUtils.getSetting().every_day_order_number.setVal(number).commit();
        DecimalFormat mFormat = new DecimalFormat("000");//确定格式，把1转换为001
        return mFormat.format(number);
    }
}
