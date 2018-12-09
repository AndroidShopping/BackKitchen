package com.shop.backkitchen.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.security.MessageDigest;
import java.util.Locale;

public class FileUtils {

    private static final String TAG = FileUtils.class.getSimpleName();
    public static final String PATH_ROOT = "/back/kitchen/";
    public static final String PATH_IMAGE = "/service/images";
    private static final String SDCARD_ROOT = Environment
            .getExternalStorageDirectory().toString();


    /**
     * 获取SDCard应用根目录
     *
     * @return
     */
    public static final String getRootFolderPath() {
        String rootFolderPath = SDCARD_ROOT + PATH_ROOT;
        return rootFolderPath;
    }

    /**
     * 获取应用保存图片的目录
     *
     * @return
     */
    public static final String getImagesFolderPath() {
        String imagesFolderPath = SDCARD_ROOT + PATH_ROOT + PATH_IMAGE;
        if (TextUtils.isEmpty(Constant.IMAGE_PATH) || TextUtils.isEmpty(SharedPreferencesUtils.getSetting().image_sd_path.getVal())){
            SharedPreferencesUtils.getSetting().image_sd_path.setVal(imagesFolderPath+"/");
            Constant.IMAGE_PATH = SharedPreferencesUtils.getSetting().image_sd_path.getVal();
        }
        return imagesFolderPath;
    }

    public static void mediaScanFile(Uri path) {
        Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        SdkConfig.getContext().sendBroadcast(localIntent);
    }

    public static File createDir(final String path) {
        final File[] dirs = new File[1];
        if (isSDCardMounted()) {

            dirs[0] = Environment.getExternalStorageDirectory();
            String tempDir = dirs[1].getPath() + path;
            dirs[0] = new File(tempDir);
        } else {
            dirs[0] = SdkConfig.getContext().getFilesDir();
        }
        makDirs(dirs[0]);
        return dirs[0];
    }

