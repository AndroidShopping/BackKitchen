package com.shop.backkitchen.util;

import android.text.TextUtils;

import com.shop.backkitchen.R;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ip的工具类
 *
 * @author mengjie6
 * @date 2018/11/23
 */

public class IpGetUtil {
    private static final Pattern IPV4_PATTERN = Pattern
            .compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");

    public static String getLocalIpAddress() {
        try {
            String ipv4;
            List<NetworkInterface> nilist = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface ni : nilist) {
                List<InetAddress> ialist = Collections.list(ni.getInetAddresses());
                for (InetAddress address : ialist) {
                    if (!address.isLoopbackAddress() && isIPv4Address(ipv4 = address.getHostAddress())) {
                        return ipv4;
                    }
                }
            }

        } catch (SocketException ex) {
        }
        return null;
    }

    public static String getHost(String fileName){
        if (TextUtils.isEmpty(fileName)){
            return "";
        }
        StringBuffer buffer = new StringBuffer("http://");
        buffer.append(Constant.IP);
        buffer.append(":");
        buffer.append(Constant.PORT);
        if (!fileName.startsWith("/")){
            buffer.append("/");
        }
        buffer.append(fileName);
        return buffer.toString();
    }

    private static boolean isIPv4Address(String input) {
        return IPV4_PATTERN.matcher(input).matches();
    }

    public static boolean checkPort(String input) {
        if (TextUtils.isEmpty(input)) {
            CommonToast.showTextToast(SdkConfig.getContext(), ResourcesUtils.getString(R.string.port_not_null));
            return false;
        }
        String port = "([0-9]|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-4]\\d{4}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])";//限定输入格式
        Pattern p = Pattern.compile(port);
        Matcher m = p.matcher(input);
        boolean b = m.matches();
        if (!b) {
            CommonToast.showTextToast(SdkConfig.getContext(), ResourcesUtils.getString(R.string.port_format_error));
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkIp(String ipStr) {
        if (TextUtils.isEmpty(ipStr)) {
            CommonToast.showTextToast(SdkConfig.getContext(), ResourcesUtils.getString(R.string.ip_not_null));
            return false;
        }
        /*正则表达式*/
        String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";//限定输入格式
        Pattern p = Pattern.compile(ip);
        Matcher m = p.matcher(ipStr);
        boolean b = m.matches();
        if (!b) {
            CommonToast.showTextToast(SdkConfig.getContext(), ResourcesUtils.getString(R.string.ip_format_error));
            return false;
        } else {
            return true;
        }
    }
}
