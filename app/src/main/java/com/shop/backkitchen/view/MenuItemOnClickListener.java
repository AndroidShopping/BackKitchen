package com.shop.backkitchen.view;

import android.util.Log;
import android.view.View;

/**
 * @author mengjie6
 * @date 2018/12/2
 */

public abstract class MenuItemOnClickListener implements View.OnClickListener {
    public MenuItemOnClickListener(BottomMenuFragment _bottomMenuFragment, MenuItem _menuItem) {
        this.bottomMenuFragment = _bottomMenuFragment;
        this.menuItem = _menuItem;
    }
    private final String TAG = "MenuItemOnClickListener";

    public BottomMenuFragment getBottomMenuFragment() {
        return bottomMenuFragment;
    }

    public void setBottomMenuFragment(BottomMenuFragment bottomMenuFragment) {
        this.bottomMenuFragment = bottomMenuFragment;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    private BottomMenuFragment bottomMenuFragment;
    private MenuItem menuItem;

    @Override
    public void onClick(View v){

        Log.i(TAG, "onClick: ");

        if(bottomMenuFragment != null && bottomMenuFragment.isVisible()) {
            bottomMenuFragment.dismiss();
        }

        this.onClickMenuItem(v, this.menuItem);
    }
    public abstract void onClickMenuItem(View v, MenuItem menuItem);
}
