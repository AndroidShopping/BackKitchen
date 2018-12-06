package com.shop.backkitchen.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shop.backkitchen.R;

/**
 * @author mengjie6
 * @date 2018/12/01
 */
public class CommonToast extends Toast {

    /**
     * 纯文字
     */
    public static final int TYPE_TEXT = 0;
    /**
     * 成功
     */
    public static final int TYPE_SUCCESS = 1;
    /**
     * 失败
     */
    public static final int TYPE_FAILED = 2;
    /**
     * 错误
     */
    public static final int TYPE_ERROR = 3;
    /**
     * 纯文字在屏幕下面显示
     */
    public static final int TYPE_TEXT_BOTTOM = 4;


    private static Toast mToast;
    private static AlertDialog mProgressDialog;

    private TextView messageView;
    private ImageView iconView;


    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    public CommonToast(Context context) {
        super(context);
    }

    @Override
    public void setText(int resId) {
        messageView.setText(resId);
    }

    @Override
    public void setText(CharSequence s) {
        messageView.setText(s);
    }

    public void setIcon(int iconResId) {
        this.iconView.setImageResource(iconResId);
    }

    /**
     * 显示Toast
     */
    private static void showToast(Context context, int typeOrResId, CharSequence text) {
        if (mToast != null) {
            mToast.cancel();
        }
        cancelProgressDialog();
        if (typeOrResId == TYPE_TEXT) {
            //纯文字
            mToast = makeTextToast(context, TYPE_TEXT, text, Toast.LENGTH_SHORT);
        } else if (typeOrResId == TYPE_TEXT_BOTTOM) {
            //纯文字在屏幕下方
            mToast = makeTextToast(context, TYPE_TEXT_BOTTOM, text, Toast.LENGTH_SHORT);
        } else {
            //带图
            mToast = makeImageToast(context, typeOrResId, text, Toast.LENGTH_SHORT);
        }
        mToast.show();
    }


    /**
     * 显示加载中dialog
     */
    public static void showProgressDialog(Context context, CharSequence text) {
        //加载中的dialog
        showProgressDialog(context, text, null);
    }

    /**
     * 显示加载中dialog
     */
    public static void showProgressDialog(Context context, CharSequence text, DialogInterface.OnCancelListener cancelListener) {
        //加载中的dialog
        if (mToast != null) {
            mToast.cancel();
        }
        cancelProgressDialog();
        mProgressDialog = makeLoadingDialog((Activity) context, text);
        if (cancelListener != null) {
            //设置cancelListener
            mProgressDialog.setOnCancelListener(cancelListener);
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }


    /**
     * 显示加载中dialog
     */
    public static void cancelProgressDialog() {
        //取消加载中的dialog
        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.cancel();
            }
            mProgressDialog = null;

        }
    }


    private static AlertDialog makeLoadingDialog(Activity context, CharSequence text) {
        LoadingDialog loadingDialog = new LoadingDialog(context, R.style.toast_loading);//创建Dialog并设置样式主题
        loadingDialog.setCanceledOnTouchOutside(false);//设置点击Dialog外部任意区域关闭Dialog
        loadingDialog.setMessage(text);
        return loadingDialog;
    }


    /**
     * 成功的toast
     */
    public static void showSuccessToast(Context context, CharSequence text) {
        showToast(context, TYPE_SUCCESS, text);
    }

    /**
     * 失败的toast
     */
    public static void showFailedToast(Context context, CharSequence text) {
        showToast(context, TYPE_FAILED, text);
    }

    /**
     * 错误的toast
     */
    public static void showErrorToast(Context context, CharSequence text) {
        showToast(context, TYPE_ERROR, text);
    }

    /**
     * 文字Toast
     */
    public static void showTextToast(Context context, CharSequence text) {
        showToast(context, TYPE_TEXT, text);
    }

    /**
     * 文字Toast 屏幕下方
     */
    public static void showTextToastBottom(Context context, CharSequence text) {
        showToast(context, TYPE_TEXT_BOTTOM, text);
    }

    public static void showImageToast(Context context, int imgDrawable, CharSequence text){
        showToast(context, imgDrawable, text);
    }

    /**
     * 纯文字的toast
     */
    private static CommonToast makeTextToast(Context context, int type, CharSequence text, int duration) {
        Context appContext = context.getApplicationContext();
        CommonToast result = new CommonToast(appContext);
        LayoutInflater inflate = (LayoutInflater)
                appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.toast_text, null);
        TextView tv = (TextView) v.findViewById(R.id.message);
        tv.setText(text);
        result.messageView = tv;
        result.setView(v);


        //普通纯文字都显示在中部
        if (type == TYPE_TEXT) {
            result.setGravity(Gravity.CENTER, 0, 0);
        }
        result.setDuration(duration);

        return result;

    }

    public static CommonToast makeImageToast(Context context, int typeOrResId, CharSequence text, int duration) {
        Context appContext = context.getApplicationContext();
        CommonToast result = new CommonToast(appContext);
        LayoutInflater inflate = (LayoutInflater)
                appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.toast_image, null);
        ImageView iv = (ImageView) v.findViewById(R.id.icon);
        TextView tv = (TextView) v.findViewById(R.id.message);

        if (typeOrResId == TYPE_SUCCESS) {
            //成功
            iv.setImageResource(R.drawable.alert_success_icon);
        } else if (typeOrResId == TYPE_FAILED) {
            //失败
            iv.setImageResource(R.drawable.alert_failed_icon);
        } else if (typeOrResId == TYPE_ERROR){
            //错误
            iv.setImageResource(R.drawable.alert_error_icon);
        }else {
            // res id
            iv.setImageResource(typeOrResId);
        }
        result.messageView = tv;
        result.iconView = iv;
        result.setView(v);
        result.setGravity(Gravity.CENTER, 0, 0);

        tv.setText(text);

        result.setDuration(duration);

        return result;

    }


    public static class LoadingDialog extends AlertDialog {

        private CharSequence message;

        public LoadingDialog(Context context, int theme) {
            super(context, theme);
        }

        public LoadingDialog(Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.toast_image);
//            View icon = findViewById(R.id.icon);
//            icon.setVisibility(View.GONE);
            ImageView loadingview = (ImageView) findViewById(R.id.icon);
            loadingview.setImageResource(R.drawable.alert_load_icon);
            TextView tv_message = (TextView) findViewById(R.id.message);
            if (!TextUtils.isEmpty(message)) {
                tv_message.setText(message);
            }


            final RotateAnimation mRotateAnimation = getRotateAnimation();
            loadingview.startAnimation(mRotateAnimation);
        }

        private RotateAnimation getRotateAnimation() {
            final RotateAnimation mRotateAnimation = new RotateAnimation(0.0F, 360.0F, 1, 0.5F, 1, 0.5F);
            mRotateAnimation.setDuration(1000);
            mRotateAnimation.setRepeatCount(-1);
            mRotateAnimation.setRepeatMode(-1);
            mRotateAnimation.setInterpolator(new LinearInterpolator());
            mRotateAnimation.setFillAfter(true);
            mRotateAnimation.setFillEnabled(true);
            return mRotateAnimation;
        }

        @Override
        public void setMessage(CharSequence message) {
            this.message = message;
        }
    }

}
