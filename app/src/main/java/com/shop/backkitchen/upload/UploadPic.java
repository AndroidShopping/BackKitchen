package com.shop.backkitchen.upload;

import android.text.TextUtils;

import com.shop.backkitchen.R;
import com.shop.backkitchen.util.FileUtils;
import com.shop.backkitchen.util.LogUtil;
import com.shop.backkitchen.util.MD5;
import com.shop.backkitchen.util.ResourcesUtils;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 上传图片
 * @author mengjie6
 * @date 2018/12/1
 */

public class UploadPic {
    public static void upload(String src, final IUploadListener listener) {
        upload(new File(src),listener);
    }
    public static void upload(final File src, final IUploadListener listener){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) {
                if (src == null || !FileUtils.isExist(src)){
                    e.onError(new Throwable(ResourcesUtils.getString(R.string.upload_failure)));
                    return;
                }
                String[] fileNames = src.getAbsolutePath().split("/");
                String fileName = fileName(fileNames[fileNames.length-1]);
                if (TextUtils.isEmpty(fileName)){
                    e.onError(new Throwable(ResourcesUtils.getString(R.string.upload_failure)));
                    return;
                }
                File file = new File(FileUtils.getImagesFolderPath());
                if (!FileUtils.isExist(file)){
                    FileUtils.makDirs(file);
                    FileUtils.createNomedia(file.getAbsolutePath());
                }
                file = new File(file,fileName);
                FileUtils.__createNewFile(file);
                if (FileUtils.copy(src,file)){
                    e.onNext(file.getAbsolutePath());
                }else {
                    e.onError(new Throwable(ResourcesUtils.getString(R.string.upload_failure)));
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String ads) {
                        if (listener != null) {
                            listener.onSuccess(ads);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("", "e.getMessage() =" + e.getMessage());
                        if (listener != null) {
                            listener.onFail(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

    private static String fileName(String fileName){
        int index = fileName.lastIndexOf(".");
        if (index == -1){
            return "";
        }
        String name = fileName.substring(0,index);
        String stff = fileName.substring(index,fileName.length());

        fileName = MD5.hexdigest(name + System.currentTimeMillis())+stff;
        return fileName;
    }

    public interface IUploadListener {
        void onSuccess(String ads);

        void onFail(String message);
    }
}
