package com.shop.backkitchen.util;

import java.math.BigDecimal;

/**
 * @author mengjie6
 * @date 2018/12/1
 */

public class BigDecimalUtil {

    /**
     * 提供精确加法计算的add方法
     * @param value1 被加数
     * @param value2 加数
     * @return 两个参数的和
     */
    public static double add(double value1,double value2){
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确加法计算的add方法
     * @param value1 被加数
     * @param value2 加数
     * @return 两个参数的和
     */
    public static long add(long value1,long value2){
        BigDecimal b1 = new BigDecimal(Long.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Long.valueOf(value2));
        return b1.add(b2).longValue();
    }

    /**
     * 提供精确减法运算的sub方法
     * @param value1 被减数
     * @param value2 减数
     * @return 两个参数的差
     */
    public static double sub(double value1,double value2){
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确乘法运算的mul方法
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    public static double mul(double value1,double value2){
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供精确乘法运算的mul方法
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    public static long mul(long value1,int value2){
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.multiply(b2).longValue();
    }
    /**
     * 提供精确乘法运算的mul方法
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    public static long mul(String value1,int value2){
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.multiply(b2).longValue();
    }

    /**
     * 提供精确的除法运算方法div
     * @param value1 被除数
     * @param value2 除数
     * @param scale 精确范围
     * @return 两个参数的商
     * @throws IllegalAccessException
     */
    public static double div(double value1,double value2,int scale) throws IllegalAccessException{
        //如果精确范围小于0，抛出异常信息
        if(scale<0){
            throw new IllegalAccessException("精确度不能小于0");
        }
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.divide(b2, scale).doubleValue();
    }

    /**
     * 提供精确的除法运算方法div
     * @param value1 被除数
     * @param value2 除数
     * @param scale 精确范围
     * @return 两个参数的商
     * @throws IllegalAccessException
     */
    public static double div(long value1){
        BigDecimal b1 = new BigDecimal(Long.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(100));
        return b1.divide(b2, 2).doubleValue();
    }

    /**
     * 将指定的值转换为BigDecimal对象，如果val为null或者为空，那么默认返回0
     * @param val
     * @return
     */
    public static BigDecimal toBigDecimal(String val){
        if(val == null || "".equals(val.trim())){
            return BigDecimal.ZERO;
        }else{
            return new BigDecimal(val);
        }
    }

    /**
     * 判断valA和valB的值是否相等，如果valA和valB的值是否相等，那么返回true，否则返回false
     * @param valA
     * @param valB
     * @author wangjc
     * @date 2014-12-8
     * @return
     */
    public static boolean equals(BigDecimal valA, BigDecimal valB){
        return (valA.compareTo(valB) == 0);
    }

    /**
     * 判断valA和valB的值是否相等，如果valA和valB的值是否相等，那么返回true，否则返回false
     * @param valA
     * @param valB
     * @author wangjc
     * @date 2014-12-8
     * @return
     */
    public static boolean equals(long valA, long valB){
        BigDecimal b1 = new BigDecimal(Long.valueOf(valA));
        BigDecimal b2 = new BigDecimal(Long.valueOf(valB));
        return equals(b1,b2);
    }
}
