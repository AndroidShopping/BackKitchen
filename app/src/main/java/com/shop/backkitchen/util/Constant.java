package com.shop.backkitchen.util;

/**
 * 一些常量
 */

public class Constant {
    public static final String TAG = "backKitchen";
    public static int INVALID = -1;

    /**
     * 网络状态的一些类型
     */
    public static final class Network {
        public static final int NETWORK_UNKNOWN = 0;
        public static final int NETWORK_WIFI = 1;
        public static final int NETWORK_2_G = 2;
        public static final int NETWORK_3_G = 3;
        public static final int NETWORK_4_G = 4;
    }

    /**
     *{@link android.content.SharedPreferences} 将数据保存在SharedPreferences中，
     */
    public static final class SP {
        public static final String SP_AD_INTERCEPTION = "sp_ad_interception";//保存用户是否启用弹窗广告拦截
        public static final String SP_TROJAN_INTERCEPTION = "sp_trojan_interception";//保存用户是否启用木马链接拦截
    }

    public static final class Time {
        public static final int MINUTE = 1000 * 60;
        public static final int HOUR = MINUTE * 60;
        public static final int DAY = HOUR * 24;
    }

    /**
     * message的type。主要使用在See{@link android.os.Handler}
     */
    public static final class Message {
        public static final int MESSAGE_LONG_CLICK = 1;//网页的长按事件
    }

    /**
     * EventBus对应的type
     */
    public static final class Event {
        public static final int EVENT_DELETE_AD_HOST = 1;//删除对应host的广告
        public static final int EVENT_OPEN_HISTORICAL_RECORDS = EVENT_DELETE_AD_HOST + 1; //打开历史纪录
        public static final int EVENT_DELETE_TROJAN_PATH = EVENT_OPEN_HISTORICAL_RECORDS + 1; //删除对应host的木马
        public static final int EVENT_ADD_TROJAN_PATH = EVENT_DELETE_TROJAN_PATH + 1; //添加对应host的木马
    }

}
