package com.shop.backkitchen.permission;

import java.util.Collection;
import java.util.List;

/**
 */
public abstract class PermissionListener {
    /**
     * 用户允许所有权限时回调，子类必须覆盖。
            */
    public void grantedAllPermissions() {
    }

    /**
     * 用户拒绝某个权限时回调
     *
     * @param deniedPermissions 拒绝的权限的集合
     * @param grantedPermissions 通过授权的权限的集合
     */
    public void deniedPermissions(Collection<PermissionGroup> deniedPermissions, Collection<PermissionGroup> grantedPermissions) {
    }

    /**
     * 如果用户拒绝过一次或者在应用的权限列表中关闭了该权限，当再次请求该权限时最好给予用户一些解释，
     * 不然如果用户勾选了不再提示复选框，将再也不能通过弹出对话框获的方式获取个权限。
     * 提供默认实现
     *
     * @param shouldShowRequestPermissions
     */
    public void shouldShowRequestPermissionRationale(List<PermissionGroup> shouldShowRequestPermissions) {
//        for (PermissionGroup deniedPermission : shouldShowRequestPermissions) {
//            Toast.makeText(HeadlineApplication.getApplication(),
//                    "需要解释申请原因的权限为：" + HeadlineApplication.getApplication().getString(deniedPermission.getHintStrId()),
//                    Toast.LENGTH_SHORT).show();
//
//        }
    }

}
