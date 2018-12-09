package com.shop.backkitchen.permission;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.shop.backkitchen.util.LogUtil;
import com.shop.backkitchen.util.SdkConfig;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 */

public class PermissionActivity extends Activity {
    private static final String NOT_GRANTED_PERMISSIONS = "not_granted_permissions";
    private static final String GRANTED_PERMISSIONS = "granted_permissions";
    private static final String REQUEST_TIME = "REQUEST_TIME";
    private static final int DEFAULT_REQUEST_CODE = 0;
    private ArrayList<String> mNotGrantedPermissions;
    private ArrayList<String> mGrantedPermissions;
    private long mRequestTime;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        LogUtil.e("PermissionTest", this + " onCreate()");
        parseIntent();
        requestPermissions();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //统计时长
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void requestPermissions() {
        if (mNotGrantedPermissions != null && !mNotGrantedPermissions.isEmpty()) {
            ArrayList<String> shouldShowRationalePermissions = new ArrayList<>();
            for (String requestPermission : mNotGrantedPermissions) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, requestPermission)) {
                    shouldShowRationalePermissions.add(requestPermission);
                }
            }
            if (!shouldShowRationalePermissions.isEmpty()) {
                PermissionManager.getInstance().shouldShowRequestPermissionRationale(mRequestTime,
                        shouldShowRationalePermissions);
            }
            ActivityCompat.requestPermissions(this, mNotGrantedPermissions.toArray(new String[mNotGrantedPermissions.size()]),
                    DEFAULT_REQUEST_CODE);
        }
    }


    private void parseIntent() {
        Intent intent = getIntent();
        Serializable notGrantedPermission = intent.getSerializableExtra(NOT_GRANTED_PERMISSIONS);
        Serializable grantedPermission = intent.getSerializableExtra(GRANTED_PERMISSIONS);
        if (notGrantedPermission != null && notGrantedPermission instanceof ArrayList) {
            mNotGrantedPermissions = (ArrayList<String>) notGrantedPermission;
        }
        if (grantedPermission != null && grantedPermission instanceof ArrayList) {
            mGrantedPermissions = (ArrayList<String>) grantedPermission;
        }
        mRequestTime = intent.getLongExtra(REQUEST_TIME, 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LogUtil.e("PermissionTest", this + " requestCode = " + requestCode);
        LogUtil.e("PermissionTest", this + " permissions = " + permissions);
        LogUtil.e("PermissionTest", this + " grantResults = " + grantResults);
        switch (requestCode) {
            case DEFAULT_REQUEST_CODE:
                handleRequestPermissionResult(permissions, grantResults);
                break;
        }
    }

    private void handleRequestPermissionResult(String[] permissions, int[] grantResults) {
        ArrayList<String> deniedPermissions = new ArrayList<>();
        ArrayList<String> grantedPermissions = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            int result = grantResults[i];
            if (result == PackageManager.PERMISSION_GRANTED) {
                grantedPermissions.add(permissions[i]);
            } else {
                deniedPermissions.add(permissions[i]);
            }
        }
        addBeforeGrantedPermissions(grantedPermissions);
        if (!deniedPermissions.isEmpty()) {
            PermissionManager.getInstance().deniedPermissions(mRequestTime, deniedPermissions, grantedPermissions);
        } else {
            PermissionManager.getInstance().grantedAllPermissions(mRequestTime);
        }
        PermissionManager.getInstance().removeListener(mRequestTime);
        finish();
    }

    private void addBeforeGrantedPermissions(ArrayList<String> grantedPermissions) {
        if (mGrantedPermissions != null && !mGrantedPermissions.isEmpty()) {
            grantedPermissions.addAll(mGrantedPermissions);
        }
    }

    public static void skip(long requestTime, ArrayList<String> notGrantedPermissions, ArrayList<String> grantedPermissions) {
        LogUtil.e("PermissionTest", "PermissionActivity skip()");
        Intent intent = new Intent();
        intent.setClass(SdkConfig.getContext(), PermissionActivity.class);
        intent.putExtra(REQUEST_TIME, requestTime);
        intent.putExtra(NOT_GRANTED_PERMISSIONS, notGrantedPermissions);
        intent.putExtra(GRANTED_PERMISSIONS, grantedPermissions);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        SdkConfig.getContext().startActivity(intent);
    }
}
