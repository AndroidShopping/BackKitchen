package com.shop.backkitchen.util;

/**
 * @author mengjie6
 * @date 2018/12/8
 */

public enum ImageSuffixEnum {
    JPG("jpg"),
    PNG("png"),
    WEP("wep"),
    TXT("txt");

    public String getSuffix() {
        return suffix;
    }

    private String suffix;
    ImageSuffixEnum(String suffix) {
        this.suffix = suffix;
    }
}
