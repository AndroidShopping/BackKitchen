package com.shop.backkitchen.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shop.backkitchen.R;
import com.shop.backkitchen.base.BaseActivity;
import com.shop.backkitchen.db.table.ShopName;
import com.shop.backkitchen.upload.UploadPic;
import com.shop.backkitchen.util.BigDecimalUtil;
import com.shop.backkitchen.util.CommonToast;
import com.shop.backkitchen.util.RealPathFromUriUtils;
import com.shop.backkitchen.util.ResourcesUtils;

/**
 * 添加商品
 *
 * @author mengjie6
 * @date 2018/12/01
 */
public class ShopAddActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_add_shop;
    private ImageView iv_icon;
    private EditText et_name;
    private EditText et_price;

    //相册请求码
    private static final int ALBUM_REQUEST_CODE = 1;
    private String realPathFromUri;
    private int categoryId = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);
        tv_add_shop = findViewById(R.id.tv_add_shop);
        iv_icon = findViewById(R.id.iv_icon);
        et_name = findViewById(R.id.et_name);
        et_price = findViewById(R.id.et_price);
        InputFilter[] filters = {new EditInputFilter()};
        et_price.setFilters(filters);
        iv_icon.setOnClickListener(this);
        tv_add_shop.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add_shop:
                // TODO: 2018/12/1 提交订单
                commitShop();
                break;
            case R.id.iv_icon:
                // TODO: 2018/12/1 打开相机
                albmPermission();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void commitShop() {
        categoryId = 1;
        if (TextUtils.isEmpty(realPathFromUri)){
            return;
        }
        if (TextUtils.isEmpty(et_name.getText().toString())){
            return;
        }
        if (TextUtils.isEmpty(et_price.getText().toString())){
            return;
        }
        if (categoryId < 0){
            return;
        }
        tv_add_shop.setEnabled(false);
        final ShopName shopName = new ShopName();
        shopName.name = et_name.getText().toString();
        shopName.price = BigDecimalUtil.mul(et_price.getText().toString(),100);
        shopName.categoryId = categoryId;
        CommonToast.showProgressDialog(thisContext,ResourcesUtils.getString(R.string.commiting));
        UploadPic.upload(realPathFromUri, new UploadPic.IUploadListener() {
            @Override
            public void onSuccess(String ads) {
                shopName.picPath = ads;
                if (shopName.save()){
                    reset();
                    Toast.makeText(thisContext,ResourcesUtils.getString(R.string.commit_success),Toast.LENGTH_SHORT).show();
                }else {
                    tv_add_shop.setEnabled(true);
                    Toast.makeText(thisContext,ResourcesUtils.getString(R.string.commit_failure),Toast.LENGTH_SHORT).show();
                }
                CommonToast.cancelProgressDialog();
            }

            @Override
            public void onFail(String message) {

            }
        });
    }

    private void reset() {
        categoryId = -1;
        et_price.setText("");
        et_name.setText("");
        realPathFromUri = "";
        iv_icon.setImageResource(R.drawable.shop_add_icon);
        tv_add_shop.setEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == ALBUM_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPicFromAlbm();
            } else {
                // Permission Denied
                Toast.makeText(this, ResourcesUtils.getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void albmPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {   //权限还没有授予，需要在这里写申请权限的代码
            // 第二个参数是一个字符串数组，里面是你需要申请的权限 可以设置申请多个权限
            // 最后一个参数是标志你这次申请的权限，该常量在onRequestPermissionsResult中使用到
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    ALBUM_REQUEST_CODE);

        } else { //权限已经被授予，在这里直接写要执行的相应方法即可
            getPicFromAlbm();
        }
    }

    /**
     * 从相册获取图片
     */
    private void getPicFromAlbm() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, ALBUM_REQUEST_CODE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case ALBUM_REQUEST_CODE: {
                // 获取图片
                try {
                    //该uri是上一个Activity返回的
                    Uri imageUri = data.getData();
                    if (imageUri != null) {
                        Glide.with(this)
                                .load(imageUri)
                                .centerCrop()
                                .into(iv_icon);
                        realPathFromUri = RealPathFromUriUtils.getRealPathFromUri(this, imageUri);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
