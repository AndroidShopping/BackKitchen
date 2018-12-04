package com.shop.backkitchen.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shop.backkitchen.R;
import com.shop.backkitchen.db.table.ShopName;
import com.shop.backkitchen.util.BigDecimalUtil;
import com.shop.backkitchen.util.CurrencyUtil;
import com.shop.backkitchen.util.ResourcesUtils;

import java.util.List;

/**
 * @author mengjie6
 * @date 2018/12/1
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> implements View.OnClickListener {

    private List<ShopName> shopNames;
    private Activity context;

    public ShopAdapter(Activity context) {
        this.context = context;
    }

    public void setShopNames(List<ShopName> shopNames) {
        this.shopNames = shopNames;
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_shop, viewGroup, false);
        return new ShopViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder shopViewHolder, int i) {
        ShopName shopName = getItem(i);
        shopViewHolder.tvName.setText(String.valueOf(shopName.name));
        switch (shopName.isShelf) {
            case 1:
                shopViewHolder.tvStatus.setSelected(false);
                shopViewHolder.tvStatus.setText(ResourcesUtils.getString(R.string.shop_obtained));
                break;
            default:
                shopViewHolder.tvStatus.setSelected(true);
                shopViewHolder.tvStatus.setText(ResourcesUtils.getString(R.string.shop_shelf));
                break;
        }
        shopViewHolder.tvStatus.setTag(i);
        if (shopName.picPath != null) {
            Glide.with(context).load(shopName.picPath).centerCrop()
                    .error(R.drawable.not_pic)
                    .placeholder(R.drawable.not_pic)
                    .into(shopViewHolder.ivIcon);
        }else {
            shopViewHolder.ivIcon.setImageResource(R.drawable.not_pic);
        }

        // TODO: 2018/12/1 设置价格
        shopViewHolder.tvPrice.setText(CurrencyUtil.numberFormatMoney(BigDecimalUtil.div(shopName.price)));

    }

    private ShopName getItem(int position) {
        if (position < 0) {
            return null;
        } else if (shopNames == null || shopNames.isEmpty()) {
            return null;
        }
        return shopNames.get(position);
    }

    @Override
    public int getItemCount() {
        return shopNames == null ? 0 : shopNames.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_status:
                // TODO: 2018/12/1 上架下架
                int position = (int) v.getTag();
                ShopName shopName = getItem(position);
                if (shopName == null){
                    return;
                }
                if (shopName.isShelf == 1){
                    shopName.isShelf = 0;
                }else {
                    shopName.isShelf = 1;
                }
                if (shopName.update()){
                    notifyItemChanged(position);
                }
                break;
        }
    }

    public class ShopViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvName;
        TextView tvStatus;
        TextView tvPrice;

        public ShopViewHolder(@NonNull View itemView, View.OnClickListener listener) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvName = itemView.findViewById(R.id.tv_name);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tvPrice = itemView.findViewById(R.id.tv_price);
            if (tvStatus != null) {
                tvStatus.setOnClickListener(listener);
            }
        }
    }
}
