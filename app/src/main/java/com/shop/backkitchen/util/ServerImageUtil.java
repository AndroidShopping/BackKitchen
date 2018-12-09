package com.shop.backkitchen.util;

import android.text.TextUtils;

/**
 * @author mengjie6
 * @date 2018/12/8
 */

public class ServerImageUtil {
    public static String service2client(String path) {
        if (TextUtils.isEmpty(path)) {
            return "";
        }
        String suffix = getFileType(path);
        if (TextUtils.isEmpty(suffix)) {
            return "";
        }
        if (!isImage(suffix)) {
            return "";
        }
        return path.replace(Constant.IMAGE_PATH, "");
    }

    public static String client2service(String path) {

        return Constant.IMAGE_PATH + "/" + path;
    }

    //JPG("jpg"),
    //PNG("png"),
    //WEP("wep"),
    //TXT("txt");
    public static String getFileType(String fileName) {
        String[] strArray = fileName.split("\\.");
        int suffixIndex = strArray.length - 1;
        System.out.println(strArray[suffixIndex]);
        return strArray[suffixIndex];
    }

    public static boolean isImage(String fileName) {
        switch (fileName) {
            case "jpg":
                return true;
            case "jpeg":
                return true;
            case "png":
                return true;
            case "wep":
                return true;
            default:
                return false;
        }
    }
}
