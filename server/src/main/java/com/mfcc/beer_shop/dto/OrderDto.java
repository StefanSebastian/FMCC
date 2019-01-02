package com.mfcc.beer_shop.dto;

import java.util.List;

/**
 * @author stefansebii@gmail.com
 */
public class OrderDto {
    private String address;
    private List<OrderItemDto> orderItems;

    public OrderDto() {}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }
}
