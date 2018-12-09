package com.shop.backkitchen.permission;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.shop.backkitchen.util.LogUtil;
import com.shop.backkitchen.util.SdkConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;


/**
 * 
 */
public class PermissionManager {
    private static final String TAG = "PermissionManager";
    private Map<Long, PermissionListener> mListenerMap;

    private PermissionManager() {
        mListenerMap = new HashMap<>();
    }

    public static PermissionManager getInstance() {
        return InstanceHolder.sPermissionManager;
    }

    private static class InstanceHolder {
        private static PermissionManager sPermissionManager = new PermissionManager();
    }

    /**
     * @param permissionListener
     * @param permissionGroups
     */
    public void requestPermission(PermissionListener permissionListener, PermissionGroup... permissionGroups) {
        TreeSet<PermissionGroup> permissionSet = filterData(permissionListener, permissionGroups);
        doRequestPermission(permissionListener, permissionSet);
    }

    private TreeSet<PermissionGroup> filterData(PermissionListener permissionListener,
                                                PermissionGroup... permissionGroups) {
        if (permissionGroups == null || permissionGroups.length == 0 || permissionListener == null) {
            throw new RuntimeException(TAG + " :请至少传入一个权限");
        }

        //使用变长参数设计为了方便调用，但为了防止调用方传入重复的权限，用set来去重。
        TreeSet<PermissionGroup> permissionSet = new TreeSet<>();
        for (PermissionGroup permissionGroup : permissionGroups) {
            permissionSet.add(permissionGroup);
        }
        return permissionSet;
    }


    /**
     * @param permissionListener
     * @param permissionGroups
     */
    private void doRequestPermission(PermissionListener permissionListener,
                                     TreeSet<PermissionGroup> permissionGroups) {
        LogUtil.i(TAG, "doRequestPermission()");

        ArrayList<String> notGrantedPermissions = new ArrayList<>();
        ArrayList<String> grantedPermissions = new ArrayList<>();

        for (PermissionGroup permissionGroup : permissionGroups) {
            String requestPermission = permissionGroup.getRequestPermission();
            boolean granted = false;

            //调用ActivityCompat.checkSelfPermission()在Fabric上报
            // Caused by java.lang.RuntimeException: Unknown exception code: 1 msg null
            //在这里做容错处理
            try {
                granted = ActivityCompat.checkSelfPermission(SdkConfig.getContext(), requestPermission) ==
                        PackageManager.PERMISSION_GRANTED;
            } catch (Exception e) {
                LogUtil.e(TAG, "ActivityCompat.checkSelfPermission()调用异常", e);
            }

            if (granted) {
                grantedPermissions.add(requestPermission);
            } else {
                notGrantedPermissions.add(requestPermission);
            }
        }

        LogUtil.i(TAG,
                "doRequestPermission()，notGrantedPermissions.isEmpty()=" + notGrantedPermissions.isEmpty());
        if (notGrantedPermissions.isEmpty()) {
            //请求的权限已经全部授权，直接回调给调用方。
            LogUtil.i(TAG, "doRequestPermission()直接调用permissionListener.grantedAllPermissions()。");
            permissionListener.grantedAllPermissions();
        } else {
            if (SdkConfig.isAppOnForeground(SdkConfig.getContext())) {
                //我们app在前台，申请权限。
                long requestTime = System.currentTimeMillis();
                mListenerMap.put(requestTime, permissionListener);
                PermissionActivity.skip(requestTime, notGrantedPermissions, grantedPermissions);
            } else {
                //我们app在后台，不申请权限，直接回调给调用方。
                permissionListener.deniedPermissions(convertRequestPermissionsToPermissisonGroups(notGrantedPermissions), convertRequestPermissionsToPermissisonGroups(grantedPermissions));
            }
        }
    }


    void deniedPermissions(long requestTime, ArrayList<String> deniedPermissions, ArrayList<String> grantedPermissions) {
        PermissionListener permissionListener = mListenerMap.get(requestTime);
        if (permissionListener != null) {
            permissionListener.deniedPermissions(convertRequestPermissionsToPermissisonGroups(deniedPermissions), convertRequestPermissionsToPermissisonGroups(grantedPermissions));
        }

    }

    void grantedAllPermissions(long requestTime) {
        LogUtil.i(TAG, "grantedAllPermissions  = " + mListenerMap.get(requestTime));
        PermissionListener permissionListener = mListenerMap.get(requestTime);
        if (permissionListener != null) {
            permissionListener.grantedAllPermissions();
        }
    }

    void shouldShowRequestPermissionRationale(long requestTime,
                                              List<String> shouldShowRequestPermissions) {
        LogUtil.i(TAG, "shouldShowRequestPermissionRationale  = " + mListenerMap.get(requestTime));
        PermissionListener permissionListener = mListenerMap.get(requestTime);
        if (permissionListener != null) {
            permissionListener.shouldShowRequestPermissionRationale(
                    convertRequestPermissionsToPermissisonGroups(shouldShowRequestPermissions));
        }
    }

    void removeListener(long requestTime) {
        LogUtil.i(TAG, "removeListener = " + mListenerMap.get(requestTime));
        mListenerMap.remove(requestTime);
    }

    private List<PermissionGroup> convertRequestPermissionsToPermissisonGroups(Collection<String> requestPermissions) {
        List<PermissionGroup> permissionGroups = new ArrayList<>();
        if (requestPermissions == null || requestPermissions.isEmpty()) {
            return permissionGroups;
        }
        for (String requestPermission : requestPermissions) {
            PermissionGroup permissionGroup = PermissionGroup.findPermissionGroupByRequestPermission(requestPermission);
            permissionGroups.add(permissionGroup);
        }
        return permissionGroups;
    }

    public boolean hasPermission(PermissionGroup... permissionGroups) {
        if (permissionGroups == null || permissionGroups.length == 0) {
            throw new RuntimeException(TAG + ": 请至少传入一个权限！");
        }
        for (PermissionGroup permissionGroup : permissionGroups) {
            String requestPermission = permissionGroup.getRequestPermission();
            if (ActivityCompat.checkSelfPermission(SdkConfig.getContext(), requestPermission) !=
                    PackageManager.PERMISSION_GRANTED) {
                return false;
            }

        }
        return true;
    }

}
