package com.shop.backkitchen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.shop.backkitchen.db.table.ShopCategory;
import com.shop.backkitchen.httpserver.dao.CategoryDao;
import com.shop.backkitchen.service.HttpService;
import com.shop.backkitchen.util.IpGetUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView)findViewById(R.id.tv_ip)).setText(IpGetUtil.getLocalIpAddress());
        startService(new Intent(this,HttpService.class));
//        for (int i = 0; i < 6; i++) {
//            ShopCategory shopCategory = new ShopCategory();
//            shopCategory.name = "测试品类"+i;
//            shopCategory.description = "测试品类测试品类测试品类测试品类测试品类测试品类"+i;
//            shopCategory.picPath= "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543242177152&di=4f794bbf5417d119e6e33a3cbfd26b82&imgtype=0&src=http%3A%2F%2Fpic2.16pic.com%2F00%2F15%2F31%2F16pic_1531045_b.jpg";
//            shopCategory.shopNames = new ArrayList<>();
//            for (int j = 0; j < 5; j++) {
//                ShopName shopName = new ShopName();
//                shopName.name = "测试商品"+j;
//                shopName.description = "测试商品测试商品测试商品测试商品测试商品description"+j;
//                shopName.categoryId = i;
//                shopName.isShelf = 1;
//                shopName.picPath= "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543242177152&di=4f794bbf5417d119e6e33a3cbfd26b82&imgtype=0&src=http%3A%2F%2Fpic2.16pic.com%2F00%2F15%2F31%2F16pic_1531045_b.jpg";
//                shopName.price = (1+j)*100;
//                shopCategory.shopNames.add(shopName);
//            }
//            shopCategory.save();
//        }
        List<ShopCategory> shopCategories = CategoryDao.getAllCategory();

    }
}
