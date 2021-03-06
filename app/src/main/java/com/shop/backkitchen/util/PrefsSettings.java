package com.shop.backkitchen.util;

import android.content.Context;


/**
 * 应用设置相关的类
 */
public class PrefsSettings extends CachedPrefs {
    public static final String SETTING_PREFS = "setting";

    public PrefsSettings(Context context) {
        super(context.getSharedPreferences(SETTING_PREFS, Context.MODE_PRIVATE));
    }

    public BoolVal is_init_category = new BoolVal("is_init_category", false);

    public IntVal every_day_order_number = new IntVal("every_day_order_number", 0);

    public LongVal order_number_update_date = new LongVal("order_number_update_date", 0L);

    public IntVal service_port = new IntVal("service_port", 9999);
    public StringVal image_sd_path = new StringVal("image_sd_path", "");
    /**
     * 申请权限的弹出是否出现过，默认为false。
     */
    public BoolVal request_permission_dialog_showed = new BoolVal("request_permission_dialog_showed", false);


}
