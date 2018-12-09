package com.shop.backkitchen.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raizlabs.android.dbflow.structure.BaseModel;
import com.shop.backkitchen.R;
import com.shop.backkitchen.db.table.ShopName;
import com.shop.backkitchen.db.table.ShopOrder;
import com.shop.backkitchen.util.BigDecimalUtil;
import com.shop.backkitchen.util.CommonToast;
import com.shop.backkitchen.util.CurrencyUtil;
import com.shop.backkitchen.util.ResourcesUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author mengjie6
 * @date 2018/12/2
 */

public class ShopOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<ShopOrder> shopOrders;
    private Activity context;

    public ShopOrderAdapter(Activity context) {
        this.context = context;
    }

    public void setShopOrders(List<ShopOrder> orders) {
        this.shopOrders = orders;
    }

    public void addShopOrders(ShopOrder orders) {
        if (orders == null){
            return;
        }
        if (shopOrders == null){
            shopOrders = new ArrayList<>();
        }
        shopOrders.add(orders);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof ShopOrder){
            return 0;
        }else {
            return 1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case 0:
                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_shop_order, viewGroup, false);
                holder = new ShopOrderHolder(v);
                break;
            default:
                View v1 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_shop_order_name, viewGroup, false);

                holder = new ShopViewHolder(v1,this);
                break;

        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        BaseModel baseModel = getItem(i);
        if (viewHolder instanceof ShopOrderHolder && baseModel instanceof ShopOrder){
            ShopOrder shopOrder = (ShopOrder) baseModel;
            ShopOrderHolder holder = (ShopOrderHolder) viewHolder;
            holder.tvName.setText(String.valueOf(shopOrder.number));
            holder.tvPrice.setText(CurrencyUtil.numberFormatMoney(BigDecimalUtil.div(shopOrder.price)));
            holder.tvTime.setText(formatTime(shopOrder.time));
        }else if (viewHolder instanceof ShopViewHolder && baseModel instanceof ShopName){
            ShopName  shopName = (ShopName) baseModel;
            ShopViewHolder shopViewHolder = (ShopViewHolder) viewHolder;
            shopViewHolder.tvName.setText(shopName.name);
            shopViewHolder.tvNumber.setText(String.format(ResourcesUtils.getString(R.string.share),shopName.number));
            if (shopName.isFinish){
                shopViewHolder.tvConfirm.setVisibility(View.VISIBLE);
                shopViewHolder.tvConfirm.setTag(i);
            }else {
                shopViewHolder.tvConfirm.setVisibility(View.GONE);
            }
        }
    }

    private String formatTime(long time){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1=new Date(time);
        String t1=format.format(d1);
        Log.e("msg", t1);
        return t1;
    }

    private BaseModel getItem(int position) {
        if (position < 0) {
            return null;
        } else if (shopOrders == null || shopOrders.isEmpty()) {
            return null;
        }

        for (ShopOrder order:shopOrders) {
            if (position == 0){
                return order;
            }
            position --;
            if (order.shopItem == null || order.shopItem.isEmpty()){
                continue;
            }
            if (position > order.shopItem.size()){
                position -= order.shopItem.size();
                continue;
            }
            for (ShopName name:order.shopItem) {
                if (position == 0){
                    if(order.shopItem.size()-1 == order.shopItem.indexOf(name)){
                        name.isFinish = true;
                    }
                    return name;
                }
                position --;
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        if (shopOrders == null){
            return 0;
        }
        int count = 0;
        for (ShopOrder order:shopOrders) {
            if (order.shopItem == null){
                continue;
            }
            count += order.shopItem.size();
        }
        count += shopOrders.size();
        return count;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                commitOrder(v);
                break;
        }
    }

    private void commitOrder(View v) {
        if (v == null){
            return;
        }
        Object obj = v.getTag();
        if (!(obj instanceof Integer)){
            return;
        }
        int position = (int) v.getTag();
        if (position < 0){
            return;
        }
        ShopOrder shopOrder = getShopOrderItem(position);
        if (shopOrder == null){
            return;
        }
        CommonToast.showProgressDialog(context,ResourcesUtils.getString(R.string.commiting));
        shopOrder.orderStatus = 3;
        if (shopOrder.update()){
            CommonToast.cancelProgressDialog();
            shopOrders.remove(shopOrder);
            notifyDataSetChanged();
//            removeItem(shopOrder);
        }else {
            CommonToast.cancelProgressDialog();
        }
    }

    private void removeItem(ShopOrder shopOrder) {
        if (shopOrder == null){
            return;
        }
        int startPosition = -1;
        int endPosition = -1;
        for (ShopOrder order:shopOrders) {
            startPosition++;
            if (shopOrder.equals(order)){
                endPosition = startPosition;
                if (shopOrder.shopItem != null && !shopOrder.shopItem.isEmpty()){
                   endPosition += shopOrder.shopItem.size();
                }
                break;
            }else {
                if (shopOrder.shopItem != null && !shopOrder.shopItem.isEmpty()){
                    startPosition += shopOrder.shopItem.size();
                }
            }
        }
        if (startPosition <0 || endPosition == -1){
            return;
        }
        shopOrders.remove(shopOrder);
        if (startPosition == endPosition){
            notifyItemRemoved(startPosition);
        }else if (endPosition > startPosition){
            notifyItemRangeRemoved(startPosition,endPosition-startPosition);
        }

    }

    private ShopOrder getShopOrderItem(int position) {
        if (position < 0) {
            return null;
        } else if (shopOrders == null || shopOrders.isEmpty()) {
            return null;
        }

        for (ShopOrder order:shopOrders) {
            if (position == 0){
                return order;
            }
            position --;
            if (order.shopItem == null || order.shopItem.isEmpty()){
                continue;
            }
            if (position > order.shopItem.size()){
                position -= order.shopItem.size();
                continue;
            }
            return order;
        }
        return null;
    }

    public class ShopViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvNumber;
        TextView tvConfirm;

        public ShopViewHolder(@NonNull View itemView, View.OnClickListener listener) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvNumber = (TextView) itemView.findViewById(R.id.tv_number);
            tvConfirm = (TextView) itemView.findViewById(R.id.tv_confirm);
            if (tvConfirm != null) {
                tvConfirm.setOnClickListener(listener);
            }
        }
    }

    public class ShopOrderHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvPrice;
        TextView tvTime;

        public ShopOrderHolder(@NonNull View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }

}
