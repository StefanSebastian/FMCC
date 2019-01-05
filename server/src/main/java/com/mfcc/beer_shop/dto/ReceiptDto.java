package com.mfcc.beer_shop.dto;

import com.mfcc.beer_shop.model.Receipt;

/**
 * @author stefansebii@gmail.com
 */
public class ReceiptDto {
    private String address;
    private String description;
    private float totalPrice;

    public ReceiptDto() {}

    public ReceiptDto(Receipt receipt) {
        this.address = receipt.getAddress();
        this.description = receipt.getDescription();
        this.totalPrice = receipt.getTotalPrice();
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
