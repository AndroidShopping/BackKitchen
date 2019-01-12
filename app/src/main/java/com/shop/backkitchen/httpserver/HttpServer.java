package com.shop.backkitchen.httpserver;

import android.text.TextUtils;

import com.shop.backkitchen.util.Constant;
import com.shop.backkitchen.util.IpGetUtil;
import com.shop.backkitchen.util.LogUtil;
import com.shop.backkitchen.util.ServerImageUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

/**
 * @author mengjie6
 * @date 2018/11/22
 */

public class HttpServer extends NanoHTTPD {
    private static final String TAG = HttpServer.class.getSimpleName();

    public HttpServer() {//初始化端口
        super(Constant.PORT);
        Constant.IP = IpGetUtil.getLocalIpAddress();
    }


    public HttpServer(int port) {
        super(port);
    }

    public HttpServer(String hostname, int port) {
        super(hostname, port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        if (!Method.POST.equals(session.getMethod()) && !Method.GET.equals(session.getMethod())){
            return addHeaderResponse(Response.Status.NOT_FOUND);
        }
        String uri = session.getUri();
        Map<String, String> headers = session.getHeaders();
        LogUtil.e(TAG,uri);
        //接收不到post参数的问题，
        try {
            session.parseBody(new HashMap<String, String>());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResponseException e) {
            e.printStackTrace();
        }
        Map<String, String> param = session.getParms();
        try {
            LogUtil.d(TAG, uri);
            //判断uri的合法性，自定义方法，这个是判断是否是接口的方法
            if (!ServerImageUtil.isImage(ServerImageUtil.getFileType(uri))) {//不是图片 就是接口
                // 针对的是接口的处理
                if (headers != null) {
                    LogUtil.d(TAG, headers.toString());
                }
                if (param != null) {
                    LogUtil.d(TAG, param.toString());
                }

                if (TextUtils.isEmpty(uri)) {
                    throw new RuntimeException("无法获取请求地址");
                }

                if (Method.OPTIONS.equals(session.getMethod())) {
                    LogUtil.d(TAG, "OPTIONS探测性请求");
                    return addHeaderResponse(Response.Status.SWITCH_PROTOCOL);
                }

                return getParseApi(uri,param);
            } else {
                if (uri.indexOf("//") != -1){
                    return super.serve(session);
                }
                //针对的是图片的处理
                String filePath = ServerImageUtil.client2service(uri); // 根据url获取文件路径
                if (filePath == null) {
                    LogUtil.d(TAG, "sd卡没有找到");
                    return getError();
                }
                File file = new File(filePath);
                if (file != null && file.exists()) {
                    LogUtil.d(TAG, "file path = " + file.getAbsolutePath());
                    //根据文件名返回mimeType： image/jpg, video/mp4, etc
                    String mimeType = getMimeTypeForFile(filePath);
                    Response res = null;
                    InputStream is = new FileInputStream(file);
                    res = newFixedLengthResponse(Response.Status.OK, mimeType, is, is.available());
                    return res;
                } else {
                    LogUtil.d(TAG, "file path = " + file.getAbsolutePath() + "的资源不存在");
                    return getError();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //自己封装的返回请求
        return addHeaderResponse(Response.Status.INTERNAL_ERROR);
    }

    private Response getParseApi(String uri, Map<String,String> param) {
        Response.Status status = Response.Status.OK;
        String mimeType = "application/json;charset=utf-8";
        String msg = ResponseResult.getResponse(uri,param);
        if (TextUtils.isEmpty(msg)){
            msg = status.getDescription();
        }
        return newFixedLengthResponse(status,mimeType,msg);
    }

    private Response getError() {
        Response.Status status = Response.Status.INTERNAL_ERROR;
        String mimeType = "application/json;charset=utf-8";
        String msg =status.getDescription();
        return newFixedLengthResponse(status,mimeType,msg);
    }

    private Response addHeaderResponse(Response.Status requestErrorApi) {
        return newFixedLengthResponse(requestErrorApi,NanoHTTPD.MIME_HTML,requestErrorApi.getDescription());
    }
}
