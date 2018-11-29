package com.shop.backkitchen.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.shop.backkitchen.util.SdkConfig;
import com.shop.backkitchen.util.StatusBarUtil;

/**
 * @author mengjie6
 * @date 2018/11/29
 */

public class BaseActivity extends FragmentActivity {

    private static final String TAG = "BaseActivity";
    protected BaseActivity thisContext;
    protected Context appContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        thisContext = this;
        appContext = SdkConfig.getContext();
        super.onCreate(savedInstanceState);
    }

    @Override
    public final void setContentView(int layoutResID) {
        LayoutInflater inflater = (LayoutInflater) thisContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(layoutResID, null);
        this.setContentView(contentView);
    }

    @Override
    public final void setContentView(View view) {
        super.setContentView(view);
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this,false);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this,0x55000000);
        }
    }
}
