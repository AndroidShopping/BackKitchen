package com.shop.backkitchen.util;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * @author mengjie6
 * @date 2018/12/1
 */

public class CurrencyUtil {

    /**
     * 货币格式
     *￥12,345,678.90
     */

    public static String numberFormatMoney(String money){
        try {
            return numberFormatMoney(Long.parseLong(money));
        }catch (NumberFormatException e){
            e.printStackTrace();
            return "";
        }
    }

    public static String numberFormatMoney(long money){
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(); //建立货币格式化引用
        if(money > 0){
            return currencyFormat.format(new BigDecimal(money));
        }
        return "";
    }

    public static String numberFormatMoney(double money){
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(); //建立货币格式化引用
        if(money > 0){
            return currencyFormat.format(new BigDecimal(money));
        }
        return "";
    }
}
