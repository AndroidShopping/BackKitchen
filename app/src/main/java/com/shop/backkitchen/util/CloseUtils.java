package com.shop.backkitchen.util;

import android.os.ParcelFileDescriptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.DatagramSocket;
import java.net.Socket;

public final class CloseUtils {

    public static void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                // #debug
                LogUtil.e("CloseUtils", e.getMessage(), e.getCause());
            }
        }
    }

    public static void close(Writer is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                // #debug
                LogUtil.e("CloseUtils", e.getMessage(), e.getCause());
            }
        }
    }

    public static void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                // #debug
                LogUtil.e("CloseUtils", e.getMessage(), e.getCause());
            }
        }
    }

    public static void close(BufferedReader bufferedReader) {
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                // #debug
                LogUtil.e("CloseUtils", e.getMessage(), e.getCause());
            }
        }
    }

    public static void close(ParcelFileDescriptor pfd) {
        if (pfd != null) {
            try {
                pfd.close();
            } catch (IOException e) {
                // #debug
                LogUtil.e("CloseUtils", e.getMessage(), e.getCause());
            }
        }
    }

    public static void close(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                // #debug
                LogUtil.e("CloseUtils", e.getMessage(), e.getCause());
            }
        }
    }

    public static void close(DatagramSocket datagramSocket) {
        if (datagramSocket != null) {
            try {
                datagramSocket.close();
            } catch (Exception e) {
                // #debug
                LogUtil.e("CloseUtils", e.getMessage(), e.getCause());
            }
        }
    }
}
