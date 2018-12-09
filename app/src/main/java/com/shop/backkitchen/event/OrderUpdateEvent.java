package com.shop.backkitchen.event;

import com.shop.backkitchen.db.table.ShopOrder;

/**
 * @author mengjie6
 * @date 2018/11/30
 */

public class OrderUpdateEvent {

    public ShopOrder getShopOrder() {
        return shopOrder;
    }

    public void setShopOrder(ShopOrder shopOrder) {
        this.shopOrder = shopOrder;
    }

    private ShopOrder shopOrder;
    public OrderUpdateEvent(ShopOrder shopOrder) {
        this.shopOrder = shopOrder;
    }
}
