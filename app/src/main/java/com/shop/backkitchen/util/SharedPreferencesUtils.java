package com.shop.backkitchen.util;

/**
 *
 * @author mengjie6
 * @date 2018/11/27
 */

public class SharedPreferencesUtils {
    private static final String TAG = "SharedPreferencesUtil";

    private static PrefsSettings prefsSettings;


    /**
     * 获取setting的Preference
     */
    public static PrefsSettings getSetting() {
        if (prefsSettings == null) {
            prefsSettings = new PrefsSettings(SdkConfig.getContext());
        }
        return prefsSettings;
    }
}
