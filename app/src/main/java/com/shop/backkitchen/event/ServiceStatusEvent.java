package com.shop.backkitchen.event;

/**
 * @author mengjie6
 * @date 2018/11/30
 */

public class ServiceStatusEvent {

    public ServiceStatusEvent(boolean serviceStatus) {
        this.serviceStatus = serviceStatus;
    }
    public boolean serviceStatus;
}
