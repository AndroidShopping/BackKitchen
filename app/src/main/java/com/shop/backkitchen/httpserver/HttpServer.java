package com.shop.backkitchen.httpserver;

import android.text.TextUtils;

import com.shop.backkitchen.util.Constant;
import com.shop.backkitchen.util.LogUtil;

import java.io.IOException;
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
    }


    public HttpServer(int port) {
        super(port);
    }

    public HttpServer(String hostname, int port) {
        super(hostname, port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        if (Method.POST.equals(session.getMethod()) && Method.GET.equals(session.getMethod())){
            return addHeaderResponse(Status.REQUEST_ERROR_POST_GET);
        }
        String uri = session.getUri();
        LogUtil.e(TAG,"uri =="+ uri);
        Map<String, String> headers = session.getHeaders();

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
            if (checkUri(uri)) {
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
                    return addHeaderResponse(Status.REQUEST_OK);
                }

                return getParseApi(uri,param);
            } else {
//                //针对的是静态资源的处理
//                String filePath = getFilePath(uri); // 根据url获取文件路径
//
//                if (filePath == null) {
//                    LogUtil.d(TAG, "sd卡没有找到");
//                    return super.serve(session);
//                }
//                File file = new File(filePath);
//
//                if (file != null && file.exists()) {
//                    LogUtil.d(TAG, "file path = " + file.getAbsolutePath());
//                    //根据文件名返回mimeType： image/jpg, video/mp4, etc
//                    String mimeType = getMimeType(filePath);
//
//                    Response res = null;
//                    InputStream is = new FileInputStream(file);
//                    res = newFixedLengthResponse(Response.Status.OK, mimeType, is, is.available());
//                    //下面是跨域的参数（因为一般要和h5联调，所以最好设置一下）
//                    res.addHeader("Access-Control-Allow-Headers", allowHeaders);
//                    res.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, HEAD");
//                    res.addHeader("Access-Control-Allow-Credentials", "true");
//                    res.addHeader("Access-Control-Allow-Origin", "*");
//                    res.addHeader("Access-Control-Max-Age", "" + 42 * 60 * 60);
//                    return res;
//                } else {
//                    LogUtil.d(TAG, "file path = " + file.getAbsolutePath() + "的资源不存在");
//                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //自己封装的返回请求
        return addHeaderResponse(Status.REQUEST_ERROR);
//        return super.serve(session);
    }

    private Response getParseApi(String uri, Map<String,String> param) {
        Status status = Status.REQUEST_OK;
        String mimeType = "application/json;charset=utf-8";
        String msg = ResponseResult.getResponse(uri,param);
        if (TextUtils.isEmpty(msg)){
            msg = status.description;
        }
        return newFixedLengthResponse(status,mimeType,msg);
    }

    private boolean checkUri(String uri) {
        return true;
    }

    private Response addHeaderResponse(Status requestErrorApi) {
        return newFixedLengthResponse(requestErrorApi,NanoHTTPD.MIME_HTML,requestErrorApi.description);
    }

    public enum Status implements Response.IStatus {
        REQUEST_OK(200, "success"),
        REQUEST_BAD(404, "请求失败"),
        REQUEST_ERROR(500, "请求失败"),
        REQUEST_ERROR_API(501, "无效的请求接口"),
        REQUEST_ERROR_CMD(502, "无效命令"),
        REQUEST_ERROR_POST_GET(503, "不是POST或GET请求");

        private final int requestStatus;
        private final String description;

        Status(int requestStatus, String description) {
            this.requestStatus = requestStatus;
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public int getRequestStatus() {
            return requestStatus;
        }
    }

}
