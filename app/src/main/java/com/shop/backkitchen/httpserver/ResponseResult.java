package com.shop.backkitchen.httpserver;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.shop.backkitchen.R;
import com.shop.backkitchen.httpserver.dao.CategoryDao;
import com.shop.backkitchen.util.GsonUtils;
import com.shop.backkitchen.util.ResourcesUtils;

import java.util.Map;

/**
 * @author mengjie6
 * @date 2018/11/24
 */

public class ResponseResult {
    public static String getResponse(String uri,Map<String,String> param){
        ResponseBase base = new ResponseBase();
        base.status = 0;
        base.message = ResourcesUtils.getString(R.string.response_msg_success);
        switch (uri) {
            case ApiName.GET_SHOP_CATEGORY:
                base.data = CategoryDao.getCategory(param);
                break;
            case ApiName.ADD_SHOP_CATEGORY:
                //此方法包括了封装返回的接口请求数据和处理异常以及跨域
                if (CategoryDao.addCategory(param)){
                    base.data = ResourcesUtils.getString(R.string.response_add_success);
                }else {
                    base.data = ResourcesUtils.getString(R.string.response_add_failure);
                }
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
}

