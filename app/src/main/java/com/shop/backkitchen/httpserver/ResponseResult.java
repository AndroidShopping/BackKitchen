package com.shop.backkitchen.httpserver;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.shop.backkitchen.R;
import com.shop.backkitchen.httpserver.dao.CategoryDao;
import com.shop.backkitchen.httpserver.dao.ShopNameDao;
import com.shop.backkitchen.httpserver.dao.ShopOrderDao;
import com.shop.backkitchen.util.GsonUtils;
import com.shop.backkitchen.util.ResourcesUtils;

import java.util.Map;

/**
 * @author mengjie6
 * @date 2018/11/24
 */

public class ResponseResult {
    public static String getResponse(String uri, Map<String, String> param) {
        ResponseBase base = new ResponseBase();
        base.status = 0;
        base.message = ResourcesUtils.getString(R.string.response_msg_success);
        switch (uri) {
            case ApiName.GET_SHOP_CATEGORY:
                base.data = CategoryDao.getCategory(param);
                break;
            case ApiName.ADD_SHOP_CATEGORY:
                if (CategoryDao.addCategory(param)) {
                    base.data = ResourcesUtils.getString(R.string.response_add_success);
                } else {
                    base.data = ResourcesUtils.getString(R.string.response_add_failure);
                }
                break;
            case ApiName.ADD_SHOP_DETAILS:
                if (ShopNameDao.addShopName(param)) {
                    base.data = ResourcesUtils.getString(R.string.response_add_success);
                } else {
                    base.data = ResourcesUtils.getString(R.string.response_add_failure);
                }
                break;
            case ApiName.UPDATE_SHOP_DETAILS:
                if (ShopNameDao.updateShopName(param)) {
                    base.data = ResourcesUtils.getString(R.string.response_add_success);
                } else {
                    base.data = ResourcesUtils.getString(R.string.response_add_failure);
                }
                break;
            case ApiName.GET_SHOP_DETAILS:
                base.data = ShopNameDao.getShopName(param);
                break;
            case ApiName.ADD_SHOP_ORDER:
                base.data = ShopOrderDao.addShopOrder(param);
                if (base.data == null){
                    base.data = ResourcesUtils.getString(R.string.response_add_failure);
                }
                break;
            case ApiName.UPDATE_SHOP_ORDER:
                if (ShopOrderDao.updateShopOrder(param)) {
                    base.data = ResourcesUtils.getString(R.string.response_add_success);
                } else {
                    base.data = ResourcesUtils.getString(R.string.response_add_failure);
                }
                break;
            case ApiName.GET_SHOP_ORDER:
                base.data = ShopOrderDao.getShopOrder(param);
                break;
            default:
                base.status = 1;
                base.message = ResourcesUtils.getString(R.string.response_msg_failure);
                break;
        }
        return GsonUtils.toJson(base);
    }

    static class ResponseBase {
        @Expose
        @SerializedName("status")
        int status;// 0成功  1 失败

        @Expose
        @SerializedName("message")
        String message;//

        @Expose
        @SerializedName("data")
        Object data;
    }

    public static class ShopOrder {
        @Expose
        @SerializedName("number")
        public String number;

        @Expose
        @SerializedName("id")
        public long id;//
    }
}

