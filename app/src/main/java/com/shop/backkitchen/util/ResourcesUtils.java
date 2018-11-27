package com.shop.backkitchen.util;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

/**
 * Created by mengjie6 on 2018/11/24.
 */

public class ResourcesUtils {
    /**
     * 获取String字符串
     */
    public static String getString(int stringResource){
        String str = SdkConfig.getContext().getResources().getString(stringResource);
        return str;
    }

    public static String[] getStringArray(int stringResource){
        String[] str = SdkConfig.getContext().getResources().getStringArray(stringResource);
        return str;
    }

    public static float getDimension(int dimensionRes){
        float value = SdkConfig.getContext().getResources().getDimension(dimensionRes);
        return value;
    }

    public static final int getColor(int colorRes){
        int color = SdkConfig.getContext().getResources().getColor(colorRes);
        return color;
    }

    public static final Drawable getDrawable(int drawRes){
        return SdkConfig.getContext().getResources().getDrawable(drawRes);
    }

    /**
     * 修改颜色透明度
     *
     * @param color 颜色值
     * @param alpha 范围0~255
     * @return
     */
    public static int changeColorAlpha(int color, int alpha) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

}
