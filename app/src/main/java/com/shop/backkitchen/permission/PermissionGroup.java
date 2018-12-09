package com.shop.backkitchen.permission;

import android.Manifest;

import com.shop.backkitchen.R;
/**
 * <p>
 * 权限分组，对Android权限分组的简单封装，方便调用。
 * <p>
 */
public enum PermissionGroup implements Comparable<PermissionGroup> {
    Storage(R.string.permission_storage, Manifest.permission.WRITE_EXTERNAL_STORAGE),
    Camera(R.string.permission_camera, Manifest.permission.CAMERA),
    ReadStorage(R.string.permission_read_external_storage, Manifest.permission.READ_EXTERNAL_STORAGE);

    private int mHintStrId;
    private String mRequestPermission;
    private int mRequestCount;

    PermissionGroup(int hintStrId, String requestPermission) {
        mHintStrId = hintStrId;
        mRequestPermission = requestPermission;
    }


    public String getRequestPermission() {
        return mRequestPermission;
    }

    public static PermissionGroup findPermissionGroupByRequestPermission(String requestPermission) {
        PermissionGroup permissionGroup = null;
        switch (requestPermission) {
            case Manifest.permission.READ_EXTERNAL_STORAGE:
                permissionGroup = ReadStorage;
                break;
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                permissionGroup = Storage;
                break;
            case Manifest.permission.CAMERA:
                permissionGroup = Camera;
                break;
            default:
                break;
        }
        return permissionGroup;
    }


    @Override
    public String toString() {
        return mRequestPermission;
    }
}