    public static byte[] inputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        int ch;
        byte[] data = null;
        try {
            while ((ch = is.read()) != -1) {
                bytestream.write(ch);
            }
            data = bytestream.toByteArray();
        } finally {
            CloseUtils.close(bytestream);
        }
        return data;
    }
    
    public static boolean isSDCardMounted() {
        //Fabric上Environment.getExternalStorageState()报空指针，所以在这里捕获异常做兼容处理。
        try {
            return Environment.MEDIA_MOUNTED.equals(
                    Environment.getExternalStorageState());
        } catch (Exception e) {
            LogUtil.d(TAG, e.getMessage());
            return false;
        }

    }

    public static void makeSureFileExist(File file_) {
        if (!file_.exists()) {
            makeSureParentExist(file_);
            createNewFile(file_);
        }
    }

    public static void createNewFile(File file_) {
        if ((!__createNewFile(file_))) {
            throw new RuntimeException(file_.getAbsolutePath()
                    + " doesn't be created!");
        }
    }

    public static boolean __createNewFile(File file_) {
        makeSureParentExist(file_);
        if (file_.exists()) {
            deleteFile(file_);
        }
        try {
            return file_.createNewFile();
        } catch (IOException e) {
        }

        return false;
    }

    public static void makDirs(File dir_) {
        if ((dir_ != null) && (!dir_.exists()) && (!dir_.mkdirs())) {
            throw new RuntimeException("fail to make " + dir_.getAbsolutePath());
        }
    }

    public static void makeSureFileExist(String filePath_) {
        makeSureFileExist(new File(filePath_));
    }


    public static boolean isExist(File file) {
        return file != null && file.exists();
    }

    public static void deleteFile(File file) {
        if (file != null && file.exists()) {
            file.delete();
        }
    }

    public static void renameFile(File oldFile, File newFile) {
        if (oldFile != null && newFile != null && oldFile.exists()) {
            deleteFile(newFile);
            oldFile.renameTo(newFile);
        }
    }

    /**
     * 复制文件
     *
     * @param src
     * @param dest
     */
    public static boolean copy(File src, File dest) {
        if (src != null && dest != null && FileUtils.isExist(src)) {
            makeSureFileExist(dest);
            FileInputStream fileInputStream = null;
            FileOutputStream fileOutputStream = null;
            try {
                fileInputStream = new FileInputStream(src);
                fileOutputStream = new FileOutputStream(dest);
                return copy(fileInputStream, fileOutputStream);
            } catch (FileNotFoundException e) {
                LogUtil.e(TAG, e.getMessage(), e.getCause());
                return false;
            }
        }
        return false;
    }

    public static boolean copy(InputStream inputStream,
                               OutputStream outputStream) {
        if (inputStream != null && outputStream != null) {
            int size = 512 * 1024;
            if (!(inputStream instanceof BufferedInputStream)) {
                inputStream = new BufferedInputStream(inputStream, size);
            }
            if (!(outputStream instanceof BufferedOutputStream)) {
                outputStream = new BufferedOutputStream(outputStream, size);
            }
            byte buffer[] = new byte[size];
            int len = 0;
            try {
                while ((len = inputStream.read(buffer)) >= 0) {
                    outputStream.write(buffer, 0, len);
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                // #debug
                LogUtil.e(TAG, e.getMessage(), e.getCause());
            } finally {
                CloseUtils.close(inputStream);
                CloseUtils.close(outputStream);
            }
        }
        return false;
    }

    public static void makeSureParentExist(File file_) {
        File parent = file_.getParentFile();
        if ((parent != null) && (!parent.exists())) {
            makDirs(parent);
        }
    }

    public static void makeSureParentExist(String fileName) {
        makeSureParentExist(new File(fileName));
    }

    public static void saveToSDCard(String filename, String content)
            throws Exception {
        File file = new File(Environment.getExternalStorageDirectory(),
                filename);
        FileOutputStream outStream = new FileOutputStream(file);
        outStream.write(content.getBytes());
        outStream.close();
    }
    

    /**
     * 本地文件是否存在
     *
     * @param fileName
     * @return
     */
    public static boolean isInnerFileExist(String fileName) {
        try {
            File innerFile = SdkConfig.getContext().getFileStreamPath(fileName);
            if (innerFile != null && innerFile.exists()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 删除文件
     *
     * @param fileName
     * @return
     */
    public static File getInnerFile(final String fileName) {
        try {
            File innerFile = SdkConfig.getContext().getFileStreamPath(fileName);
            return innerFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 向本地写数据,以追加形式
     */
    public static void saveString2Inner(String fileName, String content) {
        try {
            FileOutputStream fos = SdkConfig.getContext().openFileOutput(
                    fileName, Context.MODE_APPEND);
            PrintStream ps = new PrintStream(fos);
            ps.print(content);
            ps.close();
        } catch (Exception e) {
            LogUtil.e(TAG, "saveString2Inner FileNotFoundException", e);
        }
    }

    /**
     * 根据文件路径读取文件内容feed流顺序表
     *
     * @return
     */
    public static synchronized String readInnerFile(String fileName) {
        Context context = SdkConfig.getContext();
        StringBuilder sb = new StringBuilder(20);
        try {
            if (context.getFileStreamPath(fileName).exists()) {
                //文件存在
                FileInputStream fis = context.openFileInput(
                        fileName);
                byte[] buff = new byte[1024];
                int hasRead = 0;
                while ((hasRead = fis.read(buff)) > 0) {
                    sb.append(new String(buff, 0, hasRead));
                }
            } else {
                LogUtil.e(TAG, "文件【" + context.getFileStreamPath(fileName).getAbsolutePath() + "】不存在！！！");
            }
        } catch (FileNotFoundException e) {
            LogUtil.d(TAG, "readInnerFile FileNotFoundException", e);
        } catch (IOException e) {
            LogUtil.e(TAG, "readInnerFile IOException", e);
        }
        return sb.toString();
    }

    /**
     * 根据文件路径读取文件内容feed流顺序表
     *
     * @return
     */
    public static synchronized String readFileContent(File file) {
        if (file == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(20);
        try {
            if (file.exists()) {
                //文件存在
                FileInputStream fis = new FileInputStream(file);
                byte[] buff = new byte[1024];
                int hasRead = 0;
                while ((hasRead = fis.read(buff)) > 0) {
                    sb.append(new String(buff, 0, hasRead));
                }
            } else {
                LogUtil.e(TAG, "文件【" + file.getAbsolutePath() + "】不存在！！！");
            }
        } catch (FileNotFoundException e) {
            LogUtil.d(TAG, "readInnerFile FileNotFoundException", e);
        } catch (IOException e) {
            LogUtil.e(TAG, "readInnerFile IOException", e);
        }
        return sb.toString();
    }


    /**
     * 根据文件路径读取文件内容feed流顺序表
     *
     * @return
     */
    public static synchronized boolean deleteInnerFile(String fileName) {
        boolean result = false;
        try {
            result = SdkConfig.getContext().deleteFile(fileName);
        } catch (Exception e) {
            LogUtil.e(TAG, "deleteInnerFile Exception", e);
        }

        return result;
    }

    /**
     * 向本地写数据不感兴趣的mid，以覆盖原有形式
     */
    public static void saveMid2Local(String content, String fileName) {
        try {
            FileOutputStream fos = SdkConfig.getContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            PrintStream ps = new PrintStream(fos);
            ps.print(content);
            ps.close();
        } catch (FileNotFoundException e) {
            LogUtil.e(TAG, "saveString2Inner FileNotFoundException");
        }
    }

    /**
     * 获取目录文件大小
     *
     * @param dir
     * @return
     */
    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    dirSize += file.length();
                } else if (file.isDirectory()) {
                    dirSize += file.length();
                    dirSize += getDirSize(file);
                }
            }
        }
        return dirSize;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return B/KB/MB/GB
     */
    public static String formatFileSize(long fileS) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.0");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 清除缓存目录
     *
     * @param dir     目录
     * @param curTime 当前系统时间
     * @return
     */
    public static int clearCacheFolder(File dir, long curTime) {
        int deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, curTime);
                    }
                    if (child.lastModified() < curTime) {
                        String a = child.toString();
                        int index = a.lastIndexOf("/");
                        a = a.substring(index + 1);
                        if (!"volley".endsWith(a) && child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage(), e.getCause());
            }
        }
        return deletedFiles;
    }

    // 获取SD卡剩余空间
    public static long getRemainSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * 删除某个文件夹下的所有文件夹和文件
     *
     * @param delpath String
     * @return boolean
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static boolean deletefile(String delpath) {
        try {

            File file = new File(delpath);
            // 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true
            if (!file.isDirectory()) {
                file.delete();
            } else if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File delfile = new File(delpath + "//" + filelist[i]);
                    if (!delfile.isDirectory()) {
                        delfile.delete();
                    } else if (delfile.isDirectory()) {
                        deletefile(delpath + "//" + filelist[i]);
                    }
                }
                file.delete();
            }

        } catch (Exception e) {
        }
        return true;
    }

    // 创建路径
    public static synchronized void createPath(File file) {
        file.mkdirs();
    }

    /**
     * 创建忽略个图片文件
     * @param filePath
     */
    public static synchronized void createNomedia(String filePath) {
        File nomedia = new File(filePath + "/.nomedia" );
        if (! nomedia.exists())
            try {
                nomedia.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }


    /**
     * 判断字符串是否以".gif"结尾
     *
     * @param str
     * @return
     */
    public static boolean isEndWithGif(String str) {
        if (str != null) {
            return str.toLowerCase(Locale.getDefault()).endsWith(".gif");
        }
        return false;
    }

    /**
     * 提升读写权限
     *
     * @param filePath 文件路径
     * @return
     * @throws IOException
     */
    public static void setFilePermission(String filePath) {
        String command = "chmod " + "777" + " " + filePath;
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断指定的文件夹 如果有则返回其大小  如果没有则返回-1
     *
     * @param fileName 文件路径
     * @return
     * @throws IOException
     */
    public static long getFileSize(String path) {
        long size = -1;
        File file = new File(path);
        if (!file.exists()) {
            size = -1;
        } else if (file.isFile()) {
            size = file.length();
        } else if (file.isDirectory()) {
            size = -1;
        }
        return size;
    }

    /**
     * @param file 将获得md5值
     * @return 获取指定文件的MD5
     */
    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return "";
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return bytesToHexString(digest.digest());
    }
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

}
