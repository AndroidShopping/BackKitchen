package com.shop.backkitchen.permission;

import java.util.Collection;

/**
 *
 */
public class PermissionAccess {
    private static final String TAG = "PermissionAccess";

    private PermissionAccess() {
    }

    public static PermissionAccess getInstance() {
        return InstanceHolder.sPermissionAccess;
    }

    private static class InstanceHolder {
        private static PermissionAccess sPermissionAccess = new PermissionAccess();
    }

    /**
     * 首次进行权限请求
     *
     */
    public void requestPermissionsFirstly() {
        PermissionManager.getInstance().requestPermission(new PermissionListener() {


            @Override
            public void grantedAllPermissions() {
            }

            @Override
            public void deniedPermissions(Collection<PermissionGroup> deniedPermissions, Collection<PermissionGroup> grantedPermissions) {

            }
        }, PermissionGroup.ReadStorage, PermissionGroup.Storage);
    }

    public boolean shouldShowRequestPermissionDialog() {
        return !PermissionManager.getInstance().hasPermission(PermissionGroup.Storage) ||
                !PermissionManager.getInstance().hasPermission(PermissionGroup.ReadStorage);
    }
}
