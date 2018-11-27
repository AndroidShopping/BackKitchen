package com.shop.backkitchen.httpserver;

/**
 * @author mengjie6
 * @date 2018/11/22
 */

public interface ApiName {
    String BASE = "/shop/";
    String GET_SHOP_CATEGORY = BASE + "getShopCategory";//获取品类
    String ADD_SHOP_CATEGORY = BASE + "addShopCategory";//添加品类
//    String UPDATE_SHOP_CATEGORY = BASE + "updateShopCategory";//更新拼类
//    String DELETE_SHOP_CATEGORY = BASE + "deleteShopCategory";//删除品类

    String GET_SHOP_DETAILS = BASE + "getShopDetails";
    String ADD_SHOP_DETAILS = BASE + "addShopDetails";
    String UPDATE_SHOP_DETAILS = BASE + "updateShopDetails";
//    String DELETE_SHOP_DETAILS = BASE + "deleteShopDetails";

    String GET_SHOP_ORDER = BASE + "getShopOrder";
    String ADD_SHOP_ORDER = BASE + "addShopOrder";
    String UPDATE_SHOP_ORDER = BASE + "updateShopOrder";
    String DELETE_SHOP_ORDER = BASE + "deleteShopOrder";
}
